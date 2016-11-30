
package com.qihang.buss.sc.sales.controller;
import com.qihang.buss.sc.baseinfo.entity.*;
import com.qihang.buss.sc.baseinfo.service.*;
import com.qihang.buss.sc.sales.entity.*;
import com.qihang.buss.sc.sales.service.TScIcJhstockbillServiceI;
import com.qihang.buss.sc.sales.page.TScIcJhstockbillPage;
import com.qihang.buss.sc.sysaudit.entity.TSAuditEntity;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TSBillInfoEntity;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
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
 * @Description: 采购换货单
 * @author onlineGenerator
 * @date 2016-07-19 17:41:15
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScIcJhstockbillController")
public class TScIcJhstockbillController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScIcJhstockbillController.class);

	@Autowired
	private TScIcJhstockbillServiceI tScIcJhstockbillService;
	@Autowired
	private SystemService systemService;

	@Autowired
	private TScIcitemServiceI tScIcitemServiceI;//商品

	@Autowired
	private TScSupplierServiceI supplierServiceI;//供应商

	@Autowired
	private TScStockServiceI stockServiceI;//仓库

	@Autowired
	private TScEmpServiceI empServiceI;//经办人

	@Autowired
	private TScDepartmentServiceI departmentServiceI;//部门

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private TScSettleacctServiceI settleacctServiceI;//结算账户

	@Autowired
	private UserService userService;



	/**
	 * 采购换货单列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tScIcJhstockbill")
	public ModelAndView tScIcJhstockbill(HttpServletRequest request) {
		String defaultPageNum = "";//默认分页数
		List<TSConfig> tsConfigList = this.tScIcJhstockbillService.findByProperty(TSConfig.class,"code","CFG_PAGE");
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
		List<TSAuditEntity> tsAuditEntityList = systemService.findHql("from TSAuditEntity where billId=? and sonId = ?",new Object[]{"61",depart.getId()});
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
		Date checkDate = AccountUtil.getAccountStartDate();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = sdf.format(checkDate);
		request.setAttribute("checkDate",dateStr);
		return new ModelAndView("com/qihang/buss/sc/sales/tScIcJhstockbillList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScIcJhstockbillViewEntity tScIcJhstockbill,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//汇总应付金额，已付金额，数量，金额，折后金额
		dataGrid.setFooter("allAmount,checkAmount,qty,taxAmountEx,inTaxAmount");
		CriteriaQuery cq = new CriteriaQuery(TScIcJhstockbillViewEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScIcJhstockbill);
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
		//cq.eq("sonId",sonId);
			String isWarm = request.getParameter("isWarm");
			if(org.apache.commons.lang.StringUtils.isNotEmpty(isWarm)){
				String userId = ResourceUtil.getSessionUserName().getId();
				TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
				TSDepart depart = systemService.getParentSonInfo(sonInfo);
				Boolean isAudit = userService.isAllowAudit(tScIcJhstockbill.getTranType().toString(),userId,depart.getId());
				//判断当前用户是否在多级审核队列中
				if(isAudit){
					Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId,depart.getId(),tScIcJhstockbill.getTranType().toString());
					if(userAuditLeave.size() > 0){
						String leaves = "";
						for(Integer leave : userAuditLeave){
							leaves += leave+",";
						}
						leaves = leaves.substring(0,leaves.length()-1);
						List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in ("+leaves+")",new Object[]{depart.getId(),tScIcJhstockbill.getTranType().toString()});
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
				cq.eq("cancellation",0);
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScIcJhstockbillService.getDataGridReturn(cq, true);
		List<TScIcJhstockbillViewEntity> result = dataGrid.getResults();
		for(TScIcJhstockbillViewEntity entity : result){
			if(StringUtil.isNotEmpty(entity.getBasicUnitId())){
				TScItemPriceEntity price = systemService.getEntity(TScItemPriceEntity.class,entity.getBasicUnitId());
				if(null != price && null != price.getMeasureunitToIcItemEntity()) {
					entity.setBasicUnitName(price.getMeasureunitToIcItemEntity().getName());
				}
			}
			if(StringUtil.isNotEmpty(entity.getSecUnitId())){
				TScItemPriceEntity price = systemService.getEntity(TScItemPriceEntity.class,entity.getSecUnitId());
				if(null != price && null != price.getMeasureunitToIcItemEntity()) {
					entity.setSecUnitName(price.getMeasureunitToIcItemEntity().getName());
				}
			}
		}
//		String tranType = request.getParameter("tranType");
//		if(StringUtils.isNotEmpty(tranType)) {
//			List<TScIcJhstockbillViewEntity> result = dataGrid.getResults();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			for (TScIcJhstockbillViewEntity entity : result) {
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
	 * 删除采购换货单
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScIcJhstockbillEntity tScIcJhstockbill, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScIcJhstockbill = systemService.getEntity(TScIcJhstockbillEntity.class, tScIcJhstockbill.getId());
		String message = "采购换货单删除成功";
		try{
			//供应商
			if(StringUtils.isNotEmpty(tScIcJhstockbill.getItemId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_DEL_TYPE);
				countEntity.setOldId(tScIcJhstockbill.getItemId());
				boolean updateIsSuccess = supplierServiceI.updateSupplierCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料供应商引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//经办人
			if(StringUtils.isNotEmpty(tScIcJhstockbill.getEmpId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_DEL_TYPE);
				countEntity.setOldId(tScIcJhstockbill.getEmpId());
				boolean updateIsSuccess = empServiceI.updateEmpCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料职员引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//部门
			if(StringUtils.isNotEmpty(tScIcJhstockbill.getDeptId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_DEL_TYPE);
				countEntity.setOldId(tScIcJhstockbill.getDeptId());
				boolean updateIsSuccess = departmentServiceI.updateDepartmentCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料部门引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//结算账户
			if(StringUtils.isNotEmpty(tScIcJhstockbill.getAccountId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_DEL_TYPE);
				countEntity.setOldId(tScIcJhstockbill.getAccountId());
				boolean updateIsSuccess = settleacctServiceI.updateOrganizationCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料结算账户引用次数失败";
					j.setMsg(message);
					return j;
				}
			}

			List<TScIcJhstockbillentry1Entity> tScIcJhstockbillentry1List = tScIcJhstockbillService.findHql("from TScIcJhstockbillentry1Entity where fid = ?",new Object[]{tScIcJhstockbill.getId()});
			List<TScIcJhstockbillentry2Entity> tScIcJhstockbillentry2List = tScIcJhstockbillService.findHql("from TScIcJhstockbillentry2Entity where fid = ?",new Object[]{tScIcJhstockbill.getId()});
			for(TScIcJhstockbillentry1Entity entry : tScIcJhstockbillentry1List){
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
			for(TScIcJhstockbillentry2Entity entry : tScIcJhstockbillentry2List){
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
			tScIcJhstockbillService.delMain(tScIcJhstockbill);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			//删除待审核预警数据
			systemService.delBillAuditStatus(tScIcJhstockbill.getTranType(),tScIcJhstockbill.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "采购换货单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除采购换货单
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "采购换货单删除成功";
		try{
			for(String id:ids.split(",")){
				TScIcJhstockbillEntity tScIcJhstockbill = systemService.getEntity(TScIcJhstockbillEntity.class,
				id
				);
				tScIcJhstockbillService.delMain(tScIcJhstockbill);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "采购换货单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加采购换货单
	 *
	 * @param tScIcJhstockbill
	 * @param tScIcJhstockbillPage
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScIcJhstockbillEntity tScIcJhstockbill,TScIcJhstockbillPage tScIcJhstockbillPage, HttpServletRequest request) {
		List<TScIcJhstockbillentry1Entity> tScIcJhstockbillentry1List =  tScIcJhstockbillPage.getTScIcJhstockbillentry1List();
		List<TScIcJhstockbillentry2Entity> tScIcJhstockbillentry2List =  tScIcJhstockbillPage.getTScIcJhstockbillentry2List();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		//校验单据编号唯一性
		Boolean hasBillNo = systemService.checkBillNo(TScOrderEntity.class.getAnnotation(Table.class).name(),tScIcJhstockbill.getBillNo(),tScIcJhstockbill.getId());
		if(!hasBillNo){
			j.setSuccess(false);
			j.setMsg("单据编号已存在,请确认");
			return j;
		}
		try{
			if(null != tScIcJhstockbill.getCurPayAmount()){
				tScIcJhstockbill.setCheckAmount(tScIcJhstockbill.getCurPayAmount());
			}
			tScIcJhstockbillService.addMain(tScIcJhstockbill, tScIcJhstockbillentry1List,tScIcJhstockbillentry2List);
			//供应商
			if (org.apache.commons.lang.StringUtils.isNotEmpty(tScIcJhstockbill.getItemId())) {
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_ADD_TYPE);
				countEntity.setOldId(tScIcJhstockbill.getItemId());
				boolean updateIsSuccess = supplierServiceI.updateSupplierCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料供应商引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//经办人
			if(org.apache.commons.lang.StringUtils.isNotEmpty(tScIcJhstockbill.getEmpId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_ADD_TYPE);
				countEntity.setOldId(tScIcJhstockbill.getEmpId());
				boolean updateIsSuccess = empServiceI.updateEmpCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料职员引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//部门
			if(org.apache.commons.lang.StringUtils.isNotEmpty(tScIcJhstockbill.getDeptId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_ADD_TYPE);
				countEntity.setOldId(tScIcJhstockbill.getDeptId());
				boolean updateIsSuccess = departmentServiceI.updateDepartmentCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料职员引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//结算账户
			if(StringUtils.isNotEmpty(tScIcJhstockbill.getAccountId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_ADD_TYPE);
				countEntity.setOldId(tScIcJhstockbill.getAccountId());
				boolean updateIsSuccess = settleacctServiceI.updateOrganizationCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料结算账户引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			for(TScIcJhstockbillentry1Entity entry : tScIcJhstockbillentry1List){
				//商品
				if(org.apache.commons.lang.StringUtils.isNotEmpty(entry.getItemId())){
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
				if(org.apache.commons.lang.StringUtils.isNotEmpty(entry.getStockId())){
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
			for(TScIcJhstockbillentry2Entity entry : tScIcJhstockbillentry2List){
				//商品
				if(org.apache.commons.lang.StringUtils.isNotEmpty(entry.getItemId())){
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
				if(org.apache.commons.lang.StringUtils.isNotEmpty(entry.getStockId())){
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
			systemService.saveBillAuditStatus(tScIcJhstockbill.getTranType().toString(), tScIcJhstockbill.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "采购换货单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新采购换货单
	 *
	 * @param tScIcJhstockbill
	 * @param tScIcJhstockbillPage
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScIcJhstockbillEntity tScIcJhstockbill,TScIcJhstockbillPage tScIcJhstockbillPage, HttpServletRequest request) {
		List<TScIcJhstockbillentry1Entity> tScIcJhstockbillentry1List =  tScIcJhstockbillPage.getTScIcJhstockbillentry1List();
		List<TScIcJhstockbillentry2Entity> tScIcJhstockbillentry2List =  tScIcJhstockbillPage.getTScIcJhstockbillentry2List();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		//校验单据编号唯一性
		Boolean hasBillNo = systemService.checkBillNo(TScOrderEntity.class.getAnnotation(Table.class).name(),tScIcJhstockbill.getBillNo(),tScIcJhstockbill.getId());
		if(!hasBillNo){
			j.setSuccess(false);
			j.setMsg("单据编号已存在,请确认");
			return j;
		}
		try{
			TScIcJhstockbillEntity oldEntity = systemService.getEntity(TScIcJhstockbillEntity.class, tScIcJhstockbill.getId());
			//供应商
			if (StringUtils.isNotEmpty(tScIcJhstockbill.getItemId()) && !oldEntity.getItemId().equals(tScIcJhstockbill.getItemId())) {
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_UPDATE_TYPE);
				countEntity.setOldId(oldEntity.getItemId());
				countEntity.setNewId(tScIcJhstockbill.getItemId());
				boolean updateIsSuccess = supplierServiceI.updateSupplierCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料供应商引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//经办人
			if(StringUtils.isNotEmpty(tScIcJhstockbill.getEmpId()) && !oldEntity.getEmpId().equals(tScIcJhstockbill.getEmpId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_UPDATE_TYPE);
				countEntity.setOldId(oldEntity.getEmpId());
				countEntity.setNewId(tScIcJhstockbill.getEmpId());
				boolean updateIsSuccess = empServiceI.updateEmpCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料职员引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//部门
			if(StringUtils.isNotEmpty(tScIcJhstockbill.getDeptId()) && !oldEntity.getDeptId().equals(tScIcJhstockbill.getDeptId())){
				TScCountEntity countEntity = new TScCountEntity();
				countEntity.setType(Globals.COUNT_UPDATE_TYPE);
				countEntity.setOldId(oldEntity.getDeptId());
				countEntity.setNewId(tScIcJhstockbill.getDeptId());
				boolean updateIsSuccess = departmentServiceI.updateDepartmentCount(countEntity);
				if (updateIsSuccess == false) {
					message = "更新基础资料职员引用次数失败";
					j.setMsg(message);
					return j;
				}
			}
			//结算账户
			if (StringUtils.isNotEmpty(tScIcJhstockbill.getAccountId()) && !oldEntity.getAccountId().equals(tScIcJhstockbill.getAccountId())) {
				TScCountEntity countEntity = new TScCountEntity();
				if (StringUtils.isNotEmpty(oldEntity.getAccountId()) && !oldEntity.getAccountId().equals(oldEntity.getAccountId())) {
					countEntity.setType(Globals.COUNT_UPDATE_TYPE);
					countEntity.setOldId(oldEntity.getAccountId());
					countEntity.setNewId(tScIcJhstockbill.getAccountId());
					boolean updateIsSuccess = settleacctServiceI.updateOrganizationCount(countEntity);
					if (updateIsSuccess == false) {
						message = "更新基础资料结算账户引用次数失败";
						j.setMsg(message);
						return j;
					}
				} else if (StringUtils.isEmpty(oldEntity.getAccountId())) {
					countEntity.setType(Globals.COUNT_ADD_TYPE);
					countEntity.setOldId(tScIcJhstockbill.getAccountId());
					boolean updateIsSuccess = settleacctServiceI.updateOrganizationCount(countEntity);
					if (updateIsSuccess == false) {
						message = "更新基础资料结算账户引用次数失败";
						j.setMsg(message);
						return j;
					}
				}
			} else if (StringUtils.isNotEmpty(oldEntity.getAccountId())) {
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
			//退货明细数据
			for(TScIcJhstockbillentry1Entity entry : tScIcJhstockbillentry1List){
				TScIcJhstockbillentry1Entity oldEntry = null;
				if(StringUtils.isNotEmpty(entry.getId())) {
					oldEntry = tScIcitemServiceI.getEntity(TScIcJhstockbillentry1Entity.class, entry.getId());
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
						if (org.apache.commons.lang.StringUtils.isNotEmpty(oldEntry.getStockId()) && !oldEntry.getStockId().equals(entry.getStockId())) {
							countEntity.setType(Globals.COUNT_UPDATE_TYPE);
							countEntity.setOldId(oldEntry.getStockId());
							countEntity.setNewId(entry.getStockId());
							boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
							if (updateIsSuccess == false) {
								message = "更新基础资料仓库引用次数失败";
								j.setMsg(message);
								return j;
							}
						} else if (org.apache.commons.lang.StringUtils.isEmpty(oldEntry.getStockId())) {
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
			//换货明细数据
			for(TScIcJhstockbillentry2Entity entry : tScIcJhstockbillentry2List){
				TScIcJhstockbillentry2Entity oldEntry = null;
				if(StringUtils.isNotEmpty(entry.getId())) {
					oldEntry = tScIcitemServiceI.getEntity(TScIcJhstockbillentry2Entity.class, entry.getId());
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
						if (org.apache.commons.lang.StringUtils.isNotEmpty(oldEntry.getStockId()) && !oldEntry.getStockId().equals(entry.getStockId())) {
							countEntity.setType(Globals.COUNT_UPDATE_TYPE);
							countEntity.setOldId(oldEntry.getStockId());
							countEntity.setNewId(entry.getStockId());
							boolean updateIsSuccess = stockServiceI.updateStockCount(countEntity);
							if (updateIsSuccess == false) {
								message = "更新基础资料仓库引用次数失败";
								j.setMsg(message);
								return j;
							}
						} else if (org.apache.commons.lang.StringUtils.isEmpty(oldEntry.getStockId())) {
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
			if(null != tScIcJhstockbill.getCurPayAmount()){
				//变更金额
				BigDecimal changeAmount = BigDecimal.valueOf(oldEntity.getCheckAmount()).subtract(BigDecimal.valueOf(tScIcJhstockbill.getCurPayAmount()));
				BigDecimal checkAmount = BigDecimal.valueOf(oldEntity.getCheckAmount()).subtract(changeAmount);
				tScIcJhstockbill.setCheckAmount(checkAmount.doubleValue());
			}else{
				tScIcJhstockbill.setCheckAmount(0.0);
			}
			tScIcJhstockbillService.updateMain(tScIcJhstockbill, tScIcJhstockbillentry1List,tScIcJhstockbillentry2List);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新采购换货单失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 采购换货单新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScIcJhstockbillEntity tScIcJhstockbill, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScIcJhstockbill.getId())) {
			tScIcJhstockbill = tScIcJhstockbillService.getEntity(TScIcJhstockbillEntity.class, tScIcJhstockbill.getId());
			req.setAttribute("tScIcJhstockbillPage", tScIcJhstockbill);
		}
		String billNo = BillNoGenerate.getBillNo("51");
		req.setAttribute("billNo", billNo);
		//从系统参数中获取默认税率
		String defaultTaxRate = "";
		List<TSConfig> tsConfigList = this.tScIcJhstockbillService.findByProperty(TSConfig.class,"code","CFG_TAX_RATE");
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
		req.setAttribute("date",sdf.format(new Date()));
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
		return new ModelAndView("com/qihang/buss/sc/sales/tScIcJhstockbill-add");
	}

	/**
	 * 采购换货单编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScIcJhstockbillEntity tScIcJhstockbill, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScIcJhstockbill.getId())) {
			String load = req.getParameter("load");
			tScIcJhstockbill = tScIcJhstockbillService.getEntity(TScIcJhstockbillEntity.class, tScIcJhstockbill.getId());
			TScDepartmentEntity dept = systemService.getEntity(TScDepartmentEntity.class,tScIcJhstockbill.getDeptId());
			TScSupplierEntity supplier = systemService.getEntity(TScSupplierEntity.class,tScIcJhstockbill.getItemId());
			TSUser user = systemService.getEntity(TSUser.class,tScIcJhstockbill.getBillerId());
			TScEmpEntity emp = systemService.getEntity(TScEmpEntity.class,tScIcJhstockbill.getEmpId());
			if(StringUtils.isNotEmpty(tScIcJhstockbill.getAccountId())) {
				TScSettleacctEntity account = systemService.getEntity(TScSettleacctEntity.class, tScIcJhstockbill.getAccountId());
				if(null != account){
					tScIcJhstockbill.setAccountName(account.getName());
				}
			}
			if(null != dept) {
				tScIcJhstockbill.setDeptName(dept.getName());
			}
			if(null != supplier){
				tScIcJhstockbill.setItemName(supplier.getName());
			}
			if(null != user){
				tScIcJhstockbill.setBillerName(user.getRealName());
			}
			if(null != emp){
				tScIcJhstockbill.setEmpName(emp.getName());
			}
			TSDepart sonEntity = systemService.getEntity(TSDepart.class,tScIcJhstockbill.getSonId());
			if(null != sonEntity){
				tScIcJhstockbill.setSonName(sonEntity.getDepartname());
			}
			String tranType = tScIcJhstockbill.getTranType();
			if(StringUtils.isNotEmpty(tranType)) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				List<TSAuditRelationEntity> info = systemService.getAuditInfoList(tScIcJhstockbill.getId(), tranType);
				String auditor = "";
				String auditDate = "";
				for (int i = 0; i < info.size(); i++) {
					TSAuditRelationEntity entity = info.get(i);
//					if (1 == entity.getIsFinish()) {
//						tScIcJhstockbill.setCheckState(Globals.AUDIT_FIN);
//					}
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
			req.setAttribute("tScIcJhstockbillPage", tScIcJhstockbill);
			//从系统参数中获取默认税率
			String defaultTaxRate = "";
			List<TSConfig> tsConfigList = this.tScIcJhstockbillService.findByProperty(TSConfig.class, "code", "CFG_TAX_RATE");
			if(tsConfigList.size()>0){
				defaultTaxRate = tsConfigList.get(0).getContents();
			}
			if(StringUtils.isNotEmpty(defaultTaxRate)) {
				req.setAttribute("taxRate", defaultTaxRate);
			}else{
				req.setAttribute("taxRate",0);
			}
			//设置期间
			Date checkDate = AccountUtil.getAccountStartDate();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String dateStr = sdf.format(checkDate);
			req.setAttribute("checkDate",dateStr);
			//是否允许负库存出库
			if(AccountUtil.isMinusInventorySI()) {
				req.setAttribute("isCheckNegative",false);
			}else{
				req.setAttribute("isCheckNegative",true);
			}
		}
		return new ModelAndView("com/qihang/buss/sc/sales/tScIcJhstockbill-update");
	}

  /**
  * 导入功能跳转
  *
  * @return
  */
  @RequestMapping(params = "upload")
  public ModelAndView upload(HttpServletRequest req) {
  req.setAttribute("controller_name", "tScIcJhstockbillController");
  return new ModelAndView("common/upload/pub_excel_upload");
  }

  /**
  * 导出excel
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXls")
  public String exportXls(TScIcJhstockbillExcelEntity tScIcJhstockbill,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  CriteriaQuery cq = new CriteriaQuery(TScIcJhstockbillExcelEntity.class, dataGrid);
  com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScIcJhstockbill, request.getParameterMap());
  List<TScIcJhstockbillExcelEntity> tScIcJhstockbills = this.tScIcJhstockbillService.getListByCriteriaQuery(cq,false);
  String tranType = request.getParameter("tranType");
	  String query_date_begin = request.getParameter("date_begin");
	  String query_date_end = request.getParameter("date_end");
	  String itemName  = request.getParameter("itemName");
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
	  List<TScIcJhstockbillExcelEntity> tScIcXsstockbills = this.tScIcJhstockbillService.getListByCriteriaQuery(cq, false);
	  TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
	  TSDepart depart1 = systemService.getParentSonInfo(depart);
	  Set<String> departIds = systemService.getAllSonId(depart1.getId());
	  CriteriaQuery deptCq = new CriteriaQuery(TSDepart.class, dataGrid);
	  deptCq.in("id", departIds.toArray());
	  List<TSDepart> departList = this.tScIcJhstockbillService.getListByCriteriaQuery(deptCq,false);
	  List<TScIcJhstockbillExcelEntity> result = new ArrayList<TScIcJhstockbillExcelEntity>();
	  for (TScIcJhstockbillExcelEntity entity : tScIcXsstockbills) {
		  if(StringUtils.isNotEmpty(itemName)) {
			  String hql = "from TScIcJhstockbillentry2ExcelEntity where entryToMain.id = ? ";
			  hql += " and icitemEntity.name like '%" + itemName + "%'";
			  hql += " order by findex asc";
			  List<TScIcJhstockbillentry2ExcelEntity> entryList = this.tScIcJhstockbillService.findHql(hql, new Object[]{entity.getId()});
			  if (entryList.size() > 0) {
				  entity.setMainToEntry2(entryList);
				  result.add(entity);
			  }
		  } else {
			  result.add(entity);
		  }
	  }
  if(StringUtils.isNotEmpty(tranType)) {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	  for (TScIcJhstockbillExcelEntity entity : result) {
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
  modelMap.put(NormalExcelConstants.FILE_NAME,"采购换货单");
  modelMap.put(NormalExcelConstants.CLASS,TScIcJhstockbillExcelEntity.class);
  modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("采购换货单列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
  public String exportXlsByT(TScIcJhstockbillEntity tScIcJhstockbill,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  modelMap.put(TemplateExcelConstants.FILE_NAME, "采购换货单");
  modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
  modelMap.put(TemplateExcelConstants.MAP_DATA,null);
  modelMap.put(TemplateExcelConstants.CLASS,TScIcJhstockbillEntity.class);
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
    ExcelImportResult importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(),TScIcJhstockbillExcelEntity.class,params);
    List<TScIcJhstockbillExcelEntity> listTScIcJhstockbillEntitys = importResult.getList();
    StringBuilder stringBuilder = new StringBuilder("已存在的数据:");
    boolean flag = false;
    if(!importResult.isVerfiyFail()) {
      for (TScIcJhstockbillExcelEntity tScIcJhstockbill : listTScIcJhstockbillEntitys) {
      //以下检查导入数据是否重复的语句可视需求启用
        //Long count = tScIcJhstockbillService.getCountForJdbc("这里放检查数据是否重复的SQL语句");
        //if(count >0) {
          //flag = true;
          //stringBuilder.append(tScIcJhstockbill.getId()+",");
        //} else {
          tScIcJhstockbillService.save(tScIcJhstockbill);
        //}
      }
      j.setMsg("文件导入成功！");
      //j.setMsg("文件导入成功！" + (flag? stringBuilder.toString():""));
    }else {
      String excelPath = "/upload/excelUpload/TScIcJhstockbillEntity/"+importResult.getExcelName();
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
	 * 加载明细列表[退货信息表]
	 *
	 * @return
	 */
	@RequestMapping(params = "tScIcJhstockbillentry1List")
	public ModelAndView tScIcJhstockbillentry1List(TScIcJhstockbillEntity tScIcJhstockbill, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id0 = tScIcJhstockbill.getId();
		//===================================================================================
		//查询-退货信息表
	    String hql0 = "from TScIcJhstockbillentry1Entity where 1 = 1 AND fID = ? order by findex";
	    try{
	    	List<TScIcJhstockbillentry1Entity> tScIcJhstockbillentry1EntityList = systemService.findHql(hql0,id0);
			List<TSBillInfoEntity> billInfoEntityList = systemService.findHql("from TSBillInfoEntity");
			Map<String,Object> billInfo = new HashMap<String, Object>();
			if(billInfoEntityList.size() > 0){
				for(TSBillInfoEntity bill : billInfoEntityList){
					billInfo.put(bill.getBillId(),bill.getBillName());
				}
			}
			for(TScIcJhstockbillentry1Entity tScIcJhstockbillentry1:tScIcJhstockbillentry1EntityList){
				//商品
				if(StringUtils.isNotEmpty(tScIcJhstockbillentry1.getItemId())){
					TScIcitemEntity icitemEntity = systemService.get(TScIcitemEntity.class,tScIcJhstockbillentry1.getItemId());
					if(icitemEntity != null){
						tScIcJhstockbillentry1.setItemNo(icitemEntity.getNumber());
						tScIcJhstockbillentry1.setItemName(icitemEntity.getName());
						tScIcJhstockbillentry1.setModel(icitemEntity.getModel());
						tScIcJhstockbillentry1.setIsKFPeriod(icitemEntity.getIskfPeriod());
						tScIcJhstockbillentry1.setBatchManager(icitemEntity.getBatchManager());
						List<TScItemPriceEntity> unitList = systemService.findHql("from TScItemPriceEntity where priceToIcItem.id = ? ",new Object[]{icitemEntity.getId()});
						for(TScItemPriceEntity unit : unitList){
							if("0001".equals(unit.getUnitType())){
								tScIcJhstockbillentry1.setBasicCoefficient(Double.parseDouble(unit.getCoefficient().toString()));
							}
							//单位对应的条码
							if(StringUtils.isNotEmpty(tScIcJhstockbillentry1.getUnitId()) && tScIcJhstockbillentry1.getUnitId().equals(unit.getId())){
								if(StringUtils.isNotEmpty(unit.getBarCode())) {
									tScIcJhstockbillentry1.setBarCode(unit.getBarCode());
								}
								if(null != unit.getXsLimitPrice() && BigDecimal.ZERO != unit.getXsLimitPrice()){
									tScIcJhstockbillentry1.setXsLimitPrice(Double.parseDouble(unit.getCgLimitPrice().toString()));
								}else{
									tScIcJhstockbillentry1.setXsLimitPrice(0.0);
								}
							}
						}
					}
				}

				if(StringUtils.isNotEmpty(tScIcJhstockbillentry1.getStockId())){
					TScStockEntity stockEntity  = systemService.get(TScStockEntity.class,tScIcJhstockbillentry1.getStockId());
					tScIcJhstockbillentry1.setStockName(stockEntity.getName());
				}
				if(org.apache.commons.lang.StringUtils.isNotEmpty(tScIcJhstockbillentry1.getClassIDSrc())){
					String classIDName = (String) billInfo.get(tScIcJhstockbillentry1.getClassIDSrc());
					tScIcJhstockbillentry1.setClassIDName(classIDName);
				}
			}
			req.setAttribute("tScIcJhstockbillentry1List", tScIcJhstockbillentry1EntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		String load = req.getParameter("load");
		req.setAttribute("load",load);
		return new ModelAndView("com/qihang/buss/sc/sales/tScIcJhstockbillentry1List");
	}
	/**
	 * 加载明细列表[换货信息表]
	 *
	 * @return
	 */
	@RequestMapping(params = "tScIcJhstockbillentry2List")
	public ModelAndView tScIcJhstockbillentry2List(TScIcJhstockbillEntity tScIcJhstockbill, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id1 = tScIcJhstockbill.getId();
		//===================================================================================
		//查询-换货信息表
	    String hql1 = "from TScIcJhstockbillentry2Entity where 1 = 1 AND fID = ? order by findex";
	    try{
	    	List<TScIcJhstockbillentry2Entity> tScIcJhstockbillentry2EntityList = systemService.findHql(hql1,id1);
			for(TScIcJhstockbillentry2Entity tScIcJhstockbillentry2 : tScIcJhstockbillentry2EntityList){
				//商品
				if(StringUtils.isNotEmpty(tScIcJhstockbillentry2.getItemId())){
					TScIcitemEntity icitemEntity = systemService.get(TScIcitemEntity.class,tScIcJhstockbillentry2.getItemId());
					if(icitemEntity != null){
							tScIcJhstockbillentry2.setItemNo(icitemEntity.getNumber());
							tScIcJhstockbillentry2.setItemName(icitemEntity.getName());
							tScIcJhstockbillentry2.setModel(icitemEntity.getModel());
						    tScIcJhstockbillentry2.setIsKFPeriod(icitemEntity.getIskfPeriod());
						    tScIcJhstockbillentry2.setBatchManager(icitemEntity.getBatchManager());
							List<TScItemPriceEntity> unitList = systemService.findHql("from TScItemPriceEntity where priceToIcItem.id = ? ",new Object[]{icitemEntity.getId()});
							for(TScItemPriceEntity unit : unitList){
								if("0001".equals(unit.getUnitType())){
									tScIcJhstockbillentry2.setBasicCoefficient(Double.parseDouble(unit.getCoefficient().toString()));
								}
								//单位对应的条码
								if(StringUtils.isNotEmpty(tScIcJhstockbillentry2.getUnitId()) && tScIcJhstockbillentry2.getUnitId().equals(unit.getId())){
									if(StringUtils.isNotEmpty(unit.getBarCode())) {
										tScIcJhstockbillentry2.setBarCode(unit.getBarCode());
									}
									if(null != unit.getXsLimitPrice() && BigDecimal.ZERO != unit.getXsLimitPrice()){
										tScIcJhstockbillentry2.setXsLimitPrice(Double.parseDouble(unit.getCgLimitPrice().toString()));
									}else{
										tScIcJhstockbillentry2.setXsLimitPrice(0.0);
									}
								}
							}
					}
				}
//				//单位对应的条码
//				if(StringUtils.isNotEmpty(tScIcJhstockbillentry2.getUnitId())){
//					TScItemPriceEntity itemPriceEntity = systemService.get(TScItemPriceEntity.class,tScIcJhstockbillentry2.getUnitId());
//					if(itemPriceEntity != null){
//						if(StringUtils.isNotEmpty(itemPriceEntity.getBarCode())) {
//							tScIcJhstockbillentry2.setBarCode(itemPriceEntity.getBarCode());
//						}
//					}
//				}
				if(StringUtils.isNotEmpty(tScIcJhstockbillentry2.getStockId())){
					TScStockEntity stockEntity  = systemService.get(TScStockEntity.class,tScIcJhstockbillentry2.getStockId());
					tScIcJhstockbillentry2.setStockName(stockEntity.getName());
				}
			}
			req.setAttribute("tScIcJhstockbillentry2List", tScIcJhstockbillentry2EntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		String load = req.getParameter("load");
		req.setAttribute("load",load);
		return new ModelAndView("com/qihang/buss/sc/sales/tScIcJhstockbillentry2List");
	}

	//作废功能
	@RequestMapping(params = "cancelBill")
	@ResponseBody
	public AjaxJson cancelBill(String ids){
		AjaxJson j = tScIcJhstockbillService.cancelBill(ids);
//		if(j.isSuccess()) {
//			//校验订单是否自动关闭
//			String[] idList = ids.split(",");
//			Set<String> mainId = new HashSet<String>();
//			for (String id : idList) {
//				List<TScPoStockbillentryEntity> entryList = tScIcJhstockbillService.findHql("from TScPoStockbillentryEntity where fid = ?", new Object[]{id});
//				for (TScPoStockbillentryEntity entry : entryList) {
//					if (StringUtils.isNotEmpty(entry.getIdOrder())) {
//						mainId.add(entry.getIdOrder());
//					}
//				}
//			}
//			if (mainId.size() > 0) {
//				scPoOrderService.checkAutoFlag(mainId);
//			}
//		}
		return j;
	}

	//反作废
	@RequestMapping(params = "enableBill")
	@ResponseBody
	public AjaxJson enableBill(String ids){
		AjaxJson j = tScIcJhstockbillService.enableBill(ids);
//		if(j.isSuccess()) {
//			//校验订单是否自动关闭
//			String[] idList = ids.split(",");
//			Set<String> mainId = new HashSet<String>();
//			for (String id : idList) {
//				List<TScPoStockbillentryEntity> entryList = tScPoStockbillService.findHql("from TScPoStockbillentryEntity where fid = ?", new Object[]{id});
//				for (TScPoStockbillentryEntity entry : entryList) {
//					if (StringUtils.isNotEmpty(entry.getIdOrder())) {
//						mainId.add(entry.getIdOrder());
//					}
//				}
//			}
//			if (mainId.size() > 0) {
//				scPoOrderService.checkAutoFlag(mainId);
//			}
//		}
		return j;
	}

	@RequestMapping(params = "afterAudit")
	@ResponseBody
	public AjaxJson afterAudit(String id,Integer audit){
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		AjaxJson j = tScIcJhstockbillService.afterAudit(id,audit,depart.getId());
		return j;
	}


}
