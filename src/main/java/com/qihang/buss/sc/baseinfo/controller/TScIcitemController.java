
package com.qihang.buss.sc.baseinfo.controller;

import com.qihang.buss.sc.baseinfo.entity.*;
import com.qihang.buss.sc.baseinfo.page.TScIcitemPage;
import com.qihang.buss.sc.baseinfo.service.*;
import com.qihang.buss.sc.sales.entity.TScCountEntity;
import com.qihang.buss.sc.util.BillNoGenerate;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.ExceptionUtil;
import com.qihang.winter.core.util.PinyinUtil;
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
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.pojo.base.TSType;
import com.qihang.winter.web.system.pojo.base.TSTypegroup;
import com.qihang.winter.web.system.service.SystemService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Controller
 * @Description: 商品主表
 * @date 2016-06-29 11:50:02
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScIcitemController")
public class TScIcitemController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TScIcitemController.class);

    @Autowired
    private TScIcitemServiceI tScIcitemService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private TScStockServiceI tScStockService;
    @Autowired
    private TScSupplierServiceI tScSupplierService;
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private TScMeasureunitServiceI tScMeasureunitService;

    @Autowired
    private TScItemTypeServiceI tScItemTypeService;
    private String message;


    /**
     * 商品主表列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "tScIcitem")
    public ModelAndView tScIcitem(HttpServletRequest request) {
        return new ModelAndView("com/qihang/buss/sc/baseinfo/tScIcitemViewList");
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TScViewIcItemEntity tScIcitem, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TScViewIcItemEntity.class, dataGrid);
        dataGrid.setFooter("weight");
        List<String> list = new ArrayList<String>();
        if (StringUtil.isNotEmpty(tScIcitem.getParentID())) {
            list = tScItemTypeService.getParentIdFromTree("04", tScIcitem.getParentID(),null);
            tScIcitem.setParentID(null);
        }
        //查询条件组装器
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScIcitem);
        try {
            //自定义追加查询条件
            if (list.size() > 0) {
                cq.in("parentID", list.toArray());
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tScIcitemService.getDataGridReturn(cq, true);
        TSTypegroup typegroup = systemService.findUniqueByProperty(TSTypegroup.class,"typegroupcode","SC_DURA_DATE_TYPE");
        List<TSType> typeList = typegroup.getTSTypes();
        List<TScViewIcItemEntity> idList = dataGrid.getResults();
        for (TScViewIcItemEntity v : idList){
            String str = "";
            if(StringUtil.isEmpty(v.getKfDateType())){
                v.setKfPeriodCon("");
            }else {
                for (TSType t : typeList) {
                    if (t.getTypecode().equals(v.getKfDateType())) {
                        str = t.getTypename();
                    }
                }
                v.setKfPeriodCon(v.getKfPeriod()+str);
            }

        }
        dataGrid.setResults(idList);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除商品主表
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TScIcitemEntity tScIcitem, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tScIcitem = systemService.getEntity(TScIcitemEntity.class, tScIcitem.getId());
        String message = "商品主表删除成功";
        try {
            if(tScIcitem!=null && tScIcitem.getStockForIcitemEntity()!=null && StringUtils.isNotEmpty(tScIcitem.getStockForIcitemEntity().getId())){
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_DEL_TYPE);
                countEntity.setOldId(tScIcitem.getStockForIcitemEntity().getId());
                boolean updateIsSuccess = tScStockService.updateStockCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料仓库引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            if(tScIcitem!=null && tScIcitem.getSupplieEntity()!=null && StringUtils.isNotEmpty(tScIcitem.getSupplieEntity().getId())){
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_DEL_TYPE);
                countEntity.setOldId(tScIcitem.getSupplieEntity().getId());
                boolean updateIsSuccess = tScSupplierService.updateSupplierCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料供应商引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            String hql0 = "from TScItemPriceEntity where 1 = 1 AND iTEMID = ? ";
            List<TScItemPriceEntity> tScItemPriceOldList = systemService.findHql(hql0, tScIcitem.getId());
            for (TScItemPriceEntity entity:tScItemPriceOldList){
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_DEL_TYPE);
                countEntity.setOldId(entity.getMeasureunitToIcItemEntity().getId());
                boolean updateIsSuccess = tScMeasureunitService.updateMeasureunitCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料单位引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }

            //判断单据是否被引用
            if (tScIcitem.getCount() != null && tScIcitem.getCount() > 0) {
                message = "该信息被其他数据引用，不能删除";
                j.setSuccess(true);
                j.setMsg(message);
                return j;
            } else {
                tScIcitemService.delMain(tScIcitem);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "商品主表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除商品主表
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        String message = "商品主表删除成功";
        try {
            for (String id : ids.split(",")) {
                TScIcitemEntity tScIcitem = systemService.getEntity(TScIcitemEntity.class, id);
                tScIcitemService.delMain(tScIcitem);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "商品主表删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 添加商品主表
     *
     * @param tScIcitem
     * @param tScIcitemPage
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TScIcitemEntity tScIcitem, TScIcitemPage tScIcitemPage, HttpServletRequest request) {
        List<TScItemPriceEntity> tScItemPriceList = tScIcitemPage.gettScItemPriceList();
        List<TScItemPhotoEntity> tScItemPhotoList = tScIcitemPage.gettScItemPhotoList();
        AjaxJson j = new AjaxJson();
        String message = "添加成功";
        tScIcitem.setCount(0);
        try {
            TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
            TSDepart depart = systemService.getParentSonInfo(sonInfo);
           /* for(TScItemPhotoEntity t : tScItemPhotoList){
                if(StringUtil.isEmpty(t.getFilePath())){
                    j.setSuccess(false);
                    j.setMsg("请上传图片");
                    return j;
                }
            }*/
           //第一行不管有没有上传不理会，从第二行开始如果没有上传图片的就需要给与提示
            for (int i =0;i<tScItemPhotoList.size();i++){
                if(i!=0){
                   if(StringUtil.isEmpty(tScItemPhotoList.get(i).getFilePath())){
                       j.setSuccess(false);
                       j.setMsg("请给第"+(i+1)+"行上传图片");
                       return j;
                   }
                }
            }

            String hqlnumber = "from TScIcitemEntity where 1 = 1 AND number = ? and sonId = ?";
            List<TScIcitemEntity> numberList = systemService.findHql(hqlnumber, new Object[]{tScIcitem.getNumber(),depart.getId()});
            if(numberList.size() > 0){
                message = "编号【"+tScIcitem.getNumber()+"】已存在";
                j.setSuccess(false);
                j.setMsg(message);
                return j;
            }

            //新增时改变分类编号则改变其parentId
            String num = tScIcitemPage.getNumber();
            int a = num.lastIndexOf(".");
            String number = num.substring(0, a);
            String sql = "select * from t_sc_item_type where number = '" + number + "' and item_class_id ='" + Globals.SC_BASEINFO_ICITEM_TYPE + "'";
            List<TScItemTypeEntity> list = systemService.findListbySql(sql, TScItemTypeEntity.class);
            //如果改变了分类则调用一次编号生成工具
            if(!tScIcitem.getParentID().equals(list.get(0).getId())){
                BillNoGenerate.getBasicInfoBillNo(Globals.SC_BASEINFO_ICITEM_TYPE, number, number);
            }
            if(list.size()>0){
                tScIcitem.setParentID(list.get(0).getId());
            }else{
                message = "分类编号【"+number+"】不存在";
                j.setSuccess(false);
                j.setMsg(message);
                return j;
            }
            if (StringUtils.isNotEmpty(tScIcitem.getShortNumber())) {
                String hql0 = "from TScIcitemEntity where 1 = 1 AND shortNumber = ? ";
                List<TScIcitemEntity> icitemEntitiesList = systemService.findHql(hql0, tScIcitem.getShortNumber());
                if (icitemEntitiesList.size() > 0) {
                    message = "助记码不能重复";
                    j.setSuccess(false);
                    j.setMsg(message);
                    return j;
                }

            }
            //默认仓库
            if (StringUtils.isNotEmpty(tScIcitem.getStockID())) {
                TScStockForIcitemEntity stockForIcitemEntity = new TScStockForIcitemEntity();
                stockForIcitemEntity.setId(tScIcitem.getStockID());
                tScIcitem.setStockForIcitemEntity(stockForIcitemEntity);
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_ADD_TYPE);
                countEntity.setOldId(tScIcitem.getStockID());
                boolean updateIsSuccess = tScStockService.updateStockCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料仓库引用次数失败";
                    j.setMsg(message);
                    return j;
                }

            }
            //主供应商
            if (StringUtils.isNotEmpty(tScIcitem.getSupplierID())) {
                TScSupplierForIcitemEntity supplierForIcitemEntity = new TScSupplierForIcitemEntity();
                supplierForIcitemEntity.setId(tScIcitem.getSupplierID());
                tScIcitem.setSupplieEntity(supplierForIcitemEntity);
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_ADD_TYPE);
                countEntity.setOldId(tScIcitem.getSupplierID());
                boolean updateIsSuccess = tScSupplierService.updateSupplierCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料供应商引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            for (TScItemPriceEntity entity:tScItemPriceList){
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_ADD_TYPE);
                countEntity.setOldId(entity.getUnitID());
                boolean updateIsSuccess = tScMeasureunitService.updateMeasureunitCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料单位引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            tScIcitem.setDisabled(0);
            tScIcitem.setSonId(depart.getId());
            tScIcitemService.addMain(tScIcitem, tScItemPriceList, tScItemPhotoList);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "商品主表添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新商品主表
     *
     * @param tScIcitem
     * @param tScIcitemPage
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TScIcitemEntity tScIcitem, TScIcitemPage tScIcitemPage, HttpServletRequest request) {
        List<TScItemPriceEntity> tScItemPriceList = tScIcitemPage.gettScItemPriceList();
        List<TScItemPhotoEntity> tScItemPhotoList = tScIcitemPage.gettScItemPhotoList();
        String stockId = tScIcitem.getStockID();
        String supplierId = tScIcitem.getSupplierID();
        AjaxJson j = new AjaxJson();
        String message = "更新成功";
        try {
            //当前分支机构
            TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
            TSDepart depart = systemService.getParentSonInfo(sonInfo);
            //第一行不管有没有上传不理会，从第二行开始如果没有上传图片的就需要给与提示
            for (int i =0;i<tScItemPhotoList.size();i++){
                if(i!=0){
                    if(StringUtil.isEmpty(tScItemPhotoList.get(i).getFilePath())){
                        j.setSuccess(false);
                        j.setMsg("请给第"+(i+1)+"行上传图片");
                        return j;
                    }
                }
            }

            String hqlnumber = "from TScIcitemEntity where 1 = 1 AND number = ? and id != ? and sonId = ?";
            List<TScIcitemEntity> numberList = systemService.findHql(hqlnumber, new Object[]{tScIcitem.getNumber(),tScIcitem.getId(),depart.getId()});
            if(numberList.size() > 0){
                message = "编号【"+tScIcitem.getNumber()+"】已存在";
                j.setSuccess(false);
                j.setMsg(message);
                return j;
            }
            TScIcitemEntity oldEntity = systemService.get(TScIcitemEntity.class,tScIcitem.getId());
            //修改编号时，实现数据从分类A（被修改）移到分类B（修改后）
            if(!oldEntity.getNumber().equals(tScIcitemPage.getNumber())){
                String num = tScIcitemPage.getNumber();
                //获得修改后的分类编号
                int a = num.lastIndexOf(".");
                String number = num.substring(0, a);

                String sql = "select * from t_sc_item_type where number = '" + number + "' and item_class_id ='" + Globals.SC_BASEINFO_ICITEM_TYPE + "'";
                List<TScItemTypeEntity> list = systemService.findListbySql(sql,TScItemTypeEntity.class);
                if(list.size()>0){
                    oldEntity.setNumber(tScIcitemPage.getNumber());
                    oldEntity.setParentID(list.get(0).getId());
                }else{
                    message = "分类编号【"+number+"】不存在";
                    j.setSuccess(false);
                    j.setMsg(message);
                    return j;
                }
            }

            if (StringUtils.isNotEmpty(tScIcitem.getShortNumber())) {
                String hql0 = "from TScIcitemEntity where 1 = 1 AND shortNumber = ? AND id <> ? ";
                List<TScIcitemEntity> icitemEntitiesList = systemService.findHql(hql0, tScIcitem.getShortNumber(),tScIcitem.getId());
                if (icitemEntitiesList.size() > 0) {
                    message = "助记码不能重复";
                    j.setSuccess(false);
                    j.setMsg(message);
                    return j;
                }
            }
            if (StringUtils.isNotEmpty(tScIcitem.getStockID())) {
                TScStockForIcitemEntity stockForIcitemEntity = new TScStockForIcitemEntity();
                stockForIcitemEntity.setId(tScIcitem.getStockID());
                tScIcitem.setStockForIcitemEntity(stockForIcitemEntity);
            }
            if (StringUtils.isNotEmpty(tScIcitem.getSupplierID())) {
                TScSupplierForIcitemEntity supplierForIcitemEntity = new TScSupplierForIcitemEntity();
                supplierForIcitemEntity.setId(tScIcitem.getSupplierID());
                tScIcitem.setSupplieEntity(supplierForIcitemEntity);
            }
            sessionFactory.getCurrentSession().evict(oldEntity);
            oldEntity.setSonId(depart.getId());
            tScIcitemService.updateMain(oldEntity, tScItemPriceList, tScItemPhotoList);
            //仓库计数
            if(oldEntity.getStockForIcitemEntity() ==null && StringUtils.isNotEmpty(stockId)){
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_ADD_TYPE);
                countEntity.setOldId(stockId);
                boolean updateIsSuccess = tScStockService.updateStockCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料仓库引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            if(oldEntity.getStockForIcitemEntity()!=null){
                if(StringUtils.isNotEmpty(stockId)){
                    TScCountEntity countEntity = new TScCountEntity();
                    countEntity.setType(Globals.COUNT_UPDATE_TYPE);
                    countEntity.setOldId(oldEntity.getStockForIcitemEntity().getId());
                    countEntity.setNewId(stockId);
                    boolean updateIsSuccess = tScStockService.updateStockCount(countEntity);
                    if (updateIsSuccess == false) {
                        message = "更新基础资料仓库引用次数失败";
                        j.setMsg(message);
                        return j;
                    }
                }else{
                    TScCountEntity countEntity = new TScCountEntity();
                    countEntity.setType(Globals.COUNT_DEL_TYPE);
                    countEntity.setOldId(oldEntity.getStockForIcitemEntity().getId());
                    boolean updateIsSuccess = tScStockService.updateStockCount(countEntity);
                    if (updateIsSuccess == false) {
                        message = "更新基础资料仓库引用次数失败";
                        j.setMsg(message);
                        return j;
                    }

                }
            }
            //供应商计数
            if(oldEntity.getSupplieEntity() == null && StringUtils.isNotEmpty(supplierId)){
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_ADD_TYPE);
                countEntity.setOldId(supplierId);
                boolean updateIsSuccess = tScSupplierService.updateSupplierCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料供应商引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            if(oldEntity.getSupplieEntity()!=null){
                if(StringUtils.isNotEmpty(supplierId)){
                    TScCountEntity countEntity = new TScCountEntity();
                    countEntity.setType(Globals.COUNT_UPDATE_TYPE);
                    countEntity.setOldId(oldEntity.getSupplieEntity().getId());
                    countEntity.setNewId(supplierId);
                    boolean updateIsSuccess = tScSupplierService.updateSupplierCount(countEntity);
                    if (updateIsSuccess == false) {
                        message = "更新基础资料供应商引用次数失败";
                        j.setMsg(message);
                        return j;
                    }
                }else{
                    TScCountEntity countEntity = new TScCountEntity();
                    countEntity.setType(Globals.COUNT_DEL_TYPE);
                    countEntity.setOldId(oldEntity.getSupplieEntity().getId());
                    boolean updateIsSuccess = tScSupplierService.updateSupplierCount(countEntity);
                    if (updateIsSuccess == false) {
                        message = "更新基础资料供应商引用次数失败";
                        j.setMsg(message);
                        return j;
                    }
                }
            }
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "更新商品主表失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 商品主表新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TScIcitemEntity tScIcitem, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tScIcitem.getId())) {
            tScIcitem = tScIcitemService.getEntity(TScIcitemEntity.class, tScIcitem.getId());
            req.setAttribute("tScIcitemPage", tScIcitem);
        }
        String parentNo = req.getParameter("parentNo");

        String cfg_tax_rate = systemService.getConfigByCode("CFG_TAX_RATE");
        tScIcitem.setTaxRateIn(new BigDecimal(cfg_tax_rate));
        tScIcitem.setTaxRateOut(new BigDecimal(cfg_tax_rate));
        tScIcitem.setNumber(BillNoGenerate.getBasicInfoBillNo(Globals.SC_BASEINFO_ICITEM_TYPE, parentNo, parentNo));
        req.setAttribute("parentID", tScIcitem.getParentID());
        req.setAttribute("tranType", Globals.SC_BASEINFO_ICITEM_TYPE);//基础资料的编号 类型
        req.setAttribute("tScIcitemPage", tScIcitem);
        return new ModelAndView("com/qihang/buss/sc/baseinfo/tScIcitem-add");
    }

    /**
     * 商品主表编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TScIcitemEntity tScIcitem, HttpServletRequest req) {
        String load = req.getParameter("load");
        if (StringUtil.isNotEmpty(tScIcitem.getId())) {
            tScIcitem = tScIcitemService.getEntity(TScIcitemEntity.class, tScIcitem.getId());
            if (tScIcitem.getSupplieEntity() != null) {
                if (StringUtils.isNotEmpty(tScIcitem.getSupplieEntity().getId())) {
                    TScSupplierEntity supplierEntity = systemService.get(TScSupplierEntity.class, tScIcitem.getSupplieEntity().getId());
                    if (StringUtils.isNotEmpty(supplierEntity.getName())) {
                        tScIcitem.setSupplierName(supplierEntity.getName());
                        tScIcitem.setSupplierID(supplierEntity.getId());
                    } else {
                        tScIcitem.setSupplierName("");
                    }
                }
            }
            if (tScIcitem.getStockForIcitemEntity() != null) {
                if (StringUtils.isNotEmpty(tScIcitem.getStockForIcitemEntity().getId())) {
                    TScStockEntity stockEntity = systemService.get(TScStockEntity.class, tScIcitem.getStockForIcitemEntity().getId());
                    if (StringUtils.isNotEmpty(stockEntity.getName())) {
                        tScIcitem.setStockName(stockEntity.getName());
                        tScIcitem.setStockID(stockEntity.getId());
                    } else {
                        tScIcitem.setStockName("");
                    }
                }
            }
            String hql1 = "from TScItemPhotoEntity where 1 = 1 AND iTEMID = ? ";
            List<TScItemPhotoEntity> list = systemService.findHql(hql1, tScIcitem.getId());
            if (list.size() > 0) {
                TScItemPhotoEntity photoEntity = list.get(0);
                tScIcitem.setImageSrc(photoEntity.getFilePath());
            }
            req.setAttribute("tScIcitemPage", tScIcitem);
        }
        req.setAttribute("tranType", Globals.SC_BASEINFO_ICITEM_TYPE);//基础资料的编号 类型
        req.setAttribute("load",load);//区别复制页面和编辑页面
        return new ModelAndView("com/qihang/buss/sc/baseinfo/tScIcitem-update");
    }

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "tScIcitemController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TScIcitemEntity tScIcitem, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        CriteriaQuery cq = new CriteriaQuery(TScIcitemEntity.class, dataGrid);
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScIcitem, request.getParameterMap());
        List<TScIcitemEntity> tScIcitems = this.tScIcitemService.getListByCriteriaQuery(cq, false);
        //如需动态排除部分列不导出时启用，列名指Excel中文列名
        String[] exclusions = {"排除列名1", "排除列名2"};
        modelMap.put(NormalExcelConstants.FILE_NAME, "商品主表");
        modelMap.put(NormalExcelConstants.CLASS, TScIcitemEntity.class);
        modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("商品主表列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                "导出信息", exclusions));
        modelMap.put(NormalExcelConstants.DATA_LIST, tScIcitems);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TScIcitemEntity tScIcitem, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "商品主表");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TScIcitemEntity.class);
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
            params.setHeadRows(2);
            params.setNeedSave(true);
            params.setSaveUrl("upload/excelUpload");
            try {
                ExcelImportResult importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(), TScIcitemEntity.class, params);
                List<TScIcitemEntity> listTScIcitemEntitys = importResult.getList();
                StringBuilder stringBuilder = new StringBuilder("已存在的数据:");
                boolean flag = false;
                if (!importResult.isVerfiyFail()) {
                    for (TScIcitemEntity tScIcitem : listTScIcitemEntitys) {
                        //以下检查导入数据是否重复的语句可视需求启用
                        //Long count = tScIcitemService.getCountForJdbc("这里放检查数据是否重复的SQL语句");
                        //if(count >0) {
                        //flag = true;
                        //stringBuilder.append(tScIcitem.getId()+",");
                        //} else {
                        tScIcitemService.save(tScIcitem);
                        //}
                    }
                    j.setMsg("文件导入成功！");
                    //j.setMsg("文件导入成功！" + (flag? stringBuilder.toString():""));
                } else {
                    String excelPath = "/upload/excelUpload/TScIcitemEntity/" + importResult.getExcelName();
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
     * 加载明细列表[单位价格表]
     *
     * @return
     */
    @RequestMapping(params = "tScItemPriceList")
    public ModelAndView tScItemPriceList(TScIcitemEntity tScIcitem, HttpServletRequest req) {

        //===================================================================================
        //获取参数
        Object id0 = tScIcitem.getId();
        //===================================================================================
        //查询-单位价格表
//	    String hql0 = "from TScItemPriceEntity where 1 = 1 AND iTEMID = ? order by unitType ";
        String hql0 = "from TScItemPriceEntity where 1 = 1 AND priceToIcItem.id = ? order by unitType ";
        try {
            List<TScItemPriceEntity> tScItemPriceEntityList = systemService.findHql(hql0, id0);
            req.setAttribute("tScItemPriceList", tScItemPriceEntityList);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        //商品引用次数
        String count = req.getParameter("count");
        req.setAttribute("count",count);
        return new ModelAndView("com/qihang/buss/sc/baseinfo/tScItemPriceList");
    }

    /**
     * 加载明细列表[商品图片表]
     *
     * @return
     */
    @RequestMapping(params = "tScItemPhotoList")
    public ModelAndView tScItemPhotoList(TScIcitemEntity tScIcitem, HttpServletRequest req) {

        //===================================================================================
        //获取参数
        Object id1 = tScIcitem.getId();
        //===================================================================================
        //查询-商品图片表
        String hql1 = "from TScItemPhotoEntity where 1 = 1 AND iTEMID = ? ";
        try {
            List<TScItemPhotoEntity> tScItemPhotoEntityList = systemService.findHql(hql1, id1);
            req.setAttribute("tScItemPhotoList", tScItemPhotoEntityList);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return new ModelAndView("com/qihang/buss/sc/baseinfo/tScItemPhotoList");
    }

    /**
     * 获取简称
     *
     * @param name
     * @return
     */
    @RequestMapping(params = "getShortName")
    @ResponseBody
    public AjaxJson getShortName(@RequestParam(value = "name", required = false) String name) {
        AjaxJson j = new AjaxJson();
        if (StringUtils.isNotEmpty(name)) {
            String pinYinHeadChar = PinyinUtil.getPinYinHeadChar(name);
            j.setSuccess(true);
            j.setMsg("获取拼音首字母成功");
            j.setObj(pinYinHeadChar);
        } else {
            j.setSuccess(false);
            j.setMsg("获取拼音首字母失败");
        }
        return j;
    }

    /**
     * 获取简称
     * 把特殊字符去掉，像账套代码就不需要特殊字符
     * @param name
     * @return
     */
    @RequestMapping(params = "getShortNameDelSpecialChar")
    @ResponseBody
    public AjaxJson getShortNameDelSpecialChar(@RequestParam(value = "name", required = false) String name) {
        AjaxJson j = new AjaxJson();
        if (StringUtils.isNotEmpty(name)) {
            String pinYinHeadChar = PinyinUtil.getPinYinHeadCharDelSpecialChar(name);
            j.setSuccess(true);
            j.setMsg("获取拼音首字母成功");
            j.setObj(pinYinHeadChar);
        } else {
            j.setSuccess(false);
            j.setMsg("获取拼音首字母失败");
        }
        return j;
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
        return new ModelAndView("com/qihang/buss/sc/baseinfo/tScIcitemSelect");
    }

    /**
     * 商品批号库存表
     */
    @RequestMapping(params = "goInventorySelectDialog")
    public ModelAndView goInventorySelectDialog(TScIcitemEntity tScIcitemEntity,HttpServletRequest req){
        if (StringUtil.isNotEmpty(tScIcitemEntity.getNumber())) {
            req.setAttribute("number", tScIcitemEntity.getNumber());
        }
        if(StringUtils.isNotEmpty(tScIcitemEntity.getStockID())){
            req.setAttribute("stockId", tScIcitemEntity.getStockID());
        }
        req.setAttribute("itemId", tScIcitemEntity.getId());
        String batchNo = req.getParameter("batchNo");
        req.setAttribute("batchNo", batchNo);
        String isCheck = req.getParameter("isCheck");
        if(StringUtils.isNotEmpty(isCheck)){
            isCheck = "0";
        }
        String isZero = req.getParameter("isZero");
        if(StringUtils.isNotEmpty(isZero)){
            isZero = "0";
        }
        req.setAttribute("isCheck",isCheck);
        req.setAttribute("isZero",isZero);
        return new ModelAndView("com/qihang/buss/sc/baseinfo/tScIcInventoryBatchnoSelect");
    }

    /**
     * 根据id获得商品单位数据
     */
    @RequestMapping(params = "getItemInfo")
    @ResponseBody
    public AjaxJson getItemInfo(String id,Integer rowIndex,String tranType,String stockId,String batchNo){
        AjaxJson resut = tScIcitemService.getItemInfoById(id, tranType, stockId, batchNo);
        resut.getAttributes().put("rowIndex", rowIndex);
        return resut;
    }

    /**
     * 获取商品单位下拉数据
     * @param id
     * @return
     */
    @RequestMapping(params = "loadItemUnit")
    @ResponseBody
    public List<Map<String,Object>> loadItemUnit(String id){
        List<Map<String,Object>> result = tScIcitemService.loadItemUnit(id);
        return result;
    }

    /**
     * 单位变换时获取相应的换算率和条码
     * @param id
     * @param unitId
     * @return
     */
    @RequestMapping(params = "getChangeInfo")
    @ResponseBody
    public AjaxJson getChangeInfo(String id,String unitId,Integer rowIndex){
        AjaxJson j = tScIcitemService.getChangeInfo(id, unitId, rowIndex);
        return  j;
    }


    @RequestMapping(params = "datagridMain")
    public void datagridMain(TScIcitemEntity tScIcitem, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        tScIcitem.setSonId("000");
        //判断是否进行查询
        String name = tScIcitem.getName();
        Boolean isSelect = false;
        if(org.apache.commons.lang.StringUtils.isNotEmpty(name)) {
            if (name.indexOf("_") > -1) {
                isSelect = true;
                name = name.replace("_", "");
                tScIcitem.setName("");
            }
        }
        CriteriaQuery cq = new CriteriaQuery(TScIcitemEntity.class, dataGrid);
        //查询条件组装器
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScIcitem);
        try {
            //自定义追加查询条件
            //如若弹窗查询
            if(isSelect){
                cq.add(Restrictions.or(
                        Restrictions.like("name", "%" + name + "%"),
                        Restrictions.or(
                                Restrictions.like("number", "%" + name + "%"),
                                Restrictions.or(
                                        Restrictions.like("shortNumber", "%" + name + "%"), Restrictions.like("shortName", "%" + name + "%")))));
            }
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tScIcitemService.getDataGridReturn(cq, true);
        List<TScIcitemEntity> result = dataGrid.getResults();
        if(result.size() > 0) {
            for (TScIcitemEntity item : result){
                if(null != item.getStockForIcitemEntity()){
                    item.setStockSonId(item.getStockForIcitemEntity().getSonID());
                }
            }
        }
        dataGrid.setResults(result);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     *获取商品的单位价格表信息通过id用于销售订购
     * @param id
     * @return
     */
    @RequestMapping(params = "getIcItemPriceInfoByIcitemId")
    @ResponseBody
    public AjaxJson getIcItemPriceInfoByIcitemId(String id,Integer rowIndex){
        AjaxJson j = tScIcitemService.getIcItemPriceInfoByIcitemId(id);
        j.getAttributes().put("rowIndex", rowIndex);
        return j;

    }

    @RequestMapping(params = "getIcItemForQuoteItems")
    @ResponseBody
    public AjaxJson getIcItemForQuoteItems(String id,Integer rowIndex){
        AjaxJson j = tScIcitemService.getIcItemForQuoteItems(id);
        j.getAttributes().put("rowIndex", rowIndex);
        return j;
    }

    @RequestMapping(params = "getIcItemTree")
    @ResponseBody
    public List<Map<String,Object>> getIcItemTree(HttpServletRequest request){
        CriteriaQuery criteriaQuery = new CriteriaQuery(TScItemTypeEntity.class);
        criteriaQuery.eq("itemClassId","04");//商品分类

        criteriaQuery.eq("parentId", Globals.PARENT_ID);
        criteriaQuery.add();
        List<TScItemTypeEntity>  list  = tScItemTypeService.getListByCriteriaQuery(criteriaQuery, false);
        TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
        TSDepart depart = systemService.getParentSonInfo(sonInfo);
        List<TScItemTypeEntity> itemTypeTree = tScItemTypeService.getItemTypeTree("04", list.get(0).getId(),depart.getId());

        List<Map<String,Object>> treeInfo = new ArrayList<Map<String, Object>>();
        String isClose = request.getParameter("isClose");
        for(TScItemTypeEntity itemType : itemTypeTree){
            if(0 == itemType.getDeleted()) {
                setTreeInfo(itemType, treeInfo , isClose);
            }
        }
        return treeInfo;
    }


    public void setTreeInfo(TScItemTypeEntity itemType,List<Map<String,Object>> treeInfo,String isClose){
        Map<String,Object> childinfo = new HashMap<String, Object>();
        childinfo.put("id",itemType.getId());
        childinfo.put("text", itemType.getName());
//        if(StringUtils.isNotEmpty(isClose)){
//            childinfo.put("state","closed");
//        }
        List<Map<String,Object>> children = new ArrayList<Map<String, Object>>();
        if(null != itemType.getChildren() && itemType.getChildren().size() > 0){
            for(TScItemTypeEntity childItemType : itemType.getChildren()){
                setTreeInfo(childItemType,children , isClose);
            }
            childinfo.put("children", children);
            childinfo.put("state","closed");
        }
//        List<TScIcitemEntity> entityList = tScIcitemService.findHql("from TScIcitemEntity where parentID = ?",new Object[]{itemType.getId()});
//        if(entityList.size() > 0){
//            for(TScIcitemEntity entity : entityList){
//                Map<String,Object> leafMap = new HashMap<String, Object>();
//                leafMap.put("id",entity.getId());
//                leafMap.put("text",entity.getName());
//                children.add(leafMap);
//            }
//            childinfo.put("children", children);
//        }
        treeInfo.add(childinfo);
    }



    /**
     * 查出这条数据，设置它禁用，更新保存那条数据
     * @param
     * @param req
     * @return
     */
    @RequestMapping(params = "doDisable", method= RequestMethod.POST)
    @ResponseBody
    public AjaxJson doDisable(@RequestParam("id") String id, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        TScIcitemEntity  tScIcitemEntity=systemService.get(TScIcitemEntity.class,id);
        message="禁用成功";
        try {
            tScIcitemEntity.setDisabled(Globals.DISABLED_CODE);
            tScIcitemService.saveOrUpdate(tScIcitemEntity);
        }catch (Exception e) {
            e.printStackTrace();
            message = "禁用失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return  j;
    }


    /**
     * 查出这条数据，设置它启用，更新保存那条数据
     * @param
     * @param req
     * @return
     */
    @RequestMapping(params = "doEnable", method= RequestMethod.POST)
    @ResponseBody
    public AjaxJson doEnable(@RequestParam("id") String id, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        TScIcitemEntity tScIcitemEntity=systemService.get(TScIcitemEntity.class, id);
        message="启用成功";
        try {
            tScIcitemEntity.setDisabled(Globals.ENABLE_CODE);
            tScIcitemService.saveOrUpdate(tScIcitemEntity);
        }catch (Exception e) {
            e.printStackTrace();
            message = "启用失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return  j;
    }

    @RequestMapping(params = "checkName",method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson checkName(String name,String id){
        AjaxJson j = new AjaxJson();
        String hql = "";
        List<TScIcitemEntity> supplierName = new ArrayList<TScIcitemEntity>();
        if(!StringUtil.isNotEmpty(id)){//新增验证
            hql = " from TScIcitemEntity where 1 = 1 AND name =?";
            supplierName = systemService.findHql(hql,name);
        }else{//编辑的时候验证
            hql = "from  TScIcitemEntity where 1 = 1 AND name =? and id != ?" ;
            supplierName = systemService.findHql(hql,name,id);
        }
        if(supplierName.size() > 0){
            j.setSuccess(true);
        }else{
            j.setSuccess(false);
        }
        return j;
    }
}
