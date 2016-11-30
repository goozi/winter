package com.qihang.buss.sc.financing.service;
import com.qihang.buss.sc.financing.entity.TScRpAdjustbillFinViewEntity;
import com.qihang.winter.core.common.service.CommonService;
import java.io.Serializable;

/**
 * Created by LenovoM4550 on 2016-09-02.
 */
public interface TScRpAdjustbillFinViewServiceI extends CommonService {
    public <T> void delete(T entity);

    public <T> Serializable save(T entity);

    public <T> void saveOrUpdate(T entity);
    /**
     * 默认按钮-sql增强-删除操作
     * @param t
     * @return
     */
    public boolean doDelSql(TScRpAdjustbillFinViewEntity t);
}
