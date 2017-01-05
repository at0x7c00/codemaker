package com.lenovots.crm.admin.action;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.lenovots.crm.admin.entity.Dept;
import com.lenovots.crm.admin.entity.Job;
import com.lenovots.crm.admin.entity.Operator;
import com.lenovots.crm.admin.entity.Role;
import com.lenovots.crm.admin.service.IDepartmentService;
import com.lenovots.crm.admin.service.IJobService;
import com.lenovots.crm.admin.service.IOperatorService;
import com.lenovots.crm.admin.service.IRoleService;
import com.lenovots.crm.common.action.BaseAction;
import com.lenovots.crm.util.CommonUtil;



@Controller("operatorAction")
@Scope(value="prototype")
public class OperatorAction extends BaseAction<Operator> {
	private static final long serialVersionUID = 8856961694147206209L;

	@Resource
	private IDepartmentService departmentService;
	@Resource
	private IRoleService roleService;
	@Resource
	private IJobService jobService;
	@Resource
	private IOperatorService operatorService;
	
	private Operator operator;
	
	private List<Dept> deptList;
	private List<Job> jobList;
	private List<Role> roleList;
	
	private String inputName;
	private String inputLogin;
	private String inputPhone;
	private Integer deptId;
	private Integer jobId;
	private Integer roleId;
	private Operator addOperator;
	
	private String changePwd;
	private String oldPwd;
	private String password1;
	
	private String initPwd;
	
	
	@Override
	public String list() throws Exception {
		List<Dept> tempList = departmentService.findByHql("from Dept d where d.mainDept is null",
			null);
		deptList = new ArrayList<Dept>();
		CommonUtil.tree(tempList, "", deptList, null);
		roleList = roleService.findAll();
		jobList = jobService.findAll();
		if (msg != null) {
			msg = URLDecoder.decode(msg, "utf-8");
		}
		if (pageNum == null || pageNum < 0) {
			pageNum = 0;
		}
		if (pageSize == null || pageSize <= 0) {
			pageSize = 10;
		}
		List<Object> list = new ArrayList<Object>();
		StringBuffer hql = new StringBuffer();
		hql.append("FROM Operator op where op.id is not null ");
		if (null != inputName && !"".equals(inputName)) {
			hql.append("AND op.name like ? ");
			list.add("%"+inputName.trim()+"%");
		}
		if (null != inputLogin && !"".equals(inputLogin)) {
			hql.append("AND op.loginName like ? ");
			list.add("%"+inputLogin.trim()+"%");
		}
		if (null != inputPhone && !"".equals(inputPhone)) {
			hql.append("AND (op.phoneNumber1 like ? or op.phoneNumber2 like ?)");
			list.add("%"+inputPhone.trim()+"%");
			list.add("%"+inputPhone.trim()+"%");
		}
		if (null != deptId && deptId != -1) {
			hql.append("AND op.dept.id=? ");
			list.add(deptId);
		}
		if (null != jobId && jobId != -1) {
			hql.append("AND op.job.id=? ");
			list.add(jobId);
		}
		if (null != roleId && roleId != -1) {
			hql.append("AND op.role.id=? ");
			list.add(roleId);
		}
		hql.append(" ORDER BY op.id ASC");
		pageBean = this.operatorService.findPageBeanOfHql(hql.toString(), pageNum, pageSize, list
			.toArray());
		return SUCCESS;
	}
	
	
	@Override
	public String add() throws Exception {
		addOperator.setDept(departmentService.findById(deptId));
		addOperator.setRole(roleService.findById(roleId));
		addOperator.setJob(jobService.findById(jobId));
		if (check()) {
			if (initPwd != null) {// 使用默认密码
				addOperator.setPassword(initPwd);
			} else {// 使用输入密码
				// 已经复制到对象上了
			}
			String temp=addOperator.getPassword();
			addOperator.setPassword(CommonUtil.getMd5Code(addOperator.getPassword().trim()));
			operatorService.add(addOperator);
			msg = "<script type='text/javascript' language='javascript'>alert('提交成功，恭喜您添加成功！"+"("+temp+")"+addOperator.getPassword()+"');</script>";
			msg = URLEncoder.encode(msg, "UTF-8");
			return RELOAD;
		} else {
			prepareData();
			msg = "<script type='text/javascript' language='javascript'>alert('很遗憾，您添加的用户名已存在！');</script>";
			// msg = URLEncoder.encode(msg, "UTF-8");
			return INPUT;
		}
	}

