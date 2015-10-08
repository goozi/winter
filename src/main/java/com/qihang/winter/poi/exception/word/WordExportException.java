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
package com.qihang.winter.poi.exception.word;

/**
 * word导出异常
 * 
 * @author Zerrion
 * @date 2014年8月9日 下午10:32:51
 */
public class WordExportException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public WordExportException() {
        super();
    }

    public WordExportException(String msg) {
        super(msg);
    }

    public WordExportException(com.qihang.winter.poi.exception.word.enmus.WordExportEnum exception) {
        super(exception.getMsg());
    }

}