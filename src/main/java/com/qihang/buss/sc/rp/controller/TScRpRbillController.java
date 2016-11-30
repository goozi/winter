
package com.qihang.buss.sc.rp.controller;

import com.qihang.buss.sc.baseinfo.entity.TScDepartmentEntity;
import com.qihang.buss.sc.baseinfo.entity.TScEmpEntity;
import com.qihang.buss.sc.baseinfo.entity.TScOrganizationEntity;
import com.qihang.buss.sc.baseinfo.entity.TScSettleacctEntity;
import com.qihang.buss.sc.baseinfo.service.*;
import com.qihang.buss.sc.rp.entity.*;
import com.qihang.buss.sc.rp.service.TScRpRbillServiceI;
import com.qihang.buss.sc.rp.page.TScRpRbillPage;
import com.qihang.buss.sc.sales.entity.TScCountEntity;
import com.qihang.buss.sc.sales.entity.TScOrderEntity;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TSBillInfoEntity;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Controller
 * @Description: 收款单
 * @date 2016-08-24 20:50:38
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScRpRbillController")
public class TScRpRbillController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TScRpRbillController.class);

    @Autowired
    private TScRpRbillServiceI tScRpRbillService;
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
    private TScSettleacctServiceI settleacctServiceI;//结算账户

    @Autowired
    private UserService userService;


    /**
     * 收款单列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "tScRpRbill")
    public ModelAndView tScRpRbill(HttpServletRequest request) {
        return new ModelAndView("com/qihang/buss/sc/rp/tScRpRbillList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TScRpRbillViewEntity tScRpRbill, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        //汇总金额，已付金额，数量，金额，折后金额
        dataGrid.setFooter("allAmount,checkAmount,amount");
        CriteriaQuery cq = new CriteriaQuery(TScRpRbillViewEntity.class, dataGrid);
        cq.addOrder("date",SortDirection.desc);
        cq.addOrder("billNo",SortDirection.desc);
        cq.addOrder("id",SortDirection.desc);
        cq.addOrder("entryId",SortDirection.asc);
        //查询条件组装器
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScRpRbill);
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
                Boolean isAudit = userService.isAllowAudit(tScRpRbill.getTranType().toString(),userId,depart.getId());
                cq.eq("cancellation",0);
                //判断当前用户是否在多级审核队列中
                if(isAudit){
                    Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId,depart.getId(),tScRpRbill.getTranType().toString());
                    if(userAuditLeave.size() > 0){
                        String leaves = "";
                        for(Integer leave : userAuditLeave){
                            leaves += leave+",";
                        }
                        leaves = leaves.substring(0,leaves.length()-1);
                        List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in ("+leaves+")",new Object[]{depart.getId(),tScRpRbill.getTranType().toString()});
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
        this.tScRpRbillService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除收款单
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TScRpRbillEntity tScRpRbill, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tScRpRbill = systemService.getEntity(TScRpRbillEntity.class, tScRpRbill.getId());
        String message = "收款单删除成功";
        try {
            //客户
            if (StringUtils.isNotEmpty(tScRpRbill.getItemId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_DEL_TYPE);
                countEntity.setOldId(tScRpRbill.getItemId());
                boolean updateIsSuccess = organizationServiceI.updateOrganizationCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料供应商引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            //经办人
            if (StringUtils.isNotEmpty(tScRpRbill.getEmpId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_DEL_TYPE);
                countEntity.setOldId(tScRpRbill.getEmpId());
                boolean updateIsSuccess = empServiceI.updateEmpCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料职员引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            //部门
            if (StringUtils.isNotEmpty(tScRpRbill.getDeptId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_DEL_TYPE);
                countEntity.setOldId(tScRpRbill.getDeptId());
                boolean updateIsSuccess = departmentServiceI.updateDepartmentCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料部门引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            List<TScRpRbillentry1Entity> tScRpRbillentry1List = tScRpRbillService.findHql("from TScRpRbillentry1Entity where fid = ?", new Object[]{tScRpRbill.getId()});
            for (TScRpRbillentry1Entity entry : tScRpRbillentry1List) {
                if (StringUtils.isNotEmpty(entry.getAccountId())) {
                    TScCountEntity countEntity = new TScCountEntity();
                    countEntity.setType(Globals.COUNT_DEL_TYPE);
                    countEntity.setOldId(entry.getAccountId());
                    boolean updateIsSuccess = settleacctServiceI.updateOrganizationCount(countEntity);
                    if (updateIsSuccess == false) {
                        message = "更新基础资料结算账户引用次数失败";
                        j.setMsg(message);
                        return j;
                    }
                }
            }

            tScRpRbillService.delMain(tScRpRbill);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            //删除待审核预警数据
            systemService.delBillAuditStatus(tScRpRbill.getTranType(), tScRpRbill.getId());
        } catch (Exception e) {
            e.printStackTrace();
            message = "收款单删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除收款单
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "收款单删除成功";
        try {
            for (String id : ids.split(",")) {
                TScRpRbillEntity tScRpRbill = systemService.getEntity(TScRpRbillEntity.class,
                        id
                );
                tScRpRbillService.delMain(tScRpRbill);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "收款单删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加收款单
     *
     * @param tScRpRbill
     * @param tScRpRbillPage
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TScRpRbillEntity tScRpRbill, TScRpRbillPage tScRpRbillPage, HttpServletRequest request) {
        List<TScRpRbillentry1Entity> tScRpRbillentry1List = tScRpRbillPage.getTScRpRbillentry1List();
        List<TScRpRbillentry2Entity> tScRpRbillentry2List = tScRpRbillPage.getTScRpRbillentry2List();
        AjaxJson j = new AjaxJson();
        String message = "添加成功";
        //校验单据编号唯一性
        Boolean hasBillNo = systemService.checkBillNo(TScOrderEntity.class.getAnnotation(Table.class).name(), tScRpRbill.getBillNo(), tScRpRbill.getId());
        if (!hasBillNo) {
            j.setSuccess(false);
            j.setMsg("单据编号已存在,请确认");
            return j;
        }
        try {
            tScRpRbillService.addMain(tScRpRbill, tScRpRbillentry1List, tScRpRbillentry2List);
            //客户
            if (StringUtils.isNotEmpty(tScRpRbill.getItemId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_ADD_TYPE);
                countEntity.setOldId(tScRpRbill.getItemId());
                boolean updateIsSuccess = organizationServiceI.updateOrganizationCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料客户引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            //经办人
            if (StringUtils.isNotEmpty(tScRpRbill.getEmpId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_ADD_TYPE);
                countEntity.setOldId(tScRpRbill.getEmpId());
                boolean updateIsSuccess = empServiceI.updateEmpCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料职员引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            //部门
            if (StringUtils.isNotEmpty(tScRpRbill.getDeptId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_ADD_TYPE);
                countEntity.setOldId(tScRpRbill.getDeptId());
                boolean updateIsSuccess = departmentServiceI.updateDepartmentCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料职员引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            for (TScRpRbillentry1Entity entry : tScRpRbillentry1List) {
                //结算账户
                if (StringUtils.isNotEmpty(entry.getAccountId())) {
                    TScCountEntity countEntity = new TScCountEntity();
                    countEntity.setType(Globals.COUNT_DEL_TYPE);
                    countEntity.setOldId(entry.getAccountId());
                    boolean updateIsSuccess = settleacctServiceI.updateOrganizationCount(countEntity);
                    if (updateIsSuccess == false) {
                        message = "更新基础资料结算账户引用次数失败";
                        j.setMsg(message);
                        return j;
                    }
                }
            }
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            //待审核数据提醒操作
            systemService.saveBillAuditStatus(tScRpRbill.getTranType().toString(), tScRpRbill.getId());
        } catch (Exception e) {
            e.printStackTrace();
            message = "收款单添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新收款单
     *
     * @param tScRpRbill
     * @param tScRpRbillPage
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TScRpRbillEntity tScRpRbill, TScRpRbillPage tScRpRbillPage, HttpServletRequest request) {
        List<TScRpRbillentry1Entity> tScRpRbillentry1List = tScRpRbillPage.getTScRpRbillentry1List();
        List<TScRpRbillentry2Entity> tScRpRbillentry2List = tScRpRbillPage.getTScRpRbillentry2List();
        AjaxJson j = new AjaxJson();
        String message = "更新成功";
        //校验单据编号唯一性
        Boolean hasBillNo = systemService.checkBillNo(TScOrderEntity.class.getAnnotation(Table.class).name(), tScRpRbill.getBillNo(), tScRpRbill.getId());
        if (!hasBillNo) {
            j.setSuccess(false);
            j.setMsg("单据编号已存在,请确认");
            return j;
        }
        try {
            TScRpRbillEntity oldEntity = systemService.getEntity(TScRpRbillEntity.class, tScRpRbill.getId());
            //客户
            if (StringUtils.isNotEmpty(tScRpRbill.getItemId()) && !oldEntity.getItemId().equals(tScRpRbill.getItemId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_UPDATE_TYPE);
                countEntity.setOldId(oldEntity.getItemId());
                countEntity.setNewId(tScRpRbill.getItemId());
                boolean updateIsSuccess = organizationServiceI.updateOrganizationCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料供应商引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            //经办人
            if (StringUtils.isNotEmpty(tScRpRbill.getEmpId()) && !oldEntity.getEmpId().equals(tScRpRbill.getEmpId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_UPDATE_TYPE);
                countEntity.setOldId(oldEntity.getEmpId());
                countEntity.setNewId(tScRpRbill.getEmpId());
                boolean updateIsSuccess = empServiceI.updateEmpCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料职员引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            //部门
            if (StringUtils.isNotEmpty(tScRpRbill.getDeptId()) && !oldEntity.getDeptId().equals(tScRpRbill.getDeptId())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_UPDATE_TYPE);
                countEntity.setOldId(oldEntity.getDeptId());
                countEntity.setNewId(tScRpRbill.getDeptId());
                boolean updateIsSuccess = departmentServiceI.updateDepartmentCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料职员引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            for (TScRpRbillentry1Entity entry : tScRpRbillentry1List) {
                TScRpRbillentry1Entity oldEntry = null;
                if (StringUtils.isNotEmpty(entry.getId())) {
                    oldEntry = systemService.getEntity(TScRpRbillentry1Entity.class, entry.getId());
                }
                if (null != oldEntry) {
                    //结算账户
                    if (StringUtils.isNotEmpty(entry.getAccountId()) && !oldEntry.getAccountId().equals(entry.getAccountId())) {
                        TScCountEntity countEntity = new TScCountEntity();
                        countEntity.setType(Globals.COUNT_UPDATE_TYPE);
                        countEntity.setOldId(oldEntry.getAccountId());
                        countEntity.setNewId(entry.getAccountId());
                        boolean updateIsSuccess = settleacctServiceI.updateOrganizationCount(countEntity);
                        if (updateIsSuccess == false) {
                            message = "更新基础资料结算账户引用次数失败";
                            j.setMsg(message);
                            return j;
                        }
                    }

                } else {
                    //结算账户
                    if (StringUtils.isNotEmpty(entry.getAccountId())) {
                        TScCountEntity countEntity = new TScCountEntity();
                        countEntity.setType(Globals.COUNT_DEL_TYPE);
                        countEntity.setOldId(entry.getAccountId());
                        boolean updateIsSuccess = settleacctServiceI.updateOrganizationCount(countEntity);
                        if (updateIsSuccess == false) {
                            message = "更新基础资料结算账户引用次数失败";
                            j.setMsg(message);
                            return j;
                        }
                    }

                }
            }
            sessionFactory.getCurrentSession().evict(oldEntity);
            tScRpRbillService.updateMain(tScRpRbill, tScRpRbillentry1List, tScRpRbillentry2List);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "更新收款单失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 收款单新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TScRpRbillEntity tScRpRbill, HttpServletRequest req) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        req.setAttribute("date", sdf.format(new Date()));
        String billNo = BillNoGenerate.getBillNo(tScRpRbill.getTranType());
        tScRpRbill.setBillNo(billNo);
        req.setAttribute("tScRpRbillPage", tScRpRbill);
        //当前用户所在分支机构
        TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
        TSDepart depart = systemService.getParentSonInfo(sonInfo);
        req.setAttribute("sonId", depart.getId());
        req.setAttribute("sonName", depart.getDepartname());
        return new ModelAndView("com/qihang/buss/sc/rp/tScRpRbill-add");
    }

    /**
     * 收款单编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TScRpRbillEntity tScRpRbill, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tScRpRbill.getId())) {
            tScRpRbill = tScRpRbillService.getEntity(TScRpRbillEntity.class, tScRpRbill.getId());
            //制单人
            if (StringUtils.isNotEmpty(tScRpRbill.getBillerId())) {
                TSUser user = systemService.getEntity(TSUser.class, tScRpRbill.getBillerId());
                if (null != user) {
                    tScRpRbill.setBillerName(user.getRealName());
                }
            }
            //客户
            if (StringUtils.isNotEmpty(tScRpRbill.getItemId())) {
                TScOrganizationEntity organizationEntity = systemService.getEntity(TScOrganizationEntity.class, tScRpRbill.getItemId());
                if (null != organizationEntity) {
                    tScRpRbill.setItemName(organizationEntity.getName());
                }
            }
            //经办人
            if (StringUtils.isNotEmpty(tScRpRbill.getEmpId())) {
                TScEmpEntity emp = systemService.getEntity(TScEmpEntity.class, tScRpRbill.getEmpId());
                if (null != emp) {
                    tScRpRbill.setEmpName(emp.getName());
                }
            }
            //部门
            if (StringUtils.isNotEmpty(tScRpRbill.getDeptId())) {
                TScDepartmentEntity dept = systemService.getEntity(TScDepartmentEntity.class, tScRpRbill.getDeptId());
                if (null != dept) {
                    tScRpRbill.setDeptName(dept.getName());
                }
            }
            if (StringUtils.isNotEmpty(tScRpRbill.getSonId())) {
                TSDepart depart = systemService.getEntity(TSDepart.class, tScRpRbill.getSonId());
                if (null != depart) {
                    tScRpRbill.setSonName(depart.getDepartname());
                }
            }
            req.setAttribute("tScRpRbillPage", tScRpRbill);
            String tranType = tScRpRbill.getTranType();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            List<TSAuditRelationEntity> info = systemService.getAuditInfoList(tScRpRbill.getId(), tranType);
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
        }
        return new ModelAndView("com/qihang/buss/sc/rp/tScRpRbill-update");
    }

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "tScRpRbillController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TScRpRbillExcelEntity tScRpRbill, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TScRpRbillExcelEntity.class, dataGrid);
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScRpRbill, request.getParameterMap());
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
        List<TScRpRbillExcelEntity> tScRpRbills = this.tScRpRbillService.getListByCriteriaQuery(cq, false);
        TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
        TSDepart depart1 = systemService.getParentSonInfo(depart);
        Set<String> departIds = systemService.getAllSonId(depart1.getId());
        CriteriaQuery deptCq = new CriteriaQuery(TSDepart.class, dataGrid);
        deptCq.in("id", departIds.toArray());
        List<TSDepart> departList = systemService.getListByCriteriaQuery(deptCq, false);
        String tranType = request.getParameter("tranType");
        if (StringUtils.isNotEmpty(tranType)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (TScRpRbillExcelEntity entity : tScRpRbills) {
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
        modelMap.put(NormalExcelConstants.FILE_NAME, "收款单");
        modelMap.put(NormalExcelConstants.CLASS, TScRpRbillExcelEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("收款单列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息", exclusions));
        modelMap.put(NormalExcelConstants.DATA_LIST, tScRpRbills);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TScRpRbillEntity tScRpRbill, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "收款单");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TScRpRbillEntity.class);
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
                ExcelImportResult importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(), TScRpRbillEntity.class, params);
                List<TScRpRbillEntity> listTScRpRbillEntitys = importResult.getList();
                StringBuilder stringBuilder = new StringBuilder("已存在的数据:");
                boolean flag = false;
                if (!importResult.isVerfiyFail()) {
                    for (TScRpRbillEntity tScRpRbill : listTScRpRbillEntitys) {
                        //以下检查导入数据是否重复的语句可视需求启用
                        //Long count = tScRpRbillService.getCountForJdbc("这里放检查数据是否重复的SQL语句");
                        //if(count >0) {
                        //flag = true;
                        //stringBuilder.append(tScRpRbill.getId()+",");
                        //} else {
                        tScRpRbillService.save(tScRpRbill);
                        //}
                    }
                    j.setMsg("文件导入成功！");
                    //j.setMsg("文件导入成功！" + (flag? stringBuilder.toString():""));
                } else {
                    String excelPath = "/upload/excelUpload/TScRpRbillEntity/" + importResult.getExcelName();
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
     * 加载明细列表[结算表]
     *
     * @return
     */
    @RequestMapping(params = "tScRpRbillentry1List")
    public ModelAndView tScRpRbillentry1List(TScRpRbillEntity tScRpRbill, HttpServletRequest req) {

        //===================================================================================
        //获取参数
        Object id0 = tScRpRbill.getId();
        //===================================================================================
        //查询-结算表
        String hql0 = "from TScRpRbillentry1Entity where 1 = 1 AND fID = ? order by findex";
        try {
            List<TScRpRbillentry1Entity> tScRpRbillentry1EntityList = systemService.findHql(hql0, id0);
            for (TScRpRbillentry1Entity entry : tScRpRbillentry1EntityList) {
                if (StringUtils.isNotEmpty(entry.getAccountId())) {
                    TScSettleacctEntity account = systemService.getEntity(TScSettleacctEntity.class, entry.getAccountId());
                    if (null != account) {
                        entry.setAccountName(account.getName());
                    }
                }
            }
            req.setAttribute("tScRpRbillentry1List", tScRpRbillentry1EntityList);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        String load = req.getParameter("load");
        req.setAttribute("load", load);
        return new ModelAndView("com/qihang/buss/sc/rp/tScRpRbillentry1List");
    }

    /**
     * 加载明细列表[应收表]
     *
     * @return
     */
    @RequestMapping(params = "tScRpRbillentry2List")
    public ModelAndView tScRpRbillentry2List(TScRpRbillEntity tScRpRbill, HttpServletRequest req) {

        //===================================================================================
        //获取参数
        Object id1 = tScRpRbill.getId();
        //===================================================================================
        //查询-应收表
        String hql1 = "from TScRpRbillentry2Entity where 1 = 1 AND fID = ? order by findex";
        try {
            List<TScRpRbillentry2Entity> tScRpRbillentry2EntityList = systemService.findHql(hql1, id1);
            List<TSBillInfoEntity> billInfo = systemService.findHql("from TSBillInfoEntity");
            Map<String, Object> billInfoMap = new HashMap<String, Object>();
            if (billInfo.size() > 0) {
                for (TSBillInfoEntity info : billInfo) {
                    billInfoMap.put(info.getBillId(), info.getBillName());
                }
            }
            for (TScRpRbillentry2Entity entry : tScRpRbillentry2EntityList) {
                String classIdSrc = entry.getClassIdSRC();
                if ("102".equals(classIdSrc)) {
                    entry.setClassIdName("销售订单");
                } else if (StringUtils.isNotEmpty(classIdSrc)) {
                    String value = (String) billInfoMap.get(classIdSrc);
                    entry.setClassIdName(value);
                }
            }
            req.setAttribute("tScRpRbillentry2List", tScRpRbillentry2EntityList);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        String load = req.getParameter("load");
        req.setAttribute("load", load);
        return new ModelAndView("com/qihang/buss/sc/rp/tScRpRbillentry2List");
    }

}
