package com.qihang.buss.sc.distribution.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

/**   
 * @Title: Entity
 * @Description: 赠品信息
 * @author onlineGenerator
 * @date 2016-08-22 17:49:49
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_PromotionGiftsEntry", schema = "")
@SuppressWarnings("serial")
public class TScPromotiongiftsentryEntity implements java.io.Serializable {
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
	/**买一赠一促销单ID*/
	private java.lang.String interID;
	/**分录号*/
	private java.lang.Integer indexNumber;
	/**赠品ID*/
	@Excel(name="赠品ID")
	private java.lang.String itemID;

	/**赠品编号*/
	@Excel(name="赠品编号")
	private java.lang.String giftNumber;

	/**赠品名称*/
	@Excel(name="赠品名称")
	private java.lang.String giftName;

	/**规格*/
	@Excel(name="规格")
	private java.lang.String giftModel;

	/**条形码*/
	@Excel(name="条形码")
	private java.lang.String giftBarCode;
	/**单位名称*/
	@Excel(name="单位名称")
	private java.lang.String unitName;

	/**单位*/
	@Excel(name="单位")
	private java.lang.String unitID;
	/**数量*/
	@Excel(name="数量")
	private java.math.BigDecimal qty;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String note;
	/**版本号*/
	private java.lang.Integer version;
	/**是否禁用*/
	private java.lang.Integer disabled;
	/**是否删除*/
	private java.lang.Integer deleted;

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
	 *@return: java.lang.String  买一赠一促销单ID
	 */
	@Column(name ="INTERID",nullable=true,length=36)
	public java.lang.String getInterID(){
		return this.interID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  买一赠一促销单ID
	 */
	public void setInterID(java.lang.String interID){
		this.interID = interID;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  分录号
	 */
	@Column(name ="INDEXNUMBER",nullable=true,length=32)
	public Integer getIndexNumber() {
		return indexNumber;
	}

	public void setIndexNumber(Integer indexNumber) {
		this.indexNumber = indexNumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String
	 */
	@Column(name ="ITEMID",nullable=true,length=36)
	public java.lang.String getItemID(){
		return this.itemID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String
	 */
	public void setItemID(java.lang.String itemID){
		this.itemID = itemID;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String
	 */
	@Column(name ="UNITID",nullable=true,length=36)
	public java.lang.String getUnitID(){
		return this.unitID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String
	 */
	public void setUnitID(java.lang.String unitID){
		this.unitID = unitID;
	}

	@Column(name ="qty")
	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}


	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String
	 */
	@Column(name ="NOTE",nullable=true,length=255)
	public java.lang.String getNote(){
		return this.note;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String
	 */
	public void setNote(java.lang.String note){
		this.note = note;
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

	@Transient
	public java.lang.String getGiftNumber() {
		return this.giftNumber;
	}

	public void setGiftNumber(java.lang.String giftNumber) {
		this.giftNumber = giftNumber;
	}

	@Transient
	public java.lang.String getGiftName() {
		return this.giftName;
	}

	public void setGiftName(java.lang.String giftName) {
		this.giftName = giftName;
	}

	@Transient
	public java.lang.String getGiftModel() {
		return this.giftModel;
	}

	public void setGiftModel(java.lang.String giftModel) {
		this.giftModel = giftModel;
	}

	@Transient
	public java.lang.String getGiftBarCode() {
		return this.giftBarCode;
	}

	public void setGiftBarCode(java.lang.String giftBarCode) {
		this.giftBarCode = giftBarCode;
	}

	@Transient
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

}
