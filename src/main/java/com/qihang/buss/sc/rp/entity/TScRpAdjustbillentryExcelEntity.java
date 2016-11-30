package com.qihang.buss.sc.rp.entity;

import com.qihang.buss.sc.baseinfo.entity.TScFeeEntity;
import com.qihang.buss.sc.baseinfo.entity.TScFeeForRpEntity;
import com.qihang.winter.poi.excel.annotation.Excel;
import com.qihang.winter.poi.excel.annotation.ExcelEntity;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 应收调账从表
 * @author onlineGenerator
 * @date 2016-08-24 14:44:39
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_RP_AdjustBillEntry", schema = "")
@SuppressWarnings("serial")
public class TScRpAdjustbillentryExcelEntity implements java.io.Serializable {
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
	/**父id*/
	private String fid;
	/**分路号*/
	@Excel(name = "分录号")
	private Integer findex;
	/**收支项目*/
	private String expId;

	@ExcelEntity(id="id",name="name")
	private TScFeeForRpEntity feeEntity;

	@OneToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "expId",referencedColumnName = "ID")
	@NotFound(action = NotFoundAction.IGNORE)
	public TScFeeForRpEntity getFeeEntity() {
		return feeEntity;
	}

	public void setFeeEntity(TScFeeForRpEntity feeEntity) {
		this.feeEntity = feeEntity;
	}

	/**金额*/
	@Excel(name="金额")
	private BigDecimal amount;
	/**备注*/
	@Excel(name="备注")
	private String note;

	private String expName;

	private TScRpAdjustbillExcelEntity entryToMain;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "fid",referencedColumnName = "ID")
	public TScRpAdjustbillExcelEntity getEntryToMain() {
		return entryToMain;
	}

	public void setEntryToMain(TScRpAdjustbillExcelEntity entryToMain) {
		this.entryToMain = entryToMain;
	}

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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  父id
	 */
	//@Column(name ="FID",nullable=true,length=32)
	@Transient
	public String getFid(){
		return this.fid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  父id
	 */
	public void setFid(String fid){
		this.fid = fid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  分路号
	 */
	@Column(name ="FINDEX",nullable=true,length=10)
	public Integer getFindex(){
		return this.findex;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  分路号
	 */
	public void setFindex(Integer findex){
		this.findex = findex;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收支项目
	 */
	//@Column(name ="EXPID",nullable=true,length=32)
	@Transient
	public String getExpId(){
		return this.expId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收支项目
	 */
	public void setExpId(String expId){
		this.expId = expId;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  金额
	 */
	@Column(name ="AMOUNT",nullable=true,scale=10,length=20)
	public BigDecimal getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  金额
	 */
	public void setAmount(BigDecimal amount){
		this.amount = amount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="NOTE",nullable=true,length=255)
	public String getNote(){
		return this.note;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setNote(String note){
		this.note = note;
	}

	@Transient
	public String getExpName() {
		return expName;
	}

	public void setExpName(String expName) {
		this.expName = expName;
	}
}
