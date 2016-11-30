package com.qihang.buss.sc.financing.controller;

import com.qihang.buss.sc.baseinfo.entity.*;
import com.qihang.buss.sc.baseinfo.service.CountCommonServiceI;
import com.qihang.buss.sc.baseinfo.service.TScSettleacctServiceI;
import com.qihang.buss.sc.baseinfo.service.TScSoncompanyServiceI;
import com.qihang.buss.sc.financing.entity.TScRpAdjustbillFinViewEntity;
import com.qihang.buss.sc.financing.service.TScRpAdjustbillFinViewServiceI;
import com.qihang.buss.sc.rp.entity.TScRpAdjustbillEntity;
import com.qihang.buss.sc.rp.entity.TScRpAdjustbillentryEntity;
import com.qihang.buss.sc.rp.page.TScRpAdjustbillPage;
import com.qihang.buss.sc.rp.service.TScRpAdjustbillServiceI;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
import com.qihang.buss.sc.util.AccountUtil;
import com.qihang.buss.sc.util.BillNoGenerate;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.ResourceUtil;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.poi.excel.entity.ExportParams;
import com.qihang.winter.poi.excel.entity.vo.NormalExcelConstants;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.tag.vo.datatable.SortDirection;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.pojo.base.TSUser;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.web.system.service.UserService;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Title: tScRpBankbillController
 * @Description: 资金调账
 * @author onlineGenerator
 * @date 2016-09-02 14:42:42
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScRpBankbillFinController")
public class TScRpBankbillFinController extends BaseController {

    @Autowired
    private TScRpAdjustbillFinViewServiceI tScRpAdjustbillFinViewServiceI;

    @Autowired
    private TScRpAdjustbillServiceI tScRpAdjustbillService;

