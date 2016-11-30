package com.qihang.buss.sc.oa.controller;

import com.alibaba.fastjson.JSON;
import com.qihang.buss.sc.oa.entity.TScDailyEntity;
import com.qihang.buss.sc.oa.entity.TScDailyFileEntity;
import com.qihang.buss.sc.oa.entity.TScPlanEntity;
import com.qihang.buss.sc.oa.entity.TScSystemfileEntity;
import com.qihang.buss.sc.oa.service.TScDailyServiceI;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.ExceptionUtil;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.ResourceUtil;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.poi.excel.ExcelImportUtil;
import com.qihang.winter.poi.excel.entity.ExportParams;
import com.qihang.winter.poi.excel.entity.ImportParams;
import com.qihang.winter.poi.excel.entity.TemplateExportParams;
import com.qihang.winter.poi.excel.entity.vo.NormalExcelConstants;
import com.qihang.winter.poi.excel.entity.vo.TemplateExcelConstants;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.web.system.pojo.base.TSUser;
import com.qihang.winter.web.system.service.SystemService;
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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Title: Controller
 * @Description: 工作日志
 * @author onlineGenerator
 * @date 2015-12-01 16:09:09
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScDailyController")
public class TScDailyController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScDailyController.class);

	@Autowired
	private TScDailyServiceI tScDailyService;
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
	 * 工作日志列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScDaily")
	public ModelAndView tScDaily(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/oa/tScDailyList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScDailyEntity tScDaily,String type,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScDailyEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScDaily, request.getParameterMap());
        String userId = ResourceUtil.getSessionUserName().getId();
		String userName = ResourceUtil.getSessionUserName().getUserName();
		try{
            //自定义追加查询条件
            String query_dailyTime_begin = request.getParameter("dailyTime_begin");
            String query_dailyTime_end = request.getParameter("dailyTime_end");
            if(StringUtil.isNotEmpty(query_dailyTime_begin)){
                cq.ge("dailyTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_dailyTime_begin));
            }
            if(StringUtil.isNotEmpty(query_dailyTime_end)){
                cq.le("dailyTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_dailyTime_end));
            }
            if("1".equals(type)){
                cq.or(Restrictions.like("dailyShare", userId + ",%"), Restrictions.or(Restrictions.like("dailyShare", "%," + userId + ",%"), Restrictions.or(Restrictions.like("dailyShare", "%," + userId), Restrictions.like("dailyShare", userId))));
            }else{
                cq.eq("createBy",userName);
            }
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScDailyService.getDataGridReturn(cq, true);
        List<TScDailyEntity> result = dataGrid.getResults();
        if(result.size() > 0){
            for(TScDailyEntity entity : result ) {
                String planId = entity.getWorkPlan();
                if(StringUtils.isNotEmpty(planId)) {
                    TScPlanEntity plan = tScDailyService.getEntity(TScPlanEntity.class, planId);
					if(plan != null){
						Double progress = plan.getPlanProgress();
						if(progress == Double.parseDouble("100")){
							entity.setHasFinish(true);
						}else{
							entity.setHasFinish(false);
						}
					}else{
						entity.setHasFinish(false);
					}
                }else{
                    entity.setHasFinish(false);
                }
            }
        }
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除工作日志
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScDailyEntity tScDaily, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
        if(tScDaily.getHasFinish()){
            message = "该日志所属工作计划已完成，不可删除，请确认";
            j.setMsg(message);
        }else {
            tScDaily = systemService.getEntity(TScDailyEntity.class, tScDaily.getId());
            message = "工作日志删除成功";
            try {
                tScDailyService.delete(tScDaily);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            } catch (Exception e) {
                e.printStackTrace();
                message = "工作日志删除失败";
                throw new BusinessException(e.getMessage());
            }
            j.setMsg(message);
        }
		return j;
	}
	
	/**
	 * 批量删除工作日志
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "工作日志删除成功";
		try{
			for(String id:ids.split(",")){
				TScDailyEntity tScDaily = systemService.getEntity(TScDailyEntity.class,
				id
				);
				tScDailyService.delete(tScDaily);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "工作日志删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加工作日志
	 * 
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScDailyEntity tScDaily, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "工作日志添加成功";
		try{
			String streamName = tScDaily.getStreamName();
			String fileName = tScDaily.getFileName();
			if(StringUtils.isNotEmpty(streamName)) {
				String[] filepath = streamName.split(",");
				String[] fileNames = fileName.split(",");
				List<TScDailyFileEntity> files = new ArrayList<TScDailyFileEntity>();
				for(int i = 0 ; i < filepath.length ; i++){
					String path = filepath[i];
					String name = fileNames[i];
					TScDailyFileEntity file = new TScDailyFileEntity();
					file.setStreamName(path);
					file.setRealName(name);
					file.setParentFileObj(tScDaily);
					files.add(file);
				}
				tScDaily.setChildernFile(files);
			}
            String userId = tScDaily.getUserId();
            if(StringUtils.isNotEmpty(userId)){
                TSUser user = systemService.get(TSUser.class,userId);
                tScDaily.setCreateName(user.getRealName());
                tScDaily.setCreateBy(user.getUserName());
                tScDaily.setCreateDate(new Date());
            }
			tScDailyService.save(tScDaily);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "工作日志添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新工作日志
	 * 
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScDailyEntity tScDaily, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "工作日志更新成功";
		TScDailyEntity t = tScDailyService.get(TScDailyEntity.class, tScDaily.getId());
		try {
            String delFileId = tScDaily.getDelFileId();
            if(StringUtils.isNotEmpty(delFileId)){
                String[] delIds = delFileId.split(",");
                for(String delId : delIds){
                    this.delFile(delId);
                }
            }
			MyBeanUtils.copyBeanNotNull2Bean(tScDaily, t);
            String streamName = tScDaily.getStreamName();
            String fileName = tScDaily.getFileName();
            if(StringUtils.isNotEmpty(streamName)) {
                String[] filepath = streamName.split(",");
                String[] fileNames = fileName.split(",");
                List<TScDailyFileEntity> oldFile = t.getChildernFile();
                List<TScDailyFileEntity> files = new ArrayList<TScDailyFileEntity>();
                for(int i = 0 ; i < filepath.length ; i++){
                    String path = filepath[i];
                    String name = fileNames[i];
                    TScDailyFileEntity file = new TScDailyFileEntity();
                    if(oldFile != null && oldFile.size() > 0) {
                        TScDailyFileEntity fileEntity = (i>= oldFile.size() ? new TScDailyFileEntity() : oldFile.get(i));
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
			tScDailyService.saveOrUpdate(t);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "工作日志更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	

	/**
	 * 工作日志新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScDailyEntity tScDaily, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScDaily.getId())) {
			tScDaily = tScDailyService.getEntity(TScDailyEntity.class, tScDaily.getId());
			req.setAttribute("tScDailyPage", tScDaily);
		}
		List<TScSystemfileEntity> sysInfo =tScDailyService.getList(TScSystemfileEntity.class);//获取系统参数
		if(sysInfo.size() > 0){
			//Boolean isBinding = Boolean.parseBoolean(sysInfo.get(0).getWorklog());
            String isBinding = sysInfo.get(0).getWorklog();
			req.setAttribute("isBinding","Y".equals(isBinding) ? true : false);
			String info = sysInfo.get(0).getShareloguser();
			String[] shareUser = info.split(",");
            List<String> newList = new ArrayList<String>();
            for(String userId : shareUser){
                TSUser u = tScDailyService.get(TSUser.class,userId);
                if(u != null){
                    newList.add(userId);
                }
            }
			req.setAttribute("shareUser", JSON.toJSON(newList));
		}
//        List<String> value = tOaDailyService.findListbySql("select sys.sys_value from T_OA_SYSTEM sys where sys.sys_key = '工作日志是否绑定工作计划'");
//        if(value.size() > 0) {
//            Boolean isBinding = Boolean.parseBoolean(value.get(0));
//            req.setAttribute("isBinding",isBinding);
//        }
//        value = tOaDailyService.findListbySql("select sys.sys_value from T_OA_SYSTEM sys where sys.sys_key = '共享日志默认用户'");
//		if(value.size() > 0){
//            String info = value.get(0);
//            String[] shareUser = info.split(",");
//            req.setAttribute("shareUser",JSON.toJSON(shareUser));
//        }
        return new ModelAndView("com/qihang/buss/sc/oa/tScDaily-add");
	}
	/**
	 * 工作日志编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScDailyEntity tScDaily,Boolean plan, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScDaily.getId())) {
			tScDaily = tScDailyService.getEntity(TScDailyEntity.class, tScDaily.getId());
			req.setAttribute("tScDailyPage", tScDaily);
			String share = tScDaily.getDailyShare();
            if(StringUtils.isNotEmpty(share)) {
                String[] shares = share.split(",");
                req.setAttribute("shareId", JSON.toJSON(shares));
            }else{
                req.setAttribute("shareId",null);
            }
            req.setAttribute("plan",plan);
		}
        List<TScSystemfileEntity> sysInfo =tScDailyService.getList(TScSystemfileEntity.class);//获取系统参数
        if(sysInfo.size() > 0){
            //Boolean isBinding = Boolean.parseBoolean(sysInfo.get(0).getWorklog());
            String isBinding = sysInfo.get(0).getWorklog();
            req.setAttribute("isBinding","Y".equals(isBinding) ? true : false);
            String info = sysInfo.get(0).getShareloguser();
            String[] shareUser = info.split(",");
            req.setAttribute("shareUser", JSON.toJSON(shareUser));
        }
		return new ModelAndView("com/qihang/buss/sc/oa/tScDaily-update");
	}
	
	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "tScDailyController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScDailyEntity tScDaily,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScDailyEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScDaily, request.getParameterMap());
		List<TScDailyEntity> tScDailys = this.tScDailyService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"工作日志");
		modelMap.put(NormalExcelConstants.CLASS,TScDailyEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("工作日志列表", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST, tScDailys);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScDailyEntity tScDaily,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "工作日志");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScDailyEntity.class);
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
				List<TScDailyEntity> listTScDailyEntitys = ExcelImportUtil.importExcel(file.getInputStream(), TScDailyEntity.class, params);
				for (TScDailyEntity tScDaily : listTScDailyEntitys) {
					tScDailyService.save(tScDaily);
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

	@RequestMapping(params = "loadPlanInfo")
	@ResponseBody
	public List<Map<String,Object>> loadPlanInfo(){
		String userId = ResourceUtil.getSessionUserName().getId();
		String userName = ResourceUtil.getSessionUserName().getUserName();
		List<Map<String,Object>> planInfo = tScDailyService.loadPlanInfo(userName, userId);
		return planInfo;
	}

	//获取共享用户数据
	@RequestMapping(params = "loadShareInfo")
	@ResponseBody
	public List<Map<String,Object>> loadShareInfo(){
        //List<String> value = tOaDailyService.findListbySql("select sys.sys_value from T_OA_SYSTEM sys where sys.sys_key = '共享日志默认用户'");
        List<TScSystemfileEntity> sysInfo = tScDailyService.getList(TScSystemfileEntity.class);
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        if(sysInfo.size() > 0) {
            String value = sysInfo.get(0).getShareloguser();
            String[] shareUsers = value.split(",");
            for (String shareUser : shareUsers) {
				TSUser user = tScDailyService.get(TSUser.class,shareUser);
				if(user != null) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", shareUser);
					map.put("text",user.getRealName());
					result.add(map);
				}
            }
        }
        return result;
	}

    public Map<String,Object> delFile(String id){
        Map<String,Object> result = new HashMap<String, Object>();
        try {
            tScDailyService.deleteEntityById(TScDailyFileEntity.class,id);
            result.put("success",true);
        }catch (Exception e){
            result.put("success",false);
        }
        return result;
    }


	//===============================手机端方法===========================
    /**
     * 手机端日志列表
     * @param tScDaily
     * @param type
     * @param request
     * @param response
     * @param dataGrid
     */
    @RequestMapping(params = "datagridApp")
    @ResponseBody
    public Map<String,Object> datagridApp(TScDailyEntity tScDaily,String type,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(TScDailyEntity.class, dataGrid);
        //查询条件组装器

        TSUser user = systemService.get(TSUser.class,tScDaily.getUserId());
        tScDaily.setUserId(null);
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScDaily, request.getParameterMap());
        String userId = user.getId();
        String  userName = user.getUserName();
        try{
            //自定义追加查询条件
            String query_dailyTime_begin = request.getParameter("dailyTime_begin");
            String query_dailyTime_end = request.getParameter("dailyTime_end");
            if(StringUtil.isNotEmpty(query_dailyTime_begin)){
                cq.ge("dailyTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_dailyTime_begin));
            }
            if(StringUtil.isNotEmpty(query_dailyTime_end)){
                cq.le("dailyTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_dailyTime_end));
            }
            if("1".equals(type)){
                cq.or(Restrictions.like("dailyShare", userId + ",%"), Restrictions.or(Restrictions.like("dailyShare", "%," + userId + ",%"), Restrictions.or(Restrictions.like("dailyShare", "%," + userId), Restrictions.like("dailyShare", userId))));
            }else{
                cq.eq("createBy",userName);
            }
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tScDailyService.getDataGridReturn(cq, true);
        List<TScDailyEntity> result = dataGrid.getResults();
        if(result.size() > 0){
            for(TScDailyEntity entity : result ) {
                String planId = entity.getWorkPlan();
                entity.setChildernFile(null);
                if(StringUtils.isNotEmpty(planId)) {
                    TScPlanEntity plan = tScDailyService.getEntity(TScPlanEntity.class, planId);
					if(plan != null) {
						Double progress = plan.getPlanProgress();
						if (progress == Double.parseDouble("100")) {
							entity.setHasFinish(true);
						} else {
							entity.setHasFinish(false);
						}
						entity.setPlanName(plan.getPlanName());
					}else{
						entity.setPlanName(null);
					}
                }else{
                    entity.setHasFinish(false);
                }
            }
        }
        Map<String,Object> dailyInfo = new HashMap<String, Object>();
        dailyInfo.put("total",result.size());
        dailyInfo.put("rows",result);
        return  dailyInfo;
       // TagUtil.datagrid(response, dataGrid);
    }

	/**
	 * 获取查看界面
	 * @return
	 */
    @RequestMapping(params = "getView")
    @ResponseBody
    public TScDailyEntity getView(String id,HttpServletRequest request){
        TScDailyEntity entity = systemService.get(TScDailyEntity.class,id);
        if(entity != null){
            String shareUser = entity.getDailyShare();
            if(StringUtils.isNotEmpty(shareUser)) {
                String[] userIdList = shareUser.split(",");
                String userName = "";
                for (String userId : userIdList) {
                    TSUser user = systemService.get(TSUser.class, userId);
                    if (user != null) {
                        userName += user.getRealName()+ ",";
                    }
                }
                if(StringUtils.isNotEmpty(userName)) {
                    userName = userName.substring(0, userName.length() - 1);
                }
                entity.setShareUser(userName);
            }
            String planId = entity.getWorkPlan();
            if(StringUtils.isNotEmpty(planId)){
				TScPlanEntity plan = systemService.get(TScPlanEntity.class,planId);
				entity.setPlanName(plan.getPlanName());
            }
            List<TScDailyFileEntity> children = entity.getChildernFile();
            String streamName = "";
            String fileName = "";
            for(TScDailyFileEntity file : children){
                String path = file.getStreamName();
                String name = file.getRealName();
                streamName += path+",";
                fileName += name+",";
            }
            if(StringUtils.isNotEmpty(streamName)){
                streamName = streamName.substring(0,streamName.length()-1);
                fileName = fileName.substring(0,fileName.length()-1);
            }
            entity.setStreamName(streamName);
            entity.setFileName(fileName);
            entity.setChildernFile(null);
        }else{
			entity = new TScDailyEntity();
		}
        return entity;
    }

    /**
     * 获取编辑数据
     * @return
     */
    @RequestMapping(params = "loadUpdate")
    @ResponseBody
    public TScDailyEntity loadUpdate(@RequestBody TScDailyEntity daily){
        TScDailyEntity entity = systemService.get(TScDailyEntity.class,daily.getId());
        if(entity != null){
            List<TScDailyFileEntity> children = entity.getChildernFile();
            String streamName = "";
            String fileName = "";
            String fileId = "";
            for(TScDailyFileEntity file : children){
                String path = file.getStreamName();
                String name = file.getRealName();
                String id = file.getId();
                streamName += path+",";
                fileName += name+",";
                fileId += id+",";
            }
            if(StringUtils.isNotEmpty(streamName)){
                streamName = streamName.substring(0,streamName.length()-1);
                fileName = fileName.substring(0,fileName.length()-1);
                fileId = fileId.substring(0,fileId.length()-1);
            }
            String planId = entity.getWorkPlan();
            if(StringUtils.isNotEmpty(planId)){
                TScPlanEntity plan = systemService.get(TScPlanEntity.class,planId);
                entity.setPlanName(plan.getPlanName());
            }
            entity.setStreamName(streamName);
            entity.setFileName(fileName);
            entity.setFileId(fileId);
            entity.setChildernFile(null);
        }else{
            entity = new TScDailyEntity();
        }
        return entity;
    }

    //手机端日志新增
    @RequestMapping(params = "dailyAdd")
    @ResponseBody
    public AjaxJson dailyAdd(@RequestBody TScDailyEntity tScDaily, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "工作日志添加成功";
        try{
            String streamName = tScDaily.getStreamName();
            String fileName = tScDaily.getFileName();
            if(StringUtils.isNotEmpty(streamName)) {
                String[] filepath = streamName.split(",");
                String[] fileNames = fileName.split(",");
                List<TScDailyFileEntity> files = new ArrayList<TScDailyFileEntity>();
                for(int i = 0 ; i < filepath.length ; i++){
                    String path = filepath[i];
                    String name = fileNames[i];
                    TScDailyFileEntity file = new TScDailyFileEntity();
                    file.setStreamName(path);
                    file.setRealName(name);
                    file.setParentFileObj(tScDaily);
                    files.add(file);
                }
                tScDaily.setChildernFile(files);
            }
            String userId = tScDaily.getUserId();
            if(StringUtils.isNotEmpty(userId)){
                TSUser user = systemService.get(TSUser.class,userId);
                tScDaily.setCreateName(user.getRealName());
                tScDaily.setCreateBy(user.getUserName());
                tScDaily.setCreateDate(new Date());
            }
            tScDailyService.save(tScDaily);
           // systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        }catch(Exception e){
            e.printStackTrace();
            message = "工作日志添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新工作日志
     *
     * @return
     */
    @RequestMapping(params = "dailyUpdate")
    @ResponseBody
    public AjaxJson dailyUpdate(@RequestBody TScDailyEntity tScDaily, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "工作日志更新成功";
        TScDailyEntity t = tScDailyService.get(TScDailyEntity.class, tScDaily.getId());
        try {
            String delFileId = tScDaily.getDelFileId();
            if(StringUtils.isNotEmpty(delFileId)){
                String[] delIds = delFileId.split(",");
                for(String delId : delIds){
                    this.delFile(delId);
                }
            }
            MyBeanUtils.copyBeanNotNull2Bean(tScDaily, t);
            String streamName = tScDaily.getNewStreamName();
            String fileName = tScDaily.getNewFileName();
            if(StringUtils.isNotEmpty(streamName)) {
                String[] filepath = streamName.split(",");
                String[] fileNames = fileName.split(",");
                List<TScDailyFileEntity> oldFile = t.getChildernFile();
                List<TScDailyFileEntity> files = new ArrayList<TScDailyFileEntity>();
                for(int i = 0 ; i < filepath.length ; i++){
                    String path = filepath[i];
                    String name = fileNames[i];
                    TScDailyFileEntity file = new TScDailyFileEntity();
                    if(oldFile != null && oldFile.size() > 0) {
                        TScDailyFileEntity fileEntity = (i>= oldFile.size() ? new TScDailyFileEntity() : oldFile.get(i));
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
            String userId = tScDaily.getUserId();
            if(StringUtils.isNotEmpty(userId)){
                TSUser user = systemService.get(TSUser.class,userId);
                t.setUpdateName(user.getRealName());
                t.setUpdateBy(user.getUserName());
                t.setUpdateDate(new Date());
            }
            tScDailyService.saveOrUpdate(t);
           // systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "工作日志更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    //手机端获取计划列表
    @RequestMapping(params = "loadPlanInfo_App")
    @ResponseBody
    public List<Map<String,Object>> loadPlanInfo_App(String userId){
        TSUser user = systemService.get(TSUser.class,userId);
        List<Map<String,Object>> planInfo = tScDailyService.loadPlanInfo(user.getUserName(), userId);
        return planInfo;
    }

	@RequestMapping(params = "delApp")
	@ResponseBody
	public AjaxJson delApp(TScDailyEntity tScDaily,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "工作日志删除成功";
		tScDaily = tScDailyService.getEntity(TScDailyEntity.class,tScDaily.getId());
		String planId = tScDaily.getWorkPlan();
        Boolean isDel = true;
		if(StringUtils.isNotEmpty(planId)){
			TScPlanEntity plan = systemService.get(TScPlanEntity.class, planId);
			if(plan != null) {
				Double planProgress = plan.getPlanProgress();
				if (planProgress == Double.parseDouble("100")) {
					j.setSuccess(false);
					j.setMsg("该日志所属工作计划已完成，不可删除，请确认");
					isDel = false;
				}
			}
		}
        if(isDel) {
            try {
                tScDailyService.delete(tScDaily);
            } catch (Exception e) {
                e.printStackTrace();
                message = "工作日志删除失败";
                throw new BusinessException(e.getMessage());
            }
            j.setMsg(message);
        }
		return j;
	}
}
