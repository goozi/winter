/**
 * 
 */
package com.qihang.winter.expression.format.reader;

import com.qihang.winter.expression.format.Element;
import com.qihang.winter.expression.format.ExpressionReader;
import com.qihang.winter.expression.format.FormatException;

import java.io.IOException;


/**
 * 读取数字类型
 * @author Zerrion
 * @version 2.0 
 * 2016-06-11
 */
public class NumberTypeReader implements ElementReader {
	public static final String NUMBER_CHARS = "01234567890.";//表示数值的字符
	public static final String LONG_MARKS = "lL";//long的结尾标志
	public static final String FLOAT_MARKS = "fF";//float的结尾标志
	public static final String DOUBLE_MARKS = "dD";//double的结尾标志
	
	/**
	 * 从流中读取数字类型的ExpressionToken
	 * @param sr
	 * @return
	 * @throws FormatException 不是合法的数字类型时抛出
	 */
	public Element read(ExpressionReader sr) throws FormatException, IOException {
		int index = sr.getCruuentIndex();
		StringBuffer sb = new StringBuffer();
		int b = -1;
		while ((b = sr.read()) != -1) {
			char c = (char)b;
			if (NUMBER_CHARS.indexOf(c) == -1) {
				if (LONG_MARKS.indexOf(c) >= 0) {
					if (sb.indexOf(".") >= 0) {//有小数点
						throw new FormatException("long类型不能有小数点");
					}
					return new Element(sb.toString(), index, Element.ElementType.LONG);
				} else if (FLOAT_MARKS.indexOf(c) >= 0) {
					checkDecimal(sb);
					return new Element(sb.toString(), index, Element.ElementType.FLOAT);
				} else if (DOUBLE_MARKS.indexOf(c) >= 0) {
					checkDecimal(sb);
					return new Element(sb.toString(), index, Element.ElementType.DOUBLE);
				} else {
					sr.reset();
					if (sb.indexOf(".") >= 0) {//没有结束标志，有小数点，为double
						checkDecimal(sb);
						return new Element(sb.toString(), index, Element.ElementType.DOUBLE);
					} else {//没有结束标志，无小数点，为int
						return new Element(sb.toString(), index, Element.ElementType.INT);
					}
				}
			}
			sb.append(c);
			sr.mark(0);
		}
		//读到结未
		if (sb.indexOf(".") >= 0) {//没有结束标志，有小数点，为double
			checkDecimal(sb);
			return new Element(sb.toString(), index, Element.ElementType.DOUBLE);
		} else {//没有结束标志，无小数点，为int
			return new Element(sb.toString(), index, Element.ElementType.INT);
		}
	}
	
	/**
	 * 检查是否只有一个小数点
	 * @param sb
	 * @throws FormatException
	 */
	public static void checkDecimal(StringBuffer sb) throws FormatException {
		if (sb.indexOf(".") != sb.lastIndexOf(".")) {
			throw new FormatException("数字最多只能有一个小数点");
		}
	}
}
