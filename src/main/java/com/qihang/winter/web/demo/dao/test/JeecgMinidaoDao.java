package com.qihang.winter.web.demo.dao.test;

import java.util.List;
import java.util.Map;

import com.qihang.winter.web.demo.entity.test.JeecgMinidaoEntity;

import com.qihang.winter.minidao.annotation.Arguments;
import com.qihang.winter.minidao.annotation.MiniDao;
import com.qihang.winter.minidao.annotation.ResultType;
import com.qihang.winter.minidao.annotation.Sql;
import com.qihang.winter.minidao.hibernate.MiniDaoSupportHiber;

/**
 * Minidao例子
 * @author Zerrion
 * 
 */
//@Repository("jeecgMinidaoDao")
@MiniDao
public interface JeecgMinidaoDao extends MiniDaoSupportHiber<JeecgMinidaoEntity> {
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
