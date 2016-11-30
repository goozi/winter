package com.qihang.buss.sc.inventory.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 即时库存批号表
 * @author onlineGenerator
 * @date 2016-07-23 09:31:58
 * @version V1.0   
 *
 */
@Entity
@Table(name = "v_sc_ic_inventory_batchno", schema = "")
@SuppressWarnings("serial")
public class TScIcInventoryBatchnoViewEntity implements java.io.Serializable {
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
	@Excel(name="商品id")
	private String itemId;
	/**仓库id*/
	@Excel(name="仓库id")
	private String stockId;
	/**批号*/
	@Excel(name="批号")
	private String batchNo;
	/**生产日期*/
	@Excel(name="生产日期")
	private Date kfDate;
	/**保质期*/
	@Excel(name="保质期")
	private Integer kfPeriod;
	/**保质期类型*/
	@Excel(name="保质期类型")
	private String kfDateType;
	/**有效期至*/
	@Excel(name="有效期至")
	private Date periodDate;
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
	/**单据数量*/
	private Integer count;

	private String itemName;
	private String itemNo;
	private String stockName;
	private String barCode;
	private String model;

	private String kfDateType1;
	private String iSKFPeriod;
	private String batchManager;
	private Integer isCheck;
	private String isZero;

	private Double taxRateIn;//进项税率
	private Double taxRateOut;//销项税率

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
	@Column(name ="BATCHNO",nullable=true,length=50)
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  生产日期
	 */
	@Column(name ="KFDATE",nullable=true,length=20)
	public Date getKfDate(){
		return this.kfDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  生产日期
	 */
	public void setKfDate(Date kfDate){
		this.kfDate = kfDate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  保质期
	 */
	@Column(name ="KFPERIOD",nullable=true,length=10)
	public Integer getKfPeriod(){
		return this.kfPeriod;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  保质期
	 */
	public void setKfPeriod(Integer kfPeriod){
		this.kfPeriod = kfPeriod;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  保质期类型
	 */
	@Column(name ="KFDATETYPE",nullable=true,length=11)
	public String getKfDateType(){
		return this.kfDateType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  保质期类型
	 */
	public void setKfDateType(String kfDateType){
		this.kfDateType = kfDateType;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  有效期至
	 */
	@Column(name ="PERIODDATE",nullable=true,length=20)
	public Date getPeriodDate(){
		return this.periodDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  有效期至
	 */
	public void setPeriodDate(Date periodDate){
		this.periodDate = periodDate;
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
	 *@return: java.lang.Integer  单据数量
	 */
	@Column(name ="COUNT",nullable=true,length=32)
	public Integer getCount(){
		return this.count;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  单据数量
	 */
	public void setCount(Integer count){
		this.count = count;
	}

	@Column(name ="itemName",nullable=true)
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name ="itemNo",nullable=true)
	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
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

	@Column(name ="kfDateType1",nullable=true)
	public String getKfDateType1() {
		return kfDateType1;
	}

	public void setKfDateType1(String kfDateType1) {
		this.kfDateType1 = kfDateType1;
	}

	@Column(name ="iSKFPeriod",nullable=true)
	public String getiSKFPeriod() {
		return iSKFPeriod;
	}

	public void setiSKFPeriod(String iSKFPeriod) {
		this.iSKFPeriod = iSKFPeriod;
	}

	@Column(name ="batchManager",nullable=true)
	public String getBatchManager() {
		return batchManager;
	}

	public void setBatchManager(String batchManager) {
		this.batchManager = batchManager;
	}

	@Column(name ="model",nullable=true)
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name ="isCheck",nullable=true)
	public Integer getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}

	@Column(name ="isZero",nullable=true)
	public String getIsZero() {
		return isZero;
	}

	public void setIsZero(String isZero) {
		this.isZero = isZero;
	}

	@Column(name ="taxRateIn",nullable=true)
	public Double getTaxRateIn() {
		return taxRateIn;
	}

	public void setTaxRateIn(Double taxRateIn) {
		this.taxRateIn = taxRateIn;
	}

	@Column(name ="taxRateOut",nullable=true)
	public Double getTaxRateOut() {
		return taxRateOut;
	}

	public void setTaxRateOut(Double taxRateOut) {
		this.taxRateOut = taxRateOut;
	}
}
