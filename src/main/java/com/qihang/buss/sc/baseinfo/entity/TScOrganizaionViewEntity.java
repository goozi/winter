package com.qihang.buss.sc.baseinfo.entity;

import com.qihang.winter.poi.excel.annotation.Excel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by LenovoM4550 on 2016-10-08.
 */
@Entity
@Table(name = "v_sc_organization", schema = "")
@SuppressWarnings("serial")
public class TScOrganizaionViewEntity implements java.io.Serializable {
    /**主键*/
    private java.lang.String id;
    /**创建人名称*/
    private java.lang.String createName;
    /**创建人登录名称*/
    private java.lang.String createBy;
    /**创建日期*/
    private java.util.Date createDate;
    /**更新人名称*/
    private java.lang.String updateName;
    /**更新人登录名称*/
    private java.lang.String updateBy;
    /**更新日期*/
    private java.util.Date updateDate;
    /**编号*/
    @Excel(name="编号")
    private java.lang.String number;
    /**名称*/
    @Excel(name="名称")
    private java.lang.String name;
    /**营业执照号*/
    @Excel(name="营业执照号")
    private java.lang.String licence;
    /**结算单位*/
    @Excel(name="结算单位")
    private java.lang.String settlementName;
    /**开户银行*/
    @Excel(name="开户银行")
    private java.lang.String bank;
    /**银行账号*/
    @Excel(name="银行账号")
    private java.lang.String bankNumber;
    /**客户分类*/
    @Excel(name="客户分类")
    private java.lang.String typeId;
    /**上级经销商*/
    @Excel(name="上级经销商")
    private java.lang.String parentDealerName;
    /**默认业务员*/
    @Excel(name="默认业务员")
    private java.lang.String businessName;
    /**送货方式*/
    @Excel(name="送货方式")
    private java.lang.String deliverType;
    /**分类id*/
    private java.lang.String parentid;
    /**简称*/
    @Excel(name="简称")
    private java.lang.String shortName;
    /**助记码*/
    @Excel(name="助记码")
    private java.lang.String shortNumber;
    /**联系人*/
    @Excel(name="联系人")
    private java.lang.String contact;
    /**手机号码*/
    @Excel(name="手机号码")
    private java.lang.String mobilePhone;
    /**电话*/
    @Excel(name="电话")
    private java.lang.String phone;
    /**传真*/
    @Excel(name="传真")
    private java.lang.String fax;
    /**邮政编码*/
    @Excel(name="邮政编码")
    private java.lang.String postAlcode;
    /**区域*/
    @Excel(name="区域")
    private java.lang.String regIonid;
    /**城市*/
    @Excel(name="城市")
    private java.lang.String city;
    /**qq号*/
    @Excel(name="qq号")
    private java.lang.String ciqNumber;
    /**电子邮件*/
    @Excel(name="电子邮件")
    private java.lang.String email;
    /**公司主页*/
    @Excel(name="公司主页")
    private java.lang.String homePage;
    /**法人代表*/
    @Excel(name="法人代表")
    private java.lang.String corperate;
    /**行业*/
    @Excel(name="行业")
    private java.lang.String trade;
    /**启用信控*/
    @Excel(name="启用信控")
    private java.lang.Integer isCreditmgr;
    /**信用额度*/
    @Excel(name="信用额度")
    private java.math.BigDecimal creditAmount;
    /**联系地址*/
    @Excel(name="联系地址")
    private java.lang.String address;
    /**禁用标记*/
    private java.lang.Integer disable;
    /**是否经销商**/
    private java.lang.Integer isDealer;
    /**引用次数**/
    private java.lang.Integer count ;

    private String sonId;//分支机构

