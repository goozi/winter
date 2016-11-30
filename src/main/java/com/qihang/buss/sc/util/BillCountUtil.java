package com.qihang.buss.sc.util;

import java.math.BigDecimal;

/**
 * 报表各金额统计工具类
 * @author Zerrion
 * @since 2016-07-19
 */
public class BillCountUtil {

  /**
   * 按客户ID返回该客户各历史单据中应收合计金额
   * @param customerId 客户ID
   * @return BigDecimal
   */
  public static BigDecimal getReceivableCountByCustomerId(String customerId){
    //todo 客户各历史单据应收统计
    BigDecimal receivableCount = new BigDecimal(0);
    return receivableCount;
  }
}
