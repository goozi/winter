package com.qihang.winter.web.demo.service.test;

import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.web.demo.entity.test.WebOfficeEntity;

import org.springframework.web.multipart.MultipartFile;

public interface WebOfficeServiceI extends CommonService {
	public void saveObj(WebOfficeEntity docObj, MultipartFile file);
}
