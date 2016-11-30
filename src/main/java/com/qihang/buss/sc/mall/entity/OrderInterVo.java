package com.qihang.buss.sc.mall.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LenovoM4550 on 2016-09-14.
 */
public class OrderInterVo {
    private String date ;
    private String contact;
    private String mobilePhone;
    private String address;
    private String fetchStyle;
    private Double amount;
    private Integer mallorderid;
    private Double rebateamount;
    private Double freight;
    private Double weight;
    private String explanation;
    private String licence;
    private String fetchDate;
    private List<OrderInfoInterVo> orderInfo = new ArrayList<OrderInfoInterVo>();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFetchStyle() {
        return fetchStyle;
    }

    public void setFetchStyle(String fetchStyle) {
        this.fetchStyle = fetchStyle;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Integer getMallorderid() {
        return mallorderid;
    }

    public void setMallorderid(Integer mallorderid) {
        this.mallorderid = mallorderid;
    }

    public Double getRebateamount() {
        return rebateamount;
    }

    public void setRebateamount(Double rebateamount) {
        this.rebateamount = rebateamount;
    }

    public Double getFreight() {
        return freight;
    }

    public void setFreight(Double freight) {
        this.freight = freight;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public List<OrderInfoInterVo> getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(List<OrderInfoInterVo> orderInfo) {
        this.orderInfo = orderInfo;
    }

    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }

    public String getFetchDate() {
        return fetchDate;
    }

    public void setFetchDate(String fetchDate) {
        this.fetchDate = fetchDate;
    }
}
