package com.qihang.buss.sc.sales.entity;

import com.qihang.buss.sc.baseinfo.entity.*;
import com.qihang.winter.poi.excel.annotation.Excel;
import com.qihang.winter.poi.excel.annotation.ExcelCollection;
import com.qihang.winter.poi.excel.annotation.ExcelEntity;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.List;

/**
 * Created by LenovoM4550 on 2016-07-18.
 */
@Entity
@Table(name = "T_SC_Sl_StockBill", schema = "")
@SuppressWarnings("serial")
public class TScSlStockBillExcelEntity implements java.io.Serializable {
    /**主键*/
    private String id;

    private String tranType;
    /**单据编号*/
    @Excel(name="单据编号")
    private String billNo;
    /**单据日期*/
    @Excel(name="单据日期",format = "yyyy-MM-dd")
    private java.util.Date date;

    /**供应商*/
    private String itemId;

    @ExcelEntity(id="id",name="name")
    private TScSupplierForPoOrderEntity supplierEntity;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "itemId",referencedColumnName = "ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public TScSupplierForPoOrderEntity getSupplierEntity() {
        return supplierEntity;
    }

    public void setSupplierEntity(TScSupplierForPoOrderEntity supplierEntity) {
        this.supplierEntity = supplierEntity;
    }


    @Transient
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**经办人*/
    private String empId;

    @ExcelEntity(id="id",name="name")
    private TScEmpForPoOrderEntity empEntity;

    @Transient
    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "empId",referencedColumnName = "ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public TScEmpForPoOrderEntity getEmpEntity() {
        return empEntity;
    }

    public void setEmpEntity(TScEmpForPoOrderEntity empEntity) {
        this.empEntity = empEntity;
    }

    /**部门*/
    private String deptId;

    @ExcelEntity(id="id",name="name")
    private TScDepartmentForPoOrderEntity deptEntity;

    @Transient
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "deptId",referencedColumnName = "ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public TScDepartmentForPoOrderEntity getDeptEntity() {
        return deptEntity;
    }

    public void setDeptEntity(TScDepartmentForPoOrderEntity deptEntity) {
        this.deptEntity = deptEntity;
    }

    /**仓库*/
    private String stockId;

    @ExcelEntity(id="id",name="name")
    private TScStockForPoOrderEntity stockEntity;

    @Transient
    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "stockId",referencedColumnName = "ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public TScStockForPoOrderEntity getStockEntity() {
        return stockEntity;
    }

    public void setStockEntity(TScStockForPoOrderEntity stockEntity) {
        this.stockEntity = stockEntity;
    }

    /**应付金额*/
    @Excel(name="应付金额")
    private Double allAmount;

    /**优惠金额*/
    @Excel(name="优惠金额")
    private Double rebateAmount;

    @Excel(name="本次付款")
    private Double curPayAmount;
    /**结算账户*/
    @Excel(name="重量")
    private Double weight;

    @ExcelEntity(id="id",name="name")
    private TScSettleacctForPoStockBillEntity accountEntity;

    @Column(name="curPayAmount",nullable = true)
    public Double getCurPayAmount() {
        return curPayAmount;
    }

    public void setCurPayAmount(Double curPayAmount) {
        this.curPayAmount = curPayAmount;
    }


    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "accountId",referencedColumnName = "ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public TScSettleacctForPoStockBillEntity getAccountEntity() {
        return accountEntity;
    }

    public void setAccountEntity(TScSettleacctForPoStockBillEntity accountEntity) {
        this.accountEntity = accountEntity;
    }

    @ExcelCollection(name="采购入库单明细表")
    List<TScSlStockBillentryExcelEntity> mainToEntry;

    @OneToMany(mappedBy = "entryToMain",cascade = CascadeType.ALL)
    public List<TScSlStockBillentryExcelEntity> getMainToEntry() {
        return mainToEntry;
    }

    public void setMainToEntry(List<TScSlStockBillentryExcelEntity> mainToEntry) {
        this.mainToEntry = mainToEntry;
    }


    /**摘要*/
    @Excel(name="摘要")
    private String explanation;
    /**联系人*/
    @Excel(name="联系人")
    private String contact;
    /**手机号码*/
    @Excel(name="手机号码")
    private String mobilePhone;
    /**电话*/
    @Excel(name="电话")
    private String phone;
    /**传真*/
    @Excel(name="传真")
    private String fax;
    /**联系地址*/
    @Excel(name="联系地址")
    private String address;

    /**制单人*/
   // @Excel(name="制单人")
    private String billerId;
    @ExcelEntity(id="id",name="name")
    private TSBaseUserForPoOrder biller;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "billerId",referencedColumnName = "ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public TSBaseUserForPoOrder getBiller() {
        return biller;
    }

    public void setBiller(TSBaseUserForPoOrder biller) {
        this.biller = biller;
    }

    /**审核人*/
    private String checkerId;


    /**审核日期*/
    private String checkDate;
    /**审核状态*/
    private Integer checkState;

    @Excel(name="审核人")
    private String auditorName;
    @Excel(name="审核时间")
    private String auditDate;
    @Excel(name="审核状态",dicCode = "SC_AUDIT_STATUS")
    private Integer state;
    /**关闭标记*/
    //@Excel(name="是否关闭",dicCode = "sf_01")
   // private Integer closed;
    /**自动关闭标记*/
   // private Integer autoFlag;
    /**作废标记*/
    @Excel(name="是否作废",dicCode = "sf_01")
    private Integer cancellation;

    /**分支机构*/
    //@Excel(name="分支机构")
    private String sonId;
    @Excel(name="分支机构")
    private String sonName;

