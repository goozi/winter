package com.qihang.buss.sc.distribution.entity;

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
 * @Description: 物流信息表
 * @author onlineGenerator
 * @date 2016-08-12 16:00:03
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_DRP_Logistical", schema = "")
@SuppressWarnings("serial")
public class TScDrpLogisticalEntity implements java.io.Serializable {
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
	/**主表主键*/
	private java.lang.String fid;
	/**单据类型*/
	private java.lang.Integer tranType;
	/**承担方*/
	@Excel(name="承担方")
	private java.lang.Integer bears;
	/**买家承担方式*/
	@Excel(name="买家承担方式")
	private java.lang.Integer buyType;
	/**物流费用*/
	@Excel(name="物流费用")
	private java.lang.Double freight;
	/**本次付款*/
	@Excel(name="本次付款")
	private java.math.BigDecimal curPayAmount;
	/**物流公司*/
	@Excel(name="物流公司")
	private java.lang.String logisticalID;
	/**物流单号*/
	@Excel(name="物流单号")
	private java.lang.String logisticalNo;
	/**结算账号*/
	private java.lang.String accountID;
	/**执行金额*/
	private java.math.BigDecimal checkAmount;

	private java.lang.String accountName;
	private java.lang.String logisticalName;

	/**逻辑删除标记*/
	private java.lang.Integer deleted;
	/**禁用标记*/
	private java.lang.Integer disabled;
	/**版本号*/
	private java.lang.Integer version;
	
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
	 *@return: java.lang.String  主表主键
	 */
	@Column(name ="FID",nullable=true,length=32)
	public java.lang.String getFid(){
		return this.fid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主表主键
	 */
	public void setFid(java.lang.String fid){
		this.fid = fid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  单据类型
	 */
	@Column(name ="TRANTYPE",nullable=true,length=32)
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  承担方
	 */
	@Column(name ="BEARS",nullable=true,length=32)
	public java.lang.Integer getBears(){
		return this.bears;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  承担方
	 */
	public void setBears(java.lang.Integer bears){
		this.bears = bears;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  买家承担方式
	 */
	@Column(name ="BUYTYPE",nullable=true,length=32)
	public java.lang.Integer getBuyType(){
		return this.buyType;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  买家承担方式
	 */
	public void setBuyType(java.lang.Integer buyType){
		this.buyType = buyType;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  物流费用
	 */
	@Column(name ="FREIGHT",nullable=true,length=32)
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
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本次付款
	 */
	@Column(name ="CURPAYAMOUNT",nullable=true,length=32)
	public java.math.BigDecimal getCurPayAmount(){
		return this.curPayAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本次付款
	 */
	public void setCurPayAmount(java.math.BigDecimal curPayAmount){
		this.curPayAmount = curPayAmount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  物流公司
	 */
	@Column(name ="LOGISTICALID",nullable=true,length=32)
	public java.lang.String getLogisticalID(){
		return this.logisticalID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  物流公司
	 */
	public void setLogisticalID(java.lang.String logisticalID){
		this.logisticalID = logisticalID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  物流单号
	 */
	@Column(name ="LOGISTICALNO",nullable=true,length=50)
	public java.lang.String getLogisticalNo(){
		return this.logisticalNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  物流单号
	 */
	public void setLogisticalNo(java.lang.String logisticalNo){
		this.logisticalNo = logisticalNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结算账号
	 */
	@Column(name ="ACCOUNTID",nullable=true,length=32)
	public java.lang.String getAccountID(){
		return this.accountID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结算账号
	 */
	public void setAccountID(java.lang.String accountID){
		this.accountID = accountID;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  执行金额
	 */
	@Column(name ="CHECKAMOUNT",nullable=true,length=32)
	public java.math.BigDecimal getCheckAmount(){
		return this.checkAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  执行金额
	 */
	public void setCheckAmount(java.math.BigDecimal checkAmount){
		this.checkAmount = checkAmount;
	}
	@Transient
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	@Transient
	public String getLogisticalName() {
		return logisticalName;
	}

	public void setLogisticalName(String logisticalName) {
		this.logisticalName = logisticalName;
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
}
