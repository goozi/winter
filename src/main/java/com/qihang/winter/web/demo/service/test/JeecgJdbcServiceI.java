package com.qihang.winter.web.demo.service.test;

import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.web.demo.entity.test.JeecgJdbcEntity;
import net.sf.json.JSONObject;

public interface JeecgJdbcServiceI extends CommonService {
	public void getDatagrid1(JeecgJdbcEntity pageObj, DataGrid dataGrid);
	public void getDatagrid2(JeecgJdbcEntity pageObj, DataGrid dataGrid);
	public JSONObject getDatagrid3(JeecgJdbcEntity pageObj, DataGrid dataGrid);
	public void listAllByJdbc(DataGrid dataGrid);
}
