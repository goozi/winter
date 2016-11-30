package com.qihang.buss.sc.sales.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import com.qihang.winter.poi.excel.annotation.ExcelEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

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
public class TScOrderentryExcelEntity implements java.io.Serializable {
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
	/**版本号*/
	private Integer version;
	/**主表主键*/
	private String orderId;
	/**分录号*/
	@Excel(name="分录号")
	private Integer indexNumber;
//	@Excel(name="商品")
	private String itemID;
	@ExcelEntity(id="id",name="name")
	private TScIcitemToOrderEntity icitemToOrderEntity;
	/**仓库*/
//	@Excel(name="仓库")
	private String stockID;
	@ExcelEntity(id="id",name="name")
	private TScStockToOrderEntity scStockToOrderEntity;
	/**仓库的名称*/
	private String stockName;
	/**商品*/
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
//	@Excel(name="单位")
	private String unitID;
	@ExcelEntity(id="id",name="name")
	private TScItemPriceToOrderEntity itemPriceToOrderEntity;
	/**辅助单位*/
	private String secUnitID;
	/**基本单位 */
	private String basicUnitID;
	/**数量*/
	@Excel(name="数量")
	private BigDecimal qty;
	/**辅助换算率*/
	private BigDecimal secCoefficient;
	/**辅助数量*/
	private BigDecimal secQty;
	/**换算率*/
	@Excel(name="换算率")
	private BigDecimal coefficient;
	/**基本数量*/
	private BigDecimal basicQty;
	/**不含税单价*/
	private BigDecimal price;
	/**不含税金额*/
	private BigDecimal amount;
	/**不含税折后单价*/
	private BigDecimal discountPrice;
	/**不含税折后金额*/
	private BigDecimal discountAmount;
	/**单价*/
	@Excel(name="单价")
	private BigDecimal taxPriceEx;
	/**金额*/
	@Excel(name="金额")
	private BigDecimal taxAmountEx;
	/**折扣率(%)*/
	@Excel(name="折扣率(%)")
	private BigDecimal discountRate;
	/**折后单价*/
	@Excel(name="折后单价")
	private BigDecimal taxPrice;
	/**折后金额*/
	@Excel(name="折后金额")
	private BigDecimal inTaxAmount;
	/**税额*/
	private BigDecimal taxAmount;
	/**商城单价*/
	@Excel(name="商城单价")
	private BigDecimal mallPrice;
	/**商城金额*/
	@Excel(name="商城金额	")
	private BigDecimal mallAmount;
	/**重量*/
	@Excel(name="重量")
	private BigDecimal weight;
	/**税率(%)*/
	@Excel(name="税率")
	private BigDecimal taxRate;
	/**交货日期*/
	@Excel(name="交货日期",format = "yyyy-MM-dd")
	private Date fetchDate;
	/**促销类型*/
	@Excel(name="促销类型")
	private String salesID;
	/**赠品标记*/
	@Excel(name="赠品标记",replace = {"否_0","是_1"})
	private String freeGifts;
	/**发货数量*/
	@Excel(name="发货数量")
	private BigDecimal stockQty;
	/**源单编号*/
	@Excel(name="源单编号")
	private String billNoSrc;
	/**源单类型*/
	@Excel(name="源单类型")
	private String classIDSrc;
	/**备注 */
	@Excel(name="备注 ")
	private String note;
	/**源单主键*/
	private String interIDSrc;
	/**源单分录主键*/
	private String idSrc;
    /**销售限价*/
	private BigDecimal xsLimitPrice;

