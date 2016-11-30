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
@Table(name = "v_sc_ic_speedbal", schema = "")
@SuppressWarnings("serial")
public class TScIcSpeedbalViewEntity implements java.io.Serializable {
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
	/**单据日期*/
	@Excel(name="单据日期",format = "yyyy-MM-dd")
	private Date date;
	/**单据类型*/
	@Excel(name="单据类型")
	private String tranType;
	/**单据主键*/
	private String billId;
	/**分录主键*/
	private String billEntryId;
	/**单据创建日期*/
	@Excel(name="单据创建日期",format = "yyyy-MM-dd")
	private Date billCreateTime;
	/**商品id*/
	//@Excel(name="商品id")
	private String itemId;

	@Excel(name="商品编号")
	private String itemNo;//商品编号
	@Excel(name="商品名称")
	private String itemName;//商品名称
	@Excel(name="规格")
	private String model;//规格
	@Excel(name="条形码")
	private String barCode;//条形码
	/**仓库id*/
	//@Excel(name="仓库id")
	private String stockId;

	@Excel(name="仓库")
	private String stockName;//仓库
	//@Excel(name="单位")
	private String unitName;//单位
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
	private Double price;
	/**成本金额*/
	@Excel(name="成本金额")
	private Double amount;
	/**结余单价*/
	@Excel(name="结余单价")
	private Double ePrice;
	/**结余金额*/
	@Excel(name="结余金额")
	private Double eAmount;
	/**差异金额*/
	@Excel(name="差异金额")
	private Double diffAmount;
	/**源单类型*/
	@Excel(name="源单类型")
	private String blidSrc;
	/**出入库标记*/
	@Excel(name="出入库标记",replace = {"出库类单据_-1","入库类单据_1"})
	private Integer flag;
	/**计算状态*/
	@Excel(name="计算状态",replace = {"存货计价已计算完成_0","存货计价未计算完成_1"})
	private Integer status;
	/**负结余处理状态*/
	@Excel(name="负结余处理状态",replace = {"未处理_0","已处理_1"})
	private Integer negFlag;




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
	 *@return: java.lang.String  单据类型
	 */
	@Column(name ="TRANTYPE",nullable=true,length=32)
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单据主键
	 */
	@Column(name ="BILLID",nullable=true,length=32)
	public String getBillId(){
		return this.billId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据主键
	 */
	public void setBillId(String billId){
		this.billId = billId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分录主键
	 */
	@Column(name ="BILLENTRYID",nullable=true,length=32)
	public String getBillEntryId(){
		return this.billEntryId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分录主键
	 */
	public void setBillEntryId(String billEntryId){
		this.billEntryId = billEntryId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  单据创建日期
	 */
	@Column(name ="BILLCREATETIME",nullable=true,length=20)
	public Date getBillCreateTime(){
		return this.billCreateTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  单据创建日期
	 */
	public void setBillCreateTime(Date billCreateTime){
		this.billCreateTime = billCreateTime;
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
	public Double getPrice(){
		return this.price;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  成本单价
	 */
	public void setPrice(Double price){
		this.price = price;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  成本金额
	 */
	@Column(name ="AMOUNT",nullable=true,scale=10,length=20)
	public Double getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  成本金额
	 */
	public void setAmount(Double amount){
		this.amount = amount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  结余单价
	 */
	@Column(name ="EPRICE",nullable=true,scale=10,length=20)
	public Double getEPrice(){
		return this.ePrice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  结余单价
	 */
	public void setEPrice(Double ePrice){
		this.ePrice = ePrice;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  结余金额
	 */
	@Column(name ="EAMOUNT",nullable=true,scale=10,length=20)
	public Double getEAmount(){
		return this.eAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  结余金额
	 */
	public void setEAmount(Double eAmount){
		this.eAmount = eAmount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  差异金额
	 */
	@Column(name ="DIFFAMOUNT",nullable=true,scale=10,length=20)
	public Double getDiffAmount(){
		return this.diffAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  差异金额
	 */
	public void setDiffAmount(Double diffAmount){
		this.diffAmount = diffAmount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单类型
	 */
	@Column(name ="BLID_SRC",nullable=true,length=32)
	public String getBlidSrc(){
		return this.blidSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单类型
	 */
	public void setBlidSrc(String blidSrc){
		this.blidSrc = blidSrc;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  出入库标记
	 */
	@Column(name ="FLAG",nullable=true,length=10)
	public Integer getFlag(){
		return this.flag;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  出入库标记
	 */
	public void setFlag(Integer flag){
		this.flag = flag;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  计算状态
	 */
	@Column(name ="STATUS",nullable=true,length=1)
	public Integer getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  计算状态
	 */
	public void setStatus(Integer status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  负结余处理状态
	 */
	@Column(name ="NEGFLAG",nullable=true,length=1)
	public Integer getNegFlag(){
		return this.negFlag;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  负结余处理状态
	 */
	public void setNegFlag(Integer negFlag){
		this.negFlag = negFlag;
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

	@Column(name ="model",nullable=true)
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name ="barCode",nullable=true)
	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	@Column(name ="stockName",nullable=true)
	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}
	@Column(name ="unitName",nullable=true)
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
}
