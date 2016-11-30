package com.qihang.buss.sc.distribution.service.impl;

import com.qihang.buss.sc.baseinfo.entity.TScOrganizationEntity;
import com.qihang.buss.sc.distribution.entity.TScAptitudeEntity;
import com.qihang.buss.sc.distribution.service.TScAptitudeServiceI;
import com.qihang.buss.sc.util.BillNoGenerate;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.PasswordUtil;
import com.qihang.winter.core.util.PinyinUtil;
import com.qihang.winter.core.util.ResourceUtil;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.web.system.pojo.base.*;
import com.qihang.winter.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("tScAptitudeService")
@Transactional
public class TScAptitudeServiceImpl extends CommonServiceImpl implements TScAptitudeServiceI {

	@Autowired
	private SystemService systemService;

 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScAptitudeEntity) entity);
 	}

 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TScAptitudeEntity) entity);
	/*	TScAptitudeEntity tScAptitudeEntity = (TScAptitudeEntity)entity;

		if(tScAptitudeEntity.getEligibility() == 1){
			TSUser user = new TSUser();

			TScOrganizationEntity tScOrganizationEntity = super.get(TScOrganizationEntity.class, tScAptitudeEntity.getItemID());
			if(tScOrganizationEntity != null){
				user.setSonId(tScAptitudeEntity.getSonID());
				user.setUserName(tScOrganizationEntity.getName());
				user.setRealName(tScOrganizationEntity.getName());
				user.setPassword(PasswordUtil.encrypt(tScOrganizationEntity.getName(), "123456", PasswordUtil.getStaticSalt()));
				user.setUserKey(tScOrganizationEntity.getName());
				user.setStatus(new Short("1"));
				user.setEmail(tScOrganizationEntity.getEmail());
				user.setOfficePhone(tScAptitudeEntity.getPhone());
				user.setMobilePhone(tScAptitudeEntity.getMobilePhone());
				systemService.save(user);
				TSRoleUser roleUser = new TSRoleUser();
				TSRole role = super.findUniqueByProperty(TSRole.class,"roleName","Q7用户");
				roleUser.setTSRole(role);
				roleUser.setTSUser(user);
				super.save(roleUser);
				TSUserOrg userOrg = new TSUserOrg();
				userOrg.setTsUser(user);
				TSDepart depart = super.findUniqueByProperty(TSDepart.class,"departname", "Q7");
				userOrg.setTsDepart(depart);
				super.save(userOrg);
			}


		}*/
 		return t;
 	}

 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TScAptitudeEntity) entity);

 	}

 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScAptitudeEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScAptitudeEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScAptitudeEntity t){
	 	return true;
 	}


	/**
	 * 替换sql中的变量
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TScAptitudeEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{billno}",String.valueOf(t.getBillNo()));
 		sql  = sql.replace("#{trantype}",String.valueOf(t.getTranType()));
 		sql  = sql.replace("#{date}",String.valueOf(t.getDate()));
 		sql  = sql.replace("#{itemid}",String.valueOf(t.getItemID()));
 		sql  = sql.replace("#{contact}",String.valueOf(t.getContact()));
 		sql  = sql.replace("#{mobilephone}",String.valueOf(t.getMobilePhone()));
 		sql  = sql.replace("#{phone}",String.valueOf(t.getPhone()));
 		sql  = sql.replace("#{fax}",String.valueOf(t.getFax()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{corperate}",String.valueOf(t.getCorperate()));
 		sql  = sql.replace("#{regcapital}",String.valueOf(t.getRegCapital()));
 		sql  = sql.replace("#{economykind}",String.valueOf(t.getEconomyKind()));
 		sql  = sql.replace("#{trench}",String.valueOf(t.getTrench()));
 		sql  = sql.replace("#{itemname}",String.valueOf(t.getItemName()));
 		sql  = sql.replace("#{planqty}",String.valueOf(t.getPlanQty()));
 		sql  = sql.replace("#{trencha}",String.valueOf(t.getTrenchA()));
 		sql  = sql.replace("#{trenchb}",String.valueOf(t.getTrenchB()));
 		sql  = sql.replace("#{trenchc}",String.valueOf(t.getTrenchC()));
 		sql  = sql.replace("#{trencho}",String.valueOf(t.getTrenchO()));
 		sql  = sql.replace("#{checkerid}",String.valueOf(t.getCheckerID()));
 		sql  = sql.replace("#{billerid}",String.valueOf(t.getBillerID()));
 		sql  = sql.replace("#{checkdate}",String.valueOf(t.getCheckDate()));
 		sql  = sql.replace("#{checkstate}",String.valueOf(t.getCheckState()));
 		sql  = sql.replace("#{cancellation}",String.valueOf(t.getCancellation()));
 		sql  = sql.replace("#{iseligibility}",String.valueOf(t.getEligibility()));
 		sql  = sql.replace("#{explanation}",String.valueOf(t.getExplanation()));
 		sql  = sql.replace("#{sonid}",String.valueOf(t.getSonID()));
 		sql  = sql.replace("#{deleted}",String.valueOf(t.getDeleted()));
 		sql  = sql.replace("#{disabled}",String.valueOf(t.getDisabled()));
 		sql  = sql.replace("#{version}",String.valueOf(t.getVersion()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	/**
	 * 审核（反审核）后执行
	 * @param id
	 * @param audit
	 * @return
	 */
	@Override
	public AjaxJson afterAudit(String id, String audit) {
		TScAptitudeEntity tScAptitudeEntity = systemService.get(TScAptitudeEntity.class, id);
		AjaxJson j = new AjaxJson();
		//for(TScAptitudeEntity entry : entryList){
			//如果审核通过并且为合格经销商
			if ("1".equals(audit)) {
				//todo 审核通过且是合格经销商 自动生产 账号
				if (tScAptitudeEntity.getEligibility() == 1 && tScAptitudeEntity.getCheckState() == 2) {//是合格经销商并且已审核
					TSUser user = new TSUser();
					TScOrganizationEntity tScOrganizationEntity = super.get(TScOrganizationEntity.class, tScAptitudeEntity.getItemID());
					String name = ""; //记录用户名
					if (tScOrganizationEntity != null) {
						//如果账号已经存在 不执行插入
						String hql = " FROM TSBaseUser WHERE REALNAME = ? ";
						List<TSBaseUser>  tBUser =  this.findHql(hql, tScOrganizationEntity.getName());

						if(tBUser.isEmpty()) {
							name = tScOrganizationEntity.getShortname();
						}else if(!tBUser.isEmpty() && tBUser.size() >= 1){//如果集合不为空 并且最少有一条相同记录 就在此次生成的账号后面 加上集合长度+1
							name = tScOrganizationEntity.getShortname()+(tBUser.size()+1);
							for(TSBaseUser bUser : tBUser){
								bUser.setStatus(Globals.User_Normal);
								super.saveOrUpdate(bUser);
								List<TSUserOrg> userOrgs = bUser.getUserOrgList();
								for(TSUserOrg userOrg : userOrgs){
									TSDepart depart = userOrg.getTsDepart();
									if(depart.getDepartname().equals(bUser.getUserName())){
										depart.setDeleted(Globals.ENABLE_CODE.toString());
										super.saveOrUpdate(depart);
										break;
									}
								}
							}
						}
						name = PinyinUtil.getPinYinHeadCharDelSpecialChar(name);
						user.setSonCompanyId(tScAptitudeEntity.getSonID());
						user.setUserName(name);
						user.setRealName(tScOrganizationEntity.getName());

						user.setPassword(PasswordUtil.encrypt(name, "123456", PasswordUtil.getStaticSalt()));
						user.setUserKey(name);
						user.setStatus(Globals.User_Normal); //用户默认启用状态
						user.setEmail(tScOrganizationEntity.getEmail());
						user.setOfficePhone(tScAptitudeEntity.getPhone());
						user.setMobilePhone(tScAptitudeEntity.getMobilePhone());
						super.save(user);

						//获得用户的UUID 向客户表插入id 关联用户
						String uuid = user.getId();
						tScOrganizationEntity.setUserId(uuid);


						//根据当前登录用户(上级经销商)id查询客户表
						String currentUserId = ResourceUtil.getSessionUserName().getId();
						//TScOrganizationEntity scOrganizationEntity = super.get(TScOrganizationEntity.class, currentUserId);
						//String getByUserIdHql = " from TScOrganizationEntity where userId = ? ";
						//TScOrganizationEntity scOrganizationEntity = super.findUniqueByProperty(TScOrganizationEntity.class,"userId",currentUserId);

						TSRoleUser roleUser = new TSRoleUser();
						TSRole role = super.findUniqueByProperty(TSRole.class, "roleName", "经销商管理员");
						roleUser.setTSRole(role);
						roleUser.setTSUser(user);
						super.save(roleUser);

						TSRoleUser roleUser2 = new TSRoleUser();
						TSRole role2 = super.findUniqueByProperty(TSRole.class, "roleName", "审核角色");
						roleUser2.setTSRole(role2);
						roleUser2.setTSUser(user);
						super.save(roleUser2);

						//查看本级数据权限
						TSRoleUser roleUser3 = new TSRoleUser();
						TSRole role3 = super.findUniqueByProperty(TSRole.class, "roleCode", "sc_info_one");
						roleUser3.setTSRole(role3);
						roleUser3.setTSUser(user);
						super.save(roleUser3);

						TSUserOrg userOrg = new TSUserOrg();
						userOrg.setTsUser(user);
						//当前分支机构
						TSDepart departInfo = this.getEntity(TSDepart.class, tScAptitudeEntity.getSonID());
						//获取编号
						String billNo = BillNoGenerate.getBasicBillNo(tScAptitudeEntity.getSonID(), departInfo.getOrgCode());
						//经销商分支机构
						TSDepart departEntity = new TSDepart();
						departEntity.setOrgCode(billNo);
						departEntity.setDepartname(tScOrganizationEntity.getName());
						departEntity.setShortName(tScOrganizationEntity.getShortname());
						departEntity.setShortNumber(tScOrganizationEntity.getShortnumber());
						departEntity.setPhone(tScOrganizationEntity.getPhone());
						departEntity.setFax(tScOrganizationEntity.getFax());
						departEntity.setTSPDepart(departInfo);
						departEntity.setOrgType(Globals.ORG_TYPE_PUR);//经销商分支机构类型
						departEntity.setDeleted(Globals.ENABLE_CODE.toString());
						super.save(departEntity);
						userOrg.setTsDepart(departEntity);
						super.save(userOrg);
					}
					Map<String,Object> map = new HashMap<String,Object>();
					map.put("userName",name);
					map.put("password", "123456");
					j.setAttributes(map);
					j.setSuccess(true);
				}
			}else if("0".equals(audit)){//反审核 根据客户表关联用户id 查询用户并禁用
				if(StringUtil.isNotEmpty(tScAptitudeEntity.getItemID())){
					TScOrganizationEntity tScOrganizationEntity = super.get(TScOrganizationEntity.class, tScAptitudeEntity.getItemID());
					if( null != tScOrganizationEntity && StringUtil.isNotEmpty(tScOrganizationEntity.getUserId())){
						TSUser user = super.get(TSUser.class, tScOrganizationEntity.getUserId());
						if(null != user){
							//禁用用户
							user.setStatus(Globals.User_Forbidden);
							super.saveOrUpdate(user);
							List<TSUserOrg> userOrgs = user.getUserOrgList();
							for(TSUserOrg userOrg : userOrgs){
								TSDepart depart = userOrg.getTsDepart();
								if(depart.getDepartname().equals(user.getUserName())){
									depart.setDeleted(Globals.DISABLED_CODE.toString());
									super.saveOrUpdate(depart);
									break;
								}
							}
						}
					}
				}
			}
		//}
		return j;
	}
}