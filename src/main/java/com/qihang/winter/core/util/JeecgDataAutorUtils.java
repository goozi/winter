package com.qihang.winter.core.util;

import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.web.system.pojo.base.TSDataRule;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Zerrion
 * @ClassName: JeecgDataAutorUtils
 * @Description: 数据权限查询规则容器工具类
 * @date 2012-12-15 下午11:27:39
 */
public class JeecgDataAutorUtils {

  /**
   * 往链接请求里面，传入数据查询条件
   *
   * @param request
   * @param MENU_DATA_AUTHOR_RULES
   * @modify Zerrion 15-11-05 发现数据规则校验的拦截器把规则存入request中，但这个request是点击列表页请求时产生，而列表页中
   * datagrid数据是另一个请求产生，在拼接数据规则过滤sql时就会从另一个request中取规则，导致取不到。修改为从session中获取
   */
  public static synchronized void installDataSearchConditon(
          HttpServletRequest request, List<TSDataRule> MENU_DATA_AUTHOR_RULES) {
    @SuppressWarnings("unchecked")
    List<TSDataRule> list = (List<TSDataRule>) loadDataSearchConditonSQL();// 1.先从request获取MENU_DATA_AUTHOR_RULES，如果存则获取到LIST
    if (list == null) { // 2.如果不存在，则new一个list
      list = new ArrayList<TSDataRule>();
    }
    for (TSDataRule tsDataRule : MENU_DATA_AUTHOR_RULES) {
      list.add(tsDataRule);
    }
    request.setAttribute(Globals.MENU_DATA_AUTHOR_RULES, list); // 3.往list里面增量存指
  }

  /**
   * 获取查询条件方法
   *
   * @return
   * @modify Zerrion 15-11-05 发现数据规则校验的拦截器把规则存入request中，但这个request是点击列表页请求时产生，而列表页中
   * datagrid数据是另一个请求产生，在拼接数据规则过滤sql时就会从另一个request中取规则，导致取不到。修改为从session中获取
   */
  @SuppressWarnings("unchecked")
  public static synchronized List<TSDataRule> loadDataSearchConditonSQL() {
    return (List<TSDataRule>) ContextHolderUtils.getRequest().getAttribute(
            Globals.MENU_DATA_AUTHOR_RULES);
  }

  /**
   * 获取查询条件方法
   *
   * @return * @modify Zerrion 15-11-05 发现数据规则校验的拦截器把规则存入request中，但这个request是点击列表页请求时产生，而列表页中
   * datagrid数据是另一个请求产生，在拼接数据规则过滤sql时就会从另一个request中取规则，导致取不到。修改为从session中获取
   */
  public static synchronized String loadDataSearchConditonSQLString() {
    return (String) ContextHolderUtils.getRequest().getAttribute(
            Globals.MENU_DATA_AUTHOR_RULE_SQL);
  }

  /**
   * 往链接请求里面，传入数据查询条件
   *
   * @param request
   * @param MENU_DATA_AUTHOR_RULE_SQL
   */
  public static synchronized void installDataSearchConditon(
          HttpServletRequest request, String MENU_DATA_AUTHOR_RULE_SQL) {
    // 1.先从request获取MENU_DATA_AUTHOR_RULE_SQL，如果存则获取到sql串
    String ruleSql = loadDataSearchConditonSQLString();
    ruleSql += MENU_DATA_AUTHOR_RULE_SQL; // 2.往sql串里面增量拼新的条件
    request.setAttribute(Globals.MENU_DATA_AUTHOR_RULE_SQL,
            ruleSql);
  }
}
