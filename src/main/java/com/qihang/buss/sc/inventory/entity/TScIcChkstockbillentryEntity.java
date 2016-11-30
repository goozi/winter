package com.qihang.buss.sc.inventory.entity;

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
 * @Description: 盘点单明细
 * @author onlineGenerator
 * @date 2016-08-01 09:43:32
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_IC_ChkStockBillEntry", schema = "")
@SuppressWarnings("serial")
public class TScIcChkstockbillentryEntity implements java.io.Serializable {
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
	/**账面箱数*/
	@Excel(name="账面箱数")
	private java.lang.Double qty;
	/**账面散数*/
	@Excel(name="账面散数")
	private java.lang.Double smallQty;
	/**账面数量*/
	@Excel(name="账面数量")
	private java.lang.Double basicQty;
	/**换算率*/
	@Excel(name="换算率")
	private java.lang.Double coefficient;
	/**箱数*/
	@Excel(name="箱数")
	private java.lang.Double chkQty;
	/**散数*/
	@Excel(name="散数")
	private java.lang.Double chkSmallQty;
	/**盘点数量*/
	@Excel(name="盘点数量")
	private java.lang.Double chkBasicQty;
	/**差异数量*/
	@Excel(name="差异数量")
	private java.lang.Double diffQty;
	/**成本单价*/
	@Excel(name="成本单价")
	private java.lang.Double costPrice;
	/**账面金额*/
	@Excel(name="账面金额")
	private java.lang.Double amount;
	/**盘点金额*/
	@Excel(name="盘点金额")
	private java.lang.Double chkAmount;
	/**差异金额*/
	@Excel(name="差异金额")
	private java.lang.Double diffAmount;
	/**辅助单位*/
	private java.lang.String secUnitId;
	/**基本单位*/
	private java.lang.String basicUnitId;
	/**账面辅助数量*/
	private java.lang.Double secQty;
	/**父id*/
	private java.lang.String fid;
	/**分录号*/
	private java.lang.Integer findex;

	private String idSrc;//源单id

	private String note;

	private String itemNo;
	private String itemName;
	private String model;
	private String barCode;
	private String stockName;
	
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
	 *@return: java.lang.Double  账面箱数
	 */
	@Column(name ="QTY",nullable=true,scale=10,length=20)
	public java.lang.Double getQty(){
		return this.qty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  账面箱数
	 */
	public void setQty(java.lang.Double qty){
		this.qty = qty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  账面散数
	 */
	@Column(name ="SMALLQTY",nullable=true,scale=10,length=20)
	public java.lang.Double getSmallQty(){
		return this.smallQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  账面散数
	 */
	public void setSmallQty(java.lang.Double smallQty){
		this.smallQty = smallQty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  账面数量
	 */
	@Column(name ="BASICQTY",nullable=true,scale=10,length=20)
	public java.lang.Double getBasicQty(){
		return this.basicQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  账面数量
	 */
	public void setBasicQty(java.lang.Double basicQty){
		this.basicQty = basicQty;
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
	 *@return: java.lang.Double  箱数
	 */
	@Column(name ="CHKQTY",nullable=true,scale=10,length=20)
	public java.lang.Double getChkQty(){
		return this.chkQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  箱数
	 */
	public void setChkQty(java.lang.Double chkQty){
		this.chkQty = chkQty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  散数
	 */
	@Column(name ="CHKSMALLQTY",nullable=true,scale=10,length=20)
	public java.lang.Double getChkSmallQty(){
		return this.chkSmallQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  散数
	 */
	public void setChkSmallQty(java.lang.Double chkSmallQty){
		this.chkSmallQty = chkSmallQty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  盘点数量
	 */
	@Column(name ="CHKBASICQTY",nullable=true,scale=10,length=20)
	public java.lang.Double getChkBasicQty(){
		return this.chkBasicQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  盘点数量
	 */
	public void setChkBasicQty(java.lang.Double chkBasicQty){
		this.chkBasicQty = chkBasicQty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  差异数量
	 */
	@Column(name ="DIFFQTY",nullable=true,scale=10,length=20)
	public java.lang.Double getDiffQty(){
		return this.diffQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  差异数量
	 */
	public void setDiffQty(java.lang.Double diffQty){
		this.diffQty = diffQty;
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
	 *@return: java.lang.Double  账面金额
	 */
	@Column(name ="AMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  账面金额
	 */
	public void setAmount(java.lang.Double amount){
		this.amount = amount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  盘点金额
	 */
	@Column(name ="CHKAMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getChkAmount(){
		return this.chkAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  盘点金额
	 */
	public void setChkAmount(java.lang.Double chkAmount){
		this.chkAmount = chkAmount;
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
	 *@return: java.lang.Double  账面辅助数量
	 */
	@Column(name ="SECQTY",nullable=true,scale=10,length=20)
	public java.lang.Double getSecQty(){
		return this.secQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  账面辅助数量
	 */
	public void setSecQty(java.lang.Double secQty){
		this.secQty = secQty;
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  分录号
	 */
	@Column(name ="FINDEX",nullable=true,length=11)
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

	@Column(name ="note",nullable=true)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name ="idSrc",nullable=true)
	public String getIdSrc() {
		return idSrc;
	}

	public void setIdSrc(String idSrc) {
		this.idSrc = idSrc;
	}
}
