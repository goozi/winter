
package com.qihang.buss.sc.sales.controller;
import com.alibaba.fastjson.JSONObject;
import com.qihang.buss.sc.baseinfo.entity.*;
import com.qihang.buss.sc.baseinfo.service.*;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryBatchnoEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryEntity;
import com.qihang.buss.sc.mall.service.StockbillInterServiceI;
import com.qihang.buss.sc.sales.entity.*;
import com.qihang.buss.sc.sales.service.TScOrderServiceI;
import com.qihang.buss.sc.sales.service.TScSlStockbillServiceI;
import com.qihang.buss.sc.sales.page.TScSlStockbillPage;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TSBillInfoEntity;
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
import com.qihang.winter.tag.vo.datatable.SortDirection;
import com.qihang.winter.web.system.pojo.base.TSConfig;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.pojo.base.TSUser;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.web.system.service.UserService;
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
 * @Description: 销售出库单
 * @author onlineGenerator
 * @date 2016-08-01 15:25:28
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScSlStockbillController")
public class TScSlStockbillController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScSlStockbillController.class);

	@Autowired
	private TScSlStockbillServiceI tScSlStockbillService;
	@Autowired
	private SystemService systemService;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private TScIcitemServiceI tScIcitemServiceI;//商品

	@Autowired
	private TScOrganizationServiceI organizationServiceI;//客户

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
	private StockbillInterServiceI stockbillInterServiceI;

	@Autowired
	private UserService userService;



	/**
	 * 销售出库单列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tScSlStockbill")
	public ModelAndView tScSlStockbill(HttpServletRequest request) {
		List<TSConfig> tsConfigList = tScSlStockbillService.findHql("from TSConfig");
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
		//是否允许负库存出库
		if(AccountUtil.isMinusInventorySI()) {
			request.setAttribute("isCheckNegative",false);
		}else{
			request.setAttribute("isCheckNegative",true);
		}
		Date checkDate = AccountUtil.getAccountStartDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(checkDate);
		request.setAttribute("checkDate",dateStr);
		return new ModelAndView("com/qihang/buss/sc/sales/tScSlStockbillList");
	}

	/**
	 * 销售退货单列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tScSlStockbillReturn")
	public ModelAndView tScSlStockbillReturn(HttpServletRequest request) {
		List<TSConfig> tsConfigList = tScSlStockbillService.findHql("from TSConfig");
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
		//是否允许负库存出库
		if(AccountUtil.isMinusInventorySI()) {
			request.setAttribute("isCheckNegative",false);
		}else{
			request.setAttribute("isCheckNegative",true);
		}
		return new ModelAndView("com/qihang/buss/sc/sales/tScSlStockbillReturnList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScSlStockbillViewEntity tScSlStockbill,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//汇总应付金额，已付金额，数量，金额，折后金额
		dataGrid.setFooter("allAmount,checkAmount,qty,taxAmountEx,inTaxAmount");
		CriteriaQuery cq = new CriteriaQuery(TScSlStockbillViewEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScSlStockbill);
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
			cq.addOrder("cancellation", SortDirection.asc);
			//String sonId = ResourceUtil.getSessionUserName().getCurrentDepart().getId();
			//cq.eq("sonid",sonId);
			String isWarm = request.getParameter("isWarm");
			if(StringUtils.isNotEmpty(isWarm)){
				String userId = ResourceUtil.getSessionUserName().getId();
				TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
				TSDepart depart = systemService.getParentSonInfo(sonInfo);
				Boolean isAudit = userService.isAllowAudit(tScSlStockbill.getTranType().toString(),userId,depart.getId());
				cq.eq("cancellation",0);
				//判断当前用户是否在多级审核队列中
				if(isAudit){
					Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId,depart.getId(),tScSlStockbill.getTranType().toString());
					if(userAuditLeave.size() > 0){
						String leaves = "";
						for(Integer leave : userAuditLeave){
							leaves += leave+",";
						}
						leaves = leaves.substring(0,leaves.length()-1);
						List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in ("+leaves+")",new Object[]{depart.getId(),tScSlStockbill.getTranType().toString()});
						if(billInfo.size() > 0){
							List<String> idArr = new ArrayList<String>();
							for(TScBillAuditStatusEntity entity : billInfo){
								idArr.add(entity.getBillId());
							}
							cq.in("id",idArr.toArray());
						} else {
							cq.eq("id","0");
						}
					}
				}
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScSlStockbillService.getDataGridReturn(cq, true);
		List<TScSlStockbillViewEntity> resultList = dataGrid.getResults();
		for(TScSlStockbillViewEntity result : resultList){
			BigDecimal countAmount = BillCountUtil.getReceivableCountByCustomerId(result.getItemid());//已执行金额
			BigDecimal allAmount = new BigDecimal(result.getAllamount() == null ? 0.0 : result.getAllamount());//应付金额
			BigDecimal creditamount = result.getCreditamount();//信用额度
			Integer iscreditmgr = result.getIscreditmgr();//是否信用标记
			if(0 != iscreditmgr){
				if(!countAmount.equals(BigDecimal.ZERO)){
					if(countAmount.compareTo(creditamount) > 0){
						result.setIsAllowAudit("N");
					}else{
						result.setIsAllowAudit("Y");
					}
				}else{
					if(allAmount.compareTo(creditamount) > 0){
						result.setIsAllowAudit("N");
					}else{
						result.setIsAllowAudit("Y");
					}
				}
			}else{
				result.setIsAllowAudit("Y");
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除销售出库单
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScSlStockbillEntity tScSlStockbill, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScSlStockbill = systemService.getEntity(TScSlStockbillEntity.class, tScSlStockbill.getId());
		String message = "销售出库单删除成功";
		try{
			//客户
			if(StringUtils.isNotEmpty(tScSlStockbill.getItemid())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_DEL_TYPE);
				countEntity.setOldId(tScSlStockbill.getItemid());
				boolean updateIsSuccess = organizationServiceI.updateOrganizationCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料客户引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//经办人
			if(StringUtils.isNotEmpty(tScSlStockbill.getEmpid())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_DEL_TYPE);
				countEntity.setOldId(tScSlStockbill.getEmpid());
				boolean updateIsSuccess = empServiceI.updateEmpCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料职员引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//部门
			if(StringUtils.isNotEmpty(tScSlStockbill.getDeptid())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_DEL_TYPE);
				countEntity.setOldId(tScSlStockbill.getDeptid());
				boolean updateIsSuccess = departmentServiceI.updateDepartmentCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料部门引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//仓库
			if(StringUtils.isNotEmpty(tScSlStockbill.getStockid())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_DEL_TYPE);
				countEntity.setOldId(tScSlStockbill.getStockid());
				boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料部门引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			if(StringUtils.isNotEmpty(tScSlStockbill.getAccountid())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_DEL_TYPE);
				countEntity.setOldId(tScSlStockbill.getAccountid());
				boolean updateIsSuccess = settleacctServiceI.updateOrganizationCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料部门引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			List<TScSlStockbillentryEntity> entryList = tScSlStockbillService.findHql("from TScSlStockbillentryEntity where fid = ?",new Object[]{tScSlStockbill.getId()});
			for(TScSlStockbillentryEntity entry : entryList){
				//商品
				if(StringUtils.isNotEmpty(entry.getItemid())){
					TScCountEntity countEntity = new TScCountEntity();
					countEntity.setType(Globals.COUNT_DEL_TYPE);
					countEntity.setOldId(entry.getItemid());
					boolean updateIsSuccess = tScIcitemServiceI.updateIcitemCount(countEntity);
					if (updateIsSuccess == false) {
						message = "更新基础资料商品引用次数失败";
						j.setMsg(message);
						return j;
					}
				}
				//仓库
				if(StringUtils.isNotEmpty(entry.getStockid())){
					TScCountEntity countEntity = new TScCountEntity();
					countEntity.setType(Globals.COUNT_DEL_TYPE);
					countEntity.setOldId(entry.getStockid());
					boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
					if (updateIsSuccess == false) {
						message = "更新基础资料仓库引用次数失败";
						j.setMsg(message);
						return j;
					}
				}
			}
			tScSlStockbillService.delMain(tScSlStockbill);
			//校验订单是否自动关闭
			Set<String> mainId = new HashSet<String>();
			for(TScSlStockbillentryEntity entry : entryList){
				if(StringUtils.isNotEmpty(entry.getIdOrder())){
					mainId.add(entry.getIdOrder());
				}
			}
			if(mainId.size() > 0) {
				orderServiceI.checkAutoFlag(mainId);
			}

			TScSlLogisticalEntity logisticalEntity = systemService.findUniqueByProperty(TScSlLogisticalEntity.class,"fid",tScSlStockbill.getId());
			if(null != logisticalEntity) {
				systemService.delete(logisticalEntity);
			}

			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			//删除待审核预警数据
			systemService.delBillAuditStatus(tScSlStockbill.getTrantype().toString(), tScSlStockbill.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "销售出库单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除销售出库单
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "销售出库单删除成功";
		try{
			for(String id:ids.split(",")){
				TScSlStockbillEntity tScSlStockbill = systemService.getEntity(TScSlStockbillEntity.class,
				id
				);
				tScSlStockbillService.delMain(tScSlStockbill);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "销售出库单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加销售出库单
	 *
	 * @param tScSlStockbill
	 * @param tScSlStockbillPage
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScSlStockbillEntity tScSlStockbill,TScSlStockbillPage tScSlStockbillPage, HttpServletRequest request) {
		List<TScSlStockbillentryEntity> tScSlStockbillentryList =  tScSlStockbillPage.getTScSlStockbillentryList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		//校验单据编号唯一性
		Boolean hasBillNo = systemService.checkBillNo(TScOrderEntity.class.getAnnotation(Table.class).name(),tScSlStockbill.getBillNo(),tScSlStockbill.getId());
		if(!hasBillNo){
			j.setSuccess(false);
			j.setMsg("单据编号已存在,请确认");
			return j;
		}
		try{
			//若本次收款不为空，则将本次付款写入已收款金额
			if(null != tScSlStockbill.getCurpayamount()){
				tScSlStockbill.setCheckamount(tScSlStockbill.getCurpayamount());
			}
			tScSlStockbillService.addMain(tScSlStockbill, tScSlStockbillentryList);
			//客户
			if (org.apache.commons.lang.StringUtils.isNotEmpty(tScSlStockbill.getItemid())) {
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_ADD_TYPE);
				countEntity.setOldId(tScSlStockbill.getItemid());
				boolean updateIsSuccess = organizationServiceI.updateOrganizationCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料客户引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//仓库
			if(StringUtils.isNotEmpty(tScSlStockbill.getStockid())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_ADD_TYPE);
				countEntity.setOldId(tScSlStockbill.getStockid());
				boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料仓库引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//经办人
			if(StringUtils.isNotEmpty(tScSlStockbill.getEmpid())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_ADD_TYPE);
				countEntity.setOldId(tScSlStockbill.getEmpid());
				boolean updateIsSuccess = empServiceI.updateEmpCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料职员引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//部门
			if(StringUtils.isNotEmpty(tScSlStockbill.getDeptid())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_ADD_TYPE);
				countEntity.setOldId(tScSlStockbill.getDeptid());
				boolean updateIsSuccess = departmentServiceI.updateDepartmentCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料职员引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//结算账户
			if(StringUtils.isNotEmpty(tScSlStockbill.getAccountid())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_ADD_TYPE);
				countEntity.setOldId(tScSlStockbill.getAccountid());
				boolean updateIsSuccess = settleacctServiceI.updateOrganizationCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料结算账户引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			for(TScSlStockbillentryEntity entry : tScSlStockbillentryList){
				//商品
				if(StringUtils.isNotEmpty(entry.getItemid())){
					TScCountEntity countEntity = new TScCountEntity();
					countEntity.setType(Globals.COUNT_ADD_TYPE);
					countEntity.setOldId(entry.getItemid());
					boolean updateIsSuccess = tScIcitemServiceI.updateIcitemCount(countEntity);
					if (updateIsSuccess == false) {
						message = "更新基础资料商品引用次数失败";
						j.setMsg(message);
						return j;
					}
				}
				//仓库
				if(StringUtils.isNotEmpty(entry.getStockid())){
					TScCountEntity countEntity = new TScCountEntity();
					countEntity.setType(Globals.COUNT_ADD_TYPE);
					countEntity.setOldId(entry.getStockid());
					boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
					if (updateIsSuccess == false) {
						message = "更新基础资料仓库引用次数失败";
						j.setMsg(message);
						return j;
					}
				}
			}
			Set<String> mainId = new HashSet<String>();
			for(TScSlStockbillentryEntity entry : tScSlStockbillentryList){
				if(StringUtils.isNotEmpty(entry.getIdOrder())){
					mainId.add(entry.getIdOrder());
				}
			}
			if(mainId.size() > 0) {
				orderServiceI.checkAutoFlag(mainId);
			}
			//保存后回写订单状态
			//若存在源单且单据为销售出库单,且审核通过
			if(StringUtil.isNotEmpty(tScSlStockbill.getIdSrc()) && Globals.SC_SL_STOCKBILL_TRANTYPE.equals(Integer.parseInt(tScSlStockbill.getTrantype()))){
				String ids = "";
				for(String mId : mainId){
					ids += "'"+mId+"',";
				}
				if(StringUtil.isNotEmpty(ids)){
					ids = ids.substring(0,ids.length()-1);
				}
				List<TScOrderEntity> orderEntityList = systemService.findHql("from TScOrderEntity where id in ("+ids+")");
				String moreOrderId = "";
				for(TScOrderEntity orderEntity : orderEntityList) {
					if (2 == orderEntity.getMall()) {
						moreOrderId += orderEntity.getMallOrderID()+",";
					}
				}
				if(StringUtil.isNotEmpty(moreOrderId)){
					moreOrderId = moreOrderId.substring(0,moreOrderId.length()-1);
				}
				stockbillInterServiceI.synStockstate(moreOrderId, tScSlStockbill.getId());//返回出库状态
			}

			//保存物流信息
			String logistical = request.getParameter("json");
			if(StringUtils.isNotEmpty(logistical)){
				TScSlLogisticalEntity logisticalEntity = JSONObject.parseObject(logistical, TScSlLogisticalEntity.class);
				logisticalEntity.setFid(tScSlStockbill.getId());
				logisticalEntity.setId(null);
				if(null != logisticalEntity.getCurPayAmount()) {
					logisticalEntity.setCheckAmount(logisticalEntity.getCurPayAmount());
				}else{
					logisticalEntity.setCheckAmount(0.0);
				}
				systemService.saveOrUpdate(logisticalEntity);
				if(null != logisticalEntity.getFreight() && logisticalEntity.getFreight() > 0){
					tScSlStockbill.setFreight(logisticalEntity.getFreight());
					systemService.saveOrUpdate(tScSlStockbill);
				}
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			//待审核数据提醒操作
			systemService.saveBillAuditStatus(tScSlStockbill.getTrantype().toString(), tScSlStockbill.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "销售出库单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新销售出库单
	 *
	 * @param tScSlStockbill
	 * @param tScSlStockbillPage
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScSlStockbillEntity tScSlStockbill,TScSlStockbillPage tScSlStockbillPage, HttpServletRequest request) {
		List<TScSlStockbillentryEntity> tScSlStockbillentryList =  tScSlStockbillPage.getTScSlStockbillentryList();
		AjaxJson j = new AjaxJson();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String message = "更新成功";
		//校验单据编号唯一性
		Boolean hasBillNo = systemService.checkBillNo(TScOrderEntity.class.getAnnotation(Table.class).name(),tScSlStockbill.getBillNo(),tScSlStockbill.getId());
		if(!hasBillNo){
			j.setSuccess(false);
			j.setMsg("单据编号已存在,请确认");
			return j;
		}
		try{
			TScSlStockbillEntity oldEntity = systemService.getEntity(TScSlStockbillEntity.class, tScSlStockbill.getId());
			//客户
			if (StringUtils.isNotEmpty(tScSlStockbill.getItemid()) && !oldEntity.getItemid().equals(tScSlStockbill.getItemid())) {
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_UPDATE_TYPE);
				countEntity.setOldId(oldEntity.getItemid());
				countEntity.setNewId(tScSlStockbill.getItemid());
				boolean updateIsSuccess = organizationServiceI.updateOrganizationCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料客户引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//仓库
			if(StringUtils.isNotEmpty(tScSlStockbill.getStockid())){
				TScCountEntity countEntity = new TScCountEntity();
				if(StringUtils.isNotEmpty(oldEntity.getStockid()) &&  !oldEntity.getStockid().equals(tScSlStockbill.getStockid())) {
					countEntity.setType(Globals.COUNT_UPDATE_TYPE);
					countEntity.setOldId(oldEntity.getStockid());
					countEntity.setNewId(tScSlStockbill.getStockid());
					boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
					if (updateIsSuccess == false) {
						message = "更新基础资料仓库引用次数失败";
						j.setMsg(message);
						return j;
					}
				}else if(StringUtils.isEmpty(oldEntity.getStockid())){
					countEntity.setType(Globals.COUNT_ADD_TYPE);
					countEntity.setOldId(tScSlStockbill.getStockid());
					boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
					if (updateIsSuccess == false) {
						message = "更新基础资料仓库引用次数失败";
						j.setMsg(message);
						return j;
					}
				}
			}else if(StringUtils.isNotEmpty(oldEntity.getStockid())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_DEL_TYPE);
				countEntity.setOldId(oldEntity.getStockid());
				boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料仓库引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//经办人
			if(StringUtils.isNotEmpty(tScSlStockbill.getEmpid()) && !oldEntity.getEmpid().equals(tScSlStockbill.getEmpid())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_UPDATE_TYPE);
				countEntity.setOldId(oldEntity.getEmpid());
				countEntity.setNewId(tScSlStockbill.getEmpid());
				boolean updateIsSuccess = empServiceI.updateEmpCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料职员引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//部门
			if(StringUtils.isNotEmpty(tScSlStockbill.getDeptid()) && !oldEntity.getDeptid().equals(tScSlStockbill.getDeptid())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_UPDATE_TYPE);
				countEntity.setOldId(oldEntity.getDeptid());
				countEntity.setNewId(tScSlStockbill.getDeptid());
				boolean updateIsSuccess = departmentServiceI.updateDepartmentCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料职员引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//结算账户
			if(StringUtils.isNotEmpty(tScSlStockbill.getAccountid())){
				TScCountEntity countEntity = new TScCountEntity();
				if(StringUtils.isNotEmpty(oldEntity.getAccountid()) &&  !oldEntity.getAccountid().equals(tScSlStockbill.getAccountid())) {
					countEntity.setType(Globals.COUNT_UPDATE_TYPE);
					countEntity.setOldId(oldEntity.getAccountid());
					countEntity.setNewId(tScSlStockbill.getAccountid());
					boolean updateIsSuccess = settleacctServiceI.updateOrganizationCount(countEntity);
					if (updateIsSuccess == false) {
						message = "更新基础资料结算账户引用次数失败";
						j.setMsg(message);
						return j;
					}
				}else if(StringUtils.isEmpty(oldEntity.getAccountid())){
					countEntity.setType(Globals.COUNT_ADD_TYPE);
					countEntity.setOldId(tScSlStockbill.getAccountid());
					boolean updateIsSuccess = settleacctServiceI.updateOrganizationCount(countEntity);
					if (updateIsSuccess == false) {
						message = "更新基础资料结算账户引用次数失败";
						j.setMsg(message);
						return j;
					}
				}
			}else if(StringUtils.isNotEmpty(oldEntity.getAccountid())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_DEL_TYPE);
				countEntity.setOldId(oldEntity.getAccountid());
				boolean updateIsSuccess = settleacctServiceI.updateOrganizationCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料结算账户引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//明细数据
			for(TScSlStockbillentryEntity entry : tScSlStockbillentryList){
				TScSlStockbillentryEntity oldEntry = null;
				if(StringUtils.isNotEmpty(entry.getId())) {
					oldEntry = systemService.getEntity(TScSlStockbillentryEntity.class, entry.getId());
				}
				if(null != oldEntry) {
					//商品
					if (StringUtils.isNotEmpty(entry.getItemid()) && !oldEntry.getItemid().equals(entry.getItemid())) {
						TScCountEntity countEntity = new TScCountEntity();
						countEntity.setType(Globals.COUNT_UPDATE_TYPE);
						countEntity.setOldId(oldEntry.getItemid());
						countEntity.setNewId(entry.getItemid());
						boolean updateIsSuccess = tScIcitemServiceI.updateIcitemCount(countEntity);
						if (updateIsSuccess == false) {
							message = "更新基础资料职员引用次数失败";
							j.setMsg(message);
							return j;
						}
					}
					//仓库
					if (StringUtils.isNotEmpty(entry.getStockid())) {
						TScCountEntity countEntity = new TScCountEntity();
						if (StringUtils.isNotEmpty(oldEntry.getStockid()) && !oldEntry.getStockid().equals(entry.getStockid())) {
							countEntity.setType(Globals.COUNT_UPDATE_TYPE);
							countEntity.setOldId(oldEntry.getStockid());
							countEntity.setNewId(entry.getStockid());
							boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
							if (updateIsSuccess == false) {
								message = "更新基础资料仓库引用次数失败";
								j.setMsg(message);
								return j;
							}
						} else if (StringUtils.isEmpty(oldEntry.getStockid())) {
							countEntity.setType(Globals.COUNT_ADD_TYPE);
							countEntity.setOldId(entry.getStockid());
							boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
							if (updateIsSuccess == false) {
								message = "更新基础资料仓库引用次数失败";
								j.setMsg(message);
								return j;
							}
						}
					} else if (StringUtils.isNotEmpty(oldEntry.getStockid())) {
						TScCountEntity countEntity = new TScCountEntity();
						countEntity.setType(Globals.COUNT_DEL_TYPE);
						countEntity.setOldId(oldEntry.getStockid());
						boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
						if (updateIsSuccess == false) {
							message = "更新基础资料仓库引用次数失败";
							j.setMsg(message);
							return j;
						}
					}
				}else{
					//商品
					if (StringUtils.isNotEmpty(entry.getItemid())) {
						TScCountEntity countEntity = new TScCountEntity();
						countEntity.setType(Globals.COUNT_ADD_TYPE);
						countEntity.setOldId(entry.getItemid());
						boolean updateIsSuccess = tScIcitemServiceI.updateIcitemCount(countEntity);
						if (updateIsSuccess == false) {
							message = "更新基础资料职员引用次数失败";
							j.setMsg(message);
							return j;
						}
					}
					if(StringUtils.isNotEmpty(entry.getStockid())){
						TScCountEntity countEntity = new TScCountEntity();
						countEntity.setType(Globals.COUNT_ADD_TYPE);
						countEntity.setOldId(entry.getStockid());
						boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
						if (updateIsSuccess == false) {
							message = "更新基础资料职员引用次数失败";
							j.setMsg(message);
							return j;
						}
					}
				}
			}
			sessionFactory.getCurrentSession().evict(oldEntity);
			//如果本次付款有值，则写入执行数量
			if(null != tScSlStockbill.getCurpayamount()){
				//变更金额
				BigDecimal changeAmount = BigDecimal.valueOf(oldEntity.getCheckamount()).subtract(BigDecimal.valueOf(tScSlStockbill.getCurpayamount()));
				BigDecimal checkAmount = BigDecimal.valueOf(oldEntity.getCheckamount()).subtract(changeAmount);
				tScSlStockbill.setCheckamount(checkAmount.doubleValue());
			} else {
				tScSlStockbill.setCheckamount(0.0);
			}
			tScSlStockbillService.updateMain(tScSlStockbill, tScSlStockbillentryList);
			Set<String> mainId = new HashSet<String>();
			for(TScSlStockbillentryEntity entry : tScSlStockbillentryList){
				if(StringUtils.isNotEmpty(entry.getIdOrder())){
					mainId.add(entry.getIdOrder());
				}
			}
			if(mainId.size() > 0) {
				orderServiceI.checkAutoFlag(mainId);
			}

			String logistical = request.getParameter("json");
			if(StringUtils.isNotEmpty(logistical)){
				TScSlLogisticalEntity logisticalEntity = JSONObject.parseObject(logistical,TScSlLogisticalEntity.class);
				systemService.saveOrUpdate(logisticalEntity);
			}

			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新销售出库单失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 销售出库单新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScSlStockbillEntity tScSlStockbill, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScSlStockbill.getId())) {
			tScSlStockbill = tScSlStockbillService.getEntity(TScSlStockbillEntity.class, tScSlStockbill.getId());
			req.setAttribute("tScSlStockbillPage", tScSlStockbill);
		}else{
			String tranType = req.getParameter("tranType");
			String billNo = BillNoGenerate.getBillNo(tranType);
			req.setAttribute("billNo",billNo);
			String defaultTaxRate = "";//默认税率
			//从系统参数中获取默认税率
			//List<TSConfig> tsConfigList = this.tScSlStockbillService.findByProperty(TSConfig.class,"code","CFG_TAX_RATE");
			List<TSConfig> tsConfigList = tScSlStockbillService.findHql("from TSConfig");
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
			Date checkDate = AccountUtil.getAccountStartDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = sdf.format(checkDate);
			req.setAttribute("checkDate",dateStr);
			req.setAttribute("tranType",tranType);
			String title = "销售出库单";
			String amountStr = "应收";
			String payType = "收款";
			if("104".equals(tranType)){
				title = "销售退货单";
				amountStr = "应付";
				payType = "付款";
			}
			req.setAttribute("amountStr",amountStr);
			req.setAttribute("payType",payType);
			req.setAttribute("title",title);
			req.setAttribute("date",sdf.format(new Date()));
		}
		//是否允许负库存出库
		if(AccountUtil.isMinusInventorySI()) {
			req.setAttribute("isCheckNegative",false);
		}else{
			req.setAttribute("isCheckNegative",true);
		}
		//当前用户所在分支机构
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		req.setAttribute("sonId",depart.getId());
		req.setAttribute("sonName",depart.getDepartname());
		return new ModelAndView("com/qihang/buss/sc/sales/tScSlStockbill-add");
	}

	/**
	 * 销售出库单编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScSlStockbillEntity tScSlStockbill, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScSlStockbill.getId())) {
			tScSlStockbill = tScSlStockbillService.getEntity(TScSlStockbillEntity.class, tScSlStockbill.getId());
			req.setAttribute("tScSlStockbillPage", tScSlStockbill);
			String load = req.getParameter("load");
			req.setAttribute("load",load);
			TScDepartmentEntity dept = systemService.getEntity(TScDepartmentEntity.class,tScSlStockbill.getDeptid());
			TScStockEntity stock = systemService.getEntity(TScStockEntity.class,tScSlStockbill.getStockid());
			TScOrganizationEntity organization = systemService.getEntity(TScOrganizationEntity.class,tScSlStockbill.getItemid());
			if(StringUtils.isNotEmpty(tScSlStockbill.getAccountid())) {
				TScSettleacctEntity account = systemService.getEntity(TScSettleacctEntity.class, tScSlStockbill.getAccountid());
				if(null != account){
					tScSlStockbill.setAccountName(account.getName());
				}
			}
            TSDepart sonEntity = systemService.getEntity(TSDepart.class,tScSlStockbill.getSonid());
            if(null != sonEntity){
                tScSlStockbill.setSonName(sonEntity.getDepartname());
            }
			TSUser user = systemService.getEntity(TSUser.class,tScSlStockbill.getBillerid());
			TScEmpEntity emp = systemService.getEntity(TScEmpEntity.class,tScSlStockbill.getEmpid());
			if(null != dept) {
				tScSlStockbill.setDeptName(dept.getName());
			}
			if(null != stock) {
				tScSlStockbill.setStockName(stock.getName());
			}
			if(null != organization){
				tScSlStockbill.setItemName(organization.getName());
				if(null != organization.getIscreditmgr()){
					req.setAttribute("iscreditmgr",organization.getIscreditmgr());
					req.setAttribute("creditamount",organization.getCreditamount());
				}
				BigDecimal countAmount = BillCountUtil.getReceivableCountByCustomerId(organization.getId());
				if(!countAmount.equals(BigDecimal.ZERO)){
					BigDecimal allAmount = new BigDecimal(tScSlStockbill.getAllamount());
					req.setAttribute("countAmount",countAmount.subtract(allAmount));//编辑时，已执行金额恢复为保存前的金额
				}else{
					req.setAttribute("countAmount",countAmount);
				}
			}
			if(null != user){
				tScSlStockbill.setBillerName(user.getRealName());
			}
			if(null != emp){
				tScSlStockbill.setEmpName(emp.getName());
			}
			String tranType = tScSlStockbill.getTrantype();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			List<TSAuditRelationEntity> info = systemService.getAuditInfoList(tScSlStockbill.getId(), tranType);
			String auditor = "";
			String auditDate = "";
			for(int i=0 ; i < info.size() ; i++){
				TSAuditRelationEntity entity = info.get(i);
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
			req.setAttribute("load",load);
			//从系统参数中获取默认税率
			String defaultTaxRate = "";
			List<TSConfig> tsConfigList = tScSlStockbillService.findHql("from TSConfig");
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
			String title = "销售出库单";
			String amountStr = "应收";
			String payType = "收款";
			if("104".equals(tranType)){
				title = "销售退货单";
				amountStr = "应付";
				payType = "付款";
			}
			req.setAttribute("amountStr",amountStr);
			req.setAttribute("payType",payType);
			req.setAttribute("title",title);
			//是否允许负库存出库
			if(AccountUtil.isMinusInventorySI()) {
				req.setAttribute("isCheckNegative",false);
			}else{
				req.setAttribute("isCheckNegative",true);
			}
		}
		return new ModelAndView("com/qihang/buss/sc/sales/tScSlStockbill-update");
	}

  /**
  * 导入功能跳转
  *
  * @return
  */
  @RequestMapping(params = "upload")
  public ModelAndView upload(HttpServletRequest req) {
  req.setAttribute("controller_name","tScSlStockbillController");
  return new ModelAndView("common/upload/pub_excel_upload");
  }

  /**
  * 导出excel
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXls")
  public String exportXls(TScSlStockBillExcelEntity tScSlStockbill,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  CriteriaQuery cq = new CriteriaQuery(TScSlStockBillExcelEntity.class, dataGrid);
  com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScSlStockbill, request.getParameterMap());
	  String query_date_begin = request.getParameter("date_begin");
	  String query_date_end = request.getParameter("date_end");
	  String itemName  = request.getParameter("entryItemName");
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
  List<TScSlStockBillExcelEntity> tScSlStockbills = this.tScSlStockbillService.getListByCriteriaQuery(cq,false);
	  TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
	  TSDepart depart1 = systemService.getParentSonInfo(depart);
	  Set<String> departIds = systemService.getAllSonId(depart1.getId());
	  CriteriaQuery deptCq = new CriteriaQuery(TSDepart.class, dataGrid);
	  deptCq.in("id", departIds.toArray());
	  List<TSDepart> departList = this.tScSlStockbillService.getListByCriteriaQuery(deptCq,false);
	  List<TScSlStockBillExcelEntity> result = new ArrayList<TScSlStockBillExcelEntity>();
	  for (TScSlStockBillExcelEntity entity : tScSlStockbills) {
		  if(StringUtils.isNotEmpty(itemName)) {
			  String hql = "from TScSlStockBillentryExcelEntity where entryToMain.id = ? ";

			  hql += " and icitemEntity.name like '%" + itemName + "%' order by findex asc";

			  List<TScSlStockBillentryExcelEntity> entryList = this.tScSlStockbillService.findHql(hql, new Object[]{entity.getId()});
			  if (entryList.size() > 0) {
				  entity.setMainToEntry(entryList);
				  result.add(entity);
			  }
		  }else{
			  result.add(entity);
		  }
	  }
	  String tranType = request.getParameter("tranType");
	  if(StringUtils.isNotEmpty(tranType)) {
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  for (TScSlStockBillExcelEntity entity : result) {
			  TSAuditRelationEntity info = systemService.getAuditInfo(entity.getId(), tranType);
			  if (info != null) {
				  if (1 == info.getIsFinish()) {
					  entity.setState(Globals.AUDIT_FIN);
					  entity.setAuditorName(info.getAuditorName());
					  entity.setAuditDate(sdf.format(info.getAuditDate()));
				  } else {
					  entity.setState(Globals.AUDIT_IN);
					  entity.setAuditorName(info.getAuditorName());
					  entity.setAuditDate(sdf.format(info.getAuditDate()));
				  }
			  } else {
				  entity.setState(Globals.AUDIT_NO);
			  }
			  for(TSDepart depart2 : departList){
				  if(depart2.getId().equals(entity.getSonId())){
					  entity.setSonName(depart2.getDepartname());
					  break;
				  }
			  }
		  }
	  }
  //如需动态排除部分列不导出时启用，列名指Excel中文列名
  String[] exclusions = {"排除列名1","排除列名2"};
  String fileName = "销售出库单";
  if("104".equals(tranType)){
	  fileName = "销售退货单";
  }
  modelMap.put(NormalExcelConstants.FILE_NAME,fileName);
  modelMap.put(NormalExcelConstants.CLASS,TScSlStockBillExcelEntity.class);
  modelMap.put(NormalExcelConstants.PARAMS,new ExportParams(fileName+"列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
  public String exportXlsByT(TScSlStockbillEntity tScSlStockbill,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  modelMap.put(TemplateExcelConstants.FILE_NAME, "销售出库单");
  modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
  modelMap.put(TemplateExcelConstants.MAP_DATA,null);
  modelMap.put(TemplateExcelConstants.CLASS,TScSlStockbillEntity.class);
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
    ExcelImportResult importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(),TScSlStockbillEntity.class,params);
    List<TScSlStockbillEntity> listTScSlStockbillEntitys = importResult.getList();
    StringBuilder stringBuilder = new StringBuilder("已存在的数据:");
    boolean flag = false;
    if(!importResult.isVerfiyFail()) {
      for (TScSlStockbillEntity tScSlStockbill : listTScSlStockbillEntitys) {
      //以下检查导入数据是否重复的语句可视需求启用
        //Long count = tScSlStockbillService.getCountForJdbc("这里放检查数据是否重复的SQL语句");
        //if(count >0) {
          //flag = true;
          //stringBuilder.append(tScSlStockbill.getId()+",");
        //} else {
          tScSlStockbillService.save(tScSlStockbill);
        //}
      }
      j.setMsg("文件导入成功！");
      //j.setMsg("文件导入成功！" + (flag? stringBuilder.toString():""));
    }else {
      String excelPath = "/upload/excelUpload/TScSlStockbillEntity/"+importResult.getExcelName();
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
	 * 加载明细列表[销售出库单明细]
	 *
	 * @return
	 */
	@RequestMapping(params = "tScSlStockbillentryList")
	public ModelAndView tScSlStockbillentryList(TScSlStockbillEntity tScSlStockbill, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id0 = tScSlStockbill.getId();
		//===================================================================================
		//查询-销售出库单明细
	    String hql0 = "from TScSlStockbillentryEntity where 1 = 1 AND fID = ? order by findex";
	    try{
	    	List<TScSlStockbillentryEntity> tScSlStockbillentryEntityList = systemService.findHql(hql0,id0);
			List<TScStockEntity> stockEntityList = systemService.findHql("from TScStockEntity");
			List<TSBillInfoEntity> billInfoEntityList = systemService.findHql("from TSBillInfoEntity");
			Map<String,String> billInfo = new HashMap<String, String>();
			for(TSBillInfoEntity infoEntity : billInfoEntityList){
				billInfo.put(infoEntity.getBillId(),infoEntity.getBillName());
			}
			for(TScSlStockbillentryEntity entry : tScSlStockbillentryEntityList){
				entry.setFreegifts_(entry.getFreegifts());
				if(StringUtils.isNotEmpty(entry.getStockid())){
					for(TScStockEntity stockEntity : stockEntityList){
						if(stockEntity.getId().equals(entry.getStockid())){
							entry.setStockName(stockEntity.getName());
						}
					}
				}
				if(StringUtils.isNotEmpty(entry.getItemid())){
					TScIcitemEntity item = systemService.getEntity(TScIcitemEntity.class,entry.getItemid());
					if(null != item){
						entry.setItemName(item.getName());
						entry.setItemNo(item.getNumber());
						entry.setModel(item.getModel());
						entry.setIsKfperiod(item.getIskfPeriod());
						entry.setBatchManager(item.getBatchManager());
						TScItemPriceEntity price = systemService.getEntity(TScItemPriceEntity.class, entry.getUnitid());
						entry.setBarCode(price.getBarCode());
						if(null != price.getXsLimitPrice()) {
							entry.setXsLimitPrice(Double.parseDouble(price.getXsLimitPrice().toString()));
						}
						if(StringUtils.isNotEmpty(entry.getEntryidOrder())){
							TScOrderentryEntity orderentryEntity = systemService.getEntity(TScOrderentryEntity.class,entry.getEntryidOrder());
							if(null != orderentryEntity){
								Double billQty = Double.parseDouble(orderentryEntity.getQty().toString());
								Double stockQty = 0.0;
								if(null != orderentryEntity.getStockQty()) {
									stockQty = Double.parseDouble(orderentryEntity.getStockQty().toString());
								}
								Double qty = billQty - stockQty + entry.getQty();
								entry.setBillQty(qty);
							}else{
								entry.setBillQty(0.0);
							}
						}else{
							entry.setBillQty(0.0);
						}
					}
					if(StringUtils.isNotEmpty(entry.getBatchno())){
						List<TScIcInventoryBatchnoEntity> inventoryBatchnoEntityList = systemService.findHql("from TScIcInventoryBatchnoEntity where itemId = ? and batchNo = ? and stockId = ?",new Object[]{entry.getItemid(),entry.getBatchno(),entry.getStockid()});
						if(inventoryBatchnoEntityList.size() > 0){
							entry.setInvQty(inventoryBatchnoEntityList.get(0).getBasicQty() + entry.getQty());
						}else{
							entry.setInvQty(0.0);
						}
					}else{
						//TScIcInventoryEntity inventoryEntity = systemService.findUniqueByProperty(TScIcInventoryEntity.class,"itemId",entry.getItemid());
						List<TScIcInventoryEntity> inventoryEntityList = systemService.findHql("from TScIcInventoryEntity where itemId = ? and stockId = ?",new Object[]{entry.getItemid(),entry.getStockid()});
						if(inventoryEntityList.size() > 0){
							entry.setInvQty(inventoryEntityList.get(0).getBasicQty()+entry.getQty());
						}else{
							entry.setInvQty(0.0);
						}
					}
				}
				if(StringUtils.isNotEmpty(entry.getClassidSrc())){
					String tranTypeName = billInfo.get(entry.getClassidSrc());
					entry.setClassidName(tranTypeName);
				}
				if(null != entry.getIsFreeGift()){
					if(1 == entry.getIsFreeGift()){
						entry.setSalesName("买一赠一");
					}else if(0 == entry.getIsFreeGift()){
						entry.setSalesName("调价政策");
					}
				}
			}
			req.setAttribute("tScSlStockbillentryList", tScSlStockbillentryEntityList);
			req.setAttribute("tranType",tScSlStockbill.getTrantype());
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		String load = req.getParameter("load");
		req.setAttribute("load",load);
		return new ModelAndView("com/qihang/buss/sc/sales/tScSlStockbillentryList");
	}

	//作废功能
	@RequestMapping(params = "cancelBill")
	@ResponseBody
	public AjaxJson cancelBill(String ids){
		AjaxJson j = tScSlStockbillService.cancelBill(ids);
		if(j.isSuccess()) {
			//校验订单是否自动关闭
			String[] idList = ids.split(",");
			Set<String> mainId = new HashSet<String>();
			for (String id : idList) {
				List<TScSlStockbillentryEntity> entryList = tScSlStockbillService.findHql("from TScSlStockbillentryEntity where fid = ?", new Object[]{id});
				for (TScSlStockbillentryEntity entry : entryList) {
					if (StringUtils.isNotEmpty(entry.getIdOrder())) {
						mainId.add(entry.getIdOrder());
					}
				}
			}
			if (mainId.size() > 0) {
				orderServiceI.checkAutoFlag(mainId);
			}
		}
		return j;
	}

	//反作废
	@RequestMapping(params = "enableBill")
	@ResponseBody
	public AjaxJson enableBill(String ids){
		AjaxJson j = tScSlStockbillService.enableBill(ids);
		if(j.isSuccess()) {
			//校验订单是否自动关闭
			String[] idList = ids.split(",");
			Set<String> mainId = new HashSet<String>();
			for (String id : idList) {
				List<TScSlStockbillentryEntity> entryList = tScSlStockbillService.findHql("from TScSlStockbillentryEntity where fid = ?", new Object[]{id});
				for (TScSlStockbillentryEntity entry : entryList) {
					if (StringUtils.isNotEmpty(entry.getIdOrder())) {
						mainId.add(entry.getIdOrder());
					}
				}
			}
			if (mainId.size() > 0) {
				orderServiceI.checkAutoFlag(mainId);
			}
		}
		return j;
	}


	/**
	 * 审核（反审核）后执行方法
	 * @param id
	 * @param audit
	 * @param tranType
	 * @return
	 */
	@RequestMapping(params = "afterAudit")
	@ResponseBody
	public AjaxJson afterAudit(String id,Integer audit,String tranType){
		AjaxJson j = new AjaxJson();
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		j = tScSlStockbillService.afterAudit(id, audit, tranType,depart.getId());
		//@TODO 审核后回写订单状态
//		if(j.isSuccess()){
//			TScSlStockbillEntity stockbillEntity = systemService.getEntity(TScSlStockbillEntity.class,id);
//			//若存在源单且单据为销售出库单,且审核通过
//			if(2 == stockbillEntity.getCheckState() && StringUtil.isNotEmpty(stockbillEntity.getIdSrc()) && Globals.SC_SL_STOCKBILL_TRANTYPE.equals(Integer.parseInt(tranType))){
//				TScOrderEntity orderEntity = systemService.getEntity(TScOrderEntity.class,stockbillEntity.getIdSrc());
//				if(2 == orderEntity.getMall()){
//					String mallOrderId = orderEntity.getMallOrderID();
//					stockbillInterServiceI.synStockstate(mallOrderId.toString(),id);//返回出库状态
//				}
//			}
//		}
		return j;
	}

	@RequestMapping(params = "goSelectDialog")
	public ModelAndView goSelectDialog(TScSlStockbillEntity stockBill, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(stockBill.getItemid())) {
			req.setAttribute("itemId", stockBill.getItemid());
		}
		req.setAttribute("tranType",stockBill.getTrantype());
		String sonId = req.getParameter("sonId");
		req.setAttribute("sonId",sonId);
		return new ModelAndView("com/qihang/buss/sc/sales/tScSlStockbillListSelect");
	}

	@RequestMapping(params = "loadEntryViewList")
	@ResponseBody
	public List<TScSlStockbillViewEntity> loadEntryViewList(TScSlStockbillViewEntity tPoOrder,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid){
		CriteriaQuery cq = new CriteriaQuery(TScSlStockbillViewEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPoOrder);
		try{
			//自定义追加查询条件
			cq.eq("isstock",0);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScSlStockbillService.getDataGridReturn(cq, true);
		List<TScSlStockbillViewEntity> viewList = dataGrid.getResults();
		for(TScSlStockbillViewEntity viewEntity : viewList){
			viewEntity.setCreateDate(null);
			viewEntity.setCreateBy(null);
			viewEntity.setCreateName(null);
			viewEntity.setUpdateName(null);
			viewEntity.setUpdateDate(null);
			viewEntity.setUpdateBy(null);
		}
		return viewList;
	}

	@RequestMapping(params = "goLogisticalUpdate")
	public ModelAndView goLogisticalUpdate(TScSlLogisticalEntity entity,HttpServletRequest request){
		String load = request.getParameter("load");
		request.setAttribute("load", load);
		String tableName = request.getParameter("tableName");
		request.setAttribute("tableName", tableName);
		String logisticalValue = request.getParameter("logisticalValue");
		if(StringUtils.isEmpty(logisticalValue)) {
			if (StringUtil.isNotEmpty(entity.getFid())) {
				TScSlLogisticalEntity slLogisticalEntity = tScSlStockbillService.findUniqueByProperty(TScSlLogisticalEntity.class, "fid", entity.getFid());
				if (null != slLogisticalEntity) {
					String logisticalId = slLogisticalEntity.getLogisticalId();
					if (StringUtils.isNotEmpty(logisticalId)) {
						TScLogisticalEntity logisticalEntity = systemService.getEntity(TScLogisticalEntity.class, logisticalId);
						if (null != logisticalEntity) {
							slLogisticalEntity.setLogisticalName(logisticalEntity.getName());
						}
					}
					String accountId = slLogisticalEntity.getAccountId();
					if (StringUtils.isNotEmpty(accountId)) {
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
			TScSlLogisticalEntity logisticalEntity = JSONObject.parseObject(logisticalValue,TScSlLogisticalEntity.class);
			request.setAttribute("tScSlLogisticalPage", logisticalEntity);
		}
		return new ModelAndView("com/qihang/buss/sc/sales/tScSlLogistical-update");
	}

	/**
	 * 更新物流信息
	 *
	 * @param logistical
	 * @return
	 */
	@RequestMapping(params = "updateLogistical")
	@ResponseBody
	public AjaxJson updateLogistical(TScSlLogisticalEntity logistical, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		String tableName = request.getParameter("tableName");
		try{
			if(StringUtils.isNotEmpty(logistical.getId())) {
				TScSlLogisticalEntity oldEntity = systemService.getEntity(TScSlLogisticalEntity.class, logistical.getId());
				//物流公司
				if (StringUtils.isNotEmpty(logistical.getLogisticalId()) && !oldEntity.getLogisticalId().equals(logistical.getLogisticalId())) {
					TScCountEntity countEntity = new TScCountEntity();
					countEntity.setType(Globals.COUNT_UPDATE_TYPE);
					countEntity.setOldId(oldEntity.getLogisticalId());
					countEntity.setNewId(logistical.getLogisticalId());
					boolean updateIsSuccess = logisticalServiceI.updateLogisticalCount(countEntity);
					if (updateIsSuccess == false) {
						message = "更新基础资料物流公司引用次数失败";
						j.setMsg(message);
						return j;
					}
				}
				//结算账户
				if(StringUtils.isNotEmpty(logistical.getAccountId())){
					TScCountEntity countEntity = new TScCountEntity();
					if(StringUtils.isNotEmpty(oldEntity.getAccountId()) &&  !oldEntity.getAccountId().equals(logistical.getAccountId())) {
						countEntity.setType(Globals.COUNT_UPDATE_TYPE);
						countEntity.setOldId(oldEntity.getAccountId());
						countEntity.setNewId(logistical.getAccountId());
						boolean updateIsSuccess = settleacctServiceI.updateOrganizationCount(countEntity);
						if (updateIsSuccess == false) {
							message = "更新基础资料结算账户引用次数失败";
							j.setMsg(message);
							return j;
						}
					}else if(StringUtils.isEmpty(oldEntity.getAccountId())){
						countEntity.setType(Globals.COUNT_ADD_TYPE);
						countEntity.setOldId(logistical.getAccountId());
						boolean updateIsSuccess = settleacctServiceI.updateOrganizationCount(countEntity);
						if (updateIsSuccess == false) {
							message = "更新基础资料结算账户引用次数失败";
							j.setMsg(message);
							return j;
						}
					}
				}else if(StringUtils.isNotEmpty(oldEntity.getAccountId())){
					TScCountEntity countEntity = new TScCountEntity();
					countEntity.setType(Globals.COUNT_DEL_TYPE);
					countEntity.setOldId(oldEntity.getAccountId());
					boolean updateIsSuccess = settleacctServiceI.updateOrganizationCount(countEntity);
					if (updateIsSuccess == false) {
						message = "更新基础资料结算账户引用次数失败";
						j.setMsg(message);
						return j;
					}
				}
				sessionFactory.getCurrentSession().evict(oldEntity);
				Double checkAmount = logistical.getCheckAmount();
				if(null != checkAmount && checkAmount > 0){
					if(null != logistical.getCurPayAmount() && logistical.getCurPayAmount() > 0) {
						Double curPayAmount = logistical.getCurPayAmount();
						Double changeAmount = BigDecimal.valueOf(checkAmount).subtract(BigDecimal.valueOf(curPayAmount)).doubleValue();
						checkAmount = BigDecimal.valueOf(checkAmount).subtract(BigDecimal.valueOf(changeAmount)).doubleValue();
						logistical.setCheckAmount(checkAmount);
					}
				}else{
					if(null != logistical.getCurPayAmount() && logistical.getCurPayAmount() > 0) {
						logistical.setCheckAmount(logistical.getCurPayAmount());
					}else{
						logistical.setCheckAmount(0.0);
					}
				}
				tScSlStockbillService.saveOrUpdate(logistical);
			}else{
				//物流公司
				if (StringUtils.isNotEmpty(logistical.getLogisticalId())) {
					TScCountEntity countEntity = new TScCountEntity();
					countEntity.setType(Globals.COUNT_ADD_TYPE);
					countEntity.setOldId(logistical.getLogisticalId());
					boolean updateIsSuccess = logisticalServiceI.updateLogisticalCount(countEntity);
					if (updateIsSuccess == false) {
						message = "更新基础资料物流公司引用次数失败";
						j.setMsg(message);
						return j;
					}
				}
				//结算账户
				if(StringUtils.isNotEmpty(logistical.getAccountId())){
					TScCountEntity countEntity = new TScCountEntity();
					countEntity.setType(Globals.COUNT_ADD_TYPE);
					countEntity.setOldId(logistical.getAccountId());
					boolean updateIsSuccess = settleacctServiceI.updateOrganizationCount(countEntity);
					if (updateIsSuccess == false) {
						message = "更新基础资料结算账户引用次数失败";
						j.setMsg(message);
						return j;
					}
				}
				if(null != logistical.getCurPayAmount()){
					logistical.setCheckAmount(logistical.getCurPayAmount());
				}else{
					logistical.setCheckAmount(0.0);
				}
				tScSlStockbillService.save(logistical);
			}
			StringBuffer updateSql = new StringBuffer();
			updateSql.append("update "+tableName+" set freight="+logistical.getFreight()+" where id = '").append(logistical.getFid()).append("'");
			systemService.updateBySqlString(updateSql.toString());
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新销售出库单失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "goSelectMainDialog")
	public ModelAndView goSelectMainDialog(TScSlStockbillEntity stockBill, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(stockBill.getItemid())) {
			req.setAttribute("itemId", stockBill.getItemid());
		}
		String ids = req.getParameter("ids");
		req.setAttribute("ids",ids);
		String ids2 = req.getParameter("ids2");
		req.setAttribute("ids2",ids2);
		String sonId = req.getParameter("sonId");
		req.setAttribute("sonId",sonId);
		return new ModelAndView("com/qihang/buss/sc/sales/tScSlStockBillInfo");
	}

	@RequestMapping(params = "datagridMain")
	public void datagridMain(TScSlStockBillInfoView tScSlStockbill,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScSlStockBillInfoView.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScSlStockbill);
		try{
			//自定义追加查询条件
			String query_date_begin = request.getParameter("date_begin");
			String query_date_end = request.getParameter("date_end");
			String ids = request.getParameter("ids");//订单id；
			String ids2 = request.getParameter("ids2");//源单id；
			//过滤订单数据
			if(StringUtils.isNotEmpty(ids)) {
				List<String> idList = new ArrayList<String>();
				String[] idArr = ids.split(",");
				String queryIdStr = "";
				for (String id : idArr) {
					idList.add(id);
					queryIdStr += "'" + id + "',";
				}
				queryIdStr = queryIdStr.substring(0,queryIdStr.length()-1);
				List<TScSlStockbillEntity> stockbillEntityList = systemService.findHql("from TScSlStockbillEntity where idSrc in ("+queryIdStr+")");
				if(stockbillEntityList.size() > 0){
					for(TScSlStockbillEntity entity : stockbillEntityList){
						idList.add(entity.getId());
					}
				}
				if(idList.size() > 0) {
					cq.add(Restrictions.not(Restrictions.in("idSrc", idList.toArray())));
				}
			}
			//过滤已选源单数据
			if(StringUtils.isNotEmpty(ids2)){
				List<String> idList = new ArrayList<String>();
				String[] idArr = ids2.split(",");
				for(String id : idArr){
					idList.add(id);
				}
				if(idList.size() > 0) {
					cq.add(Restrictions.not(Restrictions.in("id", idList.toArray())));
				}
			}
			if(StringUtil.isNotEmpty(query_date_begin)){
				cq.ge("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_begin));
			}
			if(StringUtil.isNotEmpty(query_date_end)){
				cq.le("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_end));
			}
			String sonId = ResourceUtil.getSessionUserName().getCurrentDepart().getId();
			cq.eq("sonId",sonId);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScSlStockbillService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
}