	private  TScOrderExcelEntity orderExcelEntity ;

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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  版本号
	 */
    @Version
	@Column(name ="VERSION",nullable=true,length=32)
	public Integer getVersion(){
		return this.version;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  版本号
	 */
	public void setVersion(Integer version){
		this.version = version;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主表主键
	 */
//	@Column(name ="ORDERID",nullable=true,length=32)
	@Transient
	public String getOrderId(){
		return this.orderId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主表主键
	 */
	public void setOrderId(String orderId){
		this.orderId = orderId;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "ORDERID",referencedColumnName = "ID")
	public TScOrderExcelEntity getOrderExcelEntity() {
		return orderExcelEntity;
	}

	public void setOrderExcelEntity(TScOrderExcelEntity orderExcelEntity) {
		this.orderExcelEntity = orderExcelEntity;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  分录号
	 */
	@Column(name ="INDEXNUMBER",nullable=true,length=32)
	public Integer getIndexNumber(){
		return this.indexNumber;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  分录号
	 */
	public void setIndexNumber(Integer indexNumber){
		this.indexNumber = indexNumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库
	 */
//	@Column(name ="STOCKID",nullable=true,length=32)
	@Transient
	public String getStockID(){
		return this.stockID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库
	 */
	public void setStockID(String stockID){
		this.stockID = stockID;
	}

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "STOCKID",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TScStockToOrderEntity getScStockToOrderEntity() {
		return scStockToOrderEntity;
	}

	public void setScStockToOrderEntity(TScStockToOrderEntity scStockToOrderEntity) {
		this.scStockToOrderEntity = scStockToOrderEntity;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品
	 */
//	@Column(name ="ITEMID",nullable=true,length=32)
	@Transient
	public String getItemID(){
		return this.itemID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品
	 */
	public void setItemID(String itemID){
		this.itemID = itemID;
	}

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "ITEMID",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TScIcitemToOrderEntity getIcitemToOrderEntity() {
		return icitemToOrderEntity;
	}

	public void setIcitemToOrderEntity(TScIcitemToOrderEntity icitemToOrderEntity) {
		this.icitemToOrderEntity = icitemToOrderEntity;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */
//	@Column(name ="UNITID",nullable=true,length=32)
	@Transient
	public String getUnitID(){
		return this.unitID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setUnitID(String unitID){
		this.unitID = unitID;
	}


	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "UNITID",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TScItemPriceToOrderEntity getItemPriceToOrderEntity() {
		return itemPriceToOrderEntity;
	}

	public void setItemPriceToOrderEntity(TScItemPriceToOrderEntity itemPriceToOrderEntity) {
		this.itemPriceToOrderEntity = itemPriceToOrderEntity;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  辅助单位
	 */
	@Column(name ="SECUNITID ",nullable=true,length=32)
	public String getSecUnitID(){
		return this.secUnitID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  辅助单位
	 */
	public void setSecUnitID(String secUnitID ){
		this.secUnitID = secUnitID ;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  基本单位
	 */
	@Column(name ="BASICUNITID",nullable=true,length=32)
	public String getBasicUnitID(){
		return this.basicUnitID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  基本单位
	 */
	public void setBasicUnitID(String basicUnitID){
		this.basicUnitID = basicUnitID;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  数量
	 */
	@Column(name ="QTY",nullable=true,length=32)
	public BigDecimal getQty(){
		return this.qty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  数量
	 */
	public void setQty(BigDecimal qty){
		this.qty = qty;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  辅助换算率
	 */
	@Column(name ="SECCOEFFICIENT",nullable=true,length=32)
	public BigDecimal getSecCoefficient(){
		return this.secCoefficient;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  辅助换算率
	 */
	public void setSecCoefficient(BigDecimal secCoefficient){
		this.secCoefficient = secCoefficient;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  辅助数量
	 */
	@Column(name ="SECQTY",nullable=true,length=32)
	public BigDecimal getSecQty(){
		return this.secQty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  辅助数量
	 */
	public void setSecQty(BigDecimal secQty){
		this.secQty = secQty;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  换算率
	 */
	@Column(name ="COEFFICIENT",nullable=true,length=32)
	public BigDecimal getCoefficient(){
		return this.coefficient;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  换算率
	 */
	public void setCoefficient(BigDecimal coefficient){
		this.coefficient = coefficient;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  基本数量
	 */
	@Column(name ="BASICQTY",nullable=true,length=32)
	public BigDecimal getBasicQty(){
		return this.basicQty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  基本数量
	 */
	public void setBasicQty(BigDecimal basicQty){
		this.basicQty = basicQty;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  不含税单价
	 */
	@Column(name ="PRICE",nullable=true,length=32)
	public BigDecimal getPrice(){
		return this.price;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  不含税单价
	 */
	public void setPrice(BigDecimal price){
		this.price = price;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  不含税金额
	 */
	@Column(name ="AMOUNT",nullable=true,length=32)
	public BigDecimal getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  不含税金额
	 */
	public void setAmount(BigDecimal amount){
		this.amount = amount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  折扣率(%)
	 */
	@Column(name ="DISCOUNTRATE",nullable=true,length=32)
	public BigDecimal getDiscountRate(){
		return this.discountRate;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  折扣率(%)
	 */
	public void setDiscountRate(BigDecimal discountRate){
		this.discountRate = discountRate;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  不含税折后单价
	 */
	@Column(name ="DISCOUNTPRICE",nullable=true,length=32)
	public BigDecimal getDiscountPrice(){
		return this.discountPrice;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  不含税折后单价
	 */
	public void setDiscountPrice(BigDecimal discountPrice){
		this.discountPrice = discountPrice;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  不含税折后金额
	 */
	@Column(name ="DISCOUNTAMOUNT",nullable=true,length=32)
	public BigDecimal getDiscountAmount(){
		return this.discountAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  不含税折后金额
	 */
	public void setDiscountAmount(BigDecimal discountAmount){
		this.discountAmount = discountAmount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  税率(%)
	 */
	@Column(name ="TAXRATE",nullable=true,length=32)
	public BigDecimal getTaxRate(){
		return this.taxRate;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  税率(%)
	 */
	public void setTaxRate(BigDecimal taxRate){
		this.taxRate = taxRate;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  单价
	 */
	@Column(name ="TAXPRICEEX",nullable=true,length=32)
	public BigDecimal getTaxPriceEx(){
		return this.taxPriceEx;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  单价
	 */
	public void setTaxPriceEx(BigDecimal taxPriceEx){
		this.taxPriceEx = taxPriceEx;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  金额
	 */
	@Column(name ="TAXAMOUNTEX",nullable=true,length=32)
	public BigDecimal getTaxAmountEx(){
		return this.taxAmountEx;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  金额
	 */
	public void setTaxAmountEx(BigDecimal taxAmountEx){
		this.taxAmountEx = taxAmountEx;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  折后单价
	 */
	@Column(name ="TAXPRICE",nullable=true,length=32)
	public BigDecimal getTaxPrice(){
		return this.taxPrice;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  折后单价
	 */
	public void setTaxPrice(BigDecimal taxPrice){
		this.taxPrice = taxPrice;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  折后金额
	 */
	@Column(name ="INTAXAMOUNT",nullable=true,length=32)
	public BigDecimal getInTaxAmount(){
		return this.inTaxAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  折后金额
	 */
	public void setInTaxAmount(BigDecimal inTaxAmount){
		this.inTaxAmount = inTaxAmount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  税额
	 */
	@Column(name ="TAXAMOUNT",nullable=true,length=32)
	public BigDecimal getTaxAmount(){
		return this.taxAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  税额
	 */
	public void setTaxAmount(BigDecimal taxAmount){
		this.taxAmount = taxAmount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  商城单价
	 */
	@Column(name ="MALLPRICE",nullable=true,length=32)
	public BigDecimal getMallPrice(){
		return this.mallPrice;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  商城单价
	 */
	public void setMallPrice(BigDecimal mallPrice){
		this.mallPrice = mallPrice;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  商城金额
	 */
	@Column(name ="MALLAMOUNT",nullable=true,length=32)
	public BigDecimal getMallAmount(){
		return this.mallAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  商城金额
	 */
	public void setMallAmount(BigDecimal mallAmount){
		this.mallAmount = mallAmount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  重量
	 */
	@Column(name ="WEIGHT",nullable=true,length=32)
	public BigDecimal getWeight(){
		return this.weight;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  重量
	 */
	public void setWeight(BigDecimal weight){
		this.weight = weight;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  交货日期
	 */
	@Column(name ="FETCHDATE",nullable=true,length=32)
	public Date getFetchDate(){
		return this.fetchDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  交货日期
	 */
	public void setFetchDate(Date fetchDate){
		this.fetchDate = fetchDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  促销类型
	 */
	@Column(name ="SALESID",nullable=true,length=32)
	public String getSalesID(){
		return this.salesID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  促销类型
	 */
	public void setSalesID(String salesID){
		this.salesID = salesID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  赠品标记
	 */
	@Column(name ="FREEGIFTS",nullable=true,length=50)
	public String getFreeGifts(){
		return this.freeGifts;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  赠品标记
	 */
	public void setFreeGifts(String freeGifts){
		this.freeGifts = freeGifts;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  发货数量
	 */
	@Column(name ="STOCKQTY",nullable=true,length=32)
	public BigDecimal getStockQty(){
		return this.stockQty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  发货数量
	 */
	public void setStockQty(BigDecimal stockQty){
		this.stockQty = stockQty;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单类型
	 */
	@Column(name ="CLASSID_SRC",nullable=true,length=32)
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
	@Column(name ="INTERID_SRC",nullable=true,length=32)
	public String getInterIDSrc(){
		return this.interIDSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单主键
	 */
	public void setInterIDSrc(String interIDSrc){
		this.interIDSrc = interIDSrc;
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
}
