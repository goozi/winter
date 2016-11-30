package com.qihang.buss.sc.distribution.controller;

import com.qihang.buss.sc.baseinfo.entity.TScOrganizationEntity;
import com.qihang.buss.sc.baseinfo.service.CountCommonServiceI;
import com.qihang.buss.sc.baseinfo.service.TScEmpServiceI;
import com.qihang.buss.sc.baseinfo.service.TScItemTypeServiceI;
import com.qihang.buss.sc.baseinfo.service.TScOrganizationServiceI;
import com.qihang.buss.sc.distribution.entity.TScAptitudeEntity;
import com.qihang.buss.sc.distribution.service.TScAptitudeServiceI;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
import com.qihang.buss.sc.util.AccountUtil;
import com.qihang.buss.sc.util.BillCountUtil;
import com.qihang.buss.sc.util.BillNoGenerate;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.ExceptionUtil;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.ResourceUtil;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.poi.excel.ExcelImportUtil;
import com.qihang.winter.poi.excel.entity.ExportParams;
import com.qihang.winter.poi.excel.entity.ImportParams;
import com.qihang.winter.poi.excel.entity.TemplateExportParams;
import com.qihang.winter.poi.excel.entity.vo.NormalExcelConstants;
import com.qihang.winter.poi.excel.entity.vo.TemplateExcelConstants;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.pojo.base.TSUser;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.web.system.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Controller
 * @Description: 资格审查
 * @date 2016-07-15 10:49:17
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScAptitudeController")
public class TScAptitudeController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TScAptitudeController.class);

    @Autowired
    private TScAptitudeServiceI tScAptitudeService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private TScEmpServiceI tScEmpServiceI;
    @Autowired
    private CountCommonServiceI countCommonService;
    @Autowired
    private TScItemTypeServiceI tScItemTypeService;
    @Autowired
    private TScOrganizationServiceI tScOrganizationService;

    @Autowired
    private UserService userService;

    private String message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * 资格审查列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "tScAptitude")
    public ModelAndView tScAptitude(HttpServletRequest request) {
        request.setAttribute("tranType", Globals.SC_APTITUDE_TRANTYPE);
        return new ModelAndView("com/qihang/buss/sc/distribution/tScAptitudeList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TScAptitudeEntity tScAptitude, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TScAptitudeEntity.class, dataGrid);
        //查询条件组装器
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScAptitude, request.getParameterMap());
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
            //未审核预警数据
            String isWarm = request.getParameter("isWarm");
            if(org.apache.commons.lang.StringUtils.isNotEmpty(isWarm)){
                String userId = ResourceUtil.getSessionUserName().getId();
                TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
                TSDepart depart = systemService.getParentSonInfo(sonInfo);
                Boolean isAudit = userService.isAllowAudit(tScAptitude.getTranType().toString(),userId,depart.getId());
                cq.eq("cancellation",0);
                //判断当前用户是否在多级审核队列中
                if(isAudit){
                    Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId,depart.getId(),tScAptitude.getTranType().toString());
                    if(userAuditLeave.size() > 0){
                        String leaves = "";
                        for(Integer leave : userAuditLeave){
                            leaves += leave+",";
                        }
                        leaves = leaves.substring(0,leaves.length()-1);
                        List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in ("+leaves+")",new Object[]{depart.getId(),tScAptitude.getTranType().toString()});
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
        this.tScAptitudeService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除资格审查
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TScAptitudeEntity tScAptitude, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tScAptitude = systemService.getEntity(TScAptitudeEntity.class, tScAptitude.getId());
        message = "资格审查删除成功";
        try {
            tScAptitudeService.delete(tScAptitude);
            //修改经销商引用次数
            countCommonService.deleteUpdateCount("T_SC_Organization", tScAptitude.getItemID());
            //修改机构引用次数
            countCommonService.deleteUpdateCount("T_SC_SonCompany", tScAptitude.getSonID());
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            //删除待审核预警数据
            systemService.delBillAuditStatus(tScAptitude.getTranType().toString(), tScAptitude.getId());
        } catch (Exception e) {
            e.printStackTrace();
            message = "资格审查删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除资格审查
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "资格审查删除成功";
        try {
            for (String id : ids.split(",")) {
                TScAptitudeEntity tScAptitude = systemService.getEntity(TScAptitudeEntity.class,
                        id
                );
                tScAptitudeService.delete(tScAptitude);
                //修改经销商引用次数
                countCommonService.deleteUpdateCount("T_SC_Organization", tScAptitude.getItemID());
                //修改机构引用次数
                countCommonService.deleteUpdateCount("T_SC_SonCompany", tScAptitude.getSonID());
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "资格审查删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 添加资格审查
     *
     * @param tScAptitude
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TScAptitudeEntity tScAptitude, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "资格审查添加成功";
        try {
            tScAptitude.setTranType(Globals.SC_APTITUDE_TRANTYPE);
            tScAptitude.setBillerID(ResourceUtil.getSessionUserName().getId());
            tScAptitude.setCancellation(Integer.parseInt(Globals.CLOSE_NO));
            TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
            TSDepart departMain = systemService.getParentSonInfo(depart);
            tScAptitude.setSonID(departMain.getId());
            String eligibility = request.getParameter("eligibility");
            if ("0".equals(eligibility)) {
                tScAptitude.setDisabled(Globals.ENABLE_CODE);
            } else {
                tScAptitude.setDisabled(Globals.DISABLED_CODE);
            }
            tScAptitude.setCheckState(Globals.AUDIT_NO);
            tScAptitudeService.save(tScAptitude);

            //修改经销商引用次数
            countCommonService.addUpdateCount("T_SC_Organization", tScAptitude.getItemID());
            //修改机构引用次数
            countCommonService.addUpdateCount("T_SC_SonCompany", tScAptitude.getSonID());

            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            //待审核数据提醒操作
            //systemService.saveBillAuditStatus(tScAptitude.getTranType().toString(), tScAptitude.getId());
        } catch (Exception e) {
            e.printStackTrace();
            message = "资格审查添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新资格审查
     *
     * @param tScAptitude
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TScAptitudeEntity tScAptitude, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "资格审查更新成功";
        TScAptitudeEntity t = tScAptitudeService.get(TScAptitudeEntity.class, tScAptitude.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tScAptitude, t);
            tScAptitudeService.saveOrUpdate(t);
            //修改经销商引用次数
            countCommonService.editUpdateCount("T_SC_Organization", t.getItemID(), tScAptitude.getItemID());
            //修改机构引用次数
            countCommonService.editUpdateCount("T_SC_SonCompany", t.getSonID(), tScAptitude.getSonID());

            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "资格审查更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 资格审查新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TScAptitudeEntity tScAptitude, HttpServletRequest req) {
        /*if (StringUtil.isNotEmpty(tScAptitude.getId())) {
			tScAptitude = tScAptitudeService.getEntity(TScAptitudeEntity.class, tScAptitude.getId());
			req.setAttribute("tScAptitudePage", tScAptitude);
		}*/
        try {
            String sonName = ResourceUtil.getSessionUserName().getCurrentDepart().getDepartname();
            req.setAttribute("sonName", sonName);
            tScAptitude.setBillNo(BillNoGenerate.getBillNo(Globals.SC_QUOTE_TRANTYPE));
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            req.setAttribute("date", sdf.format(new Date()));
            req.setAttribute("tScAptitudePage", tScAptitude);
            String checkDate = "";
            if (StringUtil.isNotEmpty(AccountUtil.getAccountStartDate())) {
                checkDate = sdf.format(AccountUtil.getAccountStartDate());
            }
            String currentUserId = ResourceUtil.getSessionUserName().getId();
            TScOrganizationEntity scOrganizationEntity = systemService.findUniqueByProperty(TScOrganizationEntity.class, "userId", currentUserId);
            String delearType = "";
            if (null != scOrganizationEntity) {
                if (StringUtil.isNotEmpty(scOrganizationEntity.getTypeid())) {
                    delearType = scOrganizationEntity.getTypeid();
                }
            }
            req.setAttribute("delearType", delearType);
            req.setAttribute("checkDate", checkDate);
            //当前用户所在分支机构
            TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
            TSDepart depart = systemService.getParentSonInfo(sonInfo);
            req.setAttribute("sonId", depart.getId());
            req.setAttribute("sonName", depart.getDepartname());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ModelAndView("com/qihang/buss/sc/distribution/tScAptitude-add");
    }

    /**
     * 资格审查编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TScAptitudeEntity tScAptitude, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tScAptitude.getId())) {
            tScAptitude = tScAptitudeService.getEntity(TScAptitudeEntity.class, tScAptitude.getId());

            //经销商
            if (StringUtils.isNotEmpty(tScAptitude.getItemID())) {
                TScOrganizationEntity organizationEntity = tScEmpServiceI.get(TScOrganizationEntity.class, tScAptitude.getItemID());
                if (organizationEntity != null) {
                    //tScAptitude.setDistributor(organizationEntity.getName());
                    req.setAttribute("distributor", organizationEntity.getName());
                }
            }

            String currentUserId = ResourceUtil.getSessionUserName().getId();
            TScOrganizationEntity scOrganizationEntity = systemService.findUniqueByProperty(TScOrganizationEntity.class, "userId", currentUserId);
            String delearType = "";
            if (null != scOrganizationEntity) {
                if (StringUtil.isNotEmpty(scOrganizationEntity.getTypeid())) {
                    delearType = scOrganizationEntity.getTypeid();
                }
            }
            req.setAttribute("delearType", delearType);

            //分支机构
            if (org.apache.commons.lang3.StringUtils.isNotEmpty(tScAptitude.getSonID())) {
                TSDepart dept = systemService.get(TSDepart.class, tScAptitude.getSonID());
                if (null != dept) {
                    req.setAttribute("sonName", dept.getDepartname());
                }
            }

			/*//制单人
			if (org.apache.commons.lang3.StringUtils.isNotEmpty(tScAptitude.getBillerID())) {
				TSBaseUser user = systemService.get(TSBaseUser.class, tScAptitude.getBillerID());
				if (null != user) {
					req.setAttribute("billerName", user.getRealName());
				}
			}*/

            //审核人和审核日期
            List<TSAuditRelationEntity> info = systemService.getAuditInfoList(tScAptitude.getId(), String.valueOf(tScAptitude.getTranType()));
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
            req.setAttribute("tScAptitudePage", tScAptitude);
        }
        return new ModelAndView("com/qihang/buss/sc/distribution/tScAptitude-update");
    }

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "tScAptitudeController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TScAptitudeEntity tScAptitude, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TScAptitudeEntity.class, dataGrid);
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScAptitude, request.getParameterMap());
        List<TScAptitudeEntity> tScAptitudes = this.tScAptitudeService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "资格审查");
        modelMap.put(NormalExcelConstants.CLASS, TScAptitudeEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("资格审查列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tScAptitudes);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TScAptitudeEntity tScAptitude, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "资格审查");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TScAptitudeEntity.class);
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
            try {
                List<TScAptitudeEntity> listTScAptitudeEntitys = ExcelImportUtil.importExcel(file.getInputStream(), TScAptitudeEntity.class, params);
                for (TScAptitudeEntity tScAptitude : listTScAptitudeEntitys) {
                    tScAptitudeService.save(tScAptitude);
                }
                j.setMsg("文件导入成功！");
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
     * 禁用启用
     *
     * @param tScAptitudeEntity
     * @param req
     * @return
     */
    @RequestMapping(params = "doDisable")
    @ResponseBody
    public AjaxJson doDisable(TScAptitudeEntity tScAptitudeEntity, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String message = "操作成功";
        try {
            if (StringUtil.isNotEmpty(tScAptitudeEntity.getId())) {
                TScAptitudeEntity scAptitudeEntity = systemService.getEntity(TScAptitudeEntity.class, tScAptitudeEntity.getId());
                if (scAptitudeEntity.getEligibility() == Globals.ELIGIBILITY_NO) {//如果所选记录不是合格经销商 就把状态改为启用
                    if (scAptitudeEntity.getEligibility() != tScAptitudeEntity.getEligibility()) {//如果当前选择记录的是否合格经销商状态不一致 就修改状态为是
                        scAptitudeEntity.setEligibility(Globals.ELIGIBILITY_YES);// 修改是否合格经销商 是
                        if (scAptitudeEntity.getDisabled() != Globals.ENABLE_CODE) {//如果不是启用状态 修改为启用
                            if (StringUtil.isNotEmpty(scAptitudeEntity.getItemID())) {
                                TScOrganizationEntity tScOrganizationEntity = systemService.get(TScOrganizationEntity.class, scAptitudeEntity.getItemID());
                                if (null != tScOrganizationEntity) {
                                    if (StringUtil.isNotEmpty(tScOrganizationEntity.getUserId())) {
                                        TSUser tsUser = systemService.get(TSUser.class, tScOrganizationEntity.getUserId());
                                        if (null != tsUser) {
                                            //禁用用户
                                            tsUser.setStatus(Globals.User_Normal);
                                            systemService.saveOrUpdate(tsUser);
                                        }
                                    }
                                }
                            }
                            scAptitudeEntity.setDisabled(Globals.ENABLE_CODE);
                        } else {
                            message = "已启用";
                        }
                    }
                } else {
                    if (scAptitudeEntity.getEligibility() != tScAptitudeEntity.getEligibility()) {//如果当前选择记录的是否合格经销商状态不一致 就修改状态为否
                        scAptitudeEntity.setEligibility(Globals.ELIGIBILITY_NO);
                        if (scAptitudeEntity.getDisabled() != Globals.DISABLED_CODE) {//TODO 如果不是禁用状态 修改为禁用 并把对应的用户禁用掉
                            scAptitudeEntity.setDisabled(Globals.DISABLED_CODE);
                            if (StringUtil.isNotEmpty(scAptitudeEntity.getItemID())) {
                                TScOrganizationEntity tScOrganizationEntity = systemService.get(TScOrganizationEntity.class, scAptitudeEntity.getItemID());
                                if (null != tScOrganizationEntity) {
                                    if (StringUtil.isNotEmpty(tScOrganizationEntity.getUserId())) {
                                        TSUser tsUser = systemService.get(TSUser.class, tScOrganizationEntity.getUserId());
                                        if (null != tsUser) {
                                            //禁用用户
                                            tsUser.setStatus(Globals.User_Forbidden);
                                            systemService.saveOrUpdate(tsUser);
                                        }
                                    }
                                }
                            }
                        } else {
                            message = "已禁用";
                        }
                    }
                }
                tScAptitudeService.saveOrUpdate(scAptitudeEntity);
            }

        } catch (Exception e) {
            e.printStackTrace();
            message = "操作失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 审核（反审核）后执行方法
     *
     * @param id
     * @param audit
     * @return
     */
    @RequestMapping(params = "afterAudit")
    @ResponseBody
    public AjaxJson afterAudit(String id, String audit) {
        return tScAptitudeService.afterAudit(id, audit);
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagridSelect")
    public void datagridSelect(TScOrganizationEntity tScOrganization, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        //判断是否进行查询
        TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
        TSDepart depart = systemService.getParentSonInfo(sonInfo);
        String name = tScOrganization.getName();
        Boolean isSelect = false;
        if (org.apache.commons.lang.StringUtils.isNotEmpty(name)) {
            if (name.indexOf("_") > -1) {
                //isSelect = true;
                name = name.replace("_", "");
                tScOrganization.setName("");
            }
        }
        CriteriaQuery cq = new CriteriaQuery(TScOrganizationEntity.class, dataGrid);
        //if(StringUtil.isEmpty(tScOrganization.getSonId())) {
            tScOrganization.setSonId("000");
            /*Set<String> sonIds = systemService.getAllSonId(depart.getId());
            cq.in("sonId",sonIds.toArray());*/
        //}
        List<String> list = new ArrayList<String>();
        if (StringUtil.isNotEmpty(tScOrganization.getParentid())) {
            list = tScItemTypeService.getParentIdFromTree("01", tScOrganization.getParentid(),depart.getId());
            tScOrganization.setParentid(null);

        }

        String currentUserId = ResourceUtil.getSessionUserName().getId();
        TScOrganizationEntity scOrganizationEntity = systemService.findUniqueByProperty(TScOrganizationEntity.class, "userId", currentUserId);
        if (null != scOrganizationEntity) {//根据当前登录用户是审查过的
           String id = "";
            if (StringUtil.isNotEmpty(scOrganizationEntity.getId())) {//根据当前登录经销商的记录id 匹配自己下级经销商
                //根据id 查询资格审查表 如果表中存在 该记录 就不在弹窗列表中显示
                String hqlByDealer = " from TScOrganizationEntity where dealer = ? ";
                List<TScOrganizationEntity> tScOrganizationEntityList = systemService.findHql(hqlByDealer, scOrganizationEntity.getId());
                if (!tScOrganizationEntityList.isEmpty()) {
                    for (TScOrganizationEntity tScOrganizationEntity : tScOrganizationEntityList) {
                        if (StringUtil.isNotEmpty(tScOrganizationEntity.getId())) {
                            TScAptitudeEntity tScAptitudeEntity = systemService.findUniqueByProperty(TScAptitudeEntity.class, "itemID", tScOrganizationEntity.getId());
                            if (null == tScAptitudeEntity) {
                                id += tScOrganizationEntity.getId() + ",";
                            }
                        }
                    }
                }
            }
            String[] ids = null;
            if (!"".equals(id)) {
                if (id.endsWith(",")) {
                    id = id.substring(0, id.lastIndexOf(","));
                    if (id.indexOf(",") > -1) {
                        ids = id.split(",");
                        cq.in("id", ids);
                    } else {
                        cq.eq("id", id);
                    }
                }
            } else {
                cq.eq("id", "test");//如果没有查询出自己所属的下级经销商 弹窗就不显示内容 随便给Id赋值，使列表不出现记录
            }
        } else {//不是经销商用户(管理员)就加载 所有未资格审查的一级经销商
//            cq.eq("typeid", Globals.SC_TYPE_ADEALER);
            List<Object> ids = new ArrayList<Object>();
//            String sql = "select a.id from t_sc_organization a where not exists " +
//                    "(select * from t_sc_aptitude b where a.id = b.itemid) and a.typeid = 'ADealer'";
            String sql = "select a.id from t_sc_organization a where not exists " +
                    "(select * from t_sc_aptitude b where a.id = b.itemid) and a.typeid = '" + Globals.SC_TYPE_ADEALER + "'";
            List<Map<String,Object>> list_ = systemService.findForJdbc(sql, null);
            if(list_.size() > 0) {
                for (int i = 0; i < list_.size(); i++) {
                    ids.add(list_.get(i).get("id"));
                }
                cq.in("id", ids.toArray());
            } else {
                cq.eq("id", "test");//如果没有查询出自己所属的下级经销商 弹窗就不显示内容 随便给Id赋值，使列表不出现记录
            }
        }
        //查询条件组装器
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScOrganization, request.getParameterMap());
        try {
            //自定义追加查询条件
            if (list.size() > 0) {
                cq.in("parentid", list.toArray());
            }
            //如若弹窗查询
            if (isSelect) {
                cq.add(Restrictions.or(
                        Restrictions.like("name", "%" + name + "%"),
                        Restrictions.or(
                                Restrictions.like("number", "%" + name + "%"),
                                Restrictions.or(
                                        Restrictions.like("shortnumber", "%" + name + "%"), Restrictions.like("shortname", "%" + name + "%")))));
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tScOrganizationService.getDataGridReturn(cq, true);
        List<TScOrganizationEntity> organizationEntityList = dataGrid.getResults();
        for (TScOrganizationEntity entity : organizationEntityList) {
            BigDecimal countAmount = BillCountUtil.getReceivableCountByCustomerId(entity.getId());
            entity.setCountAmount(countAmount);
        }
        String tranType = request.getParameter("tranType");
        if (org.apache.commons.lang3.StringUtils.isNotEmpty(tranType)) {
            List<TScOrganizationEntity> result = dataGrid.getResults();
            List<TScOrganizationEntity> newInfo = new ArrayList<TScOrganizationEntity>();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            for (TScOrganizationEntity entity : result) {
                TSAuditRelationEntity info = systemService.getAuditInfo(entity.getId(), tranType);
                if (info != null) {
                    if (1 == info.getIsFinish()) {
                        entity.setStatus(2);
                        entity.setAuditorName(info.getAuditorName());
                        entity.setAuditDate(sdf.format(info.getAuditDate()));
                    } else {
                        entity.setStatus(1);
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
     * 资格审查客户页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goSelectDialog")
    public ModelAndView goSelectDialog(TScOrganizationEntity tScOrganization, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tScOrganization.getName())) {
            req.setAttribute("name", tScOrganization.getName());
        }
        if (tScOrganization.getDisable() == Globals.IS_DEALER) {
            req.setAttribute("isdealer", tScOrganization.getIsDealer());
        }
        if (null != tScOrganization.getIsDealer() && tScOrganization.getIsDealer().equals(Globals.IS_DEALER)) {
            req.setAttribute("isdealer", tScOrganization.getIsDealer());
        }
        return new ModelAndView("com/qihang/buss/sc/distribution/tScOrganizationSelect");
    }
}
