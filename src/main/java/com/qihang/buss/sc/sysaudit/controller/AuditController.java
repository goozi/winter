package com.qihang.buss.sc.sysaudit.controller;

import com.qihang.buss.sc.sysaudit.entity.TSAuditEntity;
import com.qihang.buss.sc.sysaudit.entity.TSAuditLeaveEntity;
import com.qihang.buss.sc.sysaudit.entity.TSBillInfoEntity;
import com.qihang.buss.sc.sysaudit.service.TSAuditServiceI;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.ComboTree;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.common.model.json.TreeGrid;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.*;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.tag.vo.datatable.SortDirection;
import com.qihang.winter.tag.vo.easyui.ComboTreeModel;
import com.qihang.winter.tag.vo.easyui.TreeGridModel;
import com.qihang.winter.web.system.pojo.base.*;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.web.system.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 多级审核功能类
 * 
 * @author  Zerrion
 * 
 */
@Scope("prototype")
@Controller
@RequestMapping("/auditController")
public class AuditController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger
			.getLogger(AuditController.class);
	private UserService userService;
	private SystemService systemService;
	private String message = null;

	@Autowired
	public void setSystemService(SystemService systemService) {
		this.systemService = systemService;
	}

	public UserService getUserService() {
		return userService;

	}

	@Autowired
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Autowired
	private TSAuditServiceI tSAuditService;

	/**
	 * 权限列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "function")
	public ModelAndView function() {
		return new ModelAndView("com/qihang/buss/sysaudit/auditFunctionList");
	}

	@RequestMapping(params = "price")
	public ModelAndView price() {
		return new ModelAndView("com/qihang/buss/sysaudit/billPriceList");
	}
	/**
	 * 操作列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "operation")
	public ModelAndView operation(HttpServletRequest request, @RequestParam(value="functionId",required = false)String functionId) {
		request.setAttribute("functionId", functionId);
		return new ModelAndView("system/operation/operationList");
	}

	/**
	 * 数据规则列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "dataRule")
	public ModelAndView operationData(HttpServletRequest request,
			@RequestParam(value = "functionId",required = false)String functionId) {
		request.setAttribute("functionId", functionId);
		return new ModelAndView("system/dataRule/ruleDataList");
	}

	/**
	 * easyuiAJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	@ResponseBody
	public List<Map<String,Object>> datagrid(HttpServletRequest request,HttpServletResponse response, DataGrid dataGrid) {
		String id = request.getParameter("id");
		String isPrice = request.getParameter("isPrice");//单价设置
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		if(StringUtils.isNotEmpty(id)){
			List<TSBillInfoEntity> infoList = new ArrayList<TSBillInfoEntity>();//systemService.findHql("from TSBillInfoEntity where id = ?",new Object[]{id});
			if("root".equals(id)){
				if(StringUtil.isEmpty(isPrice)) {
					infoList = systemService.findHql("from TSBillInfoEntity where isAudit = 1 and TSBillInfoEntity.id is null");
				} else {
					infoList = systemService.findHql("from TSBillInfoEntity where isPrice = 1 and TSBillInfoEntity.id is null");
				}
			} else {
				infoList = systemService.findHql("from TSBillInfoEntity where id = ? ",new Object[]{id});
			}
			List<TSBillInfoEntity> childInfo = new ArrayList<TSBillInfoEntity>();
			for(TSBillInfoEntity infoEntity : infoList) {
				if (infoEntity.getTSBillInfoEntitys().size() > 0) {
					getChildInfo(infoEntity, childInfo);
				} else if (null != infoEntity.getTSBillInfoEntity()) {
					childInfo.add(infoEntity);
				}
			}
			for(TSBillInfoEntity entity : childInfo) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id",entity.getId());
				map.put("billName", entity.getBillName());
				map.put("priceField",entity.getPriceField());
				String billId = entity.getBillId();
				List<TSAuditEntity> auditEntityList = systemService.findHql("from TSAuditEntity where billId = ? and sonId = ?", new Object[]{billId, depart.getId()});
				if(auditEntityList.size() > 0) {
					for(TSAuditEntity auditEntity : auditEntityList) {
						//TSAuditEntity auditEntity = systemService.findUniqueByProperty(TSAuditEntity.class, "billId", billId);
						//if (null != auditEntity) {
							List<TSAuditLeaveEntity> leaves = systemService.findHql("from TSAuditLeaveEntity where pid = ? order by leaveNum asc ", new Object[]{auditEntity.getId()});
							for (int i = 0; i < leaves.size(); i++) {
								TSAuditLeaveEntity leave = leaves.get(i);
								String userId = leave.getAuditorId();
								if (StringUtil.isNotEmpty(userId)) {
									String[] ids = userId.split(",");
									String uIdStr = "";
									for (String idStr : ids) {
										uIdStr += "'" + idStr + "',";
									}
									if (uIdStr.length() > 0) {
										uIdStr = uIdStr.substring(0, uIdStr.length() - 1);
									}
									List<TSUser> userList = systemService.findHql("from TSUser where id in (" + uIdStr + ")");
									String userName = "";
									for (TSUser user : userList) {
										userName += user.getRealName() + ",";
									}
									if (StringUtils.isNotEmpty(userName)) {
										userName = userName.substring(0, userName.length() - 1);
									}
									map.put("user" + (i + 1), userName);
								}
							}
						//}
					}
				}
				list.add(map);
			}
		} else {
			List<TSBillInfoEntity> infoList = systemService.findHql("from TSBillInfoEntity where TSBillInfoEntity.id is null");
			List<TSBillInfoEntity> childInfo = new ArrayList<TSBillInfoEntity>();
			for(TSBillInfoEntity infoEntity : infoList) {
				if (infoEntity.getTSBillInfoEntitys().size() > 0) {
					getChildInfo(infoEntity, childInfo);
				} else if (null != infoEntity.getTSBillInfoEntity()) {
					childInfo.add(infoEntity);
				}
			}
		}
		return list;
//		CriteriaQuery cq = new CriteriaQuery(TSFunction.class, dataGrid);
//		if(StringUtil.isNotEmpty(projectCode) || !"00".equals(projectCode)){
//			//一级菜单且排序号以项目代码开头或开发平台自带的菜单，或除一级菜单外的所有菜单
//			cq.or(Restrictions.and(Restrictions.or(Restrictions.like("functionOrder",projectCode+"%"),Restrictions.like("functionOrder","99%")),Restrictions.eq("functionLevel",
//							Short.valueOf("0"))),Restrictions.not(Restrictions.eq("functionLevel",Short.valueOf("0"))));
//		}
//		cq.add();
//		this.systemService.getDataGridReturn(cq, true);
//		TagUtil.datagrid(response, dataGrid);
	}

	public void getChildInfo(TSBillInfoEntity parent,List<TSBillInfoEntity> result){
		List<TSBillInfoEntity> child = parent.getTSBillInfoEntitys();
		for(TSBillInfoEntity info : child){
			if(info.getTSBillInfoEntitys().size() == 0){
				result.add(info);
			}else{
				getChildInfo(info,result);
			}
		}
	}

	/**
	 * easyuiAJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "opdategrid")
	public void opdategrid(HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSOperation.class, dataGrid);
		String functionId = oConvertUtils.getString(request
				.getParameter("functionId"));
		cq.eq("TSFunction.id", functionId);
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除权限
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(TSAuditEntity auditEntity, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		//获取数据类型数据；
		TSBillInfoEntity info = systemService.getEntity(TSBillInfoEntity.class, auditEntity.getId());
		auditEntity = systemService.findUniqueByProperty(TSAuditEntity.class,"billId",info.getBillId());
		TSAuditLeaveEntity leaveEntity = new TSAuditLeaveEntity();
		leaveEntity.setPid(auditEntity.getId());
		List<TSAuditLeaveEntity> leaveEntityList = systemService.findByExample(TSAuditLeaveEntity.class.getName(),leaveEntity);
		message = "清除成功";
		if(leaveEntityList.size() > 0) {
		systemService.updateBySqlString("delete from T_S_Audit_Leave where pid='"+ auditEntity.getId() + "'");
			try {
				systemService.delete(auditEntity);
			} catch (Exception e) {
				e.printStackTrace();
				message = "清除失败："+e.getMessage();
			}
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 删除操作
	 * 
	 * @param operation
	 * @return
	 */
	@RequestMapping(params = "delop")
	@ResponseBody
	public AjaxJson delop(TSOperation operation, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		operation = systemService.getEntity(TSOperation.class,
				operation.getId());
		message = MutiLangUtil.paramDelSuccess("common.operation");
		userService.delete(operation);
		systemService.addLog(message, Globals.Log_Type_DEL,
				Globals.Log_Leavel_INFO);

		j.setMsg(message);

		return j;
	}

	/**
	 * 递归更新子菜单的FunctionLevel
	 * @param subFunction
	 * @param parent
	 */
	private void updateSubFunction(List<TSFunction> subFunction,TSFunction  parent) {
		if(subFunction.size() ==0){//没有子菜单是结束
              return;
       }else{
    	   for (TSFunction tsFunction : subFunction) {
				tsFunction.setFunctionLevel(Short.valueOf(parent.getFunctionLevel()
						+ 1 + ""));
				systemService.saveOrUpdate(tsFunction);
				List<TSFunction> subFunction1 = systemService.findByProperty(TSFunction.class, "TSFunction.id", tsFunction.getId());
				updateSubFunction(subFunction1,tsFunction);
		   }
       }
	}
	
	/**
	 * 权限录入
	 * 
	 * @param function
	 * @return
	 */
	@RequestMapping(params = "saveFunction")
	@ResponseBody
	public AjaxJson saveFunction(TSFunction function, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		function.setFunctionUrl(function.getFunctionUrl().trim());
		String functionOrder = function.getFunctionOrder();
		if (StringUtils.isEmpty(functionOrder)) {
			function.setFunctionOrder("0");
		}
		if (function.getTSFunction().getId().equals("")) {
			function.setTSFunction(null);
		} else {
			TSFunction parent = systemService.getEntity(TSFunction.class,
					function.getTSFunction().getId());
			function.setFunctionLevel(Short.valueOf(parent.getFunctionLevel()
					+ 1 + ""));
		}
		if (StringUtil.isNotEmpty(function.getId())) {
			message = MutiLangUtil.paramUpdSuccess("common.menu");
			userService.saveOrUpdate(function);
			systemService.addLog(message, Globals.Log_Type_UPDATE,
					Globals.Log_Leavel_INFO);
			List<TSFunction> subFunction = systemService.findByProperty(TSFunction.class, "TSFunction.id", function.getId());
			updateSubFunction(subFunction,function);

			systemService.flushRoleFunciton(function.getId(), function);

		} else {
			if (function.getFunctionLevel().equals(Globals.Function_Leave_ONE)) {
				List<TSFunction> functionList = systemService.findByProperty(
						TSFunction.class, "functionLevel",
						Globals.Function_Leave_ONE);
				// int ordre=functionList.size()+1;
				// function.setFunctionOrder(Globals.Function_Order_ONE+ordre);
				function.setFunctionOrder(function.getFunctionOrder());
			} else {
				List<TSFunction> functionList = systemService.findByProperty(
						TSFunction.class, "functionLevel",
						Globals.Function_Leave_TWO);
				// int ordre=functionList.size()+1;
				// function.setFunctionOrder(Globals.Function_Order_TWO+ordre);
				function.setFunctionOrder(function.getFunctionOrder());
			}
			message = MutiLangUtil.paramAddSuccess("common.menu");
			systemService.save(function);
			systemService.addLog(message, Globals.Log_Type_INSERT,
					Globals.Log_Leavel_INFO);
		}

		j.setMsg(message);
		return j;
	}

	/**
	 * 操作录入
	 * 
	 * @param operation
	 * @return
	 */
	@RequestMapping(params = "saveop")
	@ResponseBody
	public AjaxJson saveop(TSOperation operation, HttpServletRequest request) {
		String pid = request.getParameter("TSFunction.id");
		if (pid.equals("")) {
			operation.setTSFunction(null);
		}
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(operation.getId())) {
			message = MutiLangUtil.paramUpdSuccess("common.operation");
			userService.saveOrUpdate(operation);
			systemService.addLog(message, Globals.Log_Type_UPDATE,
					Globals.Log_Leavel_INFO);
		} else {
			message = MutiLangUtil.paramAddSuccess("common.operation");
			userService.save(operation);
			systemService.addLog(message, Globals.Log_Type_INSERT,
					Globals.Log_Leavel_INFO);
		}

		j.setMsg(message);
		return j;
	}

	/**
	 * 权限列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(TSFunction function, HttpServletRequest req) {
		String functionid = req.getParameter("id");
		List<TSFunction> fuinctionlist = systemService
				.getList(TSFunction.class);
		req.setAttribute("flist", fuinctionlist);
		// List<TSIcon> iconlist = systemService.getList(TSIcon.class);
		List<TSIcon> iconlist = systemService
				.findByQueryString("from TSIcon where iconType != 3");
		req.setAttribute("iconlist", iconlist);
		List<TSIcon> iconDeskList = systemService
				.findByQueryString("from TSIcon where iconType = 3");
		req.setAttribute("iconDeskList", iconDeskList);
		if (functionid != null) {
			function = systemService.getEntity(TSFunction.class, functionid);
			req.setAttribute("function", function);
		}
		if (function.getTSFunction() != null
				&& function.getTSFunction().getId() != null) {
			function.setFunctionLevel((short) 1);
			function.setTSFunction((TSFunction) systemService.getEntity(
					TSFunction.class, function.getTSFunction().getId()));
			req.setAttribute("function", function);
		}
		return new ModelAndView("system/function/function");
	}

	/**
	 * 操作列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdateop")
	public ModelAndView addorupdateop(TSOperation operation,
			HttpServletRequest req) {
		List<TSIcon> iconlist = systemService.getList(TSIcon.class);
		req.setAttribute("iconlist", iconlist);
		if (operation.getId() != null) {
			operation = systemService.getEntity(TSOperation.class,
					operation.getId());
			req.setAttribute("operation", operation);
		}
		String functionId = oConvertUtils.getString(req
				.getParameter("functionId"));
		req.setAttribute("functionId", functionId);
		return new ModelAndView("system/operation/operation");
	}

	/**
	 * 权限列表
	 */
	@RequestMapping(params = "functionGrid")
	@ResponseBody
	public List<TreeGrid> functionGrid(@Value("#{sysProp[PROJECT_CODE]}")String projectCode,HttpServletRequest request,
																		 TreeGrid treegrid) {
		CriteriaQuery cq = new CriteriaQuery(TSFunction.class);
		if(StringUtil.isNotEmpty(projectCode) && !"00".equals(projectCode) && !ResourceUtil.getSessionUserName().getUserName().equals("programmer")){
			//一级菜单且排序号以项目代码开头或开发平台自带的菜单，或除一级菜单外的所有菜单
			cq.or(Restrictions.and(Restrictions.like("functionOrder",projectCode+"%"),Restrictions.eq("functionLevel",
							Short.valueOf("0"))),Restrictions.not(Restrictions.eq("functionLevel",Short.valueOf("0"))));
		}
		String selfId = request.getParameter("selfId");
		if (selfId != null) {
			cq.notEq("id", selfId);
		}
		if (treegrid.getId() != null) {
			cq.eq("TSFunction.id", treegrid.getId());
		}
		if (treegrid.getId() == null) {
			cq.isNull("TSFunction");
		}

		cq.addOrder("functionOrder", SortDirection.asc);
		cq.add();
		List<TSFunction> functionList = systemService.getListByCriteriaQuery(cq, false);
        Collections.sort(functionList, new NumberComparator());
        List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		TreeGridModel treeGridModel = new TreeGridModel();
		treeGridModel.setIcon("TSIcon_iconPath");
		treeGridModel.setTextField("functionName");
		treeGridModel.setParentText("TSFunction_functionName");
		treeGridModel.setParentId("TSFunction_id");
		treeGridModel.setSrc("functionUrl");
		treeGridModel.setIdField("id");
		treeGridModel.setChildList("TSFunctions");
		// 添加排序字段
		treeGridModel.setOrder("functionOrder");

		treeGridModel.setFunctionType("functionType");

		treeGrids = systemService.treegrid(functionList, treeGridModel);

		MutiLangUtil.setMutiTree(treeGrids);
		return treeGrids;
	}

	/**
	 * 权限列表
	 */
	@RequestMapping(params = "functionList")
	@ResponseBody
	public void functionList(HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSFunction.class, dataGrid);
		String id = oConvertUtils.getString(request.getParameter("id"));
		cq.isNull("TSFunction");
		if (id != null) {
			cq.eq("TSFunction.id", id);
		}
		cq.add();
		List<TSFunction> functionList = systemService.getListByCriteriaQuery(
				cq, false);
		List<TreeGrid> treeGrids = new ArrayList<TreeGrid>();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 父级权限下拉菜单
	 */
	@RequestMapping(params = "setPFunction")
	@ResponseBody
	public List<ComboTree> setPFunction(HttpServletRequest request,
																			ComboTree comboTree) {
		CriteriaQuery cq = new CriteriaQuery(TSFunction.class);
		if (null != request.getParameter("selfId")) {
			cq.notEq("id", request.getParameter("selfId"));
		}
		if (comboTree.getId() != null) {
			cq.eq("TSFunction.id", comboTree.getId());
		}
		if (comboTree.getId() == null) {
			cq.isNull("TSFunction");
		}
		cq.add();
		List<TSFunction> functionList = systemService.getListByCriteriaQuery(
				cq, false);
		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id",
				"functionName", "TSFunctions");
		comboTrees = systemService.ComboTree(functionList, comboTreeModel,
				null, false);
		MutiLangUtil.setMutiTree(comboTrees);
		return comboTrees;
	}
	/**
	 * 菜单模糊检索功能
	 * 
	 * @return
	 */
	@RequestMapping(params = "searchApp")
	public ModelAndView searchApp(TSFunction function, HttpServletRequest req) {

		String name = req.getParameter("name");
		String menuListMap = "";
		// String menuListMap =
		// "<div class='appListContainer ui-sortable' customacceptdrop='0' index='0' _olddisplay='block' style='width: auto; height: auto; margin-left: 10px; margin-top: 10px; display: block;'>";
		CriteriaQuery cq = new CriteriaQuery(TSFunction.class);

		cq.notEq("functionLevel", Short.valueOf("0"));
		if (name == null || "".equals(name)) {
			cq.isNull("TSFunction");
		} else {
			String name1 = "%" + name + "%";
			cq.like("functionName", name1);
		}
		cq.add();
		List<TSFunction> functionList = systemService.getListByCriteriaQuery(
				cq, false);
		if (functionList.size() > 0 && functionList != null) {
			for (int i = 0; i < functionList.size(); i++) {
				// menuListMap = menuListMap +
				// "<div id='menuList_area'  class='menuList_area'>";
				String icon = "";
				if (!"".equals(functionList.get(i).getTSIconDesk())
						&& functionList.get(i).getTSIconDesk() != null) {
					icon = functionList.get(i).getTSIconDesk().getIconPath();
				} else {
					icon = "plug-in/sliding/icon/default.png";
				}
				menuListMap = menuListMap
						+ "<div type='"
						+ i
						+ 1
						+ "' class='menuSearch_Info' id='"
						+ functionList.get(i).getId()
						+ "' title='"
						+ functionList.get(i).getFunctionName()
						+ "' url='"
						+ functionList.get(i).getFunctionUrl()
						+ "' icon='"
						+ icon
						+ "' style='float:left;left: 0px; top: 0px;margin-left: 30px;margin-top: 20px;'>"
						+ "<div ><img alt='"
						+ functionList.get(i).getFunctionName()
						+ "' src='"
						+ icon
						+ "'></div>"
						+ "<div class='appButton_appName_inner1' style='color:#333333;'>"
						+ functionList.get(i).getFunctionName() + "</div>"
						+ "<div class='appButton_appName_inner_right1'></div>" +
						// "</div>" +
						"</div>";
			}
		} else {
			menuListMap = menuListMap + "很遗憾，在系统中没有检索到与“" + name + "”相关的信息！";
		}
		// menuListMap = menuListMap + "</div>";
		System.out.println("-------------------------------" + menuListMap);
		req.setAttribute("menuListMap", menuListMap);
		return new ModelAndView("system/function/menuAppList");
	}

	/**
	 * 
	 * addorupdaterule 数据规则权限的编辑和新增
	 * 
	 * @Title: addorupdaterule
	 * @Description: TODO
	 * @param @param operation
	 * @param @param req
	 * @param @return 设定文件
	 * @return ModelAndView 返回类型
	 * @throws
	 */
	@RequestMapping(params = "addorupdaterule")
	public ModelAndView addorupdaterule(TSDataRule operation,
			HttpServletRequest req) {
		List<TSIcon> iconlist = systemService.getList(TSIcon.class);
		req.setAttribute("iconlist", iconlist);
		if (operation.getId() != null) {
			operation = systemService.getEntity(TSDataRule.class,
					operation.getId());
			req.setAttribute("operation", operation);
		}
		String functionId = oConvertUtils.getString(req
				.getParameter("functionId"));
		req.setAttribute("functionId", functionId);
		return new ModelAndView("system/dataRule/ruleData");
	}

	/**
	 * 
	 * opdategrid 数据规则的列表界面
	 * 
	 * @Title: opdategrid
	 * @Description: TODO
	 * @param @param request
	 * @param @param response
	 * @param @param dataGrid 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	@RequestMapping(params = "ruledategrid")
	public void ruledategrid(HttpServletRequest request,
			HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSDataRule.class, dataGrid);
		String functionId = oConvertUtils.getString(request
				.getParameter("functionId"));
		cq.eq("TSFunction.id", functionId);
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 
	 * delrule 删除数据权限规则
	 * 
	 * @Title: delrule
	 * @Description: TODO
	 * @param @param operation
	 * @param @param request
	 * @param @return 设定文件
	 * @return AjaxJson 返回类型
	 * @throws
	 */
	@RequestMapping(params = "delrule")
	@ResponseBody
	public AjaxJson delrule(TSDataRule operation, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		operation = systemService
				.getEntity(TSDataRule.class, operation.getId());
		message = MutiLangUtil.paramDelSuccess("common.operation");
		userService.delete(operation);
		systemService.addLog(message, Globals.Log_Type_DEL,
				Globals.Log_Leavel_INFO);

		j.setMsg(message);

		return j;
	}

	/**
	 * 
	 * saverule保存规则权限值
	 * 
	 * @Title: saverule
	 * @Description: TODO
	 * @param @param operation
	 * @param @param request
	 * @param @return 设定文件
	 * @return AjaxJson 返回类型
	 * @throws
	 */
	@RequestMapping(params = "saverule")
	@ResponseBody
	public AjaxJson saverule(TSDataRule operation, HttpServletRequest request) {

		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(operation.getId())) {
			message = MutiLangUtil.paramUpdSuccess("common.operation");
			userService.saveOrUpdate(operation);
			systemService.addLog(message, Globals.Log_Type_UPDATE,
					Globals.Log_Leavel_INFO);
		} else {
			if (justHaveDataRule(operation) <= 5) {
				message = MutiLangUtil.paramAddSuccess("common.operation");
				userService.save(operation);
				systemService.addLog(message, Globals.Log_Type_INSERT,
						Globals.Log_Leavel_INFO);
			} else {

				message = "同一操作字段规则不可超过5条";
			}
		}
		j.setMsg(message);
		return j;
	}

	public int justHaveDataRule(TSDataRule dataRule) {
		String sql = "SELECT id FROM t_s_data_rule WHERE functionId='"+dataRule.getTSFunction()
				.getId()+"' AND rule_column='"+dataRule.getRuleColumn()+"' AND rule_conditions='"+dataRule
				.getRuleConditions()+"'";
		
		List<String> hasOperList = this.systemService.findListbySql(sql);
		return hasOperList.size();
	}


	@RequestMapping(params = "getMenuTree")
	@ResponseBody
	public List<ComboTree> getMenuTree(HttpServletRequest request){
		String projectCode = ResourceUtil.getConfigByName("PROJECT_CODE");
		CriteriaQuery cq = new CriteriaQuery(TSBillInfoEntity.class);
//		cq.add(Restrictions.and(Restrictions.like("functionOrder", projectCode + "%"), Restrictions.eq("functionLevel",
//				Short.valueOf("0"))));
		cq.add(Restrictions.isNull("TSBillInfoEntity"));
		String isPrice = request.getParameter("isPrice");
		if(StringUtil.isEmpty(isPrice)) {
			cq.add(Restrictions.eq("isAudit", 1));
		} else {
			cq.add(Restrictions.eq("isPrice",1));
		}
		List<TSBillInfoEntity>  list  = systemService.getListByCriteriaQuery(cq,false);

		List<ComboTree> comboTrees = new ArrayList<ComboTree>();
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "billName", "TSBillInfoEntitys");
		List<ComboTree> childInfo = systemService.ComboTree(list, comboTreeModel, null, true);
		ComboTree root = new ComboTree();
		root.setId("root");
		root.setText("单据类型");
		root.setChildren(childInfo);
		root.setState("closed");
        comboTrees.add(root);
		return comboTrees;
	}

}
