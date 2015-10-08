package com.qihang.winter.web.demo.service.impl.test;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.qihang.winter.core.util.LogUtil;
import com.qihang.winter.web.demo.service.test.TaskDemoServiceI;
@Service("taskDemoService")
public class TaskDemoServiceImpl implements TaskDemoServiceI {

	
	public void work() {
		LogUtil.info(new Date().getTime());
		LogUtil.info("----------任务测试-------");
	}

}
