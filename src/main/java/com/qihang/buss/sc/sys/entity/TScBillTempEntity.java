package com.qihang.buss.sc.sys.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import com.qihang.winter.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 单据草稿表
 * @author onlineGenerator
 * @date 2016-08-24 19:27:38
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_BILL_TEMP", schema = "")
@SuppressWarnings("serial")
public class TScBillTempEntity implements java.io.Serializable {
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
	/**套帐ID*/
	@Excel(name="套帐ID")
	private java.lang.String accountId;
	/**单据类型*/
	@Excel(name="单据类型")
	private java.lang.String tranType;
	/**用户ID*/
	@Excel(name="用户ID")
	private java.lang.String billerId;
	/**数据JSON串*/
	@Excel(name="数据JSON串")
	private java.lang.String content;
	/**单据日期*/
	@Excel(name="单据日期")
	private java.util.Date date;
	/**单据编号*/
	@Excel(name="单据编号")
	private java.lang.String billNo;
	/**供应商或客户*/
	@Excel(name="供应商或客户")
	private java.lang.String itemId;
	/**经办人*/
	@Excel(name="经办人")
	private java.lang.String empId;
	/**部门*/
	@Excel(name="部门")
	private java.lang.String deptId;
	/**仓库*/
	@Excel(name="仓库")
	private java.lang.String stockId;
	
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
	 *@return: java.lang.String  套帐ID
	 */
	@Column(name ="ACCOUNTID",nullable=true,length=32)
	public java.lang.String getAccountId(){
		return this.accountId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  套帐ID
	 */
	public void setAccountId(java.lang.String accountId){
		this.accountId = accountId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单据类型
	 */
	@Column(name ="TRANTYPE",nullable=true,length=32)
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
	 *@return: java.lang.String  用户ID
	 */
	@Column(name ="BILLERID",nullable=true,length=32)
	public java.lang.String getBillerId(){
		return this.billerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户ID
	 */
	public void setBillerId(java.lang.String billerId){
		this.billerId = billerId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  数据JSON串
	 */
	@Column(name ="CONTENT",nullable=true,length=32)
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  数据JSON串
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  单据日期
	 */

	@Column(name ="date",nullable=true,length=20)
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

	@Column(name ="BILLNO",nullable=true,length=50)
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
	 *@return: java.lang.String  供应商
	 */

	@Column(name ="ITEMID",nullable=true,length=32)
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

	@Column(name ="EMPID",nullable=true,length=32)
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

	@Column(name ="DEPTID",nullable=true,length=32)
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

	@Column(name ="STOCKID",nullable=true,length=32)
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
}
