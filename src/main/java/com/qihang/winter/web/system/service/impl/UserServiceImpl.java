package com.qihang.winter.web.system.service.impl;

import com.qihang.buss.sc.sysaudit.entity.TSAuditEntity;
import com.qihang.buss.sc.sysaudit.entity.TSAuditLeaveEntity;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.pojo.base.TSRole;
import com.qihang.winter.web.system.pojo.base.TSRoleUser;
import com.qihang.winter.web.system.pojo.base.TSUser;
import com.qihang.winter.web.system.service.UserService;
import org.hibernate.Criteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * 
 * @author  Zerrion
 *
 */
@Service("userService")
@Transactional
public class UserServiceImpl extends CommonServiceImpl implements UserService {

	public TSUser checkUserExits(TSUser user){
		return this.commonDao.getUserByUserIdAndUserNameExits(user);
	}
	public String getUserRole(TSUser user){
		return this.commonDao.getUserRole(user);
	}
	
	public void pwdInit(TSUser user,String newPwd) {
			this.commonDao.pwdInit(user,newPwd);
	}
	
	public int getUsersOfThisRole(String id) {
		Criteria criteria = getSession().createCriteria(TSRoleUser.class);
		criteria.add(Restrictions.eq("TSRole.id", id));
		int allCounts = ((Long) criteria.setProjection(
				Projections.rowCount()).uniqueResult()).intValue();
		return allCounts;
	}

	@Override
	public List<Object[]> loadComList(TSUser user) {
		String sonId = user.getSonCompanyId();
		String sql = "with sonTypes as(select * from T_AP_SONTYPE where " +
				"id= '"+sonId+"'"+
				" union all " +
				"select t.* from sonTypes, T_AP_SONTYPE t   " +
				"where sonTypes.id = t.parent_id) " +
				"select id,name from sonTypes";
		List<Object[]> result = this.findListbySql(sql);
		return result;
	}

	@Override
	public List<Map<String, Object>> getUserTree(String departId) {
		List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
		List<TSDepart> departs = super.findByQueryString("from TSDepart");
		for(TSDepart depart : departs){
			String deptId = depart.getId();
			if(departId.equals(deptId) || (depart.getTSPDepart() != null && departId.equals(depart.getTSPDepart().getId()))){
				String hql = "from TSUser a where a.id in (select tsUser.id from TSUserOrg where tsDepart.id='"+deptId+"')";
				List<TSUser> users = super.findByQueryString(hql);
				List<Map<String,Object>> child = new ArrayList<Map<String, Object>>();
				for(TSUser user : users){
					Map<String,Object> children = new HashMap<String, Object>();
					children.put("id",user.getUserName());
					children.put("text",user.getUserName());
					child.add(children);
				}
				Map<String,Object> value = new HashMap<String, Object>();
				//value.put("id",deptId);
				value.put("text", depart.getDepartname());
				if(child.size() > 1){
					value.put("children",child);
					value.put("state","closed");
					result.add(value);
				}
			}
		}
		return result;
	}

