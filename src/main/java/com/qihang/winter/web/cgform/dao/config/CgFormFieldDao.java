package com.qihang.winter.web.cgform.dao.config;

import java.util.List;
import java.util.Map;

import com.qihang.winter.minidao.annotation.Arguments;
import org.springframework.stereotype.Repository;

/**
 * 
 * @Title:CgFormFieldDao
 * @description:
 * @author  Zerrion
 * @date Aug 24, 2013 11:33:33 AM
 * @version V1.0
 */
@Repository("cgFormFieldDao")
public interface CgFormFieldDao {
	
	@Arguments("tableName")
	public List<Map<String, Object>> getCgFormFieldByTableName(String tableName);
	
	@Arguments("tableName")
	public List<Map<String, Object>> getCgFormHiddenFieldByTableName(String tableName);
	
}
