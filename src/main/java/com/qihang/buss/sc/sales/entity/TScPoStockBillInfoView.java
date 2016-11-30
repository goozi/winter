package com.qihang.buss.sc.sales.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by cxf on 2016-08-26.
 * 源单据视图实体
 */
@Entity
@Table(name = "v_sc_po_stockbill_info", schema = "")
public class TScPoStockBillInfoView implements java.io.Serializable{

    private String id;
    private String billNo;
    private Date date;
    private BigDecimal allAmount;//应收金额
    private BigDecimal checkAmount;//执行金额
    private String itemId;//供应商id
    private String itemName;//供应商名称
    private String sonId;//分支机构
    private String tranType;
    private String typeName;
    private String idSrc;//源单id
    @Id
    @Column(name ="ID",nullable=false,length=36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name ="billNo",nullable=false)
    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    @Column(name ="date",nullable=false)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name ="allAmount",nullable=false)
    public BigDecimal getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(BigDecimal allAmount) {
        this.allAmount = allAmount;
    }

    @Column(name ="checkAmount",nullable=false)
    public BigDecimal getCheckAmount() {
        return checkAmount;
    }

    public void setCheckAmount(BigDecimal checkAmount) {
        this.checkAmount = checkAmount;
    }

    @Column(name ="itemName",nullable=false)
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Column(name ="sonId",nullable=false)
    public String getSonId() {
        return sonId;
    }

    public void setSonId(String sonId) {
        this.sonId = sonId;
    }

    @Column(name ="tranType",nullable=false)
    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    @Column(name ="typeName",nullable=false)
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Column(name ="itemId",nullable=false)
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Column(name ="idSrc",nullable=false)
    public String getIdSrc() {
        return idSrc;
    }

    public void setIdSrc(String idSrc) {
        this.idSrc = idSrc;
    }
}
