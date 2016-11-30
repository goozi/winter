
package com.qihang.buss.sc.sales.controller;

import com.qihang.buss.sc.baseinfo.entity.*;
import com.qihang.buss.sc.baseinfo.service.TScOrganizationServiceI;
import com.qihang.buss.sc.sales.entity.*;
import com.qihang.buss.sc.sales.service.TScOrderServiceI;
import com.qihang.buss.sc.sales.page.TScOrderPage;
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
import com.qihang.winter.web.system.manager.ClientManager;
import com.qihang.winter.web.system.pojo.base.TSBaseUser;
import com.qihang.winter.web.system.pojo.base.TSConfig;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.web.system.service.UserService;
import org.apache.batik.dom.svg12.Global;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
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

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Controller
 * @Description: 销售订单
 * @date 2016-06-16 11:35:43
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScOrderController")
public class TScOrderController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TScOrderController.class);

    @Autowired
    private TScOrderServiceI tScOrderService;
    @Autowired
    private TScOrganizationServiceI tScOrganizationService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private UserService userService;


    /**
     * 销售订单列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "tScOrder")
    public ModelAndView tScOrder(HttpServletRequest request) {
//		return new ModelAndView("com/qihang/buss/sc/sales/tScOrderList");
        Date checkDate = AccountUtil.getAccountStartDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(checkDate);
        request.setAttribute("checkDate",dateStr);
        String isWarm = request.getParameter("isWarm");
        request.setAttribute("isWarm",isWarm);
        return new ModelAndView("com/qihang/buss/sc/sales/tScOrderViewList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TScViewOrderSelectEntity tScOrder, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        //汇总应付金额，已付金额，数量，金额，折后金额
        dataGrid.setFooter("allAmount,checkAmount,qty,taxAmountEx,inTaxAmount,aweight,weight");
        CriteriaQuery cq = new CriteriaQuery(TScViewOrderSelectEntity.class, dataGrid);
        //cq.addOrder("date",SortDirection.desc);
        //cq.addOrder("billNo",SortDirection.desc);
        //cq.addOrder("id",SortDirection.desc);
        //cq.addOrder("entryId",SortDirection.asc);
        //查询条件组装器
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScOrder);
        try {
            //自定义追加查询条件
            String query_date_begin = request.getParameter("date_begin");
            String query_date_end = request.getParameter("date_end");
            if(StringUtil.isNotEmpty(query_date_begin)){
                cq.ge("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_begin));
            }
            if(StringUtil.isNotEmpty(query_date_end)){
                cq.le("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_end));
            }
            //预警数据
            String isWarm = request.getParameter("isWarm");
            if(StringUtils.isNotEmpty(isWarm)){
                if("1".equals(isWarm)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date beginDate = new Date();
                    String poWarmDate = "0";
                    List<TSConfig> tsConfigList = systemService.findByProperty(TSConfig.class, "code", "CFG_SALORDEARWAR_DAYS");//采购订单预警天数
                    if (tsConfigList.size() > 0) {
                        poWarmDate = tsConfigList.get(0).getContents();
                    }
                    Calendar date = Calendar.getInstance();
                    date.setTime(beginDate);
                    date.set(Calendar.DATE, date.get(Calendar.DATE) + Integer.parseInt(poWarmDate));
                    Date endDate = sdf.parse(sdf.format(date.getTime()));
                    cq.le("fetchDate", endDate);
                    cq.eq("cancellation", "0");
                    cq.eq("closed", "0");
                    cq.eq("autoFlag", "0");
                } else {
                    String userId = ResourceUtil.getSessionUserName().getId();
                    TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
                    TSDepart depart = systemService.getParentSonInfo(sonInfo);
                    Boolean isAudit = userService.isAllowAudit(tScOrder.getTranType(),userId,depart.getId());
                    cq.eq("cancellation","0");
                    //判断当前用户是否在多级审核队列中
                    if(isAudit){
                        Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId,depart.getId(),tScOrder.getTranType());
                        if(userAuditLeave.size() > 0){
                            String leaves = "";
                            for(Integer leave : userAuditLeave){
                                leaves += leave+",";
                            }
                            leaves = leaves.substring(0,leaves.length()-1);
                            List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in ("+leaves+")",new Object[]{depart.getId(),tScOrder.getTranType()});
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
//            cq.addOrder("cancellation",SortDirection.asc);
//            cq.addOrder("billNo", SortDirection.desc);
//            cq.addOrder("date", SortDirection.desc);
//            cq.addOrder("indexNumber", SortDirection.asc);
            List<String> orderInfo = new ArrayList<String>();
            orderInfo.add("cancellation@asc");
            orderInfo.add("date@desc");
            orderInfo.add("billNo@desc");
            orderInfo.add("indexNumber@asc");
            cq.setOrder(orderInfo);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tScOrderService.getDataGridReturn(cq, true);
        List<TScViewOrderSelectEntity> result = dataGrid.getResults();
        for(TScViewOrderSelectEntity resultInfo : result) {
            BigDecimal countAmount = BillCountUtil.getReceivableCountByCustomerId(resultInfo.getItemid());//已执行金额
            BigDecimal allAmount = resultInfo.getAllamount() == null ? BigDecimal.ZERO : resultInfo.getAllamount();//应付金额
            BigDecimal creditamount = (resultInfo.getCreditamount() == null ? BigDecimal.ZERO : resultInfo.getCreditamount());//信用额度
            Integer iscreditmgr = resultInfo.getIscreditmgr() == null ? 0 : 1;//是否信用标记
            if (0 != iscreditmgr) {
                if (!countAmount.equals(BigDecimal.ZERO)) {
                    if (countAmount.compareTo(creditamount) > 0) {
                        resultInfo.setIsAllowAudit("N");
                    } else {
                        resultInfo.setIsAllowAudit("Y");
                    }
                } else {
                    if (allAmount.compareTo(creditamount) > 0) {
                        resultInfo.setIsAllowAudit("N");
                    } else {
                        resultInfo.setIsAllowAudit("Y");
                    }
                }
            } else {
                resultInfo.setIsAllowAudit("Y");
            }
            if(StringUtils.isNotEmpty(resultInfo.getClassidSrc())){
                resultInfo.setClassIdName("销售报价单");
            }
        }

//        String tranType = request.getParameter("tranType");
//        if(StringUtils.isNotEmpty(tranType)){
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//            for(TScViewOrderSelectEntity entity : result) {
//                TSAuditRelationEntity info  = systemService.getAuditInfo(entity.getId(), tranType);
//                if(info != null){
//                    if(1 == info.getIsFinish()){
//                        entity.setStatus(Globals.AUDIT_FIN);
//                        entity.setAuditorName(info.getAuditorName());
//                        entity.setAuditDate(sdf.format(info.getAuditDate()));
//                    }else{
//                        entity.setStatus(Globals.AUDIT_IN);
//                        entity.setAuditorName(info.getAuditorName());
//                        entity.setAuditDate(sdf.format(info.getAuditDate()));
//                    }
//                }else {
//                    entity.setStatus(Globals.AUDIT_NO);
//                }
//            }
//            dataGrid.setResults(result);
//        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除销售订单
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TScOrderEntity tScOrder, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tScOrder = systemService.getEntity(TScOrderEntity.class, tScOrder.getId());
        String message = "销售订单删除成功";
        try {
//            if (StringUtils.isNotEmpty(tScOrder.getItemID())) {
//                TScCountEntity countEntity = new TScCountEntity();
//                countEntity.setType(Globals.COUNT_DEL_TYPE);
//                countEntity.setOldId(tScOrder.getItemID());
//                boolean updateIsSuccess = tScOrganizationService.updateOrganizationCount(countEntity);
//                if (updateIsSuccess == false) {
//                    message = "更新基础资料客户引用次数失败";
//                    j.setMsg(message);
//                    return j;
//                }
//            }
            tScOrderService.delMain(tScOrder);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            //删除待审核预警数据
            systemService.delBillAuditStatus(tScOrder.getTranType().toString(), tScOrder.getId());
        } catch (Exception e) {
            e.printStackTrace();
            message = "销售订单删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除销售订单
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "销售订单删除成功";
        try {
            for (String id : ids.split(",")) {
                TScOrderEntity tScOrder = systemService.getEntity(TScOrderEntity.class,
                        id
                );
                tScOrderService.delMain(tScOrder);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "销售订单删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加销售订单
     *
     * @param tScOrder
     * @param tScOrderPage
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TScOrderEntity tScOrder, TScOrderPage tScOrderPage, HttpServletRequest request) {
        List<TScOrderentryEntity> tScOrderentryList = tScOrderPage.getTScOrderentryList();
        AjaxJson j = new AjaxJson();
        String message = "添加成功";
        //校验单据编号唯一性
        Boolean hasBillNo = systemService.checkBillNo(TScOrderEntity.class.getAnnotation(Table.class).name(),tScOrder.getBillNo(),tScOrder.getId());
        if(!hasBillNo){
            j.setSuccess(false);
            j.setMsg("单据编号已存在,请确认");
            return j;
        }
        try {
            if(StringUtils.isNotEmpty(tScOrder.getItemID())){
//                BigDecimal historyAllAmount = BillCountUtil.getReceivableCountByCustomerId(tScOrder.getItemID());//历史的应收金额
//                BigDecimal nowAllAmount = new BigDecimal(0);
//                if(historyAllAmount ==null){
//                    historyAllAmount = new BigDecimal(0);
//                }
//                if(tScOrder.getAllAmount() != null){
//                    nowAllAmount = tScOrder.getAllAmount();
//                }
//                TScOrganizationEntity entity = systemService.get(TScOrganizationEntity.class, tScOrder.getItemID());
//                if(entity.getIscreditmgr()!=null){
//                    if(Globals.ISCREDITMGRYES.equals(entity.getIscreditmgr())){
//                        BigDecimal creditamount = entity.getCreditamount();
//                        if(creditamount == null){
//                            creditamount = new BigDecimal(0);
//                        }
//                        if((historyAllAmount.add(nowAllAmount)).compareTo(creditamount)==1){
//                            message = "客户的信用额度低";
//                            j.setSuccess(false);
//                            j.setMsg(message);
//                            return j;
//
//                        }
//                   }
//                }
            }
            tScOrder.setTranType(Globals.SC_ORDER_TRANTYPE);
            tScOrder.setClosed(Globals.CLOSE_NO);//关闭标记
            tScOrder.setAutoFlag(Globals.CLOSE_NO);//自动关闭标记
            tScOrder.setCancellation(Globals.CLOSE_NO);//作废标记
            tScOrder.setBillerID(ResourceUtil.getSessionUserName().getId());//制单人
            tScOrder.setCheckAmount(BigDecimal.ZERO);
            tScOrderService.addMain(tScOrder, tScOrderentryList);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            //待审核数据提醒操作
            systemService.saveBillAuditStatus(tScOrder.getTranType().toString(), tScOrder.getId());
        } catch (Exception e) {
            e.printStackTrace();
            message = "销售订单添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新销售订单
     *
     * @param tScOrder
     * @param tScOrderPage
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TScOrderEntity tScOrder, TScOrderPage tScOrderPage, HttpServletRequest request) {
        List<TScOrderentryEntity> tScOrderentryList = tScOrderPage.getTScOrderentryList();
        AjaxJson j = new AjaxJson();
        String message = "更新成功";
        try {
//            if(StringUtils.isNotEmpty(tScOrder.getItemID())){
//                BigDecimal historyAllAmount = BillCountUtil.getReceivableCountByCustomerId(tScOrder.getItemID());//历史的应收金额
//                BigDecimal nowAllAmount = new BigDecimal(0);
//                if(historyAllAmount ==null){
//                    historyAllAmount = new BigDecimal(0);
//                }
//                if(tScOrder.getAllAmount() != null){
//                    nowAllAmount = tScOrder.getAllAmount();
//                }
//                TScOrganizationEntity entity = systemService.get(TScOrganizationEntity.class, tScOrder.getItemID());
//                if(entity.getIscreditmgr()!=null){
//                    if(Globals.ISCREDITMGRYES.equals(entity.getIscreditmgr())){
//                        BigDecimal creditamount = entity.getCreditamount();
//                        if(creditamount == null){
//                            creditamount = new BigDecimal(0);
//                        }
//                        if((historyAllAmount.add(nowAllAmount)).compareTo(creditamount)==1){
//                            message = "客户的信用额度低";
//                            j.setSuccess(false);
//                            j.setMsg(message);
//                            return j;
//
//                        }
//                    }
//                }
//            }
            //校验单据编号唯一性
            Boolean hasBillNo = systemService.checkBillNo(TScOrderEntity.class.getAnnotation(Table.class).name(),tScOrder.getBillNo(),tScOrder.getId());
            if(!hasBillNo){
                j.setSuccess(false);
                j.setMsg("单据编号已存在,请确认");
                return j;
            }
            tScOrderService.updateMain(tScOrder, tScOrderentryList);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "更新销售订单失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 销售订单新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TScOrderEntity tScOrder, HttpServletRequest req) {
        /*if (StringUtil.isNotEmpty(tScOrder.getId())) {
			tScOrder = tScOrderService.getEntity(TScOrderEntity.class, tScOrder.getId());
			req.setAttribute("tScOrderPage", tScOrder);
		}*/
        tScOrder.setDate(new Date());
        tScOrder.setBillNo(BillNoGenerate.getBillNo(Globals.SC_ORDER_TRANTYPE + ""));
        req.setAttribute("tScOrderPage", tScOrder);
        //当前用户所在分支机构
        TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
        TSDepart depart = systemService.getParentSonInfo(sonInfo);
        req.setAttribute("sonId",depart.getId());
        req.setAttribute("sonName",depart.getDepartname());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        req.setAttribute("date", sdf.format(new Date()));
        Date checkDate = AccountUtil.getAccountStartDate();
        String dateStr = sdf.format(checkDate);
        req.setAttribute("checkDate",dateStr);
        return new ModelAndView("com/qihang/buss/sc/sales/tScOrder-add");
    }

    /**
     * 销售订单编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TScOrderEntity tScOrder, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tScOrder.getId())) {
            String load = req.getParameter("load");
            String pageType =tScOrder.getPageType();//变更还是编辑
            tScOrder = tScOrderService.getEntity(TScOrderEntity.class, tScOrder.getId());
        //    BigDecimal mall = tScOrder.getMall();
            Integer mall = tScOrder.getMall();
            if (null != mall){
                if(tScOrder.getMall() == 1){
                    req.setAttribute("mallName","系统录入");
                }else{
                    req.setAttribute("mallName","商城订单");
                }
            } else {
                tScOrder.setMall(1);
                req.setAttribute("mallName","系统录入");
            }
            tScOrder.setPageType(pageType);
            //客户
            if(StringUtils.isNotEmpty(tScOrder.getItemID())){
                TScOrganizationEntity  entity = systemService.get(TScOrganizationEntity.class, tScOrder.getItemID());
                if(entity != null){
                    if(StringUtils.isNotEmpty(entity.getName())){
                        tScOrder.setItemName(entity.getName());
                        tScOrder.setItemID(entity.getId());
                        req.setAttribute("iscreditmgr", entity.getIscreditmgr());
                        req.setAttribute("creditamount",entity.getCreditamount());
                        BigDecimal countAmount = BillCountUtil.getReceivableCountByCustomerId(entity.getId());
                        if(!countAmount.equals(BigDecimal.ZERO)){
                            BigDecimal allAmount = tScOrder.getAllAmount();
                            req.setAttribute("countAmount",countAmount.subtract(allAmount));//编辑时，已执行金额恢复为保存前的金额
                        }else{
                            req.setAttribute("countAmount",countAmount);
                        }
                    }
                }
             }
            //经办人
            if(StringUtils.isNotEmpty(tScOrder.getEmpID())){
                TScEmpEntity  entity = systemService.get(TScEmpEntity.class, tScOrder.getEmpID());
                if(entity != null){
                    if(StringUtils.isNotEmpty(entity.getName())){
                        tScOrder.setEmpName(entity.getName());
                        tScOrder.setEmpID(entity.getId());
                    }
                }
            }
            //部门
            if(StringUtils.isNotEmpty(tScOrder.getDeptID())){
                TScDepartmentEntity entity = systemService.get(TScDepartmentEntity.class,tScOrder.getDeptID());
                if(entity != null){
                    if(StringUtils.isNotEmpty(entity.getName())){
                        tScOrder.setDeptName(entity.getName());
                        tScOrder.setDeptID(entity.getId());
                    }
                }
            }
            //仓库
            if(StringUtils.isNotEmpty(tScOrder.getStockID())){
                TScStockEntity entity = systemService.get(TScStockEntity.class,tScOrder.getStockID());
                if(entity != null){
                    if(StringUtils.isNotEmpty(entity.getName())){
                        tScOrder.setStockName(entity.getName());
                        tScOrder.setStockID(entity.getId());
                    }
                }
            }
            //分支机构
            if(StringUtils.isNotEmpty(tScOrder.getSonID())){
                TSDepart depart = systemService.get(TSDepart.class,tScOrder.getSonID());
                if(null != depart){
                    tScOrder.setSonName(depart.getDepartname());
                }
            }
            //制单人
            if(StringUtils.isNotEmpty(tScOrder.getBillerID())){
                TSBaseUser user = systemService.get(TSBaseUser.class,tScOrder.getBillerID());
                if(null != user){
                    tScOrder.setBillerName(user.getRealName());
                }
            }
            //审核人和审核日期
                List<TSAuditRelationEntity> info = systemService.getAuditInfoList(tScOrder.getId(), String.valueOf(tScOrder.getTranType()));
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
            req.setAttribute("tScOrderPage", tScOrder);
            req.setAttribute("load",load);//屏蔽掉按钮
        }
        Date checkDate = AccountUtil.getAccountStartDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(checkDate);
        req.setAttribute("checkDate",dateStr);
        return new ModelAndView("com/qihang/buss/sc/sales/tScOrder-update");
    }

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "tScOrderController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TScOrderExcelEntity tScOrder, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TScOrderExcelEntity.class, dataGrid);
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScOrder, request.getParameterMap());
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
        List<TScOrderExcelEntity> tScOrders = this.tScOrderService.getListByCriteriaQuery(cq, false);
        TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
        TSDepart depart1 = systemService.getParentSonInfo(depart);
        Set<String> departIds = systemService.getAllSonId(depart1.getId());
        CriteriaQuery deptCq = new CriteriaQuery(TSDepart.class, dataGrid);
        deptCq.in("id", departIds.toArray());
        List<TSDepart> departList = this.tScOrderService.getListByCriteriaQuery(deptCq,false);
        List<TScOrderExcelEntity> result = new ArrayList<TScOrderExcelEntity>();
        for (TScOrderExcelEntity entity : tScOrders) {
            if(StringUtils.isNotEmpty(itemName)) {
                String hql = "from TScOrderentryExcelEntity where entryToMain.id = ? ";
                hql += " and icitemEntity.name like '%" + itemName + "%' order by indexNumber asc";
                List<TScOrderentryExcelEntity> entryList = this.tScOrderService.findHql(hql, new Object[]{entity.getId()});
                if (entryList.size() > 0) {
                    entity.setListOrder(entryList);
                    result.add(entity);
                }
            } else {
                result.add(entity);
            }
        }
        String tranType = Globals.SC_ORDER_TRANTYPE+"";
        if(StringUtils.isNotEmpty(tranType)) {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (TScOrderExcelEntity entity : result) {
                TSAuditRelationEntity info = systemService.getAuditInfo(entity.getId(), tranType);
                if (info != null) {
                    if (1 == info.getIsFinish()) {
                        entity.setCheckState(Globals.AUDIT_FIN + "");
                        entity.setCheckerID(info.getAuditorName());
                        entity.setCheckDate(info.getAuditDate());
                    } else {
                        entity.setCheckState(Globals.AUDIT_IN+"");
                        entity.setCheckerID(info.getAuditorName());
                        entity.setCheckDate(info.getAuditDate());
                    }
                } else {
                    entity.setCheckState(Globals.AUDIT_NO + "");
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
        String[] exclusions = {"排除列名1", "排除列名2"};
        modelMap.put(NormalExcelConstants.FILE_NAME, "销售订单");
        modelMap.put(NormalExcelConstants.CLASS, TScOrderExcelEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("销售订单列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息", exclusions));
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
    public String exportXlsByT(TScOrderEntity tScOrder, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "销售订单");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TScOrderEntity.class);
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
                ExcelImportResult importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(), TScOrderEntity.class, params);
                List<TScOrderEntity> listTScOrderEntitys = importResult.getList();
                StringBuilder stringBuilder = new StringBuilder("已存在的数据:");
                boolean flag = false;
                if (!importResult.isVerfiyFail()) {
                    for (TScOrderEntity tScOrder : listTScOrderEntitys) {
                        //以下检查导入数据是否重复的语句可视需求启用
                        //Long count = tScOrderService.getCountForJdbc("这里放检查数据是否重复的SQL语句");
                        //if(count >0) {
                        //flag = true;
                        //stringBuilder.append(tScOrder.getId()+",");
                        //} else {
                        tScOrderService.save(tScOrder);
                        //}
                    }
                    j.setMsg("文件导入成功！");
                    //j.setMsg("文件导入成功！" + (flag? stringBuilder.toString():""));
                } else {
                    String excelPath = "/upload/excelUpload/TScOrderEntity/" + importResult.getExcelName();
                    j.setMsg("文件导入校验失败，详见<a href='" + excelPath + "' target='_blank' style='color:blue;font-weight:bold;'>要显示的导入文件名</a>");
                }
            } catch (Exception e) {
                j.setMsg("文件导入失败！");
                logger.error(ExceptionUtil.getExceptionMessage(e));
            } finally {
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
     * 加载明细列表[销售订单从表]
     *
     * @return
     */
    @RequestMapping(params = "tScOrderentryList")
    public ModelAndView tScOrderentryList(TScOrderEntity tScOrder,@RequestParam(value = "pageType",required = false)Integer pageType, HttpServletRequest req) {

        //===================================================================================
        //获取参数
        Object id0 = tScOrder.getId();
        //===================================================================================
        //查询-销售订单从表
        String hql0 = "from TScOrderentryEntity where 1 = 1 AND oRDERID = ? order by indexNumber";
        try {
            List<TScOrderentryEntity> tScOrderentryEntityList = systemService.findHql(hql0, id0);
            List<TSBillInfoEntity> billInfoEntityList = systemService.findHql("from TSBillInfoEntity");
            Map<String,String> billInfo = new HashMap<String, String>();
            for(TSBillInfoEntity infoEntity : billInfoEntityList){
                billInfo.put(infoEntity.getBillId(),infoEntity.getBillName());
            }
            for(TScOrderentryEntity orderentryEntity:tScOrderentryEntityList){
                  //商品
                 if(StringUtils.isNotEmpty(orderentryEntity.getItemID())){
                     TScIcitemEntity icitemEntity = systemService.get(TScIcitemEntity.class,orderentryEntity.getItemID());
                     if(icitemEntity != null){
                         if(StringUtils.isNotEmpty(icitemEntity.getNumber())){
                             orderentryEntity.setItemNumber(icitemEntity.getNumber());
                         }
                         if(StringUtils.isNotEmpty(icitemEntity.getName())){
                             orderentryEntity.setItemName(icitemEntity.getName());
                         }
                         if(StringUtils.isNotEmpty(icitemEntity.getModel())){
                             orderentryEntity.setItemModel(icitemEntity.getModel());
                         }
                         if(icitemEntity.getWeight()!=null){
                             orderentryEntity.setItemWeight(String.valueOf(icitemEntity.getWeight()));
                         }
                     }
                 }
                //单位对应的条码
                if(StringUtils.isNotEmpty(orderentryEntity.getUnitID())){
                    TScItemPriceEntity itemPriceEntity = systemService.get(TScItemPriceEntity.class,orderentryEntity.getUnitID());
                    if(itemPriceEntity != null){
                        if(StringUtils.isNotEmpty(itemPriceEntity.getBarCode())) {
                            orderentryEntity.setItemBarcode(itemPriceEntity.getBarCode());
                        }
                        if(itemPriceEntity.getXsLimitPrice()!=null){
                            orderentryEntity.setXsLimitPrice(itemPriceEntity.getXsLimitPrice());
                        }else{
                            orderentryEntity.setXsLimitPrice(new BigDecimal(0));
                        }
                    }
                }
                if(StringUtils.isNotEmpty(orderentryEntity.getStockID())){
                    TScStockEntity stockEntity  = systemService.get(TScStockEntity.class,orderentryEntity.getStockID());
                    orderentryEntity.setStockName(stockEntity.getName());
                }
                if(StringUtils.isNotEmpty(orderentryEntity.getClassIDSrc())){
                    String tranTypeName = billInfo.get(orderentryEntity.getClassIDSrc());
                    orderentryEntity.setClassIDName(tranTypeName);
                }
                if(null != orderentryEntity.getIsFreeGift()){
                    if(1 == orderentryEntity.getIsFreeGift()){
                        orderentryEntity.setSalesName("买一赠一");
                    }else if (0 == orderentryEntity.getIsFreeGift()){
                        orderentryEntity.setSalesName("调价政策");
                    }
                }
                if(StringUtils.isNotEmpty(orderentryEntity.getClassIDSrc())){
                    orderentryEntity.setClassIDName("销售报价单");
                }
            }
            req.setAttribute("pageType",pageType);
            req.setAttribute("tScOrderentryList", tScOrderentryEntityList);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        String load = req.getParameter("load");
        req.setAttribute("load",load);
        return new ModelAndView("com/qihang/buss/sc/sales/tScOrderentryList");
    }

    /***
     * 关闭和反关闭
     * @param id
     * @param type
     * @return
     */
    @RequestMapping(params = "closeOrderBill")
    @ResponseBody
    public AjaxJson closeOrderBill(@RequestParam(value = "id",required = false)String id,@RequestParam(value = "type",required = false)String type){
        AjaxJson j = new AjaxJson();
        String message ="";
        if("1".equals(type)) {
             message = "销售订单关闭成功";
        }else if("2".equals(type)){
            message = "销售订单反关闭成功";
        }
        try {
            tScOrderService.closeOrderBill(id, type);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            if("1".equals(type)) {
                message = "销售订单关闭成功失败";
            }else if("2".equals(type)){
                message = "销售订单反关闭成功失败";
            }
            j.setSuccess(false);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;

    }

    /***
     * 作废和反作废
     * @param id
     * @param type
     * @return
     */
    @RequestMapping(params="cancelBill")
    @ResponseBody
    public AjaxJson cancelBill(@RequestParam(value = "id",required = false)String id,@RequestParam(value = "type",required = false)String type){
        AjaxJson j = new AjaxJson();
        String message ="";
        if("1".equals(type)) {
            message = "销售订单作废成功";
        }else if("2".equals(type)){
            message = "销售订单反作废成功";
        }
        try {
            tScOrderService.updateCancelBill(id, type);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            if("1".equals(type)) {
                message = "销售订单作废失败";
            }else if("2".equals(type)){
                message = "销售订单反作废失败";
            }
            j.setSuccess(false);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 选单页面条状
     * @param orderEntity
     * @param req
     * @return
     */
    @RequestMapping(params = "goSelectDialog")
    public ModelAndView goSelectDialog(TScOrderEntity orderEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(orderEntity.getItemID())) {
            req.setAttribute("itemId", orderEntity.getItemID());
        }
        req.setAttribute("tranType", orderEntity.getTranType());
        String ids = req.getParameter("ids");
        req.setAttribute("ids", ids);
        String sonId = req.getParameter("sonId");
        req.setAttribute("sonId",sonId);
        return new ModelAndView("com/qihang/buss/sc/sales/tScOrderViewSelect");
    }

    /**
     * 选单列表数据
     * @param tScOrder
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridSelect")
    public void datagridSelect(TScViewOrderSelectEntity tScOrder, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TScViewOrderSelectEntity.class, dataGrid);
        //查询条件组装器
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScOrder);
        try {
            //自定义追加查询条件
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tScOrderService.getDataGridReturn(cq, true);
        String tranType = request.getParameter("tranType");
        if(StringUtils.isNotEmpty(tranType)){
            List<TScViewOrderSelectEntity> result = dataGrid.getResults();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for(TScViewOrderSelectEntity entity : result) {
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
                entity.setTranTypeName("销售订单");
            }
            dataGrid.setResults(result);
        }
        TagUtil.datagrid(response, dataGrid);
    }

    @RequestMapping(params = "loadEntryViewList")
    @ResponseBody
    public List<TScViewOrderSelectEntity> loadEntryViewList(TScViewOrderSelectEntity orderEntity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid){
        CriteriaQuery cq = new CriteriaQuery(TScViewOrderSelectEntity.class, dataGrid);
        //查询条件组装器
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, orderEntity);
        try{
            //自定义追加查询条件
            cq.eq("isStock",0);
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tScOrderService.getDataGridReturn(cq, true);
        List<TScViewOrderSelectEntity> viewList = dataGrid.getResults();
        return viewList;
    }

    /**
     * 收款单-选单页面条状
     * @param orderEntity
     * @param req
     * @return
     */
    @RequestMapping(params = "goSelectMainDialog")
    public ModelAndView goSelectMainDialog(TScOrderEntity orderEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(orderEntity.getItemID())) {
            req.setAttribute("itemId", orderEntity.getItemID());
        }
        req.setAttribute("tranType", orderEntity.getTranType());
        String ids = req.getParameter("ids");
        req.setAttribute("ids",ids);
        String ids2 = req.getParameter("ids2");
        req.setAttribute("ids2",ids2);
        String sonId = req.getParameter("sonId");
        req.setAttribute("sonId",sonId);
        return new ModelAndView("com/qihang/buss/sc/sales/tScOrderList");
    }

    @RequestMapping(params = "datagridMain")
    public void datagridMain(TScOrderViewEntity tScOrder, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TScOrderViewEntity.class, dataGrid);
        //查询条件组装器
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScOrder);
        String ids = request.getParameter("ids");
        try {
            //自定义追加查询条件
//            String ids2 = request.getParameter("ids2");
            if(StringUtils.isNotEmpty(ids)) {
                List<String> idInfo = new ArrayList<String>();
                String[] idsList = ids.split(",");
                for(String idStr : idsList){
                    if(StringUtils.isNotEmpty(idStr)) {
                        idInfo.add(idStr);
                    }
                }
//
//                if(StringUtils.isNotEmpty(ids2)){
//                    String[] idsList2 = ids2.split(",");
//                    String queryIds = "";
//                    for(String id2 : idsList2){
//                        queryIds += "'"+id2+"',";
//                    }
//                    queryIds = queryIds.substring(0,queryIds.length()-1);
//                    List<TScSlStockbillEntity> stockbillEntityList = systemService.findHql("from TScSlStockbillEntity where tranType = 103 and id in ("+queryIds+")");
//                    if(stockbillEntityList.size() > 0){
//                        for(TScSlStockbillEntity entity : stockbillEntityList){
//                            if(StringUtils.isNotEmpty(entity.getIdSrc())) {
//                                idInfo.add(entity.getIdSrc());
//                            }
//                        }
//                    }
//                }
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
        this.tScOrderService.getDataGridReturn(cq, true);
        if(StringUtil.isEmpty(ids)) {
            List<String> stockbillInfo = systemService.findListbySql("select distinct id_Src from t_sc_sl_stockbill where trantype = '" + Globals.SC_SL_STOCKBILL_TRANTYPE + "' and checkState = 2 and id_Src <> '' ");
            if (stockbillInfo.size() > 0) {
                List<TScOrderViewEntity> result = dataGrid.getResults();
                for (String orderId : stockbillInfo) {
                    for (TScOrderViewEntity viewEntity : result) {
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
