package com.qihang.winter.codegenerate.generate;

import com.qihang.winter.codegenerate.database.JeecgReadTable;
import com.qihang.winter.codegenerate.util.def.FtlDef;
import com.qihang.winter.web.cgform.entity.config.CgFormFieldEntity;
import com.qihang.winter.web.cgform.entity.generate.GenerateEntity;
import freemarker.template.TemplateException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.*;


public class CgformCodeGenerate
        implements ICallBack {
  private static final Log log = LogFactory.getLog(CgformCodeGenerate.class);

  private String entityPackage = "test";
  private String entityName = "Person";
  private String tableName = "person";
  private String ftlDescription = "公告";
  private String primaryKeyPolicy = "uuid";
  private String sequenceCode = "";
  private String[] foreignKeys;
  public static int FIELD_ROW_NUM = 1;

  private com.qihang.winter.codegenerate.pojo.onetomany.SubTableEntity sub;
  private GenerateEntity subG;
  private com.qihang.winter.codegenerate.pojo.CreateFileProperty subFileProperty;
  private String policy;
  private String[] array;
  private GenerateEntity cgformConfig;
  private static com.qihang.winter.codegenerate.pojo.CreateFileProperty createFileProperty = new com.qihang.winter.codegenerate.pojo.CreateFileProperty();

  static {
    createFileProperty.setActionFlag(true);
    createFileProperty.setServiceIFlag(true);
    createFileProperty.setJspFlag(true);
    createFileProperty.setServiceImplFlag(true);
    createFileProperty.setJspMode("01");
    createFileProperty.setPageFlag(true);
    createFileProperty.setEntityFlag(true);
  }


  public CgformCodeGenerate() {
  }


  public CgformCodeGenerate(com.qihang.winter.codegenerate.pojo.CreateFileProperty createFileProperty2, GenerateEntity generateEntity) {
    this.entityName = generateEntity.getEntityName();
    this.entityPackage = generateEntity.getEntityPackage();
    this.tableName = generateEntity.getTableName();
    this.ftlDescription = generateEntity.getFtlDescription();
    FIELD_ROW_NUM = 1;
    createFileProperty = createFileProperty2;
    createFileProperty.setJspMode("01");
    this.primaryKeyPolicy = generateEntity.getPrimaryKeyPolicy();
    this.sequenceCode = "";
    this.cgformConfig = generateEntity;
  }


  public CgformCodeGenerate(com.qihang.winter.codegenerate.pojo.onetomany.SubTableEntity sub, GenerateEntity subG, com.qihang.winter.codegenerate.pojo.CreateFileProperty subFileProperty, String policy, String[] array) {
    this.entityName = subG.getEntityName();
    this.entityPackage = subG.getEntityPackage();
    this.tableName = subG.getTableName();
    this.ftlDescription = subG.getFtlDescription();
    createFileProperty = subFileProperty;
    FIELD_ROW_NUM = 1;
    this.primaryKeyPolicy = policy;
    this.sequenceCode = "";
    this.cgformConfig = subG;
    this.foreignKeys = array;
    this.sub = sub;
    this.subG = subG;
    this.subFileProperty = subFileProperty;
    this.policy = policy;
  }


  public Map<String, Object> execute() {
    Map<String, Object> data = new HashMap();
    Map<String, String> fieldMeta = new HashMap();

    data.put("bussiPackage", com.qihang.winter.codegenerate.util.CodeResourceUtil.bussiPackage);

    data.put("entityPackage", this.entityPackage);

    data.put("entityName", this.entityName);

    data.put("tableName", this.tableName);

    data.put("ftl_description", this.ftlDescription);

    data.put(FtlDef.JEECG_TABLE_ID, com.qihang.winter.codegenerate.util.CodeResourceUtil.JEECG_GENERATE_TABLE_ID);

    data.put(FtlDef.JEECG_PRIMARY_KEY_POLICY, this.primaryKeyPolicy);
    data.put(FtlDef.JEECG_SEQUENCE_CODE, this.sequenceCode);

    data.put("ftl_create_time", com.qihang.winter.codegenerate.util.CodeDateUtils.dateToString(new Date()));

    data.put("foreignKeys", this.foreignKeys);


    data.put(FtlDef.FIELD_REQUIRED_NAME, Integer.valueOf(StringUtils.isNotEmpty(com.qihang.winter.codegenerate.util.CodeResourceUtil.JEECG_UI_FIELD_REQUIRED_NUM) ? Integer.parseInt(com.qihang.winter.codegenerate.util.CodeResourceUtil.JEECG_UI_FIELD_REQUIRED_NUM) : -1));

    data.put(FtlDef.SEARCH_FIELD_NUM, Integer.valueOf(StringUtils.isNotEmpty(com.qihang.winter.codegenerate.util.CodeResourceUtil.JEECG_UI_FIELD_SEARCH_NUM) ? Integer.parseInt(com.qihang.winter.codegenerate.util.CodeResourceUtil.JEECG_UI_FIELD_SEARCH_NUM) : -1));

    data.put(FtlDef.FIELD_ROW_NAME, Integer.valueOf(FIELD_ROW_NUM));
    try {
      List<CgFormFieldEntity> columns = this.cgformConfig.deepCopy().getCgFormHead().getColumns();
      String type;
      for (CgFormFieldEntity cf : columns) {
        type = cf.getType();
        if ("string".equalsIgnoreCase(type)) {
          cf.setType("java.lang.String");
        } else if ("Date".equalsIgnoreCase(type)) {
          cf.setType("java.util.Date");
        } else if ("double".equalsIgnoreCase(type)) {
          cf.setType("java.lang.Double");
        } else if ("int".equalsIgnoreCase(type)) {
          cf.setType("java.lang.Integer");
        } else if ("BigDecimal".equalsIgnoreCase(type)) {
          cf.setType("java.math.BigDecimal");
        } else if ("Text".equalsIgnoreCase(type)) {
          cf.setType("java.lang.String");
        } else if ("Blob".equalsIgnoreCase(type)) {
          cf.setType("java.sql.Blob");
        }
        String fieldName = cf.getFieldName();
        String fieldNameV = JeecgReadTable.formatField(fieldName);
        cf.setFieldName(fieldNameV);
        fieldMeta.put(fieldNameV, fieldName.toUpperCase());
      }
      List<CgFormFieldEntity> pageColumns = new ArrayList();
      /** 20160628过滤出页脚字段 **/
      List<CgFormFieldEntity> columnsfoot = new ArrayList();
      for (CgFormFieldEntity cf : columns) {
        if(!"Y".equals(cf.getIsPageFoot())){
          if ((StringUtils.isNotEmpty(cf.getIsShow())) && ("Y".equalsIgnoreCase(cf.getIsShow()))) {
            pageColumns.add(cf);
          }
        } else {
          columnsfoot.add(cf);
        }

      }

      data.put("cgformConfig", this.cgformConfig);
      data.put("fieldMeta", fieldMeta);
      data.put("columns", columns);
      data.put("pageColumns", pageColumns);
      data.put("columnsfoot", columnsfoot);
      data.put("buttons", this.cgformConfig.getButtons() == null ? new ArrayList(0) : this.cgformConfig.getButtons());
      data.put("buttonSqlMap", this.cgformConfig.getButtonSqlMap() == null ? new HashMap(0) : this.cgformConfig.getButtonSqlMap());
      data.put("packageStyle", this.cgformConfig.getPackageStyle());
    } catch (Exception e) {
      e.printStackTrace();
    }
    long serialVersionUID = com.qihang.winter.codegenerate.util.NonceUtils.randomLong() +
            com.qihang.winter.codegenerate.util.NonceUtils.currentMills();
    data.put("serialVersionUID", String.valueOf(serialVersionUID));
    return data;
  }

  public void generateToFile() throws TemplateException, IOException {
    log.info("----jeecg---Code----Generation----[单表模型:" + this.tableName + "]------- 生成中。。。");

    CgformCodeFactory codeFactory = new CgformCodeFactory();
    codeFactory.setProjectPath(this.cgformConfig.getProjectPath());
    codeFactory.setPackageStyle(this.cgformConfig.getPackageStyle());
    if (this.cgformConfig.getCgFormHead().getJformType().intValue() == 1) {
      codeFactory.setCallBack(new CgformCodeGenerate(createFileProperty, this.cgformConfig));
    } else {
      codeFactory.setCallBack(new CgformCodeGenerate(this.sub, this.subG, this.subFileProperty, "uuid", this.foreignKeys));
    }

    if (createFileProperty.isJspFlag()) {
      if ("03".equals(createFileProperty.getJspMode())) {
        codeFactory.invoke("onetomany/cgform_jspSubTemplate.ftl", "jspList");
      } else {
        if ("01".equals(createFileProperty.getJspMode())) {
          codeFactory.invoke("cgform_jspTableTemplate_add.ftl", "jsp_add");
          codeFactory.invoke("cgform_jspTableTemplate_update.ftl", "jsp_update");
        }
        if ("02".equals(createFileProperty.getJspMode())) {
          codeFactory.invoke("cgform_jspDivTemplate_add.ftl", "jsp_add");
          codeFactory.invoke("cgform_jspDivTemplate_update.ftl", "jsp_update");
        }
        codeFactory.invoke("cgform_jspListTemplate.ftl", "jspList");
        codeFactory.invoke("cgform_jsListEnhanceTemplate.ftl", "jsList");
        codeFactory.invoke("cgform_jsEnhanceTemplate.ftl", "js");
      }
    }
    if (createFileProperty.isServiceImplFlag()) {
      codeFactory.invoke("cgform_serviceImplTemplate.ftl", "serviceImpl");
    }
    if (createFileProperty.isServiceIFlag()) {
      codeFactory.invoke("cgform_serviceITemplate.ftl", "service");
    }
    if (createFileProperty.isActionFlag()) {
      codeFactory.invoke("cgform_controllerTemplate.ftl", "controller");
    }
    if (createFileProperty.isEntityFlag()) {
      codeFactory.invoke("cgform_entityTemplate.ftl", "entity");
    }
    log.info("----jeecg----Code----Generation-----[单表模型：" + this.tableName + "]------ 生成完成。。。");
  }

  public GenerateEntity getCgformConfig() {
    return this.cgformConfig;
  }

  public void setCgformConfig(GenerateEntity cgformConfig) {
    this.cgformConfig = cgformConfig;
  }
}
