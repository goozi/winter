package com.qihang.buss.sc.sales.entity;
import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.xml.soap.Text;
import java.sql.Blob;

import org.hibernate.annotations.GenericGenerator;
import com.qihang.winter.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 销售报价单
 * @author onlineGenerator
 * @date 2016-06-16 15:22:17
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_Quote", schema = "")
@SuppressWarnings("serial")
public class TScQuoteEntity implements java.io.Serializable {
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
	private java.lang.Integer tranType;
	/**单据日期*/
  @Excel(name="单据日期")
	private java.util.Date date;
	/**单据编号*/
  @Excel(name="单据编号")
	private java.lang.String billNo;
	/**经办人*/
  @Excel(name="经办人")
	private java.lang.String empID;
	private String empName;
	/**部门*/
  @Excel(name="部门")
	private java.lang.String deptID;
	private String deptName;
	/**生效日期*/
  @Excel(name="生效日期")
	private java.util.Date inureDate;
	/**审核人*/
  @Excel(name="审核人")
	private java.lang.String checkerID;
	/**制单人*/
  @Excel(name="制单人")
	private java.lang.String billerID;
	/**审核日期*/
	private java.util.Date checkDate;
	/**审核状态*/
	private java.lang.Integer checkState;
	/**作废标记*/
	private java.lang.Integer cancellation;
	/**摘要*/
  @Excel(name="摘要")
	private java.lang.String explanation;
	/**分支机构*/
  @Excel(name="分支机构")
	private java.lang.String sonID;
	/**逻辑删除*/
	private java.lang.Integer deleted;
	/**版本号*/
	private java.lang.Integer version;

	private Integer status;//单据状态
	private String auditDate;//审核日期
	private String auditorName;//审核人

	private String sonName;//分支机构名称
	private String billerName;//制单人
	
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  单据类型
	 */
	
	@Column(name ="TRANTYPE",nullable=true,length=10)
	public java.lang.Integer getTranType(){
		return this.tranType;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  单据类型
	 */
	public void setTranType(java.lang.Integer tranType){
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
	
	@Column(name ="BILLNO",nullable=true,length=50)
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
	 *@return: java.lang.String  经办人
	 */
	
	@Column(name ="EMPID",nullable=true,length=32)
	public java.lang.String getEmpID(){
		return this.empID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  经办人
	 */
	public void setEmpID(java.lang.String empID){
		this.empID = empID;
	}

	@Transient
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
	public java.lang.String getDeptID(){
		return this.deptID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门
	 */
	public void setDeptID(java.lang.String deptID){
		this.deptID = deptID;
	}

	@Transient
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  生效日期
	 */
	
	@Column(name ="INUREDATE",nullable=true,length=32)
	public java.util.Date getInureDate(){
		return this.inureDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  生效日期
	 */
	public void setInureDate(java.util.Date inureDate){
		this.inureDate = inureDate;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核人
	 */
	
	@Column(name ="CHECKERID",nullable=true,length=32)
	public java.lang.String getCheckerID(){
		return this.checkerID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核人
	 */
	public void setCheckerID(java.lang.String checkerID){
		this.checkerID = checkerID;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  制单人
	 */
	
	@Column(name ="BILLERID",nullable=true,length=32)
	public java.lang.String getBillerID(){
		return this.billerID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  制单人
	 */
	public void setBillerID(java.lang.String billerID){
		this.billerID = billerID;
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
	
	@Column(name ="CHECKSTATE",nullable=true,length=32)
	public java.lang.Integer getCheckState(){
		return this.checkState;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  审核状态
	 */
	public void setCheckState(java.lang.Integer checkState){
		this.checkState = checkState;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  摘要
	 */
	
	@Column(name ="EXPLANATION",nullable=true,length=255)
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
	public java.lang.String getSonID(){
		return this.sonID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分支机构
	 */
	public void setSonID(java.lang.String sonID){
		this.sonID = sonID;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  逻辑删除
	 */
	
	@Column(name ="DELETED",nullable=true,length=32)
	public java.lang.Integer getDeleted(){
		return this.deleted;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  逻辑删除
	 */
	public void setDeleted(java.lang.Integer deleted){
		this.deleted = deleted;
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
}
