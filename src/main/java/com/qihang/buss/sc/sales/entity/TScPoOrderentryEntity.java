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
 * @Description: 采购订单从表
 * @author onlineGenerator
 * @date 2016-07-04 09:28:41
 * @version V1.0   
 *
 */
@Entity(name="TScPoOrderentryEntity")
@Table(name = "T_SC_PO_OrderEntry", schema = "")
@SuppressWarnings("serial")
public class TScPoOrderentryEntity implements java.io.Serializable {
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
	/**主表id*/
	private java.lang.String fid;
	/**分录号*/
	@Excel(name="分录号")
	private java.lang.Integer findex;
	/**仓库*/
	@Excel(name="仓库")
	private java.lang.String stockId;
	/**商品*/
	@Excel(name="商品")
	private java.lang.String itemId;
	/**单位*/
	@Excel(name="单位")
	private java.lang.String unitId;
	/**辅助单位*/
	@Excel(name="辅助单位")
	private java.lang.String secUnitId;
	/**基本单位*/
	@Excel(name="基本单位")
	private java.lang.String basicUnitId;
	/**数量*/
	@Excel(name="数量")
	private java.lang.Double qty;
	/**辅助换算率*/
	@Excel(name="辅助换算率")
	private java.lang.Double secCoefficient;
	/**辅助数量*/
	@Excel(name="辅助数量")
	private java.lang.Double secQty;
	/**换算率*/
	@Excel(name="换算率")
	private java.lang.Double coefficient;
	/**基本数量*/
	@Excel(name="基本数量")
	private java.lang.Double basicQty;
	/**单价*/
	@Excel(name="单价")
	private java.lang.Double price;
	/**不含税金额*/
	@Excel(name="不含税金额")
	private java.lang.Double amount;
	/**折扣率（%）*/
	@Excel(name="折扣率（%）")
	private java.lang.Double discountRate;
	/**不含税折后单价*/
	@Excel(name="不含税折后单价")
	private java.lang.Double discountPrice;
	/**不含税折后金额*/
	@Excel(name="不含税折后金额")
	private java.lang.Double discountAmount;
	/**税率（%）*/
	@Excel(name="税率（%）")
	private java.lang.Double taxRate;
	/**单价*/
	@Excel(name="单价")
	private java.lang.Double taxPriceEx;
	/**金额*/
	@Excel(name="金额")
	private java.lang.Double taxAmountEx;
	/**折后单价*/
	@Excel(name="折后单价")
	private java.lang.Double taxPrice;
	/**折后金额*/
	@Excel(name="折后金额")
	private java.lang.Double inTaxAmount;
	/**税额*/
	@Excel(name="税额")
	private java.lang.Double taxAmount;
	/**收货日期*/
	@Excel(name="收货日期")
	private java.util.Date jhDate;
	/**赠品标记*/
	@Excel(name="赠品标记")
	private java.lang.Integer freeGifts;
	/**收货数量*/
	@Excel(name="收货数量")
	private java.lang.Double stockQty;
	/**源单类型*/
	@Excel(name="源单类型")
	private java.lang.Integer classIDSrc;
	/**源单主键*/
	private java.lang.String fidSrc;
	/**源单编号*/
	@Excel(name="源单编号")
	private java.lang.String billNoSrc;
	/**源单分路主键*/
	private java.lang.String idSrc;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String note;


	private Integer version;

	private String stockName;//仓库名称
	private String itemName;//商品名称
	private String itemNo;//商品编号
	private String model;//规格
	private String barCode;//条码

