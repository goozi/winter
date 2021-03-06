package com.qihang.buss.sc.distribution.entity;
import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.xml.soap.Text;
import java.sql.Blob;

import org.apache.tools.ant.taskdefs.Java;
import org.hibernate.annotations.GenericGenerator;
import com.qihang.winter.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 发货管理
 * @author onlineGenerator
 * @date 2016-08-08 19:52:05
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_DRP_StockBill", schema = "")
@SuppressWarnings("serial")
public class TScDrpStockbillEntity implements java.io.Serializable {
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
	/**下游经销商*/
  @Excel(name="下游经销商")
	private java.lang.String dealerID;
	/**经办人*/
  @Excel(name="经办人")
	private java.lang.String empID;
	/**部门*/
  @Excel(name="部门")
	private java.lang.String deptID;
	/**仓库*/
  @Excel(name="仓库")
	private java.lang.String rStockId;
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
	/**优惠金额*/
  @Excel(name="优惠金额")
	private java.math.BigDecimal rebateAmount;
	/**物流费用*/
  @Excel(name="物流费用")
	private java.lang.Double freight;
	/**结算账号*/
  @Excel(name="结算账号")
	private java.lang.String accountID;
	/**本次收款*/
  @Excel(name="本次收款")
	private java.math.BigDecimal curPayAmount;
	/**重量*/
  @Excel(name="重量")
	private java.math.BigDecimal weight;
	/**应收金额*/
  @Excel(name="应收金额")
	private java.math.BigDecimal allAmount;
	/**源单类型*/
  @Excel(name="源单类型")
	private java.lang.Integer classIDSrc;
	/**分支机构*/
  @Excel(name="分支机构")
	private java.lang.String sonID;
	/**摘要*/
  @Excel(name="摘要")
	private java.lang.String explanation;
	/**单据金额*/
	private java.math.BigDecimal amount;
	/**收款金额*/
	private java.math.BigDecimal checkAmount;
	/**确认状态*/
	private java.lang.Integer affirmStatus;
	/**确认人*/
	private java.lang.String affirmID;
	/**确认时间*/
	private java.util.Date affirmDate;
	/**丢失金额*/
	private java.math.BigDecimal amountLoss;
	/**源单主键*/
	private java.lang.String interIDSrc;
	/**源单编号*/
  @Excel(name="源单编号")
	private java.lang.String billNoSrc;
	/**审核人*/
	private java.lang.String checkerID;
	/**制单人*/
	private java.lang.String billerID;
	/**审核日期*/
	private java.util.Date checkDate;
	/**审核状态*/
	private java.lang.Integer checkState;
	/**作废标记*/
	private java.lang.String cancellation;

	private java.lang.String dealerName;
	private java.lang.String empName;
	private java.lang.String deptName;
	private java.lang.String rStockName;
	private java.lang.String sonName;
	private java.lang.String checkerName;
	private java.lang.String billerName;
	private String accountName;
	private String affirmName;

	/**逻辑删除标记*/
	private java.lang.Integer deleted;
	/**禁用标记*/
	private java.lang.Integer disabled;
	/**版本号*/
	private java.lang.Integer version;
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
	
	@Column(name ="BILLNO",nullable=true,length=32)
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
	 *@return: java.lang.String  下游经销商
	 */
	
