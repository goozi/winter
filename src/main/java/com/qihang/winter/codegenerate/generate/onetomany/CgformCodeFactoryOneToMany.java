package com.qihang.winter.codegenerate.generate.onetomany;

import com.qihang.winter.codegenerate.generate.BaseCodeFactory;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class CgformCodeFactoryOneToMany
        extends BaseCodeFactory {
  private com.qihang.winter.codegenerate.generate.ICallBack callBack;
  private String projectPath;

  public void generateFile(String templateFileName, String type, Map data)
          throws TemplateException, IOException {
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
      throw e;
    } catch (IOException e) {
      e.printStackTrace();
      throw e;
    }
  }


  public String getProjectPath() {
    return this.projectPath;
  }


  public String getClassPath() {
    String path = getClass().getResource("/").getPath();
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
    String codePath = "";
    if ((this.packageStyle != null) && (com.qihang.winter.codegenerate.util.CodeResourceUtil.PACKAGE_SERVICE_STYLE.equals(this.packageStyle))) {
      codePath = getCodePathServiceStyle(path, type, entityPackage, entityName);
    } else {
      codePath = getCodePathProjectStyle(path, type, entityPackage, entityName);
    }
    return codePath;
  }

  public void invoke(String templateFileName, String type) throws TemplateException, IOException {
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

  public void setProjectPath(String projectPath) {
    this.projectPath = projectPath;
  }
}
