package com.qihang.buss.sc.baseinfo.service.impl;

import com.qihang.buss.sc.baseinfo.service.CountCommonServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by LenovoM4550 on 2016-09-06.
 */
@Service("countCommonService")
@Transactional
public class CountCommonServiceImpl extends CommonServiceImpl implements CountCommonServiceI {

    @Override
    public void deleteUpdateCount(String tableName, String id) {
        String sql = "update "+tableName+" set count=count-1 where id=?";
        this.executeSql(sql,id);
    }


    @Override
    public void editUpdateCount(String tableName, String oldId, String newId) {
        if (oldId.equals(newId)){
            //如果引用ID一样那么不需要修改次数
        }else{
            //引用新的ID次数加1
            String sql = "update "+tableName+" set count=count+1 where id=?";
            this.executeSql(sql,newId);
            //引用就的ID次数减1
            String sql1 = "update "+tableName+" set count=count-1 where id=?";
            this.executeSql(sql1,oldId);

        }
    }

    @Override
    public void addUpdateCount(String tableName, String id) {
        String sql = "update "+tableName+" set count=count+1 where id=?";
        this.executeSql(sql,id);
    }
}
