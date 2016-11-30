/**
 * 
 */
package com.qihang.winter.expression.format.reader;

import com.qihang.winter.expression.format.Element;
import com.qihang.winter.expression.format.ExpressionReader;
import com.qihang.winter.expression.format.FormatException;

import java.io.IOException;


/**
 * @author Zerrion
 * @version 2.0 
 * 2016-06-12
 */
public interface ElementReader {
	Element read(ExpressionReader sr) throws FormatException, IOException;
}
