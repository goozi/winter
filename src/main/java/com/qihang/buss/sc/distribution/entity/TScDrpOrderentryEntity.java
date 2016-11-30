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

import org.hibernate.annotations.GenericGenerator;
import com.qihang.winter.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 订单管理
 * @author onlineGenerator
 * @date 2016-08-02 09:58:17
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_DRP_OrderEntry", schema = "")
@SuppressWarnings("serial")
public class TScDrpOrderentryEntity implements java.io.Serializable {
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
	/**主表主键*/
	private java.lang.String interID;
	/**分录号*/
	private java.lang.Integer indexNumber;
	/**商品*/
	@Excel(name="商品")
	private java.lang.String itemID;
	/**仓库*/
	@Excel(name="仓库")
	private java.lang.String stockID;
	/**单位*/
	@Excel(name="单位")
	private java.lang.String unitID;
	/**数量*/
	@Excel(name="数量")
	private java.math.BigDecimal qty;
	/**基本单位*/
	@Excel(name="基本单位")
	private java.lang.String basicUnitID;
	/**换算率*/
	@Excel(name="换算率")
	private java.math.BigDecimal coefficient;
	/**基本数量*/
	@Excel(name="基本数量")
	private java.math.BigDecimal basicQty;
	/**辅助单位*/
	@Excel(name="辅助单位")
	private java.lang.String secUnitID;
	/**辅助换算率*/
	@Excel(name="辅助换算率")
	private java.math.BigDecimal secCoefficient;
	/**辅助数量*/
	@Excel(name="辅助数量")
	private java.math.BigDecimal secQty;
	/**单价*/
	@Excel(name="单价")
	private java.math.BigDecimal taxPriceEx;
	/**金额*/
	@Excel(name="金额")
	private java.math.BigDecimal taxAmountEx;
	/**折扣率(%)*/
	@Excel(name="折扣率(%)")
	private java.math.BigDecimal discountRate;
	/**折后单价*/
	@Excel(name="折后单价")
	private java.math.BigDecimal taxPrice;
	/**折后金额*/
	@Excel(name="折后金额")
	private java.math.BigDecimal inTaxAmount;
	/**税率(%)*/
	@Excel(name="税率(%)")
	private java.math.BigDecimal taxRate;
	/**不含税单价*/
	@Excel(name="不含税单价")
	private java.math.BigDecimal price;
	/**不含税金额*/
	@Excel(name="不含税金额")
	private java.math.BigDecimal amount;
	/**不含税折后单价*/
	@Excel(name="不含税折后单价")
	private java.math.BigDecimal discountPrice;
	/**不含税折后金额*/
	@Excel(name="不含税折后金额")
	private java.math.BigDecimal discountAmount;
	/**税额*/
	@Excel(name="税额")
	private java.math.BigDecimal taxAmount;
	/**收货确认数量*/
	@Excel(name="收货确认数量")
	private java.math.BigDecimal affirmQty;
	/**收货日期*/
	@Excel(name="收货日期")
	private java.util.Date jhDate;
	/**发货数量*/
	@Excel(name="发货数量")
	private java.math.BigDecimal outStockQty;
	/**发货日期*/
	@Excel(name="发货日期")
	private java.util.Date outDate;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String note;

