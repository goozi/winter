package com.qihang.buss.sc.baseinfo.service.impl;
import com.qihang.buss.sc.baseinfo.service.TScSoncompanyServiceI;
import com.qihang.buss.sc.sales.entity.TScCountEntity;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.baseinfo.entity.TScSoncompanyEntity;
import com.qihang.winter.core.constant.Globals;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tScSoncompanyService")
@Transactional
public class TScSoncompanyServiceImpl extends CommonServiceImpl implements TScSoncompanyServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScSoncompanyEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TScSoncompanyEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TScSoncompanyEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScSoncompanyEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScSoncompanyEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScSoncompanyEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TScSoncompanyEntity t){
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
 		sql  = sql.replace("#{shortname}",String.valueOf(t.getShortName()));
 		sql  = sql.replace("#{shortnumber}",String.valueOf(t.getShortNumber()));
 		sql  = sql.replace("#{regionid}",String.valueOf(t.getRegionID()));
 		sql  = sql.replace("#{contact}",String.valueOf(t.getContact()));
 		sql  = sql.replace("#{mobilephone}",String.valueOf(t.getMobilePhone()));
 		sql  = sql.replace("#{phone}",String.valueOf(t.getPhone()));
 		sql  = sql.replace("#{fax}",String.valueOf(t.getFax()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{ciqnumber}",String.valueOf(t.getCiqNumber()));
 		sql  = sql.replace("#{city}",String.valueOf(t.getCity()));
 		sql  = sql.replace("#{postalcode}",String.valueOf(t.getPostalCode()));
 		sql  = sql.replace("#{email}",String.valueOf(t.getEmail()));
 		sql  = sql.replace("#{homepage}",String.valueOf(t.getHomePage()));
 		sql  = sql.replace("#{bank}",String.valueOf(t.getBank()));
 		sql  = sql.replace("#{banknumber}",String.valueOf(t.getBankNumber()));
 		sql  = sql.replace("#{licence}",String.valueOf(t.getLicence()));
 		sql  = sql.replace("#{trade}",String.valueOf(t.getTrade()));
 		sql  = sql.replace("#{level}",String.valueOf(t.getLevel()));
 		sql  = sql.replace("#{disabled}",String.valueOf(t.getDisabled()));
 		sql  = sql.replace("#{deleted}",String.valueOf(t.getDeleted()));
 		sql  = sql.replace("#{version}",String.valueOf(t.getVersion()));
 		sql  = sql.replace("#{count}",String.valueOf(t.getCount()));
 		sql  = sql.replace("#{note}",String.valueOf(t.getNote()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	/**
	 * 操作单据的时候修改它的引用次数根据操作的方式
	 * @param countEntity
	 * @return
	 */
	@Override
	public boolean updateOrganizationCount(TScCountEntity countEntity) {
		//判断操作的类型 1是新增，2是编辑，3是删除
		try {
			if(Globals.COUNT_ADD_TYPE.equals(countEntity.getType())){
				//oldId是客户的id,对引用次数进行累加
				if(StringUtils.isEmpty(countEntity.getOldId())){
					return false;
				}
				TScSoncompanyEntity soncompanyEntity = this.get(TScSoncompanyEntity.class,countEntity.getOldId());
				if(soncompanyEntity == null){
					return false;
				}
				if(soncompanyEntity.getCount() == null|| soncompanyEntity.getCount() == 0){
					soncompanyEntity.setCount(1);
				}else{
					soncompanyEntity.setCount(soncompanyEntity.getCount() + 1);
				}
				super.saveOrUpdate(soncompanyEntity);
			}else if(Globals.COUNT_UPDATE_TYPE.equals(countEntity.getType())){
				//单据引用oldId是旧数据，newId是新数据，对于引用次数进行操作，旧数据-1，修改后的数据+1
				if(StringUtils.isEmpty(countEntity.getOldId())|| StringUtils.isEmpty(countEntity.getNewId())){
					return false;
				}
				TScSoncompanyEntity organizationOldEntity = this.get(TScSoncompanyEntity.class,countEntity.getOldId());
				TScSoncompanyEntity organizationNewEntity = this.get(TScSoncompanyEntity.class,countEntity.getNewId());
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
				TScSoncompanyEntity organizationOldEntity = this.get(TScSoncompanyEntity.class,countEntity.getOldId());
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