package com.qihang.buss.sc.baseinfo.controller;
import com.qihang.buss.sc.baseinfo.entity.TScSoncompanyEntity;
import com.qihang.buss.sc.baseinfo.service.TScSoncompanyServiceI;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qihang.buss.sc.util.BillNoGenerate;
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
 * @Description: 分支机构
 * @author onlineGenerator
 * @date 2016-07-08 14:37:30
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScSoncompanyController")
public class TScSoncompanyController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScSoncompanyController.class);

	@Autowired
	private TScSoncompanyServiceI tScSoncompanyService;
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
	 * 分支机构列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScSoncompany")
	public ModelAndView tScSoncompany(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScSoncompanyList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScSoncompanyEntity tScSoncompany,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScSoncompanyEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScSoncompany, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScSoncompanyService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除分支机构
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScSoncompanyEntity tScSoncompany, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScSoncompany = systemService.getEntity(TScSoncompanyEntity.class, tScSoncompany.getId());
		message = "分支机构删除成功";
		try{
			tScSoncompanyService.delete(tScSoncompany);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "分支机构删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除分支机构
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "分支机构删除成功";
		try{
			for(String id:ids.split(",")){
				TScSoncompanyEntity tScSoncompany = systemService.getEntity(TScSoncompanyEntity.class, 
				id
				);
				tScSoncompanyService.delete(tScSoncompany);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "分支机构删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加分支机构
	 * 
	 * @param tScSoncompany
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScSoncompanyEntity tScSoncompany, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "分支机构添加成功";
		tScSoncompany.setDisabled(Globals.ENABLE_CODE);
		try{
			tScSoncompanyService.save(tScSoncompany);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "分支机构添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新分支机构
	 * 
	 * @param tScSoncompany
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScSoncompanyEntity tScSoncompany, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "分支机构更新成功";
		TScSoncompanyEntity t = tScSoncompanyService.get(TScSoncompanyEntity.class, tScSoncompany.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tScSoncompany, t);
			tScSoncompanyService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "分支机构更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 分支机构新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScSoncompanyEntity tScSoncompany, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScSoncompany.getId())) {
			tScSoncompany = tScSoncompanyService.getEntity(TScSoncompanyEntity.class, tScSoncompany.getId());
//			tScSoncompany.setNumber(BillNoGenerate.getBillNo("13"));
			req.setAttribute("tScSoncompanyPage", tScSoncompany);
		}
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScSoncompany-add");
	}
	/**
	 * 分支机构编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScSoncompanyEntity tScSoncompany, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScSoncompany.getId())) {
			tScSoncompany = tScSoncompanyService.getEntity(TScSoncompanyEntity.class, tScSoncompany.getId());
			req.setAttribute("tScSoncompanyPage", tScSoncompany);
		}
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScSoncompany-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tScSoncompanyController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScSoncompanyEntity tScSoncompany,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScSoncompanyEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScSoncompany, request.getParameterMap());
		List<TScSoncompanyEntity> tScSoncompanys = this.tScSoncompanyService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"分支机构");
		modelMap.put(NormalExcelConstants.CLASS,TScSoncompanyEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("分支机构列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, tScSoncompanys);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScSoncompanyEntity tScSoncompany,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "分支机构");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScSoncompanyEntity.class);
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
				List<TScSoncompanyEntity> listTScSoncompanyEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TScSoncompanyEntity.class,params);
				for (TScSoncompanyEntity tScSoncompany : listTScSoncompanyEntitys) {
					tScSoncompanyService.save(tScSoncompany);
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
	 * 禁用启用
	 * @param tScSoncompanyEntity
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "doDisable")
	@ResponseBody
	public AjaxJson doDisable(TScSoncompanyEntity tScSoncompanyEntity, HttpServletRequest req){
		AjaxJson j = new AjaxJson();
		String message = "操作成功";
		try{
			for(String id:tScSoncompanyEntity.getId().split(",")){
				TScSoncompanyEntity scStockEntity = systemService.getEntity(TScSoncompanyEntity.class, id);
				if(tScSoncompanyEntity.getDisabled() == Globals.ENABLE_CODE){//启用
					if(tScSoncompanyEntity.getDisabled() == scStockEntity.getDisabled()){
						message="已启用";
					}else{
						scStockEntity.setDisabled(Globals.ENABLE_CODE);
					}
				}else{//禁用
					if(tScSoncompanyEntity.getDisabled() == scStockEntity.getDisabled()) {
						message="已禁用";
					}else{
						scStockEntity.setDisabled(Globals.DISABLED_CODE);
					}
				}
				tScSoncompanyService.saveOrUpdate(scStockEntity);
			}
		}catch (Exception e){
			e.printStackTrace();
			message = "操作失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
}
