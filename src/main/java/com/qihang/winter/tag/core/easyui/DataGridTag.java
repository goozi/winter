package com.qihang.winter.tag.core.easyui;

import com.google.gson.Gson;
import com.qihang.buss.sc.util.AccountUtil;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.enums.SysThemesEnum;
import com.qihang.winter.core.util.*;
import com.qihang.winter.tag.vo.easyui.ColumnValue;
import com.qihang.winter.tag.vo.easyui.DataGridColumn;
import com.qihang.winter.tag.vo.easyui.DataGridUrl;
import com.qihang.winter.tag.vo.easyui.OptTypeDirection;
import com.qihang.winter.web.cgform.entity.config.CgFormHeadEntity;
import com.qihang.winter.web.cgform.service.config.CgFormFieldServiceI;
import com.qihang.winter.web.system.pojo.base.*;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.web.system.service.UserService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.*;


/**
 * 类描述：DATAGRID标签处理类
 *
 * @author Zerrion
 * @version 1.0
 * @date： 日期：2012-12-7 时间：上午10:17:45
 */
@SuppressWarnings({"serial", "rawtypes", "unchecked", "static-access"})
public class DataGridTag extends TagSupport {
  protected String fields = "";// 显示字段
  protected String searchFields = "";// 查询字段  Author:qiulu  Date:20130618 for：添加对区间查询的支持
  protected String mergeFields = "";// 行合并字段列表
  protected String name;// 表格标示
  protected String title;// 表格标示
  protected String idField = "id";// 主键字段
  protected boolean treegrid = false;// 是否是树形列表
  protected List<DataGridUrl> urlList = new ArrayList<DataGridUrl>();// 列表操作显示
  protected List<DataGridUrl> toolBarList = new ArrayList<DataGridUrl>();// 工具条列表
  protected List<DataGridColumn> columnList = new ArrayList<DataGridColumn>();// 列表操作显示
  protected List<ColumnValue> columnValueList = new ArrayList<ColumnValue>();// 值替换集合
  protected List<ColumnValue> columnStyleList = new ArrayList<ColumnValue>();// 颜色替换集合
  public Map<String, Object> map;// 封装查询条件
  private String actionUrl;// 分页提交路径
  public int allCount;
  public int curPageNo;
  public int pageSize = 10;
  public boolean pagination = true;// 是否显示分页
  private String width;
  private String height;
  private boolean checkbox = false;// 是否显示复选框
  private boolean showPageList = true;// 定义是否显示页面列表
  private boolean openFirstNode = false;//是不是展开第一个节点
  private boolean fit = true;// 是否允许表格自动缩放，以适应父容器
  private boolean fitColumns = true;// 当为true时，自动展开/合同列的大小，以适应的宽度，防止横向滚动.
  private String sortName;//定义的列进行排序
  private String sortOrder = "asc";//定义列的排序顺序，只能是"递增"或"降序".
  private boolean showRefresh = true;// 定义是否显示刷新按钮
  private boolean showText = true;// 定义是否显示刷新按钮
  private String style = "easyui";// 列表样式easyui,datatables
  private String onLoadSuccess;// 数据加载完成调用方法
  private String onClick;// 单击事件调用方法
  private String onDblClick;// 双击事件调用方法
  private String queryMode = "single";//查询模式
  private String entityName;//对应的实体对象
  private String rowStyler;//rowStyler函数
  private String extendParams;//扩展参数,easyui有的,但是jeecg没有的参数进行扩展
  private boolean autoLoadData = true; // 列表是否自动加载数据
  //private boolean frozenColumn=false; // 是否是冰冻列    默认不是
  private String langArg;

  private boolean queryBuilder = false;// 高级查询器
  private String windowType = "tab";//窗口类型：dialog对话框，tab标签页方式打开

  private String tranType;//单据类型；
  private String tableName;//单据表名；
  private boolean checkOnSelect = false;//点击行时同时选中复选框
  private String beforeAccount = "";//单据是否启用账套状态检查，未设置值（默认为)即为不需要当前账套检查，值为true表示如果当前账套已开账未结账才允许单据操作，值为init表示当前账套未开账才允许单据操作（用于初始化的几种单据）
  private boolean scrollview = false; //数据自动滚动载入
  private boolean isSon = false;//是否查询分支机构

  private boolean colConfig = true;//是否启用列配置
  private String excludeCol = "id,createName,createBy,createDate,updateName,updateBy,updateDate";//列配置中排除的字段
  //private String clearExt="";//单据复制清除扩展属性串：多个用逗号间隔，其中主表为属性名，子表为小数点+属性名。
  /*
	默认已清除或重置的属性有：通过单据类型获取新单据编号、单据日期、制单人姓名、制单人ID、分支机构ID、分支机构名称、未审核状态、未作废状态、创建人、创建人ID、创建时间、更新人、更新人ID、更新时间
	   清空主表相关数据：表头主表ID,除初始化以外（经办、部门），来源单据相关（classIDSrc、idSrc、billNoSrc）
	   清空子表相关数据：（附表明细）的主表ID、明细表ID，来源单据相关（idSrc、entryIdSrc、idOrder、entryIdOrder、classIdSrc、billNoOrder），属性字段名前加小数点
	* */


  //json转换中的系统保留字
  protected static Map<String, String> syscode = new HashMap<String, String>();

  static {
    syscode.put("class", "clazz");
  }

  @Autowired
  private static SystemService systemService;

  @Autowired
  private static UserService userService;

  @Autowired
  private CgFormFieldServiceI cgFormFieldService;

  public void setOnLoadSuccess(String onLoadSuccess) {
    this.onLoadSuccess = onLoadSuccess;
  }

  public void setOnClick(String onClick) {
    this.onClick = onClick;
  }

  public void setOnDblClick(String onDblClick) {
    this.onDblClick = onDblClick;
  }

  public void setShowText(boolean showText) {
    this.showText = showText;
  }

  public void setPagination(boolean pagination) {
    this.pagination = pagination;
  }

