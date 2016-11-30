package com.qihang.buss.sc.sales.entity;
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

/**   
 * @Title: Entity
 * @Description: 采购换货单
 * @author onlineGenerator
 * @date 2016-07-19 17:41:15
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_IC_JHStockBill", schema = "")
@SuppressWarnings("serial")
public class TScIcJhstockbillEntity implements java.io.Serializable {
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
	/**单据类型*/
	private java.lang.String tranType;
	/**单据编号*/
  @Excel(name="单据编号")
	private java.lang.String billNo;
	/**单据日期*/
  @Excel(name="单据日期")
	private java.util.Date date;
	/**供应商*/
  @Excel(name="供应商")
	private java.lang.String itemId;
	/**经办人*/
  @Excel(name="经办人")
	private java.lang.String empId;
	/**部门*/
  @Excel(name="部门")
	private java.lang.String deptId;
	/**优惠金额*/
  @Excel(name="优惠金额")
	private java.lang.Double rebateAmount;
	/**换货差额*/
  @Excel(name="换货差额")
	private java.lang.Double diffAmount;
	/**本次付款*/
  @Excel(name="本次付款")
	private java.lang.Double curPayAmount;
	/**结算账户*/
  @Excel(name="结算账户")
	private java.lang.String accountId;
	/**应付金额*/
  @Excel(name="应付金额")
	private java.lang.Double allAmount;
	/**付款金额*/
  @Excel(name="付款金额")
	private java.lang.Double checkAmount;
	/**源单类型*/
  @Excel(name="源单类型")
	private java.lang.String classIDSrc;
	/**源单主键*/
	private java.lang.String idSrc;
	/**源单编号*/
	private java.lang.String billNoSrc;
	/**审核人*/
  @Excel(name="审核人")
	private java.lang.String checkerId;
	/**制单人*/
  @Excel(name="制单人")
	private java.lang.String billerId;
	/**审核日期*/
  @Excel(name="审核日期")
	private java.util.Date checkDate;
	/**审核状态*/
	private java.lang.Integer checkState;
	/**取消标记*/
	private java.lang.Integer cancellation;
	/**摘要*/
  @Excel(name="摘要")
	private java.lang.String explanation;
	/**分支机构*/
  @Excel(name="分支机构")
	private java.lang.String sonId;

	private Integer version;


	private String itemName;
	private String empName;
	private String deptName;
	private String billerName;
	private String accountName;
	private String sonName;
	
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
	 *@return: java.lang.String  单据类型
	 */
	
	@Column(name ="TRANTYPE",nullable=true,length=32)
	public java.lang.String getTranType(){
		return this.tranType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据类型
	 */
	public void setTranType(java.lang.String tranType){
		this.tranType = tranType;
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  单据日期
	 */
	
	@Column(name ="DATE",nullable=true,length=20)
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
	 *@return: java.lang.String  供应商
	 */
	
	@Column(name ="ITEMID",nullable=true,length=32)
	public java.lang.String getItemId(){
		return this.itemId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  供应商
	 */
	public void setItemId(java.lang.String itemId){
		this.itemId = itemId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  经办人
	 */
	
	@Column(name ="EMPID",nullable=true,length=32)
	public java.lang.String getEmpId(){
		return this.empId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  经办人
	 */
	public void setEmpId(java.lang.String empId){
		this.empId = empId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门
	 */
	
	@Column(name ="DEPTID",nullable=true,length=32)
	public java.lang.String getDeptId(){
		return this.deptId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门
	 */
	public void setDeptId(java.lang.String deptId){
		this.deptId = deptId;
	}
	
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  优惠金额
	 */
	
	@Column(name ="REBATEAMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getRebateAmount(){
		return this.rebateAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  优惠金额
	 */
	public void setRebateAmount(java.lang.Double rebateAmount){
		this.rebateAmount = rebateAmount;
	}
	
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  换货差额
	 */
	
	@Column(name ="DIFFAMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getDiffAmount(){
		return this.diffAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  换货差额
	 */
	public void setDiffAmount(java.lang.Double diffAmount){
		this.diffAmount = diffAmount;
	}
	
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  本次付款
	 */
	
	@Column(name ="CURPAYAMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getCurPayAmount(){
		return this.curPayAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  本次付款
	 */
	public void setCurPayAmount(java.lang.Double curPayAmount){
		this.curPayAmount = curPayAmount;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结算账户
	 */
	
	@Column(name ="ACCOUNTID",nullable=true,length=32)
	public java.lang.String getAccountId(){
		return this.accountId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结算账户
	 */
	public void setAccountId(java.lang.String accountId){
		this.accountId = accountId;
	}
	
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  应付金额
	 */
	
	@Column(name ="ALLAMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getAllAmount(){
		return this.allAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  应付金额
	 */
	public void setAllAmount(java.lang.Double allAmount){
		this.allAmount = allAmount;
	}
	
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  付款金额
	 */
	
	@Column(name ="CHECKAMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getCheckAmount(){
		return this.checkAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  付款金额
	 */
	public void setCheckAmount(java.lang.Double checkAmount){
		this.checkAmount = checkAmount;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单类型
	 */
	
	@Column(name ="CLASSID_SRC",nullable=true,length=10)
	public java.lang.String getClassIDSrc(){
		return this.classIDSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单类型
	 */
	public void setClassIDSrc(java.lang.String classIDSrc){
		this.classIDSrc = classIDSrc;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单主键
	 */
	
	@Column(name ="ID_SRC",nullable=true,length=32)
	public java.lang.String getIdSrc(){
		return this.idSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单主键
	 */
	public void setIdSrc(java.lang.String idSrc){
		this.idSrc = idSrc;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单编号
	 */
	
	@Column(name ="BILLNO_SRC",nullable=true,length=50)
	public java.lang.String getBillNoSrc(){
		return this.billNoSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单编号
	 */
	public void setBillNoSrc(java.lang.String billNoSrc){
		this.billNoSrc = billNoSrc;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核人
	 */
	
	@Column(name ="CHECKERID",nullable=true,length=32)
	public java.lang.String getCheckerId(){
		return this.checkerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核人
	 */
	public void setCheckerId(java.lang.String checkerId){
		this.checkerId = checkerId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  制单人
	 */
	
	@Column(name ="BILLERID",nullable=true,length=32)
	public java.lang.String getBillerId(){
		return this.billerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  制单人
	 */
	public void setBillerId(java.lang.String billerId){
		this.billerId = billerId;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  审核日期
	 */
	
	@Column(name ="CHECKDATE",nullable=true,length=20)
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
	
	@Column(name ="CHECKSTATE",nullable=true,length=1)
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
	 *@return: java.lang.Integer  取消标记
	 */
	
	@Column(name ="CANCELLATION",nullable=true,length=1)
	public java.lang.Integer getCancellation(){
		return this.cancellation;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  取消标记
	 */
	public void setCancellation(java.lang.Integer cancellation){
		this.cancellation = cancellation;
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
	
	@Column(name ="SONID",nullable=true,length=32)
	public java.lang.String getSonId(){
		return this.sonId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分支机构
	 */
	public void setSonId(java.lang.String sonId){
		this.sonId = sonId;
	}

	@Version
	@Column(name ="version",nullable=true)
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}


	@Transient
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Transient
	public String getSonName() {
		return sonName;
	}

	public void setSonName(String sonName) {
		this.sonName = sonName;
	}
}
