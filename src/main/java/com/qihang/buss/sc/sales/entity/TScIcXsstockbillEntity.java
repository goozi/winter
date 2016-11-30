package com.qihang.buss.sc.sales.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 销售换货单
 * @author onlineGenerator
 * @date 2016-08-11 16:43:33
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_IC_XsStockBill", schema = "")
@SuppressWarnings("serial")
public class TScIcXsstockbillEntity implements java.io.Serializable {
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
	/**单据类型*/
	private String tranType;
	/**单据编号*/
	private String billNo;
	/**单据日期*/
	private Date date;
	/**客户*/
  @Excel(name="客户")
	private String itemId;
	/**经办人*/
  @Excel(name="经办人")
	private String empId;
	/**部门*/
  @Excel(name="部门")
	private String deptId;
	/**优惠金额*/
  @Excel(name="优惠金额")
	private BigDecimal rebateAmount;
	/**本次收款*/
  @Excel(name="本次收款")
	private BigDecimal curPayAmount;
	/**结算账户*/
  @Excel(name="结算账户")
	private String accountID;
	/**物流费用*/
  @Excel(name="物流费用")
	private BigDecimal freight;
	/**重量*/
  @Excel(name="重量")
	private BigDecimal weight;
	/**应收金额*/
  @Excel(name="应收金额")
	private BigDecimal allAmount;
	/**源单类型*/
  @Excel(name="源单类型")
	private String classIDSrc;
	/**源单主键*/
	private String idSrc;
	/**源单编号*/
	private String billNoSrc;
	/**制单人*/
  @Excel(name="制单人")
	private String billerId;
	/**审核人*/
  @Excel(name="审核人")
	private String checkerId;
	/**审核日期*/
  @Excel(name="审核日期")
	private Date checkDate;
	/**审核状态*/
	private Integer checkState;
	/**作废标记*/
	private Integer cancellation;
	/**摘要*/
  @Excel(name="摘要")
	private String explanation;
	/**分支机构*/
  @Excel(name="分支机构")
	private String sonId;
	/**版本号*/
	private Integer version;
	/**收款金额*/
	private BigDecimal checkAmount;
	/**换货差额*/
	private BigDecimal diffAmount;

	private String itemName;
	private String empName;
	private String deptName;
	private String accountName;
	private String billerName;
	private String sonName;
	private String classIDName;

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
	 *@return: java.lang.String  单据类型
	 */

