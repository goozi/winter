package com.qihang.winter.web.demo.dao.test;

import java.util.List;
import java.util.Map;

import com.qihang.winter.web.demo.entity.test.JeecgMinidaoEntity;

import com.qihang.winter.winterdao.annotation.Arguments;
import com.qihang.winter.winterdao.annotation.WinterDao;
import com.qihang.winter.winterdao.annotation.ResultType;
import com.qihang.winter.winterdao.annotation.Sql;
import com.qihang.winter.winterdao.hibernate.WinterDaoSupportHiber;

/**
 * Minidao例子
 * @author Zerrion
 * 
 */
//@Repository("jeecgMinidaoDao")
@WinterDao
public interface JeecgMinidaoDao extends WinterDaoSupportHiber<JeecgMinidaoEntity> {
	@Arguments({"jeecgMinidao", "page", "rows"})
	public List<Map> getAllEntities(JeecgMinidaoEntity jeecgMinidao, int page, int rows);

	@Arguments({"jeecgMinidao", "page", "rows"})
	@ResultType(JeecgMinidaoEntity.class)
	public List<JeecgMinidaoEntity> getAllEntities2(JeecgMinidaoEntity jeecgMinidao, int page, int rows);

	//@Arguments("id")
	//JeecgMinidaoEntity getJeecgMinidao(String id);

	@Sql("SELECT count(*) FROM jeecg_minidao")
	Integer getCount();

	@Sql("SELECT SUM(salary) FROM jeecg_minidao")
	Integer getSumSalary();

	/*@Arguments("jeecgMinidao")
	int update(JeecgMinidaoEntity jeecgMinidao);

	@Arguments("jeecgMinidao")
	void insert(JeecgMinidaoEntity jeecgMinidao);

	@Arguments("jeecgMinidao")
	void delete(JeecgMinidaoEntity jeecgMinidao);*/
}
