
package com.qihang.buss.sc.sales.controller;
import com.qihang.buss.sc.baseinfo.entity.*;
import com.qihang.buss.sc.baseinfo.service.*;
import com.qihang.buss.sc.sales.entity.*;
import com.qihang.buss.sc.sales.page.TPoOrderPage;
import com.qihang.buss.sc.sales.service.TScPoOrderServiceI;
import com.qihang.buss.sc.sysaudit.entity.TSAuditEntity;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
import com.qihang.buss.sc.util.AccountUtil;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Title: Controller
 * @Description: 采购订单主表
 * @author onlineGenerator
 * @date 2016-07-04 09:28:41
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tPoOrderController")
public class TScPoOrderController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScPoOrderController.class);

	@Autowired
	private TScPoOrderServiceI tPoOrderService;
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
	private UserService userService;

	/**
	 * 采购订单主表列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tPoOrder")
	public ModelAndView tPoOrder(HttpServletRequest request) {
		String defaultPageNum = "";//默认分页数
		//从系统参数中获取默认分页数
		List<TSConfig> tsConfigList = this.tPoOrderService.findByProperty(TSConfig.class,"code","CFG_PAGE");
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
		List<TSAuditEntity> tsAuditEntityList = systemService.findHql("from TSAuditEntity where billId=? and sonId = ?",new Object[]{"51",depart.getId()});
		if(tsAuditEntityList.size() > 0) {
			TSAuditEntity auditEntity = tsAuditEntityList.get(0);
			if (null != auditEntity) {
				Integer isEdit = auditEntity.getIsEdit();
				request.setAttribute("isEdit", isEdit);
			}
		}
		Date checkDate = AccountUtil.getAccountStartDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(checkDate);
		request.setAttribute("checkDate",dateStr);
		String isWarm = request.getParameter("isWarm");
		request.setAttribute("isWarm",isWarm);
		return new ModelAndView("com/qihang/buss/sc/sales/tScPoOrderList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScPoOrderViewEntity tPoOrder,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//汇总应付金额，已付金额，数量，金额，折后金额
		dataGrid.setFooter("allAmount,checkAmount,qty,taxAmountEx,inTaxAmount");
		CriteriaQuery cq = new CriteriaQuery(TScPoOrderViewEntity.class, dataGrid);
		//cq.addOrder("date",SortDirection.desc);
		//cq.addOrder("billNo",SortDirection.desc);
		//cq.addOrder("id",SortDirection.desc);
		//cq.addOrder("entryId",SortDirection.asc);
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
			cq.addOrder("cancellation", SortDirection.asc);

			String isWarm = request.getParameter("isWarm");
			if(StringUtils.isNotEmpty(isWarm)){
				if("1".equals(isWarm)) {
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					Date beginDate = new Date();
					String poWarmDate = "0";
					List<TSConfig> tsConfigList = systemService.findByProperty(TSConfig.class, "code", "CFG_PRUORDEARWAR_DAYS");//采购订单预警天数
					if (tsConfigList.size() > 0) {
						poWarmDate = tsConfigList.get(0).getContents();
					}
					Calendar date = Calendar.getInstance();
					date.setTime(beginDate);
					date.set(Calendar.DATE, date.get(Calendar.DATE) + Integer.parseInt(poWarmDate));
					Date endDate = sdf.parse(sdf.format(date.getTime()));
					cq.le("jhDate", endDate);
					cq.eq("cancellation", 0);
					cq.eq("closed", 0);
					cq.eq("autoFlag", 0);
				} else {
					String userId = ResourceUtil.getSessionUserName().getId();
					TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
					TSDepart depart = systemService.getParentSonInfo(sonInfo);
					Boolean isAudit = userService.isAllowAudit(tPoOrder.getTranType().toString(),userId,depart.getId());
					cq.eq("cancellation",0);
					//判断当前用户是否在多级审核队列中
					if(isAudit){
						Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId,depart.getId(),tPoOrder.getTranType().toString());
						if(userAuditLeave.size() > 0){
							String leaves = "";
							for(Integer leave : userAuditLeave){
								leaves += leave+",";
							}
							leaves = leaves.substring(0,leaves.length()-1);
							List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in ("+leaves+")",new Object[]{depart.getId(),tPoOrder.getTranType().toString()});
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
			}
			//若前端未传回分支机构数据，则默认当前登录用户登录用户分支机构数据；
//			if(sonList.size() == 0) {
//				String sonId = ResourceUtil.getSessionUserName().getCurrentDepart().getId();
//				cq.eq("sonId", sonId);
//			}else{
//				cq.in("sonId",sonList.toArray());
//			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tPoOrderService.getDataGridReturn(cq, true);
		//sessionFactory.getCurrentSession().evict(dataGrid);
//		String tranType = request.getParameter("tranType");
//		if(StringUtils.isNotEmpty(tranType)) {
//			List<TScPoOrderViewEntity> result = dataGrid.getResults();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			for (TScPoOrderViewEntity entity : result) {
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
		//todo:测试切换数据源的连接信息[测试通过后需清除]
		//systemService.getConn();
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除采购订单主表
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScPoOrderEntity tPoOrder, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tPoOrder = systemService.getEntity(TScPoOrderEntity.class, tPoOrder.getId());
		String message = "采购订单主表删除成功";
		try{
			//供应商
			if(StringUtils.isNotEmpty(tPoOrder.getItemId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_DEL_TYPE);
				countEntity.setOldId(tPoOrder.getItemId());
				boolean updateIsSuccess = supplierServiceI.updateSupplierCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料供应商引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//经办人
			if(StringUtils.isNotEmpty(tPoOrder.getEmpId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_DEL_TYPE);
				countEntity.setOldId(tPoOrder.getEmpId());
				boolean updateIsSuccess = empServiceI.updateEmpCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料职员引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//部门
			if(StringUtils.isNotEmpty(tPoOrder.getDeptId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_DEL_TYPE);
				countEntity.setOldId(tPoOrder.getDeptId());
				boolean updateIsSuccess = departmentServiceI.updateDepartmentCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料部门引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//仓库
			if(StringUtils.isNotEmpty(tPoOrder.getStockId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_DEL_TYPE);
				countEntity.setOldId(tPoOrder.getStockId());
				boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料部门引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			List<TScPoOrderentryEntity> entryList = tPoOrderService.findHql("from TScPoOrderentryEntity where fid = ?",new Object[]{tPoOrder.getId()});
			for(TScPoOrderentryEntity entry : entryList){
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
			tPoOrderService.delMain(tPoOrder);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			//删除待审核预警数据
			systemService.delBillAuditStatus(tPoOrder.getTranType().toString(), tPoOrder.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "采购订单主表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除采购订单主表
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "采购订单主表删除成功";
		try{
			for(String id:ids.split(",")){
				TScPoOrderEntity tPoOrder = systemService.getEntity(TScPoOrderEntity.class,
				id
				);
				tPoOrderService.delMain(tPoOrder);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "采购订单主表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加采购订单主表
	 *
	 * @param tPoOrder
	 * @param tPoOrderPage
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScPoOrderEntity tPoOrder,TPoOrderPage tPoOrderPage, HttpServletRequest request) {
		List<TScPoOrderentryEntity> tPoOrderentryList =  tPoOrderPage.getTPoOrderentryList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		//校验单据编号唯一性
		Boolean hasBillNo = systemService.checkBillNo(TScOrderEntity.class.getAnnotation(Table.class).name(),tPoOrder.getBillNo(),tPoOrder.getId());
		if(!hasBillNo){
			j.setSuccess(false);
			j.setMsg("单据编号已存在,请确认");
			return j;
		}
		try{
			tPoOrder.setCheckAmount(0.0);
			tPoOrderService.addMain(tPoOrder, tPoOrderentryList);
			//供应商
			if (org.apache.commons.lang.StringUtils.isNotEmpty(tPoOrder.getItemId())) {
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_ADD_TYPE);
				countEntity.setOldId(tPoOrder.getItemId());
				boolean updateIsSuccess = supplierServiceI.updateSupplierCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料供应商引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//仓库
			if(StringUtils.isNotEmpty(tPoOrder.getStockId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_ADD_TYPE);
				countEntity.setOldId(tPoOrder.getStockId());
				boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料仓库引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//经办人
			if(StringUtils.isNotEmpty(tPoOrder.getEmpId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_ADD_TYPE);
				countEntity.setOldId(tPoOrder.getEmpId());
				boolean updateIsSuccess = empServiceI.updateEmpCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料职员引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//部门
			if(StringUtils.isNotEmpty(tPoOrder.getDeptId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_ADD_TYPE);
				countEntity.setOldId(tPoOrder.getDeptId());
				boolean updateIsSuccess = departmentServiceI.updateDepartmentCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料职员引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			for(TScPoOrderentryEntity entry : tPoOrderentryList){
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
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			//待审核数据提醒操作
			systemService.saveBillAuditStatus(tPoOrder.getTranType().toString(),tPoOrder.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "采购订单主表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新采购订单主表
	 *
	 * @param tPoOrder
	 * @param tPoOrderPage
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScPoOrderEntity tPoOrder,TPoOrderPage tPoOrderPage, HttpServletRequest request) {
		List<TScPoOrderentryEntity> tPoOrderentryList =  tPoOrderPage.getTPoOrderentryList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		//校验单据编号唯一性
		Boolean hasBillNo = systemService.checkBillNo(TScOrderEntity.class.getAnnotation(Table.class).name(),tPoOrder.getBillNo(),tPoOrder.getId());
		if(!hasBillNo){
			j.setSuccess(false);
			j.setMsg("单据编号已存在,请确认");
			return j;
		}
		try{
			TScPoOrderEntity oldEntity = systemService.getEntity(TScPoOrderEntity.class, tPoOrder.getId());
			//供应商
			if (StringUtils.isNotEmpty(tPoOrder.getItemId()) && !oldEntity.getItemId().equals(tPoOrder.getItemId())) {
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_UPDATE_TYPE);
				countEntity.setOldId(oldEntity.getItemId());
				countEntity.setNewId(tPoOrder.getItemId());
				boolean updateIsSuccess = supplierServiceI.updateSupplierCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料供应商引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//仓库
			if(StringUtils.isNotEmpty(tPoOrder.getStockId())){
				TScCountEntity countEntity = new TScCountEntity();
				if(StringUtils.isNotEmpty(oldEntity.getStockId()) &&  !oldEntity.getStockId().equals(tPoOrder.getStockId())) {
					countEntity.setType(Globals.COUNT_UPDATE_TYPE);
					countEntity.setOldId(oldEntity.getStockId());
					countEntity.setNewId(tPoOrder.getStockId());
					boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
					if (updateIsSuccess == false) {
						message = "更新基础资料仓库引用次数失败";
						j.setMsg(message);
						return j;
					}
				}else if(StringUtils.isEmpty(oldEntity.getStockId())){
					countEntity.setType(Globals.COUNT_ADD_TYPE);
					countEntity.setOldId(tPoOrder.getStockId());
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
			if(StringUtils.isNotEmpty(tPoOrder.getEmpId()) && !oldEntity.getEmpId().equals(tPoOrder.getEmpId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_UPDATE_TYPE);
				countEntity.setOldId(oldEntity.getEmpId());
				countEntity.setNewId(tPoOrder.getEmpId());
				boolean updateIsSuccess = empServiceI.updateEmpCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料职员引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//部门
			if(StringUtils.isNotEmpty(tPoOrder.getDeptId()) && !oldEntity.getDeptId().equals(tPoOrder.getDeptId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_UPDATE_TYPE);
				countEntity.setOldId(oldEntity.getDeptId());
				countEntity.setNewId(tPoOrder.getDeptId());
				boolean updateIsSuccess = departmentServiceI.updateDepartmentCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料职员引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//明细数据
			for(TScPoOrderentryEntity entry : tPoOrderentryList){
				TScPoOrderentryEntity oldEntry = tPoOrderService.getEntity(TScPoOrderentryEntity.class, entry.getId());
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
						countEntity.setOldId(oldEntity.getStockId());
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
			tPoOrderService.updateMain(tPoOrder, tPoOrderentryList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新采购订单主表失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 采购订单主表新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScPoOrderEntity tPoOrder, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPoOrder.getId())) {
			tPoOrder = tPoOrderService.getEntity(TScPoOrderEntity.class, tPoOrder.getId());
			req.setAttribute("tPoOrderPage", tPoOrder);
		}else{
			String billNo = BillNoGenerate.getBillNo("51");
			req.setAttribute("billNo",billNo);
			String defaultTaxRate = "";//默认税率
			//从系统参数中获取默认税率
			List<TSConfig> tsConfigList = this.tPoOrderService.findByProperty(TSConfig.class,"code","CFG_TAX_RATE");
			if(tsConfigList.size()>0){
				defaultTaxRate = tsConfigList.get(0).getContents();
			}
			if(StringUtils.isNotEmpty(defaultTaxRate)) {
				req.setAttribute("taxRate", defaultTaxRate);
			}else{
				req.setAttribute("taxRate",0);
			}
			//String checkDate = "2016-06-30";
			//req.setAttribute("checkDate",checkDate);
			Date checkDate = AccountUtil.getAccountStartDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = sdf.format(checkDate);
			req.setAttribute("checkDate",dateStr);
			req.setAttribute("date",sdf.format(new Date()));
			//当前用户所在分支机构
			TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart depart = systemService.getParentSonInfo(sonInfo);
			req.setAttribute("sonId",depart.getId());
			req.setAttribute("sonName",depart.getDepartname());
		}
		return new ModelAndView("com/qihang/buss/sc/sales/tScPoOrder-add");
	}

	/**
	 * 采购订单主表编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScPoOrderEntity tPoOrder, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tPoOrder.getId())) {
			Integer state = tPoOrder.getCheckState();
			tPoOrder = tPoOrderService.getEntity(TScPoOrderEntity.class, tPoOrder.getId());
			req.setAttribute("tPoOrderPage", tPoOrder);
			String load = req.getParameter("load");
			req.setAttribute("load",load);
			TScDepartmentEntity dept = tPoOrderService.getEntity(TScDepartmentEntity.class,tPoOrder.getDeptId());
			TScStockEntity stock = tPoOrderService.getEntity(TScStockEntity.class,tPoOrder.getStockId());
			TScSupplierEntity supplier = tPoOrderService.getEntity(TScSupplierEntity.class,tPoOrder.getItemId());
			TSUser user = tPoOrderService.getEntity(TSUser.class,tPoOrder.getBillerId());
			TScEmpEntity emp = tPoOrderService.getEntity(TScEmpEntity.class,tPoOrder.getEmpId());
			if(null != dept) {
				tPoOrder.setDeptName(dept.getName());
			}
			if(null != stock) {
				tPoOrder.setStockName(stock.getName());
			}
			if(null != supplier){
				tPoOrder.setItemName(supplier.getName());
			}
			if(null != user){
				tPoOrder.setBillerName(user.getRealName());
			}
			if(null != emp){
				tPoOrder.setEmpName(emp.getName());
			}
			TSDepart sonEntity = systemService.getEntity(TSDepart.class,tPoOrder.getSonId());
			if(null != sonEntity){
				tPoOrder.setSonName(sonEntity.getDepartname());
			}
			Integer tranType = tPoOrder.getTranType();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			List<TSAuditRelationEntity> info = systemService.getAuditInfoList(tPoOrder.getId(), tranType.toString());
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
			req.setAttribute("auditor", auditor);
			req.setAttribute("auditDate", auditDate);
			//TScPoOrderentryEntity entry = new TScPoOrderentryEntity();
			//entry.setFid(tPoOrder.getId());
			List<TScPoOrderentryEntity> entryList = tPoOrderService.findHql("from TScPoOrderentryEntity where fid=?",new Object[]{tPoOrder.getId()});
			req.setAttribute("isStock",0);
			for(TScPoOrderentryEntity entryInfo : entryList){
				if(null != entryInfo.getStockQty() && entryInfo.getStockQty() > 0){
					req.setAttribute("isStock",1);
				}
			}
		}
		String defaultTaxRate = "";//默认税率
		//从系统参数中获取默认税率
		List<TSConfig> tsConfigList = this.tPoOrderService.findByProperty(TSConfig.class,"code","CFG_TAX_RATE");
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
		return new ModelAndView("com/qihang/buss/sc/sales/tScPoOrder-update");
	}

  /**
  * 导入功能跳转
  *
  * @return
  */
  @RequestMapping(params = "upload")
  public ModelAndView upload(HttpServletRequest req) {
  req.setAttribute("controller_name","tPoOrderController");
  return new ModelAndView("common/upload/pub_excel_upload");
  }

  /**
  * 导出excel
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXls")
  public String exportXls(TScPoOrderExcelEntity tPoOrder,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
	  CriteriaQuery cq = new CriteriaQuery(TScPoOrderExcelEntity.class, dataGrid);
	  com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPoOrder, request.getParameterMap());
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
	  List<TScPoOrderExcelEntity> tScIcitems = this.tPoOrderService.getListByCriteriaQuery(cq, false);
	  TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
	  TSDepart depart1 = systemService.getParentSonInfo(depart);
	  Set<String> departIds = systemService.getAllSonId(depart1.getId());
	  CriteriaQuery deptCq = new CriteriaQuery(TSDepart.class, dataGrid);
	  deptCq.in("id", departIds.toArray());
	  List<TSDepart> departList = this.tPoOrderService.getListByCriteriaQuery(deptCq,false);
	  List<TScPoOrderExcelEntity> result = new ArrayList<TScPoOrderExcelEntity>();
	  for (TScPoOrderExcelEntity entity : tScIcitems) {
		  if(StringUtils.isNotEmpty(itemName)) {
			  String hql = "from TScPoOrderentryExcelEntity where entryToMain.id = ? ";
			  hql += " and icitemEntity.name like '%" + itemName + "%' order by findx asc";
			  List<TScPoOrderentryExcelEntity> entryList = this.tPoOrderService.findHql(hql, new Object[]{entity.getId()});
			  if (entryList.size() > 0) {
				  entity.setMainToEntry(entryList);
				  result.add(entity);
			  }
		  } else {
			  result.add(entity);
		  }
	  }
	  String tranType = request.getParameter("tranType");
	  if(StringUtils.isNotEmpty(tranType)) {
		  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  for (TScPoOrderExcelEntity entity : result) {
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
	  String[] exclusions = {"排除列名1", "排除列名2"};
	  modelMap.put(NormalExcelConstants.FILE_NAME, "采购订单主表");
	  modelMap.put(NormalExcelConstants.CLASS, TScPoOrderExcelEntity.class);
	  modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("采购订单主表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
			  "导出信息", exclusions));
	  modelMap.put(NormalExcelConstants.DATA_LIST, result);
	  return NormalExcelConstants.JEECG_EXCEL_VIEW;
//  CriteriaQuery cq = new CriteriaQuery(TScPoOrderEntity.class, dataGrid);
//  com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPoOrder, request.getParameterMap());
//  List<TScPoOrderEntity> tPoOrders = this.tPoOrderService.getListByCriteriaQuery(cq,false);
//  //如需动态排除部分列不导出时启用，列名指Excel中文列名
//  String[] exclusions = {"排除列名1","排除列名2"};
//  modelMap.put(NormalExcelConstants.FILE_NAME,"采购订单主表");
//  modelMap.put(NormalExcelConstants.CLASS,TScPoOrderEntity.class);
//  modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("采购订单主表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
//  "导出信息",exclusions));
//  modelMap.put(NormalExcelConstants.DATA_LIST, tPoOrders);
//  return NormalExcelConstants.JEECG_EXCEL_VIEW;
  }
  /**
  * 导出excel 使模板
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXlsByT")
  public String exportXlsByT(TScPoOrderExcelEntity tPoOrder,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  modelMap.put(TemplateExcelConstants.FILE_NAME, "采购订单主表");
  modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
  modelMap.put(TemplateExcelConstants.MAP_DATA,null);
  modelMap.put(TemplateExcelConstants.CLASS,TScPoOrderExcelEntity.class);
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
    ExcelImportResult importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(),TScPoOrderEntity.class,params);
    List<TScPoOrderEntity> listTScPoOrderEntities = importResult.getList();
    StringBuilder stringBuilder = new StringBuilder("已存在的数据:");
    boolean flag = false;
    if(!importResult.isVerfiyFail()) {
      for (TScPoOrderEntity tPoOrder : listTScPoOrderEntities) {
      //以下检查导入数据是否重复的语句可视需求启用
        //Long count = tPoOrderService.getCountForJdbc("这里放检查数据是否重复的SQL语句");
        //if(count >0) {
          //flag = true;
          //stringBuilder.append(tPoOrder.getId()+",");
        //} else {
          tPoOrderService.save(tPoOrder);
        //}
      }
      j.setMsg("文件导入成功！");
      //j.setMsg("文件导入成功！" + (flag? stringBuilder.toString():""));
    }else {
      String excelPath = "/upload/excelUpload/TScPoOrderEntity/"+importResult.getExcelName();
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
	 * 加载明细列表[采购订单从表]
	 *
	 * @return
	 */
	@RequestMapping(params = "tPoOrderentryList")
	public ModelAndView tPoOrderentryList(TScPoOrderEntity tPoOrder,String checkState, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id0 = tPoOrder.getId();
		//===================================================================================
		//查询-采购订单从表
		req.setAttribute("checkState", checkState);
	    String hql0 = "from TScPoOrderentryEntity where 1 = 1 AND fid = ? order by findex";
		List<TScStockEntity> stockEntityList = systemService.findHql("from TScStockEntity");
	    try{
	    	List<TScPoOrderentryEntity> tScPoOrderentryEntityList = systemService.findHql(hql0,id0);
			for(TScPoOrderentryEntity entry : tScPoOrderentryEntityList){
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
						TScItemPriceEntity price = systemService.getEntity(TScItemPriceEntity.class, entry.getUnitId());
						entry.setBarCode(price.getBarCode());
						entry.setCgLimitPrice(price.getCgLimitPrice());
					}
					entry.setIsKfPeriod(item.getIskfPeriod());
				}
			}
			req.setAttribute("tPoOrderentryList", tScPoOrderentryEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		String load = req.getParameter("load");
		req.setAttribute("load",load);
		return new ModelAndView("com/qihang/buss/sc/sales/tScPoOrderentryList");
	}

	//校验单价为零是否可保存
	@RequestMapping(params = "checkIsPriceZero")
	@ResponseBody
	public AjaxJson checkIsPriceZero(){
		AjaxJson j = new AjaxJson();
		return j;
	}

	//关闭功能
	@RequestMapping(params = "closeBill")
	@ResponseBody
	public AjaxJson closeBill(String ids){
		AjaxJson j = tPoOrderService.closeBill(ids);
		return j;
	}
	//反关闭功能
	@RequestMapping(params = "openBill")
	@ResponseBody
	public AjaxJson openBill(String ids){
		AjaxJson j = tPoOrderService.openBill(ids);
		return j;
	}

	//作废功能
	@RequestMapping(params = "cancelBill")
	@ResponseBody
	public AjaxJson cancelBill(String ids){
		AjaxJson j = tPoOrderService.cancelBill(ids);
		return j;
	}

	//反作废功能
	@RequestMapping(params = "enableBill")
	@ResponseBody
	public AjaxJson enableBill(String ids){
		AjaxJson j = tPoOrderService.enableBill(ids);
		return j;
	}

	//选单界面跳转
	@RequestMapping(params = "goSelectDialog")
	public ModelAndView goSelectDialog(TScPoOrderEntity tScIcitemEntity, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScIcitemEntity.getItemId())) {
			req.setAttribute("itemId", tScIcitemEntity.getItemId());
		}
		req.setAttribute("tranType", tScIcitemEntity.getTranType());
		req.setAttribute("sonId",tScIcitemEntity.getSonId());
		return new ModelAndView("com/qihang/buss/sc/sales/tScPoOrderSelect");
	}

	//选单列表显示
	@RequestMapping(params = "datagridMain")
	public void datagridMain(TScPoOrderEntity tPoOrder,String tranTypes,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScPoOrderEntity.class, dataGrid);
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
			if("52".equals(tranTypes)) {
				cq.eq("closed", 0);
				cq.eq("autoFlag", 0);
			}else if("53".equals(tranTypes)){
				cq.or(Restrictions.and(Restrictions.eq("autoFlag", 0), Restrictions.eq("closed", 0)), Restrictions.eq("autoFlag", 1));
				cq.eq("isUse",1);
				//cq.createAlias("entryList", "e");
				//cq.add(Restrictions.gt("e.stockQty",0.0));
				//cq.gt("entryList.stockQty",0.0);
			}
			cq.eq("cancellation",0);
			cq.eq("checkState",2);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tPoOrderService.getDataGridReturn(cq, true);
		List<TScPoOrderEntity> result = dataGrid.getResults();
		for (TScPoOrderEntity entity : result) {
			String itemId = entity.getItemId();
			TScSupplierEntity supplier = systemService.getEntity(TScSupplierEntity.class, itemId);
			if(null != supplier){
				entity.setItemName(supplier.getName());
			}
			String empId = entity.getEmpId();
			TScEmpEntity emp = systemService.getEntity(TScEmpEntity.class, empId);
			if(null != emp){
				entity.setEmpName(emp.getName());
			}
			String deptId = entity.getDeptId();
			TScDepartmentEntity dept = systemService.getEntity(TScDepartmentEntity.class, deptId);
			if(null != dept){
				entity.setDeptName(dept.getName());
			}
			String stockId = entity.getStockId();
			if(StringUtils.isNotEmpty(stockId)){
				TScStockEntity stock = systemService.getEntity(TScStockEntity.class,stockId);
				if(null != stock){
					entity.setStockName(stock.getName());
				}
			}
		}
		dataGrid.setResults(result);
		TagUtil.datagrid(response, dataGrid);
	}

	@RequestMapping(params = "loadEntryList")
	@ResponseBody
	public List<TScPoOrderentryEntity> loadEntryList(String id,String tranType){
		List<TScPoOrderentryEntity> entryList = tPoOrderService.loadEntryList(id,tranType);//("from TScPoOrderentryEntity where fid = ?",new Object[]{id});
		return entryList;
	}

	@RequestMapping(params = "afterAudit")
	@ResponseBody
	public AjaxJson afterAudit(String id,Integer audit){
		return tPoOrderService.afterAudit(id,audit);
	}

	@RequestMapping(params = "loadEntryViewList")
	@ResponseBody
	public List<TScPoOrderViewEntity> loadEntryViewList(TScPoOrderViewEntity tPoOrder,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid){
		CriteriaQuery cq = new CriteriaQuery(TScPoOrderViewEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPoOrder);
		try{
			//自定义追加查询条件
			cq.eq("isStock",0);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tPoOrderService.getDataGridReturn(cq, true);
		List<TScPoOrderViewEntity> viewList = dataGrid.getResults();
		return viewList;
	}

	//手机端接口

	//获取编辑数据
	@RequestMapping(params = "getBillById")
	@ResponseBody
	public AjaxJson getBillById(String id){
		AjaxJson j = new AjaxJson();
		Map<String,Object> result = new HashMap<String, Object>();
		TScPoOrderEntity orderEntity = new TScPoOrderEntity();
		List<TScPoOrderentryEntity> entityList = new ArrayList<TScPoOrderentryEntity>();
		result.put("main",orderEntity);
		result.put("child",entityList);
		j.setAttributes(result);
		return j;
	}

	/**
	 * 付款单-选单页面条状
	 * @param orderEntity
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "goSelectMainDialog")
	public ModelAndView goSelectMainDialog(TScPoOrderEntity orderEntity, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(orderEntity.getItemId())) {
			req.setAttribute("itemId", orderEntity.getItemId());
		}
		req.setAttribute("tranType", orderEntity.getTranType());
		String ids = req.getParameter("ids");
		req.setAttribute("ids", ids);
		String ids2 = req.getParameter("ids2");
		req.setAttribute("ids2",ids2);
		String sonId = req.getParameter("sonId");
		req.setAttribute("sonId",sonId);
		return new ModelAndView("com/qihang/buss/sc/sales/tScPoOrderMainList");
	}

	@RequestMapping(params = "datagridMain2")
	public void datagridMain2(TScPoOrderViewMainEntity tScOrder, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScPoOrderViewMainEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScOrder);
		String ids = request.getParameter("ids");
		try {
			//自定义追加查询条件
			//String ids2 = request.getParameter("ids2");
			if(StringUtils.isNotEmpty(ids)) {
				List<String> idInfo = new ArrayList<String>();
				String[] idsList = ids.split(",");
				for(String idStr : idsList){
					if(StringUtils.isNotEmpty(idStr)) {
						idInfo.add(idStr);
					}
				}

//				if(StringUtils.isNotEmpty(ids2)){
//					String[] idsList2 = ids2.split(",");
//					String queryIds = "";
//					for(String id2 : idsList2){
//						queryIds += "'"+id2+"',";
//					}
//					queryIds = queryIds.substring(0,queryIds.length()-1);
//					List<TScPoStockbillEntity> stockbillEntityList = systemService.findHql("from TScPoStockbillEntity where tranType = 52 and id in ("+queryIds+")");
//					if(stockbillEntityList.size() > 0){
//						for(TScPoStockbillEntity entity : stockbillEntityList){
//							if(StringUtils.isNotEmpty(entity.getIdSrc())) {
//								idInfo.add(entity.getIdSrc());
//							}
//						}
//					}
//				}
				if(idInfo.size() > 0) {
					cq.add(Restrictions.not(Restrictions.in("id", idInfo.toArray())));
				}
			}
			String query_date_begin = request.getParameter("date_begin");
			String query_date_end = request.getParameter("date_end");
			if(StringUtil.isNotEmpty(query_date_begin)){
				cq.ge("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_begin));
			}
			if(StringUtil.isNotEmpty(query_date_end)){
				cq.le("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_end));
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tPoOrderService.getDataGridReturn(cq, true);
		if(StringUtil.isEmpty(ids)) {
			List<String> stockbillInfo = systemService.findListbySql("select distinct id_Src from T_SC_PO_Order where trantype = '" + Globals.SC_PO_STOCKBILL_TRANTYPE + "' and checkState = 2 and id_Src <> '' ");
			if (stockbillInfo.size() > 0) {
				List<TScPoOrderViewMainEntity> result = dataGrid.getResults();
				for (String orderId : stockbillInfo) {
					for (TScPoOrderViewMainEntity viewEntity : result) {
						if (viewEntity.getId().equals(orderId)) {
							result.remove(viewEntity);
							break;
						}
					}
				}
				dataGrid.setResults(result);
				dataGrid.setTotal(result.size());
			}
		}
		TagUtil.datagrid(response, dataGrid);
	}

}
