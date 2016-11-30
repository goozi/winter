package com.qihang.buss.sc.distribution.entity;


import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
/**   
 * @Title: Entity

 * @Description: v_sc_promotion
 * @author onlineGenerator
 * @date 2016-08-23 20:43:38
 * @version V1.0   
 *
 */
@Entity
@Table(name = "v_sc_promotion", schema = "")
@SuppressWarnings("serial")
public class TScPromotionViewEntity implements java.io.Serializable {
	/**主键*/
	private String entryId;
	/**id*/
	private String id;
	/**商品从表与主表关联ID*/
	private String goodsInterID;
	/**赠品从表与主表关联ID*/
	private String giftsInterID;
	/**单据编号*/
	@Excel(name="单据编号")
	private String billNo;
	/**单据日期*/
	@Excel(name="单据日期")
	private Date date;
	/**经办人ID*/
	private String empId;
	/**部门ID*/
	private String deptId;
	/**起始日期*/
	@Excel(name="起始日期")
	private Date begDate;
	/**结束日期*/
	@Excel(name="结束日期")
	private Date endDate;
	/**摘要*/
	@Excel(name="摘要")
	private String explanation;
	/**制单人*/
	private String billerId;
	/**审核状态*/
	@Excel(name="审核状态")
	private Integer checkState;
	/**审核ID*/
	private String checkerID;
	/**审核日期*/
	@Excel(name="审核日期")
	private Date checkDate;

	private Integer status;//单据状态
	private String auditDate;//审核日期
	@Excel(name="审核人")
	private String auditorName;//审核人

	/**作废标记*/
	@Excel(name="作废标记")
	private Integer cancellation;
	/**分支机构ID*/
	private String sonId;
	/**商品编号*/
	@Excel(name="商品编号")
	private String goodsNumber;
	/**商品名称*/
	@Excel(name="商品名称")
	private String goodsName;
	/**商品规格*/
	private String goodsModel;
	/**商品条形码*/
	@Excel(name="商品条形码")
	private String goodsBarcode;
	/**商品单位ID*/
	private String goodsUnitId;
	/**商品数量*/
	@Excel(name="数量")
	private BigDecimal goodsQty;
	/**商品备注*/
	@Excel(name="商品备注")
	private String goodsNote;
	/**商品ID*/
	private String goodsItemId;
	/**分录号*/
	@Excel(name="分录号")
	private Integer indexNumber;
	/**赠品编号*/
	@Excel(name="赠品编号")
	private String giftNumber;
	/**赠品名称*/
	@Excel(name="赠品名称")
	private String giftName;
	/**赠品规格*/
	@Excel(name="赠品规格")
	private String giftModel;
	/**条形码*/
	@Excel(name="条形码")
	private String giftBarCode;
	/**赠品单位ID*/
	private String giftUnitId;
	/**赠品数量*/
	@Excel(name="赠品数量")
	private Double giftQty;
	/**赠品备注*/
	@Excel(name="赠品备注")
	private String giftNote;
	/**赠品ID*/
	private String giftItemId;

	/**经办人*/
	@Excel(name="经办人")
	private String empName;
	/**部门*/
	@Excel(name="部门")
	private String deptName;
	/**制单人*/
	@Excel(name="制单人")
	private String billerName;
	/**分支机构*/
	@Excel(name="分支机构")
	private String sonName;
	/**商品单位名称*/
	@Excel(name="商品单位名称")
	private String goodsUnitName;
	/**赠品单位名称*/
	@Excel(name="赠品单位名称")
	private String giftUnitName;
	/**单据类型*/
	@Excel(name="单据类型")
	private String tranType;

	private Double coefficient;//换算率

	/**
	 *方法: 取得String
	 *@return: String  entryid
	 */
	@Id
	@Column(name ="ENTRYID",nullable=true,length=36)
	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	/**
	 *方法: 取得String
	 *@return: String  id
	 */
	@Column(name ="ID",nullable=false,length=36)
	public String getId(){
		return this.id;
	}

