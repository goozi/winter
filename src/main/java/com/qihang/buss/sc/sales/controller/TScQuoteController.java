
package com.qihang.buss.sc.sales.controller;
import com.qihang.buss.sc.baseinfo.entity.*;
import com.qihang.buss.sc.distribution.entity.TScPrcplyViewEntityEntity;
import com.qihang.buss.sc.distribution.entity.TScPromotionViewEntity;
import com.qihang.buss.sc.distribution.entity.TScPromotiongiftsentryEntity;
import com.qihang.buss.sc.distribution.service.TScPrcplyServiceI;
import com.qihang.buss.sc.distribution.service.TScPromotionServiceI;
import com.qihang.buss.sc.sales.entity.*;
import com.qihang.buss.sc.sales.service.TScQuoteServiceI;
import com.qihang.buss.sc.sales.page.TScQuotePage;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
import com.qihang.buss.sc.util.AccountUtil;
import com.qihang.buss.sc.util.BillNoGenerate;
import com.qihang.buss.sc.util.PriceUtil;
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
import com.qihang.winter.poi.excel.entity.result.ExcelImportResult;
import com.qihang.winter.poi.excel.entity.vo.NormalExcelConstants;
import com.qihang.winter.poi.excel.entity.vo.TemplateExcelConstants;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.tag.vo.datatable.SortDirection;
import com.qihang.winter.web.system.pojo.base.TSBaseUser;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.web.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
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

