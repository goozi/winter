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
package com.qihang.winter.poi.excel.imports.sax;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import com.qihang.winter.poi.excel.imports.sax.parse.ISaxRowRead;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * 基于SAX Excel大数据读取,读取Excel 07版本,不支持图片读取
 * @author Zerrion
 * @date 2014年12月29日 下午9:41:38
 * @version 1.0
 */
@SuppressWarnings("rawtypes")
public class SaxReadExcel {

    private static final Logger LOGGER = LoggerFactory.getLogger(SaxReadExcel.class);

    public <T> List<T> readExcel(InputStream inputstream, Class<?> pojoClass, com.qihang.winter.poi.excel.entity.ImportParams params,
                                 ISaxRowRead rowRead, com.qihang.winter.poi.handler.inter.IExcelReadRowHanlder hanlder) {
        try {
            OPCPackage opcPackage = OPCPackage.open(inputstream);
            return readExcel(opcPackage, pojoClass, params, rowRead, hanlder);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new com.qihang.winter.poi.exception.excel.ExcelImportException(e.getMessage());
        }
    }

    private <T> List<T> readExcel(OPCPackage opcPackage, Class<?> pojoClass, com.qihang.winter.poi.excel.entity.ImportParams params,
                                  ISaxRowRead rowRead, com.qihang.winter.poi.handler.inter.IExcelReadRowHanlder hanlder) {
        try {
            XSSFReader xssfReader = new XSSFReader(opcPackage);
            SharedStringsTable sst = xssfReader.getSharedStringsTable();
            if (rowRead == null) {
                rowRead = new com.qihang.winter.poi.excel.imports.sax.parse.SaxRowRead(pojoClass, params, hanlder);
            }
            XMLReader parser = fetchSheetParser(sst, rowRead);
            Iterator<InputStream> sheets = xssfReader.getSheetsData();
            int sheetIndex = 0;
            while (sheets.hasNext() && sheetIndex < params.getSheetNum()) {
                sheetIndex++;
                InputStream sheet = sheets.next();
                InputSource sheetSource = new InputSource(sheet);
                parser.parse(sheetSource);
                sheet.close();
            }
            return rowRead.getList();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new com.qihang.winter.poi.exception.excel.ExcelImportException("SAX导入数据失败");
        }
    }

    private XMLReader fetchSheetParser(SharedStringsTable sst, ISaxRowRead rowRead)
                                                                                   throws SAXException {
        XMLReader parser = XMLReaderFactory.createXMLReader("org.apache.xerces.parsers.SAXParser");
        ContentHandler handler = new SheetHandler(sst, rowRead);
        parser.setContentHandler(handler);
        return parser;
    }

}
