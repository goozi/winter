package com.qihang.buss.sc.distribution.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: v_drp_order
 * @author onlineGenerator
 * @date 2016-08-04 17:45:21
 * @version V1.0   
 *
 */
@Entity
@Table(name = "v_sc_drp_order", schema = "")
@SuppressWarnings("serial")
public class TScDrpOrderViewEntity implements java.io.Serializable {
	/**id*/
	private String id;
	/**子表主键*/
	private java.lang.String entryId;
	/**创建人登录名称*/
	private String createBy;
	/**创建日期*/
	private Date createDate;
	/**创建人名称*/
	private String createName;
	/**更新人登录名称*/
	private String updateBy;
	/**更新日期*/
	private Date updateDate;
	/**更新人名称*/
	private String updateName;
	/**单据类型*/
	@Excel(name="单据类型")
	private String tranType;
	/**单据编号*/
	@Excel(name="单据编号")
	private String billNo;
	/**单据日期*/
	@Excel(name="单据日期")
	private Date date;
	/**上游经销商或总部*/
	private String itemId;
	/**经销商*/
	@Excel(name="上游经销商或总部")
	private String itemName;
	/**联系人*/
	@Excel(name="联系人")
	private String contact;
	/**手机号码*/
	@Excel(name="手机号码")
	private String mobilePhone;
	/**电话*/
	@Excel(name="电话")
	private String phone;
	/**传真*/
	@Excel(name="传真")
	private String fax;
	/**联系地址*/
	@Excel(name="联系地址")
	private String address;
	/**经办人ID*/
	private String empId;
	/**经办人*/
	@Excel(name="经办人")
	private String empName;

	/**部门ID*/
	private String deptId;
	/**部门*/
	@Excel(name="部门")
	private String deptName;

	/**仓库ID*/
	private String oStockID;
	/**仓库*/
	@Excel(name="仓库")
	private String oStockName;
	/**优惠金额*/
	@Excel(name="优惠金额")
	private Double reBateAmount;
	/**应付金额*/
	@Excel(name="应付金额")
	private Double allAmount;
	/**执行金额*/
	@Excel(name="执行金额")
	private Double checkAmount;
	/**审核人*/
	private String checkerId;
	@Excel(name="单据状态")
	private Integer status;//单据状态
	private String auditDate;//审核日期
	@Excel(name="审核人")
	private String auditorName;//审核人

	/**制单人ID*/
	private String billerId;
	/**制单人*/
	@Excel(name="制单人")
	private String billerName;
	/**审核日期*/
	@Excel(name="审核日期")
	private String checkDate;
	/**审核状态*/
	@Excel(name="审核状态")
	private Integer checkState;
	/**关闭状态*/
	@Excel(name="关闭状态")
	private Integer closed;
	/**自动关闭标记*/
	private Integer autoFlag;
	/**作废标记*/
	@Excel(name="作废标记")
	private Integer cancellation;
	/**摘要*/
	@Excel(name="摘要")
	private String explanation;
	/**分支机构ID*/
	private String sonId;
	/**分支机构*/
	@Excel(name="分支机构")
	private String sonName;
	/**分录号*/
	@Excel(name="分录号")
	private Integer indexNumber;
	/**仓库ID*/
	private String stockId;
	/**仓库*/
	@Excel(name="仓库")
	private String stockName;
	/**商品ID*/
	private String goodsItemID;

