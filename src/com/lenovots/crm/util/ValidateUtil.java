package com.lenovots.crm.util;

import java.util.regex.Pattern;

public class ValidateUtil {

	private static Pattern areaCodePattern = Pattern.compile("[0-9]{3,4}");
	
	//电话
	private static Pattern phonePattern = Pattern.compile("[^1][0-9]{5,11}");
	
	//手机
	private static Pattern mobilePattern = Pattern.compile("^([0]{0,}1[358][0-9]{9})$");
	
	private static Pattern postNumPattern = Pattern.compile("^[1-9]{1}[0-9]{5}$");
	private static Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$");
	
	/**
	 * �����֤
	 * @param areaCode
	 * @return
	 */
	public static boolean areaCodeValidate(String areaCode){
		if(areaCode==null){
			return false;
		}
		return areaCodePattern.matcher(areaCode).matches();
	}
	
	/**
	 * �绰������֤(�������)
	 * @param phoneNum
	 * @return
	 */
	public static boolean phoneValidate(String phoneNum){
		if(phoneNum==null){
			return false;
		}
		return phonePattern.matcher(phoneNum).matches();
	}
	
	/**
	 * �ֻ������֤
	 * @param mobile
	 * @return
	 */
	public static boolean mobileValidate(String mobile){
		if(mobile==null){
			return false;
		}
		return mobilePattern.matcher(mobile).matches();
	}
	
	/**
	 * ����������֤
	 * @param postNum
	 * @return
	 */
	public static boolean postNumValidate(String postNum){
		if(postNum==null){
			return false;
		}
		
		return postNumPattern.matcher(postNum).matches();
	}
	
	/**
	 * �����ַ��֤
	 * @param email
	 * @return
	 */
	public static boolean emailValidate(String email){
		if(email==null){
			return false;
		}
		return emailPattern.matcher(email).matches();
	}
}
