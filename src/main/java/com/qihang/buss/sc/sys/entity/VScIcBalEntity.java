package com.qihang.buss.sc.sys.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 存货结账视图
 * @author onlineGenerator
 * @date 2016-09-17 15:14:23
 * @version V1.0   
 *
 */
@Entity
@Table(name = "V_SC_IC_Bal", schema = "")
@SuppressWarnings("serial")
public class VScIcBalEntity implements java.io.Serializable {
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
	/**年份*/
	private String year;
	/**月份*/
	private String period;
	/**商品主键*/
	@Excel(name="商品主键")
	private String itemID;
	/**仓库主键*/
	@Excel(name="仓库主键")
	private String stockID;
	/**批号*/
	@Excel(name="批号")
	private String batchNo;
	/**期初数量(上一期期末)*/
	@Excel(name="期初数量(上一期期末)")
	private BigDecimal begQty;
	/**期初辅助数量*/
	@Excel(name="期初辅助数量")
	private BigDecimal secBegQty;
	/**本期收入数量*/
	@Excel(name="本期收入数量")
	private BigDecimal receive;
	/**本期收入辅助数量*/
	@Excel(name="本期收入辅助数量")
	private BigDecimal secReceive;
	/**本期发出数量*/
	@Excel(name="本期发出数量")
	private BigDecimal send;
	/**本期发出数量*/
	@Excel(name="本期发出辅助数量")
	private BigDecimal secSend;
	/**本年累计收入数量*/
	@Excel(name="本年累计收入数量")
	private BigDecimal ytdReceive;
	/**本年累计发出数量*/
	@Excel(name="本年累计发出数量")
	private BigDecimal ytdSend;
	/**本年累计收入辅助数量*/
	@Excel(name="本年累计收入辅助数量")
	private BigDecimal secYtdReceive;
	/**本年累计发出辅助数量*/
	@Excel(name="本年累计发出辅助数量")
	private BigDecimal secYtdSend;
	/**本期结存数量=期初金额+本期收入-本期发出*/
	@Excel(name="本期结存数量=期初金额+本期收入-本期发出")
	private BigDecimal endQty;
	/**本期结存辅助数量*/
	@Excel(name="本期结存辅助数量")
	private BigDecimal secEndQty;
	/**期初金额*/
	@Excel(name="期初金额")
	private BigDecimal begBal;
	/**本期收入金额*/
	@Excel(name="本期收入金额")
	private BigDecimal debit;
	/**本期发出金额*/
	@Excel(name="本期发出金额")
	private BigDecimal credit;
	/**本年累计收入金额*/
	@Excel(name="本年累计收入金额")
	private BigDecimal ytdDebit;
	/**本年累计发出金额*/
	@Excel(name="本年累计发出金额")
	private BigDecimal ytdCredit;
	/**期末结存金额=期初金额+本期收入-本期发出*/
	@Excel(name="期末结存金额=期初金额+本期收入-本期发出")
	private BigDecimal endBal;
	/**机构ID*/
	private String sonID;
	/**父ID*/
	private String fid;
	/**商品名称*/
	private String itemname;
	/**仓库名称*/
	private String stockname;
	/**分支机构名称*/
	private String departname;

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
	 *@return: java.lang.String  年份
	 */
	@Column(name ="YEAR",nullable=true,length=32)
	public String getYear(){
		return this.year;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  年份
	 */
	public void setYear(String year){
		this.year = year;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  月份
	 */
	@Column(name ="PERIOD",nullable=true,length=32)
	public String getPeriod(){
		return this.period;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  月份
	 */
	public void setPeriod(String period){
		this.period = period;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商品主键
	 */
	@Column(name ="ITEMID",nullable=true,length=32)
	public String getItemID(){
		return this.itemID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品主键
	 */
	public void setItemID(String itemID){
		this.itemID = itemID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  仓库主键
	 */
	@Column(name ="STOCKID",nullable=true,length=32)
	public String getStockID(){
		return this.stockID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库主键
	 */
	public void setStockID(String stockID){
		this.stockID = stockID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  批号
	 */
	@Column(name ="BATCHNO",nullable=true,length=32)
	public String getBatchNo(){
		return this.batchNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  批号
	 */
	public void setBatchNo(String batchNo){
		this.batchNo = batchNo;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  期初数量(上一期期末)
	 */
	@Column(name ="BEGQTY",nullable=true,length=32)
	public BigDecimal getBegQty(){
		return this.begQty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  期初数量(上一期期末)
	 */
	public void setBegQty(BigDecimal begQty){
		this.begQty = begQty;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  期初辅助数量
	 */
	@Column(name ="SECBEGQTY",nullable=true,length=32)
	public BigDecimal getSecBegQty(){
		return this.secBegQty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  期初辅助数量
	 */
	public void setSecBegQty(BigDecimal secBegQty){
		this.secBegQty = secBegQty;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本期收入数量
	 */
	@Column(name ="RECEIVE",nullable=true,length=32)
	public BigDecimal getReceive(){
		return this.receive;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本期收入数量
	 */
	public void setReceive(BigDecimal receive){
		this.receive = receive;
	}

	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本期收入辅助数量
	 */
	@Column(name ="SECRECEIVE",nullable=true,length=32)
	public BigDecimal getSecReceive(){
		return this.secReceive;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本期收入辅助数量
	 */
	public void setSecReceive(BigDecimal secReceive){
		this.secReceive = secReceive;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本期发出数量
	 */
	@Column(name ="SEND",nullable=true,length=32)
	public BigDecimal getSend(){
		return this.send;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本期发出数量
	 */
	public void setSend(BigDecimal send){
		this.send = send;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本期发出辅助数量
	 */
	@Column(name ="SecSEND",nullable=true,length=32)
	public BigDecimal getSecSend(){
		return this.secSend;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本期发出辅助数量
	 */
	public void setSecSend(BigDecimal secSend){
		this.secSend = secSend;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本年累计收入数量
	 */
	@Column(name ="YTDRECEIVE",nullable=true,length=32)
	public BigDecimal getYtdReceive(){
		return this.ytdReceive;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本年累计收入数量
	 */
	public void setYtdReceive(BigDecimal ytdReceive){
		this.ytdReceive = ytdReceive;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本年累计发出数量
	 */
	@Column(name ="YTDSEND",nullable=true,length=32)
	public BigDecimal getYtdSend(){
		return this.ytdSend;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本年累计发出数量
	 */
	public void setYtdSend(BigDecimal ytdSend){
		this.ytdSend = ytdSend;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本年累计收入辅助数量
	 */
	@Column(name ="SECYTDRECEIVE",nullable=true,length=32)
	public BigDecimal getSecYtdReceive(){
		return this.secYtdReceive;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本年累计收入辅助数量
	 */
	public void setSecYtdReceive(BigDecimal secYtdReceive){
		this.secYtdReceive = secYtdReceive;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本年累计发出辅助数量
	 */
	@Column(name ="SECYTDSEND",nullable=true,length=32)
	public BigDecimal getSecYtdSend(){
		return this.secYtdSend;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本年累计发出辅助数量
	 */
	public void setSecYtdSend(BigDecimal secYtdSend){
		this.secYtdSend = secYtdSend;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本期结存数量=期初金额+本期收入-本期发出
	 */
	@Column(name ="ENDQTY",nullable=true,length=32)
	public BigDecimal getEndQty(){
		return this.endQty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本期结存数量=期初金额+本期收入-本期发出
	 */
	public void setEndQty(BigDecimal endQty){
		this.endQty = endQty;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本期结存辅助数量
	 */
	@Column(name ="SECENDQTY",nullable=true,length=32)
	public BigDecimal getSecEndQty(){
		return this.secEndQty;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本期结存辅助数量
	 */
	public void setSecEndQty(BigDecimal secEndQty){
		this.secEndQty = secEndQty;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  期初金额
	 */
	@Column(name ="BEGBAL",nullable=true,length=32)
	public BigDecimal getBegBal(){
		return this.begBal;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  期初金额
	 */
	public void setBegBal(BigDecimal begBal){
		this.begBal = begBal;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本期收入金额
	 */
	@Column(name ="DEBIT",nullable=true,length=32)
	public BigDecimal getDebit(){
		return this.debit;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本期收入金额
	 */
	public void setDebit(BigDecimal debit){
		this.debit = debit;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本期发出金额
	 */
	@Column(name ="CREDIT",nullable=true,length=32)
	public BigDecimal getCredit(){
		return this.credit;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本期发出金额
	 */
	public void setCredit(BigDecimal credit){
		this.credit = credit;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本年累计收入金额
	 */
	@Column(name ="YTDDEBIT",nullable=true,length=32)
	public BigDecimal getYtdDebit(){
		return this.ytdDebit;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本年累计收入金额
	 */
	public void setYtdDebit(BigDecimal ytdDebit){
		this.ytdDebit = ytdDebit;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  本年累计发出金额
	 */
	@Column(name ="YTDCREDIT",nullable=true,length=32)
	public BigDecimal getYtdCredit(){
		return this.ytdCredit;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  本年累计发出金额
	 */
	public void setYtdCredit(BigDecimal ytdCredit){
		this.ytdCredit = ytdCredit;
	}
	/**
	 *方法: 取得java.math.BigDecimal
	 *@return: java.math.BigDecimal  期末结存金额=期初金额+本期收入-本期发出
	 */
	@Column(name ="ENDBAL",nullable=true,length=32)
	public BigDecimal getEndBal(){
		return this.endBal;
	}

	/**
	 *方法: 设置java.math.BigDecimal
	 *@param: java.math.BigDecimal  期末结存金额=期初金额+本期收入-本期发出
	 */
	public void setEndBal(BigDecimal endBal){
		this.endBal = endBal;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  机构ID
	 */
	@Column(name ="SONID",nullable=true,length=32)
	public String getSonID(){
		return this.sonID;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  机构ID
	 */
	public void setSonID(String sonID){
		this.sonID = sonID;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  父ID
	 */
	@Column(name ="FID",nullable=true,length=32)
	public String getFid(){
		return this.fid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  父ID
	 */
	public void setFid(String fid){
		this.fid = fid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分支机构名称
	 */
	@Column(name ="DEPARTNAME",nullable=true,length=32)
	public String getDepartname() {
		return departname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  分支机构名称
	 */
	public void setDepartname(String departname) {
		this.departname = departname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品名称
	 */
	@Column(name ="ITEMNAME",nullable=true,length=32)
	public String getItemname() {
		return itemname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商品名称
	 */
	public void setItemname(String itemname) {
		this.itemname = itemname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库名称
	 */
	@Column(name ="STOCKNAME",nullable=true,length=32)
	public String getStockname() {
		return stockname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  仓库名称
	 */
	public void setStockname(String stockname) {
		this.stockname = stockname;
	}
}
