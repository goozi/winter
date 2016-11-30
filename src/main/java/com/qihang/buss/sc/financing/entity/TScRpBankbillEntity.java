package com.qihang.buss.sc.financing.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.soap.Text;
import java.sql.Blob;

import com.qihang.buss.sc.baseinfo.entity.TScDepartmentEntity;
import com.qihang.buss.sc.baseinfo.entity.TScEmpEntity;
import com.qihang.buss.sc.baseinfo.entity.TScSettleacctEntity;
import com.qihang.buss.sc.baseinfo.entity.TScSoncompanyEntity;
import com.qihang.winter.poi.excel.annotation.ExcelEntity;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.pojo.base.TSUser;
import org.hibernate.annotations.*;
import com.qihang.winter.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 银行存取款
 * @author onlineGenerator
 * @date 2016-08-31 14:42:42
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_sc_rp_bankbill", schema = "")
@SuppressWarnings("serial")
public class TScRpBankbillEntity implements java.io.Serializable {
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
	/**版本信息*/
	private Integer version;
	/**单据类型*/
	private Integer tranType;
	/**单据日期*/
	private Date date;
	/**单据编号*/
	private String billNo ;
	/**金额*/
	private BigDecimal allAmount;
	/**审核日期*/
	private Date checkDate ;
	/**审核状态*/
	private Integer checkState ;
	/**作废标记*/
	private Integer cancellation ;
	/**摘要*/
	private String explanation ;
    /**职员关联实体**/
	private TScEmpEntity tScEmpEntity;
    /**部门关联实体**/
	private TScDepartmentEntity tScDepartmentEntity;
	/**转出机构关联实体**/
	private TSDepart dcsoncompanyEntity;
	/**转入机构关联实体**/
	private TSDepart scsoncompanyEntity;
	/**转出账户**/
	private TScSettleacctEntity pasettleacctEntity;
	/**转入账户**/
	private TScSettleacctEntity rasettleacctEntity;
    /**制单人**/
	private TSUser billerUser;
    /**审核人**/
	private TSUser checkerUser;
	/**分支机构**/
	private TSDepart tsDepart;


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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  版本信息
	 */
	@Column(name ="VERSION",nullable=true,length=11)
	public Integer getVersion(){
		return this.version;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  版本信息
	 */
	public void setVersion(Integer version){
		this.version = version;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  单据类型
	 */
	@Column(name ="TRANTYPE",nullable=true,length=20)
	public Integer getTranType(){
		return this.tranType;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  单据类型
	 */
	public void setTranType(Integer tranType){
		this.tranType = tranType;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  单据日期
	 */
	@Column(name ="DATE",nullable=true,length=32)
	public Date getDate(){
		return this.date;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  单据日期
	 */
	public void setDate(Date date){
		this.date = date;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单据编号
	 */
	@Column(name ="BILLNO ",nullable=true,length=50)
	public String getBillNo (){
		return this.billNo ;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据编号
	 */
	public void setBillNo (String billNo ){
		this.billNo  = billNo ;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  金额
	 */
	@Column(name ="ALLAMOUNT",nullable=true,length=32)
	public BigDecimal getAllAmount(){
		return this.allAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  金额
	 */
	public void setAllAmount(BigDecimal allAmount){
		this.allAmount = allAmount;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  审核日期
	 */
	@Column(name ="CHECKDATE ",nullable=true,length=32)
	public Date getCheckDate (){
		return this.checkDate ;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  审核日期
	 */
	public void setCheckDate (Date checkDate ){
		this.checkDate  = checkDate ;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  审核状态
	 */
	@Column(name ="CHECKSTATE ",nullable=true,length=3)
	public Integer getCheckState (){
		return this.checkState ;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  审核状态
	 */
	public void setCheckState (Integer checkState ){
		this.checkState  = checkState ;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  作废标记
	 */
	@Column(name ="CANCELLATION ",nullable=true,length=3)
	public Integer getCancellation (){
		return this.cancellation ;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  作废标记
	 */
	public void setCancellation (Integer cancellation ){
		this.cancellation  = cancellation ;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  摘要
	 */
	@Column(name ="EXPLANATION ",nullable=true,length=255)
	public String getExplanation (){
		return this.explanation ;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  摘要
	 */
	public void setExplanation (String explanation ){
		this.explanation  = explanation ;
	}

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "empId",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TScEmpEntity gettScEmpEntity() {
		return tScEmpEntity;
	}

	public void settScEmpEntity(TScEmpEntity tScEmpEntity) {
		this.tScEmpEntity = tScEmpEntity;
	}


	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "deptId",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TScDepartmentEntity gettScDepartmentEntity() {
		return tScDepartmentEntity;
	}

	public void settScDepartmentEntity(TScDepartmentEntity tScDepartmentEntity) {
		this.tScDepartmentEntity = tScDepartmentEntity;
	}

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "scsonId",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TSDepart getDcsoncompanyEntity() {
		return dcsoncompanyEntity;
	}

	public void setDcsoncompanyEntity(TSDepart dcsoncompanyEntity) {
		this.dcsoncompanyEntity = dcsoncompanyEntity;
	}
	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "dcsonId",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TSDepart getScsoncompanyEntity() {
		return scsoncompanyEntity;
	}

	public void setScsoncompanyEntity(TSDepart scsoncompanyEntity) {
		this.scsoncompanyEntity = scsoncompanyEntity;
	}

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "paccountId",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TScSettleacctEntity getPasettleacctEntity() {
		return pasettleacctEntity;
	}

	public void setPasettleacctEntity(TScSettleacctEntity pasettleacctEntity) {
		this.pasettleacctEntity = pasettleacctEntity;
	}

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "raccountId",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TScSettleacctEntity getRasettleacctEntity() {
		return rasettleacctEntity;
	}

	public void setRasettleacctEntity(TScSettleacctEntity rasettleacctEntity) {
		this.rasettleacctEntity = rasettleacctEntity;
	}

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "billerId",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TSUser getBillerUser() {
		return billerUser;
	}

	public void setBillerUser(TSUser billerUser) {
		this.billerUser = billerUser;
	}

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "checkerId",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TSUser getCheckerUser() {
		return checkerUser;
	}

	public void setCheckerUser(TSUser checkerUser) {
		this.checkerUser = checkerUser;
	}


	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "sonId",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TSDepart getTsDepart() {
		return tsDepart;
	}

	public void setTsDepart(TSDepart tsDepart) {
		this.tsDepart = tsDepart;
	}
}
