package com.qihang.buss.sc.mall.controller;

import com.qihang.buss.sc.mall.service.StockbillInterServiceI;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.util.PropertiesUtil;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.web.system.service.SystemService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.util.DateParseException;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LenovoM4550 on 2016-09-18.
 * 销售出库接口
 */
@Scope("prototype")
@Controller
@RequestMapping("/stockbillInterController")
public class StockbillInterController extends BaseController {
    @Autowired
    private StockbillInterServiceI stockbillInterServiceI;

    /**
     * 销售出库接口状态发送接口
     * @param mallorders
     * @return
     * @throws DateParseException
     * @throws ParseException
     */
    @RequestMapping(params = "synStockstate", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson doPost(@Param("mallorders") String mallorders,@Param("ids") String ids) {
        AjaxJson j = new AjaxJson();
        j = stockbillInterServiceI.synStockstate(mallorders, ids);
        return j;
    }
}
