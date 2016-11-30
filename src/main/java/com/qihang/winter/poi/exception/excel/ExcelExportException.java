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
package com.qihang.winter.poi.exception.excel;

/**
 * 导出异常
 * 
 * @author Zerrion
 * @date 2014年6月19日 下午10:56:18
 */
public class ExcelExportException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private com.qihang.winter.poi.exception.excel.enums.ExcelExportEnum type;

    public ExcelExportException() {
        super();
    }

    public ExcelExportException(com.qihang.winter.poi.exception.excel.enums.ExcelExportEnum type) {
        super(type.getMsg());
        this.type = type;
    }

    public ExcelExportException(com.qihang.winter.poi.exception.excel.enums.ExcelExportEnum type, Throwable cause) {
        super(type.getMsg(), cause);
    }

    public ExcelExportException(String message) {
        super(message);
    }

    public ExcelExportException(String message, com.qihang.winter.poi.exception.excel.enums.ExcelExportEnum type) {
        super(message);
        this.type = type;
    }

    public com.qihang.winter.poi.exception.excel.enums.ExcelExportEnum getType() {
        return type;
    }

    public void setType(com.qihang.winter.poi.exception.excel.enums.ExcelExportEnum type) {
        this.type = type;
    }

}
