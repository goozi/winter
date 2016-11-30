package com.qihang.winter.winterdao.aop;

import com.qihang.winter.winterdao.annotation.Arguments;
import com.qihang.winter.winterdao.annotation.ResultType;
import com.qihang.winter.winterdao.annotation.Sql;
import com.qihang.winter.winterdao.def.WinterDaoConstants;
import com.qihang.winter.winterdao.hibernate.dao.IGenericBaseCommonDao;
import com.qihang.winter.winterdao.pojo.WinterDaoPage;
import com.qihang.winter.winterdao.spring.rowMapper.WinterColumnMapRowMapper;
import com.qihang.winter.winterdao.spring.rowMapper.WinterColumnOriginalMapRowMapper;
import com.qihang.winter.winterdao.util.FreemarkerParseFactory;
import com.qihang.winter.winterdao.util.WinterDaoUtil;
import ognl.Ognl;
import ognl.OgnlException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.engine.jdbc.internal.BasicFormatterImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Zerrion
 * @version V1.0
 * @Title:WinterDaoHandler
 * @description:WinterDAO 拦截器
 * @mail goozi@163.com
 * @category www.qihangsoft.com
 */
@SuppressWarnings("rawtypes")
public class WinterDaoHandler implements InvocationHandler {

  private static final Logger logger = Logger.getLogger(WinterDaoHandler.class);

  private static final BasicFormatterImpl formatter = new BasicFormatterImpl();

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

  @Autowired
  private IGenericBaseCommonDao winterDaoHiberCommonDao;

  private String UPPER_KEY = "upper";

  private String LOWER_KEY = "lower";
  /**
   * map的关键字类型 三个值
   */
  private String keyType = "origin";
  private boolean formatSql = false;

  private boolean showSql = false;

  private String dbType;

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    // 返回结果
    Object returnObj = null;
    // SQL模板
    String templateSql = null;
    // SQL模板参数
    Map<String, Object> sqlParamsMap = new HashMap<String, Object>();
    // 分页参数
    WinterDaoPage pageSetting = new WinterDaoPage();

    // Step.0 判断是否是Hiber实体维护方法，如果是执行Hibernate方式实体维护
    Map<String, Object> rs = new HashMap<String, Object>();
    if (winterDaoHiber(rs, method, args)) {
      return rs.get("returnObj");
    }
    // Step.1装载SQL模板，所需参数
    templateSql = installDaoMetaData(pageSetting, method, sqlParamsMap, args);

    // Step.3解析SQL模板，返回可执行SQL
    String executeSql = parseSqlTemplate(method, templateSql, sqlParamsMap);

    // Step.4 组装SQL占位符参数
    Map<String, Object> sqlMap = installPlaceholderSqlParam(executeSql, sqlParamsMap);

