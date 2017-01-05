package com.lenovots.crm.common.action;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;


import com.lenovots.crm.admin.entity.Operator;
import com.lenovots.crm.common.service.ICommonService;
import com.lenovots.crm.util.PageBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

@SuppressWarnings("unchecked")
public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T>, Preparable {
	
	private static final long serialVersionUID = -1653204626115064950L;
	
	public static final String REDIRECT = "redirect";
	public static final String ANYACTION = "anyaction";
	public static final String ANYFORWARD = "anyforward";
	
	protected String styleName;
	
	protected Class clazz;

	public BaseAction() {
		ParameterizedType pt = (ParameterizedType) this.getClass().getGenericSuperclass();
		this.clazz = (Class) pt.getActualTypeArguments()[0];
		try {
			entity = (T)clazz.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@Resource
	protected ICommonService baseService;
	
	protected String op;
	protected String anyAction;
	protected Integer id;
	protected String msg;
	protected String orderBy;
	protected String direction="asc";//排序方向
	
	protected T entity;
	
	protected List list;
	protected Operator operator;
	protected Integer[] ids;
	
	protected String desk=null;
    	
	protected Integer errorType;//1.网页过期
	protected Integer pageSize;//每页显示条数
	protected Integer pageNum;//当前页面
	public static final String RELOAD = "reload";
	
	
	protected PageBean pageBean;
	
	/**
	 * 不指定方法时，直接调用list方法
	 */
	@Override
	public String execute() throws Exception {
		return list();
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	/**
	 * 显示列表
	 */
	public String list() throws Exception{
		initPage();
		pageBean = baseService.findPageBeanOfHql(clazz,"FROM "+clazz.getName(), pageNum, pageSize, null);
		return SUCCESS;
	}
	

	/**
     * 显示添加页面
     */
    public String addUI() throws Exception{
    	return SUCCESS;
    }
    
    /**
     * 显示更新页面
     */
    public String updateUI() throws Exception{
    	return SUCCESS;
    }

	/**
	 * 执行删除
	 */
	public String delete() throws Exception{
		if(ids==null){
			return RELOAD;
		}
		for(Integer id:ids){
		    baseService.deleteById(id,this.clazz);	
		}
		return RELOAD;
	}
	
	/**
	 * 执行添加
	 */
	public String add() throws Exception{
		baseService.add(entity);
		return RELOAD;
	}
	/**
	 * 执行更新
	 */
	public String update() throws Exception{
		baseService.update(entity);
		return RELOAD;
	}

	public void prepare() throws Exception {
		
	}

	/**
	 * 整理查询
	 */
	public void initPage(){
		if(pageSize==null||pageSize<=0){
			pageSize=15;//默认页面显示15条
		}
		if(pageNum==null||pageNum<0){
			pageNum=0;//默认显示第一页
		}
	}
	/**
	 * 显示跟新页面时，准备数据
	 */
	public void prepareUpdateUI() throws Exception {
		prepareModel();
	}
	public void prepareList() throws Exception {
		prepareModel();
	}
	
	/**
	 * 执行更新操作前，准备数据
	 */
	public void prepareUpdate() throws Exception {
		prepareModel();
	}
	
	/**
	 * 执行添加操作前，准备数据
	 */
	public void prepareAdd() throws Exception {
		prepareModel();
	}

	
	protected void prepareModel() throws Exception {
		if(id == null)
			entity = (T) clazz.newInstance();
		else
			entity = (T) baseService.findById(id,clazz);
	}
	
	/**
	 * 查看
	 * @return
	 */
	public String view(){
		entity = (T) baseService.findById(id, clazz);
		return SUCCESS;
	}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public Integer[] getIds() {
		return ids;
	}

	public void setIds(Integer[] ids) {
		this.ids = ids;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getErrorType() {
		return errorType;
	}

	public void setErrorType(Integer errorType) {
		this.errorType = errorType;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	
	public Operator getOperator() {
		return (Operator) ServletActionContext.getRequest().getSession().getAttribute("OPERATOR");
	}

	public void setOperator(Operator operator) {
		this.operator = operator;
	}

	public Integer getPageNum() {
		return pageNum;
	}

	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	
	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public T getModel(){
		return entity;
	}
	
	/**
	 * 获取系统配置信息
	 * @param propName
	 * @return
	 */
	public String getConfig(String propName){
		InputStream is=ActionContext.getContext().getClass().getClassLoader().getResourceAsStream("setting.properties");
		if(propName.equals("fileUpload.dir")){
			if(System.getProperty( "os.name").toLowerCase().contains("windows")){
				propName="fileUpload.dir.windows";
			}else{
				propName="fileUpload.dir.linux";
			}
		}
    	Properties prop=new Properties();
    	try {
			prop.load(is);
            return (String) prop.get(propName);			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String getOp() {
		return op;
	}

	public void setOp(String op) {
		this.op = op;
	}	

	public Integer getCurUserId(){
		return ((Operator) ServletActionContext.getRequest().getSession().getAttribute("OPERATOR")).getId();
	}


	public String getDesk() {
		return desk;
	}

	public void setDesk(String desk) {
		this.desk = desk;
	}
	
	protected Object getParameter(String paramName){
		Object obj = ServletActionContext.getRequest().getParameter(paramName);
		if(obj == null)
			obj = ServletActionContext.getRequest().getAttribute(paramName);
		if(obj == null)
			obj = ServletActionContext.getRequest().getSession().getAttribute(paramName);
		if(obj == null)
			obj = ServletActionContext.getServletContext().getAttribute(paramName);
		return obj;
	}

	protected String[] getParameters(String paramName){
		return ServletActionContext.getRequest().getParameterValues(paramName);
	}

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

	public String getAnyAction() {
		return anyAction;
	}

	public void setAnyAction(String anyAction) {
		this.anyAction = anyAction;
	}
	
	protected void println(String msg) throws IOException{
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(msg);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}
	
	protected void setAttribute(String key,Object value){
		ServletActionContext.getRequest().setAttribute(key, value);
	}
}
