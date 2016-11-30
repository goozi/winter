package com.qihang.buss.sc.baseinfo.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

/**   
 * @Title: Entity
 * @Description: 客户
 * @author onlineGenerator
 * @date 2016-06-08 10:53:43
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_Organization", schema = "")
@SuppressWarnings("serial")
public class TScOrganizationEntity implements java.io.Serializable {
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
	private java.lang.String settlecompany;
	/**开户银行*/
	@Excel(name="开户银行")
	private java.lang.String bank;
	/**银行账号*/
	@Excel(name="银行账号")
	private java.lang.String banknumber;
	/**客户分类*/
	@Excel(name="客户分类")
	private java.lang.String typeid;
	/**上级经销商*/
	@Excel(name="上级经销商")
	private java.lang.String dealer;
	/**默认业务员*/
	@Excel(name="默认业务员")
	private java.lang.String defaultoperator;
	/**送货方式*/
	@Excel(name="送货方式")
	private java.lang.String delivertype;
	/**分类id*/
	private java.lang.String parentid;
	/**简称*/
	@Excel(name="简称")
	private java.lang.String shortname;
	/**助记码*/
	@Excel(name="助记码")
	private java.lang.String shortnumber;
	/**联系人*/
	@Excel(name="联系人")
	private java.lang.String contact;
	/**手机号码*/
	@Excel(name="手机号码")
	private java.lang.String mobilephone;
	/**电话*/
	@Excel(name="电话")
	private java.lang.String phone;
	/**传真*/
	@Excel(name="传真")
	private java.lang.String fax;
	/**邮政编码*/
	@Excel(name="邮政编码")
	private java.lang.String postalcode;
	/**区域*/
	@Excel(name="区域")
	private java.lang.String regionid;
	/**城市*/
	@Excel(name="城市")
	private java.lang.String city;
	/**qq号*/
	@Excel(name="qq号")
	private java.lang.String ciqnumber;
	/**电子邮件*/
	@Excel(name="电子邮件")
	private java.lang.String email;
	/**公司主页*/
	@Excel(name="公司主页")
	private java.lang.String homepage;
	/**法人代表*/
	@Excel(name="法人代表")
	private java.lang.String corperate;
	/**行业*/
	@Excel(name="行业")
	private java.lang.String trade;
	/**启用信控*/
	@Excel(name="启用信控")
	private java.lang.Integer iscreditmgr;
	/**信用额度*/
	@Excel(name="信用额度")
	private java.math.BigDecimal creditamount;
	/**级次*/
	private java.lang.Integer level;
	/**联系地址*/
	@Excel(name="联系地址")
	private java.lang.String address;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String note;
	/**禁用标记*/
	@Excel(name="禁用标记")
	private java.lang.Integer disable;
	/**引用次数*/
	private java.lang.Integer count;
	/**逻辑删除*/
	private java.lang.Integer deleted;
	/**版本号*/
	private java.lang.Integer version;

	/** 结算单位名称 **/
	private java.lang.String settlecompanyName;
	/** 上级经销商名称 **/
	private java.lang.String dealerName;
	/** 默认业务员姓名**/
	private java.lang.String defaultoperatorName;

	private Integer status;//单据状态
	private String auditDate;//审核日期
	private String auditorName;//审核人
	private Integer isDealer;//是否经销商
	private BigDecimal countAmount;//已执行金额
	/**上级经销商用户id**/
	private String delerUserId;
	/**下级经销商用户id**/
	private String lowerDelerUserId;

	private String userId;

	private String sonId;


	@Transient
	public String getSettlecompanyName() {
		return settlecompanyName;
	}
	public void setSettlecompanyName(String settlecompanyName) {
		this.settlecompanyName = settlecompanyName;
	}

	@Transient
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}

	@Transient
	public String getDefaultoperatorName() {
		return defaultoperatorName;
	}

	public void setDefaultoperatorName(String defaultoperatorName) {
		this.defaultoperatorName = defaultoperatorName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */
	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */
	@Column(name ="UPDATE_DATE",nullable=true,length=20)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  编号
	 */
	@Column(name ="NUMBER",nullable=true,length=80)
	public java.lang.String getNumber(){
		return this.number;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  编号
	 */
	public void setNumber(java.lang.String number){
		this.number = number;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="NAME",nullable=true,length=150)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  营业执照号
	 */
	@Column(name ="LICENCE",nullable=true,length=80)
	public java.lang.String getLicence(){
		return this.licence;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  营业执照号
	 */
	public void setLicence(java.lang.String licence){
		this.licence = licence;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结算单位
	 */
	@Column(name ="SETTLECOMPANY",nullable=true,length=32)
	public java.lang.String getSettlecompany(){
		return this.settlecompany;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结算单位
	 */
	public void setSettlecompany(java.lang.String settlecompany){
		this.settlecompany = settlecompany;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  开户银行
	 */
	@Column(name ="BANK",nullable=true,length=50)
	public java.lang.String getBank(){
		return this.bank;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  开户银行
	 */
	public void setBank(java.lang.String bank){
		this.bank = bank;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  银行账号
	 */
	@Column(name ="BANKNUMBER",nullable=true,length=40)
	public java.lang.String getBanknumber(){
		return this.banknumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  银行账号
	 */
	public void setBanknumber(java.lang.String banknumber){
		this.banknumber = banknumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户分类
	 */
	@Column(name ="TYPEID",nullable=true,length=50)
	public java.lang.String getTypeid(){
		return this.typeid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户分类
	 */
	public void setTypeid(java.lang.String typeid){
		this.typeid = typeid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  上级经销商
	 */
	@Column(name ="DEALER",nullable=true,length=32)
	public java.lang.String getDealer(){
		return this.dealer;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  上级经销商
	 */
	public void setDealer(java.lang.String dealer){
		this.dealer = dealer;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  默认业务员
	 */
	@Column(name ="DEFAULTOPERATOR",nullable=true,length=50)
	public java.lang.String getDefaultoperator(){
		return this.defaultoperator;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  默认业务员
	 */
	public void setDefaultoperator(java.lang.String defaultoperator){
		this.defaultoperator = defaultoperator;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  送货方式
	 */
	@Column(name ="DELIVERTYPE",nullable=true,length=50)
	public java.lang.String getDelivertype(){
		return this.delivertype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  送货方式
	 */
	public void setDelivertype(java.lang.String delivertype){
		this.delivertype = delivertype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分类id
	 */
	@Column(name ="PARENTID",nullable=true,length=32)
	public java.lang.String getParentid(){
		return this.parentid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分类id
	 */
	public void setParentid(java.lang.String parentid){
		this.parentid = parentid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  简称
	 */
	@Column(name ="SHORTNAME",nullable=true,length=50)
	public java.lang.String getShortname(){
		return this.shortname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  简称
	 */
	public void setShortname(java.lang.String shortname){
		this.shortname = shortname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  助记码
	 */
	@Column(name ="SHORTNUMBER",nullable=true,length=80)
	public java.lang.String getShortnumber(){
		return this.shortnumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  助记码
	 */
	public void setShortnumber(java.lang.String shortnumber){
		this.shortnumber = shortnumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人
	 */
	@Column(name ="CONTACT",nullable=true,length=50)
	public java.lang.String getContact(){
		return this.contact;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系人
	 */
	public void setContact(java.lang.String contact){
		this.contact = contact;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机号码
	 */
	@Column(name ="MOBILEPHONE",nullable=true,length=15)
	public java.lang.String getMobilephone(){
		return this.mobilephone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机号码
	 */
	public void setMobilephone(java.lang.String mobilephone){
		this.mobilephone = mobilephone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话
	 */
	@Column(name ="PHONE",nullable=true,length=40)
	public java.lang.String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电话
	 */
	public void setPhone(java.lang.String phone){
		this.phone = phone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  传真
	 */
	@Column(name ="FAX",nullable=true,length=40)
	public java.lang.String getFax(){
		return this.fax;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  传真
	 */
	public void setFax(java.lang.String fax){
		this.fax = fax;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  邮政编码
	 */
	@Column(name ="POSTALCODE",nullable=true,length=20)
	public java.lang.String getPostalcode(){
		return this.postalcode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  邮政编码
	 */
	public void setPostalcode(java.lang.String postalcode){
		this.postalcode = postalcode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  区域
	 */
	@Column(name ="REGIONID",nullable=true,length=50)
	public java.lang.String getRegionid(){
		return this.regionid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  区域
	 */
	public void setRegionid(java.lang.String regionid){
		this.regionid = regionid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  城市
	 */
	@Column(name ="CITY",nullable=true,length=50)
	public java.lang.String getCity(){
		return this.city;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  城市
	 */
	public void setCity(java.lang.String city){
		this.city = city;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  qq号
	 */
	@Column(name ="CIQNUMBER",nullable=true,length=20)
	public java.lang.String getCiqnumber(){
		return this.ciqnumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  qq号
	 */
	public void setCiqnumber(java.lang.String ciqnumber){
		this.ciqnumber = ciqnumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电子邮件
	 */
	@Column(name ="EMAIL",nullable=true,length=40)
	public java.lang.String getEmail(){
		return this.email;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电子邮件
	 */
	public void setEmail(java.lang.String email){
		this.email = email;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公司主页
	 */
	@Column(name ="HOMEPAGE",nullable=true,length=80)
	public java.lang.String getHomepage(){
		return this.homepage;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公司主页
	 */
	public void setHomepage(java.lang.String homepage){
		this.homepage = homepage;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  法人代表
	 */
	@Column(name ="CORPERATE",nullable=true,length=80)
	public java.lang.String getCorperate(){
		return this.corperate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  法人代表
	 */
	public void setCorperate(java.lang.String corperate){
		this.corperate = corperate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  行业
	 */
	@Column(name ="TRADE",nullable=true,length=50)
	public java.lang.String getTrade(){
		return this.trade;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  行业
	 */
	public void setTrade(java.lang.String trade){
		this.trade = trade;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  启用信控
	 */
	@Column(name ="ISCREDITMGR",nullable=true,length=32)
	public java.lang.Integer getIscreditmgr(){
		return this.iscreditmgr;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  启用信控
	 */
	public void setIscreditmgr(java.lang.Integer iscreditmgr){
		this.iscreditmgr = iscreditmgr;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  信用额度
	 */
	@Column(name ="CREDITAMOUNT",nullable=true,length=32)
	public java.math.BigDecimal getCreditamount(){
		return this.creditamount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  信用额度
	 */
	public void setCreditamount(java.math.BigDecimal creditamount){
		this.creditamount = creditamount;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  级次
	 */
	@Column(name ="LEVEL",nullable=true,length=32)
	public java.lang.Integer getLevel(){
		return this.level;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  级次
	 */
	public void setLevel(java.lang.Integer level){
		this.level = level;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系地址
	 */
	@Column(name ="ADDRESS",nullable=true,length=255)
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系地址
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="NOTE",nullable=true,length=255)
	public java.lang.String getNote(){
		return this.note;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setNote(java.lang.String note){
		this.note = note;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  禁用标记
	 */
	@Column(name ="DISABLE",nullable=true,length=32)
	public java.lang.Integer getDisable(){
		return this.disable;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  禁用标记
	 */
	public void setDisable(java.lang.Integer disable){
		this.disable = disable;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  引用次数
	 */
	@Column(name ="COUNT",nullable=true,length=32)
	public java.lang.Integer getCount(){
		return this.count;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  引用次数
	 */
	public void setCount(java.lang.Integer count){
		this.count = count;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  逻辑删除
	 */
	@Column(name ="DELETED",nullable=true,length=32)
	public java.lang.Integer getDeleted(){
		return this.deleted;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  逻辑删除
	 */
	public void setDeleted(java.lang.Integer deleted){
		this.deleted = deleted;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  版本号
	 */
    @Version
	@Column(name ="VERSION",nullable=true,length=32)
	public java.lang.Integer getVersion(){
		return this.version;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  版本号
	 */
	public void setVersion(java.lang.Integer version){
		this.version = version;
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

	@Column(name ="isDealer",nullable=true)
	public Integer getIsDealer() {
		return isDealer;
	}

	public void setIsDealer(Integer isDealer) {
		this.isDealer = isDealer;
	}

	@Transient
	public BigDecimal getCountAmount() {
		return countAmount;
	}

	public void setCountAmount(BigDecimal countAmount) {
		this.countAmount = countAmount;
	}
	@Column(name="USER_ID")
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	@Column(name="DELER_USER_ID")
	public String getDelerUserId() {
		return delerUserId;
	}
	public void setDelerUserId(String delerUserId) {
		this.delerUserId = delerUserId;
	}
	@Column(name="LOWER_DELER_USERID")
	public String getLowerDelerUserId() {
		return lowerDelerUserId;
	}
	public void setLowerDelerUserId(String lowerDelerUserId) {
		this.lowerDelerUserId = lowerDelerUserId;
	}

	@Column(name="sonId",nullable = true)
	public String getSonId() {
		return sonId;
	}

	public void setSonId(String sonId) {
		this.sonId = sonId;
	}
}
