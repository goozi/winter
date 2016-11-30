package com.qihang.buss.sc.sys.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 应收应付结账视图
 * @author onlineGenerator
 * @date 2016-09-17 15:14:23
 * @version V1.0   
 *
 */
@Entity
@Table(name = "v_sc_RP_ContactBal", schema = "")
@SuppressWarnings("serial")
public class VScRpContactbalEntity implements java.io.Serializable {
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
	/**年份*/
	private String year;
	/**月份*/
	private String period;
	/**业务类型(0：应付；1：应收)*/
	@Excel(name="业务类型(0：应付；1：应收)")
	private Integer rp;
	/**核算项目(客户、供应商主键)*/
	@Excel(name="核算项目(客户、供应商主键)")
	private String itemID;
	/**部门主键*/
	@Excel(name="部门主键")
	private String deptID;
	/**职员主键*/
	@Excel(name="职员主键")
	private String empID;
	/**期初金额=上一期期末*/
	@Excel(name="期初金额=上一期期末")
	private BigDecimal begBal;
	/**本期收入金额*/
	@Excel(name="本期收入金额")
	private BigDecimal debit;
	/**本期发出金额*/
	@Excel(name="本期发出金额")
	private BigDecimal credit;
	/**本年累计收入金额*/
	@Excel(name="本年累计收入金额")
	private BigDecimal ytdDebit;
	/**本年累计发出金额*/
	@Excel(name="本年累计发出金额")
	private BigDecimal ytdCredit;
	/**期末结存金额=期初金额+本期收入-本期发出*/
	@Excel(name="期末结存金额=期初金额+本期收入-本期发出")
	private BigDecimal endBal;
	/**机构ID*/
	private String sonID;
	/**父ID*/
	private String fid;
	/**业务类型名称*/
	private String rpname;
	/**客户名称或供应商名称*/
	private String name;
	/**部门名称*/
	private String departname;
	/**员工名称*/
	private String empname;

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
	 *@return: java.lang.String  年份
	 */
	@Column(name ="YEAR",nullable=true,length=32)
	public String getYear(){
		return this.year;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  年份
	 */
	public void setYear(String year){
		this.year = year;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  月份
	 */
	@Column(name ="PERIOD",nullable=true,length=32)
	public String getPeriod(){
		return this.period;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  月份
	 */
	public void setPeriod(String period){
		this.period = period;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  业务类型(0：应付；1：应收)
	 */
	@Column(name ="RP",nullable=true,length=32)
	public Integer getRp(){
		return this.rp;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  业务类型(0：应付；1：应收)
	 */
	public void setRp(Integer rp){
		this.rp = rp;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  核算项目(客户、供应商主键)
	 */
	@Column(name ="ITEMID",nullable=true,length=32)
	public String getItemID(){
		return this.itemID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  核算项目(客户、供应商主键)
	 */
	public void setItemID(String itemID){
		this.itemID = itemID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门主键
	 */
	@Column(name ="DEPTID",nullable=true,length=32)
	public String getDeptID(){
		return this.deptID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门主键
	 */
	public void setDeptID(String deptID){
		this.deptID = deptID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  职员主键
	 */
	@Column(name ="EMPID",nullable=true,length=32)
	public String getEmpID(){
		return this.empID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  职员主键
	 */
	public void setEmpID(String empID){
		this.empID = empID;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  期初金额=上一期期末
	 */
	@Column(name ="BEGBAL",nullable=true,length=32)
	public BigDecimal getBegBal(){
		return this.begBal;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  期初金额=上一期期末
	 */
	public void setBegBal(BigDecimal begBal){
		this.begBal = begBal;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本期收入金额
	 */
	@Column(name ="DEBIT",nullable=true,length=32)
	public BigDecimal getDebit(){
		return this.debit;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本期收入金额
	 */
	public void setDebit(BigDecimal debit){
		this.debit = debit;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本期发出金额
	 */
	@Column(name ="CREDIT",nullable=true,length=32)
	public BigDecimal getCredit(){
		return this.credit;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本期发出金额
	 */
	public void setCredit(BigDecimal credit){
		this.credit = credit;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本年累计收入金额
	 */
	@Column(name ="YTDDEBIT",nullable=true,length=32)
	public BigDecimal getYtdDebit(){
		return this.ytdDebit;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本年累计收入金额
	 */
	public void setYtdDebit(BigDecimal ytdDebit){
		this.ytdDebit = ytdDebit;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本年累计发出金额
	 */
	@Column(name ="YTDCREDIT",nullable=true,length=32)
	public BigDecimal getYtdCredit(){
		return this.ytdCredit;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本年累计发出金额
	 */
	public void setYtdCredit(BigDecimal ytdCredit){
		this.ytdCredit = ytdCredit;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  期末结存金额=期初金额+本期收入-本期发出
	 */
	@Column(name ="ENDBAL",nullable=true,length=32)
	public BigDecimal getEndBal(){
		return this.endBal;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  期末结存金额=期初金额+本期收入-本期发出
	 */
	public void setEndBal(BigDecimal endBal){
		this.endBal = endBal;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  父ID
	 */
	@Column(name ="FID",nullable=true,length=32)
	public String getFid(){
		return this.fid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  父ID
	 */
	public void setFid(String fid){
		this.fid = fid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户名称或供应商名称
	 */
	@Column(name ="NAME",nullable=true,length=32)
	public String getName() {
		return name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结算账户名称
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门名称
	 */
	@Column(name ="DEPARTNAME",nullable=true,length=32)
	public String getDepartname() {
		return departname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门名称
	 */
	public void setDepartname(String departname) {
		this.departname = departname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  员工名称
	 */
	@Column(name ="EMPNAME",nullable=true,length=32)
	public String getEmpname() {
		return empname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  员工名称
	 */
	public void setEmpname(String empname) {
		this.empname = empname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务类型名称
	 */
	@Column(name ="RPNAME",nullable=true,length=32)
	public String getRpname() {
		return rpname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  业务类型名称
	 */
	public void setRpname(String rpname) {
		this.rpname = rpname;
	}
}
