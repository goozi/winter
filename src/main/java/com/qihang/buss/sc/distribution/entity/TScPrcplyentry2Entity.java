package com.qihang.buss.sc.distribution.entity;

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
 * @Description: 商品从表
 * @author onlineGenerator
 * @date 2016-07-25 16:19:59
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_PRCPLYENTRY2", schema = "")
@SuppressWarnings("serial")
public class TScPrcplyentry2Entity implements java.io.Serializable {
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
	/**分录号*/
	private java.lang.String indexNumber;
	/**商品*/
	@Excel(name="商品")
	private java.lang.String itemID;
	/**单位*/
	@Excel(name="单位")
	private java.lang.String unitID;
	/**数量段（从）*/
	@Excel(name="数量段（从）")
	private java.math.BigDecimal begQty;
	/**数量段（至）*/
	@Excel(name="数量段（至）")
	private java.math.BigDecimal endQty;
	/**原价*/
	@Excel(name="原价")
	private java.math.BigDecimal price;
	/**新价*/
	@Excel(name="新价")
	private java.math.BigDecimal newPrice;
	/**差价*/
	@Excel(name="差价")
	private java.math.BigDecimal differPrice;
	/**折扣率*/
	@Excel(name="折扣率")
	private java.math.BigDecimal disCountRate;
	/**起始日期*/
	@Excel(name="起始日期")
	private java.util.Date begDate;
	/**结束日期*/
	@Excel(name="结束日期")
	private java.util.Date endDate;
	/**成本单价*/
	@Excel(name="成本单价")
	private java.math.BigDecimal costPrice;
	/**原毛利率*/
	@Excel(name="原毛利率")
	private java.math.BigDecimal grossper;
	/**新毛利率*/
	@Excel(name="新毛利率")
	private java.math.BigDecimal newGrossper;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String note;
	/**主表主键*/
	private java.lang.String interID;
	/**是否禁用*/
	private java.lang.Integer disabled;
	/**是否删除*/
	private java.lang.Integer deleted;
	/**版本号*/
	private java.lang.Integer version;

	private java.lang.String itemNo;//商品编号
	private java.lang.String itemName;//商品名称
	private java.lang.String model;//规格
	private java.lang.String barCode;//条形码

	
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
	 *@return: java.lang.String  分录号
	 */
	@Column(name ="INDEXNUMBER",nullable=true,length=50)
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
	@Column(name ="ITEMID",nullable=true,length=50)
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
	@Column(name ="UNITID",nullable=true,length=50)
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
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  数量段（从）
	 */
	@Column(name ="BEGQTY",nullable=true,length=32)
	public java.math.BigDecimal getBegQty(){
		return this.begQty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  数量段（从）
	 */
	public void setBegQty(java.math.BigDecimal begQty){
		this.begQty = begQty;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  数量段（至）
	 */
	@Column(name ="ENDQTY",nullable=true,length=32)
	public java.math.BigDecimal getEndQty(){
		return this.endQty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  数量段（至）
	 */
	public void setEndQty(java.math.BigDecimal endQty){
		this.endQty = endQty;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  原价
	 */
	@Column(name ="PRICE",nullable=true,length=32)
	public java.math.BigDecimal getPrice(){
		return this.price;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  原价
	 */
	public void setPrice(java.math.BigDecimal price){
		this.price = price;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  新价
	 */
	@Column(name ="NEWPRICE",nullable=true,length=32)
	public java.math.BigDecimal getNewPrice(){
		return this.newPrice;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  新价
	 */
	public void setNewPrice(java.math.BigDecimal newPrice){
		this.newPrice = newPrice;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  差价
	 */
	@Column(name ="DIFFERPRICE",nullable=true,length=32)
	public java.math.BigDecimal getDifferPrice(){
		return this.differPrice;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  差价
	 */
	public void setDifferPrice(java.math.BigDecimal differPrice){
		this.differPrice = differPrice;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  折扣率
	 */
	@Column(name ="DISCOUNTRATE",nullable=true,length=32)
	public java.math.BigDecimal getDisCountRate(){
		return this.disCountRate;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  折扣率
	 */
	public void setDisCountRate(java.math.BigDecimal disCountRate){
		this.disCountRate = disCountRate;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  起始日期
	 */
	@Column(name ="BEGDATE",nullable=true,length=32)
	public java.util.Date getBegDate(){
		return this.begDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  起始日期
	 */
	public void setBegDate(java.util.Date begDate){
		this.begDate = begDate;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  结束日期
	 */
	@Column(name ="ENDDATE",nullable=true,length=32)
	public java.util.Date getEndDate(){
		return this.endDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  结束日期
	 */
	public void setEndDate(java.util.Date endDate){
		this.endDate = endDate;
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
	 *@return: java.math.BigDecimal  原毛利率
	 */
	@Column(name ="GROSSPER",nullable=true,length=32)
	public java.math.BigDecimal getGrossper(){
		return this.grossper;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  原毛利率
	 */
	public void setGrossper(java.math.BigDecimal grossper){
		this.grossper = grossper;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  新毛利率
	 */
	@Column(name ="NEWGROSSPER",nullable=true,length=32)
	public java.math.BigDecimal getNewGrossper(){
		return this.newGrossper;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  新毛利率
	 */
	public void setNewGrossper(java.math.BigDecimal newGrossper){
		this.newGrossper = newGrossper;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主表主键
	 */
	@Column(name ="INTERID",nullable=true,length=32)
	public java.lang.String getInterID(){
		return this.interID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主表主键
	 */
	public void setInterID(java.lang.String interID){
		this.interID = interID;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否禁用
	 */
	@Column(name ="DISABLED",nullable=true,length=32)
	public java.lang.Integer getDisabled(){
		return this.disabled;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  是否禁用
	 */
	public void setDisabled(java.lang.Integer disabled){
		this.disabled = disabled;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否删除
	 */
	@Column(name ="DELETED",nullable=true,length=32)
	public java.lang.Integer getDeleted(){
		return this.deleted;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  是否删除
	 */
	public void setDeleted(java.lang.Integer deleted){
		this.deleted = deleted;
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
}
