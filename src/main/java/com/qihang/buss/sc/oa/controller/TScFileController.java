package com.qihang.buss.sc.oa.controller;

import com.qihang.buss.sc.oa.entity.TScFileEntity;
import com.qihang.buss.sc.oa.service.TScFileServiceI;
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
import com.qihang.winter.web.system.pojo.base.TSUser;
import com.qihang.winter.web.system.service.SystemService;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
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
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Title: Controller
 * @Description: 文件柜
 * @author onlineGenerator
 * @date 2015-12-03 16:49:31
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScFileController")
public class TScFileController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScFileController.class);

	@Autowired
	private TScFileServiceI tScFileService;
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
	 * 文件柜列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScFile")
	public ModelAndView tScFile(HttpServletRequest request) {
		String role = request.getSession().getAttribute("role").toString();
		if(null != role){
			int i = 0;
			for(String s:role.split(",")){
				if (s.equals("oa审核角色")){
					request.setAttribute("role",true);
					i = 1;
					break;
				}
			}
			if(i == 0){
				request.setAttribute("role",false);
			}
		}
		return new ModelAndView("com/qihang/buss/sc/oa/tScFileList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScFileEntity tScFile,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScFileEntity.class, dataGrid);

//		List<String> list = tScFileService.findListbySql("with files as (select o.id from " +
//										"T_SC_DOCUMENT o where o.id = '"+tScFile.getFileType()+"' union all select d.id " +
//										"from files,T_SC_DOCUMENT d where files.id = d.pid) " +
//										"select f.id from files f");
		List<String> list = tScFileService.findListbySql("select id from T_SC_DOCUMENT  where FIND_IN_SET(id, getChildList_document('" + tScFile.getFileType() + "'))");

		String type = tScFile.getType();
		tScFile.setFileType(null);
		tScFile.setType(null);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScFile, request.getParameterMap());
		try{
		//自定义追加查询条件
			if(null != type){
				String userId = ResourceUtil.getSessionUserName().getId();
				if(type.equals("1")){
					if(null != userId){
						cq.eq("userId", userId);
						cq.eq("type",type);
					}
				}else if(type.equals("2")){
					cq.eq("type",type);
				}else{
					//cq.eq("userId", userId);
					cq.or(Restrictions.eq("userId", userId), Restrictions.eq("type", "2"));
				}
			}
			if(list.size() > 0){
				cq.in("fileType",list.toArray());
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScFileService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除文件柜
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScFileEntity tScFile, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScFile = systemService.getEntity(TScFileEntity.class, tScFile.getId());
		message = "文件柜删除成功";
		try{
			tScFileService.delete(tScFile);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "文件柜删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除文件柜
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "文件柜删除成功";
		try{
			for(String id:ids.split(",")){
				TScFileEntity tScFile = systemService.getEntity(TScFileEntity.class,
				id
				);
				tScFileService.delete(tScFile);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "文件柜删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加文件柜
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScFileEntity tScFile, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "文件柜添加成功";
		try{
			String userId = ResourceUtil.getSessionUserName().getId();
			if(null != userId){
				tScFile.setUserId(userId);
			}
			if(tScFile.getType().equals("1")){
					tScFile.setStatus(1);
			}else{
				//读取系统设置里面是否审核
				String sql = "select publicfile from t_sc_systemfile";
				List<String> list = tScFileService.findListbySql(sql);
				if(list.get(0).toString().equals("1")){
					tScFile.setStatus(0);
				}else{
					tScFile.setStatus(1);
				}

			}
			tScFileService.save(tScFile);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			j.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			message = "文件柜添加失败";
			j.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新文件柜
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScFileEntity tScFile, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "文件柜更新成功";
		TScFileEntity t = tScFileService.get(TScFileEntity.class, tScFile.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tScFile, t);
			tScFileService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "文件柜更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 文件柜新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScFileEntity tScFile, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScFile.getId())) {
			tScFile = tScFileService.getEntity(TScFileEntity.class, tScFile.getId());
			req.setAttribute("tScFilePage", tScFile);
		}
		return new ModelAndView("com/qihang/buss/sc/oa/tScFile-add");
	}
	/**
	 * 文件柜编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScFileEntity tScFile, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScFile.getId())) {
			tScFile = tScFileService.getEntity(TScFileEntity.class, tScFile.getId());
			req.setAttribute("tScFilePage", tScFile);
		}
		return new ModelAndView("com/qihang/buss/sc/oa/tScFile-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "tScFileController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScFileEntity tScFile,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScFileEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScFile, request.getParameterMap());
		List<TScFileEntity> tScFiles = this.tScFileService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"文件柜");
		modelMap.put(NormalExcelConstants.CLASS,TScFileEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("文件柜列表", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, tScFiles);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScFileEntity tScFile,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "文件柜");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScFileEntity.class);
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
				List<TScFileEntity> listTScFileEntitys = ExcelImportUtil.importExcel(file.getInputStream(), TScFileEntity.class, params);
				for (TScFileEntity tScFile : listTScFileEntitys) {
					tScFileService.save(tScFile);
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
	 * 批量审核文件柜
	 *
	 * @return
	 */
	@RequestMapping(params = "doBatchAudit")
	@ResponseBody
	public AjaxJson doBatchAudit(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "审核成功";
		try{
			for(String id:ids.split(",")){
				TScFileEntity tScFile = systemService.getEntity(TScFileEntity.class,id);
				tScFile.setStatus(1);
				tScFileService.saveOrUpdate(tScFile);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "审核失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "getAppFile")
	@ResponseBody
	public Map<String,Object> getAppFile(TScFileEntity tScFile,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScFileEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScFile, request.getParameterMap());
		try{
			//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScFileService.getDataGridReturn(cq, true);
		//TagUtil.datagrid(response, dataGrid);

		Map<String,Object> map = new HashMap<String, Object>();
		map.put("rows",dataGrid.getRows());
		map.put("total",dataGrid.getTotal());
		List<TScFileEntity> list = dataGrid.getResults();
		if(null != list && list.size() > 0){
			for (TScFileEntity tScFileEntity : list) {
				String sql = "select realpath from t_s_attachment where id = '"+tScFileEntity.getUrl()+"'";
				List<String> sqlList = systemService.findListbySql(sql);
				if(null != sqlList && sqlList.size() > 0){
					tScFileEntity.setUrl(sqlList.get(0));
				}
			}
		}
		map.put("results", list);
		return map;
	}

	/**
	 * 添加文件柜
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "addAppFile")
	@ResponseBody
	public AjaxJson addAppFile(@RequestBody TScFileEntity tScFile, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "文件柜添加成功";
		try{
			if(tScFile.getType().equals("1")){
				tScFile.setStatus(1);
			}else{
				//读取系统设置里面是否审核
				String sql = "select publicfile from t_sc_systemfile";
				List<String> list = tScFileService.findListbySql(sql);
				if(list.get(0).toString().equals("1")){
					tScFile.setStatus(0);
				}else{
					tScFile.setStatus(1);
				}
			}
			TSUser user = null;
			if(null != tScFile.getUserId()){
				user = systemService.get(TSUser.class, tScFile.getUserId());
			}

			if(null != user){
				tScFile.setCreateBy(user.getUserName());
				tScFile.setCreateName(user.getRealName());
			}
			tScFileService.save(tScFile);
			j.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			message = "文件柜添加失败";
			j.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
}
