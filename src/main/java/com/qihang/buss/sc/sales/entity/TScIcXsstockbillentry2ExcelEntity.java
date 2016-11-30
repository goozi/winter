package com.qihang.buss.sc.sales.entity;

import com.qihang.buss.sc.baseinfo.entity.TScIcitemForPoOrderEntity;
import com.qihang.buss.sc.baseinfo.entity.TScItemPriceForPoOrderEntity;
import com.qihang.buss.sc.baseinfo.entity.TScStockForPoOrderEntity;
import com.qihang.winter.poi.excel.annotation.Excel;
import com.qihang.winter.poi.excel.annotation.ExcelEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 采购订单从表
 * @author onlineGenerator
 * @date 2016-07-04 09:28:41
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_IC_XsStockBillEntry2", schema = "")
@SuppressWarnings("serial")
public class TScIcXsstockbillentry2ExcelEntity implements java.io.Serializable {
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
	/**主表id*/
	private String fid;
	/**分录号*/
	@Excel(name="分录号")
	private Integer findex;
	/**仓库*/

	/**商品*/
	private String itemId;
	@ExcelEntity(id="id",name="name")
	private TScIcitemForPoOrderEntity icitemEntity;

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "itemId",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TScIcitemForPoOrderEntity getIcitemEntity() {
		return icitemEntity;
	}

	public void setIcitemEntity(TScIcitemForPoOrderEntity icitemEntity) {
		this.icitemEntity = icitemEntity;
	}

	private String stockId;

	@ExcelEntity(id="id",name="name")
	private TScStockForPoOrderEntity stockEntity;

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "stockId",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TScStockForPoOrderEntity getStockEntity() {
		return stockEntity;
	}

	public void setStockEntity(TScStockForPoOrderEntity stockEntity) {
		this.stockEntity = stockEntity;
	}

	/**单位*/
	private String unitId;

	@ExcelEntity(id="id",name="name")
	private TScItemPriceForPoOrderEntity unitEntity;


	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "unitId",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TScItemPriceForPoOrderEntity getUnitEntity() {
		return unitEntity;
	}

	public void setUnitEntity(TScItemPriceForPoOrderEntity unitEntity) {
		this.unitEntity = unitEntity;
	}

	/**辅助单位*/
	private String secUnitId;
	/**基本单位*/
	private String basicUnitId;
	/**数量*/
	@Excel(name="数量")
	private Double qty;
	/**单价*/
	@Excel(name="单价")
	private Double taxPriceEx;
	/**金额*/
	@Excel(name="金额")
	private Double taxAmountEx;
	/**辅助换算率*/
	private Double secCoefficient;
	/**辅助数量*/
	private Double secQty;
	/**换算率*/
	private Double coefficient;
	/**基本数量*/
	private Double basicQty;
	/**单价*/
	private Double price;
	/**不含税金额*/
	private Double amount;
	/**折扣率（%）*/
	@Excel(name="折扣率（%）")
	private Double discountRate;
	/**不含税折后单价*/
	private Double discountPrice;
	/**不含税折后金额*/
	private Double discountAmount;
	/**折后单价*/
	@Excel(name="折后单价")
	private Double taxPrice;
	/**折后金额*/
	@Excel(name="折后金额")
	private Double inTaxAmount;
	/**税率（%）*/
	@Excel(name="税率（%）")
	private Double taxRate;
	/**税额*/
	@Excel(name="税额")
	private Double taxAmount;
	/**收货日期*/
	@Excel(name="生产日期",format = "yyyy-MM-dd")
	private Date kfDate;

	@Excel(name = "保质期")
	private Integer kfPeriod;//保质期

	@Excel(name = "保质期类型",dicCode = "SC_DURA_DATE_TYPE")
	private String kfDateType;//保质期类型

	@Excel(name = "有效期至",format = "yyyy-MM-dd")
	private Date periodDate;
	/**源单类型*/
	//private Integer classIDSrc;
	/**源单主键*/
	//private String fidSrc;
	/**源单编号*/
	//private String billNoSrc;
	/**源单分路主键*/
	//private String idSrc;
	/**备注*/
	@Excel(name="备注")
	private String note;

