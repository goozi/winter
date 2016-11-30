package com.qihang.winter.tag.core.easyui;

import com.qihang.buss.sc.sysaudit.entity.TSBillInfoEntity;
import com.qihang.buss.sc.util.AccountUtil;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.enums.SysThemesEnum;
import com.qihang.winter.core.util.*;
import com.qihang.winter.web.system.pojo.base.TSConfig;
import com.qihang.winter.web.system.pojo.base.TSUser;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.web.system.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.xpath.operations.Bool;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.List;

/**
 * @author Zerrion
 */
public class FormValidationTag extends TagSupport {
  private static final long serialVersionUID = 8360534826228271024L;
  protected String formid = "formobj";// 表单FORM ID
  protected Boolean refresh = true;
  protected String callback;// 回调函数
  protected String beforeSubmit;// 提交前处理函数
  protected String btnsub = "btn_sub";// 以ID为标记触发提交事件
  protected String btnreset = "btn_reset";// 以ID为标记触发提交事件
  protected String btntemp = "btn_temp";// 以ID为标记触发提交事件,保存草稿
  protected String btnchoosetemp = "btn_temp_choose";// 以ID为标记触发提交事件,恢复草稿（即从草稿列表中选择）
  protected String layout = "div";// 表单布局
  protected String usePlugin;// 外调插件
  protected boolean dialog = true;// 是否是弹出窗口模式
  protected String action;// 表单提交路径
  protected String tabtitle;// 表单选项卡
  protected String tiptype = "1";//校验方式
  protected String styleClass;//table 样式
  protected String cssTheme;//主题样式目录默认为空
  protected String windowType = "tab";//窗口类型：dialog对话框，tab标签页方式打开
  protected boolean tipSweep = false;//只提交时验证
  protected String tranType;//单据类型；
  protected String tableName;//单据表名；
  protected String title;//标题
  protected boolean isCancel = true;//是否启用作废功能
  protected boolean saveTemp = false;//表单是否提供保存草稿、恢复草稿功能，默认true需要,false不需要
  protected boolean isPrint = true;//是否启用打印功能


  public String getCssTheme() {
    return cssTheme;
  }

  public void setCssTheme(String cssTheme) {
    this.cssTheme = cssTheme;
  }

  public String getStyleClass() {
    return styleClass;
  }

  public void setStyleClass(String styleClass) {
    this.styleClass = styleClass;
  }

  public void setTabtitle(String tabtitle) {
    this.tabtitle = tabtitle;
  }

  public void setDialog(boolean dialog) {
    this.dialog = dialog;
  }

  public void setBtnsub(String btnsub) {
    this.btnsub = btnsub;
  }

  public void setRefresh(Boolean refresh) {
    this.refresh = refresh;
  }

  public void setBtnreset(String btnreset) {
    this.btnreset = btnreset;
  }

  public void setFormid(String formid) {
    this.formid = formid;
  }

  public void setAction(String action) {
    this.action = action;
  }

  public String getTranType() {
    return tranType;
  }

