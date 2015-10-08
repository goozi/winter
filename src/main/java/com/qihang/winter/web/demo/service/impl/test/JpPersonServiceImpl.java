package com.qihang.winter.web.demo.service.impl.test;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qihang.winter.web.demo.service.test.JpPersonServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;

@Service("jpPersonService")
@Transactional
public class JpPersonServiceImpl extends CommonServiceImpl implements JpPersonServiceI {
	
}