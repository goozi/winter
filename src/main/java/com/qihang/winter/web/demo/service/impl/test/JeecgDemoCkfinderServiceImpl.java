package com.qihang.winter.web.demo.service.impl.test;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qihang.winter.web.demo.service.test.JeecgDemoCkfinderServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;

@Service("jeecgDemoCkfinderService")
@Transactional
public class JeecgDemoCkfinderServiceImpl extends CommonServiceImpl implements
		JeecgDemoCkfinderServiceI {

}