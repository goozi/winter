
package com.qihang.winter.web.system.pojo.base;


import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import com.qihang.winter.web.system.pojo.base.TSTreeinfoEntryEntity;


/**   
 * @Title: Entity
 * @Description: 流程树表
 * @author onlineGenerator
 * @date 2016-04-28 09:20:25
 * @version V1.0   
 *
 */
public class TSTreeinfoPage implements java.io.Serializable {
	/**保存-流程树配置*/
	private List<TSTreeinfoEntryEntity> tSTreeinfoEntryList = new ArrayList<TSTreeinfoEntryEntity>();
	public List<TSTreeinfoEntryEntity> getTSTreeinfoEntryList() {
		return tSTreeinfoEntryList;
	}
	public void setTSTreeinfoEntryList(List<TSTreeinfoEntryEntity> tSTreeinfoEntryList) {
		this.tSTreeinfoEntryList = tSTreeinfoEntryList;
	}

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
	/**树名称*/
	private String treeName;
	/**流程实例*/
	private String actId;
	/**备注*/
	private String note;
	
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
	 *@return: java.lang.String  创建人名称
	 */
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
	 *@return: java.lang.String  树名称
	 */
	public String getTreeName(){
		return this.treeName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  树名称
	 */
	public void setTreeName(String treeName){
		this.treeName = treeName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  流程实例
	 */
	public String getActId(){
		return this.actId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  流程实例
	 */
	public void setActId(String actId){
		this.actId = actId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
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
}
