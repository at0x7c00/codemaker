package com.lenovots.crm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class URLUtil {
	
	/**
	 * 读取文件全部内容
	 * @param file
	 * @return
	 */
	public static String readFile(File file,String charset)throws Exception{
		FileInputStream fis = new FileInputStream(file);
		StringBuffer content = new StringBuffer();
		int i = 0; 
		byte[] buffer = new byte[1024];
		try{
			while((i=fis.read(buffer))!=-1){
				content.append(new String(buffer,0,i,charset));
			}
		}catch(Exception e){
			throw e;
		}finally{
			fis.close();
		}
		return content.toString();
	}

	/**
	 * 获取给定地址的请求结果
	 * @param address
	 * @return
	 */
	public static String getURLContent(String address){
		URLConnection conn = null;
		StringBuffer content = new StringBuffer();
		try{
			URL url = new URL(address);
			conn = url.openConnection();
			InputStream is = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int i;
			while((i = is.read(buffer))!=-1){
				content.append(new String(buffer,0,i,"UTF8"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return content.toString();
	}
	
	/**
	 * 将字符内容存放到文件
	 * @param content
	 * @param file
	 */
	public static void saveContentToFile(String content,File file,boolean append){
		OutputStream os = null;
		try{
			if(!file.exists()){
				String parentDir = file.getAbsolutePath();
				File parent = new File(parentDir.substring(0,parentDir.lastIndexOf(File.separator)));
				if(!parent.exists()){
					parent.mkdirs();
				}
				file.createNewFile();
			}
			os = new FileOutputStream(file,append);
			os.write(content.getBytes("UTF8"));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(os!=null){
					os.close();
				}
			}catch(Exception e){}
		}
	}
}
