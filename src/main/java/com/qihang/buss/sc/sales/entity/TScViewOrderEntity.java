package com.qihang.buss.sc.sales.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 销售订单显示列表视图
 */
@Entity
@Table(name = "v_sc_order", schema = "")
public class TScViewOrderEntity implements java.io.Serializable {

    /**从表的主键*/
    private String entryId;
    /**主表的主键*/
    private String id;
    /**单据编号*/
    private String billNo;
    /**单据时间*/
    private Date date;
    /**客户*/
    private String aitemID;
    /**经办人*/
    private String empID;
    /**部门*/
    private String deptID;
    /**付款方式*/
    private String fetchStyle;
    /**应收金额*/
    private BigDecimal allAmount;
    /**预收金额*/
    private BigDecimal preAmount;
    /**优惠金额*/
    private BigDecimal rebateAmount;
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
    private String mobilePhone;
    /**电话*/
    private String phone;
    /**联系地址*/
    private String address;
    /**执行金额*/
    private BigDecimal checkAmount;
    /**制单人*/
    private String billerID;
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
    private String sonID;
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
    private  String stockID;
    /**单位*/
    private String unitID;
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
    private BigDecimal bweight;
    /**税率*/
    private BigDecimal taxRate;
    /**税额*/
    private BigDecimal taxAmount;
    /**交货日期*/
    private Date fetchDate;
    /**促销类型*/
    private String  salesID;
    /**赠品标记*/
    private String freeGifts;
    /**发货数量*/
    private  BigDecimal stockQty;
    /**源单编号*/
    private String billNoSrc;
    /**源单类型*/
    private String classIDSrc;
    /**备注*/
    private String note;
    /**自动关闭标记*/
    private String autoFlag;


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

    @Transient
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Transient
    public String getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(String auditDate) {
        this.auditDate = auditDate;
    }

