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
 * @Description: 未审核单据
 * @author onlineGenerator
 * @date 2016-09-17 15:14:23
 * @version V1.0   
 *
 */
@Entity
@Table(name = "v_sc_checkstage", schema = "")
@SuppressWarnings("serial")
public class VScCheckstageEntity implements java.io.Serializable {
	/**审核状态*/
	private java.lang.Integer checkstate;
	/**单据类型*/
	private java.lang.String trantype;
	/**单据类型*/
	@Excel(name="单据类型")
	private java.lang.String billName;
	/**单据编号*/
	@Excel(name="单据编号")
	private java.lang.String billno;
	/**分支机构ID*/
	private java.lang.String sonid;
	/**单据日期*/
	@Excel(name="单据日期")
	private java.util.Date date;
	/**分支机构*/
	@Excel(name="分支机构")
	private java.lang.String departmentname;
	/**id*/
	private java.lang.String id;
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  审核状态
	 */
	@Column(name ="CHECKSTATE",nullable=true,length=50)
	public java.lang.Integer getCheckstate(){
		return this.checkstate;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  审核状态
	 */
	public void setCheckstate(java.lang.Integer checkstate){
		this.checkstate = checkstate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单据类型
	 */
	@Column(name ="TRANTYPE",nullable=true,length=32)
	public java.lang.String getTrantype(){
		return this.trantype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据类型
	 */
	public void setTrantype(java.lang.String trantype){
		this.trantype = trantype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单据类型
	 */
	@Column(name ="BILL_NAME",nullable=true,length=100)
	public java.lang.String getBillName(){
		return this.billName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据类型
	 */
	public void setBillName(java.lang.String billName){
		this.billName = billName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单据编号
	 */
	@Column(name ="BILLNO",nullable=true,length=50)
	public java.lang.String getBillno(){
		return this.billno;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据编号
	 */
	public void setBillno(java.lang.String billno){
		this.billno = billno;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分支机构ID
	 */
	@Column(name ="SONID",nullable=true,length=36)
	public java.lang.String getSonid(){
		return this.sonid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分支机构ID
	 */
	public void setSonid(java.lang.String sonid){
		this.sonid = sonid;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  单据日期
	 */
	@Column(name ="DATE",nullable=true,length=32)
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
	 *@return: java.lang.String  分支机构
	 */
	@Column(name ="DEPARTMENTNAME",nullable=true,length=80)
	public java.lang.String getDepartmentname(){
		return this.departmentname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分支机构
	 */
	public void setDepartmentname(java.lang.String departmentname){
		this.departmentname = departmentname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
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
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
}
