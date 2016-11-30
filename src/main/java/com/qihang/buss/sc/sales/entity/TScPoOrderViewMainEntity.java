package com.qihang.buss.sc.sales.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 采购订单主表
 * @author onlineGenerator
 * @date 2016-07-04 09:28:41
 * @version V1.0   
 *
 */
@Entity
@Table(name = "v_sc_po_order_main", schema = "")
@SuppressWarnings("serial")
public class TScPoOrderViewMainEntity implements java.io.Serializable {
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
	private String idSrc;
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
	/**关闭标记*/
	private Integer closed;
	/**自动关闭标记*/
	private Integer autoFlag;
	/**作废标记*/
	private Integer cancellation;
	/**摘要*/
  @Excel(name="摘要")
	private String explanation;
	/**分支机构*/
	private String sonId;

	/**版本号*/
	private Integer version;
	/**
	 * 供应商名称
	 */
	private String itemName;
	/**
	 * 部门名称
	 */
	private String deptName;
	/**
	 * 仓库名称
	 */
	private String stockName;
	/**
	 * 制单人名称
	 */
	private String billerName;

	private String empName;

	/**
	 * 被引用标记
	 */
	private String sonName;//分支机构名称


	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
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

	@Column(name ="ID_SRC",nullable=true,length=32)
	public String getIdSrc(){
		return this.idSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单主键
	 */
	public void setIdSrc(String idSrc){
		this.idSrc = idSrc;
	}

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
	 *@return: java.lang.Integer  关闭标记
	 */

	@Column(name ="CLOSED",nullable=true,length=1)
	public Integer getClosed(){
		return this.closed;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  关闭标记
	 */
	public void setClosed(Integer closed){
		this.closed = closed;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  自动关闭标记
	 */

	@Column(name ="AUTOFLAG",nullable=true,length=1)
	public Integer getAutoFlag(){
		return this.autoFlag;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  自动关闭标记
	 */
	public void setAutoFlag(Integer autoFlag){
		this.autoFlag = autoFlag;
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

	@Column(name ="itemName",nullable=true)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
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

	@Column(name ="billerName",nullable=true)
	public String getBillerName() {
		return billerName;
	}

	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}

	@Column(name ="empName",nullable=true)
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Version
	@Column(name ="VERSION",nullable=true)
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Column(name ="sonName",nullable=true)
	public String getSonName() {
		return sonName;
	}

	public void setSonName(String sonName) {
		this.sonName = sonName;
	}
}
