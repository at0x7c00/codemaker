package com.lenovots.crm.admin.action;

import com.lenovots.crm.admin.entity.Operator;
import com.lenovots.crm.admin.service.IOperatorService;
import com.lenovots.crm.common.action.BaseAction;
import com.lenovots.crm.project.entity.Entity;
import com.lenovots.crm.project.entity.Packagee;
import com.lenovots.crm.project.entity.Property;
import com.lenovots.crm.project.service.IEntityService;
import com.lenovots.crm.project.service.IPackageService;
import com.lenovots.crm.project.service.IPropertyService;
import com.lenovots.crm.util.CommonUtil;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("ajaxAction")
@Scope("prototype")
public class AjaxAction
  extends BaseAction<Operator>
{
  private static Logger logger = Logger.getLogger(AjaxAction.class);
  private static final long serialVersionUID = 1L;
  private String type;
  @Resource
  private IOperatorService operatorService;
  @Resource
  private IPackageService packageService;
  @Resource
  private IEntityService entityService;
  @Resource
  private IPropertyService propertyService;
  private String callRecordFileId;
  private Integer callResultId;
  private Integer isTaskTip;
  private Integer checkRate;
  private Integer propertyId;
  private Integer value;
  
  public String updateCanNull()
  {
    Property prop = (Property)this.propertyService.findById(this.propertyId);
    if (prop != null)
    {
      prop.setCanNull(this.value);
      this.propertyService.update(prop);
    }
    return null;
  }
  
  public String updateHistoryable()
  {
    Entity entity = (Entity)this.entityService.findById(this.id);
    if (entity != null)
    {
      entity.setHistoryable(this.value);
      this.entityService.update(entity);
    }
    return null;
  }
  
  public String updateListDisplay()
  {
    Property prop = (Property)this.propertyService.findById(this.propertyId);
    if (prop != null)
    {
      prop.setDisplay(this.value);
      this.propertyService.update(prop);
    }
    return null;
  }
  
  public String updateSelect2()
  {
    Property prop = (Property)this.propertyService.findById(this.propertyId);
    if (prop != null)
    {
      prop.setSelect2(this.value);
      this.propertyService.update(prop);
    }
    return null;
  }
  
  public String getPackagesByProjectId()
  {
    List<Packagee> packages = new ArrayList();
    CommonUtil.tree(this.packageService.findByHql("FROM Packagee p WHERE p.project.id=? AND p.parent is null", new Object[] { this.id }), packages, "", null);
    StringBuffer result = new StringBuffer("[");
    for (Packagee pk : packages) {
      result.append("{id:'").append(pk.getId()).append("',name:'").append(pk.getName()).append("',title:'").append(pk.getFullPackageName()).append("'},");
    }
    if (result.length() > 1) {
      result.delete(result.length() - 1, result.length());
    }
    result.append("]");
    write(result.toString());
    return null;
  }
  
  public String getEntityByPackageId()
  {
    List<Entity> entityList = this.entityService.findByHql("FROM Entity e WHERE e.packagee.id=?", new Object[] { this.id });
    StringBuffer result = new StringBuffer("[");
    for (Entity en : entityList) {
      result.append("{id:'").append(en.getId()).append("',name:'").append(en.getFullClassName()).append("[").append(en.getName()).append("]").append("'},");
    }
    if (result.length() > 1) {
      result.delete(result.length() - 1, result.length());
    }
    result.append("]");
    write(result.toString());
    return null;
  }
  
  public String getSubTypes()
  {
    String parent = ServletActionContext.getRequest().getParameter("parentId");
    int parentId = -1;
    if (parent != null) {
      parentId = Integer.parseInt(parent);
    }
    List<Packagee> packageeList = this.packageService.findByHql("from Packagee kt where kt.parent.id=?", new Object[] { Integer.valueOf(parentId) });
    HttpServletResponse response = ServletActionContext.getResponse();
    response.setContentType("text/json; charset=UTF-8");
    response.setCharacterEncoding("utf-8");
    try
    {
      StringBuffer result = new StringBuffer("[");
      for (Packagee kt : packageeList)
      {
        if (result.length() > 1) {
          result.append(",");
        }
        result.append("{\"key\":\"" + kt.getId() + "\",\"title\":\"" + kt.getName() + "<a href='package_delete.action?ids=" + kt.getId() + "'>删除</a>\",\"isLazy\":true}");
      }
      result.append("]");
      response.getWriter().write(result.toString());
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public String recordStyleName()
  {
    try
    {
      ServletActionContext.getRequest().getSession().setAttribute("styleName", this.styleName);
      Operator operator = getOperator();
      if (operator != null)
      {
        operator.setStyleName(this.styleName);
        this.operatorService.update(operator);
        ServletActionContext.getRequest().getSession().setAttribute("OPERATOR", operator);
      }
      write("{success:false}");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  private void write(String content)
  {
    try
    {
      HttpServletResponse response = ServletActionContext.getResponse();
      response.setCharacterEncoding("UTF-8");
      PrintWriter out = response.getWriter();
      out.write(content);
      out.flush();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public String getCheckCode()
  {
    HttpServletResponse response = ServletActionContext.getResponse();
    response.setCharacterEncoding("UTF-8");
    
    response.setHeader("Pragma", "No-cache");
    response.setHeader("Cache-Control", "no-cache, must-revalidate");
    response.setDateHeader("Expires", 0L);
    
    response.setContentType("image/jpeg");
    try
    {
      String code = getCode(4);
      outPutImage(response, code, 100, 30);
      ServletActionContext.getRequest().getSession().setAttribute("checkcode", code);
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  private String getCode(int count)
  {
    char[] base = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
    String result = "";
    for (int i = 0; i < count; i++)
    {
      int pos = new Random().nextInt(36);
      result = result + base[pos];
    }
    return result;
  }
  
  private void outPutImage(HttpServletResponse response, String code, int width, int height)
    throws IOException
  {
    OutputStream ops = response.getOutputStream();
    BufferedImage image = new BufferedImage(width, height, 1);
    Graphics g = image.getGraphics();
    
    g.setColor(new Color(255, 255, 255));
    g.fillRect(0, 0, width, height);
    
    int x = 2;
    char[] codes = code.toCharArray();
    Color[] colors = { Color.red, Color.black, Color.green, Color.blue, Color.gray, Color.pink };
    for (int i = 0; i < codes.length; i++)
    {
      g.setFont(new Font("", 1, 15 + new Random().nextInt(10)));
      g.setColor(colors[new Random().nextInt(6)]);
      g.drawString(codes[i]+"", x, height - 5);
      x += width / codes.length;
    }
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ImageIO.write(image, "jpg", baos);
    byte[] buf = baos.toByteArray();
    response.setContentLength(buf.length);
    ops.write(buf);
    baos.close();
    ops.close();
  }
  
  public String saveTipSet()
  {
    try
    {
      Operator operator = getOperator();
      if (operator != null)
      {
        operator.setIsTaskTip(this.isTaskTip);
        operator.setCheckRate(this.checkRate);
        this.operatorService.update(operator);
        ServletActionContext.getRequest().getSession().setAttribute("OPERATOR", operator);
      }
      write("{success:false}");
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return null;
  }
  
  public String getCallRecordFileId()
  {
    return this.callRecordFileId;
  }
  
  public void setCallRecordFileId(String callRecordFileId)
  {
    this.callRecordFileId = callRecordFileId;
  }
  
  public Integer getCallResultId()
  {
    return this.callResultId;
  }
  
  public void setCallResultId(Integer callResultId)
  {
    this.callResultId = callResultId;
  }
  
  public void setIsTaskTip(Integer isTaskTip)
  {
    this.isTaskTip = isTaskTip;
  }
  
  public void setCheckRate(Integer checkRate)
  {
    this.checkRate = checkRate;
  }
  
  public String getType()
  {
    return this.type;
  }
  
  public void setType(String type)
  {
    this.type = type;
  }
  
  public Integer getPropertyId()
  {
    return this.propertyId;
  }
  
  public void setPropertyId(Integer propertyId)
  {
    this.propertyId = propertyId;
  }
  
  public Integer getValue()
  {
    return this.value;
  }
  
  public void setValue(Integer value)
  {
    this.value = value;
  }
}
