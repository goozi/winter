package com.qihang.buss.sc.oa.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**   
 * @Title: Entity
 * @Description: 工作计划
 * @author onlineGenerator
 * @date 2015-11-26 13:44:24
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_PLAN", schema = "")
@SuppressWarnings("serial")
public class TScPlanEntity implements java.io.Serializable {
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
	/**计划类型*/
	@Excel(name="计划类型")
	private String planType;
	/**计划名称*/
	@Excel(name="计划名称")
	private String planName;
	/**负责人*/
	@Excel(name="负责人")
	private String planMaster;
	/**参与人*/
	@Excel(name="参与人")
	private String planGroup;
	/**领导批注*/
	@Excel(name="领导批注")
	private String planLeadder;
	/**开始时间*/
	@Excel(name="开始时间")
	private Date planStartdate;
	/**结束时间*/
	@Excel(name="结束时间")
	private Date planEnddate;
	/**计划人*/
	@Excel(name="计划人")
	private String planer;
	/**计划内容*/
	@Excel(name="计划内容")
	private String planInfo;
	/**计划进度*/
	@Excel(name="计划进度(%)")
	private Double planProgress;
	/**计划状态*/
	@Excel(name="计划状态")
	private String planState;

	private List<TScPlanFileEntity> childernFile;

    private String streamName;
	private String fileName;

	private String newStreamName;//app更新时新增附件路径
	private String newFileName;//app更新时新增附件名称
	private String delFileId;//删除的附件id

    private String userId;

	private String masters;
	private String groups;
	private String leaders;

	/**版本号*/
	private Integer version;
	@Version
	@Column(name ="VERSION",nullable=true,length=32)
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  计划类型
	 */
	@Column(name ="PLAN_TYPE",nullable=true)
	public String getPlanType(){
		return this.planType;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  计划类型
	 */
	public void setPlanType(String planType){
		this.planType = planType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  计划名称
	 */
	@Column(name ="PLAN_NAME",nullable=true,length=100)
	public String getPlanName(){
		return this.planName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  计划名称
	 */
	public void setPlanName(String planName){
		this.planName = planName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  负责人
	 */
	@Column(name ="PLAN_MASTER",nullable=true,length=255)
	public String getPlanMaster(){
		return this.planMaster;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  负责人
	 */
	public void setPlanMaster(String planMaster){
		this.planMaster = planMaster;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  参与人
	 */
	@Column(name ="PLAN_GROUP",nullable=true,length=255)
	public String getPlanGroup(){
		return this.planGroup;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  参与人
	 */
	public void setPlanGroup(String planGroup){
		this.planGroup = planGroup;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  领导批注
	 */
	@Column(name ="PLAN_LEADDER",nullable=true,length=255)
	public String getPlanLeadder(){
		return this.planLeadder;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  领导批注
	 */
	public void setPlanLeadder(String planLeadder){
		this.planLeadder = planLeadder;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  开始时间
	 */
	@Column(name ="PLAN_STARTDATE",nullable=true,length=32)
	public Date getPlanStartdate(){
		return this.planStartdate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  开始时间
	 */
	public void setPlanStartdate(Date planStartdate){
		this.planStartdate = planStartdate;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  结束时间
	 */
	@Column(name ="PLAN_ENDDATE",nullable=true,length=32)
	public Date getPlanEnddate(){
		return this.planEnddate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  结束时间
	 */
	public void setPlanEnddate(Date planEnddate){
		this.planEnddate = planEnddate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  计划人
	 */
	@Column(name ="PLANER",nullable=true,length=32)
	public String getPlaner(){
		return this.planer;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  计划人
	 */
	public void setPlaner(String planer){
		this.planer = planer;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  计划内容
	 */
	@Column(name ="PLAN_INFO",nullable=true,length=32)
	public String getPlanInfo(){
		return this.planInfo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  计划内容
	 */
	public void setPlanInfo(String planInfo){
		this.planInfo = planInfo;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  计划进度
	 */
	@Column(name ="PLAN_PROGRESS",nullable=true,scale=2,length=32)
	public Double getPlanProgress(){
		return this.planProgress;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  计划进度
	 */
	public void setPlanProgress(Double planProgress){
		this.planProgress = planProgress;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  计划状态
	 */
	@Column(name ="PLAN_STATE",nullable=true,length=50)
	public String getPlanState(){
		return this.planState;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  计划状态
	 */
	public void setPlanState(String planState){
		this.planState = planState;
	}

	@OneToMany(mappedBy="parentFileObj",cascade= CascadeType.ALL)
	public List<TScPlanFileEntity> getChildernFile() {
		return childernFile;
	}

	public void setChildernFile(List<TScPlanFileEntity> childernFile) {
		this.childernFile = childernFile;
	}

    @Transient
    public String getStreamName() {
        return streamName;
    }

    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }

	@Transient
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Transient
	public String getDelFileId() {
		return delFileId;
	}

	public void setDelFileId(String delFileId) {
		this.delFileId = delFileId;
	}

	@Transient
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Transient
	public String getNewStreamName() {
		return newStreamName;
	}

	public void setNewStreamName(String newStreamName) {
		this.newStreamName = newStreamName;
	}

	@Transient
	public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}

	@Transient
	public String getMasters() {
		return masters;
	}

	public void setMasters(String masters) {
		this.masters = masters;
	}

	@Transient
	public String getGroups() {
		return groups;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	@Transient
	public String getLeaders() {
		return leaders;
	}

	public void setLeaders(String leaders) {
		this.leaders = leaders;
	}
}
