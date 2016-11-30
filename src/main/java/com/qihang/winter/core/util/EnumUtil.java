package com.qihang.winter.core.util;

import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.core.extend.datasource.DataSourceType;
import com.qihang.winter.web.system.pojo.base.DynamicDataSourceEntity;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

/**
 * Created by gaozhenling on 16/6/13.
 */
public class EnumUtil implements InitializingBean {
  @Autowired
  private CommonService commonService;

  @Override
  public void afterPropertiesSet() throws Exception {
    List<DynamicDataSourceEntity> dynamicDataSourceEntityList = commonService.findListbySql("select * from t_s_data_source",DynamicDataSourceEntity.class);
    //系统启动时即将t_s_data_source数据源表中所有数据源名加载到DataSourceType枚举类中
    for(DynamicDataSourceEntity dynamicDataSourceEntity : dynamicDataSourceEntityList){
      EnumBuster<DataSourceType> buster =
              new EnumBuster<DataSourceType>(DataSourceType.class);
      DataSourceType dataSourceType = buster.make(dynamicDataSourceEntity.getDbKey());
      buster.addByValue(dataSourceType);
    }
    System.out.println(Arrays.toString(DataSourceType.values()));
  }


}
