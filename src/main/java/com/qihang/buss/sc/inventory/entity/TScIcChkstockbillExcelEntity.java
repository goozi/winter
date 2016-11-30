package com.qihang.buss.sc.inventory.entity;

import com.qihang.buss.sc.baseinfo.entity.TScDepartmentForPoOrderEntity;
import com.qihang.buss.sc.baseinfo.entity.TScEmpForPoOrderEntity;
import com.qihang.buss.sc.baseinfo.entity.TScStockForPoOrderEntity;
import com.qihang.buss.sc.sales.entity.TSBaseUserForPoOrder;
import com.qihang.winter.poi.excel.annotation.Excel;
import com.qihang.winter.poi.excel.annotation.ExcelCollection;
import com.qihang.winter.poi.excel.annotation.ExcelEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Entity
 * @Description: 盘点单
 * @date 2016-08-01 09:43:32
 */
@Entity
@Table(name = "T_SC_IC_ChkStockBill", schema = "")
@SuppressWarnings("serial")
public class TScIcChkstockbillExcelEntity implements java.io.Serializable {
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
     * 单据类型
     */
    private String tranType;
    /**
     * 单据编号
     */
    @Excel(name = "单据编号")
    private String billNo;
    /**
     * 单据日期
     */
    @Excel(name = "单据日期",format = "yyyy-MM-dd")
    private Date date;
    /**
     * 仓库
     */
    //@Excel(name = "仓库")
    private String stockId;

