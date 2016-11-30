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
package com.qihang.winter.poi.excel;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.qihang.winter.poi.excel.entity.ExportParams;

/**
 * excel 导出工具类
 * 
 * @author Zerrion
 * @version 1.0
 * @date 2013-10-17
 */
public final class ExcelExportUtil {

    private ExcelExportUtil() {
    }

    /**
     * @param entity
     *            表格标题属性
     * @param pojoClass
     *            Excel对象Class
     * @param dataSet
     *            Excel对象数据List
     */
    public static Workbook exportExcel(ExportParams entity, Class<?> pojoClass,
                                       Collection<?> dataSet) {
        Workbook workbook;
        if (com.qihang.winter.poi.excel.entity.enmus.ExcelType.HSSF.equals(entity.getType())) {
            workbook = new HSSFWorkbook();
        } else if (dataSet.size() < 1000) {
            workbook = new XSSFWorkbook();
        } else {
            workbook = new SXSSFWorkbook();
        }
        new com.qihang.winter.poi.excel.export.ExcelExportServer().createSheet(workbook, entity, pojoClass, dataSet);
        return workbook;
    }

    /**
     * 根据Map创建对应的Excel
     * @param entity
     *            表格标题属性
     * @param pojoClass
     *            Excel对象Class
     * @param dataSet
     *            Excel对象数据List
     */
    public static Workbook exportExcel(ExportParams entity, List<com.qihang.winter.poi.excel.entity.params.ExcelExportEntity> entityList,
                                       Collection<? extends Map<?, ?>> dataSet) {
        Workbook workbook;
        if (com.qihang.winter.poi.excel.entity.enmus.ExcelType.HSSF.equals(entity.getType())) {
            workbook = new HSSFWorkbook();
        } else if (dataSet.size() < 1000) {
            workbook = new XSSFWorkbook();
        } else {
            workbook = new SXSSFWorkbook();
        }
        new com.qihang.winter.poi.excel.export.ExcelExportServer().createSheetForMap(workbook, entity, entityList, dataSet);
        return workbook;
    }

    /**
     * 一个excel 创建多个sheet
     * 
     * @param list
     *            多个Map key title 对应表格Title key entity 对应表格对应实体 key data
     *            Collection 数据
     * @return
     */
    public static Workbook exportExcel(List<Map<String, Object>> list, String type) {
        Workbook workbook;
        if (com.qihang.winter.poi.excel.entity.enmus.ExcelType.HSSF.equals(type)) {
            workbook = new HSSFWorkbook();
        } else {
            workbook = new XSSFWorkbook();
        }
        for (Map<String, Object> map : list) {
            com.qihang.winter.poi.excel.export.ExcelExportServer server = new com.qihang.winter.poi.excel.export.ExcelExportServer();
            server.createSheet(workbook, (ExportParams) map.get("title"),
                (Class<?>) map.get("entity"), (Collection<?>) map.get("data"));
        }
        return workbook;
    }

    /**
     * 导出文件通过模板解析,不推荐这个了,推荐全部通过模板来执行处理
     * 
     * @param params
     *            导出参数类
     * @param pojoClass
     *            对应实体
     * @param dataSet
     *            实体集合
     * @param map
     *            模板集合
     * @return
     */
    public static Workbook exportExcel(com.qihang.winter.poi.excel.entity.TemplateExportParams params, Class<?> pojoClass,
                                       Collection<?> dataSet, Map<String, Object> map) {
        return new com.qihang.winter.poi.excel.export.template.ExcelExportOfTemplateUtil().createExcleByTemplate(params, pojoClass, dataSet,
            map);
    }

    /**
     * 导出文件通过模板解析只有模板,没有集合
     * 
     * @param params
     *            导出参数类
     * @param map
     *            模板集合
     * @return
     */
    public static Workbook exportExcel(com.qihang.winter.poi.excel.entity.TemplateExportParams params, Map<String, Object> map) {
        return new com.qihang.winter.poi.excel.export.template.ExcelExportOfTemplateUtil().createExcleByTemplate(params, null, null, map);
    }

}
