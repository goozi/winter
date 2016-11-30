package com.qihang.buss.sc.util;

import com.qihang.winter.core.util.PropertiesUtil;
import com.qihang.winter.expression.ExpressionEvaluator;
import com.qihang.winter.expression.datameta.Variable;

import java.util.*;

/**
 * 根据传入的控制点表达式代码及变量返回公式的运算结果
 */
public class CpeUtil {
  public static boolean execute(String cpeCode, Map<String, Object> variables){
    PropertiesUtil propertiesUtil = new PropertiesUtil("controlPointExpression.properties");
    String cpe = propertiesUtil.readProperty(cpeCode);
    List<Variable> variableList = new ArrayList<Variable>();
    for(String key : variables.keySet()){
      variableList.add(Variable.createVariable(key, variables.get(key)));
    }
    boolean result = (Boolean)ExpressionEvaluator.evaluate(cpe,
            variableList);
    return result;
  }

  public static void main(String[] args){
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("sobCreateDate", new Date(2016,5,13));//账套创建日期6月13日
    map.put("initBillDate", new Date(2016,4,31));//初始化单据日期5月31日
    boolean b = CpeUtil.execute("initBillDateCpe",map);
    System.out.println(b);
  }
}
