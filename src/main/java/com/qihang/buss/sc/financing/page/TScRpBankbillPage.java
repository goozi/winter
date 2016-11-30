package com.qihang.buss.sc.financing.page;

import com.qihang.winter.poi.excel.annotation.Excel;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 银行存取款
 */
public class TScRpBankbillPage implements java.io.Serializable {
    /**单据日期*/
    @Excel(name="单据日期",format = "yyyy-MM-dd")
    private Date date;
    /**单据编号*/
    @Excel(name="单据编号")
    private String billNo ;
    /**经办人*/
    @Excel(name="经办人")
    private String empName;
    /**部门*/
    @Excel(name="部门")
    private String deptName;
    /**转出机构*/
    @Excel(name="转出机构")
    private String scsonName;
    /**转出账户*/
    @Excel(name="转出账户")
    private String paccountName;
    /**转入机构*/
    @Excel(name="转入机构")
    private String dcsonName;
    /**转入账户*/
    @Excel(name="转入账户")
    private String raccountName;
    /**金额*/
    @Excel(name="金额")
    private BigDecimal allAmount;
    /**审核人*/
    @Excel(name="审核人")
    private String checkerName;
    /**制单人*/
    @Excel(name="制单人")
    private String billerName;
    /**审核日期*/
    @Excel(name="审核日期")
    private Date checkDate ;
    /**审核状态*/
    private Integer checkState ;

    @Excel(name="审核状态")
    private String checkStateName;
    /**作废标记*/
    private Integer cancellation ;

    @Excel(name="作废标记")
    private String cancellationName;
    /**摘要*/
    @Excel(name="摘要")
    private String explanation ;
    /**分支机构*/
    @Excel(name="分支机构")
    private String sonName;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getScsonName() {
        return scsonName;
    }

    public void setScsonName(String scsonName) {
        this.scsonName = scsonName;
    }

    public String getPaccountName() {
        return paccountName;
    }

    public void setPaccountName(String paccountName) {
        this.paccountName = paccountName;
    }

    public String getDcsonName() {
        return dcsonName;
    }

    public void setDcsonName(String dcsonName) {
        this.dcsonName = dcsonName;
    }

    public String getRaccountName() {
        return raccountName;
    }

    public void setRaccountName(String raccountName) {
        this.raccountName = raccountName;
    }

    public BigDecimal getAllAmount() {
        return allAmount;
    }

    public void setAllAmount(BigDecimal allAmount) {
        this.allAmount = allAmount;
    }

    public String getCheckerName() {
        return checkerName;
    }

    public void setCheckerName(String checkerName) {
        this.checkerName = checkerName;
    }

    public String getBillerName() {
        return billerName;
    }

    public void setBillerName(String billerName) {
        this.billerName = billerName;
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Integer getCheckState() {
        return checkState;
    }

    public void setCheckState(Integer checkState) {
        this.checkState = checkState;
    }

    public Integer getCancellation() {
        return cancellation;
    }

    public void setCancellation(Integer cancellation) {
        this.cancellation = cancellation;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getSonName() {
        return sonName;
    }

    public void setSonName(String sonName) {
        this.sonName = sonName;
    }

    public String getCheckStateName() {
        return checkStateName;
    }

    public void setCheckStateName(String checkStateName) {
        this.checkStateName = checkStateName;
    }

    public String getCancellationName() {
        return cancellationName;
    }

    public void setCancellationName(String cancellationName) {
        this.cancellationName = cancellationName;
    }
}
