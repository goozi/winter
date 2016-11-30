package com.qihang.buss.sc.baseinfo.service.impl;
import com.qihang.buss.sc.baseinfo.service.TScSupplierServiceI;
import com.qihang.buss.sc.sales.entity.TScCountEntity;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.baseinfo.entity.TScSupplierEntity;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tScSupplierService")
@Transactional
public class TScSupplierServiceImpl extends CommonServiceImpl implements TScSupplierServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScSupplierEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
		TScSupplierEntity entityTemp=(TScSupplierEntity)entity;
		if(StringUtil.isEmpty(entityTemp.getSettleCompany())){
			entityTemp.setSettleCompany(entityTemp.getSettleCompany());
			entityTemp.setCount(0);
			entityTemp.setSettleCompany(entityTemp.getId());
			super.saveOrUpdate(entityTemp);
		}else{
			TScSupplierEntity temp=get(TScSupplierEntity.class,entityTemp.getSettleCompany());
			temp.setCount(temp.getCount() == null ? 1 : temp.getCount().intValue() + 1);
			super.save(temp);
			//执行新增操作配置的sql增强
			this.doAddSql((TScSupplierEntity) entity);
		}

 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
		//TScSupplierEntity newEntity = ((TScSupplierEntity) entity);

		//TScSupplierEntity old = get(TScSupplierEntity.class, newEntity.getId());
		try {
//			//旧数据的引用减一
//			TScSupplierEntity temp= get(TScSupplierEntity.class,old.getSettleCompany());
//			//判断新旧数据的id是否不相等， 不相等才做下面的操作，相等则直接跳过判断
//			if (!temp.getId().equals(old.getId())){
//				if(temp.getCount() == null || temp.getCount() == 0){
//					temp.setCount(0);
//				}else{
//					temp.setCount(temp.getCount().intValue() - 1);
//				}
//			}
//			super.save(temp);
//			if(StringUtil.isNotEmpty(newEntity.getSettleCompany())){
//				//新数据引用加一
//				temp=get(TScSupplierEntity.class,newEntity.getSettleCompany());
//				//判断新旧数据的id是否不相等， 不相等才做下面的操作，相等则直接跳过判断
//				if (!temp.getId().equals(newEntity.getId())){
//					if(temp.getCount() == null ||temp.getCount() == 0){
//						temp.setCount(1);
//					}else{
//						temp.setCount(temp.getCount().intValue() + 1);
//					}
//				}
//				super.save(temp);
//			}
		//	MyBeanUtils.copyBeanNotNull2Bean(newEntity, old);
		} catch (Exception e) {
			e.printStackTrace();

			throw new BusinessException(e.getMessage());
		}

		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		//this.doUpdateSql(old);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScSupplierEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScSupplierEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScSupplierEntity t){
	 	return true;
 	}


	/**
	 * 操作单据的时候修改它的引用次数根据操作的方式
	 * @param countEntity
	 * @return
	 */
	@Override
	public boolean updateSupplierCount(TScCountEntity countEntity) {
		//判断操作的类型 1是新增，2是编辑，3是删除
		try {
			if(Globals.COUNT_ADD_TYPE.equals(countEntity.getType())){
				//oldId是供应商的id,对引用次数进行累加
				if(StringUtils.isEmpty(countEntity.getOldId())){
					return false;
				}
				TScSupplierEntity supplierEntity = this.get(TScSupplierEntity.class,countEntity.getOldId());
				if(supplierEntity == null){
					return false;
				}
				if(supplierEntity.getCount() == null|| supplierEntity.getCount() == 0){
					supplierEntity.setCount(1);
				}else{
					supplierEntity.setCount(supplierEntity.getCount() + 1);
				}
				super.saveOrUpdate(supplierEntity);
			}else if(Globals.COUNT_UPDATE_TYPE.equals(countEntity.getType())){
				//单据引用oldId是旧数据，newId是新数据，对于引用次数进行操作，旧数据-1，修改后的数据+1
				if(StringUtils.isEmpty(countEntity.getOldId())|| StringUtils.isEmpty(countEntity.getNewId())){
					return false;
				}
				TScSupplierEntity supplierOldEntity = this.get(TScSupplierEntity.class,countEntity.getOldId());
				TScSupplierEntity supplierNewEntity = this.get(TScSupplierEntity.class,countEntity.getNewId());
				if(supplierOldEntity == null ||supplierNewEntity == null){
					return false;
				}
				if(supplierOldEntity.getCount() == null || supplierOldEntity.getCount() == 0){
					supplierOldEntity.setCount(0);
				}else {
					supplierOldEntity.setCount(supplierOldEntity.getCount() - 1);
				}
				if(supplierNewEntity.getCount() == null || supplierNewEntity.getCount() == 0){
					supplierNewEntity.setCount(1);
				}else {
					supplierNewEntity.setCount(supplierNewEntity.getCount() + 1);
				}
				super.saveOrUpdate(supplierOldEntity);
				super.saveOrUpdate(supplierNewEntity);

			}else if(Globals.COUNT_DEL_TYPE.equals(countEntity.getType())){
				if(StringUtils.isEmpty(countEntity.getOldId())){
					return false;
				}
				TScSupplierEntity supplierOldEntity = this.get(TScSupplierEntity.class,countEntity.getOldId());
				if(supplierOldEntity == null){
					return false;
				}
				if(supplierOldEntity.getCount() == null || supplierOldEntity.getCount() == 0){
					supplierOldEntity.setCount(0);
				}else{
					supplierOldEntity.setCount(supplierOldEntity.getCount() - 1);
				}
				super.saveOrUpdate(supplierOldEntity);
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
 	public String replaceVal(String sql,TScSupplierEntity t){
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
 		sql  = sql.replace("#{ciqnumber}",String.valueOf(t.getcIQNumber()));
 		sql  = sql.replace("#{city}",String.valueOf(t.getCity()));
 		sql  = sql.replace("#{postalcode}",String.valueOf(t.getPostalCode()));
 		sql  = sql.replace("#{email}",String.valueOf(t.getEmail()));
 		sql  = sql.replace("#{homepage}",String.valueOf(t.getHomePage()));
 		sql  = sql.replace("#{bank}",String.valueOf(t.getBank()));
 		sql  = sql.replace("#{banknumber}",String.valueOf(t.getBankNumber()));
 		sql  = sql.replace("#{licence}",String.valueOf(t.getLicence()));
 		sql  = sql.replace("#{trade}",String.valueOf(t.getTrade()));
 		sql  = sql.replace("#{corperate}",String.valueOf(t.getCorperate()));
 		sql  = sql.replace("#{typeid}",String.valueOf(t.getTypeID()));
 		sql  = sql.replace("#{settlecompany}",String.valueOf(t.getSettleCompany()));
 		sql  = sql.replace("#{stockday}",String.valueOf(t.getStockDay()));
 		sql  = sql.replace("#{bywayday}",String.valueOf(t.getByWayDay()));
 		sql  = sql.replace("#{level}",String.valueOf(t.getLevel()));
 		sql  = sql.replace("#{disabled}",String.valueOf(t.getDisabled()));
 		sql  = sql.replace("#{deleted}",String.valueOf(t.getDeleted()));
 		sql  = sql.replace("#{version}",String.valueOf(t.getVersion()));
 		sql  = sql.replace("#{count}",String.valueOf(t.getCount()));
 		sql  = sql.replace("#{note}",String.valueOf(t.getNote()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}