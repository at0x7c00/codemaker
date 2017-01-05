package PACKAGE_VALUE;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lenovots.jdhr.common.entity.enumtype.YesNo;
import com.lenovots.jdhr.employee.entity.Employee;
import com.lenovots.jdhr.employee.entity.enumtype.Degree;
import com.lenovots.jdhr.employee.entity.enumtype.DimissReason;
import com.lenovots.jdhr.employee.entity.enumtype.DimissType;
import com.lenovots.jdhr.employee.entity.enumtype.Sex;
import com.lenovots.jdhr.hr.entity.HrResource;
import com.lenovots.jdhr.system.entity.FunctionPoint;
import com.lenovots.jdhr.system.entity.propertyeditor.FunctionPointEditor;
import com.lenovots.jdhr.util.web.JsonResult;

/**
 * ENTITY_REALY_NAMEController auto created by pc
 * @author pc
 * CREATE_DATE
 */
@Controller
@RequestMapping("/LOWER_CLASSNAME")
public class BEAN_CLASSNAMEController extends BaseController {

    @Resource
    private IBEAN_CLASSNAMEService LOWER_CLASSNAMEService;
    
    @InitBinder
	public void initPropEditor(WebDataBinder binder){
    	//注册属性编辑器
		INIT_PROP_EDITOR
	}
    
    //复杂关联关系的Service
	COMPLEX_SERVICES_DEFINE
    
    @ModelAttribute
    public BEAN_CLASSNAME initModelAttribute(@RequestParam(value="ID_PROPERTY_NAME",required=false) ID_PROPERTY_DATATYPENAME ID_PROPERTY_NAME){
    	BEAN_CLASSNAME LOWER_CLASSNAME = null;
    	if(ID_PROPERTY_NAME==null || ID_PROPERTY_NAME.equals("")){
    		LOWER_CLASSNAME = new BEAN_CLASSNAME();
    	}else{
    		LOWER_CLASSNAME = LOWER_CLASSNAMEService.getById(BEAN_CLASSNAME.class, ID_PROPERTY_NAME);
    	}
    	return LOWER_CLASSNAME;
    }
    
    /**
     * ENTITY_REALY_NAME列表
     * @param request
     * @return
     * @author pc
     * CREATE_DATE
     */
    @RequestMapping(value="/list")
    public void list(HttpServletRequest request,BEAN_CLASSNAME LOWER_CLASSNAME,Page pageInfo) {
        Page<BEAN_CLASSNAME> LOWER_CLASSNAMEPage = LOWER_CLASSNAMEService.getListPage(LOWER_CLASSNAME,pageInfo);
        request.setAttribute("pageBean", LOWER_CLASSNAMEPage);
        //复杂关联关系数据准备
    	COMPLEX_LIST_PREPARE
    }
    
    /**
     * 添加ENTITY_REALY_NAME页面
     * @param request
     * @return
     * CREATE_DATE
     */
    @RequestMapping(value="/add",method=RequestMethod.GET)
    public void addUI(HttpServletRequest request) {
    	//复杂关联关系数据准备
    	COMPLEX_LIST_PREPARE
    }
    
    /**
     * 添加ENTITY_REALY_NAME
     * @param LOWER_CLASSNAME
     * @return
     * CREATE_DATE
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public JsonResult add(@RequestParam("rel") String rel,@Valid @ModelAttribute("LOWER_CLASSNAME") BEAN_CLASSNAME LOWER_CLASSNAME,BindingResult result) {
    	JsonResult jsonResult = new JsonResult();
    	//if(!validate(jsonResult,result)){
    	//	return jsonResult;
    	//}
    	//默认系统时间类型保存
    	DATATYPE_DEFAULT_VALUE_SAVE
    	SET_COMPLEX_NULL_WHEN_NULL_ID
    	LOWER_CLASSNAMEService.add(LOWER_CLASSNAME);
    	jsonResult.setNavTabId(rel);
    	jsonResult.setMessage("添加成功");
        return jsonResult;
    }
    
    /**
     * 修改ENTITY_REALY_NAME页面
     * @param uid
     * @param request
     * @return
     * CREATE_DATE
     */
    @RequestMapping(value="/update",method=RequestMethod.GET)
    public void updateUI(@RequestParam(value="ID_PROPERTY_NAME") ID_PROPERTY_DATATYPENAME ID_PROPERTY_NAME,HttpServletRequest request) {
    	//复杂关联关系数据准备
    	COMPLEX_LIST_PREPARE
    }
    
    /**
     * 修改ENTITY_REALY_NAME
     * @param LOWER_CLASSNAME
     * @return
     * CREATE_DATE
     */
    @RequestMapping(value="/update",method=RequestMethod.POST)
    @ResponseBody
    public JsonResult update(@RequestParam("rel") String rel,@ModelAttribute BEAN_CLASSNAME LOWER_CLASSNAME,BindingResult result) {
    	JsonResult jsonResult = new JsonResult();
    	if(!validate(jsonResult,result)){
    		return jsonResult;
    	}
	    SET_COMPLEX_NULL_WHEN_NULL_ID
        LOWER_CLASSNAMEService.update(LOWER_CLASSNAME);
	    jsonResult.setNavTabId(rel);
        jsonResult.setMessage("修改成功");
        return jsonResult;
    }
    
    /**
     * 删除单个
     * @param uid
     * @return
     * CREATE_DATE
     */
    @RequestMapping(value="/delete",method=RequestMethod.GET)
    @ResponseBody
    public JsonResult delete(@RequestParam("rel") String rel,@ModelAttribute BEAN_CLASSNAME LOWER_CLASSNAME) {
        JsonResult jsonResult = new JsonResult();
        jsonResult.setCallbackType("");
        try {
        	LOWER_CLASSNAMEService.delete(LOWER_CLASSNAME);
		} catch (RuntimeException re) {
			jsonResult.setMessage("该记录已被使用，不能删除！");
			return jsonResult;
		}catch (Exception e) {
			jsonResult.setMessage(e.toString());
			return jsonResult;
		}
        jsonResult.setNavTabId(rel);
        jsonResult.setMessage("删除成功");
        return jsonResult;
    }
    
	@RequestMapping(value = "/detail", method = RequestMethod.GET)
	public void detailUI(@ModelAttribute BEAN_CLASSNAME LOWER_CLASSNAME,HttpServletRequest request) {
		COMPLEX_LIST_PREPARE
	}
	
    /**
     * 删除多个
     * @param uids
     * @return
     * CREATE_DATE
   
    @RequestMapping(value="/delete",method=RequestMethod.POST)
    @ResponseBody
    public JsonResult batchDelete(@RequestParam("ID_PROPERTY_NAMEs") ID_PROPERTY_DATATYPENAME[] ID_PROPERTY_NAMEs) {
    	BEAN_CLASSNAME LOWER_CLASSNAME = null;
    	for(ID_PROPERTY_DATATYPENAME id : ID_PROPERTY_NAMEs){
    		LOWER_CLASSNAME = LOWER_CLASSNAMEService.loadById(BEAN_CLASSNAME.class, id);
    		LOWER_CLASSNAMEService.delete(LOWER_CLASSNAME);
    	}
    	JsonResult jsonResult = new JsonResult();
    	jsonResult.setMessage("删除成功");
    	return jsonResult;
    } 
    */
    
}
