package com.lenovots.crm.admin.action;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lenovots.crm.admin.entity.Notice;
import com.lenovots.crm.admin.entity.Operator;
import com.lenovots.crm.admin.entity.Permission;
import com.lenovots.crm.admin.entity.SystemConfig;
import com.lenovots.crm.admin.service.INoticeService;
import com.lenovots.crm.admin.service.IOperatorService;
import com.lenovots.crm.admin.service.ISystemConfigService;
import com.lenovots.crm.common.action.BaseAction;
import com.lenovots.crm.util.CommonUtil;
import com.lenovots.crm.util.PropertiesUtil;


@Controller("mainAction")
@Scope("prototype")
public class MainAction extends BaseAction<Operator> implements Serializable{

	private static Logger logger = Logger.getLogger(MainAction.class);
	
	private static final long serialVersionUID = 1L;
	
	private String checkCode;
	
	private Operator operator;
	
	private Set<Permission> permissionList;
	
	@Resource
	private INoticeService noticeService;
	@Resource
	private ISystemConfigService systemConfigService;
    @Resource
    private IOperatorService operatorService;
   
    
    private List<Notice> noticeList;
    
    private SystemConfig systemConfig;
    
	public Set<Permission> getPermissionList() {
		return permissionList;
	}
	public void setPermissionList(Set<Permission> permissionList) {
		this.permissionList = permissionList;
	}
	public void prepareLogin() {
		operator = new Operator();
	}
	public String login() {
		
		SystemConfig systemConfig=null;
		
		List<SystemConfig> configList=systemConfigService.findAll();
		if(configList!=null&&configList.size()>0){
			systemConfig=configList.get(0);
			ServletActionContext.getRequest().getSession().setAttribute("systemConfig",systemConfig);
		}else{
			logger.error("基本设置无效!");
			addFieldError("error", "系统缺失基本配置信息:there must be one record of SystemConfig in database.");
			return INPUT;
		}
		
		String code=(String) ServletActionContext.getRequest().getSession().getAttribute("checkcode");
		
		 if(systemConfig.getUseCheckCode()==1&&(checkCode==null||code==null||!checkCode.equals(code))){
			addFieldError("error", "验证码输入错误!");
			return INPUT;
		}
		
		
		String pwd=operator.getPassword();
		String name=operator.getLoginName();
		if(pwd==null||pwd.trim().equals("")||name==null||name.trim().equals("")){
			addFieldError("error", "请填写用户名和密码");
			return INPUT;
		}
		
		
		String password = CommonUtil.getMd5Code(operator.getPassword().trim());//String password ="倈�陫plL4戻N{";//
		
		List<Operator> operators = operatorService.findByCause(new String[] { "loginName","password" }, new Object[] { operator.getLoginName(), password });
		if (operators.size() == 0) {
			addFieldError("error", "用户名或密码错误");
			logger.info("info:operator:"+operator.getLoginName()+"login failed, with password:"+operator.getPassword()+"  "+password+"("+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+")");
			return INPUT;
		}
		logger.info("用户"+operator.getLoginName()+"登录系统成功。("+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+")");
		
		operator = operators.get(0);
		ServletActionContext.getRequest().getSession().setAttribute("OPERATOR",operator);
		
		permissionList=new TreeSet<Permission>(new Comparator<Permission>(){

			public int compare(Permission o1, Permission o2) {
				
				return o1.getId()-o2.getId();
				
			}
			
		});
		
		permissionList.addAll(operator.getRole().getPermissions());
		
	    Set<Permission> resultPList = new TreeSet<Permission>(new Comparator<Permission>(){
			@Override
			public int compare(Permission o1, Permission o2) {
				return o1.getSortValue()-o2.getSortValue();
			}
			
		});
		
		//每一个父亲找到自己的儿子，并接到自己的家里
		for(Permission parent:permissionList){
			if(parent.getParent()==null&&parent.getDisplay()==1){//父亲的合法身份
				//开始查找自己的儿子
				parent.setChildren(new TreeSet<Permission>(new Comparator<Permission>(){
					@Override
					public int compare(Permission o1, Permission o2) {
						return o1.getSortValue()-o2.getSortValue();
					}
				}));
				for(Permission son:permissionList){
					if(son.getParent()!=null&&son.getParent().getId().equals(parent.getId())&&son.getDisplay()==1){
						parent.getChildren().add(son);//接走
					}
				}
				//找完儿子之后，各个父亲集合
				resultPList.add(parent);
			}
		}

		ServletActionContext.getRequest().getSession().setAttribute("permissionList", resultPList);
		ServletActionContext.getRequest().getSession().setAttribute("systemConfig", systemConfig=systemConfigService.findById(1));
		ServletActionContext.getRequest().getSession().setAttribute("styleName", operator.getStyleName()==null?"base":operator.getStyleName());
		return SUCCESS;
	}