	/**商品编号*/
	@Excel(name="商品编号")
	private String goodsNo;
	/**商品规格*/
	@Excel(name="商品规格")
	private String model;
	/**商品名称*/
	@Excel(name="商品名称")
	private String goodsName;
	/**条形码*/
	@Excel(name="条形码")
	private String barCode;
	/**单位ID*/
	private String unitId;
	/**单位*/
	@Excel(name="单位")
	private String unitName;
	/**辅助单位*/
	private String secUnitId;
	/**基本单位*/
	private String basicUnitId;
	/**数量*/
	@Excel(name="数量")
	private Double qty;
	/**辅助换算率*/
	@Excel(name="辅助换算率")
	private Double secCoefficient;
	/**辅助数量*/
	@Excel(name="辅助数量")
	private Double secQty;
	/**换算率*/
	@Excel(name="换算率")
	private Double coefficient;
	/**基本数量*/
	@Excel(name="基本数量")
	private Double basicQty;
	/**不含税单价*/
	@Excel(name="不含税单价")
	private Double price;
	/**订单金额*/
	@Excel(name="订单金额")
	private Double amount;
	/**折扣率(%)*/
	@Excel(name="折扣率(%)")
	private Double discountRate;
	/**不含税折后单价*/
	@Excel(name="不含税折后单价")
	private Double discountPrice;
	/**不含税折后金额*/
	@Excel(name="不含税折后金额")
	private Double discountAmount;
	/**税率(%)*/
	@Excel(name="税率(%)")
	private Double taxRate;
	/**单价*/
	@Excel(name="单价")
	private Double taxPriceEx;
	/**金额*/
	@Excel(name="金额")
	private Double taxAmountEx;
	/**折后单价*/
	@Excel(name="折后单价")
	private Double taxPrice;
	/**折后金额*/
	@Excel(name="折后金额")
	private Double inTaxAmount;
	/**税额*/
	@Excel(name="税额")
	private Double taxAmount;
	/**收货日期*/
	@Excel(name="收货日期")
	private Date jhDate;
	/**收货确认数量*/
	@Excel(name="收货确认数量")
	private Double affirmQty;
	/**发货数量*/
	@Excel(name="发货数量")
	private Double outStockQty;
	/**发货日期*/
	@Excel(name="发货日期")
	private Date outDate;
	/**确认收货日期*/
	@Excel(name="确认收货日期")
	private Date affirmDate;
	/**备注*/
	@Excel(name="备注")
	private String note;

	private String isAssembly;
	private String isKfPeriod;
	private String batchManager;
	private String kfDateType;
	private String KfPeriod;
	private String tranTypeName;

	private String interID;
	private String rOrderId;

