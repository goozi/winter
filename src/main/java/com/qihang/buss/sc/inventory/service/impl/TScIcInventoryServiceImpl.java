package com.qihang.buss.sc.inventory.service.impl;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryBatchnoEntity;
import com.qihang.buss.sc.inventory.service.TScIcInventoryServiceI;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.inventory.entity.TScIcInventoryEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.io.Serializable;

@Service("tScIcInventoryService")
@Transactional
public class TScIcInventoryServiceImpl extends CommonServiceImpl implements TScIcInventoryServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScIcInventoryEntity) entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TScIcInventoryEntity) entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TScIcInventoryEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScIcInventoryEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScIcInventoryEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScIcInventoryEntity t){
	 	return true;
 	}

	@Override
	public AjaxJson deleteInfo() {
		AjaxJson j = new AjaxJson();
		String deleteSql = "delete from T_SC_IC_Inventory where count <= 0 ";
		String deleteBatchNo = "delete from T_SC_IC_Inventory_batchno where count <= 0 ";
		try {
			executeSql(deleteSql);
			executeSql(deleteBatchNo);
			j.setMsg("清理成功");
		}catch (Exception e){
			j.setSuccess(false);
			j.setMsg("清理失败："+e.getMessage());
		}
		return j;
	}

	//校验商品数量是否超出库存数量
	@Override
	public AjaxJson checkIsOverInv(String checkValue) {
		AjaxJson j = new AjaxJson();
		String[] values = checkValue.split(",");
		String errorInfo = "";
		for(String value : values){
			String[] info = value.split("#");
			String itemId = info[0];//商品id
			String itemName = info[1];//商品名称
			String stockId = info[2];//仓库id
			String qty = info[3];//数量
			String batchNo = "";//批号
			if(info.length > 4){
				batchNo = info[4];
			}
			Double invQty = 0.0;
			List<TScIcInventoryEntity> inventoryList = this.findHql("from TScIcInventoryEntity where itemId = ? and stockId = ? ",new Object[]{itemId,stockId});
			if(inventoryList.size() > 0){
				if(StringUtils.isEmpty(batchNo)) {
					invQty = inventoryList.get(0).getBasicQty();
				}else{
					List<TScIcInventoryBatchnoEntity> batchnoEntityList = this.findHql("from TScIcInventoryBatchnoEntity where itemId = ? and stockId = ? and batchNo = ?",new Object[]{itemId,stockId,batchNo});
					if(batchnoEntityList.size() > 0){
						if(null != batchnoEntityList.get(0).getBasicQty()) {
							invQty = batchnoEntityList.get(0).getBasicQty();
						}
					}else{
						invQty = inventoryList.get(0).getBasicQty();
					}
				}
			}
			if(Double.parseDouble(qty) > invQty){
				errorInfo += itemName+",";
			}
		}
		if(StringUtils.isNotEmpty(errorInfo)){
			errorInfo = errorInfo.substring(0,errorInfo.length()-1);
			j.setMsg(errorInfo);
		}else{
			j.setSuccess(false);
		}
		return j;
	}

	/**
	 * 替换sql中的变量
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TScIcInventoryEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{itemid}",String.valueOf(t.getItemId()));
 		sql  = sql.replace("#{stockid}",String.valueOf(t.getStockId()));
 		sql  = sql.replace("#{qty}",String.valueOf(t.getQty()));
 		sql  = sql.replace("#{smallqty}",String.valueOf(t.getSmallQty()));
 		sql  = sql.replace("#{basicqty}",String.valueOf(t.getBasicQty()));
 		sql  = sql.replace("#{secqty}",String.valueOf(t.getSecQty()));
 		sql  = sql.replace("#{costprice}",String.valueOf(t.getCostPrice()));
 		sql  = sql.replace("#{costamount}",String.valueOf(t.getCostAmount()));
 		sql  = sql.replace("#{count}",String.valueOf(t.getCount()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}