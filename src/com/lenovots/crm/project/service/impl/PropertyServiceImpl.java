package com.lenovots.crm.project.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.lenovots.crm.common.service.impl.CrmBaseServiceImpl;
import com.lenovots.crm.project.entity.Property;
import com.lenovots.crm.project.service.IPropertyService;


@Service("propertyService")
public class PropertyServiceImpl extends CrmBaseServiceImpl<Property> implements IPropertyService{
	
	public void downLoad(String filePath, HttpServletResponse response, boolean isOnLine){
        try {
			File file = new File(filePath);
			if (!file.exists()) {
				response.sendError(404, "文件没找到!");
				return;
			}
			BufferedInputStream br = new BufferedInputStream(
					new FileInputStream(file));
			byte[] buf = new byte[1024];
			int len = 0;
			response.reset(); // 非常重要
			String fileName = new String(file.getName().getBytes("GBK"),"ISO8859_1");
			if (isOnLine) { // 在线打开方式
				URL u = new URL("file:///" + filePath);
				response.setContentType(u.openConnection().getContentType());
				response.setHeader("Content-Disposition", "inline; filename="+fileName);
				// 文件名应该编码成UTF-8
			} else { // 纯下载方式
				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition","attachment; filename=" + fileName);
			}
			OutputStream out = response.getOutputStream();
			while ((len = br.read(buf)) > 0)
				out.write(buf, 0, len);
			br.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}
