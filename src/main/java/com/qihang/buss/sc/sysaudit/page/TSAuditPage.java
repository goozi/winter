
package com.qihang.buss.sc.sysaudit.page;
import com.qihang.buss.sc.sysaudit.entity.TSAuditLeaveEntity;

import java.util.List;
import java.util.ArrayList;


/**   
 * @Title: Entity
 * @Description: 多级审核主表
 * @author onlineGenerator
 * @date 2016-06-21 11:48:24
 * @version V1.0   
 *
 */
public class TSAuditPage implements java.io.Serializable {
	/**保存-多级审核分级信息*/
	private List<TSAuditLeaveEntity> tSAuditLeaveList = new ArrayList<TSAuditLeaveEntity>();
	public List<TSAuditLeaveEntity> getTSAuditLeaveList() {
		return tSAuditLeaveList;
	}
	public void setTSAuditLeaveList(List<TSAuditLeaveEntity> tSAuditLeaveList) {
		this.tSAuditLeaveList = tSAuditLeaveList;
	}

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
	/**菜单id*/
	private java.lang.String funId;
	/**进行多级审核控制*/
	private java.lang.Integer isAudit;
	/**级次*/
	private java.lang.Integer leaveNum;
	/**审核时必须逐级审核*/
	private java.lang.Integer isSort;
	/**使用工作流消息*/
	private java.lang.Integer isSendMessage;
	/**启用审核拒绝功能*/
	private java.lang.Integer isUnaudit;
	/**按金额确定多级审核*/
	private java.lang.Integer isMoney;
	/**最低限额*/
	private java.lang.Double minMoney;
	/**按金额确定审核级次*/
	private java.lang.Integer isMoneyLeave;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  菜单id
	 */
	public java.lang.String getFunId(){
		return this.funId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  菜单id
	 */
	public void setFunId(java.lang.String funId){
		this.funId = funId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  进行多级审核控制
	 */
	public java.lang.Integer getIsAudit(){
		return this.isAudit;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  进行多级审核控制
	 */
	public void setIsAudit(java.lang.Integer isAudit){
		this.isAudit = isAudit;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  级次
	 */
	public java.lang.Integer getLeaveNum(){
		return this.leaveNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  级次
	 */
	public void setLeaveNum(java.lang.Integer leaveNum){
		this.leaveNum = leaveNum;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  审核时必须逐级审核
	 */
	public java.lang.Integer getIsSort(){
		return this.isSort;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  审核时必须逐级审核
	 */
	public void setIsSort(java.lang.Integer isSort){
		this.isSort = isSort;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  使用工作流消息
	 */
	public java.lang.Integer getIsSendMessage(){
		return this.isSendMessage;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  使用工作流消息
	 */
	public void setIsSendMessage(java.lang.Integer isSendMessage){
		this.isSendMessage = isSendMessage;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  启用审核拒绝功能
	 */
	public java.lang.Integer getIsUnaudit(){
		return this.isUnaudit;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  启用审核拒绝功能
	 */
	public void setIsUnaudit(java.lang.Integer isUnaudit){
		this.isUnaudit = isUnaudit;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  按金额确定多级审核
	 */
	public java.lang.Integer getIsMoney(){
		return this.isMoney;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  按金额确定多级审核
	 */
	public void setIsMoney(java.lang.Integer isMoney){
		this.isMoney = isMoney;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  最低限额
	 */
	public java.lang.Double getMinMoney(){
		return this.minMoney;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  最低限额
	 */
	public void setMinMoney(java.lang.Double minMoney){
		this.minMoney = minMoney;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  按金额确定审核级次
	 */
	public java.lang.Integer getIsMoneyLeave(){
		return this.isMoneyLeave;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  按金额确定审核级次
	 */
	public void setIsMoneyLeave(java.lang.Integer isMoneyLeave){
		this.isMoneyLeave = isMoneyLeave;
	}
}
