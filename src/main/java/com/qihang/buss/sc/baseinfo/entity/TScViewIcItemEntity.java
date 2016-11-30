package com.qihang.buss.sc.baseinfo.entity;

import org.apache.tools.ant.taskdefs.Java;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品显示列表视图实体类
 */
@Entity
@Table(name = "v_sc_icitem", schema = "")
public class TScViewIcItemEntity implements java.io.Serializable {

    /**
     * 主表主键
     */
    private String id;
    /**
     *从表主键
     */
    private String entryId;
    /**
     * 编号
     */
    private String number;
    /**
     * 名称
     */
    private String name;
    /**
     * 简称
     */
    private String shortName;
    /**
     * 规格
     */
    private String model;
    /**
     * 重量
     */
    private BigDecimal weight;
    /**
     * 保质期
     */
    private Integer kfPeriod;
    /**
     * 保质期类型
     */
    private String kfDateType;
    /**
     * 默认仓库
     */
    private String stockID;
    /**
     * 主供应商
     */
    private String supplierID;
    /**
     * 产家
     */
    private String factory;
    /**
     * 产地
     */
    private String producingPlace;
    /**
     * 指导价
     */
    private BigDecimal costPrice;
    /**
     * 进项税
     */
    private BigDecimal taxRateIn;
    /**
     * 销项税
     */
    private BigDecimal taxRateOut;
    /**
     * 存货类型
     */
    private String itemType;
    /**
     * 计价方法
     */
    private String track;
    /**
     * 按保质期管理
     */
    private String iskfPeriod;
    /**
     * 启用批次管理
     */
    private String batchManager;
    /**
     * 可组装/拆卸
     */
    private String isAssembly;
    /**
     * 助记码
     */
    private String shortNumber;
    /**
     * 批准文号
     */
    private String cultureNo;
    /**
     * 品牌
     */
    private String brandID;
    /**
     * 备注
     */
    private String note;
    /**
     * 条形码
     */
    private String barCode;
    /**
     * 单位
     */
    private String unitID;
    /**
     * 零售价
     */
    private BigDecimal lsRetailPrice;
    /**
     * 会员价
     */
    private BigDecimal lsMemberPrice;
    /**
     * 默认采购单位
     */
    private Integer defaultCG;
    /**
     * 默认销售单位
     */
    private Integer defaultXS;
    /**
     * 默认仓存单位
     */
    private Integer defaultCK;
    /**
     * 默认生产单位
     */
    private Integer defaultSC;
    /**
     * 采购单价
     */
    private BigDecimal cgPrice;
    /**
     * 一级采购价
     */
    private BigDecimal cgPrice1;
    /**
     * 二级采购价
     */
    private BigDecimal cgPrice2;
    /**
     * 三级采购价
     */
    private BigDecimal cgPrice3;
    /**
     * 采购限价
     */
    private BigDecimal cgLimitPrice;
    /**
     * 最近采购价
     */
    private BigDecimal cgLatestPrice;
    /**
     * 销售单价
     */
    private BigDecimal xsPrice;
    /**
     * 一级销售价
     */
    private BigDecimal xsPrice1;
    /**
     * 二级销售价
     */
    private BigDecimal xsPrice2;
    /**
     * 三级销售价
     */
    private BigDecimal xsPrice3;
    /**
     * 销售限价
     */
    private BigDecimal xsLimitPrice;
    /**
     * 最近销售价
     */
    private BigDecimal xsLatestPrice;
    /**
     * 主表创建的时间
     *
     */
    private Date createDate;

    /**
     * 引用次数
     */
    private Integer count;

    /**
     * 分类ID
     *
     */
    private java.lang.String ParentID;

    /**禁用标记*/
    private java.lang.Integer disabled;

    /**保质期和单位组合**/
    private java.lang.String kfPeriodCon;