	//private Integer version;

	private TScIcXsstockbillExcelEntity entryToMain;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fid",referencedColumnName = "ID")
	public TScIcXsstockbillExcelEntity getEntryToMain() {
		return entryToMain;
	}

	public void setEntryToMain(TScIcXsstockbillExcelEntity entryToMain) {
		this.entryToMain = entryToMain;
	}

	private String stockName;//仓库名称
	private String itemName;//商品名称
	private String itemNo;//商品编号
	private String model;//规格
	private String barCode;//条码

	//选单使用


	private Double basicCoefficient;//基本单位换算率
	private String isKfPeriod;//是否保质期管理
	private String batchManager;//是否批号控制
	private Double billQty;//可选单数量
	private Integer freeGifts_;//赠品标记
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
	 *@return: java.lang.String  主表id
	 */
	//@Column(name ="FID",nullable=true,length=32)
	@Transient
	public String getFid(){
		return this.fid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主表id
	 */
	public void setFid(String fid){
		this.fid = fid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  分录号
	 */
	@Column(name ="FINDEX",nullable=true,length=10)
	public Integer getFindex(){
		return this.findex;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  分录号
	 */
	public void setFindex(Integer findex){
		this.findex = findex;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库
	 */
	//@Column(name ="STOCKID",nullable=true,length=32)
	@Transient
	public String getStockId(){
		return this.stockId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库
	 */
	public void setStockId(String stockId){
		this.stockId = stockId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品
	 */
	//@Column(name ="ITEMID",nullable=true,length=32)
	@Transient
	public String getItemId(){
		return this.itemId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品
	 */
	public void setItemId(String itemId){
		this.itemId = itemId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */
	@Transient
	public String getUnitId(){
		return this.unitId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setUnitId(String unitId){
		this.unitId = unitId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  辅助单位
	 */
	@Column(name ="SECUNITID",nullable=true,length=32)
	public String getSecUnitId(){
		return this.secUnitId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  辅助单位
	 */
	public void setSecUnitId(String secUnitId){
		this.secUnitId = secUnitId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  基本单位
	 */
	@Column(name ="BASICUNITID",nullable=true,length=32)
	public String getBasicUnitId(){
		return this.basicUnitId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  基本单位
	 */
	public void setBasicUnitId(String basicUnitId){
		this.basicUnitId = basicUnitId;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  数量
	 */
	@Column(name ="QTY",nullable=true,scale=10,length=20)
	public Double getQty(){
		return this.qty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  数量
	 */
	public void setQty(Double qty){
		this.qty = qty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  辅助换算率
	 */
	@Column(name ="SECCOEFFICIENT",nullable=true,scale=10,length=20)
	public Double getSecCoefficient(){
		return this.secCoefficient;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  辅助换算率
	 */
	public void setSecCoefficient(Double secCoefficient){
		this.secCoefficient = secCoefficient;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  辅助数量
	 */
	@Column(name ="SECQTY",nullable=true,scale=10,length=20)
	public Double getSecQty(){
		return this.secQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  辅助数量
	 */
	public void setSecQty(Double secQty){
		this.secQty = secQty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  换算率
	 */
	@Column(name ="COEFFICIENT",nullable=true,scale=10,length=20)
	public Double getCoefficient(){
		return this.coefficient;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  换算率
	 */
	public void setCoefficient(Double coefficient){
		this.coefficient = coefficient;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  基本数量
	 */
	@Column(name ="BASICQTY",nullable=true,scale=10,length=20)
	public Double getBasicQty(){
		return this.basicQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  基本数量
	 */
	public void setBasicQty(Double basicQty){
		this.basicQty = basicQty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  单价
	 */
	@Column(name ="PRICE",nullable=true,scale=10,length=20)
	public Double getPrice(){
		return this.price;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  单价
	 */
	public void setPrice(Double price){
		this.price = price;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  不含税金额
	 */
	@Column(name ="AMOUNT",nullable=true,scale=10,length=20)
	public Double getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  不含税金额
	 */
	public void setAmount(Double amount){
		this.amount = amount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  折扣率（%）
	 */
	@Column(name ="DISCOUNTRATE",nullable=true,scale=10,length=20)
	public Double getDiscountRate(){
		return this.discountRate;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  折扣率（%）
	 */
	public void setDiscountRate(Double discountRate){
		this.discountRate = discountRate;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  不含税折后单价
	 */
	@Column(name ="DISCOUNTPRICE",nullable=true,scale=10,length=20)
	public Double getDiscountPrice(){
		return this.discountPrice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  不含税折后单价
	 */
	public void setDiscountPrice(Double discountPrice){
		this.discountPrice = discountPrice;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  不含税折后金额
	 */
	@Column(name ="DISCOUNTAMOUNT",nullable=true,scale=10,length=20)
	public Double getDiscountAmount(){
		return this.discountAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  不含税折后金额
	 */
	public void setDiscountAmount(Double discountAmount){
		this.discountAmount = discountAmount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  税率（%）
	 */
	@Column(name ="TAXRATE",nullable=true,scale=10,length=20)
	public Double getTaxRate(){
		return this.taxRate;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  税率（%）
	 */
	public void setTaxRate(Double taxRate){
		this.taxRate = taxRate;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  单价
	 */
	@Column(name ="TAXPRICEEX",nullable=true,scale=10,length=20)
	public Double getTaxPriceEx(){
		return this.taxPriceEx;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  单价
	 */
	public void setTaxPriceEx(Double taxPriceEx){
		this.taxPriceEx = taxPriceEx;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  金额
	 */
	@Column(name ="TAXAMOUNTEX",nullable=true,scale=10,length=20)
	public Double getTaxAmountEx(){
		return this.taxAmountEx;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  金额
	 */
	public void setTaxAmountEx(Double taxAmountEx){
		this.taxAmountEx = taxAmountEx;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  折后单价
	 */
	@Column(name ="TAXPRICE",nullable=true,scale=10,length=20)
	public Double getTaxPrice(){
		return this.taxPrice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  折后单价
	 */
	public void setTaxPrice(Double taxPrice){
		this.taxPrice = taxPrice;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  折后金额
	 */
	@Column(name ="INTAXAMOUNT",nullable=true,scale=10,length=20)
	public Double getInTaxAmount(){
		return this.inTaxAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  折后金额
	 */
	public void setInTaxAmount(Double inTaxAmount){
		this.inTaxAmount = inTaxAmount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  税额
	 */
	@Column(name ="TAXAMOUNT",nullable=true,scale=10,length=20)
	public Double getTaxAmount(){
		return this.taxAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  税额
	 */
	public void setTaxAmount(Double taxAmount){
		this.taxAmount = taxAmount;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  生产日期
	 */
	@Column(name ="kfDate",nullable=true,length=20)
	public Date getKfDate() {
		return kfDate;
	}

	public void setKfDate(Date kfDate) {
		this.kfDate = kfDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="NOTE",nullable=true,length=32)
	public String getNote(){
		return this.note;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setNote(String note){
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

//	@Version
//	@Column(name = "version",nullable = true)
//	public Integer getVersion() {
//		return version;
//	}
//
//	public void setVersion(Integer version) {
//		this.version = version;
//	}


	@Column(name = "kfPeriod",nullable = true)
	public Integer getKfPeriod() {
		return kfPeriod;
	}

	public void setKfPeriod(Integer kfPeriod) {
		this.kfPeriod = kfPeriod;
	}

	@Column(name = "kfDateType",nullable = true)
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

	@Column(name = "periodDate",nullable = true)
	public Date getPeriodDate() {
		return periodDate;
	}

	public void setPeriodDate(Date periodDate) {
		this.periodDate = periodDate;
	}
}
