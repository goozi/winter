
package com.qihang.buss.sc.sales.page;
import com.qihang.buss.sc.sales.entity.TScPoStockbillEntity;
import com.qihang.buss.sc.sales.entity.TScPoStockbillentryEntity;

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
 * @Description: 采购出入库单
 * @author onlineGenerator
 * @date 2016-07-12 10:47:57
 * @version V1.0   
 *
 */
public class TScPoStockbillPage implements java.io.Serializable {
	/**保存-采购出入库单明细表*/
	private List<TScPoStockbillentryEntity> tScPoStockbillentryList = new ArrayList<TScPoStockbillentryEntity>();
	public List<TScPoStockbillentryEntity> getTScPoStockbillentryList() {
		return tScPoStockbillentryList;
	}
	public void setTScPoStockbillentryList(List<TScPoStockbillentryEntity> tScPoStockbillentryList) {
		this.tScPoStockbillentryList = tScPoStockbillentryList;
	}

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
	/**单据编号*/
	private java.lang.String billNo;
	/**单据日期*/
	private java.util.Date date;
	/**供应商*/
	private java.lang.String itemId;
	/**经办人*/
	private java.lang.String empId;
	/**部门*/
	private java.lang.String deptId;
	/**仓库*/
	private java.lang.String stockId;
	/**应付金额*/
	private java.lang.Double allAmount;
	/**优惠金额*/
	private java.lang.Double rebateAmount;
	/**本次付款*/
	private java.lang.Double curPayAmount;
	/**结算账户*/
	private java.lang.String accountId;
	/**联系人*/
	private java.lang.String contact;
	/**手机*/
	private java.lang.String mobilePhone;
	/**电话*/
	private java.lang.String phone;
	/**传真*/
	private java.lang.String fax;
	/**联系地址*/
	private java.lang.String address;
	/**单据金额*/
	private java.lang.String amount;
	/**付款金额*/
	private java.lang.String checkAmount;
	/**源单类型*/
	private java.lang.String classIDSrc;
	/**源单主键*/
	private java.lang.String idSrc;
	/**源单编号*/
	private java.lang.String billNoSrc;
	/**制单人*/
	private java.lang.String billerID;
	/**审核人*/
	private java.lang.String checkerId;
	/**审核日期*/
	private java.util.Date checkDate;
	/**审核状态*/
	private java.lang.Integer checkState;
	/**作废标记*/
	private java.lang.Integer cancellation;
	/**摘要*/
	private java.lang.String explanation;
	/**分支机构*/
	private java.lang.String sonId;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
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
	 *@return: java.lang.String  单据编号
	 */
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
	public java.lang.String getItemId(){
		return this.itemId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  供应商
	 */
	public void setItemId(java.lang.String itemId){
		this.itemId = itemId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  经办人
	 */
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
	public java.lang.String getStockId(){
		return this.stockId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库
	 */
	public void setStockId(java.lang.String stockId){
		this.stockId = stockId;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  应付金额
	 */
	public java.lang.Double getAllAmount(){
		return this.allAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  应付金额
	 */
	public void setAllAmount(java.lang.Double allAmount){
		this.allAmount = allAmount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  优惠金额
	 */
	public java.lang.Double getRebateAmount(){
		return this.rebateAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  优惠金额
	 */
	public void setRebateAmount(java.lang.Double rebateAmount){
		this.rebateAmount = rebateAmount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  本次付款
	 */
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结算账户
	 */
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
	public java.lang.String getMobilePhone(){
		return this.mobilePhone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机
	 */
	public void setMobilePhone(java.lang.String mobilePhone){
		this.mobilePhone = mobilePhone;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单据金额
	 */
	public java.lang.String getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据金额
	 */
	public void setAmount(java.lang.String amount){
		this.amount = amount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  付款金额
	 */
	public java.lang.String getCheckAmount(){
		return this.checkAmount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  付款金额
	 */
	public void setCheckAmount(java.lang.String checkAmount){
		this.checkAmount = checkAmount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单类型
	 */
	public java.lang.String getClassIDSrc(){
		return this.classIDSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单类型
	 */
	public void setClassIDSrc(java.lang.String classIDSrc){
		this.classIDSrc = classIDSrc;
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
	 *@return: java.lang.String  制单人
	 */
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核人
	 */
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  审核日期
	 */
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
}
