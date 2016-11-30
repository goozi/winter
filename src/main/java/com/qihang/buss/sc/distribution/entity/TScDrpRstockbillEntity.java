package com.qihang.buss.sc.distribution.entity;
import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**   
 * @Title: Entity
 * @Description: 退货申请
 * @author onlineGenerator
 * @date 2016-09-08 17:08:42
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_DRP_RStockBill", schema = "")
@SuppressWarnings("serial")
public class TScDrpRstockbillEntity implements java.io.Serializable {
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
	/**单据类型*/
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
	/**联系地址 */
  @Excel(name="联系地址 ")
	private java.lang.String address;
	/**经办人*/
  @Excel(name="经办人")
	private java.lang.String empId;
	/**部门 */
  @Excel(name="部门 ")
	private java.lang.String deptId;
	/**仓库 */
  @Excel(name="仓库 ")
	private java.lang.String rStockId;
	/**物流费用*/
  @Excel(name="物流费用")
	private java.math.BigDecimal freight;
	/**单据金额*/
	private java.math.BigDecimal amount;
	/**应收金额*/
  @Excel(name="应收金额")
	private java.math.BigDecimal allAmount;
	/**重量*/
  @Excel(name="重量")
	private java.math.BigDecimal weight;
	/**源单类型*/
  @Excel(name="源单类型")
	private java.lang.String classIdSrc;
	/**源单主键*/
	private java.lang.String interIdSrc;
	/**源单编号*/
	private java.lang.String billNoSrc;
	/**审核人*/
  @Excel(name="审核人")
	private java.lang.String checkerId;
	/**制单人*/
  @Excel(name="制单人")
	private java.lang.String billerId ;
	/**审核日期*/
  @Excel(name="审核日期")
	private java.util.Date checkDate;
	/**审核状态*/
  @Excel(name="审核状态")
	private java.lang.Integer checkState ;
	/**作废标记*/
	private java.lang.Integer cancellation;
	/**摘要*/
  @Excel(name="摘要")
	private java.lang.String explanation;
	/**分支机构*/
  @Excel(name="分支机构")
	private java.lang.String sonId;
	/**版本号*/
	private java.lang.Integer version;
	/**是否禁用*/
	private java.lang.Integer disabled;
	/**是否删除*/
	private java.lang.Integer deleted;

	private String sonName;//分支机构名称
	private String billerName;//制单人
	private String empName;//经办人
	private String deptName;//部门
	private String rStockName;//仓库
	private String itemName;//经销商
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
	 *@return: java.lang.String  单据类型
	 */
	
