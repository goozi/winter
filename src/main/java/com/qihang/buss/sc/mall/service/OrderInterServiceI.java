package com.qihang.buss.sc.mall.service;

import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.core.util.PropertiesUtil;
import org.apache.commons.httpclient.util.DateParseException;

import java.text.ParseException;

/**
 * Created by LenovoM4550 on 2016-09-23.
 */
public interface OrderInterServiceI extends CommonService {

    /**
     * 销售订单同步
     * @param jsonString
     * @return
     */
    public AjaxJson synOrder(String jsonString,PropertiesUtil p) throws DateParseException, ParseException ;
}
