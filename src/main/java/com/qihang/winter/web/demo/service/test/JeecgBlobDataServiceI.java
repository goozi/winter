package com.qihang.winter.web.demo.service.test;

import com.qihang.winter.core.common.service.CommonService;
import org.springframework.web.multipart.MultipartFile;

public interface JeecgBlobDataServiceI extends CommonService {
	public void saveObj(String documentTitle, MultipartFile file);

}
