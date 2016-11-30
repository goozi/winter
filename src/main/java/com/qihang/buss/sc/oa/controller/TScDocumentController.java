package com.qihang.buss.sc.oa.controller;


import com.qihang.buss.sc.oa.entity.TScDocumentEntity;
import com.qihang.buss.sc.oa.entity.TScFileEntity;
import com.qihang.buss.sc.oa.service.TScDocumentServiceI;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Title: Controller
 * @Description: 文档结构
 * @author onlineGenerator
 * @date 2015-12-03 16:49:18
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScDocumentController")
public class TScDocumentController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScDocumentController.class);

	@Autowired
	private TScDocumentServiceI tScDocumentService;
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
	 * 文档结构列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScDocument")
	public ModelAndView tScDocument(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/oa/tScDocumentList");
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
	public void datagrid(TScDocumentEntity tScDocument,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScDocumentEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScDocument, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScDocumentService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除文档结构
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScDocumentEntity tScDocument, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScDocument = systemService.getEntity(TScDocumentEntity.class, tScDocument.getId());
		message = "文档结构删除成功";
		try{
			tScDocumentService.delete(tScDocument);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "文档结构删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除文档结构
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "文档结构删除成功";
		try{
			for(String id:ids.split(",")){
				TScDocumentEntity tScDocument = systemService.getEntity(TScDocumentEntity.class,
				id
				);
				tScDocumentService.delete(tScDocument);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "文档结构删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加文档结构
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScDocumentEntity tScDocument, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "文档结构添加成功";
		try{
			if(tScDocument.getId().equals("")){
				tScDocument.setId(null);
			}
			String userId = ResourceUtil.getSessionUserName().getId();
			if(null != userId){
				tScDocument.setUserId(userId);
			}
			tScDocumentService.saveOrUpdate(tScDocument);
			j.setSuccess(true);
			j.setObj(tScDocument.getId());
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "文档结构添加失败";
			j.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新文档结构
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScDocumentEntity tScDocument, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "文档结构更新成功";
		TScDocumentEntity t = tScDocumentService.get(TScDocumentEntity.class, tScDocument.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tScDocument, t);
			String userId = ResourceUtil.getSessionUserName().getId();
			if(null != userId){
				tScDocument.setUserId(userId);
			}
			tScDocumentService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "文档结构更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 文档结构新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScDocumentEntity tScDocument, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScDocument.getId())) {
			tScDocument = tScDocumentService.getEntity(TScDocumentEntity.class, tScDocument.getId());
			req.setAttribute("tScDocumentPage", tScDocument);
		}
		return new ModelAndView("com/qihang/buss/sc/oa/tScDocument-add");
	}
	/**
	 * 文档结构编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScDocumentEntity tScDocument, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScDocument.getId())) {
			tScDocument = tScDocumentService.getEntity(TScDocumentEntity.class, tScDocument.getId());
			req.setAttribute("tScDocumentPage", tScDocument);
		}
		return new ModelAndView("com/qihang/buss/sc/oa/tScDocument-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tScDocumentController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScDocumentEntity tScDocument,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScDocumentEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScDocument, request.getParameterMap());
		List<TScDocumentEntity> tScDocuments = this.tScDocumentService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"文档结构");
		modelMap.put(NormalExcelConstants.CLASS,TScDocumentEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("文档结构列表", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, tScDocuments);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScDocumentEntity tScDocument,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "文档结构");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScDocumentEntity.class);
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
				List<TScDocumentEntity> listTScDocumentEntitys = ExcelImportUtil.importExcel(file.getInputStream(), TScDocumentEntity.class, params);
				for (TScDocumentEntity tScDocument : listTScDocumentEntitys) {
					tScDocumentService.save(tScDocument);
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
	 * 主体单位分类树形信息
	 * @return
	 */
	@RequestMapping(params = "treeList")
	@ResponseBody
	public List<TScDocumentEntity> treeList(HttpServletRequest request,HttpServletResponse response){
		TScDocumentEntity entity = tScDocumentService.treeList();
		if(entity.getChildren().size()==0){
			List<TScDocumentEntity> tree = tScDocumentService.getTreeList("0000");
			entity.setId("0");
			entity.setText("文件柜");
			entity.setChildren(tree);
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("pid",0);
			map.put("type",0);
			entity.setAttributes(map);
		}
		List<TScDocumentEntity> list = new ArrayList<TScDocumentEntity>();
		list.add(entity);
		return list;
	}

	/**
	 * 删除验证
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "delVaild")
	@ResponseBody
	public AjaxJson delVaild(TScDocumentEntity tScDocument, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
			Boolean bl = tScDocumentService.delVaild(tScDocument);
			if(bl){
				message = "该目录下存在文件或子目录，不可删！";
				j.setMsg(message);
				j.setSuccess(true);
			}else{
				j.setSuccess(false);
			}
		return j;
	}

//以下为APP接口方法 开始 zyy 2016-01-14
	/**
	 * 获取APP文件目录
	 * @return
	 */
	@RequestMapping(params = "getAppDoc")
	@ResponseBody
	public List<TScDocumentEntity> getAppDoc(String userId){
		List<TScDocumentEntity> list = tScDocumentService.getAppDoc(userId);
		return list;
	}

	/**
	 * 获取APP文件目录
	 * @return
	 */
	@RequestMapping(params = "getAppDocT")
	@ResponseBody
	public Map<String,Object> getAppDocT(String id,String userId,String queryName){
		Map<String,Object> map = tScDocumentService.getAppDocT(id, userId,queryName);
		return map;
	}

	/**
	 * APP添加文档结构
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "addAppDoc")
	@ResponseBody
	public AjaxJson addAppDoc(@RequestBody TScDocumentEntity tScDocument, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "文档结构添加成功";
		try{
//			if(tScDocument.getId().equals("")){
//				tScDocument.setId(null);
//			}
			TSUser user = null;
			if(null != tScDocument.getUserId()){
				user = systemService.get(TSUser.class, tScDocument.getUserId());
			}

			if(null != user){
				tScDocument.setCreateBy(user.getUserName());
				tScDocument.setCreateName(user.getRealName());
			}
			tScDocumentService.saveOrUpdate(tScDocument);
			j.setSuccess(true);
			//j.setObj(tScDocument.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "文档结构添加失败";
			j.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * APP更新文档结构
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "updateAppDoc")
	@ResponseBody
	public AjaxJson updateAppDoc(@RequestBody TScDocumentEntity tScDocument, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "文档结构更新成功";
		TScDocumentEntity t = tScDocumentService.get(TScDocumentEntity.class, tScDocument.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tScDocument, t);
			TSUser user = null;
			if(null != t.getUserId()){
				user = systemService.get(TSUser.class, t.getUserId());
			}

			if(null != user){
				t.setUpdateBy(user.getUserName());
				t.setUpdateName(user.getRealName());
			}
			tScDocumentService.saveOrUpdate(t);
		} catch (Exception e) {
			e.printStackTrace();
			message = "文档结构更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * APP批量删除文档结构
	 *
	 * @return
	 */
	@RequestMapping(params = "delAppDocOrFile")
	@ResponseBody
	public AjaxJson delAppDocOrFile(String docs,String files,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "文档结构删除成功";
		try{
			if(null != docs && !"".equals(docs)){
				for(String id:docs.split(",")){
					TScDocumentEntity tScDocument = systemService.getEntity(TScDocumentEntity.class, id);
					if(null != tScDocument){
						tScDocumentService.delete(tScDocument);
					}
				}
			}
			if(null != files && !"".equals(files)){
				for(String id:files.split(",")){
					TScFileEntity file = systemService.getEntity(TScFileEntity.class, id);
					if(null != file){
						systemService.delete(file);
					}
				}
			}

		}catch(Exception e){
			e.printStackTrace();
			message = "文档结构删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

}
