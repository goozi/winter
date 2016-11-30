package com.qihang.buss.sc.financing.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by LenovoM4550 on 2016-09-07.
 * 费用申报单视图
 */
@Entity
@Table(name = "v_sc_rp_expensesapply", schema = "")
@SuppressWarnings("serial")
public class TScRpExpensesapplyViewEntity implements java.io.Serializable {
    /**调账主键ID**/
    private String id;
    /**创建人**/
    private String createName;
    /**创建人ID**/
    private String createBy;
    /**创建时间**/
    private Date createDate;
    /**更新人ID**/
    private String updateBy;
    /**更新日期**/
    private Date updateDate;
    /**更新人姓名**/
    private String updateName;
    /**单据编号**/
    @Excel(name="单据编号",mergeVertical=true)
    private String billNo;
    /**单据日期**/
    @Excel(name="单据日期",mergeVertical=true,exportFormat="yyyy-MM-dd",mergeRely={0})
    private Date date;
    /**核算类别**/
    @Excel(name="核算类别",replace = {"职员_1","部门_2"},mergeVertical=true,mergeRely={0})
    private Integer itemClassId;
    /**核算项目**/
    @Excel(name="核算项目",mergeVertical=true,mergeRely={0})
    private String itemName;
    /**经办人姓名**/
    @Excel(name="经办人",mergeVertical=true,mergeRely={0})
    private String empName;
    /**部门**/
    @Excel(name="部门",mergeVertical=true,mergeRely={0})
    private String deptName;
    /**总额**/
    @Excel(name="总额",mergeVertical=true,mergeRely={0})
    private Double allamount;
    /**分路号**/
    @Excel(name="分路号")
    private Integer findex;
    /**收支项目**/
    @Excel(name="收支项目")
    private String feeName;
    /**收支金额**/
    @Excel(name="金额")
    private Double amount;
    /***已报销金额**/
    @Excel(name="已报销金额")
    private Double haveAmount;
    /**未报销金额**/
    @Excel(name="未报销金额")
    private Double notAmount;
    /**摘要**/
    @Excel(name="摘要",mergeVertical=true,mergeRely={0})
    private String explanation;
    /**制单人**/
    @Excel(name="制单人",mergeVertical=true,mergeRely={0})
    private String billUserName;
    /**审核状态**/
    @Excel(name="审核状态",mergeVertical=true,replace={"未审核_0","审核中_1","已审核_2"},mergeRely={0})
    private Integer checkstate;
    /**审核人**/
    @Excel(name="审核人",mergeVertical=true,mergeRely={0})
    private String checkUserName;
    /**审核时间**/
    @Excel(name="审核时间",exportFormat="yyyy-MM-dd HH:mm:ss",mergeVertical=true,mergeRely={0})
    private Date checkDate;
    /***作废标记**/
    @Excel(name="作废标记",replace = {"未作废_0","已作废_1"},mergeVertical=true,mergeRely={0})
    private Integer cancellation;
    /**分支机构**/
    @Excel(name="分支机构",mergeVertical=true,mergeRely={0})
    private String departName;
    /**附表ID**/
    private String entryId;
    /**核算项目ID**/
    private String itemId;
    /**单据类型**/
    private String tranType;
    /**经办人**/
    private String empId;
    /**部门ID**/
    private String deptId;
    /**审核人ID**/
    private String checkerId;
    /**制单人ID**/
    private String billerId;
   /**分支机构ID**/
    private String sonId;
    /**主键ID**/
    private String fid;
    /**收支项目ID**/
    private String expId;
    /**报销明细备注**/
    private String note;
   /**单据名称***/
    private String billName;

