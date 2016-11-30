package com.qihang.buss.sc.oa.controller;

import com.qihang.buss.sc.oa.entity.TScNewsEntity;
import com.qihang.buss.sc.oa.service.TScNewsServiceI;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.ExceptionUtil;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.ResourceUtil;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.poi.excel.ExcelImportUtil;
import com.qihang.winter.poi.excel.entity.ExportParams;
import com.qihang.winter.poi.excel.entity.ImportParams;
import com.qihang.winter.poi.excel.entity.TemplateExportParams;
import com.qihang.winter.poi.excel.entity.vo.NormalExcelConstants;
import com.qihang.winter.poi.excel.entity.vo.TemplateExcelConstants;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.web.system.service.SystemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;


/**
 * @Title: Controller
 * @Description: 新闻管理
 * @author onlineGenerator
 * @date 2015-12-07 14:08:06
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScNewsController")
public class TScNewsController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScNewsController.class);

	@Autowired
	private TScNewsServiceI tScNewsService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 新闻管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScNews")
	public ModelAndView tScNews(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/oa/tScNewsList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScNewsEntity tScNews,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScNewsEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScNews, request.getParameterMap());
		try{
		//自定义追加查询条件
		String query_issuanceDate_begin = request.getParameter("issuanceDate_begin");
		String query_issuanceDate_end = request.getParameter("issuanceDate_end");
		if(StringUtil.isNotEmpty(query_issuanceDate_begin)){
			cq.ge("issuanceDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_issuanceDate_begin));
		}
		if(StringUtil.isNotEmpty(query_issuanceDate_end)){
			cq.le("issuanceDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_issuanceDate_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScNewsService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除新闻管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScNewsEntity tScNews, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScNews = systemService.getEntity(TScNewsEntity.class, tScNews.getId());
		message = "新闻管理删除成功";
		try{
			tScNewsService.delete(tScNews);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "新闻管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除新闻管理
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "新闻管理删除成功";
		try{
			for(String id:ids.split(",")){
				TScNewsEntity tScNews = systemService.getEntity(TScNewsEntity.class,
				id
				);
				tScNewsService.delete(tScNews);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "新闻管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加新闻管理
	 * 
	 * @param tScNews
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScNewsEntity tScNews, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if(tScNews.getIssuance() == null){
			tScNews.setIssuance(0);
		}

		/*List<Object[]> departList = tOaNewsService.findListbySql("WITH sontype AS ( SELECT id, departname FROM" +
				" t_s_depart WHERE departname = 'OA管理'UNION ALL SELECT de.id, de.departname FROM sontype s," +
				" t_s_depart de WHERE s.id = de.parentdepartid )" +
				" SELECT id, departname AS description FROM sontype c");
		List<Object[]> listUserId = null;
		for (Object[] depart : departList) {
			List<Object[]> baseUserList = tOaNewsService.findListbySql("SELECT ID,REALNAME FROM T_S_BASE_USER WHERE id in ("+
					"select user_id from t_s_user_org where org_id ='"+depart[0]+"')");
			listUserId.addAll(baseUserList);
		}
		tOaNews.setUserId(listUserId.toString());*/

		tScNews.setContent(tScNews.getContent().replaceAll("\"", "&quot"));
		message = "新闻管理添加成功";
		try{
			tScNewsService.save(tScNews);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "新闻管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新新闻管理
	 * 
	 * @param tScNews
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScNewsEntity tScNews, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "新闻管理更新成功";
		TScNewsEntity t = tScNewsService.get(TScNewsEntity.class, tScNews.getId());
		t.setContent(t.getContent().replaceAll("\"", "&quot"));
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tScNews, t);
			tScNewsService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "新闻管理更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 新闻管理新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScNewsEntity tScNews, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScNews.getId())) {
			tScNews = tScNewsService.getEntity(TScNewsEntity.class, tScNews.getId());
			req.setAttribute("tScNewsPage", tScNews);
		}
		return new ModelAndView("com/qihang/buss/sc/oa/tScNews-add");
	}
	/**
	 * 新闻管理编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScNewsEntity tScNews, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScNews.getId())) {
			tScNews = tScNewsService.getEntity(TScNewsEntity.class, tScNews.getId());
			req.setAttribute("tScNewsPage", tScNews);
			String load= req.getParameter("load");
			req.setAttribute("load",load);
		}
		return new ModelAndView("com/qihang/buss/sc/oa/tScNews-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tScNewsController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScNewsEntity tScNews,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScNewsEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScNews, request.getParameterMap());
		List<TScNewsEntity> tScNewss = this.tScNewsService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"新闻管理");
		modelMap.put(NormalExcelConstants.CLASS,TScNewsEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("新闻管理列表", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tScNewss);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScNewsEntity tScNews,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "新闻管理");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScNewsEntity.class);
		modelMap.put(TemplateExcelConstants.LIST_DATA,null);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<TScNewsEntity> listTScNewsEntitys = ExcelImportUtil.importExcel(file.getInputStream(), TScNewsEntity.class, params);
				for (TScNewsEntity tScNews : listTScNewsEntitys) {
					tScNewsService.save(tScNews);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}

	/**
	 * 新闻发布
	 * @param id
	 * @return
     */
	@RequestMapping(params = "release")
	@ResponseBody
	public Map<String,Object> release(String id){
		Map<String, Object> result = tScNewsService.release(id);
		return result;
	}
}
