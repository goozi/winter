package com.qihang.buss.sc.rp.entity;

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
 * @Description: 应收表
 * @author onlineGenerator
 * @date 2016-08-24 20:50:38
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_RP_RBillEntry2", schema = "")
@SuppressWarnings("serial")
public class TScRpRbillentry2Entity implements java.io.Serializable {
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
	/**父id*/
	private java.lang.String fid;
	/**分录号*/
	private java.lang.Integer findex;
	/**源单类型*/
	@Excel(name="源单类型")
	private java.lang.String classIdSRC;
	/**源单主键*/
	private java.lang.String idSrc;
	/**源单编号*/
	@Excel(name="源单编号")
	private java.lang.String billNoSrc;
	/**源单日期*/
	@Excel(name="源单日期")
	private java.util.Date dateSrc;
	/**源单金额*/
	@Excel(name="源单金额")
	private java.math.BigDecimal billAmount;
	/**源单已执行金额*/
	@Excel(name="源单已执行金额")
	private java.math.BigDecimal billCheckAmount;
	/**源单未执行金额*/
	@Excel(name="源单未执行金额")
	private java.math.BigDecimal billUnCheckAmount;
	/**本次收款*/
	@Excel(name="本次收款")
	private java.math.BigDecimal amount;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String note;

	private String classIdName;
	
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
	 *@return: java.lang.Integer  分录号
	 */
	@Column(name ="FINDEX",nullable=true,length=10)
	public java.lang.Integer getFindex(){
		return this.findex;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  分录号
	 */
	public void setFindex(java.lang.Integer findex){
		this.findex = findex;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单类型
	 */
	@Column(name ="CLASSIDSRC",nullable=true,length=11)
	public java.lang.String getClassIdSRC(){
		return this.classIdSRC;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单类型
	 */
	public void setClassIdSRC(java.lang.String classIdSRC){
		this.classIdSRC = classIdSRC;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单主键
	 */
	@Column(name ="IDSRC",nullable=true,length=32)
	public java.lang.String getIdSrc(){
		return this.idSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单主键
	 */
	public void setIdSrc(java.lang.String idSrc){
		this.idSrc = idSrc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单编号
	 */
	@Column(name ="BILLNOSRC",nullable=true,length=50)
	public java.lang.String getBillNoSrc(){
		return this.billNoSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单编号
	 */
	public void setBillNoSrc(java.lang.String billNoSrc){
		this.billNoSrc = billNoSrc;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  源单日期
	 */
	@Column(name ="DATESRC",nullable=true,length=20)
	public java.util.Date getDateSrc(){
		return this.dateSrc;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  源单日期
	 */
	public void setDateSrc(java.util.Date dateSrc){
		this.dateSrc = dateSrc;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  源单金额
	 */
	@Column(name ="BILLAMOUNT",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getBillAmount(){
		return this.billAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  源单金额
	 */
	public void setBillAmount(java.math.BigDecimal billAmount){
		this.billAmount = billAmount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  源单已执行金额
	 */
	@Column(name ="BILLCHECKAMOUNT",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getBillCheckAmount(){
		return this.billCheckAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  源单已执行金额
	 */
	public void setBillCheckAmount(java.math.BigDecimal billCheckAmount){
		this.billCheckAmount = billCheckAmount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  源单未执行金额
	 */
	@Column(name ="BILLUNCHECKAMOUNT",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getBillUnCheckAmount(){
		return this.billUnCheckAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  源单未执行金额
	 */
	public void setBillUnCheckAmount(java.math.BigDecimal billUnCheckAmount){
		this.billUnCheckAmount = billUnCheckAmount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本次收款
	 */
	@Column(name ="AMOUNT",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本次收款
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

	@Transient
	public String getClassIdName() {
		return classIdName;
	}

	public void setClassIdName(String classIdName) {
		this.classIdName = classIdName;
	}
}
