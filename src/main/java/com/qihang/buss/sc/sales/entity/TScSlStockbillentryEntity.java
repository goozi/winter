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
 * @Description: 销售出库单明细
 * @author onlineGenerator
 * @date 2016-08-01 15:25:28
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_sc_sl_stockbillentry", schema = "")
@SuppressWarnings("serial")
public class TScSlStockbillentryEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新日期*/
	private java.util.Date updateDate;
	/**父id*/
	private java.lang.String fid;
	/**商品*/
	@Excel(name="商品")
	private java.lang.String itemid;
	/**仓库*/
	@Excel(name="仓库")
	private java.lang.String stockid;
	/**批号*/
	@Excel(name="批号")
	private java.lang.String batchno;
	/**单位*/
	@Excel(name="单位")
	private java.lang.String unitid;
	/**数量*/
	@Excel(name="数量")
	private java.lang.Double qty;
	/**基本单位*/
	private java.lang.String basicunitid;
	/**换算率*/
	private java.lang.Double coefficient;
	/**基本数量*/
	private java.lang.Double basicQty;
	/**辅助单位*/
	private java.lang.String secunitid;
	/**辅助换算率*/
	private java.lang.Double secCoefficient;
	/**辅助数量*/
	private java.lang.Double secQty;
	/**单价*/
	@Excel(name="单价")
	private java.lang.Double taxPriceEx;
	/**金额*/
	@Excel(name="金额")
	private java.lang.Double taxAmountEx;
	/**折扣率（%）*/
	@Excel(name="折扣率（%）")
	private java.lang.Double discountRate;
	/**折后单价*/
	@Excel(name="折后单价")
	private java.lang.Double taxPrice;
	/**折后金额*/
	@Excel(name="折后金额")
	private java.lang.Double inTaxAmount;
	/**税率（%）*/
	@Excel(name="税率（%）")
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
	@Excel(name="税额")
	private java.lang.Double taxAmount;
	/**生产日期*/
	@Excel(name="生产日期")
	private java.util.Date kfdate;
	/**保质期*/
	@Excel(name="保质期")
	private java.lang.Integer kfperiod;
	/**保质期类型*/
	@Excel(name="保质期类型")
	private java.lang.String kfdatetype;
	/**有效期至*/
	@Excel(name="有效期至")
	private java.util.Date perioddate;
	/**成本单价*/
	private java.lang.Double costPrice;
	/**成本金额*/
	private java.lang.Double costAmount;
	/**赠品标记*/
	@Excel(name="赠品标记")
	private java.lang.Integer freegifts;
	/**退货数量*/
	@Excel(name="退货数量")
	private java.lang.Double commitqty;
	/**源单类型*/
	@Excel(name="源单类型")
	private java.lang.String classidSrc;
	/**源单主键*/
	private java.lang.String idSrc;
	/**源单编号*/
	@Excel(name="源单编号")
	private java.lang.String billnoSrc;
	/**源单分录主键*/
	private java.lang.String entryidSrc;
	/**订单主键*/
	private java.lang.String idOrder;
	/**订单编号*/
	@Excel(name="订单编号")
	private java.lang.String billnoOrder;
	/**订单分录主键*/
	private java.lang.String entryidOrder;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String note;
	/**分录号*/
	private java.lang.Integer findex;
	/**重量*/
	@Excel(name="重量")
	private java.lang.Double weight;
	/**促销类型*/
	@Excel(name="促销类型")
	private java.lang.String salesid;

	private Integer freegifts_;
	private String stockName;
	private String itemNo;
	private String itemName;
	private String model;
	private String isKfperiod;
	private String batchManager;
	private String barCode;
	private Double xsLimitPrice;
	private Double billQty;
	private Double weight_;
	private Double itemweight;//商品重量
	private Double invQty;//库存数量
	private Double stockQty;//订单执行数量
	private String classidName;

	private Integer isFreeGift;//赠品标记
	private String salesName;//促销名称
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
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
	 *@param: java.lang.String  id
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
	 *@return: java.lang.String  父id
	 */
	@Column(name ="FID",nullable=true,length=32)
	public java.lang.String getFid(){
		return this.fid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  父id
	 */
	public void setFid(java.lang.String fid){
		this.fid = fid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品
	 */
	@Column(name ="ITEMID",nullable=true,length=32)
	public java.lang.String getItemid(){
		return this.itemid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品
	 */
	public void setItemid(java.lang.String itemid){
		this.itemid = itemid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库
	 */
	@Column(name ="STOCKID",nullable=true,length=32)
	public java.lang.String getStockid(){
		return this.stockid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库
	 */
	public void setStockid(java.lang.String stockid){
		this.stockid = stockid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  批号
	 */
	@Column(name ="BATCHNO",nullable=true,length=50)
	public java.lang.String getBatchno(){
		return this.batchno;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  批号
	 */
	public void setBatchno(java.lang.String batchno){
		this.batchno = batchno;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */
	@Column(name ="UNITID",nullable=true,length=32)
	public java.lang.String getUnitid(){
		return this.unitid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setUnitid(java.lang.String unitid){
		this.unitid = unitid;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  数量
	 */
	@Column(name ="QTY",nullable=true,scale=10,length=20)
	public java.lang.Double getQty(){
		return this.qty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  数量
	 */
	public void setQty(java.lang.Double qty){
		this.qty = qty;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  基本单位
	 */
	@Column(name ="BASICUNITID",nullable=true,length=32)
	public java.lang.String getBasicunitid(){
		return this.basicunitid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  基本单位
	 */
	public void setBasicunitid(java.lang.String basicunitid){
		this.basicunitid = basicunitid;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  换算率
	 */
	@Column(name ="COEFFICIENT",nullable=true,scale=10,length=20)
	public java.lang.Double getCoefficient(){
		return this.coefficient;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  换算率
	 */
	public void setCoefficient(java.lang.Double coefficient){
		this.coefficient = coefficient;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  基本数量
	 */
	@Column(name ="BASICQTY",nullable=true,scale=10,length=20)
	public java.lang.Double getBasicQty(){
		return this.basicQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  基本数量
	 */
	public void setBasicQty(java.lang.Double basicQty){
		this.basicQty = basicQty;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  辅助单位
	 */
	@Column(name ="SECUNITID",nullable=true,length=32)
	public java.lang.String getSecunitid(){
		return this.secunitid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  辅助单位
	 */
	public void setSecunitid(java.lang.String secunitid){
		this.secunitid = secunitid;
	}



	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  辅助换算率
	 */
	@Column(name ="SECCOEFFICIENT",nullable=true,scale=10,length=20)
	public Double getSecCoefficient() {
		return secCoefficient;
	}

	public void setSecCoefficient(Double secCoefficient) {
		this.secCoefficient = secCoefficient;
	}


	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  辅助数量
	 */
	@Column(name ="SECQTY",nullable=true,scale=10,length=20)
	public Double getSecQty() {
		return secQty;
	}

	public void setSecQty(Double secQty) {
		this.secQty = secQty;
	}

	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  单价
	 */
	@Column(name ="TAXPRICEEX",nullable=true,scale=10,length=20)
	public Double getTaxPriceEx() {
		return taxPriceEx;
	}

	public void setTaxPriceEx(Double taxPriceEx) {
		this.taxPriceEx = taxPriceEx;
	}


	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  金额
	 */
	@Column(name ="TAXAMOUNTEX",nullable=true,scale=10,length=20)
	public Double getTaxAmountEx() {
		return taxAmountEx;
	}

	public void setTaxAmountEx(Double taxAmountEx) {
		this.taxAmountEx = taxAmountEx;
	}

	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  折扣率（%）
	 */
	@Column(name ="DISCOUNTRATE",nullable=true,scale=10,length=20)
	public Double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Double discountRate) {
		this.discountRate = discountRate;
	}

	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  折后单价
	 */
	@Column(name ="TAXPRICE",nullable=true,scale=10,length=20)
	public Double getTaxPrice() {
		return taxPrice;
	}

	public void setTaxPrice(Double taxPrice) {
		this.taxPrice = taxPrice;
	}

	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  折后金额
	 */
	@Column(name ="INTAXAMOUNT",nullable=true,scale=10,length=20)
	public Double getInTaxAmount() {
		return inTaxAmount;
	}

	public void setInTaxAmount(Double inTaxAmount) {
		this.inTaxAmount = inTaxAmount;
	}

	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  税率（%）
	 */
	@Column(name ="TAXRATE",nullable=true,scale=10,length=20)
	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}


	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  不含税单价
	 */
	@Column(name ="PRICE",nullable=true,scale=10,length=20)
	public java.lang.Double getPrice(){
		return this.price;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  不含税单价
	 */
	public void setPrice(java.lang.Double price){
		this.price = price;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  不含税金额
	 */
	@Column(name ="AMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  不含税金额
	 */
	public void setAmount(java.lang.Double amount){
		this.amount = amount;
	}

	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  不含税折后单价
	 */
	@Column(name ="DISCOUNTPRICE",nullable=true,scale=10,length=20)
	public Double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}

	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  不含税折后金额
	 */
	@Column(name ="DISCOUNTAMOUNT",nullable=true,scale=10,length=20)
	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  税额
	 */
	@Column(name ="TAXAMOUNT",nullable=true,scale=10,length=20)
	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  生产日期
	 */
	@Column(name ="KFDATE",nullable=true)
	public java.util.Date getKfdate(){
		return this.kfdate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  生产日期
	 */
	public void setKfdate(java.util.Date kfdate){
		this.kfdate = kfdate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  保质期
	 */
	@Column(name ="KFPERIOD",nullable=true,length=10)
	public java.lang.Integer getKfperiod(){
		return this.kfperiod;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  保质期
	 */
	public void setKfperiod(java.lang.Integer kfperiod){
		this.kfperiod = kfperiod;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  保质期类型
	 */
	@Column(name ="KFDATETYPE",nullable=true,length=10)
	public java.lang.String getKfdatetype(){
		return this.kfdatetype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  保质期类型
	 */
	public void setKfdatetype(java.lang.String kfdatetype){
		this.kfdatetype = kfdatetype;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  有效期至
	 */
	@Column(name ="PERIODDATE",nullable=true)
	public java.util.Date getPerioddate(){
		return this.perioddate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  有效期至
	 */
	public void setPerioddate(java.util.Date perioddate){
		this.perioddate = perioddate;
	}

	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  成本单价
	 */
	@Column(name ="COSTPRICE",nullable=true,scale=10,length=20)
	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  成本金额
	 */
	@Column(name ="COSTAMOUNT",nullable=true,scale=10,length=20)
	public Double getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(Double costAmount) {
		this.costAmount = costAmount;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  赠品标记
	 */
	@Column(name ="FREEGIFTS",nullable=true,length=10)
	public java.lang.Integer getFreegifts(){
		return this.freegifts;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  赠品标记
	 */
	public void setFreegifts(java.lang.Integer freegifts){
		this.freegifts = freegifts;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  退货数量
	 */
	@Column(name ="COMMITQTY",nullable=true,scale=10,length=20)
	public java.lang.Double getCommitqty(){
		return this.commitqty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  退货数量
	 */
	public void setCommitqty(java.lang.Double commitqty){
		this.commitqty = commitqty;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单类型
	 */
	@Column(name ="CLASSID_SRC",nullable=true,length=10)
	public java.lang.String getClassidSrc(){
		return this.classidSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单类型
	 */
	public void setClassidSrc(java.lang.String classidSrc){
		this.classidSrc = classidSrc;
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
	public java.lang.String getBillnoSrc(){
		return this.billnoSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单编号
	 */
	public void setBillnoSrc(java.lang.String billnoSrc){
		this.billnoSrc = billnoSrc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单分录主键
	 */
	@Column(name ="ENTRYID_SRC",nullable=true,length=32)
	public java.lang.String getEntryidSrc(){
		return this.entryidSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单分录主键
	 */
	public void setEntryidSrc(java.lang.String entryidSrc){
		this.entryidSrc = entryidSrc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单主键
	 */
	@Column(name ="ID_ORDER",nullable=true,length=32)
	public java.lang.String getIdOrder(){
		return this.idOrder;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单主键
	 */
	public void setIdOrder(java.lang.String idOrder){
		this.idOrder = idOrder;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单编号
	 */
	@Column(name ="BILLNO_ORDER",nullable=true,length=50)
	public java.lang.String getBillnoOrder(){
		return this.billnoOrder;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单编号
	 */
	public void setBillnoOrder(java.lang.String billnoOrder){
		this.billnoOrder = billnoOrder;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单分录主键
	 */
	@Column(name ="ENTRYID_ORDER",nullable=true,length=32)
	public java.lang.String getEntryidOrder(){
		return this.entryidOrder;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单分录主键
	 */
	public void setEntryidOrder(java.lang.String entryidOrder){
		this.entryidOrder = entryidOrder;
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
	 *@return: java.lang.Integer  分录号
	 */
	@Column(name ="FINDEX",nullable=true,length=10)
	public java.lang.Integer getFindex(){
		return this.findex;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  分录号
	 */
	public void setFindex(java.lang.Integer findex){
		this.findex = findex;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  重量
	 */
	@Column(name ="WEIGHT",nullable=true,scale=10,length=20)
	public java.lang.Double getWeight(){
		return this.weight;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  重量
	 */
	public void setWeight(java.lang.Double weight){
		this.weight = weight;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  促销类型
	 */
	@Column(name ="SALESID",nullable=true,length=32)
	public java.lang.String getSalesid(){
		return this.salesid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  促销类型
	 */
	public void setSalesid(java.lang.String salesid){
		this.salesid = salesid;
	}

	@Transient
	public Integer getFreegifts_() {
		return freegifts_;
	}

	public void setFreegifts_(Integer freegifts_) {
		this.freegifts_ = freegifts_;
	}

	@Transient
	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	@Transient
	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	@Transient
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Transient
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Transient
	public String getIsKfperiod() {
		return isKfperiod;
	}

	public void setIsKfperiod(String isKfperiod) {
		this.isKfperiod = isKfperiod;
	}

	@Transient
	public String getBatchManager() {
		return batchManager;
	}

	public void setBatchManager(String batchManager) {
		this.batchManager = batchManager;
	}

	@Transient
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	@Transient
	public Double getXsLimitPrice() {
		return xsLimitPrice;
	}

	public void setXsLimitPrice(Double xsLimitPrice) {
		this.xsLimitPrice = xsLimitPrice;
	}

	@Transient
	public Double getBillQty() {
		return billQty;
	}

	public void setBillQty(Double billQty) {
		this.billQty = billQty;
	}

	@Transient
	public Double getWeight_() {
		return weight_;
	}

	public void setWeight_(Double weight_) {
		this.weight_ = weight_;
	}

	@Transient
	public Double getItemweight() {
		return itemweight;
	}

	public void setItemweight(Double itemweight) {
		this.itemweight = itemweight;
	}

	@Transient
	public Double getInvQty() {
		return invQty;
	}

	public void setInvQty(Double invQty) {
		this.invQty = invQty;
	}

	@Transient
	public Double getStockQty() {
		return stockQty;
	}

	public void setStockQty(Double stockQty) {
		this.stockQty = stockQty;
	}

	@Transient
	public String getClassidName() {
		return classidName;
	}

	public void setClassidName(String classidName) {
		this.classidName = classidName;
	}

	@Column(name ="isFreeGift",nullable=true,length=10)
	public Integer getIsFreeGift() {
		return isFreeGift;
	}

	public void setIsFreeGift(Integer isFreeGift) {
		this.isFreeGift = isFreeGift;
	}

	@Transient
	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
}
