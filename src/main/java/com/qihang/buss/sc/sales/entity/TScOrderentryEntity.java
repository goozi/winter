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
 * @Description: 销售订单从表
 * @author onlineGenerator
 * @date 2016-06-16 11:35:42
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_sc_order_entry", schema = "")
@SuppressWarnings("serial")
public class TScOrderentryEntity implements java.io.Serializable {
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
	/**版本号*/
	@Excel(name="版本号")
	private java.lang.Integer version;
	/**主表主键*/
	@Excel(name="主表主键")
	private java.lang.String orderId;
	/**分录号*/
	@Excel(name="分录号")
	private java.lang.Integer indexNumber;
	/**仓库*/
	@Excel(name="仓库")
	private java.lang.String stockID;
	/**仓库的名称*/
	private String stockName;
	/**商品*/
	@Excel(name="商品")
	private java.lang.String itemID;
	/**商品编号*/
	private String itemNumber;
	/**商品名称*/
	private String itemName;
	/**商品规格*/
	private String itemModel;
	/**商品条形码*/
	private String itemBarcode;
	/**商品的重量*/
	private String itemWeight;
	/**单位*/
	@Excel(name="单位")
	private java.lang.String unitID;
	/**辅助单位*/
	@Excel(name="辅助单位")
	private java.lang.String secUnitID;
	/**基本单位 */
	@Excel(name="基本单位 ")
	private java.lang.String basicUnitID;
	/**数量*/
	@Excel(name="数量")
	private java.math.BigDecimal qty;
	/**辅助换算率*/
	@Excel(name="辅助换算率")
	private java.math.BigDecimal secCoefficient;
	/**辅助数量*/
	@Excel(name="辅助数量")
	private java.math.BigDecimal secQty;
	/**换算率*/
	@Excel(name="换算率")
	private java.math.BigDecimal coefficient;
	/**基本数量*/
	@Excel(name="基本数量")
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
	/**税额*/
	@Excel(name="税额")
	private java.math.BigDecimal taxAmount;
	/**商城单价*/
	@Excel(name="商城单价")
	private java.math.BigDecimal mallPrice;
	/**商城金额*/
	@Excel(name="商城金额")
	private java.math.BigDecimal mallAmount;
	/**重量*/
	@Excel(name="重量")
	private java.math.BigDecimal weight;
	/**交货日期*/
	@Excel(name="交货日期")
	private java.util.Date fetchDate;
	/**促销类型*/
	@Excel(name="促销类型")
	private java.lang.String salesID;
	/**赠品标记*/
	@Excel(name="赠品标记")
	private java.lang.String freeGifts;
	/**发货数量*/
	@Excel(name="发货数量")
	private java.math.BigDecimal stockQty;
	/**源单类型*/
	@Excel(name="源单类型")
	private java.lang.String classIDSrc;
	/**源单主键*/
	@Excel(name="源单主键")
	private java.lang.String interIDSrc;
	/**源单编号*/
	@Excel(name="源单编号")
	private java.lang.String billNoSrc;
	/**源单分录主键*/
	@Excel(name="源单分录主键")
	private java.lang.String idSrc;
	/**备注 */
	@Excel(name="备注 ")
	private java.lang.String note;
    /**销售限价*/
	private java.math.BigDecimal xsLimitPrice;

