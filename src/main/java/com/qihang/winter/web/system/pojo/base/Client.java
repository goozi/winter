package com.qihang.winter.web.system.pojo.base;

import java.util.List;
import java.util.Map;

/**
 * 在线用户对象
 *
 * @author Zerrion
 * @version 1.0
 * @date 2013-9-28
 */
public class Client implements java.io.Serializable {

  private static final long serialVersionUID = 1L;
  private TSUser user;
  private Map<String, TSFunction> functions;
  private Map<Integer, List<TSFunction>> functionMap;
  private List<TSDataRule> dataRuleList;//数据规则列表
  private String dataRuleSql;//数据规则sql
  /**
   * 用户IP
   */
  private java.lang.String ip;
  /**
   * 登录时间
   */
  private java.util.Date logindatetime;

  public TSUser getUser() {
    return user;
  }

  public void setUser(TSUser user) {
    this.user = user;
  }


  public Map<String, TSFunction> getFunctions() {
    return functions;
  }

  public void setFunctions(Map<String, TSFunction> functions) {
    this.functions = functions;
  }

  public java.lang.String getIp() {
    return ip;
  }

  public void setIp(java.lang.String ip) {
    this.ip = ip;
  }

  public java.util.Date getLogindatetime() {
    return logindatetime;
  }

  public void setLogindatetime(java.util.Date logindatetime) {
    this.logindatetime = logindatetime;
  }

  public Map<Integer, List<TSFunction>> getFunctionMap() {
    return functionMap;
  }

  public void setFunctionMap(Map<Integer, List<TSFunction>> functionMap) {
    this.functionMap = functionMap;
  }

  public List<TSDataRule> getDataRuleList() {
    return dataRuleList;
  }

  public void setDataRuleList(List<TSDataRule> dataRuleList) {
    this.dataRuleList = dataRuleList;
  }

  public String getDataRuleSql() {
    return dataRuleSql;
  }

  public void setDataRuleSql(String dataRuleSql) {
    this.dataRuleSql = dataRuleSql;
  }
}
