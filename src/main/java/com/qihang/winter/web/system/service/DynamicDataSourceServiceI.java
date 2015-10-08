package com.qihang.winter.web.system.service;

import java.util.List;

import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.web.system.pojo.base.DynamicDataSourceEntity;

public interface DynamicDataSourceServiceI extends CommonService {
	
	public List<DynamicDataSourceEntity> initDynamicDataSource();
	
	public void refleshCache();
}
