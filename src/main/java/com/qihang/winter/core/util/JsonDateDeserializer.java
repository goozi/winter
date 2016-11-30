/**
 * Copyright (c) 2004-2015 启航软件 http://www.qihangsoft.com
 */
package com.qihang.winter.core.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用户： LenovoM4550
 * 日期： 2015/12/4
 * 版本： 1.0
 */
public class JsonDateDeserializer extends JsonDeserializer<Date> {

  private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

  @Override
  public Date deserialize(JsonParser jp, DeserializationContext ctxt)
          throws IOException, JsonProcessingException {

    Date date = null;
    try {
      date = sdf.parse(jp.getText());
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return date;
  }
}