    @Autowired
    private TScRpAdjustbillServiceI tScRpAdjustbillServiceI;
    @Autowired
    private TScSettleacctServiceI tScSettleacctService;
    @Autowired
    private SystemService systemService;
    private String message;
    @Autowired
    private TScSoncompanyServiceI tScSoncompanyService;
    @Autowired
    private CountCommonServiceI countCommonService;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserService userService;
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TScRpBankbillFinController.class);

    /**
     * 资金管理模块下->资金调账
     *
     * @param request
     * @return
     */
    @RequestMapping(params = "financingAdjustbill")
    public ModelAndView financingAdjustbill(HttpServletRequest request) {
        request.setAttribute("tranType",Globals.SC_RP_BANKBILL_RECON);
        return new ModelAndView("com/qihang/buss/sc/financing/tScRpAdjustbillList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagrid")
    public void datagrid(TScRpAdjustbillFinViewEntity tScRpAdjustbillFinViewEntity, HttpServletRequest request, HttpServletResponse response,  DataGrid dataGrid) {
        //汇总
        dataGrid.setFooter("allamount,amount");
        CriteriaQuery cq = new CriteriaQuery(TScRpAdjustbillFinViewEntity.class, dataGrid);
        cq.setOrders("billNo","findex");
        //查询条件组装器
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScRpAdjustbillFinViewEntity);
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
            String isWarm = request.getParameter("isWarm");
            if(org.apache.commons.lang.StringUtils.isNotEmpty(isWarm)){
                String userId = ResourceUtil.getSessionUserName().getId();
                TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
                TSDepart depart = systemService.getParentSonInfo(sonInfo);
                Boolean isAudit = userService.isAllowAudit(Globals.SC_RP_BANKBILL_RECON.toString(),userId,depart.getId());
                cq.eq("cancellation",0);
                //判断当前用户是否在多级审核队列中
                if(isAudit){
                    Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId,depart.getId(),Globals.SC_RP_BANKBILL_RECON.toString());
                    if(userAuditLeave.size() > 0){
                        String leaves = "";
                        for(Integer leave : userAuditLeave){
                            leaves += leave+",";
                        }
                        leaves = leaves.substring(0,leaves.length()-1);
                        List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in ("+leaves+")",new Object[]{depart.getId(),Globals.SC_RP_BANKBILL_RECON.toString()});
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
        this.systemService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除
     *
     * @param ids
     * @param request
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "银删除成功";
        try {
            for (String id : ids.split(",")) {
                TScRpAdjustbillEntity tScRpAdjustbillEntity = systemService.getEntity(TScRpAdjustbillEntity.class, id);
                tScRpAdjustbillServiceI.delMain(tScRpAdjustbillEntity);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "银行存取款删除失败";
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
        List<TScSoncompanyEntity> listSon = tScSoncompanyService.loadAll(TScSoncompanyEntity.class);
        String hql0 = "from TScSettleacctEntity where 1 = 1 AND disabled = ?";
        List<TScSettleacctEntity> listSet = tScSettleacctService.findHql(hql0,0);
        TSUser loginUser = ResourceUtil.getSessionUserName();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("billNo", BillNoGenerate.getBillNo(String.valueOf(Globals.SC_RP_BANKBILL_RECON)));
        map.put("billerName", loginUser.getRealName());
        map.put("billerId", loginUser.getId());
        map.put("listSon", listSon);
        map.put("listSet", listSet);
        map.put("date", DateUtil.formatDate(new Date(),"yyyy-MM-dd"));
        map.put("tranType",Globals.SC_RP_BANKBILL_RECON);
        //获取分支机构
        TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
        TSDepart depart = systemService.getParentSonInfo(sonInfo);
        req.setAttribute("sonId",depart.getId());
        req.setAttribute("sonName",depart.getDepartname());
        return new ModelAndView("com/qihang/buss/sc/financing/tScRpAdjustbill-add").addAllObjects(map);
    }

    /**
     * 加载明细列表[资金调账]
     *
     * @return
     */
    @RequestMapping(params = "tScRpAdjustbillentryFinList")
    public ModelAndView tScRpAdjustbillentryList(TScRpAdjustbillEntity tScRpAdjustbill, HttpServletRequest req) {
        //获取参数
        Object id0 = tScRpAdjustbill.getId();
        //查询-应收调账从表
        String hql0 = "from TScRpAdjustbillentryEntity where 1 = 1 AND fID = ? order by findex asc ";
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
        return new ModelAndView("com/qihang/buss/sc/financing/tScRpAdjustbillentryList");
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
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            String accountDate = "";
            if(AccountUtil.getAccountStartDate()!=null){
                accountDate = sdf.format(AccountUtil.getAccountStartDate());
            }
            String data = sdf.format(tScRpAdjustbill.getDate());
            if(accountDate.compareTo(data) > 0) {
                j.setSuccess(false);
                j.setMsg("单据日期不能小于当前账套的当前期");
                return j;
            }
            //当前用户信息
            TSUser loginUser = ResourceUtil.getSessionUserName();
            tScRpAdjustbill.setBillerId(loginUser.getId());
            tScRpAdjustbillService.addMain(tScRpAdjustbill, tScRpAdjustbillentryList);
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
            //审核人
            if (StringUtils.isNotEmpty(tScRpAdjustbill.getCheckerId())) {
                TSUser user = systemService.getEntity(TSUser.class, tScRpAdjustbill.getCheckerId());
                if (null != user) {
                    req.setAttribute("checkUserName",user.getRealName());
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
            //分支机构
            if (StringUtils.isNotEmpty(tScRpAdjustbill.getSonId())) {
                TSDepart sonInfo = systemService.getEntity(TSDepart.class, tScRpAdjustbill.getSonId());
                if (null != sonInfo) {
                    tScRpAdjustbill.setSonName(sonInfo.getDepartname());
                }
            }
            String hql0 = "from TScSettleacctEntity where 1 = 1 AND disabled = ?";
            List<TScSettleacctEntity> listSet = tScSettleacctService.findHql(hql0,0);

            req.setAttribute("listSet",listSet);
            req.setAttribute("tScRpAdjustbillPage", tScRpAdjustbill);
        }
        return new ModelAndView("com/qihang/buss/sc/financing/tScRpAdjustbill-update");
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
    public AjaxJson doUpdate(TScRpAdjustbillEntity tScRpAdjustbill,TScRpAdjustbillPage tScRpAdjustbillPage, HttpServletRequest request) {
        List<TScRpAdjustbillentryEntity> tScRpAdjustbillentryList =  tScRpAdjustbillPage.getTScRpAdjustbillentryList();
        AjaxJson j = new AjaxJson();
        String message = "更新成功";
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            String accountDate = "";
            if(AccountUtil.getAccountStartDate()!=null){
                accountDate = sdf.format(AccountUtil.getAccountStartDate());
            }
            String data = sdf.format(tScRpAdjustbill.getDate());
            if(accountDate.compareTo(data) > 0) {
                j.setSuccess(false);
                j.setMsg("单据日期不能小于当前账套的当前期");
                return j;
            }
            tScRpAdjustbillService.updateMain(tScRpAdjustbill, tScRpAdjustbillentryList);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        }catch(Exception e){
            e.printStackTrace();
            message = "更新应收调账失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TScRpAdjustbillFinViewEntity tScRpAdjustbill, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TScRpAdjustbillFinViewEntity.class, dataGrid);
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScRpAdjustbill, request.getParameterMap());
        List<TScRpAdjustbillFinViewEntity> tScRpRotherbills = this.tScRpAdjustbillService.getListByCriteriaQuery(cq,false);
        //如需动态排除部分列不导出时启用，列名指Excel中文列名
        String[] exclusions = {"排除列名1","排除列名2"};
        modelMap.put(NormalExcelConstants.FILE_NAME,"资金调账");
        modelMap.put(NormalExcelConstants.CLASS,TScRpAdjustbillFinViewEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("资金调账列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
                "导出信息",exclusions));
        modelMap.put(NormalExcelConstants.DATA_LIST,tScRpRotherbills);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }
}
