package com.qihang.buss.sc.sys.service.impl;
import com.alibaba.fastjson.JSONObject;
import com.qihang.buss.sc.sys.service.TScBillTempServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.sys.entity.TScBillTempEntity;
import com.qihang.winter.core.util.DateUtils;
import com.qihang.winter.core.util.ResourceUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.io.Serializable;

@Service("tScBillTempService")
@Transactional
public class TScBillTempServiceImpl extends CommonServiceImpl implements TScBillTempServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScBillTempEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TScBillTempEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TScBillTempEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScBillTempEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScBillTempEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScBillTempEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TScBillTempEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{accountid}",String.valueOf(t.getAccountId()));
 		sql  = sql.replace("#{trantype}",String.valueOf(t.getTranType()));
 		sql  = sql.replace("#{billerid}",String.valueOf(t.getBillerId()));
 		sql  = sql.replace("#{content}",String.valueOf(t.getContent()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

	/**
	 * 把表单提交的json数据保存到单据草稿
	 *   判断如果同一人同一类型同一单据编号保存草稿时已经存在记录，则更新，否则新增
	 * @param json
	 * @return
	 */
	public boolean doAddJson(String json) throws Exception{
		String json2 = json;
		if (json.substring(0,4).equals("str=")){
			json2 = json.substring(4);
		}
		String accountId = ResourceUtil.getAccountId();
		String billerId  = ResourceUtil.getSessionUserName().getId();//制单人
		String billerName = ResourceUtil.getSessionUserName().getRealName();
		Date date = new Date();
		String tranType = "";
		//Date date = new Date();
		String billNo = "";
		String itemId = "";
		String empId = "";
		String deptId = "";
		String stockId = "";
		JSONObject jsonObject = JSONObject.parseObject(json2);
		if (jsonObject.containsKey("d_form")) {
			JSONObject jsonForm = jsonObject.getJSONObject("d_form");
			if (jsonForm.containsKey("tranType")) {
				tranType = jsonForm.get("tranType").toString();
			}
			if (jsonForm.containsKey("trantype")) {
				tranType = jsonForm.get("trantype").toString();
			}
			if (jsonForm.containsKey("date")) {
				date = DateUtils.str2Date(jsonForm.get("date").toString(),DateUtils.date_sdf);
			}
			if (jsonForm.containsKey("billNo")) {
				billNo = jsonForm.get("billNo").toString();
			}
			if (jsonForm.containsKey("itemId")) {
				itemId = jsonForm.get("itemId").toString();
			}
			if (jsonForm.containsKey("empId")) {
				empId = jsonForm.get("empId").toString();
			}
			if (jsonForm.containsKey("deptId")) {
				deptId = jsonForm.get("deptId").toString();
			}
			if (jsonForm.containsKey("stockId")) {
				stockId = jsonForm.get("stockId").toString();
			}
		}

		TScBillTempEntity tScBillTemp = null;
		String hql0 = "from TScBillTempEntity a where a.accountId=? and a.tranType=? and a.billerId=? and a.billNo=?";
		List<TScBillTempEntity>billTempList =  this.findHql(hql0,new Object[]{accountId,tranType,billerId,billNo});
		if (billTempList.size()>0) {//存在则更新第1个
			tScBillTemp = billTempList.get(0);
		}else{
			tScBillTemp  = new TScBillTempEntity();
			tScBillTemp.setCreateName(billerName);
			tScBillTemp.setCreateBy(billerId);
			tScBillTemp.setCreateDate(date);
		}
		tScBillTemp.setUpdateName(billerName);
		tScBillTemp.setUpdateBy(billerId);
		tScBillTemp.setUpdateDate(date);
		tScBillTemp.setAccountId(accountId);
		tScBillTemp.setBillerId(billerId);
		tScBillTemp.setTranType(tranType);
		tScBillTemp.setContent(json2);
		tScBillTemp.setBillNo(billNo);
		tScBillTemp.setDate(date);
		tScBillTemp.setItemId(itemId);
		tScBillTemp.setEmpId(empId);
		tScBillTemp.setDeptId(deptId);
		tScBillTemp.setStockId(stockId);
		this.save(tScBillTemp);
		return true;
	}
}