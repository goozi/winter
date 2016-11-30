package com.qihang.buss.sc.baseinfo.controller;

import com.qihang.buss.sc.baseinfo.entity.TScDepartmentEntity;
import com.qihang.buss.sc.baseinfo.entity.TScEmpEntity;
import com.qihang.buss.sc.baseinfo.entity.TScItemTypeEntity;
import com.qihang.buss.sc.baseinfo.service.TScDepartmentServiceI;
import com.qihang.buss.sc.baseinfo.service.TScEmpServiceI;
import com.qihang.buss.sc.baseinfo.service.TScItemTypeServiceI;
import com.qihang.buss.sc.sales.entity.TScCountEntity;
import com.qihang.buss.sc.util.BillNoGenerate;
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
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.pojo.base.TSRole;
import com.qihang.winter.web.system.pojo.base.TSRoleUser;
import com.qihang.winter.web.system.pojo.base.TSUser;
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
 * @Description: 职员
 * @author onlineGenerator
 * @date 2016-07-05 10:30:32
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScEmpController")
public class TScEmpController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScEmpController.class);

	@Autowired
	private TScEmpServiceI tScEmpService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TScItemTypeServiceI tScItemTypeService;
	@Autowired
	private TScDepartmentServiceI tScDepartmentService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 职员列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScEmp")
	public ModelAndView tScEmp(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScEmpList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScEmpEntity tScEmp,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//判断是否进行查询
		String name = tScEmp.getName();
		Boolean isSelect = false;
		if(org.apache.commons.lang.StringUtils.isNotEmpty(name)) {
			if (name.indexOf("_") > -1) {
				isSelect = true;
				name = name.replace("_", "");
				tScEmp.setName("");
			}
		}
		CriteriaQuery cq = new CriteriaQuery(TScEmpEntity.class, dataGrid);
		List<String> list = new ArrayList<String>();
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		if (StringUtil.isNotEmpty(tScEmp.getParentID())) {
			list = tScItemTypeService.getParentIdFromTree("05", tScEmp.getParentID(),depart.getId());
			tScEmp.setParentID(null);
		}
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScEmp, request.getParameterMap());
		try{
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
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScEmpService.getDataGridReturn(cq, true);
		List<TScEmpEntity> result = dataGrid.getResults();
		for(TScEmpEntity entity : result){
			String deptID = entity.getDeptID();
			if(StringUtils.isNotEmpty(deptID)){
				TScDepartmentEntity dept = systemService.get(TScDepartmentEntity.class,deptID);
				if(null != dept) {
					entity.setDeptIdName(dept.getName());
				}
			}
		}
		dataGrid.setResults(result);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除职员
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScEmpEntity tScEmp, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScEmp = systemService.getEntity(TScEmpEntity.class, tScEmp.getId());
		message = "职员删除成功";
		try{
			if(tScEmp.getDisabled() == 1) {
				message = "该信息已禁用，不能删除";
				j.setSuccess(true);
				j.setMsg(message);
				return j;
			}else {
				//判断单据是否被引用
				if (tScEmp.getCount() != null && tScEmp.getCount() > 0) {
					message = "该信息被其他数据引用，不能删除";
					j.setSuccess(true);
					j.setMsg(message);
					return j;
				} else {
					tScEmpService.delete(tScEmp);
					systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "职员删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除职员
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "职员删除成功";
		try{
			for(String id:ids.split(",")){
				TScEmpEntity tScEmp = systemService.getEntity(TScEmpEntity.class,
				id
				);
				tScEmpService.delete(tScEmp);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "职员删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加职员
	 * 
	 * @param tScEmp
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScEmpEntity tScEmp, HttpServletRequest request) {

		AjaxJson j = new AjaxJson();
		message = "职员添加成功";
		try{
			//当前分支机构
			TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart depart = systemService.getParentSonInfo(sonInfo);
			String password = oConvertUtils.getString(request.getParameter("password"));
			TSUser tsUser = new TSUser();
			TSRole tsRole=new TSRole();
			TSRoleUser tsRoleUser=new TSRoleUser();
	//一、判断userid是否等于,on  如果等于,on则创建用户 然后得到 用户id和角色id 然后把2张表关联起来 保存到RoleUser表
	//二、如果不等于,on说明没有勾选中 则不创建用户 以及相关的关联
			if(tScEmp.getUserId().equals(",on")) {
				//赋值创建用户
				tsUser.setEmail(tScEmp.getEmail());
				tsUser.setMobilePhone(tScEmp.getMobilePhone());
				//对应真实姓名
				tsUser.setRealName(tScEmp.getName());
//				tsUser.setUserName(tScEmp.getShortName());
				//对应用户名
//				tsUser.setUserName(tScEmp.getShortName());
//				tsUser.setRealName(tsRole.getRoleName());
//				tsUser.setUserName(tScEmp.getName());
				tsUser.setPassword(PasswordUtil.encrypt(tsUser.getUserName(), password, PasswordUtil.getStaticSalt()));
				systemService.save(tsUser);
				//查询条件roleCode 对应的值sc_emp
				TSRole role = systemService.findUniqueByProperty(TSRole.class,"roleCode","sc_emp");
				//保存角色以及用户 把2张表关联起来
				tsRoleUser.setTSRole(role);
				tsRoleUser.setTSUser(tsUser);

				systemService.save(tsRoleUser);
				//最后把用户表的id赋给职员表的userid
				tScEmp.setUserId(tsUser.getId());
			}
			if(StringUtil.isNotEmpty(tScEmp.getName())) {
				String hql = "from TScEmpEntity where 1 = 1 AND number = ? and sonId = ?";
				List<TScEmpEntity> tScEmpEntityList = systemService.findHql(hql,new Object[]{tScEmp.getNumber(),depart.getId()});
				if ( tScEmpEntityList.size() >0) {
					message = "编号【"+ tScEmp.getNumber()+"】已存在";
					j.setSuccess(false);
					j.setMsg(message);
					return j;
				}
			}
			//新增时改变分类编号则改变其parentId
			String num = tScEmp.getNumber();
			int a = num.lastIndexOf(".");
			String number = num.substring(0, a);
			String sql = "select * from t_sc_item_type where number = '" + number + "' and item_class_id ='" + Globals.SC_BASEINFO_EMP_TYPE + "'";
			List<TScItemTypeEntity> list = systemService.findListbySql(sql, TScItemTypeEntity.class);
			//如果改变了分类则调用一次编号生成工具
			if(!tScEmp.getParentID().equals(list.get(0).getId())){
				BillNoGenerate.getBasicInfoBillNo(Globals.SC_BASEINFO_EMP_TYPE, number, number);
			}
			if(list.size()>0){
				tScEmp.setParentID(list.get(0).getId());
			}else{
				message = "分类编号【"+number+"】不存在";
				j.setSuccess(false);
				j.setMsg(message);
				return j;
			}
				tScEmp.setSonId(depart.getId());
				tScEmp.setCount(0);
				tScEmp.setLevel(tScItemTypeService.getLevel(tScEmp.getParentID()));
				tScEmp.setDisabled(Globals.ENABLE_CODE);
				tScEmp.setDeleted(Globals.ENABLE_CODE);
				tScEmpService.save(tScEmp);
			 	//根据name保存后修改shortname
				String userName=" from TScEmpEntity where name = ? order by create_date desc ";
				if(StringUtil.isNotEmpty(tScEmp.getName())){
					List<TScEmpEntity> tScEmpEntityList = systemService.findHql(userName, tScEmp.getName());
					if(!tScEmpEntityList.isEmpty() && tScEmpEntityList.size()>=2){
						for(int i=0;i<tScEmpEntityList.size();i++){
							String oldShortName = tScEmpEntityList.get(i).getShortName();
							tScEmpEntityList.get(i).setShortName(oldShortName+""+tScEmpEntityList.size());
							tsUser.setUserName(oldShortName+tScEmpEntityList.size());
							break;
						}
					}
				}
			//部门
			if(StringUtils.isNotEmpty(tScEmp.getDeptID())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_ADD_TYPE);
				countEntity.setOldId(tScEmp.getDeptID());
				boolean updateIsSuccess = tScDepartmentService.updateDepartmentCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料职员引用次数失败";
					j.setMsg(message);
					return j;
				}
			}

				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);

		}catch(Exception e){
			e.printStackTrace();
			message = "职员添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新职员
	 * 
	 * @param tScEmp
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScEmpEntity tScEmp, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "职员更新成功";
		TScEmpEntity t = tScEmpService.get(TScEmpEntity.class, tScEmp.getId());
		try {
			//当前分支机构
			TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart depart = systemService.getParentSonInfo(sonInfo);
			if(StringUtil.isNotEmpty(tScEmp.getName())) {
				String hql = "from TScEmpEntity where 1 = 1 AND number = ? and id != ? and sonId = ?";
				List<TScEmpEntity> tScEmpEntityList = systemService.findHql(hql, new Object[]{tScEmp.getNumber(),tScEmp.getId(),depart.getId()});
				if ( tScEmpEntityList.size() >0) {
					message = "编号【"+ tScEmp.getNumber()+"】已存在";
					j.setSuccess(false);
					j.setMsg(message);
					return j;
				}
			}
			//修改编号时，实现数据从分类A（被修改）移到分类B（修改后）
			if(! t.getNumber().equals(tScEmp.getNumber())){
				String num = tScEmp.getNumber();
				//获得修改后的分类编号
				int a = num.lastIndexOf(".");
				String number = num.substring(0, a);

				String sql = "select * from t_sc_item_type where number = '" + number + "' and item_class_id ='" + Globals.SC_BASEINFO_EMP_TYPE + "'";
				List<TScItemTypeEntity> list = systemService.findListbySql(sql, TScItemTypeEntity.class);
				if(list.size()>0){
					tScEmp.setParentID(list.get(0).getId());
				}else{
					message = "分类编号【"+number+"】不存在";
					j.setSuccess(false);
					j.setMsg(message);
					return j;
				}
			}

			MyBeanUtils.copyBeanNotNull2Bean(tScEmp, t);
			t.setSonId(depart.getId());
			tScEmpService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "职员更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 职员新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScEmpEntity tScEmp, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScEmp.getId())) {
			tScEmp = tScEmpService.getEntity(TScEmpEntity.class, tScEmp.getId());
			req.setAttribute("tScEmpPage", tScEmp);
		}
		String parentNo = req.getParameter("parentNo");
		req.setAttribute("number", BillNoGenerate.getBasicInfoBillNo(Globals.SC_BASEINFO_EMP_TYPE,parentNo,parentNo));
		req.setAttribute("parentID", tScEmp.getParentID());
		req.setAttribute("tranType", Globals.SC_BASEINFO_EMP_TYPE);//基础资料的编号 类型
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScEmp-add");
	}
	/**
	 * 职员编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScEmpEntity tScEmp, HttpServletRequest req) {
		//2张表关联查询name
		//String hql= "from TScDepartmentEntity where id = (select deptID from TScEmpEntity where id = ?)";
//		String sql="select * from t_sc_emp a,t_sc_department b where a.deptId = b.id and a.id = ?" ;

		//List<TScDepartmentEntity> departmentEntityList=this.tScEmpService.findHql(hql,tScEmp.getId());


			tScEmp = tScEmpService.getEntity(TScEmpEntity.class, tScEmp.getId());
			TScDepartmentEntity dept = systemService.getEntity(TScDepartmentEntity.class, tScEmp.getDeptID());
			if (null != dept) {
				req.setAttribute("deptIdName", dept.getName());
			}
		req.setAttribute("tranType", Globals.SC_BASEINFO_EMP_TYPE);//基础资料的编号 类型
		req.setAttribute("tScEmpPage", tScEmp);
		String load = req.getParameter("load");
		req.setAttribute("load",load);
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScEmp-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tScEmpController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScEmpEntity tScEmp,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScEmpEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScEmp, request.getParameterMap());
		List<TScEmpEntity> tScEmps = this.tScEmpService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"职员");
		modelMap.put(NormalExcelConstants.CLASS,TScEmpEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("职员列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, tScEmps);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScEmpEntity tScEmp,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "职员");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScEmpEntity.class);
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
				List<TScEmpEntity> listTScEmpEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TScEmpEntity.class,params);
				for (TScEmpEntity tScEmp : listTScEmpEntitys) {
					tScEmpService.save(tScEmp);
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
	 * 职员跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goselectdeptIdNameDialog")
	public ModelAndView goSelectDialog(TScEmpEntity tScEmp, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScEmp.getName())) {
			req.setAttribute("name", tScEmp.getName());
		}
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScEmpSelect");
	}

	/***
	 *
	 * @员工弹出框列表
	 */
	@RequestMapping(params = "goSelectEmpDialog")
     public ModelAndView goSelectEmpDialog(TScEmpEntity tScEmp, HttpServletRequest req){
		if (StringUtil.isNotEmpty(tScEmp.getName())) {
			req.setAttribute("name", tScEmp.getName());
		 }
		 return new ModelAndView("com/qihang/buss/sc/baseinfo/tScEmpSelect");
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
		TScEmpEntity  EmpEntity=systemService.get(TScEmpEntity.class,id);
		message="禁用成功";
		try {
			EmpEntity.setDisabled(Globals.DISABLED_CODE);
			tScEmpService.saveOrUpdate(EmpEntity);
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
		TScEmpEntity  EmpEntity=systemService.get(TScEmpEntity.class,id);
		message="启用成功";
		try {
			EmpEntity.setDisabled(Globals.ENABLE_CODE);
			tScEmpService.saveOrUpdate(EmpEntity);
		}catch (Exception e) {
			e.printStackTrace();
			message = "启用失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return  j;
	}

	/**
	 *
	 * 唯一性校验
	 *
	 */
	@RequestMapping(params = "checkName")
	@ResponseBody
	public AjaxJson checkName(String name, String load,String old_name){
		AjaxJson j = tScEmpService.checkName(name,load,old_name);
		return j;
	}
}
