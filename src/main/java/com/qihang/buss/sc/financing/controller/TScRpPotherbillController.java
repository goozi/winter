package com.qihang.buss.sc.financing.controller;
import com.qihang.buss.sc.baseinfo.entity.TScDepartmentEntity;
import com.qihang.buss.sc.baseinfo.entity.TScEmpEntity;
import com.qihang.buss.sc.baseinfo.entity.TScFeeEntity;
import com.qihang.buss.sc.baseinfo.entity.TScSettleacctEntity;
import com.qihang.buss.sc.baseinfo.service.TScSettleacctServiceI;
import com.qihang.buss.sc.financing.entity.TScRpExpensesapplyentryEntity;
import com.qihang.buss.sc.financing.entity.TScRpPotherbillEntity;
import com.qihang.buss.sc.financing.entity.TScRpPotherbillViewEntity;
import com.qihang.buss.sc.financing.service.TScRpPotherbillServiceI;
import com.qihang.buss.sc.financing.page.TScRpPotherbillPage;
import com.qihang.buss.sc.financing.entity.TScRpPotherbillentryEntity;
import com.qihang.buss.sc.sysaudit.entity.TSBillInfoEntity;
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
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Title: Controller
 * @Description: 费用开支单
 * @author onlineGenerator
 * @date 2016-09-08 10:04:06
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScRpPotherbillController")
public class TScRpPotherbillController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScRpPotherbillController.class);

	@Autowired
	private TScRpPotherbillServiceI tScRpPotherbillService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TScSettleacctServiceI tScSettleacctService;
	@Autowired
	private UserService userService;


	/**
	 * 费用开支单列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tScRpPotherbill")
	public ModelAndView tScRpPotherbill(HttpServletRequest request) {
		request.setAttribute("tranType",Globals.SC_RP_BANKBILL_POTHER);
		return new ModelAndView("com/qihang/buss/sc/financing/tScRpPotherbillList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScRpPotherbillViewEntity tScRpPotherbill, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//汇总
		dataGrid.setFooter("allamount,amount,haveAmountSrc,notAmountSrc");
		CriteriaQuery cq = new CriteriaQuery(TScRpPotherbillViewEntity.class, dataGrid);
		cq.setOrders("billNo","findex");
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScRpPotherbill);
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
			String isWarm = request.getParameter("isWarm");
			if(org.apache.commons.lang.StringUtils.isNotEmpty(isWarm)){
				String userId = ResourceUtil.getSessionUserName().getId();
				TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
				TSDepart depart = systemService.getParentSonInfo(sonInfo);
				Boolean isAudit = userService.isAllowAudit(Globals.SC_RP_BANKBILL_POTHER.toString(),userId,depart.getId());
				cq.eq("cancellation",0);
				//判断当前用户是否在多级审核队列中
				if(isAudit){
					Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId,depart.getId(),Globals.SC_RP_BANKBILL_POTHER.toString());
					if(userAuditLeave.size() > 0){
						String leaves = "";
						for(Integer leave : userAuditLeave){
							leaves += leave+",";
						}
						leaves = leaves.substring(0,leaves.length()-1);
						List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in ("+leaves+")",new Object[]{depart.getId(),Globals.SC_RP_BANKBILL_POTHER.toString()});
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
		this.tScRpPotherbillService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除费用开支单
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScRpPotherbillEntity tScRpPotherbill, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScRpPotherbill = systemService.getEntity(TScRpPotherbillEntity.class, tScRpPotherbill.getId());
		String message = "费用开支单删除成功";
		try{
				String hql0 = "from TScRpPotherbillentryEntity where 1 = 1 AND fID = ?";
				List<TScRpPotherbillentryEntity> tScRpPotherbillentryEntityList = systemService.findHql(hql0,tScRpPotherbill.getId());
				for (TScRpPotherbillentryEntity e : tScRpPotherbillentryEntityList) {
					TScRpExpensesapplyentryEntity applyEnter = systemService.get(TScRpExpensesapplyentryEntity.class,e.getFidSrc());
					applyEnter.setHaveAmount(applyEnter.getHaveAmount().subtract(e.getAmount()));
					applyEnter.setNotAmount(applyEnter.getNotAmount().add(e.getAmount()));
					systemService.updateEntitie(applyEnter);
//					systemService.executeSql("update t_sc_rp_expensesapplyEntry set haveAmount = haveAmount - "+e.getAmount() + ",notAmount=notAmount+"+e.getAmount()+" where id=?",e.getFidSrc() );
				}
			tScRpPotherbillService.delMain(tScRpPotherbill);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			//删除待审核预警数据
			systemService.delBillAuditStatus(tScRpPotherbill.getTranType().toString(), tScRpPotherbill.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "费用开支单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除费用开支单
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "费用开支单删除成功";
		try{
			for(String id:ids.split(",")){
				TScRpPotherbillEntity tScRpPotherbill = systemService.getEntity(TScRpPotherbillEntity.class, id);
				String hql0 = "from TScRpPotherbillentryEntity where 1 = 1 AND fID = ?";
				List<TScRpPotherbillentryEntity> tScRpPotherbillentryEntityList = systemService.findHql(hql0,tScRpPotherbill.getId());
				for (TScRpPotherbillentryEntity e : tScRpPotherbillentryEntityList) {
					TScRpExpensesapplyentryEntity applyEnter = systemService.get(TScRpExpensesapplyentryEntity.class,e.getFidSrc());
					applyEnter.setHaveAmount(applyEnter.getHaveAmount().subtract(e.getAmount()));
					applyEnter.setNotAmount(applyEnter.getNotAmount().add(e.getAmount()));
					systemService.updateEntitie(applyEnter);
//					systemService.executeSql("update t_sc_rp_expensesapplyEntry set haveAmount = haveAmount - "+e.getAmount() + ",notAmount=notAmount+"+e.getAmount()+" where id=?",e.getFidSrc() );
				}
				tScRpPotherbillService.delMain(tScRpPotherbill);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "费用开支单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加费用开支单
	 *
	 * @param tScRpPotherbill
	 * @param tScRpPotherbillPage
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScRpPotherbillEntity tScRpPotherbill,TScRpPotherbillPage tScRpPotherbillPage, HttpServletRequest request) {
		List<TScRpPotherbillentryEntity> tScRpPotherbillentryList =  tScRpPotherbillPage.getTScRpPotherbillentryList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			String accountDate = "";
			if(AccountUtil.getAccountStartDate()!=null){
				accountDate = sdf.format(AccountUtil.getAccountStartDate());
			}
			String data = sdf.format(tScRpPotherbill.getDate());
			if(accountDate.compareTo(data) > 0) {
				j.setSuccess(false);
				j.setMsg("单据日期不能小于当前账套的当前期");
				return j;
			}
			for(TScRpPotherbillentryEntity e : tScRpPotherbillentryList){
				if(e.getAmount().compareTo(BigDecimal.valueOf(0)) == 1){
					if(e.getAmount().compareTo(e.getNotAmountSrc()) == 1){
						j.setSuccess(false);
						j.setMsg("序号:"+e.getFindex()+" 本次支出金额不能大于未报销金额");
						return j;
					}
					if(!StringUtil.isEmpty(e.getFidSrc())) {
						TScRpExpensesapplyentryEntity applyEnter = systemService.get(TScRpExpensesapplyentryEntity.class, e.getFidSrc());
						if(applyEnter != null){
							applyEnter.setHaveAmount(applyEnter.getHaveAmount().add(e.getAmount()));
							applyEnter.setNotAmount(applyEnter.getNotAmount().subtract(e.getAmount()));
							systemService.updateEntitie(applyEnter);
						}
//						systemService.executeSql("update t_sc_rp_expensesapplyEntry set haveAmount = haveAmount + " + e.getAmount() + ",notAmount=notAmount-" + e.getAmount() + " where id=?", e.getFidSrc());
					}
				}

			}
			tScRpPotherbillService.addMain(tScRpPotherbill, tScRpPotherbillentryList);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			//待审核数据提醒操作
			systemService.saveBillAuditStatus(tScRpPotherbill.getTranType().toString(), tScRpPotherbill.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "费用开支单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新费用开支单
	 *
	 * @param tScRpPotherbill
	 * @param tScRpPotherbillPage
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScRpPotherbillEntity tScRpPotherbill,TScRpPotherbillPage tScRpPotherbillPage, HttpServletRequest request) {
		List<TScRpPotherbillentryEntity> tScRpPotherbillentryList =  tScRpPotherbillPage.getTScRpPotherbillentryList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			String accountDate = "";
			if(AccountUtil.getAccountStartDate()!=null){
				accountDate = sdf.format(AccountUtil.getAccountStartDate());
			}
			String data = sdf.format(tScRpPotherbill.getDate());
			if(accountDate.compareTo(data) > 0) {
				j.setSuccess(false);
				j.setMsg("单据日期不能小于当前账套的当前期");
				return j;
			}
			for(TScRpPotherbillentryEntity e : tScRpPotherbillentryList){
				if(e.getAmount().compareTo(BigDecimal.valueOf(0)) == 1){
					if(e.getAmount().compareTo(e.getNotAmountSrc()) == 1){
						j.setSuccess(false);
						j.setMsg("序号:"+e.getFindex()+" 本次支出金额不能大于未报销金额");
						return j;
					}
					//systemService.executeSql("update t_sc_rp_expensesapplyEntry set haveAmount = haveAmount + "+e.getAmount() + ",notAmount=notAmount-"+e.getAmount()+" where id=?",e.getFidSrc() );
				}
			}
			tScRpPotherbillService.updateMain(tScRpPotherbill, tScRpPotherbillentryList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新费用开支单失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 费用开支单新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScRpPotherbillEntity tScRpPotherbill, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScRpPotherbill.getId())) {
			tScRpPotherbill = tScRpPotherbillService.getEntity(TScRpPotherbillEntity.class, tScRpPotherbill.getId());
			req.setAttribute("tScRpPotherbillPage", tScRpPotherbill);
		}
		TSUser loginUser = ResourceUtil.getSessionUserName();
		String hql0 = "from TScSettleacctEntity where 1 = 1 AND disabled = ?";
		List<TScSettleacctEntity> listSet = tScSettleacctService.findHql(hql0,0);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("tranType",Globals.SC_RP_BANKBILL_POTHER);
		map.put("billNo", BillNoGenerate.getBillNo(String.valueOf(Globals.SC_RP_BANKBILL_POTHER)));
		map.put("billerName",loginUser.getRealName());
		map.put("billerId",loginUser.getId());
		map.put("listSet",listSet);
		map.put("date",DateUtil.formatDate(new Date(),"yyyy-MM-dd"));
		//获取分支机构
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		req.setAttribute("sonId",depart.getId());
		req.setAttribute("sonName",depart.getDepartname());
		return new ModelAndView("com/qihang/buss/sc/financing/tScRpPotherbill-add").addAllObjects(map);
	}

	/**
	 * 费用开支单编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScRpPotherbillEntity tScRpPotherbill, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScRpPotherbill.getId())) {
			tScRpPotherbill = tScRpPotherbillService.getEntity(TScRpPotherbillEntity.class, tScRpPotherbill.getId());
			//核算项目
			if(tScRpPotherbill.getItemClassId() == 1){//取职员
				if (StringUtils.isNotEmpty(tScRpPotherbill.getItemId())) {
					TScEmpEntity emp = systemService.getEntity(TScEmpEntity.class, tScRpPotherbill.getItemId());
					if (null != emp) {
						tScRpPotherbill.setItemName(emp.getName());
					}
				}
			}
			//核算项目
			if(tScRpPotherbill.getItemClassId() == 2){//取部门
				if (StringUtils.isNotEmpty(tScRpPotherbill.getItemId())) {
					TScDepartmentEntity dept = systemService.getEntity(TScDepartmentEntity.class, tScRpPotherbill.getItemId());
					if (null != dept) {
						tScRpPotherbill.setItemName(dept.getName());
					}
				}
			}

			//制单人
			if (StringUtils.isNotEmpty(tScRpPotherbill.getBillerId())) {
				TSUser user = systemService.getEntity(TSUser.class, tScRpPotherbill.getBillerId());
				if (null != user) {
					tScRpPotherbill.setBillerName(user.getRealName());
				}
			}
			//审核人
			if (StringUtils.isNotEmpty(tScRpPotherbill.getCheckerId())) {
				TSUser user = systemService.getEntity(TSUser.class, tScRpPotherbill.getCheckerId());
				if (null != user) {
					tScRpPotherbill.setCheckUserName(user.getRealName());
				}
			}
			//经办人
			if (StringUtils.isNotEmpty(tScRpPotherbill.getEmpId())) {
				TScEmpEntity emp = systemService.getEntity(TScEmpEntity.class, tScRpPotherbill.getEmpId());
				if (null != emp) {
					tScRpPotherbill.setEmpName(emp.getName());
				}
			}
			//部门
			if (StringUtils.isNotEmpty(tScRpPotherbill.getDeptId())) {
				TScDepartmentEntity dept = systemService.getEntity(TScDepartmentEntity.class, tScRpPotherbill.getDeptId());
				if (null != dept) {
					tScRpPotherbill.setDeptName(dept.getName());
				}
			}
			//分支机构
			if (StringUtils.isNotEmpty(tScRpPotherbill.getSonId())) {
				TSDepart sonInfo = systemService.getEntity(TSDepart.class, tScRpPotherbill.getSonId());
				if (null != sonInfo) {
					tScRpPotherbill.setSonName(sonInfo.getDepartname());
				}
			}
			//源单类型
			if (tScRpPotherbill.getClassIdSrc() != null) {
				TSBillInfoEntity billInfo = systemService.findUniqueByProperty(TSBillInfoEntity.class,"billId",tScRpPotherbill.getClassIdSrc()+"");
				if(null != billInfo){
					tScRpPotherbill.setClassIdSrcName(billInfo.getBillName());
				}
			}
			String hql0 = "from TScSettleacctEntity where 1 = 1 AND disabled = ?";
			List<TScSettleacctEntity> listSet = tScSettleacctService.findHql(hql0,0);
			req.setAttribute("listSet",listSet);
			req.setAttribute("tScRpPotherbillPage", tScRpPotherbill);
		}

		return new ModelAndView("com/qihang/buss/sc/financing/tScRpPotherbill-update");
	}

  /**
  * 导入功能跳转
  *
  * @return
  */
  @RequestMapping(params = "upload")
  public ModelAndView upload(HttpServletRequest req) {
  req.setAttribute("controller_name","tScRpPotherbillController");
  return new ModelAndView("common/upload/pub_excel_upload");
  }

  /**
  * 导出excel
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXls")
  public String exportXls(TScRpPotherbillViewEntity tScRpPotherbill,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
	  CriteriaQuery cq = new CriteriaQuery(TScRpPotherbillViewEntity.class, dataGrid);
	  com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScRpPotherbill, request.getParameterMap());
	  List<TScRpPotherbillEntity> tScRpPotherbills = this.tScRpPotherbillService.getListByCriteriaQuery(cq,false);
	  //如需动态排除部分列不导出时启用，列名指Excel中文列名
	  String[] exclusions = {"排除列名1","排除列名2"};
	  modelMap.put(NormalExcelConstants.FILE_NAME,"费用开支单");
	  modelMap.put(NormalExcelConstants.CLASS,TScRpPotherbillViewEntity.class);
	  modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("费用开支单列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
	  "导出信息",exclusions));
	  modelMap.put(NormalExcelConstants.DATA_LIST,tScRpPotherbills);
	  return NormalExcelConstants.JEECG_EXCEL_VIEW;
  }
  /**
  * 导出excel 使模板
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXlsByT")
  public String exportXlsByT(TScRpPotherbillEntity tScRpPotherbill,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  modelMap.put(TemplateExcelConstants.FILE_NAME, "费用开支单");
  modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
  modelMap.put(TemplateExcelConstants.MAP_DATA,null);
  modelMap.put(TemplateExcelConstants.CLASS,TScRpPotherbillEntity.class);
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
    ExcelImportResult importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(),TScRpPotherbillEntity.class,params);
    List<TScRpPotherbillEntity> listTScRpPotherbillEntitys = importResult.getList();
    StringBuilder stringBuilder = new StringBuilder("已存在的数据:");
    boolean flag = false;
    if(!importResult.isVerfiyFail()) {
      for (TScRpPotherbillEntity tScRpPotherbill : listTScRpPotherbillEntitys) {
      //以下检查导入数据是否重复的语句可视需求启用
        //Long count = tScRpPotherbillService.getCountForJdbc("这里放检查数据是否重复的SQL语句");
        //if(count >0) {
          //flag = true;
          //stringBuilder.append(tScRpPotherbill.getId()+",");
        //} else {
          tScRpPotherbillService.save(tScRpPotherbill);
        //}
      }
      j.setMsg("文件导入成功！");
      //j.setMsg("文件导入成功！" + (flag? stringBuilder.toString():""));
    }else {
      String excelPath = "/upload/excelUpload/TScRpPotherbillEntity/"+importResult.getExcelName();
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
	 * 加载明细列表[费用开支单明细]
	 *
	 * @return
	 */
	@RequestMapping(params = "tScRpPotherbillentryList")
	public ModelAndView tScRpPotherbillentryList(TScRpPotherbillEntity tScRpPotherbill, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id0 = tScRpPotherbill.getId();
		//===================================================================================
		//查询-费用开支单明细
	    String hql0 = "from TScRpPotherbillentryEntity where 1 = 1 AND fID = ? order by findex asc ";
	    try{
	    	List<TScRpPotherbillentryEntity> tScRpPotherbillentryEntityList = systemService.findHql(hql0,id0);
			for (TScRpPotherbillentryEntity entry : tScRpPotherbillentryEntityList) {
				if (StringUtils.isNotEmpty(entry.getExpId())) {
					TScFeeEntity fee = systemService.getEntity(TScFeeEntity.class, entry.getExpId());
					if (null != fee) {
						entry.setExpName(fee.getName());
					}
				}
			}
			req.setAttribute("tScRpPotherbillentryList", tScRpPotherbillentryEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/qihang/buss/sc/financing/tScRpPotherbillentryList");
	}

	/**
	 * 费用开支单选择页跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tScRpPotherbillSelect")
	public ModelAndView tScRpPotherbillSelect(HttpServletRequest request) {
		String sonId = request.getParameter("sonId");
		request.setAttribute("sonId",sonId);
		return new ModelAndView("com/qihang/buss/sc/financing/tScRpExpensesapplyListSelect");
	}

}
