
package com.qihang.buss.sc.distribution.page;
import com.qihang.buss.sc.distribution.entity.TScPrcplyEntity;
import com.qihang.buss.sc.distribution.entity.TScPrcplyentry2Entity;
import com.qihang.buss.sc.distribution.entity.TScPrcplyentry1Entity;

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
 * @Description: 促销管理
 * @author onlineGenerator
 * @date 2016-07-25 16:19:59
 * @version V1.0   
 *
 */
public class TScPrcplyPage implements java.io.Serializable {
	/**保存-商品从表*/
	private List<TScPrcplyentry2Entity> tScPrcplyentry2List = new ArrayList<TScPrcplyentry2Entity>();
	public List<TScPrcplyentry2Entity> getTScPrcplyentry2List() {
		return tScPrcplyentry2List;
	}
	public void setTScPrcplyentry2List(List<TScPrcplyentry2Entity> tScPrcplyentry2List) {
		this.tScPrcplyentry2List = tScPrcplyentry2List;
	}
	/**保存-客户从表*/
	private List<TScPrcplyentry1Entity> tScPrcplyentry1List = new ArrayList<TScPrcplyentry1Entity>();
	public List<TScPrcplyentry1Entity> getTScPrcplyentry1List() {
		return tScPrcplyentry1List;
	}
	public void setTScPrcplyentry1List(List<TScPrcplyentry1Entity> tScPrcplyentry1List) {
		this.tScPrcplyentry1List = tScPrcplyentry1List;
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
	/**单据编号*/
	private java.lang.String billNo;
	/**单据日期*/
	private java.util.Date date;
	/**经办人*/
	private java.lang.String empID;
	/**部门*/
	private java.lang.String deptID;
	/**单据类型*/
	private java.lang.Integer tranType;
	/**摘要*/
	private java.lang.String explanation;
	/**制单人*/
	private java.lang.String billerID;
	/**审核人*/
	private java.lang.String checkerID;
	/**审核日期*/
	private java.util.Date checkDate;
	/**审核状态*/
	private java.lang.Integer checkState;
	/**作废标记*/
	private java.lang.Integer cancellation;
	/**分支机构*/
	private java.lang.String sonID;
	/**是否禁用*/
	private java.lang.Integer disabled;
	/**是否删除*/
	private java.lang.Integer deleted;
	/**版本号*/
	private java.lang.Integer version;
	
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  是否禁用
	 */
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
}
