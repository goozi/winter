package com.qihang.buss.sc.inventory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by LenovoM4550 on 2016-08-19.
 * 未审核数据实体（采购出入库单，采购换货，销售出入库单，销售换货）
 */
@Entity
@Table(name = "v_sc_ic_chkInfo", schema = "")
public class TScIcChkInfoView implements java.io.Serializable{
    private String id;
    private String billNo;
    private Date date;
    private String tranType;
    private String billName;

    @Id
    @Column(name ="ID",nullable=false)
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

    @Column(name ="tranType",nullable=false)
    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    @Column(name ="billName",nullable=false)
    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }
}
