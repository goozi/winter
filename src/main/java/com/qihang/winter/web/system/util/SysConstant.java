/**
 * Copyright (c) 2004-2015 启航软件 http://www.qihangsoft.com
 */
package com.qihang.winter.web.system.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户: gaozhenling
 * 日期: 15/10/7
 * 版本: 1.0
 */
public interface SysConstant {
  public static final String SESSION_KEY_OF_RAND_CODE = "randCode"; //验证码
  public Map<String,String> sessionList = new HashMap<String,String>();//会话集合
}
