package com.qihang.buss.sc.sales.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 采购换货单
 * @author onlineGenerator
 * @date 2016-07-19 17:41:15
 * @version V1.0   
 *
 */
@Entity
@Table(name = "v_sc_ic_jhstockbill", schema = "")
@SuppressWarnings("serial")
public class TScIcJhstockbillViewEntity implements java.io.Serializable {
	/**主键*/
	private String id;

	/**单据类型*/
	private String tranType;
	/**单据编号*/
  @Excel(name="单据编号")
	private String billNo;
	/**单据日期*/
  @Excel(name="单据日期")
	private Date date;
	/**供应商*/
  @Excel(name="供应商")
	private String itemId;
	/**经办人*/
  @Excel(name="经办人")
	private String empId;
	/**部门*/
  @Excel(name="部门")
	private String deptId;
	/**优惠金额*/
  @Excel(name="优惠金额")
	private Double rebateAmount;
	/**换货差额*/
  @Excel(name="换货差额")
	private Double diffAmount;
	/**本次付款*/
  @Excel(name="本次付款")
	private Double curPayAmount;
	/**结算账户*/
  @Excel(name="结算账户")
	private String accountId;
	/**应付金额*/
  @Excel(name="应付金额")
	private Double allAmount;
	/**付款金额*/
  @Excel(name="付款金额")
	private Double checkAmount;
	/**源单类型*/
  @Excel(name="源单类型")
	private String classIDSrc;
	/**源单主键*/
	private String idSrc;
	/**源单编号*/
	private String billNoSrc;
	/**审核人*/
  @Excel(name="审核人")
	private String checkerId;
	/**制单人*/
  @Excel(name="制单人")
	private String billerId;
	/**审核日期*/
  @Excel(name="审核日期")
	private Date checkDate;
	/**审核状态*/
	private Integer checkState;
	/**取消标记*/
	private Integer cancellation;
	/**摘要*/
  @Excel(name="摘要")
	private String explanation;
	/**分支机构*/
  @Excel(name="分支机构")
	private String sonId;

	private String sonName;

	private Integer version;


	//子表数据
	/**主键*/
	private java.lang.String entryId;

	/**商品编号*/
	private java.lang.String itemNo;
	/**商品名称*/
	private String itemName;
	/**规格*/
	private String model;
	/**条码*/
	private String barCode;
	/**仓库*/
	private java.lang.String stockId;
	/**批号*/
	private java.lang.String batchNo;
	/**单位*/
	private java.lang.String unitId;
	/**数量*/
	private java.lang.Double qty;
	/**基本单位*/
	private java.lang.String basicUnitId;
	/**换算率*/
	private java.lang.Double coefficient;
	/**基本数量*/
	private java.lang.Double basicQty;
	/**辅助单位*/
	private java.lang.String secUnitId;
	/**辅助换算率*/
	private java.lang.Double secCoefficient;
	/**辅助数量*/
	private java.lang.Double secQty;
	/**金额*/
	private java.lang.Double taxAmountEx;
	/**单价*/
	private java.lang.Double taxPriceEx;
	/**折扣率（%）*/
	private java.lang.Double discountRate;
	/**折后单价*/
	private java.lang.Double taxPrice;
	/**折后金额*/
	private java.lang.Double inTaxAmount;
	/**税率（%）*/
	private java.lang.Double taxRate;
	/**不含税单价*/
	private java.lang.Double price;
	/**不含税金额*/
	private java.lang.Double amount;
	/**不含税折后单价*/
	private java.lang.Double discountPrice;
	/**不含税折后金额*/
	private java.lang.Double discountAmount;
	/**税额*/
	private java.lang.Double taxAmount;
	/**生产日期*/
	private java.util.Date kfDate;
	/**保质期*/
	private java.lang.Integer kfPeriod;
	/**保质期类型*/
	private java.lang.String kfDateType;
	/**有效期至*/
	private java.util.Date periodDate;
	/**备注*/
	private java.lang.String note;
	/**父id*/
	private java.lang.String fid;
	/**分录号*/
	private java.lang.Integer findex;


	private Integer state;
	private String auditorName;
	private String auditDate;

	private String checkerName;

	private String basicUnitName;//基础单位名称
	private String secUnitName;//辅助单位名称



	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */

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
	 *@return: java.lang.String  单据类型
	 */

	@Column(name ="TRANTYPE",nullable=true,length=32)
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
	 *@return: java.lang.String  供应商
	 */

