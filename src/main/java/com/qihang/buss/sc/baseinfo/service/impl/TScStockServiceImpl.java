package com.qihang.buss.sc.baseinfo.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.qihang.buss.sc.baseinfo.entity.TScStockEntity;
import com.qihang.buss.sc.baseinfo.service.TScStockServiceI;
import com.qihang.buss.sc.sales.entity.TScCountEntity;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.constant.Globals;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.UUID;

@Service("tScStockService")
@Transactional
public class TScStockServiceImpl extends CommonServiceImpl implements TScStockServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScStockEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TScStockEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TScStockEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScStockEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScStockEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScStockEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TScStockEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{number}",String.valueOf(t.getNumber()));
 		sql  = sql.replace("#{parentid}",String.valueOf(t.getParentID()));
 		sql  = sql.replace("#{shortnumber}",String.valueOf(t.getShortNumber()));
 		sql  = sql.replace("#{shortname}",String.valueOf(t.getShortName()));
 		sql  = sql.replace("#{empid}",String.valueOf(t.getEmpID()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{phone}",String.valueOf(t.getPhone()));
 		sql  = sql.replace("#{typeid}",String.valueOf(t.getTypeID()));
 		sql  = sql.replace("#{deleted}",String.valueOf(t.getDeleted()));
 		sql  = sql.replace("#{level}",String.valueOf(t.getLevel()));
 		sql  = sql.replace("#{note}",String.valueOf(t.getNote()));
 		sql  = sql.replace("#{sonid}",String.valueOf(t.getSonID()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	/**
	 * 操作单据的时候修改它的引用次数根据操作的方式
	 * @param countEntity
	 * @return
	 */
	@Override
	public boolean updateStockCount(TScCountEntity countEntity) {
		//判断操作的类型 1是新增，2是编辑，3是删除
		try {
			if(Globals.COUNT_ADD_TYPE.equals(countEntity.getType())){
				//oldId是客户的id,对引用次数进行累加
				if(StringUtils.isEmpty(countEntity.getOldId())){
					return false;
				}
				TScStockEntity scStockEntity = this.get(TScStockEntity.class,countEntity.getOldId());
				if(scStockEntity == null){
					return false;
				}
				if(scStockEntity.getCount() == null|| scStockEntity.getCount() == 0){
					scStockEntity.setCount(1);
				}else{
					scStockEntity.setCount(scStockEntity.getCount() + 1);
				}
				super.saveOrUpdate(scStockEntity);
			}else if(Globals.COUNT_UPDATE_TYPE.equals(countEntity.getType())){
				//单据引用oldId是旧数据，newId是新数据，对于引用次数进行操作，旧数据-1，修改后的数据+1
				if(StringUtils.isEmpty(countEntity.getOldId())|| StringUtils.isEmpty(countEntity.getNewId())){
					return false;
				}
				TScStockEntity organizationOldEntity = this.get(TScStockEntity.class,countEntity.getOldId());
				TScStockEntity organizationNewEntity = this.get(TScStockEntity.class,countEntity.getNewId());
				if(organizationOldEntity == null ||organizationNewEntity == null){
					return false;
				}
				if(organizationOldEntity.getCount() == null || organizationOldEntity.getCount() == 0){
					organizationOldEntity.setCount(0);
				}else {
					organizationOldEntity.setCount(organizationOldEntity.getCount() - 1);
				}
				if(organizationNewEntity.getCount() == null || organizationNewEntity.getCount() == 0){
					organizationNewEntity.setCount(1);
				}else {
					organizationNewEntity.setCount(organizationNewEntity.getCount() + 1);
				}
				super.saveOrUpdate(organizationOldEntity);
				super.saveOrUpdate(organizationNewEntity);

			}else if(Globals.COUNT_DEL_TYPE.equals(countEntity.getType())){
				if(StringUtils.isEmpty(countEntity.getOldId())){
					return false;
				}
				TScStockEntity organizationOldEntity = this.get(TScStockEntity.class,countEntity.getOldId());
				if(organizationOldEntity == null){
					return false;
				}
				if(organizationOldEntity.getCount() == null || organizationOldEntity.getCount() == 0){
					organizationOldEntity.setCount(0);
				}else{
					organizationOldEntity.setCount(organizationOldEntity.getCount() - 1);
				}
				super.saveOrUpdate(organizationOldEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}