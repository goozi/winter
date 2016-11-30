package com.qihang.buss.sc.mall.service.impl;

import com.qihang.buss.sc.baseinfo.entity.*;
import com.qihang.buss.sc.baseinfo.service.TScItemTypeServiceI;
import com.qihang.buss.sc.baseinfo.service.TScOrganizationServiceI;
import com.qihang.buss.sc.mall.controller.HttpTemplate;
import com.qihang.buss.sc.mall.service.OrderInterServiceI;
import com.qihang.buss.sc.sales.entity.TScOrderEntity;
import com.qihang.buss.sc.sales.entity.TScOrderentryEntity;
import com.qihang.buss.sc.sales.service.TScOrderServiceI;
import com.qihang.buss.sc.sysaudit.entity.TSAuditEntity;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
import com.qihang.buss.sc.util.BillNoGenerate;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.DateUtils;
import com.qihang.winter.core.util.PropertiesUtil;
import com.qihang.winter.core.util.ResourceUtil;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.service.SystemService;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.util.DateParseException;
import org.apache.commons.httpclient.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by LenovoM4550 on 2016-09-23.
 */
@Service("orderInterServiceImpl")
@Transactional
public class OrderInterServiceImpl extends CommonServiceImpl implements OrderInterServiceI {
    @Autowired
    private SystemService systemService;
    @Autowired
    private TScItemTypeServiceI tScItemTypeService;
    @Autowired
    private TScOrderServiceI tScOrderService;
    @Autowired
    private TScOrganizationServiceI tScOrganizationService;
    @Override
    public AjaxJson synOrder(String jsonString,PropertiesUtil p) throws DateParseException, ParseException {
      //  systemService.
        StringBuilder strMallorderid = new StringBuilder();
        AjaxJson ajaxJson = new AjaxJson();
        if ("".equals(jsonString.trim())){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("参数错误");
            return ajaxJson;
        }
        //客户信息 写死一条“商城客户” 用订单同步过来插入客户的ID
        TScOrganizationEntity org = tScOrganizationService.findUniqueByProperty(TScOrganizationEntity.class,"name","商城客户");
        if(org == null){
            TScItemTypeEntity scItemTypeEntity = systemService.get(TScItemTypeEntity.class,"10000");
            org = new TScOrganizationEntity();
            org.setTypeid(scItemTypeEntity.getId());
            org.setNumber(DateUtil.formatDate(new Date(),"yyyyMMddHHmmss"));
            org.setName("商城客户");
            systemService.save(org);
        }
        JSONObject jsonObject = JSONObject.fromObject(jsonString);
        if(!"200".equals(jsonObject.getString("code"))){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg(jsonObject.getString("msg"));
            return ajaxJson;
        }
        JSONArray array = jsonObject.getJSONArray("orderlist");
        for(int i=0;i<array.size();i++){
            JSONObject obj = array.getJSONObject(i);
            //判断此单号是否同步过
            strMallorderid.append(obj.getString("mallorderid")).append(",");
            TScOrderEntity order = systemService.findUniqueByProperty(TScOrderEntity.class,"mallOrderID",obj.getString("mallorderid"));
            if(order != null){
                continue;//此订单已同步过 无需同步 跳出本次循环
            }else{
                order = new TScOrderEntity();
            }
            order.setTranType(Globals.SC_ORDER_TRANTYPE);//单据类型
            Date date = DateUtils.parseDate(obj.getString("date"),"yyyy-MM-dd hh:mm:ss");
            order.setDate(date);//单据日期
            order.setBillNo(BillNoGenerate.getBillNo(Globals.SC_ORDER_TRANTYPE + ""));//编号
            order.setItemID(org.getId());//客户ID
            order.setEmpID(org.getDefaultoperator());//经办人
            order.setContact(obj.getString("contact"));//联系人
            order.setMobilePhone(obj.getString("mobilePhone"));//手机号码
            order.setAddress(obj.getString("address"));//联系地址
            order.setMall(2);//订单来源
            order.setFetchStyle(obj.getString("fetchStyle"));//付款方式
            order.setMallOrderID(obj.getString("mallorderid"));//商城单号（订单编号）
            order.setCheckState("0");//审核状态
            order.setClosed("0");//关闭标记
            order.setCancellation("0");//作废标记
            order.setAutoFlag("0");//自动关闭标记

            Date fetchDate = DateUtils.parseDate( obj.getString("fetchDate"),"yyyy-MM-dd hh:mm:ss");//交货日期

            BigDecimal strFreight = new BigDecimal(obj.getDouble("freight"));
            order.setFreight(strFreight.setScale(2,BigDecimal.ROUND_HALF_UP));//物流费用

            Object aa = obj.get("weight");
            BigDecimal strWeight = new BigDecimal(obj.getDouble("weight"));
            order.setWeight(strWeight.setScale(2,BigDecimal.ROUND_HALF_UP));//重量

            BigDecimal strAmount = new BigDecimal(obj.getDouble("amount"));
            order.setAmount(strAmount.setScale(2,BigDecimal.ROUND_HALF_UP));//订单金额
            order.setPreAmount(strAmount.setScale(2,BigDecimal.ROUND_HALF_UP));//预收金额
            order.setCheckAmount(strAmount.setScale(2,BigDecimal.ROUND_HALF_UP));//执行金额
            order.setAllAmount(strAmount.setScale(2,BigDecimal.ROUND_HALF_UP));//应收金额
            BigDecimal rebateamount = new BigDecimal(obj.getDouble("rebateamount"));
            order.setRebateAmount(rebateamount.setScale(2,BigDecimal.ROUND_HALF_UP));//优惠金额

            order.setExplanation(obj.getString("explanation"));//摘要(备注)
           // TScOrganizationEntity or = systemService.findUniqueByProperty(TScOrganizationEntity.class,"licence",obj.getString("licence"));
            TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
            TSDepart depart = systemService.getParentSonInfo(sonInfo);
            order.setSonID(depart.getId());//分支机构
            //获取供应商信息
            TScSupplierForIcitemEntity supplier = systemService.findUniqueByProperty(TScSupplierForIcitemEntity.class,"licence",obj.getString("licence"));
            //附表信息
            List<TScOrderentryEntity> tScOrderentryList = new ArrayList<TScOrderentryEntity>();
            JSONArray mallInfo = obj.getJSONArray("orderInfo");
            if(mallInfo != null && mallInfo.size() > 0){
                for (int j = 0; j < mallInfo.size(); j++ ){
                    JSONObject mall = mallInfo.getJSONObject(j);
                    //===============保存商品分类开始=====================================================
                    Boolean bool = true;
                    String itemTypeId = addItemType(mall,bool);
                    if(!bool){
                        ajaxJson.setSuccess(false);
                        ajaxJson.setMsg("商品顶级分类没有设置");
                        return ajaxJson;
                    }
                    //=================保存商品分类结束======================================================
                    //=================保存商品的信息开始====================================================
                    TScIcitemEntity icitem  = systemService.findUniqueByProperty(TScIcitemEntity.class,"number",mall.getString("number"));
                    //获取单位（克）
                    TScMeasureunitToIcItemEntity measure = systemService.findUniqueByProperty(TScMeasureunitToIcItemEntity.class,"name",mall.get("unitName").toString());
                    if(measure == null){
                        measure = new TScMeasureunitToIcItemEntity();
                        measure.setDisabled(0);
                        measure.setName(mall.get("unitName").toString());
                        systemService.save(measure);
                    }
                    String priceId="";//单位ID
                    if(icitem == null){
                        icitem = new TScIcitemEntity();
                        icitem.setName(mall.getString("name"));//商品名称
                        icitem.setNumber(mall.getString("number"));//商品编号
                        icitem.setParentID(itemTypeId);//商品分类
                        icitem.setIskfPeriod("Y");//默认为启用
                        icitem.setKfDateType("0003");//保质期类型默认天
                        icitem.setBatchManager("Y");//默认启用批次管理
                        icitem.setIsAssembly("N");//默认不可组装
                        icitem.setDisabled(mall.getInt("disabled"));//默认启用标记
                        BigDecimal costPrice = new BigDecimal(mall.getDouble("costPrice"));//指导价格
                        icitem.setCostPrice(costPrice.setScale(2,BigDecimal.ROUND_HALF_UP));
                        icitem.setKfPeriod(mall.getInt("KFPeriod"));//保质期
                        icitem.setFactory(mall.getString("factory"));//产家
                        icitem.setProducingPlace(mall.getString("producingPlace"));//产地
                        BigDecimal strMallWeight = new BigDecimal(mall.getDouble("weight"));//重量
                        icitem.setWeight(strMallWeight.setScale(2,BigDecimal.ROUND_HALF_UP));
                        icitem.setEscMallId(mall.getInt("escMallId"));//商品ID
                        if(supplier!= null) {
                            icitem.setSupplieEntity(supplier);//供应商
                        }
                        // 品牌获取品牌code
                        String brand = mall.getString("brand");
                        String typeCode = systemService.typeId( Globals.BRAND_TYPE,brand);
                        icitem.setBrandID(typeCode);

                        systemService.save(icitem);//保存
                        //======================保存商品信息结束===============================================================================================
                        //======================保存商品图片开始================================================================================================
                        TScItemPhotoEntity photo = new TScItemPhotoEntity();
                        photo.setItemID(icitem.getId());
                        photo.setFilePath(mall.getString("imgPath"));//商品图片地址
                        photo.setVersion(0);
                        systemService.save(photo);
                        //======================保存商品图片结束================================================================================================
                        //======================商品价格保存开始===================================================================================================
                        TScItemPriceEntity price = new TScItemPriceEntity();
                        price.setMeasureunitToIcItemEntity(measure);//设置单位
                        price.setPriceToIcItem(icitem);
                        price.setUnitType("0001");//默认为基本”单位类型“
                        price.setDefaultCG(1);//默认采购单位
                        price.setDefaultXS(1);//默认销售单位
                        price.setDefaultCK(1);//默认残酷单位
                        BigDecimal taxPriceEx = new BigDecimal(mall.getDouble("taxPriceEx"));//单价
                        price.setXsPrice(taxPriceEx.setScale(2,BigDecimal.ROUND_HALF_UP));//
                        systemService.save(price);
                        priceId = price.getId();
                        //======================保存商品单价结束======================
                    }else{
                        TScItemPriceEntity price = systemService.findUniqueByProperty(TScItemPriceEntity.class,"priceToIcItem.id",icitem.getId());
                        if(price != null){
                            priceId = price.getId();
                        }
                    }
                    //======================保存订单附表信息开始=================
                    TScOrderentryEntity entry = new TScOrderentryEntity();
                    entry.setVersion(0);//设置版本号
                    entry.setIndexNumber(j+1);//分路号
                    BigDecimal qty = new BigDecimal(mall.getInt("qty"));
                    entry.setQty(qty.setScale(BigDecimal.ROUND_HALF_UP));//数量
                    entry.setBasicQty(qty.setScale(BigDecimal.ROUND_HALF_UP));//基本数量
                    BigDecimal secQty = new BigDecimal(0);
                    entry.setSecQty(secQty.setScale(BigDecimal.ROUND_HALF_UP));//辅助数量

                    entry.setItemID(icitem.getId());
                    entry.setUnitID(priceId);
                    BigDecimal strEntryWeight = new BigDecimal(mall.getDouble("weight"));//重量
                    entry.setWeight(strEntryWeight.setScale(2,BigDecimal.ROUND_HALF_UP));
                    BigDecimal taxPriceEx1 = new BigDecimal(mall.getDouble("taxPriceEx"));//单价
                    entry.setTaxPriceEx(taxPriceEx1.setScale(2,BigDecimal.ROUND_HALF_UP));//单价
                    entry.setMallPrice(taxPriceEx1.setScale(2,BigDecimal.ROUND_HALF_UP));//商城单价

                    BigDecimal taxAmountEx = new BigDecimal(mall.getDouble("taxAmountEx"));//金额
                    entry.setTaxAmountEx(taxAmountEx.setScale(2,BigDecimal.ROUND_HALF_UP));//金额
                    entry.setMallAmount(taxAmountEx.setScale(2,BigDecimal.ROUND_HALF_UP));//商城金额

                    BigDecimal taxPrice = new BigDecimal(mall.getDouble("taxPrice"));//折后单价
                    entry.setTaxPrice(taxPrice.setScale(2,BigDecimal.ROUND_HALF_UP));

                    BigDecimal inTaxAmount = new BigDecimal(mall.getDouble("inTaxAmount"));//折后金额
                    entry.setInTaxAmount(inTaxAmount.setScale(2,BigDecimal.ROUND_HALF_UP));

                    BigDecimal stockQty = new BigDecimal(mall.getInt("stockQty"));//发货数量
                    entry.setStockQty(stockQty.setScale(2,BigDecimal.ROUND_HALF_UP));

                    BigDecimal one100 = new BigDecimal(100);//计算折扣率
                    BigDecimal  discountRate= entry.getInTaxAmount().divide(entry.getQty(),2,BigDecimal.ROUND_HALF_UP).divide(entry.getTaxPriceEx(),2,BigDecimal.ROUND_HALF_UP).multiply(one100.setScale(2,BigDecimal.ROUND_HALF_UP));
                    entry.setDiscountRate(discountRate);

                    BigDecimal coefficient = new BigDecimal(1);//换算率
                    entry.setCoefficient(coefficient.setScale(2,BigDecimal.ROUND_HALF_UP));

                    //entry.setSalesID(mall.getString("sales"));//促销类型
                    entry.setFetchDate(fetchDate);//交货日期
                    tScOrderentryList.add(entry);
                }
            }
            tScOrderService.addMain(order,tScOrderentryList);
            //待审核数据提醒操作
            List<TSAuditEntity> checkIsMore = this.findHql("from TSAuditEntity where billId = ? and sonId=? and isAudit = 1", new Object[]{order.getTranType().toString(), order.getSonID()});
            if(checkIsMore.size() > 0){
                TScBillAuditStatusEntity tScBillAuditStatusEntity = new TScBillAuditStatusEntity();
                tScBillAuditStatusEntity.setSonId(order.getSonID());
                tScBillAuditStatusEntity.setBillId(order.getId());
                tScBillAuditStatusEntity.setTranType(order.getTranType().toString());
                tScBillAuditStatusEntity.setStatus(1);
                super.save(tScBillAuditStatusEntity);
            }
        }
        String strMall = strMallorderid.toString();
        //处理完调用接口返回消息
        String reponse_order_url = p.readProperty("reponse_order_url");
        String jsonMallorderid = strMall.substring(0, strMall.lastIndexOf(","));
//            StringBuilder strb = new StringBuilder();
//            strb.append("{").append("\"mallorders\"").append(":").append("\"").append(jsonMallorderid).append("\"").append("}");
        String result = HttpTemplate.templatePost(reponse_order_url, jsonMallorderid);
        ajaxJson.setSuccess(true);
        ajaxJson.setMsg("同步成功");

        return ajaxJson;
    }
    /**
     * 保存商品类型
     */
    public String addItemType(JSONObject mall,Boolean bool){
        String itemTypeId = "";
        AjaxJson ajaxJson = new AjaxJson();
        //获取商品分类
        //TScItemTypeEntity itemDingType = systemService.get(TScItemTypeEntity.class,"10003");
        TScItemTypeEntity itemDingType =  systemService.findUniqueByProperty(TScItemTypeEntity.class,"itemClassId",Globals.SC_BASEINFO_ICITEM_TYPE);
        if (itemDingType == null){
            bool = false;
        }
        //添加商品一级分类
        Object ptypeName = mall.getString("ptypeName");
        Object parentId = itemDingType.getId();
        List<TScItemTypeEntity> pitemTypeList = systemService.findHql("from TScItemTypeEntity where name=? and  parentId=?",ptypeName,parentId);
        if(pitemTypeList.size() == 0){//判断如果不存在保存一级分类
            TScItemTypeEntity pitemType = new TScItemTypeEntity();
            pitemType.setLevel(tScItemTypeService.getLevel(itemDingType.getParentId()) + 1);
            pitemType.setItemClassId(itemDingType.getItemClassId());
            pitemType.setName(mall.getString("ptypeName"));
            pitemType.setParentId(itemDingType.getId());
            pitemType.setNumber( BillNoGenerate.getBasicBillNo(itemDingType.getParentId(),itemDingType.getNumber()));
            pitemType.setCount(0);
            pitemType.setVersion(0);
            tScItemTypeService.save(pitemType);
            //保存二级分类
            TScItemTypeEntity typeName = new TScItemTypeEntity();
            typeName.setLevel(tScItemTypeService.getLevel(pitemType.getParentId()) + 1);
            typeName.setItemClassId(pitemType.getItemClassId());
            typeName.setName(mall.getString("typeName"));
            typeName.setParentId(pitemType.getId());
            pitemType.setNumber( BillNoGenerate.getBasicBillNo(pitemType.getParentId(),pitemType.getNumber()));
            typeName.setCount(0);
            typeName.setVersion(0);
            tScItemTypeService.save(typeName);
            itemTypeId = typeName.getId();
        }else{
            //商品子集分类
            List<TScItemTypeEntity>  typeNameList = systemService.findHql("from TScItemTypeEntity where name = ? and  parentId=?",mall.getString("typeName"),pitemTypeList.get(0).getId());
            if (typeNameList.size() == 0){
                //保存子集分类
                TScItemTypeEntity typeName = new TScItemTypeEntity();
                typeName.setLevel(tScItemTypeService.getLevel(pitemTypeList.get(0).getParentId()) + 1);
                typeName.setItemClassId(pitemTypeList.get(0).getItemClassId());
                typeName.setName(mall.getString("typeName"));
                typeName.setParentId(pitemTypeList.get(0).getId());
                typeName.setNumber( BillNoGenerate.getBasicBillNo(pitemTypeList.get(0).getParentId(),pitemTypeList.get(0).getNumber()));
                typeName.setCount(0);
                typeName.setVersion(0);
                tScItemTypeService.save(typeName);
                itemTypeId = typeName.getId();
            }else{
                itemTypeId=typeNameList.get(0).getId();
            }
        }
        bool= true;
        return itemTypeId;
    }
}
