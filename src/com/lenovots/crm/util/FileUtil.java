package com.lenovots.crm.util;

import java.io.File;

/**
 * 文件操作助手类
 * @author 胡桥
 * Apr 19, 2011  9:36:20 AM
 */
public class FileUtil {

	private static String officeFileSuffixs=".doc.docx.xls.xlsx.ppt.pptx.wps.et.dps.";
	private static String acceptTypeSuffixs=".swf.zip.rar.rmvb.png.mp3.wma.wmv.wav.avi.jpg.gif.bmp.doc.docx.ppt.pptx.xls.xlsx.vsd.pot.pps.rtf.office.wps.et.dps.pdf.txt.epub";
	
	private FileUtil(){
		
	}
	
	public static boolean upload(File dir,File[] files,String[] fileNames,StringBuffer attsNames,long maxSize){
		return new FileUtil().privateUpload(dir, files, fileNames, attsNames,maxSize);
	}
	
	
	/**
	 * 删除文件
	 * @param fileNams 文件路径
	 */
	public static void deleteFileOnServer(String[] fileNames){
		new FileUtil().delete(fileNames); 
	}
	
	
	private void delete(String[] fileNames){
		if(fileNames.length>0){
			for(String fileName:fileNames){
				File  temp=new File(fileName);
				if(temp.exists()){
					temp.delete();
				}
			}
		}
	}
    
    /**
     * 上传任务处理附件
     * @param dir
     * @param files
     * @param fileNames
     * @param attsNames
     * @param attahcements
     * @param maxSize
     * @return
   
    private  boolean privateUploadTaskAtt(File dir,File[] files,String[] fileNames,StringBuffer attsNames,List<TaskAttachement> attahcements,long maxSize){
    	maxSize=maxSize*1024*1024;
    	//如果不存在目录，首先创建目录
    	if(!dir.exists()){
    		dir.mkdirs();
    	}
    	//如果文件超过规定大小，上传失败
    	if(files!=null){
    		for(File file:files){
    			if(file.length()>maxSize){
    				return false;
    			}
    		}
    	}
    	
    	if(files!=null){
    		int index=0;
    		FileConverter converter=new FileConverter();
    		File swfFile;
    		for(File file:files){
    			TaskAttachement attache=new TaskAttachement();
    			int pos=fileNames[index].lastIndexOf("/");
    			if(pos==-1){
    				pos=fileNames[index].lastIndexOf("\\");
    			}
    			//真实上传文件名
    			String tempFileName=fileNames[index].substring(pos+1);
    			tempFileName=tempFileName.replace(" ", "");
    			//生成后台存储用的文件名
    			String suffix=getSuffix(fileNames[index]);
    			
    			File temp=new File(dir.getAbsoluteFile()+File.separator+System.currentTimeMillis()+"."+suffix);//以当前时间为文件名称
    			try {
    				//强行暂停，防止文件重名
    				Thread.sleep(10);
    			} catch (InterruptedException e1) {
    				e1.printStackTrace();
    			}
    			file.renameTo(temp);
    			file.delete();
    			if(index==0){
    				attsNames.append(temp.getAbsolutePath());
    			}else{
    				attsNames.append(";"+temp.getAbsolutePath());
    			}
    			String ext=getSuffix(fileNames[index]);
    			attache.setSize(temp.length());
    			attache.setAddDate(new Date());
    			attache.setSuffix(acceptTypeSuffixs.contains(ext)?ext:"default");
    			attache.setAbsolutePath(temp.getAbsolutePath());
    			attache.setRelativePath(attache.getAbsolutePath().substring(attache.getAbsolutePath().indexOf("fileUpload")));
    			attache.setFileName(tempFileName);
    			//对office文件进行转换
    			if(officeFileSuffixs.contains(attache.getSuffix().toLowerCase())){
    				
    				try {
    					int result=converter.offfice2Swf(temp,dir,converter.getSwfFileName(temp.getName()));
    					if(result!=0){
    						System.err.println("office文件转换pdf文件成功，但是pdf转换成swf文件时失败!");
    						System.out.println("来源:"+temp.getAbsolutePath());
    						System.out.println("转换到:"+dir.getAbsolutePath());
    						System.out.println("预计swf文件:"+converter.getSwfFileName(temp.getName()));
    					}else{
    						System.out.println("ok!!! converter success.");
    						swfFile=new File(dir,converter.getSwfFileName(temp.getName()));
    						attache.setAbsoluteSwfPath(swfFile.getAbsolutePath());
    						attache.setRelativeSwfPath(getBrowserPath(swfFile.getAbsolutePath().substring(swfFile.getAbsolutePath().indexOf("fileUpload"))));
    					}
    				} catch (Exception e) {
    					System.err.println("office文件转换pdf文件时失败!");
    					System.out.println("来源:"+temp.getAbsolutePath());
    					System.out.println("转换到:"+dir.getAbsolutePath());
    					System.out.println("预计swf文件:"+converter.getSwfFileName(temp.getName()));
    					e.printStackTrace();
    				}
    				
    			}else if(attache.getSuffix().toLowerCase().equals("pdf")){//pdf-->swf
    				
    				try {
    					int result;
    					result = converter.pdf2Swf(temp, dir, converter.getSwfFileName(temp.getName()));
    					if(result!=0){
    						System.out.println("pdf转换成swf文件时失败!");
    						System.out.println("来源:"+temp.getAbsolutePath());
    						System.out.println("转换到:"+dir.getAbsolutePath());
    						System.out.println("预计swf文件:"+converter.getSwfFileName(temp.getName()));
    					}else{
    						System.out.println("grate!!! converter success.");
    						swfFile=new File(dir,converter.getSwfFileName(temp.getName()));
    						attache.setAbsoluteSwfPath(swfFile.getAbsolutePath());
    						attache.setRelativeSwfPath(getBrowserPath(swfFile.getAbsolutePath().substring(swfFile.getAbsolutePath().indexOf("fileUpload"))));
    					}
    				} catch (Exception e) {
    					System.out.println("swf文件转换pdf文件时失败!");
    					System.out.println("来源:"+temp.getAbsolutePath());
    					System.out.println("转换到:"+dir.getAbsolutePath());
    					System.out.println("预计swf文件:"+converter.getSwfFileName(temp.getName()));
    					e.printStackTrace();
    				}
    			}
    			attahcements.add(attache);
    			index++;
    		}
    	}
    	return true;
    }
      */
    
