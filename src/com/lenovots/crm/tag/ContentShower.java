package com.lenovots.crm.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.lenovots.crm.util.ValidateUtil;


/**
 * 内容显示器
 * @author 胡桥
 * Aug 2, 2012  10:19:02 AM
 */
public class ContentShower extends TagSupport {
	
	public Integer length;
	public String content;
	private String breaf;
	public Integer getLength() {
		return length;
	}
	public void setLength(Integer length) {
		this.length = length;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	@Override
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();//得到out
		try {
			if(content.length()<=length){
				breaf = content;
			}else{
				breaf = content.substring(0, length)+"...";
			}
			out.println("<span title=\""+this.content+"\">"+breaf+"</span>");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_PAGE;
	}

}
