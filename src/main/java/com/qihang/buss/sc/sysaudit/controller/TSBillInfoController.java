package com.qihang.buss.sc.sysaudit.controller;
import com.qihang.buss.sc.sysaudit.entity.TSAuditEntity;
import com.qihang.buss.sc.sysaudit.entity.TSBillInfoEntity;
import com.qihang.buss.sc.sysaudit.service.TSBillInfoServiceI;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qihang.winter.core.common.model.json.TreeGrid;
import com.qihang.winter.core.util.*;
import com.qihang.winter.tag.vo.datatable.SortDirection;
import com.qihang.winter.tag.vo.easyui.TreeGridModel;
import com.qihang.winter.web.system.pojo.base.TSFunction;
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
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.web.system.service.SystemService;

import com.qihang.winter.poi.excel.ExcelImportUtil;
import com.qihang.winter.poi.excel.entity.ExportParams;
import com.qihang.winter.poi.excel.entity.ImportParams;
import com.qihang.winter.poi.excel.entity.TemplateExportParams;
import com.qihang.winter.poi.excel.entity.vo.NormalExcelConstants;
import com.qihang.winter.poi.excel.entity.vo.TemplateExcelConstants;

import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


/**
 * @Title: Controller
 * @Description: 单据类型设置
 * @author onlineGenerator
 * @date 2016-06-21 16:00:41
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tSBillInfoController")
public class TSBillInfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TSBillInfoController.class);

	@Autowired
	private TSBillInfoServiceI tSBillInfoService;
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
	 * 单据类型设置列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tSBillInfo")
	public ModelAndView tSBillInfo(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sysaudit/tSBillInfoList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TSBillInfoEntity tSBillInfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSBillInfoEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSBillInfo, request.getParameterMap());
		try{
			//自定义追加查询条件
			cq.eq("pid",null);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tSBillInfoService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
//		CriteriaQuery cq = new CriteriaQuery(TSBillInfoEntity.class, dataGrid);
//		//查询条件组装器
//		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSBillInfo, request.getParameterMap());
//		try{
//		//自定义追加查询条件
//		}catch (Exception e) {
//			throw new BusinessException(e.getMessage());
//		}
//		cq.add();
//		//List<TSBillInfoEntity> functionList = systemService.getListByCriteriaQuery(cq, false);
//		this.tSBillInfoService.getDataGridReturn(cq, true);
////		TagUtil.datagrid(response, dataGrid);
//
//		List<TSBillInfoEntity> billInfoList = systemService.getListByCriteriaQuery(cq, false);
//		Collections.sort(billInfoList, new NumberComparator());
//		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
//		TreeGridModel treeGridModel = new TreeGridModel();
//		treeGridModel.setIcon("");
//		treeGridModel.setTextField("billName");
//		treeGridModel.setParentText("TSBillInfoEntity_billName");
//		treeGridModel.setParentId("TSBillInfoEntity_id");
//		treeGridModel.setSrc("");
//		treeGridModel.setIdField("id");
//		treeGridModel.setChildList("TSBillInfoEntitys");
//		// 添加排序字段
//		treeGridModel.setOrder("billId");
//
//		treeGridModel.setFunctionType("");
//
//		treeGrids = systemService.treegrid(billInfoList, treeGridModel);
//
//		MutiLangUtil.setMutiTree(treeGrids);
//		return treeGrids;
	}

	/**
	 * 删除单据类型设置
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TSBillInfoEntity tSBillInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tSBillInfo = systemService.getEntity(TSBillInfoEntity.class, tSBillInfo.getId());
		message = "单据类型设置删除成功";
		try{
			tSBillInfoService.delete(tSBillInfo);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "单据类型设置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除单据类型设置
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "单据类型设置删除成功";
		try{
			for(String id:ids.split(",")){
				TSBillInfoEntity tSBillInfo = systemService.getEntity(TSBillInfoEntity.class, 
				id
				);
				tSBillInfoService.delete(tSBillInfo);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "单据类型设置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加单据类型设置
	 * 
	 * @param tSBillInfo
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TSBillInfoEntity tSBillInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "单据类型设置添加成功";
		try{
			if(null == tSBillInfo.getIsEdit()){
				tSBillInfo.setIsEdit(0);
			}
			if(null == tSBillInfo.getIsOffOn()){
				tSBillInfo.setIsOffOn(0);
			}
			tSBillInfoService.save(tSBillInfo);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "单据类型设置添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新单据类型设置
	 * 
	 * @param tSBillInfo
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TSBillInfoEntity tSBillInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "单据类型设置更新成功";
		TSBillInfoEntity t = tSBillInfoService.get(TSBillInfoEntity.class, tSBillInfo.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tSBillInfo, t);
			tSBillInfoService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "单据类型设置更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 单据类型设置新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TSBillInfoEntity tSBillInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSBillInfo.getId())) {
			tSBillInfo = tSBillInfoService.getEntity(TSBillInfoEntity.class, tSBillInfo.getId());
			req.setAttribute("tSBillInfoPage", tSBillInfo);
		}
		return new ModelAndView("com/qihang/buss/sysaudit/tSBillInfo-add");
	}
	/**
	 * 单据类型设置编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TSBillInfoEntity tSBillInfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSBillInfo.getId())) {
			tSBillInfo = tSBillInfoService.getEntity(TSBillInfoEntity.class, tSBillInfo.getId());
			req.setAttribute("tSBillInfoPage", tSBillInfo);
		}
		return new ModelAndView("com/qihang/buss/sysaudit/tSBillInfo-update");
	}

	/**
	 * 根据id得到json对象
	 * @param tSBillInfo
	 * @param request
     * @return
     */
	@RequestMapping(params = "goUpdateForJson")
	@ResponseBody
	public AjaxJson goUpdateForJson(TSBillInfoEntity tSBillInfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "单据类型设置更新成功";
		Map<String, Object> map = new HashMap();
		if(StringUtil.isNotEmpty(tSBillInfo.getId())){
			TSBillInfoEntity t = tSBillInfoService.get(TSBillInfoEntity.class, tSBillInfo.getId());
			String pid = t.getTSBillInfoEntity()==null?"":t.getTSBillInfoEntity().getId();
			map.put("pid", pid);
		}
		map.put("userName",ResourceUtil.getSessionUserName().getUserName());
		//map.put("t",t);
		j.setObj(map);
		j.setMsg(message);
		return j;
	}

	/**
	 * 判断 当前登录用户是否为程序员
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "isProgrammer")
	@ResponseBody
	public AjaxJson isProgrammer(HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		Map<String, Object> map = new HashMap();
		String isProgrammer = ResourceUtil.getSessionUserName().getUserName().equalsIgnoreCase("programmer")?"true":"false";
		map.put("isProgrammer", isProgrammer);
		//map.put("t",t);
		j.setObj(map);
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tSBillInfoController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TSBillInfoEntity tSBillInfo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TSBillInfoEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSBillInfo, request.getParameterMap());
		List<TSBillInfoEntity> tSBillInfos = this.tSBillInfoService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"单据类型设置");
		modelMap.put(NormalExcelConstants.CLASS,TSBillInfoEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("单据类型设置列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, tSBillInfos);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TSBillInfoEntity tSBillInfo,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "单据类型设置");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TSBillInfoEntity.class);
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
				List<TSBillInfoEntity> listTSBillInfoEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TSBillInfoEntity.class,params);
				for (TSBillInfoEntity tSBillInfo : listTSBillInfoEntitys) {
					tSBillInfoService.save(tSBillInfo);
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

	@RequestMapping(params = "goUpdatePrice")
	public ModelAndView goUpdatePrice(TSAuditEntity tSAudit, HttpServletRequest req) {
		String infoId = req.getParameter("infoId");
		TSBillInfoEntity infoEntity = tSBillInfoService.getEntity(TSBillInfoEntity.class,infoId);
		//if (StringUtil.isNotEmpty(tSAudit.getId())) {
		req.setAttribute("id", infoEntity.getId());
		req.setAttribute("billId",infoEntity.getBillId());
		req.setAttribute("priceField",infoEntity.getPriceField());
		//}
		return new ModelAndView("com/qihang/buss/sysaudit/billPrice_update");
	}

	/**
	 * 更新单据单价
	 *
	 * @param billInfoEntity
	 * @return
	 */
	@RequestMapping(params = "doUpdatePrice")
	@ResponseBody
	public AjaxJson doUpdatePrice(TSBillInfoEntity billInfoEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			tSBillInfoService.updatePriceInfo(billInfoEntity);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新单价设置失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
}
