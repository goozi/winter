package com.qihang.winter.web.cgreport.controller.core;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.druid.util.StringUtils;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.enums.SysThemesEnum;
import com.qihang.winter.core.util.ContextHolderUtils;
import com.qihang.winter.core.util.ResourceUtil;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.core.util.SysThemesUtil;
import com.qihang.winter.web.cgform.common.CgAutoListConstant;
import com.qihang.winter.web.cgform.engine.FreemarkerHelper;
import com.qihang.winter.web.cgreport.common.CgReportConstant;
import com.qihang.winter.web.cgreport.exception.CgReportNotFoundException;
import com.qihang.winter.web.cgreport.service.core.CgReportServiceI;
import com.qihang.winter.web.cgreport.util.CgReportQueryParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 
 * @Title:CgReportController
 * @description:动态报表展示控制器
 * @author Zerrion
 * @date Jul 29, 2013 9:39:40 PM
 * @version V1.0
 */
@Controller
@RequestMapping("/cgReportController")
public class CgReportController extends BaseController {
	@Autowired
	private CgReportServiceI cgReportService;
	/**
	 * 动态报表展现入口
	 * @param id 动态配置ID-code
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "list")
	public void list(String id, HttpServletRequest request,
			HttpServletResponse response) {
		//step.1 根据id获取该动态报表的配置参数
		Map<String, Object>  cgReportMap = null;
		try{
			cgReportMap = cgReportService.queryCgReportConfig(id);
		}catch (Exception e) {
			throw new CgReportNotFoundException("动态报表配置不存在!");
		}
		//step.2 获取列表ftl模板路径
		FreemarkerHelper viewEngine = new FreemarkerHelper();
		//step.3 组合模板+数据参数，进行页面展现
		loadVars(cgReportMap);
		//step.4 页面css js引用
		cgReportMap.put(CgAutoListConstant.CONFIG_IFRAME, getHtmlHead(request));
		String html = viewEngine.parseTemplate("/com/qihang/winter/web/cgreport/engine/core/cgreportlist.ftl", cgReportMap);
		try {
			response.setContentType("text/html");
			response.setHeader("Cache-Control", "no-store");
			PrintWriter writer = response.getWriter();
			writer.println(html);
//			System.out.println(html);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getHtmlHead(HttpServletRequest request){
		HttpSession session = ContextHolderUtils.getSession();
		String lang = (String)session.getAttribute("lang");
		StringBuilder sb= new StringBuilder("");
		SysThemesEnum sysThemesEnum = SysThemesUtil.getSysTheme(request);
		sb.append("<script type=\"text/javascript\" src=\"plug-in/jquery/jquery-1.8.3.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/dataformat.js\"></script>");
		sb.append(SysThemesUtil.getEasyUiTheme(sysThemesEnum));
		sb.append(SysThemesUtil.getEasyUiMainTheme(sysThemesEnum));
		sb.append("<link rel=\"stylesheet\" href=\"plug-in/easyui/themes/icon.css\" type=\"text/css\"></link>");
		sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"plug-in/accordion/css/accordion.css\">");
		sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"plug-in/accordion/css/icons.css\">");
		sb.append("<script type=\"text/javascript\" src=\"plug-in/easyui/jquery.easyui.min.1.3.2.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\"plug-in/easyui/locale/zh-cn.js\"></script>");
		sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/syUtil.js\"></script>");
		sb.append(SysThemesUtil.getLhgdialogTheme(sysThemesEnum));
		sb.append(SysThemesUtil.getCommonTheme(sysThemesEnum));
		sb.append("<script type=\"text/javascript\" src=\"plug-in/My97DatePicker/WdatePicker.js\"></script>");
		sb.append(StringUtil.replace("<script type=\"text/javascript\" src=\"plug-in/tools/curdtools_{0}.js\"></script>", "{0}", lang));
		sb.append("<script type=\"text/javascript\" src=\"plug-in/tools/easyuiextend.js\"></script>");
		return sb.toString();
	}
	
	/**
	 * popup入口
	 * @param id 动态配置ID-code
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "popup")
	public void popup(String id, HttpServletRequest request,
			HttpServletResponse response) {
		//step.1 根据id获取该动态报表的配置参数
		Map<String, Object>  cgReportMap = null;
		try{
			cgReportMap = cgReportService.queryCgReportConfig(id);
		}catch (Exception e) {
			throw new CgReportNotFoundException("动态报表配置不存在!");
		}
		//step.2 获取列表ftl模板路径
		FreemarkerHelper viewEngine = new FreemarkerHelper();
		//step.3 组合模板+数据参数，进行页面展现
		loadVars(cgReportMap);
		//step.4 页面css js引用
		cgReportMap.put(CgAutoListConstant.CONFIG_IFRAME, getHtmlHead(request));
		String html = viewEngine.parseTemplate("/com/qihang/winter/web/cgreport/engine/core/cgreportlistpopup.ftl", cgReportMap);
		try {
			response.setContentType("text/html");
			response.setHeader("Cache-Control", "no-store");
			PrintWriter writer = response.getWriter();
			writer.println(html);
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 组装模版参数
	 * @param cgReportMap
	 */
	@SuppressWarnings("unchecked")
	private void loadVars(Map<String, Object> cgReportMap) {
		Map mainM = (Map) cgReportMap.get(CgReportConstant.MAIN);
		List<Map<String,Object>> fieldList = (List<Map<String, Object>>) cgReportMap.get(CgReportConstant.ITEMS);
		List<Map<String,Object>> queryList = new ArrayList<Map<String,Object>>(0);
		for(Map<String,Object> fl:fieldList){
			fl.put(CgReportConstant.ITEM_FIELDNAME, ((String)fl.get(CgReportConstant.ITEM_FIELDNAME)).toLowerCase());
			String isQuery = (String) fl.get(CgReportConstant.ITEM_ISQUERY);
			if(CgReportConstant.BOOL_TRUE.equalsIgnoreCase(isQuery)){
				loadDic(fl,fl);
				queryList.add(fl);
			}
		}
		cgReportMap.put(CgReportConstant.CONFIG_ID, mainM.get("code"));
		cgReportMap.put(CgReportConstant.CONFIG_NAME, mainM.get("name"));
		cgReportMap.put(CgReportConstant.CONFIG_FIELDLIST, fieldList);
		cgReportMap.put(CgReportConstant.CONFIG_QUERYLIST, queryList);
	}
	
	
	/**
	 * 处理数据字典
	 * @param result 查询的结果集
	 * @param beans 字段配置
	 */
	@SuppressWarnings("unchecked")
	private void dealDic(List<Map<String, Object>> result,
			List<Map<String,Object>> beans) {
		for(Map<String,Object> bean:beans){
			String dict_code = (String) bean.get(CgReportConstant.ITEM_DICCODE);
			if(StringUtil.isEmpty(dict_code)){
				//不需要处理字典
				continue;
			}else{
				List<Map<String, Object>> dicDatas = queryDic(dict_code);
				for(Map r:result){
					String value = String.valueOf(r.get(bean.get(CgReportConstant.ITEM_FIELDNAME)));
					for(Map m:dicDatas){
						String typecode = String.valueOf(m.get("typecode"));
						String typename = String.valueOf(m.get("typename"));
						if(value.equalsIgnoreCase(typecode)){
							r.put(bean.get(CgReportConstant.ITEM_FIELDNAME),typename);
						}
					}
				}
			}
		}
	}
	/**
	 * 处理取值表达式
	 * @param result
	 * @param beans
	 */
	@SuppressWarnings("unchecked")
	private void dealReplace(List<Map<String, Object>> result,
			List<Map<String,Object>> beans){
		for(Map<String,Object> bean:beans){
			try{
				//获取取值表达式
				String replace = (String) bean.get(CgReportConstant.ITEM_REPLACE);
				if(StringUtil.isEmpty(replace)){
					continue;
				}
				String[] groups = replace.split(",");
				for(String g:groups){
					String[] items = g.split("_");
					String v = items[0];//逻辑判断值
					String txt = items[1];//要转换的文本
					for(Map r:result){
						String value = String.valueOf(r.get(bean.get(CgReportConstant.ITEM_FIELDNAME)));
						if(value.equalsIgnoreCase(v)){
							r.put(bean.get(CgReportConstant.ITEM_FIELDNAME),txt);
						}
					}
				}
			}catch (Exception e) {
				//这里出现异常原因是因为取值表达式不正确
				e.printStackTrace();
				throw new BusinessException("取值表达式不正确");
			}
		}
	}
	/**
	 * 动态报表数据查询
	 * @param configId 配置id-code
	 * @param page 分页页面
	 * @param rows 分页大小
	 * @param request 
	 * @param response
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "datagrid")
	public void datagrid(String configId,String page,String field,String rows,HttpServletRequest request,
			HttpServletResponse response) {
		//step.1 根据id获取该动态报表的配置参数
		Map<String, Object>  cgReportMap = null;
		try{
			cgReportMap = cgReportService.queryCgReportConfig(configId);
			if(cgReportMap.size()<=0){
				throw new CgReportNotFoundException("动态报表配置不存在!");
			}
		}catch (Exception e) {
			throw new CgReportNotFoundException("查找动态报表配置失败!"+e.getMessage());
		}
		//step.2 获取该配置的查询SQL
		Map configM = (Map) cgReportMap.get(CgReportConstant.MAIN);
		String querySql = (String) configM.get(CgReportConstant.CONFIG_SQL);
		List<Map<String,Object>> items = (List<Map<String, Object>>) cgReportMap.get(CgReportConstant.ITEMS);
		Map queryparams =  new LinkedHashMap<String,Object>();
		for(Map<String,Object> item:items){
			String isQuery = (String) item.get(CgReportConstant.ITEM_ISQUERY);
			if(CgReportConstant.BOOL_TRUE.equalsIgnoreCase(isQuery)){
				//step.3 装载查询条件
				CgReportQueryParamUtil.loadQueryParams(request, item, queryparams);
			}
		}
		//step.4 进行查询返回结果
		int p = page==null?1:Integer.parseInt(page);
		int r = rows==null?99999:Integer.parseInt(rows);
        //默认查询条件配置
        if(querySql.indexOf("#") > -1){
            String[] info = querySql.split("#");
            for(String item : info) {
                String [] info2 = item.split("}");
                if(info2.length > 1) {
                    String query = info2[0];
                    query = query.substring(1,query.length());
                    String value = ResourceUtil.getUserSystemData(query);
                    if(!StringUtils.isEmpty(value)){
                        querySql = querySql.replace("#{" + query + "}", "'" + value+"'");
                    }
                }else if(info2[0].indexOf("{") > -1){
                    String query = info2[0];
                    query = query.substring(1,query.length());
                    String value = ResourceUtil.getUserSystemData(query);
                    if(!StringUtils.isEmpty(value)){
                        querySql = querySql.replace("#{" + query + "}", "'" + value+"'");
                    }
                }
            }
        }

		List<Map<String, Object>> result= cgReportService.queryByCgReportSql(querySql, queryparams, p, r);
		long size = cgReportService.countQueryByCgReportSql(querySql, queryparams);
		dealDic(result,items);
		dealReplace(result,items);
		response.setContentType("application/json");
		response.setHeader("Cache-Control", "no-store");
		PrintWriter writer;
		try {
			writer = response.getWriter();
			writer.println(CgReportQueryParamUtil.getJson(result,size));
			writer.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 解析SQL，返回字段集
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "getFields", method = RequestMethod.POST)
	@ResponseBody
	public Object getSqlFields(String sql){
		List<String> result = null;
		Map reJson = new HashMap<String, Object>();
		try{
			result = cgReportService.getSqlFields(sql);
		}catch (Exception e) {
			e.printStackTrace();
			String errorInfo = "解析失败!<br><br>失败原因：";
			errorInfo += e.getMessage();
			reJson.put("status", "error");
			reJson.put("datas", errorInfo);
			return reJson;
		}
		reJson.put("status", "success");
		reJson.put("datas", result);
		return reJson;
	}
	/**
	 * 装载数据字典
	 * @param m	要放入freemarker的数据
	 * @param cgReportMap 读取出来的动态配置数据
	 */
	@SuppressWarnings("unchecked")
	private void loadDic(Map m, Map<String, Object> cgReportMap) {
		String dict_code = (String) cgReportMap.get("dict_code");
		if(StringUtil.isEmpty(dict_code)){
			m.put(CgReportConstant.FIELD_DICTLIST, new ArrayList(0));
			return;
		}
		List<Map<String, Object>> dicDatas = queryDic(dict_code);
		m.put(CgReportConstant.FIELD_DICTLIST, dicDatas);
	}
	/**
	 * 查询数据字典
	 * @param diccode 字典编码
	 * @return
	 */
	private List<Map<String, Object>> queryDic(String diccode) {
		StringBuilder dicSql = new StringBuilder();
		dicSql.append(" SELECT TYPECODE,TYPENAME FROM");
		dicSql.append(" "+CgReportConstant.SYS_DIC);
		dicSql.append(" "+"WHERE TYPEGROUPID = ");
		dicSql.append(" "+"(SELECT ID FROM "+CgReportConstant.SYS_DICGROUP+" WHERE TYPEGROUPCODE = '"+diccode+"' )");
		List<Map<String, Object>> dicDatas = cgReportService.findForJdbc(dicSql.toString());
		return dicDatas;
	}
}
