package com.qihang.buss.sc.sales.entity;

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
 * @Description: v_sl_stockbill
 * @author onlineGenerator
 * @date 2016-08-01 15:47:14
 * @version V1.0   
 *
 */
@Entity
@Table(name = "v_sc_sl_stockbill", schema = "")
@SuppressWarnings("serial")
public class TScSlStockbillViewEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**创建人名称*/
	@Excel(name="创建人名称")
	private java.lang.String createName;
	/**创建人登录名称*/
	@Excel(name="创建人登录名称")
	private java.lang.String createBy;
	/**创建日期*/
	@Excel(name="创建日期")
	private java.util.Date createDate;
	/**更新人名称*/
	@Excel(name="更新人名称")
	private java.lang.String updateName;
	/**更新人登录名称*/
	@Excel(name="更新人登录名称")
	private java.lang.String updateBy;
	/**更新日期*/
	@Excel(name="更新日期")
	private java.util.Date updateDate;
	/**单据类型*/
	@Excel(name="单据类型")
	private java.lang.String tranType;
	/**单据编号*/
	@Excel(name="单据编号")
	private java.lang.String billNo;
	/**单据日期*/
	@Excel(name="单据日期")
	private java.util.Date date;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String itemName;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String empName;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String deptName;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String stockname;
	/**供应商*/
	@Excel(name="供应商")
	private java.lang.String itemid;
	/**经办人*/
	@Excel(name="经办人")
	private java.lang.String empid;
	/**部门*/
	@Excel(name="部门")
	private java.lang.String deptid;
	/**仓库*/
	@Excel(name="仓库")
	private java.lang.String stockid;
	/**应付金额*/
	@Excel(name="应付金额")
	private java.lang.Double allamount;
	/**优惠金额*/
	@Excel(name="优惠金额")
	private java.lang.Double rebateamount;
	/**本次付款*/
	@Excel(name="本次付款")
	private java.lang.Double curpayamount;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String accountid;
	/**联系人*/
	@Excel(name="联系人")
	private java.lang.String contact;
	/**手机*/
	@Excel(name="手机")
	private java.lang.String mobilephone;
	/**电话*/
	@Excel(name="电话")
	private java.lang.String phone;
	/**传真*/
	@Excel(name="传真")
	private java.lang.String fax;
	/**联系地址*/
	@Excel(name="联系地址")
	private java.lang.String address;
	/**单据金额*/
	@Excel(name="单据金额")
	private java.lang.Double amount;
	/**付款金额*/
	@Excel(name="付款金额")
	private java.lang.Double checkamount;
	/**源单类型*/
	@Excel(name="源单类型")
	private java.lang.String classidSrc;
	/**源单主键*/
	@Excel(name="源单主键")
	private java.lang.String idSrc;
	/**源单编号*/
	@Excel(name="源单编号")
	private java.lang.String billnoSrc;
	/**billerid*/
	@Excel(name="billerid")
	private java.lang.String billerid;
	/**审核人*/
	@Excel(name="审核人")
	private java.lang.String checkerid;
	/**审核日期*/
	@Excel(name="审核日期")
	private java.util.Date checkdate;
	/**审核状态*/
	@Excel(name="审核状态")
	private java.lang.Integer checkState;
	/**作废标记*/
	@Excel(name="作废标记")
	private java.lang.Integer cancellation;
	/**摘要*/
	@Excel(name="摘要")
	private java.lang.String explanation;
	/**sonid*/
	@Excel(name="sonid")
	private java.lang.String sonId;
	/**版本号*/
	@Excel(name="版本号")
	private java.lang.Integer version;
	/**重量*/
	@Excel(name="重量")
	private java.lang.Double weight;
	/**id*/
	@Excel(name="id")
	private java.lang.String entryId;
	/**商品*/
	@Excel(name="商品")
	private java.lang.String entryitemid;
	/**仓库*/
	@Excel(name="仓库")
	private java.lang.String entrystockid;
	/**批号*/
	@Excel(name="批号")
	private java.lang.String batchno;
	/**单位*/
	@Excel(name="单位")
	private java.lang.String unitid;
	/**数量*/
	@Excel(name="数量")
	private java.lang.Double qty;
	/**基本单位*/
	@Excel(name="基本单位")
	private java.lang.String basicunitid;
	/**换算率*/
	@Excel(name="换算率")
	private java.lang.Double coefficient;
	/**基本数量*/
	@Excel(name="基本数量")
	private java.lang.Double basicqty;
	/**辅助单位*/
	@Excel(name="辅助单位")
	private java.lang.String secunitid;
	/**辅助换算率*/
	@Excel(name="辅助换算率")
	private java.lang.Double seccoefficient;
	/**辅助数量*/
	@Excel(name="辅助数量")
	private java.lang.Double secqty;
	/**单价*/
	@Excel(name="单价")
	private java.lang.Double taxpriceex;
	/**金额*/
	@Excel(name="金额")
	private java.lang.Double taxamountex;
	/**折扣率（%）*/
	@Excel(name="折扣率（%）")
	private java.lang.Double discountrate;
	/**折后单价*/
	@Excel(name="折后单价")
	private java.lang.Double taxprice;
	/**折后金额*/
	@Excel(name="折后金额")
	private java.lang.Double intaxamount;
	/**税率（%）*/
	@Excel(name="税率（%）")
	private java.lang.Double taxrate;
	/**不含税单价*/
	@Excel(name="不含税单价")
	private java.lang.Double entryprice;
	/**不含税金额*/
	@Excel(name="不含税金额")
	private java.lang.Double entryamount;
	/**不含税折后单价*/
	@Excel(name="不含税折后单价")
	private java.lang.Double discountprice;
	/**不含税折后金额*/
	@Excel(name="不含税折后金额")
	private java.lang.Double discountamount;
	/**税额*/
	@Excel(name="税额")
	private java.lang.Double taxamount;
	/**生产日期*/
	@Excel(name="生产日期")
	private java.util.Date kfdate;
	/**保质期*/
	@Excel(name="保质期")
	private java.lang.Integer kfperiod;
	/**促销类型*/
	@Excel(name="促销类型")
	private java.lang.String salesid;
	/**重量*/
	@Excel(name="重量")
	private java.lang.Double entryweight;
	/**kfdatetype*/
	@Excel(name="kfdatetype")
	private java.lang.String kfdatetype;
	/**有效期至*/
	@Excel(name="有效期至")
	private java.util.Date perioddate;
	/**成本单价*/
	@Excel(name="成本单价")
	private java.lang.Double costprice;
	/**成本金额*/
	@Excel(name="成本金额")
	private java.lang.Double costamount;
	/**赠品标记*/
	@Excel(name="赠品标记")
	private java.lang.Integer freegifts;
	/**退货数量*/
	@Excel(name="退货数量")
	private java.lang.Double commitqty;
	/**单据名称*/
	@Excel(name="单据名称")
	private java.lang.String entryclassidsrc;
	/**源单主键*/
	@Excel(name="源单主键")
	private java.lang.String idsrc;
	/**源单编号*/
	@Excel(name="源单编号")
	private java.lang.String entrybillnosrc;
	/**源单分录主键*/
	@Excel(name="源单分录主键")
	private java.lang.String entryidsrc;
	/**订单主键*/
	@Excel(name="订单主键")
	private java.lang.String idorder;
	/**订单编号*/
	@Excel(name="订单编号")
	private java.lang.String billnoorder;
	/**订单分录主键*/
	@Excel(name="订单分录主键")
	private java.lang.String entryidorder;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String note;
	/**分录号*/
	@Excel(name="分录号")
	private java.lang.Integer findex;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String entrystockname;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String entryItemName;
	/**编号*/
	@Excel(name="编号")
	private java.lang.String entryitemno;
	/**规格*/
	@Excel(name="规格")
	private java.lang.String model;
	/**条形码*/
	@Excel(name="条形码")
	private java.lang.String barcode;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String unitname;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String basicunitname;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String secunitname;
	/**单位换算率*/
	@Excel(name="单位换算率")
	private java.lang.Double basiccoefficient;
	/**采购限价*/
	@Excel(name="采购限价")
	private java.lang.Double cglimitprice;
	/**销售限价*/
	@Excel(name="销售限价")
	private java.lang.Double xslimitprice;
	/**按保质期管理*/
	@Excel(name="按保质期管理")
	private java.lang.String iskfperiod;
	/**启用批次管理*/
	@Excel(name="启用批次管理")
	private java.lang.String batchmanager;
	/**isstock*/
	@Excel(name="isstock")
	private java.lang.Integer isstock;
	/**isaudit*/
	@Excel(name="isaudit")
	private java.lang.Integer isaudit;

	private String checkerName;

	private Double freight;//物流费用
	private Double itemWeight;//商品重量
	private String kfdateTypeInfo;//保质期类型

	private Integer iscreditmgr;//客户是否信用标记
	private BigDecimal creditamount;//客户信用额度
	private String isAllowAudit;//是否进行审核

	private String sonName;

	private String tranTypeName;

	private String salesName;//促销名称

	private String mallorderid;//商城单号

	/**同步更新php状态0：未同步，1已同步**/
	private Integer sysState;//

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
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
	public java.lang.String getTranType(){
		return this.tranType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据类型
	 */
	public void setTranType(java.lang.String tranType){
		this.tranType = tranType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单据编号
	 */
	@Column(name ="BILLNO",nullable=true,length=50)
	public java.lang.String getBillNo(){
		return this.billNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据编号
	 */
	public void setBillNo(java.lang.String billNo){
		this.billNo = billNo;
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
	public void setDate(java.util.Date date){
		this.date = date;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="ITEMNAME",nullable=true,length=150)
	public java.lang.String getItemName(){
		return this.itemName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setItemName(java.lang.String itemName){
		this.itemName = itemName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="EMPNAME",nullable=true,length=80)
	public java.lang.String getEmpName(){
		return this.empName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setEmpName(java.lang.String empName){
		this.empName = empName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="DEPTNAME",nullable=true,length=80)
	public java.lang.String getDeptName(){
		return this.deptName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setDeptName(java.lang.String deptName){
		this.deptName = deptName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="STOCKNAME",nullable=true,length=80)
	public java.lang.String getStockname(){
		return this.stockname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setStockname(java.lang.String stockname){
		this.stockname = stockname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  供应商
	 */
	@Column(name ="ITEMID",nullable=true,length=32)
	public java.lang.String getItemid(){
		return this.itemid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  供应商
	 */
	public void setItemid(java.lang.String itemid){
		this.itemid = itemid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  经办人
	 */
	@Column(name ="EMPID",nullable=true,length=32)
	public java.lang.String getEmpid(){
		return this.empid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  经办人
	 */
	public void setEmpid(java.lang.String empid){
		this.empid = empid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门
	 */
	@Column(name ="DEPTID",nullable=true,length=32)
	public java.lang.String getDeptid(){
		return this.deptid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门
	 */
	public void setDeptid(java.lang.String deptid){
		this.deptid = deptid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库
	 */
	@Column(name ="STOCKID",nullable=true,length=32)
	public java.lang.String getStockid(){
		return this.stockid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库
	 */
	public void setStockid(java.lang.String stockid){
		this.stockid = stockid;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  应付金额
	 */
	@Column(name ="ALLAMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getAllamount(){
		return this.allamount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  应付金额
	 */
	public void setAllamount(java.lang.Double allamount){
		this.allamount = allamount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  优惠金额
	 */
	@Column(name ="REBATEAMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getRebateamount(){
		return this.rebateamount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  优惠金额
	 */
	public void setRebateamount(java.lang.Double rebateamount){
		this.rebateamount = rebateamount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  本次付款
	 */
	@Column(name ="CURPAYAMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getCurpayamount(){
		return this.curpayamount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  本次付款
	 */
	public void setCurpayamount(java.lang.Double curpayamount){
		this.curpayamount = curpayamount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="ACCOUNTID",nullable=true,length=50)
	public java.lang.String getAccountid(){
		return this.accountid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setAccountid(java.lang.String accountid){
		this.accountid = accountid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人
	 */
	@Column(name ="CONTACT",nullable=true,length=50)
	public java.lang.String getContact(){
		return this.contact;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系人
	 */
	public void setContact(java.lang.String contact){
		this.contact = contact;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机
	 */
	@Column(name ="MOBILEPHONE",nullable=true,length=15)
	public java.lang.String getMobilephone(){
		return this.mobilephone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机
	 */
	public void setMobilephone(java.lang.String mobilephone){
		this.mobilephone = mobilephone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话
	 */
	@Column(name ="PHONE",nullable=true,length=40)
	public java.lang.String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电话
	 */
	public void setPhone(java.lang.String phone){
		this.phone = phone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  传真
	 */
	@Column(name ="FAX",nullable=true,length=40)
	public java.lang.String getFax(){
		return this.fax;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  传真
	 */
	public void setFax(java.lang.String fax){
		this.fax = fax;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系地址
	 */
	@Column(name ="ADDRESS",nullable=true,length=255)
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系地址
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  单据金额
	 */
	@Column(name ="AMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  单据金额
	 */
	public void setAmount(java.lang.Double amount){
		this.amount = amount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  付款金额
	 */
	@Column(name ="CHECKAMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getCheckamount(){
		return this.checkamount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  付款金额
	 */
	public void setCheckamount(java.lang.Double checkamount){
		this.checkamount = checkamount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单类型
	 */
	@Column(name ="CLASSID_SRC",nullable=true,length=32)
	public java.lang.String getClassidSrc(){
		return this.classidSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单类型
	 */
	public void setClassidSrc(java.lang.String classidSrc){
		this.classidSrc = classidSrc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单主键
	 */
	@Column(name ="ID_SRC",nullable=true,length=32)
	public java.lang.String getIdSrc(){
		return this.idSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单主键
	 */
	public void setIdSrc(java.lang.String idSrc){
		this.idSrc = idSrc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单编号
	 */
	@Column(name ="BILLNO_SRC",nullable=true,length=50)
	public java.lang.String getBillnoSrc(){
		return this.billnoSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单编号
	 */
	public void setBillnoSrc(java.lang.String billnoSrc){
		this.billnoSrc = billnoSrc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  billerid
	 */
	@Column(name ="BILLERID",nullable=true,length=50)
	public java.lang.String getBillerid(){
		return this.billerid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  billerid
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
	public java.lang.Integer getCheckState(){
		return this.checkState;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  审核状态
	 */
	public void setCheckState(java.lang.Integer checkState){
		this.checkState = checkState;
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
	 *@return: java.lang.String  sonid
	 */
	@Column(name ="SONID",nullable=true,length=100)
	public java.lang.String getSonId(){
		return this.sonId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  sonid
	 */
	public void setSonId(java.lang.String sonId){
		this.sonId = sonId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  版本号
	 */
	@Column(name ="VERSION",nullable=true,length=10)
	public java.lang.Integer getVersion(){
		return this.version;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  版本号
	 */
	public void setVersion(java.lang.Integer version){
		this.version = version;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  重量
	 */
	@Column(name ="WEIGHT",nullable=true,scale=10,length=20)
	public java.lang.Double getWeight(){
		return this.weight;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  重量
	 */
	public void setWeight(java.lang.Double weight){
		this.weight = weight;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	@Id
	@Column(name ="ENTRYID",nullable=false,length=36)
	public java.lang.String getEntryId(){
		return this.entryId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setEntryId(java.lang.String entryId){
		this.entryId = entryId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品
	 */
	@Column(name ="ENTRYITEMID",nullable=true,length=32)
	public java.lang.String getEntryitemid(){
		return this.entryitemid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品
	 */
	public void setEntryitemid(java.lang.String entryitemid){
		this.entryitemid = entryitemid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库
	 */
	@Column(name ="ENTRYSTOCKID",nullable=true,length=32)
	public java.lang.String getEntrystockid(){
		return this.entrystockid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库
	 */
	public void setEntrystockid(java.lang.String entrystockid){
		this.entrystockid = entrystockid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  批号
	 */
	@Column(name ="BATCHNO",nullable=true,length=50)
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
	 *@return: java.lang.String  单位
	 */
	@Column(name ="UNITID",nullable=true,length=32)
	public java.lang.String getUnitid(){
		return this.unitid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单位
	 */
	public void setUnitid(java.lang.String unitid){
		this.unitid = unitid;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  数量
	 */
	@Column(name ="QTY",nullable=true,scale=10,length=20)
	public java.lang.Double getQty(){
		return this.qty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  数量
	 */
	public void setQty(java.lang.Double qty){
		this.qty = qty;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  基本单位
	 */
	@Column(name ="BASICUNITID",nullable=true,length=32)
	public java.lang.String getBasicunitid(){
		return this.basicunitid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  基本单位
	 */
	public void setBasicunitid(java.lang.String basicunitid){
		this.basicunitid = basicunitid;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  换算率
	 */
	@Column(name ="COEFFICIENT",nullable=true,scale=10,length=20)
	public java.lang.Double getCoefficient(){
		return this.coefficient;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  换算率
	 */
	public void setCoefficient(java.lang.Double coefficient){
		this.coefficient = coefficient;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  基本数量
	 */
	@Column(name ="BASICQTY",nullable=true,scale=10,length=20)
	public java.lang.Double getBasicqty(){
		return this.basicqty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  基本数量
	 */
	public void setBasicqty(java.lang.Double basicqty){
		this.basicqty = basicqty;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  辅助单位
	 */
	@Column(name ="SECUNITID",nullable=true,length=32)
	public java.lang.String getSecunitid(){
		return this.secunitid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  辅助单位
	 */
	public void setSecunitid(java.lang.String secunitid){
		this.secunitid = secunitid;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  辅助换算率
	 */
	@Column(name ="SECCOEFFICIENT",nullable=true,scale=10,length=20)
	public java.lang.Double getSeccoefficient(){
		return this.seccoefficient;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  辅助换算率
	 */
	public void setSeccoefficient(java.lang.Double seccoefficient){
		this.seccoefficient = seccoefficient;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  辅助数量
	 */
	@Column(name ="SECQTY",nullable=true,scale=10,length=20)
	public java.lang.Double getSecqty(){
		return this.secqty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  辅助数量
	 */
	public void setSecqty(java.lang.Double secqty){
		this.secqty = secqty;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  单价
	 */
	@Column(name ="TAXPRICEEX",nullable=true,scale=10,length=20)
	public java.lang.Double getTaxpriceex(){
		return this.taxpriceex;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  单价
	 */
	public void setTaxpriceex(java.lang.Double taxpriceex){
		this.taxpriceex = taxpriceex;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  金额
	 */
	@Column(name ="TAXAMOUNTEX",nullable=true,scale=10,length=20)
	public java.lang.Double getTaxamountex(){
		return this.taxamountex;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  金额
	 */
	public void setTaxamountex(java.lang.Double taxamountex){
		this.taxamountex = taxamountex;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  折扣率（%）
	 */
	@Column(name ="DISCOUNTRATE",nullable=true,scale=10,length=20)
	public java.lang.Double getDiscountrate(){
		return this.discountrate;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  折扣率（%）
	 */
	public void setDiscountrate(java.lang.Double discountrate){
		this.discountrate = discountrate;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  折后单价
	 */
	@Column(name ="TAXPRICE",nullable=true,scale=10,length=20)
	public java.lang.Double getTaxprice(){
		return this.taxprice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  折后单价
	 */
	public void setTaxprice(java.lang.Double taxprice){
		this.taxprice = taxprice;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  折后金额
	 */
	@Column(name ="INTAXAMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getIntaxamount(){
		return this.intaxamount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  折后金额
	 */
	public void setIntaxamount(java.lang.Double intaxamount){
		this.intaxamount = intaxamount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  税率（%）
	 */
	@Column(name ="TAXRATE",nullable=true,scale=10,length=20)
	public java.lang.Double getTaxrate(){
		return this.taxrate;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  税率（%）
	 */
	public void setTaxrate(java.lang.Double taxrate){
		this.taxrate = taxrate;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  不含税单价
	 */
	@Column(name ="ENTRYPRICE",nullable=true,scale=10,length=20)
	public java.lang.Double getEntryprice(){
		return this.entryprice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  不含税单价
	 */
	public void setEntryprice(java.lang.Double entryprice){
		this.entryprice = entryprice;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  不含税金额
	 */
	@Column(name ="ENTRYAMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getEntryamount(){
		return this.entryamount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  不含税金额
	 */
	public void setEntryamount(java.lang.Double entryamount){
		this.entryamount = entryamount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  不含税折后单价
	 */
	@Column(name ="DISCOUNTPRICE",nullable=true,scale=10,length=20)
	public java.lang.Double getDiscountprice(){
		return this.discountprice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  不含税折后单价
	 */
	public void setDiscountprice(java.lang.Double discountprice){
		this.discountprice = discountprice;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  不含税折后金额
	 */
	@Column(name ="DISCOUNTAMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getDiscountamount(){
		return this.discountamount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  不含税折后金额
	 */
	public void setDiscountamount(java.lang.Double discountamount){
		this.discountamount = discountamount;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  税额
	 */
	@Column(name ="TAXAMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getTaxamount(){
		return this.taxamount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  税额
	 */
	public void setTaxamount(java.lang.Double taxamount){
		this.taxamount = taxamount;
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
	 *@return: java.lang.String  促销类型
	 */
	@Column(name ="SALESID",nullable=true,length=10)
	public java.lang.String getSalesid(){
		return this.salesid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  促销类型
	 */
	public void setSalesid(java.lang.String salesid){
		this.salesid = salesid;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  重量
	 */
	@Column(name ="ENTRYWEIGHT",nullable=true,scale=10,length=20)
	public java.lang.Double getEntryweight(){
		return this.entryweight;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  重量
	 */
	public void setEntryweight(java.lang.Double entryweight){
		this.entryweight = entryweight;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  kfdatetype
	 */
	@Column(name ="KFDATETYPE",nullable=true,length=61)
	public java.lang.String getKfdatetype(){
		return this.kfdatetype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  kfdatetype
	 */
	public void setKfdatetype(java.lang.String kfdatetype){
		this.kfdatetype = kfdatetype;
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
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  成本单价
	 */
	@Column(name ="COSTPRICE",nullable=true,scale=10,length=20)
	public java.lang.Double getCostprice(){
		return this.costprice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  成本单价
	 */
	public void setCostprice(java.lang.Double costprice){
		this.costprice = costprice;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  成本金额
	 */
	@Column(name ="COSTAMOUNT",nullable=true,scale=10,length=20)
	public java.lang.Double getCostamount(){
		return this.costamount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  成本金额
	 */
	public void setCostamount(java.lang.Double costamount){
		this.costamount = costamount;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  赠品标记
	 */
	@Column(name ="FREEGIFTS",nullable=true,length=10)
	public java.lang.Integer getFreegifts(){
		return this.freegifts;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  赠品标记
	 */
	public void setFreegifts(java.lang.Integer freegifts){
		this.freegifts = freegifts;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  退货数量
	 */
	@Column(name ="COMMITQTY",nullable=true,scale=10,length=20)
	public java.lang.Double getCommitqty(){
		return this.commitqty;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  退货数量
	 */
	public void setCommitqty(java.lang.Double commitqty){
		this.commitqty = commitqty;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  单据名称
	 */
	@Column(name ="ENTRYCLASSIDSRC",nullable=true,length=100)
	public java.lang.String getEntryclassidsrc(){
		return this.entryclassidsrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据名称
	 */
	public void setEntryclassidsrc(java.lang.String entryclassidsrc){
		this.entryclassidsrc = entryclassidsrc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单主键
	 */
	@Column(name ="IDSRC",nullable=true,length=32)
	public java.lang.String getIdsrc(){
		return this.idsrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单主键
	 */
	public void setIdsrc(java.lang.String idsrc){
		this.idsrc = idsrc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单编号
	 */
	@Column(name ="ENTRYBILLNOSRC",nullable=true,length=50)
	public java.lang.String getEntrybillnosrc(){
		return this.entrybillnosrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单编号
	 */
	public void setEntrybillnosrc(java.lang.String entrybillnosrc){
		this.entrybillnosrc = entrybillnosrc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单分录主键
	 */
	@Column(name ="ENTRYIDSRC",nullable=true,length=32)
	public java.lang.String getEntryidsrc(){
		return this.entryidsrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单分录主键
	 */
	public void setEntryidsrc(java.lang.String entryidsrc){
		this.entryidsrc = entryidsrc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单主键
	 */
	@Column(name ="IDORDER",nullable=true,length=32)
	public java.lang.String getIdorder(){
		return this.idorder;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单主键
	 */
	public void setIdorder(java.lang.String idorder){
		this.idorder = idorder;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单编号
	 */
	@Column(name ="BILLNOORDER",nullable=true,length=50)
	public java.lang.String getBillnoorder(){
		return this.billnoorder;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单编号
	 */
	public void setBillnoorder(java.lang.String billnoorder){
		this.billnoorder = billnoorder;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单分录主键
	 */
	@Column(name ="ENTRYIDORDER",nullable=true,length=32)
	public java.lang.String getEntryidorder(){
		return this.entryidorder;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单分录主键
	 */
	public void setEntryidorder(java.lang.String entryidorder){
		this.entryidorder = entryidorder;
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  分录号
	 */
	@Column(name ="FINDEX",nullable=true,length=10)
	public java.lang.Integer getFindex(){
		return this.findex;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  分录号
	 */
	public void setFindex(java.lang.Integer findex){
		this.findex = findex;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="ENTRYSTOCKNAME",nullable=true,length=80)
	public java.lang.String getEntrystockname(){
		return this.entrystockname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setEntrystockname(java.lang.String entrystockname){
		this.entrystockname = entrystockname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="ENTRYITEMNAME",nullable=true,length=100)
	public java.lang.String getEntryItemName(){
		return this.entryItemName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setEntryItemName(java.lang.String entryItemName){
		this.entryItemName = entryItemName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  编号
	 */
	@Column(name ="ENTRYITEMNO",nullable=true,length=80)
	public java.lang.String getEntryitemno(){
		return this.entryitemno;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  编号
	 */
	public void setEntryitemno(java.lang.String entryitemno){
		this.entryitemno = entryitemno;
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
	 *@return: java.lang.String  名称
	 */
	@Column(name ="UNITNAME",nullable=true,length=80)
	public java.lang.String getUnitname(){
		return this.unitname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setUnitname(java.lang.String unitname){
		this.unitname = unitname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="BASICUNITNAME",nullable=true,length=80)
	public java.lang.String getBasicunitname(){
		return this.basicunitname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setBasicunitname(java.lang.String basicunitname){
		this.basicunitname = basicunitname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="SECUNITNAME",nullable=true,length=80)
	public java.lang.String getSecunitname(){
		return this.secunitname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setSecunitname(java.lang.String secunitname){
		this.secunitname = secunitname;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  单位换算率
	 */
	@Column(name ="BASICCOEFFICIENT",nullable=true,length=22)
	public java.lang.Double getBasiccoefficient(){
		return this.basiccoefficient;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  单位换算率
	 */
	public void setBasiccoefficient(java.lang.Double basiccoefficient){
		this.basiccoefficient = basiccoefficient;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  采购限价
	 */
	@Column(name ="CGLIMITPRICE",nullable=true,length=22)
	public java.lang.Double getCglimitprice(){
		return this.cglimitprice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  采购限价
	 */
	public void setCglimitprice(java.lang.Double cglimitprice){
		this.cglimitprice = cglimitprice;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  销售限价
	 */
	@Column(name ="XSLIMITPRICE",nullable=true,length=22)
	public java.lang.Double getXslimitprice(){
		return this.xslimitprice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  销售限价
	 */
	public void setXslimitprice(java.lang.Double xslimitprice){
		this.xslimitprice = xslimitprice;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  按保质期管理
	 */
	@Column(name ="ISKFPERIOD",nullable=true,length=50)
	public java.lang.String getIskfperiod(){
		return this.iskfperiod;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  按保质期管理
	 */
	public void setIskfperiod(java.lang.String iskfperiod){
		this.iskfperiod = iskfperiod;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  启用批次管理
	 */
	@Column(name ="BATCHMANAGER",nullable=true,length=50)
	public java.lang.String getBatchmanager(){
		return this.batchmanager;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  启用批次管理
	 */
	public void setBatchmanager(java.lang.String batchmanager){
		this.batchmanager = batchmanager;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  isstock
	 */
	@Column(name ="ISSTOCK",nullable=true,length=10)
	public java.lang.Integer getIsstock(){
		return this.isstock;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  isstock
	 */
	public void setIsstock(java.lang.Integer isstock){
		this.isstock = isstock;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  isaudit
	 */
	@Column(name ="ISAUDIT",nullable=true,length=10)
	public java.lang.Integer getIsaudit(){
		return this.isaudit;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  isaudit
	 */
	public void setIsaudit(java.lang.Integer isaudit){
		this.isaudit = isaudit;
	}

	@Column(name ="checkerName",nullable=true)
	public String getCheckerName() {
		return checkerName;
	}

	public void setCheckerName(String checkerName) {
		this.checkerName = checkerName;
	}

	@Column(name ="freight",nullable=true)
	public Double getFreight() {
		return freight;
	}

	public void setFreight(Double freight) {
		this.freight = freight;
	}

	@Column(name ="itemWeight",nullable=true)
	public Double getItemWeight() {
		return itemWeight;
	}

	public void setItemWeight(Double itemWeight) {
		this.itemWeight = itemWeight;
	}

	@Column(name ="kfdateType_",nullable=true)
	public String getKfdateTypeInfo() {
		return kfdateTypeInfo;
	}

	public void setKfdateTypeInfo(String kfdateTypeInfo) {
		this.kfdateTypeInfo = kfdateTypeInfo;
	}

	@Column(name ="iscreditmgr",nullable=true)
	public Integer getIscreditmgr() {
		return iscreditmgr;
	}

	public void setIscreditmgr(Integer iscreditmgr) {
		this.iscreditmgr = iscreditmgr;
	}

	@Column(name ="creditamount",nullable=true)
	public BigDecimal getCreditamount() {
		return creditamount;
	}

	public void setCreditamount(BigDecimal creditamount) {
		this.creditamount = creditamount;
	}

	@Transient
	public String getIsAllowAudit() {
		return isAllowAudit;
	}

	public void setIsAllowAudit(String isAllowAudit) {
		this.isAllowAudit = isAllowAudit;
	}

	@Column(name ="sonName",nullable=true)
	public String getSonName() {
		return sonName;
	}

	public void setSonName(String sonName) {
		this.sonName = sonName;
	}

	@Column(name ="tranTypeName",nullable=true)
	public String getTranTypeName() {
		return tranTypeName;
	}

	public void setTranTypeName(String tranTypeName) {
		this.tranTypeName = tranTypeName;
	}

	@Column(name ="salesName",nullable=true)
	public String getSalesName() {
		return salesName;
	}

	public void setSalesName(String salesName) {
		this.salesName = salesName;
	}

	@Column(name ="mallorderid",nullable=true)
	public String getMallorderid() {
		return mallorderid;
	}

	public void setMallorderid(String mallorderid) {
		this.mallorderid = mallorderid;
	}


	@Column(name ="sysState",nullable=true)
	public Integer getSysState() {
		return sysState;
	}

	public void setSysState(Integer sysState) {
		this.sysState = sysState;
	}

}
