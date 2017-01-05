package com.lenovots.crm.util;

import java.io.File;

public class Constants {

	/**
	 * 是
	 */
	public static final Integer YES=1;
	
	/**
	 * 不是
	 */
	public static  final Integer NO=0;
	
	/**
	 * 业务受理菜单名称
	 */
	public static  final String RECORD_MENU="业务受理";
	
	/**
	 * 业务处理菜单名称
	 */
	public static  final String PROCESS_MENU="业务处理";
	
	/**
	 * 业务查询菜单名称
	 */
	public  static  final String QUERY_MENU="业务查询";
	/**
	 * 业务统计菜单名称
	 */
	public  static  final String STATISTIC_MENU="业务统计";
	
	
	public static final String 	RECORD="登记";
	
	public static final String 	DISPATCHER="分配";
	
	public static final String 	PROCESS="处理";
	
	public static final String 	QUERY="查询";
	
	public static final String 	STATISTIC="统计";
	
	/**
	 * 工单处理结果 同意 (或通过)
	 */
	public static final Integer PROCESS_RESULT_YES=1;
	
	/**
	 * 工单处理结果 不同意 (或未通过)
	 */
	public static final Integer PROCESS_RESULT_NO=0;
	
	/**
	 * 性别 男
	 */
	public static final Integer SEX_MAN=1;
	
	/**
	 * 性别 女
	 */
	public static final Integer SEX_WOMAN=0;
	
	/**
	 * 任务处理
	 */
	public static final String TASK_PROCESS="任务处理";
	
	/**
	 * 任务回访
	 */
	public static final String TASK_VISITE="任务回访";
	
	/**
	 * 邮件附件存储路径
	 */
	public static final String MAIL_ATTACH_PATH = "/userfiles/email";
	
	/**
	 * 邮件单个附件大小
	 */
	public static final Long MAX_FILE_SIZE = 5 * 1024 * 1024L;
	
	
	/**
	 * 操作员对工单的可见度:仅对自己录入的工单可见
	 */
	public static final String VISIBILITY_SELF="self";
	
	/**
	 * 操作员对工单的可见度:对自己所在局录入的工单可见
	 */
	public static final String VISIBILITY_PRIVATE="private";
	
	/**
	 * 操作员对工单的可见度:所有工单可见
	 */
	public static final String VISIBILITY_PUBLIC="public";
	
	/**
	 * 允许同时登录用户的最大数量
	 */
	public static final int MAX_LOGIN_USERS = 20;
	
	
	/**
	 * 数据字典编号
	 */
	public static final String DIC_EXPRESSDELIVERY="expressDelivery";//快递公司
	public static final String DIC_PAYTYPE="payType";//快递公司
	public static final String DIC_SEX="sex";//性别
	public static final String DIC_CLIENTCATEGORY="clientCategory";//客户类别
	public static final String DIC_JOB="job";//职位
	public static final String DIC_ACCEPTTYPE="acceptType";//受理类型
	public static final String DIC_TIMELIMIT="timeLimit";//受理时限
	public static final String DIC_EMERGENCY="emergency";//紧急程度
	public static final String DIC_EVENTTYPE="eventType";//事件类型
	public static final String DIC_INFOTYPE="infoType";//信息类型
	public static final String DIC_LOSTREASON="lostReason";//丢失原因
	public static final String DIC_CUSTSOURCE="custSource";//客户来源
	public static final String DIC_STATUS="callStatus";//客户来源
	
	public static final String CODE_SAVE_ROOT = File.separator+"projectFiles"+File.separator;
	
	public static String DOLLAR = "[_D]";
	public static String DOLLAR_R = "\\[_D\\]";
	public static String GT = "[_GT]";
	public static String GT_R = "\\[_GT\\]";
	public static String LT = "[_LT]";
	public static String LT_R = "\\[_LT\\]";
	

}
