package com.lenovots.crm.tag;


import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.lenovots.crm.util.ValidateUtil;



public class TelJudgmentTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;

	public String telValue;
	public String onclick;
	
	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}
	
	public String getTelValue() {
		return telValue;
	}

	public void setTelValue(String telValue) {
		this.telValue = telValue;
	}

	@Override
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();//得到out
		try {
			boolean isTel = false;
			for(String str : telValue.split("[/ ]+")){
				str = str.replaceAll("\\D", "");
				if(ValidateUtil.mobileValidate(str)||ValidateUtil.phoneValidate(str)){
					isTel = true;
					break;
				}
				isTel = true;
			}
			if(isTel){
				out.println("<span class=\"CallOutSpan\" title=\"点击呼叫:"+telValue+"\" onclick=\"CallDialog('"+getTelStr()+"');\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>");
			}else{
				out.println("");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	
	private String getTelStr(){
		telValue = telValue.trim();
		if(telValue.startsWith("086-")){
			telValue=telValue.substring(3);
		}
		//telValue = telValue.replaceAll("\\D", "");
		String[] numbers = telValue.split("[/  ]+");
		StringBuffer res = new StringBuffer();
		String tmp = "";
		String tmp2 = "";
		int count=0;
		for(String str:numbers){
			str = str.replaceAll("\\D", "");
			tmp2 = tmp+str;
			if(tmp2.startsWith("0")){//01先看是否满足手机格式
				if(tmp2.length()>=10 && tmp2.length()<=12){
					res.append(tmp2).append(";");
					tmp = "";
				}else{
					tmp = tmp+str;
				}
			}else if(tmp2.startsWith("0")){//带区号的电话号码
				if(tmp2.length()>=10 && tmp2.length()<=12){
					res.append(tmp2).append(";");
					tmp = "";
				}else{
					tmp = tmp+str;
				}
			}else{//普通号码(不带零的手机或座机号)
				if((ValidateUtil.mobileValidate(tmp2) || ValidateUtil.phoneValidate(tmp2))){
					res.append(tmp2).append(";");
					tmp = "";
				}else {
					tmp = tmp+str;
				}
			}
			count++;
		}
		if(res.length()>0){
			res.delete(res.length()-1, res.length());
		}
		return res.toString();
	}
	
	public static void main(String[] args){
		TelJudgmentTag tag = new TelJudgmentTag();
		tag.setTelValue("021-58016031/0159 2222 3333/139 1738 5319/010 2223 5433/0755 0987 3432/010-5162 8088-112");
		System.out.println(">>>"+tag.getTelStr());
	}
	
}
