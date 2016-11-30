package com.qihang.winter.web.cgreport.dao.core;

import java.util.List;
import java.util.Map;

import com.qihang.winter.winterdao.annotation.Arguments;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author zhangdaihao
 *
 */
@Repository("cgReportDao")
public interface CgReportDao{

	@Arguments("configId")
	List<Map<String,Object>> queryCgReportItems(String configId);
	
	@Arguments("id")
	Map queryCgReportMainConfig(String id);
}
