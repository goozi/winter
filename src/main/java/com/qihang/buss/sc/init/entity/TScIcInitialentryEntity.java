package com.qihang.buss.sc.init.entity;

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
 * @Description: 附录明细
 * @author onlineGenerator
 * @date 2016-08-18 11:32:00
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_IC_InitialEntry", schema = "")
@SuppressWarnings("serial")
public class TScIcInitialentryEntity implements java.io.Serializable {
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
	/**父ID*/
	private java.lang.String fid;
	/**分录号*/
	private java.lang.String indexnumber;
	/**仓库*/
	@Excel(name="仓库")
	private java.lang.String stockId;
	/**商品*/
	@Excel(name="商品")
	private java.lang.String itemId;
	/**单位*/
	@Excel(name="单位")
	private java.lang.String unitId;
	/**辅助单位*/
	private java.lang.String secUnitId;
	/**基本单位*/
	private java.lang.String basicUnitId;
	/**批号*/
	@Excel(name="批号")
	private java.lang.String batchNo;
	/**生产日期*/
	@Excel(name="生产日期")
	private java.util.Date kfDate;
	/**保质期*/
	@Excel(name="保质期")
	private java.lang.Integer kfPeriod;
	/**有效期至*/
	@Excel(name="有效期至")
	private java.util.Date periodDate;
	/**保质期类型*/
	@Excel(name="保质期类型")
	private java.lang.String kfDateType;
	/**箱数*/
	@Excel(name="箱数")
	private java.math.BigDecimal qty;
	/**散数*/
	@Excel(name="散数")
	private java.math.BigDecimal smallQty;
	/**辅助换算率*/
	private java.math.BigDecimal secCoefficient;
	/**辅助数量*/
	private java.math.BigDecimal secQty;
	/**换算率*/
	@Excel(name="换算率")
	private java.math.BigDecimal coefficient;
	/**数量*/
	@Excel(name="数量")
	private java.math.BigDecimal basicQty;
	/**成本单价*/
	@Excel(name="成本单价")
	private java.math.BigDecimal costPrice;
	/**成本金额*/
	@Excel(name="成本金额")
	private java.math.BigDecimal costAmount;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String note;

