
package com.qihang.buss.sc.baseinfo.page;
import com.qihang.buss.sc.baseinfo.entity.TScIcitemEntity;
import com.qihang.buss.sc.baseinfo.entity.TScItemPriceEntity;
import com.qihang.buss.sc.baseinfo.entity.TScItemPhotoEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;


/**   
 * @Title: Entity
 * @Description: 商品主表
 * @author onlineGenerator
 * @date 2016-06-29 11:50:02
 * @version V1.0   
 *
 */
public class TScIcitemPage implements java.io.Serializable {
	/**保存-单位价格表*/
	private List<TScItemPriceEntity> tScItemPriceList = new ArrayList<TScItemPriceEntity>();
    public List<TScItemPriceEntity> gettScItemPriceList() {
        return tScItemPriceList;
    }
    public void settScItemPriceList(List<TScItemPriceEntity> tScItemPriceList) {
        this.tScItemPriceList = tScItemPriceList;
    }

    /**保存-商品图片表*/
	private List<TScItemPhotoEntity> tScItemPhotoList = new ArrayList<TScItemPhotoEntity>();
    public List<TScItemPhotoEntity> gettScItemPhotoList() {
        return tScItemPhotoList;
    }

    public void settScItemPhotoList(List<TScItemPhotoEntity> tScItemPhotoList) {
        this.tScItemPhotoList = tScItemPhotoList;
    }

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
	/**名称 */
	private java.lang.String name;
	/**编号*/
	private java.lang.String number;
	/**分类ID*/
	private java.lang.String parentID;
	/**简称*/
	private java.lang.String shortName;
	/**助记码*/
	private java.lang.String shortNumber;
	/**规格*/
	private java.lang.String model;
	/**存货类型*/
	private java.lang.String itemType;
	/**计价方法*/
	private java.lang.String track;
	/**指导价*/
	private java.math.BigDecimal costPrice;
	/**默认仓库*/
	private java.lang.String stockID;
	/**主供应商*/
	private java.lang.String supplierID;
	/**按保质期管理*/
	private java.lang.String iSKFPeriod;
	/**保质期*/
	private java.lang.Integer kFPeriod;
	/**保质期类型*/
	private java.lang.String kFDateType;
	/**启用批次管理*/
	private java.lang.String batchManager;
	/**可组装/拆卸*/
	private java.lang.String iSAssembly;
	/**产家*/
	private java.lang.String factory;
	/**产地*/
	private java.lang.String producingPlace;
	/**批准文号*/
	private java.lang.String cultureNo;
	/**品牌*/
	private java.lang.String brandID;
	/**重量*/
	private java.math.BigDecimal weight;
	/**进项税*/
	private java.math.BigDecimal taxRateIn;
	/**销项税*/
	private java.math.BigDecimal taxRateOut;
	/**级次*/
	private java.lang.Integer level;
	/**禁用标记*/
	private java.lang.Integer disabled;
	/**备注*/
	private java.lang.String note;

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主供应商
	 */
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  按保质期管理
	 */
    public String getiSKFPeriod() {
        return iSKFPeriod;
    }

    public void setiSKFPeriod(String iSKFPeriod) {
        this.iSKFPeriod = iSKFPeriod;
    }

    /**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  保质期
	 */
    public Integer getkFPeriod() {
        return kFPeriod;
    }

    public void setkFPeriod(Integer kFPeriod) {
        this.kFPeriod = kFPeriod;
    }

    /**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  保质期类型
	 */
    public String getkFDateType() {
        return kFDateType;
    }

    public void setkFDateType(String kFDateType) {
        this.kFDateType = kFDateType;
    }

    /**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  启用批次管理
	 */
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
    public String getiSAssembly() {
        return iSAssembly;
    }

    public void setiSAssembly(String iSAssembly) {
        this.iSAssembly = iSAssembly;
    }

    /**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  产家
	 */
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
}
