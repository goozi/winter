package com.qihang.winter.web.system.pojo.base;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;

/**
 * Created by LenovoM4550 on 2016-04-28.
 */

@Entity
@Table(name = "t_act_task", schema = "")
public class TaskNodeEntity implements java.io.Serializable {

    /**主键*/
    private String id;
    /**创建人名称*/
    private String createName;
    /**创建人登录名称*/
    private String createBy;
    /**创建日期*/
    private java.util.Date createDate;
    /**更新人名称*/
    private String updateName;
    /**更新人登录名称*/
    private String updateBy;
    /**更新日期*/
    private java.util.Date updateDate;
    /**节点名称*/
    private String taskName;
    /**流程id*/
    private String processInstanceId;
    /**节点状态*/
    private Integer taskStatus;
    /**节点id*/
    private String taskId;
    /**接收数据类型*/
    private String infoType;
    /**接收变量值*/
    private String infoValue;
    /**判断条件字段*/
    private String judgeInfo;
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

    @Column(name ="task_name",nullable=true,length=100)
    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    @Column(name ="process_instance_id",nullable=true,length=100)
    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    @Column(name ="task_status",nullable=true)
    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    @Column(name ="task_id",nullable=true,length = 100)
    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Column(name ="info_type",nullable=true,length = 100)
    public String getInfoType() {
        return infoType;
    }

    public void setInfoType(String infoType) {
        this.infoType = infoType;
    }

    @Column(name ="info_value",nullable=true,length = 255)
    public String getInfoValue() {
        return infoValue;
    }

    public void setInfoValue(String infoValue) {
        this.infoValue = infoValue;
    }

    @Column(name ="judge_info",nullable=true,length = 255)
    public String getJudgeInfo() {
        return judgeInfo;
    }

    public void setJudgeInfo(String judgeInfo) {
        this.judgeInfo = judgeInfo;
    }
}
