package com.qihang.buss.sc.mall.controller;

import com.qihang.buss.sc.baseinfo.entity.*;
import com.qihang.buss.sc.baseinfo.service.TScItemTypeServiceI;
import com.qihang.buss.sc.baseinfo.service.TScOrganizationServiceI;
import com.qihang.buss.sc.mall.service.OrderInterServiceI;
import com.qihang.buss.sc.sales.entity.TScOrderEntity;
import com.qihang.buss.sc.sales.entity.TScOrderentryEntity;
import com.qihang.buss.sc.sales.service.TScOrderServiceI;
import com.qihang.buss.sc.util.BillNoGenerate;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.DateUtils;
import com.qihang.winter.core.util.ExceptionUtil;
import com.qihang.winter.core.util.PropertiesUtil;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.service.SystemService;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.util.DateParseException;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.rmi.ConnectException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by LenovoM4550 on 2016-09-10.
 * 商城订单信息接口
 */
@Scope("prototype")
@Controller
@RequestMapping("/orderInterController")
public class OrderInterController extends BaseController {

    private static final Logger logger = Logger.getLogger(OrderInterController.class);
    @Autowired
    private OrderInterServiceI orderInterServiceI;


    /**
     * 订单同步接口
     * @return
     */
    @RequestMapping(params = "synOrder", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson doPost() {
        AjaxJson j = new AjaxJson();
        //获取PHP接口地址
        PropertiesUtil p = new PropertiesUtil("mall.properties");
        String phpUrl = p.readProperty("request_order_url");
        //测试地址
       // String aa = "http://localhost:8080/scm/testInterController.do?testPage";
        StringBuilder str = new StringBuilder();
        str.append("");
        try {
            String result = HttpTemplate.templatePost(phpUrl, str.toString());
            if(!StringUtil.isEmpty(result)){
                j = orderInterServiceI.synOrder(result,p);
            }else{
                j.setSuccess(false);
                j.setMsg("接口地址有误!");
            }
        } catch (Exception e){
            logger.error(ExceptionUtil.getExceptionMessage(e));
            j.setSuccess(false);
            if(e instanceof JSONException) {
                j.setMsg("json数据格式解析有误");
            }else {
                j.setMsg("系统繁忙,请稍后再试");
            }
        }
        return j;
    }

//
//    RestTemplate getRestTemplate(){
//        RestTemplate template = new RestTemplate();
//        List messages=new ArrayList();
//        messages.add(new StringHttpMessageConverter(Charset.forName("utf-8")));
//        template.setMessageConverters(messages);
//        return template;
//    }
//    HttpHeaders getHttpHeaders(){
//        HttpHeaders headers = new HttpHeaders();
//        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//        headers.setContentType(type);
//        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
//        return headers;
//    }
}
