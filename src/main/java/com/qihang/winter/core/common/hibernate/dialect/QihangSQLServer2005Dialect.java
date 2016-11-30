/**
 * Copyright (c) 2004-2015 启航软件 http://www.qihangsoft.com
 */
package com.qihang.winter.core.common.hibernate.dialect;

import java.sql.Types;
import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.type.StandardBasicTypes;

/**
 * 用户： LenovoM4550
 * 日期： 2015/10/20
 * 版本： 1.0
 */
public class QihangSQLServer2005Dialect extends SQLServerDialect {
  public QihangSQLServer2005Dialect() {
    super();
    registerHibernateType(Types.CHAR, StandardBasicTypes.STRING.getName());
    registerHibernateType(Types.NVARCHAR, StandardBasicTypes.STRING.getName());
    registerHibernateType(Types.LONGNVARCHAR, StandardBasicTypes.STRING.getName());
    registerHibernateType(Types.DECIMAL, StandardBasicTypes.DOUBLE.getName());
  }
}
