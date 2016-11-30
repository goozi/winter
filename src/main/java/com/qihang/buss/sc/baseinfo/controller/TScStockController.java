package com.qihang.buss.sc.baseinfo.controller;

import com.qihang.buss.sc.baseinfo.entity.TScEmpEntity;
import com.qihang.buss.sc.baseinfo.entity.TScItemTypeEntity;
import com.qihang.buss.sc.baseinfo.entity.TScStockEntity;
import com.qihang.buss.sc.baseinfo.service.*;
import com.qihang.buss.sc.sales.entity.TScCountEntity;
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
import com.qihang.winter.web.system.pojo.base.TSRole;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;


/**
 * @Title: Controller
 * @Description: 仓库
 * @author onlineGenerator
 * @date 2016-06-28 10:25:47
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tBiStockController")
public class TScStockController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScStockController.class);

	@Autowired
	private TScStockServiceI tScStockService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TScItemTypeServiceI tScItemTypeService;
	@Autowired
	private TScOrganizationServiceI tScOrganizationService;
	@Autowired
	private TScEmpServiceI tScEmpServiceI;
	private String message;

	@Autowired
	private CountCommonServiceI countCommonService;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 仓库列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tBiStock")
	public ModelAndView tBiStock(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tBiStockList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScStockEntity tBiStock,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//判断是否进行查询
		String name = tBiStock.getName();
		Boolean isSelect = false;
		if(StringUtils.isNotEmpty(name)) {
			if (name.indexOf("_") > -1) {
				isSelect = true;
				name = name.replace("_", "");
				tBiStock.setName("");
			}
		}
		CriteriaQuery cq = new CriteriaQuery(TScStockEntity.class, dataGrid);
		List<String> list = new ArrayList<String>();
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		if(StringUtil.isNotEmpty(tBiStock.getParentID())){
			list =tScItemTypeService.getParentIdFromTree("07", String.valueOf(tBiStock.getParentID()),depart.getId());
			tBiStock.setParentID(null);
		}
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBiStock, request.getParameterMap());
		try{
		//自定义追加查询条件
			if(list.size() > 0){
				cq.in("parentID", list.toArray());
			}
			//如若弹窗查询
			if(isSelect){
				cq.add(Restrictions.or(
						Restrictions.like("name","%"+name+"%"),
						Restrictions.or(
								Restrictions.like("number","%"+name+"%"),
								Restrictions.or(
										Restrictions.like("shortNumber","%"+name+"%"),Restrictions.like("shortName","%"+name+"%")))));
			}
			cq.eq("sonID", ResourceUtil.getSessionUserName().getCurrentDepart().getId());
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScStockService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除仓库
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScStockEntity tBiStock, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tBiStock = systemService.getEntity(TScStockEntity.class, tBiStock.getId());
		message = "仓库删除成功";
		try{
			//更新引用的 次数
			countCommonService.deleteUpdateCount("T_SC_Organization",tBiStock.getEmpID());
			tScStockService.delete(tBiStock);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "仓库删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除仓库
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "仓库删除成功";
		try{
			for(String id:ids.split(",")){
				TScStockEntity tBiStock = systemService.getEntity(TScStockEntity.class, 
				id
				);
				tScStockService.delete(tBiStock);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "仓库删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加仓库
	 * 
	 * @param tBiStock
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScStockEntity tBiStock, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "仓库添加成功";
		tBiStock.setDisabled(Globals.ENABLE_CODE);
		try{
			if(StringUtil.isNotEmpty(tBiStock.getNumber())) {
				String hql = "from TScStockEntity where 1 = 1 AND number = ? ";
				List<TScStockEntity> tScStockEntityList = systemService.findHql(hql, tBiStock.getNumber());
				if ( tScStockEntityList.size() >0) {
					message = "编号【"+ tBiStock.getNumber()+"】已存在";
					j.setSuccess(false);
					j.setMsg(message);
					return j;
				}
			}
			if(StringUtil.isNotEmpty(tBiStock.getName())) {
				String hql = "from TScStockEntity where 1 = 1 AND name = ? ";
				List<TScStockEntity> tScStockEntityList = systemService.findHql(hql, tBiStock.getName());
				if ( tScStockEntityList.size() >0) {
					message = "名称【"+ tBiStock.getName()+"】已存在";
					j.setSuccess(false);
					j.setMsg(message);
					return j;
				}
			}
			//新增时改变分类编号则改变其parentId
			String num = tBiStock.getNumber();
			int a = num.lastIndexOf(".");
			String number = num.substring(0, a);
			String sql = "select * from t_sc_item_type where number = '" + number + "' and item_class_id ='" + Globals.SC_BASEINFO_STOCK_TYPE + "'";
			List<TScItemTypeEntity> list = systemService.findListbySql(sql, TScItemTypeEntity.class);
			//如果改变了分类则调用一次编号生成工具
			if(!tBiStock.getParentID().equals(list.get(0).getId())){
				BillNoGenerate.getBasicInfoBillNo(Globals.SC_BASEINFO_STOCK_TYPE, number, number);
			}
			if(list.size()>0){
				tBiStock.setParentID(list.get(0).getId());
			}else{
				message = "分类编号【"+number+"】不存在";
				j.setSuccess(false);
				j.setMsg(message);
				return j;
			}

			tBiStock.setCount(0);
			tScStockService.save(tBiStock);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "仓库添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新仓库
	 * 
	 * @param tBiStock
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScStockEntity tBiStock, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "仓库更新成功";
		TScStockEntity t = tScStockService.get(TScStockEntity.class, tBiStock.getId());
		try {
			if(StringUtil.isNotEmpty(t.getEmpID()) && !(t.getEmpID().equals(tBiStock.getEmpID()))){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_UPDATE_TYPE);
				countEntity.setOldId(t.getEmpID());
				countEntity.setNewId(tBiStock.getEmpID());
				boolean updateIsSuccess = tScOrganizationService.updateOrganizationCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料仓库引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			if(StringUtil.isNotEmpty(tBiStock.getName())) {
				String hql = "from TScStockEntity where 1 = 1 AND number = ? and id != ?";
				List<TScStockEntity> tScStockEntityList = systemService.findHql(hql, tBiStock.getNumber(), tBiStock.getId());
				if ( tScStockEntityList.size() >0) {
					message = "编号【"+ tBiStock.getNumber()+"】已存在";
					j.setSuccess(false);
					j.setMsg(message);
					return j;
				}
			}
			//修改编号时，实现数据从分类A（被修改）移到分类B（修改后）
			if(! t.getNumber().equals(tBiStock.getNumber())){
				String num = tBiStock.getNumber();
				//获得修改后的分类编号
				int a = num.lastIndexOf(".");
				String number = num.substring(0, a);

				String sql = "select * from t_sc_item_type where number = '" + number + "' and item_class_id ='" + Globals.SC_BASEINFO_STOCK_TYPE + "'";
				List<TScItemTypeEntity> list = systemService.findListbySql(sql, TScItemTypeEntity.class);
				if(list.size()>0){
					tBiStock.setParentID(list.get(0).getId());
				}else{
					message = "分类编号【"+number+"】不存在";
					j.setSuccess(false);
					j.setMsg(message);
					return j;
				}
			}

			MyBeanUtils.copyBeanNotNull2Bean(tBiStock, t);
			tScStockService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "仓库更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 仓库新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScStockEntity tBiStock, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBiStock.getId())) {
			tBiStock = tScStockService.getEntity(TScStockEntity.class, tBiStock.getId());
			req.setAttribute("tBiStockPage", tBiStock);
		}
		String parentNo = req.getParameter("parentNo");

		req.setAttribute("number", BillNoGenerate.getBasicInfoBillNo(Globals.SC_BASEINFO_STOCK_TYPE,parentNo,parentNo));
		req.setAttribute("parentID", tBiStock.getParentID());
		req.setAttribute("tranType", Globals.SC_BASEINFO_STOCK_TYPE);//基础资料的编号 类型

		//获取分支机构
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		req.setAttribute("sonId",depart.getId());
		req.setAttribute("sonName",depart.getDepartname());

		return new ModelAndView("com/qihang/buss/sc/baseinfo/tBiStock-add");
	}
	/**
	 * 仓库编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScStockEntity tBiStock, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tBiStock.getId())) {
			tBiStock = tScStockService.getEntity(TScStockEntity.class, tBiStock.getId());
			req.setAttribute("tBiStockPage", tBiStock);
		}
		if(StringUtils.isNotEmpty(tBiStock.getEmpID())){
			TScEmpEntity scEmpEntity = tScEmpServiceI.get(TScEmpEntity.class, tBiStock.getEmpID());
			req.setAttribute("empName", scEmpEntity.getName());
		}
		req.setAttribute("tranType", Globals.SC_BASEINFO_STOCK_TYPE);//基础资料的编号 类型
		String load = req.getParameter("load");
		req.setAttribute("load",load);
		return new ModelAndView("com/qihang/buss/sc/baseinfo/tBiStock-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "tBiStockController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScStockEntity tBiStock,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScStockEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tBiStock, request.getParameterMap());
		List<TScStockEntity> tBiStocks = this.tScStockService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"仓库");
		modelMap.put(NormalExcelConstants.CLASS,TScStockEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("仓库列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, tBiStocks);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScStockEntity tBiStock,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "仓库");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScStockEntity.class);
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
				List<TScStockEntity> listTScStockEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TScStockEntity.class,params);
				for (TScStockEntity tBiStock : listTScStockEntitys) {
					tScStockService.save(tBiStock);
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
	 * @param tScStockEntity
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "doDisable")
	@ResponseBody
	public AjaxJson doDisable(TScStockEntity tScStockEntity, HttpServletRequest req){
		AjaxJson j = new AjaxJson();
		String message = "操作成功";
		try{
			for(String id:tScStockEntity.getId().split(",")){
				TScStockEntity scStockEntity = systemService.getEntity(TScStockEntity.class, id);
				if(tScStockEntity.getDisabled() == Globals.ENABLE_CODE){
					if(tScStockEntity.getDisabled() == scStockEntity.getDisabled()){
						message = "已启动";
					}else{
						scStockEntity.setDisabled(Globals.ENABLE_CODE);
					}
				}else{
					if(tScStockEntity.getDisabled() == scStockEntity.getDisabled()){
						message = "已禁用";
					}else{
						scStockEntity.setDisabled(Globals.DISABLED_CODE);
					}
				}
				tScStockService.saveOrUpdate(scStockEntity);
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
     * 仓库弹出框列表
     * @param tScStockEntity
     * @param req
     * @return
     */
    @RequestMapping(params = "goSelectDialog")
    public ModelAndView goSelectDialog(TScStockEntity tScStockEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tScStockEntity.getName())) {
            req.setAttribute("name", tScStockEntity.getName());
        }
        return new ModelAndView("com/qihang/buss/sc/baseinfo/tScStockSelect");
    }

	@RequestMapping(params = "getStockTree")
	@ResponseBody
	public List<Map<String,Object>> getStockTree(HttpServletRequest request,HttpSession session){
		CriteriaQuery criteriaQuery = new CriteriaQuery(TScItemTypeEntity.class);
		criteriaQuery.eq("itemClassId","07");//仓库分类

		criteriaQuery.eq("parentId", Globals.PARENT_ID);
		criteriaQuery.add();
		List<TScItemTypeEntity>  list  = tScItemTypeService.getListByCriteriaQuery(criteriaQuery, false);
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		List<TScItemTypeEntity> itemTypeTree = tScItemTypeService.getItemTypeTree("07", list.get(0).getId(),depart.getId());

		List<Map<String,Object>> treeInfo = new ArrayList<Map<String, Object>>();
		String isClose = request.getParameter("isClose");
		//当前用户所在分支机构
		//TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		//TSDepart depart = systemService.getParentSonInfo(sonInfo);
		String sonId = "";
		//当前登录用户角色
		String roleIds = (String) session.getAttribute("roleIds");//单前登录用户
		String roleId = "";
		String[] roleIdArr = roleIds.split(",");
		for(String rid : roleIdArr){
			roleId += "'"+rid+"',";
		}
		if(StringUtil.isNotEmpty(roleId)){
			roleId = roleId.substring(0,roleId.length()-1);
		}
		List<TSRole> roleList = systemService.findHql("from TSRole where id in (" + roleId + ")");
		//判断用户是否可以查看下级分支机构数据角色
		Boolean hasChild = true;
		if(roleList.size() > 0){
			for(TSRole role : roleList){
				if("sc_info_one".equals(role.getRoleCode())){
					hasChild = false;
					break;
				}
			}
		}
		if(hasChild){
			Set<String> sonIdArr = systemService.getAllSonId(depart.getId());
			for(String id : sonIdArr){
				sonId += "'"+id+"',";
			}
			sonId = sonId.substring(0,sonId.length()-1);
		} else {
			sonId = "'"+depart.getId()+"'";
		}
		for(TScItemTypeEntity itemType : itemTypeTree){
			if(0 ==  itemType.getDeleted()) {
				setTreeInfo(itemType, treeInfo,isClose,sonId,hasChild);
			}
		}
		return treeInfo;
	}

	/**
	 * 仓库功能迭代
	 * @param itemType 分类类型
	 * @param treeInfo 仓库树数据集
	 * @param isClose 是否合并
	 * @param sonId 分支机构id
	 * @param hasChild 是否查看下级分支机构
	 */
	public void setTreeInfo(TScItemTypeEntity itemType,List<Map<String,Object>> treeInfo,String isClose,String sonId,Boolean hasChild){
		Map<String,Object> childinfo = new HashMap<String, Object>();
		childinfo.put("id",itemType.getId());
		childinfo.put("text", itemType.getName());
		if(StringUtils.isNotEmpty(isClose)){
			childinfo.put("state","closed");
		}
		List<Map<String,Object>> children = new ArrayList<Map<String, Object>>();
		if(null != itemType.getChildren() && itemType.getChildren().size() > 0){
			for(TScItemTypeEntity childItemType : itemType.getChildren()){
				setTreeInfo(childItemType,children,isClose,sonId,hasChild);
			}
			if(children.size() > 0) {
				childinfo.put("children", children);
			}
		}
		List<TScStockEntity> stockEntityList = tScStockService.findHql("from TScStockEntity where parentID = ? and sonId in ("+sonId+")",new Object[]{itemType.getId()});
		if(stockEntityList.size() > 0){
			for(TScStockEntity stock : stockEntityList){
				Map<String, Object> leafMap = new HashMap<String, Object>();
				leafMap.put("id", stock.getId());
				leafMap.put("text", stock.getName());
				children.add(leafMap);
			}
			if(children.size() > 0) {
				childinfo.put("children", children);
			} else {
				childinfo = null;
			}
		}
		if(null != childinfo) {
			treeInfo.add(childinfo);
		}
	}
}
