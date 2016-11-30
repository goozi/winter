
package com.qihang.buss.sc.sales.page;
import com.qihang.buss.sc.sales.entity.TScOrderEntity;
import com.qihang.buss.sc.sales.entity.TScOrderentryEntity;

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
 * @Description: 销售订单
 * @author onlineGenerator
 * @date 2016-06-16 11:35:43
 * @version V1.0   
 *
 */
public class TScOrderPage implements java.io.Serializable {
	/**保存-销售订单从表*/
	private List<TScOrderentryEntity> tScOrderentryList = new ArrayList<TScOrderentryEntity>();
	public List<TScOrderentryEntity> getTScOrderentryList() {
		return tScOrderentryList;
	}
	public void setTScOrderentryList(List<TScOrderentryEntity> tScOrderentryList) {
		this.tScOrderentryList = tScOrderentryList;
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
	/**版本号*/
	private java.lang.Integer version;
	/**单据类型*/
	private java.lang.Integer tranType;
	/**单据日期*/
	private java.util.Date date;
	/**单据编号*/
	private java.lang.String billNo;
	/**客户*/
	private java.lang.String itemID;
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
	/**经办人*/
	private java.lang.String empID;
	/**部门*/
	private java.lang.String deptID;
	/**仓库*/
	private java.lang.String stockID;
	/**付款方式*/
	private java.lang.String fetchStyle;
	/**预收金额*/
	private java.math.BigDecimal preAmount;
	/**订单来源*/
	private java.math.BigDecimal mall;
	/**商城单号*/
	private java.math.BigDecimal mallOrderID;
	/**优惠金额*/
	private java.math.BigDecimal rebateAmount;
	/**物流费用*/
	private java.math.BigDecimal freight;
	/**重量*/
	private java.math.BigDecimal weight;
	/**订单金额*/
	private java.math.BigDecimal amount;
	/**应收金额*/
	private java.math.BigDecimal allAmount;
	/**执行金额*/
	private java.math.BigDecimal checkAmount;
	/**源单类型*/
	private java.lang.String classIDSrc;
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
	/**关闭标记*/
	private java.lang.String closed;
	/**自动关闭标记*/
	private java.lang.String autoFlag;
	/**作废标记*/
	private java.lang.String cancellation;
	/**摘要*/
	private java.lang.String explanation;
	/**分支机构*/
	private java.lang.String sonID;
	
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  单据类型
	 */
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
	 *@return: java.lang.String  客户
	 */
	public java.lang.String getItemID(){
		return this.itemID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户
	 */
	public void setItemID(java.lang.String itemID){
		this.itemID = itemID;
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
	 *@return: java.lang.String  付款方式
	 */
	public java.lang.String getFetchStyle(){
		return this.fetchStyle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  付款方式
	 */
	public void setFetchStyle(java.lang.String fetchStyle){
		this.fetchStyle = fetchStyle;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  预收金额
	 */
	public java.math.BigDecimal getPreAmount(){
		return this.preAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  预收金额
	 */
	public void setPreAmount(java.math.BigDecimal preAmount){
		this.preAmount = preAmount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  订单来源
	 */
	public java.math.BigDecimal getMall(){
		return this.mall;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  订单来源
	 */
	public void setMall(java.math.BigDecimal mall){
		this.mall = mall;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  商城单号
	 */
	public java.math.BigDecimal getMallOrderID(){
		return this.mallOrderID;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  商城单号
	 */
	public void setMallOrderID(java.math.BigDecimal mallOrderID){
		this.mallOrderID = mallOrderID;
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
	 *@return: java.math.BigDecimal  订单金额
	 */
	public java.math.BigDecimal getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  订单金额
	 */
	public void setAmount(java.math.BigDecimal amount){
		this.amount = amount;
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
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  执行金额
	 */
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
	 *@return: java.lang.String  关闭标记
	 */
	public java.lang.String getClosed(){
		return this.closed;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关闭标记
	 */
	public void setClosed(java.lang.String closed){
		this.closed = closed;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  自动关闭标记
	 */
	public java.lang.String getAutoFlag(){
		return this.autoFlag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  自动关闭标记
	 */
	public void setAutoFlag(java.lang.String autoFlag){
		this.autoFlag = autoFlag;
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
}
