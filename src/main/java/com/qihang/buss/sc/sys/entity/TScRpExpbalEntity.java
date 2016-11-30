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
 * @Description: 收支结账
 * @author onlineGenerator
 * @date 2016-09-17 15:14:23
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_sc_RP_ExpBal", schema = "")
@SuppressWarnings("serial")
public class TScRpExpbalEntity implements java.io.Serializable {
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
	/**年份*/
	private java.lang.String year;
	/**月份*/
	private java.lang.String period;
	/**结算账户*/
	@Excel(name="结算账户")
	private java.lang.String accountID;
	/**部门主键*/
	@Excel(name="部门主键")
	private java.lang.String deptID;
	/**职员主键*/
	@Excel(name="职员主键")
	private java.lang.String empID;
	/**期初金额*/
	@Excel(name="期初金额")
	private java.math.BigDecimal begBal;
	/**本期收入金额*/
	@Excel(name="本期收入金额")
	private java.math.BigDecimal debit;
	/**本期发出金额*/
	@Excel(name="本期发出金额")
	private java.math.BigDecimal credit;
	/**本年累计收入金额*/
	@Excel(name="本年累计收入金额")
	private java.math.BigDecimal ytdDebit;
	/**本年累计发出金额*/
	@Excel(name="本年累计发出金额")
	private java.math.BigDecimal ytdCredit;
	/**期末结存金额*/
	@Excel(name="期末结存金额")
	private java.math.BigDecimal endBal;
	/**机构ID*/
	private java.lang.String sonID;
	/**父Id*/
	private java.lang.String fid;
	
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
	 *@return: java.lang.String  年份
	 */
	@Column(name ="YEAR",nullable=true,length=32)
	public java.lang.String getYear(){
		return this.year;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  年份
	 */
	public void setYear(java.lang.String year){
		this.year = year;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  月份
	 */
	@Column(name ="PERIOD",nullable=true,length=32)
	public java.lang.String getPeriod(){
		return this.period;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  月份
	 */
	public void setPeriod(java.lang.String period){
		this.period = period;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结算账户
	 */
	@Column(name ="ACCOUNTID",nullable=true,length=32)
	public java.lang.String getAccountID(){
		return this.accountID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结算账户
	 */
	public void setAccountID(java.lang.String accountID){
		this.accountID = accountID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门主键
	 */
	@Column(name ="DEPTID",nullable=true,length=32)
	public java.lang.String getDeptID(){
		return this.deptID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门主键
	 */
	public void setDeptID(java.lang.String deptID){
		this.deptID = deptID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  职员主键
	 */
	@Column(name ="EMPID",nullable=true,length=32)
	public java.lang.String getEmpID(){
		return this.empID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  职员主键
	 */
	public void setEmpID(java.lang.String empID){
		this.empID = empID;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  期初金额
	 */
	@Column(name ="BEGBAL",nullable=true,length=32)
	public java.math.BigDecimal getBegBal(){
		return this.begBal;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  期初金额
	 */
	public void setBegBal(java.math.BigDecimal begBal){
		this.begBal = begBal;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本期收入金额
	 */
	@Column(name ="DEBIT",nullable=true,length=32)
	public java.math.BigDecimal getDebit(){
		return this.debit;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本期收入金额
	 */
	public void setDebit(java.math.BigDecimal debit){
		this.debit = debit;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本期发出金额
	 */
	@Column(name ="CREDIT",nullable=true,length=32)
	public java.math.BigDecimal getCredit(){
		return this.credit;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本期发出金额
	 */
	public void setCredit(java.math.BigDecimal credit){
		this.credit = credit;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本年累计收入金额
	 */
	@Column(name ="YTDDEBIT",nullable=true,length=32)
	public java.math.BigDecimal getYtdDebit(){
		return this.ytdDebit;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本年累计收入金额
	 */
	public void setYtdDebit(java.math.BigDecimal ytdDebit){
		this.ytdDebit = ytdDebit;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本年累计发出金额
	 */
	@Column(name ="YTDCREDIT",nullable=true,length=32)
	public java.math.BigDecimal getYtdCredit(){
		return this.ytdCredit;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本年累计发出金额
	 */
	public void setYtdCredit(java.math.BigDecimal ytdCredit){
		this.ytdCredit = ytdCredit;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  期末结存金额
	 */
	@Column(name ="ENDBAL",nullable=true,length=32)
	public java.math.BigDecimal getEndBal(){
		return this.endBal;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  期末结存金额
	 */
	public void setEndBal(java.math.BigDecimal endBal){
		this.endBal = endBal;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  机构ID
	 */
	@Column(name ="SONID",nullable=true,length=32)
	public java.lang.String getSonID(){
		return this.sonID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  机构ID
	 */
	public void setSonID(java.lang.String sonID){
		this.sonID = sonID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  父Id
	 */
	@Column(name ="FID",nullable=true,length=32)
	public java.lang.String getFid(){
		return this.fid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  父Id
	 */
	public void setFid(java.lang.String fid){
		this.fid = fid;
	}
}
