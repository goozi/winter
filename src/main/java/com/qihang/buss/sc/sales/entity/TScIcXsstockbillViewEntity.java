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
 * @Description: 销售换货单
 * @author onlineGenerator
 * @date 2016-08-11 16:43:33
 * @version V1.0   
 *
 */
@Entity
@Table(name = "v_sc_ic_xsstockbill", schema = "")
@SuppressWarnings("serial")
public class TScIcXsstockbillViewEntity implements java.io.Serializable {
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
	private java.lang.String billNo;
	/**单据日期*/
	private java.util.Date date;
	/**客户*/
  @Excel(name="客户")
	private java.lang.String itemId;
	/**经办人*/
  @Excel(name="经办人")
	private java.lang.String empId;
	/**部门*/
  @Excel(name="部门")
	private java.lang.String deptId;
	/**优惠金额*/
  @Excel(name="优惠金额")
	private java.math.BigDecimal rebateAmount;
	/**本次收款*/
  @Excel(name="本次收款")
	private java.math.BigDecimal curPayAmount;
	/**结算账户*/
  @Excel(name="结算账户")
	private java.lang.String accountID;
	/**物流费用*/
  @Excel(name="物流费用")
	private java.math.BigDecimal freight;
	/**重量*/
  @Excel(name="重量")
	private java.math.BigDecimal weight;
	/**应收金额*/
  @Excel(name="应收金额")
	private java.math.BigDecimal allAmount;
	/**源单类型*/
  @Excel(name="源单类型")
	private java.lang.String classIDSrc;
	/**源单主键*/
	private java.lang.String idSrc;
	/**源单编号*/
	private java.lang.String billNoSrc;
	/**制单人*/
  @Excel(name="制单人")
	private java.lang.String billerId;
	/**审核人*/
  @Excel(name="审核人")
	private java.lang.String checkerId;
	/**审核日期*/
  @Excel(name="审核日期")
	private java.util.Date checkDate;
	/**审核状态*/
	private java.lang.Integer checkState;
	/**作废标记*/
	private java.lang.Integer cancellation;
	/**摘要*/
  @Excel(name="摘要")
	private java.lang.String explanation;
	/**分支机构*/
  @Excel(name="分支机构")
	private java.lang.String sonId;
	/**版本号*/
	private java.lang.Integer version;
	/**收款金额*/
	private java.math.BigDecimal checkAmount;
	/**换货差额*/
	private java.math.BigDecimal diffAmount;

	private String entryId;
	/**父id*/
	private java.lang.String fid;
	/**分录号*/
	private java.lang.Integer findex;
	/**商品*/
	@Excel(name="商品")
	private java.lang.String entryItemId;
	/**仓库*/
	@Excel(name="仓库")
	private java.lang.String stockId;
	/**批号*/
	@Excel(name="批号")
	private java.lang.String batchNo;
	/**单位*/
	@Excel(name="单位")
	private java.lang.String unitId;
	/**数量*/
	@Excel(name="数量")
	private java.math.BigDecimal qty;
	/**辅助单位*/
	private java.lang.String secUnitId;
	/**基础单位*/
	private java.lang.String basicUnitId;
	/**换算率*/
	private java.math.BigDecimal coefficient;
	/**基本数量*/
	private java.math.BigDecimal basicQty;
	/**辅助换算率*/
	private java.math.BigDecimal secCoefficient;
	/**辅助数量*/
	private java.math.BigDecimal secQty;
	/**单价*/
	@Excel(name="单价")
	private java.math.BigDecimal taxPriceEx;
	/**金额*/
	@Excel(name="金额")
	private java.math.BigDecimal taxAmountEx;
	/**折扣率（%）*/
	@Excel(name="折扣率（%）")
	private java.math.BigDecimal discountRate;
	/**折后单价*/
	@Excel(name="折后单价")
	private java.math.BigDecimal taxPrice;
	/**折后金额*/
	@Excel(name="折后金额")
	private java.math.BigDecimal inTaxAmount;
	/**税率（%）*/
	@Excel(name="税率（%）")
	private java.math.BigDecimal taxRate;
	/**不含税单价*/
	private java.math.BigDecimal price;
	/**不含税金额*/
	private java.math.BigDecimal amount;
	/**不含税折后单价*/
	private java.math.BigDecimal discountPrice;
	/**不含税折后金额*/
	private java.math.BigDecimal discountAmount;
	/**税额*/
	@Excel(name="税额")
	private java.math.BigDecimal taxAmount;
	/**生产日期*/
	@Excel(name="生产日期")
	private java.util.Date kfDate;
	/**保质期*/
	@Excel(name="保质期")
	private java.math.BigDecimal kfPeriod;
	/**保质期类型*/
	@Excel(name="保质期类型")
	private java.lang.String kfDateType;
	/**有效期至*/
	@Excel(name="有效期至")
	private java.util.Date periodDate;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String note;
	/**成本单价*/
	private java.math.BigDecimal costPrice;
	/**成本金额*/
	private java.math.BigDecimal costAmount;

