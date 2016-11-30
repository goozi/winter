package com.qihang.buss.sc.base.controller;

import com.qihang.buss.sc.inventory.entity.TScIcInventoryBatchnoEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryEntity;
import com.qihang.buss.sc.sales.entity.*;
import com.qihang.buss.sc.sales.service.TScOrderServiceI;
import com.qihang.buss.sc.sales.service.TScPoOrderServiceI;
import com.qihang.buss.sc.sysaudit.entity.*;
import com.qihang.buss.sc.util.AccountUtil;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.ResourceUtil;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.web.system.pojo.base.TSConfig;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.service.SystemService;
import org.apache.batik.dom.svg12.Global;
import org.apache.xpath.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by LenovoM4550 on 2016-08-24.
 */
@Scope("prototype")
@Controller
@RequestMapping("/scBaseController")
public class SCBaseController extends BaseController{
    @Autowired
    private SystemService systemService;

    @Autowired
    private TScPoOrderServiceI scPoOrderService;//采购订单

    @Autowired
    private TScOrderServiceI orderServiceI;//销售订单

    @RequestMapping(params = "opeBill")
    @ResponseBody
    public AjaxJson opeBill(String tableName,String id,Integer value,String field,Integer tranType){
        AjaxJson j = new AjaxJson();
        TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
        TSDepart depart = systemService.getParentSonInfo(sonInfo);
        String updateSql = "update "+tableName+" set "+field+" = "+value+" where id='"+id+"'";
        try {
            if(StringUtil.isNotEmpty(tranType)){
                if("cancellation".equals(field)){
                    //采购入库
                    if(Globals.SC_PO_STOCKBILL_TRANTYPE.equals(tranType)){
                        List<TScPoStockbillentryEntity> poOrderentryEntities = systemService.findHql("from TScPoStockbillentryEntity where fid = ? and entryIdSrc is not null",new Object[]{id});
                        if(poOrderentryEntities.size() > 0) {
                            Boolean isUpdate = true;
                            List<String> updateInfo = new ArrayList<String>();
                            Set<String> mainId = new HashSet<String>();
                            for (TScPoStockbillentryEntity entry : poOrderentryEntities) {
                                if(StringUtil.isNotEmpty(entry.getIdSrc())){
                                    mainId.add(entry.getIdSrc());
                                }
                                String orderEntryId = entry.getEntryIdSrc();
                                Double qty = entry.getQty();
                                String updateQtyStr = "";
                                if(value == 1){
                                    updateQtyStr = "update t_sc_po_orderentry set stockqty = stockqty - "+qty+" where id = '"+orderEntryId+"'";
                                } else {
                                    if(StringUtil.isNotEmpty(orderEntryId)) {
                                        TScPoOrderentryEntity orderentryEntity = systemService.getEntity(TScPoOrderentryEntity.class, orderEntryId);
                                        Double stockQty = orderentryEntity.getStockQty();
                                        Double billQty = orderentryEntity.getQty();
                                        if (billQty < stockQty + qty) {
                                            j.setSuccess(false);
                                            j.setMsg("反作废后执行数量将超出采购订单数量，不可反作废，请确认");
                                            isUpdate = false;
                                            break;
                                            //return j;
                                        } else {
                                            updateQtyStr = "update t_sc_po_orderentry set stockqty = stockqty + " + qty + " where id = '" + orderEntryId + "'";
                                        }
                                    }
                                }
                                if(StringUtil.isNotEmpty(updateQtyStr)) {
                                    updateInfo.add(updateQtyStr);
                                }
                            }
                            if(isUpdate){
                                for(String updateQtyStr : updateInfo) {
                                    systemService.updateBySqlString(updateQtyStr);
                                }
                            }
                            if(mainId.size() > 0) {
                                scPoOrderService.checkAutoFlag(mainId);
                            }
                        }
                    }
                    //采购退货
                    else if(Globals.SC_PO_STOCKBILL_RETURN_TRANTYPE.equals(tranType)){
                        List<TScPoStockbillentryEntity> poOrderentryEntities = systemService.findHql("from TScPoStockbillentryEntity where fid = ? and entryIdSrc is not null",new Object[]{id});
                        if(poOrderentryEntities.size() > 0) {
                            Boolean isUpdate = true;
                            List<String> updateInfo = new ArrayList<String>();
                            for (TScPoStockbillentryEntity entry : poOrderentryEntities) {
                                String orderEntryId = entry.getEntryIdSrc();
                                Double qty = entry.getQty();
                                String updateQtyStr = "";
                                if(value == 1){
                                    updateQtyStr = "update T_SC_Po_StockBillEntry set COMMITQTY = COMMITQTY - "+qty+" where id = '"+orderEntryId+"'";
                                } else {
                                    if(StringUtil.isNotEmpty(orderEntryId)) {
                                        TScPoStockbillentryEntity orderentryEntity = systemService.getEntity(TScPoStockbillentryEntity.class, orderEntryId);
                                        Double stockQty = orderentryEntity.getCommitQty();
                                        Double billQty = orderentryEntity.getQty();
                                        if (billQty < stockQty + qty) {
                                            j.setSuccess(false);
                                            j.setMsg("反作废后退货数量将超出采购入库单数量，不可反作废，请确认");
                                            isUpdate = false;
                                            break;
                                        } else {
                                            updateQtyStr = "update T_SC_Po_StockBillEntry set COMMITQTY = COMMITQTY + " + qty + " where id = '" + orderEntryId + "'";
                                        }
                                    }
                                }
                                if(StringUtil.isNotEmpty(updateQtyStr)) {
                                    updateInfo.add(updateQtyStr);
                                }
                            }
                            if(isUpdate){
                                for(String updateQtyStr : updateInfo) {
                                    systemService.updateBySqlString(updateQtyStr);
                                }
                            }
                        }
                    }
                    //销售出库
                    else if(Globals.SC_SL_STOCKBILL_TRANTYPE.equals(tranType)){
                        List<TScSlStockbillentryEntity> slPoStockBillEntities = systemService.findHql("from TScSlStockbillentryEntity where fid = ? and entryidSrc is not null",new Object[]{id});
                        if(slPoStockBillEntities.size() > 0) {
                            Boolean isUpdate = true;
                            List<String> updateInfo = new ArrayList<String>();
                            Set<String> mainId = new HashSet<String>();
                            for (TScSlStockbillentryEntity entry : slPoStockBillEntities) {
                                if(StringUtil.isNotEmpty(entry.getIdSrc())){
                                    mainId.add(entry.getIdSrc());
                                }
                                String orderEntryId = entry.getEntryidSrc();
                                Double qty = entry.getQty();
                                String updateQtyStr = "";
                                if(value == 1){
                                    updateQtyStr = "update t_sc_order_entry set stockqty = stockqty - "+qty+" where id = '"+orderEntryId+"'";
                                } else {
                                    if(StringUtil.isNotEmpty(orderEntryId)) {
                                        TScOrderentryEntity orderentryEntity = systemService.getEntity(TScOrderentryEntity.class, orderEntryId);
                                        BigDecimal stockQty = orderentryEntity.getStockQty();
                                        BigDecimal billQty = orderentryEntity.getQty();
                                        if (billQty.compareTo(stockQty.add(BigDecimal.valueOf(qty))) < 0) {
                                            j.setSuccess(false);
                                            j.setMsg("反作废后数量将超出销售订单数量，不可反作废，请确认");
                                            isUpdate = false;
                                            break;
                                        } else {
                                            updateQtyStr = "update t_sc_order_entry set stockqty = stockqty + " + qty + " where id = '" + orderEntryId + "'";
                                        }
                                    }
                                }
                                if(StringUtil.isNotEmpty(updateQtyStr)) {
                                    updateInfo.add(updateQtyStr);
                                }
                            }
                            if(isUpdate){
                                for(String updateQtyStr : updateInfo) {
                                    systemService.updateBySqlString(updateQtyStr);
                                }
                            }
                            if(mainId.size() > 0){
                                orderServiceI.checkAutoFlag(mainId);
                            }
                        }
                    }
                    //销售退货
                    else if(Globals.SC_SL_STOCKBILL_RETURN_TRANTYPE.equals(tranType)){
                        List<TScSlStockbillentryEntity> slPoStockBillEntities = systemService.findHql("from TScSlStockbillentryEntity where fid = ? and entryidSrc is not null",new Object[]{id});
                        if(slPoStockBillEntities.size() > 0) {
                            Boolean isUpdate = true;
                            List<String> updateInfo = new ArrayList<String>();
                            for (TScSlStockbillentryEntity entry : slPoStockBillEntities) {
                                String orderEntryId = entry.getEntryidSrc();
                                Double qty = entry.getQty();
                                String updateQtyStr = "";
                                if(value == 1){
                                    updateQtyStr = "update t_sc_sl_stockbillentry set COMMITQTY = COMMITQTY - "+qty+" where id = '"+orderEntryId+"'";
                                } else {
                                    if(StringUtil.isNotEmpty(orderEntryId)) {
                                        TScSlStockbillentryEntity orderentryEntity = systemService.getEntity(TScSlStockbillentryEntity.class, orderEntryId);
                                        Double stockQty = orderentryEntity.getCommitqty();
                                        Double billQty = orderentryEntity.getQty();
                                        if (billQty < stockQty + qty) {
                                            j.setSuccess(false);
                                            j.setMsg("反作废后数量将超出销售出库单数量，不可反作废，请确认");
                                            isUpdate = false;
                                            break;
                                        } else {
                                            updateQtyStr = "update t_sc_sl_stockbillentry set COMMITQTY = COMMITQTY + " + qty + " where id = '" + orderEntryId + "'";
                                        }
                                    }
                                }
                                if(StringUtil.isNotEmpty(updateQtyStr)) {
                                    updateInfo.add(updateQtyStr);
                                }
                            }
                            if(isUpdate){
                                for(String updateQtyStr : updateInfo) {
                                    systemService.updateBySqlString(updateQtyStr);
                                }
                            }
                        }
                    }
                    //采购换货
                    else if(Globals.SC_IC_JHSTOCKBILL_TRANTYPE.equals(tranType)){
                        List<TScIcJhstockbillentry1Entity> slPoStockBillEntities = systemService.findHql("from TScIcJhstockbillentry1Entity where fid = ? and entryIdSrc is not null",new Object[]{id});
                        if(slPoStockBillEntities.size() > 0) {
                            Boolean isUpdate = true;
                            List<String> updateInfo = new ArrayList<String>();
                            for (TScIcJhstockbillentry1Entity entry : slPoStockBillEntities) {
                                String orderEntryId = entry.getEntryIdSrc();
                                Double qty = entry.getQty();
                                String updateQtyStr = "";
                                if(value == 1){
                                    updateQtyStr = "update T_SC_Po_StockBillEntry set COMMITQTY = COMMITQTY - "+qty+" where id = '"+orderEntryId+"'";
                                } else {
                                    if(StringUtil.isNotEmpty(orderEntryId)) {
                                        TScPoStockbillentryEntity orderentryEntity = systemService.getEntity(TScPoStockbillentryEntity.class, orderEntryId);
                                        Double stockQty = orderentryEntity.getCommitQty();
                                        Double billQty = orderentryEntity.getQty();
                                        if (billQty < stockQty + qty) {
                                            j.setSuccess(false);
                                            j.setMsg("反作废后退货数量将超出采购入库单数量，不可反作废，请确认");
                                            isUpdate = false;
                                            break;
                                        } else {
                                            updateQtyStr = "update T_SC_Po_StockBillEntry set COMMITQTY = COMMITQTY + " + qty + " where id = '" + orderEntryId + "'";
                                        }
                                    }
                                }
                                if(StringUtil.isNotEmpty(updateQtyStr)) {
                                    updateInfo.add(updateQtyStr);
                                }
                            }
                            if(isUpdate){
                                for(String updateQtyStr : updateInfo) {
                                    systemService.updateBySqlString(updateQtyStr);
                                }
                            }
                        }
                    }
                    //销售换货
                    else if(Globals.SC_IC_XSSTOCKBILL_TRANTYPE.equals(tranType)){
                        List<TScIcXsstockbillentry1Entity> slPoStockBillEntities = systemService.findHql("from TScIcXsstockbillentry1Entity where fid = ? and entryIdSrc is not null",new Object[]{id});
                        if(slPoStockBillEntities.size() > 0) {
                            Boolean isUpdate = true;
                            List<String> updateInfo = new ArrayList<String>();
                            for (TScIcXsstockbillentry1Entity entry : slPoStockBillEntities) {
                                String orderEntryId = entry.getEntryIdSrc();
                                BigDecimal qty = entry.getQty();
                                String updateQtyStr = "";
                                if(value == 1){
                                    updateQtyStr = "update t_sc_sl_stockbillentry set COMMITQTY = COMMITQTY - "+qty+" where id = '"+orderEntryId+"'";
                                } else {
                                    if(StringUtil.isNotEmpty(orderEntryId)) {
                                        TScSlStockbillentryEntity orderentryEntity = systemService.getEntity(TScSlStockbillentryEntity.class, orderEntryId);
                                        Double stockQty = orderentryEntity.getCommitqty();
                                        Double billQty = orderentryEntity.getQty();
                                        if (billQty < stockQty + qty.doubleValue()) {
                                            j.setSuccess(false);
                                            j.setMsg("反作废后数量将超出销售出库单数量，不可反作废，请确认");
                                            isUpdate = false;
                                            break;
                                        } else {
                                            updateQtyStr = "update t_sc_sl_stockbillentry set COMMITQTY = COMMITQTY + " + qty + " where id = '" + orderEntryId + "'";
                                        }
                                    }
                                }
                                if(StringUtil.isNotEmpty(updateQtyStr)) {
                                    updateInfo.add(updateQtyStr);
                                }
                            }
                            if(isUpdate){
                                for(String updateQtyStr : updateInfo) {
                                    systemService.updateBySqlString(updateQtyStr);
                                }
                            }
                        }
                    }
                }
            }
            if(j.isSuccess()) {
                systemService.updateBySqlString(updateSql);
                //更新审核阶段关联值
                String updateAuditStatusInfo = "update t_sc_bill_audit_status set cancellation="+value+" where tranType='"+tranType+"' and sonId='"+depart.getId()+"' and billId='"+id+"'";
                systemService.updateBySqlString(updateAuditStatusInfo);
            }
        }catch (Exception e){
            j.setSuccess(false);
            j.setMsg(e.getMessage());
        }
        return j;
    }

