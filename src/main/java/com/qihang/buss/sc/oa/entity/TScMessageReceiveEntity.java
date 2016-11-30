package com.qihang.buss.sc.oa.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**   
 * @Title: Entity
 * @Description: 收信箱
 * @author onlineGenerator
 * @date 2015-12-15 14:38:40
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_MESSAGE_RECEIVE", schema = "")
@SuppressWarnings("serial")
public class TScMessageReceiveEntity implements java.io.Serializable {
	/**主键*/
	private String id;
	/**创建人名称*/
	private String createName;
	/**创建人登录名称*/
	private String createBy;
	/**标题*/
	@Excel(name="标题")
	private String title;
	/**发送人*/
	@Excel(name="发送人")
	private String sender;
	/**接收时间*/
	private Date createDate;
	/**更新人名称*/
	private String updateName;
	/**更新人登录名称*/
	private String updateBy;
	/**更新日期*/
	private Date updateDate;
	/**读取状态*/
	private Integer readStatus;
	/**读取时间*/
	private Date readDate;
	/**用户ID*/
	private String userId;
	/**消息内容*/
	@Excel(name="消息内容")
	private String content;
	/**收信箱Id*/
	private String messageId;

	private List<TScMessageFileEntity> fileList;

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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  标题
	 */
	@Column(name ="TITLE",nullable=true,length=100)
	public String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标题
	 */
	public void setTitle(String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  发送人
	 */
	@Column(name ="SENDER",nullable=true,length=100)
	public String getSender(){
		return this.sender;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发送人
	 */
	public void setSender(String sender){
		this.sender = sender;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  接收时间
	 */
	@Column(name ="CREATE_DATE",nullable=true,length=20)
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  接收时间
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  读取状态
	 */
	@Column(name ="READ_STATUS",nullable=true,length=32)
	public Integer getReadStatus(){
		return this.readStatus;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  读取状态
	 */
	public void setReadStatus(Integer readStatus){
		this.readStatus = readStatus;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  读取时间
	 */
	@Column(name ="READ_DATE",nullable=true,length=32)
	public Date getReadDate(){
		return this.readDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  读取时间
	 */
	public void setReadDate(Date readDate){
		this.readDate = readDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户ID
	 */
	@Column(name ="USER_ID",nullable=true,length=100)
	public String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户ID
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  消息内容
	 */
	@Column(name ="CONTENT",nullable=true,length=8000)
	public String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  消息内容
	 */
	public void setContent(String content){
		this.content = content;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  收信箱Id
	 */
	@Column(name ="MESSAGE_ID",nullable=true,length=100)
	public String getMessageId(){
		return this.messageId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  收信箱Id
	 */
	public void setMessageId(String messageId){
		this.messageId = messageId;
	}

	@Transient
	public List<TScMessageFileEntity> getFileList() {
		return fileList;
	}

	public void setFileList(List<TScMessageFileEntity> fileList) {
		this.fileList = fileList;
	}
}
