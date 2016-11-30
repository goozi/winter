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
import java.util.List;

import com.qihang.winter.poi.excel.annotation.ExcelCollection;
import com.qihang.winter.poi.excel.annotation.ExcelEntity;
import com.qihang.winter.poi.excel.annotation.ExcelIgnore;
import org.hibernate.annotations.GenericGenerator;
import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**   
 * @Title: Entity
 * @Description: 销售订单
 * @author onlineGenerator
 * @date 2016-06-16 11:35:43
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_Order", schema = "")
public class TScOrderExcelEntity implements java.io.Serializable {
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
	/**版本号*/
	private Integer version;
	/**单据类型*/
//  @Excel(name="单据类型")
	private Integer tranType;
	/**单据编号*/
	@Excel(name="单据编号")
	private String billNo;
	/**单据日期*/
   @Excel(name="单据日期",format = "yyyy-MM-dd")
	private Date date;
	/**客户*/
//   @Excel(name="客户")
	private String itemID;
	/**客户的名称*/
	private String itemName;
	/**客户和客户id的关系*/
	@ExcelEntity(id="id",name="name")
	private TScOrganizationToOrderEntity organizationToOrderEntity;
	/**经办人*/
//	@Excel(name="经办人")
	private String empID;
	/**经办人名称*/
	private String empName;
	/**经办人和实体的关系*/
	@ExcelEntity(id="id",name="name")
    private TScEmpToOrderEntity empToOrderEntity;
	/**部门*/
	@Excel(name="部门")
	@ExcelIgnore
	private String deptID;
	/**部门名称*/
	private String deptName;
	/**部门实体和部门id的关系*/
	@ExcelEntity(id="id",name="name")
	private TScDepartmentToOrderEntity departmentToOrderEntity;
	/**联系人*/
  @Excel(name="联系人")
  @ExcelIgnore
	private String contact;
	/**手机号码*/
    @Excel(name="手机号码")
	@ExcelIgnore
	private String mobilePhone;
	/**电话*/
  @Excel(name="电话")
  @ExcelIgnore
	private String phone;
	/**传真*/
  @Excel(name="传真")
  @ExcelIgnore
	private String fax;
	/**联系地址*/
  @Excel(name="联系地址")
  @ExcelIgnore
	private String address;
	/**仓库*/
  @Excel(name="仓库")
  @ExcelIgnore
	private String stockID;
	/**仓库名称*/
	private String stockName;
	/**付款方式*/
  @Excel(name="付款方式",dicCode = "SC_PAY_TYPE")
	private String fetchStyle;
	/**应收金额*/
	@Excel(name="应收金额")
	private BigDecimal allAmount;
	/**预收金额*/
  @Excel(name="预收金额")
	private BigDecimal preAmount;
	/**优惠金额*/
	@Excel(name="优惠金额")
	private BigDecimal rebateAmount;
	/**物流费用*/
	@Excel(name="物流费用")
	private BigDecimal freight;
	/**重量*/
	@Excel(name="重量")
	private BigDecimal weight;
	/**订单来源*/
  @Excel(name="订单来源")
	private BigDecimal mall;
	/**商城单号*/
  @Excel(name="商城单号")
	private BigDecimal mallOrderID;
	/**审核人*/
	@Excel(name="审核人")
	private String checkerID;
	/**审核日期*/
	@Excel(name="审核日期",format = "yyyy-MM-dd")
	private Date checkDate;
	/**审核状态*/
	@Excel(name="审核状态",replace = {"未审核_0","审核中_1","已审核_2"})
	private String checkState;
	/**关闭标记*/
	@Excel(name="关闭标记",replace = {"未关闭_0","已关闭_1"})
	private String closed;
	/**作废标记*/
	@Excel(name="作废标记",replace = {"未作废_0","已作废_1"})
	private String cancellation;
	/**订单金额*/
  @Excel(name="订单金额")
  @ExcelIgnore
	private BigDecimal amount;
	/**执行金额*/
  @Excel(name="执行金额")
  @ExcelIgnore
	private BigDecimal checkAmount;
	/**源单类型*/
  @Excel(name="源单类型")
  @ExcelIgnore
	private String classIDSrc;
	/**源单主键*/
  @Excel(name="源单主键")
  @ExcelIgnore
	private String interIDSrc;
	/**源单编号*/
  @Excel(name="源单编号")
  @ExcelIgnore
	private String billNoSrc;
	/**制单人*/
  @Excel(name="制单人")
  @ExcelIgnore
	private String billerID;
	/**自动关闭标记*/
  @Excel(name="自动关闭标记")
  @ExcelIgnore
	private String autoFlag;
	/**摘要*/
  @Excel(name="摘要")
  @ExcelIgnore
	private String explanation;
	private String sonID;
	@Excel(name="分支机构")
	private String sonName;
	/**销售订购单和商品的关系*/
	@ExcelCollection(name="商品")
	private List<TScOrderentryExcelEntity> listOrder;

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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  版本号
	 */

