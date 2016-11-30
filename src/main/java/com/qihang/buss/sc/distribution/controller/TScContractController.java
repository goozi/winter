package com.qihang.buss.sc.distribution.controller;

import com.qihang.buss.sc.baseinfo.entity.TScOrganizationEntity;
import com.qihang.buss.sc.baseinfo.service.CountCommonServiceI;
import com.qihang.buss.sc.baseinfo.service.TScEmpServiceI;
import com.qihang.buss.sc.distribution.entity.TScContractEntity;
import com.qihang.buss.sc.distribution.entity.TScPrcplyViewEntityEntity;
import com.qihang.buss.sc.distribution.service.TScContractServiceI;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
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
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.web.system.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
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
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Title: Controller
 * @Description: 合同管理
 * @author onlineGenerator
 * @date 2016-07-19 15:21:26
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScContractController")
public class TScContractController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScContractController.class);

	@Autowired
	private TScContractServiceI tScContractService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TScEmpServiceI tScEmpServiceI;
	@Autowired
	private CountCommonServiceI countCommonService;

	@Autowired
	private UserService userService;
	@Autowired
	private SessionFactory sessionFactory;
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 合同管理列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tScContract")
	public ModelAndView tScContract(HttpServletRequest request) {
		String hql0 = "from TScOrganizationEntity where 1 = 1 AND disable = ? AND isDealer = ? ";
		List<TScOrganizationEntity> list = systemService.findHql(hql0,0,1);
		request.setAttribute("organList",list);

		return new ModelAndView("com/qihang/buss/sc/distribution/tScContractList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScContractEntity tScContract,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScContractEntity.class, dataGrid);
		//查询条件组装器
		try{
			//合同日期
			String query_date_begin = request.getParameter("date_begin");
			String query_date_end = request.getParameter("date_end");

			if(StringUtil.isNotEmpty(query_date_begin)){
				cq.ge("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_begin));
			}
			if(StringUtil.isNotEmpty(query_date_end)){
				cq.le("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_end));
			}
			String itemName = oConvertUtils.getString(request.getParameter("itemName"));
			if(!StringUtil.isEmpty(itemName)){
				cq.createAlias("tScOrganizationEntity", "tScOrganizationEntity");

				cq.like("tScOrganizationEntity.name", itemName);

			}
			//未审核预警数据
			String isWarm = request.getParameter("isWarm");
			if(org.apache.commons.lang.StringUtils.isNotEmpty(isWarm)){
				String userId = ResourceUtil.getSessionUserName().getId();
				TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
				TSDepart depart = systemService.getParentSonInfo(sonInfo);
				Boolean isAudit = userService.isAllowAudit(tScContract.getTrantype().toString(),userId,depart.getId());
				cq.eq("cancellation",0);
				//判断当前用户是否在多级审核队列中
				if(isAudit){
					Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId,depart.getId(),tScContract.getTrantype().toString());
					if(userAuditLeave.size() > 0){
						String leaves = "";
						for(Integer leave : userAuditLeave){
							leaves += leave+",";
						}
						leaves = leaves.substring(0,leaves.length()-1);
						List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in ("+leaves+")",new Object[]{depart.getId(),tScContract.getTrantype().toString()});
						if(billInfo.size() > 0){
							List<String> idArr = new ArrayList<String>();
							for(TScBillAuditStatusEntity entity : billInfo){
								idArr.add(entity.getBillId());
							}
							cq.in("id",idArr.toArray());
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
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScContract);

		this.tScContractService.getDataGridReturn(cq, true);

		String tranType = request.getParameter("tranType");
		if (org.apache.commons.lang3.StringUtils.isNotEmpty(tranType)) {
			List<TScPrcplyViewEntityEntity> result = dataGrid.getResults();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for (TScPrcplyViewEntityEntity entity : result) {
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
			dataGrid.setResults(result);
		}
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除合同管理
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScContractEntity tScContract, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScContract = systemService.getEntity(TScContractEntity.class, tScContract.getId());
		message = "合同管理删除成功";
		try{
			tScContractService.delete(tScContract);
			//修改经销商引用次数
			countCommonService.deleteUpdateCount("T_SC_Organization", tScContract.gettScOrganizationEntity().getId());
			//修改机构引用次数
			countCommonService.deleteUpdateCount("T_SC_SonCompany", tScContract.getSonID());

			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			//删除待审核预警数据
			systemService.delBillAuditStatus(tScContract.getTrantype().toString(), tScContract.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "合同管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除合同管理
	 *
	 * @return
	 */
	@RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "合同管理删除成功";
		try{
			for(String id:ids.split(",")){
				TScContractEntity tScContract = systemService.getEntity(TScContractEntity.class,
						id
				);
				tScContractService.delete(tScContract);
				//修改经销商引用次数
				countCommonService.deleteUpdateCount("T_SC_Organization",tScContract.gettScOrganizationEntity().getId());
				//修改机构引用次数
				countCommonService.deleteUpdateCount("T_SC_SonCompany", tScContract.getSonID());
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "合同管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加合同管理
	 *
	 * @param tScContract
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScContractEntity tScContract, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String itemID = request.getParameter("itemID");
		message = "合同管理添加成功";
		try{
			if(null != tScContract){
				if(StringUtil.isNotEmpty(tScContract.getBillNo())){//如果合同号不为空  通过合同号查询合同表 判断是否已经存在
					String hql = " from TScContractEntity where billNo = ? ";
					List<TScContractEntity> tScContractEntityList =  systemService.findHql(hql, tScContract.getBillNo());
					if(!tScContractEntityList.isEmpty()){
						message = "该合同号已经存在！";
						j.setSuccess(false);
					}else{
						TScOrganizationEntity organ = systemService.get(TScOrganizationEntity.class,itemID);
						tScContract.settScOrganizationEntity(organ);
						tScContractService.save(tScContract);
						//修改经销商引用次数
						countCommonService.addUpdateCount("T_SC_Organization", tScContract.gettScOrganizationEntity().getId());
						//修改机构引用次数
						countCommonService.addUpdateCount("T_SC_SonCompany", tScContract.getSonID());
						systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
						//待审核数据提醒操作
						//systemService.saveBillAuditStatus(tScContract.getTrantype().toString(), tScContract.getId());
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "合同管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新合同管理
	 *
	 * @param tScContract
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScContractEntity tScContract, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "合同管理更新成功";
		String itemID = request.getParameter("itemID");
		TScContractEntity t = tScContractService.get(TScContractEntity.class, tScContract.getId());
		try {
			TScOrganizationEntity organ = systemService.get(TScOrganizationEntity.class,itemID);
			tScContract.settScOrganizationEntity(organ);
			MyBeanUtils.copyBeanNotNull2Bean(tScContract, t);
			tScContractService.saveOrUpdate(t);
			//修改经销商引用次数
			countCommonService.editUpdateCount("T_SC_Organization",t.gettScOrganizationEntity().getId(),tScContract.gettScOrganizationEntity().getId());
			//修改机构引用次数
			countCommonService.editUpdateCount("T_SC_SonCompany",t.getSonID(), tScContract.getSonID());

			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "合同管理更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 合同管理新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScContractEntity tScContract, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScContract.getId())) {
			tScContract = tScContractService.getEntity(TScContractEntity.class, tScContract.getId());
			req.setAttribute("tScContractPage", tScContract);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		req.setAttribute("date",date);
		//当前用户所在分支机构
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		req.setAttribute("sonId",depart.getId());
		req.setAttribute("sonName",depart.getDepartname());
		return new ModelAndView("com/qihang/buss/sc/distribution/tScContract-add");
	}
	/**
	 * 合同管理编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScContractEntity tScContract, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScContract.getId())) {
			tScContract = tScContractService.getEntity(TScContractEntity.class, tScContract.getId());
			req.setAttribute("tScContractPage", tScContract);
		}
		if(StringUtils.isNotEmpty(tScContract.gettScOrganizationEntity().getId())){
			TScOrganizationEntity organizationEntity = tScEmpServiceI.get(TScOrganizationEntity.class, tScContract.gettScOrganizationEntity().getId());
			req.setAttribute("itemIDName", organizationEntity.getName());
		}
		if(StringUtils.isNotEmpty(tScContract.getSonID())){
			TSDepart tsDepart = tScEmpServiceI.get(TSDepart.class, tScContract.getSonID());
			req.setAttribute("sonName", tsDepart.getDepartname());
		}
		return new ModelAndView("com/qihang/buss/sc/distribution/tScContract-update");
	}

	/**
	 * 导入功能跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tScContractController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScContractEntity tScContract,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScContractEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScContract, request.getParameterMap());
//		List<TScContractEntity> tScRpExpensesapplys = this.tScContractService.getListByCriteriaQuery(cq,false);
		List<TScContractEntity> tScRpExpensesapplys = this.tScContractService.loadAll(TScContractEntity.class);
		for(TScContractEntity t : tScRpExpensesapplys){
			TSDepart ts = systemService.get(TSDepart.class,t.getSonID());
			t.setSonName(ts.getDepartname());
			t.setItemIDName(t.gettScOrganizationEntity().getName());
		}
		//如需动态排除部分列不导出时启用，列名指Excel中文列名
		String[] exclusions = {"排除列名1","排除列名2"};
		modelMap.put(NormalExcelConstants.FILE_NAME,"合同管理");
		modelMap.put(NormalExcelConstants.CLASS,TScContractEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("合同管理列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
				"导出信息",exclusions));
		modelMap.put(NormalExcelConstants.DATA_LIST,tScRpExpensesapplys);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScContractEntity tScContract,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "合同管理");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScContractEntity.class);
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
				List<TScContractEntity> listTScContractEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TScContractEntity.class,params);
				for (TScContractEntity tScContract : listTScContractEntitys) {
					tScContractService.save(tScContract);
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
}
