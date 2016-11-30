package com.qihang.buss.sc.baseinfo.entity;

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
 * @Description: 物流公司
 * @author onlineGenerator
 * @date 2016-07-01 08:45:06
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_LOGISTICAL", schema = "")
@SuppressWarnings("serial")
public class TScLogisticalEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新日期*/
	private java.util.Date updateDate;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String name;
	/**编号*/
	@Excel(name="编号")
	private java.lang.String number;
	/**分类ID*/
	private java.lang.String parentID;
	/**简称*/
	@Excel(name="简称")
	private java.lang.String shortName;
	/**助记码*/
	@Excel(name="助记码")
	private java.lang.String shortNumber;
	/**区域*/
	@Excel(name="区域")
	private java.lang.String regionID;
	/**联系人*/
	@Excel(name="联系人")
	private java.lang.String contact;
	/**手机号码*/
	@Excel(name="手机号码")
	private java.lang.String mobilePhone;
	/**电话*/
	@Excel(name="电话")
	private java.lang.String phone;
	/**传真*/
	@Excel(name="传真")
	private java.lang.String fax;
	/**联系地址*/
	@Excel(name="联系地址")
	private java.lang.String address;
	/**邮政编码*/
	@Excel(name="邮政编码")
	private java.lang.String postalCode;
	/**电子邮件*/
	@Excel(name="电子邮件")
	private java.lang.String email;
	/**开户银行*/
	@Excel(name="开户银行")
	private java.lang.String bank;
	/**银行账号*/
	@Excel(name="银行账号")
	private java.lang.String bankNumber;
	/**换货天数*/
	@Excel(name="换货天数")
	private java.lang.Integer jhDay;
	/**换货比例*/
	@Excel(name="换货比例")
	private java.math.BigDecimal jhRate;
	/**级次*/
	private java.lang.Integer level;
	/**禁用标记*/
	private java.lang.Integer disabled;
	/**逻辑删除*/
	private java.lang.Integer deleted;
	/**版本号*/
	private java.lang.Integer version;
	/**引用次数*/
	private java.lang.Integer count;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String note;

	private String sonId;//分支机构
	
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
	@Column(name ="NAME",nullable=false,length=150)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  编号
	 */
	@Column(name ="NUMBER",nullable=false,length=80)
	public java.lang.String getNumber(){
		return this.number;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  编号
	 */
	public void setNumber(java.lang.String number){
		this.number = number;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分类ID
	 */
	@Column(name ="PARENTID",nullable=true,length=32)
	public java.lang.String getParentID(){
		return this.parentID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分类ID
	 */
	public void setParentID(java.lang.String parentID){
		this.parentID = parentID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  简称
	 */
	@Column(name ="SHORTNAME",nullable=false,length=50)
	public java.lang.String getShortName(){
		return this.shortName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  简称
	 */
	public void setShortName(java.lang.String shortName){
		this.shortName = shortName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  助记码
	 */
	@Column(name ="SHORTNUMBER",nullable=true,length=80)
	public java.lang.String getShortNumber(){
		return this.shortNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  助记码
	 */
	public void setShortNumber(java.lang.String shortNumber){
		this.shortNumber = shortNumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  区域
	 */
	@Column(name ="REGIONID",nullable=true,length=50)
	public java.lang.String getRegionID(){
		return this.regionID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  区域
	 */
	public void setRegionID(java.lang.String regionID){
		this.regionID = regionID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人
	 */
	@Column(name ="CONTACT",nullable=true,length=50)
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
	@Column(name ="MOBILEPHONE",nullable=true,length=15)
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
	@Column(name ="PHONE",nullable=true,length=40)
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
	@Column(name ="FAX",nullable=true,length=40)
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
	@Column(name ="ADDRESS",nullable=true,length=255)
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
	 *@return: java.lang.String  邮政编码
	 */
	@Column(name ="POSTALCODE",nullable=true,length=20)
	public java.lang.String getPostalCode(){
		return this.postalCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  邮政编码
	 */
	public void setPostalCode(java.lang.String postalCode){
		this.postalCode = postalCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电子邮件
	 */
	@Column(name ="EMAIL",nullable=true,length=40)
	public java.lang.String getEmail(){
		return this.email;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电子邮件
	 */
	public void setEmail(java.lang.String email){
		this.email = email;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  开户银行
	 */
	@Column(name ="BANK",nullable=true,length=50)
	public java.lang.String getBank(){
		return this.bank;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  开户银行
	 */
	public void setBank(java.lang.String bank){
		this.bank = bank;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  银行账号
	 */
	@Column(name ="BANKNUMBER",nullable=true,length=40)
	public java.lang.String getBankNumber(){
		return this.bankNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  银行账号
	 */
	public void setBankNumber(java.lang.String bankNumber){
		this.bankNumber = bankNumber;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  换货天数
	 */
	@Column(name ="JHDAY",nullable=true,length=32)
	public java.lang.Integer getJhDay(){
		return this.jhDay;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  换货天数
	 */
	public void setJhDay(java.lang.Integer jhDay){
		this.jhDay = jhDay;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  换货比例
	 */
	@Column(name ="JHRATE",nullable=true,length=32)
	public java.math.BigDecimal getJhRate(){
		return this.jhRate;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  换货比例
	 */
	public void setJhRate(java.math.BigDecimal jhRate){
		this.jhRate = jhRate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  级次
	 */
	@Column(name ="LEVEL",nullable=true,length=32)
	public java.lang.Integer getLevel(){
		return this.level;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  级次
	 */
	public void setLevel(java.lang.Integer level){
		this.level = level;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  禁用标记
	 */
	@Column(name ="DISABLED",nullable=true,length=32)
	public java.lang.Integer getDisabled(){
		return this.disabled;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  禁用标记
	 */
	public void setDisabled(java.lang.Integer disabled){
		this.disabled = disabled;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  逻辑删除
	 */
	@Column(name ="DELETED",nullable=true,length=32)
	public java.lang.Integer getDeleted(){
		return this.deleted;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  逻辑删除
	 */
	public void setDeleted(java.lang.Integer deleted){
		this.deleted = deleted;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  版本号
	 */
	@Version
	@Column(name ="VERSION",nullable=true,length=32)
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
	 *@return: java.lang.Integer  引用次数
	 */
	@Column(name ="COUNT",nullable=true,length=32)
	public java.lang.Integer getCount(){
		return this.count;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  引用次数
	 */
	public void setCount(java.lang.Integer count){
		this.count = count;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="NOTE",nullable=true,length=255)
	public java.lang.String getNote(){
		return this.note;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setNote(java.lang.String note){
		this.note = note;
	}

	@Column(name ="sonId",nullable=true)
	public String getSonId() {
		return sonId;
	}

	public void setSonId(String sonId) {
		this.sonId = sonId;
	}
}
