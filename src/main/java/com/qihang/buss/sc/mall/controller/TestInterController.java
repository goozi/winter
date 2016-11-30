package com.qihang.buss.sc.mall.controller;

import com.qihang.buss.sc.mall.entity.OrderInfoInterVo;
import com.qihang.buss.sc.mall.entity.OrderInterVo;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.model.json.AjaxJson;
import net.sf.json.JSONArray;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by LenovoM4550 on 2016-09-10.
 * 测试接口
 */
@Scope("prototype")
@Controller
@RequestMapping("/testInterController")
public class TestInterController extends BaseController {

    /**
     * 跳转测试配置页
     * @return
     */
    @RequestMapping(params = "testPage", method = RequestMethod.POST)
    @ResponseBody
    public String toPage(@RequestBody String str){
        List<OrderInterVo> orderList = new ArrayList<OrderInterVo>();
        OrderInterVo info = new OrderInterVo();
        info.setDate("2016-09-01 12:00:00");
        info.setContact("张三");
        info.setMobilePhone("13459226737");
        info.setAddress("福建省");
        info.setFetchStyle("1");
        info.setAmount(361d);
        info.setMallorderid(1234444234);
        info.setRebateamount(4545d);
        info.setFreight(465d);
        info.setWeight(4588d);
        info.setExplanation("ssasdasdasafa打发");
        info.setLicence("2222222222222");
        info.setFetchDate("2016-09-01 12:00:00");
        List<OrderInfoInterVo> list = new ArrayList<OrderInfoInterVo>();
        for(int i=0;i<2;i++){
            OrderInfoInterVo mall = new OrderInfoInterVo();
            mall.setEscMallId(121);
            mall.setName("大白菜");
            mall.setNumber("45678913");
            mall.setTypeName("菠菜");
            mall.setPtypeName("蔬菜");
            mall.setKFPeriod(2);
            mall.setFactory("福建省三明市");
            mall.setProducingPlace("建宁县");
            mall.setWeight(12d);
            mall.setImgPath("");
            mall.setTaxPriceEx(789d);
            mall.setTaxAmountEx(1231d);
            mall.setQty(12);
            mall.setUnitName("克");
            mall.setTaxPrice(23d);
            mall.setInTaxAmount(231d);
            mall.setStockQty(23);
            mall.setCostPrice(1231d);
            mall.setSupplier("亚明");
            mall.setDisabled(0);
            mall.setBrand("标牌");
            list.add(mall);
        }
        info.setOrderInfo(list);
        JSONArray object = JSONArray.fromObject(info);

        return object.toString();
    }



    RestTemplate getRestTemplate(){
        RestTemplate template = new RestTemplate();
        List messages=new ArrayList();
        messages.add(new StringHttpMessageConverter(Charset.forName("utf-8")));
        template.setMessageConverters(messages);
        return template;
    }
    HttpHeaders getHttpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        return headers;
    }
}
