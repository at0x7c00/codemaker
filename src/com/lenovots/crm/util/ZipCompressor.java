package com.lenovots.crm.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
public class ZipCompressor {
	Logger logger = Logger.getLogger(ZipCompressor.class);
	public static void main(String[] args) throws Exception{
		
		File zipFile = new File("D:\\apache-tomcat-7.0.34\\bin\\..\\temp\\1377571197887.zip");
		ZipCompressor zc = new ZipCompressor();
		zc.compress("D:\\apache-tomcat-7.0.34\\webapps\\projectCreator-rid\\projectFiles\\bbc",zipFile);
		
	}
	 	static final int BUFFER = 1024;  
	  
	    public void compress(String srcPathName,File zipFile)throws Exception {  
	        File file = new File(srcPathName);  
	        if (!file.exists())  
            throw new RuntimeException(srcPathName + "不存在！");  
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);  
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream,new Adler32());  
            ZipOutputStream out = new ZipOutputStream(cos);
            out.setEncoding("UTF-8");
            String basedir = "";
            compress(file, out, basedir);  
            out.close();  
	    }  
	  
	    private void compress(File file, ZipOutputStream out, String basedir) throws Exception{  
	        /* 判断是目录还是文件 */  
	        if (file.isDirectory()) {  
	            logger.info("压缩：" + basedir + file.getName());  
	            this.compressDirectory(file, out, basedir);  
	        } else {  
	            logger.info("压缩：" + basedir + file.getName());  
	            this.compressFile(file, out, basedir);  
	        }  
	    }  
	  
	    /** 压缩一个目录 */  
	    private void compressDirectory(File dir, ZipOutputStream out, String basedir) throws Exception{  
	        if (!dir.exists())  {
	            return;  
	        }
	  
	        File[] files = dir.listFiles();  
	        for (int i = 0; i < files.length; i++) {  
	            /* 递归 */  
	            compress(files[i], out, basedir + dir.getName()+File.separator);  
	        }  
	    }  
	  
	    /** 压缩一个文件 */  
	    private void compressFile(File file, ZipOutputStream out, String basedir)throws Exception {  
	        if (!file.exists()) {  
	            return;  
	        }  
	        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));  
            System.out.println(basedir + file.getName());
            ZipEntry entry = new ZipEntry(basedir + file.getName());  
            out.putNextEntry(entry);  
            int count;  
            byte data[] = new byte[BUFFER];  
            while ((count = bis.read(data, 0, BUFFER)) != -1) {  
                out.write(data, 0, count);  
            }
            bis.close();  
	    }  
}
