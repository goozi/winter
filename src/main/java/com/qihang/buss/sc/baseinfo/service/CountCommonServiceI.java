package com.qihang.buss.sc.baseinfo.service;

/**
 * Created by LenovoM4550 on 2016-09-06.
 * 用于修改引用的次数
 */
public interface CountCommonServiceI {
    /**
     * 针对删除时修改次数
     * @param tableName 表名 例如t_sc_xxx
     * @param id 需要修改引用次数的ID
     */
    public void deleteUpdateCount(String tableName, String id);

    /**
     * 针对编辑时修改引用次数
     * @param tableName 表名例如 t_sc_xxx
     * @param oldId 原引用ID
     * @param newId 新引用ID
     */
    public void editUpdateCount(String tableName, String oldId, String newId);

    /**
     * 增加是修改引用次数
     * @param tableName 表名 例如 t_sc_xxx
     * @param id 需要修改引用次数id
     */
    public void addUpdateCount(String tableName, String id);

}