  public void setTranType(String tranType) {
    this.tranType = tranType;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public boolean isCancel() {
    return isCancel;
  }

  public void setIsCancel(boolean isCancel) {
    this.isCancel = isCancel;
  }

  public boolean isPrint() {
    return isPrint;
  }

  public void setPrint(boolean print) {
    isPrint = print;
  }

  @Autowired
  private static UserService userService;

  @Autowired
  private static SystemService systemService;

  public int doStartTag() throws JspException {
    try {
      JspWriter out = this.pageContext.getOut();
/*//			if(cssTheme==null){//手工设置值优先
        Cookie[] cookies = ((HttpServletRequest) super.pageContext
						.getRequest()).getCookies();
				for (Cookie cookie : cookies) {
					if (cookie == null || StringUtils.isEmpty(cookie.getName())) {
						continue;
					}
					if (cookie.getName().equalsIgnoreCase("JEECGCSSTHEME")) {
						cssTheme = cookie.getValue();
					}
				}
//			}
			if(cssTheme==null||"default".equals(cssTheme))cssTheme="";*/
      StringBuffer sb = new StringBuffer();
      if ("div".equals(layout)) {
        sb.append("<div id=\"content\">");
        sb.append("<div id=\"wrapper\">");
        sb.append("<div id=\"steps\">");
      }
      sb.append("<form id=\"" + formid + "\" ");
      if (this.getStyleClass() != null) {
        sb.append("class=\"" + this.getStyleClass() + "\" ");
      }
      sb.append(" action=\"" + action + "\" name=\"" + formid + "\" method=\"post\">");
      if ("btn_sub".equals(btnsub) && dialog && windowType.equals("dialog")) {
        sb.append("<input type=\"hidden\" id=\"" + btnsub + "\" class=\"" + btnsub + "\"/>");
      }
      String load = pageContext.getRequest().getParameter("load");
      //配置数据精度
      //CFG_NUMBER 数量精度
      //CFG_UNITP_RICE 单价精度
      //CFG_MONEY 金额精度
      //CFG_RATES 税率精度
      //CFG_DISCOUNT_RATE 折扣率精度
      //CFG_OTHER 其他
      //获取账套当前期别日期
      String stageDate = DateUtils.formatDate(AccountUtil.getAccountStartDate(), "yyyy-MM-dd");
      String accountStartDate = DateUtils.formatDate(AccountUtil.getAccountInitStartDate(), "yyyy-MM-dd");
      try {
        accountStartDate = DateUtils.formatAddDate(stageDate, "yyyy-MM-dd", -1);//取开账日期前一天为最大日期控制
      } catch (Exception e) {
        e.printStackTrace();
      }
      StringBuffer dateMaxOrMinDateHtml = new StringBuffer();//产生单据日期的日期选择范围控制html
      dateMaxOrMinDateHtml.append("<script type='text/javascript'>");
      dateMaxOrMinDateHtml.append("$(document).ready(function(){");
      dateMaxOrMinDateHtml.append("if ($('#date').length>0){");
      dateMaxOrMinDateHtml.append("var tranType = $('#tranType').val();");
      dateMaxOrMinDateHtml.append("var dateMaxOrMinHtml = '';");
      dateMaxOrMinDateHtml.append("var stageHtml = '';");
      dateMaxOrMinDateHtml.append("var tranType = $('#tranType').val();");
      dateMaxOrMinDateHtml.append("if (tranType=='" + Globals.SC_IC_INITIAL_TRANTYPE + "' || tranType=='" + Globals.SC_IC_BEGDATA_TRANTYPE
              + "' || tranType=='" + Globals.SC_IC_BEGDATAPAYABLE_TRANTYPE + "' || tranType=='" + Globals.SC_IC_BEGDATAINCOMEPAY_TRANTYPE + "'){");
      dateMaxOrMinDateHtml.append("dateMaxOrMinHtml = '{maxDate:\"" + accountStartDate + "\"}';");
      dateMaxOrMinDateHtml.append("stageHtml = '" + accountStartDate + "';");
      dateMaxOrMinDateHtml.append("}else{");
      dateMaxOrMinDateHtml.append("dateMaxOrMinHtml = '{minDate:\"" + stageDate + "\"}';");
      dateMaxOrMinDateHtml.append("stageHtml = '" + stageDate + "';");
      dateMaxOrMinDateHtml.append("}");
      dateMaxOrMinDateHtml.append("$('#date').attr('stageDate',stageHtml);\n");
      dateMaxOrMinDateHtml.append("debugger;\n");
      dateMaxOrMinDateHtml.append("$('#date').attr('onclick','WdatePicker(' + dateMaxOrMinHtml +')');\n");
      dateMaxOrMinDateHtml.append("}");
      dateMaxOrMinDateHtml.append("});");
      dateMaxOrMinDateHtml.append("</script>");
      sb.append(dateMaxOrMinDateHtml.toString());//输出控制单据日期可选时间范围的脚本

      sb.append("<input id='tableName' type='hidden' value='" + tableName + "'>");
      systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
      List<TSConfig> configs = systemService.findHql("from TSConfig");
      if (configs.size() > 0) {
        for (TSConfig config : configs) {
          sb.append("<input type='hidden' id='" + config.getCode() + "' value='" + config.getContents() + "'>");
        }
      }
      if (windowType.equals("tab") && !(load == null ? "" : load).equals("detail")) {
        sb.append("<div style=\"margin-bottom:5px;width:99.5%;padding: 3px;border: 0px solid ;margin-bottom: 2px;height: 26px;\">");
        sb.append("<div class=\"ui_buttons\" style=\"float:left;\">");
//                "<input type=\"button\" id=\"" + btnsub + "\" class=\"ui_state_highlight\" value=\"确定\"/>&nbsp;" +
//                "<input type=\"button\" id=\"closeTab\" class=\"button\" value=\"关闭\"/>");
        sb.append("<a id=\"" + btnsub + "\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'new-icon-save'\">保存</a> ");

        if (!(load == null ? "" : load).equals("edit")) {//非编辑、查看页面，即只有新增页面需要保存草稿和恢复草稿
          if (this.saveTemp) {
            sb.append("<a id=\"" + btntemp + "\" href=\"javascript:doTemp('" + this.formid + "'," + this.tranType + ")\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'new-icon-billtemp'\">保存草稿</a> ");
            sb.append("<a id=\"" + btnchoosetemp + "\" href=\"javascript:doTempChooseDialog('" + this.formid + "'," + this.tranType + ")\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'new-icon-billtempchoose'\">恢复草稿</a> ");
          }
        }
        sb.append("<a id=\"closeTab\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'new-icon-close'\">退出</a> ");
        sb.append("</div>");
        sb.append("</div></br>");
        if (StringUtils.isNotEmpty(title)) {
          sb.append("<div style='width:100%;height:70px'>");
          sb.append("<div id='titleInfo' style='text-align:left;width:30%;height:70px;float:left;display:inline'><span style='padding-left:10%;font-size:350%;font-weight:bold'>" + title + "</span></div>");
          sb.append("<div id='picInfo' style='margin-top:10px;text-align:center;width:20%;height:60px;float:left;display:inline'></div>");
          sb.append("<div style='width:50%;height:70px;text-align:left;float:right;display:inline;'></br>");
          //sb.append("<div style='text-align:right'><span style='font-size:15px'>单据编号：<input type='text' id='billNo' onblur='setBillNo()' style='width:150px;font-size:15px'/></span></div>");
          //控制单据编号是否可以编辑
          if (tranType != null && !"".equals(tranType)) {
            //  TSBillInfoEntity billInfoEntity = systemService.findUniqueByProperty(TSBillInfoEntity.class,"billId",tranType);
            List<TSBillInfoEntity> billInfoList = systemService.findByProperty(TSBillInfoEntity.class, "billId", tranType);
            if (billInfoList.size() > 0) {
              if (billInfoList.get(0).getIsEdit() == 1) {
                sb.append("<div style='text-align:right'><span style='font-size:15px'>单据编号：<input type='text' id='billNoStr' onblur='setBillNo()' style='width:150px;font-size:15px'/></span></div>");
              } else {
                sb.append("<div style='text-align:right'><span style='font-size:15px'>单据编号：<input type='text' id='billNoStr' onblur='setBillNo()' style='width:150px;font-size:15px' readonly='readonly' /></span></div>");
              }
            } else {
              sb.append("<div style='text-align:right'><span style='font-size:15px'>单据编号：<input type='text' id='billNoStr' onblur='setBillNo()' style='width:150px;font-size:15px'/></span></div>");
            }
          } else {
            sb.append("<div style='text-align:right'><span style='font-size:15px'>单据编号：<input type='text' id='billNoStr' onblur='setBillNo()' style='width:150px;font-size:15px'/></span></div>");
          }
//          sb.append("<div style='text-align:right'><span style='font-size:15px'>单据编号：<input type='text' id='billNoStr' onblur='setBillNo()' style='width:150px;font-size:15px'/></span></div>");
          sb.append("<div style='text-align:right'><span style='font-size:15px'>单据日期：<input id='date' type='text' style='width: 150px;font-size:15px' class='Wdate' onblur='setDate()'; onClick='WdatePicker()' datatype='*'/></span></div>");
          sb.append("</div>");
          sb.append("</div>");
        }
      } else if (windowType.equals("tab") && (load == null ? "" : load).equals("detail")) {
        TSUser user = ResourceUtil.getSessionUserName();
        userService = ApplicationContextUtil.getContext().getBean(UserService.class);
        String roleCode = userService.getUserRole(user);
        if (StringUtils.isNotEmpty(tranType) && roleCode.indexOf("sc_allowAudit") > -1) {
          sb.append("<input id='tranType' type='hidden' value='" + tranType + "'/>");
          sb.append("<div style=\"margin-bottom:5px;width:99.5%;padding: 3px;border: 0px solid ;margin-bottom: 2px;height: 26px;\">");
          sb.append("<div class=\"ui_buttons\" style=\"float:left;\">");
//          sb.append("<input type=\"button\" id=\"auditBtn\" class=\"button\" style=\"display:none\" value=\"审核\"/>");
//          sb.append("<input type=\"button\" id=\"unAuditBtn\" class=\"button\" style=\"display:none\" value=\"反审核\"/>");
          sb.append("<a id=\"add\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'new-icon-add'\">新增</a> ");
          sb.append("<a id=\"edit\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'new-icon-edit'\">编辑</a> ");
          sb.append("<a id=\"auditBtn\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'new-icon-audit'\">审核</a> ");
          sb.append("<a id=\"unAuditBtn\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'new-icon-unaudit'\">反审核</a> ");
          if (isCancel) {
            sb.append("<a id=\"cancelBtn\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'new-icon-cancellation'\">作废</a> ");
            sb.append("<a id=\"unCancelBtn\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'new-icon-uncancellation'\">反作废</a> ");
          }
          sb.append("<a id=\"closedBtn\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'new-icon-close'\">关闭</a> ");
          sb.append("<a id=\"unClosedBtn\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'new-icon-unclose'\">反关闭</a> ");
          String iscopy = ResourceUtil.getParameter("iscopy");
          if (iscopy != null && iscopy.toLowerCase().equals("true")) {//从url请求参数中判断列表是否有复制按钮，如果有，则查看页面也加复制按钮
            sb.append("<a id=\"copyBtn\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'new-icon-copy'\">复制</a> ");
          }
          sb.append("<a id=\"closeTab\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'new-icon-close'\">退出</a> ");
          if(isPrint) {
            sb.append("<a id=\"printForm\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'new-icon-print'\">打印</a> ");
          }
          sb.append("</div>");
          sb.append("</div></br>");
          if (StringUtils.isNotEmpty(title)) {
            sb.append("<div style='width:100%;height:70px'>");
            sb.append("<div id='titleInfo' style='text-align:left;width:30%;height:70px;float:left;display:inline'><span style='padding-left:10%;font-size:350%;font-weight:bold'>" + title + "</span></div>");
            sb.append("<div id='picInfo' style='margin-top:10px;text-align:center;width:20%;height:60px;float:left;display:inline'></div>");
            sb.append("<div style='width:50%;height:70px;text-align:left;float:right;display:inline;'></br>");
            sb.append("<div style='text-align:right'><span style='font-size:15px'>单据编号：<input type='text' id='billNoStr' onblur='setBillNo()' style='width:150px;font-size:15px'/></span></div>");
            sb.append("<div style='text-align:right'><span style='font-size:15px'>单据日期：<input id='date' type='text' style='width: 150px;font-size:15px' class='Wdate' onblur='setDate()'; onClick='WdatePicker()' datatype='*'/></span></div>");
            sb.append("</div>");
            sb.append("</div>");
          }
        } else if (windowType.equals("tab")) {
          sb.append("<div style=\"margin-bottom:5px;width:99.5%;padding: 3px;border: 0px solid ;margin-bottom: 2px;height: 26px;\">");
          sb.append("<div class=\"ui_buttons\" style=\"float:left;\">");
          sb.append("<a id=\"add\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'new-icon-add'\">新增</a> ");
          sb.append("<a id=\"edit\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'new-icon-edit'\">编辑</a> ");
          String iscopy = ResourceUtil.getParameter("iscopy");
          if (iscopy != null && iscopy.toLowerCase().equals("true")) {//从url请求参数中判断列表是否有复制按钮，如果有，则查看页面也加复制按钮
            sb.append("<a id=\"copyBtn\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'new-icon-copy'\">复制</a> ");
          }
          sb.append("<a id=\"closeTab\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'new-icon-close'\">退出</a> ");
////            sb.append("<a id=\"print\" href=\"#\" class=\"easyui-linkbutton\" data-options=\"plain:true,iconCls:'new-icon-print'\">打印</a> ");
          sb.append("</div>");
          sb.append("</div></br>");
//          if(StringUtils.isNotEmpty(title)) {
//            sb.append("<div style='width:100%;height:70px'>");
//            sb.append("<div id='titleInfo' style='text-align:left;width:30%;height:70px;float:left;display:inline'><span style='padding-left:10%;font-size:350%;font-weight:bold'>" + title + "</span></div>");
//            sb.append("<div id='picInfo' style='margin-top:10px;text-align:center;width:20%;height:60px;float:left;display:inline'></div>");
//            sb.append("<div style='width:50%;height:50px;text-align:left;float:right;display:inline'></br>");
//            sb.append("<div style='text-align:right'><span style='font-size:15px'>单据编号：<input type='text' id='billNoStr' style='width:150px;font-size:15px'/></span></div>");
//            sb.append("<div style='text-align:right'><span style='font-size:15px'>单据日期：<input id='date' type='text' style='width: 150px;font-size:15px' class='Wdate' onblur='setDate()'; onClick='WdatePicker()' datatype='*'/></span></div>");
//            sb.append("</div>");
//            sb.append("</div>");
//          }
        }
      }
      sb.append("<input type='hidden' value='" + title + "' id='billTitle'>");
      out.print(sb.toString());
      out.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return EVAL_PAGE;
  }


  public int doEndTag() throws JspException {
    try {
      String preTitle = pageContext.getRequest().getParameter("preTitle");
      String load = pageContext.getRequest().getParameter("load");
      String lang = (String) ((HttpServletRequest) this.pageContext.getRequest()).getSession().getAttribute("lang");
      SysThemesEnum sysThemesEnum = SysThemesUtil.getSysTheme((HttpServletRequest) super.pageContext.getRequest());
      JspWriter out = this.pageContext.getOut();
      StringBuffer sb = new StringBuffer();
      if (layout.equals("div")) {
//				if("metro".equals(cssTheme)){
//					sb.append("<link rel=\"stylesheet\" href=\"plug-in/Validform/css/"+cssTheme+"/divfrom.css\" type=\"text/css\"/>");
//				}else{
//					sb.append("<link rel=\"stylesheet\" href=\"plug-in/Validform/css/divfrom.css\" type=\"text/css\"/>");
//				}
        //divfrom.css
        sb.append(SysThemesUtil.getValidformDivfromTheme(sysThemesEnum));
        if (tabtitle != null)
          sb.append("<script type=\"text/javascript\" src=\"plug-in/Validform/js/form.js\"></script>");
      }
//			if("metro".equals(cssTheme)){
//				sb.append("<link rel=\"stylesheet\" href=\"plug-in/Validform/css/"+cssTheme+"/style.css\" type=\"text/css\"/>");
//				sb.append("<link rel=\"stylesheet\" href=\"plug-in/Validform/css/"+cssTheme+"/tablefrom.css\" type=\"text/css\"/>");
//			}else{
//				sb.append("<link rel=\"stylesheet\" href=\"plug-in/Validform/css/style.css\" type=\"text/css\"/>");
//				sb.append("<link rel=\"stylesheet\" href=\"plug-in/Validform/css/tablefrom.css\" type=\"text/css\"/>");
//			}
      //style.css
      sb.append(SysThemesUtil.getValidformStyleTheme(sysThemesEnum));
      //tablefrom.css
      sb.append(SysThemesUtil.getValidformTablefrom(sysThemesEnum));
      sb.append(StringUtil.replace("<script type=\"text/javascript\" src=\"plug-in/Validform/js/Validform_v5.3.1_min_{0}.js\"></script>", "{0}", lang));
      sb.append(StringUtil.replace("<script type=\"text/javascript\" src=\"plug-in/Validform/js/Validform_Datatype_{0}.js\"></script>", "{0}", lang));
      sb.append(StringUtil.replace("<script type=\"text/javascript\" src=\"plug-in/Validform/js/datatype_{0}.js\"></script>", "{0}", lang));
      sb.append("<script type=\"text/javascript\" src=\"plug-in/scm/js/auditTools.js\"></script>");
      sb.append("<script type=\"text/javascript\" src=\"plug-in/scm/js/editView.js\"></script>");
      if (usePlugin != null) {
        if (usePlugin.indexOf("jqtransform") >= 0) {
          sb.append("<SCRIPT type=\"text/javascript\" src=\"plug-in/Validform/plugin/jqtransform/jquery.jqtransform.js\"></SCRIPT>");
          sb.append("<LINK rel=\"stylesheet\" href=\"plug-in/Validform/plugin/jqtransform/jqtransform.css\" type=\"text/css\"></LINK>");
        }
        if (usePlugin.indexOf("password") >= 0) {
          sb.append("<SCRIPT type=\"text/javascript\" src=\"plug-in/Validform/plugin/passwordStrength/passwordStrength-min.js\"></SCRIPT>");
        }
      }
      sb.append("<script type=\"text/javascript\">");
      sb.append("var curForm;\n");
      sb.append("$(function(){\n");
      sb.append("$('#printForm').on('click',function(){\n" );
      sb.append("printForm('"+tableName+"','"+title+"');\n" );
      sb.append("});\n");
      sb.append("checkIsAudit(" + tranType + ",'" + load + "');\n");
      sb.append("$('#closeTab').click(function(){\n" +
              "$.dialog.confirm('确定退出当前界面?', function (r) {\n" +
              "      if (r) {\n" +
              "var title = window.top.$('.tabs-selected:first').text();\n" +
              "var preTitle = '" + preTitle + "';\n" +
              "window.top.$('#maintabs').tabs('select',preTitle);\n" +
              "window.top.$('#maintabs').tabs('close',title);\n" +
              "}\n" +
              "}).zindex();\n" +
              "});\n");
      sb.append(" curForm = $(\"#" + formid + "\").Validform({");
      if (this.getTiptype() != null && !"".equals(this.getTiptype())) {
        sb.append("tiptype:" + this.getTiptype() + ",");
      } else {
        sb.append("tiptype:1,");
      }
//			sb.append("tiptype:function(msg,o,cssctl){");
//			sb.append("if(!o.obj.is(\"form\")){");
//			sb.append("	var objtip=o.obj.parent().find(\".Validform_checktip\");");
//			sb.append("	cssctl(objtip,o.type);");
//			sb.append("	objtip.text(msg);");
//			sb.append("	var infoObj=o.obj.parent().find(\".Validform_checktip\");");
//			sb.append("	if(o.type==2){");
//			sb.append("		infoObj.hide();infoObj.show();");
//			sb.append("		infoObj.fadeOut(8000);");
//			sb.append("	}else{");
//			sb.append("		infoObj.hide();");
//			sb.append("		var left=o.obj.offset().left;");
//			sb.append("		var top=o.obj.offset().top;");
//			sb.append("		infoObj.css({	");
//			sb.append("			left:left+85,");
//			sb.append("			top:top-10");
//			sb.append("		}).show().animate({");
//			sb.append("			top:top-5");
//			sb.append("		},200);infoObj.fadeOut(8000);");
//			sb.append("	}");
//			sb.append("}");
//			sb.append("},");
      sb.append("btnSubmit:\"#" + btnsub + "\",");
      sb.append("btnReset:\"#" + btnreset + "\",");
      sb.append("btntemp:\"#" + btntemp + "\",");
      sb.append("btnchoosetemp:\"#" + btnchoosetemp + "\",");
      sb.append("ajaxPost:true,");
      sb.append("tipSweep:" + tipSweep + ",");
      if (beforeSubmit != null) {
        sb.append("beforeSubmit:function(curform){var tag=false;");
        sb.append("return " + beforeSubmit);
        if (beforeSubmit.indexOf("(") < 0) {
          sb.append("(curform);");
        }
        sb.append("},");
      }
      if (usePlugin != null) {
        StringBuffer passsb = new StringBuffer();
        if (usePlugin.indexOf("password") >= 0) {
          passsb.append("passwordstrength:{");
          passsb.append("minLen:6,");
          passsb.append("maxLen:18,");
          passsb.append("trigger:function(obj,error)");
          passsb.append("{");
          passsb.append("if(error)");
          passsb.append("{");
          passsb.append("obj.parent().next().find(\".Validform_checktip\").show();");
          passsb.append("obj.find(\".passwordStrength\").hide();");
          passsb.append("}");
          passsb.append("else");
          passsb.append("{");
          passsb.append("$(\".passwordStrength\").show();");
          passsb.append("obj.parent().next().find(\".Validform_checktip\").hide();");
          passsb.append("}");
          passsb.append("}");// trigger结尾
          passsb.append("}");// passwordstrength结尾
        }
        sb.append("usePlugin:{");
        if (usePlugin.indexOf("password") >= 0) {
          sb.append(passsb);
        }
        StringBuffer jqsb = new StringBuffer();
        if (usePlugin.indexOf("jqtransform") >= 0) {
          if (usePlugin.indexOf("password") >= 0) {
            sb.append(",");
          }
          jqsb.append("jqtransform :{selector:\"select\"}");
        }
        if (usePlugin.indexOf("jqtransform") >= 0) {
          sb.append(jqsb);
        }
        sb.append("},");
      }
      sb.append("callback:function(data){");
      if (windowType.equals("tab")) {
        if (callback != null && callback.contains("@Override")) {//复写默认callback
          sb.append(callback.replaceAll("@Override", "") + "(data);");
        } else {
//          sb.append("var win = frameElement.api.opener;");
          //先判断是否成功，成功再刷新父页面，否则return false
          // 如果不成功，返回值接受使用data.msg. 原有的data.responseText会报null

          sb.append("var preTitle = '" + preTitle + "';var title = window.top.$('.tabs-selected:first').text();if(data.success==true){$.dialog.confirm('保存成功，是否关闭页面？', function () { window.top.closetab(title); }); }else{if(" +
                  "data.responseText==''||data.responseText==undefined){$.messager.alert('错误', data.msg);" +
                  "$.Hidemsg();}else{try{var emsg = data.responseText.substring(data.responseText.indexOf('错误描述')," +
                  "data.responseText.indexOf('错误信息')); $.messager.alert('错误',emsg);$.Hidemsg();}" +
                  "catch(ex){$.messager.alert('错误',data.responseText+\"\");$.Hidemsg();}} return false;}");
          //
          if (refresh) {
            sb.append("var tab = window.top.$('#maintabs').tabs('getTab',preTitle);" +
                    "window.top.$('#maintabs').tabs('select',preTitle);" +
                    "var win;\n" +
                    "if(preTitle==null){\n" +
                    "win = frameElement.api.opener;}\n" +
                    "else{win = $(tab).children(':first')[0].contentWindow;}\n" +
                    "win.reloadTable();\n");
          }
          if (StringUtil.isNotEmpty(callback)) {
            sb.append("win." + callback + "(data);");
          }
        }
      } else {
        if (dialog) {
          if (callback != null && callback.contains("@Override")) {//复写默认callback
            sb.append(callback.replaceAll("@Override", "") + "(data);");
          } else {
            sb.append("var win = frameElement.api.opener;");
            //先判断是否成功，成功再刷新父页面，否则return false
            // 如果不成功，返回值接受使用data.msg. 原有的data.responseText会报null
            sb.append("if(data.success==true){frameElement.api.close();win.tip(data.msg);}else{if(" +
                    "data.responseText==''||data.responseText==undefined){$.messager.alert('错误', data.msg);" +
                    "$.Hidemsg();}else{try{var emsg = data.responseText.substring(data.responseText.indexOf('错误描述')," +
                    "data.responseText.indexOf('错误信息')); $.messager.alert('错误',emsg);$.Hidemsg();}" +
                    "catch(ex){$.messager.alert('错误',data.responseText+\"\");$.Hidemsg();}} return false;}");
            //
            if (refresh) {
              sb.append("win.reloadTable();");
            }
            if (StringUtil.isNotEmpty(callback)) {
              sb.append("win." + callback + "(data);");
            }
          }
          //失败tip不提示
          //sb.append("win.tip(data.msg);");
        } else {
          sb.append("" + callback + "(data);");
        }
      }

      sb.append("}" + "});" + "});" + "</script>");
      sb.append("");
      sb.append("</form>");
      if ("div".equals(layout)) {
        sb.append("</div>");
        if (tabtitle != null) {
          String[] tabtitles = tabtitle.split(",");
          sb.append("<div id=\"navigation\" style=\"display: none;\">");
          sb.append("<ul>");
          for (String string : tabtitles) {
            sb.append("<li>");
            sb.append("<a href=\"#\">" + string + "</a>");
            sb.append("</li>");
          }
          sb.append("</ul>");
          sb.append("</div>");
        }
        sb.append("</div></div>");
      }
      out.print(sb.toString());
      out.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return EVAL_PAGE;
  }

  public void setUsePlugin(String usePlugin) {
    this.usePlugin = usePlugin;
  }

  public void setLayout(String layout) {
    this.layout = layout;
  }

  public void setBeforeSubmit(String beforeSubmit) {
    this.beforeSubmit = beforeSubmit;
  }

  public void setCallback(String callback) {
    this.callback = callback;
  }

  public String getTiptype() {
    return tiptype;
  }

  public void setTiptype(String tiptype) {
    this.tiptype = tiptype;
  }


  public String getWindowType() {
    return windowType;
  }

  public void setWindowType(String windowType) {
    this.windowType = windowType;
  }

  public boolean isTipSweep() {
    return tipSweep;
  }

  public void setTipSweep(boolean tipSweep) {
    this.tipSweep = tipSweep;
  }

  public String getBtntemp() {
    return btntemp;
  }

  public void setBtntemp(String btntemp) {
    this.btntemp = btntemp;
  }

  public String getBtnchoosetemp() {
    return btnchoosetemp;
  }

  public void setBtnchoosetemp(String btnchoosetemp) {
    this.btnchoosetemp = btnchoosetemp;
  }

  public boolean isSaveTemp() {
    return saveTemp;
  }

  public void setSaveTemp(boolean saveTemp) {
    this.saveTemp = saveTemp;
  }
}
