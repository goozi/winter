package com.qihang.buss.sc.baseinfo.entity;
import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.xml.soap.Text;
import java.sql.Blob;
import java.util.List;

import com.qihang.winter.poi.excel.annotation.ExcelCollection;
import com.qihang.winter.poi.excel.annotation.ExcelEntity;
import org.hibernate.annotations.GenericGenerator;
import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**   
 * @Title: Entity
 * @Description: 商品主表
 * @author onlineGenerator
 * @date 2016-06-29 11:50:02
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_ICItem", schema = "")
@SuppressWarnings("serial")
public class TScIcitemEntity implements java.io.Serializable {
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
    /**编号*/
    @Excel(name="编号")
    private java.lang.String number;
	/**名称 */
    @Excel(name="名称")
	private java.lang.String name;
	/**分类ID*/
	private java.lang.String parentID;
	/**简称*/
  @Excel(name="简称")
	private java.lang.String shortName;
    /**规格*/
    @Excel(name="规格")
    private java.lang.String model;
    /**重量*/
    @Excel(name="重量")
    private java.math.BigDecimal weight;
    /**保质期*/
    @Excel(name="保质期")
    private java.lang.Integer kfPeriod;
    /**保质期类型*/
    @Excel(name="保质期类型",dicCode="SC_DURA_DATE_TYPE")
    private java.lang.String kfDateType;
    /**默认仓库*/
    private java.lang.String stockID;
    private String stockName;
    /**仓库id与实体的关系*/
    @ExcelEntity(id="id",name="name")
    private TScStockForIcitemEntity stockForIcitemEntity;
    /**主供应商*/
    private java.lang.String supplierID;
    private String supplierName;
	@ExcelEntity(id="id",name="name")
    private TScSupplierForIcitemEntity supplieEntity;
    /**产家*/
    @Excel(name="产家")
    private java.lang.String factory;
    /**产地*/
    @Excel(name="产地")
    private java.lang.String producingPlace;
    /**指导价*/
    @Excel(name="指导价")
    private java.math.BigDecimal costPrice;
    /**进项税*/
    @Excel(name="进项税")
    private java.math.BigDecimal taxRateIn;
    /**销项税*/
    @Excel(name="销项税")
    private java.math.BigDecimal taxRateOut;
    /**存货类型*/
    @Excel(name="存货类型",dicCode = "SC_STOCK_TYPE")
    private java.lang.String itemType;
    /**计价方法*/
    @Excel(name="计价方法",dicCode = "SC_PRICE_METHOD")
    private java.lang.String track;
    /**按保质期管理*/
    @Excel(name="按保质期管理",dicCode = "sf_yn")
    private java.lang.String iskfPeriod;
    /**启用批次管理*/
    @Excel(name="启用批次管理",dicCode = "sf_yn")
    private java.lang.String batchManager;
    /**可组装/拆卸*/
    @Excel(name="可组装/拆卸",dicCode = "sf_yn")
    private java.lang.String isAssembly;
    /**助记码*/
    @Excel(name="助记码")
    private java.lang.String shortNumber;
	/**批准文号*/
    @Excel(name="批准文号")
	private java.lang.String cultureNo;
	/**品牌*/
    @Excel(name="品牌",dicCode = "SC_BRAND_TYPE")
	private java.lang.String brandID;
	/**级次*/
	private java.lang.Integer level;
	/**禁用标记*/
	private java.lang.Integer disabled;
	/**备注*/
    @Excel(name="备注")
	private java.lang.String note;

    private String imageSrc;
    /**版本号*/
    private java.lang.Integer version;
	/**引用次数*/
	private Integer count;

	private Double invQty;//库存数量
	/**商城同步过来的商品ID**/
	private java.lang.Integer escMallId;

	private String sonId;//分支机构

	/**商品和单价表的关系*/
	@ExcelCollection(name="单位价格表")
	private List<TScItemPriceEntity> icitemToPrices;

	private String stockSonId;//默认仓库分支机构id

	
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
	 *@return: java.lang.String  名称 
	 */
	
	@Column(name ="NAME",nullable=true,length=100)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称 
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  编号
	 */
	