    @Id
    @Column(name ="id",nullable=true)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @Column(name ="createName",nullable=true)
    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }
    @Column(name ="createBy",nullable=true)
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    @Column(name ="createDate",nullable=true)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @Column(name ="updateName",nullable=true)
    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }
    @Column(name ="updateBy",nullable=true)
    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
    @Column(name ="updateDate",nullable=true)
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    @Column(name ="number",nullable=true)
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    @Column(name ="name",nullable=true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Column(name ="licence",nullable=true)
    public String getLicence() {
        return licence;
    }

    public void setLicence(String licence) {
        this.licence = licence;
    }
    @Column(name ="settlementName",nullable=true)
    public String getSettlementName() {
        return settlementName;
    }

    public void setSettlementName(String settlementName) {
        this.settlementName = settlementName;
    }
    @Column(name ="bank",nullable=true)
    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
    @Column(name ="bankNumber",nullable=true)
    public String getBankNumber() {
        return bankNumber;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }
    @Column(name ="typeId",nullable=true)
    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }
    @Column(name ="parentDealerName",nullable=true)
    public String getParentDealerName() {
        return parentDealerName;
    }

    public void setParentDealerName(String parentDealerName) {
        this.parentDealerName = parentDealerName;
    }
    @Column(name ="businessName",nullable=true)
    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    @Column(name ="deliverType",nullable=true)
    public String getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(String deliverType) {
        this.deliverType = deliverType;
    }
    @Column(name ="parentid",nullable=true)
    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }
    @Column(name ="shortName",nullable=true)
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
    @Column(name ="shortNumber",nullable=true)
    public String getShortNumber() {
        return shortNumber;
    }

    public void setShortNumber(String shortNumber) {
        this.shortNumber = shortNumber;
    }
    @Column(name ="contact",nullable=true)
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }
    @Column(name ="mobilePhone",nullable=true)
    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }
    @Column(name ="phone",nullable=true)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @Column(name ="fax",nullable=true)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }
    @Column(name ="postAlcode",nullable=true)
    public String getPostAlcode() {
        return postAlcode;
    }

    public void setPostAlcode(String postAlcode) {
        this.postAlcode = postAlcode;
    }
    @Column(name ="regIonid",nullable=true)
    public String getRegIonid() {
        return regIonid;
    }

    public void setRegIonid(String regIonid) {
        this.regIonid = regIonid;
    }
    @Column(name ="city",nullable=true)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    @Column(name ="ciqNumber",nullable=true)
    public String getCiqNumber() {
        return ciqNumber;
    }

    public void setCiqNumber(String ciqNumber) {
        this.ciqNumber = ciqNumber;
    }
    @Column(name ="email",nullable=true)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Column(name ="homePage",nullable=true)
    public String getHomePage() {
        return homePage;
    }

    public void setHomePage(String homePage) {
        this.homePage = homePage;
    }
    @Column(name ="corperate",nullable=true)
    public String getCorperate() {
        return corperate;
    }

    public void setCorperate(String corperate) {
        this.corperate = corperate;
    }
    @Column(name ="trade",nullable=true)
    public String getTrade() {
        return trade;
    }

    public void setTrade(String trade) {
        this.trade = trade;
    }
    @Column(name ="isCreditmgr",nullable=true)
    public Integer getIsCreditmgr() {
        return isCreditmgr;
    }

    public void setIsCreditmgr(Integer isCreditmgr) {
        this.isCreditmgr = isCreditmgr;
    }
    @Column(name ="creditAmount",nullable=true)
    public BigDecimal getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(BigDecimal creditAmount) {
        this.creditAmount = creditAmount;
    }
    @Column(name ="address",nullable=true)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    @Column(name ="disable",nullable=true)
    public Integer getDisable() {
        return disable;
    }

    public void setDisable(Integer disable) {
        this.disable = disable;
    }
    @Column(name ="isDealer",nullable=true)
    public Integer getIsDealer() {
        return isDealer;
    }

    public void setIsDealer(Integer isDealer) {
        this.isDealer = isDealer;
    }
    @Column(name ="count",nullable=true)
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Column(name ="sonId",nullable=true)
    public String getSonId() {
        return sonId;
    }

    public void setSonId(String sonId) {
        this.sonId = sonId;
    }
}

