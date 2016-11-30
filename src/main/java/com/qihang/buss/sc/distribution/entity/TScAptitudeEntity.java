package com.qihang.buss.sc.distribution.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**   
 * @Title: Entity
 * @Description: 资格审查
 * @author onlineGenerator
 * @date 2016-07-15 10:49:17
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_sc_aptitude", schema = "")
@SuppressWarnings("serial")
public class TScAptitudeEntity implements java.io.Serializable {
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
	/**单据编号*/
	@Excel(name="单据编号")
	private java.lang.String billNo;
	/**单据类型*/
	@Excel(name="单据类型")
	private java.lang.Integer tranType;
	/**单据日期*/
	@Excel(name="单据日期")
	private java.util.Date date;
	/**经销商*/
	@Excel(name="经销商")
	private java.lang.String itemID;
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
	/**联系地址*/
	@Excel(name="联系地址")
	private java.lang.String address;
	/**法人代表*/
	@Excel(name="法人代表")
	private java.lang.String corperate;
	/**注册资金*/
	@Excel(name="注册资金")
	private java.lang.Double regCapital;
	/**企业性质*/
	@Excel(name="企业性质")
	private java.lang.String economyKind;
	/**渠道*/
	@Excel(name="渠道")
	private java.lang.String trench;
	/**代理产品*/
	@Excel(name="代理产品")
	private java.lang.String itemName;
	/**预计销量（年）*/
	@Excel(name="预计销量（年）")
	private java.lang.Double planQty;
	/**销售渠道|A类*/
	@Excel(name="销售渠道|A类")
	private java.lang.Integer trenchA;
	/**销售渠道|B类*/
	@Excel(name="销售渠道|B类")
	private java.lang.Integer trenchB;
	/**销售渠道|C类*/
	@Excel(name="销售渠道|C类")
	private java.lang.Integer trenchC;
	/**销售渠道|其他*/
	@Excel(name="销售渠道|其他")
	private java.lang.Integer trenchO;
	/**审核人*/
	@Excel(name="审核人")
	private java.lang.String checkerID;
	/**制单人*/
	@Excel(name="制单人")
	private java.lang.String billerID;
	/**审核日期*/
	@Excel(name="审核日期")
	private java.util.Date checkDate;
	/**审核状态*/
	@Excel(name="审核状态")
	private java.lang.Integer checkState;
	/**作废标记*/
	@Excel(name="作废标记")
	private java.lang.Integer cancellation;
	/**是否合格经销商*/
	@Excel(name="是否合格经销商")
	private java.lang.Integer eligibility;
	/**摘要*/
	@Excel(name="摘要")
	private java.lang.String explanation;
	/**分支机构*/
	private java.lang.String sonID;
	/**逻辑删除标记*/
	private java.lang.Integer deleted;
	/**禁用标记*/
	private java.lang.Integer disabled;
	/**版本号*/
	private java.lang.Integer version;

	private Integer status;//单据状态
	private String auditDate;//审核日期
	private String auditorName;//审核人

