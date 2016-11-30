package com.qihang.winter.web.system.service;

import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.web.system.pojo.base.TSUser;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author  Zerrion
 *
 */
public interface UserService extends CommonService {

	public TSUser checkUserExits(TSUser user);
	public String getUserRole(TSUser user);
	public void pwdInit(TSUser user, String newPwd);
	/**
	 * 判断这个角色是不是还有用户使用
	 *@author Zerrion
	 *@date   2013-11-12
	 *@param id
	 *@return
	 */
	public int getUsersOfThisRole(String id);

	/**
	 * 通过登录用户的监管单位id获取其下级监管单位信息
	 * @param user
	 * @return
	 */
	public List<Object[]> loadComList(TSUser user);

	/**
	 * 获取用户树
	 * @return
	 */
	List<Map<String,Object>> getUserTree(String departId);

	/**
	 * 获取机构部门用户树
	 * @return
	 */
	List<Map<String,Object>> getOrgTreeList();

	/**
	 * 获取分支机构树
	 * @param sonId
	 * @return
	 */
	List<Map<String,Object>> loadDepartTree(String sonId,String roleIds);

	/**
	 * 校验当前用户是否可进行审核操作
	 * @param tranType
	 * @param id
	 * @return
	 */
	boolean isAllowAudit(String tranType, String id,String sonId);

	/**
	 * 根据父部门id获取所有子部门及其属于部门的职员(不包括经销商和门店)
	 * @param departID
	 * @param departName
	 * @return
	 */
	List<Map<String,Object>> getDepartmentSonId(String departID, String departName);
}
