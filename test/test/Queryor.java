package test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.log4j.Logger;

public class Queryor implements Runnable{
	
	Logger loger = Logger.getLogger(Queryor.class);
	
	private long start;
	private long end;
	private String content;
	
	public Queryor(long start, long end) {
		super();
		this.start = start;
		this.end = end;
	}

	@Override
	public void run() {
		while(start<end){
			content = doQuery(start,true);
			if(content!=null && !content.trim().equals("") && !content.contains("有误")){
				recordSuccessNo("0"+start);
			}else{
				loger.info("failed with no 0"+ start);
			}
			start++;
		}
	}
	
	private void recordSuccessNo(String no){
		try {
			loger.info("success with no : "+ no);
			FileOutputStream fos = new FileOutputStream(new File("d:\\bugger\\thread_"+start+".txt"),true);
			fos.write((no+"\r\n").getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String doQuery(long id,boolean prefixzero){
		String content = getURLContent("http://gkcx.zqbe.net/query.asp","radiobutton=1&no="+(prefixzero ? "0"+id : ""+id));
		return content;
	}
	/**
	 * 获取给定地址的请求结果
	 * @param address
	 * @return
	 */
	public static String getURLContent(String address,String params){
		URLConnection conn = null;
		StringBuffer content = new StringBuffer();
		try{
			URL url = new URL(address);
			conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());  
	        writer.write(params);  
	        writer.flush();  
	        writer.close();  
	        
			InputStream is = conn.getInputStream();
			byte[] buffer = new byte[1024];
			int i;
			while((i = is.read(buffer))!=-1){
				content.append(new String(buffer,0,i,"GBK"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return content.toString();
	}
	

}
