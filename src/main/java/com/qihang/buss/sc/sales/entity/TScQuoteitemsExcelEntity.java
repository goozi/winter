package com.qihang.buss.sc.sales.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import com.qihang.winter.poi.excel.annotation.ExcelEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 报价单商品
 * @author onlineGenerator
 * @date 2016-06-16 15:22:17
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_QuoteItems", schema = "")
@SuppressWarnings("serial")
public class TScQuoteitemsExcelEntity implements java.io.Serializable {
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
	/**主表主键*/
	private String fid;
	/**分录号*/
    @Excel(name="分录号")
	private Integer indexNumber;
	/**商品*/
//	@Excel(name="商品")
	private String itemID;
	@ExcelEntity(id="id",name="name")
	private TScIcitemToOrderEntity icitemToOrderEntity;
	/**商品编号*/
	private String itemNumber;
	/**商品名称*/
	private String itemName;
	/**商品规格*/
	private String itemModel;
	/**商品条形码*/
	private String itemBarcode;
	/**单位*/
//	@Excel(name="单位")
	private String unitID;
	@ExcelEntity(id="id",name="name")
	private TScItemPriceToOrderEntity itemPriceToOrderEntity;
	/**数量段(从)*/
	@Excel(name="数量段(从)")
	private BigDecimal begQty;
	/**数量段(至)*/
	@Excel(name="数量段(至)")
	private BigDecimal endQty;
	/**单价*/
	@Excel(name="单价")
	private BigDecimal price;
	/**成本单价*/
	@Excel(name="成本单价")
	private BigDecimal costPrice;
	/**毛利*/
	@Excel(name="毛利")
	private BigDecimal grossAmount;
	/**毛利率*/
	@Excel(name="毛利率")
	private BigDecimal grossper;
	/**备注*/
	@Excel(name="备注")
	private String note;
	/**版本号*/
	private Integer version;

	private TScQuoteExcelEntity quoteExcelEntity;

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
	 *@return: java.lang.String  主表主键
	 */
//	@Column(name ="FID",nullable=true,length=32)
	@Transient
	public String getFid(){
		return this.fid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主表主键
	 */
	public void setFid(String fid){
		this.fid = fid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  分录号
	 */
	@Column(name ="INDEXNUMBER",nullable=true,length=32)
	public Integer getIndexNumber(){
		return this.indexNumber;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  分录号
	 */
	public void setIndexNumber(Integer indexNumber){
		this.indexNumber = indexNumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品
	 */
//	@Column(name ="ITEMID",nullable=true,length=32)
	@Transient
	public String getItemID(){
		return this.itemID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品
	 */
	public void setItemID(String itemID){
		this.itemID = itemID;
	}

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "ITEMID",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TScIcitemToOrderEntity getIcitemToOrderEntity() {
		return icitemToOrderEntity;
	}

	public void setIcitemToOrderEntity(TScIcitemToOrderEntity icitemToOrderEntity) {
		this.icitemToOrderEntity = icitemToOrderEntity;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */
//	@Column(name ="UNITID",nullable=true,length=32)
	@Transient
	public String getUnitID(){
		return this.unitID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setUnitID(String unitID){
		this.unitID = unitID;
	}

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "UNITID",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TScItemPriceToOrderEntity getItemPriceToOrderEntity() {
		return itemPriceToOrderEntity;
	}

	public void setItemPriceToOrderEntity(TScItemPriceToOrderEntity itemPriceToOrderEntity) {
		this.itemPriceToOrderEntity = itemPriceToOrderEntity;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  数量段(从)
	 */
	@Column(name ="BEGQTY",nullable=true,length=32)
	public BigDecimal getBegQty(){
		return this.begQty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  数量段(从)
	 */
	public void setBegQty(BigDecimal begQty){
		this.begQty = begQty;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  数量段(至)
	 */
	@Column(name ="ENDQTY",nullable=true,length=32)
	public BigDecimal getEndQty(){
		return this.endQty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  数量段(至)
	 */
	public void setEndQty(BigDecimal endQty){
		this.endQty = endQty;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  单价
	 */
	@Column(name ="PRICE",nullable=true,length=32)
	public BigDecimal getPrice(){
		return this.price;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  单价
	 */
	public void setPrice(BigDecimal price){
		this.price = price;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  成本单价
	 */
	@Column(name ="COSTPRICE",nullable=true,length=32)
	public BigDecimal getCostPrice(){
		return this.costPrice;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  成本单价
	 */
	public void setCostPrice(BigDecimal costPrice){
		this.costPrice = costPrice;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  毛利
	 */
	@Column(name ="GROSSAMOUNT",nullable=true,length=32)
	public BigDecimal getGrossAmount(){
		return this.grossAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  毛利
	 */
	public void setGrossAmount(BigDecimal grossAmount){
		this.grossAmount = grossAmount;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  毛利率
	 */
	@Column(name ="GROSSPER",nullable=true,length=32)
	public BigDecimal getGrossper(){
		return this.grossper;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  毛利率
	 */
	public void setGrossper(BigDecimal grossper){
		this.grossper = grossper;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="NOTE",nullable=true,length=255)
	public String getNote(){
		return this.note;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setNote(String note){
		this.note = note;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  版本号
	 */
	@Column(name ="VERSION",nullable=true,length=32)
	public Integer getVersion(){
		return this.version;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  版本号
	 */
	public void setVersion(Integer version){
		this.version = version;
	}

	@Transient
	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}
	@Transient
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	@Transient
	public String getItemModel() {
		return itemModel;
	}

	public void setItemModel(String itemModel) {
		this.itemModel = itemModel;
	}
	@Transient
	public String getItemBarcode() {
		return itemBarcode;
	}

	public void setItemBarcode(String itemBarcode) {
		this.itemBarcode = itemBarcode;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FID",referencedColumnName = "ID")
	public TScQuoteExcelEntity getQuoteExcelEntity() {
		return quoteExcelEntity;
	}

	public void setQuoteExcelEntity(TScQuoteExcelEntity quoteExcelEntity) {
		this.quoteExcelEntity = quoteExcelEntity;
	}
}
