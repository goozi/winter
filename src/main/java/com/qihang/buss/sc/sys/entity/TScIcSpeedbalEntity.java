package com.qihang.buss.sc.sys.entity;

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
 * @Description: 存货日结表
 * @author onlineGenerator
 * @date 2016-08-20 10:36:42
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_IC_SpeedBal", schema = "")
@SuppressWarnings("serial")
public class TScIcSpeedbalEntity implements java.io.Serializable {
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
	/**单据日期*/
	@Excel(name="单据日期")
	private java.util.Date date;
	/**单据类型*/
	@Excel(name="单据类型")
	private java.lang.String tranType;
	/**单据主键*/
	private java.lang.String billId;
	/**分录主键*/
	private java.lang.String billEntryId;
	/**单据创建日期*/
	@Excel(name="单据创建日期")
	private java.util.Date billCreateTime;
	/**商品id*/
	@Excel(name="商品id")
	private java.lang.String itemId;
	/**仓库id*/
	@Excel(name="仓库id")
	private java.lang.String stockId;
	/**批号*/
	@Excel(name="批号")
	private java.lang.String batchNo;
	/**数量*/
	@Excel(name="数量")
	private java.lang.Double qty;
	/**辅助数量*/
	@Excel(name="辅助数量")
	private java.lang.Double secQty;
	/**成本单价*/
	@Excel(name="成本单价")
	private java.lang.Double price;
	/**成本金额*/
	@Excel(name="成本金额")
	private java.lang.Double amount;
	/**结余单价*/
	@Excel(name="结余单价")
	private java.lang.Double ePrice;
	/**结余金额*/
	@Excel(name="结余金额")
	private java.lang.Double eAmount;
	/**差异金额*/
	@Excel(name="差异金额")
	private java.lang.Double diffAmount;
	/**源单类型*/
	@Excel(name="源单类型")
	private java.lang.String blidSrc;
	/**出入库标记*/
	@Excel(name="出入库标记")
	private java.lang.Integer flag;
	/**计算状态*/
	@Excel(name="计算状态")
	private java.lang.Integer status;
	/**负结余处理状态*/
	@Excel(name="负结余处理状态")
	private java.lang.Integer negFlag;

	private String itemNo;//商品编号
	private String itemName;//商品名称
	private String model;//规格
	private String barCode;//条形码

	private String stockName;//仓库
	private String unitName;//单位
	
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  单据日期
	 */
	@Column(name ="DATE",nullable=true,length=20)
	public java.util.Date getDate(){
		return this.date;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  单据日期
	 */
	public void setDate(java.util.Date date){
		this.date = date;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单据类型
	 */
	@Column(name ="TRANTYPE",nullable=true,length=32)
	public java.lang.String getTranType(){
		return this.tranType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据类型
	 */
	public void setTranType(java.lang.String tranType){
		this.tranType = tranType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单据主键
	 */
	@Column(name ="BILLID",nullable=true,length=32)
	public java.lang.String getBillId(){
		return this.billId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据主键
	 */
	public void setBillId(java.lang.String billId){
		this.billId = billId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分录主键
	 */
	@Column(name ="BILLENTRYID",nullable=true,length=32)
	public java.lang.String getBillEntryId(){
		return this.billEntryId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分录主键
	 */
	public void setBillEntryId(java.lang.String billEntryId){
		this.billEntryId = billEntryId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  单据创建日期
	 */
	@Column(name ="BILLCREATETIME",nullable=true,length=20)
	public java.util.Date getBillCreateTime(){
		return this.billCreateTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  单据创建日期
	 */
	public void setBillCreateTime(java.util.Date billCreateTime){
		this.billCreateTime = billCreateTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品id
	 */
	@Column(name ="ITEMID",nullable=true,length=32)
	public java.lang.String getItemId(){
		return this.itemId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品id
	 */
	public void setItemId(java.lang.String itemId){
		this.itemId = itemId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库id
	 */
	@Column(name ="STOCKID",nullable=true,length=32)
	public java.lang.String getStockId(){
		return this.stockId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库id
	 */
	public void setStockId(java.lang.String stockId){
		this.stockId = stockId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  批号
	 */
	@Column(name ="BATCHNO",nullable=true,length=100)
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
	 *@return: java.lang.Double  成本单价
	 */
	@Column(name ="PRICE",nullable=true,scale=10,length=20)
	public java.lang.Double getPrice(){
		return this.price;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  成本单价
	 */
	public void setPrice(java.lang.Double price){
		this.price = price;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  成本金额
	 */
	@Column(name ="AMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  成本金额
	 */
	public void setAmount(java.lang.Double amount){
		this.amount = amount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  结余单价
	 */
	@Column(name ="EPRICE",nullable=true,scale=10,length=20)
	public java.lang.Double getEPrice(){
		return this.ePrice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  结余单价
	 */
	public void setEPrice(java.lang.Double ePrice){
		this.ePrice = ePrice;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  结余金额
	 */
	@Column(name ="EAMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getEAmount(){
		return this.eAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  结余金额
	 */
	public void setEAmount(java.lang.Double eAmount){
		this.eAmount = eAmount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  差异金额
	 */
	@Column(name ="DIFFAMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getDiffAmount(){
		return this.diffAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  差异金额
	 */
	public void setDiffAmount(java.lang.Double diffAmount){
		this.diffAmount = diffAmount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单类型
	 */
	@Column(name ="BLID_SRC",nullable=true,length=32)
	public java.lang.String getBlidSrc(){
		return this.blidSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单类型
	 */
	public void setBlidSrc(java.lang.String blidSrc){
		this.blidSrc = blidSrc;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  出入库标记
	 */
	@Column(name ="FLAG",nullable=true,length=10)
	public java.lang.Integer getFlag(){
		return this.flag;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  出入库标记
	 */
	public void setFlag(java.lang.Integer flag){
		this.flag = flag;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  计算状态
	 */
	@Column(name ="STATUS",nullable=true,length=1)
	public java.lang.Integer getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  计算状态
	 */
	public void setStatus(java.lang.Integer status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  负结余处理状态
	 */
	@Column(name ="NEGFLAG",nullable=true,length=1)
	public java.lang.Integer getNegFlag(){
		return this.negFlag;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  负结余处理状态
	 */
	public void setNegFlag(java.lang.Integer negFlag){
		this.negFlag = negFlag;
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
}
