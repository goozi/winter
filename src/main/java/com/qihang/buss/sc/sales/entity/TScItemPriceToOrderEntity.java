package com.qihang.buss.sc.sales.entity;

import com.qihang.buss.sc.baseinfo.entity.TScIcitemEntity;
import com.qihang.buss.sc.baseinfo.entity.TScMeasureunitToIcItemEntity;
import com.qihang.winter.poi.excel.annotation.Excel;
import com.qihang.winter.poi.excel.annotation.ExcelEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Entity
 * @Description: 单位价格表
 * @date 2016-06-29 11:50:02
 */
@Entity
@Table(name = "T_SC_Item_Price", schema = "")
@SuppressWarnings("serial")
public class TScItemPriceToOrderEntity implements java.io.Serializable {
    /**
     * 主键
     */
    private String id;
    /**
     * 创建人名称
     */
    private String createName;
    /**
     * 创建人登录名称
     */
    private String createBy;
    /**
     * 创建日期
     */
    private Date createDate;
    /**
     * 更新人名称
     */
    private String updateName;
    /**
     * 更新人登录名称
     */
    private String updateBy;
    /**
     * 更新日期
     */
    private Date updateDate;
    /**
     * 商品ID
     */
    private String itemID;
    /**
     * 条形码
     */
    @Excel(name = "条形码")
    private String barCode;
    /**
     * 单位
     */
    private String unitID;
    @ExcelEntity(id="id",name="name")
    private TScMeasureunitToIcItemEntity measureunitToIcItemEntity;
    /**
     * 单位换算率
     */
    private BigDecimal coefficient;
    /**
     * 单位类型
     */
    private String unitType;
    private String unitTypeName;
    /**
     * 默认采购单位
     */
    private Integer defaultCG;
    /**
     * 默认销售单位
     */
    private Integer defaultXS;
    /**
     * 默认仓存单位
     */
    private Integer defaultCK;
    /**
     * 默认生产单位
     */
    private Integer defaultSC;
    /**
     * 采购限价
     */
    private BigDecimal cgLimitPrice;
    /**
     * 最近采购价
     */
    private BigDecimal cgLatestPrice;
    /**
     * 采购单价
     */
    private BigDecimal cgPrice;
    /**
     * 一级采购价
     */
    private BigDecimal cgPrice1;
    /**
     * 二级采购价
     */
    private BigDecimal cgPrice2;
    /**
     * 三级采购价
     */
    private BigDecimal cgPrice3;
    /**
     * 销售限价
     */
    private BigDecimal xsLimitPrice;
    /**
     * 最近销售价
     */
    private BigDecimal xsLatestPrice;
    /**
     * 销售单价
     */
    private BigDecimal xsPrice;
    /**
     * 一级销售价
     */
    private BigDecimal xsPrice1;
    /**
     * 二级销售价
     */
    private BigDecimal xsPrice2;
    /**
     * 三级销售价
     */
    private BigDecimal xsPrice3;
    /**
     * 会员价
     */
    private BigDecimal lsMemberPrice;
    /**
     * 零售价
     */
    private BigDecimal lsRetailPrice;
    /**
     * 版本号
     */
    private Integer version;
    private TScIcitemEntity priceToIcItem;


    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  主键
     */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID", nullable = false, length = 36)
    public String getId() {
        return this.id;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  主键
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  创建人名称
     */
    @Column(name = "CREATE_NAME", nullable = true, length = 50)
    public String getCreateName() {
        return this.createName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  创建人名称
     */
    public void setCreateName(String createName) {
        this.createName = createName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  创建人登录名称
     */
    @Column(name = "CREATE_BY", nullable = true, length = 50)
    public String getCreateBy() {
        return this.createBy;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  创建人登录名称
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date  创建日期
     */
    @Column(name = "CREATE_DATE", nullable = true, length = 20)
    public Date getCreateDate() {
        return this.createDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date  创建日期
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  更新人名称
     */
    @Column(name = "UPDATE_NAME", nullable = true, length = 50)
    public String getUpdateName() {
        return this.updateName;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  更新人名称
     */
    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  更新人登录名称
     */
    @Column(name = "UPDATE_BY", nullable = true, length = 50)
    public String getUpdateBy() {
        return this.updateBy;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  更新人登录名称
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date  更新日期
     */
    @Column(name = "UPDATE_DATE", nullable = true, length = 20)
    public Date getUpdateDate() {
        return this.updateDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date  更新日期
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  商品ID
     */
//    @Column(name = "ITEMID", nullable = true, length = 32)
    @Transient
    public String getItemID() {
        return this.itemID;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  商品ID
     */
    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  单位
     */
//    @Column(name = "UNITID", nullable = true, length = 32)
    @Transient
    public String getUnitID() {
        return this.unitID;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  单位
     */
    public void setUnitID(String unitID) {
        this.unitID = unitID;
    }

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "UNITID",referencedColumnName = "ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public TScMeasureunitToIcItemEntity getMeasureunitToIcItemEntity() {
        return measureunitToIcItemEntity;
    }

    public void setMeasureunitToIcItemEntity(TScMeasureunitToIcItemEntity measureunitToIcItemEntity) {
        this.measureunitToIcItemEntity = measureunitToIcItemEntity;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal  单位换算率
     */
    @Column(name = "COEFFICIENT ", nullable = true, length = 32)
    public BigDecimal getCoefficient() {
        return this.coefficient;
    }

    /**
     * 方法: 设置java.math.BigDecimal
     *
     * @param: java.math.BigDecimal  单位换算率
     */
    public void setCoefficient(BigDecimal coefficient) {
        this.coefficient = coefficient;
    }


    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  单位类型
     */
    @Column(name = "UNITTYPE", nullable = true, length = 50)
    public String getUnitType() {
        return unitType;
    }

    public void setUnitType(String unitType) {
        this.unitType = unitType;
    }
    @Transient
    public String getUnitTypeName() {
        return unitTypeName;
    }

    public void setUnitTypeName(String unitTypeName) {
        this.unitTypeName = unitTypeName;
    }
    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  条形码
     */
    @Column(name = "BARCODE", nullable = true, length = 50)
    public String getBarCode() {
        return this.barCode;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  条形码
     */
    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer  默认采购单位
     */
    @Column(name = "DEFAULTCG ", nullable = true, length = 32)
    public Integer getDefaultCG() {
        return this.defaultCG;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer  默认采购单位
     */
    public void setDefaultCG(Integer defaultCG) {
        this.defaultCG = defaultCG;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer  默认销售单位
     */
    @Column(name = "DEFAULTXS", nullable = true, length = 32)
    public Integer getDefaultXS() {
        return this.defaultXS;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer  默认销售单位
     */
    public void setDefaultXS(Integer defaultXS) {
        this.defaultXS = defaultXS;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer  默认仓存单位
     */
    @Column(name = "DEFAULTCK", nullable = true, length = 32)
    public Integer getDefaultCK() {
        return this.defaultCK;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer  默认仓存单位
     */
    public void setDefaultCK(Integer defaultCK) {
        this.defaultCK = defaultCK;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer  默认生产单位
     */
    @Column(name = "DEFAULTSC", nullable = true, length = 32)
    public Integer getDefaultSC() {
        return this.defaultSC;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer  默认生产单位
     */
    public void setDefaultSC(Integer defaultSC) {
        this.defaultSC = defaultSC;
    }


    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal  采购限价
     */
    @Column(name = "CGLIMITPRICE", nullable = true, length = 32)
    public BigDecimal getCgLimitPrice() {
        return cgLimitPrice;
    }

    public void setCgLimitPrice(BigDecimal cgLimitPrice) {
        this.cgLimitPrice = cgLimitPrice;
    }


    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal  最近采购价
     */
    @Column(name = "CGLATESTPRICE ", nullable = true, length = 32)
    public BigDecimal getCgLatestPrice() {
        return cgLatestPrice;
    }

    public void setCgLatestPrice(BigDecimal cgLatestPrice) {
        this.cgLatestPrice = cgLatestPrice;
    }


    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal  采购单价
     */
    @Column(name = "CGPRICE", nullable = true, length = 32)
    public BigDecimal getCgPrice() {
        return cgPrice;
    }

    public void setCgPrice(BigDecimal cgPrice) {
        this.cgPrice = cgPrice;
    }


    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal  一级采购价
     */
    @Column(name = "CGPRICE1", nullable = true, length = 32)
    public BigDecimal getCgPrice1() {
        return cgPrice1;
    }

    public void setCgPrice1(BigDecimal cgPrice1) {
        this.cgPrice1 = cgPrice1;
    }


    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal  二级采购价
     */
    @Column(name = "CGPRICE2", nullable = true, length = 32)
    public BigDecimal getCgPrice2() {
        return cgPrice2;
    }

    public void setCgPrice2(BigDecimal cgPrice2) {
        this.cgPrice2 = cgPrice2;
    }


    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal  三级采购价
     */
    @Column(name = "CGPRICE3", nullable = true, length = 32)
    public BigDecimal getCgPrice3() {
        return cgPrice3;
    }

    public void setCgPrice3(BigDecimal cgPrice3) {
        this.cgPrice3 = cgPrice3;
    }


    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal  销售限价
     */
    @Column(name = "XSLIMITPRICE", nullable = true, length = 32)
    public BigDecimal getXsLimitPrice() {
        return xsLimitPrice;
    }

    public void setXsLimitPrice(BigDecimal xsLimitPrice) {
        this.xsLimitPrice = xsLimitPrice;
    }


    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal  最近销售价
     */
    @Column(name = "XSLATESTPRICE", nullable = true, length = 32)
    public BigDecimal getXsLatestPrice() {
        return xsLatestPrice;
    }

    public void setXsLatestPrice(BigDecimal xsLatestPrice) {
        this.xsLatestPrice = xsLatestPrice;
    }


    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal  销售单价
     */
    @Column(name = "XSPRICE", nullable = true, length = 32)
    public BigDecimal getXsPrice() {
        return xsPrice;
    }

    public void setXsPrice(BigDecimal xsPrice) {
        this.xsPrice = xsPrice;
    }


    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal  一级销售价
     */
    @Column(name = "XSPRICE1", nullable = true, length = 32)
    public BigDecimal getXsPrice1() {
        return xsPrice1;
    }

    public void setXsPrice1(BigDecimal xsPrice1) {
        this.xsPrice1 = xsPrice1;
    }


    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal  二级销售价
     */
    @Column(name = "XSPRICE2", nullable = true, length = 32)
    public BigDecimal getXsPrice2() {
        return xsPrice2;
    }

    public void setXsPrice2(BigDecimal xsPrice2) {
        this.xsPrice2 = xsPrice2;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal  三级销售价
     */
    @Column(name = "XSPRICE3", nullable = true, length = 32)
    public BigDecimal getXsPrice3() {
        return xsPrice3;
    }

    public void setXsPrice3(BigDecimal xsPrice3) {
        this.xsPrice3 = xsPrice3;
    }


    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal  会员价
     */
    @Column(name = "LSMEMBERPRICE", nullable = true, length = 32)
    public BigDecimal getLsMemberPrice() {
        return lsMemberPrice;
    }

    public void setLsMemberPrice(BigDecimal lsMemberPrice) {
        this.lsMemberPrice = lsMemberPrice;
    }

    /**
     * 方法: 取得java.math.BigDecimal
     *
     * @return: java.math.BigDecimal  零售价
     */
    @Column(name = "LSRETAILPRICE", nullable = true, length = 32)
    public BigDecimal getLsRetailPrice() {
        return lsRetailPrice;
    }

    public void setLsRetailPrice(BigDecimal lsRetailPrice) {
        this.lsRetailPrice = lsRetailPrice;
    }

    @Version
    @Column(name = "VERSION", nullable = true, length = 32)
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ITEMID",referencedColumnName = "ID")
    public TScIcitemEntity getPriceToIcItem() {
        return priceToIcItem;
    }

    public void setPriceToIcItem(TScIcitemEntity priceToIcItem) {
        this.priceToIcItem = priceToIcItem;
    }
}
