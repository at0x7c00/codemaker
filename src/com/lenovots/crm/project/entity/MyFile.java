package com.lenovots.crm.project.entity;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MyFile {
	private String name;
	private boolean isDirectory;
	private long size;
	private long lastModify;
	private File file;
	private String projectName;
	
	public MyFile(){}
	public MyFile(File file,String projectName){
		this.name = file.getName();
		this.isDirectory = file.isDirectory();
		this.size = file.length();
		this.lastModify = file.lastModified();
		this.file = file;
		this.projectName = projectName;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean getIsDirectory() {
		return isDirectory;
	}
	public void setDirectory(boolean isDirectory) {
		this.isDirectory = isDirectory;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	
	public long getLastModify() {
		return lastModify;
	}
	public Date getLastModifyDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(lastModify);
		return cal.getTime();
	}
	public void setLastModify(long lastModify) {
		this.lastModify = lastModify;
	}
	public String getFullPath(){
		StringBuffer fullPath = new StringBuffer();
		List<MyFile> parents = new ArrayList<MyFile>();
		File parent = new File(file.getAbsolutePath());
		String fileName;
		while(!parent.getName().equals(projectName)){
			parents.add(0,new MyFile(parent,projectName));
			fileName = parent.getAbsolutePath();
			fileName = fileName.substring(0,fileName.lastIndexOf(File.separator));
			parent = new File(fileName);
		}
		int i = 0;
		for(MyFile p : parents){
			fullPath.append(p.getName());
			if(i<parents.size()-1){
				fullPath.append(File.separator);
			}
			i++;
		}
		return fullPath.toString();
	}
}
