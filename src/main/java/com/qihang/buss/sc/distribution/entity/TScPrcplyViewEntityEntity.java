package com.qihang.buss.sc.distribution.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: v_prcply
 * @author onlineGenerator
 * @date 2016-07-29 11:15:00
 * @version V1.0   
 *
 */
@Entity
@Table(name = "v_sc_prcply", schema = "")
@SuppressWarnings("serial")
public class TScPrcplyViewEntityEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**子表主键*/
	private java.lang.String entryId;
	/**单据编号*/
	@Excel(name="单据编号")
	private java.lang.String billNo;
	/**单据日期*/
	@Excel(name="单据日期")
	private java.util.Date date;
	/**经办人*/
	private java.lang.String empId;
	/**部门*/
	private java.lang.String deptId;
	/**摘要*/
	@Excel(name="摘要")
	private java.lang.String explanation;
	/**制单人*/
	private java.lang.String billerId;
	/**审核人*/
	private java.lang.String checkerId;
	/**审核日期*/
	@Excel(name="审核日期")
	private java.util.Date checkDate;
	/**审核状态*/
	@Excel(name="审核状态")
	private java.lang.Integer checkState;
	/**作废标记*/
	@Excel(name="作废标记")
	private java.lang.Integer cancellation;
	/**分支机构*/
	private java.lang.String sonId;
	/**分录号*/
	@Excel(name="分录号")
	private java.lang.String indexNumber;
	/**商品*/
	private java.lang.String itemId;
	/**编号*/
	@Excel(name="商品编号")
	private java.lang.String number;
	/**名称*/
	@Excel(name="商品名称")
	private java.lang.String name;
	/**规格*/
	@Excel(name="规格")
	private java.lang.String model;
	/**条形码*/
	@Excel(name="条形码")
	private java.lang.String barCode;
	/**单位*/
	private java.lang.String unitId;
	/**数量段（从）*/
	@Excel(name="数量段（从）")
	private java.lang.Double begQty;
	/**数量段（至）*/
	@Excel(name="数量段（至）")
	private java.lang.Double endQty;
	/**原价*/
	@Excel(name="原价")
	private java.lang.Double price;
	/**新价*/
	@Excel(name="新价")
	private java.lang.Double newPrice;
	/**差价*/
	@Excel(name="差价")
	private java.lang.Double differPrice;
	/**折扣率*/
	@Excel(name="折扣率")
	private java.lang.Double discountrate;
	/**起始日期*/
	@Excel(name="起始日期")
	private java.util.Date begDate;
	/**结束日期*/
	@Excel(name="结束日期")
	private java.util.Date endDate;
	/**成本单价*/
	@Excel(name="成本单价")
	private java.lang.Double costPrice;
	/**原毛利率*/
	@Excel(name="原毛利率")
	private java.lang.Double grossper;
	/**新毛利率*/
	@Excel(name="新毛利率")
	private java.lang.Double newgrossper;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String note;
	@Excel(name="单位")
	private java.lang.String unitName;
	private java.lang.String goodsInterID;