  public void setCheckbox(boolean checkbox) {
    this.checkbox = checkbox;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public void setTreegrid(boolean treegrid) {
    this.treegrid = treegrid;
  }

  public void setWidth(String width) {
    this.width = width;
  }

  public void setHeight(String height) {
    this.height = height;
  }

  public void setIdField(String idField) {
    this.idField = idField;
  }

  public void setActionUrl(String actionUrl) {
    this.actionUrl = actionUrl;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setFit(boolean fit) {
    this.fit = fit;
  }

  public void setShowPageList(boolean showPageList) {
    this.showPageList = showPageList;
  }

  public void setShowRefresh(boolean showRefresh) {
    this.showRefresh = showRefresh;
  }

  public String getWindowType() {
    return windowType;
  }

  public void setWindowType(String windowType) {
    this.windowType = windowType;
  }

  public String getTranType() {
    return tranType;
  }

  public void setTranType(String tranType) {
    this.tranType = tranType;
  }

  public String isBeforeAccount() {
    return beforeAccount;
  }

  public void setBeforeAccount(String beforeAccount) {
    this.beforeAccount = beforeAccount;
  }

  public boolean isSon() {
    return isSon;
  }

  public void setIsSon(boolean isSon) {
    this.isSon = isSon;
  }

  public boolean isColConfig() {
    return colConfig;
  }

  public void setColConfig(boolean colConfig) {
    this.colConfig = colConfig;
  }

  /**
   * 设置询问操作URL
   */
  public void setConfUrl(String url, String title, String message, String exp, String operationCode) {
    DataGridUrl dataGridUrl = new DataGridUrl();
    dataGridUrl.setTitle(title);
    dataGridUrl.setUrl(url);
    dataGridUrl.setType(OptTypeDirection.Confirm);
    dataGridUrl.setMessage(message);
    dataGridUrl.setExp(exp);
    installOperationCode(dataGridUrl, operationCode, urlList);
  }

  /**
   * 设置删除操作URL
   */
  public void setDelUrl(String url, String title, String message, String exp, String funname, String operationCode, String icon) {
    DataGridUrl dataGridUrl = new DataGridUrl();
    dataGridUrl.setTitle(title);
    dataGridUrl.setUrl(url);
    dataGridUrl.setType(OptTypeDirection.Del);
    dataGridUrl.setMessage(message);
    dataGridUrl.setExp(exp);
    dataGridUrl.setFunname(funname);
    dataGridUrl.setIcon(icon);
    installOperationCode(dataGridUrl, operationCode, urlList);
  }

  /**
   * 设置默认操作URL
   */
  public void setDefUrl(String url, String title, String exp, String operationCode) {
    DataGridUrl dataGridUrl = new DataGridUrl();
    dataGridUrl.setTitle(title);
    dataGridUrl.setUrl(url);
    dataGridUrl.setType(OptTypeDirection.Deff);
    dataGridUrl.setExp(exp);
    installOperationCode(dataGridUrl, operationCode, urlList);

  }

  /**
   * 设置工具条
   *
   * @param height2
   * @param width2
   */
  public void setToolbar(String url, String title, String icon, String exp, String onclick, String funname, String operationCode, String width2, String height2, String windowType) {
    DataGridUrl dataGridUrl = new DataGridUrl();
    dataGridUrl.setTitle(title);
    dataGridUrl.setUrl(url);
    dataGridUrl.setType(OptTypeDirection.ToolBar);
    dataGridUrl.setIcon(icon);
    dataGridUrl.setOnclick(onclick);
    dataGridUrl.setExp(exp);
    dataGridUrl.setFunname(funname);
    dataGridUrl.setWidth(String.valueOf(width2));
    dataGridUrl.setHeight(String.valueOf(height2));
    dataGridUrl.setWindowType(windowType);
    installOperationCode(dataGridUrl, operationCode, toolBarList);

  }

  /**
   * 设置自定义函数操作URL
   */
  public void setFunUrl(String title, String exp, String funname, String operationCode, String icon) {
    DataGridUrl dataGridUrl = new DataGridUrl();
    dataGridUrl.setTitle(title);
    dataGridUrl.setType(OptTypeDirection.Fun);
    dataGridUrl.setExp(exp);
    dataGridUrl.setFunname(funname);
    dataGridUrl.setIcon(icon);
    installOperationCode(dataGridUrl, operationCode, urlList);

  }

  /**
   * 设置自定义函数操作URL
   */
  public void setOpenUrl(String url, String title, String width, String height, String exp, String operationCode, String openModel) {
    DataGridUrl dataGridUrl = new DataGridUrl();
    dataGridUrl.setTitle(title);
    dataGridUrl.setUrl(url);
    dataGridUrl.setWidth(width);
    dataGridUrl.setHeight(height);
    dataGridUrl.setType(OptTypeDirection.valueOf(openModel));
    dataGridUrl.setExp(exp);
    installOperationCode(dataGridUrl, operationCode, urlList);

  }

  /**
   * <b>Summary: </b> setColumn(设置字段)
   *
   * @param title
   * @param field
   * @param width
   */
  public void setColumn(String title, String field, Integer width, String rowspan,
                        String colspan, String align, boolean sortable, boolean checkbox,
                        String formatter, boolean hidden, String replace,
                        String treefield, boolean image, String imageSize,
                        boolean query, String url, String funname,
                        String arg, String queryMode, String dictionary, boolean popup,
                        boolean frozenColumn, String extend,
                        String style, String downloadName, boolean isAuto, String extendParams, String editor, boolean mergeCells) {
    DataGridColumn dataGridColumn = new DataGridColumn();
    dataGridColumn.setAlign(align);
    dataGridColumn.setCheckbox(checkbox);
    dataGridColumn.setColspan(colspan);
    dataGridColumn.setField(field);
    dataGridColumn.setFormatter(formatter);
    dataGridColumn.setHidden(hidden);
    dataGridColumn.setRowspan(rowspan);
    dataGridColumn.setSortable(sortable);
    dataGridColumn.setTitle(title);
    dataGridColumn.setWidth(width);
    dataGridColumn.setTreefield(treefield);
    dataGridColumn.setImage(image);
    dataGridColumn.setImageSize(imageSize);
    dataGridColumn.setReplace(replace);
    dataGridColumn.setQuery(query);
    dataGridColumn.setUrl(url);
    dataGridColumn.setFunname(funname);
    dataGridColumn.setArg(arg);
    dataGridColumn.setQueryMode(queryMode);
    dataGridColumn.setDictionary(dictionary);
    dataGridColumn.setPopup(popup);
    dataGridColumn.setFrozenColumn(frozenColumn);
    dataGridColumn.setExtend(extend);
    dataGridColumn.setStyle(style);
    dataGridColumn.setDownloadName(downloadName);
    dataGridColumn.setAutocomplete(isAuto);
    dataGridColumn.setExtendParams(extendParams);
    dataGridColumn.setEditor(editor);
    dataGridColumn.setMergeCells(mergeCells);

    columnList.add(dataGridColumn);
    Set<String> operationCodes = (Set<String>) super.pageContext.getRequest().getAttribute(Globals.OPERATIONCODES);
    if (null != operationCodes) {
      for (String MyoperationCode : operationCodes) {
        if (oConvertUtils.isEmpty(MyoperationCode))
          break;
        systemService = ApplicationContextUtil.getContext().getBean(
                SystemService.class);
        TSOperation operation = systemService.getEntity(TSOperation.class, MyoperationCode);
        if (operation.getOperationcode().equals(field)) {
          columnList.remove(dataGridColumn);
        }
      }
    }


    if (field != "opt") {
      fields += field + ",";
      if ("group".equals(queryMode)) {
        searchFields += field + "," + field + "_begin," + field + "_end,";
      } else {
        searchFields += field + ",";
      }
      if(mergeCells){
        mergeFields += field + ",";
      }
    }
    if (replace != null) {
      String[] test = replace.split(",");
      String lang_key = "";
      String text = "";
      String value = "";
      for (String string : test) {
        lang_key = string.substring(0, string.indexOf("_"));
        text += MutiLangUtil.getMutiLangInstance().getLang(lang_key) + ",";

        value += string.substring(string.indexOf("_") + 1) + ",";
      }
      setColumn(field, text, value);

    }
    if (!StringUtils.isBlank(dictionary) && (!popup)) {
      if (dictionary.contains(",")) {
        String[] dic = dictionary.split(",");
        String text = "";
        String value = "";
        String sql = "";
        if (dic.length >= 4) {
          sql = "select " + dic[1] + " as field," + dic[2]
                  + " as text from " + dic[0] + " where " + dic[3];
        } else {
          sql = "select " + dic[1] + " as field," + dic[2]
                  + " as text from " + dic[0];
        }
        systemService = ApplicationContextUtil.getContext().getBean(
                SystemService.class);
        List<Map<String, Object>> list = systemService.findForJdbc(sql);
        for (Map<String, Object> map : list) {
          text += map.get("text") + ",";
          value += map.get("field") + ",";
        }
        if (list.size() > 0)
          setColumn(field, text, value);
      } else {
        String text = "";
        String value = "";
        List<TSType> typeList = TSTypegroup.allTypes.get(dictionary.toLowerCase());
        if (typeList != null && !typeList.isEmpty()) {
          for (TSType type : typeList) {
            text += MutiLangUtil.doMutiLang(type.getTypename(), "") + ",";
            value += type.getTypecode() + ",";
          }
          setColumn(field, text, value);
        }
      }
    }
    if (StringUtil.isNotEmpty(style)) {
      String[] temp = style.split(",");
      String text = "";
      String value = "";
      if (temp.length == 1 && temp[0].indexOf("_") == -1) {
        text = temp[0];
      } else {
        for (String string : temp) {
          text += string.substring(0, string.indexOf("_")) + ",";
          value += string.substring(string.indexOf("_") + 1) + ",";
        }
      }
      setStyleColumn(field, text, value);
    }
  }

  /**
   * 设置 颜色替换值
   *
   * @param field
   * @param text
   * @param value
   */
  private void setStyleColumn(String field, String text, String value) {
    ColumnValue columnValue = new ColumnValue();
    columnValue.setName(field);
    columnValue.setText(text);
    columnValue.setValue(value);
    columnStyleList.add(columnValue);
  }

  /**
   * <b>Summary: </b> setColumn(设置字段替换值)
   *
   * @param name
   * @param text
   * @param value
   */
  public void setColumn(String name, String text, String value) {
    ColumnValue columnValue = new ColumnValue();
    columnValue.setName(name);
    columnValue.setText(text);
    columnValue.setValue(value);
    columnValueList.add(columnValue);
  }

  public int doStartTag() throws JspTagException {
    // 清空资源
    urlList.clear();
    toolBarList.clear();
    columnValueList.clear();
    columnStyleList.clear();
    columnList.clear();
    fields = "";
    mergeFields = "";
    searchFields = "";
    return EVAL_PAGE;
  }


  public int doEndTag() throws JspException {
    try {
      title = MutiLangUtil.doMutiLang(title, langArg);

      JspWriter out = this.pageContext.getOut();
//			String indexStyle =null;
//-----author:jg_longjb----start-----date:20150408--------for:读取cookie主题样式 ace界面下table的输出
//			Cookie[] cookies = ((HttpServletRequest) super.pageContext
//					.getRequest()).getCookies();
//			for (Cookie cookie : cookies) {
//				if (cookie == null || StringUtils.isEmpty(cookie.getName())) {
//					continue;
//				}
//				if (cookie.getName().equalsIgnoreCase("JEECGINDEXSTYLE")) {
//					indexStyle = cookie.getValue();
//				}
//			}
      SysThemesEnum sysThemesEnum = SysThemesUtil.getSysTheme((HttpServletRequest) super.pageContext.getRequest());
      if (style.equals("easyui")) {
        if ("ace".equals(sysThemesEnum.getStyle())) {
          out.print(this.aceStyleTable().toString());
        } else {
          out.print(end().toString());
        }
      } else {
        out.print(datatables().toString());
        out.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return EVAL_PAGE;
  }

  /**
   * datatables构造方法
   *
   * @return
   */
  public StringBuffer datatables() {
    StringBuffer sb = new StringBuffer();
    sb.append("<script type=\"text/javascript\">");
    sb.append("$(document).ready(function() {");
    sb.append("var oTable = $(\'#userList\').dataTable({");
    // sb.append(
    // "\"sDom\" : \"<\'row\'<\'span6\'l><\'span6\'f>r>t<\'row\'<\'span6\'i><\'span6\'p>>\",");
    sb.append("\"bProcessing\" : true,");// 当datatable获取数据时候是否显示正在处理提示信息"
    sb.append("\"bPaginate\" : true,"); // 是否分页"
    sb.append("\"sPaginationType\" : \"full_numbers\",");// 分页样式full_numbers,"
    sb.append("\"bFilter\" : true,");// 是否使用内置的过滤功能"
    sb.append("\"bSort\" : true, ");// 排序功能"
    sb.append("\"bAutoWidth\" : true,");// 自动宽度"
    sb.append("\"bLengthChange\" : true,");// 是否允许用户自定义每页显示条数"
    sb.append("\"bInfo\" : true,");// 页脚信息"
    sb.append("\"sAjaxSource\" : \"userController.do?test\",");
    sb.append("\"bServerSide\" : true,");// 指定从服务器端获取数据
    sb.append("\"oLanguage\" : {" + "\"sLengthMenu\" : \" _MENU_ 条记录\"," + "\"sZeroRecords\" : \"没有检索到数据\"," + "\"sInfo\" : \"第 _START_ 至 _END_ 条数据 共 _TOTAL_ 条\"," + "\"sInfoEmtpy\" : \"没有数据\"," + "\"sProcessing\" : \"正在加载数据...\"," + "\"sSearch\" : \"搜索\"," + "\"oPaginate\" : {" + "\"sFirst\" : \"首页\"," + "\"sPrevious\" : \"前页\", " + "\"sNext\" : \"后页\"," + "\"sLast\" : \"尾页\"" + "}" + "},"); // 汉化
    // 获取数据的处理函数 \"data\" : {_dt_json : JSON.stringify(aoData)},
    sb.append("\"fnServerData\" : function(sSource, aoData, fnCallback, oSettings) {");
    // + "\"data\" : {_dt_json : JSON.stringify(aoData)},"
    sb.append("oSettings.jqXHR = $.ajax({" + "\"dataType\" : \'json\'," + "\"type\" : \"POST\"," + "\"url\" : sSource," + "\"data\" : aoData," + "\"success\" : fnCallback" + "});},");
    sb.append("\"aoColumns\" : [ ");
    int i = 0;
    for (DataGridColumn column : columnList) {
      i++;
      sb.append("{");
      sb.append("\"sTitle\":\"" + column.getTitle() + "\"");
      if (column.getField().equals("opt")) {
        sb.append(",\"mData\":\"" + idField + "\"");
        sb.append(",\"sWidth\":\"20%\"");
        sb.append(",\"bSortable\":false");
        sb.append(",\"bSearchable\":false");
        sb.append(",\"mRender\" : function(data, type, rec) {");
        this.getOptUrl(sb);
        sb.append("}");
      } else {
        int colwidth = (column.getWidth() == null) ? column.getTitle().length() * 15 : column.getWidth();
        sb.append(",\"sName\":\"" + column.getField() + "\"");
        sb.append(",\"mDataProp\":\"" + column.getField() + "\"");
        sb.append(",\"mData\":\"" + column.getField() + "\"");
        sb.append(",\"sWidth\":\"" + colwidth + "\"");
        sb.append(",\"bSortable\":" + column.isSortable() + "");
        sb.append(",\"bVisible\":" + !column.isHidden() + "");
        sb.append(",\"bSearchable\":" + column.isQuery() + "");
      }
      sb.append("}");
      if (i < columnList.size())
        sb.append(",");
    }

    sb.append("]" + "});" + "});" + "</script>");
    sb.append("<table width=\"100%\"  class=\"" + style + "\" id=\"" + name + "\" toolbar=\"#" + name + "tb\"></table>");
    return sb;

  }

  public void setStyle(String style) {
    this.style = style;
  }

  /**
   * easyui构造方法
   *
   * @return
   */
  public StringBuffer end() {
    String grid = "";
    StringBuffer sb = new StringBuffer();
    width = (width == null) ? "auto" : width;
    height = (height == null) ? "auto" : height;
    sb.append("<script type=\"text/javascript\">\n");
    sb.append("var editRow = undefined;\n");
    sb.append("$(function(){\n");
    sb.append("var myview = $.extend({}, $.fn.datagrid.defaults.view, {\n");
    sb.append("  onAfterRender: function (target) {\n");
    sb.append("    $.fn.datagrid.defaults.view.onAfterRender.call(this, target);\n");
    sb.append("    var opts = $(target).datagrid('options');\n");
    sb.append("    var vc = $(target).datagrid('getPanel').children('div.datagrid-view');\n");
    sb.append("    vc.children('div.datagrid-empty').remove();\n");
    sb.append("    if (!$(target).datagrid('getRows').length) {\n");
    sb.append("      var d = $('<div class=\"datagrid-empty\"></div>').html(opts.emptyMsg || '暂无数据').appendTo(vc);\n");
    sb.append("      d.css({\n");
    sb.append("        position: 'absolute',\n");
    sb.append("        left: 0,\n");
    sb.append("        top: 50,\n");
    sb.append("        width: '100%',\n");
    sb.append("        textAlign: 'center'\n");
    sb.append("      });\n");
    sb.append("    }\n");
    sb.append("  }\n");
    sb.append("});\n");
    sb.append("  storage=$.localStorage;if(!storage)storage=$.cookieStorage;\n");
    sb.append(this.getNoAuthOperButton());
    if (treegrid) {
      grid = "treegrid";
      sb.append("var datagrid = $(\'#" + name + "\').treegrid({\n");
      sb.append("idField:'id',\n");
      sb.append("treeField:'text',\n");
    } else {
      grid = "datagrid";
      sb.append("var datagrid = $(\'#" + name + "\').datagrid({\n");
      if(scrollview){
        sb.append("view: scrollview,\n");
      }else {
        sb.append("view: myview,\n");
      }
      sb.append("emptyMsg: '暂无数据',\n");
      //是否打开箭头上下移动数据行支持
      sb.append("moveRow: true,\n");
      sb.append("idField: '" + idField + "',\n");
    }
    if (title != null) {
      sb.append("title: \'" + title + "\',\n");
    }

    if (autoLoadData) {
      sb.append("url:\'" + actionUrl + "&field=" + fields + "&mergeField="+ mergeFields +"&");
      if (StringUtils.isNotEmpty(tranType)) {
        sb.append("tranType=" + tranType+"&");
      }
      sb.append("\',\n");
    } else {
      sb.append("url:\'',\n");
    }
    if (StringUtils.isNotEmpty(rowStyler)) {
      sb.append("rowStyler: function(index,row){ return " + rowStyler + "(index,row);},\n");
    }else{
      sb.append("rowStyler: function(index,row){ return 'vertical-align: top;';},\n");
    }
    if (StringUtils.isNotEmpty(extendParams)) {
      sb.append(extendParams);
    }
    if (fit) {
      sb.append("fit:true,\n");
    } else {
      sb.append("fit:false,\n");
    }
    sb.append(StringUtil.replaceAll("loadMsg: \'{0}\',", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.data.loading")));
    sb.append("pageSize: " + pageSize + ",\n");
    if(!scrollview) {
      sb.append("pagination:" + pagination + ",\n");
    }
    sb.append("pageList:[" + pageSize * 1 + "," + pageSize * 2 + "," + pageSize * 3 + "],\n");
    if (StringUtils.isNotBlank(sortName)) {
      sb.append("sortName:'" + sortName + "',\n");
    }
    sb.append("sortOrder:'" + sortOrder + "',\n");
    sb.append("rownumbers:true,\n");
    sb.append("singleSelect:" + !checkbox + ",\n");
    if (fitColumns) {
      sb.append("fitColumns:true,\n");
    } else {
      sb.append("fitColumns:false,\n");
    }
    sb.append("striped:true,showFooter:true,\n");
    sb.append("frozenColumns:[[\n");
    this.getField(sb, 0);
    sb.append("]],\n");
    //如果用户保存过列配置则调取
    cgFormFieldService = ApplicationContextUtil.getContext().getBean(
            CgFormFieldServiceI.class);
    CgFormHeadEntity cgFormHeadEntity = cgFormFieldService.getCgFormHeadByTableName(tableName);
    if (cgFormHeadEntity != null && StringUtil.isNotEmpty(cgFormHeadEntity.getColConfig())) {
      org.json.JSONObject colObject = new org.json.JSONObject(cgFormHeadEntity.getColConfig());
      if(!colObject.has(tranType)){
        sb.append("columns:[[\n");
        this.getField(sb);
        sb.append("]],\n");
      }else {
        String colConfig = colObject.get(tranType).toString();
        sb.append("columns:" + colConfig + ",\n");
      }
    } else {
      sb.append("columns:[[\n");
      this.getField(sb);
      sb.append("]],\n");
    }
    sb.append("onLoadSuccess:function(data){$(\"#" + name + "\")." + grid + "(\"clearSelections\");\n");
    if (openFirstNode && treegrid) {
      sb.append(" if(data==null){\n");
      sb.append(" var firstNode = $(\'#" + name + "\').treegrid('getRoots')[0];\n");
      sb.append(" $(\'#" + name + "\').treegrid('expand',firstNode.id)}\n");
    }
    if(!checkOnSelect){
      sb.append("unCheckRow(this.id); \n");
    }
//    sb.append("try {\n");
////    sb.append("      debugger;\n");
//    sb.append("      var total = 0;\n");
//    sb.append("    $('.datagrid-body').on('scroll',function () {\n");
////    sb.append("      debugger;\n");
//    sb.append("      var sheight = $(this)[0].scrollHeight;\n");
//    sb.append("      var top = $(this)[0].scrollTop;\n");
//    sb.append("      var height = $(this).height();\n");
//    sb.append("      //判断是否到达底部\n");
//    sb.append("      document.title = 'top:' + (top + height) + ' heigth:' + (sheight - 3);\n");
//    sb.append("      if ((top + height) + 3 == sheight) {\n");
//    sb.append("        document.title = datagrid.datagrid('options').url;\n");
//    sb.append("        //请求json\n");
//    sb.append("        //page;// 当前页\n");
//    sb.append("        //rows;// 每页显示记录数\n");
//    sb.append("        //拼接表单的值\n");
//    sb.append("        var url = datagrid.datagrid('options').url;\n");
//    sb.append("        // url+='?params=xxx';\n");
//    sb.append("        var params = '';\n");
//    sb.append("        $(\'#" + name + "tb').find('*').each(function (index) {\n");
//    sb.append("          var obj = $(this);\n");
//    sb.append("          if (obj.prop('name') != '' && obj.val() != '') {\n");
//    sb.append("            params += '&' + obj.prop('name') + '=' + obj.val();\n");
//    sb.append("          }\n");
//    sb.append("        });\n");
//    sb.append("        //获取页码、每次默认加载20条\n");
//    sb.append("        var num = 20;\n");
//    sb.append("        //获取行数\n");
//    sb.append("        var row = datagrid.datagrid('getRows').length;\n");
//    sb.append("        if(total == 0 || row < total){\n");
//    sb.append("        var page = parseInt(row / num) + 1; //求出下一页\n");
//    sb.append("        //$.post();\n");
//    sb.append("        url += params + '&page=' + page + '&rows=20';\n");
//    sb.append("        //请求数据\n");
//    sb.append("        $.post(url, function (data) {\n");
//    sb.append("          total= data.total;\n");
//    sb.append("          var json = data.rows;\n");
//    sb.append("          for (var i = 0; i < json.length; i++) {\n");
//    sb.append("            //自动拼接字段和赋值\n");
//    sb.append("            var row = '';\n");
//    sb.append("            for (var item in json[i]) {\n");
//    sb.append("              row += '\"' + item + '\":' + '\"' + json[i][item] + '\",';\n");
//    sb.append("            }\n");
//    sb.append("            row = row.substring(0, row.length - 1);\n");
//    sb.append("            row = '{' + row + '}';\n");
//    sb.append("            row = $.parseJSON(row);\n");
//    sb.append("            datagrid.datagrid('appendRow', row);\n");
//    sb.append("            var pageOp = grid.datagrid('getPager').data('pagination').options;\n");
//    sb.append("            pageOp.pageNumber = page;\n");
//    sb.append("            pageOp.total = total;\n");
//    sb.append("          }\n");
//    sb.append("        });\n");
//    sb.append("        }\n");
//    sb.append("      } else {\n");
//    sb.append("        document.title = '滚动条没有到达底部';\n");
//    sb.append("      }\n");
//    sb.append("    });\n");
//    sb.append("  }catch (e) {\n");
//    sb.append("    alert(e);\n");
//    sb.append(" }\n");
    if (StringUtil.isNotEmpty(onLoadSuccess)) {
      sb.append(onLoadSuccess + "(data);\n");
    }
    //取行合并配置，并调用行合并方法
    sb.append(" var tableID = \'" + name + "\'; \n");
    sb.append(" var colList = getTableColList(tableID); \n");
    sb.append(" if(colList != ''){ \n");
    sb.append("  mergeCellsByField(tableID,colList,'id');  \n");
    sb.append(" } \n");

    //调用箭头上下移事件
    sb.append("moveRow(this);\n");
    sb.append("},\n");
    sb.append("onDblClickRow:function(rowIndex,rowData){\n");
    //如果是列配置页则加入以下内容
    if(name.equals("cgFormHeadConfigList")) {
      sb.append("  if (editRow != undefined) {\n");
      sb.append("    $(\'#" + name + "\').datagrid('endEdit', editRow); \n");
      sb.append("  }\n");
      sb.append("  if (editRow == undefined) {\n");
      sb.append("    $(\'#" + name + "\').datagrid('beginEdit', rowIndex); \n");
      sb.append("    editRow = rowIndex; \n");
      sb.append("  }\n");
    }
    if (StringUtil.isNotEmpty(onDblClick)) {
      sb.append(onDblClick + "(rowIndex,rowData);\n");
    }
    sb.append("},\n");
    if (treegrid) {
      sb.append("onClickRow:function(rowData){\n");
    } else {
      sb.append("onClickRow:function(rowIndex,rowData){\n");
    }
    //如果是列配置页则加入以下内容
    if(name.equals("cgFormHeadConfigList")){
      sb.append("  if (editRow != undefined) {\n");
      sb.append("    $(\'#" + name + "\').datagrid('endEdit', editRow); \n");
      sb.append("  }\n");
    }
    /**行记录赋值*/
    sb.append("rowid=rowData.id;\n");
    sb.append("gridname=\'" + name + "\';\n");
    if (StringUtil.isNotEmpty(onClick)) {
      if (treegrid) {
        sb.append("" + onClick + "(rowData);\n");
      } else {
        sb.append("" + onClick + "(rowIndex,rowData);\n");
      }
    }
    sb.append("},\n");
    //行编辑
//    sb.append("onBeforeEdit:function(index,row){ \n");
//    sb.append("    row.editing = true;\n");
//    sb.append("    $(\'#" + name + "\').datagrid('refreshRow', index); \n");
//    sb.append("},\n");
    sb.append("onAfterEdit:function(rowIndex, rowData, changes){ \n");
//    sb.append("  debugger; \n");
    sb.append("  editRow = undefined;\n");
    sb.append("  W.changeCol(rowIndex, changes); \n");
    sb.append("},\n");
    sb.append("});\n");
    this.setPager(sb, grid);
    sb.append("try{restoreheader();}catch(ex){}\n");
    sb.append("});\n");

    sb.append("//弹出列配置窗\n");
    sb.append("var excludeCols = '"+excludeCol+"'.split(',');\n");
    sb.append("var colsConfigData = [], excludeData = [];\n");
    sb.append("var goptions, columns;\n");
    sb.append("function showColumnConfig() {\n");
    sb.append("  if(colsConfigData.length>0){\n");
    sb.append("    colsConfigData.length=0;\n");
    sb.append("  }\n");
    sb.append("      if (columns == null) {\n");
    sb.append("        goptions = $('#" + name + "').datagrid(\"options\");\n");
    sb.append("        columns = goptions.columns;\n");
    sb.append("      }\n");
    sb.append("      var cols = columns[0];\n");
    sb.append("      for (var i = 0; i < cols.length; i++) {\n");
    sb.append("        var colJson = {};\n");
    sb.append("        colJson.id = i;\n");
    sb.append("        colJson.field = cols[i].field;\n");
    sb.append("        colJson.hidden = cols[i].hidden == null ? 'false' : cols[i].hidden ? 'true' : 'false';\n");
    sb.append("        colJson.title = cols[i].title;\n");
    sb.append("        colJson.displayTitle = cols[i].title;\n");
    sb.append("        colJson.width = cols[i].width;\n");
    sb.append("//      colJson.order = i;\n");
    sb.append("//      if(excludeCols.indexOf(cols[i].field)<0){\n");
    sb.append("        colsConfigData.push(colJson);\n");
    sb.append("//       }else{\n");
    sb.append("//         excludeData.push(colJson);\n");
    sb.append("//       }\n");
    sb.append("      }\n");
    sb.append("//      colsConfigData=colsConfigData.concat(excludeData);\n");
    sb.append("      $.dialog({\n");
    sb.append("              content: 'url:' + projectName + '/cgFormHeadController.do?goCgFormHeadConfig',\n");
    sb.append("              lock: true,\n");
    sb.append("              zIndex: 500,\n");
    sb.append("              width: 410,\n");
    sb.append("              height: 280,\n");
    sb.append("              title: '列配置',\n");
    sb.append("              opacity: 0.8,\n");
    sb.append("              cache: false,\n");
    sb.append("              ok: function () {\n");
    sb.append("//        $(\"html\", this.iframe.contentDocument).jqprint();\n");
    sb.append("        var tableName = $('#entityName').val();\n");
    sb.append("        var colConfig = JSON.stringify(columns, function (key, value) {\n");
    sb.append("          if (key == 'formatter') {\n");
    sb.append("            return value.toString();\n");
    sb.append("          }\n");
    sb.append("          return value;\n");
    sb.append("        });\n");
    sb.append("        $.post(projectName + '/cgFormHeadController.do?updateColConfig', {\n");
    sb.append("                tableName: tableName,\n");
    sb.append("                tranType: '"+tranType+"',\n");
    sb.append("                colConfig: colConfig\n");
    sb.append("        }, function (data) {\n");
    sb.append("          if (data.success) {\n");
    sb.append("            tip('列配置信息更新成功！');\n");
    sb.append("          }\n");
    sb.append("        });\n");
    sb.append("      },\n");
    sb.append("      init:function(){\n");
    sb.append("      iframe = this.iframe.contentWindow;\n");
    sb.append("      },\n");
    sb.append("      okVal: '确定',\n");
    sb.append("              cancelVal: '关闭',\n");
    sb.append("              cancel: true, /*为true等价于function(){}*/\n");
    sb.append("button: [{\n");
    sb.append("    name: '重置', callback: function () {\n");
    sb.append("      $.dialog.confirm('注意，重置将会删除所有列配置！', function () {\n");
    sb.append("            var tableName = $('#entityName').val();\n");
    sb.append("            $.post(projectName + '/cgFormHeadController.do?delColConfig', {\n");
    sb.append("                    tableName: tableName\n");
    sb.append("            }, function (data) {\n");
//    sb.append("              debugger;\n");
    sb.append("              if (data.success) {\n");
    sb.append("                var tab = window.top.$('#maintabs').tabs('getSelected');\n");
    sb.append("                tab.panel('refresh');\n");
    sb.append("                tip('列配置重置成功！');\n");
    sb.append("              }\n");
    sb.append("            });\n");
    sb.append("          },\n");
    sb.append("          function () {\n");
    sb.append("          });\n");
    sb.append("        return false;\n");
    sb.append("    }\n");
    sb.append("  }]\n");
    sb.append("    }).zindex();\n");
    sb.append("}\n");
    //让datagrid数据行支持前头上下移动
    sb.append("var DatagridMoveRow = (function ($) {\n");
    sb.append("  function DatagridMoveRow(gridTarget) {\n");
    sb.append("    this.el = gridTarget;\n");
    sb.append("    this.$el = $(this.el);\n");
    sb.append("    this.rowIndex = -1;\n");
    sb.append("    this.rowsCount = this.$el.datagrid('getData').rows.length;\n");
    sb.append("    return this;\n");
    sb.append("  }\n");
    sb.append("\n");
    sb.append("  DatagridMoveRow.prototype = {\n");
    sb.append("    getRowindex: function () {\n");
    sb.append("      var row = this.$el.datagrid('getSelected');\n");
    sb.append("      var selectRowIndex = this.$el.datagrid('getRowIndex',row);\n");
    sb.append("      if (selectRowIndex == -1) {\n");
    sb.append("        this.rowIndex = 0;\n");
    sb.append("      } else {\n");
    sb.append("        this.rowIndex = selectRowIndex;\n");
    sb.append("      }\n");
    sb.append("    }, moveUp: function () {\n");
    sb.append("      this.getRowindex();\n");
    sb.append("      if (this.rowIndex == 0) {\n");
    sb.append("        return false;\n");
    sb.append("      }\n");
    sb.append("      var i = --this.rowIndex;\n");
    sb.append("      if (i > -1) {\n");
    sb.append("        this.$el.datagrid('selectRow', i);\n");
    sb.append("      } else {\n");
    sb.append("        this.rowIndex = 0;\n");
    sb.append("      }\n");
    sb.append("      return false;\n");
    sb.append("    }, moveDown: function () {\n");
    sb.append("      this.getRowindex();\n");
    sb.append("      if (this.rowIndex == this.rowsCount - 1) {\n");
    sb.append("        return false;\n");
    sb.append("      }\n");
    sb.append("      var i = ++this.rowIndex;\n");
    sb.append("      this.$el.datagrid('selectRow', i);\n");
    sb.append("    }\n");
    sb.append("  };\n");
    sb.append("  return DatagridMoveRow;\n");
    sb.append("})(jQuery);\n");
    sb.append("\n");
    sb.append("var moveRow = function (target) {\n");
//    sb.append("  debugger;\n");
    sb.append("  var options = $(target).datagrid('options');\n");
    sb.append("  if (options.moveRow) {\n");
    sb.append("    var dmr = new DatagridMoveRow(target);\n");
    sb.append("    $(document).on('keydown', function (e) {\n");
    sb.append("      if (e.keyCode == 38) {\n");
    sb.append("        //up\n");
    sb.append("        dmr.moveUp();\n");
    sb.append("      } else if (e.keyCode == 40) {\n");
    sb.append("        // down\n");
    sb.append("        dmr.moveDown();\n");
    sb.append("      }\n");
    sb.append("    });\n");
    sb.append("  }\n");
    sb.append("};\n");

    sb.append("  /**\n");
    sb.append("   * 交换datagrid两例位置\n");
    sb.append("   * @param sourceIndex\n");
    sb.append("   * @param targetIndex\n");
    sb.append("   */\n");
    sb.append("  function switchCol(sourceIndex, targetIndex) {\n");
    sb.append("    var cc = columns[0];\n");
    sb.append("    var tmpSrcCol = cc[sourceIndex];\n");
    sb.append("    var tmpTgtCol = cc[targetIndex];\n");
    sb.append("    cc.splice(sourceIndex, 1);\n");
    sb.append("    //如果源位置在目标位置的下方\n");
    sb.append("    if (targetIndex < sourceIndex) {\n");
    sb.append("      targetIndex += 1;\n");
    sb.append("    }\n");
    sb.append("    cc.splice(targetIndex, 0, tmpSrcCol);\n");
    sb.append("    $('#" + name + "').datagrid({columns: [cc]});\n");
    sb.append("  }\n");
    sb.append(" /**\n");
    sb.append("   * 更新列标题，宽度，是否隐藏等\n");
    sb.append("   * @param rowIndex\n");
    sb.append("   * @param changes\n");
    sb.append("   */\n");
    sb.append("  function changeCol(rowIndex, changes) {\n");
    sb.append("    var newValue;\n");
    sb.append("    for (var obj in changes) {\n");
    sb.append("      if (obj == 'displayTitle') {\n");
    sb.append("        obj = 'title';\n");
    sb.append("        newValue = changes.displayTitle;\n");
    sb.append("      } else if (obj == 'hidden') {\n");
    sb.append("        newValue = changes.hidden == 'true';\n");
    sb.append("      } else {\n");
    sb.append("        newValue = changes[obj];\n");
    sb.append("      }\n");
    sb.append("\n");
    sb.append("      columns[0][rowIndex][obj] = newValue;\n");
    sb.append("      $('#" + name + "').datagrid({columns: columns});\n");
    sb.append("    }\n");
    sb.append("  }\n");

    sb.append("/** \n");
    sb.append(" * datagrid单击行时不选中行 \n");
    sb.append(" * @param id \n");
    sb.append(" */  \n");
    sb.append("function unCheckRow(id){  \n");
    sb.append("    var p = $('#'+id).datagrid('getPanel');  \n");
    sb.append("    //var rows = p.find('tr.datagrid-row');  \n");
    sb.append("    var rows = p.find('tr.datagrid-row td[field!=ck]');  \n");
    sb.append("    rows.unbind('click').bind('click', function(e) {  \n");
    sb.append("        var currRow = $('#"+name+"').datagrid('getSelected'); \n");
    sb.append("        $('#"+name+"').datagrid('unselectAll'); \n");
    sb.append("        $('#"+name+"').datagrid('selectRow',currRow); \n");
//    sb.append("        return false;  \n");
    sb.append("    });  \n");
    sb.append("}\n");

    sb.append("function reloadTable(){\n");
    sb.append("try{\n");
    sb.append("	$(\'#\'+gridname).datagrid(\'reload\');\n");
    sb.append("	$(\'#\'+gridname).treegrid(\'reload\');\n");
    sb.append("}catch(ex){}\n");
    sb.append("}\n");
    sb.append("function reload" + name + "(){" + "$(\'#" + name + "\')." + grid + "(\'reload\');" + "}\n");
    sb.append("function get" + name + "Selected(field){return getSelected(field);}\n");
    sb.append("function getSelected(field){" + "var row = $(\'#\'+gridname)." + grid + "(\'getSelected\');" + "if(row!=null)" + "{" + "value= row[field];" + "}" + "else" + "{" + "value=\'\';" + "}" + "return value;" + "}\n");
    sb.append("function get" + name + "Selections(field){" + "var ids = [];" + "var rows = $(\'#" + name + "\')." + grid + "(\'getSelections\');" + "for(var i=0;i<rows.length;i++){" + "ids.push(rows[i][field]);" + "}" + "ids.join(\',\');" + "return ids" + "};\n");
    sb.append("function getSelectRows(){\n");
    sb.append("	return $(\'#" + name + "\').datagrid('getChecked');\n");
    sb.append("}\n");
    sb.append(" function saveHeader(){\n");
    sb.append(" var columnsFields =null;var easyextends=false;try{columnsFields = $('#" + name + "').datagrid('getColumns');easyextends=true;\n");
    sb.append("}catch(e){columnsFields =$('#" + name + "').datagrid('getColumnFields');}\n");
    sb.append("	var cols = storage.get( '" + name + "hiddenColumns');var init=true;	if(cols){init =false;} " +
            "var hiddencolumns = [];for(var i=0;i< columnsFields.length;i++) {if(easyextends){\n");
    sb.append("hiddencolumns.push({field:columnsFields[i].field,hidden:columnsFields[i].hidden});}else{\n");
    sb.append(" var columsDetail = $('#" + name + "').datagrid(\"getColumnOption\", columnsFields[i]); \n");
    sb.append("if(init){hiddencolumns.push({field:columsDetail.field,hidden:columsDetail.hidden,visible:(columsDetail.hidden==true?false:true)});}else{\n");
    sb.append("for(var j=0;j<cols.length;j++){\n");
    sb.append("		if(cols[j].field==columsDetail.field){\n");
    sb.append("					hiddencolumns.push({field:columsDetail.field,hidden:columsDetail.hidden,visible:cols[j].visible});\n");
    sb.append("		}\n");
    sb.append("}\n");
    sb.append("}} }\n");
    sb.append("storage.set( '" + name + "hiddenColumns',JSON.stringify(hiddencolumns));\n");
    sb.append("}\n");
    sb.append("function restoreheader(){\n");
    sb.append("var cols = storage.get( '" + name + "hiddenColumns');if(!cols)return;\n");
    sb.append("for(var i=0;i<cols.length;i++){\n");
    sb.append("	try{\n");
    sb.append("if(cols.visible!=false)$('#" + name + "').datagrid((cols[i].hidden==true?'hideColumn':'showColumn'),cols[i].field);\n");
    sb.append("}catch(e){\n");
    sb.append("}\n");
    sb.append("}\n");
    sb.append("}\n");
    sb.append("function resetheader(){\n");
    sb.append("var cols = storage.get( '" + name + "hiddenColumns');if(!cols)return;\n");
    sb.append("for(var i=0;i<cols.length;i++){\n");
    sb.append("	try{\n");
    sb.append("  $('#" + name + "').datagrid((cols.visible==false?'hideColumn':'showColumn'),cols[i].field);\n");
    sb.append("}catch(e){\n");
    sb.append("}\n");
    sb.append("}\n");
    sb.append("}\n");
    if (columnList.size() > 0) {
      sb.append("function " + name + "search(){\n");
      sb.append("var queryParams=$(\'#" + name + "\').datagrid('options').queryParams;\n");
      sb.append("$(\'#" + name + "tb\').find('*').each(function(){" +
              "if($(this).attr('id') == 'sonId'){\n" +
              "   var treeInfo = $(\"#sonId\").combotree('tree'); \n" +
              "   var checkInfo = treeInfo.tree('getChecked');\n" +
              "   var sonIds = '';" +
              "   for(var i = 0 ; i < checkInfo.length ; i++){" +
              "       var node = checkInfo[i];" +
              "       var nodeId = node.id;" +
              "       sonIds += nodeId+',';" +
              "   }"+
              "   queryParams[$(this).attr('id')] = sonIds; " +
              "} else if( $(this).attr('name') != 'sonId'){" +
              "   queryParams[$(this).attr('name')]=$(this).val();" +
              "}" +
              " });\n");
      if (StringUtils.isNotEmpty(tranType)) {
        actionUrl = actionUrl + "&tranType=" + tranType;
      }
      sb.append("$(\'#" + name + "\')." + grid + "({url:'" + actionUrl + "&field=" + searchFields + "&mergeField="+mergeFields+"',pageNumber:1});" + "}\n");

      //高级查询执行方法
      sb.append("function dosearch(params){\n");
      sb.append("var jsonparams=$.parseJSON(params);\n");
      sb.append("$(\'#" + name + "\')." + grid + "({url:'" + actionUrl + "&field=" + searchFields + "&mergeField="+mergeFields+"',queryParams:jsonparams});" + "}\n");

      //searchbox框执行方法
      searchboxFun(sb, grid);
      //生成重置按钮功能js

      //回车事件
      sb.append("function EnterPress(e){\n");
      sb.append("var e = e || window.event;\n");
      sb.append("if(e.keyCode == 13){ \n");
      sb.append(name + "search();\n");
      sb.append("}}\n");

      sb.append("function " + name + "searchReset(name){\n");
      sb.append(" $(\"#\"+name+\"tb\").find(\":input\").val(\"\");\n");
      if(isSon){
        sb.append(" $(\"#sonId\").combotree('clear');");
      }
      String func = name.trim() + "search();";
      sb.append(func);
      sb.append("}\n");

      //产生合并行的两个function函数脚本:getTableColList、mergeCellsByField
      sb.append(getMergeCellsByFieldFunction());
    }
    //如果datagrid标签是否需要账套检查属性值为需要检查时，根据当前账套的开启和结账状态来产生录入、编辑、作废、反作废、复制、导入、审核、反审核、自动盘点、关闭、反关闭按钮不可操作的js脚本串
    sb.append(getBeforeAccountFunction());

    sb.append("</script>\n");

    //如果datagrid标签是否需要账套检查属性值为需要检查时，根据当前账套的开启和结账状态来产生一个id为隐藏域，值为1；可操作的值为0
    sb.append(getBeforeAccountInput());

    sb.append("<input id=\"tranType\" value=\"" + tranType + "\" type=\"hidden\">\n");
    sb.append("<input id=\"entityName\" value=\"" + tableName + "\" type=\"hidden\">\n");
    sb.append("<input id=\"gridName\" value=\"" + name + "\" type=\"hidden\">\n");
    systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
    List<TSConfig> configs = systemService.findHql("from TSConfig");
    if (configs.size() > 0) {
      for (TSConfig config : configs) {
        sb.append("<input type='hidden' id='" + config.getCode() + "' value='" + config.getContents() + "'>");
      }
    }
    sb.append("<script type=\"text/javascript\" src=\"plug-in/scm/js/auditTools.js\"></script>\n");
    sb.append("<script type=\"text/javascript\" src=\"plug-in/scm/js/opeTools.js\"></script>\n");
    sb.append("<table width=\"100%\"   id=\"" + name + "\" toolbar=\"#" + name + "tb\"></table>\n");
    sb.append("<div id=\"" + name + "tb\" style=\"padding:3px; height: auto\">\n");
    if (hasQueryColum(columnList)) {
      sb.append("<div class=\"panel-header\" id=\"custHeader\" >\n" +
              "<div class=\"panel-title\">查询条件</div>" +
              "        <div class=\"panel-tool\"><a href=\"javascript:void(0)\" class=\"layout-button-up\" ></a></div>\n" +
              "    </div>");
      sb.append("<div  class=\"panel-body layout-body\" id=\"centerBody\" style=\"height:'auto';position:relative;padding-bottom:20px\">");
      sb.append("<div name=\"searchColums\">\n");
      //-----longjb1 增加用于高级查询的参数项
      sb.append("<input  id=\"_sqlbuilder\" name=\"sqlbuilder\"   type=\"hidden\" />\n");
      if(colConfig) {
        sb.append("<a class=\"easyui-linkbutton\" icon=\"icon-columnConfig\" plain=\"true\" style=\"float:right;\" href=\"javascript:void(0);\" onclick=\"showColumnConfig();\">列配置</a>\n");
      }
      //如果表单是组合查询
      if ("group".equals(getQueryMode())) {
        for (DataGridColumn col : columnList) {
          if (col.isQuery()) {
            sb.append("<span style=\"display:-moz-inline-box;display:inline-block;\">\n");

            sb.append("<span style=\"vertical-align:bottom;display:-moz-inline-box;display:inline-block;width:130px;margin:5px 0px 5px 0px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; \" title=\"" + col.getTitle() + "\">" + col.getTitle() + "：</span>\n");
            if ("single".equals(col.getQueryMode())) {
              if (!StringUtil.isEmpty(col.getReplace())) {
                sb.append("<select name=\"" + col.getField().replaceAll("_", "\\.") + "\" WIDTH=\"100\" style=\"width: 196px;height:23px;\"> \n");
                sb.append(StringUtil.replaceAll("<option value =\"\" >{0}</option>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.please.select")));
                String[] test = col.getReplace().split(",");
                String text = "";
                String value = "";

                for (String string : test) {
                  String lang_key = string.split("_")[0];
                  text = MutiLangUtil.getMutiLangInstance().getLang(lang_key);
                  value = string.split("_")[1];
                  sb.append("<option value =\"" + value + "\">" + text + "</option>\n");
                }
                sb.append("</select>\n");
              } else if (!StringUtil.isEmpty(col.getDictionary())) {
                if (col.getDictionary().contains(",") && col.isPopup()) {
                  String[] dic = col.getDictionary().split(",");
                  String sql = "select " + dic[1] + " as field," + dic[2]
                          + " as text from " + dic[0];
                  System.out.println(dic[0] + "--" + dic[1] + "--" + dic[2]);
                  //	<input type="text" name="order_code"  style="width: 100px"  class="searchbox-inputtext" value="" onClick="inputClick(this,'account','user_msg');" />
                  sb.append("<input type=\"text\" name=\"" + col.getField().replaceAll("_", "\\.") + "\" style=\"width: 100px\" class=\"searchbox-inputtext\" value=\"\" onClick=\"inputClick(this,'" + dic[1] + "','" + dic[0] + "');\" /> \n");
                } else if (col.getDictionary().contains(",") && (!col.isPopup())) {
                  String[] dic = col.getDictionary().split(",");
                  String sql = "";
                  if (dic.length >= 4) {
                    sql = "select " + dic[1] + " as field," + dic[2]
                            + " as text from " + dic[0] + " where " + dic[3];
                  } else {
                    sql = "select " + dic[1] + " as field," + dic[2]
                            + " as text from " + dic[0];
                  }
                  systemService = ApplicationContextUtil.getContext().getBean(
                          SystemService.class);
                  List<Map<String, Object>> list = systemService.findForJdbc(sql);
                  sb.append("<select name=\"" + col.getField().replaceAll("_", "\\.") + "\" WIDTH=\"100\" style=\"width: 196px;height:23px;\"> \n");
                  sb.append(StringUtil.replaceAll("<option value =\"\" >{0}</option>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.please.select")));
                  for (Map<String, Object> map : list) {
                    sb.append(" <option value=\"" + map.get("field") + "\">\n");
                    sb.append(map.get("text"));
                    sb.append(" </option>\n");
                  }
                  sb.append("</select>\n");
                } else {
                  Map<String, List<TSType>> typedatas = TSTypegroup.allTypes;
                  List<TSType> types = typedatas.get(col.getDictionary().toLowerCase());
                  sb.append("<select name=\"" + col.getField().replaceAll("_", "\\.") + "\" WIDTH=\"100\" style=\"width: 196px;height:23px;\"> \n");
                  sb.append(StringUtil.replaceAll("<option value =\"\" >{0}</option>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.please.select")));
                  if (types != null) {
                    for (TSType type : types) {
                      sb.append(" <option value=\""
                              + type.getTypecode()
                              + "\">\n");
                      sb.append(MutiLangUtil.getMutiLangInstance().getLang(type.getTypename()));
                      sb.append(" </option>\n");
                    }
                  }
                  sb.append("</select>\n");
                }
              } else if (col.isAutocomplete()) {
                sb.append(getAutoSpan(col.getField().replaceAll("_", "\\."), extendAttribute(col.getExtend())));
              } else {

                sb.append("<input class=\"inputxt\" onkeypress=\"EnterPress(event)\" onkeydown=\"EnterPress()\"  type=\"text\" name=\"" + col.getField().replaceAll("_", "\\.") + "\"  " + extendAttribute(col.getExtend()) + " style=\"width: 196px;height: 20px;\" />\n");
              }
            } else if ("group".equals(col.getQueryMode())) {
              sb.append("<input class=\"inputxt\" type=\"text\" name=\"" + col.getField() + "_begin\"  style=\"width: 94px\" " + extendAttribute(col.getExtend()) + "/>\n");
              sb.append("<span style=\"display:-moz-inline-box;display:inline-block;width: 8px;text-align:right;\">~</span>\n");
              sb.append("<input class=\"inputxt\" type=\"text\" name=\"" + col.getField() + "_end\"  style=\"width: 94px\" " + extendAttribute(col.getExtend()) + "/>\n");
            }
            sb.append("</span>\n");
          }
        }
      }
      if (isSon){
        sb.append("<span style=\"display:-moz-inline-box;display:inline-block;\">\n" +
                "<span style=\"vertical-align:bottom;display:-moz-inline-box;display:inline-block;width:130px;margin:5px 0px 5px 0px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; \" title=\"分支机构\">分支机构：</span>\n" +
                "<input id=\"sonId\" class=\"easyui-combotree\" data-options=\"multiple:true,cascadeCheck:false,checkbox:true,editable:false,url:'departController.do?loadDepartTree'\" onkeypress=\"EnterPress(event)\" onkeydown=\"EnterPress()\"  type=\"text\" name=\"sonId\"   style=\"width: 196px;height: 20px;\" />\n" +
                "</span>");
       }
      if ("group".equals(getQueryMode()) && hasQueryColum(columnList)) {//如果表单是组合查询
        sb.append("</br>");
        sb.append("</br>");
        sb.append("<div style=\"float:right;padding-right:200px;\">");
        sb.append("<span style=\"width:180px;position:absolute;bottom:0;\">\n");
//			sb.append("<a href=\"#\" class=\"easyui-linkbutton\" iconCls=\"icon-query\" onclick=\""+  name+ StringUtil.replaceAll("search()\">{0}</a>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.query")));
//			sb.append("<a href=\"#\" class=\"easyui-linkbutton\" iconCls=\"icon-reset\" onclick=\"searchReset('"+name+ StringUtil.replaceAll("')\">{0}</a>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.reset")) );
        sb.append("<a href=\"#\" class=\"qh-button icon-query\" onclick=\"" + name + "search()\"></a>\n");
        sb.append("<a href=\"#\" class=\"qh-button icon-reset\" onclick=\"" + name + "searchReset('" + name + "')\"></a>\n");
        if (queryBuilder) {
          sb.append("<a href=\"#\" class=\"easyui-linkbutton\" iconCls=\"icon-query\" onclick=\"queryBuilder('" + StringUtil.replaceAll("')\">{0}</a>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.querybuilder")));
        }
        sb.append("</span>\n");
        sb.append("</div>");
      } else if ("single".equals(getQueryMode()) && hasQueryColum(columnList)) {//如果表单是单查询
        sb.append("<span style=\"float:right\">\n");
        sb.append("<input id=\"" + name + "searchbox\" class=\"easyui-searchbox\"  data-options=\"searcher:" + name + StringUtil.replaceAll("searchbox,prompt:\'{0}\',menu:\'#", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.please.input.keyword")) + name + "mm\'\"></input>\n");
        sb.append("<div id=\"" + name + "mm\" style=\"width:120px\">\n");
        for (DataGridColumn col : columnList) {
          if (col.isQuery()) {
            sb.append("<div data-options=\"name:\'" + col.getField().replaceAll("_", "\\.") + "\',iconCls:\'icon-ok\' " + extendAttribute(col.getExtend()) + " \">" + col.getTitle() + "</div>  \n");
          }
        }
        sb.append("</div>\n");
        sb.append("</span>\n");
      }
      sb.append("</div>\n");
      sb.append("</div>\n");
    }
// else
//    {
//      if(isSon) {
//        sb.append("<div class=\"panel-header\" id=\"custHeader\" >\n" +
//                "<div class=\"panel-title\">查询条件</div>" +
//                "        <div class=\"panel-tool\"><a href=\"javascript:void(0)\" class=\"layout-button-up\" ></a></div>\n" +
//                "    </div>");
//        sb.append("<div  class=\"panel-body layout-body\" id=\"centerBody\" style=\"height: 10%;position:relative;padding-bottom:20px\">");
//        sb.append("<div name=\"searchColums\">\n");
//        //-----longjb1 增加用于高级查询的参数项
//        sb.append("<input  id=\"_sqlbuilder\" name=\"sqlbuilder\"   type=\"hidden\" />\n");
//        sb.append("<a class=\"easyui-linkbutton\" icon=\"icon-columnConfig\" plain=\"true\" style=\"float:right;margin-top:-6px;\" href=\"javascript:void(0);\" onclick=\"showColumnConfig();\">列配置</a>\n");
//        sb.append("<span style=\"display:-moz-inline-box;display:inline-block;\">\n" +
//                "<span style=\"vertical-align:bottom;display:-moz-inline-box;display:inline-block;width:130px;margin:5px 0px 5px 0px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; \" title=\"分支机构\">分支机构：</span>\n" +
//                "<input name=\"sonId\" class=\"easyui-combotree\" data-options=\"multiple:true,checkbox:true,url:'departController.do?loadDepartTree'\" onkeypress=\"EnterPress(event)\" onkeydown=\"EnterPress()\"  type=\"text\" name=\"itemName\"   style=\"width: 196px;height: 20px;\" />\n" +
//                "</span>");
//        sb.append("</br>");
//        sb.append("<span style=\"float:right;width:100%;position:absolute;bottom:0;padding-left:80%\">\n");
////			sb.append("<a href=\"#\" class=\"easyui-linkbutton\" iconCls=\"icon-query\" onclick=\""+  name+ StringUtil.replaceAll("search()\">{0}</a>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.query")));
////			sb.append("<a href=\"#\" class=\"easyui-linkbutton\" iconCls=\"icon-reset\" onclick=\"searchReset('"+name+ StringUtil.replaceAll("')\">{0}</a>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.reset")) );
//        sb.append("<a href=\"#\" class=\"qh-button icon-query\" onclick=\"" + name + "search()\"></a>\n");
//        sb.append("<a href=\"#\" class=\"qh-button icon-reset\" onclick=\"" + name + "searchReset('" + name + "')\"></a>\n");
//        if (queryBuilder) {
//            sb.append("<a href=\"#\" class=\"easyui-linkbutton\" iconCls=\"icon-query\" onclick=\"queryBuilder('" + StringUtil.replaceAll("')\">{0}</a>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.querybuilder")));
//          }
//          sb.append("</span>\n");
//        } else if ("single".equals(getQueryMode()) && hasQueryColum(columnList)) {//如果表单是单查询
//          sb.append("<span style=\"float:right\">\n");
//          sb.append("<input id=\"" + name + "searchbox\" class=\"easyui-searchbox\"  data-options=\"searcher:" + name + StringUtil.replaceAll("searchbox,prompt:\'{0}\',menu:\'#", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.please.input.keyword")) + name + "mm\'\"></input>\n");
//          sb.append("<div id=\"" + name + "mm\" style=\"width:120px\">\n");
//          for (DataGridColumn col : columnList) {
//            if (col.isQuery()) {
//              sb.append("<div data-options=\"name:\'" + col.getField().replaceAll("_", "\\.") + "\',iconCls:\'icon-ok\' " + extendAttribute(col.getExtend()) + " \">" + col.getTitle() + "</div>  \n");
//            }
//          }
//        sb.append("</div>\n");
//        sb.append("</span>\n");
//        sb.append("</div>\n");
//        sb.append("</div>\n");
//      }
//    }
    if (toolBarList.size() == 0 && !hasQueryColum(columnList)) {
      sb.append("<div style=\"height:0px;\" >\n");
    } else {
      sb.append("<div style=\"height:30px;\" class=\"datagrid-toolbar\">\n");
    }
    sb.append("<span style=\"position:absolute;z-index:3000\" >\n");
    if (toolBarList.size() > 0) {
      for (DataGridUrl toolBar : toolBarList) {
        sb.append("<a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" icon=\"" + toolBar.getIcon() + "\" \n");
        if (StringUtil.isNotEmpty(toolBar.getOnclick())) {
          sb.append("onclick=" + toolBar.getOnclick() + "\n");
        } else {
          sb.append("onclick=\"" + toolBar.getFunname() + "(\n");
          if (!toolBar.getFunname().equals("doSubmit")) {
            sb.append("\'" + toolBar.getTitle() + "\',\n");
          }
          String width = toolBar.getWidth().contains("%") ? "'" + toolBar.getWidth() + "'" : toolBar.getWidth();
          String height = toolBar.getHeight().contains("%") ? "'" + toolBar.getHeight() + "'" : toolBar.getHeight();
          sb.append("\'" + toolBar.getUrl() + "\',\'" + name + "\'," + width + "," + height + ",\'" + toolBar.getWindowType() + "\')\"\n");
        }
        sb.append(">" + toolBar.getTitle() + "</a>\n");
      }
    }
    TSUser user = ResourceUtil.getSessionUserName();
    userService = ApplicationContextUtil.getContext().getBean(UserService.class);
    String roleCode = userService.getUserRole(user);
    TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
    TSDepart depart = systemService.getParentSonInfo(sonInfo);
    //当前登录用户分支机构id
    sb.append("<input id=\"sonCompanyId\" type=\"hidden\" value=\""+depart.getId()+"\"/>");
    if (StringUtils.isNotEmpty(tranType) && roleCode.indexOf("sc_allowAudit") > -1) {
      if(userService.isAllowAudit(tranType,user.getId(),depart.getId())) {
        sb.append("<a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" icon=\"new-icon-audit\" ");
        sb.append("onclick=\"goAudit('" + name + "')\" \n");
        sb.append(">审核</a>\n");
        sb.append("<a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" icon=\"new-icon-unAudit\" ");
        sb.append("onclick=\"goUnAudit('" + name + "')\" \n");
        sb.append(">反审核</a>\n");
      }
    }
    sb.append("</span>\n");
    sb.append("</div>\n");
    if (queryBuilder) {
      addQueryBuilder(sb, "easyui-linkbutton");
    }
    return sb;
  }


  /**
   * 生成扩展属性
   *
   * @param field
   * @return
   */
  private String extendAttribute(String field) {
    if (StringUtil.isEmpty(field)) {
      return "";
    }
    field = dealSyscode(field, 1);
    StringBuilder re = new StringBuilder();
    try {
      JSONObject obj = JSONObject.fromObject(field);
      Iterator it = obj.keys();
      while (it.hasNext()) {
        String key = String.valueOf(it.next());
        JSONObject nextObj = ((JSONObject) obj.get(key));
        Iterator itvalue = nextObj.keys();
        re.append(key + "=" + "\"");
        if (nextObj.size() <= 1) {
          String onlykey = String.valueOf(itvalue.next());
          if ("value".equals(onlykey)) {
            re.append(nextObj.get(onlykey) + "");
          } else {
            re.append(onlykey + ":" + nextObj.get(onlykey) + "");
          }
        } else {
          while (itvalue.hasNext()) {
            String multkey = String.valueOf(itvalue.next());
            String multvalue = nextObj.getString(multkey);
            re.append(multkey + ":" + multvalue + ",");
          }
          re.deleteCharAt(re.length() - 1);
        }
        re.append("\" ");
      }
    } catch (Exception e) {
      e.printStackTrace();
      return "";
    }
    return dealSyscode(re.toString(), 2);
  }


  /**
   * 生成扩展属性
   *
   * @param field
   * @return
   */
  private String extendAttributeOld(String field) {
    StringBuffer sb = new StringBuffer();
    //增加扩展属性
    if (!StringUtils.isBlank(field)) {
      Gson gson = new Gson();
      Map<String, String> mp = gson.fromJson(field, Map.class);
      for (Map.Entry<String, String> entry : mp.entrySet()) {
        sb.append(entry.getKey() + "=" + gson.toJson(entry.getValue()) + "\"");
      }
    }
    return sb.toString();
  }

  /**
   * 处理否含有json转换中的保留字
   *
   * @param field
   * @param flag  1:转换 2:还原
   * @return
   */
  private String dealSyscode(String field, int flag) {
    String change = field;
    Iterator it = syscode.keySet().iterator();
    while (it.hasNext()) {
      String key = String.valueOf(it.next());
      String value = String.valueOf(syscode.get(key));
      if (flag == 1) {
        change = field.replaceAll(key, value);
      } else if (flag == 2) {
        change = field.replaceAll(value, key);
      }
    }
    return change;
  }

  /**
   * 判断是否存在查询字段
   *
   * @return hasQuery true表示有查询字段,false表示没有
   */
  protected boolean hasQueryColum(List<DataGridColumn> columnList) {
    boolean hasQuery = false;
    for (DataGridColumn col : columnList) {
      if (col.isQuery()) {
        hasQuery = true;
      }
    }
    return hasQuery;
  }

  /**
   * 拼装操作地址
   *
   * @param sb
   */
  protected void getOptUrl(StringBuffer sb) {
    //注：操作列表会带入合计列中去，故加此判断
    sb.append("if(!rec.id){return '';}");
    List<DataGridUrl> list = urlList;
    sb.append("var href='';");
    for (DataGridUrl dataGridUrl : list) {
      String url = dataGridUrl.getUrl();
      TSUser user = ResourceUtil.getSessionUserName();
      userService = ApplicationContextUtil.getContext().getBean(UserService.class);
      String roleCode = userService.getUserRole(user);
      if ("反审核".equals(dataGridUrl.getTitle())) {
        if (roleCode.indexOf("sc_allowAudit") < 0) {
          continue;
        }
        ;
      }
      MessageFormat formatter = new MessageFormat("");
      if (dataGridUrl.getValue() != null) {
        String[] testvalue = dataGridUrl.getValue().split(",");
        List value = new ArrayList<Object>();
        for (String string : testvalue) {
          value.add("\"+rec." + string + " +\"");
        }
        url = formatter.format(url, value.toArray());
      }
      if (url != null && dataGridUrl.getValue() == null) {

        url = formatUrl(url);
      }
      String exp = dataGridUrl.getExp();// 判断显示表达式
      if (StringUtil.isNotEmpty(exp)) {
        String[] ShowbyFields = exp.split("&&");
        for (String ShowbyField : ShowbyFields) {
          int beginIndex = ShowbyField.indexOf("#");
          int endIndex = ShowbyField.lastIndexOf("#");
          String exptype = ShowbyField.substring(beginIndex + 1, endIndex);// 表达式类型
          String field = ShowbyField.substring(0, beginIndex);// 判断显示依据字段
          String[] values = ShowbyField.substring(endIndex + 1, ShowbyField.length()).split(",");// 传入字段值
          String value = "";
          for (int i = 0; i < values.length; i++) {
            value += "'" + "" + values[i] + "" + "'";
            if (i < values.length - 1) {
              value += ",";
            }
          }
          if (!ArrayUtils.contains(fields.split(","), field)) {
            if ("eq".equals(exptype)) {
              sb.append("if($.inArray('" + field + "',[" + value + "])>=0){");
            }
            if ("ne".equals(exptype)) {
              sb.append("if($.inArray('" + field + "',[" + value + "])<0){");
            }
            if ("empty".equals(exptype) && value.equals("'true'")) {
              sb.append("if('" + field + "'==''){");
            }
            if ("empty".equals(exptype) && value.equals("'false'")) {
              sb.append("if('" + field + "'!=''){");
            }
          } else {
            if ("eq".equals(exptype)) {
              sb.append("if($.inArray(rec." + field + ",[" + value + "])>=0){");
            }
            if ("ne".equals(exptype)) {
              sb.append("if($.inArray(rec." + field + ",[" + value + "])<0){");
            }
            if ("empty".equals(exptype) && value.equals("'true'")) {
              sb.append("if(rec." + field + "==''){");
            }
            if ("empty".equals(exptype) && value.equals("'false'")) {
              sb.append("if(rec." + field + "!=''){");
            }
          }
        }
      }

      if (OptTypeDirection.Confirm.equals(dataGridUrl.getType())) {
        sb.append("href+=\"[<a href=\'#\' onclick=confirm(\'" + url + "\',\'" + dataGridUrl.getMessage() + "\',\'" + name + "\')> \";");
      }
      if (OptTypeDirection.Del.equals(dataGridUrl.getType())) {
        sb.append("href+=\"[<a href=\'#\' onclick=delObj(\'" + url + "\',\'" + name + "\')>\";");
      }
      if (OptTypeDirection.Fun.equals(dataGridUrl.getType())) {
        //格式如下：funname="ftleditor(id,cgformName)" 引用当前行的字段值直接写字段名
        String name = TagUtil.getFunction(dataGridUrl.getFunname());
        String parmars = TagUtil.getFunParams(dataGridUrl.getFunname());
        sb.append("href+=\"[<a href=\'#\' onclick=" + name + "(" + parmars + ")>\";");
      }
      if (OptTypeDirection.OpenWin.equals(dataGridUrl.getType())) {
        sb.append("href+=\"[<a href=\'#\' onclick=openwindow('" + dataGridUrl.getTitle() + "','" + url + "','" + name + "'," + dataGridUrl.getWidth() + "," + dataGridUrl.getHeight() + ")>\";");
      }                              //update-end--Author:liuht  Date:20130228 for：弹出窗口设置参数不生效
      if (OptTypeDirection.Deff.equals(dataGridUrl.getType())) {
        sb.append("href+=\"[<a href=\'" + url + "' title=\'" + dataGridUrl.getTitle() + "\'>\";");
      }
      if (OptTypeDirection.OpenTab.equals(dataGridUrl.getType())) {
        sb.append("href+=\"[<a href=\'#\' onclick=addOneTab('" + dataGridUrl.getTitle() + "','" + url + "')>\";");
      }
      sb.append("href+=\"" + dataGridUrl.getTitle() + "</a>]\";");
//			if(StringUtils.isNotEmpty(tranType)){
//				sb.append("href+=\"[<a href=\'#\' onclick=goUnAudit('"+name+"')>反审核</a>]\";");
//			}
      if (StringUtil.isNotEmpty(exp)) {
        for (int i = 0; i < exp.split("&&").length; i++) {
          sb.append("}");
        }

      }
    }
    sb.append("return href;");
  }

  /**
   * 列自定义函数
   *
   * @param sb
   * @param column
   */
  protected void getFun(StringBuffer sb, DataGridColumn column) {
    String url = column.getUrl();
    url = formatUrl(url);
    sb.append("var href=\"<a style=\'color:red\' href=\'#\' onclick=" + column.getFunname() + "('" + column.getTitle() + "','" + url + "')>\";");
    sb.append("return href+value+\'</a>\';");

  }

  /**
   * 格式化URL
   *
   * @return
   */
  protected String formatUrl(String url) {
    MessageFormat formatter = new MessageFormat("");
    String parurlvalue = "";
    if (url.indexOf("&") >= 0) {
      String beforeurl = url.substring(0, url.indexOf("&"));// 截取请求地址
      String parurl = url.substring(url.indexOf("&") + 1, url.length());// 截取参数
      String[] pras = parurl.split("&");
      List value = new ArrayList<Object>();
      int j = 0;
      for (int i = 0; i < pras.length; i++) {
        if (pras[i].indexOf("{") >= 0 || pras[i].indexOf("#") >= 0) {
          String field = pras[i].substring(pras[i].indexOf("{") + 1, pras[i].lastIndexOf("}"));
          parurlvalue += "&" + pras[i].replace("{" + field + "}", "{" + j + "}");
          value.add("\"+rec." + field + " +\"");
          j++;
        } else {
          parurlvalue += "&" + pras[i];
        }
      }
      url = formatter.format(beforeurl + parurlvalue, value.toArray());
    }
    return url;

  }

  /**
   * 拼接字段  普通列
   *
   * @param sb
   */
  protected void getField(StringBuffer sb) {
    getField(sb, 1);
  }

  /**
   * 拼接字段
   *
   * @param sb
   * @frozen 0 冰冻列    1 普通列
   */
  protected void getField(StringBuffer sb, int frozen) {
    // 复选框
    if (checkbox && frozen == 0) {
      sb.append("{field:\'ck\',checkbox:\'true\'},\n");
    }
    int i = 0;
    for (DataGridColumn column : columnList) {
      i++;
      if ((column.isFrozenColumn() && frozen == 0) || (!column.isFrozenColumn() && frozen == 1)) {
        String field;
        if (treegrid) {
          field = column.getTreefield();
        } else {
          field = column.getField();
        }
        sb.append("{field:\'" + field + "\',title:\'" + column.getTitle() + "\'\n");
        if (column.getWidth() != null) {
          sb.append(",width:" + column.getWidth());
        }
        if (column.getAlign() != null) {
          sb.append(",align:\'" + column.getAlign() + "\'\n");
        }
        if (StringUtils.isNotEmpty(column.getExtendParams())) {
          sb.append("," + column.getExtendParams().substring(0,
                  column.getExtendParams().length() - 1));
        }
        // 隐藏字段
        if (column.isHidden()) {
          sb.append(",hidden:true\n");
        }
        if (column.isMergeCells()) {
          sb.append(",mergeCells:true\n");
        }
        //编辑器
        if (StringUtil.isNotEmpty(column.getEditor())) {
          if (column.getEditor().contains("{")) {
            sb.append(",editor:" + column.getEditor());
          } else {
            sb.append(",editor:'" + column.getEditor()+"'");
          }
        }

        if (!treegrid) {
          // 字段排序
          if ((column.isSortable()) && (field.indexOf("_") <= 0 && field != "opt")) {
            sb.append(",sortable:" + column.isSortable() + "\n");
          }
        }
        // 显示图片
        if (column.isImage()) {
          sb.append(",formatter:function(value,rec,index){\n");
          sb.append(" return '<img border=\"0\" src=\"'+value+'\"/>';}\n");
        }
        // 自定义显示图片
        if (column.getImageSize() != null) {
          String[] tld = column.getImageSize().split(",");
          sb.append(",formatter:function(value,rec,index){\n");
          sb.append(" return '<img width=\"" + tld[0]
                  + "\" height=\"" + tld[1]
                  + "\" border=\"0\" src=\"'+value+'\"/>';}\n");
          tld = null;
        }
        if (column.getDownloadName() != null) {
          sb.append(",formatter:function(value,rec,index){\n");
          sb.append(" return '<a target=\"_blank\" href=\"'+value+'\">"
                  + column.getDownloadName() + "</a>';}\n");
        }
        // 自定义链接
        if (column.getUrl() != null) {
          sb.append(",formatter:function(value,rec,index){\n");
          this.getFun(sb, column);
          sb.append("}\n");
        }
        if (column.getFormatter() != null) {
          sb.append(",formatter:function(value,rec,index){\n");
//				sb.append("debugger;\n");
          sb.append("if(value==null || value==undefined || value=='null') return '';\n");
          sb.append(" return new Date().format('" + column.getFormatter() + "',value);}\n");
        }
        // 加入操作
        if (column.getField().equals("opt")) {
          sb.append(",formatter:function(value,rec,index){\n");
          // sb.append("return \"");
          this.getOptUrl(sb);
          sb.append("}\n");
        }
        // 值替換
        if (columnValueList.size() > 0 && !column.getField().equals("opt")) {
          String testString = "";
          for (ColumnValue columnValue : columnValueList) {
            if (columnValue.getName().equals(column.getField())) {
              String[] value = columnValue.getValue().split(",");
              String[] text = columnValue.getText().split(",");
              String[] dic = null;
              if (column.getDictionary() != null && column.getDictionary().contains(",")) {
                dic = column.getDictionary().split(",");
              }
              sb.append(",formatter:function(value,rec,index){\n");
              sb.append("if(value==null || value==''){\n");
              sb.append("return '';\n");
              sb.append("}else{\n");
              sb.append("var valArray = value.split(',');\n");
              sb.append("if(valArray.length > 1){\n");
              sb.append("var checkboxValue = '';\n");
              sb.append("for(var k=0; k<valArray.length; k++){\n");
              for (int j = 0; j < value.length; j++) {
                sb.append("if(valArray[k] == '" + value[j] + "'){ checkboxValue = checkboxValue + \'" + text[j] + "\' + ','}\n");
              }
              if (dic != null) {
                sb.append("if(checkboxValue==''){\n");
                sb.append("$.ajax({url:'systemController.do?getDicNameFromTable&tableName=" + dic[0] + "&idField=" + dic[1] + "&idValue='+valArray[k]+'&nameField=" + dic[2] + "',success:function(data){");
                sb.append("checkboxValue += data+',';\n");
                sb.append("},async:false});}\n");
              }
              sb.append("}\n");

              sb.append("return checkboxValue.substring(0,checkboxValue.length-1);\n");
              sb.append("}\n");
              sb.append("else{\n");
              for (int j = 0; j < value.length; j++) {
                testString += "if(value=='" + value[j] + "'){return \'" + text[j] + "\'}\n";
              }
              sb.append(testString);
              sb.append("else{\n");
              if (dic != null) {
                sb.append("var newValue;\n");
                sb.append("$.ajax({url:'systemController.do?getDicNameFromTable&tableName=" + dic[0] + "&idField=" + dic[1] + "&idValue='+value+'&nameField=" + dic[2] + "',\n");
                sb.append("success:function(data){\n");
                sb.append("newValue=data;\n");
                sb.append("},async:false});\n");
                sb.append("return newValue;\n");
              } else {
                sb.append("return value;\n");
              }
              sb.append("}\n");
              sb.append("}\n");
              sb.append("}\n");
              sb.append("}\n");
            }
          }

        }
        // 背景设置
        if (columnStyleList.size() > 0 && !column.getField().equals("opt")) {
          String testString = "";
          for (ColumnValue columnValue : columnStyleList) {
            if (columnValue.getName().equals(column.getField())) {
              String[] value = columnValue.getValue().split(",");
              String[] text = columnValue.getText().split(",");
              sb.append(",styler:function(value,rec,index){\n");
              if ((value.length == 0 || StringUtils.isEmpty(value[0])) && text.length == 1) {
                if (text[0].indexOf("(") > -1) {
                  testString = " return \'" + text[0].replace("(", "(value,rec,index") + "\'\n";
                } else {
                  testString = " return \'" + text[0] + "\'\n";
                }
              } else {
                for (int j = 0; j < value.length; j++) {
                  testString += "if(value=='" + value[j] + "'){return \'" + text[j] + "\'}\n";
                }
              }
              sb.append(testString);
              sb.append("}\n");
            }
          }

        }
        sb.append("}\n");
        // 去除末尾,
        if (i < columnList.size()) {
          sb.append(",\n");
        }
      }
    }
  }

  /**
   * 设置分页条信息
   *
   * @param sb
   */
  protected void setPager(StringBuffer sb, String grid) {
    sb.append("$(\'#" + name + "\')." + grid + "(\'getPager\').pagination({");
    sb.append("beforePageText:\'\'," + "afterPageText:\'/{pages}\',");
    if (showText) {
      sb.append("displayMsg:\'{from}-{to}" + MutiLangUtil.getMutiLangInstance().getLang("common.total") + " {total}" + MutiLangUtil.getMutiLangInstance().getLang("common.item") + "\',");
    } else {
      sb.append("displayMsg:\'\',");
    }
    if (showPageList == true) {
      sb.append("showPageList:true,");
    } else {
      sb.append("showPageList:false,");
    }
    sb.append("showRefresh:" + showRefresh + "");
    sb.append("});");// end getPager
    sb.append("$(\'#" + name + "\')." + grid + "(\'getPager\').pagination({");
    sb.append("onBeforeRefresh:function(pageNumber, pageSize){ $(this).pagination(\'loading\');$(this).pagination(\'loaded\'); }");
    sb.append("});");
  }

  //列表查询框函数
  protected void searchboxFun(StringBuffer sb, String grid) {
    sb.append("function " + name + "searchbox(value,name){");
    sb.append("var queryParams=$(\'#" + name + "\').datagrid('options').queryParams;");
    sb.append("queryParams[name]=value;queryParams.searchfield=name;$(\'#" + name + "\')." + grid + "(\'reload\');}");
    sb.append("$(\'#" + name + "searchbox\').searchbox({");
    sb.append("searcher:function(value,name){");
    sb.append("" + name + "searchbox(value,name);");
    sb.append("},");
    sb.append("menu:\'#" + name + "mm\',");
    sb.append(StringUtil.replaceAll("prompt:\'{0}\'", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.please.input.query.keyword")));
    sb.append("});");
  }

  public String getNoAuthOperButton() {
    StringBuffer sb = new StringBuffer();
    if (ResourceUtil.getSessionUserName().getUserName().equals("programmer") || !Globals.BUTTON_AUTHORITY_CHECK) {
    } else {
      Set<String> operationCodes = (Set<String>) super.pageContext.getRequest().getAttribute(Globals.OPERATIONCODES);
      if (null != operationCodes) {
        for (String MyoperationCode : operationCodes) {
          if (oConvertUtils.isEmpty(MyoperationCode))
            break;
          systemService = ApplicationContextUtil.getContext().getBean(
                  SystemService.class);
          TSOperation operation = systemService.getEntity(TSOperation.class, MyoperationCode);
          if (operation.getOperationcode().startsWith(".") || operation.getOperationcode().startsWith("#")) {
            if (operation.getOperationType().intValue() == Globals.OPERATION_TYPE_HIDE) {
              //out.append("$(\""+name+"\").find(\"#"+operation.getOperationcode().replaceAll(" ", "")+"\").hide();");
              sb.append("$(\"" + operation.getOperationcode().replaceAll(" ", "") + "\").hide();");
            } else {
              //out.append("$(\""+name+"\").find(\"#"+operation.getOperationcode().replaceAll(" ", "")+"\").find(\":input\").attr(\"disabled\",\"disabled\");");
              sb.append("$(\"" + operation.getOperationcode().replaceAll(" ", "") + "\").attr(\"disabled\",\"disabled\");");
              sb.append("$(\"" + operation.getOperationcode().replaceAll(" ", "") + "\").find(\":input\").attr(\"disabled\",\"disabled\");");
            }
          }
        }
      }

    }
    LogUtil.info("----getNoAuthOperButton-------" + sb.toString());
    return sb.toString();
  }

  /**
   * 描述：组装菜单按钮操作权限
   * dateGridUrl：url
   * operationCode：操作码
   * optList： 操作列表
   *
   * @version 1.0
   */
  private void installOperationCode(DataGridUrl dataGridUrl, String operationCode, List optList) {
    if (ResourceUtil.getSessionUserName().getUserName().equals("programmer") || !Globals.BUTTON_AUTHORITY_CHECK) {
      optList.add(dataGridUrl);
    } else if (!oConvertUtils.isEmpty(operationCode)) {
      Set<String> operationCodes = (Set<String>) super.pageContext.getRequest().getAttribute(Globals.OPERATIONCODES);
      if (null != operationCodes) {
        List<String> operationCodesStr = new ArrayList<String>();
        for (String MyoperationCode : operationCodes) {
          if (oConvertUtils.isEmpty(MyoperationCode))
            break;
          systemService = ApplicationContextUtil.getContext().getBean(
                  SystemService.class);
          TSOperation operation = systemService.getEntity(TSOperation.class, MyoperationCode);
          operationCodesStr.add(operation.getOperationcode());
        }
        if (!operationCodesStr.contains(operationCode)) {
          optList.add(dataGridUrl);
        }
      }
    } else {
      optList.add(dataGridUrl);
    }
  }

  /**
   * 获取自动补全的panel
   *
   * @param filed
   * @return
   * @author Zerrion
   */
  private String getAutoSpan(String filed, String extend) {
    String id = filed.replaceAll("\\.", "_");
    StringBuffer nsb = new StringBuffer();
    nsb.append("<script type=\"text/javascript\">\n");
    nsb.append("$(document).ready(function() {\n")
            .append("$(\"#" + getEntityName() + "_" + id + "\").autocomplete(\"commonController.do?getAutoList\",{\n")
            .append("max: 5,minChars: 2,width: 194,scrollHeight: 100,matchContains: true,autoFill: false,extraParams:{\n")
            .append("featureClass : \"P\",style : \"full\",	maxRows : 10,labelField : \"" + filed + "\",valueField : \"" + filed + "\",\n")
            .append("searchField : \"" + filed + "\",entityName : \"" + getEntityName() + "\",trem: function(){return $(\"#" + getEntityName() + "_" + id + "\").val();}}\n");
    nsb.append(",parse:function(data){return jeecgAutoParse.call(this,data);}\n");
    nsb.append(",formatItem:function(row, i, max){return row['" + filed + "'];} \n");
    nsb.append("}).result(function (event, row, formatted) {\n");
    nsb.append("$(\"#" + getEntityName() + "_" + id + "\").val(row['" + filed + "']);}); });\n")
            .append("</script>\n")
            .append("<input class=\"inputxt\" type=\"text\" id=\"" + getEntityName() + "_" + id + "\" name=\"" + filed + "\" datatype=\"*\" " + extend + StringUtil.replace(" nullmsg=\"\" errormsg=\"{0}\" style=\"width: 196px;height:23px;\"/>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("input.error")));
    return nsb.toString();
  }

  /**
   * 获取实体类名称,没有这根据规则设置
   *
   * @return
   */
  private String getEntityName() {
    if (StringUtils.isEmpty(entityName)) {
      entityName = actionUrl.substring(0, actionUrl.indexOf("Controller"));
      entityName = (entityName.charAt(0) + "").toUpperCase() + entityName.substring(1) + "Entity";
    }
    return entityName;
  }

  public boolean isFitColumns() {
    return fitColumns;
  }

  public void setFitColumns(boolean fitColumns) {
    this.fitColumns = fitColumns;
  }

  public String getSortName() {
    return sortName;
  }

  public void setSortName(String sortName) {
    this.sortName = sortName;
  }

  public String getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(String sortOrder) {
    this.sortOrder = sortOrder;
  }

  public String getQueryMode() {
    return queryMode;
  }

  public void setQueryMode(String queryMode) {
    this.queryMode = queryMode;
  }

  public boolean isAutoLoadData() {
    return autoLoadData;
  }

  public void setAutoLoadData(boolean autoLoadData) {
    this.autoLoadData = autoLoadData;
  }

  public void setOpenFirstNode(boolean openFirstNode) {
    this.openFirstNode = openFirstNode;
  }

  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  public void setRowStyler(String rowStyler) {
    this.rowStyler = rowStyler;
  }

  public void setExtendParams(String extendParams) {
    this.extendParams = extendParams;
  }

  public void setLangArg(String langArg) {
    this.langArg = langArg;
  }

  //-----author:jg_longjb----start-----date:20150408--------for:新增ace 界面下的button class样式
  public StringBuffer aceStyleTable() {
    String grid = "";
    List<String> acList = new ArrayList<String>();
    StringBuffer sb = new StringBuffer();
    width = (width == null) ? "auto" : width;
    height = (height == null) ? "auto" : height;
    sb.append("<link rel=\"stylesheet\" href=\"plug-in/easyui/themes/metro/main.css\" /><script type=\"text/javascript\">");
    sb.append("$(function(){");
    sb.append("var myview = $.extend({}, $.fn.datagrid.defaults.view, {");
    sb.append("  onAfterRender: function (target) {");
    sb.append("    $.fn.datagrid.defaults.view.onAfterRender.call(this, target);");
    sb.append("    var opts = $(target).datagrid('options');");
    sb.append("    var vc = $(target).datagrid('getPanel').children('div.datagrid-view');");
    sb.append("    vc.children('div.datagrid-empty').remove();");
    sb.append("    if (!$(target).datagrid('getRows').length) {");
    sb.append("      var d = $('<div class=\"datagrid-empty\"></div>').html(opts.emptyMsg || '暂无数据').appendTo(vc);");
    sb.append("      d.css({");
    sb.append("        position: 'absolute',");
    sb.append("        left: 0,");
    sb.append("        top: 50,");
    sb.append("        width: '100%',");
    sb.append("        textAlign: 'center'");
    sb.append("      });");
    sb.append("    }");
    sb.append("  }");
    sb.append("});");
    sb.append("  storage=$.localStorage;if(!storage)storage=$.cookieStorage;");
    sb.append(this.getNoAuthOperButton());
    if (treegrid) {
      grid = "treegrid";
      sb.append("$(\'#" + name + "\').treegrid({");
      sb.append("idField:'id',");
      sb.append("treeField:'text',");
    } else {
      grid = "datagrid";
      sb.append("$(\'#" + name + "\').datagrid({");
      sb.append("view: myview,");
      sb.append("emptyMsg: '暂无数据',");
      sb.append("idField: '" + idField + "',");
    }
    if (title != null) {
      sb.append("title: \'" + title + "\',");
    }

    if (autoLoadData)
      sb.append("url:\'" + actionUrl + "&field=" + fields + "\',");
    else
      sb.append("url:\'',");
    if (StringUtils.isNotEmpty(rowStyler)) {
      sb.append("rowStyler: function(index,row){ return " + rowStyler + "(index,row);},");
    }
    if (StringUtils.isNotEmpty(extendParams)) {
      sb.append(extendParams);
    }
    if (fit) {
      sb.append("fit:true,");
    } else {
      sb.append("fit:false,");
    }
    sb.append(StringUtil.replaceAll("loadMsg: \'{0}\',", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.data.loading")));
    sb.append("striped:true,pageSize: " + pageSize + ",");
    sb.append("pagination:" + pagination + ",");
    sb.append("pageList:[" + pageSize * 1 + "," + pageSize * 2 + "," + pageSize * 3 + "," + pageSize * 4 + "," + pageSize * 5 + "],");
    if (StringUtils.isNotBlank(sortName)) {
      sb.append("sortName:'" + sortName + "',");
    }
    sb.append("sortOrder:'" + sortOrder + "',");
    sb.append("rownumbers:true,");
    sb.append("singleSelect:" + !checkbox + ",");
    if (fitColumns) {
      sb.append("fitColumns:true,");
    } else {
      sb.append("fitColumns:false,");
    }
    sb.append("showFooter:true,");
    sb.append("frozenColumns:[[");
    this.getField(sb, 0);
    sb.append("]],");

    sb.append("columns:[[");
    this.getField(sb);
    sb.append("]],");
    sb.append("onLoadSuccess:function(data){$(\"#" + name + "\")." + grid + "(\"clearSelections\");");
    if (openFirstNode && treegrid) {
      sb.append(" if(data==null){");
      sb.append(" var firstNode = $(\'#" + name + "\').treegrid('getRoots')[0];");
      sb.append(" $(\'#" + name + "\').treegrid('expand',firstNode.id)}");
    }
    if(!checkOnSelect){

      sb.append("unCheckRow(this.id); \n");
    }
    //调用箭头上下移事件
    sb.append("moveRow(this);\n");
    if (StringUtil.isNotEmpty(onLoadSuccess)) {
      sb.append(onLoadSuccess + "(data);\n");
    }
    sb.append("},");
    if (StringUtil.isNotEmpty(onDblClick)) {
      sb.append("onDblClickRow:function(rowIndex,rowData){" + onDblClick + "(rowIndex,rowData);},");
    }
    if (treegrid) {
      sb.append("onClickRow:function(rowData){");
    } else {
      sb.append("onClickRow:function(rowIndex,rowData){");
    }
    /**行记录赋值*/
    sb.append("rowid=rowData.id;");
    sb.append("gridname=\'" + name + "\';");
    if (StringUtil.isNotEmpty(onClick)) {
      if (treegrid) {
        sb.append("" + onClick + "(rowData);");
      } else {
        sb.append("" + onClick + "(rowIndex,rowData);");
      }
    }
    sb.append("},\n");
    //行编辑
    sb.append("onBeforeEdit:function(index,row){ \n");
    sb.append("    row.editing = true;\n");
    sb.append("    $(\'#" + name + "\').datagrid('refreshRow', index); \n");
    sb.append("},\n");
    sb.append("onAfterEdit:function(index,row){ \n");
    sb.append("    row.editing = false;\n");
    sb.append("    $(\'#" + name + "\').datagrid('refreshRow', index); \n");
    sb.append("},\n");
    sb.append("onCancelEdit:function(index,row){ \n");
    sb.append("    row.editing = false;\n");
    sb.append("    $(\'#" + name + "\').datagrid('refreshRow', index); \n");
    sb.append("}\n");
    sb.append("});\n");
    this.setPager(sb, grid);
    sb.append("try{restoreheader();}catch(ex){}");
    sb.append("});");
    sb.append("function reloadTable(){");
    sb.append("try{");
    sb.append("	$(\'#\'+gridname).datagrid(\'reload\');");
    sb.append("	$(\'#\'+gridname).treegrid(\'reload\');");
    sb.append("}catch(ex){}");
    sb.append("}");
    sb.append("/** \n");
    sb.append(" * datagrid单击行时不选中行 \n");
    sb.append(" * @param id \n");
    sb.append(" */  \n");
    sb.append("function unCheckRow(id){  \n");
    sb.append("    var p = $('#'+id).datagrid('getPanel');  \n");
    sb.append("    //var rows = p.find('tr.datagrid-row');  \n");
    sb.append("    var rows = p.find('tr.datagrid-row td[field!=ck]');  \n");
    sb.append("    rows.unbind('click').bind('click', function(e) {  \n");
    sb.append("        return false;  \n");
    sb.append("    });  \n");
    sb.append("}\n");
    sb.append("function reload" + name + "(){" + "$(\'#" + name + "\')." + grid + "(\'reload\');" + "}");
    sb.append("function get" + name + "Selected(field){return getSelected(field);}");
    sb.append("function getSelected(field){" + "var row = $(\'#\'+gridname)." + grid + "(\'getSelected\');" + "if(row!=null)" + "{" + "value= row[field];" + "}" + "else" + "{" + "value=\'\';" + "}" + "return value;" + "}");
    sb.append("function get" + name + "Selections(field){" + "var ids = [];" + "var rows = $(\'#" + name + "\')." + grid + "(\'getSelections\');" + "for(var i=0;i<rows.length;i++){" + "ids.push(rows[i][field]);" + "}" + "ids.join(\',\');" + "return ids" + "};");
    sb.append("function getSelectRows(){");
    sb.append("	return $(\'#" + name + "\').datagrid('getChecked');}");
    sb.append(" function saveHeader(){");
    sb.append(" var columnsFields =null;var easyextends=false;try{columnsFields = $('#" + name + "').datagrid('getColumns');easyextends=true;");
    sb.append("}catch(e){columnsFields =$('#" + name + "').datagrid('getColumnFields');}");
    sb.append("	var cols = storage.get( '" + name + "hiddenColumns');var init=true;	if(cols){init =false;} " +
            "var hiddencolumns = [];for(var i=0;i< columnsFields.length;i++) {if(easyextends){");
    sb.append("hiddencolumns.push({field:columnsFields[i].field,hidden:columnsFields[i].hidden});}else{");
    sb.append(" var columsDetail = $('#" + name + "').datagrid(\"getColumnOption\", columnsFields[i]); ");
    sb.append("if(init){hiddencolumns.push({field:columsDetail.field,hidden:columsDetail.hidden,visible:(columsDetail.hidden==true?false:true)});}else{");
    sb.append("for(var j=0;j<cols.length;j++){");
    sb.append("		if(cols[j].field==columsDetail.field){");
    sb.append("					hiddencolumns.push({field:columsDetail.field,hidden:columsDetail.hidden,visible:cols[j].visible});");
    sb.append("		}");
    sb.append("}");
    sb.append("}} }");
    sb.append("storage.set( '" + name + "hiddenColumns',JSON.stringify(hiddencolumns));");
    sb.append("}");
    sb.append("function restoreheader(){");
    sb.append("var cols = storage.get( '" + name + "hiddenColumns');if(!cols)return;");
    sb.append("for(var i=0;i<cols.length;i++){");
    sb.append("	try{");
    sb.append("if(cols.visible!=false)$('#" + name + "').datagrid((cols[i].hidden==true?'hideColumn':'showColumn'),cols[i].field);");
    sb.append("}catch(e){");
    sb.append("}");
    sb.append("}");
    sb.append("}");
    sb.append("function resetheader(){");
    sb.append("var cols = storage.get( '" + name + "hiddenColumns');if(!cols)return;");
    sb.append("for(var i=0;i<cols.length;i++){");
    sb.append("	try{");
    sb.append("  $('#" + name + "').datagrid((cols.visible==false?'hideColumn':'showColumn'),cols[i].field);");
    sb.append("}catch(e){");
    sb.append("}");
    sb.append("}");
    sb.append("}");
    if (columnList.size() > 0) {
      sb.append("function " + name + "search(){");
      sb.append("var queryParams=$(\'#" + name + "\').datagrid('options').queryParams;");
      sb.append("$(\'#" + name + "tb\').find('*').each(function(){queryParams[$(this).attr('name')]=$(this).val();});");
      sb.append("$(\'#" + name + "\')." + grid + "({url:'" + actionUrl + "&field=" + searchFields + "',pageNumber:1});" + "}");

      //高级查询执行方法
      sb.append("function dosearch(params){");
      sb.append("var jsonparams=$.parseJSON(params);");
      sb.append("$(\'#" + name + "\')." + grid + "({url:'" + actionUrl + "&field=" + searchFields + "',queryParams:jsonparams});" + "}");
      //searchbox框执行方法
      searchboxFun(sb, grid);
      //回车事件
      sb.append("function EnterPress(e){");
      sb.append("var e = e || window.event;");
      sb.append("if(e.keyCode == 13){ ");
      sb.append(name + "search();");
      sb.append("}}");

      sb.append("function " + name + "searchReset(name){");
      sb.append(" $(\"#" + name + "tb\").find(\":input\").val(\"\");");
      if(isSon) {
        sb.append(" $(\"#sonId\").combotree('clear');");
      }
      String func = name.trim() + "search();";
      sb.append(func);
      sb.append("}");
    }
    sb.append("</script>");
    sb.append("<table width=\"100%\"   id=\"" + name + "\" toolbar=\"#" + name + "tb\"></table>");
    sb.append("<div id=\"" + name + "tb\" style=\"padding:3px; height: auto\">");
    if (hasQueryColum(columnList)) {
      sb.append("<div name=\"searchColums\">");
      sb.append("<input  id=\"selInfo\" name=\"selInfo\" value=\"v\"  type=\"hidden\" />");
      sb.append("<input  id=\"_sqlbuilder\" name=\"sqlbuilder\"   type=\"hidden\" />");
      //如果表单是组合查询
      if ("group".equals(getQueryMode())) {
        for (DataGridColumn col : columnList) {
          if (col.isQuery()) {
            sb.append("<span style=\"display:-moz-inline-box;display:inline-block;\">");
            sb.append("<span style=\"vertical-align:middle;display:-moz-inline-box;display:inline-block;width: 80px;text-align:right;text-overflow:ellipsis;-o-text-overflow:ellipsis; overflow: hidden;white-space:nowrap; \" title=\"" + col.getTitle() + "\">" + col.getTitle() + "：</span>");
            if ("single".equals(col.getQueryMode())) {
              if (!StringUtil.isEmpty(col.getReplace())) {
                sb.append("<select name=\"" + col.getField().replaceAll("_", "\\.") + "\" WIDTH=\"100\" style=\"width: 104px\"> ");
                sb.append(StringUtil.replaceAll("<option value =\"\" >{0}</option>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.please.select")));
                String[] test = col.getReplace().split(",");
                String text = "";
                String value = "";


                for (String string : test) {
                  String lang_key = string.split("_")[0];
                  text = MutiLangUtil.getMutiLangInstance().getLang(lang_key);
                  value = string.split("_")[1];
                  sb.append("<option value =\"" + value + "\">" + text + "</option>");
                }
                sb.append("</select>");
              } else if (!StringUtil.isEmpty(col.getDictionary())) {
                if (col.getDictionary().contains(",")) {
                  String[] dic = col.getDictionary().split(",");
                  String sql = "";
                  if (dic.length >= 4) {
                    sql = "select " + dic[1] + " as field," + dic[2]
                            + " as text from " + dic[0] + " where " + dic[3];
                  } else {
                    sql = "select " + dic[1] + " as field," + dic[2]
                            + " as text from " + dic[0];
                  }
                  systemService = ApplicationContextUtil.getContext().getBean(
                          SystemService.class);
                  List<Map<String, Object>> list = systemService.findForJdbc(sql);
                  sb.append("<select name=\"" + col.getField().replaceAll("_", "\\.") + "\" WIDTH=\"100\" style=\"width: 104px\"> ");
                  sb.append(StringUtil.replaceAll("<option value =\"\" >{0}</option>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.please.select")));
                  for (Map<String, Object> map : list) {
                    sb.append(" <option value=\"" + map.get("field") + "\">");
                    sb.append(map.get("text"));
                    sb.append(" </option>");
                  }
                  sb.append("</select>");
                } else {
                  Map<String, List<TSType>> typedatas = TSTypegroup.allTypes;
                  List<TSType> types = typedatas.get(col.getDictionary().toLowerCase());
                  sb.append("<select name=\"" + col.getField().replaceAll("_", "\\.") + "\" WIDTH=\"100\" style=\"width: 104px\"> ");
                  sb.append(StringUtil.replaceAll("<option value =\"\" >{0}</option>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.please.select")));
                  for (TSType type : types) {
                    sb.append(" <option value=\"" + type.getTypecode() + "\">");
                    sb.append(MutiLangUtil.getMutiLangInstance().getLang(type.getTypename()));
                    sb.append(" </option>");
                  }
                  sb.append("</select>");
                }
              } else if (col.isAutocomplete()) {
                acList.add(col.getField());
                sb.append(getAutoSpan(col.getField().replaceAll("_", "\\."), extendAttribute(col.getExtend())));
              } else {
                sb.append("<input class=\"inputxt\" onkeypress=\"EnterPress(event)\" onkeydown=\"EnterPress()\"  type=\"text\" name=\"" + col.getField().replaceAll("_", "\\.") + "\"  " + extendAttribute(col.getExtend()) + "  />");
              }
            } else if ("group".equals(col.getQueryMode())) {
              sb.append("<input class=\"inputxt\" type=\"text\" name=\"" + col.getField() + "_begin\"   " + extendAttribute(col.getExtend()) + "/>");
              sb.append("<span style=\"display:-moz-inline-box;display:inline-block;width: 8px;text-align:right;\">~</span>");
              sb.append("<input class=\"inputxt\" type=\"text\" name=\"" + col.getField() + "_end\"   " + extendAttribute(col.getExtend()) + "/>");

            }
            sb.append("</span>");
          }
        }
        pageContext.getSession().removeAttribute("acList");
        pageContext.getSession().setAttribute("acList", acList);
      }
      sb.append("</div>");
    }
    if (toolBarList.size() == 0 && !hasQueryColum(columnList)) {
      sb.append("<div style=\"height:0px;\" >");
    } else {
      sb.append("<div style=\"height:30px;\" class=\"datagrid-toolbar\">");
    }
    sb.append("<span style=\"position:absolute;z-index:3000\" >");
    if (toolBarList.size() > 0) {
      for (DataGridUrl toolBar : toolBarList) {

        sb.append("<a href=\"#\" class=\"easyui-linkbutton\" plain=\"true\" icon=\"" + toolBar.getIcon() + "\" ");
        if (StringUtil.isNotEmpty(toolBar.getOnclick())) {
          sb.append("onclick=" + toolBar.getOnclick() + "");
        } else {
          sb.append("onclick=\"" + toolBar.getFunname() + "(");
          if (!toolBar.getFunname().equals("doSubmit")) {
            sb.append("\'" + toolBar.getTitle() + "\',");
          }
          String width = toolBar.getWidth().contains("%") ? "'" + toolBar.getWidth() + "'" : toolBar.getWidth();
          String height = toolBar.getHeight().contains("%") ? "'" + toolBar.getHeight() + "'" : toolBar.getHeight();
          sb.append("\'" + toolBar.getUrl() + "\',\'" + name + "\'," + width + "," + height + ",\'" + toolBar.getWindowType() + "\')\"");
        }
        sb.append(">" + toolBar.getTitle() + "</a>");
      }
    }
    sb.append("</span>");
    if ("group".equals(getQueryMode()) && hasQueryColum(columnList)) {//如果表单是组合查询
      sb.append("<span style=\"position:absolute;top:23px;right:4px;\">");
//			sb.append("<a href=\"#\" class=\"easyui-linkbutton\" iconCls=\"icon-query\" onclick=\""+  name+ StringUtil.replaceAll("search()\">{0}</a>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.query")));
//			sb.append("<a href=\"#\" class=\"easyui-linkbutton\" iconCls=\"icon-reset\" onclick=\"searchReset('"+name+ StringUtil.replaceAll("')\">{0}</a>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.reset")) );
      sb.append("<a href=\"#\" class=\"qh-button icon-query\" onclick=\"" + name + "search()\"></a>");
      sb.append("<a href=\"#\" class=\"qh-button icon-reset\" onclick=\"" + name + "searchReset('" + name + "')\"></a>");
      if (queryBuilder) {
        sb.append("<a href=\"#\" class=\"button icon-query\" onclick=\"queryBuilder('" + StringUtil.replaceAll("')\">{0}</a>", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.querybuilder")));
      }
      sb.append("</span>");
    } else if ("single".equals(getQueryMode()) && hasQueryColum(columnList)) {//如果表单是单查询
      sb.append("<span style=\"float:right\">");
      sb.append("<input id=\"" + name + "searchbox\" class=\"easyui-searchbox\"  data-options=\"searcher:" + name + StringUtil.replaceAll("searchbox,prompt:\'{0}\',menu:\'#", "{0}", MutiLangUtil.getMutiLangInstance().getLang("common.please.input.keyword")) + name + "mm\'\"></input>");
      sb.append("<div id=\"" + name + "mm\" style=\"width:120px\">");
      for (DataGridColumn col : columnList) {
        if (col.isQuery()) {
          sb.append("<div data-options=\"name:\'" + col.getField().replaceAll("_", "\\.") + "\',iconCls:\'icon-ok\' " + extendAttribute(col.getExtend()) + " \">" + col.getTitle() + "</div>  ");
        }
      }
      sb.append("</div>");
      sb.append("</span>");
    }
    sb.append("</div>");
    if (queryBuilder) {
      addQueryBuilder(sb, "button");
    }
    return sb;
  }


  //-----author:jg_longjb----start-----date:20150427--------for:新增高级查询器queryBuilder
  private void appendLine(StringBuffer sb, String str) {
    String format = "\r\n"; //调试  格式化
    sb.append(str).append(format);
  }

  /**
   * TODO 语言做成多语翻译，保留历史查询记录
   *
   * @param sb
   */
  private void addQueryBuilder(StringBuffer sb, String buttonSytle) {

    appendLine(sb, "<div style=\"position:relative;overflow:auto;\">");
    appendLine(sb, "<div id=\"" + name + "_qbwin\" class=\"easyui-window\" data-options=\"closed:true,title:'高级查询构造器'\" style=\"width:600px;height:370px;padding:0px\">");
    appendLine(sb, "	<div class=\"easyui-layout\" data-options=\"fit:true\">");
    appendLine(sb, "		<div data-options=\"region:'east',split:true\" style=\"width:130px\"><div class=\"easyui-accordion\" style=\"width:120px;height:300px;\">");
    appendLine(sb, "<div title=\"查询历史\" data-options=\"iconCls:'icon-search'\" style=\"padding:0px;\">");
    appendLine(
            sb,
            "	<ul id=\""
                    + name
                    + "tt\" class=\"easyui-tree\" data-options=\"onClick:function(node){");
    appendLine(sb, "historyQuery( node.id);  ");
    appendLine(sb, "},ondbClick: function(node){");
    appendLine(sb, "$(this).tree('beginEdit',node.target);");
    appendLine(sb, "},onContextMenu: function(e,node){");
    appendLine(sb, "		e.preventDefault();");
    appendLine(sb, "		$(this).tree('select',node.target);");
    appendLine(sb, "		$('#" + name + "mmTree').menu('show',{");
    appendLine(sb, "			left: e.pageX,");
    appendLine(sb, "			top: e.pageY");
    appendLine(sb, "		});");
    appendLine(sb, "	},  ");
    appendLine(sb, " onAfterEdit:function(node){  ");
    appendLine(sb,
            "    if(node.text!=''){ " + name + "his[node.id].name=node.text; saveHistory();}	}");
    appendLine(sb, "\">");
    appendLine(sb, "	</ul>");
    appendLine(sb, "</div>");
    appendLine(sb, "</div></div>");
    appendLine(sb, "		<div data-options=\"region:'center'\" style=\"padding:0px;\">");
    appendLine(sb, "			<table id=\"" + name + "tg\" class=\"easyui-treegrid\" title=\"查询条件编辑\" style=\"width:450px;height:300px;\"");
    appendLine(sb, "		data-options=\"");
    appendLine(sb, "			iconCls: 'icon-ok',");
    appendLine(sb, "			rownumbers: true,");
    appendLine(sb, "			animate: true,");
    appendLine(sb, "			fitColumns: true,");
    appendLine(sb, "			//url: 'sqlbuilder.json',//可以预加载条件\r\n");
    appendLine(sb, "			method: 'get',");
    appendLine(sb, "			idField: 'id',");
    appendLine(sb, "autoEditing: true,  ");
    appendLine(sb, "extEditing: false, ");
    appendLine(sb, "singleEditing: false ,");
    appendLine(sb, "			treeField: 'field',toolbar:toolbar,onContextMenu: onContextMenu");
    appendLine(sb, "		\">");
    appendLine(sb, "<thead>");
    appendLine(sb, "	<tr>");
    sb
            .append("	<th data-options=\"field:'relation',width:18,formatter:function(value,row){");
    appendLine(sb, "				return value=='and'?'并且':'或者';");
    appendLine(sb, "			},editor:{");
    appendLine(sb, "				type:'combobox',");
    appendLine(sb, "				options:{");
    appendLine(sb, "				valueField:'relationId',");
    appendLine(sb, "						textField:'relationName',");
    appendLine(sb, "						data:  ");
    appendLine(sb, "						[  ");
    appendLine(sb, "						{'relationId':'and','relationName':'并且'},  ");
    appendLine(sb, "						{'relationId':'or','relationName':'或者'}  ");
    appendLine(sb, "						],  ");
    appendLine(sb, "						required:true");
    appendLine(sb, "					}}\">关系</th>");
    sb
            .append("		<th data-options=\"field:'field',width:30,formatter:function(value,row){");
    appendLine(sb, "			var data= ");
    StringBuffer fieldArray = new StringBuffer();
    fieldArray.append("	[  ");
    for (int i = 0; i < columnList.size(); i++) {
      DataGridColumn col = columnList.get(i);
      if ("opt".equals(col.getField())) continue;//忽略操作虚拟字段
      fieldArray.append("	{'fieldId':'" + getDBFieldName(col.getField()) + "','fieldName':'" + col.getTitle() + "'}");
      if (i < columnList.size() - 1) {
        fieldArray.append(",");
      }
    }
//		appendLine(sb,"				{'fieldId':'office_Phone','fieldName':'办公电话'},");
    fieldArray.append("]");
    sb.append(fieldArray).append(";");
    appendLine(sb, "for(var i=0;i<data.length;i++){");
    appendLine(sb, "if(value == data[i]['fieldId']){");
    appendLine(sb, "return data[i]['fieldName'];");
    appendLine(sb, "}");
    appendLine(sb, "}");
    appendLine(sb, "return value;");
    appendLine(sb, "},editor:{");
    appendLine(sb, "type:'combobox',");
    appendLine(sb, "	options:{");
    appendLine(sb, "valueField:'fieldId',");
    appendLine(sb, "textField:'fieldName',");
    appendLine(sb, "data:  ");
    sb.append(fieldArray);
    appendLine(sb, " , ");
    appendLine(sb, "							required:true");
    appendLine(sb, "				}}\">字段</th>");
    sb
            .append("<th data-options=\"field:'condition',width:20,align:'right',formatter:function(value,row){");
    appendLine(sb, "							var data=  ");
    appendLine(sb, "					[  ");
    Map<String, List<TSType>> typedatas = TSTypegroup.allTypes;
    List<TSType> types = typedatas.get("rulecon");
    if (types != null) {
      for (int i = 0; i < types.size(); i++) {
        TSType type = types.get(i);
        appendLine(sb, " {'conditionId':'" + type.getTypecode() + "','conditionName':'"
                + MutiLangUtil.getMutiLangInstance().getLang(type.getTypename()) + "'}");
        if (i < types.size() - 1) {
          appendLine(sb, ",");
        }
      }
    }
    appendLine(sb, "];");
    appendLine(sb, "	for(var i=0;i<data.length;i++){");
    appendLine(sb, "			if(value == data[i]['conditionId']){");
    appendLine(sb, "			return data[i]['conditionName'];");
    appendLine(sb, "			}");
    appendLine(sb, "		}");
    appendLine(sb, "		return value;");
    appendLine(sb, "		},editor:{");
    appendLine(sb, "		type:'combobox',");
    appendLine(sb, "		options:{");
    appendLine(sb, "			valueField:'conditionId',");
    appendLine(sb, "				textField:'conditionName',	");
    appendLine(sb, "			data:  ");
    appendLine(sb, "[");
    if (types != null) {
      for (int i = 0; i < types.size(); i++) {
        TSType type = types.get(i);
        appendLine(sb, " {'conditionId':'" + type.getTypecode() + "','conditionName':'"
                + MutiLangUtil.getMutiLangInstance().getLang(type.getTypename()) + "'}");
        if (i < types.size() - 1) {
          appendLine(sb, ",");
        }
      }
    }
    appendLine(sb, "				],  ");
    appendLine(sb, "				required:true");
    appendLine(sb, "			}}\">条件</th>");
    sb
            .append("	<th data-options=\"field:'value',width:30,editor:'text'\">值</th>");
    appendLine(sb, "<th data-options=\"field:'opt',width:30,formatter:function(value,row){");
    appendLine(sb, "	return '<a  onclick=\\'removeIt('+row.id+')\\' >删除</a>';}\">操作</th>");
    appendLine(sb, "		</tr>");
    appendLine(sb, "	</thead>");
    appendLine(sb, "	</table>");
    appendLine(sb, "</div>");
    appendLine(sb, "<div data-options=\"region:'south',border:false\" style=\"text-align:right;padding:5px 0 0;\">");
    appendLine(sb, "<a class=\"" + buttonSytle + "\" data-options=\"iconCls:'icon-ok'\" href=\"javascript:void(0)\" onclick=\"javascript:queryBuilderSearch()\">确定</a>");
    appendLine(sb, "<a class=\"" + buttonSytle + "\" data-options=\"iconCls:'icon-cancel'\" href=\"javascript:void(0)\" onclick=\"javascript:$('#" + name + "_qbwin').window('close')\">取消</a>");
    appendLine(sb, "		</div>");
    appendLine(sb, "	</div>	");
    appendLine(sb, "</div>		");
    appendLine(sb, "</div>");
    appendLine(sb, "<div id=\"mm\" class=\"easyui-menu\" style=\"width:120px;\">");
    appendLine(sb, "	<div onclick=\"append()\" data-options=\"iconCls:'icon-add'\">添加</div>");
    appendLine(sb, "	<div onclick=\"edit()\" data-options=\"iconCls:'icon-edit'\">编辑</div>");
    appendLine(sb, "	<div onclick=\"save()\" data-options=\"iconCls:'icon-save'\">保存</div>");
    appendLine(sb, "	<div onclick=\"removeIt()\" data-options=\"iconCls:'icon-remove'\">删除</div>");
    appendLine(sb, "	<div class=\"menu-sep\"></div>");
    appendLine(sb, "	<div onclick=\"cancel()\">取消</div>");
    appendLine(sb, "<div onclick=\"expand()\">Expand</div>");
    appendLine(sb, "</div><div id=\"" + name + "mmTree\" class=\"easyui-menu\" style=\"width:100px;\">");
    appendLine(sb, "<div onclick=\"editTree()\" data-options=\"iconCls:'icon-edit'\">编辑</div>");
    appendLine(sb, "<div onclick=\"deleteTree()\" data-options=\"iconCls:'icon-remove'\">删除</div></div>");
    //已在baseTag中引入
//		appendLine(sb,"<script type=\"text/javascript\" src=\"plug-in/jquery/jquery.cookie.js\" ></script>");
//		appendLine(sb,"<script type=\"text/javascript\" src=\"plug-in/jquery-plugs/storage/jquery.storageapi.min.js\" ></script>");
    appendLine(sb, "<script type=\"text/javascript\">");
    appendLine(sb, "var toolbar = [{");
    appendLine(sb, "	text:'',");
    appendLine(sb, "	iconCls:'icon-add',");
    appendLine(sb, "	handler:function(){append();}");
    appendLine(sb, "},{");
    appendLine(sb, "	text:'',");
    appendLine(sb, "	iconCls:'icon-edit',");
    appendLine(sb, "	handler:function(){edit();}");
    appendLine(sb, "},{");
    appendLine(sb, "	text:'',");
    appendLine(sb, "	iconCls:'icon-remove',");
    appendLine(sb, "	handler:function(){removeIt();}");
    appendLine(sb, "},'-',{");
    appendLine(sb, "	text:'',");
    appendLine(sb, "	iconCls:'icon-save',");
    appendLine(sb, "	handler:function(){save();}");
    appendLine(sb, "	}];");
    appendLine(sb, "function onContextMenu(e,row){");
    appendLine(sb, "	e.preventDefault();");
    appendLine(sb, "	$(this).treegrid('select', row.id);");
    appendLine(sb, "	$('#mm').menu('show',{");
    appendLine(sb, "		left: e.pageX,");
    appendLine(sb, "		top: e.pageY");
    appendLine(sb, "	});");
    appendLine(sb, "}");
    appendLine(sb, "	var idIndex = 100;");
    appendLine(sb, "function append(){");
    appendLine(sb, "	idIndex++;");
    appendLine(sb, "	var node = $('#" + name + "tg').treegrid('getSelected');");
    appendLine(sb, "	$('#" + name + "tg').treegrid('append',{");
    appendLine(sb, "		data: [{");
    appendLine(sb, "			id: idIndex,");
    appendLine(sb, "			field: '',");
    appendLine(sb, "		condition:'like',");
    appendLine(sb, "		value: '%a%',");
    appendLine(sb, "		relation: 'and'");
    appendLine(sb, "				}]");
    appendLine(sb, "});$('#" + name + "tg').datagrid('beginEdit',idIndex);");
    appendLine(sb, "}");
    appendLine(sb, "		function removeIt(id){");
    appendLine(sb, "var node = $('#" + name + "tg').treegrid('getSelected');");
    appendLine(sb, "if(id){");
    appendLine(sb, "$('#" + name + "tg').treegrid('remove', id);");
    appendLine(sb, "}else if(node){	$('#" + name + "tg').treegrid('remove', node.id);");
    appendLine(sb, "}");
    appendLine(sb, "}");
    appendLine(sb, "function collapse(){");
    appendLine(sb, "	var node = $('#" + name + "tg').treegrid('getSelected');");
    appendLine(sb, "if(node){");
    appendLine(sb, "	$('#" + name + "tg').treegrid('collapse', node.id);");
    appendLine(sb, "}");
    appendLine(sb, "}");
    appendLine(sb, "function expand(){");
    appendLine(sb, "var node = $('#" + name + "tg').treegrid('getSelected');");
    appendLine(sb, "if(node){");
    appendLine(sb, "	$('#" + name + "tg').treegrid('expand', node.id);");
    appendLine(sb, "}");
    appendLine(sb, "}");
    appendLine(sb, "var editingId;");
    appendLine(sb, "function edit(id){");
    appendLine(sb, "var row = $('#" + name + "tg').treegrid('getSelected');");
    appendLine(sb, "if(id){	$('#" + name + "tg').treegrid('beginEdit', id);}else if(row){");
    appendLine(sb, "	$('#" + name + "tg').treegrid('beginEdit', row.id);");
    appendLine(sb, "}");
    appendLine(sb, "}");
    appendLine(sb, "function save(){");
    appendLine(sb, "	var t = $('#" + name + "tg');");
    appendLine(sb, "	var nodes = t.treegrid('getRoots');");
    appendLine(sb, "	for (var i = 0; i < nodes.length; i++) {");
    appendLine(sb, "	t.treegrid('endEdit',nodes[i].id);}");
    appendLine(sb, "	}");
    appendLine(sb, "function cancel(){");
    appendLine(sb, "	var t = $('#" + name + "tg');");
    appendLine(sb, "var nodes = t.treegrid('getRoots');for (var i = 0; i < nodes.length; i++) {t.treegrid('cancelEdit',nodes[i].id);}");
    appendLine(sb, "}");
    appendLine(sb, "var " + name + "his=new Array();");
    appendLine(sb, " function historyQuery(index) {");
    appendLine(sb, "	  var data  = { rows:JSON.parse(" + name + "his[index].json)};  ");
    appendLine(sb, "	    var t = $('#" + name + "tg');");
    appendLine(sb, "		var data = t.treegrid('loadData',data);");
    appendLine(sb, "		$('#_sqlbuilder').val( " + name + "his[index].json);   ");
    appendLine(sb, "		" + name + "search();");
    appendLine(sb, "	}");
    appendLine(sb, "function view(){");
    appendLine(sb, "save();");
    appendLine(sb, "var t = $('#" + name + "tg');");
    appendLine(sb, "var data = t.treegrid('getData');");
    appendLine(sb, "return   JSON.stringify(data) ;");
    appendLine(sb, "}");
    appendLine(sb, "	 function queryBuilder() {");
    appendLine(sb, "	$('#" + name + "_qbwin').window('open');");
    appendLine(sb, "}");

    appendLine(sb, "function queryBuilderSearch() {");
    appendLine(sb, "         var json =  view();");
    appendLine(sb, "	$('#_sqlbuilder').val(json);  ");
    appendLine(sb, "	var isnew=true;");
    appendLine(sb, "for(var i=0;i< " + name + "his.length;i++){");
    appendLine(sb, "	if(" + name + "his[i]&&" + name + "his[i].json==json){");
    appendLine(sb, "		isnew=false;");
    appendLine(sb, "	}");
    appendLine(sb, "}");
    appendLine(sb, "if(isnew){");
    appendLine(sb, " " + name + "his.push({name:'Query'+" + name + "his.length,json:json});saveHistory();");
    appendLine(sb, "var name= 'Query'+( " + name + "his.length-1);");
    appendLine(sb, "	var name= 'Query'+(" + name + "his.length-1);");
    appendLine(sb, "appendTree(" + name + "his.length-1,name);");
    appendLine(sb, "}");
    appendLine(sb, "	" + name + "search();");
    appendLine(sb, " }");
    appendLine(sb, " $(document).ready(function(){ ");
    appendLine(sb, " storage=$.localStorage;if(!storage)storage=$.cookieStorage;");
    appendLine(sb, "	var _qhistory = storage.get('" + name + "_query_history');");
    appendLine(sb, " if(_qhistory){");
    appendLine(sb, " " + name + "his=_qhistory;");
    // appendLine(sb, " 	var data  = { rows:his[0]};");
    appendLine(sb, " 	for(var i=0;i< " + name + "his.length;i++){");
    appendLine(sb, " 		if(" + name + "his[i])appendTree(i," + name + "his[i].name);");
    appendLine(sb, " 	}restoreheader();");
    appendLine(sb, " }});");
    appendLine(sb, "function saveHistory(){");
    appendLine(sb, "	var history=new Array();");
    appendLine(sb, "	for(var i=0;i<" + name + "his.length;i++){");
    appendLine(sb, "		if(" + name + "his[i]){");
    appendLine(sb, "			history.push(" + name + "his[i]);");
    appendLine(sb, "		}");
    appendLine(sb, "	}");
    appendLine(sb, "	storage.set( '" + name + "_query_history',JSON.stringify(history));");
    appendLine(sb, "}");
    appendLine(sb, "function deleteTree(){");
    appendLine(sb, "	var tree = $('#" + name
            + "tt');var node= tree.tree('getSelected');");
    appendLine(sb, "	" + name + "his[node.id]=null;saveHistory();");
    appendLine(sb, "	tree.tree('remove', node.target);");
    appendLine(sb, "}");
    appendLine(sb, "function editTree(){");
    appendLine(sb, "	var node = $('#" + name + "tt').tree('getSelected');");
    appendLine(sb, "	$('#" + name + "tt').tree('beginEdit',node.target);");
    appendLine(sb, "	saveHistory();");
    appendLine(sb, "}");
    appendLine(sb, "function appendTree(id,name){");
    appendLine(sb, "	$('#" + name + "tt').tree('append',{");
    appendLine(sb, "	data:[{");
    appendLine(sb, "id : id,");
    appendLine(sb, "text :name");
    appendLine(sb, "	}]");
    appendLine(sb, "});");
    appendLine(sb, "}");


    appendLine(sb, "</script>");
  }

  /**
   * hibernate字段名转换为数据库名称，只支持标准命名
   * 否则转换错误
   *
   * @param fieldName
   * @return
   */
  String getDBFieldName(String fieldName) {
    StringBuffer sb = new StringBuffer();
    for (int i = 0; i < fieldName.length(); i++) {
      char c = fieldName.charAt(i);
      if (c <= 'Z' && c >= 'A') {
        sb.append("_").append((char) ((int) c + 32));
      } else {
        sb.append(c);
      }
    }
    return sb.toString();
  }

  public boolean isCheckOnSelect() {
    return checkOnSelect;
  }

  public void setCheckOnSelect(boolean checkOnSelect) {
    this.checkOnSelect = checkOnSelect;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public boolean isQueryBuilder() {
    return queryBuilder;
  }

  public void setQueryBuilder(boolean queryBulder) {
    this.queryBuilder = queryBulder;
  }
//----author:jg_longjb----start-----date:20150427--------for:新增封装查询器组件----

  /**
   * 单据是否启用账套状态检查，未设置值（默认为0)即为不需要当前账套检查，值为1表示如果当前账套已开账未结账才允许单据操作，值为2表示当前账套未开账才允许单据操作（用于初始化的几种单据）
   *   根据当前账套的开启和结账状态来产生录入、编辑、作废、反作废、复制、导入、审核、反审核按钮不可操作的js脚本串
   * @return
   * @author hjh 2016-09-08
   */
  private String getBeforeAccountFunction() {
    StringBuffer nsb = new StringBuffer();
    boolean isDisable = false;
    if (this.beforeAccount!=null && !this.beforeAccount.equals("")) {
      boolean isOpen = AccountUtil.isAccountOpen();
      boolean isClose = AccountUtil.isAccountClose();
      if (this.beforeAccount.toLowerCase().equals("true")) {//普通单据
        if (isOpen == false || isClose) {//如果未开账，或已经结账都不允许单据操作
          isDisable = true;
        }
      } else if (this.beforeAccount.toLowerCase().equals("init")) {//初始化单据
        if (isOpen) {//如果已开账单据操作
          isDisable = true;
        }
      }
      if (isDisable){//不允许相关管理操作
        nsb.append("$(document).ready(function() {\n");
        nsb.append("  $(\"[icon=icon-add]\").linkbutton(\"disable\");\n"); //旧版录入
        nsb.append("  $(\"[icon=icon-edit]\").linkbutton(\"disable\");\n"); //旧版编辑
        nsb.append("  $(\"[icon=new-icon-add]\").linkbutton(\"disable\");\n"); //新版录入
        nsb.append("  $(\"[icon=new-icon-edit]\").linkbutton(\"disable\");\n"); //新版编辑
        nsb.append("  $(\"[icon=new-icon-cancellation]\").linkbutton(\"disable\");\n"); //作废
        nsb.append("  $(\"[icon=new-icon-uncancellation]\").linkbutton(\"disable\");\n"); //反作废
        nsb.append("  $(\"[icon=new-icon-audit]\").linkbutton(\"disable\");\n"); //审核
        nsb.append("  $(\"[icon=new-icon-unAudit]\").linkbutton(\"disable\");\n"); //反审核
        nsb.append("  $(\"[icon=icon-put]\").linkbutton(\"disable\");\n"); //旧版导入
        nsb.append("  $(\"[icon=new-icon-put]\").linkbutton(\"disable\");\n"); //新版导入
        nsb.append("  $(\"[icon=new-icon-copy]\").linkbutton(\"disable\");\n"); //复制
        nsb.append("  $(\"[icon=new-icon-chkstockbill]\").linkbutton(\"disable\");\n"); //自动盘点
        nsb.append("  $(\"[icon=new-icon-close]\").linkbutton(\"disable\");\n"); //关闭
        nsb.append("  $(\"[icon=new-icon-unclose]\").linkbutton(\"disable\");\n"); //反关闭
        nsb.append("  $(\"[icon=new-icon-syn]\").linkbutton(\"disable\");\n"); //同步订单
        nsb.append("});\n");
      }
    }
    return nsb.toString();
  }

  /**
   * 单据是否启用账套状态检查，未设置值（默认为0)即为不需要当前账套检查，值为1表示如果当前账套已开账未结账才允许单据操作，值为2表示当前账套未开账才允许单据操作（用于初始化的几种单据）
   * 根据当前账套的开启和结账状态来产生一个id为隐藏域，值为1表示不可操作；0表示可操作
   * @return
   * @author hjh 2016-09-08
     */
  private String getBeforeAccountInput(){
    StringBuffer nsb = new StringBuffer();
    int isAccount = 0;
    boolean isDisable = false;
    if (this.beforeAccount!=null && !this.beforeAccount.equals("")) {
      boolean isOpen = AccountUtil.isAccountOpen();
      boolean isClose = AccountUtil.isAccountClose();
      if (this.beforeAccount.toLowerCase().equals("true")) {//普通单据
        if (isOpen == false || isClose) {//如果未开账，或已经结账都不允许单据操作
          isDisable = true;
        }
      } else if (this.beforeAccount.toLowerCase().equals("init")) {//初始化单据
        if (isOpen) {//如果已开账单据操作
          isDisable = true;
        }
      }
      if (isDisable) {//不允许相关管理操作
        isAccount = 1;
      }
    }
    nsb.append("<input id=\"accountIsAllowBillManage\" value=\"" + isAccount + "\" type=\"hidden\">\n");
    return nsb.toString();
  }

  /**
   * 产生合并行的两个function函数脚本:getTableColList、mergeCellsByField
   * @return
   * @author hjh 2016-09-08
     */
  private String getMergeCellsByFieldFunction(){
    StringBuffer sb = new StringBuffer();
    //获得需要合并行的字段逗号间隔串
    sb.append("function getTableColList(tableID){\n");
    sb.append(" var colList = '';\n");
    sb.append(" var tTable = $('#' + tableID);\n");
    sb.append(" var fields = $(tTable).datagrid('getColumnFields');\n");
    sb.append(" for (var i=0;i<fields.length;i++){\n");
    sb.append("    var fieldoption = $(tTable).datagrid('getColumnOption',fields[i]);\n");
    sb.append("    var mer = fieldoption.mergeCells;\n");
    sb.append("    if (mer==true){colList += ',' + fields[i];}\n");
    sb.append(" }\n");
    sb.append(" if (colList!=''){colList = 'ck' + colList;}\n");
    sb.append(" return colList;\n");
    sb.append("}\n");
    //合并行操作
    sb.append("function mergeCellsByField(tableID, colList,id){\n");
    sb.append(" var ColArray = colList.split(',');\n");
    sb.append(" var tTable = $('#' + tableID); \n");
    sb.append(" var TableRowCnts = tTable.datagrid('getRows').length;\n");
    sb.append(" var tmpA;\n");
    sb.append(" var PerTxt = '';\n");
    sb.append(" var CurTxt = '';\n");
    sb.append(" var PerId = '';\n");
    sb.append(" var CurId = '';\n");
    sb.append(" for (var j = ColArray.length - 1; j >= 0; j--) {\n");
    sb.append("   PerTxt = '';\n");
    sb.append("   PerId = '';\n");
    sb.append("   tmpA = 1;\n");
    sb.append("   for (var i = 0; i <= TableRowCnts; i++) {\n");
    sb.append("      if (i == TableRowCnts) {\n");
    sb.append("         CurTxt = '';\n");
    sb.append("         CurId = '';\n");
    sb.append("      }else{\n");
    sb.append("         CurTxt = tTable.datagrid('getRows')[i][ColArray[j]];\n");
    sb.append("         CurId = tTable.datagrid('getRows')[i][id];\n");
    sb.append("      }\n");
    sb.append("      if ((PerTxt == CurTxt)&&(PerId==CurId)) {\n");
    sb.append("         tmpA += 1;\n");
    sb.append("      }else{\n");
    sb.append("         var index =  i - tmpA;\n");
    sb.append("         tTable.datagrid('mergeCells', {\n");
    sb.append("             index: index,\n");
    sb.append("             field: ColArray[j],\n");
    sb.append("             rowspan: tmpA,\n");
    sb.append("             colspan: null\n");
    sb.append("        });\n");
    sb.append("        tmpA = 1;\n");
    sb.append("      }\n");
    sb.append("      PerTxt = CurTxt;\n");
    sb.append("      PerId = CurId;\n");
    sb.append("   }\n");
    sb.append(" }\n");
    sb.append("}\n");
    return sb.toString();
  }

  public boolean isScrollview() {
    return scrollview;
  }

  public void setScrollview(boolean scrollview) {
    this.scrollview = scrollview;
  }

  public String getExcludeCol() {
    return excludeCol;
  }

  public void setExcludeCol(String excludeCol) {
    this.excludeCol = excludeCol;
  }
}