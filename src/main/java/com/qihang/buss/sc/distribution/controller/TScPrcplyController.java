
package com.qihang.buss.sc.distribution.controller;

import com.qihang.buss.sc.baseinfo.entity.TScDepartmentEntity;
import com.qihang.buss.sc.baseinfo.entity.TScEmpEntity;
import com.qihang.buss.sc.baseinfo.entity.TScIcitemEntity;
import com.qihang.buss.sc.baseinfo.entity.TScOrganizationEntity;
import com.qihang.buss.sc.baseinfo.service.CountCommonServiceI;
import com.qihang.buss.sc.baseinfo.service.TScItemTypeServiceI;
import com.qihang.buss.sc.baseinfo.service.TScOrganizationServiceI;
import com.qihang.buss.sc.distribution.entity.*;
import com.qihang.buss.sc.distribution.page.TScPrcplyPage;
import com.qihang.buss.sc.distribution.service.TScPrcplyServiceI;
import com.qihang.buss.sc.distribution.service.TScPromotionServiceI;
import com.qihang.buss.sc.sales.entity.TScSlStockbillentryEntity;
import com.qihang.buss.sc.sales.service.TScOrderServiceI;
import com.qihang.buss.sc.sysaudit.entity.TSAuditEntity;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
import com.qihang.buss.sc.util.AccountUtil;
import com.qihang.buss.sc.util.BillCountUtil;
import com.qihang.buss.sc.util.BillNoGenerate;
import com.qihang.buss.sc.util.PriceUtil;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.ExceptionUtil;
import com.qihang.winter.core.util.ResourceUtil;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.poi.excel.ExcelImportUtil;
import com.qihang.winter.poi.excel.entity.ExportParams;
import com.qihang.winter.poi.excel.entity.ImportParams;
import com.qihang.winter.poi.excel.entity.TemplateExportParams;
import com.qihang.winter.poi.excel.entity.result.ExcelImportResult;
import com.qihang.winter.poi.excel.entity.vo.NormalExcelConstants;
import com.qihang.winter.poi.excel.entity.vo.TemplateExcelConstants;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.web.system.pojo.base.TSBaseUser;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.web.system.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Title: Controller
 * @Description: 促销管理
 * @author onlineGenerator
 * @date 2016-07-25 16:19:59
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScPrcplyController")
public class TScPrcplyController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScPrcplyController.class);

	@Autowired
	private TScPrcplyServiceI tScPrcplyService;
	@Autowired
	private SystemService systemService;

	@Autowired
	private TScOrderServiceI orderServiceI;//销售订单
	@Autowired
	private CountCommonServiceI countCommonService;

	@Autowired
	private TScPromotionServiceI promotionServiceI;
	@Autowired
	private TScOrganizationServiceI tScOrganizationService;

	@Autowired
	private TScItemTypeServiceI tScItemTypeService;

	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserService userService;
	/**
	 * 促销管理列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tScPrcply")
	public ModelAndView tScPrcply(HttpServletRequest request) {
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		List<TSAuditEntity> tsAuditEntityList = systemService.findHql("from TSAuditEntity where billId=? and sonId = ?", new Object[]{Globals.SC_PRCPLY_TRANTYPE.toString(), depart.getId()});
		if(tsAuditEntityList.size() > 0) {
			TSAuditEntity auditEntity = tsAuditEntityList.get(0);
			if (null != auditEntity) {
				Integer isEdit = auditEntity.getIsEdit();
				request.setAttribute("isEdit", isEdit);
			}
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String checkDate = sdf.format(AccountUtil.getAccountStartDate());
		request.setAttribute("checkDate", checkDate);
		return new ModelAndView("com/qihang/buss/sc/distribution/tScPrcplyList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScPrcplyViewEntityEntity tScPrcply,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScPrcplyViewEntityEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScPrcply);
		try{
			//自定义追加查询条件
			String query_date_begin = request.getParameter("date_begin");
			String query_date_end = request.getParameter("date_end");

			//起始日期
			String query_begDate_begin = request.getParameter("begDate_begin");
			String query_begDate_end = request.getParameter("begDate_end");

			//结束日期
			String query_endDate_begin = request.getParameter("endDate_begin");
			String query_endDate_end = request.getParameter("endDate_end");

			//审核日期
			String query_checkDate_begin = request.getParameter("checkDate_begin");
			String query_checkDate_end = request.getParameter("checkDate_end");

			if(StringUtil.isNotEmpty(query_date_begin)){
				cq.ge("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_begin));
			}
			if(StringUtil.isNotEmpty(query_date_end)){
				cq.le("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_end));
			}

			if (StringUtil.isNotEmpty(query_begDate_begin)) {
				cq.ge("begDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_begDate_begin));
			}
			if (StringUtil.isNotEmpty(query_begDate_end)) {
				cq.le("begDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_begDate_end));
			}

			if (StringUtil.isNotEmpty(query_endDate_begin)) {
				cq.ge("endDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_endDate_begin));
			}
			if (StringUtil.isNotEmpty(query_endDate_end)) {
				cq.le("endDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_endDate_end));
			}
			if (StringUtil.isNotEmpty(query_checkDate_begin)) {
				cq.ge("checkDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_checkDate_begin));
			}
			if (StringUtil.isNotEmpty(query_endDate_end)) {
				cq.le("checkDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_checkDate_end));
			}
			String isWarm = request.getParameter("isWarm");
			if(org.apache.commons.lang.StringUtils.isNotEmpty(isWarm)) {
				String userId = ResourceUtil.getSessionUserName().getId();
				TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
				TSDepart depart = systemService.getParentSonInfo(sonInfo);
				Boolean isAudit = userService.isAllowAudit(Globals.SC_PRCPLY_TRANTYPE.toString(), userId, depart.getId());
				cq.eq("cancellation",0);
				//判断当前用户是否在多级审核队列中
				if (isAudit) {
					Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId, depart.getId(), Globals.SC_PRCPLY_TRANTYPE.toString());
					if (userAuditLeave.size() > 0) {
						String leaves = "";
						for (Integer leave : userAuditLeave) {
							leaves += leave + ",";
						}
						leaves = leaves.substring(0, leaves.length() - 1);
						List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in (" + leaves + ")", new Object[]{depart.getId(), Globals.SC_PRCPLY_TRANTYPE.toString()});
						if (billInfo.size() > 0) {
							List<String> idArr = new ArrayList<String>();
							for (TScBillAuditStatusEntity entity : billInfo) {
								idArr.add(entity.getBillId());
							}
							cq.in("id", idArr.toArray());
						}else {
							cq.eq("id","0");
						}
					}
				}
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScPrcplyService.getDataGridReturn(cq, true);
		String tranType = request.getParameter("tranType");
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(tranType)) {
			List<TScPrcplyViewEntityEntity> result = dataGrid.getResults();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (TScPrcplyViewEntityEntity entity : result) {
				if(StringUtil.isNotEmpty(entity.getEntryId())){
					TSAuditRelationEntity info = systemService.getAuditInfo(entity.getId(), tranType);
					if (info != null) {
						if (1 == info.getIsFinish()) {
							entity.setStatus(2);
							entity.setAuditorName(info.getAuditorName());
							entity.setAuditDate(sdf.format(info.getAuditDate()));
						} else {
							entity.setCheckState(1);
							entity.setAuditorName(info.getAuditorName());
							entity.setAuditDate(sdf.format(info.getAuditDate()));
						}
					} else {
						entity.setStatus(0);
					}
				}
			}
			dataGrid.setResults(result);
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除促销管理
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScPrcplyEntity tScPrcply, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScPrcply = systemService.getEntity(TScPrcplyEntity.class, tScPrcply.getId());
		String message = "调价政策单删除成功";
		try{
			tScPrcplyService.delMain(tScPrcply);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			//删除待审核预警数据
			systemService.delBillAuditStatus(tScPrcply.getTranType().toString(), tScPrcply.getId());
		} catch(Exception e){
			e.printStackTrace();
			message = "调价政策单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除促销管理
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "调价政策单删除成功";
		try{
			for(String id:ids.split(",")){
				TScPrcplyEntity tScPrcply = systemService.getEntity(TScPrcplyEntity.class,
				id
				);
				tScPrcplyService.delMain(tScPrcply);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "调价政策单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加促销管理
	 *
	 * @param tScPrcply
	 * @param tScPrcplyPage
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScPrcplyEntity tScPrcply,TScPrcplyPage tScPrcplyPage, HttpServletRequest request) {
		List<TScPrcplyentry2Entity> tScPrcplyentry2List =  tScPrcplyPage.getTScPrcplyentry2List();
		List<TScPrcplyentry1Entity> tScPrcplyentry1List =  tScPrcplyPage.getTScPrcplyentry1List();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			tScPrcply.setTranType(Integer.parseInt(Globals.SC_PRCPLY_TRANTYPE.toString()));
			tScPrcply.setBillerID(ResourceUtil.getSessionUserName().getId());
			tScPrcply.setCancellation(Globals.CANCELLATION_NO);
			TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart departMain = systemService.getParentSonInfo(depart);
			tScPrcply.setSonID(departMain.getId());
			tScPrcply.setCheckState(Globals.AUDIT_NO);
			tScPrcplyService.addMain(tScPrcply, tScPrcplyentry2List, tScPrcplyentry1List);

			//经办人
			countCommonService.addUpdateCount("t_sc_emp", tScPrcply.getEmpID());
			//部门
			countCommonService.addUpdateCount("t_sc_department",tScPrcply.getDeptID());
			//分支机构
			countCommonService.addUpdateCount("T_SC_SonCompany",tScPrcply.getSonID());

			//客户
			for(TScPrcplyentry1Entity entity1:tScPrcplyentry1List) {
				countCommonService.addUpdateCount("t_sc_department",entity1.getItemID());
			}
			//商品
			for(TScPrcplyentry2Entity entity2 :tScPrcplyentry2List){
				countCommonService.addUpdateCount("t_sc_icitem",entity2.getItemID());
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			//待审核数据提醒操作
			//systemService.saveBillAuditStatus(tScPrcply.getTranType().toString(), tScPrcply.getId());
		} catch(Exception e){
			e.printStackTrace();
			message = "促销管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新促销管理
	 *
	 * @param tScPrcply
	 * @param tScPrcplyPage
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScPrcplyEntity tScPrcply,TScPrcplyPage tScPrcplyPage, HttpServletRequest request) {
		List<TScPrcplyentry2Entity> tScPrcplyentry2List =  tScPrcplyPage.getTScPrcplyentry2List();
		List<TScPrcplyentry1Entity> tScPrcplyentry1List =  tScPrcplyPage.getTScPrcplyentry1List();
		AjaxJson j = new AjaxJson();
		String message = "更新调价政策单成功";
		try{
			TScPrcplyEntity tScPrcplyEntity = tScPrcplyService.get(TScPrcplyEntity.class, tScPrcply.getId());
			sessionFactory.getCurrentSession().evict(tScPrcplyEntity);
			tScPrcplyService.updateMain(tScPrcply, tScPrcplyentry2List, tScPrcplyentry1List);
			//经办人
			countCommonService.editUpdateCount("t_sc_emp", tScPrcplyEntity.getEmpID(), tScPrcply.getEmpID());
			//部门
			countCommonService.editUpdateCount("t_sc_department", tScPrcplyEntity.getDeptID(), tScPrcply.getDeptID());
			//分支机构
			countCommonService.editUpdateCount("T_SC_SonCompany", tScPrcplyEntity.getSonID(), tScPrcply.getSonID());

			//客户
			for(TScPrcplyentry1Entity entity1:tScPrcplyentry1List) {
				countCommonService.editUpdateCount("t_sc_department", entity1.getItemID(), entity1.getItemID());
			}
			//商品
			for(TScPrcplyentry2Entity entity2 :tScPrcplyentry2List){
				countCommonService.editUpdateCount("t_sc_icitem", entity2.getItemID(), entity2.getItemID());
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新调价政策单失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 促销管理新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScPrcplyEntity tScPrcply, HttpServletRequest req) {
		/*if (StringUtil.isNotEmpty(tScPrcply.getId())) {
			tScPrcply = tScPrcplyService.getEntity(TScPrcplyEntity.class, tScPrcply.getId());
			req.setAttribute("tScPrcplyPage", tScPrcply);
		}*/
		try {
			String sonName = ResourceUtil.getSessionUserName().getCurrentDepart().getDepartname();
			req.setAttribute("sonName", sonName);
			tScPrcply.setBillNo(BillNoGenerate.getBillNo(Globals.SC_QUOTE_TRANTYPE));
			req.setAttribute("tScPrcplyPage", tScPrcply);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			req.setAttribute("date", sdf.format(new Date()));
			String checkDate = sdf.format(AccountUtil.getAccountStartDate());
			req.setAttribute("checkDate", checkDate);

			//当前用户所在分支机构
			TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart depart = systemService.getParentSonInfo(sonInfo);
			req.setAttribute("sonId",depart.getId());
			req.setAttribute("sonName", depart.getDepartname());
		}catch (Exception e){
			e.printStackTrace();
		}
		return new ModelAndView("com/qihang/buss/sc/distribution/tScPrcply-add");
	}

	/**
	 * 促销管理编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScPrcplyEntity tScPrcply, HttpServletRequest req) {
		try {
			if (StringUtil.isNotEmpty(tScPrcply.getId())) {
				tScPrcply = tScPrcplyService.getEntity(TScPrcplyEntity.class, tScPrcply.getId());
				//经办人
				if (org.apache.commons.lang3.StringUtils.isNotEmpty(tScPrcply.getEmpID())) {
					TScEmpEntity entity = systemService.get(TScEmpEntity.class, tScPrcply.getEmpID());
					if (entity != null) {
						if (org.apache.commons.lang.StringUtils.isNotEmpty(entity.getName())) {
							tScPrcply.setEmpName(entity.getName());
							tScPrcply.setEmpID(entity.getId());
						}
					}
				}
				//部门
				if (org.apache.commons.lang3.StringUtils.isNotEmpty(tScPrcply.getDeptID())) {
					TScDepartmentEntity entity = systemService.get(TScDepartmentEntity.class, tScPrcply.getDeptID());
					if (entity != null) {
						if (org.apache.commons.lang3.StringUtils.isNotEmpty(entity.getName())) {
							tScPrcply.setDeptName(entity.getName());
							tScPrcply.setDeptID(entity.getId());
						}
					}
				}
				//分支机构
				if (org.apache.commons.lang3.StringUtils.isNotEmpty(tScPrcply.getSonID())) {
					TSDepart dept = systemService.get(TSDepart.class, tScPrcply.getSonID());
					if (null != dept) {
						tScPrcply.setSonName(dept.getDepartname());
					}
				}
				if (org.apache.commons.lang3.StringUtils.isNotEmpty(tScPrcply.getBillerID())) {
					TSBaseUser user = systemService.get(TSBaseUser.class, tScPrcply.getBillerID());
					if (null != user) {
						tScPrcply.setBillerName(user.getRealName());
					}
				}
				//审核人和审核日期
				List<TSAuditRelationEntity> info = systemService.getAuditInfoList(tScPrcply.getId(), String.valueOf(tScPrcply.getTranType()));
				String auditor = "";
				String auditDate = "";
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				for (int i = 0; i < info.size(); i++) {
					TSAuditRelationEntity entity = info.get(i);
//                if(1 == entity.getIsFinish()){
//                    tScOrder.setCheckState(Globals.AUDIT_FIN+"");
//                }
					auditor += entity.getStatus() + "级审核：<u>" + entity.getAuditorName() + "</u>;";
					if (i == 2) {
						auditor += "</br>";
					} else {
						auditor += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
					}
					auditDate = sdf.format(entity.getAuditDate());
				}
				req.setAttribute("auditor", auditor);
				req.setAttribute("auditDate", auditDate);
				req.setAttribute("tScPrcplyPage", tScPrcply);
				String checkDate = sdf.format(AccountUtil.getAccountStartDate());
				req.setAttribute("checkDate", checkDate);
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return new ModelAndView("com/qihang/buss/sc/distribution/tScPrcply-update");
	}

  /**
  * 导入功能跳转
  *
  * @return
  */
  @RequestMapping(params = "upload")
  public ModelAndView upload(HttpServletRequest req) {
  req.setAttribute("controller_name","tScPrcplyController");
  return new ModelAndView("common/upload/pub_excel_upload");
  }

  /**
  * 导出excel
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXls")
  public String exportXls(TScPrcplyEntity tScPrcply,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  CriteriaQuery cq = new CriteriaQuery(TScPrcplyEntity.class, dataGrid);
  com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScPrcply, request.getParameterMap());
  List<TScPrcplyEntity> tScPrcplys = this.tScPrcplyService.getListByCriteriaQuery(cq,false);
  //如需动态排除部分列不导出时启用，列名指Excel中文列名
  String[] exclusions = {"排除列名1","排除列名2"};
  modelMap.put(NormalExcelConstants.FILE_NAME,"促销管理");
  modelMap.put(NormalExcelConstants.CLASS,TScPrcplyEntity.class);
  modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("促销管理列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
  "导出信息",exclusions));
  modelMap.put(NormalExcelConstants.DATA_LIST, tScPrcplys);
  return NormalExcelConstants.JEECG_EXCEL_VIEW;
  }
  /**
  * 导出excel 使模板
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXlsByT")
  public String exportXlsByT(TScPrcplyEntity tScPrcply,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  modelMap.put(TemplateExcelConstants.FILE_NAME, "促销管理");
  modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
  modelMap.put(TemplateExcelConstants.MAP_DATA,null);
  modelMap.put(TemplateExcelConstants.CLASS,TScPrcplyEntity.class);
  modelMap.put(TemplateExcelConstants.LIST_DATA, null);
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
  params.setSaveUrl("upload/excelUpload");
  try {
    ExcelImportResult importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(),TScPrcplyEntity.class,params);
    List<TScPrcplyEntity> listTScPrcplyEntitys = importResult.getList();
    StringBuilder stringBuilder = new StringBuilder("已存在的数据:");
    boolean flag = false;
    if(!importResult.isVerfiyFail()) {
      for (TScPrcplyEntity tScPrcply : listTScPrcplyEntitys) {
      //以下检查导入数据是否重复的语句可视需求启用
        //Long count = tScPrcplyService.getCountForJdbc("这里放检查数据是否重复的SQL语句");
        //if(count >0) {
          //flag = true;
          //stringBuilder.append(tScPrcply.getId()+",");
        //} else {
          tScPrcplyService.save(tScPrcply);
        //}
      }
      j.setMsg("文件导入成功！");
      //j.setMsg("文件导入成功！" + (flag? stringBuilder.toString():""));
    }else {
      String excelPath = "/upload/excelUpload/TScPrcplyEntity/"+importResult.getExcelName();
      j.setMsg("文件导入校验失败，详见<a href='"+excelPath+"' target='_blank' style='color:blue;font-weight:bold;'>要显示的导入文件名</a>");
    }
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
	 * 加载明细列表[商品从表]
	 *
	 * @return
	 */
	@RequestMapping(params = "tScPrcplyentry2List")
	public ModelAndView tScPrcplyentry2List(TScPrcplyEntity tScPrcply, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id0 = tScPrcply.getId();
		//===================================================================================
		//查询-商品从表
	    String hql0 = "from TScPrcplyentry2Entity where 1 = 1 AND iNTERID = ? order by indexNumber";
	    try{
	    	List<TScPrcplyentry2Entity> tScPrcplyentry2EntityList = systemService.findHql(hql0,id0);
			for(TScPrcplyentry2Entity prcplyentry2Entity:tScPrcplyentry2EntityList){
				if(StringUtils.isNotEmpty(prcplyentry2Entity.getItemID())){
					TScIcitemEntity icitemEntity = systemService.get(TScIcitemEntity.class,prcplyentry2Entity.getItemID());
					if(icitemEntity != null){
						if(StringUtils.isNotEmpty(icitemEntity.getNumber())){
							prcplyentry2Entity.setItemNo(icitemEntity.getNumber());
						}
						if(StringUtils.isNotEmpty(icitemEntity.getName())){
							prcplyentry2Entity.setItemName(icitemEntity.getName());
						}
						if(StringUtils.isNotEmpty(icitemEntity.getModel())){
							prcplyentry2Entity.setModel(icitemEntity.getModel());
						}
					}
				}
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String minDate = sdf.format(new Date());
			String maxDate = "2516-01-01";
			req.setAttribute("minDate", minDate);
			req.setAttribute("maxDate", maxDate);
			req.setAttribute("tScPrcplyentry2List", tScPrcplyentry2EntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/qihang/buss/sc/distribution/tScPrcplyentry2List");
	}
	/**
	 * 加载明细列表[客户从表]
	 *
	 * @return
	 */
	@RequestMapping(params = "tScPrcplyentry1List")
	public ModelAndView tScPrcplyentry1List(TScPrcplyEntity tScPrcply, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id1 = tScPrcply.getId();
		//===================================================================================
		//查询-客户从表
	    String hql1 = "from TScPrcplyentry1Entity where 1 = 1 AND iNTERID = ? order by indexNumber";
	    try{
	    	List<TScPrcplyentry1Entity> tScPrcplyentry1EntityList = systemService.findHql(hql1,id1);
			for(TScPrcplyentry1Entity prcplyentry1Entity: tScPrcplyentry1EntityList){
				if(StringUtils.isNotEmpty(prcplyentry1Entity.getItemID())){
					TScOrganizationEntity cust = systemService.get(TScOrganizationEntity.class, prcplyentry1Entity.getItemID());
					if(StringUtils.isNotEmpty(cust.getName())){
						prcplyentry1Entity.setItemName(cust.getName());
					}
					if(StringUtils.isNotEmpty(cust.getContact())){
						prcplyentry1Entity.setContact(cust.getContact());
					}
					if(StringUtils.isNotEmpty(cust.getMobilephone())){
						prcplyentry1Entity.setMobilePhone(cust.getMobilephone());
					}
					if(StringUtils.isNotEmpty(cust.getPhone())){
						prcplyentry1Entity.setPhone(cust.getPhone());
					}
					if(StringUtils.isNotEmpty(cust.getAddress())){
						prcplyentry1Entity.setAddress(cust.getAddress());
					}
					if(StringUtils.isNotEmpty(cust.getFax())){
						prcplyentry1Entity.setFax(cust.getFax());
					}
				}

			}
			req.setAttribute("tScPrcplyentry1List", tScPrcplyentry1EntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/qihang/buss/sc/distribution/tScPrcplyentry1List");
	}

	@RequestMapping(params = "cancelBill")
	@ResponseBody
	public AjaxJson cancelBill(String ids){
		AjaxJson j = tScPrcplyService.cancelBill(ids);
		if(j.isSuccess()) {
			//校验订单是否自动关闭
			String[] idList = ids.split(",");
			Set<String> mainId = new HashSet<String>();
			for (String id : idList) {
				List<TScSlStockbillentryEntity> entryList = tScPrcplyService.findHql("from TScSlStockbillentryEntity where fid = ?", new Object[]{id});
				for (TScSlStockbillentryEntity entry : entryList) {
					if (StringUtils.isNotEmpty(entry.getIdOrder())) {
						mainId.add(entry.getIdOrder());
					}
				}
			}
			if (mainId.size() > 0) {
				orderServiceI.checkAutoFlag(mainId);
			}
		}
		return j;
	}

	//反作废
	@RequestMapping(params = "enableBill")
	@ResponseBody
	public AjaxJson enableBill(String ids){
		AjaxJson j = tScPrcplyService.enableBill(ids);
		if(j.isSuccess()) {
			//校验订单是否自动关闭
			String[] idList = ids.split(",");
			Set<String> mainId = new HashSet<String>();
			for (String id : idList) {
				List<TScSlStockbillentryEntity> entryList = tScPrcplyService.findHql("from TScSlStockbillentryEntity where fid = ?", new Object[]{id});
				for (TScSlStockbillentryEntity entry : entryList) {
					if (StringUtils.isNotEmpty(entry.getIdOrder())) {
						mainId.add(entry.getIdOrder());
					}
				}
			}
			if (mainId.size() > 0) {
				orderServiceI.checkAutoFlag(mainId);
			}
		}
		return j;
	}

	/**
	 * 根据提供数据，获取报价单单价
	 * @param tranType
	 * @param customerId
	 * @param itemId
	 * @param qty
	 * @return
	 */
	@RequestMapping(params = "getPrice")
	@ResponseBody
	public AjaxJson getPrice(String tranType,String customerId,String itemId,BigDecimal qty,String unitId){
		//TODO 还需要数量字段,单位字段
		AjaxJson j = new AjaxJson();
		Map<String, Object> priceInfo = PriceUtil.getPrice(tranType, customerId, itemId, unitId, qty);
		Double price = ((BigDecimal) priceInfo.get("price")).doubleValue();//单价
		String priceType = (String) priceInfo.get("priceType");//单价类型
		Map<String,Object> attribute = new HashMap<String, Object>();
		//attribute.put("price",price);
		//调价政策单价
		//TScItemPriceEntity unit = systemService.getEntity(TScItemPriceEntity.class,unitId);
		//List<TScPrcplyViewEntityEntity> lastPrice = prcplyServiceI.getLatePrice(entryItemId,itemId,unitId,qty.doubleValue());
		//若调价政策不存在
		//if(lastPrice.size() == 0) {
		//查询买一赠一
		//调价政策
		/**
		 * pap 调价政策单
		 * sq  销售报价
		 * ps  单价设置
		 * sp  供货报价
		 */
		if("pap".equals(priceType)){
			attribute.put("price", price);
			attribute.put("salesID", "");
			attribute.put("salesType", "0");
		}else {
			List<TScPromotionViewEntity> promotiongList = promotionServiceI.getGiftsGoodsInfo(itemId, qty.doubleValue());
			if (promotiongList.size() > 0) {
				List<Map<String, Object>> itemInfo = new ArrayList<Map<String, Object>>();
				//匹配赠品数据
				for (TScPromotionViewEntity view : promotiongList) {
					Map<String, Object> map1 = new HashMap<String, Object>();
					map1.put("itemId", view.getGiftItemId());
					map1.put("itemName", view.getGiftName());
					map1.put("itemNo", view.getGiftNumber());
					map1.put("model", view.getGiftModel());
					map1.put("barCode", view.getGiftBarCode());
					map1.put("unitId", view.getGiftUnitId());
					map1.put("qty", view.getGiftQty());
					map1.put("coefficient", view.getCoefficient());
					Double basicQty = BigDecimal.valueOf(view.getGiftQty()).multiply(BigDecimal.valueOf(view.getCoefficient())).doubleValue();
					map1.put("basicQty", basicQty);
					itemInfo.add(map1);
				}
				//促销类型id
				attribute.put("salesID", promotiongList.get(0).getId());
				attribute.put("salesType", "1");
				attribute.put("price", price);
				attribute.put("freeGiftInfo", itemInfo);
			} else {
				attribute.put("price", price);
				attribute.put("salesID", "");
				attribute.put("salesType", "2");
				j.setSuccess(false);
			}
		}
		//}else{
		//TScPrcplyViewEntityEntity info = lastPrice.get(0);
		//attribute.put("price",info.getNewPrice());
		//attribute.put("salesID", info.getId());
		//attribute.put("salesType", "0");
		//}
		j.setAttributes(attribute);
		return j;
	}
	/**
	 *销售报价的弹出框
	 */
	@RequestMapping(params = "getQuoteDialog")
	public ModelAndView getQuoteDialog(@RequestParam(value = "checkState",required = false)Integer checkState,
									   @RequestParam(value = "customerId",required = false)String customerId, HttpServletRequest req){
		if(checkState!=null){
			req.setAttribute("checkState", checkState);
		}
		if(org.apache.commons.lang.StringUtils.isNotEmpty(customerId)){
			req.setAttribute("customerId", customerId);
		}
		return new ModelAndView("com/qihang/buss/sc/distribution/tScQuoteDialog");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagridSelect")
	public void datagridSelect(TScOrganizationEntity tScOrganization, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//判断是否进行查询
		String name = tScOrganization.getName();
		Boolean isSelect = false;
		if(org.apache.commons.lang.StringUtils.isNotEmpty(name)) {
			if (name.indexOf("_") > -1) {
				isSelect = true;
				name = name.replace("_", "");
				tScOrganization.setName("");
			}
		}
		CriteriaQuery cq = new CriteriaQuery(TScOrganizationEntity.class, dataGrid);
		List<String> list = new ArrayList<String>();
		if (StringUtil.isNotEmpty(tScOrganization.getParentid())) {
			TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart depart = systemService.getParentSonInfo(sonInfo);
			list = tScItemTypeService.getParentIdFromTree("01", tScOrganization.getParentid(),depart.getId());
			tScOrganization.setParentid(null);

		}
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScOrganization, request.getParameterMap());
		try {
			//自定义追加查询条件
			if (list.size() > 0) {
				cq.in("parentid", list.toArray());
			}
			//如若弹窗查询
			if(isSelect){
				cq.add(Restrictions.or(
						Restrictions.like("name", "%" + name + "%"),
						Restrictions.or(
								Restrictions.like("number", "%" + name + "%"),
								Restrictions.or(
										Restrictions.like("shortnumber", "%" + name + "%"), Restrictions.like("shortname", "%" + name + "%")))));
			}
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScOrganizationService.getDataGridReturn(cq, true);
		List<TScOrganizationEntity> organizationEntityList = dataGrid.getResults();
		for(TScOrganizationEntity entity : organizationEntityList){
			BigDecimal countAmount = BillCountUtil.getReceivableCountByCustomerId(entity.getId());
			entity.setCountAmount(countAmount);
		}
		String tranType = request.getParameter("tranType");
		if(org.apache.commons.lang3.StringUtils.isNotEmpty(tranType)){
			List<TScOrganizationEntity> result = dataGrid.getResults();
			List<TScOrganizationEntity> newInfo = new ArrayList<TScOrganizationEntity>();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for(TScOrganizationEntity entity : result) {
				TSAuditRelationEntity info  = systemService.getAuditInfo(entity.getId(), tranType);
				if(info != null){
					if(1 == info.getIsFinish()){
						entity.setStatus(2);
						entity.setAuditorName(info.getAuditorName());
						entity.setAuditDate(sdf.format(info.getAuditDate()));
					}else{
						entity.setStatus(1);
						entity.setAuditorName(info.getAuditorName());
						entity.setAuditDate(sdf.format(info.getAuditDate()));
					}
				}else {
					entity.setStatus(0);
				}
			}
			dataGrid.setResults(result);
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 客户新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goSelectDialog")
	public ModelAndView goSelectDialog(TScOrganizationEntity tScOrganization, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScOrganization.getName())) {
			req.setAttribute("name", tScOrganization.getName());
		}
		if(tScOrganization.getDisable() == Globals.IS_DEALER){
			req.setAttribute("isdealer", tScOrganization.getIsDealer());
		}
		if(null != tScOrganization.getIsDealer() && tScOrganization.getIsDealer().equals(Globals.IS_DEALER)){
			req.setAttribute("isdealer", tScOrganization.getIsDealer());
		}
		return new ModelAndView("com/qihang/buss/sc/distribution/tScOrganizationSelect2Prcply");
	}

	@RequestMapping(params = "getCustInfo")
	@ResponseBody
	public AjaxJson getCustInfo(String custId,HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		TScOrganizationEntity tScOrganizationEntity = systemService.get(TScOrganizationEntity.class,custId);
		Map<String,Object> map = new HashMap<String, Object>();
		j.setSuccess(false);
		if(null != tScOrganizationEntity){
			map.put("custInfo",tScOrganizationEntity);
			j.setAttributes(map);
			j.setSuccess(true);
		}
		return  j;
	}
}
