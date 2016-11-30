package com.qihang.buss.sc.oa.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import com.qihang.winter.poi.excel.annotation.ExcelVerify;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 通讯簿
 * @author onlineGenerator
 * @date 2015-12-07 14:03:35
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_CONTACT", schema = "")
@SuppressWarnings("serial")
public class TScContactEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**创建人名称*/
	private String createName;
	/**创建人登录名称*/
	private String createBy;
	/**更新人名称*/
	private String updateName;
	/**更新人登录名称*/
	private String updateBy;
	/**创建日期*/
	private Date createDate;
	/**更新日期*/
	private Date updateDate;

	/**用户ID*/
	private String userId;
	/**姓名*/
	@Excel(name="姓名")
	@ExcelVerify(notNull=true)
	private String name;
	/**性别*/
	@Excel(name="性别",replace = {"男_1","女_0"})
	private String sex;
	/**生日*/
	@Excel(name="生日",format = "yyyy-MM-dd")
	private Date birthday;
	/**职位*/
	@Excel(name="职位")
	private String position;
	/**单位*/
	@Excel(name="单位")
	private String unit;
	/**单位地址*/
	@Excel(name="单位地址")
	private String unitAddress;
	/**单位邮编*/
	@Excel(name="单位邮编")
	private String unitCode;
	/**单位电话*/
	@Excel(name="单位电话")
	private String unitPhone;
	/**单位传真*/
	@Excel(name="单位传真")
	private String unitFax;
	/**家庭住址*/
	@Excel(name="家庭住址")
	private String address;
	/**联系电话*/
	@Excel(name="联系电话")
	private String phone;
	/**QQ*/
	@Excel(name="QQ")
	private String qq;
	/**备注*/
	@Excel(name="备注")
	private String note;
	/**分类*/
	@Excel(name="分类",replace = {"个人通讯簿_1","公共通讯簿_2"})
	@ExcelVerify(notNull=true)
	private Integer type;

	/**版本号*/
	private Integer version;
	@Version
	@Column(name ="VERSION",nullable=true,length=32)
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  分类
	 */
	@Column(name ="TYPE",nullable=true,length=11)
	public Integer getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  分类
	 */
	public void setType(Integer type){
		this.type = type;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户ID
	 */
	@Column(name ="USER_ID",nullable=true,length=36)
	public String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户ID
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  姓名
	 */
	@Column(name ="NAME",nullable=false,length=50)
	public String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  姓名
	 */
	public void setName(String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  性别
	 */
	@Column(name ="SEX",nullable=true,length=32)
	public String getSex(){
		return this.sex;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  性别
	 */
	public void setSex(String sex){
		this.sex = sex;
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
	 *@return: java.lang.String  职位
	 */
	@Column(name ="POSITION",nullable=true,length=50)
	public String getPosition(){
		return this.position;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  职位
	 */
	public void setPosition(String position){
		this.position = position;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */
	@Column(name ="UNIT",nullable=true,length=50)
	public String getUnit(){
		return this.unit;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setUnit(String unit){
		this.unit = unit;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位地址
	 */
	@Column(name ="UNIT_ADDRESS",nullable=true,length=255)
	public String getUnitAddress(){
		return this.unitAddress;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位地址
	 */
	public void setUnitAddress(String unitAddress){
		this.unitAddress = unitAddress;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位邮编
	 */
	@Column(name ="UNIT_CODE",nullable=true,length=32)
	public String getUnitCode(){
		return this.unitCode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位邮编
	 */
	public void setUnitCode(String unitCode){
		this.unitCode = unitCode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位电话
	 */
	@Column(name ="UNIT_PHONE",nullable=true,length=32)
	public String getUnitPhone(){
		return this.unitPhone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位电话
	 */
	public void setUnitPhone(String unitPhone){
		this.unitPhone = unitPhone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位传真
	 */
	@Column(name ="UNIT_FAX",nullable=true,length=32)
	public String getUnitFax(){
		return this.unitFax;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位传真
	 */
	public void setUnitFax(String unitFax){
		this.unitFax = unitFax;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  家庭住址
	 */
	@Column(name ="ADDRESS",nullable=true,length=255)
	public String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  家庭住址
	 */
	public void setAddress(String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系电话
	 */
	@Column(name ="PHONE",nullable=true,length=32)
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
	 *@return: java.lang.String  QQ
	 */
	@Column(name ="QQ",nullable=true,length=32)
	public String getQq(){
		return this.qq;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  QQ
	 */
	public void setQq(String qq){
		this.qq = qq;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="NOTE",nullable=true,length=500)
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
}
