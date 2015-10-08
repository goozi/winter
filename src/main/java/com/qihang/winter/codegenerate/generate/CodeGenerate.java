package com.qihang.winter.codegenerate.generate;

import com.qihang.winter.codegenerate.database.JeecgReadTable;
import com.qihang.winter.codegenerate.pojo.Columnt;
import com.qihang.winter.codegenerate.pojo.CreateFileProperty;
import com.qihang.winter.codegenerate.util.NonceUtils;
import com.qihang.winter.codegenerate.util.def.FtlDef;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;


public class CodeGenerate
        implements ICallBack {
  private static final Log log = LogFactory.getLog(CodeGenerate.class);

  private static String entityPackage = "test";
  private static String entityName = "Person";
  private static String tableName = "person";
  private static String ftlDescription = "公告";
  private static String primaryKeyPolicy = "uuid";
  private static String sequenceCode = "";


  private static String[] foreignKeys;


  private List<Columnt> originalColumns = new ArrayList();
  public static int FIELD_ROW_NUM = 1;
  private static CreateFileProperty createFileProperty = new CreateFileProperty();

  static {
    createFileProperty.setActionFlag(true);
    createFileProperty.setServiceIFlag(true);
    createFileProperty.setJspFlag(true);
    createFileProperty.setServiceImplFlag(true);
    createFileProperty.setJspMode("01");
    createFileProperty.setPageFlag(true);
    createFileProperty.setEntityFlag(true);
  }


  public CodeGenerate() {
  }


  public CodeGenerate(String entityPackage, String entityName, String tableName, String ftlDescription, CreateFileProperty createFileProperty, int fieldRowNum, String primaryKeyPolicy, String sequenceCode) {
    entityName = entityName;
    entityPackage = entityPackage;
    tableName = tableName;
    ftlDescription = ftlDescription;
    createFileProperty = createFileProperty;
    FIELD_ROW_NUM = fieldRowNum;
    primaryKeyPolicy = primaryKeyPolicy;
    sequenceCode = sequenceCode;
  }

  public CodeGenerate(String entityPackage, String entityName, String tableName, String ftlDescription, CreateFileProperty createFileProperty, String primaryKeyPolicy, String sequenceCode) {
    entityName = entityName;
    entityPackage = entityPackage;
    tableName = tableName;
    ftlDescription = ftlDescription;
    createFileProperty = createFileProperty;
    primaryKeyPolicy = primaryKeyPolicy;
    sequenceCode = sequenceCode;
  }

  public CodeGenerate(String entityPackage, String entityName, String tableName, String ftlDescription, CreateFileProperty createFileProperty, String primaryKeyPolicy, String sequenceCode, String[] foreignKeys) {
    entityName = entityName;
    entityPackage = entityPackage;
    tableName = tableName;
    ftlDescription = ftlDescription;
    createFileProperty = createFileProperty;
    primaryKeyPolicy = primaryKeyPolicy;
    sequenceCode = sequenceCode;
    foreignKeys = foreignKeys;
  }


  private List<Columnt> columns = new ArrayList();
  private JeecgReadTable dbFiledUtil = new JeecgReadTable();


  public Map<String, Object> execute() {
    Map<String, Object> data = new HashMap();


    data.put("bussiPackage", com.qihang.winter.codegenerate.util.CodeResourceUtil.bussiPackage);

    data.put("entityPackage", entityPackage);

    data.put("entityName", entityName);

    data.put("tableName", tableName);

    data.put("ftl_description", ftlDescription);

    data.put(FtlDef.JEECG_TABLE_ID, com.qihang.winter.codegenerate.util.CodeResourceUtil.JEECG_GENERATE_TABLE_ID);

    data.put(FtlDef.JEECG_PRIMARY_KEY_POLICY, primaryKeyPolicy);
    data.put(FtlDef.JEECG_SEQUENCE_CODE, sequenceCode);

    data.put("ftl_create_time", com.qihang.winter.codegenerate.util.CodeDateUtils.dateToString(new Date()));

    data.put("foreignKeys", foreignKeys);


    data.put(FtlDef.FIELD_REQUIRED_NAME, Integer.valueOf(StringUtils.isNotEmpty(com.qihang.winter.codegenerate.util.CodeResourceUtil.JEECG_UI_FIELD_REQUIRED_NUM) ? Integer.parseInt(com.qihang.winter.codegenerate.util.CodeResourceUtil.JEECG_UI_FIELD_REQUIRED_NUM) : -1));

    data.put(FtlDef.SEARCH_FIELD_NUM, Integer.valueOf(StringUtils.isNotEmpty(com.qihang.winter.codegenerate.util.CodeResourceUtil.JEECG_UI_FIELD_SEARCH_NUM) ? Integer.parseInt(com.qihang.winter.codegenerate.util.CodeResourceUtil.JEECG_UI_FIELD_SEARCH_NUM) : -1));

    data.put(FtlDef.FIELD_ROW_NAME, Integer.valueOf(FIELD_ROW_NUM));

    try {
      this.columns = this.dbFiledUtil.readTableColumn(tableName);
      data.put("columns", this.columns);


      this.originalColumns = this.dbFiledUtil.readOriginalTableColumn(tableName);
      data.put("originalColumns", this.originalColumns);


      for (Columnt c : this.originalColumns) {
        if (c.getFieldName().toLowerCase().equals(com.qihang.winter.codegenerate.util.CodeResourceUtil.JEECG_GENERATE_TABLE_ID.toLowerCase())) {
          data.put("primary_key_type", c.getFieldType());
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
      System.exit(-1);
    }
    long serialVersionUID = NonceUtils.randomLong() +
            NonceUtils.currentMills();
    data.put("serialVersionUID", String.valueOf(serialVersionUID));
    return data;
  }

  public void generateToFile() {
    log.info("----jeecg---Code----Generation----[单表模型:" + tableName + "]------- 生成中。。。");

    CodeFactory codeFactory = new CodeFactory();
    codeFactory.setCallBack(new CodeGenerate());

    if (createFileProperty.isJspFlag()) {
      if ("03".equals(createFileProperty.getJspMode())) {
        codeFactory.invoke("onetomany/jspSubTemplate.ftl", "jspList");
      } else {
        if ("01".equals(createFileProperty.getJspMode())) {
          codeFactory.invoke("jspTableTemplate.ftl", "jsp");
        }
        if ("02".equals(createFileProperty.getJspMode())) {
          codeFactory.invoke("jspDivTemplate.ftl", "jsp");
        }
        codeFactory.invoke("jspListTemplate.ftl", "jspList");
      }
    }
    if (createFileProperty.isServiceImplFlag()) {
      codeFactory.invoke("serviceImplTemplate.ftl", "serviceImpl");
    }
    if (createFileProperty.isServiceIFlag()) {
      codeFactory.invoke("serviceITemplate.ftl", "service");
    }
    if (createFileProperty.isActionFlag()) {
      codeFactory.invoke("controllerTemplate.ftl", "controller");
    }
    if (createFileProperty.isEntityFlag()) {
      codeFactory.invoke("entityTemplate.ftl", "entity");
    }
    log.info("----jeecg----Code----Generation-----[单表模型：" + tableName + "]------ 生成完成。。。");
  }


  public static void main(String[] args) {
    System.out.println("----jeecg--------- Code------------- Generation -----[单表模型]------- 生成中。。。");
    new CodeGenerate().generateToFile();
    System.out.println("----jeecg--------- Code------------- Generation -----[单表模型]------- 生成完成。。。");
  }
}
