/**
 * Copyright 2013-2015 JueYue (qrb.jueyue@gmail.com)
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qihang.winter.poi.excel.imports;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PushbackInputStream;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Excel 导入服务
 *
 * @author Zerrion
 * @date 2014年6月26日 下午9:20:51
 */
@SuppressWarnings({"rawtypes", "unchecked", "hiding"})
public class ExcelImportServer extends com.qihang.winter.poi.excel.imports.base.ImportBaseService {

  private final static Logger LOGGER = LoggerFactory.getLogger(ExcelImportServer.class);

  private CellValueServer cellValueServer;

  private com.qihang.winter.poi.excel.imports.verifys.VerifyHandlerServer verifyHandlerServer;

  private boolean verfiyFail = false;
  /**
   * 异常数据styler
   */
  private CellStyle errorCellStyle;

  public ExcelImportServer() {
    this.cellValueServer = new CellValueServer();
    this.verifyHandlerServer = new com.qihang.winter.poi.excel.imports.verifys.VerifyHandlerServer();
  }

  /***
   * 向List里面继续添加元素
   *
   * @param exclusions
   * @param object
   * @param param
   * @param row
   * @param titlemap
   * @param targetId
   * @param pictures
   * @param params
   */
  private void addListContinue(Object object, com.qihang.winter.poi.excel.entity.params.ExcelCollectionParams param, Row row,
                               Map<Integer, String> titlemap, String targetId,
                               Map<String, PictureData> pictures, com.qihang.winter.poi.excel.entity.ImportParams params)
          throws Exception {
    Collection collection = (Collection) com.qihang.winter.poi.util.PoiPublicUtil.getMethod(param.getName(),
            object.getClass()).invoke(object, new Object[]{});
    Object entity = com.qihang.winter.poi.util.PoiPublicUtil.createObject(param.getType(), targetId);
    String picId;
    boolean isUsed = false;// 是否需要加上这个对象
    for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
      Cell cell = row.getCell(i);
      String titleString = (String) titlemap.get(i);
      if (param.getExcelParams().containsKey(titleString)) {
        if (param.getExcelParams().get(titleString).getType() == 2) {
          picId = row.getRowNum() + "_" + i;
          saveImage(object, picId, param.getExcelParams(), titleString, pictures, params);
        } else {
          saveFieldValue(params, entity, cell, param.getExcelParams(), titleString, row);
        }
        isUsed = true;
      }
    }
    if (isUsed) {
      collection.add(entity);
    }
  }

  /**
   * 获取key的值,针对不同类型获取不同的值
   *
   * @author Zerrion
   * @date 2013-11-21
   * @param cell
   * @return
   */
  private String getKeyValue(Cell cell) {
    Object obj = null;
    switch (cell.getCellType()) {
      case Cell.CELL_TYPE_STRING:
        obj = cell.getStringCellValue();
        break;
      case Cell.CELL_TYPE_BOOLEAN:
        obj = cell.getBooleanCellValue();
        break;
      case Cell.CELL_TYPE_NUMERIC:
        obj = cell.getNumericCellValue();
        break;
      case Cell.CELL_TYPE_FORMULA:
        obj = cell.getCellFormula();
        break;
    }
    return obj == null ? null : obj.toString().trim();
  }

  /**
   * 获取保存的真实路径
   *
   * @param excelImportEntity
   * @param object
   * @return
   * @throws Exception
   */
  private String getSaveUrl(com.qihang.winter.poi.excel.entity.params.ExcelImportEntity excelImportEntity, Object object) throws Exception {
    String url = "";
    if (excelImportEntity.getSaveUrl().equals("upload")) {
      if (excelImportEntity.getMethods() != null && excelImportEntity.getMethods().size() > 0) {
        object = getFieldBySomeMethod(excelImportEntity.getMethods(), object);
      }
      url = object.getClass().getName().split("\\.")[object.getClass().getName().split("\\.").length - 1];
      return excelImportEntity.getSaveUrl() + "/"
              + url.substring(0, url.lastIndexOf("Entity"));
    }
    return excelImportEntity.getSaveUrl();
  }

  private <T> List<T> importExcel(Collection<T> result, Sheet sheet, Class<?> pojoClass,
                                  com.qihang.winter.poi.excel.entity.ImportParams params, Map<String, PictureData> pictures)
          throws Exception {
    List collection = new ArrayList();
    Map<String, com.qihang.winter.poi.excel.entity.params.ExcelImportEntity> excelParams = new HashMap<String, com.qihang.winter.poi.excel.entity.params.ExcelImportEntity>();
    List<com.qihang.winter.poi.excel.entity.params.ExcelCollectionParams> excelCollection = new ArrayList<com.qihang.winter.poi.excel.entity.params.ExcelCollectionParams>();
    String targetId = null;
    if (!Map.class.equals(pojoClass)) {
      Field fileds[] = com.qihang.winter.poi.util.PoiPublicUtil.getClassFields(pojoClass);
      com.qihang.winter.poi.excel.annotation.ExcelTarget etarget = pojoClass.getAnnotation(com.qihang.winter.poi.excel.annotation.ExcelTarget.class);
      if (etarget != null) {
        targetId = etarget.value();
      }
      getAllExcelField(targetId, fileds, excelParams, excelCollection, pojoClass, null);
    }
    Iterator<Row> rows = sheet.rowIterator();
    for (int j = 0; j < params.getTitleRows(); j++) {
      rows.next();
    }
    Map<Integer, String> titlemap = getTitleMap(rows, params, excelCollection);
    Row row = null;
    Object object = null;
    String picId;
    while (rows.hasNext()
            && (row == null || sheet.getLastRowNum() - row.getRowNum() > params
            .getLastOfInvalidRow())) {
      row = rows.next();
      // 判断是集合元素还是不是集合元素,如果是就继续加入这个集合,不是就创建新的对象
      if ((row.getCell(params.getKeyIndex()) == null || StringUtils.isEmpty(getKeyValue(row
              .getCell(params.getKeyIndex())))) && object != null) {
        for (com.qihang.winter.poi.excel.entity.params.ExcelCollectionParams param : excelCollection) {
          addListContinue(object, param, row, titlemap, targetId, pictures, params);
        }
      } else {
        object = com.qihang.winter.poi.util.PoiPublicUtil.createObject(pojoClass, targetId);
        try {
          for (int i = row.getFirstCellNum(), le = row.getLastCellNum(); i < le; i++) {
            Cell cell = row.getCell(i);
            String titleString = (String) titlemap.get(i);
            if (excelParams.containsKey(titleString) || Map.class.equals(pojoClass)) {
              if (excelParams.get(titleString) != null
                      && excelParams.get(titleString).getType() == 2) {
                picId = row.getRowNum() + "_" + i;
                saveImage(object, picId, excelParams, titleString, pictures, params);
              } else {
                saveFieldValue(params, object, cell, excelParams, titleString, row);
              }
            }
          }

          for (com.qihang.winter.poi.excel.entity.params.ExcelCollectionParams param : excelCollection) {
            addListContinue(object, param, row, titlemap, targetId, pictures, params);
          }
          collection.add(object);
        } catch (com.qihang.winter.poi.exception.excel.ExcelImportException e) {
          if (!e.getType().equals(com.qihang.winter.poi.exception.excel.enums.ExcelImportEnum.VERIFY_ERROR)) {
            throw new com.qihang.winter.poi.exception.excel.ExcelImportException(e.getType(), e);
          }
        }
      }
    }
    return collection;
  }

  /**
   * 获取表格字段列名对应信息
   * @param rows
   * @param params
   * @param excelCollection
   * @return
   */
  private Map<Integer, String> getTitleMap(Iterator<Row> rows, com.qihang.winter.poi.excel.entity.ImportParams params,
                                           List<com.qihang.winter.poi.excel.entity.params.ExcelCollectionParams> excelCollection) {
    Map<Integer, String> titlemap = new HashMap<Integer, String>();
    Iterator<Cell> cellTitle;
    String collectionName = null;
    com.qihang.winter.poi.excel.entity.params.ExcelCollectionParams collectionParams = null;
    Row row = null;
    for (int j = 0; j < params.getHeadRows(); j++) {
      row = rows.next();
      if (row == null) {
        continue;
      }
      cellTitle = row.cellIterator();
      while (cellTitle.hasNext()) {
        Cell cell = cellTitle.next();
        String value = getKeyValue(cell);
        int i = cell.getColumnIndex();
        //用以支持重名导入
        if (StringUtils.isNotEmpty(value)) {
          if (titlemap.containsKey(i)) {
            collectionName = titlemap.get(i);
            collectionParams = getCollectionParams(excelCollection, collectionName);
            titlemap.put(i, collectionName + "_" + value);
          } else if (StringUtils.isNotEmpty(collectionName)
                  && collectionParams.getExcelParams().containsKey(
                  collectionName + "_" + value)) {
            titlemap.put(i, collectionName + "_" + value);
          } else {
            collectionName = null;
            collectionParams = null;
          }
          if (StringUtils.isEmpty(collectionName)) {
            titlemap.put(i, value);
          }
        }
      }
    }
    return titlemap;
  }

  /**
   * 获取这个名称对应的集合信息
   * @param excelCollection
   * @param collectionName
   * @return
   */
  private com.qihang.winter.poi.excel.entity.params.ExcelCollectionParams getCollectionParams(List<com.qihang.winter.poi.excel.entity.params.ExcelCollectionParams> excelCollection,
                                                                                              String collectionName) {
    for (com.qihang.winter.poi.excel.entity.params.ExcelCollectionParams excelCollectionParams : excelCollection) {
      if (collectionName.equals(excelCollectionParams.getExcelName())) {
        return excelCollectionParams;
      }
    }
    return null;
  }

  /**
   * Excel 导入 field 字段类型 Integer,Long,Double,Date,String,Boolean
   *
   * @param inputstream
   * @param pojoClass
   * @param params
   * @return
   * @throws Exception
   */
  public com.qihang.winter.poi.excel.entity.result.ExcelImportResult importExcelByIs(InputStream inputstream, Class<?> pojoClass,
                                                                                     com.qihang.winter.poi.excel.entity.ImportParams params) throws Exception {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("Excel import start ,class is {}", pojoClass);
    }
    List<T> result = new ArrayList<T>();
    Workbook book = null;
    boolean isXSSFWorkbook = true;
    if (!(inputstream.markSupported())) {
      inputstream = new PushbackInputStream(inputstream, 8);
    }
    if (POIFSFileSystem.hasPOIFSHeader(inputstream)) {
      book = new HSSFWorkbook(inputstream);
      isXSSFWorkbook = false;
    } else if (POIXMLDocument.hasOOXMLHeader(inputstream)) {
      book = new XSSFWorkbook(OPCPackage.open(inputstream));
    }
    createErrorCellStyle(book);
    Map<String, PictureData> pictures;
    for (int i = 0; i < params.getSheetNum(); i++) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug(" start to read excel by is ,startTime is {}", new Date().getTime());
      }
      if (isXSSFWorkbook) {
        pictures = com.qihang.winter.poi.util.PoiPublicUtil.getSheetPictrues07((XSSFSheet) book.getSheetAt(i),
                (XSSFWorkbook) book);
      } else {
        pictures = com.qihang.winter.poi.util.PoiPublicUtil.getSheetPictrues03((HSSFSheet) book.getSheetAt(i),
                (HSSFWorkbook) book);
      }
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug(" end to read excel by is ,endTime is {}", new Date().getTime());
      }
      result.addAll(importExcel(result, book.getSheetAt(i), pojoClass, params, pictures));
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug(" end to read excel list by pos ,endTime is {}", new Date().getTime());
      }
    }
    String excelName = "";
    if (params.isNeedSave()) {
      excelName = saveThisExcel(params, pojoClass, isXSSFWorkbook, book);
    }
    return new com.qihang.winter.poi.excel.entity.result.ExcelImportResult(result, verfiyFail, book, excelName);
  }

  /**
   * 保存字段值(获取值,校验值,追加错误信息)
   *
   * @param params
   * @param object
   * @param cell
   * @param excelParams
   * @param titleString
   * @param row
   * @throws Exception
   */
  private void saveFieldValue(com.qihang.winter.poi.excel.entity.ImportParams params, Object object, Cell cell,
                              Map<String, com.qihang.winter.poi.excel.entity.params.ExcelImportEntity> excelParams, String titleString,
                              Row row) throws Exception {
    Object value = cellValueServer.getValue(params.getDataHanlder(), object, cell, excelParams,
            titleString);
    if (object instanceof Map) {
      if (params.getDataHanlder() != null) {
        params.getDataHanlder().setMapValue((Map) object, titleString, value);
      } else {
        ((Map) object).put(titleString, value);
      }
    } else {
      com.qihang.winter.poi.excel.entity.result.ExcelVerifyHanlderResult verifyResult = verifyHandlerServer.verifyData(object, value,
              titleString, excelParams.get(titleString).getVerify(), params.getVerifyHanlder());
      if (verifyResult.isSuccess()) {
        setValues(excelParams.get(titleString), object, value);
      } else {
        Cell errorCell = row.createCell(row.getLastCellNum());
        errorCell.setCellValue(verifyResult.getMsg());
        errorCell.setCellStyle(errorCellStyle);
        verfiyFail = true;
        throw new com.qihang.winter.poi.exception.excel.ExcelImportException(com.qihang.winter.poi.exception.excel.enums.ExcelImportEnum.VERIFY_ERROR);
      }
    }
  }

  /**
   *
   * @param object
   * @param picId
   * @param excelParams
   * @param titleString
   * @param pictures
   * @param params
   * @throws Exception
   */
  private void saveImage(Object object, String picId, Map<String, com.qihang.winter.poi.excel.entity.params.ExcelImportEntity> excelParams,
                         String titleString, Map<String, PictureData> pictures,
                         com.qihang.winter.poi.excel.entity.ImportParams params) throws Exception {
    if (pictures == null) {
      return;
    }
    PictureData image = pictures.get(picId);
    byte[] data = image.getData();
    String fileName = "pic" + Math.round(Math.random() * 100000000000L);
    fileName += "." + com.qihang.winter.poi.util.PoiPublicUtil.getFileExtendName(data);
    if (excelParams.get(titleString).getSaveType() == 1) {
      String path = com.qihang.winter.poi.util.PoiPublicUtil.getWebRootPath(getSaveUrl(excelParams.get(titleString),
              object));
      File savefile = new File(path);
      if (!savefile.exists()) {
        savefile.mkdirs();
      }
      savefile = new File(path + "/" + fileName);
      FileOutputStream fos = new FileOutputStream(savefile);
      fos.write(data);
      fos.close();
      setValues(excelParams.get(titleString), object,
              getSaveUrl(excelParams.get(titleString), object) + "/" + fileName);
    } else {
      setValues(excelParams.get(titleString), object, data);
    }
  }

  private void createErrorCellStyle(Workbook workbook) {
    errorCellStyle = workbook.createCellStyle();
    Font font = workbook.createFont();
    font.setColor(Font.COLOR_RED);
    errorCellStyle.setFont(font);
  }

}
