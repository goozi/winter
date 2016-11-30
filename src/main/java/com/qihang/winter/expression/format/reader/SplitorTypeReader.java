/**
 * 
 */
package com.qihang.winter.expression.format.reader;

import com.qihang.winter.expression.format.Element;
import com.qihang.winter.expression.format.ExpressionReader;
import com.qihang.winter.expression.format.FormatException;

import java.io.IOException;


/**
 * 读取分割符类型
 * @author Zerrion
 * @version 2.0 
 * 2016-06-11
 */
public class SplitorTypeReader implements ElementReader {

	public static final String SPLITOR_CHAR ="(),";//所有分割符
	
	/**
	 * 从流中读取分割符类型的ExpressionToken
	 * @param sr
	 * @return
	 * @throws FormatException 不是合法的分割符类型时抛出
	 * @throws IOException
	 */
	public Element read(ExpressionReader sr) throws FormatException, IOException {
		int index = sr.getCruuentIndex();
		int b = sr.read();
		char c = (char)b;
		if (b == -1 || SPLITOR_CHAR.indexOf(c) == -1) {
			throw new FormatException("不是有效的分割字符");
		}
		return new Element(Character.toString(c), index,
				Element.ElementType.SPLITOR);
	}
}
