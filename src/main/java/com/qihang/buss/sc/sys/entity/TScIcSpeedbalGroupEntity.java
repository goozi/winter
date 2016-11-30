package com.qihang.buss.sc.sys.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 存货日结表
 * @author onlineGenerator
 * @date 2016-08-20 10:36:42
 * @version V1.0   
 *
 */
@Entity
@Table(name = "v_sc_ic_speedbal_group", schema = "")
@SuppressWarnings("serial")
public class TScIcSpeedbalGroupEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**日期*/
	private Date date;
	/**商品id*/
	@Excel(name="商品id")
	private String itemId;
	/**仓库id*/
	@Excel(name="仓库id")
	private String stockId;
	/**批号*/
	@Excel(name="批号")
	private String batchNo;
	/**数量*/
	@Excel(name="数量")
	private Double qty;
	/**辅助数量*/
	@Excel(name="辅助数量")
	private Double secQty;
	/**成本单价*/
	@Excel(name="成本单价")
	private String price;
	/**成本金额*/
	@Excel(name="成本金额")
	private String amount;

	private String invId;
	private String invBatchId;

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
	 *@return: java.lang.String  商品id
	 */
	@Column(name ="ITEMID",nullable=true,length=32)
	public String getItemId(){
		return this.itemId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品id
	 */
	public void setItemId(String itemId){
		this.itemId = itemId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库id
	 */
	@Column(name ="STOCKID",nullable=true,length=32)
	public String getStockId(){
		return this.stockId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库id
	 */
	public void setStockId(String stockId){
		this.stockId = stockId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  批号
	 */
	@Column(name ="BATCHNO",nullable=true,length=100)
	public String getBatchNo(){
		return this.batchNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  批号
	 */
	public void setBatchNo(String batchNo){
		this.batchNo = batchNo;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  数量
	 */
	@Column(name ="QTY",nullable=true,scale=10,length=20)
	public Double getQty(){
		return this.qty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  数量
	 */
	public void setQty(Double qty){
		this.qty = qty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  辅助数量
	 */
	@Column(name ="SECQTY",nullable=true,scale=10,length=20)
	public Double getSecQty(){
		return this.secQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  辅助数量
	 */
	public void setSecQty(Double secQty){
		this.secQty = secQty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  成本单价
	 */
	@Column(name ="PRICE",nullable=true,scale=10,length=20)
	public String getPrice(){
		return this.price;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  成本单价
	 */
	public void setPrice(String price){
		this.price = price;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  成本金额
	 */
	@Column(name ="AMOUNT",nullable=true,scale=10,length=20)
	public String getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  成本金额
	 */
	public void setAmount(String amount){
		this.amount = amount;
	}

	@Column(name ="date",nullable=true)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Column(name ="invId",nullable=true)
	public String getInvId() {
		return invId;
	}

	public void setInvId(String invId) {
		this.invId = invId;
	}

	@Id
	@Column(name ="invBatchId",nullable=true)
	public String getInvBatchId() {
		return invBatchId;
	}

	public void setInvBatchId(String invBatchId) {
		this.invBatchId = invBatchId;
	}
}