	@Column(name ="NUMBER",nullable=true,length=80)
	public java.lang.String getNumber(){
		return this.number;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  编号
	 */
	public void setNumber(java.lang.String number){
		this.number = number;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分类ID
	 */
	
	@Column(name ="PARENTID",nullable=true,length=32)
	public java.lang.String getParentID(){
		return this.parentID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分类ID
	 */
	public void setParentID(java.lang.String parentID){
		this.parentID = parentID;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  简称
	 */
	
	@Column(name ="SHORTNAME",nullable=true,length=50)
	public java.lang.String getShortName(){
		return this.shortName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  简称
	 */
	public void setShortName(java.lang.String shortName){
		this.shortName = shortName;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  助记码
	 */
	
	@Column(name ="SHORTNUMBER",nullable=true,length=80)
	public java.lang.String getShortNumber(){
		return this.shortNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  助记码
	 */
	public void setShortNumber(java.lang.String shortNumber){
		this.shortNumber = shortNumber;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  规格
	 */
	
	@Column(name ="MODEL",nullable=true,length=255)
	public java.lang.String getModel(){
		return this.model;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  规格
	 */
	public void setModel(java.lang.String model){
		this.model = model;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  存货类型
	 */
	
	@Column(name ="ITEMTYPE",nullable=true,length=50)
	public java.lang.String getItemType(){
		return this.itemType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  存货类型
	 */
	public void setItemType(java.lang.String itemType){
		this.itemType = itemType;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  计价方法
	 */
	
	@Column(name ="TRACK",nullable=true,length=50)
	public java.lang.String getTrack(){
		return this.track;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  计价方法
	 */
	public void setTrack(java.lang.String track){
		this.track = track;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  指导价
	 */
	
	@Column(name ="COSTPRICE",nullable=true,length=32)
	public java.math.BigDecimal getCostPrice(){
		return this.costPrice;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  指导价
	 */
	public void setCostPrice(java.math.BigDecimal costPrice){
		this.costPrice = costPrice;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  默认仓库
	 */
	
//	@Column(name ="STOCKID",nullable=true,length=32)
    @Transient
	public java.lang.String getStockID(){
		return this.stockID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  默认仓库
	 */
	public void setStockID(java.lang.String stockID){
		this.stockID = stockID;
	}

    /**
     * 仓库id与实体的关系
     * @return
     */
    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "STOCKID",referencedColumnName = "ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public TScStockForIcitemEntity getStockForIcitemEntity() {
        return stockForIcitemEntity;
    }

    public void setStockForIcitemEntity(TScStockForIcitemEntity stockForIcitemEntity) {
        this.stockForIcitemEntity = stockForIcitemEntity;
    }

    /**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主供应商
	 */
	
//	@Column(name ="SUPPLIERID",nullable=true,length=32)
	@Transient
	public java.lang.String getSupplierID(){
		return this.supplierID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主供应商
	 */
	public void setSupplierID(java.lang.String supplierID){
		this.supplierID = supplierID;
	}

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "SUPPLIERID",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TScSupplierForIcitemEntity getSupplieEntity() {
		return supplieEntity;
	}

	public void setSupplieEntity(TScSupplierForIcitemEntity supplieEntity) {
		this.supplieEntity = supplieEntity;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  按保质期管理
	 */
	
	@Column(name ="ISKFPERIOD",nullable=true,length=50)
    public String getIskfPeriod() {
        return iskfPeriod;
    }

    public void setIskfPeriod(String iskfPeriod) {
        this.iskfPeriod = iskfPeriod;
    }

//	public java.lang.String getISKFPeriod(){
//		return this.iSKFPeriod;
//	}
//
//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  按保质期管理
//	 */
//	public void setISKFPeriod(java.lang.String iSKFPeriod){
//		this.iSKFPeriod = iSKFPeriod;
//	}


    /**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  保质期
	 */
	
	@Column(name ="KFPERIOD",nullable=true,length=32)
    public Integer getKfPeriod() {
        return kfPeriod;
    }

    public void setKfPeriod(Integer kfPeriod) {
        this.kfPeriod = kfPeriod;
    }

//	public java.lang.Integer getKFPeriod(){
//		return this.kFPeriod;
//	}
//
//	/**
//	 *方法: 设置java.lang.Integer
//	 *@param: java.lang.Integer  保质期
//	 */
//	public void setKFPeriod(java.lang.Integer kFPeriod){
//		this.kFPeriod = kFPeriod;
//	}



    /**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  保质期类型
	 */
	
	@Column(name ="KFDATETYPE",nullable=true,length=50)
    public String getKfDateType() {
        return kfDateType;
    }

    public void setKfDateType(String kfDateType) {
        this.kfDateType = kfDateType;
    }


//	public java.lang.String getKFDateType(){
//		return this.kFDateType;
//	}
//
//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  保质期类型
//	 */
//	public void setKFDateType(java.lang.String kFDateType){
//		this.kFDateType = kFDateType;
//	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  启用批次管理
	 */
	
	@Column(name ="BATCHMANAGER",nullable=true,length=50)
	public java.lang.String getBatchManager(){
		return this.batchManager;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  启用批次管理
	 */
	public void setBatchManager(java.lang.String batchManager){
		this.batchManager = batchManager;
	}



    /**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  可组装/拆卸
	 */
	
	@Column(name ="ISASSEMBLY",nullable=true,length=50)
    public String getIsAssembly() {
        return isAssembly;
    }

    public void setIsAssembly(String isAssembly) {
        this.isAssembly = isAssembly;
    }


//	public java.lang.String getISAssembly(){
//		return this.iSAssembly;
//	}
//
//	/**
//	 *方法: 设置java.lang.String
//	 *@param: java.lang.String  可组装/拆卸
//	 */
//	public void setISAssembly(java.lang.String iSAssembly){
//		this.iSAssembly = iSAssembly;
//	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  产家
	 */
	
	@Column(name ="FACTORY",nullable=true,length=255)
	public java.lang.String getFactory(){
		return this.factory;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  产家
	 */
	public void setFactory(java.lang.String factory){
		this.factory = factory;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  产地
	 */
	
	@Column(name ="PRODUCINGPLACE",nullable=true,length=255)
	public java.lang.String getProducingPlace(){
		return this.producingPlace;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  产地
	 */
	public void setProducingPlace(java.lang.String producingPlace){
		this.producingPlace = producingPlace;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  批准文号
	 */
	
	@Column(name ="CULTURENO",nullable=true,length=255)
	public java.lang.String getCultureNo(){
		return this.cultureNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  批准文号
	 */
	public void setCultureNo(java.lang.String cultureNo){
		this.cultureNo = cultureNo;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  品牌
	 */
	
	@Column(name ="BRANDID",nullable=true,length=32)
	public java.lang.String getBrandID(){
		return this.brandID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  品牌
	 */
	public void setBrandID(java.lang.String brandID){
		this.brandID = brandID;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  重量
	 */
	
	@Column(name ="WEIGHT",nullable=true,length=32)
	public java.math.BigDecimal getWeight(){
		return this.weight;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  重量
	 */
	public void setWeight(java.math.BigDecimal weight){
		this.weight = weight;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  进项税
	 */
	
	@Column(name ="TAXRATEIN",nullable=true,length=32)
	public java.math.BigDecimal getTaxRateIn(){
		return this.taxRateIn;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  进项税
	 */
	public void setTaxRateIn(java.math.BigDecimal taxRateIn){
		this.taxRateIn = taxRateIn;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  销项税
	 */
	
	@Column(name ="TAXRATEOUT",nullable=true,length=32)
	public java.math.BigDecimal getTaxRateOut(){
		return this.taxRateOut;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  销项税
	 */
	public void setTaxRateOut(java.math.BigDecimal taxRateOut){
		this.taxRateOut = taxRateOut;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  级次
	 */
	
	@Column(name ="LEVEL",nullable=true,length=32)
	public java.lang.Integer getLevel(){
		return this.level;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  级次
	 */
	public void setLevel(java.lang.Integer level){
		this.level = level;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  禁用标记
	 */
	
	@Column(name ="DISABLED",nullable=true,length=32)
	public java.lang.Integer getDisabled(){
		return this.disabled;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  禁用标记
	 */
	public void setDisabled(java.lang.Integer disabled){
		this.disabled = disabled;
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
    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    /**
     *
     * @第一个图片路径
     */
    @Transient
    public String getImageSrc() {
        return imageSrc;
    }

    public void setImageSrc(String imageSrc) {
        this.imageSrc = imageSrc;
    }

    @Version
    @Column(name ="VERSION",nullable=true,length=32)
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Transient
    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

	@OneToMany(mappedBy = "priceToIcItem",cascade = CascadeType.ALL)
	public List<TScItemPriceEntity> getIcitemToPrices() {
		return icitemToPrices;
	}

	public void setIcitemToPrices(List<TScItemPriceEntity> icitemToPrices) {
		this.icitemToPrices = icitemToPrices;
	}

	@Column(name ="COUNT",nullable=true,length=11)
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@Transient
	public Double getInvQty() {
		return invQty;
	}

	public void setInvQty(Double invQty) {
		this.invQty = invQty;
	}

	@Column(name ="escMallId",nullable=true,length=11)
	public Integer getEscMallId() {
		return escMallId;
	}

	public void setEscMallId(Integer escMallId) {
		this.escMallId = escMallId;
	}

	@Transient
	public String getStockSonId() {
		return stockSonId;
	}

	public void setStockSonId(String stockSonId) {
			this.stockSonId = stockSonId;
	}

	@Column(name ="sonId",nullable=true)
	public String getSonId() {
		return sonId;
	}

	public void setSonId(String sonId) {
		this.sonId = sonId;
	}

	//	public String getSonId() {
//		return sonId;
//	}
//
//	public void setSonId(String sonId) {
//		if(null != stockForIcitemEntity){
//			this.sonId = stockForIcitemEntity.getSonID();
//		} else {
//			this.sonId = sonId;
//		}
//	}
}
