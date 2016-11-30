package com.qihang.buss.sc.baseinfo.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

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
public class TScEmpForPoOrderEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**创建人名称*/
	private String createName;
	/**创建人登录名称*/
	private String createBy;
	/**创建日期*/
	private Date createDate;
	/**更新人名称*/
	private String updateName;
	/**更新人登录名称*/
	private String updateBy;
	/**更新日期*/
	private Date updateDate;
	/**名称*/
	@Excel(name="经办人")
	private String name;
	/**编号*/
	private String number;
	/**分类ID*/
	private String parentID;
	/**助记码*/
	private String shortNumber;
	/**简称*/
	private String shortName;
	/**部门*/
	private String deptID;
	/**职员类别*/
	private String empGroup;
	/**性别*/
	private String gender;
	/**生日*/
	private Date birthday;
	/**电子邮件*/
	private String email;
	/**QQ号*/
	private String ciqNumber;
	/**联系地址*/
	private String address;
	/**联系电话*/
	private String phone;
	/**手机号码*/
	private String mobilePhone;
	/**身份证号码*/
	private String identify;
	/**身份证地址*/
	private String idAddress;
	/**职务*/
	private Integer duty;
	/**文化程度*/
	private String degree;
	/**毕业院校*/
	private String school;
	/**学习专业*/
	private String expertise;
	/**入职日期*/
	private Date hireDate;
	/**离职日期*/
	private Date leaveDate;
	/**开户银行*/
	private Integer bank;
	/**银行账号*/
	private String bankNumber;
	/**启用信控*/
	private Integer isCreditMgr;
	/**信用额度*/
	private BigDecimal creditAmount;
	/**禁用标记*/
	private Integer disabled;
	/**逻辑删除*/
	private Integer deleted;
	/**版本号*/
	private Integer version;
	/**级次*/
	private Integer level;
	/**引用次数*/
	private Integer count;
	/**备注*/
	private String note;
	/**用户id*/
	private String userId;

	/** 部门名称 **/
	private String deptIdName;
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
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建日期
	 */
	public void setCreateDate(Date createDate){
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
	public Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新日期
	 */
	public void setUpdateDate(Date updateDate){
		this.updateDate = updateDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="NAME",nullable=false,length=80)
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
	@Column(name ="NUMBER",nullable=false,length=80)
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分类ID
	 */
	@Column(name ="PARENTID",nullable=true,length=32)
	public String getParentID(){
		return this.parentID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分类ID
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
	@Column(name ="SHORTNAME",nullable=false,length=80)
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
	 *@return: java.lang.String  部门
	 */
	@Column(name ="DEPTID",nullable=true,length=32)
	public String getDeptID(){
		return this.deptID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门
	 */
	public void setDeptID(String deptID){
		this.deptID = deptID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  职员类别
	 */
	@Column(name ="EMPGROUP",nullable=true,length=32)
	public String getEmpGroup(){
		return this.empGroup;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  职员类别
	 */
	public void setEmpGroup(String empGroup){
		this.empGroup = empGroup;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  性别
	 */
	@Column(name ="GENDER",nullable=true,length=32)
	public String getGender(){
		return this.gender;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  性别
	 */
	public void setGender(String gender){
		this.gender = gender;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  生日
	 */
	@Column(name ="BIRTHDAY",nullable=true,length=32)
	public Date getBirthday(){
		return this.birthday;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  生日
	 */
	public void setBirthday(Date birthday){
		this.birthday = birthday;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电子邮件
	 */
	@Column(name ="EMAIL",nullable=true,length=40)
	public String getEmail(){
		return this.email;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电子邮件
	 */
	public void setEmail(String email){
		this.email = email;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  QQ号
	 */
	@Column(name ="CIQNUMBER",nullable=true,length=50)
	public String getCiqNumber(){
		return this.ciqNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  QQ号
	 */
	public void setCiqNumber(String ciqNumber){
		this.ciqNumber = ciqNumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系地址
	 */
	@Column(name ="ADDRESS",nullable=true,length=255)
	public String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系地址
	 */
	public void setAddress(String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系电话
	 */
	@Column(name ="PHONE",nullable=true,length=40)
	public String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系电话
	 */
	public void setPhone(String phone){
		this.phone = phone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机号码
	 */
	@Column(name ="MOBILEPHONE",nullable=true,length=15)
	public String getMobilePhone(){
		return this.mobilePhone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机号码
	 */
	public void setMobilePhone(String mobilePhone){
		this.mobilePhone = mobilePhone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  身份证号码
	 */
	@Column(name ="IDENTIFY",nullable=true,length=20)
	public String getIdentify(){
		return this.identify;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  身份证号码
	 */
	public void setIdentify(String identify){
		this.identify = identify;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  身份证地址
	 */
	@Column(name ="IDADDRESS",nullable=true,length=255)
	public String getIdAddress(){
		return this.idAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  身份证地址
	 */
	public void setIdAddress(String idAddress){
		this.idAddress = idAddress;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  职务
	 */
	@Column(name ="DUTY",nullable=true,length=32)
	public Integer getDuty(){
		return this.duty;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  职务
	 */
	public void setDuty(Integer duty){
		this.duty = duty;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  文化程度
	 */
	@Column(name ="DEGREE",nullable=true,length=32)
	public String getDegree(){
		return this.degree;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  文化程度
	 */
	public void setDegree(String degree){
		this.degree = degree;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  毕业院校
	 */
	@Column(name ="SCHOOL",nullable=true,length=255)
	public String getSchool(){
		return this.school;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  毕业院校
	 */
	public void setSchool(String school){
		this.school = school;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  学习专业
	 */
	@Column(name ="EXPERTISE",nullable=true,length=100)
	public String getExpertise(){
		return this.expertise;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  学习专业
	 */
	public void setExpertise(String expertise){
		this.expertise = expertise;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  入职日期
	 */
	@Column(name ="HIREDATE",nullable=true,length=32)
	public Date getHireDate(){
		return this.hireDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  入职日期
	 */
	public void setHireDate(Date hireDate){
		this.hireDate = hireDate;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  离职日期
	 */
	@Column(name ="LEAVEDATE",nullable=true,length=32)
	public Date getLeaveDate(){
		return this.leaveDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  离职日期
	 */
	public void setLeaveDate(Date leaveDate){
		this.leaveDate = leaveDate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  开户银行
	 */
	@Column(name ="BANK",nullable=true,length=32)
	public Integer getBank(){
		return this.bank;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  开户银行
	 */
	public void setBank(Integer bank){
		this.bank = bank;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  银行账号
	 */
	@Column(name ="BANKNUMBER",nullable=true,length=40)
	public String getBankNumber(){
		return this.bankNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  银行账号
	 */
	public void setBankNumber(String bankNumber){
		this.bankNumber = bankNumber;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  启用信控
	 */
	@Column(name ="ISCREDITMGR",nullable=false,length=32)
	public Integer getIsCreditMgr(){
		return this.isCreditMgr;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  启用信控
	 */
	public void setIsCreditMgr(Integer isCreditMgr){
		this.isCreditMgr = isCreditMgr;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  信用额度
	 */
	@Column(name ="CREDITAMOUNT",nullable=true,length=32)
	public BigDecimal getCreditAmount(){
		return this.creditAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  信用额度
	 */
	public void setCreditAmount(BigDecimal creditAmount){
		this.creditAmount = creditAmount;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  禁用标记
	 */
	@Column(name ="DISABLED",nullable=true,length=32)
	public Integer getDisabled(){
		return this.disabled;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  禁用标记
	 */
	public void setDisabled(Integer disabled){
		this.disabled = disabled;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  逻辑删除
	 */
	@Column(name ="DELETED",nullable=true,length=32)
	public Integer getDeleted(){
		return this.deleted;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  逻辑删除
	 */
	public void setDeleted(Integer deleted){
		this.deleted = deleted;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  版本号
	 */
	@Version
	@Column(name ="VERSION",nullable=true,length=32)
	public Integer getVersion(){
		return this.version;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  版本号
	 */
	public void setVersion(Integer version){
		this.version = version;
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  引用次数
	 */
	@Column(name ="COUNT",nullable=true,length=32)
	public Integer getCount(){
		return this.count;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  引用次数
	 */
	public void setCount(Integer count){
		this.count = count;
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
	 *@return: java.lang.String  用户id
	 */
	@Column(name ="USERID",nullable=true,length=32)
	public String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户id
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}
}
