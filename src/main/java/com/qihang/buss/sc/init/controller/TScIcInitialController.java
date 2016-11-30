
package com.qihang.buss.sc.init.controller;
import com.qihang.buss.sc.baseinfo.entity.*;
import com.qihang.buss.sc.baseinfo.service.*;
import com.qihang.buss.sc.init.entity.TScIcInitialEntity;
import com.qihang.buss.sc.init.service.TScIcInitialServiceI;
import com.qihang.buss.sc.init.page.TScIcInitialPage;
import com.qihang.buss.sc.init.entity.TScIcInitialentryEntity;
import com.qihang.buss.sc.sales.entity.TScCountEntity;
import com.qihang.buss.sc.sales.entity.TScPoOrderEntity;
import com.qihang.buss.sc.sales.service.TScPoStockbillServiceI;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
import com.qihang.buss.sc.util.AccountUtil;
import com.qihang.buss.sc.util.BillNoGenerate;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.DateUtils;
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
import com.qihang.winter.web.system.pojo.base.TSConfig;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.pojo.base.TSUser;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.web.system.service.UserService;
import org.apache.batik.dom.svg12.Global;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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
 * @Description: 存货初始化单
 * @author onlineGenerator
 * @date 2016-08-18 11:32:00
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScIcInitialController")
public class TScIcInitialController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScIcInitialController.class);

	@Autowired
	private TScIcInitialServiceI tScIcInitialService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private TScPoStockbillServiceI tScPoStockbillService;

	@Autowired
	private TScIcitemServiceI tScIcitemServiceI;//商品

	@Autowired
	private TScSupplierServiceI supplierServiceI;//供应商

	@Autowired
	private TScStockServiceI stockServiceI;

	@Autowired
	private CountCommonServiceI countCommonService;
	@Autowired
	private TScEmpServiceI empServiceI;

	@Autowired
	private TScDepartmentServiceI departmentServiceI;

	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private UserService userService;


	/**
	 * 存货初始化单列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tScIcInitial")
	public ModelAndView tScIcInitial(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/init/tScIcInitialList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScIcInitialEntity tScIcInitial,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScIcInitialEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScIcInitial);
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
				Boolean isAudit = userService.isAllowAudit(tScIcInitial.getTranType().toString(),userId,depart.getId());
				cq.eq("cancellation",0);
				//判断当前用户是否在多级审核队列中
				if(isAudit){
					Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId,depart.getId(),tScIcInitial.getTranType().toString());
					if(userAuditLeave.size() > 0){
						String leaves = "";
						for(Integer leave : userAuditLeave){
							leaves += leave+",";
						}
						leaves = leaves.substring(0,leaves.length()-1);
						List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in ("+leaves+")",new Object[]{depart.getId(),tScIcInitial.getTranType().toString()});
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
		this.tScIcInitialService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除存货初始化单
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScIcInitialEntity tScIcInitial, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScIcInitial = systemService.getEntity(TScIcInitialEntity.class, tScIcInitial.getId());
		String message = "存货初始化单删除成功";
		try{
			tScIcInitialService.delMain(tScIcInitial);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			//删除待审核预警数据
			systemService.delBillAuditStatus(tScIcInitial.getTranType().toString(), tScIcInitial.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "存货初始化单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除存货初始化单
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "存货初始化单删除成功";
		try{
			for(String id:ids.split(",")){
				TScIcInitialEntity tScIcInitial = systemService.getEntity(TScIcInitialEntity.class,
				id
				);
				tScIcInitialService.delMain(tScIcInitial);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "存货初始化单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加存货初始化单
	 *
	 * @param tScIcInitial
	 * @param tScIcInitialPage
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScIcInitialEntity tScIcInitial,TScIcInitialPage tScIcInitialPage, HttpServletRequest request) {
		List<TScIcInitialentryEntity> tScIcInitialentryList =  tScIcInitialPage.getTScIcInitialentryList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			String date = DateUtils.formatDate(AccountUtil.getAccountInitStartDate());
			date = DateUtils.formatAddDate(date, "yyyy-MM-dd", -1);//初始化单据的单据日期直接默认为账套启用期间的上一月最后一天的日期，并且不能修改
			tScIcInitial.setDate(DateUtils.str2Date(date, DateUtils.date_sdf));
			tScIcInitial.setTranType(Globals.SC_IC_INITIAL_TRANTYPE+"");//单据类型
			tScIcInitial.setCancellation(Integer.parseInt(Globals.CLOSE_NO));//作废标记
			tScIcInitial.setBillerId(ResourceUtil.getSessionUserName().getId());//制单人
			tScIcInitial.setSonId(ResourceUtil.getSessionUserName().getCurrentDepart().getId());//分支机构
			tScIcInitial.setCheckState(Globals.AUDIT_NO);//审核标记-未审核
			tScIcInitialService.addMain(tScIcInitial, tScIcInitialentryList);
			//修改仓库引用次数
			if(StringUtils.isNotEmpty(tScIcInitial.getStockId())){
				countCommonService.addUpdateCount("T_SC_Stock",tScIcInitial.getStockId());
			}
			for(TScIcInitialentryEntity entry : tScIcInitialentryList) {
				//商品
				if (StringUtils.isNotEmpty(entry.getItemId())) {
					countCommonService.addUpdateCount("T_SC_ICItem",entry.getItemId());
				}
				//仓库
				if (StringUtils.isNotEmpty(entry.getStockId())) {
					countCommonService.addUpdateCount("T_SC_Stock",entry.getStockId());
				}
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			//待审核数据提醒操作
			systemService.saveBillAuditStatus(tScIcInitial.getTranType().toString(), tScIcInitial.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "存货初始化单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新存货初始化单
	 *
	 * @param tScIcInitial
	 * @param tScIcInitialPage
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScIcInitialEntity tScIcInitial,TScIcInitialPage tScIcInitialPage, HttpServletRequest request) {
		List<TScIcInitialentryEntity> tScIcInitialentryList =  tScIcInitialPage.getTScIcInitialentryList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			tScIcInitialService.updateMain(tScIcInitial, tScIcInitialentryList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新存货初始化单失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 存货初始化单新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScIcInitialEntity tScIcInitial, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScIcInitial.getId())) {
			tScIcInitial = tScIcInitialService.getEntity(TScIcInitialEntity.class, tScIcInitial.getId());
			req.setAttribute("tScIcInitialPage", tScIcInitial);
		}else{
			tScIcInitial.setTranType(Globals.SC_IC_INITIAL_TRANTYPE+"");
			String billNo = BillNoGenerate.getBillNo(tScIcInitial.getTranType());
			req.setAttribute("billNo",billNo);
			String defaultTaxRate = "";//默认税率
			//从系统参数中获取默认税率
			List<TSConfig> tsConfigList = this.tScPoStockbillService.findByProperty(TSConfig.class,"code","CFG_TAX_RATE");
			if(tsConfigList.size()>0){
				defaultTaxRate = tsConfigList.get(0).getContents();
			}
			if(StringUtils.isNotEmpty(defaultTaxRate)) {
				req.setAttribute("taxRate", defaultTaxRate);
			}else{
				req.setAttribute("taxRate",0);
			}
			String checkDate = "2016-06-30";
			req.setAttribute("checkDate",checkDate);
		}
		req.setAttribute("tranType", tScIcInitial.getTranType());
		String date = DateUtils.formatDate(AccountUtil.getAccountInitStartDate());
		try {
			date = DateUtils.formatAddDate(date, "yyyy-MM-dd", -1);//初始化单据的单据日期直接默认为账套启用期间的上一月最后一天的日期，并且不能修改
		}catch(Exception e){
			e.printStackTrace();
		}
		req.setAttribute("date",date);
		String title = "存货初始单";//"存货初始化单";
		req.setAttribute("title",title);
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		req.setAttribute("sonId",depart.getId());
		req.setAttribute("sonName",depart.getDepartname());
		return new ModelAndView("com/qihang/buss/sc/init/tScIcInitial-add");
	}

	/**
	 * 存货初始化单编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScIcInitialEntity tScIcInitial, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScIcInitial.getId())) {
			tScIcInitial = tScIcInitialService.getEntity(TScIcInitialEntity.class, tScIcInitial.getId());
			req.setAttribute("tScIcInitialPage", tScIcInitial);
			String billNo = tScIcInitial.getBillNo();
			req.setAttribute("billNo",billNo);
			String checkDate = "2016-06-30";
			req.setAttribute("checkDate",checkDate);
			req.setAttribute("tranType", tScIcInitial.getTranType());
			String title = "存货初始单";//"存货初始化单";
			req.setAttribute("title",title);
			//TScDepartmentEntity dept = tScIcInitialService.getEntity(TScDepartmentEntity.class,tScIcInitial.getDeptId());
			//仓库
			TScStockEntity stock = tScIcInitialService.getEntity(TScStockEntity.class, tScIcInitial.getStockId());
			req.setAttribute("stockName", stock == null ? "" : stock.getName());

			//部门
			TSDepart sonEntity = systemService.getEntity(TSDepart.class,tScIcInitial.getSonId());
			req.setAttribute("sonName",sonEntity==null?"":sonEntity.getDepartname());
			//制单人
			//TScEmpEntity empEntity = systemService.getEntity(TScEmpEntity.class,tScIcInitial.getBillerId());
			TSUser user = tScIcInitialService.getEntity(TSUser.class,tScIcInitial.getBillerId());
			req.setAttribute("billerName",user==null?"":user.getRealName());
			//审核人
			if (StringUtils.isNotEmpty(tScIcInitial.getCheckerId())) {
				TSUser checUser = systemService.getEntity(TSUser.class, tScIcInitial.getCheckerId());
				if (null != user) {
					req.setAttribute("checkUserName",user.getRealName());
				}
			}
		}
		return new ModelAndView("com/qihang/buss/sc/init/tScIcInitial-update");
	}

  /**
  * 导入功能跳转
  *
  * @return
  */
  @RequestMapping(params = "upload")
	  public ModelAndView upload(HttpServletRequest req) {
	  req.setAttribute("controller_name","tScIcInitialController");
	  return new ModelAndView("common/upload/pub_excel_upload");
  }

  /**
  * 导出excel
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXls")
  public String exportXls(TScIcInitialEntity tScIcInitial,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
	  CriteriaQuery cq = new CriteriaQuery(TScIcInitialEntity.class, dataGrid);
	  com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScIcInitial, request.getParameterMap());
	  List<TScIcInitialEntity> tScIcInitials = this.tScIcInitialService.getListByCriteriaQuery(cq,false);
	  //如需动态排除部分列不导出时启用，列名指Excel中文列名
	  String[] exclusions = {"排除列名1","排除列名2"};
	  modelMap.put(NormalExcelConstants.FILE_NAME,"存货初始化单");
	  modelMap.put(NormalExcelConstants.CLASS,TScIcInitialEntity.class);
	  modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("存货初始化单列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
	  "导出信息",exclusions));
	  modelMap.put(NormalExcelConstants.DATA_LIST,tScIcInitials);
	  return NormalExcelConstants.JEECG_EXCEL_VIEW;
  }
  /**
  * 导出excel 使模板
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXlsByT")
  public String exportXlsByT(TScIcInitialEntity tScIcInitial,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
	  modelMap.put(TemplateExcelConstants.FILE_NAME, "存货初始化单");
	  modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
	  modelMap.put(TemplateExcelConstants.MAP_DATA,null);
	  modelMap.put(TemplateExcelConstants.CLASS,TScIcInitialEntity.class);
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
		ExcelImportResult importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(),TScIcInitialEntity.class,params);
		List<TScIcInitialEntity> listTScIcInitialEntitys = importResult.getList();
		StringBuilder stringBuilder = new StringBuilder("已存在的数据:");
		boolean flag = false;
		if(!importResult.isVerfiyFail()) {
		  for (TScIcInitialEntity tScIcInitial : listTScIcInitialEntitys) {
		  //以下检查导入数据是否重复的语句可视需求启用
			//Long count = tScIcInitialService.getCountForJdbc("这里放检查数据是否重复的SQL语句");
			//if(count >0) {
			  //flag = true;
			  //stringBuilder.append(tScIcInitial.getId()+",");
			//} else {
			  tScIcInitialService.save(tScIcInitial);
			//}
		  }
		  j.setMsg("文件导入成功！");
		  //j.setMsg("文件导入成功！" + (flag? stringBuilder.toString():""));
		}else {
		  String excelPath = "/upload/excelUpload/TScIcInitialEntity/"+importResult.getExcelName();
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
	 * 加载明细列表[附录明细]
	 *
	 * @return
	 */
	@RequestMapping(params = "tScIcInitialentryList")
	public ModelAndView tScIcInitialentryList(TScIcInitialEntity tScIcInitial, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id0 = tScIcInitial.getId();
		//===================================================================================
		//查询-附录明细
	    String hql0 = "from TScIcInitialentryEntity where 1 = 1 AND fID = ? ";
	    try{
			//List<TScStockEntity> stockEntityList = systemService.findHql("from TScStockEntity");
	    	List<TScIcInitialentryEntity> tScIcInitialentryEntityList = systemService.findHql(hql0,id0);
			for(TScIcInitialentryEntity entry : tScIcInitialentryEntityList){
				//if(StringUtils.isNotEmpty(entry.getStockId())){
					//for(TScStockEntity stockEntity : stockEntityList){
					//	if(stockEntity.getId().equals(entry.getStockId())){
					//		entry.setStockName(stockEntity.getName());
					//	}
					//}
				//}
				if(StringUtils.isNotEmpty(entry.getItemId())){
					TScIcitemEntity item = systemService.getEntity(TScIcitemEntity.class,entry.getItemId());
					if(null != item){
						entry.setItemName(item.getName());
						entry.setItemNo(item.getNumber());
						entry.setModel(item.getModel());
						entry.setIsKFPeriod(item.getIskfPeriod());//写入计算列：是否保质期管理
						entry.setBatchManager(item.getBatchManager());//写入计算列：批号管理
						TScItemPriceEntity price = systemService.getEntity(TScItemPriceEntity.class, entry.getUnitId());
						entry.setBarCode(price.getBarCode());
					}
				}
			}
			req.setAttribute("tScIcInitialentryList", tScIcInitialentryEntityList);
			tScIcInitial = systemService.getEntity(TScIcInitialEntity.class, tScIcInitial.getId());
			req.setAttribute("checkState",tScIcInitial.getCheckState());//主表审核状态
			req.setAttribute("cancellation",tScIcInitial.getCancellation());//主表作废状态
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/qihang/buss/sc/init/tScIcInitialentryList");
	}

	//作废功能
	@RequestMapping(params = "cancelBill")
	@ResponseBody
	public AjaxJson cancelBill(String ids){
		AjaxJson j = tScIcInitialService.cancelBill(ids);
		return j;
	}

	//反作废功能
	@RequestMapping(params = "enableBill")
	@ResponseBody
	public AjaxJson enableBill(String ids){
		AjaxJson j = tScIcInitialService.enableBill(ids);
		return j;
	}
}