    /**
     * 获取预警数据
     */
    @RequestMapping(params = "loadWarnTree")
    @ResponseBody
    public List<Map<String,Object>> loadWarnTree(){
        String loginerId = ResourceUtil.getSessionUserName().getId();
        TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
        TSDepart depart = systemService.getParentSonInfo(sonInfo);
        List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
        Date beginDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //订单收货预警表
        Map<String,Object> poOrderMap = new HashMap<String, Object>();
        String poWarmDate = "0";
        List<TSConfig> tsConfigList = systemService.findByProperty(TSConfig.class,"code","CFG_PRUORDEARWAR_DAYS");//采购订单预警天数
        if(tsConfigList.size()>0){
            poWarmDate = tsConfigList.get(0).getContents();
        }
        Calendar date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE,date.get(Calendar.DATE)+Integer.parseInt(poWarmDate));
        String endDate = sdf.format(date.getTime());
        //收货前n天还未完全收货的数据
        List<TScPoOrderViewEntity> poOrderEntityList = systemService.findHql("from TScPoOrderViewEntity where sonId = ? and closed = 0 and autoFlag = 0 and cancellation = 0 and jhDate <= '" + endDate + "'", depart.getId());
        Set<String> poBillIds = new HashSet<String>();
        for(TScPoOrderViewEntity viewEntity : poOrderEntityList){
            poBillIds.add(viewEntity.getId());
        }
        poOrderMap.put("id","po");
        //TODO 添加图标
        poOrderMap.put("iconCls","warm-icon-jhdate");
        poOrderMap.put("text","订单收货预警表("+poBillIds.size()+")");
        result.add(poOrderMap);
        //订单交货预警表
        String slWarmDate = "0";
        tsConfigList = systemService.findByProperty(TSConfig.class,"code","CFG_SALORDEARWAR_DAYS");//销售订单预警天数
        if(tsConfigList.size()>0){
            slWarmDate = tsConfigList.get(0).getContents();
        }
        date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE,date.get(Calendar.DATE)+Integer.parseInt(slWarmDate));
        endDate = sdf.format(date.getTime());
        Map<String,Object> slOrderMap = new HashMap<String, Object>();
        List<TScViewOrderSelectEntity> orderEntityList = systemService.findHql("from TScViewOrderSelectEntity where sonId = ? and closed = 0 and autoFlag = 0 and cancellation = 0 and fetchDate <='"+endDate+"'",depart.getId());
        Set<String> slBillIds = new HashSet<String>();
        for(TScViewOrderSelectEntity orderSelectEntity : orderEntityList){
            slBillIds.add(orderSelectEntity.getId());
        }
        slOrderMap.put("id","sl");
        slOrderMap.put("iconCls","warm-icon-xhdate");
        slOrderMap.put("text", "订单交货预警表(" + slBillIds.size() + ")");
        result.add(slOrderMap);
        //商品保质期到期预警
        String invWarmDate = "0";
        tsConfigList = systemService.findByProperty(TSConfig.class,"code","CFG_SHELFLIFEEARWAR_DAYS");//保质期预警天数
        if(tsConfigList.size()>0){
            invWarmDate = tsConfigList.get(0).getContents();
        }
        date = Calendar.getInstance();
        date.setTime(beginDate);
        date.set(Calendar.DATE, date.get(Calendar.DATE) + Integer.parseInt(invWarmDate));
        endDate = sdf.format(date.getTime());
        List<TScIcInventoryBatchnoEntity> inventoryBatchnoEntityList = systemService.findHql("from TScIcInventoryBatchnoEntity where periodDate <= '"+endDate+"' and basicQty > 0");
        Map<String,Object> inventoryMap = new HashMap<String, Object>();
        inventoryMap.put("id","inv");
        inventoryMap.put("iconCls","warm-icon-kfdate");
        inventoryMap.put("text","商品保质期到期预警("+inventoryBatchnoEntityList.size()+")");
        result.add(inventoryMap);

        //待审核单据
        Map<String,Object> billMap = new HashMap<String, Object>();
        billMap.put("id","bill");
        //billMap.put("text","待审核单据");
        billMap.put("iconCls","warm-icon-audit");
        List<Map<String,Object>> childInfo = new ArrayList<Map<String, Object>>();
        //获取需要多级审核单据类型
        List<TSAuditEntity> tsAuditEntityList = systemService.findHql("from TSAuditEntity where isAudit = 1 and sonId = ? order by billId",depart.getId());
        Boolean isRed = false;
        Integer allWarmNum = 0;
        if(tsAuditEntityList.size() > 0) {
            for(TSAuditEntity auditEntity : tsAuditEntityList) {
                List<TScBillAuditStatusEntity> tScBillAuditStatusEntityList = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType = ? and cancellation = 0", new Object[]{depart.getId(), auditEntity.getBillId()});
                List<TSAuditLeaveEntity> leaveEntityList = systemService.findHql("from TSAuditLeaveEntity where pid=?",auditEntity.getId());
                Integer warmRows = 0;//预警条数
                for(TScBillAuditStatusEntity tScBillAuditStatusEntity : tScBillAuditStatusEntityList) {
                    Integer status = tScBillAuditStatusEntity.getStatus();//当前审核状态
                    for (TSAuditLeaveEntity leaveEntity : leaveEntityList) {
                        String leaveStatus = leaveEntity.getLeaveNum();
                        if(leaveStatus.equals(status.toString())) {
                            String userId = leaveEntity.getAuditorId();
                            if (userId.indexOf(loginerId) > -1) {
                                warmRows++;
                            }
                        }
                    }
                }
                TSBillInfoEntity billInfoEntity = systemService.findUniqueByProperty(TSBillInfoEntity.class,"billId",auditEntity.getBillId());
                if(null != billInfoEntity) {
                    String text = "";
                    if(warmRows > 0){
                        text =  billInfoEntity.getBillName() + "(<span style='color:red'>" + warmRows + "</span>)";
                        isRed = true;
                        allWarmNum += warmRows;
                    }else {
                        text = billInfoEntity.getBillName() + "(" + warmRows + ")";
                    }
                    Map<String, Object> order = new HashMap<String, Object>();
                    order.put("id", auditEntity.getBillId());
                    order.put("text", text);
                    order.put("iconCls","null");
                    childInfo.add(order);
                }
            }
            billMap.put("children", childInfo);
        }
        if(isRed){
            billMap.put("text","待审核单据(<span style='color:red'>" + allWarmNum + "</span>)");
            billMap.put("state","closed");
        } else {
            billMap.put("text","待审核单据(0)");
        }
        result.add(billMap);
        return result;
    }

    @RequestMapping(params = "warmPage")
    public ModelAndView warmPage(HttpServletRequest request){
        String path = "";
        String tranType = request.getParameter("tranType");
        if(tranType.equals(Globals.SC_QUOTE_TRANTYPE)){//销售报价单
            path = "com/qihang/buss/sc/sales/tScQuoteViewList";
        } else if(tranType.equals(Globals.SC_ORDER_TRANTYPE.toString())){//销售订单
            path = "com/qihang/buss/sc/sales/tScOrderViewList";
        }else if(tranType.equals(Globals.SC_SL_STOCKBILL_TRANTYPE.toString())){//销售出库
            path = "com/qihang/buss/sc/sales/tScSlStockbillList";
        }else if(tranType.equals(Globals.SC_SL_STOCKBILL_RETURN_TRANTYPE.toString())){//销售退货
            path = "com/qihang/buss/sc/sales/tScSlStockbillReturnList";
        }else if(tranType.equals(Globals.SC_IC_XSSTOCKBILL_TRANTYPE.toString())){//销售换货
            path = "com/qihang/buss/sc/sales/tScIcXsstockbillList";
        }else if(tranType.equals(Globals.SC_APTITUDE_TRANTYPE.toString())){//经销商资格审查
            path = "com/qihang/buss/sc/distribution/tScAptitudeList";
        }else if(tranType.equals(Globals.SC_PRCPLY_TRANTYPE.toString())){//经销商调价政策
            path = "com/qihang/buss/sc/distribution/tScPrcplyList";
        }else if(tranType.equals(Globals.SC_PROMOTION_TRANTYPE.toString())){//买一赠一
            path = "com/qihang/buss/sc/distribution/tScPromotionViewList";
        }else if(tranType.equals(Globals.SC_DRP_ORDER_TRANTYPE.toString())){//经销商订单管理
            path = "com/qihang/buss/sc/distribution/tScDrpOrderList";
        }else if(tranType.equals(Globals.SC_DRP_ORDER_STOCKBILL_TRANTYPE.toString())){//经销商发货管理
            path = "com/qihang/buss/sc/distribution/tScDrpStockbillList";
        }else if(tranType.equals(Globals.SC_DRP_ORDER_RSTOCKBILL_TRANTYPE.toString())){//经销商退货管理
            path = "com/qihang/buss/sc/distribution/tScDrpRstockbillViewList";
        }else if(tranType.equals(Globals.SC_IC_INITIAL_TRANTYPE.toString())){//存货初始化
            path = "com/qihang/buss/sc/init/tScIcInitialList";
        }else if(tranType.equals(Globals.SC_IC_BEGDATA_TRANTYPE.toString())){//应收初始化
            path = "com/qihang/buss/sc/init/tScBegdataList";
        }else if(tranType.equals(Globals.SC_IC_BEGDATAPAYABLE_TRANTYPE.toString())){//应付初始化
            path = "com/qihang/buss/sc/init/tScBegdatapayableList";
        }else if(tranType.equals(Globals.SC_IC_BEGDATAINCOMEPAY_TRANTYPE.toString())){//收支初始化
            path = "com/qihang/buss/sc/init/tScBegdataincomepayList";
        }else if(tranType.equals(Globals.SC_RP_BANKBILL_TRANTYPE.toString())){//银行存取款
            path = "com/qihang/buss/sc/financing/tScRpBankbillList";
        }else if(tranType.equals(Globals.SC_RP_BANKBILL_RECON.toString())){//资金调账
            path = "com/qihang/buss/sc/financing/tScRpAdjustbillList";
        }else if(tranType.equals(Globals.SC_RP_BANKBILL_OTHER.toString())){//资金其他收入
            path = "com/qihang/buss/sc/financing/tScRpRotherbillList";
        }else if(tranType.equals(Globals.SC_RP_BANKBILL_EXPENSESAPPLY.toString())){//资金费用申报单
            path = "com/qihang/buss/sc/financing/tScRpExpensesapplyList";
        }else if(tranType.equals(Globals.SC_RP_BANKBILL_POTHER.toString())){//资金费用开支单
            path = "com/qihang/buss/sc/financing/tScRpPotherbillList";
        }else if(tranType.equals(Globals.SC_PO_ORDER_TRANTYPE.toString())){//采购订单
            path = "com/qihang/buss/sc/sales/tScPoOrderList";
        }else if(tranType.equals(Globals.SC_PO_STOCKBILL_TRANTYPE.toString())){//采购入库单
            path = "com/qihang/buss/sc/sales/tScPoStockbillList";
        }else if(tranType.equals(Globals.SC_PO_STOCKBILL_RETURN_TRANTYPE.toString())){//采购退货单
            path = "com/qihang/buss/sc/sales/tScPoStockbillReturnList";
        }else if(tranType.equals(Globals.SC_IC_JHSTOCKBILL_TRANTYPE.toString())){//采购换货
            path = "com/qihang/buss/sc/sales/tScIcJhstockbillList";
        }else if(tranType.equals(Globals.SC_RP_PBILL.toString())){//应付调账
            request.setAttribute("amountStr","应付");
            request.setAttribute("itemTitle","供应商");
            path = "com/qihang/buss/sc/rp/tScRpAdjustbillList";
        }else if(tranType.equals(Globals.SC_RP_RBILL.toString())){//应收调账
            request.setAttribute("amountStr","应收");
            request.setAttribute("itemTitle","客户");
            path = "com/qihang/buss/sc/rp/tScRpAdjustbillList";
        }else if(tranType.equals(Globals.SC_RP_PBILL_INFO.toString())){//付款单
            path = "com/qihang/buss/sc/rp/tScRpPbillList";
        }else if(tranType.equals(Globals.SC_RP_RBILL_INFO.toString())){//收款单
            path = "com/qihang/buss/sc/rp/tScRpRbillList";
        }else if(tranType.equals(Globals.SC_IC_CHK_STOCKBILL.toString())){//盘点单
            path = "com/qihang/buss/sc/inventory/tScIcChkstockbillList";
        }
        request.setAttribute("tranType",tranType);
        //是否允许负库存出库
        if(AccountUtil.isMinusInventorySI()) {
            request.setAttribute("isCheckNegative",false);
        }else{
            request.setAttribute("isCheckNegative",true);
        }
        request.setAttribute("isWarm","11");
        return new ModelAndView(path);
    }
}