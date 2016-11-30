package com.qihang.buss.sc.distribution.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: v_drp_stockbill
 * @author onlineGenerator
 * @date 2016-08-15 14:16:26
 * @version V1.0   
 *
 */
@Entity
@Table(name = "v_sc_drp_stockbill", schema = "")
@SuppressWarnings("serial")
public class TSCViewDrpStockbillEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**子表主键*/
	private java.lang.String entryId;
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
	/**单据类型*/
	@Excel(name="单据类型")
	private java.lang.String tranType;
	private java.lang.String tranTypeName;
	/**单据编号*/
	@Excel(name="单据编号")
	private java.lang.String billNo;
	/**单据日期*/
	@Excel(name="单据日期")
	private java.lang.String date;
	/**下游经销商*/
	private java.lang.String dealerId;
	@Excel(name="下游经销商")
	private java.lang.String dealerName;
	/**联系人*/
	@Excel(name="联系人")
	private java.lang.String contact;
	/**手机号码*/
	@Excel(name="手机号码")
	private java.lang.String mobilephone;
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
	private java.lang.String empId;
	@Excel(name="经办人")
	private java.lang.String empName;
	/**部门*/
	private java.lang.String deptId;
	@Excel(name="部门")
	private java.lang.String deptName;
	/**仓库*/
	private java.lang.String bStockId;
	@Excel(name="仓库")
	private java.lang.String bStockName;
	/**优惠金额*/
	@Excel(name="优惠金额")
	private java.math.BigDecimal reBateAmount;
	/**物流费用*/
	@Excel(name="物流费用")
	private java.math.BigDecimal freight;
	/**单据金额*/
	@Excel(name="单据金额")
	private java.math.BigDecimal amount;
	/**结算账号*/
	private java.lang.String accountId;
	@Excel(name="结算账号")
	private java.lang.String accountName;
	/**本次收款*/
	@Excel(name="本次收款")
	private java.math.BigDecimal curPayAmount;
	/**收款金额*/
	@Excel(name="收款金额")
	private java.math.BigDecimal checkAmount;
	/**重量*/
	@Excel(name="重量")
	private java.lang.String weight;
	/**确认状态*/
	@Excel(name="确认状态")
	private java.lang.Integer affirmStatus;
	/**确认人*/
	private java.lang.String affirmId;
	/**确认时间*/
	@Excel(name="确认时间")
	private java.util.Date affirmDate;
	/**丢失金额*/
	@Excel(name="丢失金额")
	private java.math.BigDecimal amountLoss;
	/**源单类型*/
	@Excel(name="源单类型")
	private java.lang.Integer classidSrc;
	/**源单主键*/
	@Excel(name="源单主键")
	private java.lang.String interIdSrc;
	/**源单编号*/
	@Excel(name="源单编号")
	private java.lang.String billNoSrc;
	/**审核人*/
	private java.lang.String checkerId;
	/**制单人*/
	private java.lang.String billerId;
	@Excel(name="制单人")
	private java.lang.String billerName;
	/**审核日期*/
	@Excel(name="审核日期")
	private java.util.Date checkDate;
	/**审核状态*/
	@Excel(name="审核状态")
	private java.lang.Integer checkState;
	/**作废标记*/
	@Excel(name="作废标记")
	private java.lang.String cancellation;
	/**摘要*/
	@Excel(name="摘要")
	private java.lang.String explanation;
	/**分支机构*/
	private java.lang.String sonid;
	@Excel(name="分支机构")
	private java.lang.String sonName;

	/**分录号*/
	@Excel(name="分录号")
	private java.lang.String indexNumber;
	private java.lang.String stockId;
	@Excel(name="仓库")
	private java.lang.String stockName;
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
	@Excel(name="单位")
	private java.lang.String unitName;
	private java.lang.String secUnitName;
	private java.lang.String basicUnitName;
	/**批号*/
	@Excel(name="批号")
	private java.lang.String batchNo;
	/**生产日期*/
	@Excel(name="生产日期")
	private java.util.Date kfDate;
	/**保质期*/
	@Excel(name="保质期")
	private java.lang.Integer kfPeriod;
	/**保质期类型*/
	@Excel(name="保质期类型")
	private java.lang.String kfDateType;
	/**数量*/
	@Excel(name="数量")
	private java.math.BigDecimal qty;
	/**辅助换算率*/
	@Excel(name="辅助换算率")
	private java.math.BigDecimal secCoefficient;
	/**辅助数量*/
	@Excel(name="辅助数量")
	private java.math.BigDecimal secQty;
	/**换算率*/
	@Excel(name="换算率")
	private java.math.BigDecimal coefficient;
	/**基本数量*/
	@Excel(name="基本数量")
	private java.math.BigDecimal basicQty;
	/**基本单位*/
	@Excel(name="基本单位")
	private java.lang.String basicUnitId;
	/**不含税单价*/
	@Excel(name="不含税单价")
	private java.math.BigDecimal price;
	/**不含税金额*/
	@Excel(name="不含税金额")
	private java.math.BigDecimal itemAmount;
	/**折扣率(%)*/
	@Excel(name="折扣率(%)")
	private java.math.BigDecimal discountRate;
	/**不含税折后单价*/
	@Excel(name="不含税折后单价")
	private java.math.BigDecimal discountPrice;
	/**不含税折后金额*/
	@Excel(name="不含税折后金额")
	private java.math.BigDecimal discountAmount;
	/**税率(%)*/
	@Excel(name="税率(%)")
	private java.math.BigDecimal taxRate;
	/**单价*/
	@Excel(name="单价")
	private java.math.BigDecimal taxPriceEx;
	/**金额*/
	@Excel(name="金额")
	private java.math.BigDecimal taxAmountEx;
	/**折后单价*/
	@Excel(name="折后单价")
	private java.math.BigDecimal taxPrice;
	/**折后金额*/
	@Excel(name="折后金额")
	private java.math.BigDecimal inTaxAmount;
	/**税额*/
	@Excel(name="税额")
	private java.math.BigDecimal taxAmount;
	/**成本单价*/
	@Excel(name="成本单价")
	private java.math.BigDecimal costPrice;
	/**成本金额*/
	@Excel(name="成本金额")
	private java.math.BigDecimal costAmount;
	/**重量*/
	@Excel(name="重量")
	private java.math.BigDecimal itemWeight;
	/**促销类型*/
	@Excel(name="促销类型")
	private java.lang.Integer salesId;
	/**赠品标记*/
	@Excel(name="赠品标记")
	private java.lang.Integer freegifts;
	/**退货数量*/
	@Excel(name="退货数量")
	private java.math.BigDecimal commitQty;
	/**确认收货数量*/
	@Excel(name="确认收货数量")
	private java.math.BigDecimal stockQty;
	/**源单类型*/
	@Excel(name="源单类型")
	private java.lang.Integer itemClassIdSrc;
	/**源单主键*/
	@Excel(name="源单主键")
	private java.lang.String itemInterIdSrc;
	/**源单编号*/
	@Excel(name="源单编号")
	private java.lang.String itemBillNoSrc;
	/**源单分录号*/
	@Excel(name="源单分录号")
	private java.lang.String fidSrc;
	/**订单主键*/
	@Excel(name="订单主键")
	private java.lang.String interIdOrder;
	/**订单编号*/
	@Excel(name="订单编号")
	private java.lang.String billNoOrder;
	/**订单分录主键*/
	@Excel(name="订单分录主键")
	private java.lang.String fidOrder;
	/**辅助单位*/
	private java.lang.String secUnitId;

	/**有效期至*/
	@Excel(name="有效期至")
	private java.util.Date periodDate;

	/**备注*/
	@Excel(name="备注")
	private java.lang.String note;
	@Excel(name="单据状态")
	private Integer status;//单据状态
	private String auditDate;//审核日期
	@Excel(name="审核人")
	private String auditorName;//审核人
	@Excel(name="确认人")
	private String affirmName;//确认人
	private String isAssembly;
	private String isKfPeriod;
	private String batchManager;
	private String itemKfperiod;
	private String itemKfDateType;
	private String salesName;
	private String entryClassIdSrc;
	private String stockBillId;
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
	@Column(name ="CREATE_DATE",nullable=true)
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
	@Column(name ="UPDATE_DATE",nullable=true)
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
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Column(name ="dealerID")
	public String getDealerId() {
		return dealerId;
	}
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
	}
	@Column(name ="DealerName")
	public String getDealerName() {
		return dealerName;
	}
	public void setDealerName(String dealerName) {
		this.dealerName = dealerName;
	}
	@Column(name ="Contact")
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	@Column(name ="Mobilephone")
	public String getMobilephone() {
		return mobilephone;
	}
	public void setMobilephone(String mobilephone) {
		this.mobilephone = mobilephone;
	}
	@Column(name ="Phone")
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Column(name ="Fax")
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	@Column(name ="Address")
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	@Column(name ="EmpId")
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	@Column(name ="EmpName")
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	@Column(name ="DeptId")
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	@Column(name ="DeptName")
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	@Column(name ="BStockId")
	public String getBStockId() {
		return bStockId;
	}
	public void setBStockId(String bStockId) {
		this.bStockId = bStockId;
	}
	@Column(name ="BStockName")
	public String getBStockName() {
		return bStockName;
	}
	public void setBStockName(String bStockName) {
		this.bStockName = bStockName;
	}
	@Column(name ="ReBateAmount")
	public BigDecimal getReBateAmount() {
		return reBateAmount;
	}
	public void setReBateAmount(BigDecimal reBateAmount) {
		this.reBateAmount = reBateAmount;
	}
	@Column(name ="Freight")
	public BigDecimal getFreight() {
		return freight;
	}
	public void setFreight(BigDecimal freight) {
		this.freight = freight;
	}
	@Column(name ="Amount")
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	@Column(name ="AccountId")
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	@Column(name ="AccountName")
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	@Column(name ="CurPayAmount")
	public BigDecimal getCurPayAmount() {
		return curPayAmount;
	}
	public void setCurPayAmount(BigDecimal curPayAmount) {
		this.curPayAmount = curPayAmount;
	}
	@Column(name ="CheckAmount")
	public BigDecimal getCheckAmount() {
		return checkAmount;
	}
	public void setCheckAmount(BigDecimal checkAmount) {
		this.checkAmount = checkAmount;
	}
	@Column(name ="Weight")
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	@Column(name ="AffirmStatus")
	public Integer getAffirmStatus() {
		return affirmStatus;
	}
	public void setAffirmStatus(Integer affirmStatus) {
		this.affirmStatus = affirmStatus;
	}
	@Column(name ="AffirmId")
	public String getAffirmId() {
		return affirmId;
	}
	public void setAffirmId(String affirmId) {
		this.affirmId = affirmId;
	}
	@Column(name ="AffirmDate")
	public Date getAffirmDate() {
		return affirmDate;
	}
	public void setAffirmDate(Date affirmDate) {
		this.affirmDate = affirmDate;
	}
	@Column(name ="AmountLoss")
	public BigDecimal getAmountLoss() {
		return amountLoss;
	}
	public void setAmountLoss(BigDecimal amountLoss) {
		this.amountLoss = amountLoss;
	}
	@Column(name ="Classid_Src")
	public Integer getClassidSrc() {
		return classidSrc;
	}
	public void setClassidSrc(Integer classidSrc) {
		this.classidSrc = classidSrc;
	}
	@Column(name ="InterId_Src")
	public String getInterIdSrc() {
		return interIdSrc;
	}
	public void setInterIdSrc(String interIdSrc) {
		this.interIdSrc = interIdSrc;
	}
	@Column(name ="billNo_SRC")
	public String getBillNoSrc() {
		return billNoSrc;
	}
	public void setBillNoSrc(String billNoSrc) {
		this.billNoSrc = billNoSrc;
	}
	@Column(name ="CheckerId")
	public String getCheckerId() {
		return checkerId;
	}
	public void setCheckerId(String checkerId) {
		this.checkerId = checkerId;
	}
	@Column(name ="BillerId")
	public String getBillerId() {
		return billerId;
	}
	public void setBillerId(String billerId) {
		this.billerId = billerId;
	}
	@Column(name ="BillerName")
	public String getBillerName() {
		return billerName;
	}
	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}
	@Column(name ="CheckDate")
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	@Column(name ="CheckState")
	public Integer getCheckState() {
		return checkState;
	}
	public void setCheckState(Integer checkState) {
		this.checkState = checkState;
	}
	@Column(name ="Cancellation")
	public String getCancellation() {
		return cancellation;
	}
	public void setCancellation(String cancellation) {
		this.cancellation = cancellation;
	}
	@Column(name ="Explanation")
	public String getExplanation() {
		return explanation;
	}
	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
	@Column(name ="Sonid")
	public String getSonid() {
		return sonid;
	}
	public void setSonid(String sonid) {
		this.sonid = sonid;
	}
	@Column(name ="SonName")
	public String getSonName() {
		return sonName;
	}
	public void setSonName(String sonName) {
		this.sonName = sonName;
	}
	@Column(name ="IndexNumber")
	public String getIndexNumber() {
		return indexNumber;
	}
	public void setIndexNumber(String indexNumber) {
		this.indexNumber = indexNumber;
	}
	@Column(name ="StockId")
	public String getStockId() {
		return stockId;
	}
	public void setStockId(String stockId) {
		this.stockId = stockId;
	}
	@Column(name ="StockName")
	public String getStockName() {
		return stockName;
	}
	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	@Column(name ="ItemId")
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	@Column(name ="Number")
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	@Column(name ="Name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name ="Model")
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
	@Column(name ="UnitId")
	public String getUnitId() {
		return unitId;
	}
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}
	@Column(name ="UnitName")
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	@Column(name ="BatchNo")
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	@Column(name ="KfDate")
	public Date getKfDate() {
		return kfDate;
	}
	public void setKfDate(Date kfDate) {
		this.kfDate = kfDate;
	}
	@Column(name ="KfPeriod")
	public Integer getKfPeriod() {
		return kfPeriod;
	}
	public void setKfPeriod(Integer kfPeriod) {
		this.kfPeriod = kfPeriod;
	}
	@Column(name ="KfDateType")
	public String getKfDateType() {
		return kfDateType;
	}
	public void setKfDateType(String kfDateType) {
		this.kfDateType = kfDateType;
	}
	@Column(name ="Qty")
	public BigDecimal getQty() {
		return qty;
	}
	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
	@Column(name ="SecCoefficient")
	public BigDecimal getSecCoefficient() {
		return secCoefficient;
	}
	public void setSecCoefficient(BigDecimal secCoefficient) {
		this.secCoefficient = secCoefficient;
	}
	@Column(name ="SecQty")
	public BigDecimal getSecQty() {
		return secQty;
	}
	public void setSecQty(BigDecimal secQty) {
		this.secQty = secQty;
	}
	@Column(name ="Coefficient")
	public BigDecimal getCoefficient() {
		return coefficient;
	}
	public void setCoefficient(BigDecimal coefficient) {
		this.coefficient = coefficient;
	}
	@Column(name ="BasicQty")
	public BigDecimal getBasicQty() {
		return basicQty;
	}
	public void setBasicQty(BigDecimal basicQty) {
		this.basicQty = basicQty;
	}
	@Column(name ="BasicUnitId")
	public String getBasicUnitId() {
		return basicUnitId;
	}
	public void setBasicUnitId(String basicUnitId) {
		this.basicUnitId = basicUnitId;
	}
	@Column(name ="Price")
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	@Column(name ="ItemAmount")
	public BigDecimal getItemAmount() {
		return itemAmount;
	}
	public void setItemAmount(BigDecimal itemAmount) {
		this.itemAmount = itemAmount;
	}
	@Column(name ="DiscountRate")
	public BigDecimal getDiscountRate() {
		return discountRate;
	}
	public void setDiscountRate(BigDecimal discountRate) {
		this.discountRate = discountRate;
	}
	@Column(name ="DiscountPrice")
	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}
	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}
	@Column(name ="DiscountAmount")
	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}
	@Column(name ="TaxRate")
	public BigDecimal getTaxRate() {
		return taxRate;
	}
	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}
	@Column(name ="TaxPriceEx")
	public BigDecimal getTaxPriceEx() {
		return taxPriceEx;
	}
	public void setTaxPriceEx(BigDecimal taxPriceEx) {
		this.taxPriceEx = taxPriceEx;
	}
	@Column(name ="TaxAmountEx")
	public BigDecimal getTaxAmountEx() {
		return taxAmountEx;
	}
	public void setTaxAmountEx(BigDecimal taxAmountEx) {
		this.taxAmountEx = taxAmountEx;
	}
	@Column(name ="TaxPrice")
	public BigDecimal getTaxPrice() {
		return taxPrice;
	}
	public void setTaxPrice(BigDecimal taxPrice) {
		this.taxPrice = taxPrice;
	}
	@Column(name ="InTaxAmount")
	public BigDecimal getInTaxAmount() {
		return inTaxAmount;
	}
	public void setInTaxAmount(BigDecimal inTaxAmount) {
		this.inTaxAmount = inTaxAmount;
	}
	@Column(name ="TaxAmount")
	public BigDecimal getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(BigDecimal taxAmount) {
		this.taxAmount = taxAmount;
	}
	@Column(name ="CostPrice")
	public BigDecimal getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}
	@Column(name ="CostAmount")
	public BigDecimal getCostAmount() {
		return costAmount;
	}
	public void setCostAmount(BigDecimal costAmount) {
		this.costAmount = costAmount;
	}
	@Column(name ="ItemWeight")
	public BigDecimal getItemWeight() {
		return itemWeight;
	}
	public void setItemWeight(BigDecimal itemWeight) {
		this.itemWeight = itemWeight;
	}
	@Column(name ="SalesId")
	public Integer getSalesId() {
		return salesId;
	}
	public void setSalesId(Integer salesId) {
		this.salesId = salesId;
	}
	@Column(name ="Freegifts")
	public Integer getFreegifts() {
		return freegifts;
	}
	public void setFreegifts(Integer freegifts) {
		this.freegifts = freegifts;
	}
	@Column(name ="CommitQty")
	public BigDecimal getCommitQty() {
		return commitQty;
	}
	public void setCommitQty(BigDecimal commitQty) {
		this.commitQty = commitQty;
	}
	@Column(name ="StockQty")
	public BigDecimal getStockQty() {
		return stockQty;
	}
	public void setStockQty(BigDecimal stockQty) {
		this.stockQty = stockQty;
	}
	@Column(name ="itemClassid_src")
	public Integer getItemClassIdSrc() {
		return itemClassIdSrc;
	}
	public void setItemClassIdSrc(Integer itemClassIdSrc) {
		this.itemClassIdSrc = itemClassIdSrc;
	}
	@Column(name ="itemInterID_SRC")
	public String getItemInterIdSrc() {
		return itemInterIdSrc;
	}
	public void setItemInterIdSrc(String itemInterIdSrc) {
		this.itemInterIdSrc = itemInterIdSrc;
	}
	@Column(name ="itemBillNo_SRC")
	public String getItemBillNoSrc() {
		return itemBillNoSrc;
	}
	public void setItemBillNoSrc(String itemBillNoSrc) {
		this.itemBillNoSrc = itemBillNoSrc;
	}
	@Column(name ="fid_SRC")
	public String getFidSrc() {
		return fidSrc;
	}
	public void setFidSrc(String fidSrc) {
		this.fidSrc = fidSrc;
	}
	@Column(name ="interID_Order")
	public String getInterIdOrder() {
		return interIdOrder;
	}
	public void setInterIdOrder(String interIdOrder) {
		this.interIdOrder = interIdOrder;
	}
	@Column(name ="BillNo_Order")
	public String getBillNoOrder() {
		return billNoOrder;
	}
	public void setBillNoOrder(String billNoOrder) {
		this.billNoOrder = billNoOrder;
	}
	@Column(name ="fid_Order")
	public String getFidOrder() {
		return fidOrder;
	}
	public void setFidOrder(String fidOrder) {
		this.fidOrder = fidOrder;
	}
	@Column(name ="SecUnitId")
	public String getSecUnitId() {
		return secUnitId;
	}
	public void setSecUnitId(String secUnitId) {
		this.secUnitId = secUnitId;
	}
	@Column(name ="PeriodDate")
	public Date getPeriodDate() {
		return periodDate;
	}
	public void setPeriodDate(Date periodDate) {
		this.periodDate = periodDate;
	}
	@Column(name ="Note")
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
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
		this.auditDate = auditDate;
	}
	@Transient
	public String getAuditorName() {
		return auditorName;
	}
	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
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
	@Column(name ="affirmName")
	public String getAffirmName() {
		return affirmName;
	}
	public void setAffirmName(String affirmName) {
		this.affirmName = affirmName;
	}
	@Transient
	public String getTranTypeName() {
		return tranTypeName;
	}
	public void setTranTypeName(String tranTypeName) {
		this.tranTypeName = tranTypeName;
	}
	@Column(name ="ITEMKFPERIOD")
	public String getItemKfperiod() {
		return itemKfperiod;
	}
	public void setItemKfperiod(String itemKfperiod) {
		this.itemKfperiod = itemKfperiod;
	}
	@Column(name ="ITEMKFDATETYPE")
	public String getItemKfDateType() {
		return itemKfDateType;
	}
	public void setItemKfDateType(String itemKfDateType) {
		this.itemKfDateType = itemKfDateType;
	}
	@Column(name="SALESNAME")
	public String salesName() {
		return salesName;
	}
	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}
	@Column(name ="SECUNITNAME")
	public String getSecUnitName() {
		return secUnitName;
	}
	public void setSecUnitName(String secUnitName) {
		this.secUnitName = secUnitName;
	}
	@Column(name ="BASICUNITNAME")
	public String getBasicUnitName() {
		return basicUnitName;
	}
	public void setBasicUnitName(String basicUnitName) {
		this.basicUnitName = basicUnitName;
	}

	@Column(name="ENTRYCLASSIDSRC")
	public String getEntryClassIdSrc() {
		return entryClassIdSrc;
	}
	public void setEntryClassIdSrc(String entryClassIdSrc) {
		this.entryClassIdSrc = entryClassIdSrc;
	}
	@Column(name="STOCKBILLID")
	public String getStockBillId() {
		return stockBillId;
	}
	public void setStockBillId(String stockBillId) {
		this.stockBillId = stockBillId;
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
