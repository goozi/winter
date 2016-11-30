package com.qihang.winter.web.demo.service.test;

import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.web.demo.entity.test.TFinanceEntity;
import com.qihang.winter.web.demo.entity.test.TFinanceFilesEntity;

public interface TFinanceServiceI extends CommonService {

	void deleteFile(TFinanceFilesEntity file);

	void deleteFinance(TFinanceEntity finance);

}
