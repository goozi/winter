package com.qihang.winter.web.system.service;


import java.util.List;
import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.web.system.pojo.base.TSTreeinfoEntity;
import com.qihang.winter.web.system.pojo.base.TSTreeinfoEntryEntity;

public interface TSTreeinfoServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TSTreeinfoEntity tSTreeinfo,
						List<TSTreeinfoEntryEntity> tSTreeinfoEntryList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TSTreeinfoEntity tSTreeinfo,
						   List<TSTreeinfoEntryEntity> tSTreeinfoEntryList);
	public void delMain(TSTreeinfoEntryEntity tSTreeinfo);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TSTreeinfoEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TSTreeinfoEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TSTreeinfoEntryEntity t);

	/**
	 * 通过工作流id获取工作流名称
	 * @param actId
	 * @return
	 */
	public String getActNameById(String actId);
}
