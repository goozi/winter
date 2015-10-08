package com.qihang.winter.web.demo.service.test;

import java.util.List;

import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.web.demo.entity.test.JeecgOrderCustomEntity;
import com.qihang.winter.web.demo.entity.test.JeecgOrderMainEntity;
import com.qihang.winter.web.demo.entity.test.JeecgOrderProductEntity;


public interface JeecgOrderMainServiceI extends CommonService {
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(JeecgOrderMainEntity jeecgOrderMain, List<JeecgOrderProductEntity> jeecgOrderProducList, List<JeecgOrderCustomEntity> jeecgOrderCustomList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(JeecgOrderMainEntity jeecgOrderMain,List<JeecgOrderProductEntity> jeecgOrderProducList,List<JeecgOrderCustomEntity> jeecgOrderCustomList,boolean jeecgOrderCustomShow) ;
	public void delMain (JeecgOrderMainEntity jeecgOrderMain);
}
