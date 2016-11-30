package com.qihang.buss.sc.inventory.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 即时库存基本表
 * @author onlineGenerator
 * @date 2016-07-23 09:30:20
 * @version V1.0   
 *
 */
@Entity
@Table(name = "v_sc_ic_inventory", schema = "")
@SuppressWarnings("serial")
public class TScIcInventoryViewEntity implements java.io.Serializable {
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
	/**商品id*/
	private String itemId;
	/**产库id*/
	private String stockId;
	@Excel(name="商品编号")
	private String itemNo;//商品编号
	@Excel(name="商品名称")
	private String itemName;//商品名称
	@Excel(name="规格")
	private String model;//规格
	@Excel(name="条形码")
	private String barCode;//条形码
	@Excel(name="仓库")
	private String stockName;//仓库名称
	/**箱数*/
	@Excel(name="箱数")
	private Double qty;
	/**散数*/
	@Excel(name="散数")
	private Double smallQty;
	/**基本数量*/
	@Excel(name="基本数量")
	private Double basicQty;
	/**辅助数量*/
	@Excel(name="辅助数量")
	private Double secQty;
	/**存货单价*/
	@Excel(name="存货单价")
	private Double costPrice;
	/**存货金额*/
	@Excel(name="存货金额")
	private Double costAmount;
	/**单据使用量*/
	private Integer count;

	private Integer isZero;//是否零库存

	private String sonId;//分支机构
	@Excel(name="分支机构")
	private String sonName;//分支机构


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
	 *@return: java.lang.String  产库id
	 */
	@Column(name ="STOCKID",nullable=true,length=32)
	public String getStockId(){
		return this.stockId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  产库id
	 */
	public void setStockId(String stockId){
		this.stockId = stockId;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  箱数
	 */
	@Column(name ="QTY",nullable=true,scale=10,length=20)
	public Double getQty(){
		return this.qty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  箱数
	 */
	public void setQty(Double qty){
		this.qty = qty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  散数
	 */
	@Column(name ="SMALLQTY",nullable=true,scale=10,length=20)
	public Double getSmallQty(){
		return this.smallQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  散数
	 */
	public void setSmallQty(Double smallQty){
		this.smallQty = smallQty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  基本数量
	 */
	@Column(name ="BASICQTY",nullable=true,scale=10,length=20)
	public Double getBasicQty(){
		return this.basicQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  基本数量
	 */
	public void setBasicQty(Double basicQty){
		this.basicQty = basicQty;
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
	 *@return: java.lang.Double  存货单价
	 */
	@Column(name ="COSTPRICE",nullable=true,scale=10,length=20)
	public Double getCostPrice(){
		return this.costPrice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  存货单价
	 */
	public void setCostPrice(Double costPrice){
		this.costPrice = costPrice;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  存货金额
	 */
	@Column(name ="COSTAMOUNT",nullable=true,scale=10,length=20)
	public Double getCostAmount(){
		return this.costAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  存货金额
	 */
	public void setCostAmount(Double costAmount){
		this.costAmount = costAmount;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  单据使用量
	 */
	@Column(name ="COUNT",nullable=true,length=32)
	public Integer getCount(){
		return this.count;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  单据使用量
	 */
	public void setCount(Integer count){
		this.count = count;
	}

	@Column(name ="itemNo",nullable=true)
	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	@Column(name ="itemName",nullable=true)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name ="stockName",nullable=true)
	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	@Column(name ="barCode",nullable=true)
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	@Column(name ="isZero",nullable=true)
	public Integer getIsZero() {
		return isZero;
	}

	public void setIsZero(Integer isZero) {
		this.isZero = isZero;
	}

	@Column(name="sonId",nullable = true)
	public String getSonId() {
		return sonId;
	}

	public void setSonId(String sonId) {
		this.sonId = sonId;
	}

	@Column(name="sonName",nullable = true)
	public String getSonName() {
		return sonName;
	}

	public void setSonName(String sonName) {
		this.sonName = sonName;
	}

	@Column(name="model",nullable = true)
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}
}
