package com.qihang.buss.sc.oa.controller;


import com.qihang.buss.sc.oa.entity.TScMessageFileEntity;
import com.qihang.buss.sc.oa.entity.TScMessageReceiveEntity;
import com.qihang.buss.sc.oa.service.TScMessageReceiveServiceI;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @Title: Controller
 * @Description: 收信箱
 * @author onlineGenerator
 * @date 2015-12-15 14:38:40
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScMessageReceiveController")
public class TScMessageReceiveController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScMessageReceiveController.class);

	@Autowired
	private TScMessageReceiveServiceI tScMessageReceiveService;
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
	 * 收信箱列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScMessageReceive")
	public ModelAndView tScMessageReceive(HttpServletRequest request) {
        String status = request.getParameter("readStatus");
        ModelAndView view = new ModelAndView("com/qihang/buss/sc/oa/tScMessageReceiveList");
        view.addObject("readStatus",status);
		return view;
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScMessageReceiveEntity tScMessageReceive,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScMessageReceiveEntity.class, dataGrid);
		String userId = ResourceUtil.getSessionUserName().getId();
		tScMessageReceive.setUserId(userId);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScMessageReceive, request.getParameterMap());
		try{
		//自定义追加查询条件
		String query_createDate_begin = request.getParameter("createDate_begin");
		String query_createDate_end = request.getParameter("createDate_end");
		if(StringUtil.isNotEmpty(query_createDate_begin)){
			cq.ge("createDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_createDate_begin));
		}
		if(StringUtil.isNotEmpty(query_createDate_end)){
			cq.le("createDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_createDate_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScMessageReceiveService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除收信箱
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScMessageReceiveEntity tScMessageReceive, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScMessageReceive = systemService.getEntity(TScMessageReceiveEntity.class, tScMessageReceive.getId());
		message = "收信箱删除成功";
		try{
			tScMessageReceiveService.delete(tScMessageReceive);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "收信箱删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除收信箱
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "收信箱删除成功";
		try{
			for(String id:ids.split(",")){
				TScMessageReceiveEntity tScMessageReceive = systemService.getEntity(TScMessageReceiveEntity.class,
				id
				);
				tScMessageReceiveService.delete(tScMessageReceive);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "收信箱删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加收信箱
	 * 
	 * @param tScMessageReceive
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScMessageReceiveEntity tScMessageReceive, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "收信箱添加成功";
		try{
			tScMessageReceiveService.save(tScMessageReceive);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "收信箱添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新收信箱
	 * 
	 * @param tScMessageReceive
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScMessageReceiveEntity tScMessageReceive, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "收信箱更新成功";
		TScMessageReceiveEntity t = tScMessageReceiveService.get(TScMessageReceiveEntity.class, tScMessageReceive.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tScMessageReceive, t);
			tScMessageReceiveService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "收信箱更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 收信箱新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScMessageReceiveEntity tScMessageReceive, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScMessageReceive.getId())) {
			tScMessageReceive = tScMessageReceiveService.getEntity(TScMessageReceiveEntity.class, tScMessageReceive.getId());
			req.setAttribute("tScMessageReceivePage", tScMessageReceive);
		}
		return new ModelAndView("com/qihang/buss/sc/oa/tScMessageReceive-add");
	}
	/**
	 * 收信箱编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScMessageReceiveEntity tScMessageReceive, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScMessageReceive.getId())) {
			tScMessageReceive = tScMessageReceiveService.getEntity(TScMessageReceiveEntity.class, tScMessageReceive.getId());
			req.setAttribute("tScMessageReceivePage", tScMessageReceive);
		}
		if(tScMessageReceive.getReadStatus() == 0){
			tScMessageReceiveService.updateBySqlString("UPDATE T_SC_MESSAGE_RECEIVE SET read_status=1," +
							"read_date=DATE_FORMAT(NOW(),'%Y-%m-%d') WHERE id ='" + tScMessageReceive.getId() + "'");
		}
		return new ModelAndView("com/qihang/buss/sc/oa/tScMessageReceive-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tScMessageReceiveController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScMessageReceiveEntity tScMessageReceive,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScMessageReceiveEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScMessageReceive, request.getParameterMap());
		List<TScMessageReceiveEntity> tScMessageReceives = this.tScMessageReceiveService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"收信箱");
		modelMap.put(NormalExcelConstants.CLASS,TScMessageReceiveEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("收信箱列表", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, tScMessageReceives);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScMessageReceiveEntity tScMessageReceive,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "收信箱");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScMessageReceiveEntity.class);
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
				List<TScMessageReceiveEntity> listTScMessageReceiveEntitys = ExcelImportUtil.importExcel(file.getInputStream(), TScMessageReceiveEntity.class, params);
				for (TScMessageReceiveEntity tScMessageReceive : listTScMessageReceiveEntitys) {
					tScMessageReceiveService.save(tScMessageReceive);
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

	//App 接口 start zyy 2015-01-15
	/**
	 * easyui AJAX请求数据 app
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "getAppData")
	@ResponseBody
	public DataGrid getAppData(TScMessageReceiveEntity tScMessageReceive,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScMessageReceiveEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScMessageReceive, request.getParameterMap());
		try{
			//自定义追加查询条件
			String query_createDate_begin = request.getParameter("createDate_begin");
			String query_createDate_end = request.getParameter("createDate_end");
			if(StringUtil.isNotEmpty(query_createDate_begin)){
				cq.ge("createDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_createDate_begin));
			}
			if(StringUtil.isNotEmpty(query_createDate_end)){
				cq.le("createDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_createDate_end));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScMessageReceiveService.getDataGridReturn(cq, true);
		//TagUtil.datagrid(response, dataGrid);
		return dataGrid;
	}

	/**
	 * app批量删除收信箱
	 *
	 * @return
	 */
	@RequestMapping(params = "delAppData")
	@ResponseBody
	public AjaxJson delAppData(String ids,String userId,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "收信箱删除成功";
		try{
			for(String id:ids.split(",")){
				TScMessageReceiveEntity tScMessageReceive = systemService.getEntity(TScMessageReceiveEntity.class, id);
				if(null != tScMessageReceive){
					if(null != userId && userId.equals(tScMessageReceive.getUserId())){
						tScMessageReceiveService.delete(tScMessageReceive);
						j.setSuccess(true);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "收信箱删除失败";
			j.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 收信箱编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goView")
	@ResponseBody
	public AjaxJson goView(TScMessageReceiveEntity tScMessageReceive, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(tScMessageReceive.getId())) {
			tScMessageReceive = tScMessageReceiveService.getEntity(TScMessageReceiveEntity.class, tScMessageReceive.getId());
			List<TScMessageFileEntity> list = systemService.findByProperty(TScMessageFileEntity.class,"messageId",tScMessageReceive.getMessageId());
			if(null != list && list.size() > 0){
				List<TScMessageFileEntity> fList = new ArrayList<TScMessageFileEntity>();
				for (TScMessageFileEntity fileEntity : list) {
					if(null != fileEntity.getMessageFile() && !"".equals(fileEntity.getMessageFile())){
						String sql = "select attachmenttitle from t_s_attachment where realpath = '"+fileEntity.getMessageFile()+"'";
						List<String> sqlList = systemService.findListbySql(sql);
						if(null != sqlList && sqlList.size() > 0){
							fileEntity.setFileName(sqlList.get(0));
						}
						fList.add(fileEntity);
					}
				}
				if(fList.size() > 0){
					tScMessageReceive.setFileList(fList);
				}
			}
			if(tScMessageReceive.getReadStatus() == 0){
				tScMessageReceive.setReadStatus(1);
				tScMessageReceive.setReadDate(new Date());
				tScMessageReceiveService.saveOrUpdate(tScMessageReceive);
			}
			j.setObj(tScMessageReceive);
		}
		return j;
	}
}
