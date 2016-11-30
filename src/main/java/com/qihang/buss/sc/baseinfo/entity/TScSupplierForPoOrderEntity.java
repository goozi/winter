package com.qihang.buss.sc.baseinfo.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 供应商
 * @author onlineGenerator
 * @date 2016-06-23 14:09:01
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_SUPPLIER", schema = "")
@SuppressWarnings("serial")
public class TScSupplierForPoOrderEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**创建人名称*/
	private String createName;
	/**创建人登录名称*/
	private String createBy;
	/**创建日期*/
	private Date createDate;
	/**更新人名称*/
	private String updateName;
	/**更新人登录名称*/
	private String updateBy;
	/**更新日期*/
	private Date updateDate;
	/**名称*/
	@Excel(name="供应商")
	private String name;
	/**编号*/
	private String number;
	/**分类ID*/
	private String parentID;
	/**简称*/
	private String shortName;
	/**助记码*/
	private String shortNumber;
	/**区域*/
	private String regionID;
	/**联系人*/
	private String contact;
	/**手机号码*/
	private String mobilePhone;
	/**电话*/
	private String phone;
	/**传真*/
	private String fax;
	/**联系地址*/
	private String address;
	/**QQ号*/
	private String cIQNumber;
	/**城市*/
	private String city;
	/**邮政编码*/
	private String postalCode;
	/**电子邮件*/
	private String email;
	/**公司主页*/
	private String homePage;
	/**开户银行*/
	private String bank;
	/**银行账号*/
	private String bankNumber;
	/**营业执照号*/
	private String licence;
	/**行业*/
	private String trade;
	/**法人代表*/
	private String corperate;
	/**供应商分类*/
	private String typeID;
	/**结算单位*/
	private String settleCompany;
	/**储备天数*/
	private Integer stockDay;
	/**在途天数*/
	private Integer byWayDay;
	/**级次*/
	private Integer level;
	/**禁用标记*/
	private Integer disabled;
	/**逻辑删除*/
	private Integer deleted;
	/**引用次数*/
	private Integer count;
	/**备注*/
	private String note;

	/** 结算单位名称 **/
	private String settleCompanyName;

	@Transient
	public String getSettleCompanyName() {
		return settleCompanyName;
	}

	public void setSettleCompanyName(String settleCompanyName) {
		this.settleCompanyName = settleCompanyName;
	}



	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */
	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */
	@Column(name ="UPDATE_DATE",nullable=true,length=20)
	public Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="NAME",nullable=false,length=150)
	public String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  编号
	 */
	@Column(name ="NUMBER",nullable=false,length=80)
	public String getNumber(){
		return this.number;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  编号
	 */
	public void setNumber(String number){
		this.number = number;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分类ID
	 */
	@Column(name ="PARENTID",nullable=true,length=32)
	public String getParentID(){
		return this.parentID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分类ID
	 */
	public void setParentID(String parentID){
		this.parentID = parentID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  简称
	 */
	@Column(name ="SHORTNAME",nullable=false,length=50)
	public String getShortName(){
		return this.shortName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  简称
	 */
	public void setShortName(String shortName){
		this.shortName = shortName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  助记码
	 */
	@Column(name ="SHORTNUMBER",nullable=true,length=80)
	public String getShortNumber(){
		return this.shortNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  助记码
	 */
	public void setShortNumber(String shortNumber){
		this.shortNumber = shortNumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  区域
	 */
	@Column(name ="REGIONID",nullable=true,length=50)
	public String getRegionID(){
		return this.regionID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  区域
	 */
	public void setRegionID(String regionID){
		this.regionID = regionID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人
	 */
	@Column(name ="CONTACT",nullable=true,length=50)
	public String getContact(){
		return this.contact;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系人
	 */
	public void setContact(String contact){
		this.contact = contact;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机号码
	 */
	@Column(name ="MOBILEPHONE",nullable=true,length=15)
	public String getMobilePhone(){
		return this.mobilePhone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机号码
	 */
	public void setMobilePhone(String mobilePhone){
		this.mobilePhone = mobilePhone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话
	 */
	@Column(name ="PHONE",nullable=true,length=40)
	public String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电话
	 */
	public void setPhone(String phone){
		this.phone = phone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  传真
	 */
	@Column(name ="FAX",nullable=true,length=40)
	public String getFax(){
		return this.fax;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  传真
	 */
	public void setFax(String fax){
		this.fax = fax;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系地址
	 */
	@Column(name ="ADDRESS",nullable=true,length=255)
	public String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系地址
	 */
	public void setAddress(String address){
		this.address = address;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  QQ号
	 */
	@Column(name ="CIQNUMBER",nullable=true,length=20)
	public String getcIQNumber() {
		return cIQNumber;
	}

	public void setcIQNumber(String cIQNumber) {
		this.cIQNumber = cIQNumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  城市
	 */
	@Column(name ="CITY",nullable=true,length=50)
	public String getCity(){
		return this.city;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  城市
	 */
	public void setCity(String city){
		this.city = city;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邮政编码
	 */
	@Column(name ="POSTALCODE",nullable=true,length=20)
	public String getPostalCode(){
		return this.postalCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  邮政编码
	 */
	public void setPostalCode(String postalCode){
		this.postalCode = postalCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电子邮件
	 */
	@Column(name ="EMAIL",nullable=true,length=40)
	public String getEmail(){
		return this.email;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电子邮件
	 */
	public void setEmail(String email){
		this.email = email;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公司主页
	 */
	@Column(name ="HOMEPAGE",nullable=true,length=80)
	public String getHomePage(){
		return this.homePage;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公司主页
	 */
	public void setHomePage(String homePage){
		this.homePage = homePage;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  开户银行
	 */
	@Column(name ="BANK",nullable=true,length=50)
	public String getBank(){
		return this.bank;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  开户银行
	 */
	public void setBank(String bank){
		this.bank = bank;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  银行账号
	 */
	@Column(name ="BANKNUMBER",nullable=true,length=40)
	public String getBankNumber(){
		return this.bankNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  银行账号
	 */
	public void setBankNumber(String bankNumber){
		this.bankNumber = bankNumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  营业执照号
	 */
	@Column(name ="LICENCE",nullable=true,length=80)
	public String getLicence(){
		return this.licence;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  营业执照号
	 */
	public void setLicence(String licence){
		this.licence = licence;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  行业
	 */
	@Column(name ="TRADE",nullable=true,length=50)
	public String getTrade(){
		return this.trade;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  行业
	 */
	public void setTrade(String trade){
		this.trade = trade;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  法人代表
	 */
	@Column(name ="CORPERATE",nullable=true,length=80)
	public String getCorperate(){
		return this.corperate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  法人代表
	 */
	public void setCorperate(String corperate){
		this.corperate = corperate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  供应商分类
	 */
	@Column(name ="TYPEID",nullable=true,length=50)
	public String getTypeID(){
		return this.typeID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  供应商分类
	 */
	public void setTypeID(String typeID){
		this.typeID = typeID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结算单位
	 */
	@Column(name ="SETTLECOMPANY",nullable=true,length=50)
	public String getSettleCompany(){
		return this.settleCompany;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结算单位
	 */
	public void setSettleCompany(String settleCompany){
		this.settleCompany = settleCompany;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  储备天数
	 */
	@Column(name ="STOCKDAY",nullable=true,length=32)
	public Integer getStockDay(){
		return this.stockDay;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  储备天数
	 */
	public void setStockDay(Integer stockDay){
		this.stockDay = stockDay;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  在途天数
	 */
	@Column(name ="BYWAYDAY",nullable=true,length=32)
	public Integer getByWayDay(){
		return this.byWayDay;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  在途天数
	 */
	public void setByWayDay(Integer byWayDay){
		this.byWayDay = byWayDay;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  级次
	 */
	@Column(name ="LEVEL",nullable=true,length=32)
	public Integer getLevel(){
		return this.level;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  级次
	 */
	public void setLevel(Integer level){
		this.level = level;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  禁用标记
	 */
	@Column(name ="DISABLED",nullable=true,length=32)
	public Integer getDisabled(){
		return this.disabled;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  禁用标记
	 */
	public void setDisabled(Integer disabled){
		this.disabled = disabled;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  逻辑删除
	 */
	@Column(name ="DELETED",nullable=true,length=32)
	public Integer getDeleted(){
		return this.deleted;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  逻辑删除
	 */
	public void setDeleted(Integer deleted){
		this.deleted = deleted;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  引用次数
	 */
	@Column(name ="COUNT",nullable=true,length=32)
	public Integer getCount(){
		return this.count;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  引用次数
	 */
	public void setCount(Integer count){
		this.count = count;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="NOTE",nullable=true,length=255)
	public String getNote(){
		return this.note;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setNote(String note){
		this.note = note;
	}
}
