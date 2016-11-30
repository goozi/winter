
package com.qihang.buss.sc.distribution.controller;

import com.alibaba.fastjson.JSONObject;
import com.qihang.buss.sc.baseinfo.entity.*;
import com.qihang.buss.sc.baseinfo.service.TScItemTypeServiceI;
import com.qihang.buss.sc.baseinfo.service.TScOrganizationServiceI;
import com.qihang.buss.sc.distribution.entity.*;
import com.qihang.buss.sc.distribution.page.TScDrpRstockbillPage;
import com.qihang.buss.sc.distribution.service.TScDrpRstockbillServiceI;
import com.qihang.buss.sc.sales.entity.TScSlLogisticalEntity;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
import com.qihang.buss.sc.util.AccountUtil;
import com.qihang.buss.sc.util.BillCountUtil;
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
import com.qihang.winter.poi.excel.entity.result.ExcelImportResult;
import com.qihang.winter.poi.excel.entity.vo.NormalExcelConstants;
import com.qihang.winter.poi.excel.entity.vo.TemplateExcelConstants;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.web.system.pojo.base.TSBaseUser;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.web.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Title: Controller
 * @Description: 退货申请
 * @author onlineGenerator
 * @date 2016-09-08 17:08:42
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScDrpRstockbillController")
public class TScDrpRstockbillController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScDrpRstockbillController.class);

	@Autowired
	private TScDrpRstockbillServiceI tScDrpRstockbillService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TScItemTypeServiceI tScItemTypeService;
	@Autowired
	private TScOrganizationServiceI tScOrganizationService;

	@Autowired
	private UserService userService;

	/**
	 * 退货申请列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tScDrpRstockbill")
	public ModelAndView tScDrpRstockbill(HttpServletRequest request) {
		request.setAttribute("tranType",Globals.SC_DRP_ORDER_RSTOCKBILL_TRANTYPE);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String checkDate = sdf.format(AccountUtil.getAccountStartDate());
		request.setAttribute("checkDate", checkDate);
		return new ModelAndView("com/qihang/buss/sc/distribution/tScDrpRstockbillViewList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScDrpRstockbillViewEntity tScDrpRstockbillView,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//应收金额，物流费用,重量，数量,金额，折后金额,重量,不含税金额,不含税折后金额,税额
		dataGrid.setFooter("allAmount,freight,weight,qty,taxAmountEx,inTaxAmount,itemWeight,rbAmount,discountAmount,taxAmount");
		CriteriaQuery cq = new CriteriaQuery(TScDrpRstockbillViewEntity.class, dataGrid);
		String currentUserId = ResourceUtil.getSessionUserName().getId();
		TScOrganizationEntity scOrganizationEntity = systemService.findUniqueByProperty(TScOrganizationEntity.class, "userId", currentUserId);
		if(null != scOrganizationEntity){
			if(StringUtil.isNotEmpty(scOrganizationEntity.getId())){//如果有上级经销商 根据上级经销商id查询订单表 加载数据
				String hql = "from TScDrpOrderViewEntity where itemId = ? ";
				List<TScDrpOrderViewEntity> tScDrpOrderEntityList =  systemService.findHql(hql, scOrganizationEntity.getId());
				if(!tScDrpOrderEntityList.isEmpty()){//如果集合不为空就加载
					tScDrpRstockbillView.setSonId("000");
					cq.eq("checkState", 2);
					cq.eq("itemId", scOrganizationEntity.getId());
				}
			}
		}
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScDrpRstockbillView);
		try{
			//自定义追加查询条件
			String query_date_begin = request.getParameter("date_begin");
			String query_date_end = request.getParameter("date_end");

			if (StringUtil.isNotEmpty(query_date_begin)) {
				cq.ge("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_begin));
			}
			if (StringUtil.isNotEmpty(query_date_end)) {
				cq.le("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_end));
			}

			String query_checkDate_begin = request.getParameter("checkdate_begin");
			String query_checkDate_end = request.getParameter("checkdate_end");

			if (StringUtil.isNotEmpty(query_checkDate_begin)) {
				cq.ge("checkDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_checkDate_begin));
			}
			if (StringUtil.isNotEmpty(query_checkDate_end)) {
				cq.le("checkDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_checkDate_end));
			}

			String query_kfDate_begin = request.getParameter("kfdate_begin");
			String query_kfDate_end = request.getParameter("kfdate_end");

			if (StringUtil.isNotEmpty(query_kfDate_begin)) {
				cq.ge("kfDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_kfDate_begin));
			}
			if (StringUtil.isNotEmpty(query_kfDate_end)) {
				cq.le("kfDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_kfDate_end));
			}

			String query_periodDate_begin = request.getParameter("perioddate_begin");
			String query_periodDate_end = request.getParameter("perioddate_end");

			if (StringUtil.isNotEmpty(query_periodDate_begin)) {
				cq.ge("periodDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_periodDate_begin));
			}
			if (StringUtil.isNotEmpty(query_periodDate_end)) {
				cq.le("periodDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_periodDate_end));
			}
			/*String sonId = ResourceUtil.getSessionUserName().getCurrentDepart().getId();
			cq.eq("sonId",sonId);*/
			//未审核预警数据
			String isWarm = request.getParameter("isWarm");
			if(org.apache.commons.lang.StringUtils.isNotEmpty(isWarm)) {
				String userId = ResourceUtil.getSessionUserName().getId();
				TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
				TSDepart depart = systemService.getParentSonInfo(sonInfo);
				Boolean isAudit = userService.isAllowAudit(tScDrpRstockbillView.getTranType().toString(), userId, depart.getId());
				cq.eq("cancellation",0);
				//判断当前用户是否在多级审核队列中
				if (isAudit) {
					Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId, depart.getId(), tScDrpRstockbillView.getTranType().toString());
					if (userAuditLeave.size() > 0) {
						String leaves = "";
						for (Integer leave : userAuditLeave) {
							leaves += leave + ",";
						}
						leaves = leaves.substring(0, leaves.length() - 1);
						List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in (" + leaves + ")", new Object[]{depart.getId(), tScDrpRstockbillView.getTranType().toString()});
						if (billInfo.size() > 0) {
							List<String> idArr = new ArrayList<String>();
							for (TScBillAuditStatusEntity entity : billInfo) {
								idArr.add(entity.getBillId());
							}
							cq.in("id", idArr.toArray());
						}else {
							cq.eq("id","0");
						}
					}
				}
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScDrpRstockbillService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除退货申请
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScDrpRstockbillEntity tScDrpRstockbill, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScDrpRstockbill = systemService.getEntity(TScDrpRstockbillEntity.class, tScDrpRstockbill.getId());
		String message = "退货申请删除成功";
		try{
			tScDrpRstockbillService.delMain(tScDrpRstockbill);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			//删除待审核预警数据
			systemService.delBillAuditStatus(tScDrpRstockbill.getTranType().toString(), tScDrpRstockbill.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "退货申请删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除退货申请
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "退货申请删除成功";
		try{
			for(String id:ids.split(",")){
				TScDrpRstockbillEntity tScDrpRstockbill = systemService.getEntity(TScDrpRstockbillEntity.class,
				id
				);
				tScDrpRstockbillService.delMain(tScDrpRstockbill);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "退货申请删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加退货申请
	 *
	 * @param tScDrpRstockbill
	 * @param tScDrpRstockbillPage
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScDrpRstockbillEntity tScDrpRstockbill,TScDrpRstockbillPage tScDrpRstockbillPage, HttpServletRequest request) {
		List<TScDrpRstockbillentryEntity> tScDrpRstockbillentryList =  tScDrpRstockbillPage.getTScDrpRstockbillentryList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			tScDrpRstockbill.setTranType(Globals.SC_DRP_ORDER_RSTOCKBILL_TRANTYPE.toString());
			tScDrpRstockbill.setBillerId(ResourceUtil.getSessionUserName().getId());
			tScDrpRstockbill.setCancellation(Integer.parseInt(Globals.CLOSE_NO));
			TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart departMain = systemService.getParentSonInfo(depart);
			tScDrpRstockbill.setSonId(departMain.getId());
			tScDrpRstockbillService.addMain(tScDrpRstockbill, tScDrpRstockbillentryList);
			//获得发货单id和发货单与订单的关联id
			for(TScDrpRstockbillentryEntity entity:tScDrpRstockbillentryList){
				double qty = 0;
				if(StringUtil.isNotEmpty(entity.getIdSrc())){//如果 发货单(源单)id不为空
					if(StringUtil.isNotEmpty(entity.getQty())){//如果退货数量不为空
						qty = entity.getQty().doubleValue(); //获得退货数量
					}
					if(StringUtil.isNotEmpty(entity.getIdSrc())){
						//回抛退货数量至发货单(减少)
						TScDrpStockbillentryEntity  tScDrpStockbillentryEntitysy = systemService.get(TScDrpStockbillentryEntity.class, entity.getIdSrc());
						//获得确认货数量
						double sumQty = tScDrpStockbillentryEntitysy.getStockQty().doubleValue();
						tScDrpStockbillentryEntitysy.setQty(sumQty-qty);
						systemService.saveOrUpdate(tScDrpStockbillentryEntitysy);
					}
				}
				if(StringUtil.isNotEmpty(entity.getIdOrder())){
					//回抛退货数量至订货单(减少)
					TScDrpOrderentryEntity tScDrpOrderentryEntity = systemService.get(TScDrpOrderentryEntity.class,entity.getIdOrder());
					if(null != tScDrpOrderentryEntity){
						double sumQty = tScDrpOrderentryEntity.getQty().doubleValue()-qty;
						tScDrpOrderentryEntity.setQty(BigDecimal.valueOf(sumQty));
						systemService.saveOrUpdate(tScDrpOrderentryEntity);
					}
				}

			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			//待审核数据提醒操作
			//systemService.saveBillAuditStatus(tScDrpRstockbill.getTranType().toString(), tScDrpRstockbill.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "退货申请添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新退货申请
	 *
	 * @param tScDrpRstockbill
	 * @param tScDrpRstockbillPage
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScDrpRstockbillEntity tScDrpRstockbill,TScDrpRstockbillPage tScDrpRstockbillPage, HttpServletRequest request) {
		List<TScDrpRstockbillentryEntity> tScDrpRstockbillentryList =  tScDrpRstockbillPage.getTScDrpRstockbillentryList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			tScDrpRstockbillService.updateMain(tScDrpRstockbill, tScDrpRstockbillentryList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新退货申请失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 退货申请新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScDrpRstockbillEntity tScDrpRstockbill, HttpServletRequest req) {
		/*if (StringUtil.isNotEmpty(tScDrpRstockbill.getId())) {
			tScDrpRstockbill = tScDrpRstockbillService.getEntity(TScDrpRstockbillEntity.class, tScDrpRstockbill.getId());
			req.setAttribute("tScDrpRstockbillPage", tScDrpRstockbill);
		}*/
		try {
			String sonName = ResourceUtil.getSessionUserName().getCurrentDepart().getDepartname();
			req.setAttribute("sonName", sonName);
			tScDrpRstockbill.setBillNo(BillNoGenerate.getBillNo(Globals.SC_QUOTE_TRANTYPE));
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			req.setAttribute("date", sdf.format(new Date()));
			String checkDate = sdf.format(AccountUtil.getAccountStartDate());
			req.setAttribute("checkDate", checkDate);
			req.setAttribute("tScDrpRstockbillPage", tScDrpRstockbill);
			req.setAttribute("tranType", Globals.SC_DRP_ORDER_RSTOCKBILL_TRANTYPE);

			String currentUserId = ResourceUtil.getSessionUserName().getId();
			TScOrganizationEntity scOrganizationEntity = systemService.findUniqueByProperty(TScOrganizationEntity.class, "userId", currentUserId);
			String delearType = "";
			if(null != scOrganizationEntity) {
				if (StringUtil.isNotEmpty(scOrganizationEntity.getTypeid())) {
					delearType = scOrganizationEntity.getTypeid();
				}
			}
			req.setAttribute("delearType",delearType);
			//当前用户所在分支机构
			TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart depart = systemService.getParentSonInfo(sonInfo);
			req.setAttribute("sonId",depart.getId());
			req.setAttribute("sonName", depart.getDepartname());
		}catch (Exception e){
			e.printStackTrace();
		}
		return new ModelAndView("com/qihang/buss/sc/distribution/tScDrpRstockbill-add");
	}

	/**
	 * 退货申请编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScDrpRstockbillEntity tScDrpRstockbill, HttpServletRequest req) {
		try {
			if (StringUtil.isNotEmpty(tScDrpRstockbill.getId())) {
				tScDrpRstockbill = tScDrpRstockbillService.getEntity(TScDrpRstockbillEntity.class, tScDrpRstockbill.getId());
				//经销商
				if(StringUtil.isNotEmpty(tScDrpRstockbill.getItemId())){
					TScOrganizationEntity entity = systemService.get(TScOrganizationEntity.class,tScDrpRstockbill.getItemId());
					if(null != entity){
						if(StringUtil.isNotEmpty(entity.getName())){
							tScDrpRstockbill.setItemId(entity.getId());
							tScDrpRstockbill.setItemName(entity.getName());
						}
					}
				}
				//经办人
				if (StringUtils.isNotEmpty(tScDrpRstockbill.getEmpId())) {
					TScEmpEntity entity = systemService.get(TScEmpEntity.class, tScDrpRstockbill.getEmpId());
					if (entity != null) {
						if (org.apache.commons.lang.StringUtils.isNotEmpty(entity.getName())) {
							tScDrpRstockbill.setEmpName(entity.getName());
							tScDrpRstockbill.setEmpId(entity.getId());
						}
					}
				}
				//部门
				if (StringUtils.isNotEmpty(tScDrpRstockbill.getDeptId())) {
					TScDepartmentEntity entity = systemService.get(TScDepartmentEntity.class, tScDrpRstockbill.getDeptId());
					if (entity != null) {
						if (StringUtils.isNotEmpty(entity.getName())) {
							tScDrpRstockbill.setDeptName(entity.getName());
							tScDrpRstockbill.setDeptId(entity.getId());
						}
					}
				}
				//分支机构
				if (StringUtils.isNotEmpty(tScDrpRstockbill.getSonId())) {
					TSDepart dept = systemService.get(TSDepart.class, tScDrpRstockbill.getSonId());
					if (null != dept) {
						tScDrpRstockbill.setSonName(dept.getDepartname());
					}
				}
				String currentUserId = ResourceUtil.getSessionUserName().getId();
				TScOrganizationEntity scOrganizationEntity = systemService.findUniqueByProperty(TScOrganizationEntity.class, "userId", currentUserId);
				String delearType = "";
				if(null != scOrganizationEntity) {
					if (StringUtil.isNotEmpty(scOrganizationEntity.getTypeid())) {
						delearType = scOrganizationEntity.getTypeid();
					}
				}
				req.setAttribute("delearType",delearType);

				if (StringUtils.isNotEmpty(tScDrpRstockbill.getBillerId())) {
					TSBaseUser user = systemService.get(TSBaseUser.class, tScDrpRstockbill.getBillerId());
					if (null != user) {
						tScDrpRstockbill.setBillerName(user.getRealName());
					}
				}
				//审核人和审核日期
				List<TSAuditRelationEntity> info = systemService.getAuditInfoList(tScDrpRstockbill.getId(), String.valueOf(tScDrpRstockbill.getTranType()));
				String auditor = "";
				String auditDate = "";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				for (int i = 0; i < info.size(); i++) {
					TSAuditRelationEntity entity = info.get(i);
//                if(1 == entity.getIsFinish()){
//                    tScOrder.setCheckState(Globals.AUDIT_FIN+"");
//                }
					auditor += entity.getStatus() + "级审核：<u>" + entity.getAuditorName() + "</u>;";
					if (i == 2) {
						auditor += "</br>";
					} else {
						auditor += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					}
					auditDate = sdf.format(entity.getAuditDate());
				}
				req.setAttribute("auditor", auditor);
				req.setAttribute("auditDate", auditDate);
				req.setAttribute("tScDrpRstockbillPage", tScDrpRstockbill);
				String checkDate = sdf.format(AccountUtil.getAccountStartDate());
				req.setAttribute("checkDate",checkDate);
			}
		}catch (Exception e){
			e.printStackTrace();
		}

		return new ModelAndView("com/qihang/buss/sc/distribution/tScDrpRstockbill-update");
	}

  /**
  * 导入功能跳转
  *
  * @return
  */
  @RequestMapping(params = "upload")
  public ModelAndView upload(HttpServletRequest req) {
  req.setAttribute("controller_name","tScDrpRstockbillController");
  return new ModelAndView("common/upload/pub_excel_upload");
  }

  /**
  * 导出excel
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXls")
  public String exportXls(TScDrpRstockbillEntity tScDrpRstockbill,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  CriteriaQuery cq = new CriteriaQuery(TScDrpRstockbillEntity.class, dataGrid);
  com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScDrpRstockbill, request.getParameterMap());
  List<TScDrpRstockbillEntity> tScDrpRstockbills = this.tScDrpRstockbillService.getListByCriteriaQuery(cq,false);
  //如需动态排除部分列不导出时启用，列名指Excel中文列名
  String[] exclusions = {"排除列名1","排除列名2"};
  modelMap.put(NormalExcelConstants.FILE_NAME,"退货申请");
  modelMap.put(NormalExcelConstants.CLASS,TScDrpRstockbillEntity.class);
  modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("退货申请列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
  "导出信息",exclusions));
  modelMap.put(NormalExcelConstants.DATA_LIST, tScDrpRstockbills);
  return NormalExcelConstants.JEECG_EXCEL_VIEW;
  }
  /**
  * 导出excel 使模板
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXlsByT")
  public String exportXlsByT(TScDrpRstockbillEntity tScDrpRstockbill,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  modelMap.put(TemplateExcelConstants.FILE_NAME, "退货申请");
  modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
  modelMap.put(TemplateExcelConstants.MAP_DATA,null);
  modelMap.put(TemplateExcelConstants.CLASS,TScDrpRstockbillEntity.class);
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
  params.setSaveUrl("upload/excelUpload");
  try {
    ExcelImportResult importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(),TScDrpRstockbillEntity.class,params);
    List<TScDrpRstockbillEntity> listTScDrpRstockbillEntitys = importResult.getList();
    StringBuilder stringBuilder = new StringBuilder("已存在的数据:");
    boolean flag = false;
    if(!importResult.isVerfiyFail()) {
      for (TScDrpRstockbillEntity tScDrpRstockbill : listTScDrpRstockbillEntitys) {
      //以下检查导入数据是否重复的语句可视需求启用
        //Long count = tScDrpRstockbillService.getCountForJdbc("这里放检查数据是否重复的SQL语句");
        //if(count >0) {
          //flag = true;
          //stringBuilder.append(tScDrpRstockbill.getId()+",");
        //} else {
          tScDrpRstockbillService.save(tScDrpRstockbill);
        //}
      }
      j.setMsg("文件导入成功！");
      //j.setMsg("文件导入成功！" + (flag? stringBuilder.toString():""));
    }else {
      String excelPath = "/upload/excelUpload/TScDrpRstockbillEntity/"+importResult.getExcelName();
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
	 * 加载明细列表[退货申请]
	 *
	 * @return
	 */
	@RequestMapping(params = "tScDrpRstockbillentryList")
	public ModelAndView tScDrpRstockbillentryList(TScDrpRstockbillEntity tScDrpRstockbill, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id0 = tScDrpRstockbill.getId();
		//===================================================================================
		//查询-退货申请
	    String hql0 = "from TScDrpRstockbillentryEntity where 1 = 1 AND iNTERID = ? ";
	    try{
	    	List<TScDrpRstockbillentryEntity> tScDrpRstockbillentryEntityList = systemService.findHql(hql0,id0);
			for(TScDrpRstockbillentryEntity entity: tScDrpRstockbillentryEntityList){
				if(StringUtil.isNotEmpty(entity.getItemId())){
					TScIcitemEntity icitemEntity = systemService.get(TScIcitemEntity.class, entity.getItemId());
					if (icitemEntity != null) {
						if (org.apache.commons.lang.StringUtils.isNotEmpty(icitemEntity.getNumber())) {
							entity.setNumber(icitemEntity.getNumber());
						}
						if (org.apache.commons.lang.StringUtils.isNotEmpty(icitemEntity.getName())) {
							entity.setName(icitemEntity.getName());
						}
						if (org.apache.commons.lang.StringUtils.isNotEmpty(icitemEntity.getModel())) {
							entity.setModel(icitemEntity.getModel());
						}
					}
				}
				//单位对应的条码
				if (StringUtils.isNotEmpty(entity.getUnitId())) {
					TScItemPriceEntity itemPriceEntity = systemService.get(TScItemPriceEntity.class, entity.getUnitId());
					if (itemPriceEntity != null) {
						if (org.apache.commons.lang.StringUtils.isNotEmpty(itemPriceEntity.getBarCode())) {
							entity.setBarCode(itemPriceEntity.getBarCode());
						}
					}
				}
				if(StringUtil.isNotEmpty(entity.getStockId())){
					TScStockEntity stockEntity = systemService.get(TScStockEntity.class,entity.getStockId());
					if(null != stockEntity){
						if(StringUtil.isNotEmpty(stockEntity.getName())){
							entity.setStockName(stockEntity.getName());
						}
					}
				}
			}
			req.setAttribute("tScDrpRstockbillentryList", tScDrpRstockbillentryEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/qihang/buss/sc/distribution/tScDrpRstockbillentryList");
	}

	@RequestMapping(params = "goLogisticalUpdate")
	public ModelAndView goLogisticalUpdate(TScSlLogisticalEntity entity,HttpServletRequest request){
		String load = request.getParameter("load");
		request.setAttribute("load", load);
		String tableName = request.getParameter("tableName");
		request.setAttribute("tableName", tableName);
		String logisticalValue = request.getParameter("logisticalValue");
		if(org.apache.commons.lang.StringUtils.isEmpty(logisticalValue)) {
			if (StringUtil.isNotEmpty(entity.getFid())) {
				TScSlLogisticalEntity slLogisticalEntity = tScDrpRstockbillService.findUniqueByProperty(TScSlLogisticalEntity.class, "fid", entity.getFid());
				if (null != slLogisticalEntity) {
					String logisticalId = slLogisticalEntity.getLogisticalId();
					if (org.apache.commons.lang.StringUtils.isNotEmpty(logisticalId)) {
						TScLogisticalEntity logisticalEntity = systemService.getEntity(TScLogisticalEntity.class, logisticalId);
						if (null != logisticalEntity) {
							slLogisticalEntity.setLogisticalName(logisticalEntity.getName());
						}
					}
					String accountId = slLogisticalEntity.getAccountId();
					if (org.apache.commons.lang.StringUtils.isNotEmpty(accountId)) {
						TScSettleacctEntity scSettleacctEntity = systemService.getEntity(TScSettleacctEntity.class, accountId);
						if (null != scSettleacctEntity) {
							slLogisticalEntity.setAccountName(scSettleacctEntity.getName());
						}
					}
					request.setAttribute("tScSlLogisticalPage", slLogisticalEntity);
				} else {
					request.setAttribute("tScSlLogisticalPage", entity);
				}
			} else {
				request.setAttribute("tScSlLogisticalPage", entity);
			}
		}else{
			TScSlLogisticalEntity logisticalEntity = JSONObject.parseObject(logisticalValue, TScSlLogisticalEntity.class);
			request.setAttribute("tScSlLogisticalPage", logisticalEntity);
		}
		return new ModelAndView("com/qihang/buss/sc/distribution/tScSlLogistical-update");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagridSelected")
	public void datagridSelected(TScOrganizationEntity tScOrganization, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//判断是否进行查询
		String name = tScOrganization.getName();
		Boolean isSelect = false;
		if(org.apache.commons.lang.StringUtils.isNotEmpty(name)) {
			if (name.indexOf("_") > -1) {
				isSelect = true;
				name = name.replace("_", "");
				tScOrganization.setName("");
			}
		}
		String currentUserId = ResourceUtil.getSessionUserName().getId();
		TScOrganizationEntity scOrganizationEntity = systemService.findUniqueByProperty(TScOrganizationEntity.class, "userId", currentUserId);
		if(null != scOrganizationEntity){
			if(StringUtil.isNotEmpty(scOrganizationEntity.getDelerUserId())){
				String hql = "from TScOrganizationEntity where userId = ? ";
				TScOrganizationEntity tScOrganizationEntity = systemService.findUniqueByProperty(TScOrganizationEntity.class, "userId", scOrganizationEntity.getDelerUserId());
				if(null != tScOrganizationEntity){
					tScOrganization.setUserId(scOrganizationEntity.getDelerUserId());
				}else{
					tScOrganization.setUserId(null);
				}
			}else{
				tScOrganization.setUserId(null);
			}
		}
		CriteriaQuery cq = new CriteriaQuery(TScOrganizationEntity.class, dataGrid);
		List<String> list = new ArrayList<String>();
		if (StringUtil.isNotEmpty(tScOrganization.getParentid())) {
			TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart depart = systemService.getParentSonInfo(sonInfo);
			list = tScItemTypeService.getParentIdFromTree("01", tScOrganization.getParentid(),depart.getId());
			tScOrganization.setParentid(null);

		}
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScOrganization, request.getParameterMap());
		try {
			//自定义追加查询条件
			if (list.size() > 0) {
				cq.in("parentid", list.toArray());
			}
			//如若弹窗查询
			if(isSelect){
				cq.add(Restrictions.or(
						Restrictions.like("name", "%" + name + "%"),
						Restrictions.or(
								Restrictions.like("number", "%" + name + "%"),
								Restrictions.or(
										Restrictions.like("shortnumber", "%" + name + "%"), Restrictions.like("shortname", "%" + name + "%")))));
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScOrganizationService.getDataGridReturn(cq, true);
		List<TScOrganizationEntity> organizationEntityList = dataGrid.getResults();
		for(TScOrganizationEntity entity : organizationEntityList){
			BigDecimal countAmount = BillCountUtil.getReceivableCountByCustomerId(entity.getId());
			entity.setCountAmount(countAmount);
		}
		String tranType = request.getParameter("tranType");
		if(org.apache.commons.lang3.StringUtils.isNotEmpty(tranType)){
			List<TScOrganizationEntity> result = dataGrid.getResults();
			List<TScOrganizationEntity> newInfo = new ArrayList<TScOrganizationEntity>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for(TScOrganizationEntity entity : result) {
				TSAuditRelationEntity info  = systemService.getAuditInfo(entity.getId(), tranType);
				if(info != null){
					if(1 == info.getIsFinish()){
						entity.setStatus(2);
						entity.setAuditorName(info.getAuditorName());
						entity.setAuditDate(sdf.format(info.getAuditDate()));
					}else{
						entity.setStatus(1);
						entity.setAuditorName(info.getAuditorName());
						entity.setAuditDate(sdf.format(info.getAuditDate()));
					}
				}else {
					entity.setStatus(0);
				}
			}
			dataGrid.setResults(result);
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 资格审查客户页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goSelectToRStockbillDialog")
	public ModelAndView goSelectToRStockbillDialog(TScOrganizationEntity tScOrganization, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScOrganization.getName())) {
			req.setAttribute("name", tScOrganization.getName());
		}
		if(tScOrganization.getDisable() == Globals.IS_DEALER){
			req.setAttribute("isdealer", tScOrganization.getIsDealer());
		}
		if(null != tScOrganization.getIsDealer() && tScOrganization.getIsDealer().equals(Globals.IS_DEALER)){
			req.setAttribute("isdealer", tScOrganization.getIsDealer());
		}
		return new ModelAndView("com/qihang/buss/sc/distribution/tScOrganizationSelect2RStock");
	}
}
