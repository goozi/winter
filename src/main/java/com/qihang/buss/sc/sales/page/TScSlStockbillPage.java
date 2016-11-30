
package com.qihang.buss.sc.sales.page;
import com.qihang.buss.sc.sales.entity.TScSlStockbillEntity;
import com.qihang.buss.sc.sales.entity.TScSlStockbillentryEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;


/**   
 * @Title: Entity
 * @Description: 销售出库单
 * @author onlineGenerator
 * @date 2016-08-01 15:25:28
 * @version V1.0   
 *
 */
public class TScSlStockbillPage implements java.io.Serializable {
	/**保存-销售出库单明细*/
	private List<TScSlStockbillentryEntity> tScSlStockbillentryList = new ArrayList<TScSlStockbillentryEntity>();
	public List<TScSlStockbillentryEntity> getTScSlStockbillentryList() {
		return tScSlStockbillentryList;
	}
	public void setTScSlStockbillentryList(List<TScSlStockbillentryEntity> tScSlStockbillentryList) {
		this.tScSlStockbillentryList = tScSlStockbillentryList;
	}

	/**id*/
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
	private java.lang.String trantype;
	/**单据编号*/
	private java.lang.String billno;
	/**单据日期*/
	private java.util.Date date;
	/**供应商*/
	private java.lang.String itemid;
	/**经办人*/
	private java.lang.String empid;
	/**部门*/
	private java.lang.String deptid;
	/**仓库*/
	private java.lang.String stockid;
	/**应付金额*/
	private java.lang.Double allamount;
	/**优惠金额*/
	private java.lang.Double rebateamount;
	/**本次付款*/
	private java.lang.Double curpayamount;
	/**结算账户*/
	private java.lang.String accountid;
	/**联系人*/
	private java.lang.String contact;
	/**手机*/
	private java.lang.String mobilephone;
	/**电话*/
	private java.lang.String phone;
	/**传真*/
	private java.lang.String fax;
	/**联系地址*/
	private java.lang.String address;
	/**单据金额*/
	private java.lang.Double amount;
	/**付款金额*/
	private java.lang.Double checkamount;
	/**源单类型*/
	private java.lang.String classidSrc;
	/**源单主键*/
	private java.lang.String idSrc;
	/**源单编号*/
	private java.lang.String billnoSrc;
	/**制单人*/
	private java.lang.String billerid;
	/**审核人*/
	private java.lang.String checkerid;
	/**审核日期*/
	private java.util.Date checkdate;
	/**审核状态*/
	private java.lang.Integer checkstate;
	/**作废标记*/
	private java.lang.Integer cancellation;
	/**摘要*/
	private java.lang.String explanation;
	/**分支机构*/
	private java.lang.String sonid;
	/**版本号*/
	private java.lang.Integer version;
	/**重量*/
	private java.lang.Double weight;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名称
	 */
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
	public java.lang.String getTrantype(){
		return this.trantype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据类型
	 */
	public void setTrantype(java.lang.String trantype){
		this.trantype = trantype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单据编号
	 */
	public java.lang.String getBillno(){
		return this.billno;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据编号
	 */
	public void setBillno(java.lang.String billno){
		this.billno = billno;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  单据日期
	 */
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
	 *@return: java.lang.String  供应商
	 */
	public java.lang.String getItemid(){
		return this.itemid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  供应商
	 */
	public void setItemid(java.lang.String itemid){
		this.itemid = itemid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  经办人
	 */
	public java.lang.String getEmpid(){
		return this.empid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  经办人
	 */
	public void setEmpid(java.lang.String empid){
		this.empid = empid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门
	 */
	public java.lang.String getDeptid(){
		return this.deptid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门
	 */
	public void setDeptid(java.lang.String deptid){
		this.deptid = deptid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库
	 */
	public java.lang.String getStockid(){
		return this.stockid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库
	 */
	public void setStockid(java.lang.String stockid){
		this.stockid = stockid;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  应付金额
	 */
	public java.lang.Double getAllamount(){
		return this.allamount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  应付金额
	 */
	public void setAllamount(java.lang.Double allamount){
		this.allamount = allamount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  优惠金额
	 */
	public java.lang.Double getRebateamount(){
		return this.rebateamount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  优惠金额
	 */
	public void setRebateamount(java.lang.Double rebateamount){
		this.rebateamount = rebateamount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  本次付款
	 */
	public java.lang.Double getCurpayamount(){
		return this.curpayamount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  本次付款
	 */
	public void setCurpayamount(java.lang.Double curpayamount){
		this.curpayamount = curpayamount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结算账户
	 */
	public java.lang.String getAccountid(){
		return this.accountid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结算账户
	 */
	public void setAccountid(java.lang.String accountid){
		this.accountid = accountid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人
	 */
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
	 *@return: java.lang.String  手机
	 */
	public java.lang.String getMobilephone(){
		return this.mobilephone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机
	 */
	public void setMobilephone(java.lang.String mobilephone){
		this.mobilephone = mobilephone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话
	 */
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
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  单据金额
	 */
	public java.lang.Double getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  单据金额
	 */
	public void setAmount(java.lang.Double amount){
		this.amount = amount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  付款金额
	 */
	public java.lang.Double getCheckamount(){
		return this.checkamount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  付款金额
	 */
	public void setCheckamount(java.lang.Double checkamount){
		this.checkamount = checkamount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单类型
	 */
	public java.lang.String getClassidSrc(){
		return this.classidSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单类型
	 */
	public void setClassidSrc(java.lang.String classidSrc){
		this.classidSrc = classidSrc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单主键
	 */
	public java.lang.String getIdSrc(){
		return this.idSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单主键
	 */
	public void setIdSrc(java.lang.String idSrc){
		this.idSrc = idSrc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单编号
	 */
	public java.lang.String getBillnoSrc(){
		return this.billnoSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单编号
	 */
	public void setBillnoSrc(java.lang.String billnoSrc){
		this.billnoSrc = billnoSrc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  制单人
	 */
	public java.lang.String getBillerid(){
		return this.billerid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  制单人
	 */
	public void setBillerid(java.lang.String billerid){
		this.billerid = billerid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核人
	 */
	public java.lang.String getCheckerid(){
		return this.checkerid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核人
	 */
	public void setCheckerid(java.lang.String checkerid){
		this.checkerid = checkerid;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  审核日期
	 */
	public java.util.Date getCheckdate(){
		return this.checkdate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  审核日期
	 */
	public void setCheckdate(java.util.Date checkdate){
		this.checkdate = checkdate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  审核状态
	 */
	public java.lang.Integer getCheckstate(){
		return this.checkstate;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  审核状态
	 */
	public void setCheckstate(java.lang.Integer checkstate){
		this.checkstate = checkstate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  作废标记
	 */
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
	public java.lang.String getSonid(){
		return this.sonid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分支机构
	 */
	public void setSonid(java.lang.String sonid){
		this.sonid = sonid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  版本号
	 */
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
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  重量
	 */
	public java.lang.Double getWeight(){
		return this.weight;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  重量
	 */
	public void setWeight(java.lang.Double weight){
		this.weight = weight;
	}
}