	/**
	 *方法: 设置String
	 *@param: String  id
	 */
	public void setId(String id){
		this.id = id;
	}

	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="BILLNO",nullable=true,length=50)
	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}

	/**
	 *方法: 取得Date
	 *@return: Date
	 */
	@Column(name ="DATE",nullable=true)
	public Date getDate(){
		return this.date;
	}

	/**
	 *方法: 设置Date
	 *@param: Date
	 */
	public void setDate(Date date){
		this.date = date;
	}

	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="EMPID",nullable=true,length=36)
	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="DEPTID",nullable=true,length=36)
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	/**
	 *方法: 取得Date
	 *@return: Date
	 */
	@Column(name ="BEGDATE",nullable=true)
	public Date getBegDate() {
		return begDate;
	}

	public void setBegDate(Date begDate) {
		this.begDate = begDate;
	}

	/**
	 *方法: 取得Date
	 *@return: Date
	 */
	@Column(name ="ENDDATE",nullable=true)
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="EXPLANATION",nullable=true,length=255)
	public String getExplanation(){
		return this.explanation;
	}

	/**
	 *方法: 设置String
	 *@param: String
	 */
	public void setExplanation(String explanation){
		this.explanation = explanation;
	}

	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="BILLERID",nullable=true,length=36)
	public String getBillerId() {
		return billerId;
	}

	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}


	/**
	 *方法: 取得Integer
	 *@return: Integer
	 */
	@Column(name ="CHECKSTATE",nullable=true,length=10)
	public Integer getCheckState() {
		return checkState;
	}

	public void setCheckState(Integer checkState) {
		this.checkState = checkState;
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

	/**
	 *方法: 取得Integer
	 *@return: Integer
	 */
	@Column(name ="CANCELLATION",nullable=true,length=10)
	public Integer getCancellation(){
		return this.cancellation;
	}

	/**
	 *方法: 设置Integer
	 *@param: Integer
	 */
	public void setCancellation(Integer cancellation){
		this.cancellation = cancellation;
	}

	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="SONID",nullable=true,length=36)
	public String getSonId() {
		return sonId;
	}

	public void setSonId(String sonId) {
		this.sonId = sonId;
	}


	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="GOODSNUMBER",nullable=true,length=80)
	public String getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="GOODSNAME",nullable=true,length=100)
	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="GOODSMODEL",nullable=true,length=255)
	public String getGoodsModel() {
		return goodsModel;
	}

	public void setGoodsModel(String goodsModel) {
		this.goodsModel = goodsModel;
	}

	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="GOODSBARCODE",nullable=true,length=50)
	public String getGoodsBarcode() {
		return goodsBarcode;
	}

	public void setGoodsBarcode(String goodsBarcode) {
		this.goodsBarcode = goodsBarcode;
	}

	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="GOODSUNITID",nullable=true,length=32)
	public String getGoodsUnitId() {
		return goodsUnitId;
	}

	public void setGoodsUnitId(String goodsUnitId) {
		this.goodsUnitId = goodsUnitId;
	}

	/**
	 *方法: 取得Double
	 *@return: Double
	 */
	@Column(name ="GOODSQTY",nullable=true,length=22)
	public BigDecimal getGoodsQty() {
		return goodsQty;
	}

	public void setGoodsQty(BigDecimal goodsQty) {
		this.goodsQty = goodsQty;
	}

	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="GOODSNOTE",nullable=true,length=255)
	public String getGoodsNote() {
		return goodsNote;
	}

	public void setGoodsNote(String goodsNote) {
		this.goodsNote = goodsNote;
	}

	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="GOODSITEMID",nullable=true,length=32)
	public String getGoodsItemId() {
		return goodsItemId;
	}

	public void setGoodsItemId(String goodsItemId) {
		this.goodsItemId = goodsItemId;
	}

	/**
	 *方法: 取得Integer
	 *@return: Integer
	 */
	@Column(name ="INDEXNUMBER",nullable=true,length=10)
	public Integer getIndexNumber() {
		return indexNumber;
	}

	public void setIndexNumber(Integer indexNumber) {
		this.indexNumber = indexNumber;
	}

	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="GIFTNUMBER",nullable=true,length=80)
	public String getGiftNumber() {
		return giftNumber;
	}

	public void setGiftNumber(String giftNumber) {
		this.giftNumber = giftNumber;
	}

	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="GIFTNAME",nullable=true,length=100)
	public String getGiftName() {
		return giftName;
	}

	public void setGiftName(String giftName) {
		this.giftName = giftName;
	}

	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="GIFTMODEL",nullable=true,length=255)
	public String getGiftModel() {
		return  giftModel;
	}

	public void setGiftModel(String giftModel) {
		this. giftModel = giftModel;
	}

	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="GIFTBARCODE",nullable=true,length=50)
	public String getGiftBarCode() {
		return giftBarCode;
	}

	public void setGiftBarCode(String giftBarCode) {
		this.giftBarCode = giftBarCode;
	}

	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="GIFTUNITID",nullable=true,length=36)
	public String getGiftUnitId() {
		return giftUnitId;
	}

	public void setGiftUnitId(String giftUnitId) {
		this.giftUnitId = giftUnitId;
	}

	/**
	 *方法: 取得Double
	 *@return: Double
	 */
	@Column(name ="GIFTQTY",nullable=true,length=22)
	public Double getGiftQty() {
		return giftQty;
	}

	public void setGiftQty(Double giftQty) {
		this.giftQty = giftQty;
	}

	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="GIFTNOTE",nullable=true,length=255)
	public String getGiftNote() {
		return giftNote;
	}

	public void setGiftNote(String giveNote) {
		this.giftNote = giveNote;
	}

	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="GIFTITEMID",nullable=true,length=36)
	public String getGiftItemId() {
		return giftItemId;
	}

	public void setGiftItemId(String giftItemId) {
		this.giftItemId = giftItemId;
	}

	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="EMPNAME",nullable=true,length=80)
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="DEPTNAME",nullable=true,length=80)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="BILLERNAME",nullable=true,length=50)
	public String getBillerName() {
		return billerName;
	}

	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}

	/**
	 *方法: 取得String
	 *@return: String
	 */
	@Column(name ="SONNAME",nullable=true,length=100)
	public String getSonName() {
		return sonName;
	}

	public void setSonName(String sonName) {
		this.sonName = sonName;
	}
	@Column(name ="checkerID")
	public String getCheckerID() {
		return checkerID;
	}

	public void setCheckerID(String checkerID) {
		this.checkerID = checkerID;
	}
	@Column(name ="checkDate")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}

	@Column(name ="goodsUnitName")
	public String getGoodsUnitName() {
		return goodsUnitName;
	}

	public void setGoodsUnitName(String goodsUnitName) {
		this.goodsUnitName = goodsUnitName;
	}
	@Column(name ="giftUnitName")
	public String getGiftUnitName() {
		return giftUnitName;
	}

	public void setGiftUnitName(String giftUnitName) {
		this.giftUnitName = giftUnitName;
	}
	@Column(name ="TranType")
	public String getTranType() {
		return tranType;
	}

	public void setTranType(String tranType) {
		this.tranType = tranType;
	}

	@Column(name ="goodsInterID")
	public String getGoodsInterID() {
		return goodsInterID;
	}

	public void setGoodsInterID(String goodsInterID) {
		this.goodsInterID = goodsInterID;
	}

	@Column(name ="giftsInterID")
	public String getGiftsInterID() {
		return giftsInterID;
	}

	public void setGiftsInterID(String giftsInterID) {
		this.giftsInterID = giftsInterID;
	}

	@Column(name ="coefficient")
	public Double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}
}
