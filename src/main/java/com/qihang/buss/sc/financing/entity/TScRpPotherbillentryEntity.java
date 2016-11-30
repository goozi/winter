package com.qihang.buss.sc.financing.entity;

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
 * @Description: 费用开支单明细
 * @author onlineGenerator
 * @date 2016-09-08 10:04:06
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_sc_rp_potherbillentry", schema = "")
@SuppressWarnings("serial")
public class TScRpPotherbillentryEntity implements java.io.Serializable {
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
	/**附表ID*/
	@Excel(name="附表ID")
	private String fid;
	/**分录号*/
	@Excel(name="分录号")
	private Integer findex;
	/**收支项目*/
	@Excel(name="收支项目")
	private String expId;
	/**源单类型*/
	@Excel(name="源单类型")
	private String classIdSrc;
	/**源单主键*/
	@Excel(name="源单主键")
	private String interIdSrc;
	/**源单编号*/
	@Excel(name="源单编号")
	private String billNoSrc;
	/**源单分录主键*/
	@Excel(name="源单分录主键")
	private String fidSrc;
	/**源单已报销金额*/
	@Excel(name="源单已报销金额")
	private BigDecimal haveAmountSrc;
	/**源单未报销金额*/
	@Excel(name="源单未报销金额")
	private BigDecimal notAmountSrc;
	/**本次支出*/
	@Excel(name="本次支出")
	private BigDecimal amount;
	/**备注*/
	@Excel(name="备注")
	private String note;

	private String expName;
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
	 *@return: java.lang.String  附表ID
	 */
	@Column(name ="FID",nullable=true,length=32)
	public String getFid(){
		return this.fid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  附表ID
	 */
	public void setFid(String fid){
		this.fid = fid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  分录号
	 */
	@Column(name ="FINDEX",nullable=true,length=11)
	public Integer getFindex(){
		return this.findex;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  分录号
	 */
	public void setFindex(Integer findex){
		this.findex = findex;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收支项目
	 */
	@Column(name ="EXPID",nullable=true,length=32)
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单类型
	 */
	@Column(name ="CLASSIDSRC",nullable=true,length=32)
	public String getClassIdSrc(){
		return this.classIdSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单类型
	 */
	public void setClassIdSrc(String classIdSrc){
		this.classIdSrc = classIdSrc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单主键
	 */
	@Column(name ="INTERIDSRC",nullable=true,length=32)
	public String getInterIdSrc(){
		return this.interIdSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单主键
	 */
	public void setInterIdSrc(String interIdSrc){
		this.interIdSrc = interIdSrc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  源单编号
	 */
	@Column(name ="BILLNOSRC",nullable=true,length=32)
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
	 *@return: java.lang.String  源单分录主键
	 */
	@Column(name ="FIDSRC",nullable=true,length=32)
	public String getFidSrc(){
		return this.fidSrc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  源单分录主键
	 */
	public void setFidSrc(String fidSrc){
		this.fidSrc = fidSrc;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  源单已报销金额
	 */
	@Column(name ="HAVEAMOUNTSRC",nullable=true,scale=2,length=32)
	public BigDecimal getHaveAmountSrc(){
		return this.haveAmountSrc;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  源单已报销金额
	 */
	public void setHaveAmountSrc(BigDecimal haveAmountSrc){
		this.haveAmountSrc = haveAmountSrc;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  源单未报销金额
	 */
	@Column(name ="NOTAMOUNTSRC",nullable=true,scale=2,length=32)
	public BigDecimal getNotAmountSrc(){
		return this.notAmountSrc;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  源单未报销金额
	 */
	public void setNotAmountSrc(BigDecimal notAmountSrc){
		this.notAmountSrc = notAmountSrc;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本次支出
	 */
	@Column(name ="AMOUNT",nullable=true,scale=2,length=32)
	public BigDecimal getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本次支出
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
