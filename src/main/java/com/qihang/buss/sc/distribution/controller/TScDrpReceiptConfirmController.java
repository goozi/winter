
package com.qihang.buss.sc.distribution.controller;

import com.qihang.buss.sc.baseinfo.entity.TScIcitemEntity;
import com.qihang.buss.sc.baseinfo.entity.TScItemPriceEntity;
import com.qihang.buss.sc.baseinfo.entity.TScOrganizationEntity;
import com.qihang.buss.sc.baseinfo.entity.TScStockEntity;
import com.qihang.buss.sc.distribution.entity.*;
import com.qihang.buss.sc.distribution.page.TScDrpStockbillPage;
import com.qihang.buss.sc.distribution.service.TScDrpStockbillServiceI;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.ResourceUtil;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.web.system.pojo.base.TSBaseUser;
import com.qihang.winter.web.system.pojo.base.TSUser;
import com.qihang.winter.web.system.service.SystemService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


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
@RequestMapping("/tScDrpReceiptConfirmlController")
public class TScDrpReceiptConfirmController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScDrpReceiptConfirmController.class);

	@Autowired
	private TScDrpStockbillServiceI tScDrpStockbillService;
	@Autowired
	private SystemService systemService;

	/**
	 * 发货管理列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tScDrpReceiptConfirmStockbill")
	public ModelAndView tScDrpStockbill(HttpServletRequest request) {
		request.setAttribute("tranType", Globals.SC_DRP_ORDER_STOCKBILL_TRANTYPE);
		return new ModelAndView("com/qihang/buss/sc/distribution/tScDrpReceiptConfirmList");
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
		CriteriaQuery cq = new CriteriaQuery(TSCViewDrpStockbillEntity.class, dataGrid);

		String currentUserId = ResourceUtil.getSessionUserName().getId();
		TScOrganizationEntity scOrganizationEntity = systemService.findUniqueByProperty(TScOrganizationEntity.class, "userId", currentUserId);
		if(null != scOrganizationEntity){
			if(StringUtil.isNotEmpty(scOrganizationEntity.getDealer())){
				String hql = " from TSCViewDrpStockbillEntity where dealerId = ? ";
				List<TSCViewDrpStockbillEntity> tscViewDrpStockbillEntityList =  systemService.findHql(hql, scOrganizationEntity.getDealer());
				tScDrpStockbill.setSonid("000");
				if(!tscViewDrpStockbillEntityList.isEmpty()){//如果集合不为空 就加载相应数据
					cq.eq("dealerId",scOrganizationEntity.getDealer());
					cq.eq("checkState", 2);
				}/*else{
					if(StringUtil.isNotEmpty(scOrganizationEntity.getId())){
						cq.eq("dealerId",scOrganizationEntity.getId());
					}
				}*/
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
			}
			dataGrid.setResults(result);
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 发货确认页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goConfirm")
	public ModelAndView goConfirm(TScDrpStockbillEntity tScDrpStockbill, HttpServletRequest req) {
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
			req.setAttribute("tScDrpStockbillPage", tScDrpStockbill);
		}
		return new ModelAndView("com/qihang/buss/sc/distribution/tScDrpReceiptConfirmStockbill");
	}

	/**
	 * 确认发货
	 *
	 * @param tScDrpStockbill
	 * @param tScDrpStockbillPage
	 * @return
	 */
	@RequestMapping(params = "doConfirm")
	@ResponseBody
	public AjaxJson doConfirm(TScDrpStockbillentryEntity tScDrpStockbill,TScDrpStockbillPage tScDrpStockbillPage, HttpServletRequest request) {
		List<TScDrpStockbillentryEntity> tScDrpStockbillentryList =  tScDrpStockbillPage.getTScDrpStockbillentryList();
		AjaxJson j = new AjaxJson();
		String message = "确认成功";
		try{
			if(!tScDrpStockbillentryList.isEmpty()) {
				if(StringUtil.isNotEmpty(tScDrpStockbillentryList.get(0).getStockQty().doubleValue()) && StringUtil.isNotEmpty(tScDrpStockbillentryList.get(0).getQty())){ //如果确认收货数量和发货数量都不为空 并且 确认收货数量少于等于发货数量
					double stockQty = tScDrpStockbillentryList.get(0).getStockQty().doubleValue();
					double qty = tScDrpStockbillentryList.get(0).getQty();
					if(stockQty>0 && stockQty <= qty) {
						if (StringUtil.isNotEmpty(tScDrpStockbillentryList.get(0).getId())) {//如果id不为空
							TScDrpStockbillentryEntity tScDrpStockbillentryEntity = systemService.get(TScDrpStockbillentryEntity.class, tScDrpStockbillentryList.get(0).getId());
							if (null != tScDrpStockbillentryEntity) {//如果发货单记录不为空 //回抛数据
								tScDrpStockbillentryEntity.setStockQty(BigDecimal.valueOf(stockQty)); //回抛发货数量至发货单
								tScDrpStockbillentryEntity.setQty(qty-stockQty);//并把对应数量减少

								SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
								String affirmDate = sdf.format(new Date());
								if (StringUtil.isNotEmpty(tScDrpStockbillentryList.get(0).getFidOrder()) && StringUtil.isNotEmpty(tScDrpStockbillentryList.get(0).getInterIDSrc())) {//如果订单分录id和源单分录不为空 就回抛数据给订单
									String idOrder = tScDrpStockbillentryList.get(0).getFidOrder();
									TScDrpOrderentryEntity tScDrpOrderentryEntity = systemService.get(TScDrpOrderentryEntity.class, idOrder);
									if (null != tScDrpOrderentryEntity) { //如果记录不为空
										//回抛订单数据
										tScDrpOrderentryEntity.setAffirmQty(BigDecimal.valueOf(tScDrpStockbillentryList.get(0).getStockQty().doubleValue())); //回抛确认收货数量
										tScDrpOrderentryEntity.setAffirmDate(sdf.parse(affirmDate)); //回抛确认收货时间
										systemService.save(tScDrpOrderentryEntity);
									}
								}
								if(StringUtil.isNotEmpty(tScDrpStockbillentryList.get(0).getFid())){ //如果发货单主键ID不为空 查询出该记录 并回抛数据
									TScDrpStockbillEntity tScDrpStockbillEntity = systemService.get(TScDrpStockbillEntity.class, tScDrpStockbillentryList.get(0).getFid());
									if(null != tScDrpStockbillEntity){
										//回抛发货单数据
										tScDrpStockbillEntity.setAffirmStatus(Globals.SCDRPSTOCKBILL_YES); //设为确认状态
										tScDrpStockbillEntity.setAffirmDate(sdf.parse(affirmDate));//回抛确认收货时间
										tScDrpStockbillEntity.setAffirmID(ResourceUtil.getSessionUserName().getId());//回抛确认人
										tScDrpStockbillEntity.setAffirmName(ResourceUtil.getSessionUserName().getRealName());
										systemService.save(tScDrpStockbillEntity);
									}
								}
							}
						}
					}else {
						message = "确认数量不能大于发货数量！";
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "确认收货失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 反确认发货
	 *
	 * @param tScDrpStockbill
	 * @param tScDrpStockbillPage
	 * @return
	 */
	@RequestMapping(params = "doUnConfirm")
	@ResponseBody
	public AjaxJson doUnConfirm(TScDrpStockbillentryEntity tScDrpStockbill,TScDrpStockbillPage tScDrpStockbillPage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "反确认成功";
		try {
			List<TScDrpStockbillEntity> tScDrpStockbillEntityList =  systemService.findHql(" from TScDrpStockbillEntity where affirmStatus = 1 and checkState=2 ");
			List<TScDrpStockbillentryEntity> tScDrpStockbillentryEntityList =  systemService.findHql(" from TScDrpStockbillentryEntity where stockQty is not null");
			if(!tScDrpStockbillentryEntityList.isEmpty() && !tScDrpStockbillEntityList.isEmpty()){//如果有发货单记录 循环获得每条的 数量和确认反货数量 增加库存
				//把所有的发货单的确认状态都改为 未确认 重新设置 确认人 和 确认时间
				for(TScDrpStockbillEntity entity:tScDrpStockbillEntityList){
					entity.setAffirmStatus(Globals.SCDRPSTOCKBILL_NO);
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					String affirmDate = sdf.format(new Date());
					entity.setAffirmDate(sdf.parse(affirmDate));
					entity.setAffirmID(ResourceUtil.getSessionUserName().getId());
					systemService.saveOrUpdate(entity);
				}
				//把发货单的确认数量改为0 并增加数量
				for(TScDrpStockbillentryEntity entity:tScDrpStockbillentryEntityList){
					double qty = entity.getQty();
					double stockQty = entity.getStockQty().doubleValue();
					entity.setStockQty(BigDecimal.valueOf(0));
					entity.setQty(qty+stockQty);
					systemService.saveOrUpdate(entity);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			message = "反确认失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return  j;
	}

	/**
	 * 加载明细列表[商品信息]
	 *
	 * @return
	 */
	@RequestMapping(params = "tScDrpReceiptConfirmStockbillentryList")
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
			}
			req.setAttribute("tScDrpStockbillentryList", tScDrpStockbillentryEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/qihang/buss/sc/distribution/tScDrpReceiptConfirmStockbillentryList");
	}
}
