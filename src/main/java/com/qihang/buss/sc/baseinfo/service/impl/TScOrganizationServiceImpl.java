package com.qihang.buss.sc.baseinfo.service.impl;
import com.qihang.buss.sc.baseinfo.service.CountCommonServiceI;
import com.qihang.buss.sc.baseinfo.service.TScOrganizationServiceI;
import com.qihang.buss.sc.sales.entity.TScCountEntity;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.baseinfo.entity.TScOrganizationEntity;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.PasswordUtil;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.web.system.pojo.base.*;
import com.qihang.winter.web.system.service.SystemService;
import org.apache.commons.lang.StringUtils;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.io.Serializable;

@Service("tScOrganizationService")
@Transactional
public class TScOrganizationServiceImpl extends CommonServiceImpl implements TScOrganizationServiceI {
	@Autowired
	private SystemService systemService;

	@Autowired
	private CountCommonServiceI countCommonService;
	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public void addMain(TScOrganizationEntity tScOrganization) {
		this.save(tScOrganization);
		if(StringUtil.isEmpty(tScOrganization.getSettlecompany())){
			tScOrganization.setSettlecompany(tScOrganization.getId());
			this.saveOrUpdate(tScOrganization);
		}
		if(!tScOrganization.getSettlecompany().equals(tScOrganization.getId())){
			countCommonService.addUpdateCount("T_SC_Organization",tScOrganization.getSettlecompany());
		}
		this.doAddSql(tScOrganization);
	}

	@Override
	public void updateMain(TScOrganizationEntity tScOrganization) {
		//没有就默认写入本数据ID
		if(StringUtil.isEmpty(tScOrganization.getSettlecompany())){
			tScOrganization.setSettlecompany(tScOrganization.getId());
		}
		TScOrganizationEntity old = systemService.get(TScOrganizationEntity.class,tScOrganization.getId());
		//修改引用次数
		if(old.getSettlecompany().equals(tScOrganization.getId())){
			if(!tScOrganization.getSettlecompany().equals(old.getSettlecompany())){
				countCommonService.addUpdateCount("T_SC_Organization",tScOrganization.getSettlecompany());
			}
		}
		if(!old.getSettlecompany().equals(tScOrganization.getId())){
			if(!old.getSettlecompany().equals(tScOrganization.getSettlecompany())){
				countCommonService.editUpdateCount("T_SC_Organization",old.getSettlecompany(),tScOrganization.getSettlecompany());
			}
		}
		sessionFactory.getCurrentSession().evict(old);
		this.saveOrUpdate(tScOrganization);
		this.doUpdateSql(tScOrganization);
	}

	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScOrganizationEntity) entity);
 	}

