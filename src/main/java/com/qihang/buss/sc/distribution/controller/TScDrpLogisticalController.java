package com.qihang.buss.sc.distribution.controller;

import com.qihang.buss.sc.baseinfo.service.CountCommonServiceI;
import com.qihang.buss.sc.distribution.entity.TScDrpLogisticalEntity;
import com.qihang.buss.sc.distribution.service.TScDrpLogisticalServiceI;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
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
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.web.system.service.UserService;
import org.apache.log4j.Logger;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Controller
 * @Description: 物流信息表
 * @date 2016-08-12 16:00:03
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScDrpLogisticalController")
public class TScDrpLogisticalController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TScDrpLogisticalController.class);

    @Autowired
    private TScDrpLogisticalServiceI tScDrpLogisticalService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private CountCommonServiceI countCommonService;
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
     * 物流信息表列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "tScDrpLogistical")
    public ModelAndView tScDrpLogistical(HttpServletRequest request) {
        return new ModelAndView("com/qihang/buss/sc/distribution/tScDrpLogisticalList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TScDrpLogisticalEntity tScDrpLogistical, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TScDrpLogisticalEntity.class, dataGrid);
        //查询条件组装器
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScDrpLogistical, request.getParameterMap());
        try {
            //自定义追加查询条件
            //未审核预警数据
            String isWarm = request.getParameter("isWarm");
            if(org.apache.commons.lang.StringUtils.isNotEmpty(isWarm)){
                String userId = ResourceUtil.getSessionUserName().getId();
                TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
                TSDepart depart = systemService.getParentSonInfo(sonInfo);
                Boolean isAudit = userService.isAllowAudit(tScDrpLogistical.getTranType().toString(),userId,depart.getId());
                cq.eq("cancellation",0);
                //判断当前用户是否在多级审核队列中
                if(isAudit){
                    Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId,depart.getId(),tScDrpLogistical.getTranType().toString());
                    if(userAuditLeave.size() > 0){
                        String leaves = "";
                        for(Integer leave : userAuditLeave){
                            leaves += leave+",";
                        }
                        leaves = leaves.substring(0,leaves.length()-1);
                        List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in ("+leaves+")",new Object[]{depart.getId(),tScDrpLogistical.getTranType().toString()});
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
        this.tScDrpLogisticalService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除物流信息表
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TScDrpLogisticalEntity tScDrpLogistical, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tScDrpLogistical = systemService.getEntity(TScDrpLogisticalEntity.class, tScDrpLogistical.getId());
        message = "物流信息表删除成功";
        try {
            tScDrpLogisticalService.delete(tScDrpLogistical);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            //删除待审核预警数据
            systemService.delBillAuditStatus(tScDrpLogistical.getTranType().toString(), tScDrpLogistical.getId());
        } catch (Exception e) {
            e.printStackTrace();
            message = "物流信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除物流信息表
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "物流信息表删除成功";
        try {
            for (String id : ids.split(",")) {
                TScDrpLogisticalEntity tScDrpLogistical = systemService.getEntity(TScDrpLogisticalEntity.class,
                        id
                );
                tScDrpLogisticalService.delete(tScDrpLogistical);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "物流信息表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 添加物流信息表
     *
     * @param tScDrpLogistical
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TScDrpLogisticalEntity tScDrpLogistical, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "物流信息表添加成功";
        try {
            tScDrpLogisticalService.save(tScDrpLogistical);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            //待审核数据提醒操作
           // systemService.saveBillAuditStatus(tScDrpLogistical.getTranType().toString(), tScDrpLogistical.getId());
        } catch (Exception e) {
            e.printStackTrace();
            message = "物流信息表添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新物流信息表
     *
     * @param tScDrpLogistical
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TScDrpLogisticalEntity tScDrpLogistical, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "物流信息表更新成功";
        TScDrpLogisticalEntity t = tScDrpLogisticalService.get(TScDrpLogisticalEntity.class, tScDrpLogistical.getId());
        try {
            MyBeanUtils.copyBeanNotNull2Bean(tScDrpLogistical, t);
            tScDrpLogisticalService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "物流信息表更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 物流信息表新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TScDrpLogisticalEntity tScDrpLogistical, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tScDrpLogistical.getId())) {
            tScDrpLogistical = tScDrpLogisticalService.getEntity(TScDrpLogisticalEntity.class, tScDrpLogistical.getId());
            req.setAttribute("tScDrpLogisticalPage", tScDrpLogistical);
        }
        return new ModelAndView("com/qihang/buss/sc/distribution/tScDrpLogistical-add");
    }

    /**
     * 物流信息表编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TScDrpLogisticalEntity tScDrpLogistical, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tScDrpLogistical.getId())) {
            tScDrpLogistical = tScDrpLogisticalService.getEntity(TScDrpLogisticalEntity.class, tScDrpLogistical.getId());
            req.setAttribute("tScDrpLogisticalPage", tScDrpLogistical);
        }
        return new ModelAndView("com/qihang/buss/sc/distribution/tScDrpLogistical-update");
    }

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "tScDrpLogisticalController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TScDrpLogisticalEntity tScDrpLogistical, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TScDrpLogisticalEntity.class, dataGrid);
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScDrpLogistical, request.getParameterMap());
        List<TScDrpLogisticalEntity> tScDrpLogisticals = this.tScDrpLogisticalService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "物流信息表");
        modelMap.put(NormalExcelConstants.CLASS, TScDrpLogisticalEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("物流信息表列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tScDrpLogisticals);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TScDrpLogisticalEntity tScDrpLogistical, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "物流信息表");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TScDrpLogisticalEntity.class);
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
                List<TScDrpLogisticalEntity> listTScDrpLogisticalEntitys = ExcelImportUtil.importExcel(file.getInputStream(), TScDrpLogisticalEntity.class, params);
                for (TScDrpLogisticalEntity tScDrpLogistical : listTScDrpLogisticalEntitys) {
                    tScDrpLogisticalService.save(tScDrpLogistical);
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
}
