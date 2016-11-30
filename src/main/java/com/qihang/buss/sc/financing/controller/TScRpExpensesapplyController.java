
package com.qihang.buss.sc.financing.controller;
import com.qihang.buss.sc.baseinfo.entity.TScDepartmentEntity;
import com.qihang.buss.sc.baseinfo.entity.TScEmpEntity;
import com.qihang.buss.sc.baseinfo.entity.TScFeeEntity;
import com.qihang.buss.sc.baseinfo.entity.TScSettleacctEntity;
import com.qihang.buss.sc.financing.entity.*;
import com.qihang.buss.sc.financing.service.TScRpExpensesapplyServiceI;
import com.qihang.buss.sc.financing.page.TScRpExpensesapplyPage;
import com.qihang.buss.sc.sales.entity.TScSlStockbillViewEntity;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
import com.qihang.buss.sc.util.AccountUtil;
import com.qihang.buss.sc.util.BillNoGenerate;
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
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.pojo.base.TSUser;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.web.system.service.UserService;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Title: Controller
 * @Description: 费用申报单
 * @author onlineGenerator
 * @date 2016-09-07 11:51:52
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScRpExpensesapplyController")
public class TScRpExpensesapplyController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScRpExpensesapplyController.class);

	@Autowired
	private TScRpExpensesapplyServiceI tScRpExpensesapplyService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private UserService userService;


	/**
	 * 费用申报单列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tScRpExpensesapply")
	public ModelAndView tScRpExpensesapply(HttpServletRequest request) {
		request.setAttribute("tranType",Globals.SC_RP_BANKBILL_EXPENSESAPPLY);
		return new ModelAndView("com/qihang/buss/sc/financing/tScRpExpensesapplyList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScRpExpensesapplyViewEntity tScRpExpensesapply, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//汇总
		dataGrid.setFooter("allamount,amount,haveAmount,notAmount");
		CriteriaQuery cq = new CriteriaQuery(TScRpExpensesapplyViewEntity.class, dataGrid);
		cq.setOrders("billNo","findex");
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScRpExpensesapply);
		try{
			//自定义追加查询条件
			String query_date_begin = request.getParameter("date_begin");
			String query_date_end = request.getParameter("date_end");
			if(StringUtil.isNotEmpty(query_date_begin)){
				cq.ge("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_begin));
			}
			if(StringUtil.isNotEmpty(query_date_end)){
				cq.le("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_end));
			}
			String bool =request.getParameter("autoCheck");
			if("true".equals(bool)){
				String itemName = request.getParameter("itemName");
				cq.eq("checkstate",2);
				cq.eq("itemName",itemName);
				cq.gt("notAmount", 0d);
			}
			//未审核预警数据
			String isWarm = request.getParameter("isWarm");
			if(org.apache.commons.lang.StringUtils.isNotEmpty(isWarm)){
				String userId = ResourceUtil.getSessionUserName().getId();
				TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
				TSDepart depart = systemService.getParentSonInfo(sonInfo);
				Boolean isAudit = userService.isAllowAudit(tScRpExpensesapply.getTranType().toString(),userId,depart.getId());
				cq.eq("cancellation",0);
				//判断当前用户是否在多级审核队列中
				if(isAudit){
					Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId,depart.getId(),tScRpExpensesapply.getTranType().toString());
					if(userAuditLeave.size() > 0){
						String leaves = "";
						for(Integer leave : userAuditLeave){
							leaves += leave+",";
						}
						leaves = leaves.substring(0,leaves.length()-1);
						List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in ("+leaves+")",new Object[]{depart.getId(),tScRpExpensesapply.getTranType().toString()});
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
		this.tScRpExpensesapplyService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除费用申报单
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScRpExpensesapplyEntity tScRpExpensesapply, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScRpExpensesapply = systemService.getEntity(TScRpExpensesapplyEntity.class, tScRpExpensesapply.getId());
		String message = "费用申报单删除成功";
		try{
			tScRpExpensesapplyService.delMain(tScRpExpensesapply);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			//删除待审核预警数据
			systemService.delBillAuditStatus(tScRpExpensesapply.getTranType().toString(), tScRpExpensesapply.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "费用申报单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除费用申报单
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "费用申报单删除成功";
		try{
			for(String id:ids.split(",")){
				TScRpExpensesapplyEntity tScRpExpensesapply = systemService.getEntity(TScRpExpensesapplyEntity.class,
				id
				);
				tScRpExpensesapplyService.delMain(tScRpExpensesapply);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "费用申报单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加费用申报单
	 *
	 * @param tScRpExpensesapply
	 * @param tScRpExpensesapplyPage
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScRpExpensesapplyEntity tScRpExpensesapply,TScRpExpensesapplyPage tScRpExpensesapplyPage, HttpServletRequest request) {
		List<TScRpExpensesapplyentryEntity> tScRpExpensesapplyentryList =  tScRpExpensesapplyPage.getTScRpExpensesapplyentryList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			String accountDate = "";
			if(AccountUtil.getAccountStartDate()!=null){
				accountDate = sdf.format(AccountUtil.getAccountStartDate());
			}
			String data = sdf.format(tScRpExpensesapply.getDate());
			if(accountDate.compareTo(data) > 0) {
				j.setSuccess(false);
				j.setMsg("单据日期不能小于当前账套的当前期");
				return j;
			}
			tScRpExpensesapplyService.addMain(tScRpExpensesapply, tScRpExpensesapplyentryList);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			//待审核数据提醒操作
			systemService.saveBillAuditStatus(tScRpExpensesapply.getTranType().toString(), tScRpExpensesapply.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "费用申报单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新费用申报单
	 *
	 * @param tScRpExpensesapply
	 * @param tScRpExpensesapplyPage
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScRpExpensesapplyEntity tScRpExpensesapply,TScRpExpensesapplyPage tScRpExpensesapplyPage, HttpServletRequest request) {
		List<TScRpExpensesapplyentryEntity> tScRpExpensesapplyentryList =  tScRpExpensesapplyPage.getTScRpExpensesapplyentryList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			String accountDate = "";
			if(AccountUtil.getAccountStartDate()!=null){
				accountDate = sdf.format(AccountUtil.getAccountStartDate());
			}
			String data = sdf.format(tScRpExpensesapply.getDate());
			if(accountDate.compareTo(data) > 0) {
				j.setSuccess(false);
				j.setMsg("单据日期不能小于当前账套的当前期");
				return j;
			}
			tScRpExpensesapplyService.updateMain(tScRpExpensesapply, tScRpExpensesapplyentryList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新费用申报单失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 费用申报单新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScRpExpensesapplyEntity tScRpExpensesapply, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScRpExpensesapply.getId())) {
			tScRpExpensesapply = tScRpExpensesapplyService.getEntity(TScRpExpensesapplyEntity.class, tScRpExpensesapply.getId());
			req.setAttribute("tScRpExpensesapplyPage", tScRpExpensesapply);
		}
		TSUser loginUser = ResourceUtil.getSessionUserName();
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("tranType",Globals.SC_RP_BANKBILL_EXPENSESAPPLY);
		map.put("billNo", BillNoGenerate.getBillNo(String.valueOf(Globals.SC_RP_BANKBILL_EXPENSESAPPLY)));
		map.put("billerName",loginUser.getRealName());
		map.put("billerId",loginUser.getId());
		map.put("date", DateUtil.formatDate(new Date(),"yyyy-MM-dd"));
		//获取分支机构
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		req.setAttribute("sonId",depart.getId());
		req.setAttribute("sonName",depart.getDepartname());
		return new ModelAndView("com/qihang/buss/sc/financing/tScRpExpensesapply-add").addAllObjects(map);
	}

	/**
	 * 费用申报单编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScRpExpensesapplyEntity tScRpExpensesapply, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScRpExpensesapply.getId())) {
			tScRpExpensesapply = tScRpExpensesapplyService.getEntity(TScRpExpensesapplyEntity.class, tScRpExpensesapply.getId());
			req.setAttribute("tScRpExpensesapplyPage", tScRpExpensesapply);
			//核算项目
			if(tScRpExpensesapply.getItemClassId() == 1){//取职员
				if (StringUtils.isNotEmpty(tScRpExpensesapply.getItemId())) {
					TScEmpEntity emp = systemService.getEntity(TScEmpEntity.class, tScRpExpensesapply.getItemId());
					if (null != emp) {
						tScRpExpensesapply.setItemName(emp.getName());
					}
				}
			}
			//核算项目
			if(tScRpExpensesapply.getItemClassId() == 2){//取部门
				if (StringUtils.isNotEmpty(tScRpExpensesapply.getItemId())) {
					TScDepartmentEntity dept = systemService.getEntity(TScDepartmentEntity.class, tScRpExpensesapply.getItemId());
					if (null != dept) {
						tScRpExpensesapply.setItemName(dept.getName());
					}
				}
			}

			//制单人
			if (StringUtils.isNotEmpty(tScRpExpensesapply.getBillerId())) {
				TSUser user = systemService.getEntity(TSUser.class, tScRpExpensesapply.getBillerId());
				if (null != user) {
					tScRpExpensesapply.setBillerName(user.getRealName());
				}
			}
			//审核人
			if (StringUtils.isNotEmpty(tScRpExpensesapply.getCheckerId())) {
				TSUser user = systemService.getEntity(TSUser.class, tScRpExpensesapply.getCheckerId());
				if (null != user) {
					tScRpExpensesapply.setCheckUserName(user.getRealName());
				}
			}
			//经办人
			if (StringUtils.isNotEmpty(tScRpExpensesapply.getEmpId())) {
				TScEmpEntity emp = systemService.getEntity(TScEmpEntity.class, tScRpExpensesapply.getEmpId());
				if (null != emp) {
					tScRpExpensesapply.setEmpName(emp.getName());
				}
			}
			//部门
			if (StringUtils.isNotEmpty(tScRpExpensesapply.getDeptId())) {
				TScDepartmentEntity dept = systemService.getEntity(TScDepartmentEntity.class, tScRpExpensesapply.getDeptId());
				if (null != dept) {
					tScRpExpensesapply.setDeptName(dept.getName());
				}
			}
			//分支机构
			if (StringUtils.isNotEmpty(tScRpExpensesapply.getSonId())) {
				TSDepart sonInfo = systemService.getEntity(TSDepart.class, tScRpExpensesapply.getSonId());
				if (null != sonInfo) {
					tScRpExpensesapply.setSonName(sonInfo.getDepartname());
				}
			}
			//审核人和审核日期
			List<TSAuditRelationEntity> info = systemService.getAuditInfoList(tScRpExpensesapply.getId(), String.valueOf(tScRpExpensesapply.getTranType()));
			String auditor = "";
			String auditDate = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			for(int i=0 ; i < info.size() ; i++){
				TSAuditRelationEntity entity = info.get(i);
				auditor += entity.getStatus()+"级审核：<u>"+entity.getAuditorName()+"</u>;";
				if(i==2){
					auditor+="</br>";
				}else{
					auditor+="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
				}
				auditDate = sdf.format(entity.getAuditDate());
			}
			req.setAttribute("auditor",auditor);
			req.setAttribute("auditDate",auditDate);
		}
		return new ModelAndView("com/qihang/buss/sc/financing/tScRpExpensesapply-update");
	}

  /**
  * 导入功能跳转
  *
  * @return
  */
  @RequestMapping(params = "upload")
  public ModelAndView upload(HttpServletRequest req) {
  req.setAttribute("controller_name","tScRpExpensesapplyController");
  return new ModelAndView("common/upload/pub_excel_upload");
  }

  /**
  * 导出excel
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXls")
  public String exportXls(TScRpExpensesapplyViewEntity tScRpExpensesapply,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
	  CriteriaQuery cq = new CriteriaQuery(TScRpExpensesapplyViewEntity.class, dataGrid);
	  com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScRpExpensesapply, request.getParameterMap());
	  List<TScRpExpensesapplyViewEntity> tScRpExpensesapplys = this.tScRpExpensesapplyService.getListByCriteriaQuery(cq,false);
	  //如需动态排除部分列不导出时启用，列名指Excel中文列名
	  String[] exclusions = {"排除列名1","排除列名2"};
	  modelMap.put(NormalExcelConstants.FILE_NAME,"费用申报单");
	  modelMap.put(NormalExcelConstants.CLASS,TScRpExpensesapplyViewEntity.class);
	  modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("费用申报单列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
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
  public String exportXlsByT(TScRpExpensesapplyEntity tScRpExpensesapply,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
	  modelMap.put(TemplateExcelConstants.FILE_NAME, "费用申报单");
	  modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
	  modelMap.put(TemplateExcelConstants.MAP_DATA,null);
	  modelMap.put(TemplateExcelConstants.CLASS,TScRpExpensesapplyEntity.class);
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
  params.setSaveUrl("upload/excelUpload");
  try {
    ExcelImportResult importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(),TScRpExpensesapplyEntity.class,params);
    List<TScRpExpensesapplyEntity> listTScRpExpensesapplyEntitys = importResult.getList();
    StringBuilder stringBuilder = new StringBuilder("已存在的数据:");
    boolean flag = false;
    if(!importResult.isVerfiyFail()) {
      for (TScRpExpensesapplyEntity tScRpExpensesapply : listTScRpExpensesapplyEntitys) {
      //以下检查导入数据是否重复的语句可视需求启用
        //Long count = tScRpExpensesapplyService.getCountForJdbc("这里放检查数据是否重复的SQL语句");
        //if(count >0) {
          //flag = true;
          //stringBuilder.append(tScRpExpensesapply.getId()+",");
        //} else {
          tScRpExpensesapplyService.save(tScRpExpensesapply);
        //}
      }
      j.setMsg("文件导入成功！");
      //j.setMsg("文件导入成功！" + (flag? stringBuilder.toString():""));
    }else {
      String excelPath = "/upload/excelUpload/TScRpExpensesapplyEntity/"+importResult.getExcelName();
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
	 * 加载明细列表[费用申报单明细]
	 *
	 * @return
	 */
	@RequestMapping(params = "tScRpExpensesapplyentryList")
	public ModelAndView tScRpExpensesapplyentryList(TScRpExpensesapplyEntity tScRpExpensesapply, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id0 = tScRpExpensesapply.getId();
		//===================================================================================
		//查询-费用申报单明细
	    String hql0 = "from TScRpExpensesapplyentryEntity where 1 = 1 AND fID = ? order by findex asc";
	    try{
	    	List<TScRpExpensesapplyentryEntity> tScRpExpensesapplyentryEntityList = systemService.findHql(hql0,id0);
			for (TScRpExpensesapplyentryEntity entry : tScRpExpensesapplyentryEntityList) {
				if (StringUtils.isNotEmpty(entry.getExpId())) {
					TScFeeEntity fee = systemService.getEntity(TScFeeEntity.class, entry.getExpId());
					if (null != fee) {
						entry.setExpName(fee.getName());
					}
				}
			}
			req.setAttribute("tScRpExpensesapplyentryList", tScRpExpensesapplyentryEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/qihang/buss/sc/financing/tScRpExpensesapplyentryList");
	}

	@RequestMapping(params = "loadEntryViewList")
	@ResponseBody
	public List<TScRpExpensesapplyViewEntity> loadEntryViewList(TScRpExpensesapplyViewEntity tPoOrder, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid){
		CriteriaQuery cq = new CriteriaQuery(TScRpExpensesapplyViewEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tPoOrder);
		cq.add();
		this.systemService.getDataGridReturn(cq, true);
		List<TScRpExpensesapplyViewEntity> viewList = dataGrid.getResults();
		for(TScRpExpensesapplyViewEntity viewEntity : viewList){
			viewEntity.setCreateDate(null);
			viewEntity.setCreateBy(null);
			viewEntity.setCreateName(null);
			viewEntity.setUpdateName(null);
			viewEntity.setUpdateDate(null);
			viewEntity.setUpdateBy(null);
		}
		return viewList;
	}

	/**
	 * 检查单据是否被引用
	 * @return
     */
	@RequestMapping(params = "isReference")
	@ResponseBody
	public AjaxJson isReference(HttpServletRequest request, HttpServletResponse response){
		String id = request.getParameter("id");
		AjaxJson j = new AjaxJson();
		String hql0 = "from TScRpPotherbillentryEntity where 1 = 1 AND interIdSrc = ?";
		List<TScRpPotherbillEntity> list = systemService.findHql(hql0,id);
		if(list.size()>0){
			j.setSuccess(false);
			j.setMsg("该单据已被费用开支引用,不允许反审核");
		}
		return j;
	}

	/**
	 * 用于弹框选择列表
	 * @param tScRpExpensesapply
	 * @param request
	 * @param response
	 * @param dataGrid
     */
	@RequestMapping(params = "datagridSelect")
	public void datagridSelect(TScRpExpensesapplyViewEntity tScRpExpensesapply, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//汇总
		dataGrid.setFooter("allamount,amount,haveAmount,notAmount");
		CriteriaQuery cq = new CriteriaQuery(TScRpExpensesapplyViewEntity.class, dataGrid);
		cq.setOrders("billNo","findex");
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScRpExpensesapply);
		//自定义追加查询条件
		String query_date_begin = request.getParameter("date_begin");
		String query_date_end = request.getParameter("date_end");
		try {
			if(StringUtil.isNotEmpty(query_date_begin)){
				cq.ge("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_begin));
			}
			if(StringUtil.isNotEmpty(query_date_end)){
				cq.le("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_end));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		List<String> listIds = new ArrayList<String>();
		String ids = request.getParameter("ids");
		if(ids != null && !"".equals(ids)){
			String[] idstr = ids.split(",");
			for(int i =0 ;i < idstr.length;i++){
				if(!"".equals(idstr[i])){
					listIds.add(idstr[i]);
				}
			}
			cq.add(Restrictions.not(Restrictions.in("entryId", listIds.toArray())));
		}
		String itemName = request.getParameter("itemName");
		if(itemName != null && !"".equals(itemName)){
			cq.eq("itemName",itemName);
		}
		cq.eq("checkstate",2);
		cq.gt("notAmount", 0d);
		cq.add();
		this.tScRpExpensesapplyService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
}
