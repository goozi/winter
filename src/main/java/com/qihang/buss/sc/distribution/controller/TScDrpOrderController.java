
package com.qihang.buss.sc.distribution.controller;

import com.qihang.buss.sc.baseinfo.entity.*;
import com.qihang.buss.sc.baseinfo.service.CountCommonServiceI;
import com.qihang.buss.sc.baseinfo.service.TScItemTypeServiceI;
import com.qihang.buss.sc.baseinfo.service.TScOrganizationServiceI;
import com.qihang.buss.sc.distribution.entity.*;
import com.qihang.buss.sc.distribution.page.TScDrpOrderPage;
import com.qihang.buss.sc.distribution.service.TScDrpOrderServiceI;
import com.qihang.buss.sc.distribution.service.TScPromotionServiceI;
import com.qihang.buss.sc.sysaudit.entity.TSAuditEntity;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
import com.qihang.buss.sc.util.AccountUtil;
import com.qihang.buss.sc.util.BillCountUtil;
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
import com.qihang.winter.web.system.pojo.base.TSConfig;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.pojo.base.TSUser;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.web.system.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.engine.spi.TypedValue;
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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Title: Controller
 * @Description: 订货管理
 * @author onlineGenerator
 * @date 2016-08-02 09:58:17
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScDrpOrderController")
public class TScDrpOrderController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScDrpOrderController.class);

	@Autowired
	private TScDrpOrderServiceI tScDrpOrderService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private CountCommonServiceI countCommonService;
	@Autowired
	private TScPromotionServiceI promotionServiceI;
	@Autowired
	private TScItemTypeServiceI tScItemTypeService;
	@Autowired
	private TScOrganizationServiceI tScOrganizationService;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserService userService;

	/**
	 * 订货管理列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tScDrpOrder")
	public ModelAndView tScDrpOrder(HttpServletRequest request) {
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		List<TSAuditEntity> tsAuditEntityList = systemService.findHql("from TSAuditEntity where billId=? and sonId = ?", new Object[]{Globals.SC_DRP_ORDER_TRANTYPE.toString(), depart.getId()});
		if(tsAuditEntityList.size() > 0) {
			TSAuditEntity auditEntity = tsAuditEntityList.get(0);
			if (null != auditEntity) {
				Integer isEdit = auditEntity.getIsEdit();
				request.setAttribute("isEdit", isEdit);
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String checkDate = sdf.format(AccountUtil.getAccountStartDate());
		request.setAttribute("checkDate", checkDate);
		String tranType = Globals.SC_DRP_ORDER_TRANTYPE.toString();
		request.setAttribute("tranType",tranType);
		List<TSConfig> tsConfigList = tScDrpOrderService.findHql("from TSConfig");
		if(tsConfigList.size()>0){
			for(TSConfig config : tsConfigList) {
				if("CFG_CONTROL_METHOD".equals(config.getCode())){
					if(StringUtils.isNotEmpty(config.getContents())) {
						request.setAttribute("method", config.getContents());//信用控制方式 0:允许继续保存审核 1：不允许继续保存审核
					}
				}else if("CFG_CONTROL_TIMEPOINT".equals(config.getCode())){
					if(StringUtils.isNotEmpty(config.getContents())) {
						request.setAttribute("timePoint", config.getContents());//信用控制时点 0：单据保存时 1：单据审核时
					}
				}
			}
		}
		/**
		 * 如果存在下级经销商已审核的订货就禁用列表的 编辑 变更 反审核 功能
		 */
		String currentUserId = ResourceUtil.getSessionUserName().getId();
		TScOrganizationEntity scOrganizationEntity = systemService.findUniqueByProperty(TScOrganizationEntity.class, "userId", currentUserId);
		//1.获得当前登录用户的经销商级别
		//2.如果是一级经销商就查看自己的订货单和自己下级的已审核订货单  如果是二级经销商就查看自己的订货单和下级已审核的订货单 如果是三级经销商就只查看自己的订货单
		String hql = "from TScDrpOrderViewEntity where itemId = ? ";
		if(null != scOrganizationEntity){
			if(scOrganizationEntity.getTypeid().equals(Globals.SC_TYPE_ADEALER) || scOrganizationEntity.getTypeid().equals(Globals.SC_TYPE_BDEALER)){
				if(StringUtil.isNotEmpty(scOrganizationEntity.getId())){
					List<TScDrpOrderViewEntity> tScDrpOrderEntityList =  systemService.findHql(hql, scOrganizationEntity.getId());
					if(!tScDrpOrderEntityList.isEmpty()){//如果集合不为空就加载
						for(int i=0;i<tScDrpOrderEntityList.size();i++){
							if(tScDrpOrderEntityList.get(i).getCheckState()==0){
								tScDrpOrderEntityList.remove(i);
							}
						}
						if(!tScDrpOrderEntityList.isEmpty()){
							request.setAttribute("flag","true");
						}
					}
				}
			}
		}
		return new ModelAndView("com/qihang/buss/sc/distribution/tScDrpOrderList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScDrpOrderViewEntity tScDrpOrder,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		CriteriaQuery cq = new CriteriaQuery(TScDrpOrderViewEntity.class, dataGrid);
		try{
			//数量,金额，折扣率，折后金额,不含税金额，不含税折后金额,税额
			dataGrid.setFooter("qty,taxAmountEx,inTaxAmount,amount,discountAmount,taxAmount");
			if(StringUtil.isEmpty(tScDrpOrder.getSonId())) {
				tScDrpOrder.setSonId("000");
				Set<String> sonIds = systemService.getAllSonId(depart.getId());
				cq.in("sonId",sonIds.toArray());
			}
			com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScDrpOrder);
			String currentUserId = ResourceUtil.getSessionUserName().getId();
			TScOrganizationEntity scOrganizationEntity = systemService.findUniqueByProperty(TScOrganizationEntity.class, "userId", currentUserId);
			//1.获得当前登录用户的经销商级别
			//2.如果是一级经销商就查看自己的订货单和自己下级的已审核订货单  如果是二级经销商就查看自己的订货单和下级已审核的订货单 如果是三级经销商就只查看自己的订货单
			String hql = "from TScDrpOrderViewEntity where itemId = ? ";
			if(null != scOrganizationEntity){
				if(scOrganizationEntity.getTypeid().equals(Globals.SC_TYPE_ADEALER) || scOrganizationEntity.getTypeid().equals(Globals.SC_TYPE_BDEALER)){
					if(StringUtil.isNotEmpty(scOrganizationEntity.getId())){
						List<TScDrpOrderViewEntity> tScDrpOrderEntityList =  systemService.findHql(hql, scOrganizationEntity.getId());
						if(!tScDrpOrderEntityList.isEmpty()){//如果集合不为空就加载
							cq.add(Restrictions.or(
									Restrictions.and(Restrictions.eq("itemId", scOrganizationEntity.getId()), Restrictions.eq("checkState", 2))
							));
						}else{
							cq.eq("billerId", currentUserId);
						}
					}
				}else{
					cq.eq("billerId", currentUserId);
				}
			} else {
				Set<String> childIdInfo = systemService.getAllSonId(depart.getId());
				childIdInfo.remove(depart.getId());
				if(childIdInfo.size() > 0) {
					//cq.add(Restrictions.not(Restrictions.in("id", listIds.toArray())));
					//	cq.in("sonId",childIdInfo.toArray());
					cq.add(Restrictions.or(Restrictions.eq("sonId", depart.getId()), Restrictions.and(Restrictions.in("sonId", childIdInfo.toArray()), Restrictions.eq("checkState", 2))));
				}else{
					cq.eq("sonId", depart.getId());
				}

			}
			//查询条件组装器

			//自定义追加查询条件
			//单据日期
			String query_date_begin = request.getParameter("date_begin");
			String query_date_end = request.getParameter("date_end");

			//审核日期
			String query_checkDate_begin = request.getParameter("checkDate_begin");
			String query_checkDate_end = request.getParameter("checkDate_end");

			String query_jhDate_begin = request.getParameter("jhDate_begin");
			String query_jhDate_end = request.getParameter("jhDate_end");

			String query_outDate_begin = request.getParameter("outDate_begin");
			String query_outDate_end = request.getParameter("outDate_end");

			if (StringUtil.isNotEmpty(query_date_begin)) {
				cq.ge("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_begin));
			}
			if (StringUtil.isNotEmpty(query_date_end)) {
				cq.le("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_end));
			}

			if (StringUtil.isNotEmpty(query_checkDate_begin)) {
				cq.ge("checkDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_checkDate_begin));
			}

			if (StringUtil.isNotEmpty(query_checkDate_end)) {
				cq.le("checkDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_checkDate_end));
			}

			if (StringUtil.isNotEmpty(query_jhDate_begin)) {
				cq.ge("jhDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_jhDate_begin));
			}

			if (StringUtil.isNotEmpty(query_jhDate_end)) {
				cq.le("jhDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_jhDate_end));
			}

			if (StringUtil.isNotEmpty(query_outDate_begin)) {
				cq.ge("outDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_outDate_begin));
			}

			if (StringUtil.isNotEmpty(query_outDate_end)) {
				cq.le("outDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_outDate_end));
			}
			//未审核预警数据
			String isWarm = request.getParameter("isWarm");
			if(org.apache.commons.lang.StringUtils.isNotEmpty(isWarm)) {
				String userId = ResourceUtil.getSessionUserName().getId();
				Boolean isAudit = userService.isAllowAudit(tScDrpOrder.getTranType().toString(), userId, depart.getId());
				cq.eq("cancellation",0);
				//判断当前用户是否在多级审核队列中
				if (isAudit) {
					Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId, depart.getId(), tScDrpOrder.getTranType().toString());
					if (userAuditLeave.size() > 0) {
						String leaves = "";
						for (Integer leave : userAuditLeave) {
							leaves += leave + ",";
						}
						leaves = leaves.substring(0, leaves.length() - 1);
						List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in (" + leaves + ")", new Object[]{depart.getId(), tScDrpOrder.getTranType().toString()});
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
			List<String> orderInfo = new ArrayList<String>();
			orderInfo.add("cancellation@asc");
			orderInfo.add("date@desc");
			orderInfo.add("billNo@desc");
			orderInfo.add("indexNumber@asc");
			cq.setOrder(orderInfo);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScDrpOrderService.getDataGridReturn(cq, true);
		String tranType = request.getParameter("tranType");
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(tranType)) {
			List<TScDrpOrderViewEntity> result = dataGrid.getResults();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (TScDrpOrderViewEntity entity : result) {
//				if(entity.getCheckState() != 2 && !entity.getSonId().equals(depart.getId())){
//					continue;
//				}
				TSAuditRelationEntity info = systemService.getAuditInfo(entity.getId(), tranType);
				if (info != null) {
					if (1 == info.getIsFinish()) {
						entity.setStatus(Globals.AUDIT_FIN);
						entity.setAuditorName(info.getAuditorName());
						entity.setAuditDate(sdf.format(info.getAuditDate()));
					} else {
						entity.setCheckState(Globals.AUDIT_IN);
						entity.setAuditorName(info.getAuditorName());
						entity.setAuditDate(sdf.format(info.getAuditDate()));
					}
				} else {
					entity.setStatus(Globals.AUDIT_NO);
				}
			}
			dataGrid.setResults(result);
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除订货管理
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScDrpOrderEntity tScDrpOrder, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScDrpOrder = systemService.getEntity(TScDrpOrderEntity.class, tScDrpOrder.getId());
		String message = "订货管理删除成功";
		try{
			tScDrpOrderService.delMain(tScDrpOrder);
			/** 修改引用次数*/
			//上游经销商
			countCommonService.deleteUpdateCount("T_SC_Organization", tScDrpOrder.getItemID());
			//经办人
			countCommonService.deleteUpdateCount("t_sc_emp", tScDrpOrder.getEmpID());
			//部门
			countCommonService.deleteUpdateCount("t_sc_department", tScDrpOrder.getDeptID());
			//分支机构
			countCommonService.deleteUpdateCount("T_SC_SonCompany",tScDrpOrder.getSonID());
			//仓库
			countCommonService.deleteUpdateCount("t_sc_stock", tScDrpOrder.getStockID());
			//商品
			countCommonService.deleteUpdateCount("t_sc_icitem", tScDrpOrder.getItemID());

			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			//删除待审核预警数据
			systemService.delBillAuditStatus(tScDrpOrder.getTranType().toString(), tScDrpOrder.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "订货管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除订货管理
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "订货管理删除成功";
		try{
			for(String id:ids.split(",")){
				TScDrpOrderEntity tScDrpOrder = systemService.getEntity(TScDrpOrderEntity.class,id);
				tScDrpOrderService.delMain(tScDrpOrder);
				/** 修改引用次数*/
				//上游经销商
				countCommonService.deleteUpdateCount("T_SC_Organization", tScDrpOrder.getItemID());
				//经办人
				countCommonService.deleteUpdateCount("t_sc_emp", tScDrpOrder.getEmpID());
				//部门
				countCommonService.deleteUpdateCount("t_sc_department", tScDrpOrder.getDeptID());
				//分支机构
				countCommonService.deleteUpdateCount("T_SC_SonCompany",tScDrpOrder.getSonID());
				//仓库
				countCommonService.deleteUpdateCount("t_sc_stock", tScDrpOrder.getStockID());
				//商品
				countCommonService.deleteUpdateCount("t_sc_icitem", tScDrpOrder.getItemID());
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "订货管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加订货管理
	 *
	 * @param tScDrpOrder
	 * @param tScDrpOrderPage
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScDrpOrderEntity tScDrpOrder,TScDrpOrderPage tScDrpOrderPage, HttpServletRequest request) {
		List<TScDrpOrderentryEntity> tScDrpOrderentryList =  tScDrpOrderPage.getTScDrpOrderentryList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			tScDrpOrder.setTranType(Globals.SC_DRP_ORDER_TRANTYPE);
			tScDrpOrder.setBillerID(ResourceUtil.getSessionUserName().getId());
			tScDrpOrder.setCheckState(Globals.AUDIT_NO);
			tScDrpOrder.setCancellation(Integer.parseInt(Globals.CLOSE_NO));
			TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart departMain = systemService.getParentSonInfo(depart);
			tScDrpOrder.setSonID(departMain.getId());
			tScDrpOrder.setClosed(Integer.parseInt(Globals.CLOSE_NO));
			tScDrpOrderService.addMain(tScDrpOrder, tScDrpOrderentryList);
			/**
			 * 修改引用次数
			 */
			//上游经销商
			countCommonService.addUpdateCount("T_SC_Organization", tScDrpOrder.getItemID());
			//经办人
			countCommonService.addUpdateCount("t_sc_emp", tScDrpOrder.getEmpID());
			//部门
			countCommonService.addUpdateCount("t_sc_department", tScDrpOrder.getDeptID());
			//分支机构
			countCommonService.addUpdateCount("T_SC_SonCompany", tScDrpOrder.getSonID());
			//仓库
			countCommonService.addUpdateCount("t_sc_stock", tScDrpOrder.getStockID());
			//商品
			for(TScDrpOrderentryEntity entity1:tScDrpOrderentryList){
				countCommonService.addUpdateCount("t_sc_icitem", entity1.getItemID());
			}

			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			//待审核数据提醒操作
			//systemService.saveBillAuditStatus(tScDrpOrder.getTranType().toString(), tScDrpOrder.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "订货管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新订货管理
	 *
	 * @param tScDrpOrder
	 * @param tScDrpOrderPage
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScDrpOrderEntity tScDrpOrder,TScDrpOrderPage tScDrpOrderPage, HttpServletRequest request) {
		List<TScDrpOrderentryEntity> tScDrpOrderentryList =  tScDrpOrderPage.getTScDrpOrderentryList();
		TScDrpOrderEntity t = tScDrpOrderService.get(TScDrpOrderEntity.class, tScDrpOrder.getId());
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			sessionFactory.getCurrentSession().evict(t);
			tScDrpOrderService.updateMain(tScDrpOrder, tScDrpOrderentryList);
			/**
			 * 修改引用次数
			 */
			//上游经销商
			countCommonService.editUpdateCount("T_SC_Organization", t.getItemID(), tScDrpOrder.getItemID());
			//经办人
			countCommonService.editUpdateCount("t_sc_emp", t.getEmpID(), tScDrpOrder.getEmpID());
			//部门
			countCommonService.editUpdateCount("t_sc_department", t.getDeptID(), tScDrpOrder.getDeptID());
			//分支机构
			countCommonService.editUpdateCount("T_SC_SonCompany", t.getSonID(), tScDrpOrder.getSonID());
			//仓库
			countCommonService.editUpdateCount("t_sc_stock", t.getStockID(), tScDrpOrder.getStockID());
			//商品
			for(TScDrpOrderentryEntity entity1:tScDrpOrderentryList){
				TScDrpOrderentryEntity tScDrpOrderentryEntity = tScDrpOrderService.get(TScDrpOrderentryEntity.class, entity1.getId());
				countCommonService.editUpdateCount("t_sc_icitem", tScDrpOrderentryEntity.getItemID(), entity1.getItemID());
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新订货管理失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 订货管理新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScDrpOrderEntity tScDrpOrder, HttpServletRequest req) {
	/*	if (StringUtil.isNotEmpty(tScDrpOrder.getId())) {
			tScDrpOrder = tScDrpOrderService.getEntity(TScDrpOrderEntity.class, tScDrpOrder.getId());

		}*/
		try {
			String sonName = ResourceUtil.getSessionUserName().getCurrentDepart().getDepartname();
			req.setAttribute("sonName", sonName);
			tScDrpOrder.setBillNo(BillNoGenerate.getBillNo(Globals.SC_QUOTE_TRANTYPE));
			req.setAttribute("tScDrpOrderPage", tScDrpOrder);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			req.setAttribute("date", sdf.format(new Date()));
			String checkDate = sdf.format(AccountUtil.getAccountStartDate());
			req.setAttribute("checkDate", checkDate);
			String tranType = Globals.SC_DRP_ORDER_TRANTYPE.toString();
			req.setAttribute("tranType", tranType);
			String currentUserId = ResourceUtil.getSessionUserName().getId();
			TScOrganizationEntity scOrganizationEntity = systemService.findUniqueByProperty(TScOrganizationEntity.class, "userId", currentUserId);
			String delearType = "";
			if(null != scOrganizationEntity) {
				if (StringUtil.isNotEmpty(scOrganizationEntity.getTypeid())) {
					delearType = scOrganizationEntity.getTypeid();
				}
				if (StringUtil.isNotEmpty(scOrganizationEntity.getDealer())){
					TScOrganizationEntity tScOrganizationEntity = systemService.findUniqueByProperty(TScOrganizationEntity.class, "id", scOrganizationEntity.getDealer());
					if(null != tScOrganizationEntity){
						req.setAttribute("itemName",tScOrganizationEntity.getName());
						req.setAttribute("itemId",tScOrganizationEntity.getId());
					}
				}
			}
			req.setAttribute("delearType", delearType);

			//当前用户所在分支机构
			TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart depart = systemService.getParentSonInfo(sonInfo);
			req.setAttribute("sonId",depart.getId());
			req.setAttribute("sonName", depart.getDepartname());
		}catch (Exception e){
			e.printStackTrace();
		}
		return new ModelAndView("com/qihang/buss/sc/distribution/tScDrpOrder-add");
	}

	/**
	 * 订货管理编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScDrpOrderEntity tScDrpOrder, HttpServletRequest req) {
		try {
			String load = req.getParameter("load");
			if (StringUtil.isNotEmpty(tScDrpOrder.getId())) {
				tScDrpOrder = tScDrpOrderService.getEntity(TScDrpOrderEntity.class, tScDrpOrder.getId());
				req.setAttribute("tScDrpOrderPage", tScDrpOrder);
			}
			TScOrganizationEntity organizationEntity = new TScOrganizationEntity();
			if(StringUtils.isNotEmpty(tScDrpOrder.getItemID())){
				organizationEntity = systemService.getEntity(TScOrganizationEntity.class,tScDrpOrder.getItemID());
			}
			TScEmpEntity empEntity = new TScEmpEntity();
			if(StringUtils.isNotEmpty(tScDrpOrder.getEmpID())){
				empEntity = systemService.getEntity(TScEmpEntity.class,tScDrpOrder.getEmpID());
			}
			TScDepartmentEntity departmentEntity = new TScDepartmentEntity();
			if(StringUtils.isNotEmpty(tScDrpOrder.getDeptID())){
				departmentEntity = systemService.getEntity(TScDepartmentEntity.class,tScDrpOrder.getDeptID());
			}
			TScStockEntity stockEntity = new TScStockEntity();
			if(StringUtils.isNotEmpty(tScDrpOrder.getStockID())){
				stockEntity = systemService.getEntity(TScStockEntity.class,tScDrpOrder.getStockID());
			}
			TSDepart depart = new TSDepart();
			if(StringUtils.isNotEmpty(tScDrpOrder.getSonID())){
				depart = systemService.getEntity(TSDepart.class,tScDrpOrder.getSonID());
			}
			TSUser user = new TSUser();
			if(StringUtils.isNotEmpty(tScDrpOrder.getBillerID())){
				user = systemService.getEntity(TSUser.class, tScDrpOrder.getBillerID());
			}
			if(null != organizationEntity){
				tScDrpOrder.setItemName(organizationEntity.getName());
			}
			if(null != empEntity){
				tScDrpOrder.setEmpName(empEntity.getName());
			}
			if(null != departmentEntity){
				tScDrpOrder.setDeptName(departmentEntity.getName());
			}
			if(null != stockEntity){
				tScDrpOrder.setStockName(stockEntity.getName());
			}
			if(null != depart){
				tScDrpOrder.setSonName(depart.getDepartname());
			}
			if(null != user){
				tScDrpOrder.setBillerName(user.getRealName());
			}
			String currentUserId = ResourceUtil.getSessionUserName().getId();
			TScOrganizationEntity scOrganizationEntity = systemService.findUniqueByProperty(TScOrganizationEntity.class, "userId", currentUserId);
			String delearType = "";
			if(null != scOrganizationEntity) {
				if (StringUtil.isNotEmpty(scOrganizationEntity.getTypeid())) {
					delearType = scOrganizationEntity.getTypeid();
				}
				if (StringUtil.isNotEmpty(scOrganizationEntity.getDealer())){
					TScOrganizationEntity tScOrganizationEntity = systemService.findUniqueByProperty(TScOrganizationEntity.class, "id", scOrganizationEntity.getDealer());
					if(null != tScOrganizationEntity){
						req.setAttribute("itemName",tScOrganizationEntity.getName());
						req.setAttribute("itemId",tScOrganizationEntity.getId());
					}
				}
			}
			req.setAttribute("delearType",delearType);
			if(null != tScDrpOrder.getTranType()){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				List<TSAuditRelationEntity> info = systemService.getAuditInfoList(tScDrpOrder.getId(),tScDrpOrder.getTranType().toString());
				String auditor = "";
				String auditDate = "";
				for (int i = 0; i < info.size(); i++) {
					TSAuditRelationEntity entity = info.get(i);
					if (1 == entity.getIsFinish()) {
						tScDrpOrder.setCheckState(Globals.AUDIT_FIN);
					}
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
				req.setAttribute("load", load);
				String checkDate = sdf.format(AccountUtil.getAccountStartDate());
				req.setAttribute("checkDate", checkDate);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return new ModelAndView("com/qihang/buss/sc/distribution/tScDrpOrder-update");
	}

  /**
  * 导入功能跳转
  *
  * @return
  */
  @RequestMapping(params = "upload")
  public ModelAndView upload(HttpServletRequest req) {
  req.setAttribute("controller_name", "tScDrpOrderController");
  return new ModelAndView("common/upload/pub_excel_upload");
  }

  /**
  * 导出excel
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXls")
  public String exportXls(TScDrpOrderEntity tScDrpOrder,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  CriteriaQuery cq = new CriteriaQuery(TScDrpOrderEntity.class, dataGrid);
  com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScDrpOrder, request.getParameterMap());
  List<TScDrpOrderEntity> tScDrpOrders = this.tScDrpOrderService.getListByCriteriaQuery(cq,false);
  //如需动态排除部分列不导出时启用，列名指Excel中文列名
  String[] exclusions = {"排除列名1","排除列名2"};
  modelMap.put(NormalExcelConstants.FILE_NAME,"订货管理");
  modelMap.put(NormalExcelConstants.CLASS,TScDrpOrderEntity.class);
  modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("订货管理列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
  "导出信息",exclusions));
	  modelMap.put(NormalExcelConstants.DATA_LIST, tScDrpOrders);
  return NormalExcelConstants.JEECG_EXCEL_VIEW;
  }
  /**
  * 导出excel 使模板
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXlsByT")
  public String exportXlsByT(TScDrpOrderEntity tScDrpOrder,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  modelMap.put(TemplateExcelConstants.FILE_NAME, "订货管理");
  modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
  modelMap.put(TemplateExcelConstants.MAP_DATA,null);
  modelMap.put(TemplateExcelConstants.CLASS,TScDrpOrderEntity.class);
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
    ExcelImportResult importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(),TScDrpOrderEntity.class,params);
    List<TScDrpOrderEntity> listTScDrpOrderEntitys = importResult.getList();
    StringBuilder stringBuilder = new StringBuilder("已存在的数据:");
    boolean flag = false;
    if(!importResult.isVerfiyFail()) {
      for (TScDrpOrderEntity tScDrpOrder : listTScDrpOrderEntitys) {
      //以下检查导入数据是否重复的语句可视需求启用
        //Long count = tScDrpOrderService.getCountForJdbc("这里放检查数据是否重复的SQL语句");
        //if(count >0) {
          //flag = true;
          //stringBuilder.append(tScDrpOrder.getId()+",");
        //} else {
          tScDrpOrderService.save(tScDrpOrder);
        //}
      }
      j.setMsg("文件导入成功！");
      //j.setMsg("文件导入成功！" + (flag? stringBuilder.toString():""));
    }else {
      String excelPath = "/upload/excelUpload/TScDrpOrderEntity/"+importResult.getExcelName();
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
	 * 加载明细列表[订单管理]
	 *
	 * @return
	 */
	@RequestMapping(params = "tScDrpOrderentryList")
	public ModelAndView tScDrpOrderentryList(TScDrpOrderEntity tScDrpOrder,@RequestParam(value = "pageType",required = false)Integer pageType, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id0 = tScDrpOrder.getId();
		//===================================================================================
		//查询-订单管理
	    String hql0 = "from TScDrpOrderentryEntity where 1 = 1 AND iNTERID = ? order by indexNumber";
	    try{
	    	List<TScDrpOrderentryEntity> tScDrpOrderentryEntityList = systemService.findHql(hql0,id0);
			for(TScDrpOrderentryEntity orderEntryEntity: tScDrpOrderentryEntityList){
				if(StringUtils.isNotEmpty(orderEntryEntity.getItemID())){
					TScIcitemEntity icitemEntity = systemService.get(TScIcitemEntity.class, orderEntryEntity.getItemID());
					if(null != icitemEntity){
						if(StringUtil.isNotEmpty(icitemEntity.getName())){
							orderEntryEntity.setGoodsName(icitemEntity.getName());
						}
						if(StringUtil.isNotEmpty(icitemEntity.getNumber())){
							orderEntryEntity.setGoodsNo(icitemEntity.getNumber());
						}
						if(StringUtil.isNotEmpty(icitemEntity.getModel())){
							orderEntryEntity.setModel(icitemEntity.getModel());
						}
						if(StringUtil.isNotEmpty(icitemEntity.getWeight())){
							orderEntryEntity.setItemWeight(icitemEntity.getWeight());
						}
						if(StringUtils.isNotEmpty(orderEntryEntity.getUnitID())){
							TScItemPriceEntity price = systemService.getEntity(TScItemPriceEntity.class, orderEntryEntity.getUnitID());
							orderEntryEntity.setBarCode(price.getBarCode());
						}
					}
				}
				if(StringUtils.isNotEmpty(orderEntryEntity.getStockID())){
					TScStockEntity stockEntity = systemService.get(TScStockEntity.class, orderEntryEntity.getStockID());
					if(null != stockEntity){
						orderEntryEntity.setStockName(stockEntity.getName());
					}
				}
			}
			req.setAttribute("pageType",pageType);
			req.setAttribute("tScDrpOrderentryList", tScDrpOrderentryEntityList);
		}catch(Exception e){
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/qihang/buss/sc/distribution/tScDrpOrderentryList");
	}

	//关闭功能
	@RequestMapping(params = "closeBill")
	@ResponseBody
	public AjaxJson closeBill(String ids){
		AjaxJson j = tScDrpOrderService.closeBill(ids);
		return j;
	}
	//反关闭功能
	@RequestMapping(params = "openBill")
	@ResponseBody
	public AjaxJson openBill(String ids){
		AjaxJson j = tScDrpOrderService.openBill(ids);
		return j;
	}

	//作废功能
	@RequestMapping(params = "cancelBill")
	@ResponseBody
	public AjaxJson cancelBill(String ids){
		AjaxJson j = tScDrpOrderService.cancelBill(ids);
		return j;
	}

	//反作废功能
	@RequestMapping(params = "enableBill")
	@ResponseBody
	public AjaxJson enableBill(String ids){
		AjaxJson j = tScDrpOrderService.enableBill(ids);
		return j;
	}

	/**
	 * 选单列表数据
	 * @param tScDrpOrder
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagridSelect")
	public void datagridSelect(TScDrpOrderViewEntity tScDrpOrder, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScDrpOrderViewEntity.class, dataGrid);
		tScDrpOrder.setSonId("000");
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScDrpOrder);
		try {
			//单据日期
			String query_date_begin = request.getParameter("date_begin");
			String query_date_end = request.getParameter("date_end");

			//审核日期
			String query_checkDate_begin = request.getParameter("checkDate_begin");
			String query_checkDate_end = request.getParameter("checkDate_end");

			String query_jhDate_begin = request.getParameter("jhDate_begin");
			String query_jhDate_end = request.getParameter("jhDate_end");

			String query_outDate_begin = request.getParameter("outDate_begin");
			String query_outDate_end = request.getParameter("outDate_end");

			if (StringUtil.isNotEmpty(query_date_begin)) {
				cq.ge("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_begin));
			}
			if (StringUtil.isNotEmpty(query_date_end)) {
				cq.le("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_end));
			}

			if (StringUtil.isNotEmpty(query_checkDate_begin)) {
				cq.ge("checkDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_checkDate_begin));
			}

			if (StringUtil.isNotEmpty(query_checkDate_end)) {
				cq.le("checkDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_checkDate_end));
			}

			if (StringUtil.isNotEmpty(query_jhDate_begin)) {
				cq.ge("jhDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_jhDate_begin));
			}

			if (StringUtil.isNotEmpty(query_jhDate_end)) {
				cq.le("jhDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_jhDate_end));
			}

			if (StringUtil.isNotEmpty(query_outDate_begin)) {
				cq.ge("outDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_outDate_begin));
			}

			if (StringUtil.isNotEmpty(query_outDate_end)) {
				cq.le("outDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_outDate_end));
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScDrpOrderService.getDataGridReturn(cq, true);
		String tranType = request.getParameter("tranType");
		if(StringUtils.isNotEmpty(tranType)){
			List<TScDrpOrderViewEntity> result = dataGrid.getResults();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for(TScDrpOrderViewEntity entity : result) {
				TSAuditRelationEntity info  = systemService.getAuditInfo(entity.getId(), tranType);
				if(info != null){
					if(1 == info.getIsFinish()){
						entity.setStatus(Globals.AUDIT_FIN);
						entity.setAuditorName(info.getAuditorName());
						entity.setAuditDate(sdf.format(info.getAuditDate()));
					}else{
						entity.setStatus(Globals.AUDIT_IN);
						entity.setAuditorName(info.getAuditorName());
						entity.setAuditDate(sdf.format(info.getAuditDate()));
					}
				}else {
					entity.setStatus(Globals.AUDIT_NO);
				}
				entity.setTranTypeName("分销订单");
			}
			dataGrid.setResults(result);
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 选单页面跳转
	 * @param drpOrderEntity
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "goSelectDialog")
	public ModelAndView goSelectDialog(TScDrpOrderEntity drpOrderEntity, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(drpOrderEntity.getItemID())) {
			req.setAttribute("itemId", drpOrderEntity.getItemID());
		}
		req.setAttribute("tranType", drpOrderEntity.getTranType());
		String sonId = req.getParameter("sonId");
		req.setAttribute("sonId",sonId);
		return new ModelAndView("com/qihang/buss/sc/distribution/tScDrpOrderSelectList");
	}

	/**
	 * 根据提供数据，获取报价单单价
	 * @param tranType
	 * @param customerId
	 * @param itemId
	 * @param qty
	 * @return
	 */
	@RequestMapping(params = "getPrice")
	@ResponseBody
	public AjaxJson getPrice(String tranType,String customerId,String itemId,BigDecimal qty,String unitId,String itemNo){
		//TODO 还需要数量字段,单位字段
		AjaxJson j = new AjaxJson();
		Map<String, Object> priceInfo = PriceUtil.getPrice(tranType, customerId, itemId, unitId, qty);
		Double price = (priceInfo.get("price")==null?0:(BigDecimal)priceInfo.get("price") ).doubleValue();//单价
		String priceType = (String) priceInfo.get("priceType");//单价类型
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
			List<TScPromotionViewEntity> promotiongList = promotionServiceI.getGiftsGoodsInfo(itemId, qty.doubleValue());
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
		CriteriaQuery cq = new CriteriaQuery(TScOrganizationEntity.class, dataGrid);
		List<String> list = new ArrayList<String>();
		if (StringUtil.isNotEmpty(tScOrganization.getParentid())) {
			TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart depart = systemService.getParentSonInfo(sonInfo);
			list = tScItemTypeService.getParentIdFromTree("01", tScOrganization.getParentid(),depart.getId());
			tScOrganization.setParentid(null);

		}
		String currentUserId = ResourceUtil.getSessionUserName().getId();
		TScOrganizationEntity scOrganizationEntity = systemService.findUniqueByProperty(TScOrganizationEntity.class, "userId", currentUserId);
		if(null != scOrganizationEntity){
			if(StringUtil.isNotEmpty(scOrganizationEntity.getDealer())){
				TScOrganizationEntity tScOrganizationEntity = systemService.get(TScOrganizationEntity.class, scOrganizationEntity.getDealer());
				if(null != tScOrganizationEntity){
					cq.eq("id", tScOrganizationEntity.getId());
				}
			}
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
	@RequestMapping(params = "goSelectToOrderDialog")
	public ModelAndView goSelectToOrderDialog(TScOrganizationEntity tScOrganization, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScOrganization.getName())) {
			req.setAttribute("name", tScOrganization.getName());
		}
		if(tScOrganization.getDisable() == Globals.IS_DEALER){
			req.setAttribute("isdealer", tScOrganization.getIsDealer());
		}
		if(null != tScOrganization.getIsDealer() && tScOrganization.getIsDealer().equals(Globals.IS_DEALER)){
			req.setAttribute("isdealer", tScOrganization.getIsDealer());
		}
		String currentUserId = ResourceUtil.getSessionUserName().getId();
		req.setAttribute("currentUserId",currentUserId);
		return new ModelAndView("com/qihang/buss/sc/distribution/tScOrganizationSelect2Order");
	}


	/**
	 * 删除订货管理
	 *
	 * @return
	 */
	@RequestMapping(params = "checkUnAduit")
	@ResponseBody
	public AjaxJson checkUnAduit(HttpServletRequest request) {
		String sconId = request.getParameter("sconId");
		AjaxJson j = new AjaxJson();
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		if(!sconId.equals(depart.getId())){
			j.setSuccess(false);
			j.setMsg("下级分销商订货单不允许被反审核");
		}
		return j;
	}
}
