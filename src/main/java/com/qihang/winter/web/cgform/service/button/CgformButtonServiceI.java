package com.qihang.winter.web.cgform.service.button;

import java.util.List;

import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.web.cgform.entity.button.CgformButtonEntity;

/**
 * 
 * @author  Zerrion
 *
 */
public interface CgformButtonServiceI extends CommonService {
	
	/**
	 * 查询按钮list
	 * @param formId
	 * @return
	 */
	public List<CgformButtonEntity> getCgformButtonListByFormId(String formId);

	/**
	 * 校验按钮唯一性
	 * @param cgformButtonEntity
	 * @return
	 */
	public List<CgformButtonEntity> checkCgformButton(CgformButtonEntity cgformButtonEntity);
	
	
}