	private void prepareData() {
		List<Dept> tempList = departmentService.findByHql("from Dept d where d.mainDept is null",
			null);
		deptList = new ArrayList<Dept>();
		CommonUtil.tree(tempList, "", deptList, null);
		roleList = roleService.findAll();
		jobList = jobService.findAll();
	}
	
	private boolean check() {
		List<Operator> operatorList = this.operatorService.findByHql(
			"from Operator o where o.loginName = ? and o.id != ?", new Object[] { operator
				.getLoginName(), operator.getId() });
		if (operatorList != null && operatorList.size() > 0)
			return false;
		return true;
	}
	
	@Override
	public String addUI() throws Exception {
		prepareData();
		return SUCCESS;
	}

	@Override
	public String update() throws Exception {
		if (check()) {
			if (process()) {
				return RELOAD;
			} else {
				prepareData();
				return INPUT;
			}
		} else {
			prepareData();
			msg = "<script type='text/javascript' language='javascript'>alert('很遗憾，修改失败。您修改的用户名已存在！');</script>";
			// msg = URLEncoder.encode(msg, "UTF-8");
			return INPUT;
		}
	}
	
	private boolean process() throws Exception {
		operator.setDept(departmentService.findById(deptId));

		operator.setRole(roleService.findById(roleId));

		operator.setJob(jobService.findById(jobId));

		if (initPwd != null) {// 使用默认密码
			operator.setPassword(CommonUtil.getMd5Code(initPwd));
		} else if (changePwd != null) {// 修改了密码
			// 验证旧密码
			if (CommonUtil.getMd5Code(oldPwd).equals(operator.getPassword().trim())) {
				operator.setPassword(CommonUtil.getMd5Code(password1));
			} else {
				msg = "<script type='text/javascript' language='javascript'>alert('修改密码失败，旧密码输入不正确！');</script>";
				// msg = URLEncoder.encode(msg, "UTF-8");
				return false;
			}
		}
		operatorService.update(operator);
		if(operator.getId().equals(getCurUserId())){
			ServletActionContext.getRequest().getSession().setAttribute("OPERATOR", operator);
		}
		return true;
	}

	public void prepareInfoSet() {
			operator = super.getOperator();
	}
	
	/**
	 * 个人信息设置
	 * @return
	 */
	public String infoSet() {
		if (changePwd != null) {// 修改了密码
			// 验证旧密码
			if (CommonUtil.getMd5Code(oldPwd).equals(operator.getPassword())) {
				operator.setPassword(CommonUtil.getMd5Code(password1));
			} else {
				msg = "<script type='text/javascript' language='javascript'>alert('修改密码失败，旧密码输入不正确！');</script>";
			}
		}
		operatorService.update(operator);
		ServletActionContext.getRequest().getSession().setAttribute("OPERATOR", operator);
		return SUCCESS;
	}

	

	@Override
	public String updateUI() throws Exception {
		prepareData();
		return SUCCESS;
	}

	@Override
	protected void prepareModel() throws Exception {
		if(id!=null){
			operator = operatorService.findById(id);
		}else{
			operator = new Operator();
		}
	}
	
	@Override
	public Operator getModel() {
		return operator;
	}

	public List<Dept> getDeptList() {
		return deptList;
	}

	public void setDeptList(List<Dept> deptList) {
		this.deptList = deptList;
	}

	public List<Job> getJobList() {
		return jobList;
	}

	public void setJobList(List<Job> jobList) {
		this.jobList = jobList;
	}

	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	public String getInputName() {
		return inputName;
	}

	public void setInputName(String inputName) {
		this.inputName = inputName;
	}

	public String getInputLogin() {
		return inputLogin;
	}

	public void setInputLogin(String inputLogin) {
		this.inputLogin = inputLogin;
	}

	public String getInputPhone() {
		return inputPhone;
	}

	public void setInputPhone(String inputPhone) {
		this.inputPhone = inputPhone;
	}

	public Integer getDeptId() {
		return deptId;
	}

	public void setDeptId(Integer deptId) {
		this.deptId = deptId;
	}

	public Integer getJobId() {
		return jobId;
	}

	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Override
	public Operator getOperator() {
		return operator;
	}

	@Override
	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public Operator getAddOperator() {
		return addOperator;
	}

	public void setAddOperator(Operator addOperator) {
		this.addOperator = addOperator;
	}

	public String getInitPwd() {
		return initPwd;
	}

	public void setInitPwd(String initPwd) {
		this.initPwd = initPwd;
	}

	public String getChangePwd() {
		return changePwd;
	}

	public void setChangePwd(String changePwd) {
		this.changePwd = changePwd;
	}

	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	public String getPassword1() {
		return password1;
	}

	public void setPassword1(String password1) {
		this.password1 = password1;
	}
	
}