	@Column(name ="TRANTYPE",nullable=true,length=32)
	public java.lang.String getTranType(){
		return this.tranType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据类型
	 */
	public void setTranType(java.lang.String tranType){
		this.tranType = tranType;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  单据日期
	 */
	
	@Column(name ="DATE",nullable=true,length=32)
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
	public java.lang.String getBillNo(){
		return this.billNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据编号
	 */
	public void setBillNo(java.lang.String billNo){
		this.billNo = billNo;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  上游经销商或总部
	 */
	
	@Column(name ="ITEMID",nullable=true,length=32)
	public java.lang.String getItemId(){
		return this.itemId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  上游经销商或总部
	 */
	public void setItemId(java.lang.String itemId){
		this.itemId = itemId;
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
	public java.lang.String getMobilePhone(){
		return this.mobilePhone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机号码
	 */
	public void setMobilePhone(java.lang.String mobilePhone){
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
	public java.lang.String getEmpId(){
		return this.empId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  经办人
	 */
	public void setEmpId(java.lang.String empId){
		this.empId = empId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门 
	 */
	
	@Column(name ="DEPTID",nullable=true,length=32)
	public java.lang.String getDeptId(){
		return this.deptId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门 
	 */
	public void setDeptId(java.lang.String deptId){
		this.deptId = deptId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库 
	 */
	
	@Column(name ="STOCKID",nullable=true,length=32)
	public String getrStockId() {
		return rStockId;
	}
	public void setrStockId(String rStockId) {
		this.rStockId = rStockId;
	}
	@Transient
	public String getrStockName() {
		return rStockName;
	}
	public void setrStockName(String rStockName) {
		this.rStockName = rStockName;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  物流费用
	 */
	
	@Column(name ="FREIGHT",nullable=true,length=32)
	public java.math.BigDecimal getFreight(){
		return this.freight;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  物流费用
	 */
	public void setFreight(java.math.BigDecimal freight){
		this.freight = freight;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  单据金额
	 */
	
	@Column(name ="AMOUNT",nullable=true,length=32)
	public java.math.BigDecimal getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  单据金额
	 */
	public void setAmount(java.math.BigDecimal amount){
		this.amount = amount;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  应收金额
	 */
	
	@Column(name ="ALLAMOUNT",nullable=true,length=32)
	public java.math.BigDecimal getAllAmount(){
		return this.allAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  应收金额
	 */
	public void setAllAmount(java.math.BigDecimal allAmount){
		this.allAmount = allAmount;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  重量
	 */
	
	@Column(name ="WEIGHT",nullable=true,length=32)
	public java.math.BigDecimal getWeight(){
		return this.weight;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  重量
	 */
	public void setWeight(java.math.BigDecimal weight){
		this.weight = weight;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单类型
	 */
	
	@Column(name ="CLASSID_SRC",nullable=true,length=32)
	public java.lang.String getClassIdSrc(){
		return this.classIdSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单类型
	 */
	public void setClassIdSrc(java.lang.String classIdSrc){
		this.classIdSrc = classIdSrc;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单主键
	 */
	
	@Column(name ="INTERID_SRC",nullable=true,length=32)
	public java.lang.String getInterIdSrc(){
		return this.interIdSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单主键
	 */
	public void setInterIdSrc(java.lang.String interIdSrc){
		this.interIdSrc = interIdSrc;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单编号
	 */
	
	@Column(name ="BILLNO_SRC",nullable=true,length=32)
	public java.lang.String getBillNoSrc(){
		return this.billNoSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单编号
	 */
	public void setBillNoSrc(java.lang.String billNoSrc){
		this.billNoSrc = billNoSrc;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核人
	 */
	
	@Column(name ="CHECKERID",nullable=true,length=32)
	public java.lang.String getCheckerId(){
		return this.checkerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核人
	 */
	public void setCheckerId(java.lang.String checkerId){
		this.checkerId = checkerId;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  制单人
	 */
	
	@Column(name ="BILLERID ",nullable=true,length=32)
	public java.lang.String getBillerId (){
		return this.billerId ;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  制单人
	 */
	public void setBillerId (java.lang.String billerId ){
		this.billerId  = billerId ;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  审核日期
	 */
	
	@Column(name ="CHECKDATE",nullable=true,length=32)
	public java.util.Date getCheckDate(){
		return this.checkDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  审核日期
	 */
	public void setCheckDate(java.util.Date checkDate){
		this.checkDate = checkDate;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  审核状态
	 */
	
	@Column(name ="CHECKSTATE ",nullable=true,length=32)
	public java.lang.Integer getCheckState (){
		return this.checkState ;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  审核状态
	 */
	public void setCheckState (java.lang.Integer checkState ){
		this.checkState  = checkState ;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  作废标记
	 */
	
	@Column(name ="CANCELLATION",nullable=true,length=32)
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
	 *方法: 取得javax.xml.soap.Text
	 *@return: javax.xml.soap.Text  摘要
	 */
	
	@Column(name ="EXPLANATION",nullable=true,length=1000)
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
	public java.lang.String getSonId(){
		return this.sonId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分支机构
	 */
	public void setSonId(java.lang.String sonId){
		this.sonId = sonId;
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
	public String getSonName() {
		return sonName;
	}
	public void setSonName(String sonName) {
		this.sonName = sonName;
	}
	@Transient
	public String getBillerName() {
		return billerName;
	}
	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}
	@Transient
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	@Transient
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	@Transient
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}
