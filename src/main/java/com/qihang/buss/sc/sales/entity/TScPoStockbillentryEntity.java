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
 * @Description: 采购出入库单明细表
 * @author onlineGenerator
 * @date 2016-07-12 10:47:57
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_Po_StockBillEntry", schema = "")
@SuppressWarnings("serial")
public class TScPoStockbillentryEntity implements java.io.Serializable {
	/**主键*/
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
	private java.lang.String itemId;
	/**仓库*/
	@Excel(name="仓库")
	private java.lang.String stockId;
	/**批号*/
	@Excel(name="批号")
	private java.lang.String batchNo;
	/**单位*/
	@Excel(name="单位")
	private java.lang.String unitId;
	/**数量*/
	@Excel(name="数量")
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
	/**单价*/
	@Excel(name="单价")
	private java.lang.Double taxPriceEx ;
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
	private java.util.Date kfDate;
	/**保质期*/
	@Excel(name="保质期")
	private java.lang.Integer kfPeriod;
	/**保质期类型*/
	@Excel(name="保质期类型")
	private java.lang.String kfDateType;
	/**有效期至*/
	@Excel(name="有效期至")
	private java.util.Date periodDate;
	/**成本单价*/
	@Excel(name="成本单价")
	private java.lang.Double costPrice;
	/**成本金额*/
	@Excel(name="成本金额")
	private java.lang.Double costAmount;
	/**赠品标记*/
	@Excel(name="赠品标记")
	private java.lang.Integer freeGifts;
	/**退货数量*/
	@Excel(name="退货数量")
	private java.lang.Double commitQty;
	/**源单类型*/
	@Excel(name="源单类型")
	private java.lang.String classIDSrc;

	private String classIDName;
	/**源单主键*/
	private java.lang.String idSrc;
	/**源单编号*/
	@Excel(name="源单编号")
	private java.lang.String billNoSrc;
	/**源单分录主键*/
	private java.lang.String entryIdSrc;
	/**订单主键*/
	private java.lang.String idOrder;
	/**订单编号*/
	@Excel(name="订单编号")
	private java.lang.String billNoOrder;
	/**订单分录主键*/
	private java.lang.String entryIdOrder;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String note;
	/**分录号*/
	private java.lang.Integer findex;

	//private TScPoStockbillEntity entryToMain;

	private String stockName;//仓库名称
	private String itemName;//商品名称
	private String itemNo;//商品编号
	private String model;//规格
	private String barCode;//条码
	private String isKFPeriod;//是否保质期管理
	private Integer freeGifts_;//赠品标记
	private String batchManager;//批号管理
	private Double cgLimitPrice;//采购限价

	//选单使用
	private Double billQty;
	private Double basicCoefficient;//基本单位换算率
	
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
	 *@return: java.lang.String  批号
	 */
	@Column(name ="BATCHNO",nullable=true,length=50)
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
	 *@return: java.lang.Double  单价
	 */
	@Column(name ="TAXPRICEEX ",nullable=true,scale=10,length=20)
	public java.lang.Double getTaxPriceEx (){
		return this.taxPriceEx ;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  单价
	 */
	public void setTaxPriceEx (java.lang.Double taxPriceEx ){
		this.taxPriceEx  = taxPriceEx ;
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
	 *@return: java.util.Date  生产日期
	 */
	@Column(name ="KFDATE",nullable=true,length=20)
	public java.util.Date getKfDate(){
		return this.kfDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  生产日期
	 */
	public void setKfDate(java.util.Date kfDate){
		this.kfDate = kfDate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  保质期
	 */
	@Column(name ="KFPERIOD",nullable=true,length=10)
	public java.lang.Integer getKfPeriod(){
		return this.kfPeriod;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  保质期
	 */
	public void setKfPeriod(java.lang.Integer kfPeriod){
		this.kfPeriod = kfPeriod;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  保质期类型
	 */
	@Column(name ="KFDATETYPE",nullable=true,length=10)
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  有效期至
	 */
	@Column(name ="PERIODDATE",nullable=true,length=20)
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
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  成本单价
	 */
	@Column(name ="COSTPRICE",nullable=true,scale=10,length=20)
	public java.lang.Double getCostPrice(){
		return this.costPrice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  成本单价
	 */
	public void setCostPrice(java.lang.Double costPrice){
		this.costPrice = costPrice;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  成本金额
	 */
	@Column(name ="COSTAMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getCostAmount(){
		return this.costAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  成本金额
	 */
	public void setCostAmount(java.lang.Double costAmount){
		this.costAmount = costAmount;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  赠品标记
	 */
	@Column(name ="FREEGIFTS",nullable=true,length=10)
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
	 *@return: java.lang.Double  退货数量
	 */
	@Column(name ="COMMITQTY",nullable=true,scale=10,length=20)
	public java.lang.Double getCommitQty(){
		return this.commitQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  退货数量
	 */
	public void setCommitQty(java.lang.Double commitQty){
		this.commitQty = commitQty;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单类型
	 */
	@Column(name ="CLASSID_SRC",nullable=true,length=10)
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
	@Column(name ="ENTRYID_SRC",nullable=true,length=32)
	public java.lang.String getEntryIdSrc(){
		return this.entryIdSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单分录主键
	 */
	public void setEntryIdSrc(java.lang.String entryIdSrc){
		this.entryIdSrc = entryIdSrc;
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
	@Column(name ="ENTRYID_ORDER",nullable=true,length=32)
	public java.lang.String getEntryIdOrder(){
		return this.entryIdOrder;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单分录主键
	 */
	public void setEntryIdOrder(java.lang.String entryIdOrder){
		this.entryIdOrder = entryIdOrder;
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

	@Transient
	public String getIsKFPeriod() {
		return isKFPeriod;
	}

	public void setIsKFPeriod(String isKFPeriod) {
		this.isKFPeriod = isKFPeriod;
	}

	@Transient
	public Integer getFreeGifts_() {
		return freeGifts_;
	}

	public void setFreeGifts_(Integer freeGifts_) {
		this.freeGifts_ = freeGifts_;
	}

	@Transient
	public String getBatchManager() {
		return batchManager;
	}

	public void setBatchManager(String batchManager) {
		this.batchManager = batchManager;
	}


//	@ManyToOne(fetch = FetchType.EAGER)
//	@JoinColumn(name = "fid",referencedColumnName = "ID")
//	public TScPoStockbillEntity getEntryToMain() {
//		return entryToMain;
//	}
//
//	public void setEntryToMain(TScPoStockbillEntity entryToMain) {
//		this.entryToMain = entryToMain;
//	}


	@Transient
	public Double getBasicCoefficient() {
		return basicCoefficient;
	}

	public void setBasicCoefficient(Double basicCoefficient) {
		this.basicCoefficient = basicCoefficient;
	}

	@Transient
	public Double getBillQty() {
		return billQty;
	}

	public void setBillQty(Double billQty) {
		this.billQty = billQty;
	}

	@Transient
	public Double getCgLimitPrice() {
		return cgLimitPrice;
	}

	public void setCgLimitPrice(Double cgLimitPrice) {
		this.cgLimitPrice = cgLimitPrice;
	}

	@Transient
	public String getClassIDName() {
		return classIDName;
	}

	public void setClassIDName(String classIDName) {
		this.classIDName = classIDName;
	}
}