    @ExcelEntity(id = "id", name = "name")
    private TScStockForPoOrderEntity stockEntity;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "stockId",referencedColumnName = "ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public TScStockForPoOrderEntity getStockEntity() {
        return stockEntity;
    }

    public void setStockEntity(TScStockForPoOrderEntity stockEntity) {
        this.stockEntity = stockEntity;
    }

    /**
     * 经办人
     */
   // @Excel(name = "经办人")
    private String empId;

    @ExcelEntity(id="id",name="name")
    private TScEmpForPoOrderEntity empEntity;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "empId",referencedColumnName = "ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public TScEmpForPoOrderEntity getEmpEntity() {
        return empEntity;
    }

    public void setEmpEntity(TScEmpForPoOrderEntity empEntity) {
        this.empEntity = empEntity;
    }

    /**
     * 部门
     */
    //@Excel(name = "部门")
    private String deptId;

    @ExcelEntity(id="id",name="name")
    private TScDepartmentForPoOrderEntity deptEntity;

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "deptId",referencedColumnName = "ID")
    @NotFound(action = NotFoundAction.IGNORE)
    public TScDepartmentForPoOrderEntity getDeptEntity() {
        return deptEntity;
    }

    public void setDeptEntity(TScDepartmentForPoOrderEntity deptEntity) {
        this.deptEntity = deptEntity;
    }
    /**
     * 盘点日期
     */
    @Excel(name = "盘点日期",format = "yyyy-MM-dd")
    private Date pdDate;
    /**
     * 盘点类型
     */
    @Excel(name = "盘点类型",dicCode = "SC_ChkType")
    private String chkType;

    @ExcelCollection(name="盘点单明细表")
    Set<TScIcChkstockbillentryExcelEntity> mainToEntry;

    @OneToMany(mappedBy = "entryToMain",cascade = CascadeType.ALL)
    @OrderBy("findex asc")
    public Set<TScIcChkstockbillentryExcelEntity> getMainToEntry() {
        return mainToEntry;
    }

    public void setMainToEntry(Set<TScIcChkstockbillentryExcelEntity> mainToEntry) {
        this.mainToEntry = mainToEntry;
    }
    /**
     * 制单人
     */
    //@Excel(name = "制单人")
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
    /**
     * 审核人
     */
    //@Excel(name = "审核人")
    private String checkerId;
    @Excel(name = "审核人")
    private String auditorName;
    /**
     * 审核状态
     */
    @Excel(name = "审核状态",replace = {"未审核_0","审核中_1","已审核_2"})
    private String checkState;
    /**
     * 审核日期
     */
    @Excel(name = "审核日期",format = "yyyy-MM-dd")
    private String checkDate;
    /**
     * 作废标记
     */
    private Integer cancellation;
    /**
     * 摘要
     */
    @Excel(name = "摘要")
    private String explanation;
    /**
     * 分支机构
     */
    private String sonId;

    private Integer isAuto;//自动标记

    private String stockName;
    private String empName;
    private String deptName;
    @Excel(name = "分支机构")
    private String sonName;
    private String billerName;

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
     * @return: java.lang.String  单据类型
     */

    @Column(name = "TRANTYPE", nullable = true, length = 11)
    public String getTranType() {
        return this.tranType;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  单据类型
     */
    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  单据编号
     */

    @Column(name = "BILLNO", nullable = true, length = 50)
    public String getBillNo() {
        return this.billNo;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  单据编号
     */
    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date  单据日期
     */

    @Column(name = "DATE", nullable = true, length = 20)
    public Date getDate() {
        return this.date;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date  单据日期
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  仓库
     */

    //@Column(name = "STOCKID", nullable = true, length = 32)
    @Transient
    public String getStockId() {
        return this.stockId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  仓库
     */
    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  经办人
     */

   // @Column(name = "EMPID", nullable = true, length = 32)
    @Transient
    public String getEmpId() {
        return this.empId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  经办人
     */
    public void setEmpId(String empId) {
        this.empId = empId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  部门
     */

    //@Column(name = "DEPTID", nullable = true, length = 32)
    @Transient
    public String getDeptId() {
        return this.deptId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  部门
     */
    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    /**
     * 方法: 取得java.util.Date
     *
     * @return: java.util.Date  盘点日期
     */

    @Column(name = "PDDATE", nullable = true, length = 32)
    public Date getPdDate() {
        return this.pdDate;
    }

    /**
     * 方法: 设置java.util.Date
     *
     * @param: java.util.Date  盘点日期
     */
    public void setPdDate(Date pdDate) {
        this.pdDate = pdDate;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  盘点类型
     */

    @Column(name = "CHKTYPE", nullable = true, length = 32)
    public String getChkType() {
        return this.chkType;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  盘点类型
     */
    public void setChkType(String chkType) {
        this.chkType = chkType;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  制单人
     */

    //@Column(name = "BILLERID", nullable = true, length = 32)
    @Transient
    public String getBillerId() {
        return this.billerId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  制单人
     */
    public void setBillerId(String billerId) {
        this.billerId = billerId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  审核人
     */

    @Column(name = "CHECKERID", nullable = true, length = 32)
    public String getCheckerId() {
        return this.checkerId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  审核人
     */
    public void setCheckerId(String checkerId) {
        this.checkerId = checkerId;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  审核状态
     */

    @Column(name = "CHECKSTATE", nullable = true, length = 32)
    public String getCheckState() {
        return this.checkState;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  审核状态
     */
    public void setCheckState(String checkState) {
        this.checkState = checkState;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  审核日期
     */

    @Column(name = "CHECKDATE", nullable = true, length = 32)
    public String getCheckDate() {
        return this.checkDate;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  审核日期
     */
    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    /**
     * 方法: 取得java.lang.Integer
     *
     * @return: java.lang.Integer  作废标记
     */

    @Column(name = "CANCELLATION", nullable = true, length = 1)
    public Integer getCancellation() {
        return this.cancellation;
    }

    /**
     * 方法: 设置java.lang.Integer
     *
     * @param: java.lang.Integer  作废标记
     */
    public void setCancellation(Integer cancellation) {
        this.cancellation = cancellation;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  摘要
     */

    @Column(name = "EXPLANATION", nullable = true, length = 255)
    public String getExplanation() {
        return this.explanation;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  摘要
     */
    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    /**
     * 方法: 取得java.lang.String
     *
     * @return: java.lang.String  分支机构
     */

    @Column(name = "SONID", nullable = true, length = 32)
    public String getSonId() {
        return this.sonId;
    }

    /**
     * 方法: 设置java.lang.String
     *
     * @param: java.lang.String  分支机构
     */
    public void setSonId(String sonId) {
        this.sonId = sonId;
    }

    @Transient
    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    @Transient
    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    @Transient
    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    @Transient
    public String getSonName() {
        return sonName;
    }

    public void setSonName(String sonName) {
        this.sonName = sonName;
    }

    @Transient
    public String getBillerName() {
        return billerName;
    }

    public void setBillerName(String billerName) {
        this.billerName = billerName;
    }

    @Column(name = "isAuto", nullable = true)
    public Integer getIsAuto() {
        return isAuto;
    }

    public void setIsAuto(Integer isAuto) {
        this.isAuto = isAuto;
    }

    @Transient
    public String getAuditorName() {
        return auditorName;
    }

    public void setAuditorName(String auditorName) {
        this.auditorName = auditorName;
    }
}
