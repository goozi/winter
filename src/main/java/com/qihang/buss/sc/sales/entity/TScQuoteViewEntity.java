package com.qihang.buss.sc.sales.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 销售报价单视图
 * @author onlineGenerator
 * @date 2016-06-16 15:22:17
 * @version V1.0   
 *
 */
@Entity
@Table(name = "v_sc_quote", schema = "")
public class TScQuoteViewEntity implements java.io.Serializable {

	/**自动生成的主键id*/
    private String entryId;
	/**单据编号*/
	private String billNo;
	/**主表主键*/
	private String id;
	/**作废*/
	private Integer cancellation;
	/**单据日期*/
	private Date date;
	/**经办人*/
    private  String empid;
	/**部门*/
    private String deptid;
	/**生效日期*/
	private  Date inureDate;
	/**客户id*/
	//private String customerId;
	/**分录号*/
	private Integer indexNumber;
	/**商品id*/
	private String itemID;
	/**商品编号*/
	private String icitemNumber;
	/**商品名称*/
	private String icitemName;
	/**商品规格*/
	private String icitemModel;
	/**条形码*/
	private String icitemBarCode;
	/**商品关联的从表的单位id*/
	private String unitID;
	/**商品重量*/
	private  BigDecimal icitemWeight;
	/**单位id*/
	private String icitemUnitId;
	/**数量段(从)*/
	private BigDecimal begQty;
    /**数量段(至)*/
	private BigDecimal endQty;
    /**单价*/
	private BigDecimal price;
	/**成本单价*/
	private BigDecimal costPrice;
	/**毛利*/
	private BigDecimal grossAmount;
	/**毛利率*/
	private BigDecimal grossper;
	/**备注*/
	private String note;
	/**审核的状态*/
	private Integer checkState;
	private Integer status;//单据状态
	private String auditDate;//审核日期
	private String auditorName;//审核人

	private String empName;
	private String deptName;
	private String billerName;
	private String sonName;

	private String sonId;

	private String explanation;//摘要

	@Column(name="id")
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name="billno")
	public String getBillNo() {
		return billNo;
	}

	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	@Id
	@Column(name="entryId")
	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}

	@Column(name="cancellation")
	public Integer getCancellation() {
		return cancellation;
	}

	public void setCancellation(Integer cancellation) {
		this.cancellation = cancellation;
	}

	@Column(name="date")
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	@Column(name="empid")
	public String getEmpid() {
		return empid;
	}

	public void setEmpid(String empid) {
		this.empid = empid;
	}
	@Column(name="deptid")
	public String getDeptid() {
		return deptid;
	}

	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	@Column(name="inureDate")
	public Date getInureDate() {
		return inureDate;
	}

	public void setInureDate(Date inureDate) {
		this.inureDate = inureDate;
	}
//	@Column(name="customerId")
//	public String getCustomerId() {
//		return customerId;
//	}
//
//	public void setCustomerId(String customerId) {
//		this.customerId = customerId;
//	}
	@Column(name="indexNumber")
	public Integer getIndexNumber() {
		return indexNumber;
	}

	public void setIndexNumber(Integer indexNumber) {
		this.indexNumber = indexNumber;
	}
	@Column(name="itemID")
	public String getItemID() {
		return itemID;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}
	@Column(name="icitemNumber")
	public String getIcitemNumber() {
		return icitemNumber;
	}

	public void setIcitemNumber(String icitemNumber) {
		this.icitemNumber = icitemNumber;
	}
	@Column(name="icitemName")
	public String getIcitemName() {
		return icitemName;
	}

	public void setIcitemName(String icitemName) {
		this.icitemName = icitemName;
	}
	@Column(name="icitemModel")
	public String getIcitemModel() {
		return icitemModel;
	}

	public void setIcitemModel(String icitemModel) {
		this.icitemModel = icitemModel;
	}
	@Column(name="icitemBarCode")
	public String getIcitemBarCode() {
		return icitemBarCode;
	}

	public void setIcitemBarCode(String icitemBarCode) {
		this.icitemBarCode = icitemBarCode;
	}
	@Column(name="unitID")
	public String getUnitID() {
		return unitID;
	}

	public void setUnitID(String unitID) {
		this.unitID = unitID;
	}
	@Column(name="icitemUnitId")
	public String getIcitemUnitId() {
		return icitemUnitId;
	}

	public void setIcitemUnitId(String icitemUnitId) {
		this.icitemUnitId = icitemUnitId;
	}
	@Column(name="begQty")
	public BigDecimal getBegQty() {
		return begQty;
	}

	public void setBegQty(BigDecimal begQty) {
		this.begQty = begQty;
	}
	@Column(name="endQty")
	public BigDecimal getEndQty() {
		return endQty;
	}

	public void setEndQty(BigDecimal endQty) {
		this.endQty = endQty;
	}
	@Column(name="price")
	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	@Column(name="CostPrice")
	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}
	@Column(name="GrossAmount")
	public BigDecimal getGrossAmount() {
		return grossAmount;
	}

	public void setGrossAmount(BigDecimal grossAmount) {
		this.grossAmount = grossAmount;
	}
	@Column(name="Grossper")
	public BigDecimal getGrossper() {
		return grossper;
	}

	public void setGrossper(BigDecimal grossper) {
		this.grossper = grossper;
	}
	@Column(name="Note")
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	@Column(name="checkstate")
	public Integer getCheckState() {
		return checkState;
	}

	public void setCheckState(Integer checkState) {
		this.checkState = checkState;
	}

	@Column(name="icitemWeight")
	public BigDecimal getIcitemWeight() {
		return icitemWeight;
	}

	public void setIcitemWeight(BigDecimal icitemWeight) {
		this.icitemWeight = icitemWeight;
	}

	@Transient
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	@Transient
	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}
	@Transient
	public String getAuditorName() {
		return auditorName;
	}

	public void setAuditorName(String auditorName) {
		this.auditorName = auditorName;
	}

	@Column(name="empName")
	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}

	@Column(name="deptName")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name="billerName")
	public String getBillerName() {
		return billerName;
	}

	public void setBillerName(String billerName) {
		this.billerName = billerName;
	}

	@Column(name="sonName")
	public String getSonName() {
		return sonName;
	}

	public void setSonName(String sonName) {
		this.sonName = sonName;
	}

	@Column(name="sonId")
	public String getSonId() {
		return sonId;
	}

	public void setSonId(String sonId) {
		this.sonId = sonId;
	}

	@Column(name="explanation")
	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}
}