	/**
	 * 获取机构部门用户树
	 *
	 * @return
	 */
	@Override
	public List<Map<String,Object>> getOrgTreeList() {
		List<TSDepart> departs = super.findHql("from TSDepart where TSPDepart = null ");
        List<Map<String,Object>> trees = new ArrayList<Map<String, Object>>();
		if(departs.size() > 0) {
			for (TSDepart depart : departs) {
				if (null != depart) {
					Map<String, Object> node = new HashMap<String, Object>();
					node.put("text", depart.getDepartname());
					List<Map<String, Object>> tree = new ArrayList<Map<String, Object>>();
					String userHql = "SELECT u.id,u.realName FROM t_s_user_org o,t_s_base_user u WHERE o.org_id='" + depart.getId() + "' AND o.user_id=u.ID";
					List<Object> userList = super.findListbySql(userHql);
					if (null != userList && userList.size() > 0) {
						List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
						for (int i = 0; i < userList.size(); i++) {
							Map<String, Object> userMap = new HashMap<String, Object>();
							Object[] values = (Object[]) userList.get(i);
							userMap.put("id", values[0]);
							userMap.put("text", values[1]);
							tree.add(userMap);
						}
						//node.put("children",children);
					}
					List<Map<String, Object>> departTree = getTreeList(depart.getId());
					if(departTree.size() > 0){
						node.put("children",departTree);
					}
					trees.add(node);
				}
			}
		}
		return trees;
	}
	/**
	 *获取分支机构树
	 * @param sonId
	 * @param roleIds
	 * @return
	 */
	@Override
	public List<Map<String, Object>> loadDepartTree(String sonId, String roleIds) {
		String roleId = "";
		String[] roleIdArr = roleIds.split(",");
		for(String rid : roleIdArr){
			roleId += "'"+rid+"',";
		}
		if(StringUtil.isNotEmpty(roleId)){
			roleId = roleId.substring(0,roleId.length()-1);
		}
		List<TSRole> roleList = this.findHql("from TSRole where id in (" + roleId + ")");
		Boolean hasChild = true;
		if(roleList.size() > 0){
			for(TSRole role : roleList){
				if("sc_info_one".equals(role.getRoleCode())){
					hasChild = false;
					break;
				}
			}
		}
		TSDepart depart = this.getEntity(TSDepart.class, sonId);
		List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
		if(null != depart){
			Map<String,Object> root = new HashMap<String, Object>();
			root.put("id",depart.getId());
			root.put("text",depart.getDepartname());
			if(hasChild) {
				List<TSDepart> childInfo = depart.getTSDeparts();//子机构
				if (null != childInfo && childInfo.size() > 0) {
					List<Map<String, Object>> children = getChildInfo(childInfo);
					root.put("children", children);
				}
			} else {
				root.put("checked","true");
			}
			result.add(root);
		}
		return result;
	}

	/**
	 * 校验当前用户是否可进行审核校验
	 * @param tranType//单据类型
	 * @param userId //用户id
	 * @return
	 */
	@Override
	public boolean isAllowAudit(String tranType, String userId,String sonId) {
		List<TSAuditEntity> tsAuditEntityList = this.findHql("from TSAuditEntity where billId=? and sonId = ?", new Object[]{tranType, sonId});
		Boolean isAudit = false;
		if(tsAuditEntityList.size() > 0){
			TSAuditEntity entity = tsAuditEntityList.get(0);
			//多级审核数据
			//判断当前用户是否在这些审核用户当中
			if(null != entity.getIsAudit() && 1 == entity.getIsAudit()) {
				List<TSAuditLeaveEntity> leaveEntityList = this.findHql("from TSAuditLeaveEntity where pid=?",new Object[]{entity.getId()});
				if(leaveEntityList.size() > 0){
					for(TSAuditLeaveEntity leaveEntity : leaveEntityList){
						if(StringUtil.isNotEmpty(leaveEntity.getAuditorId())){
							if(leaveEntity.getAuditorId().indexOf(userId) > -1){
								isAudit = true;
								break;
							}
						}
					}
				}
			} else {
				isAudit = true;
			}
		} else {
			isAudit = true;
		}
		return isAudit;
	}

	//迭代分支机构数据
	public List<Map<String,Object>> getChildInfo(List<TSDepart> childInfo){
		List<Map<String,Object>> child = new ArrayList<Map<String, Object>>();
		for(TSDepart depart : childInfo){
			if("1".equals(depart.getOrgType()) || "2".equals(depart.getOrgType())) {
				Map<String, Object> leaf = new HashMap<String, Object>();
				leaf.put("id", depart.getId());
				leaf.put("text", depart.getDepartname());
				List<TSDepart> departs = depart.getTSDeparts();
				if (null != departs && departs.size() > 0) {
					List<Map<String, Object>> children = getChildInfo(departs);
					leaf.put("children", children);
				}
				child.add(leaf);
			}
		}
		return child;
	}

