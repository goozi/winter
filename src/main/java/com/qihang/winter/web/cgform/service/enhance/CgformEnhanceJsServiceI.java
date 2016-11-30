package com.qihang.winter.web.cgform.service.enhance;

import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.web.cgform.entity.enhance.CgformEnhanceJsEntity;

public interface CgformEnhanceJsServiceI extends CommonService {

	/**
	 * 根据cgJsType和formId查找数据
	 * @param cgJsType
	 * @param formId
	 * @return
	 */
	public CgformEnhanceJsEntity getCgformEnhanceJsByTypeFormId(String cgJsType, String formId);
}
