package com.qihang.buss.sc.distribution.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

/**   
 * @Title: Entity
 * @Description: 商品信息
 * @author onlineGenerator
 * @date 2016-08-08 19:52:05
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_DRP_StockBillEntry", schema = "")
@SuppressWarnings("serial")
public class TScDrpStockbillentryEntity implements java.io.Serializable {
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
	private java.lang.String fid;
	/**分录号*/
	private java.lang.String indexNumber;
	/**商品*/
	@Excel(name="商品")
	private java.lang.String itemID;
	/**仓库*/
	@Excel(name="仓库")
	private java.lang.String stockID;
	/**批号*/
	@Excel(name="批号")
	private java.lang.String batchNo;
	/**单位*/
	@Excel(name="单位")
	private java.lang.String unitID;
	/**数量*/
	@Excel(name="数量")
	private java.lang.Double qty;
	/**基本单位*/
	private java.lang.String basicUnitID;
	/**换算率*/
	private java.math.BigDecimal coefficient;
	/**辅助单位*/
	private java.lang.String secUnitID;
	/**基本数量*/
	private java.lang.Double basicQty;
	/**辅助换算率*/
	private java.math.BigDecimal secCoefficient;
	/**辅助数量*/
	private java.lang.Double secQty;
	/**单价*/
	@Excel(name="单价")
	private java.lang.Double taxPriceEx;
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
	/**重量*/
	@Excel(name="重量")
	private BigDecimal weight;
	/**税率(%)*/
	@Excel(name="税率(%)")
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
	private java.util.Date date;
	/**保质期*/
	@Excel(name="保质期")
	private java.lang.Integer kfperiod;
	/**保质期类型*/
	@Excel(name="保质期类型")
	private java.lang.String kfDateType;
	/**有效期至*/
	@Excel(name="有效期至")
	private java.util.Date periodDate;
	/**成本单价*/
	@Excel(name="成本单价")
	private java.math.BigDecimal costPrice;
	/**成本金额*/
	@Excel(name="成本金额")
	private java.math.BigDecimal costAmount;
	/**促销类型*/
	@Excel(name="促销类型")
	private java.lang.Integer salesID;
	/**赠品标记*/
	@Excel(name="赠品标记")
	private java.lang.Integer freeGifts;
	/**退货数量*/
	@Excel(name="退货数量")
	private java.lang.String commitQty;
	/**确认收货数量*/
	@Excel(name="确认收货数量")
	private java.math.BigDecimal stockQty;
	/**源单主键*/
	private java.lang.String interIDSrc;
	/**源单类型*/
	@Excel(name="源单类型")
	private java.lang.Integer classIDSrc;
	/**源单编号*/
	@Excel(name="源单编号")
	private java.lang.String billNoSrc;
	/**源单分录号*/
	private java.lang.String fidSrc;
	/**订单主键*/
	private java.lang.String interIDOrder;
	/**订单编号*/
	@Excel(name="订单编号")
	private java.lang.String billNoOrder;
	/**订单分录主键*/
	private java.lang.String fidOrder;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String note;

	private java.lang.String number;//商品编号
	private java.lang.String name;//商品名称
	private java.lang.String model;//规格
	private java.lang.String barCode;//条形码
	private java.lang.String stockName;//仓库名称
	private java.lang.String unitName;
	private java.lang.String secUnitName;
	private java.lang.String basicUnitName;
	private String classIDName;

	private Double billQty;
	private Double itemWeight;

	private Integer isFreeGift;//赠品标记
	private String salesName;//促销名称
	private Integer freegifts_;

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
	@Column(name ="FID",nullable=true,length=32)
	public java.lang.String getFid(){
		return this.fid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主表主键
	 */
	public void setFid(java.lang.String fid){
		this.fid = fid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分录号
	 */
	@Column(name ="INDEXNUMBER",nullable=true,length=32)
	public java.lang.String getIndexNumber(){
		return this.indexNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分录号
	 */
	public void setIndexNumber(java.lang.String indexNumber){
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
	 *@return: java.lang.String  批号
	 */
	@Column(name ="BATCHNO",nullable=true,length=32)
	public java.lang.String getBatchNo(){
		return this.batchNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  批号
	 */
	public void setBatchNo(java.lang.String batchNo){
		this.batchNo = batchNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */
	@Column(name ="UNITID",nullable=true,length=32)
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
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  数量
	 */
	@Column(name ="QTY",nullable=true,length=32)
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
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  基本数量
	 */
	@Column(name ="BASICQTY",nullable=true,length=32)
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
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  辅助数量
	 */
	@Column(name ="SECQTY",nullable=true,length=32)
	public java.lang.Double getSecQty(){
		return this.secQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double 辅助数量
	 */
	public void setSecQty(java.lang.Double secQty){
		this.secQty = secQty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  单价
	 */
	@Column(name ="TAXPRICEEX",nullable=true,length=32)
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  重量
	 */
	@Column(name ="WEIGHT",nullable=true,length=32)
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  生产日期
	 */
	@Column(name ="DATE",nullable=true,length=32)
	public java.util.Date getDate(){
		return this.date;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  生产日期
	 */
	public void setDate(java.util.Date date){
		this.date = date;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  保质期
	 */
	@Column(name ="KFPERIOD",nullable=true,length=32)
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
	@Column(name ="KFDATETYPE",nullable=true,length=32)
	public String getKfDateType() {
		return kfDateType;
	}

	public void setKfDateType(String kfDateType) {
		this.kfDateType = kfDateType;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  有效期至
	 */
	@Column(name ="PERIODDATE",nullable=true,length=32)
	public java.util.Date getPeriodDate(){
		return this.periodDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  有效期至
	 */
	public void setPeriodDate(java.util.Date periodDate){
		this.periodDate = periodDate;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  成本单价
	 */
	@Column(name ="COSTPRICE",nullable=true,length=32)
	public java.math.BigDecimal getCostPrice(){
		return this.costPrice;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  成本单价
	 */
	public void setCostPrice(java.math.BigDecimal costPrice){
		this.costPrice = costPrice;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  成本金额
	 */
	@Column(name ="COSTAMOUNT",nullable=true,length=32)
	public java.math.BigDecimal getCostAmount(){
		return this.costAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  成本金额
	 */
	public void setCostAmount(java.math.BigDecimal costAmount){
		this.costAmount = costAmount;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  促销类型
	 */
	@Column(name ="SALESID",nullable=true,length=32)
	public java.lang.Integer getSalesID(){
		return this.salesID;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  促销类型
	 */
	public void setSalesID(java.lang.Integer salesID){
		this.salesID = salesID;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  赠品标记
	 */
	@Column(name ="FREEGIFTS",nullable=true,length=32)
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  退货数量
	 */
	@Column(name ="COMMITQTY",nullable=true,length=32)
	public java.lang.String getCommitQty(){
		return this.commitQty;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  退货数量
	 */
	public void setCommitQty(java.lang.String commitQty){
		this.commitQty = commitQty;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  确认收货数量
	 */
	@Column(name ="STOCKQTY",nullable=true,length=32)
	public BigDecimal getStockQty() {
		return stockQty;
	}
	public void setStockQty(BigDecimal stockQty) {
		this.stockQty = stockQty;
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
	 *@return: java.lang.String  源单分录号
	 */
	@Column(name ="FID_SRC",nullable=true,length=32)
	public java.lang.String getFidSrc(){
		return this.fidSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单分录号
	 */
	public void setFidSrc(java.lang.String fidSrc){
		this.fidSrc = fidSrc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单主键
	 */
	@Column(name ="INTERID_ORDER",nullable=true,length=32)
	public java.lang.String getInterIDOrder(){
		return this.interIDOrder;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单主键
	 */
	public void setInterIDOrder(java.lang.String interIDOrder){
		this.interIDOrder = interIDOrder;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单编号
	 */
	@Column(name ="BILLNO_ORDER",nullable=true,length=50)
	public java.lang.String getBillNoOrder(){
		return this.billNoOrder;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单编号
	 */
	public void setBillNoOrder(java.lang.String billNoOrder){
		this.billNoOrder = billNoOrder;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单分录主键
	 */
	@Column(name ="FID_ORDER",nullable=true,length=32)
	public java.lang.String getFidOrder(){
		return this.fidOrder;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单分录主键
	 */
	public void setFidOrder(java.lang.String fidOrder){
		this.fidOrder = fidOrder;
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
	@Transient
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	@Transient
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
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
	public String getSecUnitName() {
		return secUnitName;
	}
	public void setSecUnitName(String secUnitName) {
		this.secUnitName = secUnitName;
	}
	@Transient
	public String getBasicUnitName() {
		return basicUnitName;
	}
	public void setBasicUnitName(String basicUnitName) {
		this.basicUnitName = basicUnitName;
	}
	@Transient
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
	@Transient
	public Double getBillQty() {
		return billQty;
	}
	public void setBillQty(Double billQty) {
		this.billQty = billQty;
	}
	@Transient
	public Double getItemWeight() {
		return itemWeight;
	}
	public void setItemWeight(Double itemWeight) {
		this.itemWeight = itemWeight;
	}
	@Transient
	public Integer getFreegifts_() {
		return freegifts_;
	}
	public void setFreegifts_(Integer freegifts_) {
		this.freegifts_ = freegifts_;
	}
	@Transient
	public String getClassIDName() {
		return classIDName;
	}
	public void setClassIDName(String classIDName) {
		this.classIDName = classIDName;
	}
}
