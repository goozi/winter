package com.qihang.buss.sc.baseinfo.service.impl;
import com.alibaba.druid.util.StringUtils;
import com.qihang.buss.sc.baseinfo.entity.TScStockEntity;
import com.qihang.buss.sc.baseinfo.service.TScMeasureunitServiceI;
import com.qihang.buss.sc.sales.entity.TScCountEntity;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.baseinfo.entity.TScMeasureunitEntity;
import com.qihang.winter.core.constant.Globals;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tScMeasureunitService")
@Transactional
public class TScMeasureunitServiceImpl extends CommonServiceImpl implements TScMeasureunitServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScMeasureunitEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TScMeasureunitEntity) entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TScMeasureunitEntity) entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScMeasureunitEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScMeasureunitEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScMeasureunitEntity t){
	 	return true;
 	}


	/**
	 * 替换sql中的变量
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TScMeasureunitEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{disabled}",String.valueOf(t.getDisabled()));
 		sql  = sql.replace("#{version}",String.valueOf(t.getVersion()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
	@Override
	public boolean updateMeasureunitCount(TScCountEntity countEntity) {
//判断操作的类型 1是新增，2是编辑，3是删除
		try {
			if(Globals.COUNT_ADD_TYPE.equals(countEntity.getType())){
				//oldId是客户的id,对引用次数进行累加
				if(StringUtils.isEmpty(countEntity.getOldId())){
					return false;
				}
				TScMeasureunitEntity scMeasureunitEntity = this.get(TScMeasureunitEntity.class,countEntity.getOldId());
				if(scMeasureunitEntity == null){
					return false;
				}
				if(scMeasureunitEntity.getCount() == null|| scMeasureunitEntity.getCount() == 0){
					scMeasureunitEntity.setCount(1);
				}else{
					scMeasureunitEntity.setCount(scMeasureunitEntity.getCount() + 1);
				}
				super.saveOrUpdate(scMeasureunitEntity);
			}else if(Globals.COUNT_UPDATE_TYPE.equals(countEntity.getType())){
				//单据引用oldId是旧数据，newId是新数据，对于引用次数进行操作，旧数据-1，修改后的数据+1
				if(StringUtils.isEmpty(countEntity.getOldId())|| StringUtils.isEmpty(countEntity.getNewId())){
					return false;
				}
				TScMeasureunitEntity measureunitOldEntity = this.get(TScMeasureunitEntity.class,countEntity.getOldId());
				TScMeasureunitEntity measureunitNewEntity = this.get(TScMeasureunitEntity.class,countEntity.getNewId());
				if(measureunitOldEntity == null ||measureunitNewEntity == null){
					return false;
				}
				if(measureunitOldEntity.getCount() == null || measureunitOldEntity.getCount() == 0){
					measureunitOldEntity.setCount(0);
				}else {
					measureunitOldEntity.setCount(measureunitOldEntity.getCount() - 1);
				}
				if(measureunitNewEntity.getCount() == null || measureunitNewEntity.getCount() == 0){
					measureunitNewEntity.setCount(1);
				}else {
					measureunitNewEntity.setCount(measureunitNewEntity.getCount() + 1);
				}
				super.saveOrUpdate(measureunitOldEntity);
				super.saveOrUpdate(measureunitNewEntity);

			}else if(Globals.COUNT_DEL_TYPE.equals(countEntity.getType())){
				if(StringUtils.isEmpty(countEntity.getOldId())){
					return false;
				}
				TScMeasureunitEntity measureunitOldEntity = this.get(TScMeasureunitEntity.class,countEntity.getOldId());
				if(measureunitOldEntity == null){
					return false;
				}
				if(measureunitOldEntity.getCount() == null || measureunitOldEntity.getCount() == 0){
					measureunitOldEntity.setCount(0);
				}else{
					measureunitOldEntity.setCount(measureunitOldEntity.getCount() - 1);
				}
				super.saveOrUpdate(measureunitOldEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}