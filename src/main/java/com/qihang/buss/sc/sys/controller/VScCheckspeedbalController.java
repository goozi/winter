package com.qihang.buss.sc.sys.controller;
import com.qihang.buss.sc.sys.entity.VScCheckspeedbalEntity;
import com.qihang.buss.sc.sys.service.VScCheckspeedbalServiceI;
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
 * @Description: 结账前检查当期月结库存统计列表
 * @author onlineGenerator
 * @date 2016-09-14 13:59:10
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/vScCheckspeedbalController")
public class VScCheckspeedbalController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VScCheckspeedbalController.class);

	@Autowired
	private VScCheckspeedbalServiceI vScCheckspeedbalService;
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
	 * 结账前检查当期月结库存统计列表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "vScCheckspeedbal")
	public ModelAndView vScCheckspeedbal(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/sys/vScCheckspeedbalList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(VScCheckspeedbalEntity vScCheckspeedbal,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(VScCheckspeedbalEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, vScCheckspeedbal, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.vScCheckspeedbalService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 本期负库存商品信息列表
	 *
	 * @return
	 */
	@RequestMapping(params = "vScCheckstageentryList")
	public ModelAndView vScCheckstageentryList(VScCheckspeedbalEntity vScCheckspeedbal, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object date = vScCheckspeedbal.getDate();
		//===================================================================================
		//查询-本期负库存商品信息列表
		String hql0 = "from VScCheckstageEntity where 1 = 1 AND date = ? AND qty<0 ";//todo:仅对当月负库存的数量进行累加上期期未的数量
		try{
			List<VScCheckspeedbalEntity> vScCheckspeedbalEntityList = systemService.findHql(hql0,date);
			//todo:需要查询存货结账表的上一期的期未的所有商品数量
			for(VScCheckspeedbalEntity vScCheckspeedbalEntity2:vScCheckspeedbalEntityList){
				//todo:将商品数量与上一期的期未的所有商品数量进行累加
				//累加后只对最终数量小于0进行显示，即数量大于等于0的从列表中筛选掉（不显示）
				if (vScCheckspeedbalEntity2.getQty()>=0){
					vScCheckspeedbalEntityList.remove(vScCheckspeedbalEntity2);
				}
			}
			req.setAttribute("vScCheckspeedbalEntityList", vScCheckspeedbalEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		String load = req.getParameter("load");
		req.setAttribute("load",load);
		return new ModelAndView("com/qihang/buss/sc/sys/vScCheckspeedbalentryList");
	}

	/**
	 * 删除结账前检查当期月结库存统计列表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(VScCheckspeedbalEntity vScCheckspeedbal, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		vScCheckspeedbal = systemService.getEntity(VScCheckspeedbalEntity.class, vScCheckspeedbal.getId());
		message = "结账前检查当期月结库存统计列表删除成功";
		try{
			vScCheckspeedbalService.delete(vScCheckspeedbal);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "结账前检查当期月结库存统计列表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除结账前检查当期月结库存统计列表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "结账前检查当期月结库存统计列表删除成功";
		try{
			for(String id:ids.split(",")){
				VScCheckspeedbalEntity vScCheckspeedbal = systemService.getEntity(VScCheckspeedbalEntity.class, 
				id
				);
				vScCheckspeedbalService.delete(vScCheckspeedbal);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "结账前检查当期月结库存统计列表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加结账前检查当期月结库存统计列表
	 * 
	 * @param vScCheckspeedbal
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(VScCheckspeedbalEntity vScCheckspeedbal, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "结账前检查当期月结库存统计列表添加成功";
		try{
			vScCheckspeedbalService.save(vScCheckspeedbal);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "结账前检查当期月结库存统计列表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新结账前检查当期月结库存统计列表
	 * 
	 * @param vScCheckspeedbal
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(VScCheckspeedbalEntity vScCheckspeedbal, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "结账前检查当期月结库存统计列表更新成功";
		VScCheckspeedbalEntity t = vScCheckspeedbalService.get(VScCheckspeedbalEntity.class, vScCheckspeedbal.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(vScCheckspeedbal, t);
			vScCheckspeedbalService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "结账前检查当期月结库存统计列表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 结账前检查当期月结库存统计列表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(VScCheckspeedbalEntity vScCheckspeedbal, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(vScCheckspeedbal.getId())) {
			vScCheckspeedbal = vScCheckspeedbalService.getEntity(VScCheckspeedbalEntity.class, vScCheckspeedbal.getId());
			req.setAttribute("vScCheckspeedbalPage", vScCheckspeedbal);
		}
		return new ModelAndView("com/qihang/buss/sc/sys/vScCheckspeedbal-add");
	}
	/**
	 * 结账前检查当期月结库存统计列表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(VScCheckspeedbalEntity vScCheckspeedbal, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(vScCheckspeedbal.getId())) {
			vScCheckspeedbal = vScCheckspeedbalService.getEntity(VScCheckspeedbalEntity.class, vScCheckspeedbal.getId());
			req.setAttribute("vScCheckspeedbalPage", vScCheckspeedbal);
		}
		return new ModelAndView("com/qihang/buss/sc/sys/vScCheckspeedbal-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","vScCheckspeedbalController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(VScCheckspeedbalEntity vScCheckspeedbal,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(VScCheckspeedbalEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, vScCheckspeedbal, request.getParameterMap());
		List<VScCheckspeedbalEntity> vScCheckspeedbals = this.vScCheckspeedbalService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"结账前检查当期月结库存统计列表");
		modelMap.put(NormalExcelConstants.CLASS,VScCheckspeedbalEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("结账前检查当期月结库存统计列表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,vScCheckspeedbals);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(VScCheckspeedbalEntity vScCheckspeedbal,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "结账前检查当期月结库存统计列表");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,VScCheckspeedbalEntity.class);
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
				List<VScCheckspeedbalEntity> listVScCheckspeedbalEntitys = ExcelImportUtil.importExcel(file.getInputStream(),VScCheckspeedbalEntity.class,params);
				for (VScCheckspeedbalEntity vScCheckspeedbal : listVScCheckspeedbalEntitys) {
					vScCheckspeedbalService.save(vScCheckspeedbal);
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
