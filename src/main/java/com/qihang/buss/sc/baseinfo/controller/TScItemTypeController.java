package com.qihang.buss.sc.baseinfo.controller;

import com.qihang.buss.sc.baseinfo.entity.TScItemTypeEntity;
import com.qihang.buss.sc.baseinfo.entity.TScSoncompanyEntity;
import com.qihang.buss.sc.baseinfo.service.TScItemTypeServiceI;
import com.qihang.buss.sc.baseinfo.service.TScSoncompanyServiceI;
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
import java.util.List;
import java.util.Map;


/**
 * @Title: Controller
 * @Description: 基础资料分类表
 * @author onlineGenerator
 * @date 2016-06-07 13:51:38
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScItemTypeController")
public class TScItemTypeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScItemTypeController.class);

	@Autowired
	private TScItemTypeServiceI tScItemTypeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TScSoncompanyServiceI tScSoncompanyServiceI;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public static  String FIELD_ITEM_CLASSID = "itemClassId";
	public static String FIELD_PARENT_ID = "parentId";
	/**
	 * 基础资料分类表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScItemType")
	public ModelAndView tScItemType(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScItemTypeList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScItemTypeEntity tScItemType,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScItemTypeEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScItemType, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScItemTypeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除基础资料分类表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScItemTypeEntity tScItemType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScItemType = systemService.getEntity(TScItemTypeEntity.class, tScItemType.getId());
		message = "基础资料分类表删除成功";
		try{
			List list = tScItemTypeService.selectChildren(tScItemType.getId());
			if(list.size() > 0){
				message = "该分类下有子分类，禁止删除！！！";
				j.setMsg(message);
				j.setSuccess(false);
				return  j;
			}
			//控制分类下有数据不可删除
			//收支项目
			String hql="from TScFeeEntity where parentID = ? ";
			if(StringUtil.isNotEmpty(tScItemType.getId())){
				List<TScItemTypeEntity> tScItemTypeEntityList= systemService.findHql(hql,tScItemType.getId());
				if (tScItemTypeEntityList.size()>0){
					message="该分类下有数据，禁止删除！！！";
					j.setMsg(message);
					j.setSuccess(false);
					return j;
				}
			}
			//商品信息
			String icitemhql="from TScIcitemEntity where parentID = ? ";
			if(StringUtil.isNotEmpty(tScItemType.getId())){
				List<TScItemTypeEntity> tScItemTypeEntityList= systemService.findHql(icitemhql,tScItemType.getId());
				if (tScItemTypeEntityList.size()>0){
					message="该分类下有数据，禁止删除！！！";
					j.setMsg(message);
					j.setSuccess(false);
					return j;
				}
			}
			//职员信息
			String emphql="from TScEmpEntity where parentID = ? ";
			if(StringUtil.isNotEmpty(tScItemType.getId())){
				List<TScItemTypeEntity> tScItemTypeEntityList= systemService.findHql(emphql,tScItemType.getId());
				if (tScItemTypeEntityList.size()>0){
					message="该分类下有数据，禁止删除！！！";
					j.setMsg(message);
					j.setSuccess(false);
					return j;
				}
			}
			//供应商信息
			String supplierhql="from TScSupplierEntity where parentID = ? ";
			if(StringUtil.isNotEmpty(tScItemType.getId())){
				List<TScItemTypeEntity> tScItemTypeEntityList= systemService.findHql(supplierhql,tScItemType.getId());
				if (tScItemTypeEntityList.size()>0){
					message="该分类下有数据，禁止删除！！！";
					j.setMsg(message);
					j.setSuccess(false);
					return j;
				}
			}
			//仓库信息
			String stockhql="from TScStockEntity where parentID = ? ";
			if(StringUtil.isNotEmpty(tScItemType.getId())){
				List<TScItemTypeEntity> tScItemTypeEntityList= systemService.findHql(stockhql,tScItemType.getId());
				if (tScItemTypeEntityList.size()>0){
					message="该分类下有数据，禁止删除！！！";
					j.setMsg(message);
					j.setSuccess(false);
					return j;
				}
			}
			//客户信息
			String organizationhql="from TScOrganizationEntity where parentID = ? ";
			if(StringUtil.isNotEmpty(tScItemType.getId())){
				List<TScItemTypeEntity> tScItemTypeEntityList= systemService.findHql(organizationhql,tScItemType.getId());
				if (tScItemTypeEntityList.size()>0){
					message="该分类下有数据，禁止删除！！！";
					j.setMsg(message);
					j.setSuccess(false);
					return j;
				}
			}
			//物流公司信息
			String logisticalhql="from TScLogisticalEntity where parentID = ? ";
			if(StringUtil.isNotEmpty(tScItemType.getId())){
				List<TScItemTypeEntity> tScItemTypeEntityList= systemService.findHql(logisticalhql,tScItemType.getId());
				if (tScItemTypeEntityList.size()>0){
					message="该分类下有数据，禁止删除！！！";
					j.setMsg(message);
					j.setSuccess(false);
					return j;
				}
			}
			//部门信息
			String departmenthql="from TScDepartmentEntity where parentID = ? ";
			if(StringUtil.isNotEmpty(tScItemType.getId())){
				List<TScItemTypeEntity> tScItemTypeEntityList= systemService.findHql(departmenthql,tScItemType.getId());
				if (tScItemTypeEntityList.size()>0){
					message="该分类下有数据，禁止删除！！！";
					j.setMsg(message);
					j.setSuccess(false);
					return j;
				}
			}
			tScItemTypeService.delete(tScItemType);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "基础资料分类表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除基础资料分类表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "基础资料分类表删除成功";
		try{
			for(String id:ids.split(",")){
				TScItemTypeEntity tScItemType = systemService.getEntity(TScItemTypeEntity.class, 
				id
				);
				tScItemTypeService.delete(tScItemType);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "基础资料分类表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加基础资料分类表
	 * 
	 * @param tScItemType
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScItemTypeEntity tScItemType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "基础资料分类表添加成功";
		try{
			if(StringUtil.isNotEmpty(tScItemType.getName())){
				String hql = "from TScItemTypeEntity where name = ? and itemClassId=?";
				String hqlNumber = "from TScItemTypeEntity where number = ? and itemClassId=?";
				List<TScItemTypeEntity> tScItemTypeEntityList = systemService.findHql(hql,tScItemType.getName(),tScItemType.getItemClassId());
				List<TScItemTypeEntity> tScItemTypeEntityListN = systemService.findHql(hqlNumber,tScItemType.getNumber(),tScItemType.getItemClassId());
				if(!tScItemTypeEntityList.isEmpty() && tScItemTypeEntityList.size()>=1) {
					message = "基础资料分类表已有同名，请重新输入";
					j.setSuccess(false);
				}else if(!tScItemTypeEntityListN.isEmpty() && tScItemTypeEntityListN.size()>=1) {
					message = "基础资料分类表已有相同编号，请重新输入";
					j.setSuccess(false);
				} else {
					if (!tScItemType.getParentId().equals(Globals.PARENT_ID)) {
						tScItemType.setLevel(tScItemTypeService.getLevel(tScItemType.getParentId()) + 1);
					}
					//设置分类的分支机构
					TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
					TSDepart depart = systemService.getParentSonInfo(sonInfo);
					tScItemType.setSonId(depart.getId());
					tScItemTypeService.save(tScItemType);
					if (tScItemType.getItemClassId() == Globals.SON_COMPANY) {
						TScSoncompanyEntity tScSoncompanyEntity = new TScSoncompanyEntity();
						tScSoncompanyEntity.setName(tScItemType.getName());
						tScSoncompanyEntity.setNumber(tScItemType.getNumber());
						tScSoncompanyEntity.setParentID(tScItemType.getParentId());
						tScSoncompanyEntity.setShortName(PinyinUtil.converterToFirstSpell(tScItemType.getName()));
						tScSoncompanyEntity.setLevel(tScItemType.getLevel());
						tScSoncompanyEntity.setDisabled(tScItemType.getDeleted());
						tScSoncompanyEntity.setDeleted(tScItemType.getDeleted());
						tScSoncompanyEntity.setCount(tScItemType.getCount());
						tScSoncompanyServiceI.save(tScSoncompanyEntity);
					}
				}
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "基础资料分类表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新基础资料分类表
	 * 
	 * @param tScItemType
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScItemTypeEntity tScItemType, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "基础资料分类表更新成功";
		TScItemTypeEntity t = tScItemTypeService.get(TScItemTypeEntity.class, tScItemType.getId());
		try {
			if(StringUtil.isNotEmpty(tScItemType.getName())) {
				String hql = "from TScItemTypeEntity where name = ? and id != ? and itemClassId=?";
				List<TScItemTypeEntity> tScItemTypeEntityList = systemService.findHql(hql, tScItemType.getName(),tScItemType.getId(),tScItemType.getItemClassId());
				String hqlNumber = "from TScItemTypeEntity where number = ? and id != ? and itemClassId=?";
				List<TScItemTypeEntity> numberList = systemService.findHql(hqlNumber, tScItemType.getNumber(),tScItemType.getId(),tScItemType.getItemClassId());
				if (tScItemTypeEntityList.size() > 0) {
					message = "分类名称【"+tScItemType.getName()+"】已存在";
					j.setSuccess(false);
					j.setMsg(message);
					return j;
				}
				if(numberList.size() > 0){
					message = "编号【"+tScItemType.getNumber()+"】已存在";
					j.setSuccess(false);
					j.setMsg(message);
					return j;
				}
				MyBeanUtils.copyBeanNotNull2Bean(tScItemType, t);
				//设置分类的分支机构
				TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
				TSDepart depart = systemService.getParentSonInfo(sonInfo);
				t.setSonId(depart.getId());
				tScItemTypeService.saveOrUpdate(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}
		} catch (Exception e) {
			e.printStackTrace();
			message = "基础资料分类表更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 基础资料分类表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScItemTypeEntity tScItemType, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScItemType.getId())) {
			tScItemType = tScItemTypeService.getEntity(TScItemTypeEntity.class, tScItemType.getId());
			req.setAttribute("tScItemTypePage", tScItemType);
		}
		String parentNo = req.getParameter("parentNo");
		req.setAttribute("itemClassId", tScItemType.getItemClassId());
		req.setAttribute("parentId", tScItemType.getParentId());
		req.setAttribute("number", BillNoGenerate.getBasicBillNo(tScItemType.getParentId(),parentNo));
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScItemType-add");
	}
	/**
	 * 基础资料分类表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScItemTypeEntity tScItemType, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScItemType.getId())) {
			tScItemType = tScItemTypeService.getEntity(TScItemTypeEntity.class, tScItemType.getId());
			req.setAttribute("tScItemTypePage", tScItemType);
		}
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tScItemType-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tScItemTypeController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScItemTypeEntity tScItemType,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScItemTypeEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScItemType, request.getParameterMap());
		List<TScItemTypeEntity> tScItemTypes = this.tScItemTypeService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"基础资料分类表");
		modelMap.put(NormalExcelConstants.CLASS,TScItemTypeEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("基础资料分类表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, tScItemTypes);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScItemTypeEntity tScItemType,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "基础资料分类表");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScItemTypeEntity.class);
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
				List<TScItemTypeEntity> listTScItemTypeEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TScItemTypeEntity.class,params);
				for (TScItemTypeEntity tScItemType : listTScItemTypeEntitys) {
					tScItemTypeService.save(tScItemType);
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
	 * itemClassId : 基础资料类型
	 * 01	客户,02	供应商,03	物流公司,04	商品,05	职员,06	部门,07	仓库,
	 * 08	单位,09	收支项目,10	结算账户,11	辅助属性,12	辅助资料,13	分支机构
	 */
	@RequestMapping(params = "getItemTypeTree")
	@ResponseBody
	public List<TScItemTypeEntity> getItemTypeTree(@RequestParam("itemClassId") String itemClassId){
		CriteriaQuery criteriaQuery = new CriteriaQuery(TScItemTypeEntity.class);
		criteriaQuery.eq(FIELD_ITEM_CLASSID,itemClassId);

		criteriaQuery.eq(FIELD_PARENT_ID, Globals.PARENT_ID);
		criteriaQuery.add();
		List<TScItemTypeEntity>  list  = tScItemTypeService.getListByCriteriaQuery(criteriaQuery, false);

		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		String sonId = depart.getId();
		if("04".equals(itemClassId)){
			sonId = null;
		}
		List<TScItemTypeEntity> itemTypeTree = tScItemTypeService.getItemTypeTree(itemClassId,list.get(0).getId(),sonId);

		return itemTypeTree;
	}
}
