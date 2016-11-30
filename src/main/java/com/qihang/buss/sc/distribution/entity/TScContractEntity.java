package com.qihang.buss.sc.distribution.entity;

import com.qihang.buss.sc.baseinfo.entity.TScOrganizationEntity;
import com.qihang.winter.poi.excel.annotation.Excel;
import com.qihang.winter.poi.excel.annotation.ExcelEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;

/**   
 * @Title: Entity
 * @Description: 合同管理
 * @author onlineGenerator
 * @date 2016-07-19 15:21:26
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_Contract", schema = "")
@SuppressWarnings("serial")
public class TScContractEntity implements java.io.Serializable {
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
	private java.lang.Integer trantype;
	/**合同日期*/
	@Excel(name="合同日期",format="yyyy-MM-dd")
	private java.util.Date date;
	/**合同号*/
	@Excel(name="合同号")
	private java.lang.String billNo;

	@Excel(name="经销商")
	private java.lang.String itemIDName;

	/**合同名*/
	@Excel(name="合同名")
	private java.lang.String contractName;
	/**合同内容*/
//	@Excel(name="合同内容")
	private java.lang.String content;
	/**分支机构*/
	private java.lang.String sonID;
	/**分支机构名称**/
	@Excel(name="分支机构")
	private java.lang.String sonName;

	/**逻辑删除标记*/
	private java.lang.Integer deleted;
	/**禁用标记*/
	private java.lang.Integer disabled;
	/**版本号*/
	private java.lang.Integer version;
//	@ExcelEntity(id="id",name="name")
	private TScOrganizationEntity tScOrganizationEntity;
	
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
	@Column(name ="TRANTYPE",nullable=true,length=32)
	public java.lang.Integer getTrantype(){
		return this.trantype;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  单据类型
	 */
	public void setTrantype(java.lang.Integer trantype){
		this.trantype = trantype;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  合同日期
	 */
	@Column(name ="DATE",nullable=true,length=32)
	public java.util.Date getDate(){
		return this.date;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  合同日期
	 */
	public void setDate(java.util.Date date){
		this.date = date;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  合同号
	 */
	@Column(name ="BILLNO",nullable=true,length=50)
	public java.lang.String getBillNo(){
		return this.billNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  合同号
	 */
	public void setBillNo(java.lang.String billNo){
		this.billNo = billNo;
	}
//	/**
//	 *方法: 取得java.lang.String
//	 *@return: java.lang.String  经销商
//	 */
//	@Column(name ="ITEMID",nullable=true,length=50)
//	public java.lang.String getItemID(){
//		return this.itemID;
//	}
//
//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  经销商
//	 */
//	public void setItemID(java.lang.String itemID){
//		this.itemID = itemID;
//	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  合同名
	 */
	@Column(name ="CONTRACTNAME",nullable=true,length=100)
	public java.lang.String getContractName(){
		return this.contractName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  合同名
	 */
	public void setContractName(java.lang.String contractName){
		this.contractName = contractName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  合同内容
	 */
	@Column(name ="CONTENT",nullable=true,length=1000)
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  合同内容
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分支机构
	 */
	@Column(name ="SONID",nullable=true,length=50)
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
	@Column(name ="Deleted")
	public Integer getDeleted() {
		return deleted;
	}
	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}
	@Column(name ="Disabled")
	public Integer getDisabled() {
		return disabled;
	}
	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}
	@Version
	@Column(name ="Version")
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	@Transient
	public String getItemIDName() {
		return itemIDName;
	}
	public void setItemIDName(String itemIDName) {
		this.itemIDName = itemIDName;
	}

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "itemID",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "itemID",nullable=true)
	public TScOrganizationEntity gettScOrganizationEntity() {
		return tScOrganizationEntity;
	}

	public void settScOrganizationEntity(TScOrganizationEntity tScOrganizationEntity) {
		this.tScOrganizationEntity = tScOrganizationEntity;
	}
	@Transient
	public String getSonName() {
		return sonName;
	}

	public void setSonName(String sonName) {
		this.sonName = sonName;
	}
}
