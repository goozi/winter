package com.qihang.buss.sc.sales.entity;

/**
 * 修改基础资料中的引用次数的实体类
 * 如果进行修改的时候newId是更改后的id,oldId是没改变之前的id
 * 如果进行新增的时候,只要把id值赋值给oldId就可以了
 * type相对应的操作类型
 */
public class TScCountEntity {
    /**原先的id*/
    private String oldId;
    /**更换的id*/
    private String newId;
    /**操作类型*/
    private Short type;

    public String getOldId() {
        return oldId;
    }

    public void setOldId(String oldId) {
        this.oldId = oldId;
    }

    public String getNewId() {
        return newId;
    }

    public void setNewId(String newId) {
        this.newId = newId;
    }

    public Short getType() {
        return type;
    }

    public void setType(Short type) {
        this.type = type;
    }
}
