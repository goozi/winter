
package com.qihang.buss.sc.sales.controller;
import com.qihang.buss.sc.baseinfo.entity.*;
import com.qihang.buss.sc.baseinfo.service.*;
import com.qihang.buss.sc.sales.entity.*;
import com.qihang.buss.sc.sales.service.TScPoOrderServiceI;
import com.qihang.buss.sc.sales.service.TScPoStockbillServiceI;
import com.qihang.buss.sc.sales.page.TScPoStockbillPage;
import com.qihang.buss.sc.sysaudit.entity.TSAuditEntity;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TSBillInfoEntity;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
import com.qihang.buss.sc.util.AccountUtil;
import com.qihang.buss.sc.util.BillNoGenerate;
import com.qihang.buss.sc.util.CpeUtil;
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
import javassist.bytecode.stackmap.TypeData;
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
 * @Description: 采购出入库单
 * @author onlineGenerator
 * @date 2016-07-12 10:47:57
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScPoStockbillController")
public class TScPoStockbillController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScPoStockbillController.class);

	@Autowired
	private TScPoStockbillServiceI tScPoStockbillService;
	@Autowired
	private SystemService systemService;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private TScIcitemServiceI tScIcitemServiceI;//商品

	@Autowired
	private TScSupplierServiceI supplierServiceI;//供应商

	@Autowired
	private TScStockServiceI stockServiceI;

	@Autowired
	private TScEmpServiceI empServiceI;

	@Autowired
	private TScDepartmentServiceI departmentServiceI;

	@Autowired
	private TScPoOrderServiceI scPoOrderService;

	@Autowired
	private UserService userService;


	/**
	 * 采购入库单列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tScPoStockbill")
	public ModelAndView tScPoStockbill(HttpServletRequest request) {
		//TODO 设置账套期间日期
		Date checkDate = AccountUtil.getAccountStartDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		request.setAttribute("checkDate",sdf.format(checkDate));

		String defaultPageNum = "";//默认分页数
		//从系统参数中获取默认分页数
		List<TSConfig> tsConfigList = this.tScPoStockbillService.findByProperty(TSConfig.class,"code","CFG_PAGE");
		if(tsConfigList.size()>0){
			defaultPageNum = tsConfigList.get(0).getContents();
		}
		if(StringUtils.isNotEmpty(defaultPageNum)) {
			request.setAttribute("pageNum",defaultPageNum);
		}else{
			request.setAttribute("pageNum",10);
		}
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		List<TSAuditEntity> tsAuditEntityList = systemService.findHql("from TSAuditEntity where billId=? and sonId = ?",new Object[]{"52",depart.getId()});
		if(tsAuditEntityList.size() > 0) {
			TSAuditEntity auditEntity = tsAuditEntityList.get(0);
			if (null != auditEntity) {
				Integer isEdit = auditEntity.getIsEdit();
				request.setAttribute("isEdit", isEdit);
			}
		}
		//是否允许负库存出库
		if(AccountUtil.isMinusInventorySI()) {
			request.setAttribute("isCheckNegative",false);
		}else{
			request.setAttribute("isCheckNegative",true);
		}
		return new ModelAndView("com/qihang/buss/sc/sales/tScPoStockbillList");
	}

	/**
	 * 采购退货单列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tScPoStockbillReturn")
	public ModelAndView tScPoStockbillReturn(HttpServletRequest request) {
		//TODO 设置账套期间日期
		Date checkDate = AccountUtil.getAccountStartDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(checkDate);
		request.setAttribute("checkDate",dateStr);

		String defaultPageNum = "";//默认分页数
		//从系统参数中获取默认分页数
		List<TSConfig> tsConfigList = this.tScPoStockbillService.findByProperty(TSConfig.class, "code", "CFG_PAGE");
		if(tsConfigList.size()>0){
			defaultPageNum = tsConfigList.get(0).getContents();
		}
		if(StringUtils.isNotEmpty(defaultPageNum)) {
			request.setAttribute("pageNum",defaultPageNum);
		}else{
			request.setAttribute("pageNum",10);
		}
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		List<TSAuditEntity> tsAuditEntityList = systemService.findHql("from TSAuditEntity where billId=? and sonId = ?", new Object[]{"53", depart.getId()});
		if(tsAuditEntityList.size() > 0) {
			TSAuditEntity auditEntity = tsAuditEntityList.get(0);
			if (null != auditEntity) {
				Integer isEdit = auditEntity.getIsEdit();
				request.setAttribute("isEdit", isEdit);
			}
		}
		//是否允许负库存出库
		if(AccountUtil.isMinusInventorySI()) {
			request.setAttribute("isCheckNegative",false);
		}else{
			request.setAttribute("isCheckNegative",true);
		}
		return new ModelAndView("com/qihang/buss/sc/sales/tScPoStockbillReturnList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScPoStockBillViewEntity tScPoStockbill,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//汇总应付金额，已付金额，数量，金额，折后金额
		dataGrid.setFooter("allAmount,checkAmount,qty,taxAmountEx,inTaxAmount");
		CriteriaQuery cq = new CriteriaQuery(TScPoStockBillViewEntity.class, dataGrid);
		//cq.addOrder("date",SortDirection.desc);
		//cq.addOrder("billNo",SortDirection.desc);
		//cq.addOrder("id",SortDirection.desc);
		//cq.addOrder("entryId",SortDirection.asc);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScPoStockbill);
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
			if(StringUtils.isNotEmpty(isWarm)){
				String userId = ResourceUtil.getSessionUserName().getId();
				TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
				TSDepart depart = systemService.getParentSonInfo(sonInfo);
				Boolean isAudit = userService.isAllowAudit(tScPoStockbill.getTranType().toString(),userId,depart.getId());
				cq.eq("cancellation",0);
				//判断当前用户是否在多级审核队列中
				if(isAudit){
					Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId,depart.getId(),tScPoStockbill.getTranType().toString());
					if(userAuditLeave.size() > 0){
						String leaves = "";
						for(Integer leave : userAuditLeave){
							leaves += leave+",";
						}
						leaves = leaves.substring(0,leaves.length()-1);
						List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in ("+leaves+")",new Object[]{depart.getId(),tScPoStockbill.getTranType().toString()});
						if(billInfo.size() > 0){
							List<String> idArr = new ArrayList<String>();
							for(TScBillAuditStatusEntity entity : billInfo){
								idArr.add(entity.getBillId());
							}
							cq.in("id",idArr.toArray());
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
		this.tScPoStockbillService.getDataGridReturn(cq, true);
		List<TScPoStockBillViewEntity> tScPoStockBillViewEntityList = dataGrid.getResults();
		List<TSBillInfoEntity> billInfoEntityList = systemService.getList(TSBillInfoEntity.class);
		for(TScPoStockBillViewEntity entity : tScPoStockBillViewEntityList){
			if(null != entity.getClassIdSrc() && entity.getClassIdSrc() > 0) {
				for (TSBillInfoEntity billInfoEntity : billInfoEntityList) {
					if(StringUtil.isNotEmpty(billInfoEntity.getBillId())) {
						if (billInfoEntity.getBillId().equals(entity.getClassIdSrc().toString())) {
							entity.setClassIdName(billInfoEntity.getBillName());
						}
					}
				}
			}
		}
//		String tranType = request.getParameter("tranType");
//		if(StringUtils.isNotEmpty(tranType)) {
//			List<TScPoStockBillViewEntity> result = dataGrid.getResults();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			for (TScPoStockBillViewEntity entity : result) {
//				TSAuditRelationEntity info = systemService.getAuditInfo(entity.getId(), tranType);
//				if (info != null) {
//					if (1 == info.getIsFinish()) {
//						entity.setState(Globals.AUDIT_FIN);
//						entity.setAuditorName(info.getAuditorName());
//						entity.setAuditDate(sdf.format(info.getAuditDate()));
//					} else {
//						entity.setState(Globals.AUDIT_IN);
//						entity.setAuditorName(info.getAuditorName());
//						entity.setAuditDate(sdf.format(info.getAuditDate()));
//					}
//				} else {
//					entity.setState(Globals.AUDIT_NO);
//				}
//			}
//			dataGrid.setResults(result);
//		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除采购出入库单
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScPoStockbillEntity tScPoStockbill, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScPoStockbill = systemService.getEntity(TScPoStockbillEntity.class, tScPoStockbill.getId());
		String message = "采购出入库单删除成功";
		try{
			//供应商
			if(StringUtils.isNotEmpty(tScPoStockbill.getItemId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_DEL_TYPE);
				countEntity.setOldId(tScPoStockbill.getItemId());
				boolean updateIsSuccess = supplierServiceI.updateSupplierCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料供应商引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//经办人
			if(StringUtils.isNotEmpty(tScPoStockbill.getEmpId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_DEL_TYPE);
				countEntity.setOldId(tScPoStockbill.getEmpId());
				boolean updateIsSuccess = empServiceI.updateEmpCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料职员引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//部门
			if(StringUtils.isNotEmpty(tScPoStockbill.getDeptId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_DEL_TYPE);
				countEntity.setOldId(tScPoStockbill.getDeptId());
				boolean updateIsSuccess = departmentServiceI.updateDepartmentCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料部门引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//仓库
			if(StringUtils.isNotEmpty(tScPoStockbill.getStockId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_DEL_TYPE);
				countEntity.setOldId(tScPoStockbill.getStockId());
				boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料部门引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			List<TScPoStockbillentryEntity> entryList = tScPoStockbillService.findHql("from TScPoStockbillentryEntity where fid = ?",new Object[]{tScPoStockbill.getId()});
			for(TScPoStockbillentryEntity entry : entryList){
				//商品
				if(StringUtils.isNotEmpty(entry.getItemId())){
					TScCountEntity countEntity = new TScCountEntity();
					countEntity.setType(Globals.COUNT_DEL_TYPE);
					countEntity.setOldId(entry.getItemId());
					boolean updateIsSuccess = tScIcitemServiceI.updateIcitemCount(countEntity);
					if (updateIsSuccess == false) {
						message = "更新基础资料商品引用次数失败";
						j.setMsg(message);
						return j;
					}
				}
				//仓库
				if(StringUtils.isNotEmpty(entry.getStockId())){
					TScCountEntity countEntity = new TScCountEntity();
					countEntity.setType(Globals.COUNT_DEL_TYPE);
					countEntity.setOldId(entry.getStockId());
					boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
					if (updateIsSuccess == false) {
						message = "更新基础资料仓库引用次数失败";
						j.setMsg(message);
						return j;
					}
				}
			}
			tScPoStockbillService.delMain(tScPoStockbill);
			//校验订单是否自动关闭
			Set<String> mainId = new HashSet<String>();
			for(TScPoStockbillentryEntity entry : entryList){
				if(StringUtils.isNotEmpty(entry.getIdOrder())){
					mainId.add(entry.getIdOrder());
				}
			}
			if(mainId.size() > 0) {
				scPoOrderService.checkAutoFlag(mainId);
			}
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			//删除待审核预警数据
			systemService.delBillAuditStatus(tScPoStockbill.getTranType().toString(), tScPoStockbill.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "采购出入库单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除采购出入库单
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "采购出入库单删除成功";
		try{
			for(String id:ids.split(",")){
				TScPoStockbillEntity tScPoStockbill = systemService.getEntity(TScPoStockbillEntity.class,
				id
				);
				tScPoStockbillService.delMain(tScPoStockbill);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "采购出入库单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加采购出入库单
	 *
	 * @param tScPoStockbill
	 * @param tScPoStockbillPage
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScPoStockbillEntity tScPoStockbill,TScPoStockbillPage tScPoStockbillPage, HttpServletRequest request) {
		List<TScPoStockbillentryEntity> tScPoStockbillentryList =  tScPoStockbillPage.getTScPoStockbillentryList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		//校验单据编号唯一性
		Boolean hasBillNo = systemService.checkBillNo(TScOrderEntity.class.getAnnotation(Table.class).name(),tScPoStockbill.getBillNo(),tScPoStockbill.getId());
		if(!hasBillNo){
			j.setSuccess(false);
			j.setMsg("单据编号已存在,请确认");
			return j;
		}
		try{
			//如果本次付款有值，则写入执行数量
			if(null != tScPoStockbill.getCurPayAmount()){
				tScPoStockbill.setCheckAmount(tScPoStockbill.getCurPayAmount().toString());
			}
			tScPoStockbillService.addMain(tScPoStockbill, tScPoStockbillentryList);
			//供应商
			if (org.apache.commons.lang.StringUtils.isNotEmpty(tScPoStockbill.getItemId())) {
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_ADD_TYPE);
				countEntity.setOldId(tScPoStockbill.getItemId());
				boolean updateIsSuccess = supplierServiceI.updateSupplierCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料供应商引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//仓库
			if(StringUtils.isNotEmpty(tScPoStockbill.getStockId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_ADD_TYPE);
				countEntity.setOldId(tScPoStockbill.getStockId());
				boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料仓库引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//经办人
			if(StringUtils.isNotEmpty(tScPoStockbill.getEmpId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_ADD_TYPE);
				countEntity.setOldId(tScPoStockbill.getEmpId());
				boolean updateIsSuccess = empServiceI.updateEmpCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料职员引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//部门
			if(StringUtils.isNotEmpty(tScPoStockbill.getDeptId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_ADD_TYPE);
				countEntity.setOldId(tScPoStockbill.getDeptId());
				boolean updateIsSuccess = departmentServiceI.updateDepartmentCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料职员引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			for(TScPoStockbillentryEntity entry : tScPoStockbillentryList){
				//商品
				if(StringUtils.isNotEmpty(entry.getItemId())){
					TScCountEntity countEntity = new TScCountEntity();
					countEntity.setType(Globals.COUNT_ADD_TYPE);
					countEntity.setOldId(entry.getItemId());
					boolean updateIsSuccess = tScIcitemServiceI.updateIcitemCount(countEntity);
					if (updateIsSuccess == false) {
						message = "更新基础资料商品引用次数失败";
						j.setMsg(message);
						return j;
					}
				}
				//仓库
				if(StringUtils.isNotEmpty(entry.getStockId())){
					TScCountEntity countEntity = new TScCountEntity();
					countEntity.setType(Globals.COUNT_ADD_TYPE);
					countEntity.setOldId(entry.getStockId());
					boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
					if (updateIsSuccess == false) {
						message = "更新基础资料仓库引用次数失败";
						j.setMsg(message);
						return j;
					}
				}
			}
			Set<String> mainId = new HashSet<String>();
			for(TScPoStockbillentryEntity entry : tScPoStockbillentryList){
				if(StringUtils.isNotEmpty(entry.getIdOrder())){
					mainId.add(entry.getIdOrder());
				}
			}
			if(mainId.size() > 0) {
				scPoOrderService.checkAutoFlag(mainId);
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			//待审核数据提醒操作
			systemService.saveBillAuditStatus(tScPoStockbill.getTranType().toString(), tScPoStockbill.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "采购出入库单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新采购出入库单
	 *
	 * @param tScPoStockbill
	 * @param tScPoStockbillPage
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScPoStockbillEntity tScPoStockbill,TScPoStockbillPage tScPoStockbillPage, HttpServletRequest request) {
		List<TScPoStockbillentryEntity> tScPoStockbillentryList =  tScPoStockbillPage.getTScPoStockbillentryList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		//校验单据编号唯一性
		Boolean hasBillNo = systemService.checkBillNo(TScOrderEntity.class.getAnnotation(Table.class).name(),tScPoStockbill.getBillNo(),tScPoStockbill.getId());
		if(!hasBillNo){
			j.setSuccess(false);
			j.setMsg("单据编号已存在,请确认");
			return j;
		}
		try{
			TScPoStockbillEntity oldEntity = systemService.getEntity(TScPoStockbillEntity.class, tScPoStockbill.getId());
			//供应商
			if (StringUtils.isNotEmpty(tScPoStockbill.getItemId()) && !oldEntity.getItemId().equals(tScPoStockbill.getItemId())) {
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_UPDATE_TYPE);
				countEntity.setOldId(oldEntity.getItemId());
				countEntity.setNewId(tScPoStockbill.getItemId());
				boolean updateIsSuccess = supplierServiceI.updateSupplierCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料供应商引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//仓库
			if(StringUtils.isNotEmpty(tScPoStockbill.getStockId())){
				TScCountEntity countEntity = new TScCountEntity();
				if(StringUtils.isNotEmpty(oldEntity.getStockId()) &&  !oldEntity.getStockId().equals(tScPoStockbill.getStockId())) {
					countEntity.setType(Globals.COUNT_UPDATE_TYPE);
					countEntity.setOldId(oldEntity.getStockId());
					countEntity.setNewId(tScPoStockbill.getStockId());
					boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
					if (updateIsSuccess == false) {
						message = "更新基础资料仓库引用次数失败";
						j.setMsg(message);
						return j;
					}
				}else if(StringUtils.isEmpty(oldEntity.getStockId())){
					countEntity.setType(Globals.COUNT_ADD_TYPE);
					countEntity.setOldId(tScPoStockbill.getStockId());
					boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
					if (updateIsSuccess == false) {
						message = "更新基础资料仓库引用次数失败";
						j.setMsg(message);
						return j;
					}
				}
			}else if(StringUtils.isNotEmpty(oldEntity.getStockId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_DEL_TYPE);
				countEntity.setOldId(oldEntity.getStockId());
				boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料仓库引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//经办人
			if(StringUtils.isNotEmpty(tScPoStockbill.getEmpId()) && !oldEntity.getEmpId().equals(tScPoStockbill.getEmpId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_UPDATE_TYPE);
				countEntity.setOldId(oldEntity.getEmpId());
				countEntity.setNewId(tScPoStockbill.getEmpId());
				boolean updateIsSuccess = empServiceI.updateEmpCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料职员引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//部门
			if(StringUtils.isNotEmpty(tScPoStockbill.getDeptId()) && !oldEntity.getDeptId().equals(tScPoStockbill.getDeptId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_UPDATE_TYPE);
				countEntity.setOldId(oldEntity.getDeptId());
				countEntity.setNewId(tScPoStockbill.getDeptId());
				boolean updateIsSuccess = departmentServiceI.updateDepartmentCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料职员引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//明细数据
			for(TScPoStockbillentryEntity entry : tScPoStockbillentryList){
				TScPoStockbillentryEntity oldEntry = null;
				if(StringUtils.isNotEmpty(entry.getId())) {
					 oldEntry = tScPoStockbillService.getEntity(TScPoStockbillentryEntity.class, entry.getId());
				}
				if(null != oldEntry) {
					//商品
					if (StringUtils.isNotEmpty(entry.getItemId()) && !oldEntry.getItemId().equals(entry.getItemId())) {
						TScCountEntity countEntity = new TScCountEntity();
						countEntity.setType(Globals.COUNT_UPDATE_TYPE);
						countEntity.setOldId(oldEntry.getItemId());
						countEntity.setNewId(entry.getItemId());
						boolean updateIsSuccess = tScIcitemServiceI.updateIcitemCount(countEntity);
						if (updateIsSuccess == false) {
							message = "更新基础资料职员引用次数失败";
							j.setMsg(message);
							return j;
						}
					}
					//仓库
					if (StringUtils.isNotEmpty(entry.getStockId())) {
						TScCountEntity countEntity = new TScCountEntity();
						if (StringUtils.isNotEmpty(oldEntry.getStockId()) && !oldEntry.getStockId().equals(entry.getStockId())) {
							countEntity.setType(Globals.COUNT_UPDATE_TYPE);
							countEntity.setOldId(oldEntry.getStockId());
							countEntity.setNewId(entry.getStockId());
							boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
							if (updateIsSuccess == false) {
								message = "更新基础资料仓库引用次数失败";
								j.setMsg(message);
								return j;
							}
						} else if (StringUtils.isEmpty(oldEntry.getStockId())) {
							countEntity.setType(Globals.COUNT_ADD_TYPE);
							countEntity.setOldId(entry.getStockId());
							boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
							if (updateIsSuccess == false) {
								message = "更新基础资料仓库引用次数失败";
								j.setMsg(message);
								return j;
							}
						}
					} else if (StringUtils.isNotEmpty(oldEntry.getStockId())) {
						TScCountEntity countEntity = new TScCountEntity();
						countEntity.setType(Globals.COUNT_DEL_TYPE);
						countEntity.setOldId(oldEntry.getStockId());
						boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
						if (updateIsSuccess == false) {
							message = "更新基础资料仓库引用次数失败";
							j.setMsg(message);
							return j;
						}
					}
				}else{
					//商品
					if (StringUtils.isNotEmpty(entry.getItemId())) {
						TScCountEntity countEntity = new TScCountEntity();
						countEntity.setType(Globals.COUNT_ADD_TYPE);
						countEntity.setOldId(entry.getItemId());
						boolean updateIsSuccess = tScIcitemServiceI.updateIcitemCount(countEntity);
						if (updateIsSuccess == false) {
							message = "更新基础资料职员引用次数失败";
							j.setMsg(message);
							return j;
						}
					}
					if(StringUtils.isNotEmpty(entry.getStockId())){
						TScCountEntity countEntity = new TScCountEntity();
						countEntity.setType(Globals.COUNT_ADD_TYPE);
						countEntity.setOldId(entry.getStockId());
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
			if(null != tScPoStockbill.getCurPayAmount()){
				//变更金额
				String cheAmount = oldEntity.getCheckAmount();
				if(StringUtil.isEmpty(cheAmount)){
					cheAmount = "0";
				}
				BigDecimal changeAmount = BigDecimal.valueOf(Double.parseDouble(cheAmount)).subtract(BigDecimal.valueOf(tScPoStockbill.getCurPayAmount()));
				BigDecimal checkAmount = BigDecimal.valueOf(Double.parseDouble(cheAmount)).subtract(changeAmount);
				tScPoStockbill.setCheckAmount(checkAmount.toString());
			}else{
				tScPoStockbill.setCheckAmount("0");
			}
			tScPoStockbillService.updateMain(tScPoStockbill, tScPoStockbillentryList);
			Set<String> mainId = new HashSet<String>();
			for(TScPoStockbillentryEntity entry : tScPoStockbillentryList){
				if(StringUtils.isNotEmpty(entry.getIdOrder())){
					mainId.add(entry.getIdOrder());
				}
			}
			if(mainId.size() > 0) {
				scPoOrderService.checkAutoFlag(mainId);
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新采购出入库单失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 采购出入库单新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScPoStockbillEntity tScPoStockbill, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScPoStockbill.getId())) {
			tScPoStockbill = tScPoStockbillService.getEntity(TScPoStockbillEntity.class, tScPoStockbill.getId());
			req.setAttribute("tScPoStockbillPage", tScPoStockbill);
		}else{
			String billNo = BillNoGenerate.getBillNo(tScPoStockbill.getTranType());
			req.setAttribute("billNo",billNo);
			String defaultTaxRate = "";//默认税率
			//从系统参数中获取默认税率
			List<TSConfig> tsConfigList = this.tScPoStockbillService.findByProperty(TSConfig.class,"code","CFG_TAX_RATE");
			if(tsConfigList.size()>0){
				defaultTaxRate = tsConfigList.get(0).getContents();
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
		}
		req.setAttribute("tranType", tScPoStockbill.getTranType());
		String title = "采购入库单";
		if("53".equals(tScPoStockbill.getTranType())){
			title = "采购退货单";
		}
		req.setAttribute("title",title);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		req.setAttribute("date", sdf.format(new Date()));
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
		return new ModelAndView("com/qihang/buss/sc/sales/tScPoStockbill-add");
	}

	/**
	 * 采购出入库单编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScPoStockbillEntity tScPoStockbill, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScPoStockbill.getId())) {
			String load = req.getParameter("load");
			tScPoStockbill = tScPoStockbillService.getEntity(TScPoStockbillEntity.class, tScPoStockbill.getId());
			req.setAttribute("tScPoStockbillPage", tScPoStockbill);
			TScDepartmentEntity dept = systemService.getEntity(TScDepartmentEntity.class,tScPoStockbill.getDeptId());
			TScStockEntity stock = systemService.getEntity(TScStockEntity.class,tScPoStockbill.getStockId());
			TScSupplierEntity supplier = systemService.getEntity(TScSupplierEntity.class,tScPoStockbill.getItemId());
			if(StringUtils.isNotEmpty(tScPoStockbill.getAccountId())) {
				TScSettleacctEntity account = systemService.getEntity(TScSettleacctEntity.class, tScPoStockbill.getAccountId());
				if(null != account){
					tScPoStockbill.setAccountName(account.getName());
				}
			}
			TSUser user = systemService.getEntity(TSUser.class,tScPoStockbill.getBillerID());
			TScEmpEntity emp = systemService.getEntity(TScEmpEntity.class,tScPoStockbill.getEmpId());
			TSDepart sonEntity = systemService.getEntity(TSDepart.class,tScPoStockbill.getSonId());
			if(null != sonEntity){
				tScPoStockbill.setSonName(sonEntity.getDepartname());
			}
			if(null != dept) {
				tScPoStockbill.setDeptName(dept.getName());
			}
			if(null != stock) {
				tScPoStockbill.setStockName(stock.getName());
			}
			if(null != supplier){
				tScPoStockbill.setItemName(supplier.getName());
			}
			if(null != user){
				tScPoStockbill.setBillerName(user.getRealName());
			}
			if(null != emp){
				tScPoStockbill.setEmpName(emp.getName());
			}
			//tScPoStockbill.setCheckState(Globals.AUDIT_NO);
			String tranType = tScPoStockbill.getTranType();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			List<TSAuditRelationEntity> info = systemService.getAuditInfoList(tScPoStockbill.getId(), tranType);
			String auditor = "";
			String auditDate = "";
			for(int i=0 ; i < info.size() ; i++){
				TSAuditRelationEntity entity = info.get(i);
//				if(1 == entity.getIsFinish()){
//					tScPoStockbill.setCheckState(Globals.AUDIT_FIN);
//				}
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
			List<TSConfig> tsConfigList = this.tScPoStockbillService.findByProperty(TSConfig.class,"code","CFG_TAX_RATE");
			if(tsConfigList.size()>0){
				defaultTaxRate = tsConfigList.get(0).getContents();
			}
			if(StringUtils.isNotEmpty(defaultTaxRate)) {
				req.setAttribute("taxRate", defaultTaxRate);
			}else{
				req.setAttribute("taxRate",0);
			}
			Date checkDate = AccountUtil.getAccountStartDate();
			String dateStr = sdf.format(checkDate);
			req.setAttribute("checkDate",dateStr);
			String title = "采购入库单";
			if("53".equals(tScPoStockbill.getTranType())){
				title = "采购退货单";
			}
			req.setAttribute("title",title);
			//是否允许负库存出库
			if(AccountUtil.isMinusInventorySI()) {
				req.setAttribute("isCheckNegative",false);
			}else{
				req.setAttribute("isCheckNegative",true);
			}
		}
		return new ModelAndView("com/qihang/buss/sc/sales/tScPoStockbill-update");
	}

  /**
  * 导入功能跳转
  *
  * @return
  */
  @RequestMapping(params = "upload")
  public ModelAndView upload(HttpServletRequest req) {
  req.setAttribute("controller_name","tScPoStockbillController");
  return new ModelAndView("common/upload/pub_excel_upload");
  }

  /**
  * 导出excel
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXls")
  public String exportXls(TScPoStockBillExcelEntity tScPoStockbill,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  CriteriaQuery cq = new CriteriaQuery(TScPoStockBillExcelEntity.class, dataGrid);
  com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScPoStockbill, request.getParameterMap());
	  String query_date_begin = request.getParameter("date_begin");
	  String query_date_end = request.getParameter("date_end");
	  String itemName  = request.getParameter("entryItemName");
	  String tranType = request.getParameter("tranType");
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
  List<TScPoStockBillExcelEntity> tScPoStockbills = this.tScPoStockbillService.getListByCriteriaQuery(cq,false);
	  List<TScPoStockBillExcelEntity> result = new ArrayList<TScPoStockBillExcelEntity>();
	  TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
	  TSDepart depart1 = systemService.getParentSonInfo(depart);
	  Set<String> departIds = systemService.getAllSonId(depart1.getId());
	  CriteriaQuery deptCq = new CriteriaQuery(TSDepart.class, dataGrid);
	  deptCq.in("id",departIds.toArray());
	  List<TSDepart> departList = this.tScPoStockbillService.getListByCriteriaQuery(deptCq,false);
	  for (TScPoStockBillExcelEntity entity : tScPoStockbills) {
		  if(StringUtils.isNotEmpty(itemName)){
			  String hql = "from TScPoStockBillentryExcelEntity where entryToMain.id = ? ";

				  hql+=" and icitemEntity.name like '%"+itemName+"%' order by findex asc";
			  List<TScPoStockBillentryExcelEntity> entryList = this.tScPoStockbillService.findHql(hql,new Object[]{entity.getId()});
			  if(entryList.size() > 0) {
				  entity.setMainToEntry(entryList);
				  result.add(entity);
			  }
		  } else {
			  result.add(entity);
		  }
		  for(TSDepart depart2 : departList){
			  if(depart2.getId().equals(entity.getSonId())){
				  entity.setSonName(depart2.getDepartname());
				  break;
			  }
		  }
	  }
	  if(StringUtils.isNotEmpty(tranType)) {
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  for (TScPoStockBillExcelEntity entity : result) {
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
		  }
	  }
  //如需动态排除部分列不导出时启用，列名指Excel中文列名
  String[] exclusions = {"排除列名1","排除列名2"};
  String fileName = "采购入库单";
  if("53".equals(tranType)){
	  fileName = "采购退货单";
  }
  modelMap.put(NormalExcelConstants.FILE_NAME,fileName);
  modelMap.put(NormalExcelConstants.CLASS,TScPoStockBillExcelEntity.class);
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
  public String exportXlsByT(TScPoStockbillEntity tScPoStockbill,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  modelMap.put(TemplateExcelConstants.FILE_NAME, "采购出入库单");
  modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
  modelMap.put(TemplateExcelConstants.MAP_DATA,null);
  modelMap.put(TemplateExcelConstants.CLASS,TScPoStockbillEntity.class);
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
    ExcelImportResult importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(),TScPoStockbillEntity.class,params);
    List<TScPoStockbillEntity> listTScPoStockbillEntitys = importResult.getList();
    StringBuilder stringBuilder = new StringBuilder("已存在的数据:");
    boolean flag = false;
    if(!importResult.isVerfiyFail()) {
      for (TScPoStockbillEntity tScPoStockbill : listTScPoStockbillEntitys) {
      //以下检查导入数据是否重复的语句可视需求启用
        //Long count = tScPoStockbillService.getCountForJdbc("这里放检查数据是否重复的SQL语句");
        //if(count >0) {
          //flag = true;
          //stringBuilder.append(tScPoStockbill.getId()+",");
        //} else {
          tScPoStockbillService.save(tScPoStockbill);
        //}
      }
      j.setMsg("文件导入成功！");
      //j.setMsg("文件导入成功！" + (flag? stringBuilder.toString():""));
    }else {
      String excelPath = "/upload/excelUpload/TScPoStockbillEntity/"+importResult.getExcelName();
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
	 * 加载明细列表[采购出入库单明细表]
	 *
	 * @return
	 */
	@RequestMapping(params = "tScPoStockbillentryList")
	public ModelAndView tScPoStockbillentryList(TScPoStockbillEntity tScPoStockbill, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id0 = tScPoStockbill.getId();
		//===================================================================================
		//查询-采购出入库单明细表
	    String hql0 = "from TScPoStockbillentryEntity where 1 = 1 AND fid = ? order by findex";
		List<TScStockEntity> stockEntityList = systemService.findHql("from TScStockEntity");
		List<TSBillInfoEntity> billInfoEntityList = systemService.findHql("from TSBillInfoEntity");
		Map<String,Object> billInfo = new HashMap<String, Object>();
		if(billInfoEntityList.size() > 0){
			for(TSBillInfoEntity bill : billInfoEntityList){
				billInfo.put(bill.getBillId(),bill.getBillName());
			}
		}
	    try{
	    	List<TScPoStockbillentryEntity> tScPoStockbillentryEntityList = systemService.findHql(hql0,id0);
			for(TScPoStockbillentryEntity entry : tScPoStockbillentryEntityList){
				entry.setFreeGifts_(entry.getFreeGifts());
				if(StringUtils.isNotEmpty(entry.getStockId())){
					for(TScStockEntity stockEntity : stockEntityList){
						if(stockEntity.getId().equals(entry.getStockId())){
							entry.setStockName(stockEntity.getName());
						}
					}
				}
				if(StringUtils.isNotEmpty(entry.getItemId())){
					TScIcitemEntity item = systemService.getEntity(TScIcitemEntity.class,entry.getItemId());
					if(null != item){
						entry.setItemName(item.getName());
						entry.setItemNo(item.getNumber());
						entry.setModel(item.getModel());
						entry.setIsKFPeriod(item.getIskfPeriod());
						entry.setBatchManager(item.getBatchManager());
						TScItemPriceEntity price = systemService.getEntity(TScItemPriceEntity.class, entry.getUnitId());
						entry.setBarCode(price.getBarCode());
						if(null != price.getCgLimitPrice()) {
							entry.setCgLimitPrice(Double.parseDouble(price.getCgLimitPrice().toString()));
						}
						entry.setIsKFPeriod(item.getIskfPeriod());
						entry.setBatchManager(item.getBatchManager());
					}
				}
				if(StringUtils.isNotEmpty(entry.getClassIDSrc())){
					String classIDName = (String) billInfo.get(entry.getClassIDSrc());
					entry.setClassIDName(classIDName);
					//如果源单来自采购订单，则查询订单数量
					if(StringUtils.isNotEmpty(entry.getEntryIdSrc()) && entry.getClassIDSrc().equals(Globals.SC_PO_ORDER_TRANTYPE.toString())){
						TScPoOrderentryEntity orderentryEntity = systemService.getEntity(TScPoOrderentryEntity.class,entry.getEntryIdSrc());
						if(null != orderentryEntity){
							Double stockQty = orderentryEntity.getStockQty();
							Double qty = orderentryEntity.getQty();
							Double nowQty = entry.getQty();
							BigDecimal billQty = BigDecimal.valueOf(qty).subtract(BigDecimal.valueOf(stockQty)).add(BigDecimal.valueOf(nowQty));
							entry.setBillQty(billQty.doubleValue());
						}
					}
				}
			}
			req.setAttribute("tScPoStockbillentryList", tScPoStockbillentryEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		String load = req.getParameter("load");
		req.setAttribute("load",load);
		String tranType = req.getParameter("tranType");
		req.setAttribute("tranType",tranType);
		return new ModelAndView("com/qihang/buss/sc/sales/tScPoStockbillentryList");
	}

	//校验单价为零是否可保存
	@RequestMapping(params = "checkIsPriceZero")
	@ResponseBody
	public AjaxJson checkIsPriceZero(){
		AjaxJson j = new AjaxJson();
		return j;
	}

	//作废功能
	@RequestMapping(params = "cancelBill")
	@ResponseBody
	public AjaxJson cancelBill(String ids){
		AjaxJson j = tScPoStockbillService.cancelBill(ids);
		if(j.isSuccess()) {
			//校验订单是否自动关闭
			String[] idList = ids.split(",");
			Set<String> mainId = new HashSet<String>();
			for (String id : idList) {
				List<TScPoStockbillentryEntity> entryList = tScPoStockbillService.findHql("from TScPoStockbillentryEntity where fid = ?", new Object[]{id});
				for (TScPoStockbillentryEntity entry : entryList) {
					if (StringUtils.isNotEmpty(entry.getIdOrder())) {
						mainId.add(entry.getIdOrder());
					}
				}
			}
			if (mainId.size() > 0) {
				scPoOrderService.checkAutoFlag(mainId);
			}
		}
		return j;
	}

	//反作废
	@RequestMapping(params = "enableBill")
	@ResponseBody
	public AjaxJson enableBill(String ids){
		AjaxJson j = tScPoStockbillService.enableBill(ids);
		if(j.isSuccess()) {
			//校验订单是否自动关闭
			String[] idList = ids.split(",");
			Set<String> mainId = new HashSet<String>();
			for (String id : idList) {
				List<TScPoStockbillentryEntity> entryList = tScPoStockbillService.findHql("from TScPoStockbillentryEntity where fid = ?", new Object[]{id});
				for (TScPoStockbillentryEntity entry : entryList) {
					if (StringUtils.isNotEmpty(entry.getIdOrder())) {
						mainId.add(entry.getIdOrder());
					}
				}
			}
			if (mainId.size() > 0) {
				scPoOrderService.checkAutoFlag(mainId);
			}
		}
		return j;
	}

	//选单界面跳转
	@RequestMapping(params = "goSelectDialog")
	public ModelAndView goSelectDialog(TScPoStockbillEntity stockBill, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(stockBill.getItemId())) {
			req.setAttribute("itemId", stockBill.getItemId());
		}
		req.setAttribute("tranType",stockBill.getTranType());
		req.setAttribute("sonId",stockBill.getSonId());
		return new ModelAndView("com/qihang/buss/sc/sales/tScPoStockbillSelect");
	}


	//选单列表显示
	@RequestMapping(params = "datagridMain")
	public void datagridMain(TScPoStockbillView2Entity tPoOrder,String tranTypes,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScPoStockbillView2Entity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPoOrder);
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
//			if("52".equals(tranTypes)) {
//				cq.eq("closed", 0);
//				cq.eq("autoFlag", 0);
//			}else if("53".equals(tranTypes)){
//				cq.or(Restrictions.and(Restrictions.eq("autoFlag", 0), Restrictions.eq("closed", 0)), Restrictions.eq("autoFlag", 1));
//				cq.eq("isUse",1);
//				//cq.createAlias("entryList", "e");
//				//cq.add(Restrictions.gt("e.stockQty",0.0));
//				//cq.gt("entryList.stockQty",0.0);
//			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScPoStockbillService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}


	/**
	 * 选单明细数据
	 * @param id
	 * @param tranType
	 * @return
	 */
	@RequestMapping(params = "loadEntryList")
	@ResponseBody
	public List<TScPoStockbillentryEntity> loadEntryList(String id,String tranType){
		List<TScPoStockbillentryEntity> entryList = tScPoStockbillService.loadEntryList(id,tranType);//("from TScPoOrderentryEntity where fid = ?",new Object[]{id});
		return entryList;
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
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		return tScPoStockbillService.afterAudit(id,audit,tranType,depart.getId());
	}

	@RequestMapping(params = "loadEntryViewList")
	@ResponseBody
	public List<TScPoStockBillViewEntity> loadEntryViewList(TScPoStockBillViewEntity tPoOrder,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid){
		CriteriaQuery cq = new CriteriaQuery(TScPoStockBillViewEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPoOrder);
		try{
			//自定义追加查询条件
			cq.eq("isStock",0);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScPoStockbillService.getDataGridReturn(cq, true);
		List<TScPoStockBillViewEntity> viewList = dataGrid.getResults();
		return viewList;
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
		return new ModelAndView("com/qihang/buss/sc/sales/tScPoStockBillInfo");
	}

	@RequestMapping(params = "datagridMain2")
	public void datagridMain2(TScPoStockBillInfoView tScSlStockbill,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScPoStockBillInfoView.class, dataGrid);
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
				List<TScPoStockbillEntity> stockbillEntityList = systemService.findHql("from TScPoStockbillEntity where idSrc in ("+queryIdStr+")");
				if(stockbillEntityList.size() > 0){
					for(TScPoStockbillEntity entity : stockbillEntityList){
						idList.add(entity.getId());
					}
				}
				cq.add(Restrictions.not(Restrictions.in("idSrc",idList.toArray())));
			}
			//过滤已选源单数据
			if(StringUtils.isNotEmpty(ids2)){
				List<String> idList = new ArrayList<String>();
				String[] idArr = ids2.split(",");
				for(String id : idArr){
					idList.add(id);
				}
				cq.add(Restrictions.not(Restrictions.in("id",idList.toArray())));
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
		this.tScPoStockbillService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
}
