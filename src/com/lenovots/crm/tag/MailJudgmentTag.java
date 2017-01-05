package com.lenovots.crm.tag;


import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.lenovots.crm.util.ValidateUtil;



public class MailJudgmentTag extends TagSupport {
	
	private static final long serialVersionUID = 1L;

	public String mailValue;
	
	public String getMailValue() {
		return mailValue;
	}

	public void setMailValue(String mailValue) {
		this.mailValue = mailValue;
	}

	@Override
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();//得到out
		try {
			if(mailValue!=null && ValidateUtil.emailValidate(mailValue.trim())){
				out.println("<a title=\"发送邮件至:"+mailValue+"\" href=\"mailto:"+mailValue.trim()+"\" class=\"ui-icon ui-icon-mail-closed\" style=\"display:inline;text-decoration:none;\">&nbsp;&nbsp;&nbsp;&nbsp;</a>");
			}else{
				out.println("");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}
	
}