	private Integer isFreeGift;
	private String salesName;//促销类型
	private String classIDName;
	
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  版本号
	 */
    @Version
	@Column(name ="VERSION",nullable=true,length=32)
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主表主键
	 */
	@Column(name ="ORDERID",nullable=true,length=32)
	public java.lang.String getOrderId(){
		return this.orderId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主表主键
	 */
	public void setOrderId(java.lang.String orderId){
		this.orderId = orderId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  分录号
	 */
	@Column(name ="INDEXNUMBER",nullable=true,length=32)
	public java.lang.Integer getIndexNumber(){
		return this.indexNumber;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  分录号
	 */
	public void setIndexNumber(java.lang.Integer indexNumber){
		this.indexNumber = indexNumber;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  辅助单位
	 */
	@Column(name ="SECUNITID ",nullable=true,length=32)
	public java.lang.String getSecUnitID(){
		return this.secUnitID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  辅助单位
	 */
	public void setSecUnitID(java.lang.String secUnitID ){
		this.secUnitID = secUnitID ;
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
	 *@return: java.math.BigDecimal  商城单价
	 */
	@Column(name ="MALLPRICE",nullable=true,length=32)
	public java.math.BigDecimal getMallPrice(){
		return this.mallPrice;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  商城单价
	 */
	public void setMallPrice(java.math.BigDecimal mallPrice){
		this.mallPrice = mallPrice;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  商城金额
	 */
	@Column(name ="MALLAMOUNT",nullable=true,length=32)
	public java.math.BigDecimal getMallAmount(){
		return this.mallAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  商城金额
	 */
	public void setMallAmount(java.math.BigDecimal mallAmount){
		this.mallAmount = mallAmount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  重量
	 */
	@Column(name ="WEIGHT",nullable=true,length=32)
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  交货日期
	 */
	@Column(name ="FETCHDATE",nullable=true,length=32)
	public java.util.Date getFetchDate(){
		return this.fetchDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  交货日期
	 */
	public void setFetchDate(java.util.Date fetchDate){
		this.fetchDate = fetchDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  促销类型
	 */
	@Column(name ="SALESID",nullable=true,length=32)
	public java.lang.String getSalesID(){
		return this.salesID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  促销类型
	 */
	public void setSalesID(java.lang.String salesID){
		this.salesID = salesID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  赠品标记
	 */
	@Column(name ="FREEGIFTS",nullable=true,length=50)
	public java.lang.String getFreeGifts(){
		return this.freeGifts;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  赠品标记
	 */
	public void setFreeGifts(java.lang.String freeGifts){
		this.freeGifts = freeGifts;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  发货数量
	 */
	@Column(name ="STOCKQTY",nullable=true,length=32)
	public java.math.BigDecimal getStockQty(){
		return this.stockQty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  发货数量
	 */
	public void setStockQty(java.math.BigDecimal stockQty){
		this.stockQty = stockQty;
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
	 *@return: java.lang.String  源单分录主键
	 */
	@Column(name ="ID_SRC",nullable=true,length=32)
	public String getIdSrc() {
		return idSrc;
	}

	public void setIdSrc(String idSrc) {
		this.idSrc = idSrc;
	}

//	public java.lang.String getFIDSrc(){
//		return this.fIDSrc;
//	}
//
//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  源单分录主键
//	 */
//	public void setFIDSrc(java.lang.String fIDSrc){
//		this.fIDSrc = fIDSrc;
//	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注 
	 */
	@Column(name ="Note",nullable=true,length=255)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

//	public java.lang.String getFNote(){
//		return this.fNote;
//	}
//
//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  备注
//	 */
//	public void setFNote(java.lang.String fNote){
//		this.fNote = fNote;
//	}

	@Transient
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	@Transient
	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	@Transient
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	@Transient
	public String getItemModel() {
		return itemModel;
	}

	public void setItemModel(String itemModel) {
		this.itemModel = itemModel;
	}
	@Transient
	public String getItemBarcode() {
		return itemBarcode;
	}

	public void setItemBarcode(String itemBarcode) {
		this.itemBarcode = itemBarcode;
	}
	@Transient
	public String getItemWeight() {
		return itemWeight;
	}

	public void setItemWeight(String itemWeight) {
		this.itemWeight = itemWeight;
	}
	@Transient
	public BigDecimal getXsLimitPrice() {
		return xsLimitPrice;
	}

	public void setXsLimitPrice(BigDecimal xsLimitPrice) {
		this.xsLimitPrice = xsLimitPrice;
	}

	@Column(name ="isFreeGift",nullable=true)
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
	public String getClassIDName() {
		return classIDName;
	}

	public void setClassIDName(String classIDName) {
		this.classIDName = classIDName;
	}
}
