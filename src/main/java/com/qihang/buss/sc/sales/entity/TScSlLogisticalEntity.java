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
 * @Description: 物流信息表
 * @author onlineGenerator
 * @date 2016-08-01 15:25:53
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_SL_Logistical", schema = "")
@SuppressWarnings("serial")
public class TScSlLogisticalEntity implements java.io.Serializable {
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
	/**父id*/
	private java.lang.String fid;
	/**单据类型*/
	private java.lang.String tranType;
	/**物流公司*/
	@Excel(name="物流公司")
	private java.lang.String logisticalId;
	/**物流单号*/
	@Excel(name="物流单号")
	private java.lang.String logisticalNo;
	/**承担方*/
	@Excel(name="承担方")
	private java.lang.Integer bears;
	/**买家承担方式*/
	@Excel(name="买家承担方式")
	private java.lang.Integer buyType;
	/**物流费用*/
	@Excel(name="物流费用")
	private java.lang.Double freight;
	/**结算账户*/
	@Excel(name="结算账户")
	private java.lang.String accountId;
	/**本次付款*/
	@Excel(name="本次付款")
	private java.lang.Double curPayAmount;
	/**执行金额*/
	@Excel(name="执行金额")
	private java.lang.Double checkAmount;

	private Integer version;
	private String logisticalName;//物流公司名称
	private String accountName;//结算账户名称



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
	 *@return: java.lang.String  父id
	 */
	@Column(name ="FID",nullable=true,length=32)
	public java.lang.String getFid(){
		return this.fid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  父id
	 */
	public void setFid(java.lang.String fid){
		this.fid = fid;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  物流公司
	 */
	@Column(name ="LOGISTICALID",nullable=true,length=32)
	public java.lang.String getLogisticalId(){
		return this.logisticalId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  物流公司
	 */
	public void setLogisticalId(java.lang.String logisticalId){
		this.logisticalId = logisticalId;
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  承担方
	 */
	@Column(name ="BEARS",nullable=true,length=1)
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
	@Column(name ="BUYTYPE",nullable=true,length=1)
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
	@Column(name ="FREIGHT",nullable=true,scale=10,length=20)
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结算账户
	 */
	@Column(name ="ACCOUNTID",nullable=true,length=32)
	public java.lang.String getAccountId(){
		return this.accountId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结算账户
	 */
	public void setAccountId(java.lang.String accountId){
		this.accountId = accountId;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  本次付款
	 */
	@Column(name ="CURPAYAMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getCurPayAmount(){
		return this.curPayAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  本次付款
	 */
	public void setCurPayAmount(java.lang.Double curPayAmount){
		this.curPayAmount = curPayAmount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  执行金额
	 */
	@Column(name ="CHECKAMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getCheckAmount(){
		return this.checkAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  执行金额
	 */
	public void setCheckAmount(java.lang.Double checkAmount){
		this.checkAmount = checkAmount;
	}

	@Transient
	public String getLogisticalName() {
		return logisticalName;
	}

	public void setLogisticalName(String logisticalName) {
		this.logisticalName = logisticalName;
	}

	@Transient
	public String getAccountName() {
		return accountName;
	}

	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	@Version
	@Column(name ="version",nullable=true,scale=10,length=20)
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
}
