
package com.qihang.buss.sc.oa.service.impl;


import com.qihang.buss.sc.oa.entity.TScMessageEntity;
import com.qihang.buss.sc.oa.entity.TScMessageFileEntity;
import com.qihang.buss.sc.oa.entity.TScMessageReceiveEntity;
import com.qihang.buss.sc.oa.service.TScMessageServiceI;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.oConvertUtils;
import com.qihang.winter.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;


@Service("tScMessageService")
@Transactional
public class TScMessageServiceImpl extends CommonServiceImpl implements TScMessageServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScMessageEntity)entity);
 	}


	public void addMain(TScMessageEntity tScMessage,
	        List<TScMessageFileEntity> tScMessageFileList){
		TSUser user = null;
		if(null != tScMessage.getSender()){
			user = super.get(TSUser.class,tScMessage.getSender());
		}

		if(null != user){
			tScMessage.setCreateBy(user.getUserName());
			tScMessage.setCreateName(user.getRealName());
		}
			//保存主信息
			this.save(tScMessage);
		String[] ids;
		List idList = null;
		if(tScMessage != null && tScMessage.getUserId() != null){
			ids = tScMessage.getUserId().split(",");
			idList = Arrays.asList(ids);
			for (int i = 0; i < idList.size(); i++) {
				TScMessageReceiveEntity messageReceiveEntity = new TScMessageReceiveEntity();
				if(null != user){
					messageReceiveEntity.setCreateBy(user.getUserName());
					messageReceiveEntity.setCreateName(user.getRealName());
				}
				messageReceiveEntity.setUserId(idList.get(i).toString());
				messageReceiveEntity.setTitle(tScMessage.getTitle());
				messageReceiveEntity.setReadStatus(0);
				messageReceiveEntity.setContent(tScMessage.getContent());
				messageReceiveEntity.setSender(tScMessage.getCreateName());
				messageReceiveEntity.setMessageId(tScMessage.getId());
				this.save(messageReceiveEntity);
			}
		}
		TScMessageReceiveEntity messageReceiveEntity = new TScMessageReceiveEntity();

			/**保存-消息管理附件表*/
			for(TScMessageFileEntity tScMessageFile:tScMessageFileList){
				if(!oConvertUtils.isEmpty(tScMessageFile.getMessageFile())) {
					//外键设置
					if(null != user){
						tScMessageFile.setCreateBy(user.getUserName());
						tScMessageFile.setCreateName(user.getRealName());
					}
					tScMessageFile.setMessageId(tScMessage.getId());
					this.save(tScMessageFile);
				}
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tScMessage);
	}

	
	public void updateMain(TScMessageEntity tScMessage,
	        List<TScMessageFileEntity> tScMessageFileList) {

		TSUser user = null;
		if(null != tScMessage.getSender()){
			user = super.get(TSUser.class,tScMessage.getSender());
		}

		if(null != user){
			tScMessage.setUpdateBy(user.getUserName());
			tScMessage.setUpdateName(user.getRealName());
		}
		//保存主表信息
		this.saveOrUpdate(tScMessage);
		//===================================================================================
		//获取参数
		Object id0 = tScMessage.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-消息管理附件表
	    String hql0 = "from TScMessageFileEntity where 1 = 1 AND mESSAGE_ID = ? ";
	    List<TScMessageFileEntity> tScMessageFileOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-消息管理附件表
		for(TScMessageFileEntity oldE:tScMessageFileOldList){
			boolean isUpdate = false;
				for(TScMessageFileEntity sendE:tScMessageFileList){
					//需要更新的明细数据-消息管理附件表
					if(oldE.getId().equals(sendE.getId())){
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE, oldE);
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-消息管理附件表
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-消息管理附件表
			for(TScMessageFileEntity tScMessageFile:tScMessageFileList){
				if(oConvertUtils.isEmpty(tScMessageFile.getId())){
					//外键设置
					if(null != user){
						tScMessageFile.setCreateBy(user.getUserName());
						tScMessageFile.setCreateName(user.getRealName());
					}
					tScMessageFile.setMessageId(tScMessage.getId());
					this.save(tScMessageFile);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tScMessage);
	}

	
	public void delMain(TScMessageEntity tScMessage) {
		//删除主表信息
		this.delete(tScMessage);
		//===================================================================================
		//获取参数
		Object id0 = tScMessage.getId();
		//===================================================================================
		//删除-消息管理附件表
	    String hql0 = "from TScMessageFileEntity where 1 = 1 AND mESSAGE_ID = ? ";
	    List<TScMessageFileEntity> tScMessageFileOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(tScMessageFileOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScMessageEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScMessageEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScMessageEntity t){
	 	return true;
 	}

	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScMessageEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{title}",String.valueOf(t.getTitle()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{user_id}",String.valueOf(t.getUserId()));
 		sql  = sql.replace("#{content}",String.valueOf(t.getContent()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}