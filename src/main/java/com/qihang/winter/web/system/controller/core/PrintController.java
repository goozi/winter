package com.qihang.winter.web.system.controller.core;

import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.web.cgform.common.CgAutoListConstant;
import com.qihang.winter.web.cgform.entity.cgformftl.CgformFtlEntity;
import com.qihang.winter.web.cgform.entity.config.CgFormHeadEntity;
import com.qihang.winter.web.cgform.service.cgformftl.CgformFtlServiceI;
import com.qihang.winter.web.cgform.service.config.CgFormFieldServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Title: Controller
 * @Description: 打印模板及打印管理
 * @author Zerrion
 * @date 2016-10-20 10:13:55
 * @version V1.0
 *
 */
@Controller
@RequestMapping("/printController")
public class PrintController extends BaseController {
  @Autowired
  private CgFormFieldServiceI cgFormFieldService;
  @Autowired
  private CgformFtlServiceI cgformFtlService;

  @RequestMapping(params = "goList")
  public String goList(){
    return "jeecg/print/printFormList";
  }

  /**
   * easyui AJAX请求数据
   *
   * @param request
   * @param response
   * @param dataGrid
   */

  @RequestMapping(params = "datagrid")
  public void datagrid(CgFormHeadEntity cgFormHead,
                       HttpServletRequest request, HttpServletResponse response,
                       DataGrid dataGrid) {
    cgFormHead.setWindowType(null);
    CriteriaQuery cq = new CriteriaQuery(CgFormHeadEntity.class,
            dataGrid);
    cq.notEq("jformType", CgAutoListConstant.JFORM_TYPE_SUB_TABLE);
    cq.add();
    // 查询条件组装器
    HqlGenerateUtil.installHql(cq,
            cgFormHead);
    this.cgFormFieldService.getDataGridReturn(cq, true);
    TagUtil.datagrid(response, dataGrid);
  }

  /**
   * easyui AJAX请求数据
   *
   * @param request
   * @param response
   * @param dataGrid
   */

  @SuppressWarnings("unchecked")
  @RequestMapping(params = "ftlDatagrid")
  public void ftlDatagrid(CgformFtlEntity cgformFtl, HttpServletRequest request,
                       HttpServletResponse response, DataGrid dataGrid) {
    CriteriaQuery cq = new CriteriaQuery(CgformFtlEntity.class, dataGrid);
    // 查询条件组装器
    HqlGenerateUtil.installHql(cq,
            cgformFtl, request.getParameterMap());
    this.cgformFtlService.getDataGridReturn(cq, true);
    TagUtil.datagrid(response, dataGrid);
  }

  /**
   * 跳转到打印模板管理列表页面
   *
   * @param request
   */
  //TODO:放弃jacob和poi上传word，改用ckeditor
  @RequestMapping(params = "printTemplateList")
  public ModelAndView printTemplateList(HttpServletRequest request) {
    String formid = request.getParameter("formid");
    request.setAttribute("formid", formid);
    return new ModelAndView("jeecg/print/printTemplateList");
  }
}