	private String itemName;
	private String empName;
	private String deptName;
	private String accountName;

	private String entryItemNo;
	private String entryItemName;
	private String model;
	private String barCode;
	private String stockName;
	private String unitName;
	private String sonName;
	private String billerName;

	private String checkerName;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */

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
	
	@Column(name ="TRANTYPE",nullable=true,length=11)
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
	 *@return: java.lang.String  客户
	 */
	
	@Column(name ="ITEMID",nullable=true,length=32)
	public java.lang.String getItemId(){
		return this.itemId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户
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
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  优惠金额
	 */
	
	@Column(name ="REBATEAMOUNT",nullable=true,scale=10,length=20)
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
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本次收款
	 */
	
	@Column(name ="CURPAYAMOUNT",nullable=true,scale=10,length=20)
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结算账户
	 */
	
	@Column(name ="ACCOUNTID",nullable=true,length=32)
	public java.lang.String getAccountID(){
		return this.accountID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结算账户
	 */
	public void setAccountID(java.lang.String accountID){
		this.accountID = accountID;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  物流费用
	 */
	
	@Column(name ="FREIGHT",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getFreight(){
		return this.freight;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  物流费用
	 */
	public void setFreight(java.math.BigDecimal freight){
		this.freight = freight;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  重量
	 */
	
	@Column(name ="WEIGHT",nullable=true,scale=10,length=20)
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
	
	@Column(name ="ALLAMOUNT",nullable=true,scale=10,length=20)
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单类型
	 */
	
	@Column(name ="CLASSID_SRC",nullable=true,length=32)
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
	 *@return: java.lang.Integer  作废标记
	 */
	
	@Column(name ="CANCELLATION",nullable=true,length=1)
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
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  版本号
	 */
	
	@Column(name ="VERSION",nullable=true,length=10)
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
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  收款金额
	 */
	
	@Column(name ="CHECKAMOUNT",nullable=true,scale=10,length=20)
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
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  换货差额
	 */
	
	@Column(name ="DIFFAMOUNT",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getDiffAmount(){
		return this.diffAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  换货差额
	 */
	public void setDiffAmount(java.math.BigDecimal diffAmount){
		this.diffAmount = diffAmount;
	}


	@Id
	@Column(name ="entryId",nullable=true)
	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	@Column(name ="fid",nullable=true)
	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	@Column(name ="findex",nullable=true)
	public Integer getFindex() {
		return findex;
	}

	public void setFindex(Integer findex) {
		this.findex = findex;
	}

	@Column(name ="entryItemId",nullable=true)
	public String getEntryItemId() {
		return entryItemId;
	}

	public void setEntryItemId(String entryItemId) {
		this.entryItemId = entryItemId;
	}

	@Column(name ="stockId",nullable=true)
	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	@Column(name ="batchNo",nullable=true)
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name ="unitId",nullable=true)
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	@Column(name ="qty",nullable=true)
	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	@Column(name ="secUnitId",nullable=true)
	public String getSecUnitId() {
		return secUnitId;
	}

	public void setSecUnitId(String secUnitId) {
		this.secUnitId = secUnitId;
	}

	@Column(name ="basicUnitId",nullable=true)
	public String getBasicUnitId() {
		return basicUnitId;
	}

	public void setBasicUnitId(String basicUnitId) {
		this.basicUnitId = basicUnitId;
	}

	@Column(name ="coefficient",nullable=true)
	public BigDecimal getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(BigDecimal coefficient) {
		this.coefficient = coefficient;
	}

	@Column(name ="basicQty",nullable=true)
	public BigDecimal getBasicQty() {
		return basicQty;
	}

	public void setBasicQty(BigDecimal basicQty) {
		this.basicQty = basicQty;
	}

	@Column(name ="secCoefficient",nullable=true)
	public BigDecimal getSecCoefficient() {
		return secCoefficient;
	}

	public void setSecCoefficient(BigDecimal secCoefficient) {
		this.secCoefficient = secCoefficient;
	}

	@Column(name ="secQty",nullable=true)
	public BigDecimal getSecQty() {
		return secQty;
	}

	public void setSecQty(BigDecimal secQty) {
		this.secQty = secQty;
	}

	@Column(name ="taxPriceEx",nullable=true)
	public BigDecimal getTaxPriceEx() {
		return taxPriceEx;
	}

	public void setTaxPriceEx(BigDecimal taxPriceEx) {
		this.taxPriceEx = taxPriceEx;
	}

	@Column(name ="taxAmountEx",nullable=true)
	public BigDecimal getTaxAmountEx() {
		return taxAmountEx;
	}

	public void setTaxAmountEx(BigDecimal taxAmountEx) {
		this.taxAmountEx = taxAmountEx;
	}

	@Column(name ="discountRate",nullable=true)
	public BigDecimal getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}

	@Column(name ="taxPrice",nullable=true)
	public BigDecimal getTaxPrice() {
		return taxPrice;
	}

	public void setTaxPrice(BigDecimal taxPrice) {
		this.taxPrice = taxPrice;
	}

	@Column(name ="inTaxAmount",nullable=true)
	public BigDecimal getInTaxAmount() {
		return inTaxAmount;
	}

	public void setInTaxAmount(BigDecimal inTaxAmount) {
		this.inTaxAmount = inTaxAmount;
	}

	@Column(name ="taxRate",nullable=true)
	public BigDecimal getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	@Column(name ="price",nullable=true)
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Column(name ="amount",nullable=true)
	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Column(name ="discountPrice",nullable=true)
	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}

	@Column(name ="discountAmount",nullable=true)
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	@Column(name ="taxAmount",nullable=true)
	public BigDecimal getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}

	@Column(name ="kfDate",nullable=true)
	public Date getKfDate() {
		return kfDate;
	}

	public void setKfDate(Date kfDate) {
		this.kfDate = kfDate;
	}

	@Column(name ="kfPeriod",nullable=true)
	public BigDecimal getKfPeriod() {
		return kfPeriod;
	}

	public void setKfPeriod(BigDecimal kfPeriod) {
		this.kfPeriod = kfPeriod;
	}

	@Column(name ="kfDateType",nullable=true)
	public String getKfDateType() {
		return kfDateType;
	}

	public void setKfDateType(String kfDateType) {
		this.kfDateType = kfDateType;
	}

	@Column(name ="periodDate",nullable=true)
	public Date getPeriodDate() {
		return periodDate;
	}

	public void setPeriodDate(Date periodDate) {
		this.periodDate = periodDate;
	}

	@Column(name ="note",nullable=true)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name ="costPrice",nullable=true)
	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	@Column(name ="costAmount",nullable=true)
	public BigDecimal getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}

	@Column(name ="itemName",nullable=true)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name ="empName",nullable=true)
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Column(name ="deptName",nullable=true)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name ="accountName",nullable=true)
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Column(name ="entryItemNo",nullable=true)
	public String getEntryItemNo() {
		return entryItemNo;
	}

	public void setEntryItemNo(String entryItemNo) {
		this.entryItemNo = entryItemNo;
	}

	@Column(name ="entryItemName",nullable=true)
	public String getEntryItemName() {
		return entryItemName;
	}

	public void setEntryItemName(String entryItemName) {
		this.entryItemName = entryItemName;
	}

	@Column(name ="model",nullable=true)
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name ="barCode",nullable=true)
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	@Column(name ="stockName",nullable=true)
	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	@Column(name ="unitName",nullable=true)
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	@Column(name ="sonName",nullable=true)
	public String getSonName() {
		return sonName;
	}

	public void setSonName(String sonName) {
		this.sonName = sonName;
	}

	@Column(name ="billerName",nullable=true)
	public String getBillerName() {
		return billerName;
	}

	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}

	@Column(name ="checkerName",nullable=true)
	public String getCheckerName() {
		return checkerName;
	}

	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}
}
