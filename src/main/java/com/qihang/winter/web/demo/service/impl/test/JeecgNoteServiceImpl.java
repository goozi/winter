package com.qihang.winter.web.demo.service.impl.test;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.qihang.winter.web.demo.service.test.JeecgNoteServiceI;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;

@Service("jeecgNoteService")
@Transactional
public class JeecgNoteServiceImpl extends CommonServiceImpl implements JeecgNoteServiceI {
	
}