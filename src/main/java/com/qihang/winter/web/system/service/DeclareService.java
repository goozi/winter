package com.qihang.winter.web.system.service;

import java.util.List;

import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.web.system.pojo.base.TSAttachment;

/**
 * 
 * @author  Zerrion
 *
 */
public interface DeclareService extends CommonService {
	
	public List<TSAttachment> getAttachmentsByCode(String businessKey,String description);
	
}
