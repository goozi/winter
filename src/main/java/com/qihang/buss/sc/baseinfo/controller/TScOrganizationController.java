package com.qihang.buss.sc.baseinfo.controller;

import com.qihang.buss.sc.baseinfo.entity.TScEmpEntity;
import com.qihang.buss.sc.baseinfo.entity.TScItemTypeEntity;
import com.qihang.buss.sc.baseinfo.entity.TScOrganizaionViewEntity;
import com.qihang.buss.sc.baseinfo.entity.TScOrganizationEntity;
import com.qihang.buss.sc.baseinfo.service.TScItemTypeServiceI;
import com.qihang.buss.sc.baseinfo.service.TScOrganizationServiceI;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
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
import com.qihang.winter.tag.vo.datatable.SortDirection;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.pojo.base.TSTypegroup;
import com.qihang.winter.web.system.service.SystemService;
import org.apache.commons.lang3.StringUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Controller
 * @Description: 客户
 * @date 2016-06-08 10:53:43
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScOrganizationController")
public class TScOrganizationController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TScOrganizationController.class);

    @Autowired
    private TScOrganizationServiceI tScOrganizationService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private CommonService commonService;

    @Autowired
    private TScItemTypeServiceI tScItemTypeService;

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    /**
     * 客户列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "tScOrganization")
    public ModelAndView tScOrganization(HttpServletRequest request) {

        return new ModelAndView("com/qihang/buss/sc/baseinfo/tScOrganizationList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TScOrganizaionViewEntity tScOrganization, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String name = tScOrganization.getName();
        String settlementName = tScOrganization.getSettlementName();
        Boolean isSelect = false;
        Boolean isSelectSN = false;
        if(org.apache.commons.lang.StringUtils.isNotEmpty(name)) {
            if (name.indexOf("_") > -1) {
                isSelect = true;
                name = name.replace("_", "");
                tScOrganization.setName("");
            }
            if (name.indexOf(" ") > -1) {
                isSelect = true;
                name = name.replace(" ", "");
                tScOrganization.setName("");
            }

        }
        if(org.apache.commons.lang.StringUtils.isNotEmpty(settlementName)) {
            if (name.indexOf("_") > -1) {
                isSelectSN = true;
                settlementName = settlementName.replace("_", "");
                tScOrganization.setSettlementName("");
            }
            if (settlementName.indexOf(" ") > -1) {
                isSelectSN = true;
                settlementName = settlementName.replace(" ", "");
                tScOrganization.setSettlementName("");
            }

        }
        CriteriaQuery cq = new CriteriaQuery(TScOrganizaionViewEntity.class, dataGrid);
        List<String> list = new ArrayList<String>();
        TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
        TSDepart depart = systemService.getParentSonInfo(sonInfo);
        if (StringUtil.isNotEmpty(tScOrganization.getParentid())) {
            list = tScItemTypeService.getParentIdFromTree("01", tScOrganization.getParentid(),depart.getId());
            tScOrganization.setParentid(null);
        }
        if(isSelect){
            cq.add(Restrictions.or(
                    Restrictions.like("name", "%" + name + "%"),
                    Restrictions.or(
                            Restrictions.like("number", "%" + name + "%"),
                            Restrictions.or(
                                    Restrictions.like("shortNumber", "%" + name + "%"), Restrictions.like("shortName", "%" + name + "%")))));
        }
        if(isSelectSN){
            cq.add(Restrictions.like("settlementName", "%" + settlementName + "%"));
        }
        cq.addOrder("createDate", SortDirection.desc);
        //查询条件组装器
        //自定义追加查询条件
        if (list.size() > 0) {
            cq.in("parentid", list.toArray());
        }
        cq.add();
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScOrganization, request.getParameterMap());
        this.tScOrganizationService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }


    /**
     * 删除客户
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TScOrganizationEntity tScOrganization, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tScOrganization = systemService.getEntity(TScOrganizationEntity.class, tScOrganization.getId());
        message = "客户删除成功";
        try {
            if(tScOrganization.getDisable() == Globals.DISABLED_CODE) {
                message = "该信息已禁用，不能删除";
                j.setSuccess(true);
                j.setMsg(message);
                return j;
            }else {
                /** 判断单据是否被引用 **/
                if (tScOrganization.getCount() != null && tScOrganization.getCount() > 0) {
                    message = "该信息被其他数据引用，不能删除!";
                    j.setSuccess(false);
                    j.setMsg(message);
                    return j;
                } else {
                    if(!tScOrganization.getSettlecompany().equals(tScOrganization.getId())){
                        TScOrganizationEntity temp = commonService.get(TScOrganizationEntity.class, tScOrganization.getSettlecompany());
                        if (temp == null || temp.getCount() == null || temp.getCount() == 0) {
                            temp.setCount(0);
                        } else {
                            temp.setCount(temp.getCount().intValue() - 1);
                        }
                        commonService.saveOrUpdate(temp);
                    }
                    tScOrganizationService.delete(tScOrganization);
                    systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "客户删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除客户
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "客户删除成功";
        try {
            for (String id : ids.split(",")) {
                TScOrganizationEntity tScOrganization = systemService.getEntity(TScOrganizationEntity.class,
                        id
                );
                if (tScOrganization.getCount() != null && tScOrganization.getCount() > 0) {
                    message = "部分信息被其他数据引用，不能删除!";
                    j.setSuccess(false);
                    j.setMsg(message);

                } else {
                    tScOrganizationService.delete(tScOrganization);
                }

                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "客户删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 添加客户
     *
     * @param tScOrganization
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TScOrganizationEntity tScOrganization, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "客户添加成功";
        tScOrganization.setDisable(Globals.ENABLE_CODE);
        try {
            //当前分支机构
            TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
            TSDepart depart = systemService.getParentSonInfo(sonInfo);
            if(StringUtil.isNotEmpty(tScOrganization.getName())) {
                String hql = "from TScOrganizationEntity where 1 = 1 AND number = ? and sonId = ?";
                List<TScOrganizationEntity> tScOrganizationEntityList = systemService.findHql(hql, new Object[]{tScOrganization.getNumber(),depart.getId()});
                if ( tScOrganizationEntityList.size() >0) {
                    message = "编号【"+ tScOrganization.getNumber()+"】已存在";
                    j.setSuccess(false);
                    j.setMsg(message);
                    return j;
                }
            }
            //新增时改变分类编号则改变其parentId
            String num = tScOrganization.getNumber();
            int a = num.lastIndexOf(".");
            String number = num.substring(0, a);
            String sql = "select * from t_sc_item_type where number = '" + number + "' and item_class_id ='" + Globals.SC_BASEINFO_ORGANIZATION_TYPE + "'";
            List<TScItemTypeEntity> list = systemService.findListbySql(sql, TScItemTypeEntity.class);
            //如果改变了分类则调用一次编号生成工具
            if(!tScOrganization.getParentid().equals(list.get(0).getId())){
                BillNoGenerate.getBasicInfoBillNo(Globals.SC_BASEINFO_ORGANIZATION_TYPE, number, number);
            }
            if(list.size()>0){
                tScOrganization.setParentid(list.get(0).getId());
            }else{
                message = "分类编号【"+number+"】不存在";
                j.setSuccess(false);
                j.setMsg(message);
                return j;
            }
            // tScOrganizationService.save(tScOrganization);
            tScOrganization.setSonId(depart.getId());
            tScOrganizationService.addMain(tScOrganization);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "客户添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新客户
     *
     * @param tScOrganization
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TScOrganizationEntity tScOrganization, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "客户更新成功";
        //   TScOrganizationEntity t = tScOrganizationService.get(TScOrganizationEntity.class, tScOrganization.getId());
        try {
            //当前分支机构
            TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
            TSDepart depart = systemService.getParentSonInfo(sonInfo);
            if(StringUtil.isNotEmpty(tScOrganization.getName())) {
                String hql = "from TScOrganizationEntity where 1 = 1 AND number = ? and id != ? and sonId = ?";
                List<TScOrganizationEntity> tScOrganizationEntityList = systemService.findHql(hql, new Object[]{tScOrganization.getNumber(),tScOrganization.getId(),depart.getId()});
                if ( tScOrganizationEntityList.size() >0) {
                    message = "编号【"+ tScOrganization.getNumber()+"】已存在";
                    j.setSuccess(false);
                    j.setMsg(message);
                    return j;
                }
            }
            //   MyBeanUtils.copyBeanNotNull2Bean(tScOrganization, t);
            //  tScOrganizationService.saveOrUpdate(t);
            //编辑时若改变编号则改变其parentId
            String num = tScOrganization.getNumber();
            int a = num.lastIndexOf(".");
            String number = num.substring(0, a);
            String sql = "select * from t_sc_item_type where number = '" + number + "' and item_class_id ='" + Globals.SC_BASEINFO_ORGANIZATION_TYPE + "'";
            List<TScItemTypeEntity> list = systemService.findListbySql(sql, TScItemTypeEntity.class);
            if(list.size()>0){
                tScOrganization.setParentid(list.get(0).getId());
            }else{
                message = "分类编号【"+number+"】不存在";
                j.setSuccess(false);
                j.setMsg(message);
                return j;
            }
            tScOrganization.setSonId(depart.getId());
            tScOrganizationService.updateMain(tScOrganization);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "客户更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 客户新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TScOrganizationEntity tScOrganization, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tScOrganization.getId())) {
            tScOrganization = tScOrganizationService.getEntity(TScOrganizationEntity.class, tScOrganization.getId());
            req.setAttribute("tScOrganizationPage", tScOrganization);
        }
        String parentNo = req.getParameter("parentNo");
        req.setAttribute("number", BillNoGenerate.getBasicInfoBillNo(Globals.SC_BASEINFO_ORGANIZATION_TYPE,parentNo,parentNo));
        req.setAttribute("parentid", tScOrganization.getParentid());
        req.setAttribute("tranType", Globals.SC_BASEINFO_ORGANIZATION_TYPE);//基础资料的编号 类型

        return new ModelAndView("com/qihang/buss/sc/baseinfo/tScOrganization-add");
    }

    /**
     * 客户编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TScOrganizationEntity tScOrganization, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tScOrganization.getId())) {
            tScOrganization = tScOrganizationService.getEntity(TScOrganizationEntity.class, tScOrganization.getId());
            TScOrganizationEntity tScOrganizationTemp = tScOrganizationService.getEntity(TScOrganizationEntity.class, tScOrganization.getSettlecompany());
            if(null != tScOrganizationTemp) {
                tScOrganization.setSettlecompanyName(tScOrganizationTemp.getName());
            }
            TScOrganizationEntity tScOrganizationTempo = tScOrganizationService.getEntity(TScOrganizationEntity.class, tScOrganization.getDealer());
            if(null != tScOrganizationTempo) {
                tScOrganization.setDealerName(tScOrganizationTempo.getName());
            }
            if (!tScOrganization.getDefaultoperator().isEmpty()) {
                TScEmpEntity tScEmpEntity = commonService.getEntity(TScEmpEntity.class, tScOrganization.getDefaultoperator());
                if(tScEmpEntity != null)
                 tScOrganization.setDefaultoperatorName(tScEmpEntity.getName());
            }
            req.setAttribute("tScOrganizationPage", tScOrganization);
        }
        req.setAttribute("tranType", Globals.SC_BASEINFO_ORGANIZATION_TYPE);//基础资料的编号 类型
        String load = req.getParameter("load");
        req.setAttribute("load",load);
        return new ModelAndView("com/qihang/buss/sc/baseinfo/tScOrganization-update");
    }

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "tScOrganizationController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TScOrganizaionViewEntity tScOrganization, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TScOrganizaionViewEntity.class, dataGrid);
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScOrganization, request.getParameterMap());
//        List<TSTypegroup> typegroupList = systemService.findByProperty(TSTypegroup.class,"typegroupcode","SC_REGION");
//        List<TSTypegroup> typegroupList = systemService.findByProperty(TSTypegroup.class,"typegroupcode","SC_TYPE_ID");
//        List<TSTypegroup> typegroupList = systemService.findByProperty(TSTypegroup.class,"typegroupcode","SC_TYPE");
//        List<TSTypegroup> typegroupList = systemService.findByProperty(TSTypegroup.class,"typegroupcode","SC_BANK");
//        List<TSTypegroup> typegroupList = systemService.findByProperty(TSTypegroup.class,"typegroupcode","SC_TRADE");
//        List<TSTypegroup> typegroupList = systemService.findByProperty(TSTypegroup.class,"typegroupcode","SC_DELIVER_TYPE");
//        List<TSTypegroup> typegroupList = systemService.findByProperty(TSTypegroup.class,"typegroupcode","sf_01");

        List<TScOrganizaionViewEntity> tScOrganizations = this.tScOrganizationService.getListByCriteriaQuery(cq, false);
        modelMap.put(NormalExcelConstants.FILE_NAME, "客户");
        modelMap.put(NormalExcelConstants.CLASS, TScOrganizaionViewEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("客户列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息"));
        modelMap.put(NormalExcelConstants.DATA_LIST, tScOrganizations);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TScOrganizationEntity tScOrganization, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "客户");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TScOrganizationEntity.class);
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
                List<TScOrganizationEntity> listTScOrganizationEntitys = ExcelImportUtil.importExcel(file.getInputStream(), TScOrganizationEntity.class, params);
                for (TScOrganizationEntity tScOrganization : listTScOrganizationEntitys) {
                    tScOrganizationService.save(tScOrganization);
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
     * 客户新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goSelectDialog")
    public ModelAndView goSelectDialog(TScOrganizationEntity tScOrganization, HttpServletRequest req) {
//        if (StringUtil.isNotEmpty(tScOrganization.getName())) {
//            req.setAttribute("name", tScOrganization.getName().trim());
//        }
//        if(tScOrganization.getDisable() == Globals.IS_DEALER){
//            req.setAttribute("isdealer", tScOrganization.getIsDealer());
//        }
//        if(null != tScOrganization.getIsDealer() && tScOrganization.getIsDealer().equals(Globals.IS_DEALER)){
//            req.setAttribute("isdealer", tScOrganization.getIsDealer());
//        }
//        if(StringUtil.isNotEmpty(tScOrganization.getTypeid())){
//            req.setAttribute("typeid", tScOrganization.getTypeid());
//        }
        return new ModelAndView("com/qihang/buss/sc/baseinfo/tScOrganizationSelect");
    }


    /**
     * 禁用启用
     * @param tScOrganizationEntity
     * @param req
     * @return
     */
    @RequestMapping(params = "doDisable")
    @ResponseBody
    public AjaxJson doDisable(TScOrganizationEntity tScOrganizationEntity, HttpServletRequest req){
        AjaxJson j = new AjaxJson();
        String message = "操作成功";
        try{
            for(String id:tScOrganizationEntity.getId().split(",")){
                TScOrganizationEntity scOrganizationEntity = systemService.getEntity(TScOrganizationEntity.class, id);
                if(tScOrganizationEntity.getDisable() == Globals.ENABLE_CODE){
                    if(tScOrganizationEntity.getDisable() == scOrganizationEntity.getDisable()){
                        message = "该记录已启用";
                    }else{
                        scOrganizationEntity.setDisable(Globals.ENABLE_CODE);
                    }
                }else{
                    if(tScOrganizationEntity.getDisable() == scOrganizationEntity.getDisable()){
                        message = "该记录已禁用";
                    }else{
                        scOrganizationEntity.setDisable(Globals.DISABLED_CODE);
                    }
                }
                systemService.saveOrUpdate(scOrganizationEntity);
            }
        }catch (Exception e){
            e.printStackTrace();
            message = "操作失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }
    /**
     *
     * 唯一性校验
     *
     */
    @RequestMapping(params = "checkName")
    @ResponseBody
    public AjaxJson checkName(String name, String load,String old_name){
        AjaxJson j = tScOrganizationService.checkName(name,load,old_name);
        return j;
    }

    /**
     * easyui AJAX请求数据
     * 弹框选择
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagridSelect")
    public void datagridSelect(TScOrganizaionViewEntity tScOrganization, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        String typeid = request.getParameter("typeid");
        CriteriaQuery cq = new CriteriaQuery(TScOrganizaionViewEntity.class, dataGrid);
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScOrganization, request.getParameterMap());
        cq.eq("disable",0);
        cq.eq("isDealer",1);
        cq.eq("typeId",typeid);
        cq.addOrder("createDate", SortDirection.desc);
        cq.add();
        this.tScOrganizationService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }
}