//    @ExcelEntity(id="id",name="name")
//    private TSDepart sonInfo;
//
//    @OneToOne(cascade = CascadeType.REFRESH)
//    @JoinColumn(name = "sonId",referencedColumnName = "ID")
//    @NotFound(action = NotFoundAction.IGNORE)
//    public TSDepart getSonInfo() {
//        return sonInfo;
//    }
//
//    public void setSonInfo(TSDepart sonInfo) {
//        this.sonInfo = sonInfo;
//    }



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
     *方法: 取得java.util.Date
     *@return: java.util.Date  单据日期
     */

    @Column(name ="DATE",nullable=true,length=20)
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
     *@return: java.lang.String  供应商
     */


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
     *方法: 取得java.lang.Double
     *@return: java.lang.Double  优惠金额
     */

    @Column(name ="REBATEAMOUNT",nullable=true,scale=10,length=20)
    public Double getRebateAmount(){
        return this.rebateAmount;
    }

    /**
     *方法: 设置java.lang.Double
     *@param: java.lang.Double  优惠金额
     */
    public void setRebateAmount(Double rebateAmount){
        this.rebateAmount = rebateAmount;
    }


    /**
     *方法: 取得java.lang.Double
     *@return: java.lang.Double  应付金额
     */

    @Column(name ="ALLAMOUNT",nullable=true,scale=10,length=20)
    public Double getAllAmount(){
        return this.allAmount;
    }

    /**
     *方法: 设置java.lang.Double
     *@param: java.lang.Double  应付金额
     */
    public void setAllAmount(Double allAmount){
        this.allAmount = allAmount;
    }


    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  审核人
     */

    @Column(name ="CHECKERID",nullable=true,length=32)
    public String getCheckerId(){
        return this.checkerId;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  审核人
     */
    public void setCheckerId(String checkerId){
        this.checkerId = checkerId;
    }

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  制单人
     */

    //@Column(name ="BILLERID",nullable=true,length=32)
    @Transient
    public String getBillerId(){
        return this.billerId;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  制单人
     */
    public void setBillerId(String billerId){
        this.billerId = billerId;
    }

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  审核日期
     */

    @Column(name ="CHECKDATE",nullable=true,length=32)
    public String getCheckDate(){
        return this.checkDate;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  审核日期
     */
    public void setCheckDate(String checkDate){
        this.checkDate = checkDate;
    }

    /**
     *方法: 取得java.lang.Integer
     *@return: java.lang.Integer  审核状态
     */

    @Column(name ="CHECKSTATE",nullable=true,length=2)
    public Integer getCheckState(){
        return this.checkState;
    }

    /**
     *方法: 设置java.lang.Integer
     *@param: java.lang.Integer  审核状态
     */
    public void setCheckState(Integer checkState){
        this.checkState = checkState;
    }

    /**
     *方法: 取得java.lang.Integer
     *@return: java.lang.Integer  关闭标记
     */

   // @Column(name ="CLOSED",nullable=true,length=1)
  //  public Integer getClosed(){
   //     return this.closed;
  //  }

    /**
     *方法: 设置java.lang.Integer
     *@param: java.lang.Integer  关闭标记
     */
  //  public void setClosed(Integer closed){
 //       this.closed = closed;
 //   }

    /**
     *方法: 取得java.lang.Integer
     *@return: java.lang.Integer  自动关闭标记
     */

  //  @Column(name ="AUTOFLAG",nullable=true,length=1)
 //   public Integer getAutoFlag(){
 //       return this.autoFlag;
 //   }

    /**
     *方法: 设置java.lang.Integer
     *@param: java.lang.Integer  自动关闭标记
     */
  //  public void setAutoFlag(Integer autoFlag){
  //      this.autoFlag = autoFlag;
  //  }

    /**
     *方法: 取得java.lang.Integer
     *@return: java.lang.Integer  作废标记
     */

    @Column(name ="CANCELLATION",nullable=true,length=1)
    public Integer getCancellation(){
        return this.cancellation;
    }

    /**
     *方法: 设置java.lang.Integer
     *@param: java.lang.Integer  作废标记
     */
    public void setCancellation(Integer cancellation){
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

    @Column(name = "sonId",nullable = true)
    public String getSonId(){
        return this.sonId;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  分支机构
     */
    public void setSonId(String sonId){
        this.sonId = sonId;
    }

    @Transient
    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }

    @Transient
    public String getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(String auditDate) {
        this.auditDate = auditDate;
    }

    @Transient
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    @Column(name = "tranType",nullable = true)
    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    @Column(name = "weight",nullable = true)
    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    @Transient
    public String getSonName() {
        return sonName;
    }

    public void setSonName(String sonName) {
        this.sonName = sonName;
    }
}
