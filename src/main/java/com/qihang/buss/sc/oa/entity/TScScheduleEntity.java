package com.qihang.buss.sc.oa.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 工作日志
 * @author onlineGenerator
 * @date 2015-12-01 16:09:09
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_SCHEDULE", schema = "")
@SuppressWarnings("serial")
public class TScScheduleEntity implements java.io.Serializable {
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
	/**日程标题*/
	@Excel(name="日程标题")
	private String CalendarTitle;
	/**开始时间*/
	@Excel(name="开始时间")
	private Date CalendarStartTime;
	/**结束时间*/
	@Excel(name="结束时间")
	private Date CalendarEndTime;
	/**是否全天计划*/
	@Excel(name="是否全天计划")
	private Boolean IsAllDayEvent;
	/**时区*/
	@Excel(name="时区")
	private Integer timezone;

	private String userId;

	private String date;
	private String startTime;
	private String endTime;
//    //临时变量
//    private String startTime;
//    private String endTime;
//
//    public String getStartTime() {
//        return startTime;
//    }
//
//    public void setStartTime(String startTime) {
//        this.startTime = startTime;
//    }
//
//    public String getEndTime() {
//        return endTime;
//    }
//
//    public void setEndTime(String endTime) {
//        this.endTime = endTime;
//    }

    //    private String dailyShare;//日志共享
//
//	private List<TOaDailyFileEntity> childernFile;
//
//	private String streamName;
//	private String fileName;
//	private Boolean hasFinish;

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

	@Column(name ="CALENDAR_TITLE",nullable=true,length=8000)
	public String getCalendarTitle() {
		return CalendarTitle;
	}

	public void setCalendarTitle(String calendarTitle) {
		CalendarTitle = calendarTitle;
	}

	@Column(name ="CALENDAR_START_TIME",nullable=true)
	public Date getCalendarStartTime() {
		return CalendarStartTime;
	}

	public void setCalendarStartTime(Date calendarStartTime) {
		CalendarStartTime = calendarStartTime;
	}

	@Column(name ="CALENDAR_END_TIME",nullable=true)
	public Date getCalendarEndTime() {
		return CalendarEndTime;
	}

	public void setCalendarEndTime(Date calendarEndTime) {
		CalendarEndTime = calendarEndTime;
	}

	@Column(name ="IS_ALL_DAY_EVENT",nullable=true)
	public Boolean getIsAllDayEvent() {
		return IsAllDayEvent;
	}

	public void setIsAllDayEvent(Boolean isAllDayEvent) {
		IsAllDayEvent = isAllDayEvent;
	}

	@Column(name ="TIME_ZONE",nullable=true)
	public Integer getTimezone() {
		return timezone;
	}

	public void setTimezone(Integer timezone) {
		this.timezone = timezone;
	}

	@Transient
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Transient
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Transient
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	@Transient
	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
