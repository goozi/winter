package com.qihang.buss.sc.sales.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 销售订单显示列表视图
 */
@Entity
@Table(name = "v_sc_order_select", schema = "")
public class TScViewOrderSelectEntity implements java.io.Serializable {

    /**从表的主键*/
    private String entryId;
    /**主表的主键*/
    private String id;
    /**单据编号*/
    private String billNo;
    /**单据时间*/
    private Date date;
    /**客户*/
    private String aitemid;
    /**经办人*/
    private String empid;
    /**部门*/
    private String deptid;
    /**付款方式*/
    private String fetchstyle;
    /**应收金额*/
    private BigDecimal allamount;
    /**预收金额*/
    private BigDecimal preamount;
    /**优惠金额*/
    private BigDecimal rebateamount;
    /**物流费用*/
    private BigDecimal freight;
    /**合计重量*/
    private BigDecimal aweight;
    /**订单来源*/
    private BigDecimal mall;
    /**商城单号*/
    private BigDecimal mallOrderID;
    /**摘要*/
    private String explanation;
    /**联系人*/
    private String contact;
    /**手机号码*/
    private String mobilephone;
    /**电话*/
    private String phone;
    /**联系地址*/
    private String address;
    /**执行金额*/
    private BigDecimal checkamount;
    /**制单人*/
    private String billerid;
    /**审核人*/
    private String auditorName;
    /**审核日期*/
    private String auditDate;
    /**审核状态*/
    private Integer status;
    /**关闭标记*/
    private String closed;
    /**作废标记*/
    private String cancellation;
    /**分支机构*/
    private String sonId;
    /**分录号*/
    private Integer indexNumber;
    /**商品编号*/
    private String itemNumber;
    /**商品名称*/
    private String itemName;
    /**规格*/
    private String model;
    /**条形码*/
    private String barCode;
    /**仓库*/
    private  String stockid;
    /**仓库名称*/
    private String stockName;
    /**单位*/
    private String unitid;
    /**单位名称*/
    private String unitName;
    /**数量*/
    private BigDecimal qty;
    /**换算率*/
    private BigDecimal coefficient;
    /**单价*/
    private BigDecimal taxPriceEx;
    /**金额*/
    private BigDecimal taxAmountEx;
    /**折扣率*/
    private BigDecimal discountRate;
    /**折后单价*/
    private BigDecimal taxPrice;
    /**折后金额*/
    private BigDecimal inTaxAmount;
    /**商城单价*/
    private BigDecimal mallPrice;
    /**商城金额*/
    private BigDecimal mallAmount;
    /**子表重量*/
    private BigDecimal weight;
    /**税率*/
    private BigDecimal taxRate;
    /**税额*/
    private BigDecimal taxAmount;
    /**交货日期*/
    private Date fetchDate;
    /**促销类型*/
    private String  salesID;
    /**赠品标记*/
    private String freegifts;
    /**发货数量*/
    private  BigDecimal stockQty;
    /**源单编号*/
    private String billnoSrc;
    /**源单类型*/
    private String classidSrc;
    /**备注*/
    private String note;
    /**自动关闭标记*/
    private String autoFlag;

    private Integer checkState;//审核状态值
    private Integer isStock;//是否可继续使用
    private String itemid;//商品id

    private String isKfperiod;
    private String batchManager;

    private String aitemName;
    private String empName;
    private String deptName;
    private String astockName;
    private String astockId;
    private String basicUnitId;
    private String secUnitId;
    private String tranType;
    private Double itemweight;//商品重量
    private Integer kfperiod;//保质期
    private String kfdatetype;//保质期类型
    private Double invQty;//库存数量
    private String billerName;//制单人

    private BigDecimal creditamount;
    private Integer iscreditmgr;

    private String isAllowAudit;

    private Double basicQty;

    private String sonName;

    private String tranTypeName;

    private Integer isFreeGift;
    private String salesName;