    @Transient
    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    @Column(name ="BillNo")
    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }
    @Column(name ="Date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    @Column(name ="aitemID")
    public String getAitemID() {
        return aitemID;
    }

    public void setAitemID(String aitemID) {
        this.aitemID = aitemID;
    }
    @Column(name ="EmpID")
    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }
    @Column(name ="DeptID")
    public String getDeptID() {
        return deptID;
    }
    public void setDeptID(String deptID) {
        this.deptID = deptID;
    }
    @Column(name ="FetchStyle")
    public String getFetchStyle() {
        return fetchStyle;
    }

    public void setFetchStyle(String fetchStyle) {
        this.fetchStyle = fetchStyle;
    }
    @Column(name ="allamount")
    public BigDecimal getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(BigDecimal allAmount) {
        this.allAmount = allAmount;
    }
    @Column(name ="preamount")
    public BigDecimal getPreAmount() {
        return preAmount;
    }

    public void setPreAmount(BigDecimal preAmount) {
        this.preAmount = preAmount;
    }
    @Column(name ="rebateamount")
    public BigDecimal getRebateAmount() {
        return rebateAmount;
    }

    public void setRebateAmount(BigDecimal rebateAmount) {
        this.rebateAmount = rebateAmount;
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
    @Column(name ="mallorderid")
    public BigDecimal getMallOrderID() {
        return mallOrderID;
    }

    public void setMallOrderID(BigDecimal mallOrderID) {
        this.mallOrderID = mallOrderID;
    }
    @Column(name ="Explanation")
    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    @Column(name ="Contact")
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    @Column(name ="MobilePhone")
    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    @Column(name ="Phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Column(name ="Address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    @Column(name ="checkamount")
    public BigDecimal getCheckAmount() {
        return checkAmount;
    }

    public void setCheckAmount(BigDecimal checkAmount) {
        this.checkAmount = checkAmount;
    }
    @Column(name ="BillerID")
    public String getBillerID() {
        return billerID;
    }

    public void setBillerID(String billerID) {
        this.billerID = billerID;
    }
    @Column(name ="Closed")
    public String getClosed() {
        return closed;
    }

    public void setClosed(String closed) {
        this.closed = closed;
    }
    @Column(name ="Cancellation")
    public String getCancellation() {
        return cancellation;
    }

    public void setCancellation(String cancellation) {
        this.cancellation = cancellation;
    }
    @Column(name ="SonID")
    public String getSonID() {
        return sonID;
    }

    public void setSonID(String sonID) {
        this.sonID = sonID;
    }
    @Column(name ="IndexNumber")
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
    @Column(name ="StockID")
    public String getStockID() {
        return stockID;
    }

    public void setStockID(String stockID) {
        this.stockID = stockID;
    }
    @Column(name ="UnitID")
    public String getUnitID() {
        return unitID;
    }

    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }
    @Column(name ="Qty")
    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }
    @Column(name ="Coefficient")
    public BigDecimal getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(BigDecimal coefficient) {
        this.coefficient = coefficient;
    }
    @Column(name ="TaxPriceEx")
    public BigDecimal getTaxPriceEx() {
        return taxPriceEx;
    }

    public void setTaxPriceEx(BigDecimal taxPriceEx) {
        this.taxPriceEx = taxPriceEx;
    }
    @Column(name ="TaxAmountEx")
    public BigDecimal getTaxAmountEx() {
        return taxAmountEx;
    }

    public void setTaxAmountEx(BigDecimal taxAmountEx) {
        this.taxAmountEx = taxAmountEx;
    }
    @Column(name ="DiscountRate")
    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }
    @Column(name ="TaxPrice")
    public BigDecimal getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(BigDecimal taxPrice) {
        this.taxPrice = taxPrice;
    }
    @Column(name ="InTaxAmount")
    public BigDecimal getInTaxAmount() {
        return inTaxAmount;
    }

    public void setInTaxAmount(BigDecimal inTaxAmount) {
        this.inTaxAmount = inTaxAmount;
    }
    @Column(name ="MallPrice")
    public BigDecimal getMallPrice() {
        return mallPrice;
    }

    public void setMallPrice(BigDecimal mallPrice) {
        this.mallPrice = mallPrice;
    }
    @Column(name ="MallAmount")
    public BigDecimal getMallAmount() {
        return mallAmount;
    }

    public void setMallAmount(BigDecimal mallAmount) {
        this.mallAmount = mallAmount;
    }
    @Column(name ="bweight")
    public BigDecimal getBweight() {
        return bweight;
    }

    public void setBweight(BigDecimal bweight) {
        this.bweight = bweight;
    }
    @Column(name ="TaxRate")
    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
    @Column(name ="TaxAmount")
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }
    @Column(name ="FetchDate")
    public Date getFetchDate() {
        return fetchDate;
    }

    public void setFetchDate(Date fetchDate) {
        this.fetchDate = fetchDate;
    }
    @Column(name ="SalesID")
    public String getSalesID() {
        return salesID;
    }

    public void setSalesID(String salesID) {
        this.salesID = salesID;
    }
    @Column(name ="FreeGifts")
    public String getFreeGifts() {
        return freeGifts;
    }

    public void setFreeGifts(String freeGifts) {
        this.freeGifts = freeGifts;
    }
    @Column(name ="StockQty")
    public BigDecimal getStockQty() {
        return stockQty;
    }

    public void setStockQty(BigDecimal stockQty) {
        this.stockQty = stockQty;
    }
    @Column(name ="BillNo_SRC")
    public String getBillNoSrc() {
        return billNoSrc;
    }

    public void setBillNoSrc(String billNoSrc) {
        this.billNoSrc = billNoSrc;
    }
    @Column(name ="ClassID_SRC")
    public String getClassIDSrc() {
        return classIDSrc;
    }

    public void setClassIDSrc(String classIDSrc) {
        this.classIDSrc = classIDSrc;
    }
    @Column(name ="Note")
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    @Column(name ="AutoFlag")
    public String getAutoFlag() {
        return autoFlag;
    }

    public void setAutoFlag(String autoFlag) {
        this.autoFlag = autoFlag;
    }
}