	/**收货确认时间*/
	@Excel(name="确认收货时间")
	private java.util.Date affirmDate;
	private java.lang.String goodsNo;
	private java.lang.String goodsName;
	private java.lang.String model;
	private java.lang.String barCode;
	private java.lang.String stockName;
	private java.lang.String basicUnitName;
	/**商品的重量*/
	private java.math.BigDecimal itemWeight;
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
	 *@return: java.lang.String  主表主键
	 */
	@Column(name ="INTERID",nullable=true,length=32)
	public java.lang.String getInterID(){
		return this.interID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主表主键
	 */
	public void setInterID(java.lang.String interID){
		this.interID = interID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分录号
	 */
	@Column(name ="INDEXNUMBER",nullable=true,length=32)
	public java.lang.Integer getIndexNumber(){
		return this.indexNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分录号
	 */
	public void setIndexNumber(java.lang.Integer indexNumber){
		this.indexNumber = indexNumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品
	 */
	@Column(name ="ITEMID",nullable=true,length=32)
	public java.lang.String getItemID(){
		return this.itemID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品
	 */
	public void setItemID(java.lang.String itemID){
		this.itemID = itemID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库
	 */
	@Column(name ="STOCKID",nullable=true,length=32)
	public java.lang.String getStockID(){
		return this.stockID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库
	 */
	public void setStockID(java.lang.String stockID){
		this.stockID = stockID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */
	@Column(name ="UNITID",nullable=true,length=50)
	public java.lang.String getUnitID(){
		return this.unitID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setUnitID(java.lang.String unitID){
		this.unitID = unitID;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  数量
	 */
	@Column(name ="QTY",nullable=true,length=32)
	public java.math.BigDecimal getQty(){
		return this.qty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  数量
	 */
	public void setQty(java.math.BigDecimal qty){
		this.qty = qty;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  基本单位
	 */
	@Column(name ="BASICUNITID",nullable=true,length=50)
	public java.lang.String getBasicUnitID(){
		return this.basicUnitID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  基本单位
	 */
	public void setBasicUnitID(java.lang.String basicUnitID){
		this.basicUnitID = basicUnitID;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  换算率
	 */
	@Column(name ="COEFFICIENT",nullable=true,length=32)
	public java.math.BigDecimal getCoefficient(){
		return this.coefficient;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  换算率
	 */
	public void setCoefficient(java.math.BigDecimal coefficient){
		this.coefficient = coefficient;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  基本数量
	 */
	@Column(name ="BASICQTY",nullable=true,length=32)
	public java.math.BigDecimal getBasicQty(){
		return this.basicQty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  基本数量
	 */
	public void setBasicQty(java.math.BigDecimal basicQty){
		this.basicQty = basicQty;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  辅助单位
	 */
	@Column(name ="SECUNITID",nullable=true,length=32)
	public java.lang.String getSecUnitID(){
		return this.secUnitID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  辅助单位
	 */
	public void setSecUnitID(java.lang.String secUnitID){
		this.secUnitID = secUnitID;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  辅助换算率
	 */
	@Column(name ="SECCOEFFICIENT",nullable=true,length=32)
	public java.math.BigDecimal getSecCoefficient(){
		return this.secCoefficient;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  辅助换算率
	 */
	public void setSecCoefficient(java.math.BigDecimal secCoefficient){
		this.secCoefficient = secCoefficient;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  辅助数量
	 */
	@Column(name ="SECQTY",nullable=true,length=32)
	public java.math.BigDecimal getSecQty(){
		return this.secQty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  辅助数量
	 */
	public void setSecQty(java.math.BigDecimal secQty){
		this.secQty = secQty;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  单价
	 */
	@Column(name ="TAXPRICEEX",nullable=true,length=32)
	public java.math.BigDecimal getTaxPriceEx(){
		return this.taxPriceEx;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  单价
	 */
	public void setTaxPriceEx(java.math.BigDecimal taxPriceEx){
		this.taxPriceEx = taxPriceEx;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  金额
	 */
	@Column(name ="TAXAMOUNTEX",nullable=true,length=32)
	public java.math.BigDecimal getTaxAmountEx(){
		return this.taxAmountEx;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  金额
	 */
	public void setTaxAmountEx(java.math.BigDecimal taxAmountEx){
		this.taxAmountEx = taxAmountEx;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  折扣率(%)
	 */
	@Column(name ="DISCOUNTRATE",nullable=true,length=32)
	public java.math.BigDecimal getDiscountRate(){
		return this.discountRate;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  折扣率(%)
	 */
	public void setDiscountRate(java.math.BigDecimal discountRate){
		this.discountRate = discountRate;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  折后单价
	 */
	@Column(name ="TAXPRICE",nullable=true,length=32)
	public java.math.BigDecimal getTaxPrice(){
		return this.taxPrice;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  折后单价
	 */
	public void setTaxPrice(java.math.BigDecimal taxPrice){
		this.taxPrice = taxPrice;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  折后金额
	 */
	@Column(name ="INTAXAMOUNT",nullable=true,length=32)
	public java.math.BigDecimal getInTaxAmount(){
		return this.inTaxAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  折后金额
	 */
	public void setInTaxAmount(java.math.BigDecimal inTaxAmount){
		this.inTaxAmount = inTaxAmount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  税率(%)
	 */
	@Column(name ="TAXRATE",nullable=true,length=32)
	public java.math.BigDecimal getTaxRate(){
		return this.taxRate;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  税率(%)
	 */
	public void setTaxRate(java.math.BigDecimal taxRate){
		this.taxRate = taxRate;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  不含税单价
	 */
	@Column(name ="PRICE",nullable=true,length=32)
	public java.math.BigDecimal getPrice(){
		return this.price;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  不含税单价
	 */
	public void setPrice(java.math.BigDecimal price){
		this.price = price;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  不含税金额
	 */
	@Column(name ="AMOUNT",nullable=true,length=32)
	public java.math.BigDecimal getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  不含税金额
	 */
	public void setAmount(java.math.BigDecimal amount){
		this.amount = amount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  不含税折后单价
	 */
	@Column(name ="DISCOUNTPRICE",nullable=true,length=32)
	public java.math.BigDecimal getDiscountPrice(){
		return this.discountPrice;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  不含税折后单价
	 */
	public void setDiscountPrice(java.math.BigDecimal discountPrice){
		this.discountPrice = discountPrice;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  不含税折后金额
	 */
	@Column(name ="DISCOUNTAMOUNT",nullable=true,length=32)
	public java.math.BigDecimal getDiscountAmount(){
		return this.discountAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  不含税折后金额
	 */
	public void setDiscountAmount(java.math.BigDecimal discountAmount){
		this.discountAmount = discountAmount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  税额
	 */
	@Column(name ="TAXAMOUNT",nullable=true,length=32)
	public java.math.BigDecimal getTaxAmount(){
		return this.taxAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  税额
	 */
	public void setTaxAmount(java.math.BigDecimal taxAmount){
		this.taxAmount = taxAmount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  收货确认数量
	 */
	@Column(name ="AFFIRMQTY",nullable=true,length=32)
	public java.math.BigDecimal getAffirmQty(){
		return this.affirmQty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  收货确认数量
	 */
	public void setAffirmQty(java.math.BigDecimal affirmQty){
		this.affirmQty = affirmQty;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  收货日期
	 */
	@Column(name ="JHDATE",nullable=true,length=32)
	public java.util.Date getJhDate(){
		return this.jhDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  收货日期
	 */
	public void setJhDate(java.util.Date jhDate){
		this.jhDate = jhDate;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  发货数量
	 */
	@Column(name ="OUTSTOCKQTY",nullable=true,length=32)
	public java.math.BigDecimal getOutStockQty(){
		return this.outStockQty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  发货数量
	 */
	public void setOutStockQty(java.math.BigDecimal outStockQty){
		this.outStockQty = outStockQty;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  发货日期
	 */
	@Column(name ="OUTDATE",nullable=true,length=32)
	public java.util.Date getOutDate(){
		return this.outDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  发货日期
	 */
	public void setOutDate(java.util.Date outDate){
		this.outDate = outDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="NOTE",nullable=true,length=32)
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  收货确认时间
	 */
	@Column(name ="AFFIRMDATE",nullable=true,length=32)
	public java.util.Date getAffirmDate(){
		return this.affirmDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  收货确认时间
	 */
	public void setAffirmDate(java.util.Date affirmDate){
		this.affirmDate = affirmDate;
	}
	@Transient
	public String getGoodsNo() {
		return goodsNo;
	}

	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}
	@Transient
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
@Transient
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
@Transient
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
@Transient
	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
@Transient
	public String getBasicUnitName() {
		return basicUnitName;
	}

	public void setBasicUnitName(String basicUnitName) {
		this.basicUnitName = basicUnitName;
	}
	@Column(name ="Deleted")
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
	public BigDecimal getItemWeight() {
		return itemWeight;
	}
	public void setItemWeight(BigDecimal itemWeight) {
		this.itemWeight = itemWeight;
	}
}