    /**
     * 上传工单处理附件
     * @param dir
     * @param files
     * @param fileNames
     * @param attsNames
     * @param attahcements
     * @param maxSize
     * @return
    
    private  boolean privateUploadBussAtt(File dir,File[] files,String[] fileNames,StringBuffer attsNames,List<BussinessAttachement> attahcements,long maxSize){
    	maxSize=maxSize*1024*1024;
    	//如果不存在目录，首先创建目录
    	if(!dir.exists()){
    		dir.mkdirs();
    	}
    	//如果文件超过规定大小，上传失败
    	if(files!=null){
    		for(File file:files){
    			if(file.length()>maxSize){
    				return false;
    			}
    		}
    	}
    	
    	if(files!=null){
    		int index=0;
    		FileConverter converter=new FileConverter();
    		File swfFile;
    		for(File file:files){
    			BussinessAttachement attache=new BussinessAttachement();
    			int pos=fileNames[index].lastIndexOf("/");
    			if(pos==-1){
    				pos=fileNames[index].lastIndexOf("\\");
    			}
    			//真实上传文件名
    			String tempFileName=fileNames[index].substring(pos+1);
    			tempFileName=tempFileName.replace(" ", "");
    			//生成后台存储用的文件名
    			String suffix=getSuffix(fileNames[index]);
    			
    			File temp=new File(dir.getAbsoluteFile()+File.separator+System.currentTimeMillis()+"."+suffix);//以当前时间为文件名称
    			try {
    				//强行暂停，防止文件重名
    				Thread.sleep(10);
    			} catch (InterruptedException e1) {
    				e1.printStackTrace();
    			}
    			file.renameTo(temp);
    			file.delete();
    			if(index==0){
    				attsNames.append(temp.getAbsolutePath());
    			}else{
    				attsNames.append(";"+temp.getAbsolutePath());
    			}
    			String ext=getSuffix(fileNames[index]);
    			attache.setSize(temp.length());
    			attache.setAddDate(new Date());
    			attache.setSuffix(acceptTypeSuffixs.contains(ext)?ext:"default");
    			attache.setAbsolutePath(temp.getAbsolutePath());
    			attache.setRelativePath(attache.getAbsolutePath().substring(attache.getAbsolutePath().indexOf("fileUpload")));
    			attache.setFileName(tempFileName);
    			//对office文件进行转换
    			if(officeFileSuffixs.contains(attache.getSuffix().toLowerCase())){
    				
    				try {
    					int result=converter.offfice2Swf(temp,dir,converter.getSwfFileName(temp.getName()));
    					if(result!=0){
    						System.err.println("office文件转换pdf文件成功，但是pdf转换成swf文件时失败!");
    						System.out.println("来源:"+temp.getAbsolutePath());
    						System.out.println("转换到:"+dir.getAbsolutePath());
    						System.out.println("预计swf文件:"+converter.getSwfFileName(temp.getName()));
    					}else{
    						System.out.println("ok!!! converter success.");
    						swfFile=new File(dir,converter.getSwfFileName(temp.getName()));
    						attache.setAbsoluteSwfPath(swfFile.getAbsolutePath());
    						attache.setRelativeSwfPath(getBrowserPath(swfFile.getAbsolutePath().substring(swfFile.getAbsolutePath().indexOf("fileUpload"))));
    					}
    				} catch (Exception e) {
    					System.err.println("office文件转换pdf文件时失败!");
    					System.out.println("来源:"+temp.getAbsolutePath());
    					System.out.println("转换到:"+dir.getAbsolutePath());
    					System.out.println("预计swf文件:"+converter.getSwfFileName(temp.getName()));
    					e.printStackTrace();
    				}
    				
    			}else if(attache.getSuffix().toLowerCase().equals("pdf")){//pdf-->swf
    				
    				try {
    					int result;
    					result = converter.pdf2Swf(temp, dir, converter.getSwfFileName(temp.getName()));
    					if(result!=0){
    						System.out.println("pdf转换成swf文件时失败!");
    						System.out.println("来源:"+temp.getAbsolutePath());
    						System.out.println("转换到:"+dir.getAbsolutePath());
    						System.out.println("预计swf文件:"+converter.getSwfFileName(temp.getName()));
    					}else{
    						System.out.println("grate!!! converter success.");
    						swfFile=new File(dir,converter.getSwfFileName(temp.getName()));
    						attache.setAbsoluteSwfPath(swfFile.getAbsolutePath());
    						attache.setRelativeSwfPath(getBrowserPath(swfFile.getAbsolutePath().substring(swfFile.getAbsolutePath().indexOf("fileUpload"))));
    					}
    				} catch (Exception e) {
    					System.out.println("swf文件转换pdf文件时失败!");
    					System.out.println("来源:"+temp.getAbsolutePath());
    					System.out.println("转换到:"+dir.getAbsolutePath());
    					System.out.println("预计swf文件:"+converter.getSwfFileName(temp.getName()));
    					e.printStackTrace();
    				}
    			}
    			attahcements.add(attache);
    			index++;
    		}
    	}
    	return true;
    }
     */
    /**
     * 上传普通文件
     * @param dir
     * @param files
     * @param fileNames
     * @param attsNames
     * @param maxSize
     * @return
     */
    private  boolean privateUpload(File dir,File[] files,String[] fileNames,StringBuffer attsNames,long maxSize){
    	maxSize=maxSize*1024*1024;
    	//如果不存在目录，首先创建目录
    	if(!dir.exists()){
    		dir.mkdirs();
    	}
    	//如果文件超过规定大小，上传失败
    	if(files!=null){
    		for(File file:files){
    			if(file.length()>maxSize){
    				return false;
    			}
    		}
    	}
    	
    	if(files!=null){
    		int index=0;
    		for(File file:files){
    			int pos=fileNames[index].lastIndexOf("/");
    			if(pos==-1){
    				pos=fileNames[index].lastIndexOf("\\");
    			}
    			//真实上传文件名
    			String tempFileName=fileNames[index].substring(pos+1);
    			tempFileName=tempFileName.replace(" ", "");
    			//生成后台存储用的文件名
    			String suffix=getSuffix(fileNames[index]);
    			
    			File temp=new File(dir.getAbsoluteFile()+File.separator+System.currentTimeMillis()+"."+suffix);//以当前时间为文件名称
    			try {
    				//强行暂停，防止文件重名
    				Thread.sleep(10);
    			} catch (InterruptedException e1) {
    				e1.printStackTrace();
    			}
    			file.renameTo(temp);
    			file.delete();
    			if(index==0){
    				attsNames.append(temp.getAbsolutePath());
    			}else{
    				attsNames.append(";"+temp.getAbsolutePath());
    			}
    		}
    	}
    	return true;
    }
    /**
     * 获取文件后缀名
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName){
    	if(fileName.indexOf(".")==-1){
    		return "";
    	}
    	return fileName.substring(fileName.lastIndexOf(".")+1).toLowerCase();
    }
    public static  String getBrowserPath(String orginPath){
    	String[] temps=orginPath.split("\\\\");
    	StringBuffer result=new StringBuffer();
		int x=1;
		for(String str:temps){
			if(x<temps.length){
				result.append(str+"/");
			}else{
				result.append(str);
			}
			x++;
		}
		return result.toString();
    }
    
}