	//选单使用
	private Integer kfPeriod;//保质期
	private String kfDateType;//保质期类型
	private Double basicCoefficient;//基本单位换算率
	private String isKfPeriod;//是否保质期管理
	private String batchManager;//是否批号控制
	private Double billQty;//可选单数量
	private Integer freeGifts_;//赠品标记
	private BigDecimal cgLimitPrice;//采购限价
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
	 *@return: java.lang.String  主表id
	 */
	@Column(name ="FID",nullable=true,length=32)
	public java.lang.String getFid(){
		return this.fid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主表id
	 */
	public void setFid(java.lang.String fid){
		this.fid = fid;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库
	 */
	@Column(name ="STOCKID",nullable=true,length=32)
	public java.lang.String getStockId(){
		return this.stockId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库
	 */
	public void setStockId(java.lang.String stockId){
		this.stockId = stockId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品
	 */
	@Column(name ="ITEMID",nullable=true,length=32)
	public java.lang.String getItemId(){
		return this.itemId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品
	 */
	public void setItemId(java.lang.String itemId){
		this.itemId = itemId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */
	@Column(name ="UNITID",nullable=true,length=32)
	public java.lang.String getUnitId(){
		return this.unitId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setUnitId(java.lang.String unitId){
		this.unitId = unitId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  辅助单位
	 */
	@Column(name ="SECUNITID",nullable=true,length=32)
	public java.lang.String getSecUnitId(){
		return this.secUnitId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  辅助单位
	 */
	public void setSecUnitId(java.lang.String secUnitId){
		this.secUnitId = secUnitId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  基本单位
	 */
	@Column(name ="BASICUNITID",nullable=true,length=32)
	public java.lang.String getBasicUnitId(){
		return this.basicUnitId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  基本单位
	 */
	public void setBasicUnitId(java.lang.String basicUnitId){
		this.basicUnitId = basicUnitId;
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
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  辅助换算率
	 */
	@Column(name ="SECCOEFFICIENT",nullable=true,scale=10,length=20)
	public java.lang.Double getSecCoefficient(){
		return this.secCoefficient;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  辅助换算率
	 */
	public void setSecCoefficient(java.lang.Double secCoefficient){
		this.secCoefficient = secCoefficient;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  辅助数量
	 */
	@Column(name ="SECQTY",nullable=true,scale=10,length=20)
	public java.lang.Double getSecQty(){
		return this.secQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  辅助数量
	 */
	public void setSecQty(java.lang.Double secQty){
		this.secQty = secQty;
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
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  单价
	 */
	@Column(name ="PRICE",nullable=true,scale=10,length=20)
	public java.lang.Double getPrice(){
		return this.price;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  单价
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
	 *@return: java.lang.Double  折扣率（%）
	 */
	@Column(name ="DISCOUNTRATE",nullable=true,scale=10,length=20)
	public java.lang.Double getDiscountRate(){
		return this.discountRate;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  折扣率（%）
	 */
	public void setDiscountRate(java.lang.Double discountRate){
		this.discountRate = discountRate;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  不含税折后单价
	 */
	@Column(name ="DISCOUNTPRICE",nullable=true,scale=10,length=20)
	public java.lang.Double getDiscountPrice(){
		return this.discountPrice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  不含税折后单价
	 */
	public void setDiscountPrice(java.lang.Double discountPrice){
		this.discountPrice = discountPrice;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  不含税折后金额
	 */
	@Column(name ="DISCOUNTAMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getDiscountAmount(){
		return this.discountAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  不含税折后金额
	 */
	public void setDiscountAmount(java.lang.Double discountAmount){
		this.discountAmount = discountAmount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  税率（%）
	 */
	@Column(name ="TAXRATE",nullable=true,scale=10,length=20)
	public java.lang.Double getTaxRate(){
		return this.taxRate;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  税率（%）
	 */
	public void setTaxRate(java.lang.Double taxRate){
		this.taxRate = taxRate;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  单价
	 */
	@Column(name ="TAXPRICEEX",nullable=true,scale=10,length=20)
	public java.lang.Double getTaxPriceEx(){
		return this.taxPriceEx;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  单价
	 */
	public void setTaxPriceEx(java.lang.Double taxPriceEx){
		this.taxPriceEx = taxPriceEx;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  金额
	 */
	@Column(name ="TAXAMOUNTEX",nullable=true,scale=10,length=20)
	public java.lang.Double getTaxAmountEx(){
		return this.taxAmountEx;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  金额
	 */
	public void setTaxAmountEx(java.lang.Double taxAmountEx){
		this.taxAmountEx = taxAmountEx;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  折后单价
	 */
	@Column(name ="TAXPRICE",nullable=true,scale=10,length=20)
	public java.lang.Double getTaxPrice(){
		return this.taxPrice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  折后单价
	 */
	public void setTaxPrice(java.lang.Double taxPrice){
		this.taxPrice = taxPrice;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  折后金额
	 */
	@Column(name ="INTAXAMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getInTaxAmount(){
		return this.inTaxAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  折后金额
	 */
	public void setInTaxAmount(java.lang.Double inTaxAmount){
		this.inTaxAmount = inTaxAmount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  税额
	 */
	@Column(name ="TAXAMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getTaxAmount(){
		return this.taxAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  税额
	 */
	public void setTaxAmount(java.lang.Double taxAmount){
		this.taxAmount = taxAmount;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  收货日期
	 */
	@Column(name ="JHDATE",nullable=true,length=20)
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  赠品标记
	 */
	@Column(name ="FREEGIFTS",nullable=true,length=1)
	public java.lang.Integer getFreeGifts(){
		return this.freeGifts;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  赠品标记
	 */
	public void setFreeGifts(java.lang.Integer freeGifts){
		this.freeGifts = freeGifts;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  收货数量
	 */
	@Column(name ="STOCKQTY",nullable=true,scale=10,length=20)
	public java.lang.Double getStockQty(){
		return this.stockQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  收货数量
	 */
	public void setStockQty(java.lang.Double stockQty){
		this.stockQty = stockQty;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  源单类型
	 */
	@Column(name ="CLASSID_SRC",nullable=true,length=10)
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
	 *@return: java.lang.String  源单主键
	 */
	@Column(name ="FID_SRC",nullable=true,length=32)
	public java.lang.String getFidSrc(){
		return this.fidSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单主键
	 */
	public void setFidSrc(java.lang.String fidSrc){
		this.fidSrc = fidSrc;
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
	 *@return: java.lang.String  源单分路主键
	 */
	@Column(name ="ID_SRC",nullable=true,length=32)
	public java.lang.String getIdSrc(){
		return this.idSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单分路主键
	 */
	public void setIdSrc(java.lang.String idSrc){
		this.idSrc = idSrc;
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

	@Transient
	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	@Transient
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Transient
	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
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

	@Version
	@Column(name = "version",nullable = true)
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}


	@Transient
	public Integer getKfPeriod() {
		return kfPeriod;
	}

	public void setKfPeriod(Integer kfPeriod) {
		this.kfPeriod = kfPeriod;
	}

	@Transient
	public String getKfDateType() {
		return kfDateType;
	}

	public void setKfDateType(String kfDateType) {
		this.kfDateType = kfDateType;
	}

	@Transient
	public Double getBasicCoefficient() {
		return basicCoefficient;
	}

	public void setBasicCoefficient(Double basicCoefficient) {
		this.basicCoefficient = basicCoefficient;
	}

	@Transient

	public String getIsKfPeriod() {
		return isKfPeriod;
	}

	public void setIsKfPeriod(String isKfPeriod) {
		this.isKfPeriod = isKfPeriod;
	}

	@Transient
	public String getBatchManager() {
		return batchManager;
	}

	public void setBatchManager(String batchManager) {
		this.batchManager = batchManager;
	}

	@Transient
	public Double getBillQty() {
		return billQty;
	}

	public void setBillQty(Double billQty) {
		this.billQty = billQty;
	}

	@Transient
	public Integer getFreeGifts_() {
		return freeGifts_;
	}

	public void setFreeGifts_(Integer freeGifts_) {
		this.freeGifts_ = freeGifts_;
	}

	@Transient
	public BigDecimal getCgLimitPrice() {
		return cgLimitPrice;
	}

	public void setCgLimitPrice(BigDecimal cgLimitPrice) {
		this.cgLimitPrice = cgLimitPrice;
	}
}