	/**经办人*/
	private String empName;
	/**部门*/
	private String deptName;
	/**制单人*/
	private String billerName;
	/**分支机构*/
	private String sonName;
	/**经销商*/
	private String distributor;

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
	 *@return: java.lang.String  单据编号
	 */
	@Column(name ="BILLNO",nullable=true,length=50)
	public java.lang.String getBillNo(){
		return this.billNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据编号
	 */
	public void setBillNo(java.lang.String billNo){
		this.billNo = billNo;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  单据类型
	 */
	@Column(name ="TRANTYPE",nullable=true,length=32)
	public java.lang.Integer getTranType(){
		return this.tranType;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  单据类型
	 */
	public void setTranType(java.lang.Integer tranType){
		this.tranType = tranType;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  单据日期
	 */
	@Column(name ="DATE",nullable=true,length=32)
	public java.util.Date getDate(){
		return this.date;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  单据日期
	 */
	public void setDate(java.util.Date date){
		this.date = date;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  经销商
	 */
	@Column(name ="ITEMID",nullable=true,length=32)
	public java.lang.String getItemID(){
		return this.itemID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  经销商
	 */
	public void setItemID(java.lang.String itemID){
		this.itemID = itemID;
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
	public java.lang.String getMobilePhone(){
		return this.mobilePhone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机号码
	 */
	public void setMobilePhone(java.lang.String mobilePhone){
		this.mobilePhone = mobilePhone;
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
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  注册资金
	 */
	@Column(name ="REGCAPITAL",nullable=true,length=32)
	public java.lang.Double getRegCapital(){
		return this.regCapital;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  注册资金
	 */
	public void setRegCapital(java.lang.Double regCapital){
		this.regCapital = regCapital;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  企业性质
	 */
	@Column(name ="ECONOMYKIND",nullable=true,length=80)
	public java.lang.String getEconomyKind(){
		return this.economyKind;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  企业性质
	 */
	public void setEconomyKind(java.lang.String economyKind){
		this.economyKind = economyKind;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  渠道
	 */
	@Column(name ="TRENCH",nullable=true,length=255)
	public java.lang.String getTrench(){
		return this.trench;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  渠道
	 */
	public void setTrench(java.lang.String trench){
		this.trench = trench;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  代理产品
	 */
	@Column(name ="ITEMNAME",nullable=true,length=255)
	public java.lang.String getItemName(){
		return this.itemName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  代理产品
	 */
	public void setItemName(java.lang.String itemName){
		this.itemName = itemName;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  预计销量（年）
	 */
	@Column(name ="PLANQTY",nullable=true,length=32)
	public java.lang.Double getPlanQty(){
		return this.planQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  预计销量（年）
	 */
	public void setPlanQty(java.lang.Double planQty){
		this.planQty = planQty;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  销售渠道|A类
	 */
	@Column(name ="TRENCHA",nullable=true,length=32)
	public java.lang.Integer getTrenchA(){
		return this.trenchA;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  销售渠道|A类
	 */
	public void setTrenchA(java.lang.Integer trenchA){
		this.trenchA = trenchA;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  销售渠道|B类
	 */
	@Column(name ="TRENCHB",nullable=true,length=32)
	public java.lang.Integer getTrenchB(){
		return this.trenchB;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  销售渠道|B类
	 */
	public void setTrenchB(java.lang.Integer trenchB){
		this.trenchB = trenchB;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  销售渠道|C类
	 */
	@Column(name ="TRENCHC",nullable=true,length=32)
	public java.lang.Integer getTrenchC(){
		return this.trenchC;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  销售渠道|C类
	 */
	public void setTrenchC(java.lang.Integer trenchC){
		this.trenchC = trenchC;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  销售渠道|其他
	 */
	@Column(name ="TRENCHO",nullable=true,length=32)
	public java.lang.Integer getTrenchO(){
		return this.trenchO;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  销售渠道|其他
	 */
	public void setTrenchO(java.lang.Integer trenchO){
		this.trenchO = trenchO;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核人
	 */
	@Column(name ="CHECKERID",nullable=true,length=50)
	public java.lang.String getCheckerID(){
		return this.checkerID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核人
	 */
	public void setCheckerID(java.lang.String checkerID){
		this.checkerID = checkerID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  制单人
	 */
	@Column(name ="BILLERID",nullable=true,length=50)
	public java.lang.String getBillerID(){
		return this.billerID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  制单人
	 */
	public void setBillerID(java.lang.String billerID){
		this.billerID = billerID;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  审核日期
	 */
	@Column(name ="CHECKDATE",nullable=true,length=32)
	public java.util.Date getCheckDate(){
		return this.checkDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  审核日期
	 */
	public void setCheckDate(java.util.Date checkDate){
		this.checkDate = checkDate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  审核状态
	 */
	@Column(name ="CHECKSTATE",nullable=true,length=32)
	public java.lang.Integer getCheckState(){
		return this.checkState;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  审核状态
	 */
	public void setCheckState(java.lang.Integer checkState){
		this.checkState = checkState;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  作废标记
	 */
	@Column(name ="CANCELLATION",nullable=true,length=32)
	public java.lang.Integer getCancellation(){
		return this.cancellation;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  作废标记
	 */
	public void setCancellation(java.lang.Integer cancellation){
		this.cancellation = cancellation;
	}



	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否合格经销商
	 */
	@Column(name ="ISELIGIBILITY",nullable=true,length=32)
	public Integer getEligibility() {
		return eligibility;
	}

	public void setEligibility(Integer eligibility) {
		this.eligibility = eligibility;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  摘要
	 */
	@Column(name ="EXPLANATION",nullable=true,length=255)
	public java.lang.String getExplanation(){
		return this.explanation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  摘要
	 */
	public void setExplanation(java.lang.String explanation){
		this.explanation = explanation;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分支机构
	 */
	@Column(name ="SONID",nullable=true,length=50)
	public java.lang.String getSonID(){
		return this.sonID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分支机构
	 */
	public void setSonID(java.lang.String sonID){
		this.sonID = sonID;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  逻辑删除标记
	 */
	@Column(name ="DELETED",nullable=true,length=32)
	public java.lang.Integer getDeleted(){
		return this.deleted;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  逻辑删除标记
	 */
	public void setDeleted(java.lang.Integer deleted){
		this.deleted = deleted;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  禁用标记
	 */
	@Column(name ="DISABLED",nullable=true,length=32)
	public java.lang.Integer getDisabled(){
		return this.disabled;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  禁用标记
	 */
	public void setDisabled(java.lang.Integer disabled){
		this.disabled = disabled;
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

	@Transient
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Transient
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Transient
	public String getBillerName() {
		return billerName;
	}
	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}

	@Transient
	public String getSonName() {
		return sonName;
	}
	public void setSonName(String sonName) {
		this.sonName = sonName;
	}

	@Transient
	public String getDistributor() {
		return distributor;
	}

	public void setDistributor(String distributor) {
		this.distributor = distributor;
	}
}
