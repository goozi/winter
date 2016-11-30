
package com.qihang.buss.sc.financing.controller;
import com.qihang.buss.sc.baseinfo.entity.*;
import com.qihang.buss.sc.baseinfo.service.CountCommonServiceI;
import com.qihang.buss.sc.baseinfo.service.TScSettleacctServiceI;
import com.qihang.buss.sc.financing.entity.TScRpBankbillEntity;
import com.qihang.buss.sc.financing.entity.TScRpRotherbillEntity;
import com.qihang.buss.sc.financing.entity.TScRpRotherbillViewEntity;
import com.qihang.buss.sc.financing.entity.TScRpRotherbillentryEntity;
import com.qihang.buss.sc.financing.page.TScRpRotherbillPage;
import com.qihang.buss.sc.financing.service.TScRpRotherbillServiceI;
import com.qihang.buss.sc.rp.entity.TScRpAdjustbillentryEntity;
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
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Title: Controller
 * @Description: 资金其他收入
 * @author onlineGenerator
 * @date 2016-09-05 17:52:07
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScRpRotherbillController")
public class TScRpRotherbillController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScRpRotherbillController.class);

	@Autowired
	private TScRpRotherbillServiceI tScRpRotherbillService;
	@Autowired
	private SystemService systemService;

	@Autowired
	private TScSettleacctServiceI tScSettleacctService;
	@Autowired
	private CountCommonServiceI countCommonService;
	@Autowired
	private UserService userService;


	/**
	 * 资金其他收入列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tScRpRotherbill")
	public ModelAndView tScRpRotherbill(HttpServletRequest request) {
		request.setAttribute("tranType",Globals.SC_RP_BANKBILL_OTHER);
		return new ModelAndView("com/qihang/buss/sc/financing/tScRpRotherbillList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScRpRotherbillViewEntity tScRpRotherbill, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//汇总
		dataGrid.setFooter("allamount,amount");
		CriteriaQuery cq = new CriteriaQuery(TScRpRotherbillViewEntity.class, dataGrid);
		cq.setOrders("billNo","findex");
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScRpRotherbill);
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
				Boolean isAudit = userService.isAllowAudit(Globals.SC_RP_BANKBILL_OTHER.toString(),userId,depart.getId());
				cq.eq("cancellation",0);
				//判断当前用户是否在多级审核队列中
				if(isAudit){
					Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId,depart.getId(),Globals.SC_RP_BANKBILL_OTHER.toString());
					if(userAuditLeave.size() > 0){
						String leaves = "";
						for(Integer leave : userAuditLeave){
							leaves += leave+",";
						}
						leaves = leaves.substring(0,leaves.length()-1);
						List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in ("+leaves+")",new Object[]{depart.getId(),Globals.SC_RP_BANKBILL_OTHER.toString()});
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
		this.tScRpRotherbillService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除资金其他收入
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScRpRotherbillEntity tScRpRotherbill, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScRpRotherbill = systemService.getEntity(TScRpRotherbillEntity.class, tScRpRotherbill.getId());
		String message = "资金其他收入删除成功";
		try{
			tScRpRotherbillService.delMain(tScRpRotherbill);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			//删除待审核预警数据
			systemService.delBillAuditStatus(tScRpRotherbill.getTranType().toString(), tScRpRotherbill.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "资金其他收入删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除资金其他收入
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "资金其他收入删除成功";
		try{
			for(String id:ids.split(",")){
				TScRpRotherbillEntity tScRpRotherbill = systemService.getEntity(TScRpRotherbillEntity.class,
				id
				);
				tScRpRotherbillService.delMain(tScRpRotherbill);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "资金其他收入删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加资金其他收入
	 *
	 * @param tScRpRotherbill
	 * @param tScRpRotherbillPage
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScRpRotherbillEntity tScRpRotherbill, TScRpRotherbillPage tScRpRotherbillPage, HttpServletRequest request) {
		List<TScRpRotherbillentryEntity> tScRpRotherbillentryList =  tScRpRotherbillPage.getTScRpRotherbillentryList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			String accountDate = "";
			if(AccountUtil.getAccountStartDate()!=null){
				accountDate = sdf.format(AccountUtil.getAccountStartDate());
			}
			String data = sdf.format(tScRpRotherbill.getDate());
			if(accountDate.compareTo(data) > 0) {
				j.setSuccess(false);
				j.setMsg("单据日期不能小于当前账套的当前期");
				return j;
			}
			for(TScRpRotherbillentryEntity e : tScRpRotherbillentryList){
				if(e.getAmount() == null){
					j.setSuccess(false);
					j.setMsg("序号:"+e.getFindex()+" 金额不能为空!");
					return j;
				}
			}
			tScRpRotherbillService.addMain(tScRpRotherbill, tScRpRotherbillentryList);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			//待审核数据提醒操作
			systemService.saveBillAuditStatus(tScRpRotherbill.getTranType().toString(), tScRpRotherbill.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "资金其他收入添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新资金其他收入
	 *
	 * @param tScRpRotherbill
	 * @param tScRpRotherbillPage
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScRpRotherbillEntity tScRpRotherbill,TScRpRotherbillPage tScRpRotherbillPage, HttpServletRequest request) {
		List<TScRpRotherbillentryEntity> tScRpRotherbillentryList =  tScRpRotherbillPage.getTScRpRotherbillentryList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			String accountDate = "";
			if(AccountUtil.getAccountStartDate()!=null){
				accountDate = sdf.format(AccountUtil.getAccountStartDate());
			}
			String data = sdf.format(tScRpRotherbill.getDate());
			if(accountDate.compareTo(data) > 0) {
				j.setSuccess(false);
				j.setMsg("单据日期不能小于当前账套的当前期");
				return j;
			}
			for(TScRpRotherbillentryEntity e : tScRpRotherbillentryList){
				if(e.getAmount() == null){
					j.setSuccess(false);
					j.setMsg("序号:"+e.getFindex()+" 金额不能为空!");
					return j;
				}
			}
			tScRpRotherbillService.updateMain(tScRpRotherbill, tScRpRotherbillentryList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新资金其他收入失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 资金其他收入新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScRpRotherbillEntity tScRpRotherbill, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScRpRotherbill.getId())) {
			tScRpRotherbill = tScRpRotherbillService.getEntity(TScRpRotherbillEntity.class, tScRpRotherbill.getId());
			req.setAttribute("tScRpRotherbillPage", tScRpRotherbill);
		}
		TSUser loginUser = ResourceUtil.getSessionUserName();
		String hql0 = "from TScSettleacctEntity where 1 = 1 AND disabled = ?";
		List<TScSettleacctEntity> listSet = tScSettleacctService.findHql(hql0,0);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("tranType",Globals.SC_RP_BANKBILL_OTHER);
		map.put("billNo", BillNoGenerate.getBillNo(String.valueOf(Globals.SC_RP_BANKBILL_OTHER)));
		map.put("billerName",loginUser.getRealName());
		map.put("billerId",loginUser.getId());
		map.put("listSet",listSet);
		map.put("date", DateUtil.formatDate(new Date(),"yyyy-MM-dd"));

		//获取分支机构
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		req.setAttribute("sonId",depart.getId());
		req.setAttribute("sonName",depart.getDepartname());
		return new ModelAndView("com/qihang/buss/sc/financing/tScRpRotherbill-add").addAllObjects(map);
	}

	/**
	 * 资金其他收入编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScRpRotherbillEntity tScRpRotherbill, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScRpRotherbill.getId())) {
			tScRpRotherbill = tScRpRotherbillService.getEntity(TScRpRotherbillEntity.class, tScRpRotherbill.getId());
			//制单人
			if (StringUtils.isNotEmpty(tScRpRotherbill.getBillerId())) {
				TSUser user = systemService.getEntity(TSUser.class, tScRpRotherbill.getBillerId());
				if (null != user) {
					tScRpRotherbill.setBillerName(user.getRealName());
				}
			}
			//审核人
			if (StringUtils.isNotEmpty(tScRpRotherbill.getCheckerId())) {
				TSUser user = systemService.getEntity(TSUser.class, tScRpRotherbill.getCheckerId());
				if (null != user) {
					tScRpRotherbill.setCheckUserName(user.getRealName());
				}
			}
			//经办人
			if (StringUtils.isNotEmpty(tScRpRotherbill.getEmpId())) {
				TScEmpEntity emp = systemService.getEntity(TScEmpEntity.class, tScRpRotherbill.getEmpId());
				if (null != emp) {
					tScRpRotherbill.setEmpName(emp.getName());
				}
			}
			//部门
			if (StringUtils.isNotEmpty(tScRpRotherbill.getDeptId())) {
				TScDepartmentEntity dept = systemService.getEntity(TScDepartmentEntity.class, tScRpRotherbill.getDeptId());
				if (null != dept) {
					tScRpRotherbill.setDeptName(dept.getName());
				}
			}
			//分支机构
			if (StringUtils.isNotEmpty(tScRpRotherbill.getSonId())) {
				TSDepart sonInfo = systemService.getEntity(TSDepart.class, tScRpRotherbill.getSonId());
				if (null != sonInfo) {
					tScRpRotherbill.setSonName(sonInfo.getDepartname());
				}
			}
			String hql0 = "from TScSettleacctEntity where 1 = 1 AND disabled = ?";
			List<TScSettleacctEntity> listSet = tScSettleacctService.findHql(hql0,0);
			req.setAttribute("listSet",listSet);
			req.setAttribute("tScRpRotherbillPage", tScRpRotherbill);
		}
		return new ModelAndView("com/qihang/buss/sc/financing/tScRpRotherbill-update");
	}

  /**
  * 导入功能跳转
  *
  * @return
  */
  @RequestMapping(params = "upload")
	  public ModelAndView upload(HttpServletRequest req) {
	  req.setAttribute("controller_name","tScRpRotherbillController");
	  return new ModelAndView("common/upload/pub_excel_upload");
  }

  /**
  * 导出excel
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXls")
  public String exportXls(TScRpRotherbillViewEntity tScRpRotherbill,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
		  CriteriaQuery cq = new CriteriaQuery(TScRpRotherbillViewEntity.class, dataGrid);
		  com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScRpRotherbill, request.getParameterMap());
		  List<TScRpRotherbillViewEntity> tScRpRotherbills = this.tScRpRotherbillService.getListByCriteriaQuery(cq,false);
		    //如需动态排除部分列不导出时启用，列名指Excel中文列名
		  String[] exclusions = {"排除列名1","排除列名2"};
		  modelMap.put(NormalExcelConstants.FILE_NAME,"资金其他收入");
		  modelMap.put(NormalExcelConstants.CLASS,TScRpRotherbillViewEntity.class);
		  modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("资金其他收入列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
		  "导出信息",exclusions));
		  modelMap.put(NormalExcelConstants.DATA_LIST,tScRpRotherbills);
		  return NormalExcelConstants.JEECG_EXCEL_VIEW;
  }
  /**
  * 导出excel 使模板
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXlsByT")
  public String exportXlsByT(TScRpRotherbillEntity tScRpRotherbill,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
	  modelMap.put(TemplateExcelConstants.FILE_NAME, "资金其他收入");
	  modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
	  modelMap.put(TemplateExcelConstants.MAP_DATA,null);
	  modelMap.put(TemplateExcelConstants.CLASS,TScRpRotherbillEntity.class);
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
			ExcelImportResult importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(),TScRpRotherbillEntity.class,params);
			List<TScRpRotherbillEntity> listTScRpRotherbillEntitys = importResult.getList();
			StringBuilder stringBuilder = new StringBuilder("已存在的数据:");
			boolean flag = false;
			if(!importResult.isVerfiyFail()) {
			  for (TScRpRotherbillEntity tScRpRotherbill : listTScRpRotherbillEntitys) {
			  //以下检查导入数据是否重复的语句可视需求启用
				//Long count = tScRpRotherbillService.getCountForJdbc("这里放检查数据是否重复的SQL语句");
				//if(count >0) {
				  //flag = true;
				  //stringBuilder.append(tScRpRotherbill.getId()+",");
				//} else {
				  tScRpRotherbillService.save(tScRpRotherbill);
				//}
			  }
			  j.setMsg("文件导入成功！");
			  //j.setMsg("文件导入成功！" + (flag? stringBuilder.toString():""));
			}else {
			  String excelPath = "/upload/excelUpload/TScRpRotherbillEntity/"+importResult.getExcelName();
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
	 * 加载明细列表[资金其他收入单]
	 *
	 * @return
	 */
	@RequestMapping(params = "tScRpRotherbillentryList")
	public ModelAndView tScRpRotherbillentryList(TScRpRotherbillEntity tScRpRotherbill, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id0 = tScRpRotherbill.getId();
		//===================================================================================
		//查询-资金其他收入单
	    String hql0 = "from TScRpRotherbillentryEntity where 1 = 1 AND fID = ?  order by findex asc ";
	    try{
	    	List<TScRpRotherbillentryEntity> tScRpRotherbillentryEntityList = systemService.findHql(hql0,id0);
			for (TScRpRotherbillentryEntity entry : tScRpRotherbillentryEntityList) {
				if (StringUtils.isNotEmpty(entry.getExpId())) {
					TScFeeEntity fee = systemService.getEntity(TScFeeEntity.class, entry.getExpId());
					if (null != fee) {
						entry.setExpName(fee.getName());
					}
				}
			}
			req.setAttribute("tScRpRotherbillentryList", tScRpRotherbillentryEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/qihang/buss/sc/financing/tScRpRotherbillentryList");
	}

}
