package com.qihang.buss.sc.oa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by  on 2015/12/2.
 * 电子邮箱
 */
@Controller
@RequestMapping("/tScEmailController")
public class TScEmailController {

  /**
   * 视图页面跳转
   *
   * @return
   */
  @RequestMapping(params = "view")
  public ModelAndView view(HttpServletRequest request) {
    return new ModelAndView("com/qihang/buss/sc/oa/tScEmail");
  }

}
