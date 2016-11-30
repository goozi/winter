package com.qihang.buss.sc.sys.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**   
 * @Title: Entity
 * @Description: t_s_config
 * @author onlineGenerator
 * @date 2016-06-15 11:58:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_s_config", schema = "")
@SuppressWarnings("serial")
public class TSConfigEntity  implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**code*/
	@Excel(name="code")
	private java.lang.String code;
	/**content*/
	@Excel(name="content")
	private java.lang.String content;
	/**name*/
	@Excel(name="name")
	private java.lang.String name;
	/**note*/
	@Excel(name="note")
	private java.lang.String note;
	/**userid*/
	@Excel(name="userid")
	private java.lang.String userid;
	/**accountid*/
	@Excel(name="accountid")
	private java.lang.String accountid;

	private java.lang.String pageRecord;
	private java.lang.String rowHeight;
	private java.lang.String defaultRate;
	private java.lang.String number;
	private java.lang.String unitPrice;
	private java.lang.String money;
	private java.lang.String rates;
	private java.lang.String discountRate;
	private java.lang.String other;
	private java.lang.String controlMethod;
	private java.lang.String controlTimePoint;
	private java.lang.String recearwarDays;
	private java.lang.String pruordearwarDays;
	private java.lang.String payearwarDays;
	private java.lang.String salordearwarDays;
	private java.lang.String shelflifeearwarDays;
	private java.lang.String syslogholdDays;
	private java.lang.String accountId;

	/**允许负库存结账*/
	private java.lang.String minusInventoryAccount;
	/**允许负库存出库*/
	private java.lang.String minusInventorySl;
	/**允许盘点有未审核存货单据的数据*/
	private java.lang.String stocktakingNotAuditedStockBill;
	/**单据保存时系统自动审核*/
	private java.lang.String billSaveSystemWithExamine;
	/**单据审核时系统自带后续业务单据*/
	private java.lang.String billExamineSystemWithFollow;
	/**不允许手工开入库单*/
	private java.lang.String cannotManualOpenInRepertory;
	/**不允许入库单数量大于采购订单数量*/
	private java.lang.String cannotInRepertoryngtPurchasen;
	/**采购模块启用价格调用顺序*/
	private java.lang.String purchaseStartPriceCallOrder;
	/**采购设置下拉框一*/
	private java.lang.String purchaseselectOne;
	/**采购设置下拉框二*/
	private java.lang.String purchaseselectTwo;
	/**不允许手工开出库单*/
	private java.lang.String cannotManualOpenOutRepertory;
	/**不允许出库单数量大于销售订单数量*/
	private java.lang.String cannotOutRepertoryngtSale;
	/**销售模块启用价格调用顺序*/
	private java.lang.String saleStartPriceCallOrder;
	/**销售设置下拉框一*/
	private java.lang.String saleSelectOne;
	/**销售设置下拉框二*/
	private java.lang.String saleSelectTwo;
	/**销售设置下拉框三*/
	private java.lang.String saleSelectThree;
	@Transient
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	@Transient
	public String getMinusInventoryAccount() {
		return minusInventoryAccount;
	}
	public void setMinusInventoryAccount(String minusInventoryAccount) {
		this.minusInventoryAccount = minusInventoryAccount;
	}

	@Transient
	public String getMinusInventorySl() {
		return minusInventorySl;
	}
	public void setMinusInventorySl(String minusInventorySl) {
		this.minusInventorySl = minusInventorySl;
	}

	@Transient
	public String getStocktakingNotAuditedStockBill() {
		return stocktakingNotAuditedStockBill;
	}
	public void setStocktakingNotAuditedStockBill(String stocktakingNotAuditedStockBill) {
		this.stocktakingNotAuditedStockBill = stocktakingNotAuditedStockBill;
	}

	@Transient
	public String getBillSaveSystemWithExamine() {
		return billSaveSystemWithExamine;
	}
	public void setBillSaveSystemWithExamine(String billSaveSystemWithExamine) {
		this.billSaveSystemWithExamine = billSaveSystemWithExamine;
	}

	@Transient
	public String getBillExamineSystemWithFollow() {
		return billExamineSystemWithFollow;
	}
	public void setBillExamineSystemWithFollow(String billExamineSystemWithFollow) {
		this.billExamineSystemWithFollow = billExamineSystemWithFollow;
	}

	@Transient
	public String getCannotManualOpenInRepertory() {
		return cannotManualOpenInRepertory;
	}
	public void setCannotManualOpenInRepertory(String cannotManualOpenInRepertory) {
		this.cannotManualOpenInRepertory = cannotManualOpenInRepertory;
	}

	@Transient
	public String getCannotInRepertoryngtPurchasen() {
		return cannotInRepertoryngtPurchasen;
	}
	public void setCannotInRepertoryngtPurchasen(String cannotInRepertoryngtPurchasen) {
		this.cannotInRepertoryngtPurchasen = cannotInRepertoryngtPurchasen;
	}

	@Transient
	public String getPurchaseStartPriceCallOrder() {
		return purchaseStartPriceCallOrder;
	}
	public void setPurchaseStartPriceCallOrder(String purchaseStartPriceCallOrder) {
		this.purchaseStartPriceCallOrder = purchaseStartPriceCallOrder;
	}

	@Transient
	public String getPurchaseselectOne() {
		return purchaseselectOne;
	}
	public void setPurchaseselectOne(String purchaseselectOne) {
		this.purchaseselectOne = purchaseselectOne;
	}

	@Transient
	public String getPurchaseselectTwo() {
		return purchaseselectTwo;
	}
	public void setPurchaseselectTwo(String purchaseselectTwo) {
		this.purchaseselectTwo = purchaseselectTwo;
	}

	@Transient
	public String getCannotManualOpenOutRepertory() {
		return cannotManualOpenOutRepertory;
	}
	public void setCannotManualOpenOutRepertory(String cannotManualOpenOutRepertory) {
		this.cannotManualOpenOutRepertory = cannotManualOpenOutRepertory;
	}

	@Transient
	public String getCannotOutRepertoryngtSale() {
		return cannotOutRepertoryngtSale;
	}
	public void setCannotOutRepertoryngtSale(String cannotOutRepertoryngtSale) {
		this.cannotOutRepertoryngtSale = cannotOutRepertoryngtSale;
	}

	@Transient
	public String getSaleStartPriceCallOrder() {
		return saleStartPriceCallOrder;
	}
	public void setSaleStartPriceCallOrder(String saleStartPriceCallOrder) {
		this.saleStartPriceCallOrder = saleStartPriceCallOrder;
	}

	@Transient
	public String getSaleSelectOne() {
		return saleSelectOne;
	}
	public void setSaleSelectOne(String saleSelectOne) {
		this.saleSelectOne = saleSelectOne;
	}

	@Transient
	public String getSaleSelectTwo() {
		return saleSelectTwo;
	}
	public void setSaleSelectTwo(String saleSelectTwo) {
		this.saleSelectTwo = saleSelectTwo;
	}

	@Transient
	public String getSaleSelectThree() {
		return saleSelectThree;
	}
	public void setSaleSelectThree(String saleSelectThree) {
		this.saleSelectThree = saleSelectThree;
	}

	@Transient
	public java.lang.String getPageRecord() {
		return pageRecord;
	}
	public void setPageRecord(java.lang.String pageRecord) {
		this.pageRecord = pageRecord;
	}

	@Transient
	public String getRowHeight() {
		return rowHeight;
	}
	public void setRowHeight(String rowHeight) {
		this.rowHeight = rowHeight;
	}
	@Transient
	public String getDefaultRate() {
		return defaultRate;
	}
	public void setDefaultRate(String defaultRate) {
		this.defaultRate = defaultRate;
	}
	@Transient
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	@Transient
	public String getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(String unitPrice) {
		this.unitPrice = unitPrice;
	}
	@Transient
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	@Transient
	public String getRates() {
		return rates;
	}
	public void setRates(String rates) {
		this.rates = rates;
	}
	@Transient
	public String getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(String discountRate) {
		this.discountRate = discountRate;
	}
	@Transient
	public String getOther() {
		return other;
	}
	public void setOther(String other) {
		this.other = other;
	}
	@Transient
	public String getControlMethod() {
		return controlMethod;
	}
	public void setControlMethod(String controlMethod) {
		this.controlMethod = controlMethod;
	}
	@Transient
	public String getControlTimePoint() {
		return controlTimePoint;
	}
	public void setControlTimePoint(String controlTimePoint) {
		this.controlTimePoint = controlTimePoint;
	}
	@Transient
	public String getRecearwarDays() {
		return recearwarDays;
	}
	public void setRecearwarDays(String recearwarDays) {
		this.recearwarDays = recearwarDays;
	}
	@Transient
	public String getPruordearwarDays() {
		return pruordearwarDays;
	}
	public void setPruordearwarDays(String pruordearwarDays) {
		this.pruordearwarDays = pruordearwarDays;
	}
	@Transient
	public String getPayearwarDays() {
		return payearwarDays;
	}
	public void setPayearwarDays(String payearwarDays) {
		this.payearwarDays = payearwarDays;
	}
	@Transient
	public String getSalordearwarDays() {
		return salordearwarDays;
	}
	public void setSalordearwarDays(String salordearwarDays) {
		this.salordearwarDays = salordearwarDays;
	}
	@Transient
	public String getShelflifeearwarDays() {
		return shelflifeearwarDays;
	}
	public void setShelflifeearwarDays(String shelflifeearwarDays) {
		this.shelflifeearwarDays = shelflifeearwarDays;
	}
	@Transient
	public String getSyslogholdDays() {
		return syslogholdDays;
	}
	public void setSyslogholdDays(String syslogholdDays) {
		this.syslogholdDays = syslogholdDays;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=32)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  code
	 */
	@Column(name ="CODE",nullable=true,length=100)
	public java.lang.String getCode(){
		return this.code;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  code
	 */
	public void setCode(java.lang.String code){
		this.code = code;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  content
	 */
	@Column(name ="CONTENT",nullable=true)
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  content
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  name
	 */
	@Column(name ="NAME",nullable=false,length=100)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  name
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  note
	 */
	@Column(name ="NOTE",nullable=true)
	public java.lang.String getNote(){
		return this.note;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  note
	 */
	public void setNote(java.lang.String note){
		this.note = note;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  userid
	 */
	@Column(name ="USERID",nullable=true,length=32)
	public java.lang.String getUserid(){
		return this.userid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  userid
	 */
	public void setUserid(java.lang.String userid){
		this.userid = userid;
	}



	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  accountid
	 */
	@Column(name ="ACCOUNTID",nullable=true,length=32)
	public java.lang.String getAccountid(){
		return this.accountid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  accountid
	 */
	public void setAccountid(java.lang.String accountid){
		this.accountid = accountid;
	}

}
