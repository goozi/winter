
package com.qihang.buss.sc.inventory.controller;
import com.qihang.buss.sc.baseinfo.entity.*;
import com.qihang.buss.sc.inventory.entity.*;
import com.qihang.buss.sc.inventory.page.TScAutoChkPage;
import com.qihang.buss.sc.inventory.service.TScIcChkstockbillServiceI;
import com.qihang.buss.sc.inventory.page.TScIcChkstockbillPage;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Title: Controller
 * @Description: 盘点单
 * @author onlineGenerator
 * @date 2016-08-01 09:43:32
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScIcChkstockbillController")
public class TScIcChkstockbillController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScIcChkstockbillController.class);

	@Autowired
	private TScIcChkstockbillServiceI tScIcChkstockbillService;
	@Autowired
	private SystemService systemService;

	@Autowired
	private UserService userService;


	/**
	 * 盘点单列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tScIcChkstockbill")
	public ModelAndView tScIcChkstockbill(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/inventory/tScIcChkstockbillList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScIcChkstockbillViewEntity tScIcChkstockbill,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		dataGrid.setFooter("qty,smallQty,basicQty,chkQty,chkSmallQty,chkBasicQty,diffQty,amount,chkAmount,diffAmount");
		CriteriaQuery cq = new CriteriaQuery(TScIcChkstockbillViewEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScIcChkstockbill);
		try{
		//自定义追加查询条件
			String query_date_begin = request.getParameter("date_begin");
			String query_date_end = request.getParameter("date_end");
			if (StringUtil.isNotEmpty(query_date_begin)) {
				cq.ge("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_begin));
			}
			if (StringUtil.isNotEmpty(query_date_end)) {
				cq.le("date", new SimpleDateFormat("yyyy-MM-dd").parse(query_date_end));
			}
			String isWarm = request.getParameter("isWarm");
			if(org.apache.commons.lang.StringUtils.isNotEmpty(isWarm)){
				String userId = ResourceUtil.getSessionUserName().getId();
				TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
				TSDepart depart = systemService.getParentSonInfo(sonInfo);
				Boolean isAudit = userService.isAllowAudit(tScIcChkstockbill.getTranType().toString(),userId,depart.getId());
				cq.eq("cancellation",0);
				//判断当前用户是否在多级审核队列中
				if(isAudit){
					Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId,depart.getId(),tScIcChkstockbill.getTranType().toString());
					if(userAuditLeave.size() > 0){
						String leaves = "";
						for(Integer leave : userAuditLeave){
							leaves += leave+",";
						}
						leaves = leaves.substring(0,leaves.length()-1);
						List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in ("+leaves+")",new Object[]{depart.getId(),tScIcChkstockbill.getTranType().toString()});
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
		this.tScIcChkstockbillService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除盘点单
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScIcChkstockbillEntity tScIcChkstockbill, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScIcChkstockbill = systemService.getEntity(TScIcChkstockbillEntity.class, tScIcChkstockbill.getId());
		String message = "盘点单删除成功";
		try{
			tScIcChkstockbillService.delMain(tScIcChkstockbill);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			//删除待审核预警数据
			systemService.delBillAuditStatus(tScIcChkstockbill.getTranType(), tScIcChkstockbill.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "盘点单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除盘点单
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "盘点单删除成功";
		try{
			for(String id:ids.split(",")){
				TScIcChkstockbillEntity tScIcChkstockbill = systemService.getEntity(TScIcChkstockbillEntity.class,
				id
				);
				tScIcChkstockbillService.delMain(tScIcChkstockbill);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "盘点单删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加盘点单
	 *
	 * @param tScIcChkstockbill
	 * @param tScIcChkstockbillPage
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScIcChkstockbillEntity tScIcChkstockbill,TScIcChkstockbillPage tScIcChkstockbillPage, HttpServletRequest request) {
		List<TScIcChkstockbillentryEntity> tScIcChkstockbillentryList =  tScIcChkstockbillPage.getTScIcChkstockbillentryList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			//设置盘点日期
			tScIcChkstockbill.setPdDate(new Date());
			tScIcChkstockbill.setIsAuto(0);
			tScIcChkstockbillService.addMain(tScIcChkstockbill, tScIcChkstockbillentryList);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			//待审核数据提醒操作
			systemService.saveBillAuditStatus(tScIcChkstockbill.getTranType().toString(), tScIcChkstockbill.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "盘点单添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新盘点单
	 *
	 * @param tScIcChkstockbill
	 * @param tScIcChkstockbillPage
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScIcChkstockbillEntity tScIcChkstockbill,TScIcChkstockbillPage tScIcChkstockbillPage, HttpServletRequest request) {
		List<TScIcChkstockbillentryEntity> tScIcChkstockbillentryList =  tScIcChkstockbillPage.getTScIcChkstockbillentryList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			tScIcChkstockbillService.updateMain(tScIcChkstockbill, tScIcChkstockbillentryList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新盘点单失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 盘点单新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScIcChkstockbillEntity tScIcChkstockbill, HttpServletRequest req) {
		String billNo = BillNoGenerate.getBillNo(tScIcChkstockbill.getTranType());
		tScIcChkstockbill.setBillNo(billNo);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		req.setAttribute("date",date);
		req.setAttribute("tScIcChkstockbillPage", tScIcChkstockbill);
		//当前用户所在分支机构
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		req.setAttribute("sonId",depart.getId());
		req.setAttribute("sonName",depart.getDepartname());
		return new ModelAndView("com/qihang/buss/sc/inventory/tScIcChkstockbill-add");
	}

	/**
	 * 盘点单编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScIcChkstockbillEntity tScIcChkstockbill, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScIcChkstockbill.getId())) {
			tScIcChkstockbill = tScIcChkstockbillService.getEntity(TScIcChkstockbillEntity.class, tScIcChkstockbill.getId());
			//仓库
			if(StringUtils.isNotEmpty(tScIcChkstockbill.getStockId())){
				TScStockEntity stockEntity = systemService.getEntity(TScStockEntity.class,tScIcChkstockbill.getStockId());
				if(null != stockEntity){
					tScIcChkstockbill.setStockName(stockEntity.getName());
				}
			}
			//经办人
			if(StringUtils.isNotEmpty(tScIcChkstockbill.getEmpId())){
				TScEmpEntity empEntity = systemService.getEntity(TScEmpEntity.class,tScIcChkstockbill.getEmpId());
				if(null != empEntity){
					tScIcChkstockbill.setEmpName(empEntity.getName());
				}
			}
			//部门
			if(StringUtils.isNotEmpty(tScIcChkstockbill.getDeptId())){
				TScDepartmentEntity departmentEntity = systemService.getEntity(TScDepartmentEntity.class,tScIcChkstockbill.getDeptId());
				if(null != departmentEntity){
					tScIcChkstockbill.setDeptName(departmentEntity.getName());
				}
			}
			//分支机构
			if(StringUtils.isNotEmpty(tScIcChkstockbill.getSonId())){
				TSDepart departmentEntity = systemService.getEntity(TSDepart.class,tScIcChkstockbill.getSonId());
				if(null != departmentEntity){
					tScIcChkstockbill.setSonName(departmentEntity.getDepartname());
				}
			}
			//制单人
			if(StringUtils.isNotEmpty(tScIcChkstockbill.getBillerId())){
				TSUser user = systemService.getEntity(TSUser.class,tScIcChkstockbill.getBillerId());
				if(null != user){
					tScIcChkstockbill.setBillerName(user.getRealName());
				}
			}
			String tranType = tScIcChkstockbill.getTranType();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			List<TSAuditRelationEntity> info = systemService.getAuditInfoList(tScIcChkstockbill.getId(), tranType);
			String auditor = "";
			String auditDate = "";
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
			req.setAttribute("auditor", auditor);
			req.setAttribute("auditDate", auditDate);
			String load = req.getParameter("load");
			req.setAttribute("load",load);
			req.setAttribute("tScIcChkstockbillPage", tScIcChkstockbill);
		}
		return new ModelAndView("com/qihang/buss/sc/inventory/tScIcChkstockbill-update");
	}

  /**
  * 导入功能跳转
  *
  * @return
  */
  @RequestMapping(params = "upload")
  public ModelAndView upload(HttpServletRequest req) {
  req.setAttribute("controller_name", "tScIcChkstockbillController");
  return new ModelAndView("common/upload/pub_excel_upload");
  }

  /**
  * 导出excel
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXls")
  public String exportXls(TScIcChkstockbillExcelEntity tScIcChkstockbill,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  CriteriaQuery cq = new CriteriaQuery(TScIcChkstockbillExcelEntity.class, dataGrid);
  com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScIcChkstockbill, request.getParameterMap());
  List<TScIcChkstockbillExcelEntity> tScIcChkstockbills = this.tScIcChkstockbillService.getListByCriteriaQuery(cq,false);
	  TSDepart depart = ResourceUtil.getSessionUserName().getCurrentDepart();
	  TSDepart depart1 = systemService.getParentSonInfo(depart);
	  Set<String> departIds = systemService.getAllSonId(depart1.getId());
	  CriteriaQuery deptCq = new CriteriaQuery(TSDepart.class, dataGrid);
	  deptCq.in("id", departIds.toArray());
	  List<TSDepart> departList = systemService.getListByCriteriaQuery(deptCq,false);
	  for(TScIcChkstockbillExcelEntity entity : tScIcChkstockbills){
		  if(StringUtils.isNotEmpty(entity.getCheckerId())){
			  TSUser user = systemService.getEntity(TSUser.class,entity.getCheckerId());
			  entity.setAuditorName(user.getRealName());
		  }
		  for(TSDepart depart2 : departList){
			  if(depart2.getId().equals(entity.getSonId())){
				  entity.setSonName(depart2.getDepartname());
			  }
		  }
	  }
  //如需动态排除部分列不导出时启用，列名指Excel中文列名
  String[] exclusions = {"排除列名1","排除列名2"};
  modelMap.put(NormalExcelConstants.FILE_NAME,"盘点单");
  modelMap.put(NormalExcelConstants.CLASS,TScIcChkstockbillExcelEntity.class);
  modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("盘点单列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
  "导出信息",exclusions));
  modelMap.put(NormalExcelConstants.DATA_LIST, tScIcChkstockbills);
  return NormalExcelConstants.JEECG_EXCEL_VIEW;
  }
  /**
  * 导出excel 使模板
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXlsByT")
  public String exportXlsByT(TScIcChkstockbillEntity tScIcChkstockbill,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  modelMap.put(TemplateExcelConstants.FILE_NAME, "盘点单");
  modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
  modelMap.put(TemplateExcelConstants.MAP_DATA,null);
  modelMap.put(TemplateExcelConstants.CLASS,TScIcChkstockbillEntity.class);
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
    ExcelImportResult importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(),TScIcChkstockbillEntity.class,params);
    List<TScIcChkstockbillEntity> listTScIcChkstockbillEntitys = importResult.getList();
    StringBuilder stringBuilder = new StringBuilder("已存在的数据:");
    boolean flag = false;
    if(!importResult.isVerfiyFail()) {
      for (TScIcChkstockbillEntity tScIcChkstockbill : listTScIcChkstockbillEntitys) {
      //以下检查导入数据是否重复的语句可视需求启用
        //Long count = tScIcChkstockbillService.getCountForJdbc("这里放检查数据是否重复的SQL语句");
        //if(count >0) {
          //flag = true;
          //stringBuilder.append(tScIcChkstockbill.getId()+",");
        //} else {
          tScIcChkstockbillService.save(tScIcChkstockbill);
        //}
      }
      j.setMsg("文件导入成功！");
      //j.setMsg("文件导入成功！" + (flag? stringBuilder.toString():""));
    }else {
      String excelPath = "/upload/excelUpload/TScIcChkstockbillEntity/"+importResult.getExcelName();
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
	 * 加载明细列表[盘点单明细]
	 *
	 * @return
	 */
	@RequestMapping(params = "tScIcChkstockbillentryList")
	public ModelAndView tScIcChkstockbillentryList(TScIcChkstockbillEntity tScIcChkstockbill, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id0 = tScIcChkstockbill.getId();
		//===================================================================================
		//查询-盘点单明细
	    String hql0 = "from TScIcChkstockbillentryEntity where 1 = 1 AND fID = ? ";
		List<TScStockEntity> stockEntityList = systemService.findHql("from TScStockEntity");
	    try{
	    	List<TScIcChkstockbillentryEntity> tScIcChkstockbillentryEntityList = systemService.findHql(hql0,id0);
			for(TScIcChkstockbillentryEntity entry : tScIcChkstockbillentryEntityList){
				if(StringUtils.isNotEmpty(entry.getStockId())){
					for(TScStockEntity stockEntity : stockEntityList){
						if(stockEntity.getId().equals(entry.getStockId())){
							entry.setStockName(stockEntity.getName());
						}
					}
				}
				if(StringUtils.isNotEmpty(entry.getItemId())){
					TScIcitemEntity item = systemService.getEntity(TScIcitemEntity.class,entry.getItemId());
					if(null != item){
						entry.setItemName(item.getName());
						entry.setItemNo(item.getNumber());
						entry.setModel(item.getModel());
						TScItemPriceEntity price = systemService.getEntity(TScItemPriceEntity.class, entry.getUnitId());
						entry.setBarCode(price.getBarCode());
					}
				}
			}
			req.setAttribute("tScIcChkstockbillentryList", tScIcChkstockbillentryEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/qihang/buss/sc/inventory/tScIcChkstockbillentryList");
	}

	@RequestMapping(params = "goSearchDialog")
	public ModelAndView goSearchDialog(HttpServletRequest request){
		ModelAndView view = new ModelAndView("com/qihang/buss/sc/inventory/tScIcChkstockbillSearch");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(new Date());
		view.addObject("date",date);
		return view;
	}

	/**
	 * 自动盘点功能
	 * @param info
	 * @return
	 */
	@RequestMapping(params = "autoChk")
	@ResponseBody
	public AjaxJson autoChk(TScAutoChkPage info) throws ParseException {
		TSUser user = ResourceUtil.getSessionUserName();
		String billerId = user.getId();
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		AjaxJson j = tScIcChkstockbillService.autoChk(info, billerId, depart.getId());
		return j;
	}

	/**
	 * 校验单据是否已审核
	 */
	@RequestMapping(params = "checkIsAudit")
	@ResponseBody
	public AjaxJson checkIsAudit(){
		AjaxJson j = new AjaxJson();
		List<TScIcChkInfoView> checkInfo = systemService.findHql("from TScIcChkInfoView");
		if(checkInfo.size() > 0){
			j.setSuccess(false);
		}
		return j;
	}


	@RequestMapping(params = "goAuditInfoDialog")
	public ModelAndView goAuditInfoDialog(HttpServletRequest request){
		ModelAndView view = new ModelAndView("com/qihang/buss/sc/inventory/tScIcChkAuditInfoList");
		return view;
	}

	@RequestMapping(params = "auditInfoDatagrid")
	public void auditInfoDatagrid(TScIcChkInfoView tScIcChkstockbill,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScIcChkInfoView.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScIcChkstockbill);
		try{
			//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScIcChkstockbillService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	@RequestMapping(params = "afterAudit")
	@ResponseBody
	public AjaxJson afterAudit(String id,Integer audit,String tranType){
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		return tScIcChkstockbillService.afterAudit(id,audit,tranType,depart.getId());
	}

}
