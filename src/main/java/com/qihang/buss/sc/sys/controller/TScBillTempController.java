package com.qihang.buss.sc.sys.controller;
import com.alibaba.fastjson.JSONObject;
import com.qihang.buss.sc.sys.entity.TScBillTempEntity;
import com.qihang.buss.sc.sys.service.TScBillTempServiceI;

import java.util.Date;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qihang.winter.core.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.context.annotation.Scope;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.service.SystemService;

import java.io.OutputStream;

import com.qihang.winter.poi.excel.ExcelExportUtil;
import com.qihang.winter.poi.excel.ExcelImportUtil;
import com.qihang.winter.poi.excel.entity.ExportParams;
import com.qihang.winter.poi.excel.entity.ImportParams;
import com.qihang.winter.poi.excel.entity.TemplateExportParams;
import com.qihang.winter.poi.excel.entity.vo.NormalExcelConstants;
import com.qihang.winter.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;


/**
 * @Title: Controller
 * @Description: 单据草稿表
 * @author onlineGenerator
 * @date 2016-08-24 19:27:38
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScBillTempController")
public class TScBillTempController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScBillTempController.class);

	@Autowired
	private TScBillTempServiceI tScBillTempService;
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
	 * 单据草稿表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScBillTemp")
	public ModelAndView tScBillTemp(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/sys/tScBillTempList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScBillTempEntity tScBillTemp,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScBillTempEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScBillTemp, request.getParameterMap());
		try{
			//自定义追加查询条件
			String query_date_begin = request.getParameter("date_begin");
			String query_date_end = request.getParameter("date_end");
			if(StringUtil.isNotEmpty(query_date_begin)){
				cq.ge("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_begin));
			}
			if(StringUtil.isNotEmpty(query_date_end)){
				cq.le("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_end));
			}
			String billerId  = ResourceUtil.getSessionUserName().getId();//制单人
			String sonId = ResourceUtil.getSessionUserName().getCurrentDepart().getId();//分支机构
			String accountId = ResourceUtil.getAccountId();
			cq.eq("billerId",billerId);
			//cq.eq("sonId",sonId);
			cq.eq("accountId",accountId);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScBillTempService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除单据草稿表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScBillTempEntity tScBillTemp, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScBillTemp = systemService.getEntity(TScBillTempEntity.class, tScBillTemp.getId());
		message = "单据草稿表删除成功";
		try{
			tScBillTempService.delete(tScBillTemp);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "单据草稿表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除单据草稿表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "单据草稿表删除成功";
		try{
			for(String id:ids.split(",")){
				TScBillTempEntity tScBillTemp = systemService.getEntity(TScBillTempEntity.class, 
				id
				);
				tScBillTempService.delete(tScBillTemp);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "单据草稿表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加单据草稿表
	 * 
	 * @param tScBillTemp
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScBillTempEntity tScBillTemp, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "单据草稿表添加成功";
		try{
			tScBillTempService.save(tScBillTemp);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "单据草稿表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加单据草稿表
	 *
	 * @param json
	 * @return
	 */
	@RequestMapping(params = "doAddJson")
	@ResponseBody
	public AjaxJson doAddJson(@RequestBody String  json) {
	//public AjaxJson doAddJson( String  json) {
		AjaxJson j = new AjaxJson();
		message = "单据草稿表添加成功";
		try{
			this.tScBillTempService.doAddJson(json);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "单据草稿表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 选单页面查询
	 * @param tScBillTemp
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "goSelectDialog")
	public ModelAndView goSelectDialog(TScBillTempEntity tScBillTemp, HttpServletRequest req) {
		if (StringUtil.isNotEmpty( tScBillTemp.getTranType())) {
			req.setAttribute("tranType",  tScBillTemp.getTranType());
		}
		String billerId  = ResourceUtil.getSessionUserName().getId();//制单人
		String sonId = ResourceUtil.getSessionUserName().getCurrentDepart().getId();//分支机构
		String accountId = ResourceUtil.getAccountId();
		req.setAttribute("billerId",billerId);
		req.setAttribute("sonId",sonId);
		req.setAttribute("accountId",accountId);
		return new ModelAndView("com/qihang/buss/sc/sys/tScBillTempList");
	}
	
	/**
	 * 更新单据草稿表
	 * 
	 * @param tScBillTemp
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScBillTempEntity tScBillTemp, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "单据草稿表更新成功";
		TScBillTempEntity t = tScBillTempService.get(TScBillTempEntity.class, tScBillTemp.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tScBillTemp, t);
			tScBillTempService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "单据草稿表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 单据草稿表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScBillTempEntity tScBillTemp, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScBillTemp.getId())) {
			tScBillTemp = tScBillTempService.getEntity(TScBillTempEntity.class, tScBillTemp.getId());
			req.setAttribute("tScBillTempPage", tScBillTemp);
		}
		return new ModelAndView("com/qihang/buss/sc/sys/tScBillTemp-add");
	}
	/**
	 * 单据草稿表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScBillTempEntity tScBillTemp, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScBillTemp.getId())) {
			tScBillTemp = tScBillTempService.getEntity(TScBillTempEntity.class, tScBillTemp.getId());
			req.setAttribute("tScBillTempPage", tScBillTemp);
		}
		return new ModelAndView("com/qihang/buss/sc/sys/tScBillTemp-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tScBillTempController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScBillTempEntity tScBillTemp,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScBillTempEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScBillTemp, request.getParameterMap());
		List<TScBillTempEntity> tScBillTemps = this.tScBillTempService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"单据草稿表");
		modelMap.put(NormalExcelConstants.CLASS,TScBillTempEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("单据草稿表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tScBillTemps);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScBillTempEntity tScBillTemp,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "单据草稿表");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScBillTempEntity.class);
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
				List<TScBillTempEntity> listTScBillTempEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TScBillTempEntity.class,params);
				for (TScBillTempEntity tScBillTemp : listTScBillTempEntitys) {
					tScBillTempService.save(tScBillTemp);
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
}
