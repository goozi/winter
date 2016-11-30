package com.qihang.buss.sc.init.service;
import com.qihang.buss.sc.init.entity.TScBegdataEntity;
import com.qihang.winter.core.common.service.CommonService;

import java.io.Serializable;

public interface TScBegdataServiceI extends CommonService{
	
 	public <T> void delete(T entity);
 	
 	public <T> Serializable save(T entity);
 	
 	public <T> void saveOrUpdate(T entity);
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScBegdataEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScBegdataEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScBegdataEntity t);

	/**
	 * 单据审核后，不执行核算项目的应收账款增加或减少，
	 * 而是等到系统结束初始化时才执行核算项目的应收账款增加或减少，
	 * 同时反应在“日结表，还有结账表”等相关报表中。
	 */
	public boolean doAddReceivable();
}