    @Id
    @Column(name ="entryId",nullable=true)
    public String getEntryId() {
        return entryId;
    }

    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }

    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name ="ID",nullable=false,length=36)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name ="createName",nullable=true)
    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }
    @Column(name ="createBy",nullable=true)
    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    @Column(name ="createDate",nullable=true)
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @Column(name ="updateBy",nullable=true)
    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
    @Column(name ="updateDate",nullable=true)
    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    @Column(name ="updateName",nullable=true)
    public String getUpdateName() {
        return updateName;
    }

    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }
    @Column(name ="billNo",nullable=true)
    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }
    @Column(name ="date",nullable=true)
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    @Column(name ="empName",nullable=true)
    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }
    @Column(name ="deptName",nullable=true)
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }
    @Column(name ="allamount",nullable=true)
    public Double getAllamount() {
        return allamount;
    }

    public void setAllamount(Double allamount) {
        this.allamount = allamount;
    }
    @Column(name ="findex",nullable=true)
    public Integer getFindex() {
        return findex;
    }

    public void setFindex(Integer findex) {
        this.findex = findex;
    }
    @Column(name ="feeName",nullable=true)
    public String getFeeName() {
        return feeName;
    }

    public void setFeeName(String feeName) {
        this.feeName = feeName;
    }
    @Column(name ="amount",nullable=true)
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
    @Column(name ="explanation",nullable=true)
    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }
    @Column(name ="billUserName",nullable=true)
    public String getBillUserName() {
        return billUserName;
    }

    public void setBillUserName(String billUserName) {
        this.billUserName = billUserName;
    }
    @Column(name ="checkstate",nullable=true)
    public Integer getCheckstate() {
        return checkstate;
    }

    public void setCheckstate(Integer checkstate) {
        this.checkstate = checkstate;
    }
    @Column(name ="checkUserName",nullable=true)
    public String getCheckUserName() {
        return checkUserName;
    }

    public void setCheckUserName(String checkUserName) {
        this.checkUserName = checkUserName;
    }
    @Column(name ="checkDate",nullable=true)
    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }
    @Column(name ="cancellation",nullable=true)
    public Integer getCancellation() {
        return cancellation;
    }

    public void setCancellation(Integer cancellation) {
        this.cancellation = cancellation;
    }
    @Column(name ="departName",nullable=true)
    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    @Column(name ="haveAmount",nullable=true)
    public Double getHaveAmount() {
        return haveAmount;
    }

    public void setHaveAmount(Double haveAmount) {
        this.haveAmount = haveAmount;
    }
    @Column(name ="notAmount",nullable=true)
    public Double getNotAmount() {
        return notAmount;
    }

    public void setNotAmount(Double notAmount) {
        this.notAmount = notAmount;
    }
    @Column(name ="itemClassId",nullable=true)
    public Integer getItemClassId() {
        return itemClassId;
    }

    public void setItemClassId(Integer itemClassId) {
        this.itemClassId = itemClassId;
    }
    @Column(name ="itemName",nullable=true)
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    @Column(name ="itemId",nullable=true)
    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    @Column(name ="tranType",nullable=true)
    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }
    @Column(name ="empId",nullable=true)
    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
    @Column(name ="deptId",nullable=true)
    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }
    @Column(name ="checkerId",nullable=true)
    public String getCheckerId() {
        return checkerId;
    }

    public void setCheckerId(String checkerId) {
        this.checkerId = checkerId;
    }
    @Column(name ="billerId",nullable=true)
    public String getBillerId() {
        return billerId;
    }

    public void setBillerId(String billerId) {
        this.billerId = billerId;
    }
    @Column(name ="sonId",nullable=true)
    public String getSonId() {
        return sonId;
    }

    public void setSonId(String sonId) {
        this.sonId = sonId;
    }
    @Column(name ="fid",nullable=true)
    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }
    @Column(name ="expId",nullable=true)
    public String getExpId() {
        return expId;
    }

    public void setExpId(String expId) {
        this.expId = expId;
    }
    @Column(name ="note",nullable=true)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
    @Column(name ="billName",nullable=true)
    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
    }
}