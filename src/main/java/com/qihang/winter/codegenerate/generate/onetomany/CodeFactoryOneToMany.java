package com.qihang.winter.codegenerate.generate.onetomany;

import com.qihang.winter.codegenerate.generate.BaseCodeFactory;
import com.qihang.winter.codegenerate.util.CodeStringUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CodeFactoryOneToMany
        extends BaseCodeFactory {
  private com.qihang.winter.codegenerate.generate.ICallBack callBack;

  public static enum CodeType {
    serviceImpl("ServiceImpl"),
    dao("Dao"), service("ServiceI"), controller("Controller"), page("Page"), entity("Entity"), jsp(""), jspList("List"), jspSubList("SubList");

    private String type;

    private CodeType(String type) {
      this.type = type;
    }

    public String getValue() {
      return this.type;
    }
  }


  public void generateFile(String templateFileName, String type, Map data) {
    try {
      String entityPackage = data.get("entityPackage").toString();
      String entityName = data.get("entityName").toString();
      String fileNamePath = getCodePath(type, entityPackage, entityName);
      String fileDir = StringUtils.substringBeforeLast(fileNamePath, "/");
      Template template = getConfiguration()
              .getTemplate(templateFileName);
      FileUtils.forceMkdir(new File(fileDir + "/"));
      Writer out = new OutputStreamWriter(new FileOutputStream(
              fileNamePath), com.qihang.winter.codegenerate.util.CodeResourceUtil.SYSTEM_ENCODING);
      template.process(data, out);
      out.close();
    } catch (TemplateException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }


  public String getProjectPath() {
    String path = System.getProperty("user.dir").replace("\\", "/") + "/";
    return path;
  }


  public String getClassPath() {
    String path = Thread.currentThread().getContextClassLoader().getResource("./").getPath();
    return path;
  }

  public static void main(String[] args) {
    System.out.println(Thread.currentThread().getContextClassLoader().getResource("./").getPath());
  }


  public String getTemplatePath() {
    String path = getClassPath() + com.qihang.winter.codegenerate.util.CodeResourceUtil.TEMPLATEPATH;
    return path;
  }


  public String getCodePath(String type, String entityPackage, String entityName) {
    String path = getProjectPath();
    StringBuilder str = new StringBuilder();
    if (StringUtils.isNotBlank(type)) {
      String codeType = ((CodeType) Enum.valueOf(CodeType.class, type)).getValue();
      str.append(path);
      if (("jsp".equals(type)) || ("jspList".equals(type))) {
        str.append(com.qihang.winter.codegenerate.util.CodeResourceUtil.JSPPATH);
      } else {
        str.append(com.qihang.winter.codegenerate.util.CodeResourceUtil.CODEPATH);
      }
      if ("Action".equalsIgnoreCase(codeType)) {
        str.append(StringUtils.lowerCase("action"));
      } else if ("ServiceImpl".equalsIgnoreCase(codeType)) {
        str.append(StringUtils.lowerCase("service/impl"));
      } else if ("ServiceI".equalsIgnoreCase(codeType)) {
        str.append(StringUtils.lowerCase("service"));
      } else if (!"List".equalsIgnoreCase(codeType)) {

        str.append(StringUtils.lowerCase(codeType));
      }
      str.append("/");
      str.append(StringUtils.lowerCase(entityPackage));
      str.append("/");

      if (("jsp".equals(type)) || ("jspList".equals(type))) {
        String jspName = StringUtils.capitalize(entityName);

        str.append(CodeStringUtils.getInitialSmall(jspName));
        str.append(codeType);
        str.append(".jsp");
      } else {
        str.append(StringUtils.capitalize(entityName));
        str.append(codeType);
        str.append(".java");
      }
    } else {
      throw new IllegalArgumentException("type is null");
    }
    return str.toString();
  }

  public void invoke(String templateFileName, String type) {
    Map<String, Object> data = new HashMap();
    data = this.callBack.execute();
    generateFile(templateFileName, type, data);
  }

  public com.qihang.winter.codegenerate.generate.ICallBack getCallBack() {
    return this.callBack;
  }

  public void setCallBack(com.qihang.winter.codegenerate.generate.ICallBack callBack) {
    this.callBack = callBack;
  }
}
