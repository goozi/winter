package com.qihang.buss.sc.distribution.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: v_sc_drp_rstockbill
 * @author onlineGenerator
 * @date 2016-09-08 17:52:33
 * @version V1.0   
 *
 */
@Entity
@Table(name = "v_sc_drp_rstockbill", schema = "")
@SuppressWarnings("serial")
public class TScDrpRstockbillViewEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**子表主键*/
	private java.lang.String entryId;
	/**单据类型*/
	@Excel(name="单据类型")
	private java.lang.String tranType;
	/**单据日期*/
	@Excel(name="单据日期")
	private java.util.Date date;
	/**单据编号*/
	@Excel(name="单据编号")
	private java.lang.String billNo;
	/**上游经销商或总部*/
	@Excel(name="上游经销商或总部")
	private java.lang.String itemId;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String itemName;
	/**联系人*/
	@Excel(name="联系人")
	private java.lang.String contact;
	/**手机号码*/
	@Excel(name="手机号码")
	private java.lang.String mobilePhone;
	/**电话*/
	@Excel(name="电话")
	private java.lang.String phone;
	/**传真*/
	@Excel(name="传真")
	private java.lang.String fax;
	/**联系地址*/
	@Excel(name="联系地址")
	private java.lang.String address;
	/**经办人*/
	@Excel(name="经办人")
	private java.lang.String empId;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String empName;
	/**部门*/
	@Excel(name="部门")
	private java.lang.String deptId;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String deptName;
	/**仓库*/
	@Excel(name="仓库")
	private java.lang.String rStockId;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String rStockName;
	/**物流费用*/
	@Excel(name="物流费用")
	private java.lang.Double freight;
	/**单据金额*/
	@Excel(name="单据金额")
	private java.lang.Double amount;
	/**应收金额*/
	@Excel(name="应收金额")
	private java.lang.Double allAmount;
	/**重量*/
	@Excel(name="重量")
	private java.lang.Double weight;
	/**源单类型*/
	@Excel(name="源单类型")
	private java.lang.String rClassidSrc;
	/**源单主键*/
	@Excel(name="源单主键")
	private java.lang.String rInteridSrc;
	/**源单编号*/
	@Excel(name="源单编号")
	private java.lang.String rBillnoSrc;
	/**审核人*/
	@Excel(name="审核人")
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
	/**摘要*/
	@Excel(name="摘要")
	private java.lang.String explanation;
	/**分支机构*/
	@Excel(name="分支机构")
	private java.lang.String sonId;
	/**sonname*/
	@Excel(name="sonname")
	private java.lang.String sonName;
	/**分录号*/
	@Excel(name="分录号")
	private java.lang.String indexNumber;
	/**仓库*/
	@Excel(name="仓库")
	private java.lang.String stockId;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String stockName;
	/**商品*/
	@Excel(name="商品")
	private java.lang.String goodsItemId;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String goodsName;
	/**编号*/
	@Excel(name="编号")
	private java.lang.String number;
	/**规格*/
	@Excel(name="规格")
	private java.lang.String model;
	/**条形码*/
	@Excel(name="条形码")
	private java.lang.String barCode;
	/**单位*/
	@Excel(name="单位")
	private java.lang.String unitId;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String unitName;
	/**辅助单位*/
	@Excel(name="辅助单位")
	private java.lang.String secUnitId;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String secUnitName;
	/**基本单位*/
	@Excel(name="基本单位")
	private java.lang.String basicUnitId;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String basicUnitName;
	/**批号*/
	@Excel(name="批号")
	private java.lang.String batchNo;
	/**生产日期*/
	@Excel(name="生产日期")
	private java.util.Date kfDate;
	/**保质期*/
	@Excel(name="保质期")
	private java.lang.String kfPeriod;
	/**有效期至*/
	@Excel(name="有效期至")
	private java.util.Date periodDate;
	/**保质期类型*/
	@Excel(name="保质期类型")
	private java.lang.String kfDateType;
	/**数量*/
	@Excel(name="数量")
	private java.lang.Double qty;
	/**辅助换算率*/
	@Excel(name="辅助换算率")
	private java.lang.Double seccoefficient;
	/**辅助数量*/
	@Excel(name="辅助数量")
	private java.lang.Double secQty;
	/**换算率*/
	@Excel(name="换算率")
	private java.lang.Double coefficient;
	/**基本数量*/
	@Excel(name="基本数量")
	private java.lang.Double basicQty;
	/**不含税单价*/
	@Excel(name="不含税单价")
	private java.lang.Double price;
	/**不含税金额*/
	@Excel(name="不含税金额")
	private java.lang.Double rbAmount;
	/**折扣率(%)*/
	@Excel(name="折扣率(%)")
	private java.lang.Double discountRate;
	/**不含税折后单价*/
	@Excel(name="不含税折后单价")
	private java.lang.Double discountPrice;
	/**不含税折后金额*/
	@Excel(name="不含税折后金额")
	private java.lang.Double discountAmount;
	/**税率(%)*/
	@Excel(name="税率(%)")
	private java.lang.Double taxRate;
	/**单价*/
	@Excel(name="单价")
	private java.lang.Double taxPriceEx;
	/**金额*/
	@Excel(name="金额")
	private java.lang.Double taxAmountEx;
	/**折后单价*/
	@Excel(name="折后单价")
	private java.lang.Double taxPrice;
	/**折后金额*/
	@Excel(name="折后金额")
	private java.lang.Double inTaxAmount;
	/**税额*/
	@Excel(name="税额")
	private java.lang.Double taxAmount;
	/**源单主键*/
	@Excel(name="源单主键")
	private java.lang.String interIdSrc;
	/**源单编号*/
	@Excel(name="源单编号")
	private java.lang.String billNoSrc;
	/**源单分录主键*/
	@Excel(name="源单分录主键")
	private java.lang.String idSrc;
	/**订单主键*/
	@Excel(name="订单主键")
	private java.lang.String interIdOrder;
	/**订单编号*/
	@Excel(name="订单编号")
	private java.lang.String billNoOrder;
	/**订单分录主键*/
	@Excel(name="订单分录主键")
	private java.lang.String idOrder;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String note;

	private Integer status;//单据状态
	private String auditDate;//审核日期
	@Excel(name="审核人")
	private String auditorName;//审核人
	private String billerId;
	@Excel(name="制单人")
	private String billerName;

	private String goodsWeight;
	private String rbillId;
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
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
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单据类型
	 */
	@Column(name ="TRANTYPE",nullable=true,length=32)
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
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
	 *@return: java.lang.String  单据编号
	 */
	@Column(name ="BILLNO",nullable=true,length=32)
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  上游经销商或总部
	 */
	@Column(name ="ITEMID",nullable=true,length=32)
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="ITEMNAME",nullable=true,length=150)
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人
	 */
	@Column(name ="CONTACT",nullable=true,length=32)
	public java.lang.String getContact(){
		return this.contact;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系人
	 */
	public void setContact(java.lang.String contact){
		this.contact = contact;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机号码
	 */
	@Column(name ="MOBILEPHONE",nullable=true,length=32)
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话
	 */
	@Column(name ="PHONE",nullable=true,length=32)
	public java.lang.String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电话
	 */
	public void setPhone(java.lang.String phone){
		this.phone = phone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  传真
	 */
	@Column(name ="FAX",nullable=true,length=32)
	public java.lang.String getFax(){
		return this.fax;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  传真
	 */
	public void setFax(java.lang.String fax){
		this.fax = fax;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系地址
	 */
	@Column(name ="ADDRESS",nullable=true,length=32)
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系地址
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  经办人
	 */
	@Column(name ="EMPID",nullable=true,length=32)
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="EMPNAME",nullable=true,length=80)
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门
	 */
	@Column(name ="DEPTID",nullable=true,length=32)
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="DEPTNAME",nullable=true,length=80)
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库
	 */
	@Column(name ="RSTOCKID",nullable=true,length=32)
	public String getrStockId() {
		return rStockId;
	}
	public void setrStockId(String rStockId) {
		this.rStockId = rStockId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="RSTOCKNAME",nullable=true,length=80)
	public String getrStockName() {
		return rStockName;
	}
	public void setrStockName(String rStockName) {
		this.rStockName = rStockName;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  物流费用
	 */
	@Column(name ="FREIGHT",nullable=true,length=22)
	public java.lang.Double getFreight(){
		return this.freight;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  物流费用
	 */
	public void setFreight(java.lang.Double freight){
		this.freight = freight;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  单据金额
	 */
	@Column(name ="AMOUNT",nullable=true,length=22)
	public java.lang.Double getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  单据金额
	 */
	public void setAmount(java.lang.Double amount){
		this.amount = amount;
	}

	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  应收金额
	 */
	@Column(name ="ALLAMOUNT",nullable=true,length=22)
	public Double getAllAmount() {
		return allAmount;
	}
	public void setAllAmount(Double allAmount) {
		this.allAmount = allAmount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  重量
	 */
	@Column(name ="WEIGHT",nullable=true,length=22)
	public java.lang.Double getWeight(){
		return this.weight;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  重量
	 */
	public void setWeight(java.lang.Double weight){
		this.weight = weight;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单类型
	 */
	@Column(name ="R_CLASSID_SRC",nullable=true,length=32)
	public java.lang.String getRClassidSrc(){
		return this.rClassidSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单类型
	 */
	public void setRClassidSrc(java.lang.String rClassidSrc){
		this.rClassidSrc = rClassidSrc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单主键
	 */
	@Column(name ="R_INTERID_SRC",nullable=true,length=32)
	public java.lang.String getRInteridSrc(){
		return this.rInteridSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单主键
	 */
	public void setRInteridSrc(java.lang.String rInteridSrc){
		this.rInteridSrc = rInteridSrc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单编号
	 */
	@Column(name ="R_BILLNO_SRC",nullable=true,length=32)
	public java.lang.String getRBillnoSrc(){
		return this.rBillnoSrc;
	}
	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单编号
	 */
	public void setRBillnoSrc(java.lang.String rBillnoSrc){
		this.rBillnoSrc = rBillnoSrc;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核人
	 */
	@Column(name ="CHECKERID",nullable=true,length=32)
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
	 *@return: java.lang.String  摘要
	 */
	@Column(name ="EXPLANATION",nullable=true)
	public java.lang.String getExplanation(){
		return this.explanation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  摘要
	 */
	public void setExplanation(java.lang.String explanation){
		this.explanation = explanation;
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
	 *@return: java.lang.String  sonname
	 */
	@Column(name ="SONNAME",nullable=true,length=100)
	public String getSonName() {
		return sonName;
	}
	public void setSonName(String sonName) {
		this.sonName = sonName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分录号
	 */
	@Column(name ="INDEXNUMBER",nullable=true,length=32)
	public String getIndexNumber() {
		return indexNumber;
	}
	public void setIndexNumber(String indexNumber) {
		this.indexNumber = indexNumber;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库
	 */
	@Column(name ="STOCKID",nullable=true,length=32)
	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="STOCKNAME",nullable=true,length=80)
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品
	 */
	@Column(name ="GOODSITEMID",nullable=true,length=32)
	public String getGoodsItemId() {
		return goodsItemId;
	}
	public void setGoodsItemId(String goodsItemId) {
		this.goodsItemId = goodsItemId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="GOODSNAME",nullable=true,length=100)
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  编号
	 */
	@Column(name ="NUMBER",nullable=true,length=80)
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
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
	 *@return: java.lang.String  名称
	 */
	@Column(name ="UNITNAME",nullable=true,length=80)
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  辅助单位
	 */
	@Column(name ="SECUNITID",nullable=true,length=32)
	public String getSecUnitId() {
		return secUnitId;
	}
	public void setSecUnitId(String secUnitId) {
		this.secUnitId = secUnitId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="SECUNITNAME",nullable=true,length=80)
	public String getSecUnitName() {
		return secUnitName;
	}
	public void setSecUnitName(String secUnitName) {
		this.secUnitName = secUnitName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  基本单位
	 */
	@Column(name ="BASICUNITID",nullable=true,length=32)
	public String getBasicUnitId() {
		return basicUnitId;
	}
	public void setBasicUnitId(String basicUnitId) {
		this.basicUnitId = basicUnitId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="BASICUNITNAME",nullable=true,length=80)
	public String getBasicUnitName() {
		return basicUnitName;
	}
	public void setBasicUnitName(String basicUnitName) {
		this.basicUnitName = basicUnitName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  批号
	 */
	@Column(name ="BATCHNO",nullable=true,length=32)
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  生产日期
	 */
	@Column(name ="KFDATE",nullable=true)
	public Date getKfDate() {
		return kfDate;
	}
	public void setKfDate(Date kfDate) {
		this.kfDate = kfDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  保质期
	 */
	@Column(name ="KFPERIOD",nullable=true,length=32)
	public String getKfPeriod() {
		return kfPeriod;
	}
	public void setKfPeriod(String kfPeriod) {
		this.kfPeriod = kfPeriod;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  有效期至
	 */
	@Column(name ="PERIODDATE",nullable=true)
	public Date getPeriodDate() {
		return periodDate;
	}
	public void setPeriodDate(Date periodDate) {
		this.periodDate = periodDate;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  保质期类型
	 */
	@Column(name ="KFDATETYPE",nullable=true,length=32)
	public String getKfDateType() {
		return kfDateType;
	}
	public void setKfDateType(String kfDateType) {
		this.kfDateType = kfDateType;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  数量
	 */
	@Column(name ="QTY",nullable=true,length=22)
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
	 *@return: java.lang.Double  辅助换算率
	 */
	@Column(name ="SECCOEFFICIENT",nullable=true,length=22)
	public java.lang.Double getSeccoefficient(){
		return this.seccoefficient;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  辅助换算率
	 */
	public void setSeccoefficient(java.lang.Double seccoefficient){
		this.seccoefficient = seccoefficient;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  辅助数量
	 */
	@Column(name ="SECQTY",nullable=true,length=22)
	public Double getSecQty() {
		return secQty;
	}
	public void setSecQty(Double secQty) {
		this.secQty = secQty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  换算率
	 */
	@Column(name ="COEFFICIENT",nullable=true,length=22)
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
	@Column(name ="BASICQTY",nullable=true,length=22)
	public Double getBasicQty() {
		return basicQty;
	}
	public void setBasicQty(Double basicQty) {
		this.basicQty = basicQty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  不含税单价
	 */
	@Column(name ="PRICE",nullable=true,length=22)
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
	@Column(name ="RBAMOUNT",nullable=true,length=22)
	public Double getRbAmount() {
		return rbAmount;
	}
	public void setRbAmount(Double rbAmount) {
		this.rbAmount = rbAmount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  折扣率(%)
	 */
	@Column(name ="DISCOUNTRATE",nullable=true,length=22)
	public Double getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(Double discountRate) {
		this.discountRate = discountRate;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  不含税折后单价
	 */
	@Column(name ="DISCOUNTPRICE",nullable=true,length=22)
	public Double getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  不含税折后金额
	 */
	@Column(name ="DISCOUNTAMOUNT",nullable=true,length=22)
	public Double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  税率(%)
	 */
	@Column(name ="TAXRATE",nullable=true,length=22)
	public Double getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  单价
	 */
	@Column(name ="TAXPRICEEX",nullable=true,length=22)
	public Double getTaxPriceEx() {
		return taxPriceEx;
	}
	public void setTaxPriceEx(Double taxPriceEx) {
		this.taxPriceEx = taxPriceEx;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  金额
	 */
	@Column(name ="TAXAMOUNTEX",nullable=true,length=22)
	public Double getTaxAmountEx() {
		return taxAmountEx;
	}
	public void setTaxAmountEx(Double taxAmountEx) {
		this.taxAmountEx = taxAmountEx;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  折后单价
	 */
	@Column(name ="TAXPRICE",nullable=true,length=22)
	public Double getTaxPrice() {
		return taxPrice;
	}
	public void setTaxPrice(Double taxPrice) {
		this.taxPrice = taxPrice;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  折后金额
	 */
	@Column(name ="INTAXAMOUNT",nullable=true,length=22)
	public Double getInTaxAmount() {
		return inTaxAmount;
	}
	public void setInTaxAmount(Double inTaxAmount) {
		this.inTaxAmount = inTaxAmount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  税额
	 */
	@Column(name ="TAXAMOUNT",nullable=true,length=22)
	public Double getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单主键
	 */
	@Column(name ="INTERID_SRC",nullable=true,length=32)
	public String getInterIdSrc() {
		return interIdSrc;
	}
	public void setInterIdSrc(String interIdSrc) {
		this.interIdSrc = interIdSrc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单编号
	 */
	@Column(name ="BILLNO_SRC",nullable=true,length=32)
	public String getBillNoSrc() {
		return billNoSrc;
	}
	public void setBillNoSrc(String billNoSrc) {
		this.billNoSrc = billNoSrc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单分录主键
	 */
	@Column(name ="ID_SRC",nullable=true,length=32)
	public java.lang.String getIdSrc(){
		return this.idSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单分录主键
	 */
	public void setIdSrc(java.lang.String idSrc){
		this.idSrc = idSrc;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单主键
	 */
	@Column(name ="INTERID_ORDER",nullable=true,length=32)
	public String getInterIdOrder() {
		return interIdOrder;
	}
	public void setInterIdOrder(String interIdOrder) {
		this.interIdOrder = interIdOrder;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单编号
	 */
	@Column(name ="BILLNO_ORDER",nullable=true,length=32)
	public String getBillNoOrder() {
		return billNoOrder;
	}
	public void setBillNoOrder(String billNoOrder) {
		this.billNoOrder = billNoOrder;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单分录主键
	 */
	@Column(name ="ID_ORDER",nullable=true,length=32)
	public java.lang.String getIdOrder(){
		return this.idOrder;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单分录主键
	 */
	public void setIdOrder(java.lang.String idOrder){
		this.idOrder = idOrder;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="NOTE",nullable=true)
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
		this.auditDate = auditDate;}

	@Transient
	public String getAuditorName() {
		return auditorName;
	}
	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}
	@Column(name="billerName")
	public String getBillerName() {
		return billerName;
	}
	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}
	@Column(name="GoodsWeight")
	public String getGoodsWeight() {
		return goodsWeight;
	}
	public void setGoodsWeight(String goodsWeight) {
		this.goodsWeight = goodsWeight;
	}
	@Column(name="BILLERID")
	public String getBillerId() {
		return billerId;
	}
	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}
	@Column(name="RbillId")
	public String getRbillId() {
		return rbillId;
	}
	public void setRbillId(String rbillId) {
		this.rbillId = rbillId;
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
