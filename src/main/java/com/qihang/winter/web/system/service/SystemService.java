package com.qihang.winter.web.system.service;

import java.util.List;
import java.util.Set;

import com.qihang.buss.sc.sales.entity.TScIcJhstockbillEntity;
import com.qihang.buss.sc.sys.entity.TScAccountConfigEntity;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.web.system.pojo.base.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author  Zerrion
 *
 */
public interface SystemService extends CommonService {
	/**
 	 * 方法描述:  查询数据字典
 	 * 作    者： yiming.zhang
 	 * 日    期： 2014年5月11日-下午4:22:42
 	 * @param dicTable
 	 * @param dicCode
 	 * @param dicText
 	 * @return 
 	 * 返回类型： List<DictEntity>
 	 */
 	public List<DictEntity> queryDict(String dicTable, String dicCode, String dicText);

	/*
	 *通过字典代码和字典值得到字典名
	 * @Param dicCode 字典代码
	 * @Param typeCode 字典值
	 */
	public String typeName(String dicCode,String typeCode);

	/**
	 * 通过字典代码和字典名称获取字典的ID
	 * @param dicName
	 * @param typeCode
     * @return
     */
	public String typeId(String dicName,String typeCode);
	
	/**
	 * 登陆用户检查
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public TSUser checkUserExits(TSUser user) throws Exception;
	/**
	 * 日志添加
	 * @param LogContent 内容
	 * @param loglevel 级别
	 * @param operatetype 类型
	 */
	public void addLog(String LogContent, Short loglevel,Short operatetype);
	/**
	 * 根据类型编码和类型名称获取Type,如果为空则创建一个
	 * @param typecode
	 * @param typename
	 * @return
	 */
	public TSType getType(String typecode, String typename, TSTypegroup tsTypegroup);
	/**
	 * 根据类型分组编码和名称获取TypeGroup,如果为空则创建一个
	 * @return
	 */
	public TSTypegroup getTypeGroup(String typegroupcode,String typgroupename);
	/**
	 * 根据用户ID 和 菜单Id 获取 具有操作权限的按钮Codes
	 * @param userId
	 * @param functionId
	 * @return
	 */
	public  Set<String> getOperationCodesByUserIdAndFunctionId(String userId,String functionId);
	/**
	 * 根据角色ID 和 菜单Id 获取 具有操作权限的按钮Codes
	 * @param roleId
	 * @param functionId
	 * @return
	 */
	public  Set<String> getOperationCodesByRoleIdAndFunctionId(String roleId,String functionId);
	/**
	 * 根据编码获取字典组
	 * 
	 * @param typegroupCode
	 * @return
	 */
	public TSTypegroup getTypeGroupByCode(String typegroupCode);
	/**
	 * 对数据字典进行缓存
	 */
	public void initAllTypeGroups();
	
	/**
	 * 刷新字典缓存
	 * @param type
	 */
	public void refleshTypesCach(TSType type);
	/**
	 * 刷新字典分组缓存
	 */
	public void refleshTypeGroupCach();
	/**
	 * 刷新菜单
	 * 
	 * @param id
	 */
	public void flushRoleFunciton(String id, TSFunction newFunciton);

    /**
     * 生成组织机构编码
     * @param id 组织机构主键
     * @param pid 组织机构的父级主键
     * @return 组织机构编码
     */
	String generateOrgCode(String id, String pid);
	
	/**
	 * 
	  * getOperationCodesByRoleIdAndruleDataId
	  * 根据角色id 和 菜单Id 获取 具有操作权限的数据规则
	  *
	  * @Title: getOperationCodesByRoleIdAndruleDataId
	  * @Description: TODO
	  * @param @param roleId
	  * @param @param functionId
	  * @param @return    设定文件
	  * @return Set<String>    返回类型
	  * @throws
	 */
	
	public  Set<String> getOperationCodesByRoleIdAndruleDataId(String roleId,String functionId);
	
	public  Set<String> getOperationCodesByUserIdAndDataId(String userId,String functionId);
	
	/**
	 * 加载所有图标
	 * @return
	 */
	public  void initAllTSIcons();
	
	/**
	 * 更新图标
	 * @param icon
	 */
	public  void upTSIcons(TSIcon icon);
	/**
	 * 删除图标
	 * @param icon
	 */
	public  void delTSIcons(TSIcon icon);

	/**
	 * 获取审核结果
	 * @param id
	 * @param tranType
	 * @return
	 */
	public TSAuditRelationEntity getAuditInfo(String id, String tranType);

	/**
	 * 获取所有审核结果
	 * @param id
	 * @param tranType
	 * @return
	 */
	public List<TSAuditRelationEntity> getAuditInfoList(String id, String tranType);
   /**
   * 通过CODE获取系统配置
   * @param code
   * @return
   */
   public String getConfigByCode(String code);

	/**
	 * 测试当前hibernate会话的连接信息
	 * @author:hjh 20160906
	 */
	public void getConn();

	/**
	 * 通过jdbc到主数据库查询当前账套dbKey对应的账套信息
	 * @param dbkey
	 * @author:hjh 20160906
     */
	public TScAccountConfigEntity getCurrentAccountConfigByDbkey(String dbkey);

	/**
	 * 通过jdbc到主数据库查询当前账套Id对应的账套信息
	 * @param accountId
	 * @author:hjh 20160906
	 */
	public TScAccountConfigEntity getCurrentAccountConfigByAccountid(String accountId);

	/**
	 * 校验单据编号唯一性
	 * @param tableName
	 * @param billNo
	 * @return
	 */
	public Boolean checkBillNo(String tableName, String billNo,String billId);

	/**
	 * 按条件到主数据库查询指定账定对应账套信息
	 * @param fieldName
	 * @param fieldValue
	 * @return
	 */
	public DynamicDataSourceEntity getDynamicDataSourceByParameter(String fieldName, String fieldValue);

	/**
	 *
	 * @param sonId
	 * @return
	 */
	public Set<String> formatterSonId(String sonId);

	/**
	 * 获取经销商或部门分支机构id
	 * @param sonInfo
	 * @return
	 */
	public TSDepart getParentSonInfo(TSDepart sonInfo);

	/**
	 * 根据父部门id获取所有子部门id
	 * @param id
	 * @return
	 */
	public Set<String> getAllSonId(String id);

	/**
	 * 新增数据后，若需要多级审核，则在首页做出预警
	 * @param tranType
	 * @param id
	 */
	public void saveBillAuditStatus(String tranType, String id);

	/**
	 * 删除待审核预警数据
	 * @param tranType
	 * @param id
	 */
	public void delBillAuditStatus(String tranType, String id);

	/**
	 * 获取当前用户的审核级别
	 * @param userId
	 * @param id
	 * @param tranType
	 * @return
	 */
	public Set<Integer> getUserAuditLeave(String userId, String id, String tranType);
}
