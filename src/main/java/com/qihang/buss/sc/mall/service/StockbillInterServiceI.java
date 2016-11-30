package com.qihang.buss.sc.mall.service;

import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.CommonService;

/**
 * Created by LenovoM4550 on 2016-09-23.
 */
public interface StockbillInterServiceI extends CommonService {

    public AjaxJson synStockstate(String mallorders,String ids);
}
