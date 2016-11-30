package com.qihang.buss.sc.inventory.entity;

import com.qihang.buss.sc.baseinfo.entity.TScIcitemForPoOrderEntity;
import com.qihang.buss.sc.baseinfo.entity.TScItemPriceForPoOrderEntity;
import com.qihang.buss.sc.baseinfo.entity.TScStockForPoOrderEntity;
import com.qihang.winter.poi.excel.annotation.Excel;
import com.qihang.winter.poi.excel.annotation.ExcelEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 盘点单明细
 * @author onlineGenerator
 * @date 2016-08-01 09:43:32
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_IC_ChkStockBillEntry", schema = "")
@SuppressWarnings("serial")
public class TScIcChkstockbillentryExcelEntity implements java.io.Serializable {
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
	/**分录号*/
	@Excel(name="分录号")
	private Integer findex;
	/**商品*/
	//@Excel(name="商品")
	private String itemId;

	@ExcelEntity(id="id",name="name")
	private TScIcitemForPoOrderEntity icitemEntity;

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "itemId",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TScIcitemForPoOrderEntity getIcitemEntity() {
		return icitemEntity;
	}

	public void setIcitemEntity(TScIcitemForPoOrderEntity icitemEntity) {
		this.icitemEntity = icitemEntity;
	}
	/**仓库*/
	//@Excel(name="仓库")
	private String stockId;

	@ExcelEntity(id="id",name="name")
	private TScStockForPoOrderEntity stockEntity;

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "stockId",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TScStockForPoOrderEntity getStockEntity() {
		return stockEntity;
	}

	public void setStockEntity(TScStockForPoOrderEntity stockEntity) {
		this.stockEntity = stockEntity;
	}
	/**批号*/
	@Excel(name="批号")
	private String batchNo;
	/**单位*/
	//@Excel(name="单位")
	private String unitId;

	@ExcelEntity(id="id",name="name")
	private TScItemPriceForPoOrderEntity unitEntity;


	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "unitId",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TScItemPriceForPoOrderEntity getUnitEntity() {
		return unitEntity;
	}

	public void setUnitEntity(TScItemPriceForPoOrderEntity unitEntity) {
		this.unitEntity = unitEntity;
	}
	/**账面箱数*/
	@Excel(name="账面箱数")
	private Double qty;
	/**账面散数*/
	@Excel(name="账面散数")
	private Double smallQty;
	/**账面数量*/
	@Excel(name="账面数量")
	private Double basicQty;
	/**换算率*/
	@Excel(name="换算率")
	private Double coefficient;
	/**箱数*/
	@Excel(name="箱数")
	private Double chkQty;
	/**散数*/
	@Excel(name="散数")
	private Double chkSmallQty;
	/**盘点数量*/
	@Excel(name="盘点数量")
	private Double chkBasicQty;
	/**差异数量*/
	@Excel(name="差异数量")
	private Double diffQty;
	/**成本单价*/
	@Excel(name="成本单价")
	private Double costPrice;
	/**账面金额*/
	@Excel(name="账面金额")
	private Double amount;
	/**盘点金额*/
	@Excel(name="盘点金额")
	private Double chkAmount;
	/**差异金额*/
	@Excel(name="差异金额")
	private Double diffAmount;
	/**辅助单位*/
	private String secUnitId;
	/**基本单位*/
	private String basicUnitId;
	/**账面辅助数量*/
	private Double secQty;
	/**父id*/
	private String fid;


	private String idSrc;//源单id

	private String note;

	private String itemNo;
	private String itemName;
	private String model;
	private String barCode;
	private String stockName;

	private TScIcChkstockbillExcelEntity entryToMain;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fid",referencedColumnName = "ID")
	public TScIcChkstockbillExcelEntity getEntryToMain() {
		return entryToMain;
	}