    @Id
    @Column(name ="entryId")
    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }
    @Column(name ="id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name ="number")
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Column(name ="Name")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Column(name ="ShortName")
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Column(name ="Model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    @Column(name ="Weight")
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
    @Column(name ="KFPeriod")
    public Integer getKfPeriod() {
        return kfPeriod;
    }

    public void setKfPeriod(Integer kfPeriod) {
        this.kfPeriod = kfPeriod;
    }
    @Column(name ="KFDateType")
    public String getKfDateType() {
        return kfDateType;
    }

    public void setKfDateType(String kfDateType) {
        this.kfDateType = kfDateType;
    }
    @Column(name ="StockID")
    public String getStockID() {
        return stockID;
    }

    public void setStockID(String stockID) {
        this.stockID = stockID;
    }
    @Column(name ="SupplierID")
    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }
    @Column(name ="Factory")
    public String getFactory() {
        return factory;
    }

    public void setFactory(String factory) {
        this.factory = factory;
    }
    @Column(name ="ProducingPlace")
    public String getProducingPlace() {
        return producingPlace;
    }

    public void setProducingPlace(String producingPlace) {
        this.producingPlace = producingPlace;
    }
    @Column(name ="CostPrice")
    public BigDecimal getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(BigDecimal costPrice) {
        this.costPrice = costPrice;
    }
    @Column(name ="TaxRateIn")
    public BigDecimal getTaxRateIn() {
        return taxRateIn;
    }

    public void setTaxRateIn(BigDecimal taxRateIn) {
        this.taxRateIn = taxRateIn;
    }
    @Column(name ="TaxRateOut")
    public BigDecimal getTaxRateOut() {
        return taxRateOut;
    }

    public void setTaxRateOut(BigDecimal taxRateOut) {
        this.taxRateOut = taxRateOut;
    }
    @Column(name ="ItemType")
    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    @Column(name ="Track")
    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }
    @Column(name ="ISKFPeriod")
    public String getIskfPeriod() {
        return iskfPeriod;
    }

    public void setIskfPeriod(String iskfPeriod) {
        this.iskfPeriod = iskfPeriod;
    }
    @Column(name ="BatchManager")
    public String getBatchManager() {
        return batchManager;
    }

    public void setBatchManager(String batchManager) {
        this.batchManager = batchManager;
    }

    @Column(name ="ISAssembly")
    public String getIsAssembly() {
        return isAssembly;
    }

    public void setIsAssembly(String isAssembly) {
        this.isAssembly = isAssembly;
    }
    @Column(name ="ShortNumber")
    public String getShortNumber() {
        return shortNumber;
    }

    public void setShortNumber(String shortNumber) {
        this.shortNumber = shortNumber;
    }
    @Column(name ="CultureNo")
    public String getCultureNo() {
        return cultureNo;
    }

    public void setCultureNo(String cultureNo) {
        this.cultureNo = cultureNo;
    }
    @Column(name ="BrandID")
    public String getBrandID() {
        return brandID;
    }

    public void setBrandID(String brandID) {
        this.brandID = brandID;
    }
    @Column(name ="Note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    @Column(name ="LSRetailPrice")
    public BigDecimal getLsRetailPrice() {
        return lsRetailPrice;
    }

    public void setLsRetailPrice(BigDecimal lsRetailPrice) {
        this.lsRetailPrice = lsRetailPrice;
    }
    @Column(name ="LSMemberPrice")
    public BigDecimal getLsMemberPrice() {
        return lsMemberPrice;
    }

    public void setLsMemberPrice(BigDecimal lsMemberPrice) {
        this.lsMemberPrice = lsMemberPrice;
    }
    @Column(name ="DefaultCG")
    public Integer getDefaultCG() {
        return defaultCG;
    }

    public void setDefaultCG(Integer defaultCG) {
        this.defaultCG = defaultCG;
    }
    @Column(name ="DefaultXS")
    public Integer getDefaultXS() {
        return defaultXS;
    }

    public void setDefaultXS(Integer defaultXS) {
        this.defaultXS = defaultXS;
    }
    @Column(name ="DefaultCK")
    public Integer getDefaultCK() {
        return defaultCK;
    }

    public void setDefaultCK(Integer defaultCK) {
        this.defaultCK = defaultCK;
    }
    @Column(name ="DefaultSC")
    public Integer getDefaultSC() {
        return defaultSC;
    }

    public void setDefaultSC(Integer defaultSC) {
        this.defaultSC = defaultSC;
    }
    @Column(name ="CGPrice")
    public BigDecimal getCgPrice() {
        return cgPrice;
    }

    public void setCgPrice(BigDecimal cgPrice) {
        this.cgPrice = cgPrice;
    }
    @Column(name ="CGPrice1")
    public BigDecimal getCgPrice1() {
        return cgPrice1;
    }

    public void setCgPrice1(BigDecimal cgPrice1) {
        this.cgPrice1 = cgPrice1;
    }
    @Column(name ="CGPrice2")
    public BigDecimal getCgPrice2() {
        return cgPrice2;
    }

    public void setCgPrice2(BigDecimal cgPrice2) {
        this.cgPrice2 = cgPrice2;
    }
    @Column(name ="CGPrice3")
    public BigDecimal getCgPrice3() {
        return cgPrice3;
    }

    public void setCgPrice3(BigDecimal cgPrice3) {
        this.cgPrice3 = cgPrice3;
    }
    @Column(name ="CGLimitPrice")
    public BigDecimal getCgLimitPrice() {
        return cgLimitPrice;
    }

    public void setCgLimitPrice(BigDecimal cgLimitPrice) {
        this.cgLimitPrice = cgLimitPrice;
    }
    @Column(name ="CGLatestPrice")
    public BigDecimal getCgLatestPrice() {
        return cgLatestPrice;
    }

    public void setCgLatestPrice(BigDecimal cgLatestPrice) {
        this.cgLatestPrice = cgLatestPrice;
    }
    @Column(name ="XSPrice")
    public BigDecimal getXsPrice() {
        return xsPrice;
    }

    public void setXsPrice(BigDecimal xsPrice) {
        this.xsPrice = xsPrice;
    }
    @Column(name ="XSPrice1")
    public BigDecimal getXsPrice1() {
        return xsPrice1;
    }

    public void setXsPrice1(BigDecimal xsPrice1) {
        this.xsPrice1 = xsPrice1;
    }
    @Column(name ="XSPrice2")
    public BigDecimal getXsPrice2() {
        return xsPrice2;
    }

    public void setXsPrice2(BigDecimal xsPrice2) {
        this.xsPrice2 = xsPrice2;
    }
    @Column(name ="XSPrice3")
    public BigDecimal getXsPrice3() {
        return xsPrice3;
    }

    public void setXsPrice3(BigDecimal xsPrice3) {
        this.xsPrice3 = xsPrice3;
    }
    @Column(name ="XSLimitPrice")
    public BigDecimal getXsLimitPrice() {
        return xsLimitPrice;
    }

    public void setXsLimitPrice(BigDecimal xsLimitPrice) {
        this.xsLimitPrice = xsLimitPrice;
    }
    @Column(name ="XSLatestPrice")
    public BigDecimal getXsLatestPrice() {
        return xsLatestPrice;
    }

    public void setXsLatestPrice(BigDecimal xsLatestPrice) {
        this.xsLatestPrice = xsLatestPrice;
    }

    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @Column(name = "BarCode")
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }
    @Column(name = "UnitID")
    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }

    @Column(name = "count")
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Column(name = "ParentID")
    public String getParentID() {
        return ParentID;
    }

    public void setParentID(String parentID) {
        ParentID = parentID;
    }

    @Column(name ="DISABLED",nullable=true,length=32)
    public java.lang.Integer getDisabled(){
        return this.disabled;
    }
    public void setDisabled(java.lang.Integer disabled){
        this.disabled = disabled;
    }

    @Transient
    public String getKfPeriodCon() {
        return kfPeriodCon;
    }

    public void setKfPeriodCon(String kfPeriodCon) {
        this.kfPeriodCon = kfPeriodCon;
    }
}
