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

import com.qihang.winter.core.util.ResourceUtil;
import org.hibernate.annotations.GenericGenerator;
import com.qihang.winter.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 存货初始化单
 * @author onlineGenerator
 * @date 2016-08-18 18:05:14
 * @version V1.0   
 *
 */
@Entity
@Table(name = "v_sc_ic_initial", schema = "")
@SuppressWarnings("serial")
public class VScIcInitialEntity implements java.io.Serializable {
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
	/**单据类型*/
	private java.lang.String trantype;
	/**单据编号*/
	@Excel(name="单据编号")
	private java.lang.String billno;
	/**单据日期*/
	@Excel(name="单据日期")
	private java.util.Date date;
	/**仓库ID*/
	private java.lang.String stockid;
	/**仓库*/
	@Excel(name="仓库")
	private java.lang.String stockname;
	/**分录号*/
	@Excel(name="分录号")
	private java.lang.String indexnumber;
	/**商品编号*/
	@Excel(name="商品编号")
	private java.lang.String entryitemno;
	/**商品名称*/
	@Excel(name="商品名称")
	private java.lang.String entryitemname;
	/**规格*/
	@Excel(name="规格")
	private java.lang.String model;
	/**条形码*/
	@Excel(name="条形码")
	private java.lang.String barcode;
	/**批号*/
	@Excel(name="批号")
	private java.lang.String batchno;
	/**单位ID*/
	private java.lang.String unitid;
	/**单位*/
	@Excel(name="单位")
	private java.lang.String unitname;
	/**基本数量*/
	@Excel(name="基本数量")
	private java.math.BigDecimal basicQty;
	/**基本单位ID*/
	private java.lang.String basicunitid;
	/**基本单位*/
	@Excel(name="基本单位")
	private java.lang.String basicunitname;
	/**换算率(%)*/
	@Excel(name="换算率(%)")
	private java.math.BigDecimal coefficient;
	/**基本数量*/
	@Excel(name="基本数量")
	private java.math.BigDecimal basicqty;
	/**辅助单位ID*/
	private java.lang.String secunitid;
	/**辅助单位*/
	@Excel(name="辅助单位")
	private java.lang.String secunitname;
	/**辅助换算率(%)*/
	@Excel(name="辅助换算率(%)")
	private java.math.BigDecimal seccoefficient;
	/**辅助数量*/
	@Excel(name="辅助数量")
	private java.math.BigDecimal secqty;

