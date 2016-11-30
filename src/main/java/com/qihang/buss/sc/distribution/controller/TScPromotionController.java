
package com.qihang.buss.sc.distribution.controller;

import com.qihang.buss.sc.baseinfo.entity.TScDepartmentEntity;
import com.qihang.buss.sc.baseinfo.entity.TScEmpEntity;
import com.qihang.buss.sc.baseinfo.entity.TScIcitemEntity;
import com.qihang.buss.sc.baseinfo.entity.TScItemPriceEntity;
import com.qihang.buss.sc.baseinfo.service.CountCommonServiceI;
import com.qihang.buss.sc.distribution.entity.*;
import com.qihang.buss.sc.distribution.page.TScPromotionPage;
import com.qihang.buss.sc.distribution.service.TScPromotionServiceI;
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
import com.qihang.winter.web.system.pojo.base.TSBaseUser;
import com.qihang.winter.web.system.pojo.base.TSDepart;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Controller
 * @Description: 买一送一促销单
 * @date 2016-08-22 17:49:49
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScPromotionController")
public class TScPromotionController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TScPromotionController.class);

    @Autowired
    private TScPromotionServiceI tScPromotionService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private CountCommonServiceI countCommonService;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private UserService userService;
    /**
     * 买一送一促销单列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "tScPromotion")
    public ModelAndView tScPromotion(HttpServletRequest request) {
        try{
            TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
            TSDepart depart = systemService.getParentSonInfo(sonInfo);
            List<TSAuditEntity> tsAuditEntityList = systemService.findHql("from TSAuditEntity where billId=? and sonId = ?", new Object[]{Globals.SC_PROMOTION_TRANTYPE.toString(), depart.getId()});
            if(tsAuditEntityList.size() > 0) {
                TSAuditEntity auditEntity = tsAuditEntityList.get(0);
                if (null != auditEntity) {
                    Integer isEdit = auditEntity.getIsEdit();
                    request.setAttribute("isEdit", isEdit);
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String checkDate = sdf.format(AccountUtil.getAccountStartDate());
            request.setAttribute("checkDate", checkDate);
        }catch (Exception e){
            e.printStackTrace();
        }
        //TODO 账套期间
        //String checkDate = "2016-09-30";
        //request.setAttribute("checkDate", checkDate);
        return new ModelAndView("com/qihang/buss/sc/distribution/tScPromotionViewList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TScPromotionViewEntity tScPromotion, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TScPromotionViewEntity.class, dataGrid);
        //查询条件组装器
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScPromotion);
        try {
            //自定义追加查询条件

            //单据日期
            String query_date_begin = request.getParameter("date_begin");
            String query_date_end = request.getParameter("date_end");

            //起始日期
            String query_begDate_begin = request.getParameter("begDate_begin");
            String query_begDate_end = request.getParameter("begDate_end");

            //结束日期
            String query_endDate_begin = request.getParameter("endDate_begin");
            String query_endDate_end = request.getParameter("endDate_end");

            //审核日期
            String query_checkDate_begin = request.getParameter("checkDate_begin");
            String query_checkDate_end = request.getParameter("checkDate_end");

            String query_auditDate_begin = request.getParameter("auditDate_begin");
            String query_auditDate_end = request.getParameter("auditDate_end");

            if (StringUtil.isNotEmpty(query_date_begin)) {
                cq.ge("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_begin));
            }
            if (StringUtil.isNotEmpty(query_date_end)) {
                cq.le("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_end));
            }

            if (StringUtil.isNotEmpty(query_begDate_begin)) {
                cq.ge("begDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_begDate_begin));
            }
            if (StringUtil.isNotEmpty(query_begDate_end)) {
                cq.le("begDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_begDate_end));
            }

            if (StringUtil.isNotEmpty(query_endDate_begin)) {
                cq.ge("endDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_endDate_begin));
            }
            if (StringUtil.isNotEmpty(query_endDate_end)) {
                cq.le("endDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_endDate_end));
            }

            if (StringUtil.isNotEmpty(query_checkDate_begin)) {
                cq.ge("checkDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_checkDate_begin));
            }
            if (StringUtil.isNotEmpty(query_checkDate_end)) {
                cq.le("checkDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_checkDate_end));
            }

            if (StringUtil.isNotEmpty(query_auditDate_begin)) {
                cq.ge("auditDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_auditDate_begin));
            }
            if (StringUtil.isNotEmpty(query_auditDate_end)) {
                cq.le("auditDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_auditDate_end));
            }
            String isWarm = request.getParameter("isWarm");
            if(org.apache.commons.lang.StringUtils.isNotEmpty(isWarm)) {
                String userId = ResourceUtil.getSessionUserName().getId();
                TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
                TSDepart depart = systemService.getParentSonInfo(sonInfo);
                Boolean isAudit = userService.isAllowAudit(tScPromotion.getTranType().toString(), userId, depart.getId());
                cq.eq("cancellation",0);
                //判断当前用户是否在多级审核队列中
                if (isAudit) {
                    Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId, depart.getId(), tScPromotion.getTranType().toString());
                    if (userAuditLeave.size() > 0) {
                        String leaves = "";
                        for (Integer leave : userAuditLeave) {
                            leaves += leave + ",";
                        }
                        leaves = leaves.substring(0, leaves.length() - 1);
                        List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in (" + leaves + ")", new Object[]{depart.getId(), tScPromotion.getTranType().toString()});
                        if (billInfo.size() > 0) {
                            List<String> idArr = new ArrayList<String>();
                            for (TScBillAuditStatusEntity entity : billInfo) {
                                idArr.add(entity.getBillId());
                            }
                            cq.in("id", idArr.toArray());
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
        this.tScPromotionService.getDataGridReturn(cq, true);
        String tranType = Globals.SC_PROMOTION_TRANTYPE +"";
        if (StringUtils.isNotEmpty(tranType)) {
            List<TScPromotionViewEntity> result = dataGrid.getResults();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (TScPromotionViewEntity entity : result) {
                TSAuditRelationEntity info = systemService.getAuditInfo(entity.getId(), tranType);
                if (info != null) {
                    if (1 == info.getIsFinish()) {
                        entity.setStatus(2);
                        entity.setAuditorName(info.getAuditorName());
                        entity.setAuditDate(sdf.format(info.getAuditDate()));
                    } else {
                        entity.setCheckState(1);
                        entity.setAuditorName(info.getAuditorName());
                        entity.setAuditDate(sdf.format(info.getAuditDate()));
                    }
                } else {
                    entity.setStatus(0);
                }
            }
            dataGrid.setResults(result);
        }
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除买一送一促销单
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TScPromotionEntity tScPromotion, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tScPromotion = systemService.getEntity(TScPromotionEntity.class, tScPromotion.getId());
        String message = "买一送一促销单删除成功";
        try {
            tScPromotionService.delMain(tScPromotion);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            //删除待审核预警数据
            systemService.delBillAuditStatus(tScPromotion.getTranType().toString(), tScPromotion.getId());
        } catch (Exception e) {
            e.printStackTrace();
            message = "买一送一促销单删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除买一送一促销单
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "买一送一促销单删除成功";
        try {
            for (String id : ids.split(",")) {
                TScPromotionEntity tScPromotion = systemService.getEntity(TScPromotionEntity.class, id);
                tScPromotionService.delMain(tScPromotion);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "买一送一促销单删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加买一送一促销单
     *
     * @param tScPromotion
     * @param tScPromotionPage
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TScPromotionEntity tScPromotion, TScPromotionPage tScPromotionPage, HttpServletRequest request) {
        List<TScPromotiongiftsentryEntity> tScPromotiongiftsentryList = tScPromotionPage.getTScPromotiongiftsentryList();
        List<TScPromotiongoodsentryEntity> tScPromotiongoodsentryList = tScPromotionPage.getTScPromotiongoodsentryList();
        AjaxJson j = new AjaxJson();
        String message = "添加成功";
        try {
            int tranType = Globals.SC_PROMOTION_TRANTYPE;
            tScPromotion.setCheckState(Globals.AUDIT_NO);
            tScPromotion.setTranType(tranType);
            tScPromotion.setBillerID(ResourceUtil.getSessionUserName().getId());
            tScPromotion.setCancellation(Globals.CANCELLATION_NO);
            TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
            TSDepart departMain = systemService.getParentSonInfo(depart);
            tScPromotion.setSonID(departMain.getId());

            tScPromotionService.addMain(tScPromotion, tScPromotiongiftsentryList, tScPromotiongoodsentryList);
            /**
             * 修改引用次数
             */
            //经办人
            countCommonService.addUpdateCount("t_sc_emp", tScPromotion.getEmpID());
            //部门
            countCommonService.addUpdateCount("t_sc_department", tScPromotion.getDeptID());
            //分支机构
            countCommonService.addUpdateCount("T_SC_SonCompany", tScPromotion.getSonID());
            //赠品
            for (TScPromotiongiftsentryEntity entity1 :tScPromotiongiftsentryList){
                countCommonService.addUpdateCount("t_sc_icitem",entity1.getItemID());
            }
            //商品
            for (TScPromotiongoodsentryEntity entity2 :tScPromotiongoodsentryList){
                countCommonService.addUpdateCount("t_sc_icitem",entity2.getItemID());
            }
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            //待审核数据提醒操作
           // systemService.saveBillAuditStatus(tScPromotion.getTranType().toString(), tScPromotion.getId());
        } catch (Exception e) {
            e.printStackTrace();
            message = "买一送一促销单添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新买一送一促销单
     *
     * @param tScPromotion
     * @param tScPromotionPage
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TScPromotionEntity tScPromotion, TScPromotionPage tScPromotionPage, HttpServletRequest request) {
        List<TScPromotiongiftsentryEntity> tScPromotiongiftsentryList = tScPromotionPage.getTScPromotiongiftsentryList();
        List<TScPromotiongoodsentryEntity> tScPromotiongoodsentryList = tScPromotionPage.getTScPromotiongoodsentryList();
        AjaxJson j = new AjaxJson();
        String message = "更新成功";
        try {
            TScPromotionEntity tScPrcplyEntity = tScPromotionService.get(TScPromotionEntity.class, tScPromotion.getId());
            sessionFactory.getCurrentSession().evict(tScPrcplyEntity);
            tScPromotionService.updateMain(tScPromotion, tScPromotiongiftsentryList, tScPromotiongoodsentryList);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "更新买一送一促销单失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 买一送一促销单新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TScPromotionEntity tScPromotion, HttpServletRequest req) {
        try {
            String sonName = ResourceUtil.getSessionUserName().getCurrentDepart().getDepartname();
            req.setAttribute("sonName", sonName);
            tScPromotion.setBillNo(BillNoGenerate.getBillNo(Globals.SC_QUOTE_TRANTYPE));
            req.setAttribute("tScPromotionPage", tScPromotion);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            req.setAttribute("date", sdf.format(new Date()));
            String checkDate = sdf.format(AccountUtil.getAccountStartDate());
            req.setAttribute("checkDate", checkDate);

            //当前用户所在分支机构
            TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
            TSDepart depart = systemService.getParentSonInfo(sonInfo);
            req.setAttribute("sonId",depart.getId());
            req.setAttribute("sonName",depart.getDepartname());
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ModelAndView("com/qihang/buss/sc/distribution/tScPromotion-add");
    }

    /**
     * 买一送一促销单编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TScPromotionEntity tScPromotion, HttpServletRequest req) {
        try {
            if (StringUtil.isNotEmpty(tScPromotion.getId())) {
                tScPromotion = tScPromotionService.getEntity(TScPromotionEntity.class, tScPromotion.getId());

                //经办人
                if (StringUtils.isNotEmpty(tScPromotion.getEmpID())) {
                    TScEmpEntity entity = systemService.get(TScEmpEntity.class, tScPromotion.getEmpID());
                    if (entity != null) {
                        if (org.apache.commons.lang.StringUtils.isNotEmpty(entity.getName())) {
                            tScPromotion.setEmpName(entity.getName());
                            tScPromotion.setEmpID(entity.getId());
                        }
                    }
                }
                //部门
                if (StringUtils.isNotEmpty(tScPromotion.getDeptID())) {
                    TScDepartmentEntity entity = systemService.get(TScDepartmentEntity.class, tScPromotion.getDeptID());
                    if (entity != null) {
                        if (StringUtils.isNotEmpty(entity.getName())) {
                            tScPromotion.setDeptName(entity.getName());
                            tScPromotion.setDeptID(entity.getId());
                        }
                    }
                }
                //分支机构
                if (StringUtils.isNotEmpty(tScPromotion.getSonID())) {
                    TSDepart dept = systemService.get(TSDepart.class, tScPromotion.getSonID());
                    if (null != dept) {
                        tScPromotion.setSonName(dept.getDepartname());
                    }
                }
                if (StringUtils.isNotEmpty(tScPromotion.getBillerID())) {
                    TSBaseUser user = systemService.get(TSBaseUser.class, tScPromotion.getBillerID());
                    if (null != user) {
                        tScPromotion.setBillerName(user.getRealName());
                    }
                }
                //审核人和审核日期
                List<TSAuditRelationEntity> info = systemService.getAuditInfoList(tScPromotion.getId(), String.valueOf(tScPromotion.getTranType()));
                String auditor = "";
                String auditDate = "";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                for (int i = 0; i < info.size(); i++) {
                    TSAuditRelationEntity entity = info.get(i);
//                if(1 == entity.getIsFinish()){
//                    tScOrder.setCheckState(Globals.AUDIT_FIN+"");
//                }
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
                req.setAttribute("tScPromotionPage", tScPromotion);
                String checkDate = sdf.format(AccountUtil.getAccountStartDate());
                req.setAttribute("checkDate", checkDate);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ModelAndView("com/qihang/buss/sc/distribution/tScPromotion-update");
    }

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "tScPromotionController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TScPromotionEntity tScPromotion, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TScPromotionEntity.class, dataGrid);
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScPromotion, request.getParameterMap());
        List<TScPromotionEntity> tScPromotions = this.tScPromotionService.getListByCriteriaQuery(cq, false);
        //如需动态排除部分列不导出时启用，列名指Excel中文列名
        String[] exclusions = {"排除列名1", "排除列名2"};
        modelMap.put(NormalExcelConstants.FILE_NAME, "买一送一促销单");
        modelMap.put(NormalExcelConstants.CLASS, TScPromotionEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("买一送一促销单列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息", exclusions));
        modelMap.put(NormalExcelConstants.DATA_LIST, tScPromotions);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TScPromotionEntity tScPromotion, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "买一送一促销单");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TScPromotionEntity.class);
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
                ExcelImportResult importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(), TScPromotionEntity.class, params);
                List<TScPromotionEntity> listTScPromotionEntitys = importResult.getList();
                StringBuilder stringBuilder = new StringBuilder("已存在的数据:");
                boolean flag = false;
                if (!importResult.isVerfiyFail()) {
                    for (TScPromotionEntity tScPromotion : listTScPromotionEntitys) {
                        //以下检查导入数据是否重复的语句可视需求启用
                        //Long count = tScPromotionService.getCountForJdbc("这里放检查数据是否重复的SQL语句");
                        //if(count >0) {
                        //flag = true;
                        //stringBuilder.append(tScPromotion.getId()+",");
                        //} else {
                        tScPromotionService.save(tScPromotion);
                        //}
                    }
                    j.setMsg("文件导入成功！");
                    //j.setMsg("文件导入成功！" + (flag? stringBuilder.toString():""));
                } else {
                    String excelPath = "/upload/excelUpload/TScPromotionEntity/" + importResult.getExcelName();
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
     * 加载明细列表[赠品信息]
     *
     * @return
     */
    @RequestMapping(params = "tScPromotiongiftsentryList")
    public ModelAndView tScPromotiongiftsentryList(TScPromotionEntity tScPromotion, HttpServletRequest req) {

        //===================================================================================
        //获取参数
        Object id0 = tScPromotion.getId();
        //===================================================================================
        //查询-赠品信息
        String hql0 = "from TScPromotiongiftsentryEntity where 1 = 1 AND iNTERID = ? ";
        try {
            List<TScPromotiongiftsentryEntity> tScPromotiongiftsentryEntityList = systemService.findHql(hql0, id0);
            for (TScPromotiongiftsentryEntity entity : tScPromotiongiftsentryEntityList) {
                //商品
                if (org.apache.commons.lang.StringUtils.isNotEmpty(entity.getItemID())) {
                    TScIcitemEntity icitemEntity = systemService.get(TScIcitemEntity.class, entity.getItemID());
                    if (icitemEntity != null) {
                        if (org.apache.commons.lang.StringUtils.isNotEmpty(icitemEntity.getNumber())) {
                            entity.setGiftNumber(icitemEntity.getNumber());
                        }
                        if (org.apache.commons.lang.StringUtils.isNotEmpty(icitemEntity.getName())) {
                            entity.setGiftName(icitemEntity.getName());
                        }
                        if (org.apache.commons.lang.StringUtils.isNotEmpty(icitemEntity.getModel())) {
                            entity.setGiftModel(icitemEntity.getModel());
                        }
                    }
                }
                //单位对应的条码
                if (org.apache.commons.lang.StringUtils.isNotEmpty(entity.getUnitID())) {
                    TScItemPriceEntity itemPriceEntity = systemService.get(TScItemPriceEntity.class, entity.getUnitID());
                    if (itemPriceEntity != null) {
                        if (org.apache.commons.lang.StringUtils.isNotEmpty(itemPriceEntity.getBarCode())) {
                            entity.setGiftBarCode(itemPriceEntity.getBarCode());
                        }
                    }
                }
            }
            req.setAttribute("tScPromotiongiftsentryList", tScPromotiongiftsentryEntityList);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return new ModelAndView("com/qihang/buss/sc/distribution/tScPromotiongiftsentryList");
    }

    /**
     * 加载明细列表[商品信息]
     *
     * @return
     */
    @RequestMapping(params = "tScPromotiongoodsentryList")
    public ModelAndView tScPromotiongoodsentryList(TScPromotionEntity tScPromotion, HttpServletRequest req) {

        //===================================================================================
        //获取参数
        Object id1 = tScPromotion.getId();
        //===================================================================================
        //查询-品信息
        String hql1 = "from TScPromotiongoodsentryEntity where 1 = 1 AND iNTERID = ? ";
        try {
            List<TScPromotiongoodsentryEntity> tScPromotiongoodsentryEntityList = systemService.findHql(hql1, id1);
            for (TScPromotiongoodsentryEntity entity : tScPromotiongoodsentryEntityList) {
                //商品
                if (org.apache.commons.lang.StringUtils.isNotEmpty(entity.getItemID())) {
                    TScIcitemEntity icitemEntity = systemService.get(TScIcitemEntity.class, entity.getItemID());
                    if (icitemEntity != null) {
                        if (org.apache.commons.lang.StringUtils.isNotEmpty(icitemEntity.getNumber())) {
                            entity.setGoodsNumber(icitemEntity.getNumber());
                        }
                        if (org.apache.commons.lang.StringUtils.isNotEmpty(icitemEntity.getName())) {
                            entity.setGoodsName(icitemEntity.getName());
                        }
                        if (org.apache.commons.lang.StringUtils.isNotEmpty(icitemEntity.getModel())) {
                            entity.setGoodsModel(icitemEntity.getModel());
                        }
                    }
                }
                //单位对应的条码
                if (org.apache.commons.lang.StringUtils.isNotEmpty(entity.getUnitID())) {
                    TScItemPriceEntity itemPriceEntity = systemService.get(TScItemPriceEntity.class, entity.getUnitID());
                    if (itemPriceEntity != null) {
                        if (org.apache.commons.lang.StringUtils.isNotEmpty(itemPriceEntity.getBarCode())) {
                            entity.setGoodsBarCode(itemPriceEntity.getBarCode());
                        }
                    }
                }
            }
            req.setAttribute("tScPromotiongoodsentryList", tScPromotiongoodsentryEntityList);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return new ModelAndView("com/qihang/buss/sc/distribution/tScPromotiongoodsentryList");
    }

    /**
     * 商品选择页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goSelectDialog")
    public ModelAndView goSelectDialog(TScIcitemEntity tScIcitemEntity, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tScIcitemEntity.getNumber())) {
            req.setAttribute("number", tScIcitemEntity.getNumber());
        }
        return new ModelAndView("com/qihang/buss/sc/distribution/tScIcitemSelect");
    }
}