import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Title: Controller
 * @Description: 销售报价单
 * @author onlineGenerator
 * @date 2016-06-16 15:22:17
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScQuoteController")
public class TScQuoteController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScQuoteController.class);

	@Autowired
	private TScQuoteServiceI tScQuoteService;
	@Autowired
	private SystemService systemService;

	@Autowired
	private TScPromotionServiceI promotionServiceI;

	@Autowired
	private TScPrcplyServiceI prcplyServiceI;

	@Autowired
	private UserService userService;

	/**
	 * 销售报价单列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tScQuote")
	public ModelAndView tScQuote(HttpServletRequest request) {
//		return new ModelAndView("com/qihang/buss/sc/sales/tScQuoteList");
		Date checkDate = AccountUtil.getAccountStartDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(checkDate);
		request.setAttribute("checkDate",dateStr);
		return new ModelAndView("com/qihang/buss/sc/sales/tScQuoteViewList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScQuoteViewEntity tScQuote,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
//		//若分支机构为空，默认当期登录用户分支机构；
//		if(StringUtil.isEmpty(tScQuote.getSonId())){
//			TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
//			String parentSonId = systemService.getParentSonId(sonInfo);
//			tScQuote.setSonId(parentSonId);
//		}
		CriteriaQuery cq = new CriteriaQuery(TScQuoteViewEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScQuote);
		try{
		//自定义追加查询条件
		String query_date_begin = request.getParameter("date_begin");
		String query_date_end = request.getParameter("date_end");
		if(StringUtil.isNotEmpty(query_date_begin)){
			cq.ge("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_begin));
		}
		if(StringUtil.isNotEmpty(query_date_end)){
			cq.le("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_end));
		}
		cq.addOrder("cancellation",SortDirection.asc);
		//	String sonId = ResourceUtil.getSessionUserName().getCurrentDepart().getId();
		//	cq.eq("sonId",sonId);
			String isWarm = request.getParameter("isWarm");
			if(org.apache.commons.lang.StringUtils.isNotEmpty(isWarm)){
				String userId = ResourceUtil.getSessionUserName().getId();
				TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
				TSDepart depart = systemService.getParentSonInfo(sonInfo);
				Boolean isAudit = userService.isAllowAudit(Globals.SC_QUOTE_TRANTYPE.toString(),userId,depart.getId());
				cq.eq("cancellation",0);
				//判断当前用户是否在多级审核队列中
				if(isAudit){
					Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId,depart.getId(),Globals.SC_QUOTE_TRANTYPE.toString());
					if(userAuditLeave.size() > 0){
						String leaves = "";
						for(Integer leave : userAuditLeave){
							leaves += leave+",";
						}
						leaves = leaves.substring(0,leaves.length()-1);
						List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in ("+leaves+")",new Object[]{depart.getId(),Globals.SC_QUOTE_TRANTYPE.toString()});
						if(billInfo.size() > 0){
							List<String> idArr = new ArrayList<String>();
							for(TScBillAuditStatusEntity entity : billInfo){
								idArr.add(entity.getBillId());
							}
							cq.in("id",idArr.toArray());
						}else {
							cq.eq("id", "0");
						}
					}
				}
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		//cq.addOrder("date", SortDirection.desc);
		//cq.addOrder("entryId", SortDirection.desc);
		cq.add();
		this.tScQuoteService.getDataGridReturn(cq, true);
		String tranType = request.getParameter("tranType");
		if(StringUtils.isNotEmpty(tranType)) {
			List<TScQuoteViewEntity> result = dataGrid.getResults();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (TScQuoteViewEntity entity : result) {
				TSAuditRelationEntity info = systemService.getAuditInfo(entity.getId(), tranType);
				if (info != null) {
					if (1 == info.getIsFinish()) {
						entity.setStatus(2);
						entity.setAuditorName(info.getAuditorName());
						entity.setAuditDate(sdf.format(info.getAuditDate()));
					} else {
						entity.setStatus(1);
						entity.setAuditorName(info.getAuditorName());
						entity.setAuditDate(sdf.format(info.getAuditDate()));
					}
				} else {
					entity.setStatus(0);
				}
			}
			dataGrid.setResults(result);
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 *销售报价的弹出框
	 */
	@RequestMapping(params = "getQuoteDialog")
	public ModelAndView getQuoteDialog(@RequestParam(value = "checkState",required = false)Integer checkState,
									   @RequestParam(value = "customerId",required = false)String customerId, HttpServletRequest req){
		if(checkState!=null){
			req.setAttribute("checkState", checkState);
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(customerId)){
			req.setAttribute("customerId", customerId);
		}
		String sonId = req.getParameter("sonId");
		req.setAttribute("sonId",sonId);
		return new ModelAndView("com/qihang/buss/sc/sales/tScQuoteDialog");
	}
	/**
	 * easyui AJAX请求数据datagird的视图
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagridView")
	public void datagridView(TScQuoteViewSelectEntity tScQuote,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScQuoteViewSelectEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScQuote);
		try{
			//自定义追加查询条件
			String query_date_begin = request.getParameter("date_begin");
			String query_date_end = request.getParameter("date_end");
			if(StringUtil.isNotEmpty(query_date_begin)){
				cq.ge("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_begin));
			}
			if(StringUtil.isNotEmpty(query_date_end)){
				cq.le("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_end));
			}
			Date nowDate = new Date();
			cq.le("inureDate",nowDate);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScQuoteService.getDataGridReturn(cq, true);
//		List<TScQuoteViewSelectEntity> viewList = dataGrid.getResults();
//		Integer index = 1;
//		for(TScQuoteViewSelectEntity view : viewList){
//			view.setIndexNumber(index);
//			index++;
//		}
		TagUtil.datagrid(response, dataGrid);
	}
	@RequestMapping(params = "getDatagridByNumberView")
	@ResponseBody
	public List<TScQuoteViewSelectEntity> getDatagridByNumberView(TScQuoteViewSelectEntity tScQuote,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String hql = "from TScQuoteViewSelectEntity where 1=1 and  checkState =? and id = ?";
		Object[] queryObj = new Object[]{tScQuote.getCheckState(),tScQuote.getId()};
		if(StringUtil.isNotEmpty(tScQuote.getCustomerId())){
			hql += " and customerId = ?";
			queryObj = new Object[]{tScQuote.getCheckState(),tScQuote.getId(),tScQuote.getCustomerId()};
		}
		List<TScQuoteViewSelectEntity> viewList = systemService.findHql(hql,queryObj);
		return viewList;
	}
	/**
	 * 删除销售报价单
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScQuoteEntity tScQuote, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScQuote = systemService.getEntity(TScQuoteEntity.class, tScQuote.getId());
		String message = "销售报价单删除成功";
		try{
			tScQuoteService.delMain(tScQuote);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			//删除待审核预警数据
			systemService.delBillAuditStatus(tScQuote.getTranType().toString(), tScQuote.getId());
		} catch(Exception e){
			e.printStackTrace();
			message = "销售报价单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除销售报价单
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "销售报价单删除成功";
		try{
			for(String id:ids.split(",")){
				TScQuoteEntity tScQuote = systemService.getEntity(TScQuoteEntity.class,
				id
				);
				tScQuoteService.delMain(tScQuote);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "销售报价单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加销售报价单
	 *
	 * @param tScQuote
	 * @param tScQuotePage
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScQuoteEntity tScQuote,TScQuotePage tScQuotePage, HttpServletRequest request) {
		List<TScQuotecustomerEntity> tScQuotecustomerList =  tScQuotePage.getTScQuotecustomerList();
		List<TScQuoteitemsEntity> tScQuoteitemsList =  tScQuotePage.getTScQuoteitemsList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		//校验单据编号唯一性
		Boolean hasBillNo = systemService.checkBillNo(TScOrderEntity.class.getAnnotation(Table.class).name(),tScQuote.getBillNo(),tScQuote.getId());
		if(!hasBillNo){
			j.setSuccess(false);
			j.setMsg("单据编号已存在,请确认");
			return j;
		}
		try{
			tScQuote.setTranType(Integer.parseInt(Globals.SC_QUOTE_TRANTYPE));
			tScQuote.setBillerID(ResourceUtil.getSessionUserName().getId());
			tScQuote.setCancellation(Integer.parseInt(Globals.CLOSE_NO));
			tScQuote.setDeleted(Integer.parseInt(Globals.CLOSE_NO));
			tScQuote.setCheckState(Integer.parseInt(Globals.CLOSE_NO));
			//tScQuote.setSonID(ResourceUtil.getSessionUserName().getCurrentDepart().getId());
			tScQuoteService.addMain(tScQuote, tScQuotecustomerList, tScQuoteitemsList);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			//待审核数据提醒操作
			systemService.saveBillAuditStatus(tScQuote.getTranType().toString(), tScQuote.getId());
		} catch(Exception e){
			e.printStackTrace();
			message = "销售报价单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新销售报价单
	 *
	 * @param tScQuote
	 * @param tScQuotePage
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScQuoteEntity tScQuote,TScQuotePage tScQuotePage, HttpServletRequest request) {
		List<TScQuotecustomerEntity> tScQuotecustomerList =  tScQuotePage.getTScQuotecustomerList();
		List<TScQuoteitemsEntity> tScQuoteitemsList =  tScQuotePage.getTScQuoteitemsList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		//校验单据编号唯一性
		Boolean hasBillNo = systemService.checkBillNo(TScOrderEntity.class.getAnnotation(Table.class).name(),tScQuote.getBillNo(),tScQuote.getId());
		if(!hasBillNo){
			j.setSuccess(false);
			j.setMsg("单据编号已存在,请确认");
			return j;
		}
		try{
			tScQuoteService.updateMain(tScQuote, tScQuotecustomerList, tScQuoteitemsList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新销售报价单失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 销售报价单新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScQuoteEntity tScQuote, HttpServletRequest req) {
		/*if (StringUtil.isNotEmpty(tScQuote.getId())) {
			tScQuote = tScQuoteService.getEntity(TScQuoteEntity.class, tScQuote.getId());
			req.setAttribute("tScQuotePage", tScQuote);
		}*/
		//当前用户所在分支机构
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart parentInfo = systemService.getParentSonInfo(sonInfo);
		req.setAttribute("sonId",parentInfo.getId());
		req.setAttribute("sonName", parentInfo.getDepartname());
		tScQuote.setBillNo(BillNoGenerate.getBillNo(Globals.SC_QUOTE_TRANTYPE));
		req.setAttribute("tScQuotePage", tScQuote);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		req.setAttribute("date",sdf.format(new Date()));
		Date checkDate = AccountUtil.getAccountStartDate();
		String dateStr = sdf.format(checkDate);
		req.setAttribute("checkDate",dateStr);
		return new ModelAndView("com/qihang/buss/sc/sales/tScQuote-add");
	}

	/**
	 * 销售报价单编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScQuoteEntity tScQuote, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScQuote.getId())) {
			tScQuote = tScQuoteService.getEntity(TScQuoteEntity.class, tScQuote.getId());
			//经办人
			if(StringUtils.isNotEmpty(tScQuote.getEmpID())){
				TScEmpEntity entity = systemService.get(TScEmpEntity.class, tScQuote.getEmpID());
				if(entity != null){
					if(org.apache.commons.lang.StringUtils.isNotEmpty(entity.getName())){
						tScQuote.setEmpName(entity.getName());
						tScQuote.setEmpID(entity.getId());
					}
				}
			}
			//部门
			if(StringUtils.isNotEmpty(tScQuote.getDeptID())){
				TScDepartmentEntity entity = systemService.get(TScDepartmentEntity.class,tScQuote.getDeptID());
				if(entity != null){
					if(StringUtils.isNotEmpty(entity.getName())){
						tScQuote.setDeptName(entity.getName());
						tScQuote.setDeptID(entity.getId());
					}
				}
			}
			//分支机构
			if(StringUtils.isNotEmpty(tScQuote.getSonID())){
				TSDepart dept  = systemService.get(TSDepart.class,tScQuote.getSonID());
				if(null != dept){
					tScQuote.setSonName(dept.getDepartname());
				}
			}
			if(StringUtils.isNotEmpty(tScQuote.getBillerID())){
				TSBaseUser user = systemService.get(TSBaseUser.class,tScQuote.getBillerID());
				if(null != user){
					tScQuote.setBillerName(user.getRealName());
				}
			}
			//审核人和审核日期
			List<TSAuditRelationEntity> info = systemService.getAuditInfoList(tScQuote.getId(), String.valueOf(tScQuote.getTranType()));
			String auditor = "";
			String auditDate = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for(int i=0 ; i < info.size() ; i++){
				TSAuditRelationEntity entity = info.get(i);
//                if(1 == entity.getIsFinish()){
//                    tScOrder.setCheckState(Globals.AUDIT_FIN+"");
//                }
				auditor += entity.getStatus()+"级审核：<u>"+entity.getAuditorName()+"</u>;";
				if(i==2){
					auditor+="</br>";
				}else{
					auditor+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				}
				auditDate = sdf.format(entity.getAuditDate());
			}
			req.setAttribute("auditor",auditor);
			req.setAttribute("auditDate",auditDate);
			req.setAttribute("tScQuotePage", tScQuote);
			String load = req.getParameter("load");
			req.setAttribute("load",load);
			Date checkDate = AccountUtil.getAccountStartDate();
			String dateStr = sdf.format(checkDate);
			req.setAttribute("checkDate",dateStr);
		}
		return new ModelAndView("com/qihang/buss/sc/sales/tScQuote-update");
	}

  /**
  * 导入功能跳转
  *
  * @return
  */
  @RequestMapping(params = "upload")
  public ModelAndView upload(HttpServletRequest req) {
  req.setAttribute("controller_name", "tScQuoteController");
  return new ModelAndView("common/upload/pub_excel_upload");
  }

  /**
  * 导出excel
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXls")
  public String exportXls(TScQuoteExcelEntity tScQuote,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  CriteriaQuery cq = new CriteriaQuery(TScQuoteExcelEntity.class, dataGrid);
  com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScQuote, request.getParameterMap());
	  String query_date_begin = request.getParameter("date_begin");
	  String query_date_end = request.getParameter("date_end");
	  String itemName  = request.getParameter("icitemName");
	  try {
		  if(StringUtil.isNotEmpty(query_date_begin)) {
			  cq.ge("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_begin));
		  }
		  if(StringUtil.isNotEmpty(query_date_end)){
			  cq.le("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_end));
		  }
	  } catch (ParseException e) {
		  e.printStackTrace();
	  }
	  cq.add();
      List<TScQuoteExcelEntity> tScQuotes = this.tScQuoteService.getListByCriteriaQuery(cq,false);
	  TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
	  TSDepart depart1 = systemService.getParentSonInfo(depart);
	  Set<String> departIds = systemService.getAllSonId(depart1.getId());
	  CriteriaQuery deptCq = new CriteriaQuery(TSDepart.class, dataGrid);
	  deptCq.in("id", departIds.toArray());
	  List<TSDepart> departList = this.tScQuoteService.getListByCriteriaQuery(deptCq,false);
	  List<TScQuoteExcelEntity> result = new ArrayList<TScQuoteExcelEntity>();
	  for(TScQuoteExcelEntity entity : tScQuotes){
		  if(StringUtils.isNotEmpty(itemName)){
			  String hql = "from TScQuoteitemsExcelEntity where quoteExcelEntity.id = ? ";
			  hql+=" and icitemToOrderEntity.name like '%"+itemName+"%' order by indexNumber asc";
			  List<TScQuoteitemsExcelEntity> entryList = this.tScQuoteService.findHql(hql,new Object[]{entity.getId()});
			  if(entryList.size() > 0) {
				  entity.setListItems(entryList);
				  result.add(entity);
			  }
		  }else{
			  result.add(entity);
		  }
	  }
	  String tranType = Globals.SC_QUOTE_TRANTYPE+"";
	  if(org.apache.commons.lang.StringUtils.isNotEmpty(tranType)) {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  for (TScQuoteExcelEntity entity : result) {
			  TSAuditRelationEntity info = systemService.getAuditInfo(entity.getId(), tranType);
			  if (info != null) {
				  if (1 == info.getIsFinish()) {
					  entity.setCheckState(Globals.AUDIT_FIN );
					  entity.setCheckerID(info.getAuditorName());
					  entity.setCheckDate(info.getAuditDate());
				  } else {
					  entity.setCheckState(Globals.AUDIT_IN);
					  entity.setCheckerID(info.getAuditorName());
					  entity.setCheckDate(info.getAuditDate());
				  }
			  } else {
				  entity.setCheckState(Globals.AUDIT_NO);
			  }
			  for(TSDepart depart2 : departList){
				  if(depart2.getId().equals(entity.getSonID())){
					  entity.setSonName(depart2.getDepartname());
					  break;
				  }
			  }
		  }
	  }
  //如需动态排除部分列不导出时启用，列名指Excel中文列名
  String[] exclusions = {"排除列名1","排除列名2"};
  modelMap.put(NormalExcelConstants.FILE_NAME,"销售报价单");
  modelMap.put(NormalExcelConstants.CLASS,TScQuoteExcelEntity.class);
  modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("销售报价单列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
  "导出信息",exclusions));
  modelMap.put(NormalExcelConstants.DATA_LIST, result);
  return NormalExcelConstants.JEECG_EXCEL_VIEW;
  }
  /**
  * 导出excel 使模板
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXlsByT")
  public String exportXlsByT(TScQuoteEntity tScQuote,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  modelMap.put(TemplateExcelConstants.FILE_NAME, "销售报价单");
  modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
  modelMap.put(TemplateExcelConstants.MAP_DATA,null);
  modelMap.put(TemplateExcelConstants.CLASS,TScQuoteEntity.class);
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
  params.setSaveUrl("upload/excelUpload");
  try {
    ExcelImportResult importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(),TScQuoteEntity.class,params);
    List<TScQuoteEntity> listTScQuoteEntitys = importResult.getList();
    StringBuilder stringBuilder = new StringBuilder("已存在的数据:");
    boolean flag = false;
    if(!importResult.isVerfiyFail()) {
      for (TScQuoteEntity tScQuote : listTScQuoteEntitys) {
      //以下检查导入数据是否重复的语句可视需求启用
        //Long count = tScQuoteService.getCountForJdbc("这里放检查数据是否重复的SQL语句");
        //if(count >0) {
          //flag = true;
          //stringBuilder.append(tScQuote.getId()+",");
        //} else {
          tScQuoteService.save(tScQuote);
        //}
      }
      j.setMsg("文件导入成功！");
      //j.setMsg("文件导入成功！" + (flag? stringBuilder.toString():""));
    }else {
      String excelPath = "/upload/excelUpload/TScQuoteEntity/"+importResult.getExcelName();
      j.setMsg("文件导入校验失败，详见<a href='"+excelPath+"' target='_blank' style='color:blue;font-weight:bold;'>要显示的导入文件名</a>");
    }
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
	 * 加载明细列表[报价单客户]
	 *
	 * @return
	 */
	@RequestMapping(params = "tScQuotecustomerList")
	public ModelAndView tScQuotecustomerList(TScQuoteEntity tScQuote, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id0 = tScQuote.getId();
		//===================================================================================
		//查询-报价单客户
	    String hql0 = "from TScQuotecustomerEntity where 1 = 1 AND fID = ? order by indexNumber";
	    try{
	    	List<TScQuotecustomerEntity> tScQuotecustomerEntityList = systemService.findHql(hql0,id0);
			for (TScQuotecustomerEntity entity :tScQuotecustomerEntityList){
				TScOrganizationEntity organizationEntity = systemService.get(TScOrganizationEntity.class, entity.getItemID());
                if(organizationEntity != null){
					entity.setItemName(organizationEntity.getName());
					entity.setItemID(organizationEntity.getId());
				}
			}

			req.setAttribute("tScQuotecustomerList", tScQuotecustomerEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		String load = req.getParameter("load");
		req.setAttribute("load",load);
		return new ModelAndView("com/qihang/buss/sc/sales/tScQuotecustomerList");
	}
	/**
	 * 加载明细列表[报价单商品]
	 *
	 * @return
	 */
	@RequestMapping(params = "tScQuoteitemsList")
	public ModelAndView tScQuoteitemsList(TScQuoteEntity tScQuote, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id1 = tScQuote.getId();
		//===================================================================================
		//查询-报价单商品
	    String hql1 = "from TScQuoteitemsEntity where 1 = 1 AND fID = ? order by indexNumber";
	    try{
	    	List<TScQuoteitemsEntity> tScQuoteitemsEntityList = systemService.findHql(hql1,id1);
            for (TScQuoteitemsEntity entity:tScQuoteitemsEntityList){
				//商品
				if(org.apache.commons.lang.StringUtils.isNotEmpty(entity.getItemID())){
					TScIcitemEntity icitemEntity = systemService.get(TScIcitemEntity.class,entity.getItemID());
					if(icitemEntity != null){
						if(org.apache.commons.lang.StringUtils.isNotEmpty(icitemEntity.getNumber())){
							entity.setItemNumber(icitemEntity.getNumber());
						}
						if(org.apache.commons.lang.StringUtils.isNotEmpty(icitemEntity.getName())){
							entity.setItemName(icitemEntity.getName());
						}
						if(org.apache.commons.lang.StringUtils.isNotEmpty(icitemEntity.getModel())){
							entity.setItemModel(icitemEntity.getModel());
						}
					}
				}
				//单位对应的条码
				if(org.apache.commons.lang.StringUtils.isNotEmpty(entity.getUnitID())){
					TScItemPriceEntity itemPriceEntity = systemService.get(TScItemPriceEntity.class,entity.getUnitID());
					if(itemPriceEntity != null){
						if(org.apache.commons.lang.StringUtils.isNotEmpty(itemPriceEntity.getBarCode())) {
							entity.setItemBarcode(itemPriceEntity.getBarCode());
						}
					}
				}
			}
			req.setAttribute("tScQuoteitemsList", tScQuoteitemsEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		String load = req.getParameter("load");
		req.setAttribute("load",load);
		return new ModelAndView("com/qihang/buss/sc/sales/tScQuoteitemsList");
	}

	/**
	 * 根据提供数据，获取报价单单价
	 * @param tranType
	 * @param itemId
	 * @param entryItemId
	 * @param qty
	 * @return
	 */
	@RequestMapping(params = "getPrice")
	@ResponseBody
	public AjaxJson getPrice(String tranType,String itemId,String entryItemId,BigDecimal qty,String unitId,String itemNo){
		//TODO 还需要数量字段,单位字段
		AjaxJson j = new AjaxJson();
		Map<String, Object> priceInfo = PriceUtil.getPrice(tranType,itemId,entryItemId,unitId,qty);
		Double price = ((BigDecimal) priceInfo.get("price")).doubleValue();//单价
		String priceType = priceInfo.get("priceType") == null ? "" : (String) priceInfo.get("priceType");//单价类型
		Map<String,Object> attribute = new HashMap<String, Object>();
		//attribute.put("price",price);
		//调价政策单价
		//TScItemPriceEntity unit = systemService.getEntity(TScItemPriceEntity.class,unitId);
		//List<TScPrcplyViewEntityEntity> lastPrice = prcplyServiceI.getLatePrice(entryItemId,itemId,unitId,qty.doubleValue());
		//若调价政策不存在
		//if(lastPrice.size() == 0) {
			//查询买一赠一
		//调价政策
		/**
		 * pap 调价政策单
		 * sq  销售报价
		 * ps  单价设置
		 * sp  供货报价
		 */
		if("pap".equals(priceType)){
			attribute.put("price", price);
			attribute.put("salesID", "");
			attribute.put("salesType", "0");
		}else {
			List<TScPromotionViewEntity> promotiongList = promotionServiceI.getGiftsGoodsInfo(entryItemId, qty.doubleValue());
			if (promotiongList.size() > 0) {
				List<Map<String, Object>> itemInfo = new ArrayList<Map<String, Object>>();
				//匹配赠品数据
				for (TScPromotionViewEntity view : promotiongList) {
					Map<String, Object> map1 = new HashMap<String, Object>();
					map1.put("itemId", view.getGiftItemId());
					map1.put("itemName", view.getGiftName());
					map1.put("itemNo", view.getGiftNumber());
					map1.put("model", view.getGiftModel());
					map1.put("barCode", view.getGiftBarCode());
					map1.put("unitId", view.getGiftUnitId());
					map1.put("qty", view.getGiftQty());
					map1.put("coefficient", view.getCoefficient());
					Double basicQty = BigDecimal.valueOf(view.getGiftQty()).multiply(BigDecimal.valueOf(view.getCoefficient())).doubleValue();
					map1.put("basicQty", basicQty);
					itemInfo.add(map1);
				}
				//促销类型id
				attribute.put("salesID", promotiongList.get(0).getId());
				attribute.put("salesType", "1");
				attribute.put("price", price);
				attribute.put("freeGiftInfo", itemInfo);
			} else {
				attribute.put("price", price);
				attribute.put("salesID", "");
				attribute.put("salesType", "2");
				j.setSuccess(false);
			}
		}
		//}else{
			//TScPrcplyViewEntityEntity info = lastPrice.get(0);
			//attribute.put("price",info.getNewPrice());
			//attribute.put("salesID", info.getId());
			//attribute.put("salesType", "0");
		//}
		j.setAttributes(attribute);
		return j;
	}
}
