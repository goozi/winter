
package com.qihang.winter.expression.format.reader;

import com.qihang.winter.expression.format.Element;
import com.qihang.winter.expression.format.ExpressionReader;
import com.qihang.winter.expression.format.FormatException;

import java.io.IOException;


/**
 * 读取一个词段
 * @author Zerrion
 * @version 2.0 
 * 2016-06-11
 */
public class VariableTypeReader implements ElementReader {

	public static final String STOP_CHAR = "+-*/%^<>=&|!?:#$(),[]'\" \r\n\t";//词段的结束符
	
	public static final String TRUE_WORD = "true";
	public static final String FALSE_WORD = "false";
	
	public static final String NULL_WORD = "null";
	/**
	 * 
	 * @param sr
	 * @return
	 * @throws FormatException
	 */
	private String readWord(ExpressionReader sr) throws FormatException, IOException {
		StringBuffer sb = new StringBuffer();
		boolean readStart = true;
		int b = -1;
		while ((b = sr.read()) != -1) {
			char c = (char)b;
			if (STOP_CHAR.indexOf(c) >= 0 && !readStart) {//单词停止符,并且忽略第一个字符
				sr.reset();
				return sb.toString();
			}
			if (!Character.isJavaIdentifierPart(c)) {
				throw new FormatException("名称不能为非法字符：" + c);
			}
			if (readStart) {
				if (!Character.isJavaIdentifierStart(c)) {
					throw new FormatException("名称开头不能为字符：" + c);
				}
				readStart = false;
			}
			sb.append(c);
			sr.mark(0);
		}
		return sb.toString();
	}
	
	public Element read(ExpressionReader sr) throws FormatException, IOException {
		int index = sr.getCruuentIndex();
		String word = readWord(sr);

		if (TRUE_WORD.equals(word) || FALSE_WORD.equals(word)) {
			return new Element(word, index, Element.ElementType.BOOLEAN);
		} else if (NULL_WORD.equals(word)) {
			return new Element(word, index, Element.ElementType.NULL);
		} else {
			return new Element(word, index, Element.ElementType.VARIABLE);
		}
	}
	
}