//	public <T> Serializable save(T entity) {
//		this.save(entity);
//		if (entity.getSettlecompany()){
//
//		}
// 		Serializable t = super.save(entity);
//		TScOrganizationEntity entityTemp = (TScOrganizationEntity)entity;
//		//结算单位是自身
//		if(StringUtil.isEmpty(entityTemp.getSettlecompany()) || entityTemp.getSettlecompany()== ""){
//			entityTemp.setSettlecompany(entityTemp.getId());
//			entityTemp.setCount(1);
//			super.saveOrUpdate(entity);
//		this.save(entityTemp);
//		} else {
//			// 保存结算单位的引用次数
//			TScOrganizationEntity temp = get(TScOrganizationEntity.class, entityTemp.getSettlecompany()) ;
//			temp.setCount(temp.getCount() == null ? 1 : temp.getCount().intValue() + 1);
//			super.save(temp);
//			//执行新增操作配置的sql增强
//			this.doAddSql(entityTemp);
//		}
////		if(entityTemp.getIsDealer() == 1){
////			TSUser user = new TSUser();
////			user.setSonId(entityTemp.getParentid());
////			user.setUserName(entityTemp.getName());
////			user.setRealName(entityTemp.getName());
////			user.setPassword(PasswordUtil.encrypt(user.getUserName(), "123456", PasswordUtil.getStaticSalt()));
////			user.setUserKey(entityTemp.getName());
////			user.setStatus(new Short("1"));
////			user.setEmail(entityTemp.getEmail());
////			user.setOfficePhone(entityTemp.getMobilephone());
////			user.setMobilePhone(entityTemp.getMobilephone());
////			systemService.save(user);
////			TSRoleUser roleUser = new TSRoleUser();
////			TSRole role = super.findUniqueByProperty(TSRole.class,"roleName","Q7用户");
////			roleUser.setTSRole(role);
////			roleUser.setTSUser(user);
////			super.save(roleUser);
////			TSUserOrg userOrg = new TSUserOrg();
////			userOrg.setTsUser(user);
////			TSDepart depart = super.findUniqueByProperty(TSDepart.class, "departname", "Q7");
////			userOrg.setTsDepart(depart);
////			super.save(userOrg);
////		}
// 		return t;
// 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScOrganizationEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScOrganizationEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScOrganizationEntity t){
	 	return true;
 	}

    /**
     * 操作单据的时候修改它的引用次数根据操作的方式
     * @param countEntity
     * @return
     */
    @Override
    public boolean updateOrganizationCount(TScCountEntity countEntity) {
        //判断操作的类型 1是新增，2是编辑，3是删除
        try {
            if(Globals.COUNT_ADD_TYPE.equals(countEntity.getType())){
                //oldId是客户的id,对引用次数进行累加
                 if(StringUtils.isEmpty(countEntity.getOldId())){
                     return false;
                 }
                TScOrganizationEntity organizationEntity = this.get(TScOrganizationEntity.class,countEntity.getOldId());
                if(organizationEntity == null){
                    return false;
                }
                if(organizationEntity.getCount() == null|| organizationEntity.getCount() == 0){
                    organizationEntity.setCount(1);
                }else{
                    organizationEntity.setCount(organizationEntity.getCount() + 1);
                }
                super.saveOrUpdate(organizationEntity);
            }else if(Globals.COUNT_UPDATE_TYPE.equals(countEntity.getType())){
                //单据引用oldId是旧数据，newId是新数据，对于引用次数进行操作，旧数据-1，修改后的数据+1
                if(StringUtils.isEmpty(countEntity.getOldId())|| StringUtils.isEmpty(countEntity.getNewId())){
                    return false;
                }
                TScOrganizationEntity organizationOldEntity = this.get(TScOrganizationEntity.class,countEntity.getOldId());
                TScOrganizationEntity organizationNewEntity = this.get(TScOrganizationEntity.class,countEntity.getNewId());
                if(organizationOldEntity == null ||organizationNewEntity == null){
                    return false;
                }
                if(organizationOldEntity.getCount() == null || organizationOldEntity.getCount() == 0){
                    organizationOldEntity.setCount(0);
                }else {
                    organizationOldEntity.setCount(organizationOldEntity.getCount() - 1);
                }
                if(organizationNewEntity.getCount() == null || organizationNewEntity.getCount() == 0){
                    organizationNewEntity.setCount(1);
                }else {
                    organizationNewEntity.setCount(organizationNewEntity.getCount() + 1);
                }
                super.saveOrUpdate(organizationOldEntity);
                super.saveOrUpdate(organizationNewEntity);

            }else if(Globals.COUNT_DEL_TYPE.equals(countEntity.getType())){
                if(StringUtils.isEmpty(countEntity.getOldId())){
                    return false;
                }
                TScOrganizationEntity organizationOldEntity = this.get(TScOrganizationEntity.class,countEntity.getOldId());
                if(organizationOldEntity == null){
                    return false;
                }
                if(organizationOldEntity.getCount() == null || organizationOldEntity.getCount() == 0){
                    organizationOldEntity.setCount(0);
                }else{
                    organizationOldEntity.setCount(organizationOldEntity.getCount() - 1);
                }
                super.saveOrUpdate(organizationOldEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
	 * 替换sql中的变量
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TScOrganizationEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{number}",String.valueOf(t.getNumber()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{licence}",String.valueOf(t.getLicence()));
 		sql  = sql.replace("#{settlecompany}",String.valueOf(t.getSettlecompany()));
 		sql  = sql.replace("#{bank}",String.valueOf(t.getBank()));
 		sql  = sql.replace("#{banknumber}",String.valueOf(t.getBanknumber()));
 		sql  = sql.replace("#{typeid}",String.valueOf(t.getTypeid()));
 		sql  = sql.replace("#{dealer}",String.valueOf(t.getDealer()));
 		sql  = sql.replace("#{defaultoperator}",String.valueOf(t.getDefaultoperator()));
 		sql  = sql.replace("#{delivertype}",String.valueOf(t.getDelivertype()));
 		sql  = sql.replace("#{parentid}",String.valueOf(t.getParentid()));
 		sql  = sql.replace("#{shortname}",String.valueOf(t.getShortname()));
 		sql  = sql.replace("#{shortnumber}",String.valueOf(t.getShortnumber()));
 		sql  = sql.replace("#{contact}",String.valueOf(t.getContact()));
 		sql  = sql.replace("#{mobilephone}",String.valueOf(t.getMobilephone()));
 		sql  = sql.replace("#{phone}",String.valueOf(t.getPhone()));
 		sql  = sql.replace("#{fax}",String.valueOf(t.getFax()));
 		sql  = sql.replace("#{postalcode}",String.valueOf(t.getPostalcode()));
 		sql  = sql.replace("#{regionid}",String.valueOf(t.getRegionid()));
 		sql  = sql.replace("#{city}",String.valueOf(t.getCity()));
 		sql  = sql.replace("#{ciqnumber}",String.valueOf(t.getCiqnumber()));
 		sql  = sql.replace("#{email}",String.valueOf(t.getEmail()));
 		sql  = sql.replace("#{homepage}",String.valueOf(t.getHomepage()));
 		sql  = sql.replace("#{corperate}",String.valueOf(t.getCorperate()));
 		sql  = sql.replace("#{trade}",String.valueOf(t.getTrade()));
 		sql  = sql.replace("#{iscreditmgr}",String.valueOf(t.getIscreditmgr()));
 		sql  = sql.replace("#{creditamount}",String.valueOf(t.getCreditamount()));
 		sql  = sql.replace("#{level}",String.valueOf(t.getLevel()));
 		sql  = sql.replace("#{address}",String.valueOf(t.getAddress()));
 		sql  = sql.replace("#{note}",String.valueOf(t.getNote()));
 		sql  = sql.replace("#{disable}",String.valueOf(t.getDisable()));
 		sql  = sql.replace("#{count}",String.valueOf(t.getCount()));
 		sql  = sql.replace("#{deleted}",String.valueOf(t.getDeleted()));
 		sql  = sql.replace("#{version}",String.valueOf(t.getVersion()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	public AjaxJson checkName(String name, String load, String old_name){
		AjaxJson j = new AjaxJson();
		String sql = "select name from t_sc_organization where 1 = 1 AND name = '"+name+"'";
		List<String> list = this.findListbySql(sql);

		if(list.size() > 0){
			j.setSuccess(true);
		}else{
			j.setSuccess(false);
		}

		list.add("");//若缺此代码，编辑数据库里没有的项，list为空，list.get(0)越界报错
		//编辑时若为改变名称则不显示已存在的对话框
		if(load.equals("edit") && old_name.equals(list.get(0))){
			j.setSuccess(false);
		}

		return j;
	}
}