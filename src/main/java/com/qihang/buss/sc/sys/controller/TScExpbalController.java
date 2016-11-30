package com.qihang.buss.sc.sys.controller;
import com.qihang.buss.sc.sys.entity.TScExpbalEntity;
import com.qihang.buss.sc.sys.entity.TScRpExpbalEntity;
import com.qihang.buss.sc.sys.service.TScExpbalServiceI;
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
 * @Description: 收支结账表
 * @author onlineGenerator
 * @date 2016-08-31 14:14:32
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScExpbalController")
public class TScExpbalController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScExpbalController.class);

	@Autowired
	private TScExpbalServiceI tScExpbalService;
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
	 * 收支结账表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScExpbal")
	public ModelAndView tScExpbal(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/sys/tScExpbalList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScRpExpbalEntity tScExpbal,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScRpExpbalEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScExpbal, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScExpbalService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除收支结账表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScExpbalEntity tScExpbal, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScExpbal = systemService.getEntity(TScExpbalEntity.class, tScExpbal.getId());
		message = "收支结账表删除成功";
		try{
			tScExpbalService.delete(tScExpbal);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "收支结账表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除收支结账表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "收支结账表删除成功";
		try{
			for(String id:ids.split(",")){
				TScExpbalEntity tScExpbal = systemService.getEntity(TScExpbalEntity.class, 
				id
				);
				tScExpbalService.delete(tScExpbal);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "收支结账表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加收支结账表
	 * 
	 * @param tScExpbal
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScExpbalEntity tScExpbal, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "收支结账表添加成功";
		try{
			tScExpbalService.save(tScExpbal);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "收支结账表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新收支结账表
	 * 
	 * @param tScExpbal
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScExpbalEntity tScExpbal, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "收支结账表更新成功";
		TScExpbalEntity t = tScExpbalService.get(TScExpbalEntity.class, tScExpbal.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tScExpbal, t);
			tScExpbalService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "收支结账表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 收支结账表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScExpbalEntity tScExpbal, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScExpbal.getId())) {
			tScExpbal = tScExpbalService.getEntity(TScExpbalEntity.class, tScExpbal.getId());
			req.setAttribute("tScExpbalPage", tScExpbal);
		}
		return new ModelAndView("com/qihang/buss/sc/sys/tScExpbal-add");
	}
	/**
	 * 收支结账表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScExpbalEntity tScExpbal, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScExpbal.getId())) {
			tScExpbal = tScExpbalService.getEntity(TScExpbalEntity.class, tScExpbal.getId());
			req.setAttribute("tScExpbalPage", tScExpbal);
		}
		return new ModelAndView("com/qihang/buss/sc/sys/tScExpbal-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tScExpbalController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScExpbalEntity tScExpbal,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScExpbalEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScExpbal, request.getParameterMap());
		List<TScExpbalEntity> tScExpbals = this.tScExpbalService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"收支结账表");
		modelMap.put(NormalExcelConstants.CLASS,TScExpbalEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("收支结账表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tScExpbals);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScExpbalEntity tScExpbal,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "收支结账表");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScExpbalEntity.class);
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
				List<TScExpbalEntity> listTScExpbalEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TScExpbalEntity.class,params);
				for (TScExpbalEntity tScExpbal : listTScExpbalEntitys) {
					tScExpbalService.save(tScExpbal);
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
