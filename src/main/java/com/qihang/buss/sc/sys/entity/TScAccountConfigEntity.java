package com.qihang.buss.sc.sys.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.xml.soap.Text;
import java.sql.Blob;

import org.hibernate.annotations.GenericGenerator;
import com.qihang.winter.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

/**   
 * @Title: Entity
 * @Description: 账套设置
 * @author onlineGenerator
 * @date 2016-08-31 20:16:10
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_ACCOUNT_CONFIG", schema = "")
@SuppressWarnings("serial")
public class TScAccountConfigEntity implements java.io.Serializable {
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
	/**公司名称*/
	@Excel(name="公司名称")
	private java.lang.String companyName;
	/**税号*/
	@Excel(name="税号")
	private java.lang.String taxNumber;
	/**银行账号*/
	@Excel(name="银行账号")
	private java.lang.String bankAccount;
	/**公司地址*/
	@Excel(name="公司地址")
	private java.lang.String companyAddress;
	/**电话*/
	@Excel(name="电话")
	private java.lang.String phone;
	/**传真*/
	@Excel(name="传真")
	private java.lang.String fax;
	/**E_Mail*/
	@Excel(name="E_Mail")
	private java.lang.String email;
	/**公司网址*/
	@Excel(name="公司网址")
	private java.lang.String companyUrl;
	/**注册号*/
	@Excel(name="注册号")
	private java.lang.String registrationNumber;
	/**数据源ID*/
	@Excel(name="数据源ID")
	private java.lang.String dbId;
	/**账套名称*/
	@Excel(name="实体库")
	private java.lang.String dbKey;
	/**联系人*/
	@Excel(name="联系人")
	private java.lang.String contact;
	/**允许负库存结账*/
	@Excel(name="允许负库存结账")
	private java.lang.Integer minusInventoryAccount;
	/**允许负库存出库*/
	@Excel(name="允许负库存出库")
	private java.lang.Integer minusInventorySl;
	/**账套启用年份*/
	@Excel(name="账套启用年月")
	@DateTimeFormat(pattern = "YYYY-MM")
	private java.util.Date accountStartDate;
