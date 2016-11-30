
package com.qihang.buss.sc.sys.page;
import com.qihang.buss.sc.sys.entity.TScAccountStageEntity;
import com.qihang.buss.sc.sys.entity.VScCheckstageEntity;
import com.qihang.buss.sc.sys.entity.VScCheckspeedbalEntity;
import com.qihang.buss.sc.sys.entity.TScIcBalEntity;
import com.qihang.buss.sc.sys.entity.TScRpContactbalEntity;
import com.qihang.buss.sc.sys.entity.TScRpExpbalEntity;

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
 * @Description: 账套期别
 * @author onlineGenerator
 * @date 2016-09-17 15:14:23
 * @version V1.0   
 *
 */
public class TScAccountStagePage implements java.io.Serializable {
	/**保存-未审核单据*/
	private List<VScCheckstageEntity> vScCheckstageList = new ArrayList<VScCheckstageEntity>();
	public List<VScCheckstageEntity> getVScCheckstageList() {
		return vScCheckstageList;
	}
	public void setVScCheckstageList(List<VScCheckstageEntity> vScCheckstageList) {
		this.vScCheckstageList = vScCheckstageList;
	}
	/**保存-负库存情况*/
	private List<VScCheckspeedbalEntity> vScCheckspeedbalList = new ArrayList<VScCheckspeedbalEntity>();
	public List<VScCheckspeedbalEntity> getVScCheckspeedbalList() {
		return vScCheckspeedbalList;
	}
	public void setVScCheckspeedbalList(List<VScCheckspeedbalEntity> vScCheckspeedbalList) {
		this.vScCheckspeedbalList = vScCheckspeedbalList;
	}
	/**保存-存货结账*/
	private List<TScIcBalEntity> tScIcBalList = new ArrayList<TScIcBalEntity>();
	public List<TScIcBalEntity> getTScIcBalList() {
		return tScIcBalList;
	}
	public void setTScIcBalList(List<TScIcBalEntity> tScIcBalList) {
		this.tScIcBalList = tScIcBalList;
	}
	/**保存-应收应付结账*/
	private List<TScRpContactbalEntity> tScRpContactbalList = new ArrayList<TScRpContactbalEntity>();
	public List<TScRpContactbalEntity> getTScRpContactbalList() {
		return tScRpContactbalList;
	}
	public void setTScRpContactbalList(List<TScRpContactbalEntity> tScRpContactbalList) {
		this.tScRpContactbalList = tScRpContactbalList;
	}
	/**保存-收支结账*/
	private List<TScRpExpbalEntity> tScRpExpbalList = new ArrayList<TScRpExpbalEntity>();
	public List<TScRpExpbalEntity> getTScRpExpbalList() {
		return tScRpExpbalList;
	}
	public void setTScRpExpbalList(List<TScRpExpbalEntity> tScRpExpbalList) {
		this.tScRpExpbalList = tScRpExpbalList;
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
	/**期别*/
	private java.util.Date date;
	/**账套ID*/
	private java.lang.String accountId;
	/**结账状态(0未结账,1已结账)*/
	private java.lang.Integer state;

	/**非持久化字段，用于期未结账时的操作选项传递*/
	private java.lang.String openType;//结账操作选择:stage结账,unstage反结账
	
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  期别
	 */
	public java.util.Date getDate(){
		return this.date;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  期别
	 */
	public void setDate(java.util.Date date){
		this.date = date;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  账套ID
	 */
	public java.lang.String getAccountId(){
		return this.accountId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  账套ID
	 */
	public void setAccountId(java.lang.String accountId){
		this.accountId = accountId;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  结账状态(0未结账,1已结账)
	 */
	public java.lang.Integer getState(){
		return this.state;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  结账状态(0未结账,1已结账)
	 */
	public void setState(java.lang.Integer state){
		this.state = state;
	}

	/**
	 *方法: 结账操作选择:stage结账,unstage反结账
	 */
	public String getOpenType() {
		return openType;
	}

	/**
	 *方法: 结账操作选择:stage结账,unstage反结账
	 */
	public void setOpenType(String openType) {
		this.openType = openType;
	}
}
