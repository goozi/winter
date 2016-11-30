package com.qihang.buss.sc.oa.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**   
 * @Title: Entity
 * @Description: 工作日志
 * @author onlineGenerator
 * @date 2015-12-01 16:09:09
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_DAILY", schema = "")
@SuppressWarnings("serial")
public class TScDailyEntity implements java.io.Serializable {
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
	/**日志标题*/
	@Excel(name="日志标题")
	private String dailyTitle;
	/**日志类型*/
	@Excel(name="日志类型")
	private String dailyType;
	/**日志时间*/
	@Excel(name="日志时间")
	private Date dailyTime;
	/**工作计划*/
	@Excel(name="工作计划")
	private String workPlan;
	/**日志内容*/
	@Excel(name="日志内容")
	private String dailyInfo;

    private String dailyShare;//日志共享

	private List<TScDailyFileEntity> childernFile;

	private String streamName;
	private String fileName;
	private String fileId;
	private Boolean hasFinish;

	private String newStreamName;//app更新时新增附件路径
	private String newFileName;//app更新时新增附件名称

	private String planName;
	private String delFileId;//删除附件id

	private String userId;

	private String shareUser;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  日志标题
	 */
	@Column(name ="DAILY_TITLE",nullable=true,length=100)
	public String getDailyTitle(){
		return this.dailyTitle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  日志标题
	 */
	public void setDailyTitle(String dailyTitle){
		this.dailyTitle = dailyTitle;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  日志类型
	 */
	@Column(name ="DAILY_TYPE",nullable=true,length=32)
	public String getDailyType(){
		return this.dailyType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  日志类型
	 */
	public void setDailyType(String dailyType){
		this.dailyType = dailyType;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  日志时间
	 */
	@Column(name ="DAILY_TIME",nullable=true)
	public Date getDailyTime(){
		return this.dailyTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  日志时间
	 */
	public void setDailyTime(Date dailyTime){
		this.dailyTime = dailyTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  工作计划
	 */
	@Column(name ="WORK_PLAN",nullable=true,length=32)
	public String getWorkPlan(){
		return this.workPlan;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  工作计划
	 */
	public void setWorkPlan(String workPlan){
		this.workPlan = workPlan;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  日志内容
	 */
	@Column(name ="DAILY_INFO",nullable=true,length=8000)
	public String getDailyInfo(){
		return this.dailyInfo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  日志内容
	 */
	public void setDailyInfo(String dailyInfo){
		this.dailyInfo = dailyInfo;
	}

	@OneToMany(mappedBy="parentFileObj",cascade= CascadeType.ALL)
	public List<TScDailyFileEntity> getChildernFile() {
		return childernFile;
	}

	public void setChildernFile(List<TScDailyFileEntity> childernFile) {
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


    @Column(name ="DAILY_SHARE",nullable=true,length=1000)
    public String getDailyShare() {
        return dailyShare;
    }

    public void setDailyShare(String dailyShare) {
        this.dailyShare = dailyShare;
    }


	@Transient
	public Boolean getHasFinish() {
		return hasFinish;
	}

	public void setHasFinish(Boolean hasFinish) {
		this.hasFinish = hasFinish;
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
	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
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
	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	@Transient
	public String getShareUser() {
		return shareUser;
	}

	public void setShareUser(String shareUser) {
		this.shareUser = shareUser;
	}
}
