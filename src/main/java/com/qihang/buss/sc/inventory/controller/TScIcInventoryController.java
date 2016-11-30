package com.qihang.buss.sc.inventory.controller;
import com.qihang.buss.sc.baseinfo.entity.TScIcitemEntity;
import com.qihang.buss.sc.baseinfo.entity.TScStockEntity;
import com.qihang.buss.sc.baseinfo.service.TScItemTypeServiceI;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryBatchnoEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryBatchnoViewEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryViewEntity;
import com.qihang.buss.sc.inventory.service.TScIcInventoryServiceI;

import java.util.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qihang.winter.web.system.pojo.base.TSConfig;
import org.apache.commons.lang3.StringUtils;
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
import com.qihang.winter.core.util.ExceptionUtil;



/**   
 * @Title: Controller
 * @Description: 即时库存基本表
 * @author onlineGenerator
 * @date 2016-07-23 09:30:20
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScIcInventoryController")
public class TScIcInventoryController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScIcInventoryController.class);

	@Autowired
	private TScIcInventoryServiceI tScIcInventoryService;
	@Autowired
	private SystemService systemService;

	@Autowired
	private TScItemTypeServiceI tScItemTypeService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 即时库存基本表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScIcInventory")
	public ModelAndView tScIcInventory(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/inventory/tScIcInventoryList");
	}

	@RequestMapping(params = "batchNoInfo")
	public ModelAndView batchNoInfo(HttpServletRequest request) {
		String itemId = request.getParameter("itemId");
		request.setAttribute("itemId",itemId);
		String stockId = request.getParameter("stockId");
		request.setAttribute("stockId",stockId);
		return new ModelAndView("com/qihang/buss/sc/inventory/tScIcInventoryBatchnoList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScIcInventoryViewEntity tScIcInventory,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		dataGrid.setFooter("qty,smallQty,basicQty,secQty,costAmount");
		String stockId = tScIcInventory.getStockId();
		String itemId = tScIcInventory.getItemId();
		Integer isZero = (tScIcInventory.getIsZero() == null ? 0 : tScIcInventory.getIsZero());
		tScIcInventory.setIsZero(null);
		//仓库
		List<String> stockIdList = new ArrayList<String>();
		if(StringUtils.isNotEmpty(stockId)){
			List<String> list = new ArrayList<String>();
			TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart depart = systemService.getParentSonInfo(sonInfo);
			list =tScItemTypeService.getParentIdFromTree("07", String.valueOf(stockId),depart.getId());
			if(list.size() > 0){
				String idStr = "";
				for(String id : list){
					idStr+="'"+id+"',";
				}
				idStr = idStr.substring(0,idStr.length()-1);
				List<TScStockEntity> stockEntityList = systemService.findHql("from TScStockEntity where parentID in ("+idStr+")");
				for(TScStockEntity stockEntity : stockEntityList){
					stockIdList.add(stockEntity.getId());
				}
			}else{
				stockIdList.add(stockId);
			}
			tScIcInventory.setStockId(null);
		}
		//商品
		List<String> itemIdList = new ArrayList<String>();
		if(StringUtils.isNotEmpty(itemId)){
			List<String> list = new ArrayList<String>();
			TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart depart = systemService.getParentSonInfo(sonInfo);
			list =tScItemTypeService.getParentIdFromTree("04", String.valueOf(itemId),depart.getId());
			if(list.size() > 0){
				String idStr = "";
				for(String id : list){
					idStr+="'"+id+"',";
				}
				idStr = idStr.substring(0,idStr.length()-1);
				List<TScIcitemEntity> itemEntityList = systemService.findHql("from TScIcitemEntity where parentID in ("+idStr+")");
				for(TScIcitemEntity itemEntity : itemEntityList){
					itemIdList.add(itemEntity.getId());
				}
			}else{
				itemIdList.add(itemId);
			}
			tScIcInventory.setItemId(null);
		}
		CriteriaQuery cq = new CriteriaQuery(TScIcInventoryViewEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScIcInventory, request.getParameterMap());
		try{
		//自定义追加查询条件
			if(stockIdList.size() > 0){
				cq.in("stockId", stockIdList.toArray());
			}
			if(itemIdList.size() > 0){
				cq.in("itemId", itemIdList.toArray());
			}
			String query_date_begin = request.getParameter("createDate_begin");
			String query_date_end = request.getParameter("createDate_end");
			if(StringUtil.isNotEmpty(query_date_begin)){
				cq.ge("createDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_begin));
			}
			if(StringUtil.isNotEmpty(query_date_end)){
				cq.le("createDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_end));
			}
			if(isZero == 0){
				cq.gt("basicQty",0.0);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScIcInventoryService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	@RequestMapping(params = "datagridBatchNo")
	public void datagridBatchNo(TScIcInventoryBatchnoViewEntity tScIcInventory,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScIcInventoryBatchnoViewEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScIcInventory, request.getParameterMap());
		try{
			//自定义追加查询条件
			String query_date_begin = request.getParameter("createDate_begin");
			String query_date_end = request.getParameter("createDate_end");
			if(StringUtil.isNotEmpty(query_date_begin)){
				cq.ge("createDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_begin));
			}
			if(StringUtil.isNotEmpty(query_date_end)){
				cq.le("createDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_end));
			}
			String isWarm = request.getParameter("isWarm");
			if(StringUtil.isNotEmpty(isWarm)){
				Date beginDate = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String invWarmDate = "0";
				List<TSConfig> tsConfigList = systemService.findByProperty(TSConfig.class,"code","CFG_SHELFLIFEEARWAR_DAYS");//保质期预警天数
				if(tsConfigList.size()>0){
					invWarmDate = tsConfigList.get(0).getContents();
				}
				Calendar date = Calendar.getInstance();
				date = Calendar.getInstance();
				date.setTime(beginDate);
				date.set(Calendar.DATE, date.get(Calendar.DATE) + Integer.parseInt(invWarmDate));
				Date endDate = sdf.parse(sdf.format(date.getTime()));
				cq.le("periodDate",endDate);
				cq.gt("basicQty",0.0);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScIcInventoryService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除即时库存基本表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScIcInventoryEntity tScIcInventory, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScIcInventory = systemService.getEntity(TScIcInventoryEntity.class, tScIcInventory.getId());
		message = "即时库存基本表删除成功";
		try{
			tScIcInventoryService.delete(tScIcInventory);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "即时库存基本表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除即时库存基本表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "即时库存基本表删除成功";
		try{
			for(String id:ids.split(",")){
				TScIcInventoryEntity tScIcInventory = systemService.getEntity(TScIcInventoryEntity.class, 
				id
				);
				tScIcInventoryService.delete(tScIcInventory);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "即时库存基本表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加即时库存基本表
	 * 
	 * @param tScIcInventory
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScIcInventoryEntity tScIcInventory, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "即时库存基本表添加成功";
		try{
			tScIcInventoryService.save(tScIcInventory);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "即时库存基本表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新即时库存基本表
	 * 
	 * @param tScIcInventory
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScIcInventoryEntity tScIcInventory, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "即时库存基本表更新成功";
		TScIcInventoryEntity t = tScIcInventoryService.get(TScIcInventoryEntity.class, tScIcInventory.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tScIcInventory, t);
			tScIcInventoryService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "即时库存基本表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 即时库存基本表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScIcInventoryEntity tScIcInventory, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScIcInventory.getId())) {
			tScIcInventory = tScIcInventoryService.getEntity(TScIcInventoryEntity.class, tScIcInventory.getId());
			req.setAttribute("tScIcInventoryPage", tScIcInventory);
		}
		return new ModelAndView("com/qihang/buss/sc/inventory/tScIcInventory-add");
	}
	/**
	 * 即时库存基本表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScIcInventoryEntity tScIcInventory, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScIcInventory.getId())) {
			tScIcInventory = tScIcInventoryService.getEntity(TScIcInventoryEntity.class, tScIcInventory.getId());
			req.setAttribute("tScIcInventoryPage", tScIcInventory);
		}
		return new ModelAndView("com/qihang/buss/sc/inventory/tScIcInventory-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tScIcInventoryController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScIcInventoryViewEntity tScIcInventory,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScIcInventoryViewEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScIcInventory, request.getParameterMap());
		List<TScIcInventoryViewEntity> tScIcInventorys = this.tScIcInventoryService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"即时库存基本表");
		modelMap.put(NormalExcelConstants.CLASS,TScIcInventoryViewEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("即时库存基本表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, tScIcInventorys);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScIcInventoryEntity tScIcInventory,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "即时库存基本表");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScIcInventoryEntity.class);
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
				List<TScIcInventoryEntity> listTScIcInventoryEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TScIcInventoryEntity.class,params);
				for (TScIcInventoryEntity tScIcInventory : listTScIcInventoryEntitys) {
					tScIcInventoryService.save(tScIcInventory);
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

	@RequestMapping(params = "deleteInfo")
	@ResponseBody
	public AjaxJson deleteInfo(){
		AjaxJson j = tScIcInventoryService.deleteInfo();
		return  j;
	}

	/**
	 * 校验商品数量是不是超出库存
	 * 数据格式：商品id#商品名称#仓库id#数量#批号，
	 * @param checkValue
	 * @return
	 */
	@RequestMapping(params = "checkIsOverInv")
	@ResponseBody
	public AjaxJson checkIsOverInv(String checkValue){
		AjaxJson j = tScIcInventoryService.checkIsOverInv(checkValue);
		return j;
	}

	@RequestMapping(params = "warmInventory")
	@ResponseBody
	public ModelAndView warmInventory(HttpServletRequest request){
		return new ModelAndView("com/qihang/buss/sc/inventory/tScIcInventoryBatchnoList_Warm");
	}
}