    // Step.5 获取SQL执行返回值
    try {
      returnObj = getReturnMinidaoResult(dbType, pageSetting, method, executeSql, sqlMap);
    } catch (EmptyResultDataAccessException e) {
      returnObj = null;
    }
    if (showSql) {
      logger.info("WinterDao-SQL:\n\n" + (formatSql == true ? formatter.format(executeSql) : executeSql) + "\n");
    }
    return returnObj;
  }

  /**
   * 判斷是否是執行的方法（非查詢）
   *
   * @param methodName
   * @return
   */
  private static boolean checkActiveKey(String methodName) {
    String keys[] = WinterDaoConstants.INF_METHOD_ACTIVE.split(",");
    for (String s : keys) {
      if (methodName.startsWith(s))
        return true;
    }
    return false;
  }

  /**
   * 判斷是否批處理
   *
   * @param methodName
   * @return
   */
  private static boolean checkBatchKey(String methodName) {
    String keys[] = WinterDaoConstants.INF_METHOD_BATCH.split(",");
    for (String s : keys) {
      if (methodName.startsWith(s))
        return true;
    }
    return false;
  }

  /**
   * 把批量处理的结果拼接起来
   *
   * @author Zerrion
   * @date 2013-11-17
   */
  private void addResulArray(int[] result, int index, int[] arr) {
    int length = arr.length;
    for (int i = 0; i < length; i++) {
      result[index - length + i] = arr[i];
    }
  }

  /**
   * 批处理
   *
   * @return
   * @author Zerrion
   * @date 2013-11-17
   */
  private int[] batchUpdate(String executeSql) {
    String[] sqls = executeSql.split(";");
    if (sqls.length < 100) {
      return jdbcTemplate.batchUpdate(sqls);
    }
    int[] result = new int[sqls.length];
    List<String> sqlList = new ArrayList<String>();
    for (int i = 0; i < sqls.length; i++) {
      sqlList.add(sqls[i]);
      if (i % 100 == 0) {
        addResulArray(result, i + 1, jdbcTemplate.batchUpdate(sqlList.toArray(new String[0])));
        sqlList.clear();
      }
    }
    addResulArray(result, sqls.length, jdbcTemplate.batchUpdate(sqlList.toArray(new String[0])));
    return result;
  }

  /**
   * 根据参数设置map的key大小写
   **/
  private RowMapper<Map<String, Object>> getColumnMapRowMapper() {
    if (getKeyType().equalsIgnoreCase(LOWER_KEY)) {
      return new WinterColumnMapRowMapper();
    } else if (getKeyType().equalsIgnoreCase(UPPER_KEY)) {
      return new ColumnMapRowMapper();
    } else {
      return new WinterColumnOriginalMapRowMapper();
    }
  }

  /**
   * 获取总数sql - 如果要支持其他数据库，修改这里就可以
   *
   * @param sql
   * @return
   */
  private String getCountSql(String sql) {
    return "select count(0) from (" + sql + ") tmp_count";
  }

  public String getDbType() {
    return dbType;
  }

  public JdbcTemplate getJdbcTemplate() {
    return jdbcTemplate;
  }

  public String getKeyType() {
    return keyType;
  }

  public IGenericBaseCommonDao getWinterDaoHiberCommonDao() {
    return winterDaoHiberCommonDao;
  }

  public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
    return namedParameterJdbcTemplate;
  }

  /**
   * 获取MiniDao处理结果集
   *
   * @param dbType
   * @param pageSetting
   * @param method
   * @param executeSql
   * @param paramMap
   * @return 结果集
   */
  @SuppressWarnings("unchecked")
  private Object getReturnMinidaoResult(String dbType, WinterDaoPage pageSetting, Method method, String executeSql, Map<String, Object> paramMap) {
    // step.4.调用SpringJdbc引擎，执行SQL返回值
    // 5.1获取返回值类型[Map/Object/List<Object>/List<Map>/基本类型]
    String methodName = method.getName();
    // 判斷是否非查詢方法
    if (checkActiveKey(methodName)) {
      if (paramMap != null) {
        return namedParameterJdbcTemplate.update(executeSql, paramMap);
      } else {
        return jdbcTemplate.update(executeSql);
      }
    } else if (checkBatchKey(methodName)) {
      return batchUpdate(executeSql);
    } else {
      // 如果是查詢操作
      Class<?> returnType = method.getReturnType();
      if (returnType.isPrimitive()) {
        Number number = jdbcTemplate.queryForObject(executeSql, BigDecimal.class);
        // update-begin--Author:Zerrion Date:20140611 for：修复int类型的bug
        if ("int".equals(returnType.getCanonicalName())) {
          return number.intValue();
        } else if ("long".equals(returnType.getCanonicalName())) {
          return number.longValue();
        } else if ("double".equals(returnType.getCanonicalName())) {
          return number.doubleValue();
        }
      } else if (returnType.isAssignableFrom(List.class) || returnType.isAssignableFrom(WinterDaoPage.class)) {
        int page = pageSetting.getPage();
        int rows = pageSetting.getRows();
        if (page != 0 && rows != 0) {
          if (returnType.isAssignableFrom(WinterDaoPage.class)) {
            if (paramMap != null) {
              pageSetting.setTotal(namedParameterJdbcTemplate.queryForObject(getCountSql(executeSql), paramMap, Integer.class));
            } else {
              pageSetting.setTotal(jdbcTemplate.queryForObject(getCountSql(executeSql), Integer.class));
            }
          }
          executeSql = WinterDaoUtil.createPageSql(dbType, executeSql, page, rows);
        }

        RowMapper resultType = getListRealType(method);
        List list;
        if (paramMap != null) {
          list = namedParameterJdbcTemplate.query(executeSql, paramMap, resultType);
        } else {
          list = jdbcTemplate.query(executeSql, resultType);
        }
        if (returnType.isAssignableFrom(WinterDaoPage.class)) {
          pageSetting.setResults(list);
          return pageSetting;
        } else {
          return list;
        }
      } else if (returnType.isAssignableFrom(Map.class)) {
        // Map类型
        if (paramMap != null) {
          return namedParameterJdbcTemplate.queryForObject(executeSql, paramMap, getColumnMapRowMapper());
        } else {
          return jdbcTemplate.queryForObject(executeSql, getColumnMapRowMapper());
        }
      } else if (returnType.isAssignableFrom(String.class)) {
        if (paramMap != null) {
          return namedParameterJdbcTemplate.queryForObject(executeSql, paramMap, String.class);
        } else {
          return jdbcTemplate.queryForObject(executeSql, String.class);
        }
      } else if (WinterDaoUtil.isWrapClass(returnType)) {
        if (paramMap != null) {
          return namedParameterJdbcTemplate.queryForObject(executeSql, paramMap, returnType);
        } else {
          return jdbcTemplate.queryForObject(executeSql, returnType);
        }
      } else {
        // 对象类型
        RowMapper<?> rm = ParameterizedBeanPropertyRowMapper.newInstance(returnType);
        if (paramMap != null) {
          return namedParameterJdbcTemplate.queryForObject(executeSql, paramMap, rm);
        } else {
          return jdbcTemplate.queryForObject(executeSql, rm);
        }
      }
    }
    return null;
  }

  /**
   * 获取真正的类型
   *
   * @param method
   * @return
   */
  private RowMapper<?> getListRealType(Method method) {
    ResultType resultType = method.getAnnotation(ResultType.class);
    if (resultType != null) {
      if (resultType.value().equals(Map.class)) {
        return getColumnMapRowMapper();
      }
      return ParameterizedBeanPropertyRowMapper.newInstance(resultType.value());
    }
    String genericReturnType = method.getGenericReturnType().toString();
    String realType = genericReturnType.replace("java.util.List", "").replace("<", "").replace(">", "");
    if (realType.contains("java.util.Map")) {
      return getColumnMapRowMapper();
    } else if (realType.length() > 0) {
      try {
        return ParameterizedBeanPropertyRowMapper.newInstance(Class.forName(realType));
      } catch (ClassNotFoundException e) {
        logger.error(e.getMessage(), e.fillInStackTrace());
        throw new RuntimeException("winterdao get class error ,class name is:" + realType);
      }
    }
    return getColumnMapRowMapper();
  }

  /**
   * 装载SQL模板参数
   *
   * @param pageSetting
   * @param method
   * @param sqlParamsMap 返回(装载模板参数)
   * @param args
   * @return templateSql(@SQL标签的SQL)
   * @throws Exception
   */
  private String installDaoMetaData(WinterDaoPage pageSetting, Method method, Map<String, Object> sqlParamsMap, Object[] args) throws Exception {
    String templateSql = null;
    // 如果方法参数大于1个的话，方法必须使用注释标签Arguments
    boolean arguments_flag = method.isAnnotationPresent(Arguments.class);
    if (arguments_flag) {
      // [1].获取方法的参数标签
      Arguments arguments = method.getAnnotation(Arguments.class);
      logger.debug("@Arguments------------------------------------------" + Arrays.toString(arguments.value()));
      if (arguments.value().length > args.length) {
        // 校验机制-如果注释标签参数数目大于方法的参数，则抛出异常
        throw new Exception("[注释标签]参数数目，不能大于[方法参数]参数数目");
      }
      // step.2.将args转换成键值对，封装成Map对象
      int args_num = 0;
      for (String v : arguments.value()) {
        // update-begin--Author:fancq Date:20140102 for：支持多数据分页
        if (v.equalsIgnoreCase("page")) {
          pageSetting.setPage(Integer.parseInt(args[args_num].toString()));
        }
        if (v.equalsIgnoreCase("rows")) {
          pageSetting.setRows(Integer.parseInt(args[args_num].toString()));
        }
        // update-end--Author:fancq Date:20140102 for：支持多数据分页
        sqlParamsMap.put(v, args[args_num]);
        args_num++;
      }
    } else {
      // 如果未使用[参数标签]
      if (args != null && args.length > 1) {
        throw new Exception("方法参数数目>=2，方法必须使用注释标签@Arguments");
      } else if (args != null && args.length == 1) {
        // step.2.将args转换成键值对，封装成Map对象
        sqlParamsMap.put(WinterDaoConstants.SQL_FTL_DTO, args[0]);
      }

    }

    // [2].获取方法的SQL标签
    if (method.isAnnotationPresent(Sql.class)) {
      Sql sql = method.getAnnotation(Sql.class);
      // 如果用户采用自定义标签SQL，则SQL文件无效
      if (StringUtils.isNotEmpty(sql.value())) {
        templateSql = sql.value();
      }
      logger.debug("@Sql------------------------------------------" + sql.value());
    }
    return templateSql;
  }

  /**
   * 组装占位符参数 -> Map
   *
   * @param executeSql
   * @return
   * @throws OgnlException
   */
  private Map<String, Object> installPlaceholderSqlParam(String executeSql, Map sqlParamsMap) throws OgnlException {
    Map<String, Object> map = new HashMap<String, Object>();
    String regEx = ":[ tnx0Bfr]*[0-9a-z.A-Z]+"; // 表示以：开头，[0-9或者.或者A-Z大小都写]的任意字符，超过一个
    Pattern pat = Pattern.compile(regEx);
    Matcher m = pat.matcher(executeSql);
    while (m.find()) {
      logger.debug(" Match [" + m.group() + "] at positions " + m.start() + "-" + (m.end() - 1));
      String ognl_key = m.group().replace(":", "").trim();
      map.put(ognl_key, Ognl.getValue(ognl_key, sqlParamsMap));
    }
    return map;
  }

  public boolean isFormatSql() {
    return formatSql;
  }

  /**
   * MiniDao支持实体维护 说明：向下兼容Hibernate实体维护方式,实体的增删改查SQL自动生成,不需要写SQL
   *
   * @param rs
   * @param method
   * @param args
   * @return
   */
  @SuppressWarnings("unchecked")
  private boolean winterDaoHiber(Map rs, Method method, Object[] args) {
    // 是否采用Hibernate方式，进行实体维护，不需要生成SQL
    // boolean arguments_flag = entity.isAnnotationPresent(Entity.class);
    // 判断如果是持久化对象，则调用Hibernate进行持久化维护
    if (WinterDaoConstants.METHOD_SAVE_BY_HIBER.equals(method.getName())) {
      winterDaoHiberCommonDao.save(args[0]);
      return true;
    }
    if (WinterDaoConstants.METHOD_GET_BY_ID_HIBER.equals(method.getName())) {
      // 获取Dao方法与实体配置
      Class<?> clz = (Class<?>) args[0];
      rs.put("returnObj", winterDaoHiberCommonDao.get(clz, args[1].toString()));
      return true;
    }
    if (WinterDaoConstants.METHOD_GET_BY_ENTITY_HIBER.equals(method.getName())) {
      // 获取主键名
      rs.put("returnObj", winterDaoHiberCommonDao.get(args[0]));
      return true;
    }
    if (WinterDaoConstants.METHOD_UPDATE_BY_HIBER.equals(method.getName())) {
      winterDaoHiberCommonDao.saveOrUpdate(args[0]);
      return true;
    }
    if (WinterDaoConstants.METHOD_DELETE_BY_HIBER.equals(method.getName())) {
      winterDaoHiberCommonDao.delete(args[0]);
      return true;
    }
    if (WinterDaoConstants.METHOD_DELETE_BY_ID_HIBER.equals(method.getName())) {
      Class<?> clz = (Class<?>) args[0];
      winterDaoHiberCommonDao.deleteEntityById(clz, args[1].toString());
      return true;
    }
    if (WinterDaoConstants.METHOD_LIST_BY_HIBER.equals(method.getName())) {
      rs.put("returnObj", winterDaoHiberCommonDao.loadAll(args[0]));
      return true;
    }
    return false;
  }

  /**
   * 解析SQL模板
   *
   * @param method
   * @param templateSql
   * @param sqlParamsMap
   * @return 可执行SQL
   */
  private String parseSqlTemplate(Method method, String templateSql, Map<String, Object> sqlParamsMap) {
    // step.1.根据命名规范[接口名_方法名.sql]，获取SQL模板文件的路径
    String executeSql = null;

    // step.2.获取SQL模板内容
    // step.3.通过模板引擎给SQL模板装载参数,解析生成可执行SQL
    if (StringUtils.isNotEmpty(templateSql)) {
      executeSql = FreemarkerParseFactory.parseTemplateContent(templateSql, sqlParamsMap);
    } else {
      String sqlTempletPath = method.getDeclaringClass().getName().replace(".", "/").replace("/dao/", "/sql/") + "_" + method.getName() + ".sql";
      if (!FreemarkerParseFactory.isExistTemplate(sqlTempletPath)) {
        sqlTempletPath = method.getDeclaringClass().getName().replace(".", "/") + "_" + method.getName() + ".sql";
      }
      logger.debug("WinterDao-SQL-Path:" + sqlTempletPath);
      executeSql = FreemarkerParseFactory.parseTemplate(sqlTempletPath, sqlParamsMap);
    }
    return executeSql;
  }

  public void setDbType(String dbType) {
    this.dbType = dbType;
  }

  public void setFormatSql(boolean formatSql) {
    this.formatSql = formatSql;
  }

  public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  public void setKeyType(String keyType) {
    this.keyType = keyType;
  }

  public void setShowSql(boolean showSql) {
    this.showSql = showSql;
  }

}