	public String loginOut() {
		Operator operator=(Operator) ServletActionContext.getRequest().getSession().getAttribute("OPERATOR");
		if(operator!=null){
			logger.info("用户"+operator.getLoginName()+"退出了系统。("+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())+")");
		}
		ServletActionContext.getRequest().getSession().removeAttribute("OPERATOR");
		ServletActionContext.getRequest().getSession().removeAttribute("permissionList");
		ServletActionContext.getRequest().getSession().removeAttribute("styleName");
		
		systemConfig=systemConfigService.findById(1);
		return SUCCESS;
	}

	public String loginUI() {
		clearFieldErrors();
		systemConfig=systemConfigService.findById(1);
		return SUCCESS;
	}

	public String loginSuccess() {
		permissionList = (TreeSet<Permission>) ServletActionContext.getRequest().getSession().getAttribute("permissionList");
		return SUCCESS;
	}

	public String menu() {
		permissionList = (TreeSet<Permission>) ServletActionContext.getRequest().getSession().getAttribute("permissionList");
		return SUCCESS;
	}

	public String admin() {
		noticeList=noticeService.findByHql("FROM Notice n ORDER BY n.id DESC", 0, 5,null);
		return SUCCESS;
	}
	
	
	/**
	 * 软件注册
	 * @return
	 */
	public String regist(){
		String registCode = (String) getParameter("registCode");
		if(registCode!=null){
			registCode= registCode.replaceAll("\t", "");
			registCode= registCode.replaceAll("\r", "");
			registCode= registCode.replaceAll("\n", "");
			registCode= registCode.replaceAll(" ", "");
			PropertiesUtil.writeProperties("setting.properties",this.getClass().getResource("/").getPath(), "license", registCode);
		}
		return "loginUI";
	}
	
	@Override
	public String addUI() throws Exception {
		return null;
	}

	@Override
	public String delete() throws Exception {
		return null;
	}

	@Override
	public String list() throws Exception {
		return null;
	}

	@Override
	protected void prepareModel() throws Exception {
		if(id==null){
			operator=new Operator();
		}else{
			operator=operatorService.findById(id);
		}
	}

	@Override
	public String updateUI() throws Exception {
		return null;
	}

	@Override
	public Operator getModel() {
		return operator;
	}

	@Override
	public String add() throws Exception {
		return null;
	}

	@Override
	public String update() throws Exception {
		return null;
	}

	public String getCheckCode() {
		return checkCode;
	}

	public void setCheckCode(String checkCode) {
		this.checkCode = checkCode;
	}

	@Override
	public Operator getOperator() {
		return operator;
	}

	@Override
	public void setOperator(Operator operator) {
		this.operator = operator;
	}
	public List<Notice> getNoticeList() {
		return noticeList;
	}
	public void setNoticeList(List<Notice> noticeList) {
		this.noticeList = noticeList;
	}
	public SystemConfig getSystemConfig() {
		return systemConfig;
	}
	public void setSystemConfig(SystemConfig systemConfig) {
		this.systemConfig = systemConfig;
	}
	
}
