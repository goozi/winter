package com.qihang.buss.sc.sysaudit.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.xml.soap.Text;
import java.sql.Blob;
import java.util.List;

import com.qihang.winter.core.common.entity.IdEntity;
import org.hibernate.annotations.GenericGenerator;
import com.qihang.winter.poi.excel.annotation.Excel;

/**
 * @Title: Entity
 * @Description: 单据类型设置
 * @author onlineGenerator
 * @date 2016-06-21 16:00:41
 * @version V1.0
 *
 */
@Entity
@Table(name = "T_S_Bill_Info", schema = "")
@org.hibernate.annotations.Proxy(lazy = false)
@SuppressWarnings("serial")
public class TSBillInfoEntity extends IdEntity implements java.io.Serializable {

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
	/**单据名称*/
	@Excel(name="单据名称")
	private java.lang.String billName;
	/**单据类型*/
	@Excel(name="单据类型")
	private java.lang.String billId;
	/**父id*/
	private TSBillInfoEntity TSBillInfoEntity;
	/**前缀*/
	@Excel(name="前缀")
	private java.lang.String prefix;
	/**日期格式*/
	@Excel(name="日期格式")
	private java.lang.String dateFormatter;
	/**流水号长度*/
	@Excel(name="流水号长度")
	private java.lang.Integer billNoLen;
	/**允许手工编辑单据号*/
	@Excel(name="允许手工编辑单据号")
	private java.lang.Integer isEdit;
	/**允许断号*/
	@Excel(name="允许断号")
	private java.lang.Integer isOffOn;
	/**流水号归零*/
	@Excel(name="流水号归零")
	private java.lang.Integer backZero;
	/**单据数量*/
	private java.lang.Integer billNo;

	private String priceField;//单价字段

	private Integer isAudit;//是否审核
	private Integer isPrice;//是否单价设置


	private List<TSBillInfoEntity> TSBillInfoEntitys = new ArrayList<TSBillInfoEntity>();

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
	 *@return: java.lang.String  单据名称
	 */
	@Column(name ="BILL_NAME",nullable=true,length=100)
	public java.lang.String getBillName(){
		return this.billName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据名称
	 */
	public void setBillName(java.lang.String billName){
		this.billName = billName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单据类型
	 */
	@Column(name ="BILL_ID",nullable=true,length=32)
	public java.lang.String getBillId(){
		return this.billId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据类型
	 */
	public void setBillId(java.lang.String billId){
		this.billId = billId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  父id
	 */
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "pid")
	public com.qihang.buss.sc.sysaudit.entity.TSBillInfoEntity getTSBillInfoEntity() {
		return TSBillInfoEntity;
	}

	public void setTSBillInfoEntity(com.qihang.buss.sc.sysaudit.entity.TSBillInfoEntity TSBillInfoEntity) {
		this.TSBillInfoEntity = TSBillInfoEntity;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  前缀
	 */
	@Column(name ="PREFIX",nullable=true,length=32)
	public java.lang.String getPrefix(){
		return this.prefix;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  前缀
	 */
	public void setPrefix(java.lang.String prefix){
		this.prefix = prefix;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  日期格式
	 */
	@Column(name ="DATE_FORMATTER",nullable=true,length=32)
	public java.lang.String getDateFormatter(){
		return this.dateFormatter;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  日期格式
	 */
	public void setDateFormatter(java.lang.String dateFormatter){
		this.dateFormatter = dateFormatter;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  流水号长度
	 */
	@Column(name ="BILL_NO_LEN",nullable=true,length=32)
	public java.lang.Integer getBillNoLen(){
		return this.billNoLen;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  流水号长度
	 */
	public void setBillNoLen(java.lang.Integer billNoLen){
		this.billNoLen = billNoLen;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  允许手工编辑单据号
	 */
	@Column(name ="IS_EDIT",nullable=true,length=1)
	public java.lang.Integer getIsEdit(){
		return this.isEdit;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  允许手工编辑单据号
	 */
	public void setIsEdit(java.lang.Integer isEdit){
		this.isEdit = isEdit;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  允许断号
	 */
	@Column(name ="IS_OFF_ON",nullable=true,length=1)
	public java.lang.Integer getIsOffOn(){
		return this.isOffOn;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  允许断号
	 */
	public void setIsOffOn(java.lang.Integer isOffOn){
		this.isOffOn = isOffOn;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  流水号归零
	 */
	@Column(name ="BACK_ZERO",nullable=true,length=1)
	public java.lang.Integer getBackZero(){
		return this.backZero;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  流水号归零
	 */
	public void setBackZero(java.lang.Integer backZero){
		this.backZero = backZero;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  单据数量
	 */
	@Column(name ="BILL_NO",nullable=true,length=10)
	public java.lang.Integer getBillNo(){
		return this.billNo;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  单据数量
	 */
	public void setBillNo(java.lang.Integer billNo){
		this.billNo = billNo;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "TSBillInfoEntity")
	public List<TSBillInfoEntity> getTSBillInfoEntitys() {
		return TSBillInfoEntitys;
	}

	public void setTSBillInfoEntitys(List<TSBillInfoEntity> TSBillInfoEntitys) {
		this.TSBillInfoEntitys = TSBillInfoEntitys;
	}

	@Column(name ="PRICE_FIELD",nullable=true,length=10)
	public String getPriceField() {
		return priceField;
	}

	public void setPriceField(String priceField) {
		this.priceField = priceField;
	}

	@Column(name ="isAudit",nullable=true)
	public Integer getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(Integer isAudit) {
		this.isAudit = isAudit;
	}

	@Column(name ="isPrice",nullable=true)
	public Integer getIsPrice() {
		return isPrice;
	}

	public void setIsPrice(Integer isPrice) {
		this.isPrice = isPrice;
	}
}