//	/**账套启用月份*/
//	@Excel(name="账套启用月份")
//	private java.lang.String accountStartMonth;
	/**程序版本*/
	@Excel(name="程序版本")
	private java.lang.String systemVersion;
	/**版本*/
	@Excel(name="版本")
	private java.lang.Integer version;
	/**账套开启状态*/
	@Excel(name="账套开启状态")
	private java.lang.Integer openState;
	/**开启时间*/
	@Excel(name="开启时间")
	private java.util.Date openDate;
	/**开启人Id*/
	@Excel(name="开启人Id")
	private java.lang.String openBy;
	/**开启人名称*/
	@Excel(name="开启人名称")
	private java.lang.String openName;
	/**账套结账状态*/
	@Excel(name="账套结账状态")
	private java.lang.Integer closeState;
	/**结账时间*/
	@Excel(name="结账时间")
	private java.util.Date closeDate;
	/**结账人Id*/
	@Excel(name="结账人Id")
	private java.lang.String closeBy;
	/**结账人名称*/
	@Excel(name="结账人名称")
	private java.lang.String closeName;
	/**当前期别年份*/
	@Excel(name="当前期别年份")
	@DateTimeFormat(pattern = "YYYY-MM")
	private java.util.Date stageStartDate;

	/**非持久-账套名称*/
	private java.lang.String dbDescription;
	
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
	 *@return: java.lang.String  公司名称
	 */
	@Column(name ="COMPANYNAME",nullable=true,length=32)
	public java.lang.String getCompanyName(){
		return this.companyName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公司名称
	 */
	public void setCompanyName(java.lang.String companyName){
		this.companyName = companyName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  税号
	 */
	@Column(name ="TAXNUMBER",nullable=true,length=32)
	public java.lang.String getTaxNumber(){
		return this.taxNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  税号
	 */
	public void setTaxNumber(java.lang.String taxNumber){
		this.taxNumber = taxNumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  银行账号
	 */
	@Column(name ="BANKACCOUNT",nullable=true,length=32)
	public java.lang.String getBankAccount(){
		return this.bankAccount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  银行账号
	 */
	public void setBankAccount(java.lang.String bankAccount){
		this.bankAccount = bankAccount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公司地址
	 */
	@Column(name ="COMPANYADDRESS",nullable=true,length=32)
	public java.lang.String getCompanyAddress(){
		return this.companyAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公司地址
	 */
	public void setCompanyAddress(java.lang.String companyAddress){
		this.companyAddress = companyAddress;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话
	 */
	@Column(name ="PHONE",nullable=true,length=32)
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
	@Column(name ="FAX",nullable=true,length=32)
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
	 *@return: java.lang.String  E_Mail
	 */
	@Column(name ="EMAIL",nullable=true,length=32)
	public java.lang.String getEmail(){
		return this.email;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  E_Mail
	 */
	public void setEmail(java.lang.String email){
		this.email = email;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公司网址
	 */
	@Column(name ="COMPANYURL",nullable=true,length=32)
	public java.lang.String getCompanyUrl(){
		return this.companyUrl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公司网址
	 */
	public void setCompanyUrl(java.lang.String companyUrl){
		this.companyUrl = companyUrl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  注册号
	 */
	@Column(name ="REGISTRATIONNUMBER",nullable=true,length=32)
	public java.lang.String getRegistrationNumber(){
		return this.registrationNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  注册号
	 */
	public void setRegistrationNumber(java.lang.String registrationNumber){
		this.registrationNumber = registrationNumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数据源ID
	 */
	@Column(name ="DBID",nullable=true,length=36)
	public java.lang.String getDbId(){
		return this.dbId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数据源ID
	 */
	public void setDbId(java.lang.String dbId){
		this.dbId = dbId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  账套名称
	 */
	@Column(name ="DBKEY",nullable=true,length=50)
	public java.lang.String getDbKey(){
		return this.dbKey;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  账套名称
	 */
	public void setDbKey(java.lang.String dbKey){
		this.dbKey = dbKey;
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  允许负库存结账
	 */
	@Column(name ="MINUSINVENTORYACCOUNT",nullable=true,length=32)
	public java.lang.Integer getMinusInventoryAccount(){
		return this.minusInventoryAccount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  允许负库存结账
	 */
	public void setMinusInventoryAccount(java.lang.Integer minusInventoryAccount){
		this.minusInventoryAccount = minusInventoryAccount;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  允许负库存出库
	 */
	@Column(name ="MINUSINVENTORYSL",nullable=true,length=32)
	public java.lang.Integer getMinusInventorySl(){
		return this.minusInventorySl;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  允许负库存出库
	 */
	public void setMinusInventorySl(java.lang.Integer minusInventorySl){
		this.minusInventorySl = minusInventorySl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  账套启用年月
	 */
	@Column(name ="ACCOUNTSTARTDATE",nullable=true,length=32)
	public java.util.Date getAccountStartDate(){
		return this.accountStartDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  账套启用年月
	 */
	public void setAccountStartDate(java.util.Date accountStartYear){
		this.accountStartDate = accountStartYear;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  当前期别年月
	 */
	@Column(name ="STAGESTARTDATE",nullable=true,length=32)
	public java.util.Date getStageStartDate(){
		return this.stageStartDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  当前期别年月
	 */
	public void setStageStartDate(java.util.Date stageStartDate){
		this.stageStartDate = stageStartDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  账套启用月份
	 */
//	@Column(name ="ACCOUNTSTARTMONTH",nullable=true,length=32)
//	public java.lang.String getAccountStartMonth(){
//		return this.accountStartMonth;
//	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  账套启用月份
	 */
//	public void setAccountStartMonth(java.lang.String accountStartMonth){
//		this.accountStartMonth = accountStartMonth;
//	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  程序版本
	 */
	@Column(name ="SYSTEMVERSION",nullable=true,length=32)
	public java.lang.String getSystemVersion(){
		return this.systemVersion;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  程序版本
	 */
	public void setSystemVersion(java.lang.String systemVersion){
		this.systemVersion = systemVersion;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  版本
	 */
	@Column(name ="VERSION",nullable=true,length=32)
	@Version
	public java.lang.Integer getVersion(){
		return this.version;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  版本
	 */
	public void setVersion(java.lang.Integer version){
		this.version = version;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  账套开启状态
	 */

	@Column(name ="OPENSTATE",nullable=true,length=32)
	public java.lang.Integer getOpenState(){
		return this.openState;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  账套开启状态
	 */
	public void setOpenState(java.lang.Integer openState){
		this.openState = openState;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  开启时间
	 */

	@Column(name ="OPENDATE",nullable=true,length=32)
	public java.util.Date getOpenDate(){
		return this.openDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  开启时间
	 */
	public void setOpenDate(java.util.Date openDate){
		this.openDate = openDate;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  开启人Id
	 */

	@Column(name ="OPENBY",nullable=true,length=36)
	public java.lang.String getOpenBy(){
		return this.openBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  开启人Id
	 */
	public void setOpenBy(java.lang.String openBy){
		this.openBy = openBy;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  开启人名称
	 */

	@Column(name ="OPENNAME",nullable=true,length=36)
	public java.lang.String getOpenName(){
		return this.openName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  开启人名称
	 */
	public void setOpenName(java.lang.String openName){
		this.openName = openName;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  账套结账状态
	 */

	@Column(name ="CLOSESTATE",nullable=true,length=32)
	public java.lang.Integer getCloseState(){
		return this.closeState;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  账套结账状态
	 */
	public void setCloseState(java.lang.Integer closeState){
		this.closeState = closeState;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  结账时间
	 */

	@Column(name ="CLOSEDATE",nullable=true,length=36)
	public java.util.Date getCloseDate(){
		return this.closeDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  结账时间
	 */
	public void setCloseDate(java.util.Date closeDate){
		this.closeDate = closeDate;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结账人Id
	 */

	@Column(name ="CLOSEBY",nullable=true,length=36)
	public java.lang.String getCloseBy(){
		return this.closeBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结账人Id
	 */
	public void setCloseBy(java.lang.String closeBy){
		this.closeBy = closeBy;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结账人名称
	 */

	@Column(name ="CLOSENAME",nullable=true,length=36)
	public java.lang.String getCloseName(){
		return this.closeName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结账人名称
	 */
	public void setCloseName(java.lang.String closeName){
		this.closeName = closeName;
	}
	@Transient
	public String getDbDescription() {
		return dbDescription;
	}

	public void setDbDescription(String dbDescription) {
		this.dbDescription = dbDescription;
	}
}
