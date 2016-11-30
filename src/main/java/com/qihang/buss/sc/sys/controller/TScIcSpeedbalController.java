package com.qihang.buss.sc.sys.controller;
import com.qihang.buss.sc.baseinfo.entity.TScIcitemEntity;
import com.qihang.buss.sc.baseinfo.entity.TScItemPriceEntity;
import com.qihang.buss.sc.baseinfo.entity.TScStockEntity;
import com.qihang.buss.sc.sys.entity.TScIcSpeedbalEntity;
import com.qihang.buss.sc.sys.entity.TScIcSpeedbalViewEntity;
import com.qihang.buss.sc.sys.service.TScIcSpeedbalServiceI;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.core.util.MyBeanUtils;

import java.io.OutputStream;
import com.qihang.winter.core.util.BrowserUtils;
import com.qihang.winter.poi.excel.ExcelExportUtil;
import com.qihang.winter.poi.excel.ExcelImportUtil;
import com.qihang.winter.poi.excel.entity.ExportParams;
import com.qihang.winter.poi.excel.entity.ImportParams;
import com.qihang.winter.poi.excel.entity.TemplateExportParams;
import com.qihang.winter.poi.excel.entity.vo.NormalExcelConstants;
import com.qihang.winter.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.qihang.winter.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import com.qihang.winter.core.util.ExceptionUtil;



/**   
 * @Title: Controller
 * @Description: 存货日结表
 * @author onlineGenerator
 * @date 2016-08-20 10:36:42
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScIcSpeedbalController")
public class TScIcSpeedbalController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScIcSpeedbalController.class);

	@Autowired
	private TScIcSpeedbalServiceI tScIcSpeedbalService;
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
	 * 存货日结表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScIcSpeedbal")
	public ModelAndView tScIcSpeedbal(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/sys/tScIcSpeedbalList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScIcSpeedbalViewEntity tScIcSpeedbal,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScIcSpeedbalViewEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScIcSpeedbal, request.getParameterMap());
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
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScIcSpeedbalService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除存货日结表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScIcSpeedbalEntity tScIcSpeedbal, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScIcSpeedbal = systemService.getEntity(TScIcSpeedbalEntity.class, tScIcSpeedbal.getId());
		message = "存货日结表删除成功";
		try{
			tScIcSpeedbalService.delete(tScIcSpeedbal);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "存货日结表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除存货日结表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "存货日结表删除成功";
		try{
			for(String id:ids.split(",")){
				TScIcSpeedbalEntity tScIcSpeedbal = systemService.getEntity(TScIcSpeedbalEntity.class, 
				id
				);
				tScIcSpeedbalService.delete(tScIcSpeedbal);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "存货日结表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加存货日结表
	 * 
	 * @param tScIcSpeedbal
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScIcSpeedbalEntity tScIcSpeedbal, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "存货日结表添加成功";
		try{
			tScIcSpeedbalService.save(tScIcSpeedbal);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "存货日结表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新存货日结表
	 * 
	 * @param tScIcSpeedbal
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScIcSpeedbalEntity tScIcSpeedbal, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "存货日结表更新成功";
		TScIcSpeedbalEntity t = tScIcSpeedbalService.get(TScIcSpeedbalEntity.class, tScIcSpeedbal.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tScIcSpeedbal, t);
			tScIcSpeedbalService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "存货日结表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 存货日结表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScIcSpeedbalEntity tScIcSpeedbal, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScIcSpeedbal.getId())) {
			tScIcSpeedbal = tScIcSpeedbalService.getEntity(TScIcSpeedbalEntity.class, tScIcSpeedbal.getId());
			req.setAttribute("tScIcSpeedbalPage", tScIcSpeedbal);
		}
		return new ModelAndView("com/qihang/buss/sc/sys/tScIcSpeedbal-add");
	}
	/**
	 * 存货日结表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScIcSpeedbalEntity tScIcSpeedbal, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScIcSpeedbal.getId())) {
			tScIcSpeedbal = tScIcSpeedbalService.getEntity(TScIcSpeedbalEntity.class, tScIcSpeedbal.getId());
			req.setAttribute("tScIcSpeedbalPage", tScIcSpeedbal);
		}
		return new ModelAndView("com/qihang/buss/sc/sys/tScIcSpeedbal-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tScIcSpeedbalController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScIcSpeedbalViewEntity tScIcSpeedbal,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScIcSpeedbalViewEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScIcSpeedbal, request.getParameterMap());
		List<TScIcSpeedbalViewEntity> tScIcSpeedbals = this.tScIcSpeedbalService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"存货日结表");
		modelMap.put(NormalExcelConstants.CLASS,TScIcSpeedbalViewEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("存货日结表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tScIcSpeedbals);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScIcSpeedbalEntity tScIcSpeedbal,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "存货日结表");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScIcSpeedbalEntity.class);
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
				List<TScIcSpeedbalEntity> listTScIcSpeedbalEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TScIcSpeedbalEntity.class,params);
				for (TScIcSpeedbalEntity tScIcSpeedbal : listTScIcSpeedbalEntitys) {
					tScIcSpeedbalService.save(tScIcSpeedbal);
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
