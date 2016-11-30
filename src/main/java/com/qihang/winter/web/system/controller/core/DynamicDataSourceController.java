package com.qihang.winter.web.system.controller.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qihang.winter.core.annotation.DataSource;
import com.qihang.winter.core.common.dao.impl.CommonDao;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.extend.datasource.DataSourceContextHolder;
import com.qihang.winter.core.extend.datasource.DataSourceType;
import com.qihang.winter.core.util.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.web.system.pojo.base.DynamicDataSourceEntity;
import com.qihang.winter.web.system.service.DynamicDataSourceServiceI;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil;

import java.util.Map;

/**   
 * @Title: Controller
 * @Description: 数据源配置
 * @author zhangdaihao
 * @date 2014-09-05 13:22:10
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/dynamicDataSourceController")
public class DynamicDataSourceController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(DynamicDataSourceController.class);

	@Autowired
	private DynamicDataSourceServiceI dynamicDataSourceService;
	@Autowired
	private SystemService systemService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 数据源配置列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "dbSource")
	public ModelAndView dbSource(HttpServletRequest request) {
		return new ModelAndView("system/dbsource/dbSourceList");
	}

	/**
	 * 数据源列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tSDataSource")
	public ModelAndView tSDataSource(HttpServletRequest request) {
		//return new ModelAndView("com/qihang/buss/sc/sys/tSDataSourceList");
		return new ModelAndView("system/dbsource/tSDataSourceList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "datagrid")
	//@DataSource("dataSource_jeecg")
	public void datagrid(DynamicDataSourceEntity dbSource,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, request);
		//通过DataSource注解切回主库
		CriteriaQuery cq = new CriteriaQuery(DynamicDataSourceEntity.class, dataGrid);
		//查询条件组装器
		HqlGenerateUtil.installHql(cq, dbSource, request.getParameterMap());
		this.dynamicDataSourceService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
		//查询完主库后，再要回到原数据源
		DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, request);
		//查看切换后的数据源连接信息
//		dynamicDataSourceService.testCurConnection();
	}

	/**
	 * 删除数据源配置
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(DynamicDataSourceEntity dbSource, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		dbSource = systemService.getEntity(DynamicDataSourceEntity.class, dbSource.getId());
		
		message = MutiLangUtil.paramDelSuccess("common.datasource.manage");
		
		dynamicDataSourceService.delete(dbSource);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加数据源配置
	 * 
	 * @param dbSource
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(DynamicDataSourceEntity dbSource, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(dbSource.getId())) {
			message = MutiLangUtil.paramUpdSuccess("common.datasource.manage");
			DynamicDataSourceEntity t = dynamicDataSourceService.get(DynamicDataSourceEntity.class, dbSource.getId());
			try {
				MyBeanUtils.copyBeanNotNull2Bean(dbSource, t);
				dynamicDataSourceService.saveOrUpdate(t);
				dynamicDataSourceService.refleshCache();
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
				message = MutiLangUtil.paramUpdFail("common.datasource.manage");
			}
		} else {
			message = MutiLangUtil.paramAddSuccess("common.datasource.manage");
			
			dynamicDataSourceService.save(dbSource);
			dynamicDataSourceService.refleshCache();
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 数据源配置列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(DynamicDataSourceEntity dbSource, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(dbSource.getId())) {
			dbSource = dynamicDataSourceService.getEntity(DynamicDataSourceEntity.class, dbSource.getId());
			req.setAttribute("dbSourcePage", dbSource);
		}
		return new ModelAndView("system/dbsource/dbSource");
	}

	/**
	 * 数据源新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	//@DataSource("dataSource_jeecg")
	public ModelAndView goAdd(DynamicDataSourceEntity tSDataSource, HttpServletRequest req) {
		//要判断如果当前数据源不是主库，则先记录当前数据源，再要切回主库
		String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, req);
		if (StringUtil.isNotEmpty(tSDataSource.getId())) {
			tSDataSource = dynamicDataSourceService.getEntity(DynamicDataSourceEntity.class, tSDataSource.getId());
			req.setAttribute("tSDataSourcePage", tSDataSource);
		}else{//初始新的数据源l默认为当前数据源同一数据库服务器
			tSDataSource = new DynamicDataSourceEntity();
			//获得当前连接的db_key
			String dataSourceType = ContextHolderUtils.getSession().getAttribute("dataSourceType").toString();
			if (dataSourceType!=null) {
				String dbKey = dataSourceType.toString();
				//获得当前数据源的连接参数
				DynamicDataSourceEntity dynamicDataSourceEntity = DynamicDataSourceEntity.DynamicDataSourceMap.get(dbKey);
				if (dynamicDataSourceEntity!=null) {
					//tSDataSource.setDbKey(dynamicDataSourceEntity.getDbKey());
					Map<String, String> dbmap = DBUtil.getDbParameterByUrl(dynamicDataSourceEntity.getUrl());
					String curdatabasename = dbmap.containsKey("databasename")?dbmap.get("databasename"):"";
					tSDataSource.setDbKey(curdatabasename); //用来存储当前数据源的连接串的数据库名称
					tSDataSource.setDriverClass(dynamicDataSourceEntity.getDriverClass());
					tSDataSource.setUrl(dynamicDataSourceEntity.getUrl());//.replace(tSDataSource.getDbKey(), "")
					tSDataSource.setDbUser(dynamicDataSourceEntity.getDbUser());
					tSDataSource.setDbPassword(dynamicDataSourceEntity.getDbPassword());
					tSDataSource.setDbType(dynamicDataSourceEntity.getDbType());
					req.setAttribute("tSDataSourcePage", tSDataSource);
				}
			}
		}
		//查询完主库后，再要回到原数据源
		DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, req);
		//return new ModelAndView("com/qihang/buss/sc/sys/tSDataSource-add");
		return new ModelAndView("system/dbsource/tSDataSource-add");
	}

	/**
	 * 数据源编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	//@DataSource("dataSource_jeecg")
	public ModelAndView goUpdate(DynamicDataSourceEntity tSDataSource, HttpServletRequest req) {
		//要判断如果当前数据源不是主库，则先记录当前数据源，再要切回主库
		String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, req);
		if (StringUtil.isNotEmpty(tSDataSource.getId())) {
			tSDataSource = dynamicDataSourceService.getEntity(DynamicDataSourceEntity.class, tSDataSource.getId());
			req.setAttribute("tSDataSourcePage", tSDataSource);
		}
		//查询完主库后，再要回到原数据源
		//return new ModelAndView("com/qihang/buss/sc/sys/tSDataSource-update");
		DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, req);
		return new ModelAndView("system/dbsource/tSDataSource-update");
	}

	/**
	 * 添加数据源
	 *
	 * @param tSDataSource
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	//@DataSource("dataSource_jeecg")
	public AjaxJson doAdd(DynamicDataSourceEntity tSDataSource, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "数据源添加成功";
		//要判断如果当前数据源不是主库，则先记录当前数据源，再要切回主库
		String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, request);
		try{
			//tSDataSourceService.save(tSDataSource);
			//获得当前连接的db_key及参数,从配置参数文件中读取数据库bin文件路径，根据新库名创建新的数据库,增加数据源配置记录，并初始化数据
			dynamicDataSourceService.createDatabaseAndInitialize(tSDataSource);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "数据源添加失败";
			throw new BusinessException(e.getMessage());
		}
		//查询完主库后，再要回到原数据源
		DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, request);
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新数据源
	 *
	 * @param tSDataSource
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(DynamicDataSourceEntity tSDataSource, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "数据源更新成功";
		//要判断如果当前数据源不是主库，则先记录当前数据源，再要切回主库
		String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, request);
		DynamicDataSourceEntity t = dynamicDataSourceService.get(DynamicDataSourceEntity.class, tSDataSource.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tSDataSource, t);
			dynamicDataSourceService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "数据源更新失败";
			throw new BusinessException(e.getMessage());
		}
		//查询完主库后，再要回到原数据源
		DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, request);
		j.setMsg(message);
		return j;
	}

	/**
	 * 删除数据源
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	//@DataSource("dataSource_jeecg")
	public AjaxJson doDel(DynamicDataSourceEntity tSDataSource, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		//要判断如果当前数据源不是主库，则先记录当前数据源，再要切回主库
		String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, request);
		tSDataSource = systemService.getEntity(DynamicDataSourceEntity.class, tSDataSource.getId());
		message = "数据源删除成功";
		try{
			dynamicDataSourceService.delete(tSDataSource);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "数据源删除失败";
			throw new BusinessException(e.getMessage());
		}
		//查询完主库后，再要回到原数据源
		DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, request);
		j.setMsg(message);
		return j;
	}
}