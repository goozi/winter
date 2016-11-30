package com.qihang.buss.sc.sys.entity;

import com.qihang.winter.core.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;
import java.io.Serializable;
import java.util.Date;

/**
 * Description : [描述这个类的作用]
 *
 * @author : Zerrion
 *         CreateDate : 2016-08-22 21:07
 *         UpdateUser : [更新人]
 *         UpdateDate : [更新时间]
 *         UpdateRemark : [说明本次修改内容]
 * @version : v1.0
 */
@Entity
@Table(name = "T_S_BILL_MAX_SN", schema = "")
public class TSBillMaxSn extends IdEntity implements Serializable {
  /**
   * 账套ID
   */
  private String accountId;
  /**
   * 单据类型ID
   */
  private String billId;
  /**
   * 最大流水号
   */
  private Long maxSn;

  /**
   * 最后更新日期
   */
  private Date lastDate;
  /**
   * 乐观锁
   */
  private Integer version;
  /**
   * 子节点
   */
  private  String subNode;

  @Column(name = "ACCOUNT_ID", nullable = false, length = 36)
  public String getAccountId() {
    return accountId;
  }

  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }

  @Column(name = "BILL_ID", nullable = false, length = 36)
  public String getBillId() {
    return billId;
  }

  public void setBillId(String billId) {
    this.billId = billId;
  }

  @Column(name = "MAX_SN", nullable = false, length = 64)
  public Long getMaxSn() {
    return maxSn;
  }

  public void setMaxSn(Long maxSn) {
    this.maxSn = maxSn;
  }

  @Column(name = "VERSION", length = 32)
  @Version
  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  @Column(name = "LAST_DATE", nullable = false)
  public Date getLastDate() {
    return lastDate;
  }

  public void setLastDate(Date lastDate) {
    this.lastDate = lastDate;
  }

  @Column(name = "SUB_NODE", nullable = false, length = 36)
  public String getSubNode() {
    return subNode;
  }

  public void setSubNode(String subNode) {
    this.subNode = subNode;
  }
}
