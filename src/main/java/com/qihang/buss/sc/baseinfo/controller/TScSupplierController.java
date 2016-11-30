package com.qihang.buss.sc.baseinfo.controller;

import com.qihang.buss.sc.baseinfo.entity.TScIcitemEntity;
import com.qihang.buss.sc.baseinfo.entity.TScItemTypeEntity;
import com.qihang.buss.sc.baseinfo.entity.TScLogisticalEntity;
import com.qihang.buss.sc.baseinfo.entity.TScSupplierEntity;
import com.qihang.buss.sc.baseinfo.service.TScItemTypeServiceI;
import com.qihang.buss.sc.baseinfo.service.TScSupplierServiceI;
import com.qihang.buss.sc.util.BillNoGenerate;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.ExceptionUtil;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
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
 * @Description: 供应商
 * @author onlineGenerator
 * @date 2016-06-23 14:09:01
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScSupplierController")
public class TScSupplierController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScSupplierController.class);

	@Autowired
	private TScSupplierServiceI tScSupplierService;
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
	 * 供应商列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScSupplier")
	public ModelAndView tScSupplier(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScSupplierList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScSupplierEntity tScSupplier,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//判断是否进行查询
		String name = tScSupplier.getName();
		Boolean isSelect = false;
		if(org.apache.commons.lang.StringUtils.isNotEmpty(name)) {
			if (name.indexOf("_") > -1) {
				isSelect = true;
				name = name.replace("_", "");
				tScSupplier.setName("");
			}
		}
		String billType = request.getParameter("billType");
		if("0".equals(billType) || StringUtils.isEmpty(billType)) {
			CriteriaQuery cq = new CriteriaQuery(TScSupplierEntity.class, dataGrid);
			List<String> list = new ArrayList<String>();
			TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart depart = systemService.getParentSonInfo(sonInfo);
			if (StringUtil.isNotEmpty(tScSupplier.getParentID())) {
				list = tScItemTypeService.getParentIdFromTree("02", tScSupplier.getParentID(),depart.getId());
				tScSupplier.setParentID(null);
			}
			//查询条件组装器
			com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScSupplier, request.getParameterMap());
			try {
				//自定义追加查询条件
				if (list.size() > 0) {
					cq.in("parentID", list.toArray());
				}
				//如若弹窗查询
				if(isSelect){
					cq.add(Restrictions.or(
							Restrictions.like("name", "%" + name + "%"),
							Restrictions.or(
									Restrictions.like("number", "%" + name + "%"),
									Restrictions.or(
											Restrictions.like("shortNumber", "%" + name + "%"), Restrictions.like("shortName", "%" + name + "%")))));
				}
			} catch (Exception e) {
				throw new BusinessException(e.getMessage());
			}
			cq.add();
			this.tScSupplierService.getDataGridReturn(cq, true);
			TagUtil.datagrid(response, dataGrid);
		}else{
			CriteriaQuery cq = new CriteriaQuery(TScLogisticalEntity.class, dataGrid);
//			List<String> list = new ArrayList<String>();
//			if (StringUtil.isNotEmpty(tScSupplier.getParentID())) {
//				list = tScItemTypeService.getParentIdFromTree("02", tScSupplier.getParentID());
//				tScSupplier.setParentID(null);
//
//			}
			//查询条件组装器
			com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScSupplier, request.getParameterMap());
			try {
				//自定义追加查询条件
//				if (list.size() > 0) {
//					cq.in("parentID", list.toArray());
//				}
			} catch (Exception e) {
				throw new BusinessException(e.getMessage());
			}
			cq.add();
			this.tScSupplierService.getDataGridReturn(cq, true);
			TagUtil.datagrid(response, dataGrid);
		}
	}

	/**
	 * 删除供应商
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScSupplierEntity tScSupplier, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScSupplier = systemService.getEntity(TScSupplierEntity.class, tScSupplier.getId());
		message = "供应商删除成功";
		try{
			if(tScSupplier.getDisabled() == 1){
				message="该信息已禁用，不能删除";
				j.setSuccess(true);
				j.setMsg(message);
				return j;
			}else {
				//判断单据是否被引用
				if (tScSupplier.getCount() != null && tScSupplier.getCount() > 0) {
					message = "该信息被其他数据引用，不能删除";
					j.setSuccess(true);
					j.setMsg(message);
					return j;
				} else {
					tScSupplier.setDeleted(1);
					tScSupplierService.delete(tScSupplier);
					systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
				}
			}

		}catch(Exception e){
			e.printStackTrace();
			message = "供应商删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除供应商
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "供应商删除成功";
		try{
			for(String id:ids.split(",")){
				TScSupplierEntity tScSupplier = systemService.getEntity(TScSupplierEntity.class,
				id
				);
				if(tScSupplier.getCount()!= null && tScSupplier.getCount() > 0) {
					message = "部分信息被其他数据引用，不能删除";
					j.setSuccess(false);
					j.setMsg(message);
					return j;



				} else {
					if (tScSupplier.getDisabled() == 1) {
						message = "部分信息已禁用，不能删除";
						j.setSuccess(true);
						j.setMsg(message);
						return j;
					} else {
						tScSupplierService.delete(tScSupplier);
					}
				}
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "供应商删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加供应商
	 * 
	 * @param tScSupplier
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScSupplierEntity tScSupplier, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "供应商添加成功";
		try {
			//当前分支机构
			TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart depart = systemService.getParentSonInfo(sonInfo);
			if(StringUtil.isNotEmpty(tScSupplier.getName())) {
				String hql = "from TScSupplierEntity where 1 = 1 AND number = ? and sonId = ?";
				List<TScSupplierEntity> tScSupplierEntityList = systemService.findHql(hql, new Object[]{tScSupplier.getNumber(),depart.getId()});
				if ( tScSupplierEntityList.size() >0) {
					message = "编号【"+ tScSupplier.getNumber()+"】已存在";
					j.setSuccess(false);
					j.setMsg(message);
					return j;
				}
			}
			//新增时改变分类编号则改变其parentId
			String num = tScSupplier.getNumber();
			int a = num.lastIndexOf(".");
			String number = num.substring(0, a);
			String sql = "select * from t_sc_item_type where number = '" + number + "' and item_class_id ='" + Globals.SC_BASEINFO_SUPPLIER_TYPE + "'";
			List<TScItemTypeEntity> list = systemService.findListbySql(sql, TScItemTypeEntity.class);
			//如果改变了分类则调用一次编号生成工具
			if(!tScSupplier.getParentID().equals(list.get(0).getId())){
				BillNoGenerate.getBasicInfoBillNo(Globals.SC_BASEINFO_SUPPLIER_TYPE, number, number);
			}
			if(list.size()>0){
				tScSupplier.setParentID(list.get(0).getId());
			}else{
				message = "分类编号【"+number+"】不存在";
				j.setSuccess(false);
				j.setMsg(message);
				return j;
			}
					tScSupplier.setCount(0);
					tScSupplier.setLevel(tScItemTypeService.getLevel(tScSupplier.getParentID()));
					tScSupplier.setDisabled(Globals.ENABLE_CODE);
			        tScSupplier.setSonId(depart.getId());
					tScSupplierService.save(tScSupplier);
					//该id为主键id
//			tScSupplier.setSettleCompany(tScSupplier.getId());
					systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);

		}catch(Exception e){
			e.printStackTrace();
			message = "供应商添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新供应商
	 * 
	 * @param tScSupplier
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScSupplierEntity tScSupplier, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "供应商更新成功";
//		TScSupplierEntity t = tScSupplierService.get(TScSupplierEntity.class, tScSupplier.getId());
		try {
			//当前分支机构
			TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart depart = systemService.getParentSonInfo(sonInfo);
			String hql = "from TScSupplierEntity where 1 = 1 AND number = ? and id != ? and sonId = ?";
			List<TScIcitemEntity> tScIcitemEntityList = systemService.findHql(hql, new Object[]{tScSupplier.getNumber(),tScSupplier.getId(),depart.getId()});
			if(tScIcitemEntityList.size() > 0){
				message = "编号【"+tScSupplier.getNumber()+"】已存在";
				j.setSuccess(false);
				j.setMsg(message);
				return j;
			}
			//编辑时改变编号则改变其parentId
			String num = tScSupplier.getNumber();
			int a = num.lastIndexOf(".");
			String number = num.substring(0, a);
			String sql = "select * from t_sc_item_type where number = '" + number + "' and item_class_id ='" + Globals.SC_BASEINFO_SUPPLIER_TYPE + "'";
			List<TScItemTypeEntity> list = systemService.findListbySql(sql, TScItemTypeEntity.class);
			if(list.size()>0){
				tScSupplier.setParentID(list.get(0).getId());
			}else{
				message = "分类编号【"+number+"】不存在";
				j.setSuccess(false);
				j.setMsg(message);
				return j;
			}
			//编辑时结算单位为空则默认自身
			if(!StringUtil.isNotEmpty(tScSupplier.getSettleCompany()) || tScSupplier.getSettleCompany().equals("")){
				tScSupplier.setSettleCompany(tScSupplier.getId());
			}
//			MyBeanUtils.copyBeanNotNull2Bean(tScSupplier, t);//保存前不覆盖 而是直接保存，这样到service 拿到的 数据就是新的数据
			tScSupplier.setSonId(depart.getId());
			tScSupplierService.saveOrUpdate(tScSupplier);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "供应商更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 供应商新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScSupplierEntity tScSupplier, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScSupplier.getId())) {
			tScSupplier = tScSupplierService.getEntity(TScSupplierEntity.class, tScSupplier.getId());
			req.setAttribute("tScSupplierPage", tScSupplier);
		}
		String parentNo = req.getParameter("parentNo");
		req.setAttribute("number", BillNoGenerate.getBasicInfoBillNo(Globals.SC_BASEINFO_SUPPLIER_TYPE,parentNo,parentNo));
		req.setAttribute("parentID", tScSupplier.getParentID());
		req.setAttribute("tranType", Globals.SC_BASEINFO_SUPPLIER_TYPE);//基础资料的编号 类型

		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScSupplier-add");
	}
	/**
	 * 供应商编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScSupplierEntity tScSupplier, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScSupplier.getId())) {
			tScSupplier = tScSupplierService.getEntity(TScSupplierEntity.class, tScSupplier.getId());
			if (StringUtil.isNotEmpty(tScSupplier.getSettleCompany())) {
				TScSupplierEntity tScSupplierTemp = tScSupplierService.getEntity(TScSupplierEntity.class, tScSupplier.getSettleCompany());
				tScSupplier.setSettleCompanyName(tScSupplierTemp.getName());
			}
			req.setAttribute("tScSupplierPage", tScSupplier);
		}
		req.setAttribute("tranType", Globals.SC_BASEINFO_SUPPLIER_TYPE);//基础资料的编号 类型
		String load = req.getParameter("load");
		req.setAttribute("load",load);
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScSupplier-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "tScSupplierController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScSupplierEntity tScSupplier,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScSupplierEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScSupplier, request.getParameterMap());
		List<TScSupplierEntity> tScSuppliers = this.tScSupplierService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"供应商");
		modelMap.put(NormalExcelConstants.CLASS,TScSupplierEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("供应商列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, tScSuppliers);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScSupplierEntity tScSupplier,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "供应商");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScSupplierEntity.class);
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
				List<TScSupplierEntity> listTScSupplierEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TScSupplierEntity.class,params);
				for (TScSupplierEntity tScSupplier : listTScSupplierEntitys) {
					tScSupplierService.save(tScSupplier);
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
	 * 结算单位页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goSelectDialog")
	public ModelAndView goSelectDialog(TScSupplierEntity tScSupplier, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScSupplier.getName())) {
			req.setAttribute("name", tScSupplier.getName());
		}
		String isBillType = req.getParameter("isBillType");
		if(StringUtils.isNotEmpty(isBillType)){
			isBillType = "true";
		}else{
			isBillType = "false";
		}
		req.setAttribute("isBillType",isBillType);
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScSupplierSelect");
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
			TScSupplierEntity  SupplierEntity=systemService.get(TScSupplierEntity.class,id);
			message="禁用成功";
		try {
			if (SupplierEntity.getDisabled()==Globals.DISABLED_CODE){
				message="该记录已禁用";
			}else {
				SupplierEntity.setDisabled(Globals.DISABLED_CODE);
				tScSupplierService.saveOrUpdate(SupplierEntity);
			}
			j.setMsg(message);
		}catch (Exception e) {
			e.printStackTrace();
			message = "禁用失败";
			throw new BusinessException(e.getMessage());
		}
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
		TScSupplierEntity  SupplierEntity=systemService.get(TScSupplierEntity.class,id);
		message="启用成功";
		try {
			if (SupplierEntity.getDisabled()==Globals.ENABLE_CODE){
				message="该记录已启用";
			}else {
				SupplierEntity.setDisabled(Globals.ENABLE_CODE);
				tScSupplierService.saveOrUpdate(SupplierEntity);
			}
		}catch (Exception e) {
			e.printStackTrace();
			message = "启用失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return  j;
	}

	@RequestMapping(params = "checkName",method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson checkName(String name,String id){
		AjaxJson j = new AjaxJson();
		String hql = "";
		List<TScSupplierEntity> supplierName = new ArrayList<TScSupplierEntity>();
		if(!StringUtil.isNotEmpty(id)){//新增验证
			hql = " from TScSupplierEntity where 1 = 1 AND name =?";
			supplierName = systemService.findHql(hql,name);
		}else{//编辑的时候验证
			hql = "from  TScSupplierEntity where 1 = 1 AND name =? and id != ?" ;
			supplierName = systemService.findHql(hql,name,id);
		}
		if(supplierName.size() > 0){
			j.setSuccess(true);
		}else{
			j.setSuccess(false);
		}
		return j;
	}
}
