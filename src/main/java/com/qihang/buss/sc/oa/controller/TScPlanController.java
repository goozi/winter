package com.qihang.buss.sc.oa.controller;

import com.alibaba.fastjson.JSON;
import com.qihang.buss.sc.oa.entity.TScDailyEntity;
import com.qihang.buss.sc.oa.entity.TScPlanEntity;
import com.qihang.buss.sc.oa.entity.TScPlanFileEntity;
import com.qihang.buss.sc.oa.service.TScNoticeServiceI;
import com.qihang.buss.sc.oa.service.TScPlanServiceI;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.*;
import com.qihang.winter.poi.excel.ExcelImportUtil;
import com.qihang.winter.poi.excel.entity.ExportParams;
import com.qihang.winter.poi.excel.entity.ImportParams;
import com.qihang.winter.poi.excel.entity.TemplateExportParams;
import com.qihang.winter.poi.excel.entity.vo.NormalExcelConstants;
import com.qihang.winter.poi.excel.entity.vo.TemplateExcelConstants;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.web.system.manager.ClientManager;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.pojo.base.TSUser;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.web.system.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Title: Controller
 * @Description: 工作计划
 * @author onlineGenerator
 * @date 2015-11-26 13:44:24
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScPlanController")
public class TScPlanController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScPlanController.class);

	@Autowired
	private UserService userService;
	@Autowired
	private TScNoticeServiceI tScNoticeService;
	@Autowired
	private TScPlanServiceI tScPlanService;
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
	 * 工作计划列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScPlan")
	public ModelAndView tScPlan(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/oa/tScPlanList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScPlanEntity tScPlan,String type,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScPlanEntity.class, dataGrid);
		//查询条件组装器
		HttpSession session = ContextHolderUtils.getSession();
		TSUser user = new TSUser();
		if (ClientManager.getInstance().getClient(session.getId()) != null) {
			user = ClientManager.getInstance().getClient(session.getId()).getUser();
		}
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScPlan, request.getParameterMap());
		try{
			//自定义追加查询条件
			String q1 = user.getId()+",%";
			String q2 = "%"+user.getId()+"%";
			String q3 = "%,"+user.getId();
			if("1".equals(type)) {
				cq.eq("createBy", user.getUserName());
			}else if("2".equals(type)){
				cq.or(Restrictions.like("planGroup", q1), Restrictions.or(Restrictions.like("planGroup",q2),Restrictions.or(Restrictions.like("planGroup", q3), Restrictions.like("planGroup",user.getId()))));
			}else if("3".equals(type)){
				cq.or(Restrictions.like("planMaster", q1), Restrictions.or(Restrictions.like("planMaster",q2),Restrictions.or(Restrictions.like("planMaster", q3), Restrictions.like("planMaster", user.getId()))));
			}else if("4".equals(type)){
				cq.or(Restrictions.like("planLeadder", q1), Restrictions.or(Restrictions.like("planLeadder",q2),Restrictions.or(Restrictions.like("planLeadder", q3), Restrictions.like("planLeadder", user.getId()))));
			}else{
				//cq.eq("createBy", user.getUserName());
				cq.or(Restrictions.like("planGroup", q1), Restrictions.or(Restrictions.like("planGroup",q2),Restrictions.or(Restrictions.like("planGroup", q3), Restrictions.like("planGroup", user.getId()))));

			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScPlanService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除工作计划
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScPlanEntity tScPlan, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScPlan = systemService.getEntity(TScPlanEntity.class, tScPlan.getId());
		message = "工作计划删除成功";
        if(tScPlan.getPlanProgress() == Double.parseDouble("0")) {
            try {
				List<TScDailyEntity> dailyList = systemService.findByProperty(TScDailyEntity.class,"workPlan",tScPlan.getId());
				if(dailyList != null && dailyList.size() > 0) {
					message = "该工作计划已被使用，不可删除，请确认";
				}else{
					tScPlanService.delete(tScPlan);
					systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
				}
            } catch (Exception e) {
                e.printStackTrace();
                message = "工作计划删除失败";
                throw new BusinessException(e.getMessage());
            }
        }else{
            if(tScPlan.getPlanProgress() < Double.parseDouble("100")) {
                message = "进行中的工作计划无法删除，请确认";
            }else{
                message = "已完成的工作计划无法删除，请确认";
            }
        }
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除工作计划
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "工作计划删除成功";
		try{
			for(String id:ids.split(",")){
				TScPlanEntity tScPlan = systemService.getEntity(TScPlanEntity.class,id);
				List<TScDailyEntity> dailyList = systemService.findByProperty(TScDailyEntity.class,"workPlan",id);
				if(dailyList != null && dailyList.size() > 0) {
					message = "该工作计划已被使用，不可删除，请确认";
				}else{
					tScPlanService.delete(tScPlan);
					systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "工作计划删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加工作计划
	 * 
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScPlanEntity tScPlan, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "工作计划添加成功";
		try{
			tScPlan.setPlanProgress(Double.parseDouble("0"));
			tScPlan.setPlanState("计划中");
			String streamName = tScPlan.getStreamName();
            String fileName = tScPlan.getFileName();
			if(StringUtils.isNotEmpty(streamName)) {
				String[] filepath = streamName.split(",");
                String[] fileNames = fileName.split(",");
				List<TScPlanFileEntity> files = new ArrayList<TScPlanFileEntity>();
				for(int i = 0 ; i < filepath.length ; i++){
                    String path = filepath[i];
                    String name = fileNames[i];
					TScPlanFileEntity file = new TScPlanFileEntity();
					file.setStreamName(path);
                    file.setRealName(name);
                    file.setParentFileObj(tScPlan);
					files.add(file);
				}
				tScPlan.setChildernFile(files);
			}
            String info = tScPlan.getPlanInfo();
            if(StringUtils.isNotEmpty(info)) {
                info = info.replaceAll("\"", "'");
                tScPlan.setPlanInfo(info);
            }
            String userId = tScPlan.getUserId();
            if(StringUtils.isNotEmpty(userId)) {
                TSUser user = systemService.get(TSUser.class, userId);
                tScPlan.setCreateBy(user.getUserName());
                tScPlan.setCreateName(user.getRealName());
                tScPlan.setCreateDate(new Date());
            }
			tScPlanService.save(tScPlan);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "工作计划添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新工作计划
	 * 
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScPlanEntity tScPlan, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "工作计划更新成功";
		TScPlanEntity t = tScPlanService.get(TScPlanEntity.class, tScPlan.getId());
		try {
			String delFileId = tScPlan.getDelFileId();
			if(StringUtils.isNotEmpty(delFileId)){
				String[] delIds = delFileId.split(",");
				for(String delId : delIds){
					this.delFile(delId);
				}
			}
			MyBeanUtils.copyBeanNotNull2Bean(tScPlan, t);
			if(t.getPlanProgress() == 100){
				t.setPlanState("已完成");
			}else{
				t.setPlanState("计划中");
			}
            String streamName = tScPlan.getStreamName();
            String fileName = tScPlan.getFileName();
            if(StringUtils.isNotEmpty(streamName)) {
                String[] filepath = streamName.split(",");
                String[] fileNames = fileName.split(",");
                List<TScPlanFileEntity> oldFile = t.getChildernFile();
                List<TScPlanFileEntity> files = new ArrayList<TScPlanFileEntity>();
                for(int i = 0 ; i < filepath.length ; i++){
                    String path = filepath[i];
                    String name = fileNames[i];
                    TScPlanFileEntity file = new TScPlanFileEntity();
                    if(oldFile != null && oldFile.size() > 0) {
                        TScPlanFileEntity fileEntity = (i>= oldFile.size() ? new TScPlanFileEntity() : oldFile.get(i));
                        if (path.equals(fileEntity.getStreamName())) {
                           continue;
                        } else {
                            file.setStreamName(path);
                            file.setRealName(name);
                            file.setParentFileObj(t);
                            oldFile.add(file);
                        }
                    }else{
                        file.setStreamName(path);
                        file.setRealName(name);
                        file.setParentFileObj(t);
                        files.add(file);
                    }
                }
                if(oldFile != null && oldFile.size() > 0) {
                    t.setChildernFile(oldFile);
                }else{
                    t.setChildernFile(files);
                }
            }
			tScPlanService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "工作计划更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 工作计划新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScPlanEntity tScPlan, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScPlan.getId())) {
			tScPlan = tScPlanService.getEntity(TScPlanEntity.class, tScPlan.getId());
			req.setAttribute("tScPlanPage", tScPlan);
			req.setAttribute("id",tScPlan.getId());
		}
		return new ModelAndView("com/qihang/buss/sc/oa/tScPlan-add");
	}
	/**
	 * 工作计划编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScPlanEntity tScPlan,Boolean plan, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScPlan.getId())) {
			tScPlan = tScPlanService.getEntity(TScPlanEntity.class, tScPlan.getId());
            Double planProgress = tScPlan.getPlanProgress();
            List<TScPlanFileEntity> files = tScPlan.getChildernFile();
            String streamNames = "";
            String fileNames = "";
            for(TScPlanFileEntity file : files){
                streamNames+=file.getStreamName()+",";
                fileNames+=file.getRealName()+",";
            }
            tScPlan.setStreamName(streamNames);
            tScPlan.setFileName(fileNames);
            if(plan){
                req.setAttribute("editAble",false);
            }else{
                req.setAttribute("editAble",true);
            }
            req.setAttribute("plan",plan);
			req.setAttribute("tScPlanPage", tScPlan);
			//String querryString = req.getQueryString();
			String planMaster = tScPlan.getPlanMaster();
			String planGroup = tScPlan.getPlanGroup();
			String planLeader = tScPlan.getPlanLeadder();
			String[] masters = planMaster.split(",");
			String[] groups = planGroup.split(",");
			String[] leaders = planLeader.split(",");
            planMaster = "";
            planGroup = "";
            planLeader = "";
            for(String m : masters){
                planMaster+=m+"','";
            }
            for(String g : groups){
                planGroup+=g+"','";
            }
            for(String l : leaders){
                planLeader+=l+"','";
            }
            planMaster = planMaster.substring(0,planMaster.length()-3);
            planGroup = planGroup.substring(0,planGroup.length()-3);
            planLeader = planLeader.substring(0,planLeader.length()-3);
			List<TSUser> masterList = tScPlanService.findHql("from TSUser where id in ('"+planMaster+"')");
			List<TSUser> groupList = tScPlanService.findHql("from TSUser where id in ('"+planGroup+"')");
			List<TSUser> leaderList = tScPlanService.findHql("from TSUser where id in ('"+planLeader+"')");
			req.setAttribute("masterList",masterList);
			req.setAttribute("groupList",groupList);
			req.setAttribute("leaderList",leaderList);
            req.setAttribute("masterIdList", JSON.toJSON(masters));
            req.setAttribute("groupIdList", JSON.toJSON(groups));
            req.setAttribute("leaderIdList", JSON.toJSON(leaders));
            Map<String,String[]> params = req.getParameterMap();
            for(String key : params.keySet()){
                String[] values = params.get(key);
                if("load".equals(key)){
                    for(int i=0 ;i < values.length; i++){
                        String value = values[i];
                        req.setAttribute(key,values[i]);
                    }
                }
            }
		}
		return new ModelAndView("com/qihang/buss/sc/oa/tScPlan-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "tScPlanController");
		return new ModelAndView("common/upload/personal/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScPlanEntity tScPlan,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScPlanEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScPlan, request.getParameterMap());
		List<TScPlanEntity> tScPlans = this.tScPlanService.getListByCriteriaQuery(cq,false);
        for(TScPlanEntity entity : tScPlans){
            entity.setPlanInfo(HTMLUtil.delHTMLTag(entity.getPlanInfo()));
        }
		modelMap.put(NormalExcelConstants.FILE_NAME,"工作计划");
		modelMap.put(NormalExcelConstants.CLASS,TScPlanEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("工作计划列表", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, tScPlans);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScPlanEntity tScPlan,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "工作计划");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScPlanEntity.class);
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
				List<TScPlanEntity> listTScPlanEntitys = ExcelImportUtil.importExcel(file.getInputStream(), TScPlanEntity.class, params);
				for (TScPlanEntity tScPlan : listTScPlanEntitys) {
					tScPlanService.save(tScPlan);
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

	public Map<String,Object> delFile(String id){
		Map<String,Object> result = new HashMap<String, Object>();
		try {
			tScPlanService.deleteEntityById(TScPlanFileEntity.class,id);
			result.put("success",true);
		}catch (Exception e){
			result.put("success",false);
		}
		return result;
	}

	//手机端数据获取
	@RequestMapping(params = "datagridAPP")
    @ResponseBody
	public Map<String,Object> datagridAPP(TScPlanEntity tScPlan,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
 		Map<String,Object> result = new HashMap<String, Object>();
        tScPlan.setUserId(null);
        CriteriaQuery cq = new CriteriaQuery(TScPlanEntity.class, dataGrid);
		//查询条件组装器
        String beginTime = request.getParameter("planStartdate_begin");
        String endTime = request.getParameter("planStartdate_end");
        String type = request.getParameter("type");
        String userId = request.getParameter("userId");
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScPlan, request.getParameterMap());
		try{
			//自定义追加查询条件
			//String userId = tScPlan.getUserId();
            TSUser user = systemService.get(TSUser.class,userId);
			String q1 = user.getId()+",%";
			String q2 = "%"+user.getId()+"%";
			String q3 = "%,"+user.getId();
            if("1".equals(type)) {
                cq.eq("createBy", user.getUserName());
            }else if("2".equals(type)){
                cq.or(Restrictions.like("planGroup", q1), Restrictions.or(Restrictions.like("planGroup", q2), Restrictions.or(Restrictions.like("planGroup", q3), Restrictions.like("planGroup", user.getId()))));
            }else if("3".equals(type)){
                cq.or(Restrictions.like("planMaster", q1), Restrictions.or(Restrictions.like("planMaster", q2), Restrictions.or(Restrictions.like("planMaster", q3), Restrictions.like("planMaster", user.getId()))));
            }else if("4".equals(type)){
                cq.or(Restrictions.like("planLeadder", q1), Restrictions.or(Restrictions.like("planLeadder", q2), Restrictions.or(Restrictions.like("planLeadder", q3), Restrictions.like("planLeadder", user.getId()))));
            }else{
                //cq.eq("createBy", user.getUserName());
                cq.or(Restrictions.like("planGroup", q1), Restrictions.or(Restrictions.like("planGroup", q2), Restrictions.or(Restrictions.like("planGroup", q3), Restrictions.like("planGroup", user.getId()))));

            }
            if(StringUtils.isNotEmpty(beginTime)) {
                cq.ge("planStartdate",new SimpleDateFormat("yyyy-MM-dd").parse(beginTime));
            }
            if(StringUtils.isNotEmpty(endTime)){
                cq.le("planStartdate",new SimpleDateFormat("yyyy-MM-dd").parse(endTime));
            }
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScPlanService.getDataGridReturn(cq, true);
        List<TScPlanEntity> planList = dataGrid.getResults();
        List<TScPlanEntity> valueList = new ArrayList<TScPlanEntity>();
        for(TScPlanEntity plan : planList){
            List<String> users = this.getUserById(plan);
            TScPlanEntity value = new TScPlanEntity();
            value.setId(plan.getId());
            value.setPlanName(plan.getPlanName());
            value.setPlanType(plan.getPlanType());
            value.setPlanStartdate(plan.getPlanStartdate());
            value.setPlanEnddate(plan.getPlanEnddate());
            value.setPlaner(plan.getPlaner());
            value.setPlanProgress(plan.getPlanProgress());
            value.setPlanState(plan.getPlanState());
            value.setPlanMaster(users.get(0));
            value.setPlanGroup(users.get(1));
            value.setPlanLeadder(users.get(2));
			value.setCreateBy(plan.getCreateBy());
            valueList.add(value);
        }
        result.put("total", planList.size());
        result.put("rows", valueList);
        return result;
	}

    private List<String> getUserById(TScPlanEntity plan) {
        String[] masterList = plan.getPlanMaster().split(",");
        String[] groupList = plan.getPlanGroup().split(",");
        String[] leaderList = plan.getPlanLeadder().split(",");
        String masters = "";
        String groups = "";
        String leaders = "";
        for(String masterId : masterList){
            TSUser u = systemService.get(TSUser.class,masterId);
            if(u != null) {
                masters += u.getRealName() + ",";
            }
        }
        for(String groupId : groupList){
            TSUser u = systemService.get(TSUser.class,groupId);
            if(u != null) {
                groups += u.getUserName() + ",";
            }
        }
        for(String leaderId : leaderList){
            TSUser u = systemService.get(TSUser.class,leaderId);
            if(u != null) {
                leaders += u.getUserName() + ",";
            }
        }
        if(masters.length() > 0) {
            masters = masters.substring(0, masters.length() - 1);
        }else{
            masters = " ";
        }
        if(groups.length() > 0) {
            groups = groups.substring(0, groups.length() - 1);
        }else{
            groups = " ";
        }
        if(leaders.length() > 0) {
            leaders = leaders.substring(0, leaders.length() - 1);
        }else{
            leaders = " ";
        }
        List<String> result = new ArrayList<String>();
        result.add(masters);
        result.add(groups);
        result.add(leaders);
        return result;
    }

	//=================================手机端方法===================================

    //手机端查看数据
    @RequestMapping(params = "loadView")
    @ResponseBody
    public TScPlanEntity loadView(TScPlanEntity tScPlan,HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tScPlan.getId())) {
            List<TScPlanEntity> plans = tScPlanService.findHql("from TScPlanEntity where id = ?", tScPlan.getId());
            for(TScPlanEntity plan : plans) {
                tScPlan = plan;
                List<TScPlanFileEntity> files = tScPlan.getChildernFile();
                String streamNames = "";
                String fileNames = "";
                for (TScPlanFileEntity file : files) {
                    streamNames += file.getStreamName() + ",";
                    fileNames += file.getRealName() + ",";
                }
                tScPlan.setChildernFile(null);
                tScPlan.setStreamName(streamNames);
                tScPlan.setFileName(fileNames);
                List<String> users = this.getUserById(tScPlan);
                tScPlan.setPlanMaster(users.get(0));
                tScPlan.setPlanGroup(users.get(1));
                tScPlan.setPlanLeadder(users.get(2));
            }
        }
        return tScPlan;
    }

    //手机端查看数据
    @RequestMapping(params = "loadUpdate")
    @ResponseBody
    public TScPlanEntity loadUpdate(String id,HttpServletRequest req) {
        TScPlanEntity tScPlan = new TScPlanEntity();
        if (StringUtil.isNotEmpty(id)) {
            tScPlan = tScPlanService.getEntity(TScPlanEntity.class, id);
            List<TScPlanFileEntity> files = tScPlan.getChildernFile();
            String streamNames = "";
            String fileNames = "";
            for(TScPlanFileEntity file : files){
                streamNames+=file.getStreamName()+",";
                fileNames+=file.getRealName()+",";
            }
            tScPlan.setChildernFile(null);
            tScPlan.setStreamName(streamNames);
            tScPlan.setFileName(fileNames);
			List<String> users = this.getUserById(tScPlan);
			tScPlan.setMasters(StringUtils.isEmpty(users.get(0)) ? "" : users.get(0));
			tScPlan.setGroups(StringUtils.isEmpty(users.get(1)) ? "" : users.get(1));
			tScPlan.setLeaders(StringUtils.isEmpty(users.get(2)) ? "" : users.get(2));
        }
        return tScPlan;
    }

    /**
     * 手机端编辑保存
     * @param tScPlan
     * @return
     */
    @RequestMapping(params = "updateApp")
    @ResponseBody
    public AjaxJson updateApp(@RequestBody TScPlanEntity tScPlan, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "工作计划更新成功";
        TScPlanEntity t = tScPlanService.get(TScPlanEntity.class, tScPlan.getId());
        try {
            String delFileId = tScPlan.getDelFileId();
            if(StringUtils.isNotEmpty(delFileId)){
                String[] delIds = delFileId.split(",");
                for(String delId : delIds){
                    this.delFile(delId);
                }
            }
            MyBeanUtils.copyBeanNotNull2Bean(tScPlan, t);
            if(t.getPlanProgress() == 100){
                t.setPlanState("已完成");
            }else{
                t.setPlanState("计划中");
            }
            String streamName = tScPlan.getNewStreamName();
            String fileName = tScPlan.getNewFileName();
            if(StringUtils.isNotEmpty(streamName)) {
                String[] filepath = streamName.split(",");
                String[] fileNames = fileName.split(",");
                List<TScPlanFileEntity> oldFile = t.getChildernFile();
                List<TScPlanFileEntity> files = new ArrayList<TScPlanFileEntity>();
                for(int i = 0 ; i < filepath.length ; i++){
                    String path = filepath[i];
                    String name = fileNames[i];
                    TScPlanFileEntity file = new TScPlanFileEntity();
                    if(oldFile != null && oldFile.size() > 0) {
                        TScPlanFileEntity fileEntity = (i>= oldFile.size() ? new TScPlanFileEntity() : oldFile.get(i));
                        if (path.equals(fileEntity.getStreamName())) {
                            continue;
                        } else {
                            file.setStreamName(path);
                            file.setRealName(name);
                            file.setParentFileObj(t);
                            oldFile.add(file);
                        }
                    }else{
                        file.setStreamName(path);
                        file.setRealName(name);
                        file.setParentFileObj(t);
                        files.add(file);
                    }
                }
                if(oldFile != null && oldFile.size() > 0) {
                    t.setChildernFile(oldFile);
                }else{
                    t.setChildernFile(files);
                }
            }
            TSUser user = systemService.get(TSUser.class,tScPlan.getUserId());
            t.setUpdateBy(user.getUserName());
            t.setUpdateName(user.getRealName());
            t.setUpdateDate(new Date());
            tScPlanService.saveOrUpdate(t);
            //systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "工作计划更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

	/**
	 * 添加工作计划
	 *
	 * @return
	 */
	@RequestMapping(params = "addApp")
	@ResponseBody
	public AjaxJson addApp(@RequestBody TScPlanEntity tScPlan, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "工作计划添加成功";
		try{
			tScPlan.setPlanProgress(Double.parseDouble("0"));
			tScPlan.setPlanState("计划中");
			String streamName = tScPlan.getStreamName();
			String fileName = tScPlan.getFileName();
			if(StringUtils.isNotEmpty(streamName)) {
				String[] filepath = streamName.split(",");
				String[] fileNames = fileName.split(",");
				List<TScPlanFileEntity> files = new ArrayList<TScPlanFileEntity>();
				for(int i = 0 ; i < filepath.length ; i++){
					String path = filepath[i];
					String name = fileNames[i];
					TScPlanFileEntity file = new TScPlanFileEntity();
					file.setStreamName(path);
					file.setRealName(name);
					file.setParentFileObj(tScPlan);
					files.add(file);
				}
				tScPlan.setChildernFile(files);
			}
			String info = tScPlan.getPlanInfo();
			if(StringUtils.isNotEmpty(info)) {
				info = info.replaceAll("\"", "'");
				tScPlan.setPlanInfo(info);
			}
			String userId = tScPlan.getUserId();
			if(StringUtils.isNotEmpty(userId)) {
				TSUser user = systemService.get(TSUser.class, userId);
				tScPlan.setCreateBy(user.getUserName());
				tScPlan.setCreateName(user.getRealName());
				tScPlan.setCreateDate(new Date());
			}
			tScPlanService.save(tScPlan);
			//systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "工作计划添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

    //获取字典数据
    @RequestMapping(params = "loadComboInfo")
	@ResponseBody
	public List<Map<String,Object>> loadComboInfo(String dicCode){
		List<Map<String,Object>> comboInfo  = tScPlanService.loadComboInfo(dicCode);
        return comboInfo;
	}

	/**
	 * 删除工作计划
	 *
	 * @return
	 */
	@RequestMapping(params = "delApp")
	@ResponseBody
	public AjaxJson delApp(String id, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		TScPlanEntity tScPlan = systemService.getEntity(TScPlanEntity.class, id);
		message = "工作计划删除成功";
        if(tScPlan != null) {
            if (tScPlan.getPlanProgress() == Double.parseDouble("0")) {
                try {
					List<TScDailyEntity> dailyList = systemService.findByProperty(TScDailyEntity.class,"workPlan",tScPlan.getId());
					if(dailyList != null && dailyList.size() > 0) {
						message = "该工作计划已被使用，不可删除，请确认";
						j.setSuccess(false);
					}else{
						tScPlanService.delete(tScPlan);
					}
                    //systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
                } catch (Exception e) {
                    e.printStackTrace();
                    message = "工作计划删除失败";
                    throw new BusinessException(e.getMessage());
                }
            } else {
                if (tScPlan.getPlanProgress() < Double.parseDouble("100")) {
                    message = "进行中的工作计划无法删除，请确认";
                } else {
                    message = "已完成的工作计划无法删除，请确认";
                }
                j.setSuccess(false);
            }
        }else{
            message = "该工作计划不存在，请确认";
            j.setSuccess(false);
        }
		j.setMsg(message);
		return j;
	}

	@RequestMapping(params = "getPlanProgress")
	@ResponseBody
	public AjaxJson getPlanProgress(String id){
			AjaxJson j = new AjaxJson();
			TScPlanEntity entity = tScPlanService.get(TScPlanEntity.class, id);
			Double planProgress = entity.getPlanProgress();
			j.setObj(planProgress);
			return j;
	}

	@RequestMapping(params = "updatePlanProgress")
	@ResponseBody
	public AjaxJson updatePlanProgress(@RequestBody TScPlanEntity entity){
			AjaxJson j = new AjaxJson();
			String id = entity.getId();
			String userId = entity.getUserId();
			TSUser user = systemService.get(TSUser.class,userId);
			Double planProgress = entity.getPlanProgress();
			message = "工作计划进度更新成功";
		 try {
				 StringBuffer updateSql = new StringBuffer();
				 updateSql.append("update t_Sc_plan set plan_progress = ?,update_name=?,update_by=?,update_date=getdate() where id = ?");
				 systemService.executeSql(updateSql.toString(), new Object[]{planProgress, user.getRealName(), user.getUserName(), id});
		 }catch (Exception e){
				 j.setSuccess(false);
				 message = "工作计划进度更新失败";
		 }
			j.setMsg(message);
			return j;
	}

	/**
	 * 部门用户数据树返回
	 *
	 * @return
	 */
	@RequestMapping(params = "userSel")
	@ResponseBody
	public List<Map<String,Object>> userSel() {
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		List<Map<String,Object>> typeInfo = userService.getDepartmentSonId(sonInfo.getId(),sonInfo.getDepartname());
		return typeInfo;
	}

}
