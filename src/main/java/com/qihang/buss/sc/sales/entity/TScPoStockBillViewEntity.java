package com.qihang.buss.sc.sales.entity;

import com.qihang.winter.poi.excel.annotation.Excel;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 采购出入库单
 * @author onlineGenerator
 * @date 2016-07-04 09:28:41
 * @version V1.0   
 *
 */
@Entity
@Table(name = "v_sc_po_stockbill", schema = "")
@SuppressWarnings("serial")
public class TScPoStockBillViewEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**创建人名称*/
	private String createName;
	/**创建人登录名称*/
	private String createBy;
	/**创建日期*/
	private Date createDate;
	/**更新人名称*/
	private String updateName;
	/**更新人登录名称*/
	private String updateBy;
	/**更新日期*/
	private Date updateDate;
	/**单据类型*/
	private Integer tranType;
	/**单据日期*/
  @Excel(name="单据日期")
	private Date date;
	/**单据编号*/
  @Excel(name="单据编号")
	private String billNo;
	/**供应商*/
  @Excel(name="供应商")
	private String itemId;
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
	/**经办人*/
  @Excel(name="经办人")
	private String empId;
	/**部门*/
  @Excel(name="部门")
	private String deptId;
	/**仓库*/
  @Excel(name="仓库")
	private String stockId;
	/**优惠金额*/
  @Excel(name="优惠金额")
	private Double rebateAmount;
	/**订单金额*/
  @Excel(name="订单金额")
	private Double amount;
	/**应付金额*/
  @Excel(name="应付金额")
	private Double allAmount;
	/**执行金额*/
  @Excel(name="执行金额")
	private Double checkAmount;
	/**源单类型*/
  @Excel(name="源单类型")
	private Integer classIdSrc;
	/**源单主键*/
	//private String idSrc;
	/**源单编号*/
  @Excel(name="源单编号")
	private String billNoSrc;
	/**审核人*/
	private String checkerId;
	/**制单人*/
  @Excel(name="制单人")
	private String billerId;
	/**审核日期*/
	private String checkDate;
	/**审核状态*/
	private Integer checkState;
	/**作废标记*/
	private Integer cancellation;
	/**摘要*/
  @Excel(name="摘要")
	private String explanation;
	/**分支机构*/
	private String sonId;
	private String sonName;
	private String tranTypeName;

	/**本次付款*/
	@Excel(name="本次付款")
	private java.lang.Double curPayAmount;
	/**结算账户*/
	@Excel(name="结算账户")
	private java.lang.String accountId;

	private String checkerName;


	@Column(name ="curPayAmount",nullable=false)
	public Double getCurPayAmount() {
		return curPayAmount;
	}

	public void setCurPayAmount(Double curPayAmount) {
		this.curPayAmount = curPayAmount;
	}

	@Column(name ="accountId",nullable=false)
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
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
	 *@return: java.lang.String  创建人名称
	 */

	@Column(name ="CREATE_NAME",nullable=true,length=50)
	public String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名称
	 */
	public void setCreateName(String createName){
		this.createName = createName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */

	@Column(name ="CREATE_BY",nullable=true,length=50)
	public String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人登录名称
	 */
	public void setCreateBy(String createBy){
		this.createBy = createBy;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建日期
	 */

	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */

	@Column(name ="UPDATE_NAME",nullable=true,length=50)
	public String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名称
	 */
	public void setUpdateName(String updateName){
		this.updateName = updateName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人登录名称
	 */

	@Column(name ="UPDATE_BY",nullable=true,length=50)
	public String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人登录名称
	 */
	public void setUpdateBy(String updateBy){
		this.updateBy = updateBy;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新日期
	 */

	@Column(name ="UPDATE_DATE",nullable=true,length=20)
	public Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  单据类型
	 */

	@Column(name ="TRANTYPE",nullable=true,length=10)
	public Integer getTranType(){
		return this.tranType;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  单据类型
	 */
	public void setTranType(Integer tranType){
		this.tranType = tranType;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  供应商
	 */

	@Column(name ="ITEMID",nullable=true,length=32)
	public String getItemId(){
		return this.itemId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  供应商
	 */
	public void setItemId(String itemId){
		this.itemId = itemId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人
	 */

	@Column(name ="CONTACT",nullable=true,length=50)
	public String getContact(){
		return this.contact;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系人
	 */
	public void setContact(String contact){
		this.contact = contact;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机号码
	 */

	@Column(name ="MOBILEPHONE",nullable=true,length=15)
	public String getMobilePhone(){
		return this.mobilePhone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机号码
	 */
	public void setMobilePhone(String mobilePhone){
		this.mobilePhone = mobilePhone;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话
	 */

	@Column(name ="PHONE",nullable=true,length=40)
	public String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电话
	 */
	public void setPhone(String phone){
		this.phone = phone;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  传真
	 */

	@Column(name ="FAX",nullable=true,length=40)
	public String getFax(){
		return this.fax;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  传真
	 */
	public void setFax(String fax){
		this.fax = fax;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系地址
	 */

	@Column(name ="ADDRESS",nullable=true,length=255)
	public String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系地址
	 */
	public void setAddress(String address){
		this.address = address;
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
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  优惠金额
	 */

	@Column(name ="REBATEAMOUNT",nullable=true,scale=10,length=20)
	public Double getRebateAmount(){
		return this.rebateAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  优惠金额
	 */
	public void setRebateAmount(Double rebateAmount){
		this.rebateAmount = rebateAmount;
	}

	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  订单金额
	 */

	@Column(name ="AMOUNT",nullable=true,scale=10,length=20)
	public Double getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  订单金额
	 */
	public void setAmount(Double amount){
		this.amount = amount;
	}

	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  应付金额
	 */

	@Column(name ="ALLAMOUNT",nullable=true,scale=10,length=20)
	public Double getAllAmount(){
		return this.allAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  应付金额
	 */
	public void setAllAmount(Double allAmount){
		this.allAmount = allAmount;
	}

	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  执行金额
	 */

	@Column(name ="CHECKAMOUNT",nullable=true,scale=10,length=20)
	public Double getCheckAmount(){
		return this.checkAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  执行金额
	 */
	public void setCheckAmount(Double checkAmount){
		this.checkAmount = checkAmount;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  源单类型
	 */

	@Column(name ="CLASSID_SRC",nullable=true,length=10)
	public Integer getClassIdSrc(){
		return this.classIdSrc;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  源单类型
	 */
	public void setClassIdSrc(Integer classIdSrc){
		this.classIdSrc = classIdSrc;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单主键
	 */

//	@Column(name ="ID_SRC",nullable=true,length=32)
//	public String getIdSrc(){
//		return this.idSrc;
//	}
//
//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  源单主键
//	 */
//	public void setIdSrc(String idSrc){
//		this.idSrc = idSrc;
//	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单编号
	 */

	@Column(name ="BILLNO_SRC",nullable=true,length=50)
	public String getBillNoSrc(){
		return this.billNoSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单编号
	 */
	public void setBillNoSrc(String billNoSrc){
		this.billNoSrc = billNoSrc;
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
	 *@return: java.lang.Integer  审核状态
	 */

	@Column(name ="CHECKSTATE",nullable=true,length=2)
	public Integer getCheckState(){
		return this.checkState;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  审核状态
	 */
	public void setCheckState(Integer checkState){
		this.checkState = checkState;
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

	private String entryId;//明细id
	private Integer index;//分录号
	private String entryStockName;//仓库
	private String entryItemName;//商品名称
	private String entryItemNo;//商品编码
	private String model;//规格
	private String barCode;//条形码
	private String unitName;//单位
	private Double qty;//数量
	private Double coefficient;//换算率
	private Double basicQty;//基本数量
	private Double discountRate;//折扣率
	private Double taxRate; //税率
	private Double taxPriceEx;//单价
	private Double taxAmountEx;//金额
	private Double taxPrice;//折后单价
	private Double inTaxAmount;//折后金额
	private Double taxAmount;//税额
	private Date kfDate;//生产日期
	private Integer kfPeriod;//保质期
	private String kfDateType;//保质期类型
	private String kfDateTypeInfo;//保质期类型值
	private Date periodDate;//有效期至
	private Double costPrice;//成本单价
	private Double costAmount;//成本金额
	private Integer freeGifts;//赠品标记
	private Double commitQty;//退货数量
	private String note;//备注
	private String batchNo;//批号

	/**源单类型*/
	private java.lang.String entryClassIDSrc;
	/**源单主键*/
	private java.lang.String idSrc;
	/**源单编号*/
	private java.lang.String entryBillNoSrc;
	/**源单分录主键*/
	private java.lang.String entryIdSrc;
	/**订单主键*/
	private java.lang.String idOrder;
	/**订单编号*/
	private java.lang.String billNoOrder;
	/**订单分录主键*/
	private java.lang.String entryIdOrder;

	private String basicUnitName;//基本单位
	private String secUnitName;//辅助单位
	private Double secQty;//辅助数量
	private Double secCoefficient;//辅助换算率
	private Double entryPrice;//不含税单价
	private Double entryAmount;//不含税金额
	private Double discountPrice;//不含税折扣单价
	private Double discountAmount;//不含税折扣金额

	private Integer isAudit;//是否可审核（针对采购退货）


	@Id
	@Column(name ="entryId",nullable=true)
	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	@Column(name ="findex",nullable=true)
	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	@Column(name ="entryStockName",nullable=true)
	public String getEntryStockName() {
		return entryStockName;
	}

	public void setEntryStockName(String entryStockName) {
		this.entryStockName = entryStockName;
	}

	@Column(name ="entryItemName",nullable=true)
	public String getEntryItemName() {
		return entryItemName;
	}

	public void setEntryItemName(String entryItemName) {
		this.entryItemName = entryItemName;
	}

	@Column(name ="unitName",nullable=true)
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	@Column(name ="qty",nullable=true)
	public Double getQty() {
		return qty;
	}

	public void setQty(Double qty) {
		this.qty = qty;
	}

	@Column(name ="coefficient",nullable=true)
	public Double getCoefficient() {
		return coefficient;
	}

	public void setCoefficient(Double coefficient) {
		this.coefficient = coefficient;
	}

	@Column(name ="basicQty",nullable=true)
	public Double getBasicQty() {
		return basicQty;
	}

	public void setBasicQty(Double basicQty) {
		this.basicQty = basicQty;
	}

	@Column(name ="discountRate",nullable=true)
	public Double getDiscountRate() {
		return discountRate;
	}

	public void setDiscountRate(Double discountRate) {
		this.discountRate = discountRate;
	}

	@Column(name ="taxRate",nullable=true)
	public Double getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(Double taxRate) {
		this.taxRate = taxRate;
	}

	@Column(name ="taxPriceEx",nullable=true)
	public Double getTaxPriceEx() {
		return taxPriceEx;
	}

	public void setTaxPriceEx(Double taxPriceEx) {
		this.taxPriceEx = taxPriceEx;
	}

	@Column(name ="taxAmountEx",nullable=true)
	public Double getTaxAmountEx() {
		return taxAmountEx;
	}

	public void setTaxAmountEx(Double taxAmountEx) {
		this.taxAmountEx = taxAmountEx;
	}

	@Column(name ="taxPrice",nullable=true)
	public Double getTaxPrice() {
		return taxPrice;
	}

	public void setTaxPrice(Double taxPrice) {
		this.taxPrice = taxPrice;
	}

	@Column(name ="inTaxAmount",nullable=true)
	public Double getInTaxAmount() {
		return inTaxAmount;
	}

	public void setInTaxAmount(Double inTaxAmount) {
		this.inTaxAmount = inTaxAmount;
	}

	@Column(name ="taxAmount",nullable=true)
	public Double getTaxAmount() {
		return taxAmount;
	}

	public void setTaxAmount(Double taxAmount) {
		this.taxAmount = taxAmount;
	}

	@Column(name ="freeGifts",nullable=true)
	public Integer getFreeGifts() {
		return freeGifts;
	}

	public void setFreeGifts(Integer freeGifts) {
		this.freeGifts = freeGifts;
	}

	@Column(name ="commitQty",nullable=true)
	public Double getCommitQty() {
		return commitQty;
	}

	public void setCommitQty(Double commitQty) {
		this.commitQty = commitQty;
	}

	@Column(name ="note",nullable=true)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name ="entryItemNo",nullable=true)
	public String getEntryItemNo() {
		return entryItemNo;
	}

	public void setEntryItemNo(String entryItemNo) {
		this.entryItemNo = entryItemNo;
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


	@Column(name ="basicUnitName",nullable=true)
	public String getBasicUnitName() {
		return basicUnitName;
	}

	public void setBasicUnitName(String basicUnitName) {
		this.basicUnitName = basicUnitName;
	}

	@Column(name ="secUnitName",nullable=true)
	public String getSecUnitName() {
		return secUnitName;
	}

	public void setSecUnitName(String secUnitName) {
		this.secUnitName = secUnitName;
	}

	@Column(name ="secQty",nullable=true)
	public Double getSecQty() {
		return secQty;
	}

	public void setSecQty(Double secQty) {
		this.secQty = secQty;
	}

	@Column(name ="secCoefficient",nullable=true)
	public Double getSecCoefficient() {
		return secCoefficient;
	}

	public void setSecCoefficient(Double secCoefficient) {
		this.secCoefficient = secCoefficient;
	}

	@Column(name ="entryPrice",nullable=true)
	public Double getEntryPrice() {
		return entryPrice;
	}

	public void setEntryPrice(Double entryPrice) {
		this.entryPrice = entryPrice;
	}

	@Column(name ="entryAmount",nullable=true)
	public Double getEntryAmount() {
		return entryAmount;
	}

	public void setEntryAmount(Double entryAmount) {
		this.entryAmount = entryAmount;
	}

	@Column(name ="discountPrice",nullable=true)
	public Double getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(Double discountPrice) {
		this.discountPrice = discountPrice;
	}

	@Column(name ="discountAmount",nullable=true)
	public Double getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(Double discountAmount) {
		this.discountAmount = discountAmount;
	}

	/**
	 * 审核内容
	 */
	private Integer state;
	private String auditorName;
	private String auditDate;

	@Transient
	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Transient
	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	@Transient
	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}


	@Column(name ="kfDate",nullable=true)
	public Date getKfDate() {
		return kfDate;
	}

	public void setKfDate(Date kfDate) {
		this.kfDate = kfDate;
	}

	@Column(name ="kfPeriod",nullable=true)
	public Integer getKfPeriod() {
		return kfPeriod;
	}

	public void setKfPeriod(Integer kfPeriod) {
		this.kfPeriod = kfPeriod;
	}

	@Column(name ="kfDateType",nullable=true)
	public String getKfDateType() {
		return kfDateType;
	}

	public void setKfDateType(String kfDateType) {
		this.kfDateType = kfDateType;
	}

	@Column(name ="periodDate",nullable=true)
	public Date getPeriodDate() {
		return periodDate;
	}

	public void setPeriodDate(Date periodDate) {
		this.periodDate = periodDate;
	}

	@Column(name ="costPrice",nullable=true)
	public Double getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(Double costPrice) {
		this.costPrice = costPrice;
	}

	@Column(name ="costAmount",nullable=true)
	public Double getCostAmount() {
		return costAmount;
	}

	public void setCostAmount(Double costAmount) {
		this.costAmount = costAmount;
	}

	@Column(name ="batchNo",nullable=true)
	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	@Column(name ="entryClassIdSrc",nullable=true)
	public String getEntryClassIDSrc() {
		return entryClassIDSrc;
	}

	public void setEntryClassIDSrc(String entryClassIDSrc) {
		this.entryClassIDSrc = entryClassIDSrc;
	}

	@Column(name ="entryIdSrc",nullable=true)
	public String getEntryIdSrc() {
		return entryIdSrc;
	}

	public void setEntryIdSrc(String entryIdSrc) {
		this.entryIdSrc = entryIdSrc;
	}

	@Column(name ="entryBillNoSrc",nullable=true)
	public String getEntryBillNoSrc() {
		return entryBillNoSrc;
	}

	public void setEntryBillNoSrc(String entryBillNoSrc) {
		this.entryBillNoSrc = entryBillNoSrc;
	}

	@Column(name ="idSrc",nullable=true)
	public String getIdSrc() {
		return idSrc;
	}

	public void setIdSrc(String idSrc) {
		this.idSrc = idSrc;
	}

	@Column(name ="idOrder",nullable=true)
	public String getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(String idOrder) {
		this.idOrder = idOrder;
	}

	@Column(name ="billNoOrder",nullable=true)
	public String getBillNoOrder() {
		return billNoOrder;
	}

	public void setBillNoOrder(String billNoOrder) {
		this.billNoOrder = billNoOrder;
	}

	@Column(name ="entryIdOrder",nullable=true)
	public String getEntryIdOrder() {
		return entryIdOrder;
	}

	public void setEntryIdOrder(String entryIdOrder) {
		this.entryIdOrder = entryIdOrder;
	}


	private String itemName;
	private String empName;
	private String deptName;
	private String stockName;
	private String entryItemId;
	private String entryStockId;
	private Double basicCoefficient;
	private Double cgLimitPrice;
	private Double xsLimitPrice;
	private Integer isStock;
	private String isKFPeriod;
	private String batchManager;
	private String unitId;

	private String classIdName;//源单类型名称；

	@Column(name ="itemName",nullable=true)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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

	@Column(name ="stockName",nullable=true)
	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	@Column(name ="entryItemId",nullable=true)
	public String getEntryItemId() {
		return entryItemId;
	}

	public void setEntryItemId(String entryItemId) {
		this.entryItemId = entryItemId;
	}

	@Column(name ="entryStockId",nullable=true)
	public String getEntryStockId() {
		return entryStockId;
	}

	public void setEntryStockId(String entryStockId) {
		this.entryStockId = entryStockId;
	}

	@Column(name ="basicCoefficient",nullable=true)
	public Double getBasicCoefficient() {
		return basicCoefficient;
	}

	public void setBasicCoefficient(Double basicCoefficient) {
		this.basicCoefficient = basicCoefficient;
	}

	@Column(name ="cgLimitPrice",nullable=true)
	public Double getCgLimitPrice() {
		return cgLimitPrice;
	}

	public void setCgLimitPrice(Double cgLimitPrice) {
		this.cgLimitPrice = cgLimitPrice;
	}

	@Column(name ="xsLimitPrice",nullable=true)
	public Double getXsLimitPrice() {
		return xsLimitPrice;
	}

	public void setXsLimitPrice(Double xsLimitPrice) {
		this.xsLimitPrice = xsLimitPrice;
	}

	@Column(name ="isStock",nullable=true)
	public Integer getIsStock() {
		return isStock;
	}

	public void setIsStock(Integer isStock) {
		this.isStock = isStock;
	}

	@Column(name ="isKFPeriod",nullable=true)
	public String getIsKFPeriod() {
		return isKFPeriod;
	}

	public void setIsKFPeriod(String isKFPeriod) {
		this.isKFPeriod = isKFPeriod;
	}

	@Column(name ="batchManager",nullable=true)
	public String getBatchManager() {
		return batchManager;
	}

	public void setBatchManager(String batchManager) {
		this.batchManager = batchManager;
	}

	@Column(name ="unitId",nullable=true)
	public String getUnitId() {
		return unitId;
	}

	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	@Column(name ="isAudit",nullable=true)
	public Integer getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(Integer isAudit) {
		this.isAudit = isAudit;
	}

	@Column(name ="kfDateTypeInfo",nullable=true)
	public String getKfDateTypeInfo() {
		return kfDateTypeInfo;
	}

	public void setKfDateTypeInfo(String kfDateTypeInfo) {
		this.kfDateTypeInfo = kfDateTypeInfo;
	}

	@Column(name ="sonName",nullable=true)
	public String getSonName() {
		return sonName;
	}

	public void setSonName(String sonName) {
		this.sonName = sonName;
	}

	@Column(name ="tranTypeName",nullable=true)
	public String getTranTypeName() {
		return tranTypeName;
	}

	public void setTranTypeName(String tranTypeName) {
		this.tranTypeName = tranTypeName;
	}

	@Column(name ="checkerName",nullable=true)
	public String getCheckerName() {
		return checkerName;
	}

	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}

	@Transient
	public String getClassIdName() {
		return classIdName;
	}

	public void setClassIdName(String classIdName) {
		this.classIdName = classIdName;
	}
}
