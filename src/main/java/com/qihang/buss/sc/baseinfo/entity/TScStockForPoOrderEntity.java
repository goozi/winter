package com.qihang.buss.sc.baseinfo.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**   
 * @Title: Entity
 * @Description: 仓库
 * @author onlineGenerator
 * @date 2016-06-28 10:25:47
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_Stock", schema = "")
@SuppressWarnings("serial")
public class TScStockForPoOrderEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**创建人名称*/
	private String createName;
	/**创建人登录名称*/
	private String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新人名称*/
	private String updateName;
	/**更新人登录名称*/
	private String updateBy;
	/**更新日期*/
	private java.util.Date updateDate;
	/**名称*/
	@Excel(name="仓库")
	private String name;
	/**编号*/
	private String number;
	/**分类ID*/
	private String parentID;
	/**助记码*/
	private String shortNumber;
	/**简称*/
	private String shortName;
	/**仓管*/
	private String empID;
	/**仓库地址*/
	private String address;
	/**电话*/
	private String phone;
	/**仓库类型*/
	private Integer typeID;
	/**禁用标记*/
	private Integer deleted;
	/**级次*/
	private Integer level;
	/**备注*/
	private String note;
	/**机构ID*/
	private String sonID;
	private Integer disabled;
	private Integer version;
	private Integer count;

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
	 *@return: java.lang.String  名称
	 */
	@Column(name ="NAME",nullable=true,length=80)
	public String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  编号
	 */
	@Column(name ="NUMBER",nullable=true,length=80)
	public String getNumber(){
		return this.number;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  编号
	 */
	public void setNumber(String number){
		this.number = number;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  分类ID
	 */
	@Column(name ="PARENTID",nullable=true,length=50)
	public String getParentID(){
		return this.parentID;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  分类ID
	 */
	public void setParentID(String parentID){
		this.parentID = parentID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  助记码
	 */
	@Column(name ="SHORTNUMBER",nullable=true,length=80)
	public String getShortNumber(){
		return this.shortNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  助记码
	 */
	public void setShortNumber(String shortNumber){
		this.shortNumber = shortNumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  简称
	 */
	@Column(name ="SHORTNAME",nullable=true,length=80)
	public String getShortName(){
		return this.shortName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  简称
	 */
	public void setShortName(String shortName){
		this.shortName = shortName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓管
	 */
	@Column(name ="EMPID",nullable=true,length=50)
	public String getEmpID(){
		return this.empID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓管
	 */
	public void setEmpID(String empID){
		this.empID = empID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库地址
	 */
	@Column(name ="ADDRESS",nullable=true,length=255)
	public String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库地址
	 */
	public void setAddress(String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话
	 */
	@Column(name ="PHONE",nullable=true,length=32)
	public String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电话
	 */
	public void setPhone(String phone){
		this.phone = phone;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  仓库类型
	 */
	@Column(name ="TYPEID",nullable=true,length=32)
	public Integer getTypeID(){
		return this.typeID;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  仓库类型
	 */
	public void setTypeID(Integer typeID){
		this.typeID = typeID;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  禁用标记
	 */
	@Column(name ="DELETED",nullable=true,length=32)
	public Integer getDeleted(){
		return this.deleted;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  禁用标记
	 */
	public void setDeleted(Integer deleted){
		this.deleted = deleted;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  级次
	 */
	@Column(name ="LEVEL",nullable=true,length=32)
	public Integer getLevel(){
		return this.level;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  级次
	 */
	public void setLevel(Integer level){
		this.level = level;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="NOTE",nullable=true,length=255)
	public String getNote(){
		return this.note;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setNote(String note){
		this.note = note;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  机构ID
	 */
	@Column(name ="SONID",nullable=true,length=32)
	public String getSonID(){
		return this.sonID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  机构ID
	 */
	public void setSonID(String sonID){
		this.sonID = sonID;
	}

	public Integer getDisabled() {
		return disabled;
	}

	public void setDisabled(Integer disabled) {
		this.disabled = disabled;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