	@Column(name ="ITEMID",nullable=true,length=32)
	public String getItemId(){
		return this.itemId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  供应商
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
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  优惠金额
	 */

	@Column(name ="REBATEAMOUNT",nullable=true,scale=10,length=20)
	public Double getRebateAmount(){
		return this.rebateAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  优惠金额
	 */
	public void setRebateAmount(Double rebateAmount){
		this.rebateAmount = rebateAmount;
	}

	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  换货差额
	 */

	@Column(name ="DIFFAMOUNT",nullable=true,scale=10,length=20)
	public Double getDiffAmount(){
		return this.diffAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  换货差额
	 */
	public void setDiffAmount(Double diffAmount){
		this.diffAmount = diffAmount;
	}

	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  本次付款
	 */

	@Column(name ="CURPAYAMOUNT",nullable=true,scale=10,length=20)
	public Double getCurPayAmount(){
		return this.curPayAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  本次付款
	 */
	public void setCurPayAmount(Double curPayAmount){
		this.curPayAmount = curPayAmount;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结算账户
	 */

	@Column(name ="ACCOUNTID",nullable=true,length=32)
	public String getAccountId(){
		return this.accountId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结算账户
	 */
	public void setAccountId(String accountId){
		this.accountId = accountId;
	}

	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  应付金额
	 */

	@Column(name ="ALLAMOUNT",nullable=true,scale=10,length=20)
	public Double getAllAmount(){
		return this.allAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  应付金额
	 */
	public void setAllAmount(Double allAmount){
		this.allAmount = allAmount;
	}

	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  付款金额
	 */

	@Column(name ="CHECKAMOUNT",nullable=true,scale=10,length=20)
	public Double getCheckAmount(){
		return this.checkAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  付款金额
	 */
	public void setCheckAmount(Double checkAmount){
		this.checkAmount = checkAmount;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单类型
	 */

	@Column(name ="CLASSID_SRC",nullable=true,length=10)
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
	 *@return: java.lang.Integer  取消标记
	 */

	@Column(name ="CANCELLATION",nullable=true,length=1)
	public Integer getCancellation(){
		return this.cancellation;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  取消标记
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

	@Version
	@Column(name ="version",nullable=true)
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Id
	@Column(name ="entryId",nullable=true)
	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	@Column(name ="itemNo",nullable=true)
	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	@Column(name ="itemName",nullable=true)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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
	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	@Column(name ="basicUnitId",nullable=true)
	public String getBasicUnitId() {
		return basicUnitId;
	}

	public void setBasicUnitId(String basicUnitId) {
		this.basicUnitId = basicUnitId;
	}

	@Column(name ="coefficient",nullable=true)
	public Double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}

	@Column(name ="basicQty",nullable=true)
	public Double getBasicQty() {
		return basicQty;
	}

	public void setBasicQty(Double basicQty) {
		this.basicQty = basicQty;
	}

	@Column(name ="secUnitId",nullable=true)
	public String getSecUnitId() {
		return secUnitId;
	}

	public void setSecUnitId(String secUnitId) {
		this.secUnitId = secUnitId;
	}

	@Column(name ="secCoefficient",nullable=true)
	public Double getSecCoefficient() {
		return secCoefficient;
	}

	public void setSecCoefficient(Double secCoefficient) {
		this.secCoefficient = secCoefficient;
	}

	@Column(name ="secQty",nullable=true)
	public Double getSecQty() {
		return secQty;
	}

	public void setSecQty(Double secQty) {
		this.secQty = secQty;
	}

	@Column(name ="taxAmountEx",nullable=true)
	public Double getTaxAmountEx() {
		return taxAmountEx;
	}

	public void setTaxAmountEx(Double taxAmountEx) {
		this.taxAmountEx = taxAmountEx;
	}

	@Column(name ="taxPriceEx",nullable=true)
	public Double getTaxPriceEx() {
		return taxPriceEx;
	}

	public void setTaxPriceEx(Double taxPriceEx) {
		this.taxPriceEx = taxPriceEx;
	}

	@Column(name ="discountRate",nullable=true)
	public Double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Double discountRate) {
		this.discountRate = discountRate;
	}

	@Column(name ="taxPrice",nullable=true)
	public Double getTaxPrice() {
		return taxPrice;
	}

	public void setTaxPrice(Double taxPrice) {
		this.taxPrice = taxPrice;
	}

	@Column(name ="inTaxAmount",nullable=true)
	public Double getInTaxAmount() {
		return inTaxAmount;
	}

	public void setInTaxAmount(Double inTaxAmount) {
		this.inTaxAmount = inTaxAmount;
	}

	@Column(name ="taxRate",nullable=true)
	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	@Column(name ="price",nullable=true)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name ="amount",nullable=true)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name ="discountPrice",nullable=true)
	public Double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}

	@Column(name ="discountAmount",nullable=true)
	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	@Column(name ="taxAmount",nullable=true)
	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
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
	public Integer getKfPeriod() {
		return kfPeriod;
	}

	public void setKfPeriod(Integer kfPeriod) {
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

	@Transient
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Transient
	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	@Transient
	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	@Column(name ="sonName",nullable=true)
	public String getSonName() {
		return sonName;
	}

	public void setSonName(String sonName) {
		this.sonName = sonName;
	}

	@Column(name ="checkerName",nullable=true)
	public String getCheckerName() {
		return checkerName;
	}

	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}

	@Transient
	public String getBasicUnitName() {
		return basicUnitName;
	}

	public void setBasicUnitName(String basicUnitName) {
		this.basicUnitName = basicUnitName;
	}
	@Transient
	public String getSecUnitName() {
		return secUnitName;
	}

	public void setSecUnitName(String secUnitName) {
		this.secUnitName = secUnitName;
	}
}
