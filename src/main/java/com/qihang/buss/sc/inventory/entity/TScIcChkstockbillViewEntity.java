package com.qihang.buss.sc.inventory.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 盘点单
 * @author onlineGenerator
 * @date 2016-08-01 09:43:32
 * @version V1.0   
 *
 */
@Entity
@Table(name = "v_sc_ic_chkstockbill", schema = "")
@SuppressWarnings("serial")
public class TScIcChkstockbillViewEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**单据类型*/
	private String tranType;
	/**单据编号*/
  @Excel(name="单据编号")
	private String billNo;
	/**单据日期*/
  @Excel(name="单据日期")
	private Date date;
	/**仓库*/
  @Excel(name="仓库")
	private String stockId;
	/**经办人*/
  @Excel(name="经办人")
	private String empId;
	/**部门*/
  @Excel(name="部门")
	private String deptId;
	/**盘点日期*/
	private Date pdDate;
	/**盘点类型*/
  @Excel(name="盘点类型")
	private String chkType;
	/**制单人*/
  @Excel(name="制单人")
	private String billerId;
	/**审核人*/
  @Excel(name="审核人")
	private String checkerId;
	/**审核状态*/
	private String checkState;
	/**审核日期*/
  @Excel(name="审核日期")
	private String checkDate;
	/**作废标记*/
	private Integer cancellation;
	/**摘要*/
  @Excel(name="摘要")
	private String explanation;
	/**分支机构*/
	private String sonId;

	/**主键*/
	private java.lang.String entryId;
	/**商品*/
	@Excel(name="商品")
	private java.lang.String itemId;
	/**仓库*/
	@Excel(name="仓库")
	private java.lang.String entryStockId;
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

	private String stockName;//仓库名称
	private String empName;//经办人名称
	private String deptName;//部门名称
	private String billerName;//制单人
	private String checkerName;//审核人

	private String entryStockName;//明细仓库名称
	private String itemNo;//商品编号
	private String itemName;//商品名称
	private String model;//规格
	private String barCode;//条码
	private String unitName;//单位名称

	private String sonName;//分支机构

	private Integer isAuto;//盘点类型

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	//@Id
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
	 *@return: java.lang.String  单据类型
	 */

	@Column(name ="TRANTYPE",nullable=true,length=11)
	public String getTranType(){
		return this.tranType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据类型
	 */
	public void setTranType(String tranType){
		this.tranType = tranType;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单据编号
	 */

	@Column(name ="BILLNO",nullable=true,length=50)
	public String getBillNo(){
		return this.billNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据编号
	 */
	public void setBillNo(String billNo){
		this.billNo = billNo;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  单据日期
	 */

	@Column(name ="DATE",nullable=true,length=20)
	public Date getDate(){
		return this.date;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  单据日期
	 */
	public void setDate(Date date){
		this.date = date;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库
	 */

	@Column(name ="STOCKID",nullable=true,length=32)
	public String getStockId(){
		return this.stockId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库
	 */
	public void setStockId(String stockId){
		this.stockId = stockId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  经办人
	 */

	@Column(name ="EMPID",nullable=true,length=32)
	public String getEmpId(){
		return this.empId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  经办人
	 */
	public void setEmpId(String empId){
		this.empId = empId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门
	 */

	@Column(name ="DEPTID",nullable=true,length=32)
	public String getDeptId(){
		return this.deptId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门
	 */
	public void setDeptId(String deptId){
		this.deptId = deptId;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  盘点日期
	 */

	@Column(name ="PDDATE",nullable=true,length=32)
	public Date getPdDate(){
		return this.pdDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  盘点日期
	 */
	public void setPdDate(Date pdDate){
		this.pdDate = pdDate;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  盘点类型
	 */

	@Column(name ="CHKTYPE",nullable=true,length=32)
	public String getChkType(){
		return this.chkType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  盘点类型
	 */
	public void setChkType(String chkType){
		this.chkType = chkType;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  制单人
	 */

	@Column(name ="BILLERID",nullable=true,length=32)
	public String getBillerId(){
		return this.billerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  制单人
	 */
	public void setBillerId(String billerId){
		this.billerId = billerId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核人
	 */

	@Column(name ="CHECKERID",nullable=true,length=32)
	public String getCheckerId(){
		return this.checkerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核人
	 */
	public void setCheckerId(String checkerId){
		this.checkerId = checkerId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核状态
	 */

	@Column(name ="CHECKSTATE",nullable=true,length=32)
	public String getCheckState(){
		return this.checkState;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核状态
	 */
	public void setCheckState(String checkState){
		this.checkState = checkState;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核日期
	 */

	@Column(name ="CHECKDATE",nullable=true,length=32)
	public String getCheckDate(){
		return this.checkDate;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核日期
	 */
	public void setCheckDate(String checkDate){
		this.checkDate = checkDate;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  作废标记
	 */

	@Column(name ="CANCELLATION",nullable=true,length=1)
	public Integer getCancellation(){
		return this.cancellation;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  作废标记
	 */
	public void setCancellation(Integer cancellation){
		this.cancellation = cancellation;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  摘要
	 */

	@Column(name ="EXPLANATION",nullable=true,length=255)
	public String getExplanation(){
		return this.explanation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  摘要
	 */
	public void setExplanation(String explanation){
		this.explanation = explanation;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分支机构
	 */

	@Column(name ="SONID",nullable=true,length=32)
	public String getSonId(){
		return this.sonId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分支机构
	 */
	public void setSonId(String sonId){
		this.sonId = sonId;
	}

	@Id
	@Column(name ="entryId",nullable=true)
	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	@Column(name ="itemId",nullable=true)
	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	@Column(name ="entryStockId",nullable=true)
	public String getEntryStockId() {
		return entryStockId;
	}

	public void setEntryStockId(String entryStockId) {
		this.entryStockId = entryStockId;
	}

	@Column(name ="batchNo",nullable=true)
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name ="unitId",nullable=true)
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	@Column(name ="qty",nullable=true)
	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	@Column(name ="smallQty",nullable=true)
	public Double getSmallQty() {
		return smallQty;
	}

	public void setSmallQty(Double smallQty) {
		this.smallQty = smallQty;
	}

	@Column(name ="basicQty",nullable=true)
	public Double getBasicQty() {
		return basicQty;
	}

	public void setBasicQty(Double basicQty) {
		this.basicQty = basicQty;
	}

	@Column(name ="coefficient",nullable=true)
	public Double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}

	@Column(name ="chkQty",nullable=true)
	public Double getChkQty() {
		return chkQty;
	}

	public void setChkQty(Double chkQty) {
		this.chkQty = chkQty;
	}

	@Column(name ="chkSmallQty",nullable=true)
	public Double getChkSmallQty() {
		return chkSmallQty;
	}

	public void setChkSmallQty(Double chkSmallQty) {
		this.chkSmallQty = chkSmallQty;
	}

	@Column(name ="chkBasicQty",nullable=true)
	public Double getChkBasicQty() {
		return chkBasicQty;
	}

	public void setChkBasicQty(Double chkBasicQty) {
		this.chkBasicQty = chkBasicQty;
	}

	@Column(name ="diffQty",nullable=true)
	public Double getDiffQty() {
		return diffQty;
	}

	public void setDiffQty(Double diffQty) {
		this.diffQty = diffQty;
	}

	@Column(name ="costPrice",nullable=true)
	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	@Column(name ="amount",nullable=true)
	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name ="chkAmount",nullable=true)
	public Double getChkAmount() {
		return chkAmount;
	}

	public void setChkAmount(Double chkAmount) {
		this.chkAmount = chkAmount;
	}

	@Column(name ="diffAmount",nullable=true)
	public Double getDiffAmount() {
		return diffAmount;
	}

	public void setDiffAmount(Double diffAmount) {
		this.diffAmount = diffAmount;
	}

	@Column(name ="secUnitId",nullable=true)
	public String getSecUnitId() {
		return secUnitId;
	}

	public void setSecUnitId(String secUnitId) {
		this.secUnitId = secUnitId;
	}

	@Column(name ="basicUnitId",nullable=true)
	public String getBasicUnitId() {
		return basicUnitId;
	}

	public void setBasicUnitId(String basicUnitId) {
		this.basicUnitId = basicUnitId;
	}

	@Column(name ="secQty",nullable=true)
	public Double getSecQty() {
		return secQty;
	}

	public void setSecQty(Double secQty) {
		this.secQty = secQty;
	}

	@Column(name ="fid",nullable=true)
	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	@Column(name ="findex",nullable=true)
	public Integer getFindex() {
		return findex;
	}

	public void setFindex(Integer findex) {
		this.findex = findex;
	}

	@Column(name ="stockName",nullable=true)
	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	@Column(name ="empName",nullable=true)
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Column(name ="deptName",nullable=true)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name ="billerName",nullable=true)
	public String getBillerName() {
		return billerName;
	}

	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}

	@Column(name ="checkerName",nullable=true)
	public String getCheckerName() {
		return checkerName;
	}

	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}

	@Column(name ="entryStockName",nullable=true)
	public String getEntryStockName() {
		return entryStockName;
	}

	public void setEntryStockName(String entryStockName) {
		this.entryStockName = entryStockName;
	}

	@Column(name ="itemNo",nullable=true)
	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	@Column(name ="itemName",nullable=true)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name ="model",nullable=true)
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name ="barCode",nullable=true)
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	@Column(name ="unitName",nullable=true)
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	@Column(name ="sonName",nullable=true)
	public String getSonName() {
		return sonName;
	}

	public void setSonName(String sonName) {
		this.sonName = sonName;
	}
	@Column(name ="isAuto",nullable=true)
	public Integer getIsAuto() {
		return isAuto;
	}

	public void setIsAuto(Integer isAuto) {
		this.isAuto = isAuto;
	}
}
