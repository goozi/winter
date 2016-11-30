package com.qihang.buss.sc.baseinfo.controller;

import com.qihang.buss.sc.baseinfo.entity.TScItemTypeEntity;
import com.qihang.buss.sc.baseinfo.entity.TScLogisticalEntity;
import com.qihang.buss.sc.baseinfo.service.TScItemTypeServiceI;
import com.qihang.buss.sc.baseinfo.service.TScLogisticalServiceI;
import com.qihang.buss.sc.util.BillNoGenerate;
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
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.service.SystemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**   
 * @Title: Controller
 * @Description: 物流公司
 * @author onlineGenerator
 * @date 2016-07-01 08:45:06
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScLogisticalController")
public class TScLogisticalController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScLogisticalController.class);

	@Autowired
	private TScLogisticalServiceI tScLogisticalService;
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
	 * 物流公司列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScLogistical")
	public ModelAndView tScLogistical(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScLogisticalList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScLogisticalEntity tScLogistical,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScLogisticalEntity.class, dataGrid);
		List<String> list = new ArrayList<String>();
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		if (StringUtil.isNotEmpty(tScLogistical.getParentID())) {
			list = tScItemTypeService.getParentIdFromTree("03", tScLogistical.getParentID(),depart.getId());
			tScLogistical.setParentID(null);
		}
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScLogistical, request.getParameterMap());
		try{
		//自定义追加查询条件
			if (list.size() > 0) {
				cq.in("parentID", list.toArray());
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScLogisticalService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除物流公司
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScLogisticalEntity tScLogistical, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScLogistical = systemService.getEntity(TScLogisticalEntity.class, tScLogistical.getId());
		message = "物流公司删除成功";
		try{
			if(tScLogistical.getDisabled() == 1) {
				message = "该信息已禁用，不能删除";
				j.setSuccess(true);
				j.setMsg(message);
				return j;
			}else {
				//判断单据是否被引用
				if (tScLogistical.getCount() != null && tScLogistical.getCount() > 0) {
					message = "该信息被其他数据引用，不能删除";
					j.setSuccess(true);
					j.setMsg(message);
					return j;
				} else {
					tScLogisticalService.delete(tScLogistical);
					systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "物流公司删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除物流公司
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "物流公司删除成功";
		try{
			for(String id:ids.split(",")){
				TScLogisticalEntity tScLogistical = systemService.getEntity(TScLogisticalEntity.class, 
				id
				);
				tScLogisticalService.delete(tScLogistical);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "物流公司删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加物流公司
	 * 
	 * @param tScLogistical
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScLogisticalEntity tScLogistical, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "物流公司添加成功";
		try{
			//当前分支机构
			TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart depart = systemService.getParentSonInfo(sonInfo);
			if(StringUtil.isNotEmpty(tScLogistical.getName())) {
				String hql = "from TScLogisticalEntity where 1 = 1 AND number = ? and sonId = ?";
				List<TScLogisticalEntity> tScLogisticalEntityList = systemService.findHql(hql, new Object[]{tScLogistical.getNumber(),depart.getId()});
				if ( tScLogisticalEntityList.size() >0) {
					message = "编号【"+ tScLogistical.getNumber()+"】已存在";
					j.setSuccess(false);
					j.setMsg(message);
					return j;
				}
			}
			//新增时改变分类编号则改变其parentId
			String num = tScLogistical.getNumber();
			int a = num.lastIndexOf(".");
			String number = num.substring(0, a);
			String sql = "select * from t_sc_item_type where number = '" + number + "' and item_class_id ='" + Globals.SC_BASEINFO_LOGISTICAL_TYPE + "'";
			List<TScItemTypeEntity> list = systemService.findListbySql(sql, TScItemTypeEntity.class);
			//如果改变了分类则调用一次编号生成工具
			if(!tScLogistical.getParentID().equals(list.get(0).getId())){
				BillNoGenerate.getBasicInfoBillNo(Globals.SC_BASEINFO_LOGISTICAL_TYPE, number, number);
			}
			if(list.size()>0){
				tScLogistical.setParentID(list.get(0).getId());
			}else{
				message = "分类编号【"+number+"】不存在";
				j.setSuccess(false);
				j.setMsg(message);
				return j;
			}
			tScLogistical.setSonId(depart.getId());
			tScLogistical.setLevel(tScItemTypeService.getLevel(tScLogistical.getParentID()));
			tScLogistical.setDisabled(Globals.ENABLE_CODE);
			tScLogisticalService.save(tScLogistical);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "物流公司添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新物流公司
	 * 
	 * @param tScLogistical
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScLogisticalEntity tScLogistical, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "物流公司更新成功";
		TScLogisticalEntity t = tScLogisticalService.get(TScLogisticalEntity.class, tScLogistical.getId());
		try {
			//当前分支机构
			TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart depart = systemService.getParentSonInfo(sonInfo);
			if(StringUtil.isNotEmpty(tScLogistical.getName())) {
				String hql = "from TScLogisticalEntity where 1 = 1 AND number = ? and id != ? and sonId = ?";
				List<TScLogisticalEntity> tScLogisticalEntityList = systemService.findHql(hql, new Object[]{tScLogistical.getNumber(),tScLogistical.getId(),depart.getId()});
				if ( tScLogisticalEntityList.size() >0) {
					message = "编号【"+ tScLogistical.getNumber()+"】已存在";
					j.setSuccess(false);
					j.setMsg(message);
					return j;
				}
			}
			//修改编号时，实现数据从分类A（被修改）移到分类B（修改后）
			if(! t.getNumber().equals(tScLogistical.getNumber())){
				String num = tScLogistical.getNumber();
				//获得修改后的分类编号
				int a = num.lastIndexOf(".");
				String number = num.substring(0, a);

				String sql = "select * from t_sc_item_type where number = '" + number + "' and item_class_id ='" + Globals.SC_BASEINFO_LOGISTICAL_TYPE + "'";
				List<TScItemTypeEntity> list = systemService.findListbySql(sql, TScItemTypeEntity.class);
				if(list.size()>0){
					tScLogistical.setParentID(list.get(0).getId());
				}else{
					message = "分类编号【"+number+"】不存在";
					j.setSuccess(false);
					j.setMsg(message);
					return j;
				}
			}
			MyBeanUtils.copyBeanNotNull2Bean(tScLogistical, t);
			t.setSonId(depart.getId());
			tScLogisticalService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "物流公司更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 物流公司新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScLogisticalEntity tScLogistical, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScLogistical.getId())) {
			tScLogistical = tScLogisticalService.getEntity(TScLogisticalEntity.class, tScLogistical.getId());
			req.setAttribute("tScLogisticalPage", tScLogistical);
		}
		String parentNo = req.getParameter("parentNo");

		req.setAttribute("number", BillNoGenerate.getBasicInfoBillNo(Globals.SC_BASEINFO_LOGISTICAL_TYPE,parentNo,parentNo));
		req.setAttribute("parentID", tScLogistical.getParentID());
		req.setAttribute("tranType", Globals.SC_BASEINFO_LOGISTICAL_TYPE);//基础资料的编号 类型

		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScLogistical-add");
	}
	/**
	 * 物流公司编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScLogisticalEntity tScLogistical, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScLogistical.getId())) {
			tScLogistical = tScLogisticalService.getEntity(TScLogisticalEntity.class, tScLogistical.getId());
			req.setAttribute("tScLogisticalPage", tScLogistical);
		}
		req.setAttribute("tranType", Globals.SC_BASEINFO_LOGISTICAL_TYPE);//基础资料的编号 类型
		String load = req.getParameter("load");
		req.setAttribute("load",load);
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScLogistical-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tScLogisticalController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScLogisticalEntity tScLogistical,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScLogisticalEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScLogistical, request.getParameterMap());
		List<TScLogisticalEntity> tScLogisticals = this.tScLogisticalService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"物流公司");
		modelMap.put(NormalExcelConstants.CLASS,TScLogisticalEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("物流公司列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, tScLogisticals);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScLogisticalEntity tScLogistical,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "物流公司");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScLogisticalEntity.class);
		modelMap.put(TemplateExcelConstants.LIST_DATA, null);
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
				List<TScLogisticalEntity> listTScLogisticalEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TScLogisticalEntity.class,params);
				for (TScLogisticalEntity tScLogistical : listTScLogisticalEntitys) {
					tScLogisticalService.save(tScLogistical);
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
	 * 查出这条数据，设置它禁用，更新保存那条数据
	 * @param
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "doDisable", method= RequestMethod.POST)
	@ResponseBody
	public AjaxJson doDisable(@RequestParam("id") String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		TScLogisticalEntity  LogisticalEntity=systemService.get(TScLogisticalEntity.class,id);
		message="禁用成功";
		try {
			LogisticalEntity.setDisabled(Globals.DISABLED_CODE);
			tScLogisticalService.saveOrUpdate(LogisticalEntity);
		}catch (Exception e) {
			e.printStackTrace();
			message = "禁用失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return  j;
	}


	/**
	 * 查出这条数据，设置它启用，更新保存那条数据
	 * @param
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "doEnable", method= RequestMethod.POST)
	@ResponseBody
	public AjaxJson doEnable(@RequestParam("id") String id, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		TScLogisticalEntity  LogisticalEntity=systemService.get(TScLogisticalEntity.class,id);
		message="启用成功";
		try {
			LogisticalEntity.setDisabled(Globals.ENABLE_CODE);
			tScLogisticalService.saveOrUpdate(LogisticalEntity);
		}catch (Exception e) {
			e.printStackTrace();
			message = "启用失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return  j;
	}

	/**
	 * 单位选择页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goSelectDialog")
	public ModelAndView goSelectDialog(TScLogisticalEntity tScLogistical, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScLogistical.getName())) {
			req.setAttribute("name", tScLogistical.getName());
		}
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScLogisticalSelect");
	}
	/**
	 *
	 * 唯一性校验
	 *
	 */
	@RequestMapping(params = "checkName")
	@ResponseBody
	public AjaxJson checkName(String name, String load,String old_name){
		AjaxJson j = tScLogisticalService.checkName(name,load,old_name);
		return j;
	}
}
