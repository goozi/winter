package com.qihang.buss.sc.oa.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qihang.buss.sc.oa.entity.TScNoticeEntity;
import com.qihang.buss.sc.oa.entity.TScNoticeFileEntity;
import com.qihang.buss.sc.oa.entity.TScNoticeRelationEntity;
import com.qihang.buss.sc.oa.service.TScNoticeServiceI;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.core.util.oConvertUtils;
import com.qihang.winter.web.system.pojo.base.TSBaseUser;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service("tScNoticeService")
@Transactional
public class TScNoticeServiceImpl extends CommonServiceImpl implements TScNoticeServiceI {

    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TScNoticeEntity) entity);
    }

    public void addMain(TScNoticeEntity tScNotice,
                        List<TScNoticeFileEntity> tScNoticeFileList) {
        //保存主信息
        this.save(tScNotice);

        /**保存-公告通知附件表*/
        for (TScNoticeFileEntity tScNoticeFile : tScNoticeFileList) {
            //外键设置
            tScNoticeFile.setNoteId(tScNotice.getId());
            this.save(tScNoticeFile);
        }
        //执行新增操作配置的sql增强
        this.doAddSql(tScNotice);
    }


    public void updateMain(TScNoticeEntity tScNotice,
                           List<TScNoticeFileEntity> tScNoticeFileList) {
        //保存主表信息
        this.saveOrUpdate(tScNotice);
        //===================================================================================
        //获取参数
        Object id0 = tScNotice.getId();
        //===================================================================================
        //1.查询出数据库的明细数据-公告通知附件表
        String hql0 = "from TScNoticeFileEntity where 1 = 1 AND nOTE_ID = ? ";
        List<TScNoticeFileEntity> tScNoticeFileOldList = this.findHql(hql0, id0);
        //2.筛选更新明细数据-公告通知附件表
        for (TScNoticeFileEntity oldE : tScNoticeFileOldList) {
            boolean isUpdate = false;
            for (TScNoticeFileEntity sendE : tScNoticeFileList) {
                //需要更新的明细数据-公告通知附件表
                if (oldE.getId().equals(sendE.getId())) {
                    try {
                        MyBeanUtils.copyBeanNotNull2Bean(sendE, oldE);
                        this.saveOrUpdate(oldE);
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw new BusinessException(e.getMessage());
                    }
                    isUpdate = true;
                    break;
                }
            }
            if (!isUpdate) {
                //如果数据库存在的明细，前台没有传递过来则是删除-公告通知附件表
                super.delete(oldE);
            }

        }
        //3.持久化新增的数据-公告通知附件表
        for (TScNoticeFileEntity tScNoticeFile : tScNoticeFileList) {
            if (oConvertUtils.isEmpty(tScNoticeFile.getId())) {
                //外键设置
                tScNoticeFile.setNoteId(tScNotice.getId());
                this.save(tScNoticeFile);
            }
        }
        //执行更新操作配置的sql增强
        this.doUpdateSql(tScNotice);
    }


    public void delMain(TScNoticeEntity tScNotice) {
        //删除主表信息
        this.delete(tScNotice);
        //===================================================================================
        //获取参数
        Object id0 = tScNotice.getId();
        //===================================================================================
        //删除-公告通知附件表
        String hql0 = "from TScNoticeFileEntity where 1 = 1 AND nOTE_ID = ? ";
        List<TScNoticeFileEntity> tScNoticeFileOldList = this.findHql(hql0, id0);
        this.deleteAllEntitie(tScNoticeFileOldList);
    }


    /**
     * 默认按钮-sql增强-新增操作
     *
     * @param id
     * @return
     */
    public boolean doAddSql(TScNoticeEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-更新操作
     *
     * @param id
     * @return
     */
    public boolean doUpdateSql(TScNoticeEntity t) {
        return true;
    }

    /**
     * 默认按钮-sql增强-删除操作
     *
     * @param id
     * @return
     */
    public boolean doDelSql(TScNoticeEntity t) {
        return true;
    }

    /**
     * 公告信息发布
     *
     * @param id
     * @return
     */
    public Map<String, Object> release(String id) {
        Map<String, Object> result = new HashMap<String, Object>();
//        List<String> departList = super.findListbySql("WITH sontype AS ( SELECT id, departname FROM t_s_depart" +
//                " WHERE departname = 'OA管理' UNION ALL SELECT de.id, de.departname FROM sontype s," +
//                " t_s_depart de WHERE s.id = de.parentdepartid ) SELECT id, departname as description FROM sontype c");
//        List<Object[]> departList = super.findListbySql("select id,description from t_s_depart where parentdepartid = " +
//                "(SELECT ID FROM t_s_depart WHERE departname = 'OA管理')");
//        List<String> departList = super.findListbySql("select * from t_s_depart  where FIND_IN_SET(id, getChildList_OATree('select id from t_s_depart where departname='OA管理''))");
        List<String> departList = super.findListbySql("select getChildList_depart('OA管理')");
        if (StringUtil.isEmpty(id)) {
            result.put("success", false);
            result.put("msg", "发布失败");
        } else {
            TScNoticeEntity entity = this.get(TScNoticeEntity.class, id);
            if(null != entity && entity.getUserId() != null) {
				String[] uIdArray = entity.getUserId().split(",");
				if(null != uIdArray && uIdArray.length > 0){
                    for (int i=0;i<uIdArray.length;i++){
                        if(!departList.contains(uIdArray[i])){
                            TScNoticeRelationEntity notice = new TScNoticeRelationEntity();
                            notice.setNoticeId(entity.getId());
                            notice.setUserId(uIdArray[i]);
                            notice.setStatus(0);
                            super.saveOrUpdate(notice);
                        }
                    }
				}
			}
//            int updatei = super.updateBySqlString("UPDATE T_SC_NOTICE SET ISSUANCE = 1," +
//                    "ISSUANCE_DATE=convert(varchar(10),getdate(),120) WHERE ID='" + id + "'");
            int updatei = super.updateBySqlString("UPDATE T_SC_NOTICE SET ISSUANCE = 1," +
                    "ISSUANCEDATE=DATE_FORMAT(NOW(),'%Y-%m-%d') WHERE ID='" + id + "'");
            if (updatei > 0) {
                result.put("success", true);
                result.put("msg", "发布成功");
            }
        }
        return result;
    }

    /**
     * 获取通知人员树形结构
     *
     * @param type
     * @param noticeId
     * @return
     */
    public com.alibaba.fastjson.JSONArray getUserTreeInfo(String type, String noticeId, String style){
        String[] ids;
        List idList = null;
        JSONArray tree = new JSONArray();
        if (StringUtil.isNotEmpty(noticeId)) {
          /*  if(style.equals("news")){
                TOaNewsEntity entity = super.getEntity(TOaNewsEntity.class, noticeId);
                if (entity != null && entity.getUserId() != null) {
                    ids = entity.getUserId().split(",");
                    idList = Arrays.asList(ids);
                }
            }else*/ if(style.equals("notice")){
                TScNoticeEntity entity = super.getEntity(TScNoticeEntity.class, noticeId);
                if (entity != null && entity.getUserId() != null) {
                    ids = entity.getUserId().split(",");
                    idList = Arrays.asList(ids);
                }
            }/*else{
                TOaMessageEntity entity = super.getEntity(TOaMessageEntity.class, noticeId);
                if(entity != null && entity.getUserId() != null){
                    ids = entity.getUserId().split(",");
                    idList = Arrays.asList(ids);
                }
            }*/


        }

//        List<Object[]> departList = super.findListbySql("WITH sontype AS ( SELECT id, departname FROM t_s_depart" +
//                " WHERE id = '"+type+"' UNION ALL SELECT de.id, de.departname FROM sontype s," +
//                " t_s_depart de WHERE s.id = de.parentdepartid ) SELECT id, departname as description FROM sontype c");
//        List<Object[]> departList = super.findListbySql("select id,description from t_s_depart where parentdepartid = " +
//                "(SELECT ID FROM t_s_depart WHERE id = '"+type+"')");
        List<Object[]> departList = super.findListbySql("select * from t_s_depart  where FIND_IN_SET(id, getChildList_OATree('"+type+"'))");

        if(departList.size() > 0 && departList != null){
            JSONArray nodeTree = new JSONArray();
            for (Object[] depart : departList) {
                JSONObject treeNode = new JSONObject();
                if("3".equals(depart[5]) && !type.equals(depart[0])) {
                    treeNode.put("id", depart[0]);
                    treeNode.put("text", depart[1]);
                    List<Object[]> baseUserList = super.findListbySql("SELECT ID,REALNAME FROM T_S_BASE_USER WHERE id in (" +
                            "select user_id from t_s_user_org where org_id ='" + depart[0] + "')");
                    if (baseUserList.size() > 0) {
                        JSONArray sysUserTreeInfo = new JSONArray();
                        for (Object[] baseUser : baseUserList) {
                            JSONObject fsonsTreeInfo = new JSONObject();
                            if (idList != null && idList.contains(baseUser[0])) {
                                fsonsTreeInfo.put("checked", true);
                            } else {
                                fsonsTreeInfo.put("checked", false);
                            }
                            fsonsTreeInfo.put("id", baseUser[0]);
                            fsonsTreeInfo.put("text", baseUser[1]);
                            sysUserTreeInfo.add(fsonsTreeInfo);
                        }
                        treeNode.put("children", sysUserTreeInfo);
                        tree.add(treeNode);
                    }
                } else if(type.equals(depart[0])){
                    treeNode.put("id",depart[0]);
                    treeNode.put("text",depart[1]);
                    List<Object[]> baseUserList = super.findListbySql("SELECT ID,REALNAME FROM T_S_BASE_USER WHERE id in (" +
                            "select user_id from t_s_user_org where org_id ='" + depart[0] + "')");
                    if (baseUserList.size() > 0) {
                        JSONArray sysUserTreeInfo = new JSONArray();
                        for (Object[] baseUser : baseUserList) {
                            JSONObject fsonsTreeInfo = new JSONObject();
                            if (idList != null && idList.contains(baseUser[0])) {
                                fsonsTreeInfo.put("checked", true);
                            } else {
                                fsonsTreeInfo.put("checked", false);
                            }
                            fsonsTreeInfo.put("id", baseUser[0]);
                            fsonsTreeInfo.put("text", baseUser[1]);
                            sysUserTreeInfo.add(fsonsTreeInfo);
                        }
                        treeNode.put("children", sysUserTreeInfo);
                        tree.add(treeNode);
                    }
                }
            }

        }
        return tree;
    }

    @Override
    public List<Map<String, Object>> loadTypeInfo(Set<String> childIds,TSDepart depart) {
        List<Map<String,Object>> typeInfo = new ArrayList<Map<String, Object>>();
        Map<String,Object> rootMap = new HashMap<String, Object>();
        rootMap.put("id",depart.getId());
        rootMap.put("text",depart.getDepartname());
        typeInfo.add(rootMap);
        for(String sonId : childIds){
            if(!sonId.equals(depart.getId())) {
                TSDepart departInfo = this.getEntity(TSDepart.class, sonId);
                if (null != departInfo && "3".equals(departInfo.getOrgType())) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("id", departInfo.getId());
                    map.put("text", departInfo.getDepartname());
                    typeInfo.add(map);
                }
            }
        }
        return typeInfo;
    }

    public com.alibaba.fastjson.JSONArray getUserTreeInfos(String type, String noticeId) {
        String[] ids;
        List idList = null;
        JSONArray rooChildList = new JSONArray();
        if (StringUtil.isNotEmpty(noticeId)) {
            TScNoticeEntity noticeEntity = super.getEntity(TScNoticeEntity.class, noticeId);
            if (noticeEntity != null) {
                ids = noticeEntity.getUserId().split(",");
                idList = Arrays.asList(ids);
            }
        }
        if(type.equals("402880965155f5b7015156674d13000d") ){//全部//// TODO: 2015/12/3 后期确定公告类型顶级的id后修改

            List<Object[]> departList = super.findListbySql("WITH sontype AS ( SELECT id, departname FROM t_s_depart" +
                    " WHERE departname = 'OA管理' UNION ALL SELECT de.id, de.departname FROM sontype s," +
                    " t_s_depart de WHERE s.id = de.parentdepartid ) SELECT id, departname as description FROM sontype c");
            if (departList.size() > 0) {
                for (Object[] depart : departList) {
                    JSONObject rootChildTree1 = new JSONObject();
                    rootChildTree1.put("id", depart[0]);
                    rootChildTree1.put("text", depart[1]);

                    List<Object[]> baseUserList = super.findListbySql("SELECT ID,REALNAME FROM T_S_BASE_USER WHERE DEPARTID = '" +
                            depart[0]+"'");
                    if (baseUserList.size() > 0) {
                        JSONArray sysUserTreeInfo = new JSONArray();
                        for (Object[] baseUser : baseUserList) {
                            JSONObject fsonsTreeInfo = new JSONObject();
                            if (idList != null && idList.contains(baseUser[0])) {
                                fsonsTreeInfo.put("checked", true);
                            } else {
                                fsonsTreeInfo.put("checked", false);
                            }
                            fsonsTreeInfo.put("id", baseUser[0]);
                            fsonsTreeInfo.put("text", baseUser[1]);
                            sysUserTreeInfo.add(fsonsTreeInfo);
                        }
                        rootChildTree1.put("children", sysUserTreeInfo);
                        rooChildList.add(rootChildTree1);
                    }

                }
            }
        }else {
            List<TSDepart> departList = super.findListbySql("SELECT ID,departname FROM T_S_DEPART WHERE ORG_CODE = '"+type+"'",TSDepart.class);
            if(departList.size() > 0){
                JSONObject rootChildTree1 = new JSONObject();
                rootChildTree1.put("id", departList.get(0).getId());
                rootChildTree1.put("text", departList.get(0).getDepartname());
                List<TSBaseUser> baseUserList = super.findListbySql("SELECT id,REALNAME FROM T_S_BASE_USER WHERE DEPARTID = " +
                        departList.get(0).getId(), TSBaseUser.class);
                if (baseUserList.size() > 0) {
                    JSONArray sysUserTreeInfo = new JSONArray();
                    for (TSBaseUser baseUser : baseUserList) {
                        JSONObject fsonsTreeInfo = new JSONObject();
                        if (idList != null && idList.contains(baseUser.getId())) {
                            fsonsTreeInfo.put("checked", true);
                        } else {
                            fsonsTreeInfo.put("checked", false);
                        }
                        fsonsTreeInfo.put("id", baseUser.getId());
                        fsonsTreeInfo.put("text", baseUser.getRealName());
                        sysUserTreeInfo.add(fsonsTreeInfo);
                    }
                    rootChildTree1.put("children", sysUserTreeInfo);
                    rooChildList.add(rootChildTree1);
                }
            }
        }

        return rooChildList;
    }

    /**
     * 替换sql中的变量
     *
     * @param sql
     * @return
     */
    public String replaceVal(String sql, TScNoticeEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
        sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
        sql = sql.replace("#{update_name}", String.valueOf(t.getUpdateName()));
        sql = sql.replace("#{update_by}", String.valueOf(t.getUpdateBy()));
        sql = sql.replace("#{update_date}", String.valueOf(t.getUpdateDate()));
        sql = sql.replace("#{title}", String.valueOf(t.getTitle()));
        sql = sql.replace("#{content}", String.valueOf(t.getContent()));
        sql = sql.replace("#{type}", String.valueOf(t.getType()));
        sql = sql.replace("#{create_date}", String.valueOf(t.getCreateDate()));
        sql = sql.replace("#{user_id}", String.valueOf(t.getUserId()));
        sql = sql.replace("#{issuance}", String.valueOf(t.getIssuance()));
        sql = sql.replace("#{issuance_date}", String.valueOf(t.getIssuanceDate()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }
}