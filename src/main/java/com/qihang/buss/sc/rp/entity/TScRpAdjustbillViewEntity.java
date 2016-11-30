package com.qihang.buss.sc.rp.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 应收调账
 * @author onlineGenerator
 * @date 2016-08-24 14:44:39
 * @version V1.0   
 *
 */
@Entity
@Table(name = "v_sc_rp_adjustbill", schema = "")
@SuppressWarnings("serial")
public class TScRpAdjustbillViewEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**单据类型*/
	private String tranType;
	/**单据日期*/
	private Date date;
	/**单据编号*/
	private String billNo;
	/**客户*/
  @Excel(name="客户")
  private String itemName;
	private String itemId;
	/**经办人*/
  @Excel(name="经办人")
  private String empName;
	private String empId;
	/**部门*/
  @Excel(name="部门")
  private String deptName;
	private String deptId;
	/**应收金额*/
  @Excel(name="应收金额")
	private BigDecimal allAmount;
	/**执行金额*/
	private String checkAmount;
	/**收支项目*/
	@Excel(name="收支项目")
	private String expName;
	private java.lang.String expId;
	/**金额*/
	@Excel(name="金额")
	private java.math.BigDecimal amount;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String note;
	/**源单类型*/
  @Excel(name="源单类型")
  private String className;
	private String classIdSrc;
	/**源单主键*/
	private String idSrc;
	/**源单编号*/
  @Excel(name="源单编号")
	private String billNoSrc;
	/**制单人*/
	@Excel(name="制单人")
	private String billerName;
	private String billerId;
	/**审核人*/
	@Excel(name="审核人")
	private String checkerName;
	private String checkerId;
	/**审核日期*/
	private Date checkDate;
	/**审核状态*/
	private Integer checkState;
	/**作废标记*/
	private Integer cancellation;
	/**摘要*/
  @Excel(name="摘要")
	private String explanation;
	/**分支机构*/
  @Excel(name="分支机构")
  private String sonName;
	private String sonId;

	/**主键*/
	private java.lang.String entryId;
	/**父id*/
	private java.lang.String fid;
	/**分路号*/
	private java.lang.Integer findex;


	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */

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
	 *@return: java.lang.String  单据类型
	 */

	@Column(name ="TRANTYPE",nullable=true,length=11)
	public String getTranType(){
		return this.tranType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据类型
	 */
	public void setTranType(String tranType){
		this.tranType = tranType;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  单据日期
	 */

	@Column(name ="DATE",nullable=true,length=20)
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

	@Column(name ="BILLNO",nullable=true,length=50)
	public String getBillNo(){
		return this.billNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据编号
	 */
	public void setBillNo(String billNo){
		this.billNo = billNo;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户
	 */

	@Column(name ="ITEMID",nullable=true,length=32)
	public String getItemId(){
		return this.itemId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户
	 */
	public void setItemId(String itemId){
		this.itemId = itemId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  经办人
	 */

	@Column(name ="EMPID",nullable=true,length=32)
	public String getEmpId(){
		return this.empId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  经办人
	 */
	public void setEmpId(String empId){
		this.empId = empId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门
	 */

	@Column(name ="DEPTID",nullable=true,length=32)
	public String getDeptId(){
		return this.deptId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门
	 */
	public void setDeptId(String deptId){
		this.deptId = deptId;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  应收金额
	 */

	@Column(name ="ALLAMOUNT",nullable=true,scale=10,length=20)
	public BigDecimal getAllAmount(){
		return this.allAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  应收金额
	 */
	public void setAllAmount(BigDecimal allAmount){
		this.allAmount = allAmount;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  执行金额
	 */

	@Column(name ="CHECKAMOUNT",nullable=true,length=32)
	public String getCheckAmount(){
		return this.checkAmount;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  执行金额
	 */
	public void setCheckAmount(String checkAmount){
		this.checkAmount = checkAmount;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单类型
	 */

	@Column(name ="CLASSIDSRC",nullable=true,length=11)
	public String getClassIdSrc(){
		return this.classIdSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单类型
	 */
	public void setClassIdSrc(String classIdSrc){
		this.classIdSrc = classIdSrc;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单主键
	 */

	@Column(name ="IDSRC",nullable=true,length=32)
	public String getIdSrc(){
		return this.idSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单主键
	 */
	public void setIdSrc(String idSrc){
		this.idSrc = idSrc;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单编号
	 */

	@Column(name ="BILLNOSRC",nullable=true,length=50)
	public String getBillNoSrc(){
		return this.billNoSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单编号
	 */
	public void setBillNoSrc(String billNoSrc){
		this.billNoSrc = billNoSrc;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  制单人
	 */

	@Column(name ="BILLERID",nullable=true,length=32)
	public String getBillerId(){
		return this.billerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  制单人
	 */
	public void setBillerId(String billerId){
		this.billerId = billerId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核人
	 */

	@Column(name ="CHECKERID",nullable=true,length=32)
	public String getCheckerId(){
		return this.checkerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核人
	 */
	public void setCheckerId(String checkerId){
		this.checkerId = checkerId;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  审核日期
	 */

	@Column(name ="CHECKDATE",nullable=true,length=20)
	public Date getCheckDate(){
		return this.checkDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  审核日期
	 */
	public void setCheckDate(Date checkDate){
		this.checkDate = checkDate;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  审核状态
	 */

	@Column(name ="CHECKSTATE",nullable=true,length=1)
	public Integer getCheckState(){
		return this.checkState;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  审核状态
	 */
	public void setCheckState(Integer checkState){
		this.checkState = checkState;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  作废标记
	 */

	@Column(name ="CANCELLATION",nullable=true,length=1)
	public Integer getCancellation(){
		return this.cancellation;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  作废标记
	 */
	public void setCancellation(Integer cancellation){
		this.cancellation = cancellation;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  摘要
	 */

	@Column(name ="EXPLANATION",nullable=true,length=255)
	public String getExplanation(){
		return this.explanation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  摘要
	 */
	public void setExplanation(String explanation){
		this.explanation = explanation;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分支机构
	 */

	@Column(name ="SONID",nullable=true,length=32)
	public String getSonId(){
		return this.sonId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分支机构
	 */
	public void setSonId(String sonId){
		this.sonId = sonId;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  父id
	 */
	@Column(name ="FID",nullable=true,length=32)
	public java.lang.String getFid(){
		return this.fid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  父id
	 */
	public void setFid(java.lang.String fid){
		this.fid = fid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  分路号
	 */
	@Column(name ="FINDEX",nullable=true,length=10)
	public java.lang.Integer getFindex(){
		return this.findex;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  分路号
	 */
	public void setFindex(java.lang.Integer findex){
		this.findex = findex;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收支项目
	 */
	@Column(name ="EXPID",nullable=true,length=32)
	public java.lang.String getExpId(){
		return this.expId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收支项目
	 */
	public void setExpId(java.lang.String expId){
		this.expId = expId;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  金额
	 */
	@Column(name ="AMOUNT",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  金额
	 */
	public void setAmount(java.math.BigDecimal amount){
		this.amount = amount;
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


	@Column(name ="itemName",nullable=true)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name ="empName",nullable=true)
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Column(name ="deptName",nullable=true)
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name ="expName",nullable=true)
	public String getExpName() {
		return expName;
	}

	public void setExpName(String expName) {
		this.expName = expName;
	}

	@Column(name ="billerName",nullable=true)
	public String getBillerName() {
		return billerName;
	}

	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}

	@Column(name ="checkerName",nullable=true)
	public String getCheckerName() {
		return checkerName;
	}

	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}

	@Column(name ="sonName",nullable=true)
	public String getSonName() {
		return sonName;
	}

	public void setSonName(String sonName) {
		this.sonName = sonName;
	}

	@Id
	@Column(name ="entryId",nullable=true)
	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	@Column(name ="className",nullable=true)
	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}
}
