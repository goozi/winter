
package com.qihang.buss.sc.sysaudit.service.impl;
import com.qihang.buss.sc.baseinfo.entity.TScIcitemEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryBatchnoEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryBatchnoViewEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryEntity;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryViewEntity;
import com.qihang.buss.sc.sales.entity.TScPoOrderentryEntity;
import com.qihang.buss.sc.sales.entity.TScPoStockBillViewEntity;
import com.qihang.buss.sc.sales.entity.TScPoStockbillentryEntity;
import com.qihang.buss.sc.sysaudit.entity.*;
import com.qihang.buss.sc.sysaudit.service.TSAuditServiceI;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;

import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.web.system.pojo.base.TSUser;
import org.apache.commons.lang3.StringUtils;
import org.apache.xpath.operations.Bool;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.oConvertUtils;


@Service("tSAuditService")
@Transactional
public class TSAuditServiceImpl extends CommonServiceImpl implements TSAuditServiceI {

	@Autowired
	private SessionFactory sessionFactory;

 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		//this.doDelSql((TSAuditEntity) entity);
 	}
	
	public void addMain(TSAuditEntity tSAudit,
	        List<TSAuditLeaveEntity> tSAuditLeaveList){
			//保存主信息
			this.save(tSAudit);
		
			/**保存-多级审核分级信息*/
			for(TSAuditLeaveEntity tSAuditLeave:tSAuditLeaveList){
				//外键设置
				tSAuditLeave.setPid(tSAudit.getId());
				this.save(tSAuditLeave);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tSAudit);
	}

	
	public void updateMain(TSAuditEntity tSAudit,
	        List<TSAuditLeaveEntity> tSAuditLeaveList) {
		//保存主表信息
		this.saveOrUpdate(tSAudit);
		//===================================================================================
		//获取参数
		Object id0 = tSAudit.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-多级审核分级信息
	    String hql0 = "from TSAuditLeaveEntity where 1 = 1 AND pID = ? ";
	    List<TSAuditLeaveEntity> tSAuditLeaveOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-多级审核分级信息
		for(TSAuditLeaveEntity oldE:tSAuditLeaveOldList){
			boolean isUpdate = false;
				for(TSAuditLeaveEntity sendE:tSAuditLeaveList){
					//需要更新的明细数据-多级审核分级信息
					if(oldE.getId().equals(sendE.getId())){
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
							this.saveOrUpdate(oldE);
						} catch (Exception e) {
							e.printStackTrace();
							throw new BusinessException(e.getMessage());
						}
						isUpdate= true;
		    			break;
		    		}
		    	}
	    		if(!isUpdate){
		    		//如果数据库存在的明细，前台没有传递过来则是删除-多级审核分级信息
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-多级审核分级信息
			for(TSAuditLeaveEntity tSAuditLeave:tSAuditLeaveList){
				if(oConvertUtils.isEmpty(tSAuditLeave.getId())){
					//外键设置
					tSAuditLeave.setPid(tSAudit.getId());
					this.save(tSAuditLeave);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tSAudit);
	}

	
	public void delMain(TSAuditEntity tSAudit) {
		//删除主表信息
		this.delete(tSAudit);
		//===================================================================================
		//获取参数
		Object id0 = tSAudit.getId();
		//===================================================================================
		//删除-多级审核分级信息
	    String hql0 = "from TSAuditLeaveEntity where 1 = 1 AND pID = ? ";
	    List<TSAuditLeaveEntity> tSAuditLeaveOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(tSAuditLeaveOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TSAuditEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TSAuditEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TSAuditEntity t){
	 	return true;
 	}

	@Override
	public AjaxJson checkIsAudit(String tranType, String userId,String billId,String roleCode,String sonId) {
		AjaxJson j = new AjaxJson();
		List<TSAuditEntity> auditEntityList = this.findHql("from TSAuditEntity where billId = ? and sonId = ?", new Object[]{tranType, sonId});
		for(TSAuditEntity auditEntity : auditEntityList) {
			//TSAuditEntity auditEntity = this.findUniqueByProperty(TSAuditEntity.class, "billId", tranType);
			if (null != auditEntity) {
				List<TSAuditLeaveEntity> leaves = findHql("from TSAuditLeaveEntity where pid = ? order by leaveNum asc", new Object[]{auditEntity.getId()});
				Boolean isAudit = false;
				if(null != auditEntity.getIsAudit() && 1 == auditEntity.getIsAudit()) {
					if (leaves.size() > 0) {
						for (TSAuditLeaveEntity leaveEntity : leaves) {
							if (StringUtil.isNotEmpty(leaveEntity.getAuditorId())) {
								if (leaveEntity.getAuditorId().indexOf(userId) > -1) {
									isAudit = true;
									break;
								}
							}
						}
					}
				} else {
					isAudit = true;
				}
				Map<String, Object> attribute = new HashMap<String, Object>();
				if(isAudit) {
					List<TSAuditRelationEntity> relationEntities = findHql("from TSAuditRelationEntity where billId=? and tranType = ? order by orderNum desc", new Object[]{billId, tranType});
					if (relationEntities.size() > 0) {
						for (TSAuditRelationEntity entity : relationEntities) {
							if (0 == entity.getIsFinish()) {
								continue;
							} else {
								if (1 == (auditEntity.getIsSort() == null ? 0 : auditEntity.getIsSort())) {
									for (TSAuditLeaveEntity leaveEntity : leaves) {
										if (leaveEntity.getAuditorId().indexOf(userId) > -1 && leaveEntity.getLeaveNum().equals(entity.getStatus().toString())) {
											attribute.put("isBack", "1");
											attribute.put("isSub", "0");
											attribute.put("isAudit",isAudit);
											j.setAttributes(attribute);
											return j;
										}
									}
								} else {
									attribute.put("isBack", "1");
									attribute.put("isSub", "0");
									attribute.put("isAudit",isAudit);
									j.setAttributes(attribute);
									return j;
								}
							}
						}
						//判断是否反审核
						if (j.isSuccess()) {
							Integer status = relationEntities.get(0).getStatus();
							for (TSAuditLeaveEntity leaveEntity : leaves) {
								String leaveNum = leaveEntity.getLeaveNum();
								if (status.toString().equals(leaveNum)) {
									String auditorId = leaveEntity.getAuditorId();
									if (auditorId.indexOf(userId) > -1 && userId.equals(relationEntities.get(0).getAuditorId())) {
										attribute.put("isBack", "1");
									} else {
										attribute.put("isBack", "0");
									}
								}
							}
						}
					}
					//if(j.isSuccess()) {
					if (null != auditEntity && null != auditEntity.getIsAudit() && 1 == auditEntity.getIsAudit()) {
						if (null != auditEntity.getIsSort() && 1 == auditEntity.getIsSort()) {
							relationEntities = findHql("from TSAuditRelationEntity where billId=? and tranType = ? and isFinish = 0 order by orderNum desc", new Object[]{billId, tranType});
							if (relationEntities.size() > 0) {
								TSAuditRelationEntity relation = relationEntities.get(0);
								Integer status = relation.getStatus() + 1;
								for (TSAuditLeaveEntity leave : leaves) {
									if (status.toString().equals(leave.getLeaveNum())) {
										String userIds = leave.getAuditorId();
										if (userIds.indexOf(userId) > -1) {
											attribute.put("isSub", "1");
											break;
										} else {
											continue;
										}
									}
								}
								attribute.put("status", status);
							} else {
								TSAuditLeaveEntity leave = leaves.get(0);
								String userIds = leave.getAuditorId();
								attribute.put("status", 1);
								if (userIds.indexOf(userId) > -1) {
									attribute.put("isSub", "1");
								} else {
									attribute.put("isSub", "0");
								}
							}
						} else {
							int i = 0;
							for (TSAuditLeaveEntity leaveEntity : leaves) {
								String userIds = leaveEntity.getAuditorId();
								if (userId.indexOf(userId) > -1) {
									attribute.put("isSub", "1");
									attribute.put("status", i + 1);
								} else {
									attribute.put("isSub", "0");
								}
								i++;
							}
						}
					} else {
						if (roleCode.indexOf("sc_allowAudit") > -1) {
							attribute.put("isSub", "1");
						} else {
							attribute.put("isSub", "0");
						}
					}
					//}
					attribute.put("isAudit",isAudit);
					j.setAttributes(attribute);
				} else {
					attribute.put("isAudit",isAudit);
					j.setAttributes(attribute);
				}
			}
		}
		return j;
	}

	@Override
	public AjaxJson saveAuditInfo(TSAuditRelationEntity entity, TSUser user,String entityName,String sonId) {
		AjaxJson j = new AjaxJson();
		List<TSAuditRelationEntity> entityList = this.findHql("from TSAuditRelationEntity where billId = ? and tranType = ? and isFinish = 0 and status > 0 and sonId=? order by orderNum desc", new Object[]{entity.getBillId(), entity.getTranType(),sonId});
		TSAuditRelationEntity relationEntity = new TSAuditRelationEntity();
		for(TSAuditRelationEntity relation : entityList){
			if(relation.getStatus() == entity.getStatus()){
				entity.setId(relation.getId());
				entity.setDeleted(0);
				relationEntity = relation;
			}
		}
		List<TSAuditEntity> auditEntityList = this.findHql("from TSAuditEntity where billId = ? and sonId = ?", new Object[]{entity.getTranType(), sonId});
		if(auditEntityList.size() > 0) {
			TSAuditEntity auditEntity = auditEntityList.get(0);
			Integer auditLeave = 0;
			if (null != auditEntity) {
				List<TSAuditLeaveEntity> leaves = this.findHql("from TSAuditLeaveEntity where pid = ?", new Object[]{auditEntity.getId()});
				auditLeave = leaves.size();
			}

			entity.setAuditorId(user.getId());
			entity.setAuditorName(user.getRealName());
			entity.setAuditDate(new Date());
			entity.setDeleted(0);
			entity.setIsBack(0);
			entity.setSonId(sonId);
			Integer newState = 1;
			if (auditLeave == entity.getStatus()) {
				entity.setIsFinish(1);
				newState = 2;
			} else {
				entity.setIsFinish(0);
			}
			entity.setVersion(0);
			if (entityList.size() > 0) {
				TSAuditRelationEntity entity1 = entityList.get(0);
				if (1 == (auditEntity.getIsSort() == null ? 0 : auditEntity.getIsSort())) {
					Integer status = entity1.getStatus();
					if (entity.getStatus() != status + 1 && StringUtils.isEmpty(entity.getId())) {
						j.setMsg("需要逐级审核该单据，请选择其他审核级别");
						return j;
					}
				}
				if (StringUtils.isEmpty(entity.getId())) {
					entity.setOrderNum(entityList.size() + 1);
				}
			} else {
				if (1 == (auditEntity.getIsSort() == null ? 0 : auditEntity.getIsSort())) {
					if (entity.getStatus() != 1) {
						j.setMsg("需要逐级审核该单据，请选择其他审核级别");
						return j;
					}
				}
				entity.setOrderNum(1);
			}
			try {
				if (StringUtils.isEmpty(entity.getId())) {
					this.save(entity);
				} else {
					MyBeanUtils.copyBeanNotNull2Bean(entity, relationEntity);
					this.saveOrUpdate(relationEntity);
				}
				if (entity.getIsFinish() == 1) {
					if (StringUtils.isNotEmpty(entityName) && !"null".equals(entityName)) {
						updateBySqlString("update " + entityName + " set checkState=2,checkerId='" + user.getId() + "',checkDate=now() where id = '" + entity.getBillId() + "'");
//					//TODO 更新即时库存
//					List<Object> billInfo = this.findListbySql("select * from "+entityName+" where id = '"+entity.getBillId()+"'");
					}
				} else {
					if (StringUtils.isNotEmpty(entityName) && !"null".equals(entityName)) {
						String update = "update " + entityName + " set checkState=1,checkerId='" + user.getId() + "',checkDate=now() where id = '" + entity.getBillId() + "'";
						updateBySqlString(update);
//					//TODO 更新即时库存
//					List<Object> billInfo = this.findListbySql("select * from "+entityName+" where id = '"+entity.getBillId()+"'");
					}
				}
				j.setMsg("审核成功");
				List<TScBillAuditStatusEntity> tScBillAuditStatusEntityList = this.findHql("from TScBillAuditStatusEntity where sonId=? and tranType = ? and billId=?",new Object[]{sonId,entity.getTranType(),entity.getBillId()});
				if(0==entity.getIsFinish()){
					if(tScBillAuditStatusEntityList.size() > 0){
						TScBillAuditStatusEntity billAuditStatusEntity = tScBillAuditStatusEntityList.get(0);
						billAuditStatusEntity.setStatus(billAuditStatusEntity.getStatus()+1);
						super.saveOrUpdate(billAuditStatusEntity);
					} else {
						TScBillAuditStatusEntity billAuditStatusEntity = new TScBillAuditStatusEntity();
						billAuditStatusEntity.setStatus(2);
						billAuditStatusEntity.setSonId(sonId);
						billAuditStatusEntity.setTranType(entity.getTranType());
						billAuditStatusEntity.setBillId(entity.getBillId());
						super.save(billAuditStatusEntity);
					}
				} else {
					if(tScBillAuditStatusEntityList.size() > 0){
						for(TScBillAuditStatusEntity billAuditStatusEntity : tScBillAuditStatusEntityList){
							super.delete(billAuditStatusEntity);
						}
					}
				}
				//记录已审核单据
				List<TScAuditBillInfoEntity> auditBillInfoEntityList = this.findHql("from TScAuditBillInfoEntity where sonId=? and tranType=? and billId=?",new Object[]{sonId,entity.getTranType(),entity.getBillId()});
				if(auditBillInfoEntityList.size() == 0) {
					TScAuditBillInfoEntity auditBillInfoEntity = new TScAuditBillInfoEntity();
					auditBillInfoEntity.setBillId(entity.getBillId());
					auditBillInfoEntity.setSonId(sonId);
					auditBillInfoEntity.setTranType(entity.getTranType());
					auditBillInfoEntity.setOldState(1);
					auditBillInfoEntity.setNewState(newState);
					super.save(auditBillInfoEntity);
				} else {
					TScAuditBillInfoEntity auditBillInfoEntity = auditBillInfoEntityList.get(0);
					auditBillInfoEntity.setOldState(1);
					auditBillInfoEntity.setNewState(newState);
					super.saveOrUpdate(auditBillInfoEntity);
				}
			} catch (Exception e) {
				j.setMsg("审核失败：" + e.getMessage());
			}
		}
		return j;
	}

	//判断单据是否可编辑
	@Override
	public AjaxJson checkIsEdit(String billId, String tranType,String userId) {
		AjaxJson j = new AjaxJson();
		List<TSAuditRelationEntity> relationEntities = this.findHql("from TSAuditRelationEntity where billId = ? and tranType = ? and status > 0 and isFinish = 1 and deleted=0", new Object[]{billId, tranType});
		if(relationEntities.size() > 0){
			j.setSuccess(false);
			j.setMsg("单据已审核，不可编辑");
		}else{
			relationEntities = this.findHql("from TSAuditRelationEntity where billId = ? and tranType = ? and isFinish = 0 and status > 0 and deleted=0 order by orderNum desc",new Object[]{billId,tranType});
			if(relationEntities.size() > 0) {
//				TSAuditRelationEntity relationEntity = relationEntities.get(0);
//				Integer status = relationEntity.getStatus()+1;
//				TSAuditEntity entity = this.findUniqueByProperty(TSAuditEntity.class, "billId", tranType);
//				if(null != entity){
//					List<TSAuditLeaveEntity> leaveEntityList = this.findHql("from TSAuditLeaveEntity where pid = ?",new Object[]{entity.getId()});
//					for(TSAuditLeaveEntity leaveEntity : leaveEntityList){
//						if(status.toString().equals(leaveEntity.getLeaveNum())) {
//							String auditorIds = leaveEntity.getAuditorId();
//							if (auditorIds.indexOf(userId) < 0) {
				j.setSuccess(false);
				j.setMsg("单据正在审核中，不可编辑");
			}
//							}else{
//								j.setSuccess(true);
//							}
//						}
//					}
//				}
//			}else {
//				j.setSuccess(true);
//			}
		}
		return j;
	}

	/**
	 * 反审核功能
	 * @param relation
	 * @param userId
	 * @return
	 */
	@Override
	public AjaxJson unAudit(TSAuditRelationEntity relation,String userId,String entityName,String sonId) {
		AjaxJson j = new AjaxJson();
		//获取多级审核设置数据
		List<TSAuditEntity> auditEntityList = this.findHql("from TSAuditEntity where billId = ? and sonId = ?", new Object[]{relation.getTranType(), sonId});
		for(TSAuditEntity entity : auditEntityList) {
			//逐级审核
			if (1 == (entity.getIsSort() == null ? 0 : entity.getIsSort())) {
				List<TSAuditRelationEntity> relationEntityList = this.findHql("from TSAuditRelationEntity where billId = ? and tranType = ? and status > 0 and deleted = 0 and sonId = ? order by orderNum desc", new Object[]{relation.getBillId(), relation.getTranType(),sonId});
				if (relationEntityList.size() > 0) {
					TSAuditRelationEntity relationEntity = relationEntityList.get(0);
					if (relationEntity.getStatus() == relation.getStatus()) {
						if (userId.equals(relationEntity.getAuditorId())) {
							if (relation.getStatus() > 1) {
								relationEntity.setDeleted(1);
								relationEntity.setAuditInfo(relation.getAuditInfo());
								relationEntity.setAuditDate(new Date());
								saveOrUpdate(relationEntity);
								if (StringUtils.isNotEmpty(entityName) && !"null".equals(entityName)) {
									updateBySqlString("update " + entityName + " set checkState=1,checkerId='" + userId + "',checkDate=now() where id = '" + relation.getBillId() + "'");
									//TODO 更新即时库存
								}
								List<TScBillAuditStatusEntity> tScBillAuditStatusEntityList = this.findHql("from TScBillAuditStatusEntity where sonId=? and tranType = ? and billId=?",new Object[]{sonId,relation.getTranType(),relation.getBillId()});
								if(tScBillAuditStatusEntityList.size() > 0){
									TScBillAuditStatusEntity billAuditStatusEntity = tScBillAuditStatusEntityList.get(0);
									billAuditStatusEntity.setStatus(billAuditStatusEntity.getStatus()-1);
									super.saveOrUpdate(billAuditStatusEntity);
								} else {
									TScBillAuditStatusEntity billAuditStatusEntity = new TScBillAuditStatusEntity();
									billAuditStatusEntity.setStatus(relationEntity.getStatus());
									billAuditStatusEntity.setBillId(relationEntity.getBillId());
									billAuditStatusEntity.setTranType(relationEntity.getTranType());
									billAuditStatusEntity.setSonId(sonId);
									super.save(billAuditStatusEntity);
								}
							} else {
								relationEntityList = this.findHql("from TSAuditRelationEntity where billId = ? and tranType = ? and status > 0 and sonId=? order by orderNum desc", new Object[]{relation.getBillId(), relation.getTranType(),sonId});
								Integer state = 0;
								if (relationEntityList.get(0).getIsFinish() == 0) {
									state = -1;
								}
								for (TSAuditRelationEntity delRelation : relationEntityList) {
									delRelation.setStatus(state);
									delRelation.setDeleted(1);
									if (state < 0) {
										delRelation.setOrderNum(0);
									}
									saveOrUpdate(delRelation);
								}
								if (StringUtils.isNotEmpty(entityName) && !"null".equals(entityName)) {
									updateBySqlString("update " + entityName + " set checkState=0,checkerId='',checkDate=null where id = '" + relation.getBillId() + "'");
								}
								List<TScBillAuditStatusEntity> tScBillAuditStatusEntityList = this.findHql("from TScBillAuditStatusEntity where sonId=? and tranType = ? and billId=?",new Object[]{sonId,relation.getTranType(),relation.getBillId()});
								for(TScBillAuditStatusEntity billAuditStatusEntity : tScBillAuditStatusEntityList){
									billAuditStatusEntity.setStatus(billAuditStatusEntity.getStatus()-1);
									super.saveOrUpdate(billAuditStatusEntity);
								}
							}
						} else {
							j.setMsg("您没有反审核该单据的权限，请确认");
							return j;
						}
					} else {
						j.setMsg("您没有反审核该单据的权限，请确认");
						return j;
					}
				} else {
					j.setMsg("该单据已反审核，不可再次进行此操作");
					return j;
				}
				//}
				//更新已审核单据记录
				List<TScAuditBillInfoEntity> auditBillInfoEntity = this.findHql("from TScAuditBillInfoEntity where sonId=? and tranType=? and billId=?",new Object[]{sonId,relation.getTranType(),relation.getBillId()});
				if(auditBillInfoEntity.size() > 0){
					for(TScAuditBillInfoEntity info : auditBillInfoEntity){
						if(info.getNewState() == 2){
							info.setOldState(2);
						} else {
							info.setOldState(1);
						}
						//设置新状态为审核中状态
						info.setNewState(1);
						super.saveOrUpdate(info);
					}
				}
				j.setMsg("反审核成功");
				j.setSuccess(true);
			} else {
				//非逐级反审核
				//多级审核信息
				List<TSAuditLeaveEntity> leaveEntityList = this.findHql("from TSAuditLeaveEntity where pid = ? order by leaveNum", new Object[]{entity.getId()});
				Boolean isContinue = false;
				//判断是否有审核权限
				for (TSAuditLeaveEntity leaveEntity : leaveEntityList) {
					String auditorIds = leaveEntity.getAuditorId();
					if (auditorIds.indexOf(userId) > -1) {
						isContinue = true;
					}
				}
				if (isContinue) {
					//获取当前反审核级次数据；
					List<TSAuditRelationEntity> relationEntityList = this.findHql("from TSAuditRelationEntity where billId = ? and tranType = ? and  status = ? and sonId=? ", new Object[]{relation.getBillId(), relation.getTranType(),relation.getStatus(),sonId});
					//该级审核之后的审核数据；
					List<TSAuditRelationEntity> afterRelationEntityList = this.findHql("from TSAuditRelationEntity where billId = ? and tranType = ? and  status > ? and sonId=? ", new Object[]{relation.getBillId(), relation.getTranType(),relation.getStatus(),sonId});
					//最小审核状态数据
					List<TSAuditRelationEntity> minRelationEntityList = this.findHql("from TSAuditRelationEntity where billId = ? and tranType = ? and  status > 0 and sonId=? order by status", new Object[]{relation.getBillId(), relation.getTranType(),sonId});
					Integer minStatus = 1;
					if(minRelationEntityList.size() > 0){
						minStatus = minRelationEntityList.get(0).getStatus();
					}
					if(relationEntityList.size() > 0){
						for(TSAuditRelationEntity relationEntity : relationEntityList){
							if(relationEntity.getStatus() == minStatus){
								relationEntity.setStatus(0);
								super.saveOrUpdate(relationEntity);
								if(afterRelationEntityList.size() > 0){
									for(TSAuditRelationEntity auditRelationEntity : afterRelationEntityList){
										auditRelationEntity.setStatus(0);
										super.saveOrUpdate(auditRelationEntity);
									}
								}
								if (StringUtils.isNotEmpty(entityName) && !"null".equals(entityName)) {
									updateBySqlString("update " + entityName + " set checkState=0,checkerId='',checkDate=null where id = '" + relation.getBillId() + "'");
								}
							} else {
								relationEntity.setDeleted(1);
								relationEntity.setAuditInfo(relation.getAuditInfo());
								relationEntity.setAuditDate(new Date());
								super.saveOrUpdate(relationEntity);
								if(afterRelationEntityList.size() > 0){
									for(TSAuditRelationEntity auditRelationEntity : afterRelationEntityList){
										auditRelationEntity.setStatus(0);
										super.saveOrUpdate(auditRelationEntity);
									}
								}
								if (StringUtils.isNotEmpty(entityName) && !"null".equals(entityName)) {
									updateBySqlString("update " + entityName + " set checkState=1,checkerId='" + userId + "',checkDate=now() where id = '" + relation.getBillId() + "'");
								}
							}
							//获取审核状态值
							List<TScBillAuditStatusEntity> tScBillAuditStatusEntityList = this.findHql("from TScBillAuditStatusEntity where sonId=? and tranType = ? and billId=?", new Object[]{sonId, relation.getTranType(), relation.getBillId()});
							if (tScBillAuditStatusEntityList.size() > 0) {
								TScBillAuditStatusEntity scBillAuditStatusEntity = tScBillAuditStatusEntityList.get(0);
								scBillAuditStatusEntity.setStatus(relation.getStatus());
								super.saveOrUpdate(scBillAuditStatusEntity);
							} else {
								TScBillAuditStatusEntity billAuditStatusEntity = new TScBillAuditStatusEntity();
								billAuditStatusEntity.setStatus(relation.getStatus());
								billAuditStatusEntity.setBillId(relationEntity.getBillId());
								billAuditStatusEntity.setTranType(relationEntity.getTranType());
								billAuditStatusEntity.setSonId(sonId);
								super.save(billAuditStatusEntity);
							}
						}
					}
					/*//获取已审核数据
					List<TSAuditRelationEntity> relationEntityList = this.findHql("from TSAuditRelationEntity where billId = ? and tranType = ? and  status > 0 and sonId=? order by orderNum desc", new Object[]{relation.getBillId(), relation.getTranType(),sonId});
					Integer state = 0;
					//若单据已审核结束
					if (relationEntityList.get(0).getIsFinish() == 0) {
						state = -1;
					}
					if(relationEntityList.size() > 1) {
						for (TSAuditRelationEntity relation2 : relationEntityList) {
							//已审核数据状态大于当前审核状态
							if (relation2.getStatus() > relation.getStatus()) {
								relation2.setStatus(state);
								if (state < 0) {
									relation2.setOrderNum(0);
								}
								saveOrUpdate(relation2);
								//获取审核状态值
								List<TScBillAuditStatusEntity> tScBillAuditStatusEntityList = this.findHql("from TScBillAuditStatusEntity where sonId=? and tranType = ? and billId=?", new Object[]{sonId, relation.getTranType(), relation.getBillId()});
								if (tScBillAuditStatusEntityList.size() > 0) {
									TScBillAuditStatusEntity scBillAuditStatusEntity = tScBillAuditStatusEntityList.get(0);
									scBillAuditStatusEntity.setStatus(scBillAuditStatusEntity.getStatus() - 1);
									super.saveOrUpdate(scBillAuditStatusEntity);
								} else {
									TScBillAuditStatusEntity billAuditStatusEntity = new TScBillAuditStatusEntity();
									billAuditStatusEntity.setStatus(relation2.getStatus());
									billAuditStatusEntity.setBillId(relation2.getBillId());
									billAuditStatusEntity.setTranType(relation2.getTranType());
									billAuditStatusEntity.setSonId(sonId);
									super.save(billAuditStatusEntity);
								}
							} else if (relation2.getStatus() == relation.getStatus()) {
								if (relation.getStatus() > 1) {
									relation2.setDeleted(1);
									relation2.setAuditInfo(relation.getAuditInfo());
									relation2.setAuditDate(new Date());
									saveOrUpdate(relation2);
									if (StringUtils.isNotEmpty(entityName) && !"null".equals(entityName)) {
										updateBySqlString("update " + entityName + " set checkState=1,checkerId='" + userId + "',checkDate=now() where id = '" + relation.getBillId() + "'");
										//TODO 更新即时库存
									}
									//更新最新审核阶段
									List<TScBillAuditStatusEntity> tScBillAuditStatusEntityList = this.findHql("from TScBillAuditStatusEntity where sonId=? and tranType = ? and billId=?", new Object[]{sonId, relation.getTranType(), relation.getBillId()});
									if (tScBillAuditStatusEntityList.size() > 0) {
										TScBillAuditStatusEntity scBillAuditStatusEntity = tScBillAuditStatusEntityList.get(0);
										scBillAuditStatusEntity.setStatus(scBillAuditStatusEntity.getStatus() - 1);
										super.saveOrUpdate(scBillAuditStatusEntity);
									} else {
										TScBillAuditStatusEntity billAuditStatusEntity = new TScBillAuditStatusEntity();
										billAuditStatusEntity.setStatus(relation2.getStatus());
										billAuditStatusEntity.setBillId(relation2.getBillId());
										billAuditStatusEntity.setTranType(relation2.getTranType());
										billAuditStatusEntity.setSonId(sonId);
										super.save(billAuditStatusEntity);
									}
								} else {
									relation2.setStatus(state);
									if (state < 0) {
										relation2.setOrderNum(0);
									}
									saveOrUpdate(relation2);
									if (StringUtils.isNotEmpty(entityName) && !"null".equals(entityName)) {
										updateBySqlString("update " + entityName + " set checkState=0,checkerId='',checkDate=null where id = '" + relation.getBillId() + "'");
										//TODO 更新即时库存
									}
									//清楚审核阶段数据
									List<TScBillAuditStatusEntity> tScBillAuditStatusEntityList = this.findHql("from TScBillAuditStatusEntity where sonId=? and tranType = ? and billId=?", new Object[]{sonId, relation.getTranType(), relation.getBillId()});
									if (tScBillAuditStatusEntityList.size() > 0) {
										TScBillAuditStatusEntity scBillAuditStatusEntity = tScBillAuditStatusEntityList.get(0);
										scBillAuditStatusEntity.setStatus(1);
										super.saveOrUpdate(scBillAuditStatusEntity);
									} else {
										TScBillAuditStatusEntity billAuditStatusEntity = new TScBillAuditStatusEntity();
										billAuditStatusEntity.setStatus(1);
										billAuditStatusEntity.setBillId(relation2.getBillId());
										billAuditStatusEntity.setTranType(relation2.getTranType());
										billAuditStatusEntity.setSonId(sonId);
										super.save(billAuditStatusEntity);
									}
								}
							}
						}
					} else {
						for (TSAuditRelationEntity relation2 : relationEntityList) {
							relation2.setStatus(state);
							if (state < 0) {
								relation2.setOrderNum(0);
							}
							saveOrUpdate(relation2);
							if (StringUtils.isNotEmpty(entityName) && !"null".equals(entityName)) {
								updateBySqlString("update " + entityName + " set checkState=0,checkerId='',checkDate=null where id = '" + relation.getBillId() + "'");
								//TODO 更新即时库存
							}
							List<TScBillAuditStatusEntity> tScBillAuditStatusEntityList = this.findHql("from TScBillAuditStatusEntity where sonId=? and tranType = ? and billId=?", new Object[]{sonId, relation.getTranType(), relation.getBillId()});
							if (tScBillAuditStatusEntityList.size() > 0) {
								TScBillAuditStatusEntity scBillAuditStatusEntity = tScBillAuditStatusEntityList.get(0);
								scBillAuditStatusEntity.setStatus(1);
								super.saveOrUpdate(scBillAuditStatusEntity);
							} else {
								TScBillAuditStatusEntity billAuditStatusEntity = new TScBillAuditStatusEntity();
								billAuditStatusEntity.setStatus(1);
								billAuditStatusEntity.setBillId(relation2.getBillId());
								billAuditStatusEntity.setTranType(relation2.getTranType());
								billAuditStatusEntity.setSonId(sonId);
								super.save(billAuditStatusEntity);
							}
						}
					}*/
					//}
					//更新已审核单据记录
					List<TScAuditBillInfoEntity> auditBillInfoEntity = this.findHql("from TScAuditBillInfoEntity where sonId=? and tranType=? and billId=?",new Object[]{sonId,relation.getTranType(),relation.getBillId()});
					if(auditBillInfoEntity.size() > 0){
						for(TScAuditBillInfoEntity info : auditBillInfoEntity){
							//设置新状态为审核中状态
							if(info.getNewState() == 2){
								info.setOldState(2);
							} else {
								info.setOldState(1);
							}
							info.setNewState(1);
							super.saveOrUpdate(info);
						}
					}
					j.setMsg("反审核成功");
					j.setSuccess(true);
				} else {
					j.setSuccess(false);
					j.setMsg("您没有反审核该单据的权限，请确认");
					return j;
				}
			}
		}
		return j;
	}

	@Override
	public List<Map<String,Object>> getLeaveInfo(String billId, String tranType,String userId,String isSub,String sonId) {
		List<TSAuditEntity> tsAuditEntityList = this.findHql("from TSAuditEntity where billId=? and sonId = ?", new Object[]{tranType, sonId});
		List<Map<String, Object>> statsInfo = new ArrayList<Map<String, Object>>();
		if(tsAuditEntityList.size() > 0) {
			TSAuditEntity entity = tsAuditEntityList.get(0);
			List<TSAuditRelationEntity> relationEntityList = this.findHql("from TSAuditRelationEntity where billId = ? and tranType = ? and status >0 and deleted = 0 and sonId=? order by orderNum desc", new Object[]{billId, tranType,sonId});
			List<TSAuditLeaveEntity> leaveEntityList = new ArrayList<TSAuditLeaveEntity>();
			Integer status = null;
			if (null != entity) {
				if (relationEntityList.size() > 0) {
					status = relationEntityList.get(0).getStatus();
				} else {
					status = 0;
				}
				if ("1".equals(isSub)) {
					leaveEntityList = this.findHql("from TSAuditLeaveEntity where pid = ? and leaveNum > ? and auditorId like '%" + userId + "%' order by leaveNum ", new Object[]{entity.getId(), status.toString()});
				} else {
					leaveEntityList = this.findHql("from TSAuditLeaveEntity where pid = ? and leaveNum <= ? and auditorId like '%" + userId + "%' order by leaveNum desc", new Object[]{entity.getId(), status.toString()});
				}
			}
			Boolean isFirst = true;
			for (TSAuditLeaveEntity en : leaveEntityList) {
				if (relationEntityList.size() > 0) {
					for (TSAuditRelationEntity relationEntity : relationEntityList) {
						Map<String, Object> map = new HashMap<String, Object>();
						String id = en.getLeaveNum();
						map.put("id", id);
						String text = "";
						switch (Integer.parseInt(id)) {
							case 1:
								text = "第一级";
								break;
							case 2:
								text = "第二级";
								break;
							case 3:
								text = "第三级";
								break;
							case 4:
								text = "第四级";
								break;
							case 5:
								text = "第五级";
								break;
							case 6:
								text = "第六级";
								break;
						}
						map.put("text", text);
						if ("1".equals(isSub)) {
							if (id.equals((status + 1 + ""))) {
								map.put("selected", true);
							}
						} else {
							if (isFirst) {
								map.put("selected", true);
								isFirst = false;
							}
						}
						if ("1".equals(isSub)) {
							statsInfo.add(map);
							break;
						} else {
							if (en.getLeaveNum().equals(relationEntity.getStatus().toString())) {
								statsInfo.add(map);
							}
						}
					}
				} else {
					Map<String, Object> map = new HashMap<String, Object>();
					String id = en.getLeaveNum();
					map.put("id", id);
					String text = "";
					switch (Integer.parseInt(id)) {
						case 1:
							text = "第一级";
							break;
						case 2:
							text = "第二级";
							break;
						case 3:
							text = "第三级";
							break;
						case 4:
							text = "第四级";
							break;
						case 5:
							text = "第五级";
							break;
						case 6:
							text = "第六级";
							break;
					}
					map.put("text", text);
					if ("1".equals(isSub)) {
						if (id.equals((status + 1 + ""))) {
							map.put("selected", true);
						}
					} else {
						if (isFirst) {
							map.put("selected", true);
							isFirst = false;
						}
					}
					statsInfo.add(map);
				}
			}
		}
		return statsInfo;
	}

	//判断是否多级审核
	@Override
	public AjaxJson checkIsMore(String tranType,String sonId) {
		AjaxJson j = new AjaxJson();
		List<TSAuditEntity> tsAuditEntityList = this.findHql("from TSAuditEntity where billId=? and sonId = ?", new Object[]{tranType, sonId});
		if(tsAuditEntityList.size() > 0) {
			TSAuditEntity entity = tsAuditEntityList.get(0);
			if (null != entity && 1 == (entity.getIsAudit() == null ? 0 : entity.getIsAudit())) {
				j.setSuccess(true);
			} else {
				j.setSuccess(false);
			}
		} else {
			j.setSuccess(false);
		}
		return j;
	}

	@Override
	public AjaxJson auditBill(String tranType, String billId,String userId,String userName,String entityName,String sonId) {
		AjaxJson j = new AjaxJson();
		try {
			TSAuditRelationEntity relationEntity = new TSAuditRelationEntity();
			relationEntity.setBillId(billId);
			relationEntity.setTranType(tranType);
			relationEntity.setSonId(sonId);
			List<TSAuditRelationEntity> relationEntityList = this.findByExample(TSAuditRelationEntity.class.getName(),relationEntity);
			relationEntity.setStatus(1);
			relationEntity.setIsFinish(1);
			relationEntity.setAuditorId(userId);
			relationEntity.setAuditorName(userName);
			relationEntity.setAuditDate(new Date());
			relationEntity.setOrderNum(relationEntityList.size() + 1);
			relationEntity.setDeleted(0);
			relationEntity.setIsBack(0);
			this.save(relationEntity);
			if(StringUtils.isNotEmpty(entityName) && !"null".equals(entityName)) {
				updateBySqlString("update " + entityName + " set checkState=2,checkerId='"+userId+"',checkDate=now() where id = '"+billId+"'");
				//TODO 更新即时库存
			}
			//记录已审核数据
			List<TScAuditBillInfoEntity> auditBillInfoEntityList = this.findHql("from TScAuditBillInfoEntity where sonId=? and tranType=? and billId=?",new Object[]{sonId,relationEntity.getTranType(),relationEntity.getBillId()});
			if(auditBillInfoEntityList.size() == 0) {
				TScAuditBillInfoEntity auditBillInfoEntity = new TScAuditBillInfoEntity();
				auditBillInfoEntity.setBillId(relationEntity.getBillId());
				auditBillInfoEntity.setSonId(sonId);
				auditBillInfoEntity.setTranType(relationEntity.getTranType());
				auditBillInfoEntity.setOldState(1);
				auditBillInfoEntity.setNewState(2);
				super.save(auditBillInfoEntity);
			} else {
				TScAuditBillInfoEntity auditBillInfoEntity = auditBillInfoEntityList.get(0);
				auditBillInfoEntity.setNewState(2);
				super.saveOrUpdate(auditBillInfoEntity);
			}
//				if("51".equals(tranType)){
//					List<TScPoOrderentryEntity> entryList = this.findHql("from TScPoOrderentryEntity")
//				}
			j.setMsg("审核成功");
		}catch (Exception e){
			j.setMsg("审核失败："+e.getMessage());
		}
		return j;
	}

	/**
	 * 快速反审核
	 * @param tranType
	 * @param billId
	 * @return
	 */
	@Override
	public AjaxJson unAuditBill(String tranType, String billId,String entityName,String sonId) {
		AjaxJson j = new AjaxJson();
		List<TSAuditRelationEntity> relationEntityList = this.findHql("from TSAuditRelationEntity where billId=? and tranType = ? and status > 0 and sonId=?",new Object[]{billId,tranType,sonId});
		for(TSAuditRelationEntity relationEntity : relationEntityList){
			relationEntity.setStatus(0);
			saveOrUpdate(relationEntity);
		}
		if (StringUtils.isNotEmpty(entityName) && !"null".equals(entityName)) {
			updateBySqlString("update " + entityName + " set checkState=0,checkerId='',checkDate=null where id = '"+billId+"'");
			//TODO 更新即时库存
		}
		//删除已审核单据记录
		List<TScAuditBillInfoEntity> auditBillInfoEntityList = this.findHql("from TScAuditBillInfoEntity where sonId=? and tranType=? and billId=?",new Object[]{sonId,tranType,billId});
		if(auditBillInfoEntityList.size() > 0) {
			for(TScAuditBillInfoEntity info : auditBillInfoEntityList){
				info.setNewState(1);
				info.setOldState(2);
				super.saveOrUpdate(info);
			}
		}
		j.setMsg("反审核成功");
		return j;
	}

	/**
	 * 校验审核（反审核）后是否出现负库存
	 * @param id 单据id
	 * @param tranType 单据类型
	 * @param tableName 明细表名
	 * @param parentId 关联id
	 * @return
	 */
	@Override
	public AjaxJson checkIsNegative(String id, String tranType,String tableName,String parentId) {
		AjaxJson j = new AjaxJson();
		String itemNames = "";
		Boolean isAudit = true;
		String[] tableNames = tableName.split(",");
		for(String tName : tableNames) {
			List<Map<String, Object>> entryList = this.findForJdbc("select * from "+tName+" where "+parentId+" = ?", new Object[]{id}); //this.findHql("from TScPoStockBillViewEntity where id = ?",new Object[]{id});
			for (Map<String, Object> entry : entryList) {
				String itemId = (String) entry.get("itemId");
				String stockId = (String) entry.get("stockId");
				String batchNo = (String) entry.get("batchNo");
				if(StringUtils.isEmpty(batchNo)) {
					List<TScIcInventoryViewEntity> inventoryEntities = this.findHql("from TScIcInventoryViewEntity where itemId = ? and stockId = ?", new Object[]{itemId, stockId});
					if (inventoryEntities.size() > 0) {
						for (TScIcInventoryViewEntity inventoryEntity : inventoryEntities) {
							Double basicQty = inventoryEntity.getBasicQty();
							Double qty = (Double) entry.get("basicQty");
							if ((basicQty - qty) < 0) {
								isAudit = false;
								itemNames += inventoryEntity.getItemName()+"，";
							}
						}
					} else {
						TScIcitemEntity icitemEntity = this.getEntity(TScIcitemEntity.class,itemId);
						itemNames += icitemEntity.getName()+"，";
						isAudit = false;
					}
				}else{
					List<TScIcInventoryBatchnoViewEntity> inventoryEntities = this.findHql("from TScIcInventoryBatchnoViewEntity where itemId = ? and batchNo = ? and stockId = ?", new Object[]{itemId, batchNo,stockId});
					if (inventoryEntities.size() > 0) {
						for (TScIcInventoryBatchnoViewEntity inventoryEntity : inventoryEntities) {
							Double basicQty = inventoryEntity.getBasicQty() == null ? 0.0 : inventoryEntity.getBasicQty();
							Double qty = (Double) entry.get("qty");
							if ((basicQty - qty) < 0) {
								isAudit = false;
								itemNames += inventoryEntity.getItemName()+"，";
							}
						}
					} else {
						TScIcitemEntity icitemEntity = this.getEntity(TScIcitemEntity.class,itemId);
						itemNames += icitemEntity.getName()+"，";
						isAudit = false;
					}
				}
			}
			if (!isAudit) {
				j.setMsg(itemNames);
				j.setSuccess(false);
			}
		}
		return j;
	}
	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TSAuditEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{fun_id}",String.valueOf(t.getFunId()));
 		sql  = sql.replace("#{is_audit}",String.valueOf(t.getIsAudit()));
 		sql  = sql.replace("#{leave_num}",String.valueOf(t.getLeaveNum()));
 		sql  = sql.replace("#{is_sort}",String.valueOf(t.getIsSort()));
 		sql  = sql.replace("#{is_send_message}",String.valueOf(t.getIsSendMessage()));
 		sql  = sql.replace("#{is_unaudit}",String.valueOf(t.getIsUnaudit()));
 		sql  = sql.replace("#{is_money}",String.valueOf(t.getIsMoney()));
 		sql  = sql.replace("#{min_money}",String.valueOf(t.getMinMoney()));
 		sql  = sql.replace("#{is_money_leave}",String.valueOf(t.getIsMoneyLeave()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}