package com.qihang.winter.web.system.controller.core;


import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.util.StringUtils;
import com.qihang.winter.core.common.model.json.ComboTree;
import com.qihang.winter.core.util.ResourceUtil;
import com.qihang.winter.tag.vo.easyui.ComboTreeModel;
import com.qihang.winter.web.system.pojo.base.*;
import com.qihang.winter.web.system.service.TSTreeinfoServiceI;
import org.springframework.context.annotation.Scope;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.web.system.service.SystemService;


/**   
 * @Title: Controller
 * @Description: 流程树表
 * @author onlineGenerator
 * @date 2016-04-28 09:20:25
 * @version V1.0   
 *
 */
@Scope("prototype") 
@Controller
@RequestMapping("/tSTreeinfoController")
public class TSTreeinfoController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TSTreeinfoController.class);

	@Autowired
	private TSTreeinfoServiceI tSTreeinfoService;
	@Autowired
	private SystemService systemService;


	/**
	 * 流程树表列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tSTreeinfo")
	public ModelAndView tSTreeinfo(HttpServletRequest request) {
		return new ModelAndView("system/tree/tSTreeinfoList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TSTreeinfoEntity tSTreeinfo,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSTreeinfoEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSTreeinfo);
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
//		this.tSTreeinfoService.getDataGridReturn(cq, true);
//		List<TSTreeinfoEntity> result = dataGrid.getResults();
//		for(TSTreeinfoEntity entity : result){
//			String actId = entity.getActId();
//			String actName = tSTreeinfoService.getActNameById(actId);
//			entity.setActName(actName);
//		}
//		dataGrid.setResults(result);
		StringBuffer sql = new StringBuffer();
		sql.append("select a.*,b.id eid,b.node_name,b.type,b.value,'' actName from t_s_treeInfo a left join t_s_treeInfo_entry b on a.id=b.parent_id ");
		List<Map<String,Object>> infoList = this.tSTreeinfoService.findForJdbc(sql.toString(),dataGrid.getPage(),dataGrid.getRows());
		List<Object> objList = this.tSTreeinfoService.findListbySql(sql.toString());
		for(Map<String,Object> entity : infoList){
			String actId = (String) entity.get("act_id");
			String actName = tSTreeinfoService.getActNameById(actId);
			entity.put("actName",actName);
		}
		dataGrid.setResults(infoList);
		dataGrid.setTotal(objList.size());
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除流程树表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TSTreeinfoEntryEntity tSTreeinfo, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tSTreeinfo = systemService.getEntity(TSTreeinfoEntryEntity.class, tSTreeinfo.getId());
		String message = "流程树表删除成功";
		try{
			tSTreeinfoService.delMain(tSTreeinfo);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "流程树表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除流程树表
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "流程树表删除成功";
		try{
			for(String id:ids.split(",")){
				TSTreeinfoEntryEntity tSTreeinfo = systemService.getEntity(TSTreeinfoEntryEntity.class,
				id
				);
				tSTreeinfoService.delMain(tSTreeinfo);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "流程树表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加流程树表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TSTreeinfoEntity tSTreeinfo,TSTreeinfoPage tSTreeinfoPage, HttpServletRequest request) {
		List<TSTreeinfoEntryEntity> tSTreeinfoEntryList =  tSTreeinfoPage.getTSTreeinfoEntryList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			tSTreeinfoService.addMain(tSTreeinfo, tSTreeinfoEntryList);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "流程树表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新流程树表
	 * 
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TSTreeinfoEntity tSTreeinfo,TSTreeinfoPage tSTreeinfoPage, HttpServletRequest request) {
		List<TSTreeinfoEntryEntity> tSTreeinfoEntryList =  tSTreeinfoPage.getTSTreeinfoEntryList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			tSTreeinfo.setUpdateDate(new Date());
			tSTreeinfoService.updateMain(tSTreeinfo, tSTreeinfoEntryList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新流程树表失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 流程树表新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TSTreeinfoEntity tSTreeinfo, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSTreeinfo.getId())) {
			tSTreeinfo = tSTreeinfoService.getEntity(TSTreeinfoEntity.class, tSTreeinfo.getId());
			req.setAttribute("tSTreeinfoPage", tSTreeinfo);
		}
		return new ModelAndView("system/tree/tSTreeinfo-add");
	}
	
	/**
	 * 流程树表编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TSTreeinfoEntity tSTreeinfo, HttpServletRequest req) throws ParseException {
		if (StringUtil.isNotEmpty(tSTreeinfo.getId())) {
			tSTreeinfo = tSTreeinfoService.getEntity(TSTreeinfoEntity.class, tSTreeinfo.getId());
			req.setAttribute("tSTreeinfoPage", tSTreeinfo);
		}
		return new ModelAndView("system/tree/tSTreeinfo-update");
	}
	
	
	/**
	 * 加载明细列表[流程树配置]
	 * 
	 * @return
	 */
	@RequestMapping(params = "tSTreeinfoEntryList")
	public ModelAndView tSTreeinfoEntryList(TSTreeinfoEntity tSTreeinfo, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object id0 = tSTreeinfo.getId();
		//===================================================================================
		//查询-流程树配置
	    String hql0 = "from TSTreeinfoEntryEntity where 1 = 1 AND pARENT_ID = ? ";
	    try{
	    	List<TSTreeinfoEntryEntity> tSTreeinfoEntryEntityList = systemService.findHql(hql0,id0);
			req.setAttribute("tSTreeinfoEntryList", tSTreeinfoEntryEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("system/tree/tSTreeinfoEntryList");
	}

	@RequestMapping(params = "nodeTask")
	public ModelAndView nodeTask(HttpServletRequest request){
		String act_id = request.getParameter("act_id");
		String node_id = request.getParameter("node_id");
		request.setAttribute("act_id", act_id);
		request.setAttribute("node_id",node_id);
		return new ModelAndView("system/tree/nodeTask");
	}

	@RequestMapping(params = "loadNodeTask")
	@ResponseBody
	public List<ComboTree> loadNodeTask(HttpServletRequest request){
		ComboTreeModel comboTreeModel = new ComboTreeModel("id", "taskName", "");
		String projectCode = ResourceUtil.getConfigByName("PROJECT_CODE");
		String act_id = request.getParameter("act_id");
		String node_id = request.getParameter("node_id");
		List<Map<String,Object>> orgRoleArrList = systemService
				.findForJdbc(
						"select r.* from t_act_task r, t_tree_task_relation ro, t_s_treeInfo_entry o WHERE r.id=ro.task_node_id AND ro.tree_node_id = o.id AND o.id='" + node_id + "' and r.process_instance_id like '%"+act_id+"%'");
		List<TActTaskEntity> orgRoleList = new ArrayList<TActTaskEntity>();
		for (Map<String,Object> roleArr : orgRoleArrList) {
			TActTaskEntity role = new TActTaskEntity();
			role.setId((String) roleArr.get("id"));
			role.setTaskId((String) roleArr.get("task_id"));
			role.setTaskName((String) roleArr.get("task_name"));
			orgRoleList.add(role);
		}

		List<Map<String,Object>> allList = systemService.findForJdbc("select * from t_act_task r where r.process_instance_id like '%" + act_id + "%'");
		List<TActTaskEntity> allInfoList = new ArrayList<TActTaskEntity>();
		for(Map<String,Object> map : allList){
			TActTaskEntity entity = new TActTaskEntity();
			entity.setId((String) map.get("id"));
			entity.setTaskName((String) map.get("task_name"));
			allInfoList.add(entity);
		}
		List<ComboTree> comboTrees = systemService.ComboTree(allInfoList,
				comboTreeModel, orgRoleList, false);

		return comboTrees;
	}


	@RequestMapping(params = "saveNodeTask")
	@ResponseBody
	public AjaxJson saveNodeTask(HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String nodeId = request.getParameter("node_id");
		String taskIds = request.getParameter("taskIds");
		String actId = request.getParameter("act_id");
		j.setSuccess(true);
		j.setMsg("保存成功");
		if(!StringUtils.isEmpty(nodeId) && !StringUtils.isEmpty(taskIds)){
			String delSql = "delete from t_tree_task_relation where tree_node_id=?";
			systemService.executeSql(delSql, nodeId);
			String[] roleIdsArr = taskIds.split(",");
			List<TreeTaskRelationEntity> roleNodes = new ArrayList<TreeTaskRelationEntity>();
			for(String taskId : roleIdsArr){
				TreeTaskRelationEntity treeTask = new TreeTaskRelationEntity();
				treeTask.setTreeNodeId(nodeId);
				treeTask.setTaskNodeId(taskId);
				treeTask.setProcessInstanceId(actId);
				roleNodes.add(treeTask);
			}
			try {
				this.systemService.batchSave(roleNodes);
			}catch (Exception e){
				j.setSuccess(false);
				j.setMsg(e.getMessage());
			}
		}
		return j;
	}
}
