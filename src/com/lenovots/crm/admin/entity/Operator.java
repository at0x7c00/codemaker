package com.lenovots.crm.admin.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.lenovots.crm.util.Constants;


/**
 * 操作员
 * 
 * @author 胡桥
 * 
 */
@Entity(name="操作员")
@Table(name="t_operator")
public class Operator {

	private Integer id;
	private String loginName;// 登录账号
	private String name;
	private String password;
	private Integer sex; // 1：男 0:女

	private Dept dept;// 所属部门

	private Integer isCenter = 0;// 是否是中心坐席
	private Integer isMonitor = 0;// 是否是班长

	private Integer isOnline;// 是否在线

	private String mobileNumber1;
	private String mobileNumber2;
	private String phoneNumber1;
	private String phoneNumber2;

	private String photo;// 签名图片地址
	private String email;// 邮箱账号
	private String mailPassword;// 邮箱密码
	private String pop3Address;// pop3服务器
	private String smtpAddress;// smtp服务器
	
	private String visibility=Constants.VISIBILITY_SELF;//工单可见范围
	
	private String workNum;//工号
	
	private String workPwd;//电话系统登录密码

    private Integer isTaskTip=1;//是否开启任务提示  1:开启  0:关闭
    
    private Integer checkRate=120;//秒 默认2分钟查找一次任务
    
    private Integer isSound=1;//是否播放提示声音
    
    private String fileName="de.wav";//声音文件名称
    
    private String signPwd;//签章密码
	
	private Job job;

	private Role role;
	
	private Integer isSuper=0;//是否是超级管理员
	
	private String styleName="base";

	public Operator(){
	}
	
	public Operator(Integer id,String name){
		this.id = id;
		this.name = name;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Dept getDept() {
		return dept;
	}

	public void setDept(Dept dept) {
		this.dept = dept;
	}

	public Integer getIsCenter() {
		return isCenter;
	}

	public void setIsCenter(Integer isCenter) {
		this.isCenter = isCenter;
	}

	public Integer getIsMonitor() {
		return isMonitor;
	}

	public void setIsMonitor(Integer isMonitor) {
		this.isMonitor = isMonitor;
	}

	public Integer getIsOnline() {
		return isOnline;
	}

	public void setIsOnline(Integer isOnline) {
		this.isOnline = isOnline;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public String getMobileNumber1() {
		return mobileNumber1;
	}

	public void setMobileNumber1(String mobileNumber1) {
		this.mobileNumber1 = mobileNumber1;
	}

	public String getMobileNumber2() {
		return mobileNumber2;
	}

	public void setMobileNumber2(String mobileNumber2) {
		this.mobileNumber2 = mobileNumber2;
	}

	public String getPhoneNumber1() {
		return phoneNumber1;
	}

	public void setPhoneNumber1(String phoneNumber1) {
		this.phoneNumber1 = phoneNumber1;
	}

	public String getPhoneNumber2() {
		return phoneNumber2;
	}

	public void setPhoneNumber2(String phoneNumber2) {
		this.phoneNumber2 = phoneNumber2;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getMailPassword() {
		return mailPassword;
	}

	public void setMailPassword(String mailPassword) {
		this.mailPassword = mailPassword;
	}

	public String getPop3Address() {
		return pop3Address;
	}

	public void setPop3Address(String pop3Address) {
		this.pop3Address = pop3Address;
	}

	public String getSmtpAddress() {
		return smtpAddress;
	}

	public void setSmtpAddress(String smtpAddress) {
		this.smtpAddress = smtpAddress;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWorkNum() {
		return workNum;
	}

	public void setWorkNum(String workNum) {
		this.workNum = workNum;
	}

	public String getWorkPwd() {
		return workPwd;
	}

	public void setWorkPwd(String workPwd) {
		this.workPwd = workPwd;
	}

	public Integer getIsTaskTip() {
		return isTaskTip;
	}

	public void setIsTaskTip(Integer isTaskTip) {
		this.isTaskTip = isTaskTip;
	}

	public Integer getCheckRate() {
		return checkRate;
	}

	public void setCheckRate(Integer checkRate) {
		this.checkRate = checkRate;
	}

	public Integer getIsSound() {
		return isSound;
	}

	public void setIsSound(Integer isSound) {
		this.isSound = isSound;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}

	public String getSignPwd() {
		return signPwd;
	}

	public void setSignPwd(String signPwd) {
		this.signPwd = signPwd;
	}

	public Integer getIsSuper() {
		return isSuper;
	}

	public void setIsSuper(Integer isSuper) {
		this.isSuper = isSuper;
	}

	public String getStyleName() {
		return styleName;
	}

	public void setStyleName(String styleName) {
		this.styleName = styleName;
	}

}
