
package com.qihang.buss.sc.distribution.controller;

import com.alibaba.fastjson.JSONObject;
import com.qihang.buss.sc.baseinfo.entity.*;
import com.qihang.buss.sc.baseinfo.service.*;
import com.qihang.buss.sc.distribution.entity.*;
import com.qihang.buss.sc.distribution.page.TScDrpStockbillPage;
import com.qihang.buss.sc.distribution.service.TScDrpLogisticalServiceI;
import com.qihang.buss.sc.distribution.service.TScDrpStockbillServiceI;
import com.qihang.buss.sc.sales.service.TScOrderServiceI;
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
import com.qihang.winter.web.system.pojo.base.TSConfig;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.pojo.base.TSUser;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.web.system.service.UserService;
import org.apache.batik.dom.svg12.Global;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
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
 * @Description: 发货管理
 * @author onlineGenerator
 * @date 2016-08-08 19:52:05
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScDrpStockbillController")
public class TScDrpStockbillController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScDrpStockbillController.class);

	@Autowired
	private TScDrpStockbillServiceI tScDrpStockbillService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TScDrpLogisticalServiceI tsCdRPlogisticalService;
	@Autowired
	private TScItemTypeServiceI tScItemTypeService;
	@Autowired
	private TScOrganizationServiceI tScOrganizationService;
	@Autowired
	private TScIcitemServiceI tScIcitemServiceI;//商品
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private TScOrganizationServiceI organizationServiceI;//供应商

	@Autowired
	private TScStockServiceI stockServiceI;//仓库

	@Autowired
	private TScEmpServiceI empServiceI;//经办人

	@Autowired
	private TScDepartmentServiceI departmentServiceI;//部门

	@Autowired
	private TScOrderServiceI orderServiceI;//销售订单

	@Autowired
	private TScLogisticalServiceI logisticalServiceI;//物流公司

	@Autowired
	private TScSettleacctServiceI settleacctServiceI;//结算账户
	@Autowired
	private CountCommonServiceI countCommonService;

	@Autowired
	private UserService userService;

	/**
	 * 发货管理列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tScDrpStockbill")
	public ModelAndView tScDrpStockbill(HttpServletRequest request) {
		request.setAttribute("tranType", Globals.SC_DRP_ORDER_STOCKBILL_TRANTYPE);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String checkDate = sdf.format(AccountUtil.getAccountStartDate());
		request.setAttribute("checkDate", checkDate);
		return new ModelAndView("com/qihang/buss/sc/distribution/tScDrpStockbillList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TSCViewDrpStockbillEntity tScDrpStockbill,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//收款金额,优惠金额，物流费用,数量,金额，折后金额,重量,不含税金额,不含税折后金额,税额,成本金额，退货数量,确认收货数量
		dataGrid.setFooter("allAmount,rebateAmount,freight,amountLoss,qty,taxAmountEx,inTaxAmount,weight,itemAmount,discountAmount,taxAmount,costAmount,commitQty,stockQty");
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		CriteriaQuery cq = new CriteriaQuery(TSCViewDrpStockbillEntity.class, dataGrid);
		if(StringUtil.isEmpty(tScDrpStockbill.getSonid())) {
			tScDrpStockbill.setSonid("000");
			Set<String> sonIds = systemService.getAllSonId(depart.getId());
			cq.in("sonid",sonIds.toArray());
		}
		String currentUserId = ResourceUtil.getSessionUserName().getId();
		TScOrganizationEntity scOrganizationEntity = systemService.findUniqueByProperty(TScOrganizationEntity.class, "userId", currentUserId);
		if(null != scOrganizationEntity){
			if(scOrganizationEntity.getTypeid().equals(Globals.SC_TYPE_BDEALER) || scOrganizationEntity.getTypeid().equals(Globals.SC_TYPE_CDEALER)){
				if(StringUtil.isNotEmpty(scOrganizationEntity.getDealer())){
					String hql = " from TSCViewDrpStockbillEntity where dealerId = ? ";
					List<TSCViewDrpStockbillEntity> tscViewDrpStockbillEntityList =  systemService.findHql(hql, scOrganizationEntity.getDealer());
					if(!tscViewDrpStockbillEntityList.isEmpty()){//如果集合不为空 就加载相应数据
						cq.add(Restrictions.or(
								Restrictions.and(Restrictions.eq("dealerId", scOrganizationEntity.getDealer()), Restrictions.eq("checkState", 2)),
								Restrictions.or(Restrictions.eq("billerId",currentUserId))
						));
					}
				}
			}else{
				cq.eq("billerId",currentUserId);
			}
		}
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScDrpStockbill);
		try{
			//自定义追加查询条件
			String query_date_begin = request.getParameter("date_begin");
			String query_date_end = request.getParameter("date_end");

			String query_affirmDate_begin = request.getParameter("affirmDate_begin");
			String query_affirmDate_end = request.getParameter("affirmDate_end");

			String query_checkDate_begin = request.getParameter("checkDate_begin");
			String query_checkDate_end = request.getParameter("checkDate_begin");

			String query_kfDate_begin = request.getParameter("kfDate_begin");
			String query_kfDate_end = request.getParameter("kfDate_end");

			if(StringUtil.isNotEmpty(query_date_begin)){
				cq.ge("date", Integer.parseInt(query_date_begin));
			}
			if(StringUtil.isNotEmpty(query_date_end)){
				cq.le("date", Integer.parseInt(query_date_end));
			}
			if(StringUtil.isNotEmpty(query_affirmDate_begin)){
				cq.ge("affirmDate", Integer.parseInt(query_affirmDate_begin));
			}
			if(StringUtil.isNotEmpty(query_affirmDate_end)){
				cq.le("affirmDate", Integer.parseInt(query_affirmDate_end));
			}
			if(StringUtil.isNotEmpty(query_checkDate_begin)){
				cq.ge("checkDate", Integer.parseInt(query_checkDate_begin));
			}
			if(StringUtil.isNotEmpty(query_checkDate_end)){
				cq.le("checkDate", Integer.parseInt(query_checkDate_end));
			}
			if(StringUtil.isNotEmpty(query_kfDate_begin)){
				cq.ge("kfDate", Integer.parseInt(query_kfDate_begin));
			}
			if(StringUtil.isNotEmpty(query_kfDate_end)){
				cq.le("kfDate", Integer.parseInt(query_kfDate_end));
			}
			String isWarm = request.getParameter("isWarm");
			if(org.apache.commons.lang.StringUtils.isNotEmpty(isWarm)) {
				String userId = ResourceUtil.getSessionUserName().getId();
				Boolean isAudit = userService.isAllowAudit(tScDrpStockbill.getTranType().toString(), userId, depart.getId());
				cq.eq("cancellation","0");
				//判断当前用户是否在多级审核队列中
				if (isAudit) {
					Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId, depart.getId(), tScDrpStockbill.getTranType().toString());
					if (userAuditLeave.size() > 0) {
						String leaves = "";
						for (Integer leave : userAuditLeave) {
							leaves += leave + ",";
						}
						leaves = leaves.substring(0, leaves.length() - 1);
						List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in (" + leaves + ")", new Object[]{depart.getId(), tScDrpStockbill.getTranType().toString()});
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
			this.tScDrpStockbillService.getDataGridReturn(cq, true);
			String tranType = request.getParameter("tranType");
			if (org.apache.commons.lang3.StringUtils.isNotEmpty(tranType)) {
				List<TSCViewDrpStockbillEntity> result = dataGrid.getResults();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				for (TSCViewDrpStockbillEntity entity : result) {
					TSAuditRelationEntity info = systemService.getAuditInfo(entity.getId(), tranType);
					if (info != null) {
						if (1 == info.getIsFinish()) {
							//entity.setCheckState(Globals.AUDIT_FIN);
							entity.setAuditorName(info.getAuditorName());
							entity.setAuditDate(sdf.format(info.getAuditDate()));
						} else {
							//entity.setCheckState(Globals.AUDIT_IN);
							entity.setAuditorName(info.getAuditorName());
							entity.setAuditDate(sdf.format(info.getAuditDate()));
						}
					}
				}
				dataGrid.setResults(result);
			}
		TagUtil.datagrid(response, dataGrid);
	}

	@RequestMapping(params = "datagridSelect")
	public void datagridSelect(TSCViewDrpStockbillEntity tScDrpStockbill,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSCViewDrpStockbillEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScDrpStockbill);
		try{
			//自定义追加查询条件
			String query_date_begin = request.getParameter("date_begin");
			String query_date_end = request.getParameter("date_end");

			String query_affirmDate_begin = request.getParameter("affirmDate_begin");
			String query_affirmDate_end = request.getParameter("affirmDate_end");

			String query_checkDate_begin = request.getParameter("checkDate_begin");
			String query_checkDate_end = request.getParameter("checkDate_begin");

			String query_kfDate_begin = request.getParameter("kfDate_begin");
			String query_kfDate_end = request.getParameter("kfDate_end");

			if(StringUtil.isNotEmpty(query_date_begin)){
				cq.ge("date", Integer.parseInt(query_date_begin));
			}
			if(StringUtil.isNotEmpty(query_date_end)){
				cq.le("date", Integer.parseInt(query_date_end));
			}
			if(StringUtil.isNotEmpty(query_affirmDate_begin)){
				cq.ge("affirmDate", Integer.parseInt(query_affirmDate_begin));
			}
			if(StringUtil.isNotEmpty(query_affirmDate_end)){
				cq.le("affirmDate", Integer.parseInt(query_affirmDate_end));
			}
			if(StringUtil.isNotEmpty(query_checkDate_begin)){
				cq.ge("checkDate", Integer.parseInt(query_checkDate_begin));
			}
			if(StringUtil.isNotEmpty(query_checkDate_end)){
				cq.le("checkDate", Integer.parseInt(query_checkDate_end));
			}
			if(StringUtil.isNotEmpty(query_kfDate_begin)){
				cq.ge("kfDate", Integer.parseInt(query_kfDate_begin));
			}
			if(StringUtil.isNotEmpty(query_kfDate_end)){
				cq.le("kfDate", Integer.parseInt(query_kfDate_end));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScDrpStockbillService.getDataGridReturn(cq, true);
		String tranType = request.getParameter("tranType");
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(tranType)) {
			List<TSCViewDrpStockbillEntity> result = dataGrid.getResults();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (TSCViewDrpStockbillEntity entity : result) {
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
				entity.setTranTypeName("分销发货单");
			}
			dataGrid.setResults(result);
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除发货管理
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScDrpStockbillEntity tScDrpStockbill, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScDrpStockbill = systemService.getEntity(TScDrpStockbillEntity.class, tScDrpStockbill.getId());
		String message = "发货管理删除成功";
		try{
			tScDrpStockbillService.delMain(tScDrpStockbill);
			TScDrpStockbillEntity t = tScDrpStockbillService.get(TScDrpStockbillEntity.class,tScDrpStockbill.getId());
			/**
			 * 修改引用次数
			 */
			//下游经销商
			countCommonService.deleteUpdateCount("T_SC_Organization", tScDrpStockbill.getDealerID());
			//仓库
			countCommonService.deleteUpdateCount("t_sc_stock", tScDrpStockbill.getrStockId());
			//经办人
			countCommonService.deleteUpdateCount("t_sc_emp", tScDrpStockbill.getEmpID());
			//部门
			countCommonService.deleteUpdateCount("t_sc_department", tScDrpStockbill.getDeptID());
			//结算账户
			countCommonService.deleteUpdateCount("T_SC_SettleAcct", tScDrpStockbill.getAccountID());

			/*TScDrpStockbillentryEntity tScDrpStockbillentryEntity =	tScDrpStockbillService.get(TScDrpStockbillentryEntity.class, tScDrpStockbill.getId());
			//商品
			countCommonService.deleteUpdateCount("t_sc_icitem", tScDrpStockbillentryEntity.getItemID());
			//仓库
			countCommonService.deleteUpdateCount("t_sc_stock", tScDrpStockbillentryEntity.getStockID());*/

			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			//删除待审核预警数据
			systemService.delBillAuditStatus(tScDrpStockbill.getTranType().toString(), tScDrpStockbill.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "发货管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除发货管理
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "发货管理删除成功";
		try{
			for(String id:ids.split(",")){
				TScDrpStockbillEntity tScDrpStockbill = systemService.getEntity(TScDrpStockbillEntity.class,
				id
				);
				tScDrpStockbillService.delMain(tScDrpStockbill);
				/**
				 * 修改引用次数
				 */
				//下游经销商
				countCommonService.deleteUpdateCount("T_SC_Organization", tScDrpStockbill.getDealerID());
				//仓库
				countCommonService.deleteUpdateCount("t_sc_stock", tScDrpStockbill.getrStockId());
				//经办人
				countCommonService.deleteUpdateCount("t_sc_emp", tScDrpStockbill.getEmpID());
				//部门
				countCommonService.deleteUpdateCount("t_sc_department", tScDrpStockbill.getDeptID());
				//结算账户
				countCommonService.deleteUpdateCount("T_SC_SettleAcct", tScDrpStockbill.getAccountID());


				/*TScDrpStockbillentryEntity tScDrpStockbillentryEntity =	tScDrpStockbillService.get(TScDrpStockbillentryEntity.class, tScDrpStockbill.getId());
				//商品
				countCommonService.deleteUpdateCount("t_sc_icitem", tScDrpStockbillentryEntity.getItemID());
				//仓库
				countCommonService.deleteUpdateCount("t_sc_stock", tScDrpStockbillentryEntity.getStockID());*/
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "发货管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加发货管理
	 *
	 * @param tScDrpStockbill
	 * @param tScDrpStockbillPage
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScDrpStockbillEntity tScDrpStockbill,TScDrpStockbillPage tScDrpStockbillPage, HttpServletRequest request) {
		List<TScDrpStockbillentryEntity> tScDrpStockbillentryList =  tScDrpStockbillPage.getTScDrpStockbillentryList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			tScDrpStockbill.setTranType(Globals.SC_DRP_ORDER_STOCKBILL_TRANTYPE.toString());
			tScDrpStockbill.setBillerID(ResourceUtil.getSessionUserName().getId());
			tScDrpStockbill.setCheckState(Globals.AUDIT_NO);
			tScDrpStockbill.setCancellation(Globals.CLOSE_NO);
			tScDrpStockbill.setAffirmStatus(Globals.SCDRPSTOCKBILL_NO);
			TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart departMain = systemService.getParentSonInfo(depart);
			tScDrpStockbill.setSonID(departMain.getId());
			String rStockId = request.getParameter("rStockId");
			String rStockName = request.getParameter("rStockName");
			if(StringUtil.isNotEmpty(rStockId)){
				tScDrpStockbill.setrStockId(rStockId);
			}
			if(StringUtil.isNotEmpty(rStockName)){
				tScDrpStockbill.setrStockName(rStockName);
			}

			/**
			 * 修改引用次数
			 */
			//下游经销商
			countCommonService.addUpdateCount("T_SC_Organization", tScDrpStockbill.getDealerID());
			//仓库
			countCommonService.addUpdateCount("t_sc_stock", tScDrpStockbill.getrStockId());
			//经办人
			countCommonService.addUpdateCount("t_sc_emp", tScDrpStockbill.getEmpID());
			//部门
			countCommonService.addUpdateCount("t_sc_department", tScDrpStockbill.getDeptID());
			//结算账户
			countCommonService.addUpdateCount("T_SC_SettleAcct",tScDrpStockbill.getAccountID());
			for(TScDrpStockbillentryEntity entity:tScDrpStockbillentryList){
				//商品
				countCommonService.addUpdateCount("t_sc_icitem",entity.getItemID());
				//仓库
				countCommonService.addUpdateCount("t_sc_stock", entity.getStockID());
			}
			Set<String> mainId = new HashSet<String>();
			for(TScDrpStockbillentryEntity entry : tScDrpStockbillentryList){
				if(StringUtils.isNotEmpty(entry.getInterIDOrder())){
					mainId.add(entry.getInterIDOrder());
				}
				double qty = 0;
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String currentDate = sdf.format(new Date());

				if(StringUtil.isNotEmpty(entry.getStockQty())){ //如果确认发货数量不为空
					//获得确认发货数量
					qty = entry.getStockQty().doubleValue();
				}

				if(StringUtil.isNotEmpty(entry.getFidOrder())){ //如果订单分录主键不为空
					//回抛发货数量和发货时间
					TScDrpOrderentryEntity tScDrpOrderentryEntity = systemService.get(TScDrpOrderentryEntity.class, entry.getFidOrder());
					if(null != tScDrpOrderentryEntity){
						double sunQty = tScDrpOrderentryEntity.getOutStockQty()==null?0:tScDrpOrderentryEntity.getOutStockQty().doubleValue()+qty;
						tScDrpOrderentryEntity.setOutStockQty(BigDecimal.valueOf(sunQty));//累加发货数量
						tScDrpOrderentryEntity.setOutDate(sdf.parse(currentDate));
					}
				}
				tScDrpStockbillService.addMain(tScDrpStockbill, tScDrpStockbillentryList);
			}
			if(mainId.size() > 0) {
				orderServiceI.checkAutoFlag(mainId);
			}
			//保存物流信息
			String logistical = request.getParameter("json");
			if(StringUtils.isNotEmpty(logistical)){
				TScDrpLogisticalEntity logisticalEntity = JSONObject.parseObject(logistical,TScDrpLogisticalEntity.class);
				logisticalEntity.setFid(tScDrpStockbill.getId());
				logisticalEntity.setId(null);
				systemService.saveOrUpdate(logisticalEntity);
				if(null != logisticalEntity.getFreight() && logisticalEntity.getFreight() > 0){
					tScDrpStockbill.setFreight(logisticalEntity.getFreight());
					systemService.saveOrUpdate(tScDrpStockbill);
				}
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			//待审核数据提醒操作
			//systemService.saveBillAuditStatus(tScDrpStockbill.getTranType().toString(), tScDrpStockbill.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "发货管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新发货管理
	 *
	 * @param tScDrpStockbill
	 * @param tScDrpStockbillPage
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScDrpStockbillEntity tScDrpStockbill,TScDrpStockbillPage tScDrpStockbillPage, HttpServletRequest request) {
		List<TScDrpStockbillentryEntity> tScDrpStockbillentryList =  tScDrpStockbillPage.getTScDrpStockbillentryList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		TScDrpStockbillEntity t = tScDrpStockbillService.get(TScDrpStockbillEntity.class, tScDrpStockbill.getId());
		try{
			sessionFactory.getCurrentSession().evict(t);
			tScDrpStockbillService.updateMain(tScDrpStockbill, tScDrpStockbillentryList);
			/**
			 * 修改引用次数
			 */
			//下游经销商
			countCommonService.editUpdateCount("T_SC_Organization",t.getDealerID(), tScDrpStockbill.getDealerID());
			//仓库
			countCommonService.editUpdateCount("t_sc_stock",t.getrStockId(), tScDrpStockbill.getrStockId());
			//经办人
			countCommonService.editUpdateCount("t_sc_emp", t.getEmpID(),tScDrpStockbill.getEmpID());
			//部门
			countCommonService.editUpdateCount("t_sc_department", t.getDeptID(),tScDrpStockbill.getDeptID());
			//结算账户
			countCommonService.editUpdateCount("T_SC_SettleAcct",t.getAccountID(), tScDrpStockbill.getAccountID());
			for(TScDrpStockbillentryEntity entity:tScDrpStockbillentryList){

				TScDrpStockbillentryEntity tScDrpStockbillentryEntity =	tScDrpStockbillService.get(TScDrpStockbillentryEntity.class, entity.getId());
				//商品
				countCommonService.editUpdateCount("t_sc_icitem",tScDrpStockbillentryEntity.getItemID(), entity.getItemID());
				//仓库
				countCommonService.editUpdateCount("t_sc_stock",tScDrpStockbillentryEntity.getStockID(), entity.getStockID());
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新发货管理失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 发货管理新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScDrpStockbillEntity tScDrpStockbill, HttpServletRequest req) {
		try {
			if(StringUtil.isNotEmpty(tScDrpStockbill.getId())){
				tScDrpStockbill = tScDrpStockbillService.getEntity(TScDrpStockbillEntity.class, tScDrpStockbill.getId());
				req.setAttribute("tScDrpStockbillPage", tScDrpStockbill);
			}else {
				String tranType = Globals.SC_DRP_ORDER_STOCKBILL_TRANTYPE.toString();
				req.setAttribute("tranType",tranType);
				String defaultTaxRate = "";//默认税率
				//从系统参数中获取默认税率
				//List<TSConfig> tsConfigList = this.tScSlStockbillService.findByProperty(TSConfig.class,"code","CFG_TAX_RATE");
				List<TSConfig> tsConfigList = tScDrpStockbillService.findHql("from TSConfig");
				if(tsConfigList.size()>0){
					for(TSConfig config : tsConfigList) {
						if("CFG_TAX_RATE".equals(config.getCode())) {
							defaultTaxRate = config.getContents();
						}else if("CFG_CONTROL_METHOD".equals(config.getCode())){
							if(StringUtils.isNotEmpty(config.getContents())) {
								req.setAttribute("method", config.getContents());//信用控制方式 0:允许继续保存审核 1：不允许继续保存审核
							}
						}else if("CFG_CONTROL_TIMEPOINT".equals(config.getCode())){
							if(StringUtils.isNotEmpty(config.getContents())) {
								req.setAttribute("timePoint", config.getContents());//信用控制时点 0：单据保存时 1：单据审核时
							}
						}
					}
				}
				if(StringUtils.isNotEmpty(defaultTaxRate)) {
					req.setAttribute("taxRate", defaultTaxRate);
				}else{
					req.setAttribute("taxRate",0);
				}

				String sonName = ResourceUtil.getSessionUserName().getCurrentDepart().getDepartname();
				req.setAttribute("sonName", sonName);
				tScDrpStockbill.setBillNo(BillNoGenerate.getBillNo(Globals.SC_QUOTE_TRANTYPE));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				req.setAttribute("tScDrpStockbillPage", tScDrpStockbill);
				req.setAttribute("date", sdf.format(new Date()));
				String checkDate = sdf.format(AccountUtil.getAccountStartDate());
				req.setAttribute("checkDate",checkDate);

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
				req.setAttribute("sonName",depart.getDepartname());
				String title = "分销订单";
			/*String amountStr = "应收";
			String payType = "收款";
			req.setAttribute("amountStr",amountStr);
			req.setAttribute("payType",payType);*/
				req.setAttribute("title",title);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return new ModelAndView("com/qihang/buss/sc/distribution/tScDrpStockbill-add");
	}

	/**
	 * 发货管理编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScDrpStockbillEntity tScDrpStockbill, HttpServletRequest req) {
		try {
			String load = req.getParameter("load");
			if (StringUtil.isNotEmpty(tScDrpStockbill.getId())) {
				tScDrpStockbill = tScDrpStockbillService.getEntity(TScDrpStockbillEntity.class, tScDrpStockbill.getId());
				TScOrganizationEntity organizationEntity = new TScOrganizationEntity();
				if(StringUtils.isNotEmpty(tScDrpStockbill.getDealerID())){
					organizationEntity = systemService.getEntity(TScOrganizationEntity.class,tScDrpStockbill.getDealerID());
				}
				if(null != organizationEntity){
					tScDrpStockbill.setDealerName(organizationEntity.getName());
				}
				//经办人
				if (org.apache.commons.lang3.StringUtils.isNotEmpty(tScDrpStockbill.getEmpID())) {
					TScEmpEntity entity = systemService.get(TScEmpEntity.class, tScDrpStockbill.getEmpID());
					if (entity != null) {
						if (org.apache.commons.lang.StringUtils.isNotEmpty(entity.getName())) {
							tScDrpStockbill.setEmpName(entity.getName());
							tScDrpStockbill.setEmpID(entity.getId());
						}
					}
				}
				//部门
				if (org.apache.commons.lang3.StringUtils.isNotEmpty(tScDrpStockbill.getDeptID())) {
					TScDepartmentEntity entity = systemService.get(TScDepartmentEntity.class, tScDrpStockbill.getDeptID());
					if (entity != null) {
						if (org.apache.commons.lang3.StringUtils.isNotEmpty(entity.getName())) {
							tScDrpStockbill.setDeptName(entity.getName());
							tScDrpStockbill.setDeptID(entity.getId());
						}
					}
				}
				//分支机构
				if (org.apache.commons.lang3.StringUtils.isNotEmpty(tScDrpStockbill.getSonID())) {
					TSDepart dept = systemService.get(TSDepart.class, tScDrpStockbill.getSonID());
					if (null != dept) {
						tScDrpStockbill.setSonName(dept.getDepartname());
					}
				}
				//仓库
				if (org.apache.commons.lang3.StringUtils.isNotEmpty(tScDrpStockbill.getrStockId())) {
					TScStockEntity stockEntity = systemService.get(TScStockEntity.class, tScDrpStockbill.getrStockId());
					if (null != stockEntity) {
						tScDrpStockbill.setrStockName(stockEntity.getName());
					}
				}
				if (org.apache.commons.lang3.StringUtils.isNotEmpty(tScDrpStockbill.getBillerID())) {
					TSBaseUser user = systemService.get(TSBaseUser.class, tScDrpStockbill.getBillerID());
					if (null != user) {
						tScDrpStockbill.setBillerName(user.getRealName());
					}
				}
				TScStockEntity stockEntity = new TScStockEntity();
				if(StringUtils.isNotEmpty(tScDrpStockbill.getrStockId())){
					stockEntity = systemService.getEntity(TScStockEntity.class,tScDrpStockbill.getrStockId());
				}
				TSUser cheUser = new TSUser();
				if(StringUtils.isNotEmpty(tScDrpStockbill.getCheckerID())){
					cheUser = systemService.getEntity(TSUser.class, tScDrpStockbill.getCheckerID());
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				req.setAttribute("tScDrpStockbillPage", tScDrpStockbill);
				String checkDate = sdf.format(AccountUtil.getAccountStartDate());
				req.setAttribute("checkDate",checkDate);

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

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			if(null != tScDrpStockbill.getTranType()){
				List<TSAuditRelationEntity> info = systemService.getAuditInfoList(tScDrpStockbill.getId(),tScDrpStockbill.getTranType().toString());
				String auditor = "";
				String auditDate = "";
				for (int i = 0; i < info.size(); i++) {
					TSAuditRelationEntity entity = info.get(i);
					if (1 == entity.getIsFinish()) {
						tScDrpStockbill.setCheckState(Globals.AUDIT_FIN);
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
			}
			if(StringUtil.isNotEmpty(tScDrpStockbill.getAffirmID())){
				TScDrpStockbillEntity tScDrpStockbillEntity =  systemService.findUniqueByProperty(TScDrpStockbillEntity.class, "affirmID", tScDrpStockbill.getAffirmID());
				String affirmtor = "";
				String affirmDate = "";
				String amountLoss = "";
				if(null != tScDrpStockbillEntity){
					affirmtor = tScDrpStockbillEntity.getAffirmName();
					affirmDate = sdf.format(tScDrpStockbillEntity.getAffirmDate());
					amountLoss = tScDrpStockbillEntity.getAmountLoss()+"";
					req.setAttribute("affirmtor", affirmtor);
					req.setAttribute("affirmDate", affirmDate);
					req.setAttribute("amountLoss", amountLoss);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return new ModelAndView("com/qihang/buss/sc/distribution/tScDrpStockbill-update");
	}

  /**
  * 导入功能跳转
  *
  * @return
  */
  @RequestMapping(params = "upload")
  public ModelAndView upload(HttpServletRequest req) {
  req.setAttribute("controller_name", "tScDrpStockbillController");
  return new ModelAndView("common/upload/pub_excel_upload");
  }

  /**
  * 导出excel
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXls")
  public String exportXls(TScDrpStockbillEntity tScDrpStockbill,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  CriteriaQuery cq = new CriteriaQuery(TScDrpStockbillEntity.class, dataGrid);
  com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScDrpStockbill, request.getParameterMap());
  List<TScDrpStockbillEntity> tScDrpStockbills = this.tScDrpStockbillService.getListByCriteriaQuery(cq,false);
  //如需动态排除部分列不导出时启用，列名指Excel中文列名
  String[] exclusions = {"排除列名1","排除列名2"};
  modelMap.put(NormalExcelConstants.FILE_NAME,"发货管理");
  modelMap.put(NormalExcelConstants.CLASS,TScDrpStockbillEntity.class);
  modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("发货管理列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
  "导出信息",exclusions));
  modelMap.put(NormalExcelConstants.DATA_LIST, tScDrpStockbills);
  return NormalExcelConstants.JEECG_EXCEL_VIEW;
  }
  /**
  * 导出excel 使模板
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXlsByT")
  public String exportXlsByT(TScDrpStockbillEntity tScDrpStockbill,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  modelMap.put(TemplateExcelConstants.FILE_NAME, "发货管理");
  modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
  modelMap.put(TemplateExcelConstants.MAP_DATA,null);
  modelMap.put(TemplateExcelConstants.CLASS,TScDrpStockbillEntity.class);
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
    ExcelImportResult importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(),TScDrpStockbillEntity.class,params);
    List<TScDrpStockbillEntity> listTScDrpStockbillEntitys = importResult.getList();
    StringBuilder stringBuilder = new StringBuilder("已存在的数据:");
    boolean flag = false;
    if(!importResult.isVerfiyFail()) {
      for (TScDrpStockbillEntity tScDrpStockbill : listTScDrpStockbillEntitys) {
      //以下检查导入数据是否重复的语句可视需求启用
        //Long count = tScDrpStockbillService.getCountForJdbc("这里放检查数据是否重复的SQL语句");
        //if(count >0) {
          //flag = true;
          //stringBuilder.append(tScDrpStockbill.getId()+",");
        //} else {
          tScDrpStockbillService.save(tScDrpStockbill);
        //}
      }
      j.setMsg("文件导入成功！");
      //j.setMsg("文件导入成功！" + (flag? stringBuilder.toString():""));
    }else {
      String excelPath = "/upload/excelUpload/TScDrpStockbillEntity/"+importResult.getExcelName();
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
	 * 加载明细列表[商品信息]
	 *
	 * @return
	 */
	@RequestMapping(params = "tScDrpStockbillentryList")
	public ModelAndView tScDrpStockbillentryList(TScDrpStockbillEntity tScDrpStockbill, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id0 = tScDrpStockbill.getId();
		//===================================================================================
		//查询-商品信息
	    String hql0 = "from TScDrpStockbillentryEntity where 1 = 1 AND fID = ? ";
	    try{
	    	List<TScDrpStockbillentryEntity> tScDrpStockbillentryEntityList = systemService.findHql(hql0,id0);
			for (TScDrpStockbillentryEntity entity : tScDrpStockbillentryEntityList) {
				//商品
				if (org.apache.commons.lang.StringUtils.isNotEmpty(entity.getItemID())) {
					TScIcitemEntity icitemEntity = systemService.get(TScIcitemEntity.class, entity.getItemID());
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
				if (org.apache.commons.lang.StringUtils.isNotEmpty(entity.getUnitID())) {
					TScItemPriceEntity itemPriceEntity = systemService.get(TScItemPriceEntity.class, entity.getUnitID());
					if (itemPriceEntity != null) {
						if (org.apache.commons.lang.StringUtils.isNotEmpty(itemPriceEntity.getBarCode())) {
							entity.setBarCode(itemPriceEntity.getBarCode());
						}
					}
				}
				//仓库
				if(StringUtil.isNotEmpty(entity.getStockID())){
					TScStockEntity tScStockEntity =  systemService.get(TScStockEntity.class,entity.getStockID());
					if(null != tScStockEntity){
						entity.setStockName(tScStockEntity.getName());
					}
				}
			}
			req.setAttribute("tScDrpStockbillentryList", tScDrpStockbillentryEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/qihang/buss/sc/distribution/tScDrpStockbillentryList");
	}

	@RequestMapping(params = "goLogisticalUpdate")
	public ModelAndView goLogisticalUpdate(TScDrpLogisticalEntity entity,HttpServletRequest request){
		String load = request.getParameter("load");
		request.setAttribute("load",load);
		String logisticalValue = request.getParameter("logisticalValue");
		if(StringUtils.isEmpty(logisticalValue)) {
			if (StringUtil.isNotEmpty(entity.getFid())) {
				TScDrpLogisticalEntity drpLogisticalEntity = tsCdRPlogisticalService.findUniqueByProperty(TScDrpLogisticalEntity.class, "fid", entity.getFid());
				if (null != drpLogisticalEntity) {
					String logisticalId = drpLogisticalEntity.getLogisticalID();
					if (StringUtils.isNotEmpty(logisticalId)) {
						TScLogisticalEntity logisticalEntity = systemService.getEntity(TScDrpLogisticalEntity.class, logisticalId);
						if (null != logisticalEntity) {
							drpLogisticalEntity.setLogisticalName(logisticalEntity.getName());
						}
					}
					String accountId = drpLogisticalEntity.getAccountID();
					if (StringUtils.isNotEmpty(accountId)) {
						TScSettleacctEntity scSettleacctEntity = systemService.getEntity(TScSettleacctEntity.class, accountId);
						if (null != scSettleacctEntity) {
							drpLogisticalEntity.setAccountName(scSettleacctEntity.getName());
						}
					}
					request.setAttribute("tScDrpLogisticalPage", drpLogisticalEntity);
				} else {
					request.setAttribute("tScDrpLogisticalPage", entity);
				}
			} else {
				request.setAttribute("tScDrpLogisticalPage", entity);
			}
		}else{
			TScDrpLogisticalEntity logisticalEntity = JSONObject.parseObject(logisticalValue, TScDrpLogisticalEntity.class);
			request.setAttribute("tScDrpLogisticalPage", logisticalEntity);
		}
		return new ModelAndView("com/qihang/buss/sc/distribution/tScDrpLogistical-update");
	}

	//反关闭功能
	@RequestMapping(params = "openBill")
	@ResponseBody
	public AjaxJson openBill(String ids){
		AjaxJson j = tScDrpStockbillService.openBill(ids);
		return j;
	}

	//作废功能
	@RequestMapping(params = "cancelBill")
	@ResponseBody
	public AjaxJson cancelBill(String ids){
		AjaxJson j = tScDrpStockbillService.cancelBill(ids);
		return j;
	}

	//反作废功能
	@RequestMapping(params = "enableBill")
	@ResponseBody
	public AjaxJson enableBill(String ids){
		AjaxJson j = tScDrpStockbillService.enableBill(ids);
		return j;
	}

	//审核及反审核后执行
	@RequestMapping(params = "afterAudit")
	@ResponseBody
	public AjaxJson afterAudit(String id,Integer audit){

		AjaxJson j = tScDrpStockbillService.afterAudit(id, audit);
		return j;
	}

	//选单界面跳转
	@RequestMapping(params = "goSelectDialog")
	public ModelAndView goSelectDialog(TScDrpStockbillEntity stockBill, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(stockBill)) {
			req.setAttribute("itemId", stockBill.getDealerID());
		}
		req.setAttribute("tranType",stockBill.getTranType());
		String sonId = req.getParameter("sonId");
		req.setAttribute("sonId",sonId);
		return new ModelAndView("com/qihang/buss/sc/distribution/tScDrpStockbillSelectList");
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
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
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
		if(StringUtil.isEmpty(tScOrganization.getSonId())) {
			tScOrganization.setSonId("000");
			/*Set<String> sonIds = systemService.getAllSonId(depart.getId());
			cq.in("sonId",sonIds.toArray());*/
		}
		String currentUserId = ResourceUtil.getSessionUserName().getId();
		TScOrganizationEntity scOrganizationEntity = systemService.findUniqueByProperty(TScOrganizationEntity.class, "userId", currentUserId);
		if (null != scOrganizationEntity) {//根据当前登录用户是审查过的
			//String id = "";
			if (StringUtil.isNotEmpty(scOrganizationEntity.getId())) {//根据当前登录经销商的记录id 匹配自己下级经销商
				//根据id 查询资格审查表 如果表中存在 该记录 就不在弹窗列表中显示
				String hqlByDealer = " select id from TScOrganizationEntity where dealer = ? ";
				List<TScOrganizationEntity> tScOrganizationEntityList = systemService.findHql(hqlByDealer, scOrganizationEntity.getId());
				if(!tScOrganizationEntityList.isEmpty()){
					cq.in("id",tScOrganizationEntityList.toArray());
				}
			}
			/*String[] ids = null;
			if(!"".equals(id)){
				if(id.endsWith(",")){
					id = id.substring(0,id.lastIndexOf(","));
					if(id.indexOf(",") > -1){
						ids = id.split(",");
						cq.in("id",ids);
					}else{
						cq.eq("id",id);
					}
				}
			}else{
				cq.eq("id","test");//如果没有查询出自己所属的下级经销商 弹窗就不显示内容 随便给Id赋值，使列表不出现记录
			}*/
		}else{//不是经销商用户就加载 所有一级经销商
			cq.eq("typeid", Globals.SC_TYPE_ADEALER);
		}
		List<String> list = new ArrayList<String>();
		if (StringUtil.isNotEmpty(tScOrganization.getParentid())) {
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
	@RequestMapping(params = "goSelectToStockbillDialog")
	public ModelAndView goSelectToStockbillDialog(TScOrganizationEntity tScOrganization, HttpServletRequest req) {
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
		return new ModelAndView("com/qihang/buss/sc/distribution/tScOrganizationSelect2Stock");
	}
}
