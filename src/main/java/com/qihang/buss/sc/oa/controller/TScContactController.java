package com.qihang.buss.sc.oa.controller;


import com.qihang.buss.sc.oa.entity.TScContactEntity;
import com.qihang.buss.sc.oa.service.TScContactServiceI;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.*;
import com.qihang.winter.poi.excel.ExcelImportUtil;
import com.qihang.winter.poi.excel.entity.ExportParams;
import com.qihang.winter.poi.excel.entity.ImportParams;
import com.qihang.winter.poi.excel.entity.TemplateExportParams;
import com.qihang.winter.poi.excel.entity.vo.NormalExcelConstants;
import com.qihang.winter.poi.excel.entity.vo.TemplateExcelConstants;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.web.system.manager.ClientManager;
import com.qihang.winter.web.system.pojo.base.Client;
import com.qihang.winter.web.system.pojo.base.TSUser;
import com.qihang.winter.web.system.service.SystemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * @Title: Controller
 * @Description: 通讯录
 * @author onlineGenerator
 * @date 2015-12-07 14:03:35
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScContactController")
public class TScContactController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScContactController.class);

	@Autowired
	private TScContactServiceI tScContactService;
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
	 * 通讯簿列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScContact")
	public ModelAndView tScContact(HttpServletRequest request,Integer type) {
		request.setAttribute("type",type);
		return new ModelAndView("com/qihang/buss/sc/oa/tScContactList");
	}

	@RequestMapping(params = "tScContactAll")
	public ModelAndView tScContactAll(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/oa/tScContactAllList");
	}
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScContactEntity tScContact,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScContactEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScContact, request.getParameterMap());
		try{
		//自定义追加查询条件
			if(null != tScContact.getType() && tScContact.getType() == 1){
				String userId = ResourceUtil.getSessionUserName().getId();
				if(null != userId){
					cq.eq("userId",userId);
				}
			}

		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScContactService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除通讯簿
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScContactEntity tScContact, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScContact = systemService.getEntity(TScContactEntity.class, tScContact.getId());
		message = "通讯簿删除成功";
		try{
			tScContactService.delete(tScContact);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "通讯簿删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除通讯簿
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "通讯簿删除成功";
		try{
			for(String id:ids.split(",")){
				TScContactEntity tScContact = systemService.getEntity(TScContactEntity.class,
				id
				);
				tScContactService.delete(tScContact);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "通讯簿删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加通讯簿
	 * 
	 * @param tScContact
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScContactEntity tScContact, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "通讯簿添加成功";
		try{
			if(null == tScContact.getUserId() || "".equals(tScContact.getUserId())){
				String userId = ResourceUtil.getSessionUserName().getId();
				if(null != userId){
					tScContact.setUserId(userId);
				}
			}
			tScContactService.save(tScContact);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			j.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			message = "通讯簿添加失败";
			j.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新通讯簿
	 * 
	 * @param tScContact
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScContactEntity tScContact, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "通讯簿更新成功";
		TScContactEntity t = tScContactService.get(TScContactEntity.class, tScContact.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tScContact, t);
			tScContactService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			message = "通讯簿更新失败";
			j.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 通讯簿新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScContactEntity tScContact, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScContact.getId())) {
			tScContact = tScContactService.getEntity(TScContactEntity.class, tScContact.getId());
			req.setAttribute("tScContactPage", tScContact);
		}
		req.setAttribute("type", tScContact.getType());
		return new ModelAndView("com/qihang/buss/sc/oa/tScContact-add");
	}
	/**
	 * 通讯簿编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScContactEntity tScContact, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScContact.getId())) {
			tScContact = tScContactService.getEntity(TScContactEntity.class, tScContact.getId());
			req.setAttribute("tScContactPage", tScContact);
		}
		return new ModelAndView("com/qihang/buss/sc/oa/tScContact-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "tScContactController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScContactEntity tScContact,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScContactEntity.class, dataGrid);
		String str = "公共";
		if(null != tScContact.getType() && tScContact.getType() == 1){
			str = "个人";
			String userId = ResourceUtil.getSessionUserName().getId();
			if(null != userId){
				cq.eq("userId",userId);
			}
		}
		cq.add();
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScContact, request.getParameterMap());
		List<TScContactEntity> tScContacts = this.tScContactService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,str+"通讯簿");
		modelMap.put(NormalExcelConstants.CLASS,TScContactEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams(str+"通讯簿", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, tScContacts);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScContactEntity tScContact,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "通讯簿");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScContactEntity.class);
		modelMap.put(TemplateExcelConstants.LIST_DATA, null);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		HttpSession session = ContextHolderUtils.getSession();
		Client client = ClientManager.getInstance().getClient(session.getId());
		if(client == null){
			client = ClientManager.getInstance().getClient(
							request.getParameter("sessionId"));
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<TScContactEntity> listTScContactEntitys = ExcelImportUtil.importExcel(file.getInputStream(), TScContactEntity.class, params);
				for (TScContactEntity tScContact : listTScContactEntitys) {
					tScContact.setUserId(client.getUser().getId());
					tScContactService.save(tScContact);
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
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "getAppData")
	@ResponseBody
	public DataGrid getAppData(TScContactEntity tScContact,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScContactEntity.class, dataGrid);
		String userId = null;
		if(null != tScContact.getUserId() && tScContact.getUserId() != ""){
			userId = tScContact.getUserId();
		}
		tScContact.setUserId(null);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScContact, request.getParameterMap());
		try{
			//自定义追加查询条件
			if(null != tScContact.getType() && tScContact.getType() == 1){
					cq.eq("userId",userId);
			}

		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScContactService.getDataGridReturn(cq, true);
		return dataGrid;
	}

	/**
	 * 通讯簿编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAppUpdate")
	@ResponseBody
	public AjaxJson goAppUpdate(TScContactEntity tScContact, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(tScContact.getId())) {
			tScContact = tScContactService.getEntity(TScContactEntity.class, tScContact.getId());
			if(null != tScContact){
				j.setObj(tScContact);
			}else {
				j.setSuccess(false);
			}
		}
		return j;
	}

	/**
	 * APP批量删除通讯簿
	 *
	 * @return
	 */
	@RequestMapping(params = "delAppData")
	@ResponseBody
	public AjaxJson delAppData(String ids,String userId,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		try{
			for(String id:ids.split(",")){
				TScContactEntity tScContact = systemService.getEntity(TScContactEntity.class, id);
				if(userId.equals(tScContact.getUserId())){
					tScContactService.delete(tScContact);
					message = "通讯簿删除成功";
				}
			}
			j.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			message = "通讯簿删除失败";
			j.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加通讯簿
	 *
	 * @param tScContact
	 * @return
	 */
	@RequestMapping(params = "doAppAdd")
	@ResponseBody
	public AjaxJson doAppAdd(@RequestBody TScContactEntity tScContact, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "通讯簿添加成功";
		try{
			if(null == tScContact.getUserId() || "".equals(tScContact.getUserId())){
				String userId = ResourceUtil.getSessionUserName().getId();
				if(null != userId){
					tScContact.setUserId(userId);
				}
			}
			TSUser user = null;
			if(null != tScContact.getUserId()){
				user = systemService.get(TSUser.class, tScContact.getUserId());
			}

			if(null != user){
				tScContact.setCreateBy(user.getUserName());
				tScContact.setCreateName(user.getRealName());
			}
			tScContactService.save(tScContact);
			j.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			message = "通讯簿添加失败";
			j.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新通讯簿
	 *
	 * @param tScContact
	 * @return
	 */
	@RequestMapping(params = "doAppUpdate")
	@ResponseBody
	public AjaxJson doAppUpdate(@RequestBody TScContactEntity tScContact, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "通讯簿更新成功";
		TScContactEntity t = tScContactService.get(TScContactEntity.class, tScContact.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tScContact, t);
			TSUser user = null;
			if(null != t.getUserId()){
				user = systemService.get(TSUser.class, t.getUserId());
			}

			if(null != user){
				t.setUpdateBy(user.getUserName());
				t.setUpdateName(user.getRealName());
			}
			tScContactService.saveOrUpdate(t);
			j.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			message = "通讯簿更新失败";
			j.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

}