	private String fetchStyle;
	/**
	 *方法: 取得String
	 *@return: String  id
	 */
	//@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
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
	 *@return: String  创建人登录名称
	 */
	@Column(name ="CREATE_BY",nullable=true,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置String
	 *@param: String  创建人登录名称
	 */
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得Date
	 *@return: Date  创建日期
	 */
	@Column(name ="CREATE_DATE",nullable=true)
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置Date
	 *@param: Date  创建日期
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得String
	 *@return: String  创建人名称
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置String
	 *@param: String  创建人名称
	 */
	public void setCreateName(String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得String
	 *@return: String  更新人登录名称
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置String
	 *@param: String  更新人登录名称
	 */
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得Date
	 *@return: Date  更新日期
	 */
	@Column(name ="UPDATE_DATE",nullable=true)
	public Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置Date
	 *@param: Date  更新日期
	 */
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}
	/**
	 *方法: 取得String
	 *@return: String  更新人名称
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置String
	 *@param: String  更新人名称
	 */
	public void setUpdateName(String updateName){
		this.updateName = updateName;
	}
	@Column(name ="tranType")
	public String getTranType() {
		return tranType;
	}
	public void setTranType(String tranType) {
		this.tranType = tranType;
	}
	@Column(name ="billNo")
	public String getBillNo() {
		return billNo;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	@Column(name ="date")
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Column(name ="itemID")
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	@Column(name ="contact")
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	@Column(name ="mobilePhone")
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	@Column(name ="phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name ="fax")
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	@Column(name ="address")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name ="empID")
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	@Column(name ="empName")
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	@Column(name ="deptID")
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(name ="deptName")
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	@Column(name ="oStockID")
	public String getOStockID() {
		return oStockID;
	}
	public void setOStockID(String oStockID) {
		this.oStockID = oStockID;
	}

	@Column(name ="oStockName")
	public String getOStockName() {
		return oStockName;
	}
	public void setOStockName(String oStockName) {
		this.oStockName = oStockName;
	}
	@Column(name ="rebateAmount")
	public Double getReBateAmount() {
		return reBateAmount;
	}
	public void setReBateAmount(Double reBateAmount) {
		this.reBateAmount = reBateAmount;
	}
	@Column(name ="allAmount")
	public Double getAllAmount() {
		return allAmount;
	}
	public void setAllAmount(Double allAmount) {
		this.allAmount = allAmount;
	}
	@Column(name ="checkAmount")
	public Double getCheckAmount() {
		return checkAmount;
	}
	public void setCheckAmount(Double checkAmount) {
		this.checkAmount = checkAmount;
	}
	@Column(name ="checkerID")
	public String getCheckerId() {
		return checkerId;
	}
	public void setCheckerId(String checkerId) {
		this.checkerId = checkerId;
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
	@Column(name ="billerID")
	public String getBillerId() {
		return billerId;
	}
	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}
	@Column(name ="billerName")
	public String getBillerName() {
		return billerName;
	}
	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}
	@Column(name ="checkDate")
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	@Column(name ="checkState")
	public Integer getCheckState() {
		return checkState;
	}
	public void setCheckState(Integer checkState) {
		this.checkState = checkState;
	}
	@Column(name ="closed")
	public Integer getClosed() {
		return closed;
	}
	public void setClosed(Integer closed) {
		this.closed = closed;
	}
	@Column(name ="autoFlag")
	public Integer getAutoFlag() {
		return autoFlag;
	}
	public void setAutoFlag(Integer autoFlag) {
		this.autoFlag = autoFlag;
	}
	@Column(name ="cancellation")
	public Integer getCancellation() {
		return cancellation;
	}
	public void setCancellation(Integer cancellation) {
		this.cancellation = cancellation;
	}
	@Column(name ="explanation")
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	@Column(name ="sonID")
	public String getSonId() {
		return sonId;
	}
	public void setSonId(String sonId) {
		this.sonId = sonId;
	}
	@Column(name ="sonName")
	public String getSonName() {
		return sonName;
	}
	public void setSonName(String sonName) {
		this.sonName = sonName;
	}
	@Column(name ="indexNumber")
	public Integer getIndexNumber() {
		return indexNumber;
	}
	public void setIndexNumber(Integer indexNumber) {
		this.indexNumber = indexNumber;
	}
	@Column(name ="stockID")
	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	@Column(name ="stockName")
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	@Column(name ="goodsItemID")
	public String getGoodsItemID() {
		return goodsItemID;
	}
	public void setGoodsItemID(String goodsItemID) {
		this.goodsItemID = goodsItemID;
	}
	@Column(name ="itemName")
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	@Column(name ="goodsNo")
	public String getGoodsNo() {
		return goodsNo;
	}
	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}
	@Column(name ="model")
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	@Column(name ="BarCode")
	public String getBarCode() {
		return barCode;
	}
	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}
	@Column(name ="goodsName")
	public String getGoodsName() {
		return goodsName;
	}
	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}
	@Column(name ="unitID")
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	@Column(name ="unitName")
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	@Column(name ="secUnitID")
	public String getSecUnitId() {
		return secUnitId;
	}
	public void setSecUnitId(String secUnitId) {
		this.secUnitId = secUnitId;
	}
	@Column(name ="basicUnitID")
	public String getBasicUnitId() {
		return basicUnitId;
	}
	public void setBasicUnitId(String basicUnitId) {
		this.basicUnitId = basicUnitId;
	}
	@Column(name ="qty")
	public Double getQty() {
		return qty;
	}
	public void setQty(Double qty) {
		this.qty = qty;
	}
	@Column(name ="secCoefficient")
	public Double getSecCoefficient() {
		return secCoefficient;
	}
	public void setSecCoefficient(Double secCoefficient) {
		this.secCoefficient = secCoefficient;
	}
	@Column(name ="secQty")
	public Double getSecQty() {
		return secQty;
	}
	public void setSecQty(Double secQty) {
		this.secQty = secQty;
	}
	@Column(name ="coefficient")
	public Double getCoefficient() {
		return coefficient;
	}
	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}
	@Column(name ="basicQty")
	public Double getBasicQty() {
		return basicQty;
	}
	public void setBasicQty(Double basicQty) {
		this.basicQty = basicQty;
	}
	@Column(name ="price")
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	@Column(name ="amount")
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@Column(name ="discountRate")
	public Double getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(Double discountRate) {
		this.discountRate = discountRate;
	}
	@Column(name ="discountPrice")
	public Double getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}
	@Column(name ="discountAmount")
	public Double getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}
	@Column(name ="taxRate")
	public Double getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}
	@Column(name ="taxPriceEx")
	public Double getTaxPriceEx() {
		return taxPriceEx;
	}
	public void setTaxPriceEx(Double taxPriceEx) {
		this.taxPriceEx = taxPriceEx;
	}
	@Column(name ="taxAmountEx")
	public Double getTaxAmountEx() {
		return taxAmountEx;
	}
	public void setTaxAmountEx(Double taxAmountEx) {
		this.taxAmountEx = taxAmountEx;
	}
	@Column(name ="taxPrice")
	public Double getTaxPrice() {
		return taxPrice;
	}
	public void setTaxPrice(Double taxPrice) {
		this.taxPrice = taxPrice;
	}
	@Column(name ="inTaxAmount")
	public Double getInTaxAmount() {
		return inTaxAmount;
	}
	public void setInTaxAmount(Double inTaxAmount) {
		this.inTaxAmount = inTaxAmount;
	}
	@Column(name ="taxAmount")
	public Double getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}
	@Column(name ="jhDate")
	public Date getJhDate() {
		return jhDate;
	}
	public void setJhDate(Date jhDate) {
		this.jhDate = jhDate;
	}
	@Column(name ="affirmQty")
	public Double getAffirmQty() {
		return affirmQty;
	}
	public void setAffirmQty(Double affirmQty) {
		this.affirmQty = affirmQty;
	}
	@Column(name ="outStockQty")
	public Double getOutStockQty() {
		return outStockQty;
	}
	public void setOutStockQty(Double outStockQty) {
		this.outStockQty = outStockQty;
	}
	@Column(name ="outDate")
	public Date getOutDate() {
		return outDate;
	}
	public void setOutDate(Date outDate) {
		this.outDate = outDate;
	}
	@Column(name ="affirmDate")
	public Date getAffirmDate() {
		return affirmDate;
	}
	public void setAffirmDate(Date affirmDate) {
		this.affirmDate = affirmDate;
	}
	@Column(name ="note")
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@Column(name ="ISAssembly")
	public String getIsAssembly() {
		return isAssembly;
	}
	public void setIsAssembly(String isAssembly) {
		this.isAssembly = isAssembly;
	}
	@Column(name ="ISKFPeriod")
	public String getIsKfPeriod() {
		return isKfPeriod;
	}
	public void setIsKfPeriod(String isKfPeriod) {
		this.isKfPeriod = isKfPeriod;
	}
	@Column(name ="BatchManager")
	public String getBatchManager() {
		return batchManager;
	}
	public void setBatchManager(String batchManager) {
		this.batchManager = batchManager;
	}
	@Column(name ="KFDateType")
	public String getKfDateType() {
		return kfDateType;
	}
	public void setKfDateType(String kfDateType) {
		this.kfDateType = kfDateType;
	}
	@Column(name ="KFPeriod")
	public String getKfPeriod() {
		return KfPeriod;
	}
	public void setKfPeriod(String kfPeriod) {
		KfPeriod = kfPeriod;
	}
	@Transient
	public String getTranTypeName() {
		return tranTypeName;
	}
	public void setTranTypeName(String tranTypeName) {
		this.tranTypeName = tranTypeName;
	}
	@Column(name="INTERID")
	public String getInterID() {
		return interID;
	}
	public void setInterID(String interID) {
		this.interID = interID;
	}
	@Id
	@Column(name="RORDERID")
	public String getrOrderId() {
		return rOrderId;
	}
	public void setrOrderId(String rOrderId) {
		this.rOrderId = rOrderId;
	}
	@Column(name="FETCHSTYLE")
	public String getFetchStyle() {
		return fetchStyle;
	}
	public void setFetchStyle(String fetchStyle) {
		this.fetchStyle = fetchStyle;
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
