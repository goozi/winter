
package com.qihang.buss.sc.sys.controller;
import com.qihang.buss.sc.baseinfo.entity.TScIcitemEntity;
import com.qihang.buss.sc.baseinfo.entity.TScItemPriceEntity;
import com.qihang.buss.sc.baseinfo.entity.TScMeasureunitEntity;
import com.qihang.buss.sc.baseinfo.entity.TScStockEntity;
import com.qihang.buss.sc.sys.entity.*;
import com.qihang.buss.sc.sys.service.TScAccountStageServiceI;
import com.qihang.buss.sc.sys.page.TScAccountStagePage;
import com.qihang.buss.sc.util.AccountUtil;
import com.qihang.buss.sc.util.StageUtil;
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
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.service.SystemService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @Title: Controller
 * @Description: 账套期别
 * @author onlineGenerator
 * @date 2016-09-17 15:14:23
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScAccountStageController")
public class TScAccountStageController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScAccountStageController.class);

	@Autowired
	private TScAccountStageServiceI tScAccountStageService;
	@Autowired
	private SystemService systemService;


	/**
	 * 账套期别列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tScAccountStage")
	public ModelAndView tScAccountStage(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/sys/tScAccountStageList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScAccountStageEntity tScAccountStage,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScAccountStageEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScAccountStage);
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScAccountStageService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除账套期别
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScAccountStageEntity tScAccountStage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScAccountStage = systemService.getEntity(TScAccountStageEntity.class, tScAccountStage.getId());
		String message = "账套期别删除成功";
		try{
			tScAccountStageService.delMain(tScAccountStage);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "账套期别删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除账套期别
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "账套期别删除成功";
		try{
			for(String id:ids.split(",")){
				TScAccountStageEntity tScAccountStage = systemService.getEntity(TScAccountStageEntity.class,
				id
				);
				tScAccountStageService.delMain(tScAccountStage);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "账套期别删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加账套期别
	 *
	 * @param tScAccountStage
	 * @param tScAccountStagePage
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScAccountStageEntity tScAccountStage,TScAccountStagePage tScAccountStagePage, HttpServletRequest request) {
		List<VScCheckstageEntity> vScCheckstageList =  tScAccountStagePage.getVScCheckstageList();
		List<VScCheckspeedbalEntity> vScCheckspeedbalList =  tScAccountStagePage.getVScCheckspeedbalList();
		List<TScIcBalEntity> tScIcBalList =  tScAccountStagePage.getTScIcBalList();
		List<TScRpContactbalEntity> tScRpContactbalList =  tScAccountStagePage.getTScRpContactbalList();
		List<TScRpExpbalEntity> tScRpExpbalList =  tScAccountStagePage.getTScRpExpbalList();
		AjaxJson j = new AjaxJson();
		String message = "[" + DateUtils.formatDate(tScAccountStage.getDate(),"yyyy年MM月") + "]结账成功";
		String openType = tScAccountStagePage.getOpenType();
		if (openType.equals(Globals.ACCOUNT_UNSTAGE_CODE)) {
			String strDate = DateUtils.date2Str(tScAccountStage.getDate(), DateUtils.date_sdf);
			try {
				strDate = DateUtils.formatAddMonth(strDate, "yyyy-MM-dd",-1);//上期
			}catch (Exception e){

			}
			Date dATE1 = DateUtils.str2Date(strDate, DateUtils.date_sdf);
			message = "[" + DateUtils.formatDate(dATE1,"yyyy年MM月") + "]反结账成功";
		}
		try{
			//取当前用户登录的sonId分支机构ID
//			TSDepart sonDepart = ResourceUtil.getSessionUserName().getCurrentDepart();//ResourceUtil.getSessionUserName().getSonCompanyId();
//			TSDepart pDepart = systemService.getParentSonInfo(sonDepart);
//			String sonId = pDepart.getId();
			String createname = ResourceUtil.getSessionUserName().getRealName();
			String createby = ResourceUtil.getSessionUserName().getId();
			//获取当前账套是否允许负库存结账
			boolean isMinusInventoryAccount = AccountUtil.isMinusInventoryAccount();
			//检查账套是否开账
			boolean isAccountOpen = AccountUtil.isAccountOpen();
			if (openType.equals(Globals.ACCOUNT_UNSTAGE_CODE)){//反结账
				//检查当期是否允许反结账
				String checkunstage = tScAccountStageService.checkIsUnStage(tScAccountStage);
				boolean isUnStage = (checkunstage==null || checkunstage.equals(""))?true:false;//是否允许反结账
				if (isUnStage==false){
					throw new BusinessException(checkunstage);
				}
				//对上一期执行反结账操作
				StageUtil.doUnStageMain(tScAccountStage.getDate(), createname, createby);
			}else{
				//检查当期是否允许结账
				String checkstage = tScAccountStageService.checkIsStage(tScAccountStage, true);
				boolean isStage = (checkstage==null || checkstage.equals(""))?true:false;//是否允许反结账
				if (isStage==false){
					throw new BusinessException(checkstage);
				}
				//对上一期执行反结账操作
				StageUtil.doStageMain(tScAccountStage.getDate(), createname, createby);
			}
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			//清除账套数据ehcache缓存
			AccountUtil.clearAccountConfigCache();
		}catch(Exception e){
			e.printStackTrace();
			message = message.replace("成功","失败："+e.getMessage());
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新账套期别
	 *
	 * @param tScAccountStage
	 * @param tScAccountStagePage
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScAccountStageEntity tScAccountStage,TScAccountStagePage tScAccountStagePage, HttpServletRequest request) {
		List<VScCheckstageEntity> vScCheckstageList =  tScAccountStagePage.getVScCheckstageList();
		List<VScCheckspeedbalEntity> vScCheckspeedbalList =  tScAccountStagePage.getVScCheckspeedbalList();
		List<TScIcBalEntity> tScIcBalList =  tScAccountStagePage.getTScIcBalList();
		List<TScRpContactbalEntity> tScRpContactbalList =  tScAccountStagePage.getTScRpContactbalList();
		List<TScRpExpbalEntity> tScRpExpbalList =  tScAccountStagePage.getTScRpExpbalList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			tScAccountStageService.updateMain(tScAccountStage, vScCheckstageList,vScCheckspeedbalList,tScIcBalList,tScRpContactbalList,tScRpExpbalList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新账套期别失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 账套期别新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScAccountStageEntity tScAccountStage, HttpServletRequest req) {
//		if (AccountUtil.isAccountOpen()==false){
//			throw new BusinessException("系统当前账套未开账，不允许结账。");
//		}
		if (StringUtil.isNotEmpty(tScAccountStage.getId())) {
			tScAccountStage = tScAccountStageService.getEntity(TScAccountStageEntity.class, tScAccountStage.getId());
			req.setAttribute("tScAccountStagePage", tScAccountStage);
		}else{
			//获得当前账套ID
			String accountId = ResourceUtil.getAccountId();
			tScAccountStage.setAccountId(accountId);
			//获得当前账套的当前期别年月
			Date date = AccountUtil.getAccountStartDate();
			if (date==null){
				throw new BusinessException("系统当前期别年月为空，不允许期未结账。");
			}
			String strdate = date==null?"":DateUtils.formatDate(date);
			try {
				//strdate = DateUtils.formatAddMonth(strdate, "yyyy-MM-dd", 1);
				//Date nextDate = DateUtils.parseDate(strdate, "yyyy-MM-dd"); // DateUtils.formatAddMonth(strdate,"yyyy-MM-dd", 1);
				//tScAccountStage.setDate(nextDate);
				tScAccountStage.setDate(date);//对当前期别进行结账
			} catch (Exception e){

			}
			req.setAttribute("tScAccountStagePage", tScAccountStage);
		}
		//获取当前账套是否允许负库存结账
		boolean isMinusInventoryAccount = AccountUtil.isMinusInventoryAccount();
		//检查账套是否开账
		boolean isAccountOpen = AccountUtil.isAccountOpen();
		//检查当期是否允许结账
		String checkstage = tScAccountStageService.checkIsStage(tScAccountStage, true);
		boolean isStage = (checkstage==null || checkstage.equals(""))?true:false;//是否允许反结账
		//检查当期是否允许反结账
		String checkunstage = tScAccountStageService.checkIsUnStage(tScAccountStage);
		boolean isUnStage = (checkunstage==null || checkunstage.equals(""))?true:false;//是否允许反结账
		req.setAttribute("isMinusInventoryAccount", isMinusInventoryAccount);
		req.setAttribute("isAccountOpen", isAccountOpen);//是否开账
		req.setAttribute("isStage", isStage);//当期是否允许结账
		req.setAttribute("isUnStage", isUnStage);//当期是否允许反结账
		req.setAttribute("checkstage", checkstage);//当期不允许结账的错误信息
		req.setAttribute("checkunstage", checkunstage);//当期不允许反结账的错误信息
		req.setAttribute("stage",Globals.ACCOUNT_STAGE_CODE);//期未结账时进行当期结账操作
		req.setAttribute("unstage",Globals.ACCOUNT_UNSTAGE_CODE);//期未结账时进行上期反结账操作
		return new ModelAndView("com/qihang/buss/sc/sys/tScAccountStage-add");
	}

	/**
	 * 账套期别编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScAccountStageEntity tScAccountStage, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScAccountStage.getId())) {
			tScAccountStage = tScAccountStageService.getEntity(TScAccountStageEntity.class, tScAccountStage.getId());
			req.setAttribute("tScAccountStagePage", tScAccountStage);
		}
		return new ModelAndView("com/qihang/buss/sc/sys/tScAccountStage-update");
	}

  /**
  * 导入功能跳转
  *
  * @return
  */
  @RequestMapping(params = "upload")
  public ModelAndView upload(HttpServletRequest req) {
  req.setAttribute("controller_name","tScAccountStageController");
  return new ModelAndView("common/upload/pub_excel_upload");
  }

  /**
  * 导出excel
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXls")
  public String exportXls(TScAccountStageEntity tScAccountStage,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  CriteriaQuery cq = new CriteriaQuery(TScAccountStageEntity.class, dataGrid);
  com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScAccountStage, request.getParameterMap());
  List<TScAccountStageEntity> tScAccountStages = this.tScAccountStageService.getListByCriteriaQuery(cq,false);
  //如需动态排除部分列不导出时启用，列名指Excel中文列名
  String[] exclusions = {"排除列名1","排除列名2"};
  modelMap.put(NormalExcelConstants.FILE_NAME,"账套期别");
  modelMap.put(NormalExcelConstants.CLASS,TScAccountStageEntity.class);
  modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("账套期别列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
  "导出信息",exclusions));
  modelMap.put(NormalExcelConstants.DATA_LIST,tScAccountStages);
  return NormalExcelConstants.JEECG_EXCEL_VIEW;
  }
  /**
  * 导出excel 使模板
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXlsByT")
  public String exportXlsByT(TScAccountStageEntity tScAccountStage,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  modelMap.put(TemplateExcelConstants.FILE_NAME, "账套期别");
  modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
  modelMap.put(TemplateExcelConstants.MAP_DATA,null);
  modelMap.put(TemplateExcelConstants.CLASS,TScAccountStageEntity.class);
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
    ExcelImportResult importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(),TScAccountStageEntity.class,params);
    List<TScAccountStageEntity> listTScAccountStageEntitys = importResult.getList();
    StringBuilder stringBuilder = new StringBuilder("已存在的数据:");
    boolean flag = false;
    if(!importResult.isVerfiyFail()) {
      for (TScAccountStageEntity tScAccountStage : listTScAccountStageEntitys) {
      //以下检查导入数据是否重复的语句可视需求启用
        //Long count = tScAccountStageService.getCountForJdbc("这里放检查数据是否重复的SQL语句");
        //if(count >0) {
          //flag = true;
          //stringBuilder.append(tScAccountStage.getId()+",");
        //} else {
          tScAccountStageService.save(tScAccountStage);
        //}
      }
      j.setMsg("文件导入成功！");
      //j.setMsg("文件导入成功！" + (flag? stringBuilder.toString():""));
    }else {
      String excelPath = "/upload/excelUpload/TScAccountStageEntity/"+importResult.getExcelName();
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
	 * 加载明细列表[未审核单据]
	 *
	 * @return
	 */
	@RequestMapping(params = "vScCheckstageList")
	public ModelAndView vScCheckstageList(TScAccountStageEntity tScAccountStage, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Date dATE0 = tScAccountStage.getDate();
		//===================================================================================
		//查询-未审核单据
	    String hql0 = "from VScCheckstageEntity where 1 = 1 AND dATE >= ? AND dATE< ? order by dATE,billNo";//日期条件为当月第一天到当月最后一天,即大于等于当月第一天，小于下个月第一天
	    try{
			String strDate = DateUtils.date2Str(dATE0, DateUtils.date_sdf);
			strDate = DateUtils.formatAddMonth(strDate, "yyyy-MM-dd",1);//当月1日先加一个月
			//strDate = DateUtils.formatAddDate(strDate,  "yyyy-MM-dd",-1);//再减一天，即当月的最后一天
			Date dATE1 = DateUtils.str2Date(strDate, DateUtils.date_sdf);
	    	List<VScCheckstageEntity> vScCheckstageEntityList = systemService.findHql(hql0, dATE0, dATE1);
			req.setAttribute("vScCheckstageList", vScCheckstageEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/qihang/buss/sc/sys/vScCheckstageList");
	}
	/**
	 * 加载明细列表[负库存情况]
	 *
	 * @return
	 */
	@RequestMapping(params = "vScCheckspeedbalList")
	public ModelAndView vScCheckspeedbalList(TScAccountStageEntity tScAccountStage, HttpServletRequest req) {

		//===================================================================================
	    try{
			List<VScCheckspeedbalEntity> vScCheckspeedbalEntityList = tScAccountStageService.findVScCheckspeedbalList(tScAccountStage, true);
			req.setAttribute("vScCheckspeedbalList", vScCheckspeedbalEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/qihang/buss/sc/sys/vScCheckspeedbalList");
	}
	/**
	 * 加载明细列表[存货结账]
	 *
	 * @return
	 */
	@RequestMapping(params = "tScIcBalList")
	public ModelAndView tScIcBalList(TScAccountStageEntity tScAccountStage, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id2 = tScAccountStage.getId();
		//===================================================================================
		//查询-存货结账
	    String hql2 = "from TScIcBalEntity where 1 = 1 AND fID = ? ";
	    try{
	    	List<TScIcBalEntity> tScIcBalEntityList = systemService.findHql(hql2,id2);
			req.setAttribute("tScIcBalList", tScIcBalEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/qihang/buss/sc/sys/tScIcBalList");
	}

	/**
	 * 加载明细列表[存货结账视图]
	 *
	 * @return
	 */
	@RequestMapping(params = "vScIcBalList")
	public ModelAndView vScIcBalList(TScAccountStageEntity tScAccountStage, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id2 = tScAccountStage.getDate(); //.getId();
		String year = DateUtils.formatDate((Date)id2, "yyyy");
		String period = DateUtils.formatDate((Date)id2, "MM");
		if (Integer.parseInt(period)<10){//如果月份为1-9月，则去掉前面的0，因为表中存储时没有存储时前面没有0
			period = period.substring(1);
		}
		//===================================================================================
		//查询-存货结账
		String hql2 = "from VScIcBalEntity where 1 = 1 AND year=? and period=?";//fID = ? ";
		try{
			List<VScIcBalEntity> vScIcBalEntityList = systemService.findHql(hql2,year,period);
			req.setAttribute("tScIcBalList", vScIcBalEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/qihang/buss/sc/sys/vScIcBalList");
	}

	/**
	 * 加载明细列表[应收应付结账]
	 *
	 * @return
	 */
	@RequestMapping(params = "tScRpContactbalList")
	public ModelAndView tScRpContactbalList(TScAccountStageEntity tScAccountStage, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id3 = tScAccountStage.getId();
		//===================================================================================
		//查询-应收应付结账
	    String hql3 = "from TScRpContactbalEntity where 1 = 1 AND fID = ? ";
	    try{
	    	List<TScRpContactbalEntity> tScRpContactbalEntityList = systemService.findHql(hql3,id3);
			req.setAttribute("tScRpContactbalList", tScRpContactbalEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/qihang/buss/sc/sys/tScRpContactbalList");
	}

	/**
	 * 加载明细列表[应收应付结账视图]
	 *
	 * @return
	 */
	@RequestMapping(params = "vScRpContactbalList")
	public ModelAndView vScRpContactbalList(TScAccountStageEntity tScAccountStage, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id3 = tScAccountStage.getDate(); //.getId();
		String year = DateUtils.formatDate((Date)id3, "yyyy");
		String period = DateUtils.formatDate((Date)id3, "MM");
		if (Integer.parseInt(period)<10){//如果月份为1-9月，则去掉前面的0，因为表中存储时没有存储时前面没有0
			period = period.substring(1);
		}
		//===================================================================================
		//查询-应收应付结账
		String hql3 = "from VScRpContactbalEntity where 1 = 1 AND year=? and period=?";// fID = ? ";
		try{
			List<VScRpContactbalEntity> vScRpContactbalEntityList = systemService.findHql(hql3,year,period);
			req.setAttribute("tScRpContactbalList", vScRpContactbalEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/qihang/buss/sc/sys/vScRpContactbalList");
	}

	/**
	 * 加载明细列表[收支结账]
	 *
	 * @return
	 */
	@RequestMapping(params = "tScRpExpbalList")
	public ModelAndView tScRpExpbalList(TScAccountStageEntity tScAccountStage, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id4 = tScAccountStage.getId();
		//===================================================================================
		//查询-收支结账
	    String hql4 = "from TScRpExpbalEntity where 1 = 1 AND fID = ? ";
	    try{
	    	List<TScRpExpbalEntity> tScRpExpbalEntityList = systemService.findHql(hql4,id4);
			req.setAttribute("tScRpExpbalList", tScRpExpbalEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/qihang/buss/sc/sys/tScRpExpbalList");
	}

	/**
	 * 加载明细列表[收支结账视图]
	 *
	 * @return
	 */
	@RequestMapping(params = "vScRpExpbalList")
	public ModelAndView vScRpExpbalList(TScAccountStageEntity tScAccountStage, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id4 = tScAccountStage.getDate(); //.getId();
		String year = DateUtils.formatDate((Date)id4, "yyyy");
		String period = DateUtils.formatDate((Date)id4, "MM");
		if (Integer.parseInt(period)<10){//如果月份为1-9月，则去掉前面的0，因为表中存储时没有存储时前面没有0
			period = period.substring(1);
		}
		//===================================================================================
		//查询-收支结账
		String hql4 = "from VScRpExpbalEntity where 1 = 1 AND year=? and period=?";// fID = ? ";
		try{
			List<VScRpExpbalEntity> vScRpExpbalEntityList = systemService.findHql(hql4,year,period);
			req.setAttribute("tScRpExpbalList", vScRpExpbalEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/qihang/buss/sc/sys/vScRpExpbalList");
	}

}
