package com.qihang.buss.sc.financing.service.impl;

import com.qihang.buss.sc.financing.entity.TScRpAdjustbillFinViewEntity;
import com.qihang.buss.sc.financing.service.TScRpAdjustbillFinViewServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.Serializable;
import java.util.UUID;

/**
 * Created by LenovoM4550 on 2016-09-02.
 */
@Service("tScRpAdjustbillFinViewServiceImpl")
@Transactional
public class TScRpAdjustbillFinViewServiceImpl  extends CommonServiceImpl implements TScRpAdjustbillFinViewServiceI {
    public <T> void delete(T entity) {
        super.delete(entity);
        //执行删除操作配置的sql增强
        this.doDelSql((TScRpAdjustbillFinViewEntity)entity);
    }

    public <T> Serializable save(T entity) {
        Serializable t = super.save(entity);
        //执行新增操作配置的sql增强
        this.doAddSql((TScRpAdjustbillFinViewEntity)entity);
        return t;
    }

    public <T> void saveOrUpdate(T entity) {
        super.saveOrUpdate(entity);
        //执行更新操作配置的sql增强
        this.doUpdateSql((TScRpAdjustbillFinViewEntity)entity);
    }
    /**
     * 默认按钮-sql增强-新增操作
     * @param t
     * @return
     */
    public boolean doAddSql(TScRpAdjustbillFinViewEntity t){
        return true;
    }
    /**
     * 默认按钮-sql增强-更新操作
     * @param t
     * @return
     */
    public boolean doUpdateSql(TScRpAdjustbillFinViewEntity t){
        return true;
    }
    /**
     * 默认按钮-sql增强-删除操作
     * @param t
     * @return
     */
    public boolean doDelSql(TScRpAdjustbillFinViewEntity t){
        return true;
    }
    /**
     * 替换sql中的变量
     * @param sql
     * @return
     */
    public String replaceVal(String sql,TScRpAdjustbillFinViewEntity t){
        sql  = sql.replace("#{id}",String.valueOf(t.getId()));
        sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
        sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
        sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
        sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
        sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
        sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
//        sql  = sql.replace("#{trantype}",String.valueOf(t.getTranType()));
//        sql  = sql.replace("#{date}",String.valueOf(t.getDate()));
//        sql  = sql.replace("#{billno}",String.valueOf(t.getBillNo()));
//        sql  = sql.replace("#{itemid}",String.valueOf(t.getItemId()));
//        //sql  = sql.replace("#{empid}",String.valueOf(t.getEmpId()));
//        //sql  = sql.replace("#{deptid}",String.valueOf(t.getDeptId()));
//        sql  = sql.replace("#{allamount}",String.valueOf(t.getAllAmount()));
//        sql  = sql.replace("#{checkamount}",String.valueOf(t.getCheckAmount()));
//        sql  = sql.replace("#{classidsrc}",String.valueOf(t.getClassIdSrc()));
//        sql  = sql.replace("#{idsrc}",String.valueOf(t.getIdSrc()));
//        sql  = sql.replace("#{billnosrc}",String.valueOf(t.getBillNoSrc()));
//// 		sql  = sql.replace("#{billerid}",String.valueOf(t.getBillerId()));
//// 		sql  = sql.replace("#{checkerid}",String.valueOf(t.getCheckerId()));
//        sql  = sql.replace("#{checkdate}",String.valueOf(t.getCheckDate()));
//        sql  = sql.replace("#{checkstate}",String.valueOf(t.getCheckState()));
        sql  = sql.replace("#{cancellation}",String.valueOf(t.getCancellation()));
        sql  = sql.replace("#{explanation}",String.valueOf(t.getExplanation()));
// 		sql  = sql.replace("#{sonid}",String.valueOf(t.getSonId()));
        sql  = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

}
