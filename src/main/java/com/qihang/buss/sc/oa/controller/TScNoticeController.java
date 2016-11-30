package com.qihang.buss.sc.oa.controller;


import com.qihang.buss.sc.oa.entity.TScNoticeEntity;
import com.qihang.buss.sc.oa.entity.TScNoticeFileEntity;
import com.qihang.buss.sc.oa.entity.TScNoticeRelationEntity;
import com.qihang.buss.sc.oa.page.TScNoticePage;
import com.qihang.buss.sc.oa.service.TScNoticeServiceI;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.ResourceUtil;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.service.SystemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;


/**   
 * @Title: Controller
 * @Description: 公告通知
 * @author onlineGenerator
 * @date 2015-12-03 09:40:04
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScNoticeController")
public class TScNoticeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScNoticeController.class);

	@Autowired
	private TScNoticeServiceI tScNoticeService;
	@Autowired
	private SystemService systemService;

	/**
	 * 公告通知列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScNotice")
	public ModelAndView tScNotice(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/oa/tScNoticeList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScNoticeEntity tScNotice,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScNoticeEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScNotice);
		try{
		//自定义追加查询条件
		String query_issuanceDate_begin = request.getParameter("issuanceDate_begin");
		String query_issuanceDate_end = request.getParameter("issuanceDate_end");
		if(StringUtil.isNotEmpty(query_issuanceDate_begin)){
			cq.ge("issuanceDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_issuanceDate_begin));
		}
		if(StringUtil.isNotEmpty(query_issuanceDate_end)){
			cq.le("issuanceDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_issuanceDate_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScNoticeService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除公告通知
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScNoticeEntity tScNotice, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScNotice = systemService.getEntity(TScNoticeEntity.class, tScNotice.getId());
		String message = "公告通知删除成功";
		if(tScNotice.getIssuance() == 1){
			message = "公告通知已发布，不能删除";
			j.setMsg(message);
			return j;
		}

		try{
			tScNoticeService.delMain(tScNotice);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "公告通知删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除公告通知
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "公告通知删除成功";
		try{
			for(String id:ids.split(",")){
				TScNoticeEntity tScNotice = systemService.getEntity(TScNoticeEntity.class, id);
				if(tScNotice.getIssuance() != 1){
					tScNoticeService.delMain(tScNotice);
				}else{
					message = "已删除未发布的公告通知";
				}
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "公告通知删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加公告通知
	 * 
	 * @param tScNotice
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScNoticeEntity tScNotice,TScNoticePage tScNoticePage, HttpServletRequest request) {
		List<TScNoticeFileEntity> tScNoticeFileList =  tScNoticePage.getTScNoticeFileList();
		AjaxJson j = new AjaxJson();
		if(tScNotice.getIssuance() == null){
			tScNotice.setIssuance(0);
		}
		tScNotice.setContent(tScNotice.getContent().replaceAll("\"", "&quot"));
		String message = "添加成功";
		try{
			tScNoticeService.addMain(tScNotice, tScNoticeFileList);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "公告通知添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新公告通知
	 * 
	 * @param
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScNoticeEntity tScNotice,TScNoticePage tScNoticePage, HttpServletRequest request) {
		List<TScNoticeFileEntity> tScNoticeFileList =  tScNoticePage.getTScNoticeFileList();
		AjaxJson j = new AjaxJson();
		tScNotice.setContent(tScNotice.getContent().replaceAll("\"", "&quot"));
		String message = "更新成功";
		try{
			tScNoticeService.updateMain(tScNotice, tScNoticeFileList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新公告通知失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 公告通知新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScNoticeEntity tScNotice, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScNotice.getId())) {
			tScNotice = tScNoticeService.getEntity(TScNoticeEntity.class, tScNotice.getId());
			req.setAttribute("tScNoticePage", tScNotice);
		}
		return new ModelAndView("com/qihang/buss/sc/oa/tScNotice-add");
	}
	
	/**
	 * 公告通知编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScNoticeEntity tScNotice, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScNotice.getId())) {
			tScNotice = tScNoticeService.getEntity(TScNoticeEntity.class, tScNotice.getId());
			req.setAttribute("tScNoticePage", tScNotice);
			req.setAttribute("userIdList", tScNotice.getUserId());//通知人员

			//查看后将公告状态标记为已读
			String load = req.getParameter("load");
			if("detail".equals(load)) {
				String userId = ResourceUtil.getSessionUserName().getId();
				TScNoticeRelationEntity relationEntity = new TScNoticeRelationEntity();
				relationEntity.setUserId(userId);
				relationEntity.setNoticeId(tScNotice.getId());
				List<TScNoticeRelationEntity> relationEntityList = systemService.findByExample(TScNoticeRelationEntity.class.getName(), relationEntity);
				if (relationEntityList.size() > 0) {
					relationEntity = relationEntityList.get(0);
					relationEntity.setStatus(1);
					systemService.saveOrUpdate(relationEntity);
				}
			}
		}
		return new ModelAndView("com/qihang/buss/sc/oa/tScNotice-update");
	}
	
	
	/**
	 * 加载明细列表[公告通知附件表]
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScNoticeFileList")
	public ModelAndView tScNoticeFileList(TScNoticeEntity tScNotice, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object id0 = tScNotice.getId();
		//===================================================================================
		//查询-公告通知附件表
	    String hql0 = "from TScNoticeFileEntity where 1 = 1 AND nOTE_ID = ? ";
	    try{
	    	List<TScNoticeFileEntity> tScNoticeFileEntityList = systemService.findHql(hql0,id0);
			req.setAttribute("tScNoticeFileList", tScNoticeFileEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/qihang/buss/sc/oa/tScNoticeFileList");
	}

	/**
	 * 公告信息发布
	 * @param id
	 * @return
	 */
	@RequestMapping(params = "release")
	@ResponseBody
	public Map<String,Object> release(String id){
		Map<String,Object> result = tScNoticeService.release(id);
		return result;
	}

	/**
	 * 获取通知人员树形结构
	 * @param type
	 * @param noticeId
     * @return
     */
	@RequestMapping(params = "getUserTreeInfo")
	@ResponseBody
	public com.alibaba.fastjson.JSONArray getUserTreeInfo(String type, String noticeId,String style){
		if(StringUtil.isEmpty(type)){
			TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart depart = systemService.getParentSonInfo(sonInfo);
			type = depart.getId();
		}
		return tScNoticeService.getUserTreeInfo(type, noticeId, style);
	}

	@RequestMapping(params = "loadTypeInfo")
	@ResponseBody
	public List<Map<String,Object>> loadTypeInfo(){
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		Set<String> childIds = systemService.getAllSonId(depart.getId());
		List<Map<String,Object>> typeInfo = tScNoticeService.loadTypeInfo(childIds,depart);
		return typeInfo;
	}

	//首页公告数据
	@RequestMapping(params = "datagrid_main")
	@ResponseBody
	public List<Map<String,Object>> datagrid_main(HttpServletRequest request){
		String userId = request.getParameter("userId");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
		List<TScNoticeRelationEntity> noticeEntityList = tScNoticeService.findHql("from TScNoticeRelationEntity where userId = ? and status = 0",new Object[]{userId});
		if(noticeEntityList.size() > 0){
			//默认展示10行未读数据
			int length = noticeEntityList.size();
			if(length > 10){
				length = 10;
			}
			for(int i = 0 ; i < length ; i++){
				TScNoticeRelationEntity relationEntity = noticeEntityList.get(i);
				if(StringUtil.isNotEmpty(relationEntity.getNoticeId())) {
					TScNoticeEntity noticeEntity = tScNoticeService.getEntity(TScNoticeEntity.class, relationEntity.getNoticeId());
					if(null != noticeEntity) {
						Map<String, Object> notickInfo = new HashMap<String, Object>();
						notickInfo.put("id", noticeEntity.getId());
						notickInfo.put("state",relationEntity.getStatus());
						notickInfo.put("title",noticeEntity.getTitle());
						notickInfo.put("issueDate",sdf.format(noticeEntity.getIssuanceDate()));
						result.add(notickInfo);
					}
				}
			}
		}
		return result;
	}
	
}
