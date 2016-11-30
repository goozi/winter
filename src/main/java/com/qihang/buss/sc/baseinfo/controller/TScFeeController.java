package com.qihang.buss.sc.baseinfo.controller;

import com.qihang.buss.sc.baseinfo.entity.TScFeeEntity;
import com.qihang.buss.sc.baseinfo.entity.TScItemTypeEntity;
import com.qihang.buss.sc.baseinfo.service.TScFeeServiceI;
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
 * @Description: 收支项目
 * @author onlineGenerator
 * @date 2016-07-08 11:34:35
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScFeeController")
public class TScFeeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScFeeController.class);

	@Autowired
	private TScFeeServiceI tScFeeService;
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
	 * 收支项目列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScFee")
	public ModelAndView tScFee(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScFeeList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScFeeEntity tScFee,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScFeeEntity.class, dataGrid);
		List<String> list = new ArrayList<String>();
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		if (StringUtil.isNotEmpty(tScFee.getParentID())) {
			list = tScItemTypeService.getParentIdFromTree("09", tScFee.getParentID(),depart.getId());
			tScFee.setParentID(null);
		}
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScFee, request.getParameterMap());
		try{
		//自定义追加查询条件
			if (list.size() > 0) {
				cq.in("parentID", list.toArray());
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScFeeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除收支项目
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScFeeEntity tScFee, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScFee = systemService.getEntity(TScFeeEntity.class, tScFee.getId());
		message = "收支项目删除成功";
		try{
			if(tScFee.getDisabled() == 1){
				message = "该信息已禁用，不能删除";
				j.setSuccess(true);
				j.setMsg(message);
				return j;
			} else {
				//判断单据是否被引用
				if (tScFee.getCount() != null && tScFee.getCount() > 0) {
					message = "该信息被其他数据引用，不能删除";
					j.setSuccess(true);
					j.setMsg(message);
					return j;
				} else {
					tScFeeService.delete(tScFee);
					systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "收支项目删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除收支项目
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "收支项目删除成功";
		try{
			for(String id:ids.split(",")){
				TScFeeEntity tScFee = systemService.getEntity(TScFeeEntity.class, 
				id
				);
				tScFeeService.delete(tScFee);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "收支项目删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加收支项目
	 * 
	 * @param tScFee
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScFeeEntity tScFee, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "收支项目添加成功";
		try{
			TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart depart = systemService.getParentSonInfo(sonInfo);
			if(StringUtil.isNotEmpty(tScFee.getName())) {
				String hql = "from TScFeeEntity where 1 = 1 AND number = ? and sonId = ?";
				List<TScFeeEntity> tScFeeEntityList = systemService.findHql(hql, new Object[]{tScFee.getNumber(),depart.getId()});
				if ( tScFeeEntityList.size() >0) {
					message = "编号【"+ tScFee.getNumber()+"】已存在";
					j.setSuccess(false);
					j.setMsg(message);
					return j;
				}
			}
			//新增时改变分类编号则改变其parentId
			String num = tScFee.getNumber();
			int a = num.lastIndexOf(".");
			String number = num.substring(0, a);
			String sql = "select * from t_sc_item_type where number = '" + number + "' and item_class_id ='" + Globals.SC_BASEINFO_FEE_TYPE + "'";
			List<TScItemTypeEntity> list = systemService.findListbySql(sql, TScItemTypeEntity.class);
			//如果改变了分类则调用一次编号生成工具
			if(!tScFee.getParentID().equals(list.get(0).getId())){
				BillNoGenerate.getBasicInfoBillNo(Globals.SC_BASEINFO_FEE_TYPE, number, number);
			}
			if(list.size()>0){
				tScFee.setParentID(list.get(0).getId());
			}else{
				message = "分类编号【"+number+"】不存在";
				j.setSuccess(false);
				j.setMsg(message);
				return j;
			}
			tScFee.setSonId(depart.getId());
			tScFee.setLevel(tScItemTypeService.getLevel(tScFee.getParentID()));
			tScFee.setDisabled(Globals.ENABLE_CODE);
			tScFee.setDeleted(Globals.ENABLE_CODE);
			tScFee.setCount(0);
			tScFeeService.save(tScFee);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "收支项目添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新收支项目
	 * 
	 * @param tScFee
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScFeeEntity tScFee, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "收支项目更新成功";
		TScFeeEntity t = tScFeeService.get(TScFeeEntity.class, tScFee.getId());
		try {
			TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart depart = systemService.getParentSonInfo(sonInfo);
			if(StringUtil.isNotEmpty(tScFee.getName())) {
				String hql = "from TScFeeEntity where 1 = 1 AND number = ? and id != ? and sonId = ?";
				List<TScFeeEntity> tScFeeEntityList = systemService.findHql(hql, new Object[]{tScFee.getNumber(),tScFee.getId(),depart.getId()});
				if ( tScFeeEntityList.size() >0) {
					message = "编号【"+ tScFee.getNumber()+"】已存在";
					j.setSuccess(false);
					j.setMsg(message);
					return j;
				}
			}
			//修改编号时，实现数据从分类A（被修改）移到分类B（修改后）
			if(! t.getNumber().equals(tScFee.getNumber())){
				String num = tScFee.getNumber();
				//获得修改后的分类编号
				int a = num.lastIndexOf(".");
				String number = num.substring(0, a);

				String sql = "select * from t_sc_item_type where number = '" + number + "' and item_class_id ='" + Globals.SC_BASEINFO_FEE_TYPE + "'";
				List<TScItemTypeEntity> list = systemService.findListbySql(sql, TScItemTypeEntity.class);
				if(list.size()>0){
					tScFee.setParentID(list.get(0).getId());
				}else{
					message = "分类编号【"+number+"】不存在";
					j.setSuccess(false);
					j.setMsg(message);
					return j;
				}
			}
			MyBeanUtils.copyBeanNotNull2Bean(tScFee, t);
			t.setSonId(depart.getId());
			tScFeeService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "收支项目更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 收支项目新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScFeeEntity tScFee, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScFee.getId())) {
			tScFee = tScFeeService.getEntity(TScFeeEntity.class, tScFee.getId());
			req.setAttribute("tScFeePage", tScFee);
		}
		String parentNo = req.getParameter("parentNo");

		req.setAttribute("number", BillNoGenerate.getBasicInfoBillNo(Globals.SC_BASEINFO_FEE_TYPE,parentNo,parentNo));
		req.setAttribute("parentID", tScFee.getParentID());
		req.setAttribute("tranType", Globals.SC_BASEINFO_FEE_TYPE);//基础资料的编号 类型

		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScFee-add");
	}
	/**
	 * 收支项目编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScFeeEntity tScFee, HttpServletRequest req) {
		String load = req.getParameter("load");
		if (StringUtil.isNotEmpty(tScFee.getId())) {
			tScFee = tScFeeService.getEntity(TScFeeEntity.class, tScFee.getId());
			req.setAttribute("tScFeePage", tScFee);
		}
		req.setAttribute("tranType", Globals.SC_BASEINFO_FEE_TYPE);//基础资料的编号 类型
		req.setAttribute("load",load);

		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScFee-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tScFeeController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScFeeEntity tScFee,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScFeeEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScFee, request.getParameterMap());
		List<TScFeeEntity> tScFees = this.tScFeeService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"收支项目");
		modelMap.put(NormalExcelConstants.CLASS,TScFeeEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("收支项目列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, tScFees);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScFeeEntity tScFee,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "收支项目");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScFeeEntity.class);
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
				List<TScFeeEntity> listTScFeeEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TScFeeEntity.class,params);
				for (TScFeeEntity tScFee : listTScFeeEntitys) {
					tScFeeService.save(tScFee);
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
		TScFeeEntity  FeeEntity=systemService.get(TScFeeEntity.class,id);
		message="禁用成功";
		try {
			FeeEntity.setDisabled(Globals.DISABLED_CODE);
			tScFeeService.saveOrUpdate(FeeEntity);
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
		TScFeeEntity FeeEntity=systemService.get(TScFeeEntity.class,id);
		message="启用成功";
		try {
			FeeEntity.setDisabled(Globals.ENABLE_CODE);
			tScFeeService.saveOrUpdate(FeeEntity);
		}catch (Exception e) {
			e.printStackTrace();
			message = "启用失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return  j;
	}

	/***
	 *
	 * @员工弹出框列表
	 */
	@RequestMapping(params = "goSelectEmpDialog")
	public ModelAndView goSelectEmpDialog(TScFeeEntity tScFee, HttpServletRequest req){
		if (StringUtil.isNotEmpty(tScFee.getName())) {
			req.setAttribute("name", tScFee.getName());
		}
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScFeeSelect");
	}

	/**
	 *
	 * 唯一性校验
	 *
	 */
	@RequestMapping(params = "checkName")
	@ResponseBody
	public AjaxJson checkName(String name, String load,String old_name){
		AjaxJson j = tScFeeService.checkName(name,load,old_name);
		return j;
	}
}
