package com.qihang.buss.sc.sys.service.impl;
import com.qihang.buss.sc.sys.service.TScBalServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.buss.sc.sys.entity.TScBalEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.io.Serializable;

@Service("tScBalService")
@Transactional
public class TScBalServiceImpl extends CommonServiceImpl implements TScBalServiceI {

	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TScBalEntity)entity);
 	}
 	
 	public <T> Serializable save(T entity) {
 		Serializable t = super.save(entity);
 		//执行新增操作配置的sql增强
 		this.doAddSql((TScBalEntity)entity);
 		return t;
 	}
 	
 	public <T> void saveOrUpdate(T entity) {
 		super.saveOrUpdate(entity);
 		//执行更新操作配置的sql增强
 		this.doUpdateSql((TScBalEntity)entity);
 	}
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param t
	 * @return
	 */
 	public boolean doAddSql(TScBalEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param t
	 * @return
	 */
 	public boolean doUpdateSql(TScBalEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param t
	 * @return
	 */
 	public boolean doDelSql(TScBalEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TScBalEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{year}",String.valueOf(t.getYear()));
 		sql  = sql.replace("#{period}",String.valueOf(t.getPeriod()));
 		sql  = sql.replace("#{itemid}",String.valueOf(t.getItemID()));
 		sql  = sql.replace("#{stockid}",String.valueOf(t.getStockID()));
 		sql  = sql.replace("#{batchno}",String.valueOf(t.getBatchNo()));
 		sql  = sql.replace("#{begqty}",String.valueOf(t.getBegQty()));
 		sql  = sql.replace("#{secbegqty}",String.valueOf(t.getSecBegQty()));
 		sql  = sql.replace("#{receive}",String.valueOf(t.getReceive()));
 		sql  = sql.replace("#{send}",String.valueOf(t.getSend()));
 		sql  = sql.replace("#{ytdreceive}",String.valueOf(t.getYtdReceive()));
 		sql  = sql.replace("#{ytdsend}",String.valueOf(t.getYtdSend()));
 		sql  = sql.replace("#{secytdreceive}",String.valueOf(t.getSecYtdReceive()));
 		sql  = sql.replace("#{secytdsend}",String.valueOf(t.getSecYtdSend()));
 		sql  = sql.replace("#{endqty}",String.valueOf(t.getEndQty()));
 		sql  = sql.replace("#{secendqty}",String.valueOf(t.getSecEndQty()));
 		sql  = sql.replace("#{begbal}",String.valueOf(t.getBegBal()));
 		sql  = sql.replace("#{debit}",String.valueOf(t.getDebit()));
 		sql  = sql.replace("#{credit}",String.valueOf(t.getCredit()));
 		sql  = sql.replace("#{ytddebit}",String.valueOf(t.getYtdDebit()));
 		sql  = sql.replace("#{ytdcredit}",String.valueOf(t.getYtdCredit()));
 		sql  = sql.replace("#{endbal}",String.valueOf(t.getEndBal()));
 		sql  = sql.replace("#{sonid}",String.valueOf(t.getSonID()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
}