package com.qihang.buss.sc.baseinfo.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import com.qihang.winter.poi.excel.annotation.ExcelCollection;
import com.qihang.winter.poi.excel.annotation.ExcelEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
public class TScIcitemForPoOrderEntity implements java.io.Serializable {
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
    /**编号*/
    @Excel(name="商品编号")
    private String number;
	/**名称 */
    @Excel(name="商品名称")
	private String name;
	/**分类ID*/
	private String parentID;
	/**简称*/
	private String shortName;
    /**规格*/
    @Excel(name="规格")
    private String model;
    /**重量*/
    private BigDecimal weight;
    /**保质期*/
    private Integer kfPeriod;
    /**保质期类型*/
    private String kfDateType;
    /**默认仓库*/
    private String stockID;
    private String stockName;
    /**仓库id与实体的关系*/
    /**主供应商*/
    private String supplierID;
    private String supplierName;
    /**产家*/
    private String factory;
    /**产地*/
    private String producingPlace;
    /**指导价*/
    private BigDecimal costPrice;
    /**进项税*/
    private BigDecimal taxRateIn;
    /**销项税*/
    private BigDecimal taxRateOut;
    /**存货类型*/
    private String itemType;
    /**计价方法*/
    private String track;
    /**按保质期管理*/
    private String iskfPeriod;
    /**启用批次管理*/
    private String batchManager;
    /**可组装/拆卸*/
    private String isAssembly;
    /**助记码*/
    private String shortNumber;
	/**批准文号*/
	private String cultureNo;
	/**品牌*/
	private String brandID;
	/**级次*/
	private Integer level;
	/**禁用标记*/
	private Integer disabled;
	/**备注*/
	private String note;

    private String imageSrc;
    /**版本号*/
    private Integer version;
	/**引用次数*/
	private Integer count;



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
	 *@return: java.lang.String  名称
	 */

	@Column(name ="NAME",nullable=true,length=100)
	public String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setName(String name){
		this.name = name;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  编号
	 */

	@Column(name ="NUMBER",nullable=true,length=80)
	public String getNumber(){
		return this.number;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  编号
	 */
	public void setNumber(String number){
		this.number = number;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分类ID
	 */

	@Column(name ="PARENTID",nullable=true,length=32)
	public String getParentID(){
		return this.parentID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分类ID
	 */
	public void setParentID(String parentID){
		this.parentID = parentID;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  简称
	 */

	@Column(name ="SHORTNAME",nullable=true,length=50)
	public String getShortName(){
		return this.shortName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  简称
	 */
	public void setShortName(String shortName){
		this.shortName = shortName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  助记码
	 */

	@Column(name ="SHORTNUMBER",nullable=true,length=80)
	public String getShortNumber(){
		return this.shortNumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  助记码
	 */
	public void setShortNumber(String shortNumber){
		this.shortNumber = shortNumber;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  规格
	 */

	@Column(name ="MODEL",nullable=true,length=255)
	public String getModel(){
		return this.model;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  规格
	 */
	public void setModel(String model){
		this.model = model;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  存货类型
	 */

	@Column(name ="ITEMTYPE",nullable=true,length=50)
	public String getItemType(){
		return this.itemType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  存货类型
	 */
	public void setItemType(String itemType){
		this.itemType = itemType;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  计价方法
	 */

	@Column(name ="TRACK",nullable=true,length=50)
	public String getTrack(){
		return this.track;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  计价方法
	 */
	public void setTrack(String track){
		this.track = track;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  指导价
	 */

	@Column(name ="COSTPRICE",nullable=true,length=32)
	public BigDecimal getCostPrice(){
		return this.costPrice;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  指导价
	 */
	public void setCostPrice(BigDecimal costPrice){
		this.costPrice = costPrice;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  默认仓库
	 */

//	@Column(name ="STOCKID",nullable=true,length=32)
    @Transient
	public String getStockID(){
		return this.stockID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  默认仓库
	 */
	public void setStockID(String stockID){
		this.stockID = stockID;
	}


    /**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主供应商
	 */

//	@Column(name ="SUPPLIERID",nullable=true,length=32)
	@Transient
	public String getSupplierID(){
		return this.supplierID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主供应商
	 */
	public void setSupplierID(String supplierID){
		this.supplierID = supplierID;
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
	public String getBatchManager(){
		return this.batchManager;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  启用批次管理
	 */
	public void setBatchManager(String batchManager){
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
	public String getFactory(){
		return this.factory;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  产家
	 */
	public void setFactory(String factory){
		this.factory = factory;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  产地
	 */

	@Column(name ="PRODUCINGPLACE",nullable=true,length=255)
	public String getProducingPlace(){
		return this.producingPlace;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  产地
	 */
	public void setProducingPlace(String producingPlace){
		this.producingPlace = producingPlace;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  批准文号
	 */

	@Column(name ="CULTURENO",nullable=true,length=255)
	public String getCultureNo(){
		return this.cultureNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  批准文号
	 */
	public void setCultureNo(String cultureNo){
		this.cultureNo = cultureNo;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  品牌
	 */

	@Column(name ="BRANDID",nullable=true,length=32)
	public String getBrandID(){
		return this.brandID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  品牌
	 */
	public void setBrandID(String brandID){
		this.brandID = brandID;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  重量
	 */

	@Column(name ="WEIGHT",nullable=true,length=32)
	public BigDecimal getWeight(){
		return this.weight;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  重量
	 */
	public void setWeight(BigDecimal weight){
		this.weight = weight;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  进项税
	 */

	@Column(name ="TAXRATEIN",nullable=true,length=32)
	public BigDecimal getTaxRateIn(){
		return this.taxRateIn;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  进项税
	 */
	public void setTaxRateIn(BigDecimal taxRateIn){
		this.taxRateIn = taxRateIn;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  销项税
	 */

	@Column(name ="TAXRATEOUT",nullable=true,length=32)
	public BigDecimal getTaxRateOut(){
		return this.taxRateOut;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  销项税
	 */
	public void setTaxRateOut(BigDecimal taxRateOut){
		this.taxRateOut = taxRateOut;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  级次
	 */

	@Column(name ="LEVEL",nullable=true,length=32)
	public Integer getLevel(){
		return this.level;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  级次
	 */
	public void setLevel(Integer level){
		this.level = level;
	}

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  禁用标记
	 */

	@Column(name ="DISABLED",nullable=true,length=32)
	public Integer getDisabled(){
		return this.disabled;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  禁用标记
	 */
	public void setDisabled(Integer disabled){
		this.disabled = disabled;
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

	@Column(name ="COUNT",nullable=true,length=11)
	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
}
