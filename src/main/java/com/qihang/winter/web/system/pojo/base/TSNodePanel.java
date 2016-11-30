package com.qihang.winter.web.system.pojo.base;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by LenovoM4550 on 2016-04-28.
 */

@Entity
@Table(name = "t_tree_form_relation", schema = "")
public class TSNodePanel implements java.io.Serializable {

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
    /**树节点id*/
    private String nodeId;
    /**面板名称*/
    private String panelName;
    /**面板类型*/
    private Integer type;
    /**
     面板值
     */
    private String value;
    /**
     * 备注
     */
    private String note;
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

    @Column(name ="node_id",nullable=true,length=32)
    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    @Column(name ="panel_name",nullable=true,length=100)
    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    @Column(name ="type",nullable=true)
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Column(name ="value",nullable=true,length=255)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Column(name ="note",nullable=true,length=255)
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