	/**
	 * 获取递归树
	 * @param id
	 * @return
	 */
	public List<Map<String,Object>> getTreeList(String id) {
		List<Map<String,Object>> tree = new ArrayList<Map<String,Object>>();
		String userHql = "SELECT u.id,u.realName FROM t_s_user_org o,t_s_base_user u WHERE o.org_id='" + id + "' AND o.user_id=u.ID";
		List<Object> userList = super.findListbySql(userHql);
		if (null != userList && userList.size() > 0) {
			for (int i = 0; i < userList.size(); i++) {
				Map<String, Object> userMap = new HashMap<String, Object>();
				Object[] values = (Object[]) userList.get(i);
				userMap.put("id", values[0]);
				userMap.put("text", values[1]);
				tree.add(userMap);
			}
		}
		String sql = "SELECT * FROM t_s_depart  WHERE parentdepartid='"+id +"'";
		List<TSDepart> list = super.findListbySql(sql, TSDepart.class);
		if( null != list && list.size() > 0){
			for(TSDepart doc : list){
				Map<String,Object> entity = new HashMap<String, Object>();
				entity.put("id",doc.getId());
				entity.put("text",doc.getDepartname());
				entity.put("children", getTreeList(doc.getId()));
				tree.add(entity);
			}
		}
		return tree;
	}

	/**
	 * 根据父部门id获取所有子部门及其属于部门的职员(不包括经销商和门店)
	 * @param departID
	 * @param departName
	 * @return
	 */
	public List<Map<String,Object>> getDepartmentSonId(String departID ,String departName) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();

		String sqlDeparts = "select * from t_s_depart where parentdepartid = '" +departID+ "' and org_type = 3";
		List<TSDepart> departs = this.findListbySql(sqlDeparts,TSDepart.class);
		String sql = "SELECT u.id,u.realName FROM t_s_user_org o,t_s_base_user u WHERE o.org_id='" + departID + "' AND o.user_id=u.ID";
		List<Map<String,Object>> users = super.findForJdbc(sql, null);

		Map<String,Object> node = new HashMap<String,Object>();
		node.put("text", departName);

		List<Map<String, Object>> departList= new ArrayList<Map<String, Object>>();
		//机构用户
		if(users.size() > 0){
			for(int i=0; i<users.size(); i++){
				Map<String,Object> primaryUser = new HashMap<String, Object>();
				primaryUser.put("id",users.get(i).get("id"));
				primaryUser.put("text",users.get(i).get("realName"));
				departList.add(primaryUser);
			}
		}
		//机构部门
		if(departs.size() > 0){
			for(TSDepart depart : departs){
				Map<String,Object> department = new HashMap<String, Object>();
				department.put("id", depart.getId());
				department.put("text", depart.getDepartname());

				String sqlUser = "SELECT u.id,u.realName FROM t_s_user_org o,t_s_base_user u WHERE o.org_id='" + depart.getId() + "' AND o.user_id=u.ID";
//				List<Object> userList = super.findListbySql(sqlUser);
				List<Map<String,Object>> userList = super.findForJdbc(sqlUser, null);

				List<Map<String, Object>> departmentUsers= new ArrayList<Map<String, Object>>();
				if(userList.size() > 0){
					for(int i=0; i<userList.size(); i++){
						Map<String,Object> departmentUser = new HashMap<String, Object>();
						departmentUser.put("id", userList.get(i).get("id"));
						departmentUser.put("text", userList.get(i).get("realName"));
						departmentUsers.add(departmentUser);
					}
					department.put("children", departmentUsers);
				}
				departList.add(department);
			}
		}
		node.put("children",departList);
		result.add(node);
		return result;
	}
}
