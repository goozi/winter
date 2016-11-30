package com.qihang.buss.sc.mall.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LenovoM4550 on 2016-09-18.
 */
public class HttpTemplate {

    /**
     * postq请求发送数据
     * @param url
     * @return
     */
    public static String templatePost(String url,String str){
        HttpHeaders headers = new HttpTemplate().getHttpHeaders();
        RestTemplate template = new HttpTemplate().getRestTemplate();
        HttpEntity formEntity = new HttpEntity(str,headers);
        String result = template.postForObject(url,formEntity, String.class);
        return result;
    }

    public  RestTemplate getRestTemplate(){
        RestTemplate template = new RestTemplate();
        List messages=new ArrayList();
        messages.add(new StringHttpMessageConverter(Charset.forName("utf-8")));
        template.setMessageConverters(messages);
        return template;
    }
    public  HttpHeaders getHttpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        return headers;
    }
}
