package com.qihang.winter.web.system.pojo.base;

import java.util.HashMap;
import java.util.Map;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.persistence.*;
import javax.xml.soap.Text;
import java.sql.Blob;

import com.qihang.winter.poi.excel.annotation.Excel;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

/**   
 * @Title: Entity
 * @Description: 数据源配置
 * @author zhangdaihao
 * @date 2014-09-05 13:22:10
 * @version V1.0   
 *
 */
@Entity
@Table(name = "t_s_data_source", schema = "")
@DynamicUpdate(true)
@DynamicInsert(true)
@SuppressWarnings("serial")
public class DynamicDataSourceEntity implements java.io.Serializable {
	
	public static Map<String, DynamicDataSourceEntity> DynamicDataSourceMap = new HashMap<String, DynamicDataSourceEntity>(); 
	
	//用于将code,name放入到缓存中
		
	/**id*/
	private java.lang.String id;
	/**dbKey*/
	@Excel(name="dbKey")
	private java.lang.String dbKey;
	/**数据源描述*/
	@Excel(name="数据源描述")
	private java.lang.String description;
	/**驱动类*/
	@Excel(name="驱动类")
	private java.lang.String driverClass;
	/**url*/
	@Excel(name="url")
	private java.lang.String url;
	/**数据库用户*/
	@Excel(name="数据库用户")
	private java.lang.String dbUser;
	/**数据库密码*/
	@Excel(name="数据库密码")
	private java.lang.String dbPassword;
	/**数据库类型*/
	@Excel(name="数据库类型")
	private java.lang.String dbType;
	/**账套Id*/
	@Excel(name="账套Id")
	private java.lang.String accountId;
	/** 非持久化属性，根据url解析获得 **/
	private String dbIp;//数据库ip
	private String dbPort;//数据库端口
	private String dbName;//数据库实例名或数据库名
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,precision=36,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  dbKey
	 */
	@Column(name ="DB_KEY",nullable=false,precision=50,length=50)
	public java.lang.String getDbKey(){
		return this.dbKey;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  dbKey
	 */
	public void setDbKey(java.lang.String dbKey){
		this.dbKey = dbKey;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  description
	 */
	@Column(name ="DESCRIPTION",nullable=false,precision=50,length=50)
	public java.lang.String getDescription(){
		return this.description;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  description
	 */
	public void setDescription(java.lang.String description){
		this.description = description;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  driverClass
	 */
	@Column(name ="DRIVER_CLASS",nullable=false,precision=50,length=50)
	public java.lang.String getDriverClass(){
		return this.driverClass;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  driverClass
	 */
	public void setDriverClass(java.lang.String driverClass){
		this.driverClass = driverClass;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  url
	 */
	@Column(name ="URL",nullable=false,precision=100,length=100)
	public java.lang.String getUrl(){
		return this.url;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  url
	 */
	public void setUrl(java.lang.String url){
		this.url = url;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  dbUser
	 */
	@Column(name ="DB_USER",nullable=false,precision=50,length=50)
	public java.lang.String getDbUser(){
		return this.dbUser;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  dbUser
	 */
	public void setDbUser(java.lang.String dbUser){
		this.dbUser = dbUser;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  dbPassword
	 */
	@Column(name ="DB_PASSWORD",nullable=true,precision=50,length=50)
	public java.lang.String getDbPassword(){
		return this.dbPassword;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  dbPassword
	 */
	public void setDbPassword(java.lang.String dbPassword){
		this.dbPassword = dbPassword;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  dbType
	 */
	@Column(name ="DB_TYPE",nullable=true,precision=50,length=50)
	public java.lang.String getDbType(){
		return this.dbType;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  dbType
	 */
	public void setDbType(java.lang.String dbType){
		this.dbType = dbType;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  账套Id
	 */
	@Column(name ="ACCOUNTID",nullable=true,length=36)
	public java.lang.String getAccountId(){
		return this.accountId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  账套Id
	 */
	public void setAccountId(java.lang.String accountId){
		this.accountId = accountId;
	}

	@Transient
	public String getDbIp() {
		return dbIp;
	}

	public void setDbIp(String dbIp) {
		this.dbIp = dbIp;
	}

	@Transient
	public String getDbPort() {
		return dbPort;
	}

	public void setDbPort(String dbPort) {
		this.dbPort = dbPort;
	}

	@Transient
	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
}