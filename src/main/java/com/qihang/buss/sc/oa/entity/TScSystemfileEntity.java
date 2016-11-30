package com.qihang.buss.sc.oa.entity;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**   
 * @Title: Entity
 * @Description: 系统设置
 * @author onlineGenerator
 * @date 2015-12-09 17:33:11
 * @version V1.0   
 *
 */
@Entity
@Table(name = "T_SC_SYSTEMFILE", schema = "")
@SuppressWarnings("serial")
public class TScSystemfileEntity implements java.io.Serializable {
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
	/**工作日志是否绑定工作计划*/
	@Excel(name="工作日志是否绑定工作计划")
	private String worklog;
	/**公共文件柜是否审批*/
	@Excel(name="公共文件柜是否审批")
	private String publicfile;
	/**共享日志默认用户*/
	@Excel(name="共享日志默认用户")
	private String shareloguser;

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
	 *@return: java.lang.String  工作日志是否绑定工作计划
	 */
	@Column(name ="WORKLOG",nullable=true,length=32)
	public String getWorklog(){
		return this.worklog;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  工作日志是否绑定工作计划
	 */
	public void setWorklog(String worklog){
		this.worklog = worklog;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公共文件柜是否审批
	 */
	@Column(name ="PUBLICFILE",nullable=true,length=32)
	public String getPublicfile(){
		return this.publicfile;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公共文件柜是否审批
	 */
	public void setPublicfile(String publicfile){
		this.publicfile = publicfile;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  共享日志默认用户
	 */
	@Column(name ="SHARELOGUSER",nullable=true,length=1000)
	public String getShareloguser(){
		return this.shareloguser;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  共享日志默认用户
	 */
	public void setShareloguser(String shareloguser){
		this.shareloguser = shareloguser;
	}
}
