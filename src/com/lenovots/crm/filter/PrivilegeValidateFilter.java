package com.lenovots.crm.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lenovots.crm.admin.entity.Operator;
import com.lenovots.crm.admin.entity.Permission;
import com.lenovots.crm.admin.entity.Role;
import com.lenovots.crm.util.CommonUtil;


/**
 * 权限验证过滤器
 * 
 * @author 胡桥 May 9, 2011 1:45:01 PM
 */
public class PrivilegeValidateFilter implements Filter {
	
	public static boolean hasRegist = true ;//是否已注册  
	public static Long timeStart;
	
	private static final String[] privilegesWithOutLogin = new String[] {// 不需要权限控制的action
	"loginUI.action",// 登录界面
	"login.action",// 登录 
	"loginOut.action",// 注销 
	"registUI.action",// 注册页面 
	"regist.action",// 注册
	"entity_codeView.action"
	};
	private static final String[] privilegesWithLogin = new String[] {// 只需要登录就能拥有的权限

	"loginSuccess.action",// 登录成功

	"main/menu.action",// 主菜单

	"main/admin.action",// 主页面

	"operator_infoSet.action",// 个人信息设置

	"bussiness_exportEcel.action",// 工单导出

	"bussiness_msg.action",// 工单添加结果页面
	
	"notice_deptTree.action",// 发布公告时选择部门页面

	"article.print.action",//
	
	"article.play.action",//
	
	"questionnaireInstance_view.action",//   
	 
	"dictionaryDetail_up.action",//
	
	"dictionaryDetail_down.action",
	
	"callTask_clientCall.action",
	
	"varLabel_findLabelByName.action"


	};

	private static final String publicPrefix = "ajax";

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
		throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		req.setCharacterEncoding("UTF-8");
		String url = req.getRequestURI();
		String contentPath = req.getContextPath();
		//查看MAC地址，不拦截
		if(url.endsWith("mac.jsp")){
			chain.doFilter(request, response);
			return;
		}
		if(url.endsWith("regist.action")){
			chain.doFilter(request, response);
			return;
		}
		if(!hasRegist){
			//启动计时
			if(timeStart==null){
				timeStart = System.currentTimeMillis();
			}
			if((System.currentTimeMillis()-timeStart)/1000>60*60*3){
				String license = CommonUtil.getConfig("license");
				String pubKey = CommonUtil.getConfig("pubkey");
				//没有公钥
				if(pubKey == null || pubKey.trim().equals("")){
					resp.sendRedirect(contentPath + "/noPubKey.jsp");
					return;
				}
				//没有授权号或者验证失败
				if(license == null || license.trim().equals("") || !CommonUtil.verify(pubKey.getBytes(), CommonUtil.getPhysicalAddress(), license.getBytes())){
					resp.sendRedirect(contentPath + "/noLicense.jsp");
					return;
				}
				hasRegist = true;
			}else{//小于这个时间时任然尝试验证
				String license = CommonUtil.getConfig("license");
				String pubKey = CommonUtil.getConfig("pubkey");
				if(pubKey != null && !pubKey.trim().equals("") && license != null && !license.trim().equals("")){
					if(CommonUtil.verify(pubKey.getBytes(), CommonUtil.getPhysicalAddress(), license.getBytes())){
						hasRegist = true;
					}
				}
			}
		}

		Operator operator = (Operator) req.getSession().getAttribute("OPERATOR");
		url = url.substring(url.indexOf("/") + (contentPath.length() + 1));
		
		if (url.startsWith(publicPrefix)) {
			chain.doFilter(request, response);
			return;
		}
		for (String str : privilegesWithOutLogin) {
			if (url.startsWith(str)) {
				chain.doFilter(request, response);
				return;
			}
		}
		if (operator == null) {
			resp.sendRedirect(contentPath + "/loginUI.action");
			return;
		}
		if (!privilegeValidate(operator, url, null, req)) {
			resp.sendRedirect(contentPath + "/noPrivilege.jsp");
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig arg0) throws ServletException {
	}

	public boolean privilegeValidate(Operator operator, String url,
		HashMap<String, String> yourParam, HttpServletRequest req) {

		if (url.startsWith(publicPrefix)) {
			return true;
		}

		for (String str : privilegesWithOutLogin) {
			if (url.startsWith(str)) {
				return true;
			}
		}

//		System.out.println("------------------->页面url：" + url);

		

		if (operator != null) {// the operator is login
			for (String str : privilegesWithLogin) {
				if(str.equals("questionnaireInstance.view_action")){
					System.out.println(str);   
					System.out.println(url);   
				} 
				str = str.replaceAll("UI", "");
				if (url.startsWith(str) || url.equals(str)) {
					return true;
				}
			}
			url = processUrl(url);
			HashMap<String, String> params = new HashMap<String, String>();
			Role role = operator.getRole();
			if (role == null) {
				return false;
			}

			Set<Permission> permissions = role.getPermissions();
//			for (Permission p : permissions) {
//				System.out.println(p.getUrl() + "    " + p.getName());
//			}
			for (Permission p : permissions) {
				String purl = p.getUrl();
				if (purl != null && !purl.trim().equals("")) {
					purl = processUrl(purl);
//					System.out.println("-----------------------------------------------------------------");
//					System.out.println("访问:" + url);
//					System.out.println("对比:" + purl + "(" + p.getName() + ")");
//					System.out.println("purl.startWith(\"" + url + "\")" + purl.startsWith(url));

					params = parseUrl(purl);
					Set<Entry<String, String>> set = params.entrySet();
					boolean ok = true;
					for (Entry<String, String> e : set) {
						ok = false;
					//	System.out.println("key:" + e.getKey());
					//	System.out.println("传递的value:" + req.getParameter(e.getKey()));
					//	System.out.println("期望的value:" + e.getValue());

						String valueInScope = req.getParameter(e.getKey());
						String valueInUrl = null;
						
						if (yourParam != null) {
							valueInUrl = yourParam.get(e.getKey());
						}

						if (valueInScope != null && valueInScope.equals(e.getValue())) {
							ok = true;
						}

						if (valueInUrl != null && valueInUrl.equals(e.getValue())) {
							ok = true;
						}
					}
					if (purl.startsWith(url) && ok) {
						return ok;
					}
				}
			}
		}
		return false;
	}

	/**
	 * 解析
	 * @param purl
	 * @return
	 */
	public HashMap<String, String> parseUrl(String url) {
		HashMap<String, String> params = new HashMap<String, String>();
		if (url.contains("?")) {// 含有参数
			String temp = url.substring(url.indexOf("?") + 1);
			String[] kvs = temp.split("&");
			for (String kv : kvs) {
				params.put(kv.split("=")[0], kv.split("=").length == 2 ? kv.split("=")[1] : "");
			}
		}
		return params;
	}

	private String processUrl(String url) {
		if (url != null) {
			url = url.replaceAll("List", "");// 如：有processList权限，则有process权限
			url = url.replaceAll("UI", "");// 如：有processUI权限，则有process权限
											// 即：processList=processUI=process
			//url = url.replaceAll("view", "list");
			//url = url.replaceAll("detail", "list"); 
			//url = url.replaceAll("print", "list");
			//url = url.replaceAll("show", "list");
		}
		return url;
	}
}