	@Column(name ="DEALERID",nullable=true,length=32)
	public java.lang.String getDealerID(){
		return this.dealerID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  下游经销商
	 */
	public void setDealerID(java.lang.String dealerID){
		this.dealerID = dealerID;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  经办人
	 */
	
	@Column(name ="EMPID",nullable=true,length=32)
	public java.lang.String getEmpID(){
		return this.empID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  经办人
	 */
	public void setEmpID(java.lang.String empID){
		this.empID = empID;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门
	 */
	
	@Column(name ="DEPTID",nullable=true,length=32)
	public java.lang.String getDeptID(){
		return this.deptID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门
	 */
	public void setDeptID(java.lang.String deptID){
		this.deptID = deptID;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库
	 */
	
	@Column(name ="STOCKID",nullable=true,length=32)
	public String getrStockId() {
		return rStockId;
	}
	public void setrStockId(String rStockId) {
		this.rStockId = rStockId;
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
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  优惠金额
	 */
	
	@Column(name ="REBATEAMOUNT",nullable=true,length=32)
	public java.math.BigDecimal getRebateAmount(){
		return this.rebateAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  优惠金额
	 */
	public void setRebateAmount(java.math.BigDecimal rebateAmount){
		this.rebateAmount = rebateAmount;
	}
	
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double 物流费用
	 */
	
	@Column(name ="FREIGHT",nullable=true,length=32)
	public java.lang.Double getFreight(){
		return this.freight;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  物流费用
	 */
	public void setFreight(java.lang.Double freight){
		this.freight = freight;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结算账号
	 */
	
	@Column(name ="ACCOUNTID",nullable=true,length=32)
	public java.lang.String getAccountID(){
		return this.accountID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结算账号
	 */
	public void setAccountID(java.lang.String accountID){
		this.accountID = accountID;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本次收款
	 */
	
	@Column(name ="CURPAYAMOUNT",nullable=true,length=32)
	public java.math.BigDecimal getCurPayAmount(){
		return this.curPayAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本次收款
	 */
	public void setCurPayAmount(java.math.BigDecimal curPayAmount){
		this.curPayAmount = curPayAmount;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  重量
	 */
	
	@Column(name ="WEIGHT",nullable=true,length=32)
	public java.math.BigDecimal getWeight(){
		return this.weight;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  重量
	 */
	public void setWeight(java.math.BigDecimal weight){
		this.weight = weight;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  应收金额
	 */
	
	@Column(name ="ALLAMOUNT",nullable=true,length=32)
	public java.math.BigDecimal getAllAmount(){
		return this.allAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  应收金额
	 */
	public void setAllAmount(java.math.BigDecimal allAmount){
		this.allAmount = allAmount;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  源单类型
	 */
	
	@Column(name ="CLASSID_SRC",nullable=true,length=32)
	public java.lang.Integer getClassIDSrc(){
		return this.classIDSrc;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  源单类型
	 */
	public void setClassIDSrc(java.lang.Integer classIDSrc){
		this.classIDSrc = classIDSrc;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分支机构
	 */
	
	@Column(name ="SONID",nullable=true,length=32)
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
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  单据金额
	 */
	
	@Column(name ="AMOUNT",nullable=true,length=32)
	public java.math.BigDecimal getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  单据金额
	 */
	public void setAmount(java.math.BigDecimal amount){
		this.amount = amount;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  收款金额
	 */
	
	@Column(name ="CHECKAMOUNT",nullable=true,length=32)
	public java.math.BigDecimal getCheckAmount(){
		return this.checkAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  收款金额
	 */
	public void setCheckAmount(java.math.BigDecimal checkAmount){
		this.checkAmount = checkAmount;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  确认状态
	 */
	
	@Column(name ="AFFIRMSTATUS",nullable=true,length=32)
	public java.lang.Integer getAffirmStatus(){
		return this.affirmStatus;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  确认状态
	 */
	public void setAffirmStatus(java.lang.Integer affirmStatus){
		this.affirmStatus = affirmStatus;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  确认人
	 */
	
	@Column(name ="AFFIRMID",nullable=true,length=32)
	public java.lang.String getAffirmID(){
		return this.affirmID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  确认人
	 */
	public void setAffirmID(java.lang.String affirmID){
		this.affirmID = affirmID;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  确认时间
	 */
	
	@Column(name ="AFFIRMDATE",nullable=true,length=32)
	public java.util.Date getAffirmDate(){
		return this.affirmDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  确认时间
	 */
	public void setAffirmDate(java.util.Date affirmDate){
		this.affirmDate = affirmDate;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  丢失金额
	 */
	
	@Column(name ="AMOUNTLOSS",nullable=true,length=32)
	public java.math.BigDecimal getAmountLoss(){
		return this.amountLoss;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  丢失金额
	 */
	public void setAmountLoss(java.math.BigDecimal amountLoss){
		this.amountLoss = amountLoss;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单主键
	 */
	
	@Column(name ="INTERID_SRC",nullable=true,length=32)
	public java.lang.String getInterIDSrc(){
		return this.interIDSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单主键
	 */
	public void setInterIDSrc(java.lang.String interIDSrc){
		this.interIDSrc = interIDSrc;
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
	
	@Column(name ="BILLERID",nullable=true,length=32)
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  作废标记
	 */
	
	@Column(name ="CANCELLATION",nullable=true,length=32)
	public java.lang.String getCancellation(){
		return this.cancellation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  作废标记
	 */
	public void setCancellation(java.lang.String cancellation){
		this.cancellation = cancellation;
	}
@Transient
	public String getDealerName() {
		return dealerName;
	}

	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
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
	public String getrStockName() {
		return rStockName;
	}
	public void setrStockName(String rStockName) {
		this.rStockName = rStockName;
	}
	@Transient
	public String getSonName() {
		return sonName;
	}

	public void setSonName(String sonName) {
		this.sonName = sonName;
	}
	@Transient
	public String getCheckerName() {
		return checkerName;
	}

	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}
	@Transient
	public String getBillerName() {
		return billerName;
	}

	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}
	@Column(name ="DELETED")
	public Integer getDeleted() {
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	@Column(name ="Disabled")
	public Integer getDisabled() {
		return disabled;
	}
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}
	@Version
	@Column(name ="Version")
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	@Transient
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	@Transient
	public String getAffirmName() {
		return affirmName;
	}
	public void setAffirmName(String affirmName) {
		this.affirmName = affirmName;
	}
}
