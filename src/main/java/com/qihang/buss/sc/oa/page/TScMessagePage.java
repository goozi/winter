
package com.qihang.buss.sc.oa.page;


import com.qihang.buss.sc.oa.entity.TScMessageFileEntity;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**   
 * @Title: Entity
 * @Description: 消息管理
 * @author onlineGenerator
 * @date 2015-12-14 20:53:49
 * @version V1.0   
 *
 */
public class TScMessagePage implements java.io.Serializable {
	/**保存-消息管理附件表*/
	@JsonProperty("tScMessageFileList")
	private List<TScMessageFileEntity> tScMessageFileList = new ArrayList<TScMessageFileEntity>();
	public List<TScMessageFileEntity> getTScMessageFileList() {
		return tScMessageFileList;
	}
	public void setTScMessageFileList(List<TScMessageFileEntity> tScMessageFileList) {
		this.tScMessageFileList = tScMessageFileList;
	}

	/**主键*/
	private String id;
	/**标题*/
	private String title;
	/**发送人*/
	private String createName;
	/**创建人登录名称*/
	private String createBy;
	/**发送时间*/
	private Date createDate;
	/**更新人名称*/
	private String updateName;
	/**更新人登录名称*/
	private String updateBy;
	/**更新日期*/
	private Date updateDate;
	/**接收人*/
	private String userId;
	/**消息内容*/
	private String content;
	/**读取状态*/
	private Double readStatus;

	/**发送人*/
	private String sender;

	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
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
	 *@return: java.lang.String  标题
	 */
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
	public String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  发送人
	 */
	public void setCreateName(String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人登录名称
	 */
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
	 *@return: java.util.Date  发送时间
	 */
	public Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  发送时间
	 */
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名称
	 */
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
	 *@return: java.lang.String  接收人
	 */
	public String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  接收人
	 */
	public void setUserId(String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  消息内容
	 */
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
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  读取状态
	 */
	public Double getReadStatus(){
		return this.readStatus;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  读取状态
	 */
	public void setReadStatus(Double readStatus){
		this.readStatus = readStatus;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
}