	//商品相关非持久化属性
	private java.lang.String itemNo;//商品编号
	private java.lang.String itemName;//商品名称
	private java.lang.String model;//商品规格
	private java.lang.String barCode;//条形码
	//相关bigDecimal属性
	private java.lang.String qtystr;
	private java.lang.String smallQtystr;
	private java.lang.String secCoefficientstr;
	private java.lang.String secQtystr;
	private java.lang.String coefficientstr;
	private java.lang.String basicQtystr;
	private java.lang.String costPricestr;
	private java.lang.String costAmountstr;
	private String isKFPeriod;//是否保质期管理
	private String batchManager;//批号管理
	
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
	 *@return: java.lang.String  父ID
	 */
	@Column(name ="FID",nullable=true,length=32)
	public java.lang.String getFid(){
		return this.fid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  父ID
	 */
	public void setFid(java.lang.String fid){
		this.fid = fid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分录号
	 */
	@Column(name ="INDEXNUMBER",nullable=true,length=32)
	public java.lang.String getIndexnumber(){
		return this.indexnumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分录号
	 */
	public void setIndexnumber(java.lang.String indexnumber){
		this.indexnumber = indexnumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库
	 */
	@Column(name ="STOCKID",nullable=true,length=32)
	public java.lang.String getStockId(){
		return this.stockId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库
	 */
	public void setStockId(java.lang.String stockId){
		this.stockId = stockId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品
	 */
	@Column(name ="ITEMID",nullable=true,length=32)
	public java.lang.String getItemId(){
		return this.itemId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品
	 */
	public void setItemId(java.lang.String itemId){
		this.itemId = itemId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */
	@Column(name ="UNITID",nullable=true,length=32)
	public java.lang.String getUnitId(){
		return this.unitId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setUnitId(java.lang.String unitId){
		this.unitId = unitId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  辅助单位
	 */
	@Column(name ="SECUNITID",nullable=true,length=32)
	public java.lang.String getSecUnitId(){
		return this.secUnitId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  辅助单位
	 */
	public void setSecUnitId(java.lang.String secUnitId){
		this.secUnitId = secUnitId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  基本单位
	 */
	@Column(name ="BASICUNITID",nullable=true,length=36)
	public java.lang.String getBasicUnitId(){
		return this.basicUnitId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  基本单位
	 */
	public void setBasicUnitId(java.lang.String basicUnitId){
		this.basicUnitId = basicUnitId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  批号
	 */
	@Column(name ="BATCHNO",nullable=true,length=100)
	public java.lang.String getBatchNo(){
		return this.batchNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  批号
	 */
	public void setBatchNo(java.lang.String batchNo){
		this.batchNo = batchNo;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  生产日期
	 */
	@Column(name ="KFDATE",nullable=true,length=32)
	public java.util.Date getKfDate(){
		return this.kfDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  生产日期
	 */
	public void setKfDate(java.util.Date kfDate){
		this.kfDate = kfDate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  保质期
	 */
	@Column(name ="KFPERIOD",nullable=true,length=32)
	public java.lang.Integer getKfPeriod(){
		return this.kfPeriod;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  保质期
	 */
	public void setKfPeriod(java.lang.Integer kfPeriod){
		this.kfPeriod = kfPeriod;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  有效期至
	 */
	@Column(name ="PERIODDATE",nullable=true,length=32)
	public java.util.Date getPeriodDate(){
		return this.periodDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  有效期至
	 */
	public void setPeriodDate(java.util.Date periodDate){
		this.periodDate = periodDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  保质期类型
	 */
	@Column(name ="KFDATETYPE",nullable=true,length=32)
	public java.lang.String getKfDateType(){
		return this.kfDateType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  保质期类型
	 */
	public void setKfDateType(java.lang.String kfDateType){
		this.kfDateType = kfDateType;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  箱数
	 */
	@Column(name ="QTY",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getQty(){
		return this.qty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  箱数
	 */
	public void setQty(java.math.BigDecimal qty){
		this.qty = qty;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  散数
	 */
	@Column(name ="SMALLQTY",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getSmallQty(){
		return this.smallQty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  散数
	 */
	public void setSmallQty(java.math.BigDecimal smallQty){
		this.smallQty = smallQty;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  辅助换算率
	 */
	@Column(name ="SECCOEFFICIENT",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getSecCoefficient(){
		return this.secCoefficient;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  辅助换算率
	 */
	public void setSecCoefficient(java.math.BigDecimal secCoefficient){
		this.secCoefficient = secCoefficient;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  辅助数量
	 */
	@Column(name ="SECQTY",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getSecQty(){
		return this.secQty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  辅助数量
	 */
	public void setSecQty(java.math.BigDecimal secQty){
		this.secQty = secQty;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  换算率
	 */
	@Column(name ="COEFFICIENT",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getCoefficient(){
		return this.coefficient;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  换算率
	 */
	public void setCoefficient(java.math.BigDecimal coefficient){
		this.coefficient = coefficient;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  数量
	 */
	@Column(name ="BASICQTY",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getBasicQty(){
		return this.basicQty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  数量
	 */
	public void setBasicQty(java.math.BigDecimal basicQty){
		this.basicQty = basicQty;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  成本单价
	 */
	@Column(name ="COSTPRICE",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getCostPrice(){
		return this.costPrice;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  成本单价
	 */
	public void setCostPrice(java.math.BigDecimal costPrice){
		this.costPrice = costPrice;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  成本金额
	 */
	@Column(name ="COSTAMOUNT",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getCostAmount(){
		return this.costAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  成本金额
	 */
	public void setCostAmount(java.math.BigDecimal costAmount){
		this.costAmount = costAmount;
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
	public String getItemNo() {
		return itemNo;
	}

	public void setItemNo(String itemNo) {
		this.itemNo = itemNo;
	}

	@Transient
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Transient
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Transient
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	@Transient
	public String getQtystr() {
		String returnValue =this.qty==null?"":(this.qty.stripTrailingZeros()==null?"":this.qty.stripTrailingZeros().toPlainString());
		return returnValue;
	}

	public void setQtystr(String qtystr) {
		this.qtystr = qtystr;
	}

	@Transient
	public String getSmallQtystr() {
		String returnValue =this.smallQty==null?"":(this.smallQty.stripTrailingZeros()==null?"":this.smallQty.stripTrailingZeros().toPlainString());
		return returnValue;
	}

	public void setSmallQtystr(String smallQtystr) {
		this.smallQtystr = smallQtystr;
	}

	@Transient
	public String getSecCoefficientstr() {
		String returnValue =this.secCoefficient==null?"":(this.secCoefficient.stripTrailingZeros()==null?"":this.secCoefficient.stripTrailingZeros().toPlainString());
		return returnValue;
	}

	public void setSecCoefficientstr(String secCoefficientstr) {
		this.secCoefficientstr = secCoefficientstr;
	}

	@Transient
	public String getSecQtystr() {
		String returnValue =this.secQty==null?"":(this.secQty.stripTrailingZeros()==null?"":this.secQty.stripTrailingZeros().toPlainString());
		return returnValue;
	}

	public void setSecQtystr(String secQtystr) {
		this.secQtystr = secQtystr;
	}

	@Transient
	public String getCoefficientstr() {
		String returnValue =this.coefficient==null?"":(this.coefficient.stripTrailingZeros()==null?"":this.coefficient.stripTrailingZeros().toPlainString());
		return returnValue;
	}

	public void setCoefficientstr(String coefficientstr) {
		this.coefficientstr = coefficientstr;
	}

	@Transient
	public String getBasicQtystr() {
		String returnValue =this.basicQty==null?"":(this.basicQty.stripTrailingZeros()==null?"":this.basicQty.stripTrailingZeros().toPlainString());
		return returnValue;
	}

	public void setBasicQtystr(String basicQtystr) {
		this.basicQtystr = basicQtystr;
	}

	@Transient
	public String getCostPricestr() {
		String returnValue =this.costPrice==null?"":(this.costPrice.stripTrailingZeros()==null?"":this.costPrice.stripTrailingZeros().toPlainString());
		return returnValue;
	}

	public void setCostPricestr(String costPricestr) {
		this.costPricestr = costPricestr;
	}

	@Transient
	public String getCostAmountstr() {
		String returnValue =this.costAmount==null?"":(this.costAmount.stripTrailingZeros()==null?"":this.costAmount.stripTrailingZeros().toPlainString());
		return returnValue;
	}

	public void setCostAmountstr(String costAmountstr) {
		this.costAmountstr = costAmountstr;
	}

	@Transient
	public String getIsKFPeriod() {
		return isKFPeriod;
	}

	public void setIsKFPeriod(String isKFPeriod) {
		this.isKFPeriod = isKFPeriod;
	}
	@Transient
	public String getBatchManager() {
		return batchManager;
	}

	public void setBatchManager(String batchManager) {
		this.batchManager = batchManager;
	}
}