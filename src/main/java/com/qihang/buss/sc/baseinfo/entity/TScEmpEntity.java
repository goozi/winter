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
 * @Description: 职员
 * @author onlineGenerator
 * @date 2016-07-05 10:30:32
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_EMP", schema = "")
@SuppressWarnings("serial")
public class TScEmpEntity implements java.io.Serializable {
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
	/**名称*/
	@Excel(name="名称")
	private java.lang.String name;
	/**编号*/
	@Excel(name="编号")
	private java.lang.String number;
	/**分类ID*/
	private java.lang.String parentID;
	/**助记码*/
	@Excel(name="助记码")
	private java.lang.String shortNumber;
	/**简称*/
	@Excel(name="简称")
	private java.lang.String shortName;
	/**部门*/
	@Excel(name="部门")
	private java.lang.String deptID;
	/**职员类别*/
	@Excel(name="职员类别")
	private java.lang.String empGroup;
	/**性别*/
	@Excel(name="性别")
	private java.lang.String gender;
	/**生日*/
	@Excel(name="生日")
	private java.util.Date birthday;
	/**电子邮件*/
	@Excel(name="电子邮件")
	private java.lang.String email;
	/**QQ号*/
	@Excel(name="QQ号")
	private java.lang.String ciqNumber;
	/**联系地址*/
	@Excel(name="联系地址")
	private java.lang.String address;
	/**联系电话*/
	@Excel(name="联系电话")
	private java.lang.String phone;
	/**手机号码*/
	@Excel(name="手机号码")
	private java.lang.String mobilePhone;
	/**身份证号码*/
	@Excel(name="身份证号码")
	private java.lang.String identify;
	/**身份证地址*/
	@Excel(name="身份证地址")
	private java.lang.String idAddress;
	/**职务*/
	@Excel(name="职务")
	private java.lang.String duty;
	/**文化程度*/
	@Excel(name="文化程度")
	private java.lang.String degree;
	/**毕业院校*/
	@Excel(name="毕业院校")
	private java.lang.String school;
	/**学习专业*/
	@Excel(name="学习专业")
	private java.lang.String expertise;
	/**入职日期*/
	@Excel(name="入职日期")
	private java.util.Date hireDate;
	/**离职日期*/
	@Excel(name="离职日期")
	private java.util.Date leaveDate;
	/**开户银行*/
	@Excel(name="开户银行")
	private java.lang.String bank;
	/**银行账号*/
	@Excel(name="银行账号")
	private java.lang.String bankNumber;
	/**启用信控*/
	@Excel(name="启用信控")
	private java.lang.Integer isCreditMgr;
	/**信用额度*/
	@Excel(name="信用额度")
	private java.math.BigDecimal creditAmount;
	/**禁用标记*/
	private java.lang.Integer disabled;
	/**逻辑删除*/
	private java.lang.Integer deleted;
	/**版本号*/
	private java.lang.Integer version;
	/**级次*/
	private java.lang.Integer level;
	/**引用次数*/
	private java.lang.Integer count;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String note;
	/**用户id*/
	private java.lang.String userId;

	private String sonId;//分支机构

	/**信用额度转换double**/
	private Double dcreditAmount;
	/** 部门名称 **/
	private java.lang.String deptIdName;
	@Transient
	public String getDeptIdName() {
		return deptIdName;
	}

