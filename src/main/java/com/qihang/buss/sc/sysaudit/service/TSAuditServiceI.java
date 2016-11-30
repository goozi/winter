
package com.qihang.buss.sc.sysaudit.service;
import com.qihang.buss.sc.sysaudit.entity.TSAuditEntity;
import com.qihang.buss.sc.sysaudit.entity.TSAuditLeaveEntity;

import java.util.List;
import java.util.Map;

import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TSBillInfoEntity;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.web.system.pojo.base.TSUser;

public interface TSAuditServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TSAuditEntity tSAudit,
	        List<TSAuditLeaveEntity> tSAuditLeaveList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TSAuditEntity tSAudit,
	        List<TSAuditLeaveEntity> tSAuditLeaveList);
	public void delMain (TSAuditEntity tSAudit);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TSAuditEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TSAuditEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TSAuditEntity t);

	/**
	 * 校验是否进行审核
	 * @param tranType
	 * @param userId
	 * @return
	 */
	public AjaxJson checkIsAudit(String tranType, String userId,String billId,String roleCode,String sonId);

	/**
	 *
	 * @param entity
	 * @param user
	 * @return
	 */
	public AjaxJson saveAuditInfo(TSAuditRelationEntity entity, TSUser user,String entityName,String sonId);

	/**
	 * 判断单据是否可编辑
	 * @param billId
	 * @param tranType
	 * @return
	 */
	public AjaxJson checkIsEdit(String billId, String tranType,String userId);

	/**
	 * 反审核功能
	 * @param entity
	 * @param userId
	 * @return
	 */
	public  AjaxJson unAudit(TSAuditRelationEntity entity,String userId,String entityName,String sonId);

	/**
	 * 获取审核级别数据
	 * @param billId
	 * @param tranType
	 * @param userId
	 * @return
	 */
	public List<Map<String,Object>> getLeaveInfo(String billId, String tranType,String userId,String isSub,String sonId);

	/**
	 * 判断是否多级审核
	 * @param tranType
	 * @return
	 */
	public AjaxJson checkIsMore(String tranType,String sonId);

	/**
	 * 快速审核
	 * @param tranType
	 * @param billId
	 * @return
	 */
	public AjaxJson auditBill(String tranType, String billId,String userId,String userName,String entityName,String sonId);

	/**
	 * 快速反审核
	 * @param tranType
	 * @param billId
	 * @return
	 */
	public AjaxJson unAuditBill(String tranType, String billId,String entityName,String sonId);

	/**
	 * 审核前负库存校验
	 * @param id
	 * @param tranType
	 * @return
	 */
	public AjaxJson checkIsNegative(String id, String tranType,String tableName,String parentId);

}
