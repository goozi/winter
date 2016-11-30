package com.qihang.winter.web.system.pojo.base;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.qihang.winter.core.common.entity.IdEntity;
import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * 部门机构表
 * @author  Zerrion
 */
@Entity
@Table(name = "t_s_depart")
public class TSDepart extends IdEntity implements java.io.Serializable {
	private TSDepart TSPDepart;//上级部门
	@Excel(name = "分支机构")
	private String departname;//部门名称
	private String description;//部门描述
    private String orgCode;//机构编码
    private String orgType;//机构编码
	private List<TSDepart> TSDeparts = new ArrayList<TSDepart>();//下属部门

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentdepartid")
	public TSDepart getTSPDepart() {
		return this.TSPDepart;
	}

	public void setTSPDepart(TSDepart TSPDepart) {
		this.TSPDepart = TSPDepart;
	}

	@Column(name = "departname", nullable = false, length = 100)
	public String getDepartname() {
		return this.departname;
	}

	public void setDepartname(String departname) {
		this.departname = departname;
	}

	@Column(name = "description", length = 500)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@OneToMany(targetEntity = TSDepart.class,cascade = CascadeType.ALL,mappedBy = "TSPDepart")
	@Fetch(FetchMode.SUBSELECT)
	@OrderBy("id")
	public List<TSDepart> getTSDeparts() {
		return TSDeparts;
	}

	public void setTSDeparts(List<TSDepart> tSDeparts) {
		TSDeparts = tSDeparts;
	}

    @Column(name = "org_code", length = 64)
    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Column(name = "org_type", length = 1)
    public String getOrgType() {
        return orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }


	private String shortName;//简称
	private String shortNumber;//助记码
	private String regionId;//区域
	private String contact;//联系人
	private String mobilePhone;//手机
	private String phone;//电话
	private String fax;//传真
	private String address;//地址
	private String ciqNumber;//QQ
	private String city;//城市
	private String postalCode;//邮编
	private String email;//电子邮箱
	private String homePage;//公司主页
	private String bank;//开户银行
	private String bankNumber;//银行账号
	private String licence;//营业执照号
	private String trade;//行业
	private String deleted;//禁用标记
	private String note;//备注

	@Column(name = "shortName", nullable = true)
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "shortNumber", nullable = true)
	public String getShortNumber() {
		return shortNumber;
	}

	public void setShortNumber(String shortNumber) {
		this.shortNumber = shortNumber;
	}

	@Column(name = "regionId", nullable = true)
	public String getRegionId() {
		return regionId;
	}

	public void setRegionId(String regionId) {
		this.regionId = regionId;
	}

	@Column(name = "contact", nullable = true)
	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	@Column(name = "mobilePhone", nullable = true)
	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name = "phone", nullable = true)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Column(name = "fax", nullable = true)
	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "address", nullable = true)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "ciqNumber", nullable = true)
	public String getCiqNumber() {
		return ciqNumber;
	}

	public void setCiqNumber(String ciqNumber) {
		this.ciqNumber = ciqNumber;
	}

	@Column(name = "city", nullable = true)
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "postalCode", nullable = true)
	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	@Column(name = "email", nullable = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "homePage", nullable = true)
	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	@Column(name = "bank", nullable = true)
	public String getBank() {
		return bank;
	}

	public void setBank(String bank) {
		this.bank = bank;
	}

	@Column(name = "bankNumber", nullable = true)
	public String getBankNumber() {
		return bankNumber;
	}

	public void setBankNumber(String bankNumber) {
		this.bankNumber = bankNumber;
	}

	@Column(name = "licence", nullable = true)
	public String getLicence() {
		return licence;
	}

	public void setLicence(String licence) {
		this.licence = licence;
	}

	@Column(name = "trade", nullable = true)
	public String getTrade() {
		return trade;
	}

	public void setTrade(String trade) {
		this.trade = trade;
	}

	@Column(name = "deleted", nullable = true)
	public String getDeleted() {
		return deleted;
	}

	public void setDeleted(String deleted) {
		this.deleted = deleted;
	}

	@Column(name = "note", nullable = true)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
}