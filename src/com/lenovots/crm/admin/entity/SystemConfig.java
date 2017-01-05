package com.lenovots.crm.admin.entity;

/**
 *
 * 系统设置类
 * @author 胡桥
 * Aug 30, 2011  2:27:13 PM
 */
public class SystemConfig {

	private Integer id;
	
	private Integer phoneDisplayType=0;//电话控件显示方式    0:JS包装  1:直接页面显示
	
	private String ocxObjSource;//ocx控件源码
	
	private String objId;//电话控件ID
	
	private String phoneServerIp;//电话系统服务器Ip地址
	
	private String versionDesc;//版本备注
	
	private String recorderUrl;//录音文件存放位置
	
	private String systemTitle;//站点名称
	
	private String loginImage;//登陆页面图片
	
	private String mainBgImage;//主页面背景图片
	
	private String subTitle;//副标题
	
	private Integer useCheckCode=0;//启用验证码登录 默认为不启动
	
	private Integer notifyTime;//通话提醒时间,单位：分钟
	
	private Integer selfPlayer=1;//是否使用自己的播放页面
	
	private String projectRootPath;//项目根路径

	public Integer getNotifyTime() {
		return notifyTime;
	}

	public void setNotifyTime(Integer notifyTime) {
		this.notifyTime = notifyTime;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPhoneDisplayType() {
		return phoneDisplayType;
	}

	public void setPhoneDisplayType(Integer phoneDisplayType) {
		this.phoneDisplayType = phoneDisplayType;
	}

	public String getOcxObjSource() {
		return ocxObjSource;
	}

	public void setOcxObjSource(String ocxObjSource) {
		this.ocxObjSource = ocxObjSource;
	}

	public String getPhoneServerIp() {
		return phoneServerIp;
	}

	public void setPhoneServerIp(String phoneServerIp) {
		this.phoneServerIp = phoneServerIp;
	}

	public String getVersionDesc() {
		return versionDesc;
	}

	public void setVersionDesc(String versionDesc) {
		this.versionDesc = versionDesc;
	}

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getRecorderUrl() {
		return recorderUrl;
	}

	public void setRecorderUrl(String recorderUrl) {
		this.recorderUrl = recorderUrl;
	}

	public String getSystemTitle() {
		return systemTitle;
	}

	public void setSystemTitle(String systemTitle) {
		this.systemTitle = systemTitle;
	}

	public String getLoginImage() {
		return loginImage;
	}

	public void setLoginImage(String loginImage) {
		this.loginImage = loginImage;
	}

	public String getMainBgImage() {
		return mainBgImage;
	}

	public void setMainBgImage(String mainBgImage) {
		this.mainBgImage = mainBgImage;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public Integer getUseCheckCode() {
		return useCheckCode;
	}

	public void setUseCheckCode(Integer useCheckCode) {
		this.useCheckCode = useCheckCode;
	}

	public Integer getSelfPlayer() {
		return selfPlayer;
	}

	public void setSelfPlayer(Integer selfPlayer) {
		this.selfPlayer = selfPlayer;
	}

	public String getProjectRootPath() {
		return projectRootPath;
	}

	public void setProjectRootPath(String projectRootPath) {
		this.projectRootPath = projectRootPath;
	}
	
	
}