	public void setEntryToMain(TScIcChkstockbillExcelEntity entryToMain) {
		this.entryToMain = entryToMain;
	}

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
	 *@return: java.lang.String  商品
	 */
	//@Column(name ="ITEMID",nullable=true,length=32)
	@Transient
	public String getItemId(){
		return this.itemId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品
	 */
	public void setItemId(String itemId){
		this.itemId = itemId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库
	 */
	//@Column(name ="STOCKID",nullable=true,length=32)
	@Transient
	public String getStockId(){
		return this.stockId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */
	//@Column(name ="UNITID",nullable=true,length=32)
	@Transient
	public String getUnitId(){
		return this.unitId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setUnitId(String unitId){
		this.unitId = unitId;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  账面箱数
	 */
	@Column(name ="QTY",nullable=true,scale=10,length=20)
	public Double getQty(){
		return this.qty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  账面箱数
	 */
	public void setQty(Double qty){
		this.qty = qty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  账面散数
	 */
	@Column(name ="SMALLQTY",nullable=true,scale=10,length=20)
	public Double getSmallQty(){
		return this.smallQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  账面散数
	 */
	public void setSmallQty(Double smallQty){
		this.smallQty = smallQty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  账面数量
	 */
	@Column(name ="BASICQTY",nullable=true,scale=10,length=20)
	public Double getBasicQty(){
		return this.basicQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  账面数量
	 */
	public void setBasicQty(Double basicQty){
		this.basicQty = basicQty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  换算率
	 */
	@Column(name ="COEFFICIENT",nullable=true,scale=10,length=20)
	public Double getCoefficient(){
		return this.coefficient;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  换算率
	 */
	public void setCoefficient(Double coefficient){
		this.coefficient = coefficient;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  箱数
	 */
	@Column(name ="CHKQTY",nullable=true,scale=10,length=20)
	public Double getChkQty(){
		return this.chkQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  箱数
	 */
	public void setChkQty(Double chkQty){
		this.chkQty = chkQty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  散数
	 */
	@Column(name ="CHKSMALLQTY",nullable=true,scale=10,length=20)
	public Double getChkSmallQty(){
		return this.chkSmallQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  散数
	 */
	public void setChkSmallQty(Double chkSmallQty){
		this.chkSmallQty = chkSmallQty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  盘点数量
	 */
	@Column(name ="CHKBASICQTY",nullable=true,scale=10,length=20)
	public Double getChkBasicQty(){
		return this.chkBasicQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  盘点数量
	 */
	public void setChkBasicQty(Double chkBasicQty){
		this.chkBasicQty = chkBasicQty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  差异数量
	 */
	@Column(name ="DIFFQTY",nullable=true,scale=10,length=20)
	public Double getDiffQty(){
		return this.diffQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  差异数量
	 */
	public void setDiffQty(Double diffQty){
		this.diffQty = diffQty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  成本单价
	 */
	@Column(name ="COSTPRICE",nullable=true,scale=10,length=20)
	public Double getCostPrice(){
		return this.costPrice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  成本单价
	 */
	public void setCostPrice(Double costPrice){
		this.costPrice = costPrice;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  账面金额
	 */
	@Column(name ="AMOUNT",nullable=true,scale=10,length=20)
	public Double getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  账面金额
	 */
	public void setAmount(Double amount){
		this.amount = amount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  盘点金额
	 */
	@Column(name ="CHKAMOUNT",nullable=true,scale=10,length=20)
	public Double getChkAmount(){
		return this.chkAmount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  盘点金额
	 */
	public void setChkAmount(Double chkAmount){
		this.chkAmount = chkAmount;
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
	 *@return: java.lang.String  辅助单位
	 */
	@Column(name ="SECUNITID",nullable=true,length=32)
	public String getSecUnitId(){
		return this.secUnitId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  辅助单位
	 */
	public void setSecUnitId(String secUnitId){
		this.secUnitId = secUnitId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  基本单位
	 */
	@Column(name ="BASICUNITID",nullable=true,length=32)
	public String getBasicUnitId(){
		return this.basicUnitId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  基本单位
	 */
	public void setBasicUnitId(String basicUnitId){
		this.basicUnitId = basicUnitId;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  账面辅助数量
	 */
	@Column(name ="SECQTY",nullable=true,scale=10,length=20)
	public Double getSecQty(){
		return this.secQty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  账面辅助数量
	 */
	public void setSecQty(Double secQty){
		this.secQty = secQty;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  父id
	 */
	//@Column(name ="FID",nullable=true,length=32)
	@Transient
	public String getFid(){
		return this.fid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  父id
	 */
	public void setFid(String fid){
		this.fid = fid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  分录号
	 */
	@Column(name ="FINDEX",nullable=true,length=11)
	public Integer getFindex(){
		return this.findex;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  分录号
	 */
	public void setFindex(Integer findex){
		this.findex = findex;
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
	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	@Column(name ="note",nullable=true)
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Column(name ="idSrc",nullable=true)
	public String getIdSrc() {
		return idSrc;
	}

	public void setIdSrc(String idSrc) {
		this.idSrc = idSrc;
	}
}
