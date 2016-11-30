package com.qihang.winter.web.system.service.impl;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;

import com.qihang.winter.web.system.pojo.base.TSTreeinfoEntity;
import com.qihang.winter.web.system.pojo.base.TSTreeinfoEntryEntity;
import com.qihang.winter.web.system.service.TSTreeinfoServiceI;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.oConvertUtils;

import java.util.UUID;


@Service("tSTreeinfoService")
@Transactional
public class TSTreeinfoServiceImpl extends CommonServiceImpl implements TSTreeinfoServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TSTreeinfoEntryEntity) entity);
 	}
	
	public void addMain(TSTreeinfoEntity tSTreeinfo,
	        List<TSTreeinfoEntryEntity> tSTreeinfoEntryList){
			//保存主信息
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			tSTreeinfo.setCreateDate(sdf.parse(sdf.format(new Date())));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		this.save(tSTreeinfo);
		
			/**保存-流程树配置*/
			for(TSTreeinfoEntryEntity tSTreeinfoEntry:tSTreeinfoEntryList){
				//外键设置
				try {
					tSTreeinfoEntry.setCreateDate(sdf.parse(sdf.format(new Date())));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				tSTreeinfoEntry.setParentId(tSTreeinfo.getId());
				this.save(tSTreeinfoEntry);
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tSTreeinfo);
	}

	
	public void updateMain(TSTreeinfoEntity tSTreeinfo,
	        List<TSTreeinfoEntryEntity> tSTreeinfoEntryList) {
		//保存主表信息
		this.saveOrUpdate(tSTreeinfo);
		//===================================================================================
		//获取参数
		Object id0 = tSTreeinfo.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-流程树配置
	    String hql0 = "from TSTreeinfoEntryEntity where 1 = 1 AND pARENT_ID = ? ";
	    List<TSTreeinfoEntryEntity> tSTreeinfoEntryOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-流程树配置
		for(TSTreeinfoEntryEntity oldE:tSTreeinfoEntryOldList){
			boolean isUpdate = false;
				for(TSTreeinfoEntryEntity sendE:tSTreeinfoEntryList){
					//需要更新的明细数据-流程树配置
					if(oldE.getId().equals(sendE.getId())){
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE, oldE);
							oldE.setUpdateDate(new Date());
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
		    		//如果数据库存在的明细，前台没有传递过来则是删除-流程树配置
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-流程树配置
			for(TSTreeinfoEntryEntity tSTreeinfoEntry:tSTreeinfoEntryList){
				if(oConvertUtils.isEmpty(tSTreeinfoEntry.getId())){
					//外键设置
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					tSTreeinfoEntry.setParentId(tSTreeinfo.getId());
					try {
						tSTreeinfoEntry.setCreateDate(sdf.parse(sdf.format(new Date())));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					this.save(tSTreeinfoEntry);
				}
			}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tSTreeinfo);
	}

	
	public void delMain(TSTreeinfoEntryEntity tSTreeinfo) {
		//删除主表信息
		this.delete(tSTreeinfo);
		//===================================================================================
		//获取参数
		Object id0 = tSTreeinfo.getId();
		//===================================================================================
		//删除-流程树配置
//	    String hql0 = "from TSTreeinfoEntryEntity where 1 = 1 AND pARENT_ID = ? ";
//	    List<TSTreeinfoEntryEntity> tSTreeinfoEntryOldList = this.findHql(hql0,id0);
//		this.deleteAllEntitie(tSTreeinfoEntryOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TSTreeinfoEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TSTreeinfoEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TSTreeinfoEntryEntity t){
	 	return true;
 	}

	@Override
	public String getActNameById(String actId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select NAME_ from ACT_RE_PROCDEF where ID_ like '"+actId+"%' ");
		List<Object> values = this.findListbySql(sql.toString());
		String name = "";
		if(values.size() > 0){
			name = (String) values.get(0);
		}
		return name;
	}

	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TSTreeinfoEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{tree_name}",String.valueOf(t.getTreeName()));
 		sql  = sql.replace("#{act_id}",String.valueOf(t.getActId()));
 		sql  = sql.replace("#{note}",String.valueOf(t.getNote()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}