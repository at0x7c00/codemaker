package com.lenovots.crm.project.service.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URL;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.lenovots.crm.common.service.impl.CrmBaseServiceImpl;
import com.lenovots.crm.project.entity.Entity;
import com.lenovots.crm.project.entity.Project;
import com.lenovots.crm.project.service.IEntityService;
import com.lenovots.crm.project.util.CodeCreateEnginer;


@Service("entityService")
public class EntityServiceImpl extends CrmBaseServiceImpl<Entity> implements IEntityService{
	
	
	/**
	 * 创建mvc代码
	 * @param entity
	 * @throws Exception 
	 */
	public void makeMVC(Project project,Entity entity,int sqlId) throws Exception{
		//创建目录包
		CodeCreateEnginer.makeDirs(project.getOutputDir(), entity.getFullPackageName());
		//创建Java文件
		if(Entity.ENTITY_TYPE_ENUM==entity.getType()){
			CodeCreateEnginer.makeEnumSource(project.getOutputDir()+"\\src", entity);
			return;
		}else{
			CodeCreateEnginer.makeJavaSource(project.getOutputDir()+"\\src", entity);
			CodeCreateEnginer.makePropertyEditorJavaSource(project.getOutputDir()+"\\src", entity);
			CodeCreateEnginer.createPermissionSQL(project.getOutputDir(),entity,sqlId);
		}
		
		//创建hibernate映射文件
		//CodeCreateEnginer.makeORMapping(project.getOutputDir()+"\\src", entity);
		//创建jsp页面
		CodeCreateEnginer.makeCrudJsp(project.getTemplateDir(), project.getOutputDir()+"\\WebRoot\\WEB-INF\\jsp", entity);
		
		//Controller层
		CodeCreateEnginer.makeControllerSrc(project.getTemplateDir(), project.getOutputDir(), entity);
		//Service接口层
		CodeCreateEnginer.makeServiceSrc(project.getTemplateDir(), project.getOutputDir(), entity);
		//Service实现层
		CodeCreateEnginer.makeServiceImplSrc(project.getTemplateDir(), project.getOutputDir(), entity);
		
		//Dao接口层
		CodeCreateEnginer.makeDaoSrc(project.getTemplateDir(), project.getOutputDir(), entity);
		//Dao实现层
		CodeCreateEnginer.makeDaoImplSrc(project.getTemplateDir(), project.getOutputDir(), entity);
	}
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
