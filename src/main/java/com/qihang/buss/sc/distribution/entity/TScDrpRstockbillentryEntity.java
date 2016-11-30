package com.qihang.buss.sc.distribution.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

/**   
 * @Title: Entity
 * @Description: 退货申请
 * @author onlineGenerator
 * @date 2016-09-08 17:08:42
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_DRP_RStockBillEntry", schema = "")
@SuppressWarnings("serial")
public class TScDrpRstockbillentryEntity implements java.io.Serializable {
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
	private java.lang.String interId;
	/**分录号*/
	@Excel(name="分录号")
	private java.lang.String indexNumber;
	/**仓库*/
	@Excel(name="仓库")
	private java.lang.String stockId;
	/**商品*/
	@Excel(name="商品")
	private java.lang.String itemId;
	/**单位 */
	@Excel(name="单位 ")
	private java.lang.String unitId;
	/**辅助单位*/
	@Excel(name="辅助单位")
	private java.lang.String secUnitId;
	/**基本单位 */
	@Excel(name="基本单位 ")
	private java.lang.String basicUnitId;
	/**批号*/
	@Excel(name="批号")
	private java.lang.String batchNo;
	/**生产日期*/
	@Excel(name="生产日期")
	private java.util.Date kfDate ;
	/**保质期*/
	@Excel(name="保质期")
	private java.lang.String kfPeriod;
	/**有效期至*/
	@Excel(name="有效期至")
	private java.util.Date periodDate;
	/**保质期类型*/
	@Excel(name="保质期类型")
	private java.lang.String kfDateType;
	/**数量*/
	@Excel(name="数量")
	private java.math.BigDecimal qty;
	/**辅助换算率*/
	@Excel(name="辅助换算率")
	private java.math.BigDecimal secCoefficient ;
	/**辅助数量*/
	@Excel(name="辅助数量")
	private java.math.BigDecimal secQty;
	/**换算率*/
	@Excel(name="换算率")
	private java.math.BigDecimal coefficient;
	/**基本数量 */
	@Excel(name="基本数量 ")
	private java.math.BigDecimal basicQty;
	/**不含税单价*/
	@Excel(name="不含税单价")
	private java.math.BigDecimal price;
	/**不含税金额*/
	@Excel(name="不含税金额")
	private java.math.BigDecimal amount;
	/**折扣率(%)*/
	@Excel(name="折扣率(%)")
	private java.math.BigDecimal discountRate;
	/**不含税折后单价*/
	@Excel(name="不含税折后单价")
	private java.math.BigDecimal discountPrice;
	/**不含税折后金额*/
	@Excel(name="不含税折后金额")
	private java.math.BigDecimal discountAmount;
	/**税率(%)*/
	@Excel(name="税率(%)")
	private java.math.BigDecimal taxRate;
	/**单价*/
	@Excel(name="单价")
	private java.math.BigDecimal taxPriceEx;
	/**金额*/
	@Excel(name="金额")
	private java.math.BigDecimal taxAmountEx;
	/**折后单价*/
	@Excel(name="折后单价")
	private java.math.BigDecimal taxPrice;
	/**折后金额*/
	@Excel(name="折后金额")
	private java.math.BigDecimal inTaxAmount;
	/**源单主键*/
	@Excel(name="源单主键")
	private java.lang.String interIdSrc;
	/**税额*/
	@Excel(name="税额")
	private java.math.BigDecimal taxAmount;
	/**源单编号*/
	@Excel(name="源单编号")
	private java.lang.String billNoSrc;
	/**源单分录主键*/
	@Excel(name="源单分录主键")
	private java.lang.String idSrc;
	/**订单主键*/
	@Excel(name="订单主键")
	private java.lang.String interIdOrder;
	/**订单编号*/
	@Excel(name="订单编号")
	private java.lang.String billNoOrder;
	/**订单分录主键*/
	@Excel(name="订单分录主键")
	private java.lang.String idOrder;
	/**备注 */
	@Excel(name="备注 ")
	private java.lang.String note;

	/**版本号*/
	private java.lang.Integer version;
	/**是否禁用*/
	private java.lang.Integer disabled;
	/**是否删除*/
	private java.lang.Integer deleted;

	private String number;
	private String name;
	private String model;
	private String barCode;
	private String stockName;
	private BigDecimal itemWeight;

	private String	classIDSrc;
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
	public java.lang.String getInterId(){
		return this.interId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主表主键
	 */
	public void setInterId(java.lang.String interId){
		this.interId = interId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分录号
	 */
	@Column(name ="INDEXNUMBER",nullable=true,length=32)
	public String getIndexNumber() {
		return indexNumber;
	}
	public void setIndexNumber(String indexNumber) {
		this.indexNumber = indexNumber;
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  生产日期
	 */
	@Column(name ="KFDATE ",nullable=true,length=32)
	public java.util.Date getKfDate (){
		return this.kfDate ;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  生产日期
	 */
	public void setKfDate (java.util.Date kfDate ){
		this.kfDate  = kfDate ;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  保质期
	 */
	@Column(name ="KFPERIOD",nullable=true,length=32)
	public java.lang.String getKfPeriod(){
		return this.kfPeriod;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  保质期
	 */
	public void setKfPeriod(java.lang.String kfPeriod){
		this.kfPeriod = kfPeriod;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  保质期类型
	 */
	@Column(name ="KFDATETYPE",nullable=true,length=32)
	public java.lang.String getKfDateType(){
		return this.kfDateType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  保质期类型
	 */
	public void setKfDateType(java.lang.String kfDateType){
		this.kfDateType = kfDateType;
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
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  辅助换算率
	 */
	@Column(name ="SECCOEFFICIENT ",nullable=true,length=32)
	public java.math.BigDecimal getSecCoefficient (){
		return this.secCoefficient ;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  辅助换算率
	 */
	public void setSecCoefficient (java.math.BigDecimal secCoefficient ){
		this.secCoefficient  = secCoefficient ;
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
	 *@return: java.lang.String  源单主键
	 */
	@Column(name ="INTERID_SRC",nullable=true,length=32)
	public java.lang.String getInterIdSrc(){
		return this.interIdSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单主键
	 */
	public void setInterIdSrc(java.lang.String interIdSrc){
		this.interIdSrc = interIdSrc;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单编号
	 */
	@Column(name ="BILLNO_SRC",nullable=true,length=32)
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
	 *@return: java.lang.String  源单分录主键
	 */
	@Column(name ="ID_SRC",nullable=true,length=32)
	public java.lang.String getIdSrc(){
		return this.idSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单分录主键
	 */
	public void setIdSrc(java.lang.String idSrc){
		this.idSrc = idSrc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单主键
	 */
	@Column(name ="INTERID_ORDER",nullable=true,length=32)
	public java.lang.String getInterIdOrder(){
		return this.interIdOrder;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单主键
	 */
	public void setInterIdOrder(java.lang.String interIdOrder){
		this.interIdOrder = interIdOrder;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单编号
	 */
	@Column(name ="BILLNO_ORDER",nullable=true,length=32)
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
	@Column(name ="ID_ORDER",nullable=true,length=32)
	public java.lang.String getIdOrder(){
		return this.idOrder;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单分录主键
	 */
	public void setIdOrder(java.lang.String idOrder){
		this.idOrder = idOrder;
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
	@Version
	@Column(name ="Version")
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	@Column(name ="Disabled")
	public Integer getDisabled() {
		return disabled;
	}
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}
	@Column(name ="Deleted")
	public Integer getDeleted() {
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
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
	public BigDecimal getItemWeight() {
		return itemWeight;
	}
	public void setItemWeight(BigDecimal itemWeight) {
		this.itemWeight = itemWeight;
	}

	@Transient
	public String getClassIDSrc() {
		return classIDSrc;
	}
	public void setClassIDSrc(String classIDSrc) {
		this.classIDSrc = classIDSrc;
	}
}
