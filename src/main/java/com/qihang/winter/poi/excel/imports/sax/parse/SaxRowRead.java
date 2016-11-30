/**
 * Copyright 2013-2015 JueYue (qrb.jueyue@gmail.com)
 *   
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qihang.winter.poi.excel.imports.sax.parse;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import com.qihang.winter.poi.excel.entity.params.ExcelImportEntity;
import com.qihang.winter.poi.excel.entity.sax.SaxReadCellEntity;
import com.qihang.winter.poi.exception.excel.ExcelImportException;
import com.qihang.winter.poi.util.PoiPublicUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * 当行读取数据
 * @author Zerrion
 * @param <T>
 * @date 2015年1月1日 下午7:59:39
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SaxRowRead extends com.qihang.winter.poi.excel.imports.base.ImportBaseService implements ISaxRowRead {

    private static final Logger            LOGGER          = LoggerFactory
                                                               .getLogger(SaxRowRead.class);
    /** 需要返回的数据 **/
    private List                           list;
    /** 导出的对象 **/
    private Class<?>                       pojoClass;
    /** 导入参数 **/
    private com.qihang.winter.poi.excel.entity.ImportParams params;
    /** 列表头对应关系 **/
    private Map<Integer, String>           titlemap        = new HashMap<Integer, String>();
    /** 当前的对象**/
    private Object                         object          = null;

    private Map<String, ExcelImportEntity> excelParams     = new HashMap<String, ExcelImportEntity>();

    private List<com.qihang.winter.poi.excel.entity.params.ExcelCollectionParams>    excelCollection = new ArrayList<com.qihang.winter.poi.excel.entity.params.ExcelCollectionParams>();

    private String                         targetId;

    private com.qihang.winter.poi.excel.imports.CellValueServer cellValueServer;

    private com.qihang.winter.poi.handler.inter.IExcelReadRowHanlder hanlder;

    public SaxRowRead(Class<?> pojoClass, com.qihang.winter.poi.excel.entity.ImportParams params, com.qihang.winter.poi.handler.inter.IExcelReadRowHanlder hanlder) {
        list = Lists.newArrayList();
        this.params = params;
        this.pojoClass = pojoClass;
        cellValueServer = new com.qihang.winter.poi.excel.imports.CellValueServer();
        this.hanlder = hanlder;
        initParams(pojoClass, params);
    }

    private void initParams(Class<?> pojoClass, com.qihang.winter.poi.excel.entity.ImportParams params) {
        try {

            Field fileds[] = PoiPublicUtil.getClassFields(pojoClass);
            com.qihang.winter.poi.excel.annotation.ExcelTarget etarget = pojoClass.getAnnotation(com.qihang.winter.poi.excel.annotation.ExcelTarget.class);
            if (etarget != null) {
                targetId = etarget.value();
            }
            getAllExcelField(targetId, fileds, excelParams, excelCollection, pojoClass, null);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ExcelImportException(e.getMessage());
        }

    }

    @Override
    public <T> List<T> getList() {
        return list;
    }

    @Override
    public void parse(int index, List<SaxReadCellEntity> datas) {
        try {
            if (datas == null || datas.size() == 0) {
                return;
            }
            //标题行跳过
            if (index < params.getTitleRows()) {
                return;
            }
            //表头行
            if (index < params.getTitleRows() + params.getHeadRows()) {
                addHeadData(datas);
            } else {
                addListData(datas);
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ExcelImportException(e.getMessage());
        }
    }

    /**
     * 集合元素处理
     * @param datas
     */
    private void addListData(List<SaxReadCellEntity> datas) throws Exception {
        // 判断是集合元素还是不是集合元素,如果是就继续加入这个集合,不是就创建新的对象
        if ((datas.get(params.getKeyIndex()) == null || StringUtils.isEmpty(String.valueOf(datas
            .get(params.getKeyIndex()).getValue()))) && object != null) {
            for (com.qihang.winter.poi.excel.entity.params.ExcelCollectionParams param : excelCollection) {
                addListContinue(object, param, datas, titlemap, targetId, params);
            }
        } else {
            if (object != null && hanlder != null) {
                hanlder.hanlder(object);
            }
            object = PoiPublicUtil.createObject(pojoClass, targetId);
            SaxReadCellEntity entity;
            for (int i = 0, le = datas.size(); i < le; i++) {
                entity = datas.get(i);
                String titleString = (String) titlemap.get(i);
                if (excelParams.containsKey(titleString)) {
                    saveFieldValue(params, object, entity, excelParams, titleString);
                }
            }
            for (com.qihang.winter.poi.excel.entity.params.ExcelCollectionParams param : excelCollection) {
                addListContinue(object, param, datas, titlemap, targetId, params);
            }
            if (hanlder == null) {
                list.add(object);
            }
        }

    }

    /***
     * 向List里面继续添加元素
     * 
     * @param exclusions
     * @param object
     * @param param
     * @param datas
     * @param titlemap
     * @param targetId
     * @param params
     */
    private void addListContinue(Object object, com.qihang.winter.poi.excel.entity.params.ExcelCollectionParams param,
                                 List<SaxReadCellEntity> datas, Map<Integer, String> titlemap,
                                 String targetId, com.qihang.winter.poi.excel.entity.ImportParams params) throws Exception {
        Collection collection = (Collection) PoiPublicUtil.getMethod(param.getName(),
            object.getClass()).invoke(object, new Object[] {});
        Object entity = PoiPublicUtil.createObject(param.getType(), targetId);
        boolean isUsed = false;// 是否需要加上这个对象
        for (int i = 0; i < datas.size(); i++) {
            String titleString = (String) titlemap.get(i);
            if (param.getExcelParams().containsKey(titleString)) {
                saveFieldValue(params, entity, datas.get(i), param.getExcelParams(), titleString);
                isUsed = true;
            }
        }
        if (isUsed) {
            collection.add(entity);
        }
    }

    /**
     * 设置值
     * @param params
     * @param object
     * @param entity
     * @param excelParams
     * @param titleString
     * @throws Exception 
     */
    private void saveFieldValue(com.qihang.winter.poi.excel.entity.ImportParams params, Object object, SaxReadCellEntity entity,
                                Map<String, ExcelImportEntity> excelParams, String titleString)
                                                                                               throws Exception {
        Object value = cellValueServer.getValue(params.getDataHanlder(), object, entity,
            excelParams, titleString);
        setValues(excelParams.get(titleString), object, value);
    }

    /**
     * put 表头数据
     * @param datas
     */
    private void addHeadData(List<SaxReadCellEntity> datas) {
        for (int i = 0; i < datas.size(); i++) {
            if (StringUtils.isNotEmpty(String.valueOf(datas.get(i).getValue()))) {
                titlemap.put(i, String.valueOf(datas.get(i).getValue()));
            }
        }
    }
}
