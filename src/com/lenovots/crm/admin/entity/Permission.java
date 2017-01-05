package com.lenovots.crm.admin.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;


/**
 * Ȩ����
 */
public class Permission implements Comparable<Permission>,Serializable{
    
	private static final long serialVersionUID = 1L;
	
	
	private Integer sortValue;//排序
	
	private String icon="default.gif";
	
	public Permission(String name,Integer id){
		this.name=name;
		this.id=id;
	}
	
	public static void main(String[] args) {
		Permission p=new Permission();
		
		p.addChildPermission(new Permission("A",1));
		p.addChildPermission(new Permission("B",2));
		p.addChildPermission(new Permission("C",3));
		
		for(Permission child:p.getChildren()){
			System.out.println(child.getName()+" "+child.getId()+"  " +child.hashCode());
		}
	}
	
	private Integer display=1;//是否页面显示
	
	private Integer id;
	/**����Ȩ��*/
	private Permission parent;
	/**�¼�Ȩ��*/
	private Set<Permission> children = new TreeSet<Permission>(new Comparator<Permission>(){
		@Override
		public int compare(Permission o1, Permission o2) {
			return o2.getSortValue()-o1.getSortValue();
		}
	});
	/**Ȩ�����*/
	private String name;
	/**���ʵ�ַ*/
	private String url;

	/**Ȩ�޼���*/
	private Integer level;
	
	public Permission(){
		
	}
    public Permission(Permission parent,String name,String url,Integer level){
    	this.parent=parent;
    	this.name=name;
    	this.url=url;
    	this.level=level;
    }
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Permission getParent() {
		return parent;
	}

	public void setParent(Permission parent) {
		this.parent = parent;
	}

	public Set<Permission> getChildren() {
		return children;
	}

	public void setChildren(Set<Permission> children) {
		this.children = children;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public Permission addChildPermission(Permission child){
		this.children.add(child);
		return this;
	}
	public Permission addChildrenPermission(Permission...child){
		for(int i=0;i<child.length;i++){
		   this.children.add(child[i]);
		}
		return this;
	}
	

    @Override
	public int hashCode() {
		int result=17;
		result = 37 * result + ((id == null) ? 0 : id);
		return result;
	}
    
	@Override
	public boolean equals(Object obj) {
		if(this==obj){
			return true;
		}
		if(!(obj instanceof Permission)){
			return false;
		}
		Permission p=(Permission)obj;
		return this.id.equals(p.getId());
	}
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public int compareTo(Permission o) {
		return o.id-this.id;
	}
	public Integer getDisplay() {
		return display;
	}
	public void setDisplay(Integer display) {
		this.display = display;
	}
	public Integer getSortValue() {
		return sortValue;
	}
	public void setSortValue(Integer sortValue) {
		this.sortValue = sortValue;
	}
	
}
