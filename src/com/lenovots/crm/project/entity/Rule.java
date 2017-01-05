package com.lenovots.crm.project.entity;
/**
 * 映射规则
 * @author 胡桥
 * 2013-08-19 11:02:34
 **/
public class Rule{
	private Integer id;
	private String outputFileName;
	private Template template;//输入模板
	private String outputDir;//输出位置
	private String cause;//限制条件
	private boolean append;
	public void setId(Integer id){
		this.id=id;
	}
	public Integer getId(){
		return this.id;
	}
	public void setTemplate(Template template){
		this.template=template;
	}
	public Template getTemplate(){
		 return this.template;
	}
	public void setOutputDir(String outputDir){
		this.outputDir=outputDir;
	}
	public String getOutputDir(){
		 return this.outputDir;
	}
	public String getOutputFileName() {
		return outputFileName;
	}
	public void setOutputFileName(String outputFileName) {
		this.outputFileName = outputFileName;
	}
	public String getCause() {
		return cause;
	}
	public void setCause(String cause) {
		this.cause = cause;
	}
	public boolean isAppend() {
		return append;
	}
	public void setAppend(boolean append) {
		this.append = append;
	}
}