    private String classIdName;

    private String checkerName;
    private Date checkDate;
    private String checkerId;

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

    @Column(name ="billno")
    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    @Column(name ="date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name ="aitemid")
    public String getAitemid() {
        return aitemid;
    }

    public void setAitemid(String aitemid) {
        this.aitemid = aitemid;
    }

    @Column(name ="empid")
    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    @Column(name ="deptid")
    public String getDeptid() {
        return deptid;
    }

    public void setDeptid(String deptid) {
        this.deptid = deptid;
    }

    @Column(name ="fetchstyle")
    public String getFetchstyle() {
        return fetchstyle;
    }

    public void setFetchstyle(String fetchstyle) {
        this.fetchstyle = fetchstyle;
    }

    @Column(name ="allamount")
    public BigDecimal getAllamount() {
        return allamount;
    }

    public void setAllamount(BigDecimal allamount) {
        this.allamount = allamount;
    }

    @Column(name ="preamount")
    public BigDecimal getPreamount() {
        return preamount;
    }

    public void setPreamount(BigDecimal preamount) {
        this.preamount = preamount;
    }

    @Column(name ="rebateamount")
    public BigDecimal getRebateamount() {
        return rebateamount;
    }

    public void setRebateamount(BigDecimal rebateamount) {
        this.rebateamount = rebateamount;
    }

    @Column(name ="freight")
    public BigDecimal getFreight() {
        return freight;
    }

    public void setFreight(BigDecimal freight) {
        this.freight = freight;
    }

    @Column(name ="aweight")
    public BigDecimal getAweight() {
        return aweight;
    }

    public void setAweight(BigDecimal aweight) {
        this.aweight = aweight;
    }

    @Column(name ="mall")
    public BigDecimal getMall() {
        return mall;
    }

    public void setMall(BigDecimal mall) {
        this.mall = mall;
    }

    @Column(name ="mallOrderID")
    public BigDecimal getMallOrderID() {
        return mallOrderID;
    }

    public void setMallOrderID(BigDecimal mallOrderID) {
        this.mallOrderID = mallOrderID;
    }

    @Column(name ="explanation")
    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    @Column(name ="contact")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Column(name ="mobilephone")
    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    @Column(name ="phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name ="address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Column(name ="checkamount")
    public BigDecimal getCheckamount() {
        return checkamount;
    }

    public void setCheckamount(BigDecimal checkamount) {
        this.checkamount = checkamount;
    }

    @Column(name ="billerid")
    public String getBillerid() {
        return billerid;
    }

    public void setBillerid(String billerid) {
        this.billerid = billerid;
    }

    @Transient
    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    //@Column(name ="auditDate")
    @Transient
    public String getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(String auditDate) {
        this.auditDate = auditDate;
    }

    @Transient
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name ="closed")
    public String getClosed() {
        return closed;
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }

    @Column(name ="cancellation")
    public String getCancellation() {
        return cancellation;
    }

    public void setCancellation(String cancellation) {
        this.cancellation = cancellation;
    }

    @Column(name ="sonID")
    public String getSonId() {
        return sonId;
    }

    public void setSonId(String sonId) {
        this.sonId = sonId;
    }

    @Column(name ="indexNumber")
    public Integer getIndexNumber() {
        return indexNumber;
    }

    public void setIndexNumber(Integer indexNumber) {
        this.indexNumber = indexNumber;
    }

    @Column(name ="itemNumber")
    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    @Column(name ="itemName")
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Column(name ="model")
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column(name ="barCode")
    public String getBarCode() {
        return barCode;
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    @Column(name ="stockid")
    public String getStockid() {
        return stockid;
    }

    public void setStockid(String stockid) {
        this.stockid = stockid;
    }

    @Column(name ="stockName")
    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    @Column(name ="unitid")
    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    @Column(name ="unitName")
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    @Column(name ="qty")
    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    @Column(name ="coefficient")
    public BigDecimal getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(BigDecimal coefficient) {
        this.coefficient = coefficient;
    }

    @Column(name ="taxPriceEx")
    public BigDecimal getTaxPriceEx() {
        return taxPriceEx;
    }

    public void setTaxPriceEx(BigDecimal taxPriceEx) {
        this.taxPriceEx = taxPriceEx;
    }

    @Column(name ="taxAmountEx")
    public BigDecimal getTaxAmountEx() {
        return taxAmountEx;
    }

    public void setTaxAmountEx(BigDecimal taxAmountEx) {
        this.taxAmountEx = taxAmountEx;
    }

    @Column(name ="discountRate")
    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    @Column(name ="taxPrice")
    public BigDecimal getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(BigDecimal taxPrice) {
        this.taxPrice = taxPrice;
    }

    @Column(name ="inTaxAmount")
    public BigDecimal getInTaxAmount() {
        return inTaxAmount;
    }

    public void setInTaxAmount(BigDecimal inTaxAmount) {
        this.inTaxAmount = inTaxAmount;
    }

    @Column(name ="mallPrice")
    public BigDecimal getMallPrice() {
        return mallPrice;
    }

    public void setMallPrice(BigDecimal mallPrice) {
        this.mallPrice = mallPrice;
    }

    @Column(name ="mallAmount")
    public BigDecimal getMallAmount() {
        return mallAmount;
    }

    public void setMallAmount(BigDecimal mallAmount) {
        this.mallAmount = mallAmount;
    }

    @Column(name ="weight")
    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @Column(name ="taxRate")
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    @Column(name ="taxAmount")
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    @Column(name ="fetchDate")
    public Date getFetchDate() {
        return fetchDate;
    }

    public void setFetchDate(Date fetchDate) {
        this.fetchDate = fetchDate;
    }

    @Column(name ="salesID")
    public String getSalesID() {
        return salesID;
    }

    public void setSalesID(String salesID) {
        this.salesID = salesID;
    }

    @Column(name ="freegifts")
    public String getFreegifts() {
        return freegifts;
    }

    public void setFreegifts(String freegifts) {
        this.freegifts = freegifts;
    }

    @Column(name ="stockQty")
    public BigDecimal getStockQty() {
        return stockQty;
    }

    public void setStockQty(BigDecimal stockQty) {
        this.stockQty = stockQty;
    }

    @Column(name ="billnoSrc")
    public String getBillnoSrc() {
        return billnoSrc;
    }

    public void setBillnoSrc(String billnoSrc) {
        this.billnoSrc = billnoSrc;
    }

    @Column(name ="classidSrc")
    public String getClassidSrc() {
        return classidSrc;
    }

    public void setClassidSrc(String classidSrc) {
        this.classidSrc = classidSrc;
    }

    @Column(name ="note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Column(name ="autoFlag")
    public String getAutoFlag() {
        return autoFlag;
    }

    public void setAutoFlag(String autoFlag) {
        this.autoFlag = autoFlag;
    }

    @Column(name ="checkState")
    public Integer getCheckState() {
        return checkState;
    }

    public void setCheckState(Integer checkState) {
        this.checkState = checkState;
    }

    @Column(name ="isStock")
    public Integer getIsStock() {
        return isStock;
    }

    public void setIsStock(Integer isStock) {
        this.isStock = isStock;
    }

    @Column(name ="itemId")
    public String getItemid() {
        return itemid;
    }

    public void setItemid(String itemid) {
        this.itemid = itemid;
    }

    @Column(name ="isKfperiod")
    public String getIsKfperiod() {
        return isKfperiod;
    }

    public void setIsKfperiod(String isKfperiod) {
        this.isKfperiod = isKfperiod;
    }

    @Column(name ="batchManager")
    public String getBatchManager() {
        return batchManager;
    }

    public void setBatchManager(String batchManager) {
        this.batchManager = batchManager;
    }

    @Column(name ="aitemName")
    public String getAitemName() {
        return aitemName;
    }

    public void setAitemName(String aitemName) {
        this.aitemName = aitemName;
    }

    @Column(name ="empName")
    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    @Column(name ="deptName")
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Column(name ="astockName")
    public String getAstockName() {
        return astockName;
    }

    public void setAstockName(String astockName) {
        this.astockName = astockName;
    }

    @Column(name ="astockId")
    public String getAstockId() {
        return astockId;
    }

    public void setAstockId(String astockId) {
        this.astockId = astockId;
    }

    @Column(name ="basicUnitId")
    public String getBasicUnitId() {
        return basicUnitId;
    }

    public void setBasicUnitId(String basicUnitId) {
        this.basicUnitId = basicUnitId;
    }

    @Column(name ="secUnitId")
    public String getSecUnitId() {
        return secUnitId;
    }

    public void setSecUnitId(String secUnitId) {
        this.secUnitId = secUnitId;
    }

    @Column(name ="tranType")
    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    @Column(name ="itemweight")
    public Double getItemweight() {
        return itemweight;
    }

    public void setItemweight(Double itemweight) {
        this.itemweight = itemweight;
    }

    @Column(name ="kfperiod")
    public Integer getKfperiod() {
        return kfperiod;
    }

    public void setKfperiod(Integer kfperiod) {
        this.kfperiod = kfperiod;
    }

    @Column(name ="kfdatetype")
    public String getKfdatetype() {
        return kfdatetype;
    }

    public void setKfdatetype(String kfdatetype) {
        this.kfdatetype = kfdatetype;
    }

    @Column(name ="invQty")
    public Double getInvQty() {
        return invQty;
    }

    public void setInvQty(Double invQty) {
        this.invQty = invQty;
    }

    @Column(name ="billerName")
    public String getBillerName() {
        return billerName;
    }

    public void setBillerName(String billerName) {
        this.billerName = billerName;
    }

    @Column(name ="creditamount")
    public BigDecimal getCreditamount() {
        return creditamount;
    }

    public void setCreditamount(BigDecimal creditamount) {
        this.creditamount = creditamount;
    }

    @Column(name ="iscreditmgr")
    public Integer getIscreditmgr() {
        return iscreditmgr;
    }

    public void setIscreditmgr(Integer iscreditmgr) {
        this.iscreditmgr = iscreditmgr;
    }

    @Transient
    public String getIsAllowAudit() {
        return isAllowAudit;
    }

    public void setIsAllowAudit(String isAllowAudit) {
        this.isAllowAudit = isAllowAudit;
    }

    @Column(name ="basicQty")
    public Double getBasicQty() {
        return basicQty;
    }

    public void setBasicQty(Double basicQty) {
        this.basicQty = basicQty;
    }

    @Column(name ="sonName")
    public String getSonName() {
        return sonName;
    }

    public void setSonName(String sonName) {
        this.sonName = sonName;
    }

    @Transient
    public String getTranTypeName() {
        return tranTypeName;
    }

    public void setTranTypeName(String tranTypeName) {
        this.tranTypeName = tranTypeName;
    }

    @Column(name ="isFreeGift")
    public Integer getIsFreeGift() {
        return isFreeGift;
    }

    public void setIsFreeGift(Integer isFreeGift) {
        this.isFreeGift = isFreeGift;
    }

    @Column(name ="salesName")
    public String getSalesName() {
        return salesName;
    }

    public void setSalesName(String salesName) {
        this.salesName = salesName;
    }

    @Transient
    public String getClassIdName() {
        return classIdName;
    }

    public void setClassIdName(String classIdName) {
        this.classIdName = classIdName;
    }

    @Column(name ="checkerName")
    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    @Column(name ="checkDate")
    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    @Column(name ="checkerId")
    public String getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(String checkerId) {
        this.checkerId = checkerId;
    }
}
