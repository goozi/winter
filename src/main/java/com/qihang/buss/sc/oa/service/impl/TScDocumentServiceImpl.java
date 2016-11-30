package com.qihang.buss.sc.oa.service.impl;


import com.qihang.buss.sc.oa.entity.TScDocumentEntity;
import com.qihang.buss.sc.oa.entity.TScFileEntity;
import com.qihang.buss.sc.oa.service.TScDocumentServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.util.ResourceUtil;
import com.qihang.winter.core.util.SystemPath;
import com.qihang.winter.web.system.pojo.base.TSUser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.Serializable;
import java.util.*;

@Service("tScDocumentService")
@Transactional
public class TScDocumentServiceImpl extends CommonServiceImpl implements TScDocumentServiceI {

	
 	public <T> void delete(T entity) {
		TScDocumentEntity doc = (TScDocumentEntity) entity;
		List<TScDocumentEntity> list = getTreeList(doc.getId());
		if(null != list && list.size() > 0){
			for(TScDocumentEntity dm :list){
				if(dm.getChildren().size() > 0){
					this.delete(dm);
				}else {
					this.doDelSql((TScDocumentEntity) dm);
					super.deleteEntityById(TScDocumentEntity.class, dm.getId());
				}
			}
		}
		//执行删除操作配置的sql增强
		this.doDelSql((TScDocumentEntity) entity);
		//super.delete(doc);
		super.deleteEntityById(TScDocumentEntity.class, doc.getId());

 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TScDocumentEntity) entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TScDocumentEntity) entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TScDocumentEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TScDocumentEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TScDocumentEntity t){
		List<TScFileEntity> entityList = super.findByProperty(TScFileEntity.class,"fileType",t.getId());
		if(null != entityList && entityList.size() > 0){
			for (TScFileEntity tf : entityList){
				List<String> list= super.findListbySql("select realpath from t_s_attachment where id ='"+tf.getUrl()+"'");
				if(null != list && list.size() > 0){
					StringBuffer sb = new StringBuffer(SystemPath.getSysPath()+list.get(0));
					File file = new File(sb.toString());
					if(file.exists()){
						file.delete();
					}
				}
				super.deleteEntityById(TScFileEntity.class,tf.getId());
			}
		}
	 	return true;
 	}

	/**
	 * 获取数集合
	 *
	 * @return
	 */
	@Override
	public TScDocumentEntity treeList() {
		TScDocumentEntity entity = new TScDocumentEntity();
		List<TScDocumentEntity> tree = getTreeList("0000");
		if(tree.size()==0){
			//创建个人文件柜
			TScDocumentEntity t1 = new TScDocumentEntity();
			t1.setPid("0000");
			t1.setType(1);
			t1.setName("个人文件柜");
			t1.setCreateDate(new Date());
			TSUser user = ResourceUtil.getSessionUserName();
			t1.setCreateBy(user.getCreateBy());
			t1.setCreateName(user.getCreateName());
			t1.setUserId(user.getId());
			//创建公告文件柜
			TScDocumentEntity t2 = new TScDocumentEntity();
			t2.setPid("0000");
			t2.setType(2);
			t2.setName("公共文件柜");
			t2.setCreateDate(new Date());
			t2.setCreateBy(user.getCreateBy());
			t2.setCreateName(user.getCreateName());
			t2.setUserId(user.getId());
			//保存
			super.save(t1);
			super.save(t2);
		}
		entity.setId("0");
		entity.setText("文件柜");
		entity.setChildren(tree);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("pid",0);
		map.put("type",0);
		entity.setAttributes(map);
		return entity;
	}

	/**
	 * 删除验证
	 *
	 * @param tOaDocument
	 * @return
	 */
	@Override
	public Boolean delVaild(TScDocumentEntity tScDocument) {
		Boolean bl = false;
		List<TScDocumentEntity> list = super.findByProperty(TScDocumentEntity.class, "pid", tScDocument.getId());
		List<TScFileEntity> fileList = super.findByProperty(TScFileEntity.class, "fileType", tScDocument.getId());
		if((null != list && list.size() > 0) || (null != fileList && fileList.size() > 0)){
			bl = true;
		}
		return bl;
	}

	@Override
	public List<TScDocumentEntity> getTreeList(String id) {
		List<TScDocumentEntity> tree = new ArrayList<TScDocumentEntity>();
		List<TScDocumentEntity> list = super.findByProperty(TScDocumentEntity.class, "pid", id);
		if( null != list && list.size() > 0){
			for(TScDocumentEntity doc : list){
				TScDocumentEntity entity = new TScDocumentEntity();
				Map<String,Object> map = new HashMap<String, Object>();
				entity.setId(doc.getId());
				entity.setText(doc.getName());
				entity.setChildren(getTreeList(doc.getId()));
				map.put("pid", doc.getPid());
				map.put("type", doc.getType());
				entity.setAttributes(map);
				if (!id.equals("0000") && entity.getChildren().size() > 0) {
					entity.setState("closed");
				}
				if(doc.getPid().equals("0000") || doc.getType() == 2){
					tree.add(entity);
				}else if(doc.getType() == 1){
						String userId = ResourceUtil.getSessionUserName().getId();
							if(userId.equals(doc.getUserId())){
								tree.add(entity);
							}
					}/*else{
						TScDocumentEntity entity = new TScDocumentEntity();
						Map<String,Object> map = new HashMap<String, Object>();
						entity.setId(doc.getId());
						entity.setText(doc.getName());
						entity.setChildren(getTreeList(doc.getId()));
						map.put("pid", doc.getPid());
						map.put("type", doc.getType());
						entity.setAttributes(map);
						if (!id.equals("0000") && entity.getChildren().size() > 0) {
							entity.setState("closed");
						}
						tree.add(entity);
					}*/

				/*TScDocumentEntity entity = new TScDocumentEntity();
				Map<String,Object> map = new HashMap<String, Object>();
				entity.setId(doc.getId());
				entity.setText(doc.getName());
				entity.setChildren(getTreeList(doc.getId()));
				map.put("pid", doc.getPid());
				map.put("type", doc.getType());
				entity.setAttributes(map);
				if (!id.equals("0000") && entity.getChildren().size() > 0) {
					entity.setState("closed");
				}
				tree.add(entity);*/
			}
		}
		return tree;
	}

	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TScDocumentEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{pid}",String.valueOf(t.getPid()));
 		sql  = sql.replace("#{name}",String.valueOf(t.getName()));
 		sql  = sql.replace("#{type}",String.valueOf(t.getType()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	/**
	 * 获取APP文档目录
	 *
	 * @return
	 */
	@Override
	public List<TScDocumentEntity> getAppDoc(String userId) {
		List<TScDocumentEntity> list = getAppList("0000",userId);
		return list;
	}

	/**
	 * 获取APP文档目录测试
	 *
	 * @param id
	 * @param userId
	 * @return
	 */
	@Override
	public Map<String,Object> getAppDocT(String id, String userId,String queryName) {
		if(null == id || id == ""){
			id = "0000";
		}
		Map<String,Object> map = getAppListT(id, userId,queryName);
		if(map.size() > 0){
			map.put("success",1);
		}else{
			map.put("success",0);
		}
		return map;
	}

	public List<TScDocumentEntity> getAppList(String id,String userId) {
		List<TScDocumentEntity> tree = new ArrayList<TScDocumentEntity>();
		List<TScDocumentEntity> list = super.findByProperty(TScDocumentEntity.class, "pid", id);
		if( null != list && list.size() > 0){
			for(TScDocumentEntity doc : list){
				TScDocumentEntity entity = new TScDocumentEntity();
				entity.setId(doc.getId());
				entity.setName(doc.getName());
				entity.setPid(doc.getPid());
				entity.setType(doc.getType());
				entity.setUserId(doc.getUserId());
				entity.setCreateDate(doc.getCreateDate());
				entity.setUpdateDate(doc.getUpdateDate());
				entity.setChildren(getAppList(doc.getId(),userId));
				if(doc.getPid().equals("0000") || doc.getType() == 2){
					tree.add(entity);
				}else if(doc.getType() == 1){
						if(doc.getUserId().equals(userId)){
							tree.add(entity);
						}
				}
			}
		}
		return tree;
	}

	public Map<String,Object> getAppListT(String id,String userId,String queryName) {
		List<TScDocumentEntity> dList = new ArrayList<TScDocumentEntity>();
		Map<String,Object> map = new HashMap<String, Object>();
		StringBuffer sbd = new StringBuffer("from TScDocumentEntity where pid = ?");
		List<TScDocumentEntity> list = null;
		if(null != queryName && queryName != ""){
			sbd.append(" and name like ?");
			list = super.findHql(sbd.toString(), new Object[]{id,'%'+queryName+'%'});
		}else{
			list = super.findHql(sbd.toString(),new Object[]{id});
		}
		if( null != list && list.size() > 0){
			for(TScDocumentEntity doc : list){
				if(doc.getPid().equals("0000") || doc.getType() == 2){
					dList.add(doc);
				}else if(doc.getType() == 1){
					if(doc.getUserId().equals(userId)){
						dList.add(doc);
					}
				}
			}
			if(dList.size() > 0){
				map.put("docs",dList);
			}
		}
		List<TScFileEntity> fList = new ArrayList<TScFileEntity>();
		StringBuffer sbf = new StringBuffer("from TScFileEntity where fileType = ?");
		List<TScFileEntity> fileList = null;
		if(null != queryName && queryName != ""){
			sbf.append(" and name like ?");
			fileList = super.findHql(sbf.toString(),new Object[]{id,'%'+queryName+'%'});
		}else{
			fileList = super.findHql(sbf.toString(),new Object[]{id});
		}
		if(null != fileList && fileList.size() > 0){
			for(TScFileEntity file : fileList){
				if(file.getFileType().equals("0000") || file.getType().equals("2")){
					fList.add(file);
				}else if(file.getType().equals("1")){
					if(userId.equals(file.getUserId())){
						fList.add(file);
					}
				}
			}
			if(null != fList && fList.size() > 0){
				for (TScFileEntity tScFileEntity : fList) {
					String sql = "select realpath from t_s_attachment where id = '"+tScFileEntity.getUrl()+"'";
					List<String> sqlList = super.findListbySql(sql);
					if(null != sqlList && sqlList.size() > 0){
						super.getSession().evict(tScFileEntity);
						tScFileEntity.setUrl(sqlList.get(0));
					}
				}
			}
			if(fList.size() > 0){
				map.put("files",fList);
			}

		}
		return map;
	}

}