	@Column(name ="TRANTYPE",nullable=true,length=11)
	public String getTranType(){
		return this.tranType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据类型
	 */
	public void setTranType(String tranType){
		this.tranType = tranType;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单据编号
	 */

	@Column(name ="BILLNO",nullable=true,length=50)
	public String getBillNo(){
		return this.billNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据编号
	 */
	public void setBillNo(String billNo){
		this.billNo = billNo;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  单据日期
	 */

	@Column(name ="DATE",nullable=true,length=20)
	public Date getDate(){
		return this.date;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  单据日期
	 */
	public void setDate(Date date){
		this.date = date;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户
	 */

	@Column(name ="ITEMID",nullable=true,length=32)
	public String getItemId(){
		return this.itemId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户
	 */
	public void setItemId(String itemId){
		this.itemId = itemId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  经办人
	 */

	@Column(name ="EMPID",nullable=true,length=32)
	public String getEmpId(){
		return this.empId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  经办人
	 */
	public void setEmpId(String empId){
		this.empId = empId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门
	 */

	@Column(name ="DEPTID",nullable=true,length=32)
	public String getDeptId(){
		return this.deptId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门
	 */
	public void setDeptId(String deptId){
		this.deptId = deptId;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  优惠金额
	 */

	@Column(name ="REBATEAMOUNT",nullable=true,scale=10,length=20)
	public BigDecimal getRebateAmount(){
		return this.rebateAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  优惠金额
	 */
	public void setRebateAmount(BigDecimal rebateAmount){
		this.rebateAmount = rebateAmount;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本次收款
	 */

	@Column(name ="CURPAYAMOUNT",nullable=true,scale=10,length=20)
	public BigDecimal getCurPayAmount(){
		return this.curPayAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本次收款
	 */
	public void setCurPayAmount(BigDecimal curPayAmount){
		this.curPayAmount = curPayAmount;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结算账户
	 */

	@Column(name ="ACCOUNTID",nullable=true,length=32)
	public String getAccountID(){
		return this.accountID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结算账户
	 */
	public void setAccountID(String accountID){
		this.accountID = accountID;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  物流费用
	 */

	@Column(name ="FREIGHT",nullable=true,scale=10,length=20)
	public BigDecimal getFreight(){
		return this.freight;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  物流费用
	 */
	public void setFreight(BigDecimal freight){
		this.freight = freight;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  重量
	 */

	@Column(name ="WEIGHT",nullable=true,scale=10,length=20)
	public BigDecimal getWeight(){
		return this.weight;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  重量
	 */
	public void setWeight(BigDecimal weight){
		this.weight = weight;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  应收金额
	 */

	@Column(name ="ALLAMOUNT",nullable=true,scale=10,length=20)
	public BigDecimal getAllAmount(){
		return this.allAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  应收金额
	 */
	public void setAllAmount(BigDecimal allAmount){
		this.allAmount = allAmount;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单类型
	 */

	@Column(name ="CLASSID_SRC",nullable=true,length=32)
	public String getClassIDSrc(){
		return this.classIDSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单类型
	 */
	public void setClassIDSrc(String classIDSrc){
		this.classIDSrc = classIDSrc;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单主键
	 */

	@Column(name ="ID_SRC",nullable=true,length=32)
	public String getIdSrc(){
		return this.idSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单主键
	 */
	public void setIdSrc(String idSrc){
		this.idSrc = idSrc;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单编号
	 */

	@Column(name ="BILLNO_SRC",nullable=true,length=50)
	public String getBillNoSrc(){
		return this.billNoSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单编号
	 */
	public void setBillNoSrc(String billNoSrc){
		this.billNoSrc = billNoSrc;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  制单人
	 */

	@Column(name ="BILLERID",nullable=true,length=32)
	public String getBillerId(){
		return this.billerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  制单人
	 */
	public void setBillerId(String billerId){
		this.billerId = billerId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核人
	 */

	@Column(name ="CHECKERID",nullable=true,length=32)
	public String getCheckerId(){
		return this.checkerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核人
	 */
	public void setCheckerId(String checkerId){
		this.checkerId = checkerId;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  审核日期
	 */

	@Column(name ="CHECKDATE",nullable=true,length=20)
	public Date getCheckDate(){
		return this.checkDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  审核日期
	 */
	public void setCheckDate(Date checkDate){
		this.checkDate = checkDate;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  审核状态
	 */

	@Column(name ="CHECKSTATE",nullable=true,length=1)
	public Integer getCheckState(){
		return this.checkState;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  审核状态
	 */
	public void setCheckState(Integer checkState){
		this.checkState = checkState;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  作废标记
	 */

	@Column(name ="CANCELLATION",nullable=true,length=1)
	public Integer getCancellation(){
		return this.cancellation;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  作废标记
	 */
	public void setCancellation(Integer cancellation){
		this.cancellation = cancellation;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  摘要
	 */

	@Column(name ="EXPLANATION",nullable=true,length=255)
	public String getExplanation(){
		return this.explanation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  摘要
	 */
	public void setExplanation(String explanation){
		this.explanation = explanation;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分支机构
	 */

	@Column(name ="SONID",nullable=true,length=32)
	public String getSonId(){
		return this.sonId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分支机构
	 */
	public void setSonId(String sonId){
		this.sonId = sonId;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  版本号
	 */

	@Version
	@Column(name ="VERSION",nullable=true,length=10)
	public Integer getVersion(){
		return this.version;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  版本号
	 */
	public void setVersion(Integer version){
		this.version = version;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  收款金额
	 */

	@Column(name ="CHECKAMOUNT",nullable=true,scale=10,length=20)
	public BigDecimal getCheckAmount(){
		return this.checkAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  收款金额
	 */
	public void setCheckAmount(BigDecimal checkAmount){
		this.checkAmount = checkAmount;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  换货差额
	 */

	@Column(name ="DIFFAMOUNT",nullable=true,scale=10,length=20)
	public BigDecimal getDiffAmount(){
		return this.diffAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  换货差额
	 */
	public void setDiffAmount(BigDecimal diffAmount){
		this.diffAmount = diffAmount;
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
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
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
	public String getClassIDName() {
		return classIDName;
	}

	public void setClassIDName(String classIDName) {
		this.classIDName = classIDName;
	}
}
