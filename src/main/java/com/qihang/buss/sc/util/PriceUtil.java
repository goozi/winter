package com.qihang.buss.sc.util;

import com.qihang.winter.core.util.ApplicationContextUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016-07-28.
 */
@Scope("prototype")
@Controller
@RequestMapping("/PriceUtil")
public class PriceUtil {
  /**
   * 价格体系，判断报价单、调价政策单及单价设置中某商品的单价，报价单、调价政策单及单价设置中按系统设置的优先级来判断何者优先，
   * 如同一时间有多个报价单，优先取用最新报价单中价格
   *
   * @param billId     单据类型
   * @param customerId 客户ID
   * @param itemId     商品ID
   * @param unitId     单位ID，关联商品中单位子表
   * @param number     数量
   * @return Map<String, Object> price 单价；priceType 单价类型
   */
  @RequestMapping(params = "getPrice", method = RequestMethod.POST)
  @ResponseBody
  public static Map<String, Object> getPrice(final String billId, final String customerId, final String itemId, final String unitId, final BigDecimal number) {
    JdbcTemplate jdbcTemplate = ApplicationContextUtil.getContext().getBean(
            JdbcTemplate.class);
    Map<String, Object> price = (Map<String, Object>) jdbcTemplate.execute(new CallableStatementCreator() {
      @Override
      public CallableStatement createCallableStatement(Connection con) throws SQLException {
        String storedProc = "{call P_SC_GET_PRICE(?,?,?,?,?,?,?)}";
        CallableStatement cs = con.prepareCall(storedProc);
        cs.setString(1, billId);
        cs.setString(2, customerId);
        cs.setString(3, itemId);
        cs.setString(4, unitId);
        cs.setBigDecimal(5, number);
        cs.registerOutParameter(6, Types.DOUBLE);
        cs.registerOutParameter(7,Types.VARCHAR);
        return cs;
      }
    }, new CallableStatementCallback() {
      public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
        cs.execute();
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("price",cs.getBigDecimal(6)) ;
        map.put("priceType",cs.getString(7));
        return map;
      }
    });
    return price;
  }
}
