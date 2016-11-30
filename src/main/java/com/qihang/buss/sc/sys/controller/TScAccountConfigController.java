package com.qihang.buss.sc.sys.controller;
import com.qihang.buss.sc.sys.entity.TScAccountConfigEntity;
import com.qihang.buss.sc.sys.page.TScAccountConfigPage;
import com.qihang.buss.sc.sys.service.TScAccountConfigServiceI;
import com.qihang.buss.sc.util.AccountUtil;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.extend.datasource.DataSourceContextHolder;
import com.qihang.winter.core.extend.datasource.DataSourceType;
import com.qihang.winter.core.util.*;
import com.qihang.winter.poi.excel.ExcelImportUtil;
import com.qihang.winter.poi.excel.entity.ExportParams;
import com.qihang.winter.poi.excel.entity.ImportParams;
import com.qihang.winter.poi.excel.entity.TemplateExportParams;
import com.qihang.winter.poi.excel.entity.vo.NormalExcelConstants;
import com.qihang.winter.poi.excel.entity.vo.TemplateExcelConstants;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.web.system.pojo.base.DynamicDataSourceEntity;
import com.qihang.winter.web.system.service.SystemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * @Title: Controller
 * @Description: 账套设置
 * @author onlineGenerator
 * @date 2016-06-17 17:59:34
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScAccountConfigController")
public class TScAccountConfigController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScAccountConfigController.class);

	@Autowired
	private TScAccountConfigServiceI tScAccountConfigService;

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
	 * 账套设置列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScAccountConfig")
	public ModelAndView tScAccountConfig(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/sys/tScAccountConfigList");
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
	public void datagrid(TScAccountConfigEntity tScAccountConfig,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, request);
		CriteriaQuery cq = new CriteriaQuery(TScAccountConfigEntity.class, dataGrid);
		cq.notEq("id",Globals.ACCOUNT_SCM_ID);//不显示scm主库的账套
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScAccountConfig, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScAccountConfigService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
		//查询完主库后，再要回到原数据源
		DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, request);
	}

	/**
	 * 删除账套设置
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	//@DataSource("dataSource_jeecg")
	public AjaxJson doDel(TScAccountConfigEntity tScAccountConfig, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, request);
		tScAccountConfig = systemService.getEntity(TScAccountConfigEntity.class, tScAccountConfig.getId());
		message = "账套设置删除成功";
		try{
			//删除账套
			tScAccountConfigService.delete(tScAccountConfig);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "账套设置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		//查询完主库后，再要回到原数据源
		DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, request);
		return j;
	}
	
	/**
	 * 批量删除账套设置
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	// @DataSource("dataSource_jeecg")
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		 String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, request);
		AjaxJson j = new AjaxJson();
		message = "账套设置删除成功";
		try{
			for(String id:ids.split(",")){
				TScAccountConfigEntity tScAccountConfig = systemService.getEntity(TScAccountConfigEntity.class, 
				id
				);
				tScAccountConfigService.delete(tScAccountConfig);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "账套设置删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		 //查询完主库后，再要回到原数据源
		 DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, request);
		return j;
	}


	/**
	 * 添加账套设置
	 * 
	 * @param tScAccountConfig
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	//@DataSource("dataSource_jeecg")
	public AjaxJson doAdd(TScAccountConfigEntity tScAccountConfig, HttpServletRequest request) {
		String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, request);
		AjaxJson j = new AjaxJson();
		message = "账套设置添加成功";
		try{
			tScAccountConfigService.save(tScAccountConfig);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "账套设置添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		//查询完主库后，再要回到原数据源
		DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, request);
		return j;
	}

	/**
	 * 添加账套初始设置（含系统设置和数据源设置）
	 *
	 * @param tScAccountConfig
	 * @param tScAccountConfigPage
	 * @return
	 */
	@RequestMapping(params = "doAddInit")
	@ResponseBody
	//@DataSource("dataSource_jeecg")
	public AjaxJson doAddInit(TScAccountConfigEntity tScAccountConfig, TScAccountConfigPage tScAccountConfigPage, HttpServletRequest request) {
		String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, request);
		List<DynamicDataSourceEntity> tSDataSourceList =  tScAccountConfigPage.getTSDataSourceList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		if (tSDataSourceList.size()==0){
			message = "请配置账套的建账信息";
			j.setSuccess(false);
		}else {
			DynamicDataSourceEntity tSDataSource = tSDataSourceList.get(0);
			//检查配置的数据库bin目录下是否有连接数据库exe文件
			boolean isExe = DBUtil.testDBConnExe(tSDataSource.getDbType());//"mysql");
			if (isExe == false) {
				message = "sysConfig.properties配置的dbbinpath路径不存在连接数据库的exe文件，请联系管理员检查相关配置";
				j.setSuccess(false);
			} else {
				//检查数据库文件存放目录是否存在
				boolean isDataPath = DBUtil.testDBConnDataPath();
				if (isDataPath==false){
					message = "sysConfig.properties配置的dbdatapath路径不存在，请联系管理员检查相关配置";
					j.setSuccess(false);
				}else {
					//检查数据库连接是否成功
					tSDataSource.setDbKey(tScAccountConfig.getDbKey());
					if (tSDataSource.getDbName() == null || tSDataSource.getDbName().equals("")) {
						tSDataSource.setDbName(tSDataSource.getDbKey());
					}
					boolean isConnect = DBUtil.testConnection(tSDataSource.getDbType(), tSDataSource.getDbIp(), tSDataSource.getDbPort(), tSDataSource.getDbName(), tSDataSource.getDbUser(), tSDataSource.getDbPassword());//"mysql", "192.168.0.245", "3306", "scm", "scm", "scm");
					if (isConnect == false) {
						message = "实体库数据源连接失败，请检查连接参数";
						j.setSuccess(false);
					} else {
						//todo:检查主库scm用户是否有数据导出等权限？
						//建账套
						try {
							tScAccountConfigService.addMain(tScAccountConfig, tSDataSourceList);
							systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
							//将新的账套数据源加入到DynamicDataSourceMap中
							List<DynamicDataSourceEntity> dynamicSourceEntityList = systemService.loadAll(DynamicDataSourceEntity.class);
							for (DynamicDataSourceEntity dynamicSourceEntity : dynamicSourceEntityList) {
								if (!DynamicDataSourceEntity.DynamicDataSourceMap.containsKey(dynamicSourceEntity.getDbKey())) {//不在map中的加入
									DynamicDataSourceEntity.DynamicDataSourceMap.put(dynamicSourceEntity.getDbKey(), dynamicSourceEntity);
//									EnumBuster<DataSourceType> buster = new EnumBuster<DataSourceType>(DataSourceType.class);
//									DataSourceType newdataSourceType = buster.make(dynamicSourceEntity.getDbKey());
//									buster.addByValue(newdataSourceType);
									DynamicEnumUtil.addEnum(DataSourceType.class, dynamicSourceEntity.getDbKey());
									//DataSourceContextHolder.setDataSourceType(DataSourceType.valueOf(dynamicSourceEntity.getDbKey()));
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							message = "账套设置添加失败";
							throw new BusinessException(e.getMessage());
						}
					}
				}
			}
		}
		j.setMsg(message);
		//查询完主库后，再要回到原数据源
		DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, request);
		return j;
	}
	
	/**
	 * 更新账套设置
	 * 
	 * @param tScAccountConfig
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	//@DataSource("dataSource_jeecg")
	public AjaxJson doUpdate(TScAccountConfigEntity tScAccountConfig, HttpServletRequest request) {
		String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, request);
		AjaxJson j = new AjaxJson();
		message = "账套设置更新成功";
//		TScAccountConfigEntity t = tScAccountConfigService.get(TScAccountConfigEntity.class, tScAccountConfig.getId());
		try {
//			MyBeanUtils.copyBeanNotNull2Bean(tScAccountConfig, t);
//			if(StringUtil.isEmpty(tScAccountConfig.getId())){
//				tScAccountConfig.setId(null);
//			}
			//tScAccountConfigService.saveOrUpdate(tScAccountConfig);
			//获得当前账套ID，并将设置的几个属性复制过来，免得把系统设置的账套设置未给的属性清空掉,再更新当前账套的设置信息
			String accountId = ResourceUtil.getAccountId();
			TScAccountConfigEntity tScAccountConfigEntityold = tScAccountConfigService.getEntity(TScAccountConfigEntity.class, accountId);
			tScAccountConfigEntityold.setCompanyName(tScAccountConfig.getCompanyName());
			tScAccountConfigEntityold.setTaxNumber(tScAccountConfig.getTaxNumber());
			tScAccountConfigEntityold.setBankAccount(tScAccountConfig.getBankAccount());
			tScAccountConfigEntityold.setCompanyAddress(tScAccountConfig.getCompanyAddress());
			tScAccountConfigEntityold.setPhone(tScAccountConfig.getPhone());
			tScAccountConfigEntityold.setFax(tScAccountConfig.getFax());
			tScAccountConfigEntityold.setEmail(tScAccountConfig.getEmail());
			tScAccountConfigEntityold.setCompanyUrl(tScAccountConfig.getCompanyUrl());
			tScAccountConfigEntityold.setRegistrationNumber(tScAccountConfig.getRegistrationNumber());
			tScAccountConfigService.saveOrUpdate(tScAccountConfigEntityold);
			AccountUtil.clearAccountConfigCache(tScAccountConfig);//清除ehcache
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "账套设置更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		//查询完主库后，再要回到原数据源
		DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, request);
		return j;
	}

	/**
	 * 账套设置新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	//@DataSource("dataSource_jeecg")
	public ModelAndView goAdd(TScAccountConfigEntity tScAccountConfig, HttpServletRequest req) {
		String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, req);
		if (StringUtil.isNotEmpty(tScAccountConfig.getId())) {
			tScAccountConfig = tScAccountConfigService.getEntity(TScAccountConfigEntity.class, tScAccountConfig.getId());
			req.setAttribute("tScAccountConfigPage", tScAccountConfig);
		}
		//查询完主库后，再要回到原数据源
		DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, req);
		return new ModelAndView("com/qihang/buss/sc/sys/tScAccountConfig-add");
	}

	/**
	 * 账套设置录入初始（含账套和数据源信息）页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAddInit")
	//@DataSource("dataSource_jeecg")
	public ModelAndView goInit(TScAccountConfigEntity tScAccountConfig, HttpServletRequest req) {
		String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, req);
		DynamicDataSourceEntity tSDataSource;
		if (StringUtil.isNotEmpty(tScAccountConfig.getId())) {
			tScAccountConfig = tScAccountConfigService.getEntity(TScAccountConfigEntity.class, tScAccountConfig.getId());
			req.setAttribute("tScAccountConfigPage", tScAccountConfig);
			tSDataSource = tScAccountConfigService.getEntity(DynamicDataSourceEntity.class, tScAccountConfig.getDbId());
			req.setAttribute("tSDataSourcePage", tSDataSource);
		}else{//初始新的数据源l默认为当前数据源同一数据库服务器
			tScAccountConfig.setAccountStartDate(DateUtils.str2Date(DateUtils.formatDate(DateUtils.getDate(),"yyyy-MM") + "-1", DateUtils.date_sdf));//默认账套启用年月为当月1日
			req.setAttribute("tScAccountConfigPage", tScAccountConfig);
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
					//请使用有创建新数据库、用户、并可以授权的用户，如mysql建议使用root用户,sql server建议使用sa用户,oracle建议使用system用户
					String dbUser = "";
					if (tSDataSource.getDbType().equals(Globals.DB_TYPE_MYSQL)){
						dbUser = Globals.DB_SYS_USER_MYSQL;
					}else if (tSDataSource.getDbType().equals(Globals.DB_TYPE_SQLSERVER)){
						dbUser = Globals.DB_SYS_USER_SQLSERVER;
					}else if (tSDataSource.getDbType().equals(Globals.DB_TYPE_ORACLE)){
						dbUser = Globals.DB_SYS_USER_ORACLE;
					}
					tSDataSource.setDbUser(dbUser);
					tSDataSource.setDbPassword("");

					//从url中解析出数据库IP、端口、实例名或数据库名 信息
					String curip = dbmap.containsKey("ip")?dbmap.get("ip"):"";
					String curport = dbmap.containsKey("port")?dbmap.get("port"):"";
					tSDataSource.setDbIp(curip);
					tSDataSource.setDbPort(curport);
					tSDataSource.setDbName(curdatabasename);
					req.setAttribute("tSDataSourcePage", tSDataSource);
				}
			}
		}
		//查询完主库后，再要回到原数据源
		DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, req);
		return new ModelAndView("com/qihang/buss/sc/sys/tScAccountConfig-addinit");
	}
	/**
	 * 账套设置编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	//@DataSource("dataSource_jeecg")
	public ModelAndView goUpdate(TScAccountConfigEntity tScAccountConfig, HttpServletRequest req) {
		String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, req);
		//List<TScAccountConfigEntity> tSCAccountonfigList = tScAccountConfigService.getList(TScAccountConfigEntity.class);
		//if (tSCAccountonfigList.size() > 0) {
			//获得当前账套ID
			String accountId = ResourceUtil.getAccountId();
			TScAccountConfigEntity tScAccountConfigEntity = tScAccountConfigService.getEntity(TScAccountConfigEntity.class, accountId); //tSCAccountonfigList.get(0);//hjh20160918edit改为对当前账套进行系统设置
			req.setAttribute("tScAccountConfigPage", tScAccountConfigEntity);
		//}

//		if (StringUtil.isNotEmpty(tScAccountConfig.getId())) {
//			tScAccountConfig = tScAccountConfigService.getEntity(TScAccountConfigEntity.class, tScAccountConfig.getId());

//		}
		//查询完主库后，再要回到原数据源
		//是否开账 设到页面
		req.setAttribute("isAccountOpen",AccountUtil.isAccountOpen());
		DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, req);
		return new ModelAndView("com/qihang/buss/sc/sys/tScAccountConfig-update");
	}

	/**
	 * 账套设置查看页面跳转（含 系统设置和数据源）
	 *
	 * @return
	 */
	@RequestMapping(params = "goView")
	//@DataSource("dataSource_jeecg")
	public ModelAndView goView(TScAccountConfigEntity tScAccountConfig, HttpServletRequest req) {
		String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, req);
		if (StringUtil.isNotEmpty(tScAccountConfig.getId())) {
			tScAccountConfig = tScAccountConfigService.getEntity(TScAccountConfigEntity.class, tScAccountConfig.getId());
			String hql0 = "from DynamicDataSourceEntity where 1 = 1 AND accountId = ? ";
			String id0 = tScAccountConfig.getId();
			List<DynamicDataSourceEntity> dynamicDataSourceEntityList = systemService.findHql(hql0,id0);
			if (dynamicDataSourceEntityList.size()>0){
				DynamicDataSourceEntity tSDataSource = dynamicDataSourceEntityList.get(0);
				//从url中解析出数据库IP、端口、实例名或数据库名 信息
				Map<String, String> curDbMap = DBUtil.getDbParameterByUrl(tSDataSource.getUrl());
				String curip = curDbMap.containsKey("ip")?curDbMap.get("ip"):"";
				String curport = curDbMap.containsKey("port")?curDbMap.get("port"):"";
				String curdatabasename = curDbMap.containsKey("databasename")?curDbMap.get("databasename"):"";
				tSDataSource.setDbIp(curip);
				tSDataSource.setDbPort(curport);
				tSDataSource.setDbName(curdatabasename);
				req.setAttribute("tSDataSourcePage", tSDataSource);
			}
			req.setAttribute("tScAccountConfigPage", tScAccountConfig);
		}
		//查询完主库后，再要回到原数据源
		DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, req);
		return new ModelAndView("com/qihang/buss/sc/sys/tScAccountConfig-view");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "tScAccountConfigController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScAccountConfigEntity tScAccountConfig,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScAccountConfigEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScAccountConfig, request.getParameterMap());
		List<TScAccountConfigEntity> tScAccountConfigs = this.tScAccountConfigService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"账套设置");
		modelMap.put(NormalExcelConstants.CLASS,TScAccountConfigEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("账套设置列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, tScAccountConfigs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScAccountConfigEntity tScAccountConfig,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "账套设置");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScAccountConfigEntity.class);
		modelMap.put(TemplateExcelConstants.LIST_DATA,null);
		return TemplateExcelConstants.JEECG_TEMPLATE_EXCEL_VIEW;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(1);
			params.setNeedSave(true);
			try {
				List<TScAccountConfigEntity> listTScAccountConfigEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TScAccountConfigEntity.class,params);
				for (TScAccountConfigEntity tScAccountConfig : listTScAccountConfigEntitys) {
					tScAccountConfigService.save(tScAccountConfig);
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return j;
	}

	/**
	 * 加载明细列表[数据源]
	 *
	 * @return
	 */
	@RequestMapping(params = "tSDataSourceList")
	//@DataSource("dataSource_jeecg")
	public ModelAndView tSDataSourceList(TScAccountConfigEntity tScAccountConfig, HttpServletRequest req) {
		String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, req);

		//===================================================================================
		//获取参数
		Object id0 = tScAccountConfig.getId();
		//===================================================================================
		//查询-数据源
		String hql0 = "from DynamicDataSourceEntity where 1 = 1 AND accountId = ? ";
		try{
			List<DynamicDataSourceEntity> tSDataSourceEntityList = systemService.findHql(hql0,id0);
			req.setAttribute("tSDataSourceList", tSDataSourceEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		//查询完主库后，再要回到原数据源
		DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, req);
		return new ModelAndView("com/qihang/buss/sc/sys/tSDataSourceList");
	}

	/**
	 * 登录页面的账套弹出框列表
	 * @param tScAccountConfig
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "goSelectDialog")
	public ModelAndView goSelectDialog(TScAccountConfigEntity tScAccountConfig, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScAccountConfig.getDbKey())) {
			req.setAttribute("name", tScAccountConfig.getDbKey());
		}
		return new ModelAndView("login/tScAccountConfigSelect");
	}

	/**
	 * easyui AJAX请求数据-登录页面的账套弹出框的账套列表数据加载
	 * @param request
	 * @param response
	 * @param dataGrid
	 */
	@RequestMapping(params = "loadSelectDatagrid")
	//@DataSource("dataSource_jeecg")
	public void loadSelectDatagrid(TScAccountConfigEntity tScAccountConfig,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, request);
		//请在dataGrid的url里带field参数，系统会自动封装到dataGrid的field属性中
//		if (StringUtil.isNotEmpty(dataGrid.getField())){
//
//		}else{
//			String field = "id,dbKey,accountStartDate,companyName,contact,openState,closeState";
//			dataGrid.setField(field);
//		}
		CriteriaQuery cq = new CriteriaQuery(TScAccountConfigEntity.class, dataGrid);
		cq.notEq("id",Globals.ACCOUNT_SCM_ID);//不显示scm主库的账套
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScAccountConfig, request.getParameterMap());
		try{
			//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScAccountConfigService.getDataGridReturn(cq, true);
		//查询数据源,并计算出每个账套的账套名称
		List<DynamicDataSourceEntity> dynamicDataSourceEntityList = systemService.getList(DynamicDataSourceEntity.class);
		for (int i=0;i<cq.getDataGrid().getResults().size();i++){
			TScAccountConfigEntity tScAccountConfigEntity = (TScAccountConfigEntity) cq.getDataGrid().getResults().get(i);
			for (int j=0;j<dynamicDataSourceEntityList.size();j++){
				DynamicDataSourceEntity dynamicDataSourceEntity = dynamicDataSourceEntityList.get(j);
				if (tScAccountConfigEntity.getId().equals(dynamicDataSourceEntity.getAccountId())){
					tScAccountConfigEntity.setDbDescription(dynamicDataSourceEntity.getDescription());
					break;
				}
			}
		}
		//查询完主库后，再要回到原数据源
		DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, request);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 执行开账动作
	 * @param id
	 * @param request
	 * @return
	 * @author cxf
	 */
	@RequestMapping(params = "openAccount")
	@ResponseBody
	public AjaxJson openAcount(String id,String type,HttpServletRequest request){
		//String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, request);
		AjaxJson j = new AjaxJson();
		String sonId = ResourceUtil.getSessionUserName().getCurrentDepart().getId();
		String userId = ResourceUtil.getSessionUserName().getId();
		String userName = ResourceUtil.getSessionUserName().getRealName();
		//执行开账
		if("1".equals(type)) {
			TScAccountConfigEntity accountConfigEntity = systemService.getCurrentAccountConfigByAccountid(id);
			if (null != accountConfigEntity) {
				j = tScAccountConfigService.openAccount(accountConfigEntity.getId(),accountConfigEntity.getAccountStartDate(), sonId, userId, userName);
				message = "开账成功";
				if (j.isSuccess()) {
					String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, request);
					accountConfigEntity.setOpenState(1);
					accountConfigEntity.setOpenBy(userId);
					accountConfigEntity.setOpenName(userName);
					accountConfigEntity.setOpenDate(accountConfigEntity.getAccountStartDate());
					String updateSql = "update T_SC_ACCOUNT_CONFIG set openState=1,openBy='" + userId + "',openName='" + userName + "',openDate='" + accountConfigEntity.getAccountStartDate() + "' where id = '" + id + "'";
					systemService.updateBySqlString(updateSql);
					AccountUtil.clearAccountConfigCache(accountConfigEntity);
					systemService.addLog(message, Globals.Log_Type_OTHER, Globals.Log_Leavel_INFO);
					DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, request);
				}
			} else {
				j.setSuccess(false);
				j.setMsg("开账失败");
			}
		} else {
			//执行反开账
			j = tScAccountConfigService.unOpenAccount(id);
			if(j.isSuccess()) {
				String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, request);
				String updateSql = "update T_SC_ACCOUNT_CONFIG set openState=0,openBy='',openName='',openDate=null where id = '" + id + "'";
				systemService.updateBySqlString(updateSql);
				TScAccountConfigEntity accountConfigEntity = systemService.getEntity(TScAccountConfigEntity.class, id);
				AccountUtil.clearAccountConfigCache(accountConfigEntity);
				message = "反开账成功";
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, request);
			}
		}
		//
		return j;
	}
	/**
	 * 执行反开账动作
	 * @param id
	 * @param request
	 * @return
	 * @author cxf
	 */
	@RequestMapping(params = "unOpenAccount")
	@ResponseBody
	public AjaxJson unOpenAcount(String id,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		j = tScAccountConfigService.unOpenAccount(id);
		if(j.isSuccess()) {
			String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, request);
			String updateSql = "update T_SC_ACCOUNT_CONFIG set openState=0,openBy='',openName='',openDate=null where id = '" + id + "'";
			systemService.updateBySqlString(updateSql);
			TScAccountConfigEntity accountConfigEntity = systemService.getEntity(TScAccountConfigEntity.class, id);
			AccountUtil.clearAccountConfigCache(accountConfigEntity);
			message = "反开账成功";
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, request);
		}
		return j;
	}

	/**
	 * 打开开账页面
	 * @param request
	 * @return
	 * @author cxf
     */
	@RequestMapping(params = "goOpenAccount")
	public ModelAndView goOpenAccount(HttpServletRequest request){
		ModelAndView view = new ModelAndView("com/qihang/buss/sc/navigate/openAccountChoose");
		String sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, request);
		String accountId = ResourceUtil.getAccountId();//获取当前账套id
		TScAccountConfigEntity accountConfigEntity = systemService.getEntity(TScAccountConfigEntity.class, accountId);
		if(null != accountConfigEntity) {
			Integer state = accountConfigEntity.getOpenState();
			if (state > 0) {
				view.addObject("hasOpen", 2);
			} else {
				view.addObject("hasOpen", 1);
			}
			view.addObject("accountId", accountId);
			DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, request);
		}
		return view;
	}

	/**
	 * 根据导航页面参数检查当前登录账套的当前导航桌面按钮是否可操作
	 *   未设置值（默认为空字符串)即为不需要当前账套检查，
	 *   值为true表示如果当前账套已开账未结账才允许单据操作，值为init表示当前账套未开账才允许单据操作（用于初始化的几种单据）
	 * @param beforeAccount
	 * @param request
	 * @return
	 * @author cxf
	 */
	@RequestMapping(params = "navigatebeforeaccount")
	@ResponseBody
	public AjaxJson navigatebeforeaccount(String beforeAccount,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		boolean isOk = false;
		beforeAccount = beforeAccount==null?"":beforeAccount.trim().toLowerCase();
		if (!beforeAccount.equals("")) {
			boolean isAccountOpen = AccountUtil.isAccountOpen();
			if (beforeAccount.equals("true")) {//普通单据桌面导航检查
				if (isAccountOpen){//开账才能进行普通单据操作
					isOk = true;
				}else{
					isOk = false;
					j.setMsg("未开账不允许进行普通单据操作。");
				}
			} else if (beforeAccount.equals("init")) {//初始化单据桌面导航检查
				if (!isAccountOpen){//未开账才能进行初始化单据操作
					isOk = true;
				}else{
					isOk = false;
					j.setMsg("已开账不允许进行初始化单据操作。");
				}
			} else {
				isOk = true;
			}
		}else{
			isOk = true;
		}
		j.setSuccess(isOk);
		return j;
	}
}
