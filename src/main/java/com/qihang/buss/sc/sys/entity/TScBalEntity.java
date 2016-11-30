package com.qihang.buss.sc.sys.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import com.qihang.winter.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 应收应付结账表
 * @author onlineGenerator
 * @date 2016-08-30 16:17:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_BAL", schema = "")
@SuppressWarnings("serial")
public class TScBalEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**创建人名称*/
	private java.lang.String createName;
	/**创建人登录名称*/
	private java.lang.String createBy;
	/**创建日期*/
	private java.util.Date createDate;
	/**更新人名称*/
	private java.lang.String updateName;
	/**更新人登录名称*/
	private java.lang.String updateBy;
	/**更新日期*/
	private java.util.Date updateDate;
	/**年份*/
	@Excel(name="年份")
	private java.lang.Integer year;
	/**月份*/
	@Excel(name="月份")
	private java.lang.Integer period;
	/**商品主键*/
	@Excel(name="商品主键")
	private java.lang.String itemID;
	/**仓库主键*/
	@Excel(name="仓库主键")
	private java.lang.String stockID;
	/**批号*/
	@Excel(name="批号")
	private java.lang.String batchNo;
	/**期初数量*/
	@Excel(name="期初数量")
	private java.math.BigDecimal begQty;
	/**期初辅助数量*/
	@Excel(name="期初辅助数量")
	private java.math.BigDecimal secBegQty;
	/**本期收入数量*/
	@Excel(name="本期收入数量")
	private java.math.BigDecimal receive;
	/**本期发出数量*/
	@Excel(name="本期发出数量")
	private java.math.BigDecimal send;
	/**本年累计收入数量*/
	@Excel(name="本年累计收入数量")
	private java.math.BigDecimal ytdReceive;
	/**本年累计发出数量*/
	@Excel(name="本年累计发出数量")
	private java.math.BigDecimal ytdSend;
	/**本年累计收入辅助数量*/
	@Excel(name="本年累计收入辅助数量")
	private java.math.BigDecimal secYtdReceive;
	/**本年累计发出辅助数量*/
	@Excel(name="本年累计发出辅助数量")
	private java.math.BigDecimal secYtdSend;
	/**本期结存数量*/
	@Excel(name="本期结存数量")
	private java.math.BigDecimal endQty;
	/**本期结存辅助数量*/
	@Excel(name="本期结存辅助数量")
	private java.math.BigDecimal secEndQty;
	/**期初金额*/
	@Excel(name="期初金额")
	private java.math.BigDecimal begBal;
	/**本期收入金额*/
	@Excel(name="本期收入金额")
	private java.math.BigDecimal debit;
	/**本期发出金额*/
	@Excel(name="本期发出金额")
	private java.math.BigDecimal credit;
	/**本年累计收入金额*/
	@Excel(name="本年累计收入金额")
	private java.math.BigDecimal ytdDebit;
	/**本年累计发出金额*/
	@Excel(name="本年累计发出金额")
	private java.math.BigDecimal ytdCredit;
	/**期末结存金额*/
	@Excel(name="期末结存金额")
	private java.math.BigDecimal endBal;
	/**机构ID*/
	@Excel(name="机构ID")
	private java.lang.String sonID;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
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
	@Column(name ="CREATE_DATE",nullable=true,length=20)
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
	@Column(name ="UPDATE_DATE",nullable=true,length=20)
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  年份
	 */
	@Column(name ="YEAR",nullable=true,length=32)
	public java.lang.Integer getYear(){
		return this.year;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  年份
	 */
	public void setYear(java.lang.Integer year){
		this.year = year;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  月份
	 */
	@Column(name ="PERIOD",nullable=true,length=32)
	public java.lang.Integer getPeriod(){
		return this.period;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  月份
	 */
	public void setPeriod(java.lang.Integer period){
		this.period = period;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品主键
	 */
	@Column(name ="ITEMID",nullable=true,length=32)
	public java.lang.String getItemID(){
		return this.itemID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品主键
	 */
	public void setItemID(java.lang.String itemID){
		this.itemID = itemID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库主键
	 */
	@Column(name ="STOCKID",nullable=true,length=32)
	public java.lang.String getStockID(){
		return this.stockID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库主键
	 */
	public void setStockID(java.lang.String stockID){
		this.stockID = stockID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  批号
	 */
	@Column(name ="BATCHNO",nullable=true,length=80)
	public java.lang.String getBatchNo(){
		return this.batchNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  批号
	 */
	public void setBatchNo(java.lang.String batchNo){
		this.batchNo = batchNo;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  期初数量
	 */
	@Column(name ="BEGQTY",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getBegQty(){
		return this.begQty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  期初数量
	 */
	public void setBegQty(java.math.BigDecimal begQty){
		this.begQty = begQty;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  期初辅助数量
	 */
	@Column(name ="SECBEGQTY",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getSecBegQty(){
		return this.secBegQty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  期初辅助数量
	 */
	public void setSecBegQty(java.math.BigDecimal secBegQty){
		this.secBegQty = secBegQty;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本期收入数量
	 */
	@Column(name ="RECEIVE",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getReceive(){
		return this.receive;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本期收入数量
	 */
	public void setReceive(java.math.BigDecimal receive){
		this.receive = receive;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本期发出数量
	 */
	@Column(name ="SEND",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getSend(){
		return this.send;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本期发出数量
	 */
	public void setSend(java.math.BigDecimal send){
		this.send = send;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本年累计收入数量
	 */
	@Column(name ="YTDRECEIVE",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getYtdReceive(){
		return this.ytdReceive;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本年累计收入数量
	 */
	public void setYtdReceive(java.math.BigDecimal ytdReceive){
		this.ytdReceive = ytdReceive;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本年累计发出数量
	 */
	@Column(name ="YTDSEND",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getYtdSend(){
		return this.ytdSend;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本年累计发出数量
	 */
	public void setYtdSend(java.math.BigDecimal ytdSend){
		this.ytdSend = ytdSend;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本年累计收入辅助数量
	 */
	@Column(name ="SECYTDRECEIVE",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getSecYtdReceive(){
		return this.secYtdReceive;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本年累计收入辅助数量
	 */
	public void setSecYtdReceive(java.math.BigDecimal secYtdReceive){
		this.secYtdReceive = secYtdReceive;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本年累计发出辅助数量
	 */
	@Column(name ="SECYTDSEND",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getSecYtdSend(){
		return this.secYtdSend;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本年累计发出辅助数量
	 */
	public void setSecYtdSend(java.math.BigDecimal secYtdSend){
		this.secYtdSend = secYtdSend;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本期结存数量
	 */
	@Column(name ="ENDQTY",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getEndQty(){
		return this.endQty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本期结存数量
	 */
	public void setEndQty(java.math.BigDecimal endQty){
		this.endQty = endQty;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本期结存辅助数量
	 */
	@Column(name ="SECENDQTY",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getSecEndQty(){
		return this.secEndQty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本期结存辅助数量
	 */
	public void setSecEndQty(java.math.BigDecimal secEndQty){
		this.secEndQty = secEndQty;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  期初金额
	 */
	@Column(name ="BEGBAL",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getBegBal(){
		return this.begBal;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  期初金额
	 */
	public void setBegBal(java.math.BigDecimal begBal){
		this.begBal = begBal;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本期收入金额
	 */
	@Column(name ="DEBIT",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getDebit(){
		return this.debit;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本期收入金额
	 */
	public void setDebit(java.math.BigDecimal debit){
		this.debit = debit;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本期发出金额
	 */
	@Column(name ="CREDIT",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getCredit(){
		return this.credit;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本期发出金额
	 */
	public void setCredit(java.math.BigDecimal credit){
		this.credit = credit;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本年累计收入金额
	 */
	@Column(name ="YTDDEBIT",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getYtdDebit(){
		return this.ytdDebit;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本年累计收入金额
	 */
	public void setYtdDebit(java.math.BigDecimal ytdDebit){
		this.ytdDebit = ytdDebit;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本年累计发出金额
	 */
	@Column(name ="YTDCREDIT",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getYtdCredit(){
		return this.ytdCredit;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本年累计发出金额
	 */
	public void setYtdCredit(java.math.BigDecimal ytdCredit){
		this.ytdCredit = ytdCredit;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  期末结存金额
	 */
	@Column(name ="ENDBAL",nullable=true,scale=10,length=20)
	public java.math.BigDecimal getEndBal(){
		return this.endBal;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  期末结存金额
	 */
	public void setEndBal(java.math.BigDecimal endBal){
		this.endBal = endBal;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  机构ID
	 */
	@Column(name ="SONID",nullable=true,length=32)
	public java.lang.String getSonID(){
		return this.sonID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  机构ID
	 */
	public void setSonID(java.lang.String sonID){
		this.sonID = sonID;
	}
}
