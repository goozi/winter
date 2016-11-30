package com.qihang.buss.sc.baseinfo.controller;
import com.qihang.buss.sc.baseinfo.entity.TScSettleacctEntity;
import com.qihang.buss.sc.baseinfo.service.TScSettleacctServiceI;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
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
 * @Description: 结算账户
 * @author onlineGenerator
 * @date 2016-07-05 15:48:30
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScSettleacctController")
public class TScSettleacctController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScSettleacctController.class);

	@Autowired
	private TScSettleacctServiceI tScSettleacctService;
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
	 * 结算账户列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScSettleacct")
	public ModelAndView tScSettleacct(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScSettleacctList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScSettleacctEntity tScSettleacct,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScSettleacctEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScSettleacct, request.getParameterMap());
		try{
		//自定义追加查询条件
			String ids = request.getParameter("ids");
			if(StringUtils.isNotEmpty(ids)){
				List<String> idList = new ArrayList<String>();
				for(String id : ids.split(",")){
					idList.add(id);
				}
				cq.add(Restrictions.not(Restrictions.in("id",idList.toArray())));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScSettleacctService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除结算账户
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScSettleacctEntity tScSettleacct, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScSettleacct = systemService.getEntity(TScSettleacctEntity.class, tScSettleacct.getId());
		message = "结算账户删除成功";
		try{
			tScSettleacctService.delete(tScSettleacct);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "结算账户删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除结算账户
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "结算账户删除成功";
		try{
			for(String id:ids.split(",")){
				TScSettleacctEntity tScSettleacct = systemService.getEntity(TScSettleacctEntity.class, 
				id
				);
				tScSettleacctService.delete(tScSettleacct);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "结算账户删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加结算账户
	 * 
	 * @param tScSettleacct
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScSettleacctEntity tScSettleacct, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScSettleacct.setDisabled(0);
		message = "结算账户添加成功";
		try{
			if(StringUtil.isNotEmpty(tScSettleacct.getName())) {
				String hql = "from TScSettleacctEntity where 1 = 1 AND name = ? ";
				List<TScSettleacctEntity> tScStockEntityList = systemService.findHql(hql, tScSettleacct.getName());
				if ( tScStockEntityList.size() >0) {
					message = "名称【"+ tScSettleacct.getName()+"】已存在";
					j.setSuccess(false);
					j.setMsg(message);
					return j;
				}
			}
			tScSettleacctService.save(tScSettleacct);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "结算账户添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新结算账户
	 * 
	 * @param tScSettleacct
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScSettleacctEntity tScSettleacct, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "结算账户更新成功";
		TScSettleacctEntity t = tScSettleacctService.get(TScSettleacctEntity.class, tScSettleacct.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tScSettleacct, t);
			tScSettleacctService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "结算账户更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 结算账户新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScSettleacctEntity tScSettleacct, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScSettleacct.getId())) {
			tScSettleacct = tScSettleacctService.getEntity(TScSettleacctEntity.class, tScSettleacct.getId());
			req.setAttribute("tScSettleacctPage", tScSettleacct);
		}
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScSettleacct-add");
	}
	/**
	 * 结算账户编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScSettleacctEntity tScSettleacct, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScSettleacct.getId())) {
			tScSettleacct = tScSettleacctService.getEntity(TScSettleacctEntity.class, tScSettleacct.getId());
			req.setAttribute("tScSettleacctPage", tScSettleacct);
		}
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScSettleacct-update");
	}
	/**
	 * 复制
	 *
	 * @return
	 */
	@RequestMapping(params = "goCopy")
	public ModelAndView goCopy(TScSettleacctEntity tScSettleacct, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScSettleacct.getId())) {
			tScSettleacct = tScSettleacctService.getEntity(TScSettleacctEntity.class, tScSettleacct.getId());
			req.setAttribute("tScSettleacctPage", tScSettleacct);
		}
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScSettleacct-add");
	}
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tScSettleacctController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScSettleacctEntity tScSettleacct,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScSettleacctEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScSettleacct, request.getParameterMap());
		List<TScSettleacctEntity> tScSettleaccts = this.tScSettleacctService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"结算账户");
		modelMap.put(NormalExcelConstants.CLASS,TScSettleacctEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("结算账户列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, tScSettleaccts);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScSettleacctEntity tScSettleacct,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "结算账户");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScSettleacctEntity.class);
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
				List<TScSettleacctEntity> listTScSettleacctEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TScSettleacctEntity.class,params);
				for (TScSettleacctEntity tScSettleacct : listTScSettleacctEntitys) {
					tScSettleacctService.save(tScSettleacct);
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
	 * @param tScSettleacctEntity
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "doDisable")
	@ResponseBody
	public AjaxJson doDisable(TScSettleacctEntity tScSettleacctEntity, HttpServletRequest req){
		AjaxJson j = new AjaxJson();
		String message = "操作成功";
		try{
			for(String id:tScSettleacctEntity.getId().split(",")){
				TScSettleacctEntity scStockEntity = systemService.getEntity(TScSettleacctEntity.class, id);
				if(tScSettleacctEntity.getDisabled() == Globals.ENABLE_CODE){//启用
					if(tScSettleacctEntity.getDisabled() == scStockEntity.getDisabled()){
						message="已启用";
					}else{
						scStockEntity.setDisabled(Globals.ENABLE_CODE);
					}
				}else{//禁用
					if(tScSettleacctEntity.getDisabled() == scStockEntity.getDisabled()) {
						message="已禁用";
					}else{
						scStockEntity.setDisabled(Globals.DISABLED_CODE);
					}
				}
				tScSettleacctService.saveOrUpdate(scStockEntity);
			}
		}catch (Exception e){
			e.printStackTrace();
			message = "操作失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "goSelectDialog")
	public ModelAndView goSelectDialog(TScSettleacctEntity tScIcitemEntity, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScIcitemEntity.getName())) {
			req.setAttribute("name", tScIcitemEntity.getName());
		}
		String isMore = req.getParameter("isMore");
		if(StringUtils.isNotEmpty(isMore)){
			req.setAttribute("isMore",true);
		}else{
			req.setAttribute("isMore",false);
		}
		String ids = req.getParameter("ids");
		req.setAttribute("ids",ids);
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScSettleacctSelect");
	}
}
