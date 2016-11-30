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
package com.qihang.winter.poi.excel.entity.result;

import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * 导入返回类
 *
 * @author Zerrion
 * @date 2014年6月29日 下午5:12:10
 */
public class ExcelImportResult<T> {

  /**
   * 结果集
   */
  private List<T> list;

  /**
   * 是否存在校验失败
   */
  private boolean verfiyFail;

  /**
   * 数据源
   */
  private Workbook workbook;


  /**
   * 保存的excel路径
   */
  private String excelName;

  public ExcelImportResult() {

  }

  public ExcelImportResult(List<T> list, boolean verfiyFail, Workbook workbook, String excelName) {
    this.list = list;
    this.verfiyFail = verfiyFail;
    this.workbook = workbook;
    this.excelName = excelName;
  }

  public List<T> getList() {
    return list;
  }

  public Workbook getWorkbook() {
    return workbook;
  }

  public boolean isVerfiyFail() {
    return verfiyFail;
  }

  public void setList(List<T> list) {
    this.list = list;
  }

  public void setVerfiyFail(boolean verfiyFail) {
    this.verfiyFail = verfiyFail;
  }

  public void setWorkbook(Workbook workbook) {
    this.workbook = workbook;
  }

  public String getExcelName() {
    return excelName;
  }

  public void setExcelName(String excelName) {
    this.excelName = excelName;
  }
}