    @Version
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

	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  单据类型
	 */

	@Column(name ="TRANTYPE",nullable=true,length=32)
	public Integer getTranType(){
		return this.tranType;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  单据类型
	 */
	public void setTranType(Integer tranType){
		this.tranType = tranType;
	}

	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  单据日期
	 */

	@Column(name ="DATE",nullable=true,length=32)
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
	 *@return: java.lang.String  单据编号
	 */

	@Column(name ="BILLNO",nullable=true,length=50)
	public String getBillNo(){
		return this.billNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  单据编号
	 */
	public void setBillNo(String billNo){
		this.billNo = billNo;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  客户
	 */

//	@Column(name ="ITEMID",nullable=true,length=32)
	@Transient
	public String getItemID(){
		return this.itemID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  客户
	 */
	public void setItemID(String itemID){
		this.itemID = itemID;
	}

	/***
	 * 客户与id的关系
	 * @return
	 */
	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "ITEMID",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TScOrganizationToOrderEntity getOrganizationToOrderEntity() {
		return organizationToOrderEntity;
	}

	public void setOrganizationToOrderEntity(TScOrganizationToOrderEntity organizationToOrderEntity) {
		this.organizationToOrderEntity = organizationToOrderEntity;
	}


	@Transient
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系人
	 */
	
	@Column(name ="CONTACT",nullable=true,length=50)
	public String getContact(){
		return this.contact;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系人
	 */
	public void setContact(String contact){
		this.contact = contact;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机号码
	 */
	
	@Column(name ="MOBILEPHONE",nullable=true,length=15)
	public String getMobilePhone(){
		return this.mobilePhone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机号码
	 */
	public void setMobilePhone(String mobilePhone){
		this.mobilePhone = mobilePhone;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电话
	 */
	
	@Column(name ="PHONE",nullable=true,length=40)
	public String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电话
	 */
	public void setPhone(String phone){
		this.phone = phone;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  传真
	 */
	
	@Column(name ="FAX",nullable=true,length=40)
	public String getFax(){
		return this.fax;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  传真
	 */
	public void setFax(String fax){
		this.fax = fax;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系地址
	 */
	
	@Column(name ="ADDRESS",nullable=true,length=255)
	public String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系地址
	 */
	public void setAddress(String address){
		this.address = address;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  经办人
	 */
	
//	@Column(name ="EMPID",nullable=true,length=32)
	@Transient
	public String getEmpID(){
		return this.empID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  经办人
	 */
	public void setEmpID(String empID){
		this.empID = empID;
	}

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "EMPID",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TScEmpToOrderEntity getEmpToOrderEntity() {
		return empToOrderEntity;
	}

	public void setEmpToOrderEntity(TScEmpToOrderEntity empToOrderEntity) {
		this.empToOrderEntity = empToOrderEntity;
	}

	@Transient
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  部门
	 */
	
//	@Column(name ="DEPTID",nullable=true,length=32)
	@Transient
	public String getDeptID(){
		return this.deptID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  部门
	 */
	public void setDeptID(String deptID){
		this.deptID = deptID;
	}

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "DEPTID",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TScDepartmentToOrderEntity getDepartmentToOrderEntity() {
		return departmentToOrderEntity;
	}

	public void setDepartmentToOrderEntity(TScDepartmentToOrderEntity departmentToOrderEntity) {
		this.departmentToOrderEntity = departmentToOrderEntity;
	}

	@Transient
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库
	 */
	
	@Column(name ="STOCKID",nullable=true,length=32)
	public String getStockID(){
		return this.stockID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库
	 */
	public void setStockID(String stockID){
		this.stockID = stockID;
	}

	@Transient
	public String getStockName() {
		return stockName;
	}

	public void setStockName(String stockName) {
		this.stockName = stockName;
	}

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  付款方式
	 */
	
	@Column(name ="FETCHSTYLE",nullable=true,length=50)
	public String getFetchStyle(){
		return this.fetchStyle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  付款方式
	 */
	public void setFetchStyle(String fetchStyle){
		this.fetchStyle = fetchStyle;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  预收金额
	 */
	
	@Column(name ="PREAMOUNT",nullable=true,length=32)
	public BigDecimal getPreAmount(){
		return this.preAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  预收金额
	 */
	public void setPreAmount(BigDecimal preAmount){
		this.preAmount = preAmount;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  订单来源
	 */
	
	@Column(name ="MALL",nullable=true,length=32)
	public BigDecimal getMall(){
		return this.mall;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  订单来源
	 */
	public void setMall(BigDecimal mall){
		this.mall = mall;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  商城单号
	 */
	
	@Column(name ="MALLORDERID",nullable=true,length=32)
	public BigDecimal getMallOrderID(){
		return this.mallOrderID;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  商城单号
	 */
	public void setMallOrderID(BigDecimal mallOrderID){
		this.mallOrderID = mallOrderID;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  优惠金额
	 */
	
	@Column(name ="REBATEAMOUNT",nullable=true,length=32)
	public BigDecimal getRebateAmount(){
		return this.rebateAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  优惠金额
	 */
	public void setRebateAmount(BigDecimal rebateAmount){
		this.rebateAmount = rebateAmount;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  物流费用
	 */
	
	@Column(name ="FREIGHT",nullable=true,length=32)
	public BigDecimal getFreight(){
		return this.freight;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  物流费用
	 */
	public void setFreight(BigDecimal freight){
		this.freight = freight;
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
	 *@return: java.math.BigDecimal  订单金额
	 */
	
	@Column(name ="AMOUNT",nullable=true,length=32)
	public BigDecimal getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  订单金额
	 */
	public void setAmount(BigDecimal amount){
		this.amount = amount;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  应收金额
	 */
	
	@Column(name ="ALLAMOUNT",nullable=true,length=32)
	public BigDecimal getAllAmount(){
		return this.allAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  应收金额
	 */
	public void setAllAmount(BigDecimal allAmount){
		this.allAmount = allAmount;
	}
	
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  执行金额
	 */
	
	@Column(name ="CHECKAMOUNT",nullable=true,length=32)
	public BigDecimal getCheckAmount(){
		return this.checkAmount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  执行金额
	 */
	public void setCheckAmount(BigDecimal checkAmount){
		this.checkAmount = checkAmount;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单类型
	 */
	
	@Column(name ="CLASSID_SRC",nullable=true,length=32)
	public String getClassIDSrc(){
		return this.classIDSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单类型
	 */
	public void setClassIDSrc(String classIDSrc){
		this.classIDSrc = classIDSrc;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单主键
	 */
	
	@Column(name ="INTERID_SRC",nullable=true,length=32)
	public String getInterIDSrc(){
		return this.interIDSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单主键
	 */
	public void setInterIDSrc(String interIDSrc){
		this.interIDSrc = interIDSrc;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单编号
	 */
	
	@Column(name ="BILLNO_SRC",nullable=true,length=50)
	public String getBillNoSrc(){
		return this.billNoSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单编号
	 */
	public void setBillNoSrc(String billNoSrc){
		this.billNoSrc = billNoSrc;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核人
	 */
	
	@Column(name ="CHECKERID",nullable=true,length=32)
	public String getCheckerID(){
		return this.checkerID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核人
	 */
	public void setCheckerID(String checkerID){
		this.checkerID = checkerID;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  制单人
	 */
	
	@Column(name ="BILLERID",nullable=true,length=32)
	public String getBillerID(){
		return this.billerID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  制单人
	 */
	public void setBillerID(String billerID){
		this.billerID = billerID;
	}
	
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  审核日期
	 */
	
	@Column(name ="CHECKDATE",nullable=true,length=32)
	public Date getCheckDate(){
		return this.checkDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  审核日期
	 */
	public void setCheckDate(Date checkDate){
		this.checkDate = checkDate;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  审核状态
	 */
	
	@Column(name ="CHECKSTATE",nullable=true,length=50)
	public String getCheckState(){
		return this.checkState;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  审核状态
	 */
	public void setCheckState(String checkState){
		this.checkState = checkState;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  关闭标记
	 */
	
	@Column(name ="CLOSED",nullable=true,length=50)
	public String getClosed(){
		return this.closed;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关闭标记
	 */
	public void setClosed(String closed){
		this.closed = closed;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  自动关闭标记
	 */
	
	@Column(name ="AUTOFLAG",nullable=true,length=50)
	public String getAutoFlag(){
		return this.autoFlag;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  自动关闭标记
	 */
	public void setAutoFlag(String autoFlag){
		this.autoFlag = autoFlag;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  作废标记
	 */
	
	@Column(name ="CANCELLATION",nullable=true,length=50)
	public String getCancellation(){
		return this.cancellation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  作废标记
	 */
	public void setCancellation(String cancellation){
		this.cancellation = cancellation;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  摘要
	 */
	
	@Column(name ="EXPLANATION",nullable=true,length=255)
	public String getExplanation(){
		return this.explanation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  摘要
	 */
	public void setExplanation(String explanation){
		this.explanation = explanation;
	}
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  分支机构
	 */
	
	@Column(name ="SONID",nullable=true,length=32)
	public String getSonID(){
		return this.sonID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分支机构
	 */
	public void setSonID(String sonID){
		this.sonID = sonID;
	}

	@OneToMany(mappedBy = "orderExcelEntity",cascade = CascadeType.ALL)
	public List<TScOrderentryExcelEntity> getListOrder() {
		return listOrder;
	}

	public void setListOrder(List<TScOrderentryExcelEntity> listOrder) {
		this.listOrder = listOrder;
	}

	@Transient
	public String getSonName() {
		return sonName;
	}

	public void setSonName(String sonName) {
		this.sonName = sonName;
	}
}
