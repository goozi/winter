
package com.qihang.buss.sc.sales.controller;

import com.alibaba.fastjson.JSONObject;
import com.qihang.buss.sc.baseinfo.entity.*;
import com.qihang.buss.sc.baseinfo.service.*;
import com.qihang.buss.sc.sales.entity.*;
import com.qihang.buss.sc.sales.entity.TScIcXsstockbillEntity;
import com.qihang.buss.sc.sales.service.TScIcXsstockbillServiceI;
import com.qihang.buss.sc.sales.page.TScIcXsstockbillPage;
import com.qihang.buss.sc.sys.service.TScAccountConfigServiceI;
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
 * @author onlineGenerator
 * @version V1.0
 * @Title: Controller
 * @Description: 销售换货单
 * @date 2016-08-11 16:43:33
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScIcXsstockbillController")
public class TScIcXsstockbillController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TScIcXsstockbillController.class);

    @Autowired
    private TScIcXsstockbillServiceI tScIcXsstockbillService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private TScIcitemServiceI tScIcitemServiceI;//商品

    @Autowired
    private TScStockServiceI stockServiceI;//仓库

    @Autowired
    private TScEmpServiceI empServiceI;//经办人

    @Autowired
    private TScDepartmentServiceI departmentServiceI;//部门

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private TScOrganizationServiceI organizationServiceI;//客户

    @Autowired
    private TScSettleacctServiceI settleacctServiceI;//结算账户

    @Autowired
    private UserService userService;


    /**
     * 销售换货单列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "tScIcXsstockbill")
    public ModelAndView tScIcXsstockbill(HttpServletRequest request) {
        TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
        TSDepart depart = systemService.getParentSonInfo(sonInfo);
        List<TSAuditEntity> tsAuditEntityList = systemService.findHql("from TSAuditEntity where billId=? and sonId = ?",new Object[]{"110",depart.getId()});
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
        return new ModelAndView("com/qihang/buss/sc/sales/tScIcXsstockbillList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TScIcXsstockbillViewEntity tScIcXsstockbill, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        //汇总应付金额，已付金额，数量，金额，折后金额
        dataGrid.setFooter("allAmount,checkAmount,qty,taxAmountEx,inTaxAmount");
        CriteriaQuery cq = new CriteriaQuery(TScIcXsstockbillViewEntity.class, dataGrid);
        //查询条件组装器
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScIcXsstockbill);
        try {
            //自定义追加查询条件
            String query_date_begin = request.getParameter("date_begin");
            String query_date_end = request.getParameter("date_end");
            if (StringUtil.isNotEmpty(query_date_begin)) {
                cq.ge("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_begin));
            }
            if (StringUtil.isNotEmpty(query_date_end)) {
                cq.le("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_end));
            }
            cq.addOrder("cancellation", SortDirection.asc);
            String isWarm = request.getParameter("isWarm");
            if(org.apache.commons.lang.StringUtils.isNotEmpty(isWarm)){
                String userId = ResourceUtil.getSessionUserName().getId();
                TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
                TSDepart depart = systemService.getParentSonInfo(sonInfo);
                Boolean isAudit = userService.isAllowAudit(tScIcXsstockbill.getTranType().toString(),userId,depart.getId());
                //判断当前用户是否在多级审核队列中
                if(isAudit){
                    Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId,depart.getId(),tScIcXsstockbill.getTranType().toString());
                    if(userAuditLeave.size() > 0){
                        String leaves = "";
                        for(Integer leave : userAuditLeave){
                            leaves += leave+",";
                        }
                        leaves = leaves.substring(0,leaves.length()-1);
                        List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in ("+leaves+")",new Object[]{depart.getId(),tScIcXsstockbill.getTranType().toString()});
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
            //String sonId = ResourceUtil.getSessionUserName().getCurrentDepart().getId();
            //cq.eq("sonId",sonId);
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tScIcXsstockbillService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除销售换货单
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TScIcXsstockbillEntity tScIcXsstockbill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tScIcXsstockbill = systemService.getEntity(TScIcXsstockbillEntity.class, tScIcXsstockbill.getId());
        String message = "销售换货单删除成功";
        try {
            //客户
            if (StringUtils.isNotEmpty(tScIcXsstockbill.getItemId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_DEL_TYPE);
                countEntity.setOldId(tScIcXsstockbill.getItemId());
                boolean updateIsSuccess = organizationServiceI.updateOrganizationCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料供应商引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            //经办人
            if (StringUtils.isNotEmpty(tScIcXsstockbill.getEmpId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_DEL_TYPE);
                countEntity.setOldId(tScIcXsstockbill.getEmpId());
                boolean updateIsSuccess = empServiceI.updateEmpCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料职员引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            //部门
            if (StringUtils.isNotEmpty(tScIcXsstockbill.getDeptId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_DEL_TYPE);
                countEntity.setOldId(tScIcXsstockbill.getDeptId());
                boolean updateIsSuccess = departmentServiceI.updateDepartmentCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料部门引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            //结算账户
            if (StringUtils.isNotEmpty(tScIcXsstockbill.getAccountID())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_DEL_TYPE);
                countEntity.setOldId(tScIcXsstockbill.getAccountID());
                boolean updateIsSuccess = settleacctServiceI.updateOrganizationCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料结算账户引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            List<TScIcXsstockbillentry1Entity> tScIcXsstockbillentry1List = tScIcXsstockbillService.findHql("from TScIcXsstockbillentry1Entity where fid = ?", new Object[]{tScIcXsstockbill.getId()});
            List<TScIcXsstockbillentry2Entity> tScIcXsstockbillentry2List = tScIcXsstockbillService.findHql("from TScIcXsstockbillentry2Entity where fid = ?", new Object[]{tScIcXsstockbill.getId()});
            for (TScIcXsstockbillentry1Entity entry : tScIcXsstockbillentry1List) {
                //商品
                if (StringUtils.isNotEmpty(entry.getItemId())) {
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
                if (StringUtils.isNotEmpty(entry.getStockId())) {
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
            for (TScIcXsstockbillentry2Entity entry : tScIcXsstockbillentry2List) {
                //商品
                if (StringUtils.isNotEmpty(entry.getItemId())) {
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
                if (StringUtils.isNotEmpty(entry.getStockId())) {
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
            tScIcXsstockbillService.delMain(tScIcXsstockbill);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            //删除待审核预警数据
            systemService.delBillAuditStatus(tScIcXsstockbill.getTranType(), tScIcXsstockbill.getId());
        } catch (Exception e) {
            e.printStackTrace();
            message = "销售换货单删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除销售换货单
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "销售换货单删除成功";
        try {
            for (String id : ids.split(",")) {
                TScIcXsstockbillEntity tScIcXsstockbill = systemService.getEntity(TScIcXsstockbillEntity.class,
                        id
                );
                tScIcXsstockbillService.delMain(tScIcXsstockbill);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "销售换货单删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加销售换货单
     *
     * @param tScIcXsstockbill
     * @param tScIcXsstockbillPage
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TScIcXsstockbillEntity tScIcXsstockbill, TScIcXsstockbillPage tScIcXsstockbillPage, HttpServletRequest request) {
        List<TScIcXsstockbillentry1Entity> tScIcXsstockbillentry1List = tScIcXsstockbillPage.getTScIcXsstockbillentry1List();
        List<TScIcXsstockbillentry2Entity> tScIcXsstockbillentry2List = tScIcXsstockbillPage.getTScIcXsstockbillentry2List();
        AjaxJson j = new AjaxJson();
        String message = "添加成功";
        //校验单据编号唯一性
        Boolean hasBillNo = systemService.checkBillNo(TScOrderEntity.class.getAnnotation(Table.class).name(),tScIcXsstockbill.getBillNo(),tScIcXsstockbill.getId());
        if(!hasBillNo){
            j.setSuccess(false);
            j.setMsg("单据编号已存在,请确认");
            return j;
        }
        try {
            if(null != tScIcXsstockbill.getCurPayAmount()){
                tScIcXsstockbill.setCheckAmount(tScIcXsstockbill.getCurPayAmount());
            }
            tScIcXsstockbillService.addMain(tScIcXsstockbill, tScIcXsstockbillentry1List, tScIcXsstockbillentry2List);
            //供应商
            if (StringUtils.isNotEmpty(tScIcXsstockbill.getItemId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_ADD_TYPE);
                countEntity.setOldId(tScIcXsstockbill.getItemId());
                boolean updateIsSuccess = organizationServiceI.updateOrganizationCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料供应商引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            //经办人
            if (StringUtils.isNotEmpty(tScIcXsstockbill.getEmpId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_ADD_TYPE);
                countEntity.setOldId(tScIcXsstockbill.getEmpId());
                boolean updateIsSuccess = empServiceI.updateEmpCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料职员引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            //部门
            if (StringUtils.isNotEmpty(tScIcXsstockbill.getDeptId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_ADD_TYPE);
                countEntity.setOldId(tScIcXsstockbill.getDeptId());
                boolean updateIsSuccess = departmentServiceI.updateDepartmentCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料职员引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            //结算账户
            if (StringUtils.isNotEmpty(tScIcXsstockbill.getAccountID())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_ADD_TYPE);
                countEntity.setOldId(tScIcXsstockbill.getAccountID());
                boolean updateIsSuccess = settleacctServiceI.updateOrganizationCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料结算账户引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            for (TScIcXsstockbillentry1Entity entry : tScIcXsstockbillentry1List) {
                //商品
                if (StringUtils.isNotEmpty(entry.getItemId())) {
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
                if (StringUtils.isNotEmpty(entry.getStockId())) {
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
            for (TScIcXsstockbillentry2Entity entry : tScIcXsstockbillentry2List) {
                //商品
                if (StringUtils.isNotEmpty(entry.getItemId())) {
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
                if (StringUtils.isNotEmpty(entry.getStockId())) {
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
            //保存物流信息
            String logistical = request.getParameter("json");
            if (org.apache.commons.lang.StringUtils.isNotEmpty(logistical)) {
                TScSlLogisticalEntity logisticalEntity = JSONObject.parseObject(logistical, TScSlLogisticalEntity.class);
                logisticalEntity.setFid(tScIcXsstockbill.getId());
                logisticalEntity.setId(null);
                systemService.saveOrUpdate(logisticalEntity);
            }
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            //待审核数据提醒操作
            systemService.saveBillAuditStatus(tScIcXsstockbill.getTranType().toString(), tScIcXsstockbill.getId());
        } catch (Exception e) {
            e.printStackTrace();
            message = "销售换货单添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新销售换货单
     *
     * @param tScIcXsstockbill
     * @param tScIcXsstockbillPage
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TScIcXsstockbillEntity tScIcXsstockbill, TScIcXsstockbillPage tScIcXsstockbillPage, HttpServletRequest request) {
        List<TScIcXsstockbillentry1Entity> tScIcXsstockbillentry1List = tScIcXsstockbillPage.getTScIcXsstockbillentry1List();
        List<TScIcXsstockbillentry2Entity> tScIcXsstockbillentry2List = tScIcXsstockbillPage.getTScIcXsstockbillentry2List();
        AjaxJson j = new AjaxJson();
        String message = "更新成功";
        //校验单据编号唯一性
        Boolean hasBillNo = systemService.checkBillNo(TScOrderEntity.class.getAnnotation(Table.class).name(),tScIcXsstockbill.getBillNo(),tScIcXsstockbill.getId());
        if(!hasBillNo){
            j.setSuccess(false);
            j.setMsg("单据编号已存在,请确认");
            return j;
        }
        try {
            TScIcXsstockbillEntity oldEntity = systemService.getEntity(TScIcXsstockbillEntity.class, tScIcXsstockbill.getId());
            //客户
            if (StringUtils.isNotEmpty(tScIcXsstockbill.getItemId()) && !oldEntity.getItemId().equals(tScIcXsstockbill.getItemId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_UPDATE_TYPE);
                countEntity.setOldId(oldEntity.getItemId());
                countEntity.setNewId(tScIcXsstockbill.getItemId());
                boolean updateIsSuccess = organizationServiceI.updateOrganizationCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料供应商引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            //经办人
            if (StringUtils.isNotEmpty(tScIcXsstockbill.getEmpId()) && !oldEntity.getEmpId().equals(tScIcXsstockbill.getEmpId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_UPDATE_TYPE);
                countEntity.setOldId(oldEntity.getEmpId());
                countEntity.setNewId(tScIcXsstockbill.getEmpId());
                boolean updateIsSuccess = empServiceI.updateEmpCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料职员引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            //部门
            if (StringUtils.isNotEmpty(tScIcXsstockbill.getDeptId()) && !oldEntity.getDeptId().equals(tScIcXsstockbill.getDeptId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_UPDATE_TYPE);
                countEntity.setOldId(oldEntity.getDeptId());
                countEntity.setNewId(tScIcXsstockbill.getDeptId());
                boolean updateIsSuccess = departmentServiceI.updateDepartmentCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料职员引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            //结算账户
            if (StringUtils.isNotEmpty(tScIcXsstockbill.getAccountID()) && !oldEntity.getAccountID().equals(tScIcXsstockbill.getAccountID())) {
                TScCountEntity countEntity = new TScCountEntity();
                if (StringUtils.isNotEmpty(oldEntity.getAccountID()) && !oldEntity.getAccountID().equals(oldEntity.getAccountID())) {
                    countEntity.setType(Globals.COUNT_UPDATE_TYPE);
                    countEntity.setOldId(oldEntity.getAccountID());
                    countEntity.setNewId(tScIcXsstockbill.getAccountID());
                    boolean updateIsSuccess = settleacctServiceI.updateOrganizationCount(countEntity);
                    if (updateIsSuccess == false) {
                        message = "更新基础资料结算账户引用次数失败";
                        j.setMsg(message);
                        return j;
                    }
                } else if (StringUtils.isEmpty(oldEntity.getAccountID())) {
                    countEntity.setType(Globals.COUNT_ADD_TYPE);
                    countEntity.setOldId(tScIcXsstockbill.getAccountID());
                    boolean updateIsSuccess = settleacctServiceI.updateOrganizationCount(countEntity);
                    if (updateIsSuccess == false) {
                        message = "更新基础资料结算账户引用次数失败";
                        j.setMsg(message);
                        return j;
                    }
                }
            } else if (StringUtils.isNotEmpty(oldEntity.getAccountID())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_DEL_TYPE);
                countEntity.setOldId(oldEntity.getAccountID());
                boolean updateIsSuccess = settleacctServiceI.updateOrganizationCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料结算账户引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            //退货明细数据
            for (TScIcXsstockbillentry1Entity entry : tScIcXsstockbillentry1List) {
                TScIcXsstockbillentry1Entity oldEntry = null;
                if (StringUtils.isNotEmpty(entry.getId())) {
                    oldEntry = tScIcitemServiceI.getEntity(TScIcXsstockbillentry1Entity.class, entry.getId());
                }
                if (null != oldEntry) {
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
                } else {
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
                    if (StringUtils.isNotEmpty(entry.getStockId())) {
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
            for (TScIcXsstockbillentry2Entity entry : tScIcXsstockbillentry2List) {
                TScIcXsstockbillentry2Entity oldEntry = null;
                if (StringUtils.isNotEmpty(entry.getId())) {
                    oldEntry = systemService.getEntity(TScIcXsstockbillentry2Entity.class, entry.getId());
                }
                if (null != oldEntry) {
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
                } else {
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
                    if (StringUtils.isNotEmpty(entry.getStockId())) {
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
            if(null != tScIcXsstockbill.getCurPayAmount()){
                //变更金额
                BigDecimal changeAmount = oldEntity.getCheckAmount().subtract(tScIcXsstockbill.getCurPayAmount());
                BigDecimal checkAmount = oldEntity.getCheckAmount().subtract(changeAmount);
                tScIcXsstockbill.setCheckAmount(checkAmount);
            }else{
                tScIcXsstockbill.setCheckAmount(BigDecimal.ZERO);
            }
            tScIcXsstockbillService.updateMain(tScIcXsstockbill, tScIcXsstockbillentry1List, tScIcXsstockbillentry2List);
            String logistical = request.getParameter("json");
            if (StringUtils.isNotEmpty(logistical)) {
                TScSlLogisticalEntity logisticalEntity = JSONObject.parseObject(logistical, TScSlLogisticalEntity.class);
                systemService.saveOrUpdate(logisticalEntity);
            }
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "更新销售换货单失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 销售换货单新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TScIcXsstockbillEntity tScIcXsstockbill, HttpServletRequest req) {
        //tScIcXsstockbill = tScIcXsstockbillService.getEntity(TScIcXsstockbillEntity.class, tScIcXsstockbill.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String billNo = BillNoGenerate.getBillNo("110");
        tScIcXsstockbill.setBillNo(billNo);
        req.setAttribute("tScIcXsstockbillPage", tScIcXsstockbill);
        req.setAttribute("date", sdf.format(new Date()));
        //是否允许负库存出库
        if(AccountUtil.isMinusInventorySI()) {
            req.setAttribute("isCheckNegative",false);
        }else{
            req.setAttribute("isCheckNegative",true);
        }
        Date checkDate = AccountUtil.getAccountStartDate();
        String dateStr = sdf.format(checkDate);
        req.setAttribute("checkDate",dateStr);
        //当前用户所在分支机构
        TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
        TSDepart depart = systemService.getParentSonInfo(sonInfo);
        req.setAttribute("sonId",depart.getId());
        req.setAttribute("sonName",depart.getDepartname());
        return new ModelAndView("com/qihang/buss/sc/sales/tScIcXsstockbill-add");
    }

    /**
     * 销售换货单编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TScIcXsstockbillEntity tScIcXsstockbill, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tScIcXsstockbill.getId())) {
            tScIcXsstockbill = tScIcXsstockbillService.getEntity(TScIcXsstockbillEntity.class, tScIcXsstockbill.getId());
            //req.setAttribute("tScIcXsstockbillPage", tScIcXsstockbill);
            String load = req.getParameter("load");
            TScDepartmentEntity dept = systemService.getEntity(TScDepartmentEntity.class, tScIcXsstockbill.getDeptId());
            TScOrganizationEntity organizationEntity = systemService.getEntity(TScOrganizationEntity.class, tScIcXsstockbill.getItemId());
            TSUser user = systemService.getEntity(TSUser.class, tScIcXsstockbill.getBillerId());
            TScEmpEntity emp = systemService.getEntity(TScEmpEntity.class, tScIcXsstockbill.getEmpId());
            if (StringUtils.isNotEmpty(tScIcXsstockbill.getAccountID())) {
                TScSettleacctEntity account = systemService.getEntity(TScSettleacctEntity.class, tScIcXsstockbill.getAccountID());
                if (null != account) {
                    tScIcXsstockbill.setAccountName(account.getName());
                }
            }
            if (null != dept) {
                tScIcXsstockbill.setDeptName(dept.getName());
            }
            if (null != organizationEntity) {
                tScIcXsstockbill.setItemName(organizationEntity.getName());
            }
            if (null != user) {
                tScIcXsstockbill.setBillerName(user.getRealName());
            }
            if (null != emp) {
                tScIcXsstockbill.setEmpName(emp.getName());
            }
            TSDepart sonEntity = systemService.getEntity(TSDepart.class, tScIcXsstockbill.getSonId());
            if (null != sonEntity) {
                tScIcXsstockbill.setSonName(sonEntity.getDepartname());
            }
            if(StringUtils.isNotEmpty(tScIcXsstockbill.getClassIDSrc())){
                tScIcXsstockbill.setClassIDName("销售出库");
            }
            String tranType = tScIcXsstockbill.getTranType();
            if (StringUtils.isNotEmpty(tranType)) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                List<TSAuditRelationEntity> info = systemService.getAuditInfoList(tScIcXsstockbill.getId(), tranType);
                String auditor = "";
                String auditDate = "";
                for (int i = 0; i < info.size(); i++) {
                    TSAuditRelationEntity entity = info.get(i);
//                    if (1 == entity.getIsFinish()) {
//                        tScIcXsstockbill.setCheckState(Globals.AUDIT_FIN);
//                    }
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
            req.setAttribute("tScIcXsstockbillPage", tScIcXsstockbill);
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
        return new ModelAndView("com/qihang/buss/sc/sales/tScIcXsstockbill-update");
    }

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "tScIcXsstockbillController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TScIcXsstockbillExcelEntity tScIcXsstockbill, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TScIcXsstockbillExcelEntity.class, dataGrid);
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScIcXsstockbill, request.getParameterMap());
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
        List<TScIcXsstockbillExcelEntity> tScIcXsstockbills = this.tScIcXsstockbillService.getListByCriteriaQuery(cq, false);
        TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
        TSDepart depart1 = systemService.getParentSonInfo(depart);
        Set<String> departIds = systemService.getAllSonId(depart1.getId());
        CriteriaQuery deptCq = new CriteriaQuery(TSDepart.class, dataGrid);
        deptCq.in("id", departIds.toArray());
        List<TSDepart> departList = this.tScIcXsstockbillService.getListByCriteriaQuery(deptCq,false);
        List<TScIcXsstockbillExcelEntity> result = new ArrayList<TScIcXsstockbillExcelEntity>();
        for (TScIcXsstockbillExcelEntity entity : tScIcXsstockbills) {
            if(StringUtils.isNotEmpty(itemName)) {
                String hql = "from TScIcXsstockbillentry2ExcelEntity where entryToMain.id = ? ";

                hql += " and icitemEntity.name like '%" + itemName + "%' order by findex asc";
                hql += " order by findex asc";
                List<TScIcXsstockbillentry2ExcelEntity> entryList = this.tScIcXsstockbillService.findHql(hql, new Object[]{entity.getId()});
                if (entryList.size() > 0) {
                    entity.setMainToEntry2(entryList);
                    result.add(entity);
                }
            }else{
                result.add(entity);
            }
        }
        String tranType = request.getParameter("tranType");
        if (StringUtils.isNotEmpty(tranType)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (TScIcXsstockbillExcelEntity entity : tScIcXsstockbills) {
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
        modelMap.put(NormalExcelConstants.FILE_NAME, "销售换货单");
        modelMap.put(NormalExcelConstants.CLASS, TScIcXsstockbillExcelEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("销售换货单列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
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
    public String exportXlsByT(TScIcXsstockbillEntity tScIcXsstockbill, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "销售换货单");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TScIcXsstockbillEntity.class);
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
                ExcelImportResult importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(), TScIcXsstockbillEntity.class, params);
                List<TScIcXsstockbillEntity> listTScIcXsstockbillEntitys = importResult.getList();
                StringBuilder stringBuilder = new StringBuilder("已存在的数据:");
                boolean flag = false;
                if (!importResult.isVerfiyFail()) {
                    for (TScIcXsstockbillEntity tScIcXsstockbill : listTScIcXsstockbillEntitys) {
                        //以下检查导入数据是否重复的语句可视需求启用
                        //Long count = tScIcXsstockbillService.getCountForJdbc("这里放检查数据是否重复的SQL语句");
                        //if(count >0) {
                        //flag = true;
                        //stringBuilder.append(tScIcXsstockbill.getId()+",");
                        //} else {
                        tScIcXsstockbillService.save(tScIcXsstockbill);
                        //}
                    }
                    j.setMsg("文件导入成功！");
                    //j.setMsg("文件导入成功！" + (flag? stringBuilder.toString():""));
                } else {
                    String excelPath = "/upload/excelUpload/TScIcXsstockbillEntity/" + importResult.getExcelName();
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
     * 加载明细列表[销售换货单退货表]
     *
     * @return
     */
    @RequestMapping(params = "tScIcXsstockbillentry1List")
    public ModelAndView tScIcXsstockbillentry1List(TScIcXsstockbillEntity tScIcXsstockbill, HttpServletRequest req) {

        //===================================================================================
        //获取参数
        Object id0 = tScIcXsstockbill.getId();
        //===================================================================================
        //查询-销售换货单退货表
        String hql0 = "from TScIcXsstockbillentry1Entity where 1 = 1 AND fID = ? order by findex";
        try {
            List<TScIcXsstockbillentry1Entity> tScIcXsstockbillentry1EntityList = systemService.findHql(hql0, id0);
            List<TSBillInfoEntity> billInfoEntityList = systemService.findHql("from TSBillInfoEntity");
            Map<String,String> billInfo = new HashMap<String, String>();
            for(TSBillInfoEntity infoEntity : billInfoEntityList){
                billInfo.put(infoEntity.getBillId(),infoEntity.getBillName());
            }
            for (TScIcXsstockbillentry1Entity tScIcXsstockbillentry1 : tScIcXsstockbillentry1EntityList) {
                //商品
                if (StringUtils.isNotEmpty(tScIcXsstockbillentry1.getItemId())) {
                    TScIcitemEntity icitemEntity = systemService.get(TScIcitemEntity.class, tScIcXsstockbillentry1.getItemId());
                    if (icitemEntity != null) {
                        tScIcXsstockbillentry1.setItemNo(icitemEntity.getNumber());
                        tScIcXsstockbillentry1.setItemName(icitemEntity.getName());
                        tScIcXsstockbillentry1.setModel(icitemEntity.getModel());
                        tScIcXsstockbillentry1.setIsKFPeriod(icitemEntity.getIskfPeriod());
                        tScIcXsstockbillentry1.setBatchManager(icitemEntity.getBatchManager());
                    }
                    List<TScItemPriceEntity> unitList = systemService.findHql("from TScItemPriceEntity where priceToIcItem.id = ? ", new Object[]{icitemEntity.getId()});
                    for (TScItemPriceEntity unit : unitList) {
                        if ("0001".equals(unit.getUnitType())) {
                            tScIcXsstockbillentry1.setBasicCoefficient(unit.getCoefficient());
                        }
                        //单位对应的条码
                        if (StringUtils.isNotEmpty(tScIcXsstockbillentry1.getUnitId()) && tScIcXsstockbillentry1.getUnitId().equals(unit.getId())) {
                            if (StringUtils.isNotEmpty(unit.getBarCode())) {
                                tScIcXsstockbillentry1.setBarCode(unit.getBarCode());
                            }
                            if (null != unit.getXsLimitPrice() && BigDecimal.ZERO != unit.getXsLimitPrice()) {
                                tScIcXsstockbillentry1.setXsLimitPrice(unit.getXsLimitPrice());
                            } else {
                                tScIcXsstockbillentry1.setXsLimitPrice(BigDecimal.ZERO);
                            }
                        }
                    }
                }

                if (StringUtils.isNotEmpty(tScIcXsstockbillentry1.getStockId())) {
                    TScStockEntity stockEntity = systemService.get(TScStockEntity.class, tScIcXsstockbillentry1.getStockId());
                    tScIcXsstockbillentry1.setStockName(stockEntity.getName());
                }

                if(StringUtils.isNotEmpty(tScIcXsstockbillentry1.getClassIDSrc())){
                    String tranTypeName = billInfo.get(tScIcXsstockbillentry1.getClassIDSrc());
                    tScIcXsstockbillentry1.setClassIDName(tranTypeName);
                }
            }
            req.setAttribute("tScIcXsstockbillentry1List", tScIcXsstockbillentry1EntityList);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        String load = req.getParameter("load");
        req.setAttribute("load",load);
        return new ModelAndView("com/qihang/buss/sc/sales/tScIcXsstockbillentry1List");
    }

    /**
     * 加载明细列表[销售换货单换货表]
     *
     * @return
     */
    @RequestMapping(params = "tScIcXsstockbillentry2List")
    public ModelAndView tScIcXsstockbillentry2List(TScIcXsstockbillEntity tScIcXsstockbill, HttpServletRequest req) {

        //===================================================================================
        //获取参数
        Object id1 = tScIcXsstockbill.getId();
        //===================================================================================
        //查询-销售换货单换货表
        String hql1 = "from TScIcXsstockbillentry2Entity where 1 = 1 AND fID = ? order by findex";
        try {
            List<TScIcXsstockbillentry2Entity> tScIcXsstockbillentry2EntityList = systemService.findHql(hql1, id1);
            for (TScIcXsstockbillentry2Entity tScIcXsstockbillentry2 : tScIcXsstockbillentry2EntityList) {
                //商品
                if (StringUtils.isNotEmpty(tScIcXsstockbillentry2.getItemId())) {
                    TScIcitemEntity icitemEntity = systemService.get(TScIcitemEntity.class, tScIcXsstockbillentry2.getItemId());
                    if (icitemEntity != null) {
                        tScIcXsstockbillentry2.setItemNo(icitemEntity.getNumber());
                        tScIcXsstockbillentry2.setItemName(icitemEntity.getName());
                        tScIcXsstockbillentry2.setModel(icitemEntity.getModel());
                        tScIcXsstockbillentry2.setIsKFPeriod(icitemEntity.getIskfPeriod());
                        tScIcXsstockbillentry2.setBatchManager(icitemEntity.getBatchManager());
                    }
                    List<TScItemPriceEntity> unitList = systemService.findHql("from TScItemPriceEntity where priceToIcItem.id = ? ", new Object[]{icitemEntity.getId()});
                    for (TScItemPriceEntity unit : unitList) {
                        if ("0001".equals(unit.getUnitType())) {
                            tScIcXsstockbillentry2.setBasicCoefficient(unit.getCoefficient());
                        }
                        //单位对应的条码
                        if (StringUtils.isNotEmpty(tScIcXsstockbillentry2.getUnitId()) && tScIcXsstockbillentry2.getUnitId().equals(unit.getId())) {
                            if (StringUtils.isNotEmpty(unit.getBarCode())) {
                                tScIcXsstockbillentry2.setBarCode(unit.getBarCode());
                            }
                            if (null != unit.getXsLimitPrice() && BigDecimal.ZERO != unit.getXsLimitPrice()) {
                                tScIcXsstockbillentry2.setXsLimitPrice(unit.getXsLimitPrice());
                            } else {
                                tScIcXsstockbillentry2.setXsLimitPrice(BigDecimal.ZERO);
                            }
                        }
                    }
                }

                if (StringUtils.isNotEmpty(tScIcXsstockbillentry2.getStockId())) {
                    TScStockEntity stockEntity = systemService.get(TScStockEntity.class, tScIcXsstockbillentry2.getStockId());
                    tScIcXsstockbillentry2.setStockName(stockEntity.getName());
                }
            }
            req.setAttribute("tScIcXsstockbillentry2List", tScIcXsstockbillentry2EntityList);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        String load = req.getParameter("load");
        req.setAttribute("load",load);
        return new ModelAndView("com/qihang/buss/sc/sales/tScIcXsstockbillentry2List");
    }

    //作废功能
    @RequestMapping(params = "cancelBill")
    @ResponseBody
    public AjaxJson cancelBill(String ids) {
        AjaxJson j = tScIcXsstockbillService.cancelBill(ids);
//		if(j.isSuccess()) {
//			//校验订单是否自动关闭
//			String[] idList = ids.split(",");
//			Set<String> mainId = new HashSet<String>();
//			for (String id : idList) {
//				List<TScPoStockbillentryEntity> entryList = tScIcXsstockbillService.findHql("from TScPoStockbillentryEntity where fid = ?", new Object[]{id});
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
    public AjaxJson enableBill(String ids) {
        AjaxJson j = tScIcXsstockbillService.enableBill(ids);
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
    public AjaxJson afterAudit(String id, Integer audit) {
        TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
        TSDepart depart = systemService.getParentSonInfo(sonInfo);
        AjaxJson j = tScIcXsstockbillService.afterAudit(id, audit,depart.getId());
        return j;
    }
}