//	@Excel(name="联系人")
//	private java.lang.String contact;
//	@Excel(name="手机号码")
//	private java.lang.String mobilePhone;
//	@Excel(name="电话")
//	private java.lang.String phone;
//	@Excel(name="传真")
//	private java.lang.String fax;
//	@Excel(name="地址")
//	private java.lang.String address;
//	private java.lang.String custInterId;
//	private java.lang.String custId;
//	private java.lang.String custNote;
	//
	@Excel(name="经办人")
	private java.lang.String empName;
	@Excel(name="部门")
	private java.lang.String deptName;
	@Excel(name="制单人")
	private java.lang.String billerName;
	@Excel(name="分支机构")
	private java.lang.String sonName;

	private Integer status;//单据状态
	private String auditDate;//审核日期
	@Excel(name="审核人")
	private String auditorName;//审核人

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	//@Id
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
	 *@return: java.lang.String  单据编号
	 */
	@Column(name ="BILLNO",nullable=true,length=50)
	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  单据日期
	 */
	@Column(name ="DATE",nullable=true)
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
	 *@return: java.lang.String  经办人
	 */
	@Column(name ="EMPID",nullable=true,length=50)
	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门
	 */
	@Column(name ="DEPTID",nullable=true,length=50)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  摘要
	 */
	@Column(name ="EXPLANATION",nullable=true,length=255)
	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  制单人
	 */
	@Column(name ="BILLERID",nullable=true,length=50)
	public String getBillerId() {
		return billerId;
	}

	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核人
	 */
	@Column(name ="CHECKERID",nullable=true,length=50)
	public String getCheckerId() {
		return checkerId;
	}

	public void setCheckerId(String checkerId) {
		this.checkerId = checkerId;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  审核日期
	 */
	@Column(name ="CHECKDATE",nullable=true)
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  审核状态
	 */
	@Column(name ="CHECKSTATE",nullable=true,length=10)
	public Integer getCheckState() {
		return checkState;
	}

	public void setCheckState(Integer checkState) {
		this.checkState = checkState;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  作废标记
	 */
	@Column(name ="CANCELLATION",nullable=true,length=10)
	public java.lang.Integer getCancellation(){
		return this.cancellation;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  作废标记
	 */
	public void setCancellation(java.lang.Integer cancellation){
		this.cancellation = cancellation;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分支机构
	 */
	@Column(name ="SONID",nullable=true,length=32)
	public String getSonId() {
		return sonId;
	}

	public void setSonId(String sonId) {
		this.sonId = sonId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分录号
	 */
	@Column(name ="INDEXNUMBER",nullable=true,length=50)
	public String getIndexNumber() {
		return indexNumber;
	}

	public void setIndexNumber(String indexNumber) {
		this.indexNumber = indexNumber;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品
	 */
	@Column(name ="ITEMID",nullable=true,length=50)
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  编号
	 */
	@Column(name ="NUMBER",nullable=true,length=80)
	public java.lang.String getNumber(){
		return this.number;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  编号
	 */
	public void setNumber(java.lang.String number){
		this.number = number;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="NAME",nullable=true,length=100)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  规格
	 */
	@Column(name ="MODEL",nullable=true,length=255)
	public java.lang.String getModel(){
		return this.model;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  规格
	 */
	public void setModel(java.lang.String model){
		this.model = model;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  条形码
	 */
	@Column(name ="BARCODE",nullable=true,length=50)
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */
	@Column(name ="UNITID",nullable=true,length=32)
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数量段（从）
	 */
	@Column(name ="BEGQTY",nullable=true,length=32)
	public Double getBegQty() {
		return begQty;
	}

	public void setBegQty(Double begQty) {
		this.begQty = begQty;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数量段（至）
	 */
	@Column(name ="ENDQTY",nullable=true,length=32)
	public Double getEndQty() {
		return endQty;
	}

	public void setEndQty(Double endQty) {
		this.endQty = endQty;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  原价
	 */
	@Column(name ="PRICE",nullable=true,length=32)
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  新价
	 */
	@Column(name ="NEWPRICE",nullable=true,length=32)
	public Double getNewPrice() {
		return newPrice;
	}

	public void setNewPrice(Double newPrice) {
		this.newPrice = newPrice;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  差价
	 */
	@Column(name ="DIFFERPRICE",nullable=true,length=32)
	public Double getDifferPrice() {
		return differPrice;
	}

	public void setDifferPrice(Double differPrice) {
		this.differPrice = differPrice;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  折扣率
	 */
	@Column(name ="DISCOUNTRATE",nullable=true,length=32)
	public Double getDiscountrate() {
		return discountrate;
	}

	public void setDiscountrate(Double discountrate) {
		this.discountrate = discountrate;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  起始日期
	 */
	@Column(name ="BEGDATE",nullable=true)
	public Date getBegDate() {
		return begDate;
	}

	public void setBegDate(Date begDate) {
		this.begDate = begDate;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  结束日期
	 */
	@Column(name ="ENDDATE",nullable=true)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  成本单价
	 */
	@Column(name ="COSTPRICE",nullable=true,length=32)
	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  原毛利率
	 */
	@Column(name ="GROSSPER",nullable=true,length=32)
	public Double getGrossper() {
		return grossper;
	}

	public void setGrossper(Double grossper) {
		this.grossper = grossper;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  新毛利率
	 */
	@Column(name ="NEWGROSSPER",nullable=true,length=32)
	public Double getNewgrossper() {
		return newgrossper;
	}

	public void setNewgrossper(Double newgrossper) {
		this.newgrossper = newgrossper;
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

	@Column(name ="unitName")
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	@Column(name ="goodsInterID")
	public String getGoodsInterID() {
		return goodsInterID;
	}

	public void setGoodsInterID(String goodsInterID) {
		this.goodsInterID = goodsInterID;
	}
//	@Column(name ="contact")
//	public String getContact() {
//		return contact;
//	}
//
//	public void setContact(String contact) {
//		this.contact = contact;
//	}
//	@Column(name ="mobilePhone")
//	public String getMobilePhone() {
//		return mobilePhone;
//	}
//
//	public void setMobilePhone(String mobilePhone) {
//		this.mobilePhone = mobilePhone;
//	}
//	@Column(name ="phone")
//	public String getPhone() {
//		return phone;
//	}
//
//	public void setPhone(String phone) {
//		this.phone = phone;
//	}
//	@Column(name ="fax")
//	public String getFax() {
//		return fax;
//	}
//
//	public void setFax(String fax) {
//		this.fax = fax;
//	}

//	@Column(name ="address")
//	public String getAddress() {
//		return address;
//	}
//
//	public void setAddress(String address) {
//		this.address = address;
//	}

//	@Column(name ="custInterId")
//	public String getCustInterId() {
//		return custInterId;
//	}
//
//	public void setCustInterId(String custInterId) {
//		this.custInterId = custInterId;
//	}

//	@Column(name ="custId")
//	public String getCustId() {
//		return custId;
//	}
//
//	public void setCustId(String custId) {
//		this.custId = custId;
//	}

//	@Column(name ="custNote")
//	public String getCustNote() {
//		return custNote;
//	}
//	public void setCustNote(String custNote) {
//		this.custNote = custNote;
//	}
	@Column(name ="empName")
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Column(name ="deptName")
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name ="billerName")
	public String getBillerName() {
		return billerName;
	}
	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}

	@Column(name ="sonName")
	public String getSonName() {
		return sonName;
	}
	public void setSonName(String sonName) {
		this.sonName = sonName;
	}

	@Transient
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}

	@Transient
	public String getAuditDate() {
		return auditDate;
	}
	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	@Transient
	public String getAuditorName() {
		return auditorName;
	}
	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	@Id
	@Column(name ="entryId")
	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}
}
