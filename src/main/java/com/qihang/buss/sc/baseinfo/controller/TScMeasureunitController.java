package com.qihang.buss.sc.baseinfo.controller;
import com.qihang.buss.sc.baseinfo.entity.TScMeasureunitEntity;
import com.qihang.buss.sc.baseinfo.service.TScMeasureunitServiceI;
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
import java.util.List;
import java.util.Map;


/**
 * @Title: Controller
 * @Description: 单位
 * @author onlineGenerator
 * @date 2016-07-05 15:32:02
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScMeasureunitController")
public class TScMeasureunitController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScMeasureunitController.class);

	@Autowired
	private TScMeasureunitServiceI tScMeasureunitService;
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
	 * 单位列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScMeasureunit")
	public ModelAndView tScMeasureunit(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScMeasureunitList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScMeasureunitEntity tScMeasureunit,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScMeasureunitEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScMeasureunit, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScMeasureunitService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除单位
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScMeasureunitEntity tScMeasureunit, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScMeasureunit = systemService.getEntity(TScMeasureunitEntity.class, tScMeasureunit.getId());
		message = "单位删除成功";
		try{
			if(tScMeasureunit.getDisabled() == Globals.DISABLED_CODE) {
				message = "该信息已禁用，不能删除";
				j.setSuccess(true);
				j.setMsg(message);
				return j;
			}else {
				//判断单据是否被引用
				if (tScMeasureunit.getCount() != null && tScMeasureunit.getCount() > 0) {
					message = "该信息被其他数据引用，不能删除";
					j.setSuccess(true);
					j.setMsg(message);
					return j;
				} else {
					tScMeasureunitService.delete(tScMeasureunit);
					systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "单位删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除单位
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "单位删除成功";
		try{
			for(String id:ids.split(",")){
				TScMeasureunitEntity tScMeasureunit = systemService.getEntity(TScMeasureunitEntity.class, 
				id
				);
				tScMeasureunitService.delete(tScMeasureunit);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "单位删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加单位
	 * 
	 * @param tScMeasureunit
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScMeasureunitEntity tScMeasureunit, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "单位添加成功";
		try{
			if(StringUtil.isNotEmpty(tScMeasureunit.getName())){
				String hql = "from TScMeasureunitEntity where name = ? ";
				List<TScMeasureunitEntity> tScMeasureunitEntityList = systemService.findHql(hql,tScMeasureunit.getName());
				if(!tScMeasureunitEntityList.isEmpty() && tScMeasureunitEntityList.size()>=1){
					message = "已有相同名称的数据";
					j.setSuccess(false);
				}else{
					tScMeasureunit.setDisabled(0);
					tScMeasureunit.setCount(0);
					tScMeasureunitService.save(tScMeasureunit);
					systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "单位添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新单位
	 * 
	 * @param tScMeasureunit
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScMeasureunitEntity tScMeasureunit, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "单位更新成功";
		TScMeasureunitEntity t = tScMeasureunitService.get(TScMeasureunitEntity.class, tScMeasureunit.getId());
		try {
			if(StringUtil.isNotEmpty(tScMeasureunit.getName())) {
				String hql = "from TScMeasureunitEntity where name = ? ";
				List<TScMeasureunitEntity> tScMeasureunitEntityList = systemService.findHql(hql, tScMeasureunit.getName());
				if (!tScMeasureunitEntityList.isEmpty() && tScMeasureunitEntityList.size() >= 1) {
					message = "已有相同名称的数据";
					j.setSuccess(false);

				}
				if (tScMeasureunit.getDisabled() == Globals.DISABLED_CODE) {
					message = "该信息已禁用，不能删除";
					j.setSuccess(true);
					j.setMsg(message);
					return j;

				} else {
					MyBeanUtils.copyBeanNotNull2Bean(tScMeasureunit, t);
					tScMeasureunitService.saveOrUpdate(t);
					systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "单位更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	@RequestMapping(params = "doDisabled")
	@ResponseBody
	public AjaxJson doDisabled(TScMeasureunitEntity tScMeasureunit,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "禁用成功";
		try {
			TScMeasureunitEntity t = tScMeasureunitService.get(TScMeasureunitEntity.class, tScMeasureunit.getId());
			t.setDisabled(1);
			tScMeasureunitService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "禁用失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "doEnable")
	@ResponseBody
	public AjaxJson doEnable(TScMeasureunitEntity tScMeasureunit,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "启用成功";
		try {
			TScMeasureunitEntity t = tScMeasureunitService.get(TScMeasureunitEntity.class, tScMeasureunit.getId());
			t.setDisabled(0);
			tScMeasureunitService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "启用失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 单位新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScMeasureunitEntity tScMeasureunit, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScMeasureunit.getId())) {
			tScMeasureunit = tScMeasureunitService.getEntity(TScMeasureunitEntity.class, tScMeasureunit.getId());
			req.setAttribute("tScMeasureunitPage", tScMeasureunit);
		}
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScMeasureunit-add");
	}

	/**
	 * 单位编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScMeasureunitEntity tScMeasureunit, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScMeasureunit.getId())) {
			tScMeasureunit = tScMeasureunitService.getEntity(TScMeasureunitEntity.class, tScMeasureunit.getId());
			req.setAttribute("tScMeasureunitPage", tScMeasureunit);
		}
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScMeasureunit-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tScMeasureunitController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScMeasureunitEntity tScMeasureunit,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScMeasureunitEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScMeasureunit, request.getParameterMap());
		List<TScMeasureunitEntity> tScMeasureunits = this.tScMeasureunitService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"单位");
		modelMap.put(NormalExcelConstants.CLASS,TScMeasureunitEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("单位列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, tScMeasureunits);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScMeasureunitEntity tScMeasureunit,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "单位");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScMeasureunitEntity.class);
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
				List<TScMeasureunitEntity> listTScMeasureunitEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TScMeasureunitEntity.class,params);
				for (TScMeasureunitEntity tScMeasureunit : listTScMeasureunitEntitys) {
					tScMeasureunitService.save(tScMeasureunit);
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
	 * 单位选择页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goSelectDialog")
	public ModelAndView goSelectDialog(TScMeasureunitEntity tScMeasureunitEntity, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScMeasureunitEntity.getName())) {
			req.setAttribute("name", tScMeasureunitEntity.getName());
		}
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScMeasureunitSelect");
	}
}
