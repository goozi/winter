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
package com.qihang.winter.poi.excel.entity;

import com.qihang.winter.poi.excel.entity.enmus.ExcelType;
import com.qihang.winter.poi.excel.export.styler.ExcelExportStylerDefaultImpl;
import org.apache.poi.hssf.util.HSSFColor;

/**
 * Excel 导出参数
 *
 * @author Zerrion
 * @version 1.0 2013年8月24日
 */
public class ExportParams extends ExcelBaseParams {

  /**
   * 表格名称
   */
  private String title;

  /**
   * 表格名称
   */
  private short titleHeight = 10;

  /**
   * 第二行名称
   */
  private String secondTitle;

  /**
   * 表格名称
   */
  private short secondTitleHeight = 8;
  /**
   * sheetName
   */
  private String sheetName;
  /**
   * 过滤的属性
   */
  private String[] exclusions;
  /**
   * 是否添加需要需要
   */
  private boolean addIndex;
  /**
   * 是否添加需要需要
   */
  private String indexName = "序号";
  /**
   * 冰冻列
   */
  private int freezeCol;
  /**
   * 表头颜色
   */
  private short color = HSSFColor.WHITE.index;
  /**
   * 属性说明行的颜色 例如:HSSFColor.SKY_BLUE.index 默认
   */
  private short headerColor = HSSFColor.SKY_BLUE.index;
  /**
   * Excel 导出版本
   */
  private com.qihang.winter.poi.excel.entity.enmus.ExcelType type = com.qihang.winter.poi.excel.entity.enmus.ExcelType.HSSF;
  /**
   * Excel 导出style
   */
  private Class<?> style = ExcelExportStylerDefaultImpl.class;
  /**
   * 是否创建表头
   */
  private boolean isCreateHeadRows = true;

  public ExportParams() {

  }

  public ExportParams(String title, String sheetName) {
    this.title = title;
    this.sheetName = sheetName;
  }

  public ExportParams(String title, String sheetName, com.qihang.winter.poi.excel.entity.enmus.ExcelType type) {
    this.title = title;
    this.sheetName = sheetName;
    this.type = type;
  }

  public ExportParams(String title, String secondTitle, String sheetName) {
    this.title = title;
    this.secondTitle = secondTitle;
    this.sheetName = sheetName;
  }

  public ExportParams(String title, String secondTitle, String sheetName, String[] exclusions) {
    this.title = title;
    this.secondTitle = secondTitle;
    this.sheetName = sheetName;
    this.exclusions = exclusions;
  }

  public ExportParams(String title, short titleHeight, String secondTitle, short secondTitleHeight, String sheetName, String[] exclusions, boolean addIndex, String indexName, int freezeCol, short color, short headerColor, ExcelType type, Class<?> style, boolean isCreateHeadRows) {
    this.title = title;
    this.titleHeight = titleHeight;
    this.secondTitle = secondTitle;
    this.secondTitleHeight = secondTitleHeight;
    this.sheetName = sheetName;
    this.exclusions = exclusions;
    this.addIndex = addIndex;
    this.indexName = indexName;
    this.freezeCol = freezeCol;
    this.color = color;
    this.headerColor = headerColor;
    this.type = type;
    this.style = style;
    this.isCreateHeadRows = isCreateHeadRows;
  }

  public short getColor() {
    return color;
  }

  public String[] getExclusions() {
    return exclusions;
  }

  public short getHeaderColor() {
    return headerColor;
  }

  public String getSecondTitle() {
    return secondTitle;
  }

  public short getSecondTitleHeight() {
    return (short) (secondTitleHeight * 50);
  }

  public String getSheetName() {
    return sheetName;
  }

  public String getTitle() {
    return title;
  }

  public short getTitleHeight() {
    return (short) (titleHeight * 50);
  }

  public boolean isAddIndex() {
    return addIndex;
  }

  public void setAddIndex(boolean addIndex) {
    this.addIndex = addIndex;
  }

  public void setColor(short color) {
    this.color = color;
  }

  public void setExclusions(String[] exclusions) {
    this.exclusions = exclusions;
  }

  public void setHeaderColor(short headerColor) {
    this.headerColor = headerColor;
  }

  public void setSecondTitle(String secondTitle) {
    this.secondTitle = secondTitle;
  }

  public void setSecondTitleHeight(short secondTitleHeight) {
    this.secondTitleHeight = secondTitleHeight;
  }

  public void setSheetName(String sheetName) {
    this.sheetName = sheetName;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setTitleHeight(short titleHeight) {
    this.titleHeight = titleHeight;
  }

  public com.qihang.winter.poi.excel.entity.enmus.ExcelType getType() {
    return type;
  }

  public void setType(com.qihang.winter.poi.excel.entity.enmus.ExcelType type) {
    this.type = type;
  }

  public String getIndexName() {
    return indexName;
  }

  public void setIndexName(String indexName) {
    this.indexName = indexName;
  }

  public Class<?> getStyle() {
    return style;
  }

  public void setStyle(Class<?> style) {
    this.style = style;
  }

  public int getFreezeCol() {
    return freezeCol;
  }

  public void setFreezeCol(int freezeCol) {
    this.freezeCol = freezeCol;
  }

  public boolean isCreateHeadRows() {
    return isCreateHeadRows;
  }

  public void setCreateHeadRows(boolean isCreateHeadRows) {
    this.isCreateHeadRows = isCreateHeadRows;
  }

}
