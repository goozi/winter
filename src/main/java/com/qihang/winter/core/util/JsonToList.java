package com.qihang.winter.core.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@SuppressWarnings("all")
public class JsonToList {

	
	//把前台传过来的 json转成LIST<对象>
	public static List getBeanListFromJsonData(String data, Class beanClass)
	  {
	    JSONArray jsonArray = JSONArray.fromObject(data);
	    List list = new ArrayList(jsonArray.size());
	    for (Iterator iter = jsonArray.iterator(); iter.hasNext(); ) {
	      JSONObject jsonObject = (JSONObject)iter.next();
	      list.add(JSONObject.toBean(jsonObject, beanClass));
	    }
	    return list;
	  }
	
	//前台页面传时间过来转换字符串时间变成data时间
	public static List getBeanListFromJsonData(String data, String[] dateProps, String dateFormat, Class beanClass)
	  {
	    return getBeanListFromJsonData(formatJsonDataArray(data, dateProps, 
	      dateFormat), beanClass);
	  }
	
	//进行进一步把日期字符串转成DATE类型进行循环判断每一个JSONObject是否包含给定的字段
	public static String formatJsonDataArray(String jsonData, String[] dateProps, String dateFormat)
	  {
	    if (dateProps == null) {
	      return jsonData;
	    }
	    JSONArray jsonArray = JSONArray.fromObject(jsonData);
	    for (int i = 0; i < jsonArray.size(); ++i) {
	      JSONObject jsonObject = jsonArray.getJSONObject(i);
	      for (Iterator iter = Arrays.asList(dateProps).iterator(); iter
	        .hasNext(); )
	      {
	        String dateProp = (String)iter.next();
	        if (jsonObject.has(dateProp)) {
	          fixJSONObject(jsonObject, dateProp, dateFormat);
	        }
	      }
	    }
	    return jsonArray.toString();
	  }
	
	//替换对应的字段变成 DATE类型
	 private static void fixJSONObject(JSONObject jsonObject, String dateProp, String dateFormat)
	  {
	    try
	    {
	      if ((jsonObject.get(dateProp).equals(null)) || 
	        (jsonObject.get(dateProp).equals(""))) {
	        jsonObject.put(dateProp, new JSONObject(true));
	      }
	      else
	      {
	    	  String str_date = jsonObject.get(dateProp).toString();
	    	  if(str_date.length()==10){
	    		  Date date = new SimpleDateFormat("yyyy-MM-dd").parse(str_date);
	    		  jsonObject.put(dateProp, "{\"time\":" + JSONObject.fromObject(date).get("time") + "}");
	    	  }else{
	    		  Date date = new SimpleDateFormat(dateFormat).parse(str_date);
	    		  jsonObject.put(dateProp, "{\"time\":" + JSONObject.fromObject(date).get("time") + "}");
	    	  }
	         //
	      }
	    }
	    catch (Exception e) {
	      e.printStackTrace();
	    }
	  }
}