	public void setDeptIdName(String deptIdName) {
		this.deptIdName = deptIdName;
	}

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
	 *@return: java.lang.String  名称
	 */
	@Column(name ="NAME",nullable=false,length=80)
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
	 *@return: java.lang.String  简称
	 */
	@Column(name ="SHORTNAME",nullable=false,length=80)
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
	 *@return: java.lang.String  部门
	 */
	@Column(name ="DEPTID",nullable=true,length=32)
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
	 *@return: java.lang.String  职员类别
	 */
	@Column(name ="EMPGROUP",nullable=true,length=32)
	public java.lang.String getEmpGroup(){
		return this.empGroup;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  职员类别
	 */
	public void setEmpGroup(java.lang.String empGroup){
		this.empGroup = empGroup;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  性别
	 */
	@Column(name ="GENDER",nullable=true,length=32)
	public java.lang.String getGender(){
		return this.gender;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  性别
	 */
	public void setGender(java.lang.String gender){
		this.gender = gender;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  生日
	 */
	@Column(name ="BIRTHDAY",nullable=true,length=32)
	public java.util.Date getBirthday(){
		return this.birthday;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  生日
	 */
	public void setBirthday(java.util.Date birthday){
		this.birthday = birthday;
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
	 *@return: java.lang.String  QQ号
	 */
	@Column(name ="CIQNUMBER",nullable=true,length=50)
	public java.lang.String getCiqNumber(){
		return this.ciqNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  QQ号
	 */
	public void setCiqNumber(java.lang.String ciqNumber){
		this.ciqNumber = ciqNumber;
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
	 *@return: java.lang.String  联系电话
	 */
	@Column(name ="PHONE",nullable=true,length=40)
	public java.lang.String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系电话
	 */
	public void setPhone(java.lang.String phone){
		this.phone = phone;
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
	 *@return: java.lang.String  身份证号码
	 */
	@Column(name ="IDENTIFY",nullable=true,length=20)
	public java.lang.String getIdentify(){
		return this.identify;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  身份证号码
	 */
	public void setIdentify(java.lang.String identify){
		this.identify = identify;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  身份证地址
	 */
	@Column(name ="IDADDRESS",nullable=true,length=255)
	public java.lang.String getIdAddress(){
		return this.idAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  身份证地址
	 */
	public void setIdAddress(java.lang.String idAddress){
		this.idAddress = idAddress;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  职务
	 */
	@Column(name ="DUTY",nullable=true,length=32)
	public java.lang.String getDuty(){
		return this.duty;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  职务
	 */
	public void setDuty(java.lang.String duty){
		this.duty = duty;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  文化程度
	 */
	@Column(name ="DEGREE",nullable=true,length=32)
	public java.lang.String getDegree(){
		return this.degree;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  文化程度
	 */
	public void setDegree(java.lang.String degree){
		this.degree = degree;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  毕业院校
	 */
	@Column(name ="SCHOOL",nullable=true,length=255)
	public java.lang.String getSchool(){
		return this.school;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  毕业院校
	 */
	public void setSchool(java.lang.String school){
		this.school = school;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  学习专业
	 */
	@Column(name ="EXPERTISE",nullable=true,length=100)
	public java.lang.String getExpertise(){
		return this.expertise;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  学习专业
	 */
	public void setExpertise(java.lang.String expertise){
		this.expertise = expertise;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  入职日期
	 */
	@Column(name ="HIREDATE",nullable=true,length=32)
	public java.util.Date getHireDate(){
		return this.hireDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  入职日期
	 */
	public void setHireDate(java.util.Date hireDate){
		this.hireDate = hireDate;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  离职日期
	 */
	@Column(name ="LEAVEDATE",nullable=true,length=32)
	public java.util.Date getLeaveDate(){
		return this.leaveDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  离职日期
	 */
	public void setLeaveDate(java.util.Date leaveDate){
		this.leaveDate = leaveDate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  开户银行
	 */
	@Column(name ="BANK",nullable=true,length=32)
	public java.lang.String getBank(){
		return this.bank;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  开户银行
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
	 *@return: java.lang.Integer  启用信控
	 */
	@Column(name ="ISCREDITMGR",nullable=false,length=32)
	public java.lang.Integer getIsCreditMgr(){
		return this.isCreditMgr;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  启用信控
	 */
	public void setIsCreditMgr(java.lang.Integer isCreditMgr){
		this.isCreditMgr = isCreditMgr;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  信用额度
	 */
	@Column(name ="CREDITAMOUNT",nullable=true,length=32)
	public java.math.BigDecimal getCreditAmount(){
		return this.creditAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  信用额度
	 */
	public void setCreditAmount(java.math.BigDecimal creditAmount){
		this.creditAmount = creditAmount;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户id
	 */
	@Column(name ="USERID",nullable=true,length=32)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户id
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}

	@Transient
	public Double getDcreditAmount() {
		if(this.getCreditAmount() != null)
			return this.getCreditAmount().doubleValue();
		else
			return null;
	}

	public void setDcreditAmount(Double dcreditAmount) {
		this.dcreditAmount = dcreditAmount;
	}

	@Column(name ="sonId",nullable=true)
	public String getSonId() {
		return sonId;
	}

	public void setSonId(String sonId) {
		this.sonId = sonId;
	}
}
