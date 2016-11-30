package com.qihang.buss.sc.baseinfo.controller;

import com.qihang.buss.sc.baseinfo.entity.TScDepartmentEntity;
import com.qihang.buss.sc.baseinfo.entity.TScEmpEntity;
import com.qihang.buss.sc.baseinfo.entity.TScItemTypeEntity;
import com.qihang.buss.sc.baseinfo.entity.TScSupplierEntity;
import com.qihang.buss.sc.baseinfo.service.TScDepartmentServiceI;
import com.qihang.buss.sc.baseinfo.service.TScEmpServiceI;
import com.qihang.buss.sc.baseinfo.service.TScItemTypeServiceI;
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
import com.qihang.winter.tag.vo.datatable.SortDirection;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.service.SystemService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @Title: Controller
 * @Description: 部门
 * @author onlineGenerator
 * @date 2016-07-01 15:40:43
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScDepartmentController")
public class TScDepartmentController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScDepartmentController.class);

	@Autowired
	private TScDepartmentServiceI tScDepartmentService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TScItemTypeServiceI tScItemTypeService;
	@Autowired
	private TScEmpServiceI tScEmpServiceI;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 部门列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScDepartment")
	public ModelAndView tScDepartment(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScDepartmentList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScDepartmentEntity tScDepartment,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//判断是否进行查询
		String name = tScDepartment.getName();
		Boolean isSelect = false;
		if(org.apache.commons.lang.StringUtils.isNotEmpty(name)) {
			if (name.indexOf("_") > -1) {
				isSelect = true;
				name = name.replace("_", "");
				tScDepartment.setName("");
			}
		}
		CriteriaQuery cq = new CriteriaQuery(TScDepartmentEntity.class, dataGrid);
		cq.addOrder("createDate", SortDirection.desc);
		List<String> list = new ArrayList<String>();
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		if (StringUtil.isNotEmpty(tScDepartment.getParentID())) {
			list = tScItemTypeService.getParentIdFromTree(Globals.SC_BASEINFO_DEPARTMENT_TYPE, tScDepartment.getParentID(),depart.getId());
			tScDepartment.setParentID(null);
		}
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScDepartment, request.getParameterMap());
		try{
		//自定义追加查询条件
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
			String disabled = request.getParameter("disabled");
			if(StringUtils.isNotEmpty(disabled)){
				cq.eq("disabled",Integer.parseInt(disabled));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScDepartmentService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除部门
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScDepartmentEntity tScDepartment, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScDepartment = systemService.getEntity(TScDepartmentEntity.class, tScDepartment.getId());
		message = "部门删除成功";
		try{
			//判断单据是否被引用
			if (tScDepartment.getCount() != null && tScDepartment.getCount() > 0) {
				message = "该信息被其他数据引用，不能删除";
				j.setSuccess(true);
				j.setMsg(message);
				return j;
			} else {
			tScDepartmentService.delete(tScDepartment);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "部门删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除部门
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "部门删除成功";
		try{
			for(String id:ids.split(",")){
				TScDepartmentEntity tScDepartment = systemService.getEntity(TScDepartmentEntity.class, 
				id
				);
				tScDepartmentService.delete(tScDepartment);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "部门删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加部门
	 * 
	 * @param tScDepartment
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScDepartmentEntity tScDepartment, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScDepartment.setDisabled(0);
		message = "部门添加成功";
		try{
			TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart depart = systemService.getParentSonInfo(sonInfo);
			if(StringUtil.isNotEmpty(tScDepartment.getName())) {
				String hql = "from TScDepartmentEntity where 1 = 1 AND number = ? and sonCompanyId = ?";
				List<TScDepartmentEntity> tScDepartmentEntityList = systemService.findHql(hql, tScDepartment.getNumber(),depart.getId());
				if ( tScDepartmentEntityList.size() >0) {
					message = "编号【"+ tScDepartment.getNumber()+"】已存在";
					j.setSuccess(false);
					j.setMsg(message);
					return j;
				}
			}
			//新增时改变分类编号则改变其parentId
			String num = tScDepartment.getNumber();
			int a = num.lastIndexOf(".");
			String number = num.substring(0, a);
			String sql = "select * from t_sc_item_type where number = '" + number + "' and item_class_id ='" + Globals.SC_BASEINFO_DEPARTMENT_TYPE + "'";
			List<TScItemTypeEntity> list = systemService.findListbySql(sql, TScItemTypeEntity.class);
			//如果改变了分类则调用一次编号生成工具
			if(!tScDepartment.getParentID().equals(list.get(0).getId())){
				BillNoGenerate.getBasicInfoBillNo(Globals.SC_BASEINFO_DEPARTMENT_TYPE, number, number);
			}
			if(list.size()>0){
				tScDepartment.setParentID(list.get(0).getId());
			}else{
				message = "分类编号【"+number+"】不存在";
				j.setSuccess(false);
				j.setMsg(message);
				return j;
			}
			tScDepartment.setCount(0);
			tScDepartmentService.save(tScDepartment);
			//分支机构新建一条级别为部门的分支机构数据；
			tScDepartmentService.saveSysDepartInfo(tScDepartment, depart.getId());
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "部门添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新部门
	 * 
	 * @param tScDepartment
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScDepartmentEntity tScDepartment, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "部门更新成功";
		TScDepartmentEntity t = tScDepartmentService.get(TScDepartmentEntity.class, tScDepartment.getId());
		try {
			TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart depart = systemService.getParentSonInfo(sonInfo);
			if(StringUtil.isNotEmpty(tScDepartment.getName())) {
				String hql = "from TScDepartmentEntity where 1 = 1 AND number = ? and id != ? and sonCompanyId = ?";
				List<TScDepartmentEntity> tScDepartmentEntityList = systemService.findHql(hql, tScDepartment.getNumber(),tScDepartment.getId(),depart.getId());
				if ( tScDepartmentEntityList.size() >0) {
					message = "编号【"+ tScDepartment.getNumber()+"】已存在";
					j.setSuccess(false);
					j.setMsg(message);
					return j;
				}
			}
			//修改编号时，实现数据从分类A（被修改）移到分类B（修改后）
			if(! t.getNumber().equals(tScDepartment.getNumber())){
				String num = tScDepartment.getNumber();
				//获得修改后的分类编号
				int a = num.lastIndexOf(".");
				String number = num.substring(0, a);

				String sql = "select * from t_sc_item_type where number = '" + number + "' and item_class_id ='" + Globals.SC_BASEINFO_DEPARTMENT_TYPE + "'";
				List<TScItemTypeEntity> list = systemService.findListbySql(sql, TScItemTypeEntity.class);
				if(list.size()>0){
					tScDepartment.setParentID(list.get(0).getId());
				}else{
					message = "分类编号【"+number+"】不存在";
					j.setSuccess(false);
					j.setMsg(message);
					return j;
				}
			}
			MyBeanUtils.copyBeanNotNull2Bean(tScDepartment, t);
			tScDepartmentService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "部门更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 部门新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScDepartmentEntity tScDepartment, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScDepartment.getId())) {
			tScDepartment = tScDepartmentService.getEntity(TScDepartmentEntity.class, tScDepartment.getId());
			req.setAttribute("tScDepartmentPage", tScDepartment);
		}
		String parentNo = req.getParameter("parentNo");

		req.setAttribute("number", BillNoGenerate.getBasicInfoBillNo(Globals.SC_BASEINFO_DEPARTMENT_TYPE,parentNo,parentNo));
		req.setAttribute("parentID", tScDepartment.getParentID());
		req.setAttribute("tranType", Globals.SC_BASEINFO_DEPARTMENT_TYPE);//基础资料的编号 类型

		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScDepartment-add");
	}
	/**
	 * 部门编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScDepartmentEntity tScDepartment, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScDepartment.getId())) {
			tScDepartment = tScDepartmentService.getEntity(TScDepartmentEntity.class, tScDepartment.getId());
			req.setAttribute("tScDepartmentPage", tScDepartment);
		}
		TScEmpEntity scEmpEntity = tScEmpServiceI.get(TScEmpEntity.class, tScDepartment.getManager());
		if(null == scEmpEntity) {
			req.setAttribute("managerName", "");
		}else{
			req.setAttribute("managerName",scEmpEntity.getName());
		}
		req.setAttribute("tranType", Globals.SC_BASEINFO_DEPARTMENT_TYPE);//基础资料的编号 类型

		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScDepartment-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tScDepartmentController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScDepartmentEntity tScDepartment,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScDepartmentEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScDepartment, request.getParameterMap());
		List<TScDepartmentEntity> tScDepartments = this.tScDepartmentService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"部门");
		modelMap.put(NormalExcelConstants.CLASS,TScDepartmentEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("部门列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, tScDepartments);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScDepartmentEntity tScDepartment,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "部门");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScDepartmentEntity.class);
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
				List<TScDepartmentEntity> listTScDepartmentEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TScDepartmentEntity.class,params);
				for (TScDepartmentEntity tScDepartment : listTScDepartmentEntitys) {
					tScDepartmentService.save(tScDepartment);
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
	 * 禁用启用
	 * @param tScDepartmentEntity
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "doDisable")
	@ResponseBody
	public AjaxJson doDisable(TScDepartmentEntity tScDepartmentEntity, HttpServletRequest req){
		AjaxJson j = new AjaxJson();
		String message = "操作成功";
		try{
			for(String id:tScDepartmentEntity.getId().split(",")){
				TScDepartmentEntity scStockEntity = systemService.getEntity(TScDepartmentEntity.class, id);
				if(tScDepartmentEntity.getDisabled() == Globals.ENABLE_CODE){//启用
					if(tScDepartmentEntity.getDisabled() == scStockEntity.getDisabled()){
						message="已启用";
					}else{
						scStockEntity.setDisabled(Globals.ENABLE_CODE);
					}
				}else{//禁用
					if(tScDepartmentEntity.getDisabled() == scStockEntity.getDisabled()) {
						message="已禁用";
					}else{
						scStockEntity.setDisabled(Globals.DISABLED_CODE);
					}
				}
				tScDepartmentService.saveOrUpdate(scStockEntity);
			}
		}catch (Exception e){
			e.printStackTrace();
			message = "操作失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 部门择页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goSelectDialog")
	public ModelAndView goSelectDialog(TScDepartmentEntity deptEntity, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(deptEntity.getName())) {
			req.setAttribute("name", deptEntity.getName());
		}
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScDepartmentSelect");
	}
	/***
	 *
	 * @部门弹出框列表
	 */
	@RequestMapping(params = "goSelectDepartDialog")
	public ModelAndView goSelectDepartDialog(TScDepartmentEntity departmentEntity, HttpServletRequest req){
		if (StringUtil.isNotEmpty(departmentEntity.getName())) {
			req.setAttribute("name", departmentEntity.getName());
		}
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScDepartmentDialogList");
	}

	/**
	 * 通过id获取名称
	 * @param departmentEntity
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "goDepartName")
	@ResponseBody
	public AjaxJson goDepartName(TScDepartmentEntity departmentEntity, HttpServletRequest req){
		AjaxJson j = new AjaxJson();
		try {
			if(StringUtils.isNotEmpty(departmentEntity.getId())){
                TScDepartmentEntity entity = tScDepartmentService.get(TScDepartmentEntity.class, departmentEntity.getId());
                j.setObj(entity.getName());
				j.setSuccess(true);
            }
		} catch (Exception e) {
			j.setSuccess(false);
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping(params = "checkName",method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson checkName(String name,String id){
		AjaxJson j = new AjaxJson();
		String hql = "";
		List<TScDepartmentEntity> supplierName = new ArrayList<TScDepartmentEntity>();
		if(!StringUtil.isNotEmpty(id)){//新增验证
			hql = " from TScDepartmentEntity where 1 = 1 AND name =?";
			supplierName = systemService.findHql(hql,name);
		}else{//编辑的时候验证
			hql = "from  TScDepartmentEntity where 1 = 1 AND name =? and id != ?" ;
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
