package com.qihang.buss.sc.baseinfo.service.impl;
import com.qihang.buss.sc.baseinfo.service.TScDepartmentServiceI;
import com.qihang.buss.sc.sales.entity.TScCountEntity;
import com.qihang.buss.sc.util.BillNoGenerate;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.baseinfo.entity.TScDepartmentEntity;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import org.apache.commons.lang.StringUtils;
import com.qihang.winter.core.constant.Globals;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tScDepartmentService")
@Transactional
public class TScDepartmentServiceImpl extends CommonServiceImpl implements TScDepartmentServiceI {

	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScDepartmentEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TScDepartmentEntity) entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TScDepartmentEntity) entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScDepartmentEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScDepartmentEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScDepartmentEntity t){
	 	return true;
 	}

	@Override
	public boolean updateDepartmentCount(TScCountEntity countEntity) {
		//判断操作的类型 1是新增，2是编辑，3是删除
		try {
			if(Globals.COUNT_ADD_TYPE.equals(countEntity.getType())){
				//oldId是供应商的id,对引用次数进行累加
				if(StringUtils.isEmpty(countEntity.getOldId())){
					return false;
				}
				TScDepartmentEntity deptEntity = this.get(TScDepartmentEntity.class,countEntity.getOldId());
				if(deptEntity == null){
					return false;
				}
				if(deptEntity.getCount() == null|| deptEntity.getCount() == 0){
					deptEntity.setCount(1);
				}else{
					deptEntity.setCount(deptEntity.getCount() + 1);
				}
				super.saveOrUpdate(deptEntity);
			}else if(Globals.COUNT_UPDATE_TYPE.equals(countEntity.getType())){
				//单据引用oldId是旧数据，newId是新数据，对于引用次数进行操作，旧数据-1，修改后的数据+1
				if(StringUtils.isEmpty(countEntity.getOldId())|| StringUtils.isEmpty(countEntity.getNewId())){
					return false;
				}
				TScDepartmentEntity deptOldEntity = this.get(TScDepartmentEntity.class,countEntity.getOldId());
				TScDepartmentEntity deptNewEntity = this.get(TScDepartmentEntity.class,countEntity.getNewId());
				if(deptOldEntity == null ||deptNewEntity == null){
					return false;
				}
				if(deptOldEntity.getCount() == null || deptOldEntity.getCount() == 0){
					deptOldEntity.setCount(0);
				}else {
					deptOldEntity.setCount(deptOldEntity.getCount() - 1);
				}
				if(deptNewEntity.getCount() == null || deptNewEntity.getCount() == 0){
					deptNewEntity.setCount(1);
				}else {
					deptNewEntity.setCount(deptNewEntity.getCount() + 1);
				}
				super.saveOrUpdate(deptOldEntity);
				super.saveOrUpdate(deptNewEntity);

			}else if(Globals.COUNT_DEL_TYPE.equals(countEntity.getType())){
				if(StringUtils.isEmpty(countEntity.getOldId())){
					return false;
				}
				TScDepartmentEntity deptOldEntity = this.get(TScDepartmentEntity.class,countEntity.getOldId());
				if(deptOldEntity == null){
					return false;
				}
				if(deptOldEntity.getCount() == null || deptOldEntity.getCount() == 0){
					deptOldEntity.setCount(0);
				}else{
					deptOldEntity.setCount(deptOldEntity.getCount() - 1);
				}
				super.saveOrUpdate(deptOldEntity);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 保存分支机构数据
	 * @param tScDepartment
	 * @param sonId
	 */
	@Override
	public void saveSysDepartInfo(TScDepartmentEntity tScDepartment, String sonId) {
		TSDepart departInfo = this.getEntity(TSDepart.class, sonId);
		//获取编号
		String billNo = BillNoGenerate.getBasicBillNo(sonId,departInfo.getOrgCode());
		TSDepart departEntity = new TSDepart();
		departEntity.setOrgCode(billNo);
		departEntity.setDepartname(tScDepartment.getName());
		departEntity.setShortName(tScDepartment.getShortName());
		departEntity.setShortNumber(tScDepartment.getShortNumber());
		departEntity.setPhone(tScDepartment.getPhone());
		departEntity.setFax(tScDepartment.getFax());
		departEntity.setTSPDepart(departInfo);
		departEntity.setOrgType(Globals.ORG_TYPE_DEPT);
		super.save(departEntity);
		//将新增的分支机构与部门相关联
		tScDepartment.setSonId(sonId);
		this.saveOrUpdate(tScDepartment);
	}

	/**
	 * 替换sql中的变量
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TScDepartmentEntity t){
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
 		sql  = sql.replace("#{manager}",String.valueOf(t.getManager()));
 		sql  = sql.replace("#{phone}",String.valueOf(t.getPhone()));
 		sql  = sql.replace("#{fax}",String.valueOf(t.getFax()));
 		sql  = sql.replace("#{iscreditmgr}",String.valueOf(t.getIsCreditMgr()));
 		sql  = sql.replace("#{creditamount}",String.valueOf(t.getCreditAmount()));
 		sql  = sql.replace("#{disabled}",String.valueOf(t.getDisabled()));
 		sql  = sql.replace("#{level}",String.valueOf(t.getLevel()));
 		sql  = sql.replace("#{count}",String.valueOf(t.getCount()));
 		sql  = sql.replace("#{note}",String.valueOf(t.getNote()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

}