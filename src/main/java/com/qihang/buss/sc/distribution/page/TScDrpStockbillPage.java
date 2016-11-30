
package com.qihang.buss.sc.distribution.page;
import com.qihang.buss.sc.distribution.entity.TScDrpStockbillEntity;
import com.qihang.buss.sc.distribution.entity.TScDrpStockbillentryEntity;

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
 * @Description: 发货管理
 * @author onlineGenerator
 * @date 2016-08-08 19:52:05
 * @version V1.0   
 *
 */
public class TScDrpStockbillPage implements java.io.Serializable {
	/**保存-商品信息*/
	private List<TScDrpStockbillentryEntity> tScDrpStockbillentryList = new ArrayList<TScDrpStockbillentryEntity>();
	public List<TScDrpStockbillentryEntity> getTScDrpStockbillentryList() {
		return tScDrpStockbillentryList;
	}
	public void setTScDrpStockbillentryList(List<TScDrpStockbillentryEntity> tScDrpStockbillentryList) {
		this.tScDrpStockbillentryList = tScDrpStockbillentryList;
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
	private java.lang.String date;
	/**下游经销商*/
	private java.lang.String dealerID;
	/**经办人*/
	private java.lang.String empID;
	/**部门*/
	private java.lang.String deptID;
	/**仓库*/
	private java.lang.String stockID;
	/**联系人*/
	private java.lang.String contact;
	/**手机号码*/
	private java.lang.String mobilePhone;
	/**电话*/
	private java.lang.String phone;
	/**传真*/
	private java.lang.String fax;
	/**联系地址*/
	private java.lang.String address;
	/**优惠金额*/
	private java.math.BigDecimal rebateAmount;
	/**物流费用*/
	private java.math.BigDecimal freight;
	/**结算账号*/
	private java.lang.String accountID;
	/**本次收款*/
	private java.math.BigDecimal curPayAmount;
	/**重量*/
	private java.math.BigDecimal weight;
	/**应收金额*/
	private java.math.BigDecimal allAmount;
	/**源单类型*/
	private java.lang.Integer classIDSrc;
	/**分支机构*/
	private java.lang.String sonID;
	/**摘要*/
	private java.lang.String explanation;
	/**单据金额*/
	private java.math.BigDecimal amount;
	/**收款金额*/
	private java.math.BigDecimal checkAmount;
	/**确认状态*/
	private java.lang.Integer affirmStatus;
	/**确认人*/
	private java.lang.String affirmID;
	/**确认时间*/
	private java.util.Date affirmDate;
	/**丢失金额*/
	private java.math.BigDecimal amountLoss;
	/**源单主键*/
	private java.lang.String interIDSrc;
	/**源单编号*/
	private java.lang.String billNoSrc;
	/**审核人*/
	private java.lang.String checkerID;
	/**制单人*/
	private java.lang.String billerID;
	/**审核日期*/
	private java.util.Date checkDate;
	/**审核状态*/
	private java.lang.String checkState;
	/**作废标记*/
	private java.lang.String cancellation;
	
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单据日期
	 */
	public java.lang.String getDate(){
		return this.date;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据日期
	 */
	public void setDate(java.lang.String date){
		this.date = date;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  下游经销商
	 */
	public java.lang.String getDealerID(){
		return this.dealerID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  下游经销商
	 */
	public void setDealerID(java.lang.String dealerID){
		this.dealerID = dealerID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  经办人
	 */
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门
	 */
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库
	 */
	public java.lang.String getStockID(){
		return this.stockID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库
	 */
	public void setStockID(java.lang.String stockID){
		this.stockID = stockID;
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
	 *@return: java.lang.String  手机号码
	 */
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
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  优惠金额
	 */
	public java.math.BigDecimal getRebateAmount(){
		return this.rebateAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  优惠金额
	 */
	public void setRebateAmount(java.math.BigDecimal rebateAmount){
		this.rebateAmount = rebateAmount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  物流费用
	 */
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结算账号
	 */
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
	 *@return: java.math.BigDecimal  本次收款
	 */
	public java.math.BigDecimal getCurPayAmount(){
		return this.curPayAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本次收款
	 */
	public void setCurPayAmount(java.math.BigDecimal curPayAmount){
		this.curPayAmount = curPayAmount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  重量
	 */
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
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  应收金额
	 */
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  源单类型
	 */
	public java.lang.Integer getClassIDSrc(){
		return this.classIDSrc;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  源单类型
	 */
	public void setClassIDSrc(java.lang.Integer classIDSrc){
		this.classIDSrc = classIDSrc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分支机构
	 */
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
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  单据金额
	 */
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
	 *@return: java.math.BigDecimal  收款金额
	 */
	public java.math.BigDecimal getCheckAmount(){
		return this.checkAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  收款金额
	 */
	public void setCheckAmount(java.math.BigDecimal checkAmount){
		this.checkAmount = checkAmount;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  确认状态
	 */
	public java.lang.Integer getAffirmStatus(){
		return this.affirmStatus;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  确认状态
	 */
	public void setAffirmStatus(java.lang.Integer affirmStatus){
		this.affirmStatus = affirmStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  确认人
	 */
	public java.lang.String getAffirmID(){
		return this.affirmID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  确认人
	 */
	public void setAffirmID(java.lang.String affirmID){
		this.affirmID = affirmID;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  确认时间
	 */
	public java.util.Date getAffirmDate(){
		return this.affirmDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  确认时间
	 */
	public void setAffirmDate(java.util.Date affirmDate){
		this.affirmDate = affirmDate;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  丢失金额
	 */
	public java.math.BigDecimal getAmountLoss(){
		return this.amountLoss;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  丢失金额
	 */
	public void setAmountLoss(java.math.BigDecimal amountLoss){
		this.amountLoss = amountLoss;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单主键
	 */
	public java.lang.String getInterIDSrc(){
		return this.interIDSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单主键
	 */
	public void setInterIDSrc(java.lang.String interIDSrc){
		this.interIDSrc = interIDSrc;
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
	 *@return: java.lang.String  审核人
	 */
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核状态
	 */
	public java.lang.String getCheckState(){
		return this.checkState;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核状态
	 */
	public void setCheckState(java.lang.String checkState){
		this.checkState = checkState;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  作废标记
	 */
	public java.lang.String getCancellation(){
		return this.cancellation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  作废标记
	 */
	public void setCancellation(java.lang.String cancellation){
		this.cancellation = cancellation;
	}
}