	private String secqtyStr;
	/**成本单价*/
	@Excel(name="成本单价")
	private java.math.BigDecimal costprice;
	/**成本金额*/
	@Excel(name="成本金额")
	private java.math.BigDecimal costamount;
	/**生产日期*/
	@Excel(name="生产日期")
	private java.util.Date kfdate;
	/**保质期*/
	@Excel(name="保质期")
	private java.lang.Integer kfperiod;
	/**保质期类型*/
	@Excel(name="保质期类型")
	private java.lang.String kfdatetypeinfo;
	/**保质期+保质期类型名称*/
	@Excel(name="保质期类型")
	private java.lang.String kfdatetype;
	/**有效期至*/
	@Excel(name="有效期至")
	private java.util.Date perioddate;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String entryexplanation;
	/**摘要*/
	@Excel(name="摘要")
	private java.lang.String explanation;
	/**制单人*/
	@Excel(name="制单人")
	private java.lang.String billerid;
	/**审核人*/
	@Excel(name="审核人")
	private java.lang.String checkerid;
	/**审核日期*/
	@Excel(name="审核日期")
	private java.util.Date checkdate;
	/**审核状态*/
	@Excel(name="审核状态")
	private java.lang.Integer checkstate;
	/**作废标记*/
	@Excel(name="作废标记")
	private java.lang.Integer cancellation;
	/**分支机构*/
	@Excel(name="分支机构ID")
	private java.lang.String sonid;
	/**分支机构*/
	@Excel(name="分支机构")
	private java.lang.String sonname;
	/**版本*/
	private java.lang.Integer version;
	/**附表主键*/
	private java.lang.String entryid;
	/**商品ID*/
	private java.lang.String entryitemid;
	/**附表仓库ID*/
	private java.lang.String entrystockid;
	/**备注*/
	private java.lang.String note;
	/**附表仓库名称*/
	private java.lang.String entrystockname;
	/**箱数*/
	@Excel(name="箱数")
	private java.math.BigDecimal qty;
	/**散数*/
	@Excel(name="散数")
	private java.math.BigDecimal smallQty;
	//相关bigDecimal属性
	private java.lang.String qtystr;
	private java.lang.String basicqtystr;
	private java.lang.String costpricestr;
	private java.lang.String costamountstr;
	private java.lang.String coefficientstr;
	private java.lang.String seccoefficientstr;
	private java.lang.String smallqtystr;
	private java.lang.String secqtystr;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
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
	@Column(name ="CREATE_DATE",nullable=true)
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
	@Column(name ="UPDATE_DATE",nullable=true)
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
	 *@return: java.lang.String  单据类型
	 */
	@Column(name ="TRANTYPE",nullable=true,length=11)
	public java.lang.String getTrantype(){
		return this.trantype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据类型
	 */
	public void setTrantype(java.lang.String trantype){
		this.trantype = trantype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单据编号
	 */
	@Column(name ="BILLNO",nullable=true,length=50)
	public java.lang.String getBillno(){
		return this.billno;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据编号
	 */
	public void setBillno(java.lang.String billno){
		this.billno = billno;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  单据日期
	 */
	@Column(name ="DATE",nullable=true)
	public java.util.Date getDate(){
		return this.date;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  单据日期
	 */
	public void setdate(java.util.Date date){
		this.date = date;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库ID
	 */
	@Column(name ="STOCKID",nullable=true,length=32)
	public java.lang.String getStockid(){
		return this.stockid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库ID
	 */
	public void setStockid(java.lang.String stockid){
		this.stockid = stockid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库
	 */
	@Column(name ="STOCKNAME",nullable=true,length=80)
	public java.lang.String getStockname(){
		return this.stockname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库
	 */
	public void setStockname(java.lang.String stockname){
		this.stockname = stockname;
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
	 *@return: java.lang.String  商品编号
	 */
	@Column(name ="ENTRYITEMNO",nullable=true,length=80)
	public java.lang.String getEntryitemno(){
		return this.entryitemno;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品编号
	 */
	public void setEntryitemno(java.lang.String entryitemno){
		this.entryitemno = entryitemno;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品名称
	 */
	@Column(name ="ENTRYITEMNAME",nullable=true,length=100)
	public java.lang.String getEntryitemname(){
		return this.entryitemname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品名称
	 */
	public void setEntryitemname(java.lang.String entryitemname){
		this.entryitemname = entryitemname;
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
	 *@return: java.lang.String  条形码
	 */
	@Column(name ="BARCODE",nullable=true,length=50)
	public java.lang.String getBarcode(){
		return this.barcode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  条形码
	 */
	public void setBarcode(java.lang.String barcode){
		this.barcode = barcode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  批号
	 */
	@Column(name ="BATCHNO",nullable=true,length=100)
	public java.lang.String getBatchno(){
		return this.batchno;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  批号
	 */
	public void setBatchno(java.lang.String batchno){
		this.batchno = batchno;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位ID
	 */
	@Column(name ="UNITID",nullable=true,length=32)
	public java.lang.String getUnitid(){
		return this.unitid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位ID
	 */
	public void setUnitid(java.lang.String unitid){
		this.unitid = unitid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单位
	 */
	@Column(name ="UNITNAME",nullable=true,length=80)
	public java.lang.String getUnitname(){
		return this.unitname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setUnitname(java.lang.String unitname){
		this.unitname = unitname;
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
	 *@param: java.math.BigDecimal  数量
	 */
	public void setQty(java.math.BigDecimal qty){
		this.qty = qty;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  基本单位ID
	 */
	@Column(name ="BASICUNITID",nullable=true,length=32)
	public java.lang.String getBasicunitid(){
		return this.basicunitid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  基本单位ID
	 */
	public void setBasicunitid(java.lang.String basicunitid){
		this.basicunitid = basicunitid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  基本单位
	 */
	@Column(name ="BASICUNITNAME",nullable=true,length=80)
	public java.lang.String getBasicunitname(){
		return this.basicunitname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  基本单位
	 */
	public void setBasicunitname(java.lang.String basicunitname){
		this.basicunitname = basicunitname;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  换算率(%)
	 */
	@Column(name ="COEFFICIENT",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getCoefficient(){
		return this.coefficient;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  换算率(%)
	 */
	public void setCoefficient(java.math.BigDecimal coefficient){
		this.coefficient = coefficient;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  基本数量
	 */
	@Column(name ="basicQty",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getBasicqty(){
		return this.basicqty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  基本数量
	 */
	public void setBasicqty(java.math.BigDecimal basicqty){
		this.basicqty = basicqty;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  辅助单位ID
	 */
	@Column(name ="SECUNITID",nullable=true,length=32)
	public java.lang.String getSecunitid(){
		return this.secunitid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  辅助单位ID
	 */
	public void setSecunitid(java.lang.String secunitid){
		this.secunitid = secunitid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  辅助单位
	 */
	@Column(name ="SECUNITNAME",nullable=true,length=80)
	public java.lang.String getSecunitname(){
		return this.secunitname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  辅助单位
	 */
	public void setSecunitname(java.lang.String secunitname){
		this.secunitname = secunitname;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  辅助换算率(%)
	 */
	@Column(name ="SECCOEFFICIENT",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getSeccoefficient(){
		return this.seccoefficient;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  辅助换算率(%)
	 */
	public void setSeccoefficient(java.math.BigDecimal seccoefficient){
		this.seccoefficient = seccoefficient;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  辅助数量
	 */
	@Column(name ="SECQTY",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getSecqty(){
		return this.secqty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  辅助数量
	 */
	public void setSecqty(java.math.BigDecimal secqty){
		this.secqty = secqty;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  成本单价
	 */
	@Column(name ="COSTPRICE",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getCostprice(){
		return this.costprice;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  成本单价
	 */
	public void setCostprice(java.math.BigDecimal costprice){
		this.costprice = costprice;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  成本金额
	 */
	@Column(name ="COSTAMOUNT",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getCostamount(){
		return this.costamount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  成本金额
	 */
	public void setCostamount(java.math.BigDecimal costamount){
		this.costamount = costamount;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  生产日期
	 */
	@Column(name ="KFDATE",nullable=true)
	public java.util.Date getKfdate(){
		return this.kfdate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  生产日期
	 */
	public void setKfdate(java.util.Date kfdate){
		this.kfdate = kfdate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  保质期
	 */
	@Column(name ="KFPERIOD",nullable=true,length=10)
	public java.lang.Integer getKfperiod(){
		return this.kfperiod;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  保质期
	 */
	public void setKfperiod(java.lang.Integer kfperiod){
		this.kfperiod = kfperiod;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  保质期+保质期类型名称
	 */
	@Column(name ="KFDATETYPE",nullable=true,length=32)
	public java.lang.String getKfdatetype(){
		return this.kfdatetype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  保质期+保质期类型名称
	 */
	public void setKfdatetype(java.lang.String kfdatetype){
		this.kfdatetype = kfdatetype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  保质期类型
	 */
	@Column(name ="KFDATETYPEINFO",nullable=true,length=32)
	public java.lang.String getKfdatetypeinfo(){
		return this.kfdatetypeinfo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  保质期类型
	 */
	public void setKfdatetypeinfo(java.lang.String kfdatetypeinfo){
		this.kfdatetypeinfo = kfdatetypeinfo;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  有效期至
	 */
	@Column(name ="PERIODDATE",nullable=true)
	public java.util.Date getPerioddate(){
		return this.perioddate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  有效期至
	 */
	public void setPerioddate(java.util.Date perioddate){
		this.perioddate = perioddate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="ENTRYEXPLANATION",nullable=true,length=255)
	public java.lang.String getEntryexplanation(){
		return this.entryexplanation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setEntryexplanation(java.lang.String entryexplanation){
		this.entryexplanation = entryexplanation;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  摘要
	 */
	@Column(name ="EXPLANATION",nullable=true,length=255)
	public java.lang.String getExplanation(){
		return this.explanation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  摘要
	 */
	public void setExplanation(java.lang.String explanation){
		this.explanation = explanation;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  制单人
	 */
	@Column(name ="BILLERID",nullable=true,length=50)
	public java.lang.String getBillerid(){
		return this.billerid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  制单人
	 */
	public void setBillerid(java.lang.String billerid){
		this.billerid = billerid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核人
	 */
	@Column(name ="CHECKERID",nullable=true,length=32)
	public java.lang.String getCheckerid(){
		return this.checkerid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核人
	 */
	public void setCheckerid(java.lang.String checkerid){
		this.checkerid = checkerid;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  审核日期
	 */
	@Column(name ="CHECKDATE",nullable=true)
	public java.util.Date getCheckdate(){
		return this.checkdate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  审核日期
	 */
	public void setCheckdate(java.util.Date checkdate){
		this.checkdate = checkdate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  审核状态
	 */
	@Column(name ="CHECKSTATE",nullable=true,length=10)
	public java.lang.Integer getCheckstate(){
		return this.checkstate;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  审核状态
	 */
	public void setCheckstate(java.lang.Integer checkstate){
		this.checkstate = checkstate;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  作废标记
	 */
	@Column(name ="CANCELLATION",nullable=true,length=10)
	public java.lang.Integer getCancellation(){
		return this.cancellation;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  作废标记
	 */
	public void setCancellation(java.lang.Integer cancellation){
		this.cancellation = cancellation;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分支机构ID
	 */
	@Column(name ="SONID",nullable=true,length=100)
	public java.lang.String getSonid(){
		return this.sonid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分支机构ID
	 */
	public void setSonid(java.lang.String sonid){
		this.sonid = sonid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分支机构
	 */
	@Column(name ="SONNAME",nullable=true,length=100)
	public java.lang.String getSonname(){
		return this.sonname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分支机构
	 */
	public void setSonname(java.lang.String sonname){
		this.sonname = sonname;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  版本
	 */
	@Column(name ="VERSION",nullable=true,length=10)
	public java.lang.Integer getVersion(){
		return this.version;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  版本
	 */
	public void setVersion(java.lang.Integer version){
		this.version = version;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  附表主键
	 */
	@Id
	@Column(name ="ENTRYID",nullable=false,length=36)
	public java.lang.String getEntryid(){
		return this.entryid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  附表主键
	 */
	public void setEntryid(java.lang.String entryid){
		this.entryid = entryid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品ID
	 */
	@Column(name ="ENTRYITEMID",nullable=true,length=32)
	public java.lang.String getEntryitemid(){
		return this.entryitemid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品ID
	 */
	public void setEntryitemid(java.lang.String entryitemid){
		this.entryitemid = entryitemid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  附表仓库ID
	 */
	@Column(name ="ENTRYSTOCKID",nullable=true,length=32)
	public java.lang.String getEntrystockid(){
		return this.entrystockid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  附表仓库ID
	 */
	public void setEntrystockid(java.lang.String entrystockid){
		this.entrystockid = entrystockid;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  附表仓库名称
	 */
	@Column(name ="ENTRYSTOCKNAME",nullable=true,length=80)
	public java.lang.String getEntrystockname(){
		return this.entrystockname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  附表仓库名称
	 */
	public void setEntrystockname(java.lang.String entrystockname){
		this.entrystockname = entrystockname;
	}

	@Transient
	public String getSecqtyStr() {
		String returnValue =this.secqty==null?"":(this.secqty.stripTrailingZeros()==null?"":this.secqty.stripTrailingZeros().toPlainString());
		return returnValue;
	}

	public void setSecqtyStr(String secqtyStr) {
		this.secqtyStr = secqtyStr;
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
	public String getCostamountstr() {
		String returnValue =this.costamount==null?"":(this.costamount.stripTrailingZeros()==null?"":this.costamount.stripTrailingZeros().toPlainString());
		return returnValue;
	}

	public void setCostamountstr(String costAmountstr) {
		this.costamountstr = costAmountstr;
	}

	@Transient
	public String getCostpricestr() {
		String returnValue =this.costprice==null?"":(this.costprice.stripTrailingZeros()==null?"":this.costprice.stripTrailingZeros().toPlainString());
		return returnValue;
	}

	public void setCostpricestr(String costPricestr) {
		this.costpricestr = costPricestr;
	}

	@Transient
	public String getBasicqtystr() {
		String returnValue =this.basicqty==null?"":(this.basicqty.stripTrailingZeros()==null?"":this.basicqty.stripTrailingZeros().toPlainString());
		return returnValue;
	}

	public void setBasicqtystr(String basicQtystr) {
		this.basicqtystr = basicQtystr;
	}

	@Transient
	public String getSmallqtystr() {
		String returnValue =this.smallQty==null?"":(this.smallQty.stripTrailingZeros()==null?"":this.smallQty.stripTrailingZeros().toPlainString());
		return returnValue;
	}

	public void setSmallqtystr(String smallQtystr) {
		this.smallqtystr = smallQtystr;
	}

	@Transient
	public String getSeccoefficientstr() {
		String returnValue =this.seccoefficient==null?"":(this.seccoefficient.stripTrailingZeros()==null?"":this.seccoefficient.stripTrailingZeros().toPlainString());
		return returnValue;
	}

	public void setSeccoefficientstr(String secCoefficientstr) {
		this.seccoefficientstr = secCoefficientstr;
	}

	@Transient
	public String getSecqtystr() {
		String returnValue =this.secqty==null?"":(this.secqty.stripTrailingZeros()==null?"":this.secqty.stripTrailingZeros().toPlainString());
		return returnValue;
	}

	public void setSecqtystr(String secQtystr) {
		this.secqtystr = secQtystr;
	}

	@Transient
	public String getCoefficientstr() {
		String returnValue =this.coefficient==null?"":(this.coefficient.stripTrailingZeros()==null?"":this.coefficient.stripTrailingZeros().toPlainString());
		return returnValue;
	}

	public void setCoefficientstr(String coefficientstr) {
		this.coefficientstr = coefficientstr;
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
}