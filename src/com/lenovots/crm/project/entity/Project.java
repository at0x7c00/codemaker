package com.lenovots.crm.project.entity;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.Column;

/** 
 * 项目
 * @author 胡桥
 * Jul 13, 2012  4:14:55 PM
 */
public class Project {

	private Integer id;
	private String name;
	private String outputDir;
	private String templateDir;
	private String remark;
	private Integer useI18n;
	
	private Set<Rule> rules = new TreeSet<Rule>(new Comparator<Rule>(){
		@Override
		public int compare(Rule o1, Rule o2) {
			return o1.getId().compareTo(o2.getId());
		}
	});
	
	private Set<EnvironmentVariable> variables = new TreeSet<EnvironmentVariable>(new Comparator<EnvironmentVariable>(){
		@Override
		public int compare(EnvironmentVariable o1, EnvironmentVariable o2) {
			return o1.getId().compareTo(o2.getId());
		}
	});
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOutputDir() {
		return outputDir;
	}
	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}
	public String getTemplateDir() {
		return templateDir;
	}
	public void setTemplateDir(String templateDir) {
		this.templateDir = templateDir;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Set<Rule> getRules() {
		return rules;
	}
	public void setRules(Set<Rule> rules) {
		this.rules = rules;
	}
	public Set<EnvironmentVariable> getVariables() {
		return variables;
	}
	public void setVariables(Set<EnvironmentVariable> variables) {
		this.variables = variables;
	}
	public Integer getUseI18n() {
		return useI18n;
	}
	public void setUseI18n(Integer useI18n) {
		this.useI18n = useI18n;
	}
	
}
