package com.qihang.buss.sc.baseinfo.service.impl;
import com.alibaba.druid.util.StringUtils;
import com.qihang.buss.sc.baseinfo.service.TScLogisticalServiceI;
import com.qihang.buss.sc.sales.entity.TScCountEntity;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.baseinfo.entity.TScLogisticalEntity;
import com.qihang.winter.core.constant.Globals;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.io.Serializable;

@Service("tScLogisticalService")
@Transactional
public class TScLogisticalServiceImpl extends CommonServiceImpl implements TScLogisticalServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScLogisticalEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TScLogisticalEntity) entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TScLogisticalEntity) entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScLogisticalEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScLogisticalEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScLogisticalEntity t){
	 	return true;
 	}

	/**
	 * 操作单据的时候修改它的引用次数根据操作的方式
	 * @param countEntity
	 * @return
	 */
	@Override
	public boolean updateLogisticalCount(TScCountEntity countEntity) {
		//判断操作的类型 1是新增，2是编辑，3是删除
		try {
			if(Globals.COUNT_ADD_TYPE.equals(countEntity.getType())){
				//oldId是供应商的id,对引用次数进行累加
				if(StringUtils.isEmpty(countEntity.getOldId())){
					return false;
				}
				TScLogisticalEntity logisticalEntity = this.get(TScLogisticalEntity.class,countEntity.getOldId());
				if(logisticalEntity == null){
					return false;
				}
				if(logisticalEntity.getCount() == null|| logisticalEntity.getCount() == 0){
					logisticalEntity.setCount(1);
				}else{
					logisticalEntity.setCount(logisticalEntity.getCount() + 1);
				}
				super.saveOrUpdate(logisticalEntity);
			}else if(Globals.COUNT_UPDATE_TYPE.equals(countEntity.getType())){
				//单据引用oldId是旧数据，newId是新数据，对于引用次数进行操作，旧数据-1，修改后的数据+1
				if(StringUtils.isEmpty(countEntity.getOldId())|| StringUtils.isEmpty(countEntity.getNewId())){
					return false;
				}
				TScLogisticalEntity logisticalOldEntity = this.get(TScLogisticalEntity.class,countEntity.getOldId());
				TScLogisticalEntity logisticalNewEntity = this.get(TScLogisticalEntity.class,countEntity.getNewId());
				if(logisticalOldEntity == null ||logisticalNewEntity == null){
					return false;
				}
				if(logisticalOldEntity.getCount() == null || logisticalOldEntity.getCount() == 0){
					logisticalOldEntity.setCount(0);
				}else {
					logisticalOldEntity.setCount(logisticalOldEntity.getCount() - 1);
				}
				if(logisticalNewEntity.getCount() == null || logisticalNewEntity.getCount() == 0){
					logisticalNewEntity.setCount(1);
				}else {
					logisticalNewEntity.setCount(logisticalNewEntity.getCount() + 1);
				}
				super.saveOrUpdate(logisticalOldEntity);
				super.saveOrUpdate(logisticalNewEntity);

			}else if(Globals.COUNT_DEL_TYPE.equals(countEntity.getType())){
				if(StringUtils.isEmpty(countEntity.getOldId())){
					return false;
				}
				TScLogisticalEntity logisticalOldEntity = this.get(TScLogisticalEntity.class,countEntity.getOldId());
				if(logisticalOldEntity == null){
					return false;
				}
				if(logisticalOldEntity.getCount() == null || logisticalOldEntity.getCount() == 0){
					logisticalOldEntity.setCount(0);
				}else{
					logisticalOldEntity.setCount(logisticalOldEntity.getCount() - 1);
				}
				super.saveOrUpdate(logisticalOldEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TScLogisticalEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{number}",String.valueOf(t.getNumber()));
 		sql  = sql.replace("#{parentid}",String.valueOf(t.getParentID()));
 		sql  = sql.replace("#{shortname}",String.valueOf(t.getShortName()));
 		sql  = sql.replace("#{shortnumber}",String.valueOf(t.getShortNumber()));
 		sql  = sql.replace("#{regionid}",String.valueOf(t.getRegionID()));
 		sql  = sql.replace("#{contact}",String.valueOf(t.getContact()));
 		sql  = sql.replace("#{mobilephone}",String.valueOf(t.getMobilePhone()));
 		sql  = sql.replace("#{phone}",String.valueOf(t.getPhone()));
 		sql  = sql.replace("#{fax}",String.valueOf(t.getFax()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{postalcode}",String.valueOf(t.getPostalCode()));
 		sql  = sql.replace("#{email}",String.valueOf(t.getEmail()));
 		sql  = sql.replace("#{bank}",String.valueOf(t.getBank()));
 		sql  = sql.replace("#{banknumber}",String.valueOf(t.getBankNumber()));
 		sql  = sql.replace("#{jhday}",String.valueOf(t.getJhDay()));
 		sql  = sql.replace("#{jhrate}",String.valueOf(t.getJhRate()));
 		sql  = sql.replace("#{level}",String.valueOf(t.getLevel()));
 		sql  = sql.replace("#{disabled}",String.valueOf(t.getDisabled()));
 		sql  = sql.replace("#{deleted}",String.valueOf(t.getDeleted()));
 		sql  = sql.replace("#{version}",String.valueOf(t.getVersion()));
 		sql  = sql.replace("#{count}",String.valueOf(t.getCount()));
 		sql  = sql.replace("#{note}",String.valueOf(t.getNote()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	public AjaxJson checkName(String name, String load, String old_name){
		AjaxJson j = new AjaxJson();
		String sql = "select name from t_sc_logistical where 1 = 1 AND name = '"+name+"'";
		List<String> list = this.findListbySql(sql);

		if(list.size() > 0){
			j.setSuccess(true);
		}else{
			j.setSuccess(false);
		}

		list.add("");//若缺此代码，编辑数据库里没有的项，list为空，list.get(0)越界报错
		//编辑时若为改变名称则不显示已存在的对话框
		if(load.equals("edit") && old_name.equals(list.get(0))){
			j.setSuccess(false);
		}

		return j;
	}
}