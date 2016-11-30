
package com.qihang.buss.sc.rp.controller;

import com.qihang.buss.sc.baseinfo.entity.*;
import com.qihang.buss.sc.baseinfo.service.*;
import com.qihang.buss.sc.rp.entity.TScRpAdjustbillEntity;
import com.qihang.buss.sc.rp.entity.TScRpAdjustbillExcelEntity;
import com.qihang.buss.sc.rp.entity.TScRpAdjustbillViewEntity;
import com.qihang.buss.sc.rp.service.TScRpAdjustbillServiceI;
import com.qihang.buss.sc.rp.page.TScRpAdjustbillPage;
import com.qihang.buss.sc.rp.entity.TScRpAdjustbillentryEntity;
import com.qihang.buss.sc.sales.entity.TScCountEntity;
import com.qihang.buss.sc.sales.entity.TScOrderEntity;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
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
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.pojo.base.TSUser;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.web.system.service.UserService;
import freemarker.template.SimpleDate;
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
 * @Description: 应收调账
 * @date 2016-08-24 14:44:39
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScRpAdjustbillController")
public class TScRpAdjustbillController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TScRpAdjustbillController.class);

    @Autowired
    private TScRpAdjustbillServiceI tScRpAdjustbillService;
    @Autowired
    private SystemService systemService;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private TScOrganizationServiceI organizationServiceI;//客户

    @Autowired
    private TScEmpServiceI empServiceI;//经办人

    @Autowired
    private TScDepartmentServiceI departmentServiceI;//部门

    @Autowired
    private TScFeeServiceI feeServiceI;//收支项目；

    @Autowired
    private TScSupplierServiceI supplierServiceI;//供应商

    @Autowired
    private UserService userService;

    /**
     * 应收调账列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "tScRpAdjustbill")
    public ModelAndView tScRpAdjustbill(HttpServletRequest request) {
        request.setAttribute("tranType", "201");
        request.setAttribute("amountStr", "应收");
        request.setAttribute("itemTitle", "客户");
        return new ModelAndView("com/qihang/buss/sc/rp/tScRpAdjustbillList");
    }

    /**
     * 应付调账列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "tScRpAdjustbill_pay")
    public ModelAndView tScRpAdjustbill_pay(HttpServletRequest request) {
        request.setAttribute("tranType", "202");
        request.setAttribute("amountStr", "应付");
        request.setAttribute("itemTitle", "供应商");
        return new ModelAndView("com/qihang/buss/sc/rp/tScRpAdjustbillList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TScRpAdjustbillViewEntity tScRpAdjustbill, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        //汇总金额，已付金额，数量，金额，折后金额
        dataGrid.setFooter("allAmount,checkAmount,amount");
        CriteriaQuery cq = new CriteriaQuery(TScRpAdjustbillViewEntity.class, dataGrid);
        cq.addOrder("date",SortDirection.desc);
        cq.addOrder("billNo",SortDirection.desc);
        cq.addOrder("id",SortDirection.desc);
        cq.addOrder("entryId",SortDirection.asc);
        //查询条件组装器
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScRpAdjustbill);
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
                Boolean isAudit = userService.isAllowAudit(tScRpAdjustbill.getTranType().toString(),userId,depart.getId());
                cq.eq("cancellation",0);
                //判断当前用户是否在多级审核队列中
                if(isAudit){
                    Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId,depart.getId(),tScRpAdjustbill.getTranType().toString());
                    if(userAuditLeave.size() > 0){
                        String leaves = "";
                        for(Integer leave : userAuditLeave){
                            leaves += leave+",";
                        }
                        leaves = leaves.substring(0,leaves.length()-1);
                        List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in ("+leaves+")",new Object[]{depart.getId(),tScRpAdjustbill.getTranType().toString()});
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
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tScRpAdjustbillService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除应收调账
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TScRpAdjustbillEntity tScRpAdjustbill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tScRpAdjustbill = systemService.getEntity(TScRpAdjustbillEntity.class, tScRpAdjustbill.getId());
        String message = "应收调账删除成功";
        try {
            //客户
            if (StringUtils.isNotEmpty(tScRpAdjustbill.getItemId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_DEL_TYPE);
                countEntity.setOldId(tScRpAdjustbill.getItemId());
                if ("201".equals(tScRpAdjustbill.getTranType())) {
                    boolean updateIsSuccess = organizationServiceI.updateOrganizationCount(countEntity);
                    if (updateIsSuccess == false) {
                        message = "更新基础资料客户引用次数失败";
                        j.setMsg(message);
                        return j;
                    }
                } else {
                    boolean updateIsSuccess = supplierServiceI.updateSupplierCount(countEntity);
                    if (updateIsSuccess == false) {
                        message = "更新基础资料供应商引用次数失败";
                        j.setMsg(message);
                        return j;
                    }
                }
            }
            //经办人
            if (StringUtils.isNotEmpty(tScRpAdjustbill.getEmpId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_DEL_TYPE);
                countEntity.setOldId(tScRpAdjustbill.getEmpId());
                boolean updateIsSuccess = empServiceI.updateEmpCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料职员引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            //部门
            if (StringUtils.isNotEmpty(tScRpAdjustbill.getDeptId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_DEL_TYPE);
                countEntity.setOldId(tScRpAdjustbill.getDeptId());
                boolean updateIsSuccess = departmentServiceI.updateDepartmentCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料部门引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            List<TScRpAdjustbillentryEntity> entryList = systemService.findHql("from TScRpAdjustbillentryEntity where fid = ?", new Object[]{tScRpAdjustbill.getId()});
            for (TScRpAdjustbillentryEntity entry : entryList) {
                //收支项目
                if (StringUtils.isNotEmpty(entry.getExpId())) {
                    TScCountEntity countEntity = new TScCountEntity();
                    countEntity.setType(Globals.COUNT_DEL_TYPE);
                    countEntity.setOldId(entry.getExpId());
                    boolean updateIsSuccess = feeServiceI.updateFeeCount(countEntity);
                    if (updateIsSuccess == false) {
                        message = "更新基础资料收支项目引用次数失败";
                        j.setMsg(message);
                        return j;
                    }
                }
            }
            tScRpAdjustbillService.delMain(tScRpAdjustbill);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            //删除待审核预警数据
            systemService.delBillAuditStatus(tScRpAdjustbill.getTranType(), tScRpAdjustbill.getId());
        } catch (Exception e) {
            e.printStackTrace();
            message = "应收调账删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除应收调账
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "应收调账删除成功";
        try {
            for (String id : ids.split(",")) {
                TScRpAdjustbillEntity tScRpAdjustbill = systemService.getEntity(TScRpAdjustbillEntity.class,
                        id
                );
                tScRpAdjustbillService.delMain(tScRpAdjustbill);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "应收调账删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加应收调账
     *
     * @param tScRpAdjustbill
     * @param tScRpAdjustbillPage
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TScRpAdjustbillEntity tScRpAdjustbill, TScRpAdjustbillPage tScRpAdjustbillPage, HttpServletRequest request) {
        List<TScRpAdjustbillentryEntity> tScRpAdjustbillentryList = tScRpAdjustbillPage.getTScRpAdjustbillentryList();
        AjaxJson j = new AjaxJson();
        String message = "添加成功";
        //校验单据编号唯一性
        Boolean hasBillNo = systemService.checkBillNo(TScOrderEntity.class.getAnnotation(Table.class).name(), tScRpAdjustbill.getBillNo(), tScRpAdjustbill.getId());
        if (!hasBillNo) {
            j.setSuccess(false);
            j.setMsg("单据编号已存在,请确认");
            return j;
        }
        try {
            tScRpAdjustbillService.addMain(tScRpAdjustbill, tScRpAdjustbillentryList);
            //客户
            if (StringUtils.isNotEmpty(tScRpAdjustbill.getItemId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_ADD_TYPE);
                countEntity.setOldId(tScRpAdjustbill.getItemId());
                if ("201".equals(tScRpAdjustbill.getTranType())) {
                    boolean updateIsSuccess = organizationServiceI.updateOrganizationCount(countEntity);
                    if (updateIsSuccess == false) {
                        message = "更新基础资料客户引用次数失败";
                        j.setMsg(message);
                        return j;
                    }
                } else {
                    boolean updateIsSuccess = supplierServiceI.updateSupplierCount(countEntity);
                    if (updateIsSuccess == false) {
                        message = "更新基础资料供应商引用次数失败";
                        j.setMsg(message);
                        return j;
                    }
                }
            }
            //经办人
            if (StringUtils.isNotEmpty(tScRpAdjustbill.getEmpId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_ADD_TYPE);
                countEntity.setOldId(tScRpAdjustbill.getEmpId());
                boolean updateIsSuccess = empServiceI.updateEmpCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料职员引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            //部门
            if (StringUtils.isNotEmpty(tScRpAdjustbill.getDeptId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_ADD_TYPE);
                countEntity.setOldId(tScRpAdjustbill.getDeptId());
                boolean updateIsSuccess = departmentServiceI.updateDepartmentCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料职员引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            for (TScRpAdjustbillentryEntity entry : tScRpAdjustbillentryList) {
                //收支项目
                if (StringUtils.isNotEmpty(entry.getExpId())) {
                    TScCountEntity countEntity = new TScCountEntity();
                    countEntity.setType(Globals.COUNT_ADD_TYPE);
                    countEntity.setOldId(entry.getExpId());
                    boolean updateIsSuccess = feeServiceI.updateFeeCount(countEntity);
                    if (updateIsSuccess == false) {
                        message = "更新基础资料收支项目引用次数失败";
                        j.setMsg(message);
                        return j;
                    }
                }
            }
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            //待审核数据提醒操作
            systemService.saveBillAuditStatus(tScRpAdjustbill.getTranType().toString(), tScRpAdjustbill.getId());
        } catch (Exception e) {
            e.printStackTrace();
            message = "应收调账添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新应收调账
     *
     * @param tScRpAdjustbill
     * @param tScRpAdjustbillPage
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TScRpAdjustbillEntity tScRpAdjustbill, TScRpAdjustbillPage tScRpAdjustbillPage, HttpServletRequest request) {
        List<TScRpAdjustbillentryEntity> tScRpAdjustbillentryList = tScRpAdjustbillPage.getTScRpAdjustbillentryList();
        AjaxJson j = new AjaxJson();
        String message = "更新成功";
        //校验单据编号唯一性
        Boolean hasBillNo = systemService.checkBillNo(TScOrderEntity.class.getAnnotation(Table.class).name(), tScRpAdjustbill.getBillNo(), tScRpAdjustbill.getId());
        if (!hasBillNo) {
            j.setSuccess(false);
            j.setMsg("单据编号已存在,请确认");
            return j;
        }
        try {
            TScRpAdjustbillEntity oldEntity = systemService.getEntity(TScRpAdjustbillEntity.class, tScRpAdjustbill.getId());
            //客户
            if (StringUtils.isNotEmpty(tScRpAdjustbill.getItemId()) && !oldEntity.getItemId().equals(tScRpAdjustbill.getItemId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_UPDATE_TYPE);
                countEntity.setOldId(oldEntity.getItemId());
                countEntity.setNewId(tScRpAdjustbill.getItemId());
                if ("201".equals(tScRpAdjustbill.getTranType())) {
                    boolean updateIsSuccess = organizationServiceI.updateOrganizationCount(countEntity);
                    if (updateIsSuccess == false) {
                        message = "更新基础资料客户引用次数失败";
                        j.setMsg(message);
                        return j;
                    }
                } else {
                    boolean updateIsSuccess = supplierServiceI.updateSupplierCount(countEntity);
                    if (updateIsSuccess == false) {
                        message = "更新基础资料供应商引用次数失败";
                        j.setMsg(message);
                        return j;
                    }
                }
            }
            //经办人
            if (StringUtils.isNotEmpty(tScRpAdjustbill.getEmpId()) && !oldEntity.getEmpId().equals(tScRpAdjustbill.getEmpId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_UPDATE_TYPE);
                countEntity.setOldId(oldEntity.getEmpId());
                countEntity.setNewId(tScRpAdjustbill.getEmpId());
                boolean updateIsSuccess = empServiceI.updateEmpCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料职员引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            //部门
            if (StringUtils.isNotEmpty(tScRpAdjustbill.getDeptId()) && !oldEntity.getDeptId().equals(tScRpAdjustbill.getDeptId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_UPDATE_TYPE);
                countEntity.setOldId(oldEntity.getDeptId());
                countEntity.setNewId(tScRpAdjustbill.getDeptId());
                boolean updateIsSuccess = departmentServiceI.updateDepartmentCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料职员引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            for (TScRpAdjustbillentryEntity entry : tScRpAdjustbillentryList) {
                TScRpAdjustbillentryEntity oldEntry = null;
                if (StringUtils.isNotEmpty(entry.getId())) {
                    oldEntry = systemService.getEntity(TScRpAdjustbillentryEntity.class, entry.getId());
                }
                if (null != oldEntry) {
                    //收支项目
                    if (StringUtils.isNotEmpty(entry.getExpId()) && !oldEntry.getExpId().equals(entry.getExpId())) {
                        TScCountEntity countEntity = new TScCountEntity();
                        countEntity.setType(Globals.COUNT_UPDATE_TYPE);
                        countEntity.setOldId(oldEntry.getExpId());
                        countEntity.setNewId(entry.getExpId());
                        boolean updateIsSuccess = feeServiceI.updateFeeCount(countEntity);
                        if (updateIsSuccess == false) {
                            message = "更新基础资料收支项目引用次数失败";
                            j.setMsg(message);
                            return j;
                        }
                    }
                } else {
                    //收支项目
                    if (StringUtils.isNotEmpty(entry.getExpId())) {
                        TScCountEntity countEntity = new TScCountEntity();
                        countEntity.setType(Globals.COUNT_ADD_TYPE);
                        countEntity.setOldId(entry.getExpId());
                        boolean updateIsSuccess = feeServiceI.updateFeeCount(countEntity);
                        if (updateIsSuccess == false) {
                            message = "更新基础资料收支项目引用次数失败";
                            j.setMsg(message);
                            return j;
                        }
                    }
                }
            }
            sessionFactory.getCurrentSession().evict(oldEntity);
            tScRpAdjustbillService.updateMain(tScRpAdjustbill, tScRpAdjustbillentryList);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "更新应收调账失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 应收调账新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TScRpAdjustbillEntity tScRpAdjustbill, HttpServletRequest req) {
        String billNo = BillNoGenerate.getBillNo(tScRpAdjustbill.getTranType());
        tScRpAdjustbill.setBillNo(billNo);
        req.setAttribute("tScRpAdjustbillPage", tScRpAdjustbill);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        req.setAttribute("date", sdf.format(new Date()));
        String title = "应收调账";
        String itemTitle = "客户";
        req.setAttribute("amountStr", "应收");
        if ("202".equals(tScRpAdjustbill.getTranType())) {
            title = "应付调账";
            itemTitle = "供应商";
            req.setAttribute("amountStr", "应付");
        }
        req.setAttribute("itemTitle", itemTitle);
        req.setAttribute("title", title);
        //当前用户所在分支机构
        TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
        TSDepart depart = systemService.getParentSonInfo(sonInfo);
        req.setAttribute("sonId", depart.getId());
        req.setAttribute("sonName", depart.getDepartname());
        return new ModelAndView("com/qihang/buss/sc/rp/tScRpAdjustbill-add");
    }

    /**
     * 应收调账编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TScRpAdjustbillEntity tScRpAdjustbill, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tScRpAdjustbill.getId())) {
            tScRpAdjustbill = tScRpAdjustbillService.getEntity(TScRpAdjustbillEntity.class, tScRpAdjustbill.getId());
            //制单人
            if (StringUtils.isNotEmpty(tScRpAdjustbill.getBillerId())) {
                TSUser user = systemService.getEntity(TSUser.class, tScRpAdjustbill.getBillerId());
                if (null != user) {
                    tScRpAdjustbill.setBillerName(user.getRealName());
                }
            }
            //客户
            if (StringUtils.isNotEmpty(tScRpAdjustbill.getItemId())) {
                if ("201".equals(tScRpAdjustbill.getTranType())) {
                    TScOrganizationEntity organizationEntity = systemService.getEntity(TScOrganizationEntity.class, tScRpAdjustbill.getItemId());
                    if (null != organizationEntity) {
                        tScRpAdjustbill.setItemName(organizationEntity.getName());
                    }
                } else {
                    TScSupplierEntity supplierEntity = systemService.getEntity(TScSupplierEntity.class, tScRpAdjustbill.getItemId());
                    if (null != supplierEntity) {
                        tScRpAdjustbill.setItemName(supplierEntity.getName());
                    }
                }
            }
            //经办人
            if (StringUtils.isNotEmpty(tScRpAdjustbill.getEmpId())) {
                TScEmpEntity emp = systemService.getEntity(TScEmpEntity.class, tScRpAdjustbill.getEmpId());
                if (null != emp) {
                    tScRpAdjustbill.setEmpName(emp.getName());
                }
            }
            //部门
            if (StringUtils.isNotEmpty(tScRpAdjustbill.getDeptId())) {
                TScDepartmentEntity dept = systemService.getEntity(TScDepartmentEntity.class, tScRpAdjustbill.getDeptId());
                if (null != dept) {
                    tScRpAdjustbill.setDeptName(dept.getName());
                }
            }
            if (StringUtils.isNotEmpty(tScRpAdjustbill.getSonId())) {
                TSDepart sonInfo = systemService.getEntity(TSDepart.class, tScRpAdjustbill.getSonId());
                if (null != sonInfo) {
                    tScRpAdjustbill.setSonName(sonInfo.getDepartname());
                }
            }
            req.setAttribute("tScRpAdjustbillPage", tScRpAdjustbill);

            String tranType = tScRpAdjustbill.getTranType();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            List<TSAuditRelationEntity> info = systemService.getAuditInfoList(tScRpAdjustbill.getId(), tranType);
            String auditor = "";
            String auditDate = "";
            for (int i = 0; i < info.size(); i++) {
                TSAuditRelationEntity entity = info.get(i);
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
            String load = req.getParameter("load");
            req.setAttribute("load", load);
            String title = "应收调账";
            String itemTitle = "客户";
            req.setAttribute("amountStr", "应收");
            if ("202".equals(tScRpAdjustbill.getTranType())) {
                title = "应付调账";
                itemTitle = "供应商";
                req.setAttribute("amountStr", "应付");
            }
            req.setAttribute("itemTitle", itemTitle);
            req.setAttribute("title", title);
        }
        return new ModelAndView("com/qihang/buss/sc/rp/tScRpAdjustbill-update");
    }

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "tScRpAdjustbillController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TScRpAdjustbillExcelEntity tScRpAdjustbill, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TScRpAdjustbillExcelEntity.class, dataGrid);
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScRpAdjustbill, request.getParameterMap());
        String query_date_begin = request.getParameter("date_begin");
        String query_date_end = request.getParameter("date_end");
        try {
            if (StringUtil.isNotEmpty(query_date_begin)) {
                cq.ge("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_begin));
            }
            if (StringUtil.isNotEmpty(query_date_end)) {
                cq.le("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_end));
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cq.add();
        List<TScRpAdjustbillExcelEntity> tScRpAdjustbills = this.tScRpAdjustbillService.getListByCriteriaQuery(cq, false);
        TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
        TSDepart depart1 = systemService.getParentSonInfo(depart);
        Set<String> departIds = systemService.getAllSonId(depart1.getId());
        CriteriaQuery deptCq = new CriteriaQuery(TSDepart.class, dataGrid);
        deptCq.in("id", departIds.toArray());
        List<TSDepart> departList = systemService.getListByCriteriaQuery(deptCq, false);
        String tranType = request.getParameter("tranType");
        String title = "应收";
        if (StringUtils.isNotEmpty(tranType)) {
            if ("202".equals(tranType)) {
                title = "应付";
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (TScRpAdjustbillExcelEntity entity : tScRpAdjustbills) {
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
                for (TSDepart depart2 : departList) {
                    if (depart2.getId().equals(entity.getSonId())) {
                        entity.setSonName(depart2.getDepartname());
                        break;
                    }
                }
            }
        }
        //如需动态排除部分列不导出时启用，列名指Excel中文列名
        String[] exclusions = {"排除列名1", "排除列名2"};
        modelMap.put(NormalExcelConstants.FILE_NAME, title + "调账");
        modelMap.put(NormalExcelConstants.CLASS, TScRpAdjustbillExcelEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams(title + "调账列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息", exclusions));
        modelMap.put(NormalExcelConstants.DATA_LIST, tScRpAdjustbills);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TScRpAdjustbillEntity tScRpAdjustbill, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "应收调账");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TScRpAdjustbillEntity.class);
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
                ExcelImportResult importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(), TScRpAdjustbillEntity.class, params);
                List<TScRpAdjustbillEntity> listTScRpAdjustbillEntitys = importResult.getList();
                StringBuilder stringBuilder = new StringBuilder("已存在的数据:");
                boolean flag = false;
                if (!importResult.isVerfiyFail()) {
                    for (TScRpAdjustbillEntity tScRpAdjustbill : listTScRpAdjustbillEntitys) {
                        //以下检查导入数据是否重复的语句可视需求启用
                        //Long count = tScRpAdjustbillService.getCountForJdbc("这里放检查数据是否重复的SQL语句");
                        //if(count >0) {
                        //flag = true;
                        //stringBuilder.append(tScRpAdjustbill.getId()+",");
                        //} else {
                        tScRpAdjustbillService.save(tScRpAdjustbill);
                        //}
                    }
                    j.setMsg("文件导入成功！");
                    //j.setMsg("文件导入成功！" + (flag? stringBuilder.toString():""));
                } else {
                    String excelPath = "/upload/excelUpload/TScRpAdjustbillEntity/" + importResult.getExcelName();
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
     * 加载明细列表[应收调账从表]
     *
     * @return
     */
    @RequestMapping(params = "tScRpAdjustbillentryList")
    public ModelAndView tScRpAdjustbillentryList(TScRpAdjustbillEntity tScRpAdjustbill, HttpServletRequest req) {

        //===================================================================================
        //获取参数
        Object id0 = tScRpAdjustbill.getId();
        //===================================================================================
        //查询-应收调账从表
        String hql0 = "from TScRpAdjustbillentryEntity where 1 = 1 AND fID = ? order by findex";
        try {
            List<TScRpAdjustbillentryEntity> tScRpAdjustbillentryEntityList = systemService.findHql(hql0, id0);
            for (TScRpAdjustbillentryEntity entry : tScRpAdjustbillentryEntityList) {
                if (StringUtils.isNotEmpty(entry.getExpId())) {
                    TScFeeEntity fee = systemService.getEntity(TScFeeEntity.class, entry.getExpId());
                    if (null != fee) {
                        entry.setExpName(fee.getName());
                    }
                }
            }
            req.setAttribute("tScRpAdjustbillentryList", tScRpAdjustbillentryEntityList);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        String load = req.getParameter("load");
        req.setAttribute("load", load);
        return new ModelAndView("com/qihang/buss/sc/rp/tScRpAdjustbillentryList");
    }

}
