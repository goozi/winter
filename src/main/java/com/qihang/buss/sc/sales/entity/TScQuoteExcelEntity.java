package com.qihang.buss.sc.sales.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import com.qihang.winter.poi.excel.annotation.ExcelCollection;
import com.qihang.winter.poi.excel.annotation.ExcelEntity;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

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
public class TScQuoteExcelEntity implements java.io.Serializable {
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
	/**单据编号*/
	@Excel(name="单据编号")
	private String billNo;
	/**单据日期*/
	@Excel(name="单据日期",format = "yyyy-MM-dd")
	private Date date;
	/**经办人*/
	private String empID;
	private String empName;
	/**经办人和实体的关系*/
	@ExcelEntity(id="id",name="name")
	private TScEmpToOrderEntity empToOrderEntity;
	/**部门*/
	private String deptID;
	private String deptName;
	/**部门实体和部门id的关系*/
	@ExcelEntity(id="id",name="name")
	private TScDepartmentToOrderEntity departmentToOrderEntity;
	/**生效日期*/
  @Excel(name="生效日期",format = "yyyy-MM-dd")
	private Date inureDate;
	/**审核人*/
	/**制单人*/
//  @Excel(name="制单人")
	private String billerID;
	/**作废标记*/
	private Integer cancellation;
	/**分支机构*/
//  @Excel(name="分支机构")
	private String sonID;
	/**逻辑删除*/
	private Integer deleted;
	/**版本号*/
	private Integer version;

	private Integer status;//单据状态
	private String auditDate;//审核日期
	private String auditorName;//审核人


	/**销售订购单和商品的关系*/
	@ExcelCollection(name="商品")
	private List<TScQuoteitemsExcelEntity> listItems;

	@Excel(name="摘要")
	private String explanation;


	@ExcelEntity(id="id",name="name")
	private TSBaseUserForPoOrder biller;

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "billerId",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TSBaseUserForPoOrder getBiller() {
		return biller;
	}

	public void setBiller(TSBaseUserForPoOrder biller) {
		this.biller = biller;
	}

	@Excel(name="审核人")
	private String checkerID;
	/**审核日期*/
	@Excel(name="审核日期",format = "yyyy-MM-dd")
	private Date checkDate;
	/**审核状态*/
	@Excel(name="审核状态",replace = {"未审核_0","审核中_1","已审核_2"})
	private Integer checkState;

	@Excel(name="分支机构")
	private String sonName;
//	@ExcelEntity(id="id",name="name")
//	private TSDepart sonInfo;
//
//	@OneToOne(cascade = CascadeType.REFRESH)
//	@JoinColumn(name = "sonId",referencedColumnName = "ID")
//	@NotFound(action = NotFoundAction.IGNORE)
//	public TSDepart getSonInfo() {
//		return sonInfo;
//	}
//
//	public void setSonInfo(TSDepart sonInfo) {
//		this.sonInfo = sonInfo;
//	}

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

	@Column(name ="DATE",nullable=true,length=32)
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
	 *@return: java.lang.String  经办人
	 */

//	@Column(name ="EMPID",nullable=true,length=32)
	@Transient
	public String getEmpID(){
		return this.empID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  经办人
	 */
	public void setEmpID(String empID){
		this.empID = empID;
	}

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "EMPID",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TScEmpToOrderEntity getEmpToOrderEntity() {
		return empToOrderEntity;
	}

	public void setEmpToOrderEntity(TScEmpToOrderEntity empToOrderEntity) {
		this.empToOrderEntity = empToOrderEntity;
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

//	@Column(name ="DEPTID",nullable=true,length=32)
	@Transient
	public String getDeptID(){
		return this.deptID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门
	 */
	public void setDeptID(String deptID){
		this.deptID = deptID;
	}

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "DEPTID",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TScDepartmentToOrderEntity getDepartmentToOrderEntity() {
		return departmentToOrderEntity;
	}

	public void setDepartmentToOrderEntity(TScDepartmentToOrderEntity departmentToOrderEntity) {
		this.departmentToOrderEntity = departmentToOrderEntity;
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
	public Date getInureDate(){
		return this.inureDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  生效日期
	 */
	public void setInureDate(Date inureDate){
		this.inureDate = inureDate;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核人
	 */

	@Column(name ="CHECKERID",nullable=true,length=32)
	public String getCheckerID(){
		return this.checkerID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核人
	 */
	public void setCheckerID(String checkerID){
		this.checkerID = checkerID;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  制单人
	 */

	@Transient
	public String getBillerID(){
		return this.billerID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  制单人
	 */
	public void setBillerID(String billerID){
		this.billerID = billerID;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  审核日期
	 */

	@Column(name ="CHECKDATE",nullable=true,length=32)
	public Date getCheckDate(){
		return this.checkDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  审核日期
	 */
	public void setCheckDate(Date checkDate){
		this.checkDate = checkDate;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  审核状态
	 */

	@Column(name ="CHECKSTATE",nullable=true,length=32)
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

	@Column(name ="CANCELLATION",nullable=true,length=32)
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

	@Column(name ="sonID",nullable=true,length=32)
	public String getSonID(){
		return this.sonID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分支机构
	 */
	public void setSonID(String sonID){
		this.sonID = sonID;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  逻辑删除
	 */

	@Column(name ="DELETED",nullable=true,length=32)
	public Integer getDeleted(){
		return this.deleted;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  逻辑删除
	 */
	public void setDeleted(Integer deleted){
		this.deleted = deleted;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  版本号
	 */

	@Column(name ="VERSION",nullable=true,length=32)
	public Integer getVersion(){
		return this.version;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  版本号
	 */
	public void setVersion(Integer version){
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

	@OneToMany(mappedBy = "quoteExcelEntity",cascade = CascadeType.ALL)
	public List<TScQuoteitemsExcelEntity> getListItems() {
		return listItems;
	}

	public void setListItems(List<TScQuoteitemsExcelEntity> listItems) {
		this.listItems = listItems;
	}

	@Transient
	public String getSonName() {
		return sonName;
	}

	public void setSonName(String sonName) {
		this.sonName = sonName;
	}
}
