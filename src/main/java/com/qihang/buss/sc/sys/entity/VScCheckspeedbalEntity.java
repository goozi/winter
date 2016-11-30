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
 * @Description: 负库存情况
 * @author onlineGenerator
 * @date 2016-09-17 15:14:23
 * @version V1.0   
 *
 */
@Entity
@Table(name = "v_sc_checkspeedbal", schema = "")
@SuppressWarnings("serial")
public class VScCheckspeedbalEntity implements java.io.Serializable {
	/**日结年月*/
	private java.util.Date date;
	/**仓库id*/
	private java.lang.String stockid;
	/**商品id*/
	private java.lang.String itemid;
	/**仓库*/
	@Excel(name="仓库")
	private java.lang.String sonname;
	/**商品编号*/
	@Excel(name="商品编号")
	private java.lang.String itemnumber;
	/**商品名称*/
	@Excel(name="商品名称")
	private java.lang.String itemname;
	/**批号*/
	@Excel(name="批号")
	private java.lang.String batchno;
	/**单位*/
	@Excel(name="单位")
	private java.lang.String unitname;
	/**箱数*/
	@Excel(name="箱数")
	private java.lang.Integer bigqty;
	/**散数*/
	@Excel(name="散数")
	private java.lang.Double smallqty;
	/**基本数量*/
	@Excel(name="基本数量")
	private java.lang.Double qty;
	/**辅助数量*/
	@Excel(name="辅助数量")
	private java.lang.Double secqty;
	/**分支机构*/
	@Excel(name="分支机构")
	private java.lang.String departmentname;
	/**id*/
	private java.lang.String id;
	/**单位换算率*/
	private java.lang.Double coefficient;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.util.Date  日结年月
	 */
	@Column(name ="DATE",nullable=true,length=32)
	public java.util.Date getDate(){
		return this.date;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.util.Date  日结年月
	 */
	public void setDate(java.util.Date date){
		this.date = date;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库id
	 */
	@Column(name ="STOCKID",nullable=true,length=32)
	public java.lang.String getStockid(){
		return this.stockid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库id
	 */
	public void setStockid(java.lang.String stockid){
		this.stockid = stockid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品id
	 */
	@Column(name ="ITEMID",nullable=true,length=32)
	public java.lang.String getItemid(){
		return this.itemid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品id
	 */
	public void setItemid(java.lang.String itemid){
		this.itemid = itemid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库
	 */
	@Column(name ="SONNAME",nullable=true,length=80)
	public java.lang.String getSonname(){
		return this.sonname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库
	 */
	public void setSonname(java.lang.String sonname){
		this.sonname = sonname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品编号
	 */
	@Column(name ="ITEMNUMBER",nullable=true,length=80)
	public java.lang.String getItemnumber(){
		return this.itemnumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品编号
	 */
	public void setItemnumber(java.lang.String itemnumber){
		this.itemnumber = itemnumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品名称
	 */
	@Column(name ="ITEMNAME",nullable=true,length=100)
	public java.lang.String getItemname(){
		return this.itemname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品名称
	 */
	public void setItemname(java.lang.String itemname){
		this.itemname = itemname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  批号
	 */
	@Column(name ="BATCHNO",nullable=true,length=100)
	public java.lang.String getBatchno(){
		return this.batchno;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  批号
	 */
	public void setBatchno(java.lang.String batchno){
		this.batchno = batchno;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */
	@Column(name ="UNITNAME",nullable=true,length=80)
	public java.lang.String getUnitname(){
		return this.unitname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setUnitname(java.lang.String unitname){
		this.unitname = unitname;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  箱数
	 */
	@Column(name ="BIGQTY",nullable=true,length=19)
	public java.lang.Integer getBigqty(){
		return this.bigqty;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  箱数
	 */
	public void setBigqty(java.lang.Integer bigqty){
		this.bigqty = bigqty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  散数
	 */
	@Column(name ="SMALLQTY",nullable=true,length=23)
	public java.lang.Double getSmallqty(){
		return this.smallqty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  散数
	 */
	public void setSmallqty(java.lang.Double smallqty){
		this.smallqty = smallqty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  基本数量
	 */
	@Column(name ="QTY",nullable=true,length=23)
	public java.lang.Double getQty(){
		return this.qty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  基本数量
	 */
	public void setQty(java.lang.Double qty){
		this.qty = qty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  辅助数量
	 */
	@Column(name ="SECQTY",nullable=true,length=23)
	public java.lang.Double getSecqty(){
		return this.secqty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  辅助数量
	 */
	public void setSecqty(java.lang.Double secqty){
		this.secqty = secqty;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分支机构
	 */
	@Column(name ="DEPARTMENTNAME",nullable=true,length=100)
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
	@Column(name ="ID",nullable=true,length=170)
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
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  单位换算率
	 */
	@Column(name ="COEFFICIENT",nullable=true,length=22)
	public java.lang.Double getCoefficient(){
		return this.coefficient;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  单位换算率
	 */
	public void setCoefficient(java.lang.Double coefficient){
		this.coefficient = coefficient;
	}
}
