package com.qihang.winter.minidao.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description:MiniDao-SQL标签(记录用户自定义SQL模板)
 * @author Zerrion
 * @mail goozi@163.com
 * @category www.qihangsoft.com
 * @date 20130817
 * @version V1.0
 */
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Sql {

	String value();

}
