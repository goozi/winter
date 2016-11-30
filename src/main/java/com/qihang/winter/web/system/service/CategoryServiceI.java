package com.qihang.winter.web.system.service;

import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.web.system.pojo.base.TSCategoryEntity;

public interface CategoryServiceI extends CommonService {
	/**
	 * 保存分类管理
	 * @param category
	 */
	void saveCategory(TSCategoryEntity category);

}
