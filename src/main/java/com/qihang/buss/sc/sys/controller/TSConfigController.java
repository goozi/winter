package com.qihang.buss.sc.sys.controller;

import com.qihang.buss.sc.sys.entity.TSConfigEntity;
import com.qihang.buss.sc.sys.page.TSConfigEntityPage;
import com.qihang.buss.sc.sys.page.TScSupplychainConfigEntityPage;
import com.qihang.buss.sc.sys.service.TSConfigServiceI;
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
import com.qihang.winter.poi.excel.entity.vo.NormalExcelConstants;
import com.qihang.winter.poi.excel.entity.vo.TemplateExcelConstants;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.web.system.pojo.base.TSConfig;
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
import java.util.List;
import java.util.Map;



/**
 * @Title: Controller
 * @Description: t_s_config
 * @author onlineGenerator
 * @date 2016-06-15 11:58:37
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tSConfigController")
public class TSConfigController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TSConfigController.class);

	@Autowired
	private TSConfigServiceI tSConfigService;
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
	 * t_s_config列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tSConfig")
	public ModelAndView tSConfig(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/sys/tSConfigMainpage");
	}





	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TSConfigEntity tSConfig,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSConfigEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSConfig, request.getParameterMap());
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tSConfigService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除t_s_config
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TSConfigEntity tSConfig, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tSConfig = systemService.getEntity(TSConfigEntity.class, tSConfig.getId());
		message = "t_s_config删除成功";
		try{
			tSConfigService.delete(tSConfig);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "t_s_config删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除t_s_config
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "t_s_config删除成功";
		try{
			for(String id:ids.split(",")){
				TSConfigEntity tSConfig = systemService.getEntity(TSConfigEntity.class,
				id
				);
				tSConfigService.delete(tSConfig);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "t_s_config删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加t_s_config
	 *
	 * @param tSConfig
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TSConfigEntity tSConfig, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "t_s_config添加成功";
		try{
			tSConfigService.save(tSConfig);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "t_s_config添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新t_s_config
	 *
	 * @param tSConfig
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TSConfigEntityPage tSConfig, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "t_s_config更新成功";
//		TSConfigEntity t = tSConfigService.get(TSConfigEntity.class, tSConfig.getId());
		try {
//			MyBeanUtils.copyBeanNotNull2Bean(tSConfig, t);
			//tSConfigService.saveOrUpdate(tSConfig);
			//获得当前账套ID，并将设置的几个属性复制过来，免得把系统设置的账套设置未给的属性清空掉,再更新当前账套的设置信息
			String accountId = ResourceUtil.getAccountId();
			String hql = " from TSConfigEntity where accountid=?";
			List<TSConfigEntity> tSConfigList = systemService.findHql(hql, accountId);
			if (tSConfigList.size()>0) {
				for (TSConfigEntity tsConfigEntity : tSConfigList) {
					tsConfigEntity.setPageRecord(tSConfig.getPageRecord());
					tsConfigEntity.setRowHeight(tSConfig.getRowHeight());
					tsConfigEntity.setDefaultRate(tSConfig.getDefaultRate());
					tsConfigEntity.setNumber(tSConfig.getNumber());
					tsConfigEntity.setUnitPrice(tSConfig.getUnitPrice());
					tsConfigEntity.setMoney(tSConfig.getMoney());
					tsConfigEntity.setRates(tSConfig.getRates());
					tsConfigEntity.setDiscountRate(tSConfig.getDiscountRate());
					tsConfigEntity.setOther(tSConfig.getOther());
					tsConfigEntity.setControlMethod(tSConfig.getControlMethod());
					tsConfigEntity.setControlTimePoint(tSConfig.getControlTimePoint());
					tsConfigEntity.setRecearwarDays(tSConfig.getRecearwarDays());
					tsConfigEntity.setPruordearwarDays(tSConfig.getPruordearwarDays());
					tsConfigEntity.setPayearwarDays(tSConfig.getPayearwarDays());
					tsConfigEntity.setSalordearwarDays(tSConfig.getSalordearwarDays());
					tsConfigEntity.setShelflifeearwarDays(tSConfig.getShelflifeearwarDays());
					tsConfigEntity.setSyslogholdDays(tSConfig.getSyslogholdDays());
					tSConfigService.saveOrUpdate(tsConfigEntity);
				}
			}
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "t_s_config更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 更新供应链设置
	 *
	 * @param tScSupplychainConfig
	 * @return
	 */
	@RequestMapping(params = "doSupplyUpdate")
	@ResponseBody
	public AjaxJson doSupplyUpdate(TScSupplychainConfigEntityPage tSConfig, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		message = "t_s_config更新成功";
//		TSConfigEntity t = tSConfigService.get(TSConfigEntity.class, tSConfig.getId());
		try {
			//获得当前账套ID，并将设置的几个属性复制过来，免得把系统设置的账套设置未给的属性清空掉,再更新当前账套的设置信息
			String accountId = ResourceUtil.getAccountId();
			String hql = " from TSConfigEntity where accountid=?";
			List<TSConfigEntity> tSConfigList = systemService.findHql(hql, accountId);
			if (tSConfigList.size()>0) {
				if (tSConfig.getMinusInventoryAccount() == null) {
					tSConfig.setMinusInventoryAccount("0");
				}
				if (tSConfig.getMinusInventorySl() == null) {
					tSConfig.setMinusInventorySl("0");
				}
				if (tSConfig.getStocktakingNotAuditedStockBill() == null) {
					tSConfig.setStocktakingNotAuditedStockBill("0");
				}
				if (tSConfig.getBillSaveSystemWithExamine() == null) {
					tSConfig.setBillSaveSystemWithExamine("0");
				}
				if (tSConfig.getBillExamineSystemWithFollow() == null) {
					tSConfig.setBillExamineSystemWithFollow("0");
				}
				if (tSConfig.getCannotManualOpenInRepertory() == null) {
					tSConfig.setCannotManualOpenInRepertory("0");
				}
				if (tSConfig.getCannotInRepertoryngtPurchasen() == null) {
					tSConfig.setCannotInRepertoryngtPurchasen("0");
				}
				if (tSConfig.getPurchaseStartPriceCallOrder() == null) {
					tSConfig.setPurchaseStartPriceCallOrder("0");
				}
				if (tSConfig.getCannotManualOpenOutRepertory() == null) {
					tSConfig.setCannotManualOpenOutRepertory("0");
				}
				if (tSConfig.getCannotOutRepertoryngtSale() == null) {
					tSConfig.setCannotOutRepertoryngtSale("0");
				}
				if (tSConfig.getSaleStartPriceCallOrder() == null) {
					tSConfig.setSaleStartPriceCallOrder("0");
				}
				for (TSConfigEntity tsConfigEntity : tSConfigList) {
					tsConfigEntity.setMinusInventoryAccount(tSConfig.getMinusInventoryAccount());
					tsConfigEntity.setMinusInventorySl(tSConfig.getMinusInventorySl());
					tsConfigEntity.setStocktakingNotAuditedStockBill(tSConfig.getStocktakingNotAuditedStockBill());
					tsConfigEntity.setBillSaveSystemWithExamine(tSConfig.getBillSaveSystemWithExamine());
					tsConfigEntity.setBillExamineSystemWithFollow(tSConfig.getBillExamineSystemWithFollow());
					tsConfigEntity.setCannotManualOpenInRepertory(tSConfig.getCannotManualOpenInRepertory());
					tsConfigEntity.setCannotInRepertoryngtPurchasen(tSConfig.getCannotInRepertoryngtPurchasen());
					tsConfigEntity.setPurchaseStartPriceCallOrder(tSConfig.getPurchaseStartPriceCallOrder());
					tsConfigEntity.setCannotManualOpenOutRepertory(tSConfig.getCannotManualOpenOutRepertory());
					tsConfigEntity.setCannotOutRepertoryngtSale(tSConfig.getCannotOutRepertoryngtSale());
					tsConfigEntity.setSaleStartPriceCallOrder(tSConfig.getSaleStartPriceCallOrder());
					tsConfigEntity.setPurchaseselectOne(tSConfig.getPurchaseselectOne());
					tsConfigEntity.setPurchaseselectTwo(tSConfig.getPurchaseselectTwo());
					tsConfigEntity.setSaleSelectOne(tSConfig.getSaleSelectOne());
					tsConfigEntity.setSaleSelectTwo(tSConfig.getSaleSelectTwo());
					tsConfigEntity.setSaleSelectThree(tSConfig.getSaleSelectThree());
					tSConfigService.saveOrUpdateSupply(tsConfigEntity);
				}

			}
//			MyBeanUtils.copyBeanNotNull2Bean(tSConfig, t);
			//tSConfigService.saveOrUpdateSupply(tSConfig);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "t_s_config更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * t_s_config新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TSConfigEntity tSConfig, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSConfig.getId())) {
			tSConfig = tSConfigService.getEntity(TSConfigEntity.class, tSConfig.getId());
			req.setAttribute("tSConfigPage", tSConfig);
		}
		return new ModelAndView("com/qihang/buss/sc/sys/tSConfig-add");
	}


	/**
	 *
	 * @param code
	 * @param value
	 * @return
	 */
//	private  String getUpdateData(String code) {
//		List<TSConfigEntity> tsConfigEntityList=systemService.findHql("from TSConfigEntity  t where t.code=?",code);
//		TSConfigEntity tsConfigEntity;
//		if(tsConfigEntityList.size()>0) {
//			tsConfigEntity = tsConfigEntityList.get(0);
//		}
//
//		return null;
//	}

	/**
	 *
	 * @param code
	 * @param value
	 * @return
	 */
//	private  String saveCFG(String code,String value) {
//		List<TSConfigEntity> tsConfigEntityList=systemService.findHql("from TSConfigEntity t where  t.code=?", code);
//		TSConfigEntity tsConfigEntity;
//		if(tsConfigEntityList.size()>0){
//			tsConfigEntity=tsConfigEntityList.get(0);
//			tsConfigEntity.setContent(value);
//			systemService.saveOrUpdate(tsConfigEntity);
//		}
//		return null;
//	}
	/**
	 * t_s_config编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TSConfigEntityPage configEntityPage , HttpServletRequest req) {
		//获得当前账套ID
		String accountId = ResourceUtil.getAccountId();
		String hql = " from TSConfigEntity where accountid=?";

		List<TSConfigEntity> tSConfigList = systemService.findHql(hql, accountId);

		if (tSConfigList.size() > 0) {
			for (TSConfigEntity temp : tSConfigList) {
				//分页记录
				if (temp.getCode().equals(Globals.PAGE_NUMBER_CODE)) {
					configEntityPage.setPageRecord(temp.getContent());
				}
				//行高
				else if (temp.getCode().equals(Globals.ROW_HEIGHT_CODE)){
					configEntityPage.setRowHeight(temp.getContent());

				}
				//默认税率
				else if (temp.getCode().equals(Globals.DEFAULT_RATE_CODE)){
					configEntityPage.setDefaultRate(temp.getContent());

				}
				//数量
				else if (temp.getCode().equals(Globals.NUMBER_CODE)) {
					configEntityPage.setNumber(temp.getContent());

				}
				//单价
				else if (temp.getCode().equals(Globals.UNITP_RICE_CODE)) {
					configEntityPage.setUnitPrice(temp.getContent());

				}
				//金额
				else if (temp.getCode().equals(Globals.MONEY_CODE)) {
					configEntityPage.setMoney(temp.getContent());

				}
				//税率
				else if (temp.getCode().equals(Globals.RATES_CODE)) {
					configEntityPage.setRates(temp.getContent());

				}
				//折扣率
				else if (temp.getCode().equals(Globals.DISCOUNT_RATE_CODE)){
					configEntityPage.setDiscountRate(temp.getContent());

				}
				//其他
				else if (temp.getCode().equals(Globals.OTHER_CODE)) {
					configEntityPage.setOther(temp.getContent());

				}
				//控制方式
				else if (temp.getCode().equals(Globals.CONTROL_METHOD_CODE)){
					configEntityPage.setControlMethod(temp.getContent());

				}
				//控制时点
				else if (temp.getCode().equals(Globals.CONTROL_TIME_POINT_CODE)){
					configEntityPage.setControlTimePoint(temp.getContent());

				}
				//应收预警天数
				else if (temp.getCode().equals(Globals.RECEARWAR_DAYS_CODE)){
					configEntityPage.setRecearwarDays(temp.getContent());

				}
				//采购订单预警天数
				else if (temp.getCode().equals(Globals.PRUORDEARWAR_DAYS_CODE)){
					configEntityPage.setPruordearwarDays(temp.getContent());

				}
				//应付预警天数
				else if (temp.getCode().equals(Globals.PAYEARWAR_DAYS_CODE)){
					configEntityPage.setPayearwarDays(temp.getContent());

				}
				//销售订单预警天数
				else if (temp.getCode().equals(Globals.SALORDEARWAR_DAYS_CODE)){
					configEntityPage.setSalordearwarDays(temp.getContent());

				}
				//保质期预警天数
				else if (temp.getCode().equals(Globals.SHELFLIFEEARWAR_DAYS_CODE)){
					configEntityPage.setShelflifeearwarDays(temp.getContent());

				}
				//系统日志保留天数
				else if (temp.getCode().equals(Globals.SYSLOGHOLD_DAYS_CODE)){
					configEntityPage.setSyslogholdDays(temp.getContent());
				}

			}
		}
		req.setAttribute("configEntityPage", configEntityPage);

		return new ModelAndView("com/qihang/buss/sc/sys/tSConfig-update");
	}

	/**
	 * 供应链设置编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goSupplyUpdate")
	public ModelAndView goSupplyUpdate(TScSupplychainConfigEntityPage tScSupplychainConfigEntityPage , HttpServletRequest req) {
		//获得当前账套ID
		String accountId = ResourceUtil.getAccountId();
		String hql = " from TSConfigEntity where accountid=?";

		List<TSConfigEntity> tSConfigList = tSConfigService.findHql(hql,accountId);
		if (tSConfigList.size() > 0) {
			for (TSConfigEntity temp : tSConfigList) {
				//允许负库存结账
				if (temp.getCode().equals(Globals.MINUSINVENTORYACCOUNT_CODE)) {
					tScSupplychainConfigEntityPage.setMinusInventoryAccount(temp.getContent());
				}
				//允许负库存出库
				else if (temp.getCode().equals(Globals.MINUSINVENTORYSL_CODE)){
					tScSupplychainConfigEntityPage.setMinusInventorySl(temp.getContent());

				}
				//允许盘点有未审核存货单据的数据
				else if (temp.getCode().equals(Globals.STOCKTAKINGNOTAUDITEDSTOCKBILL_CODE)){
					tScSupplychainConfigEntityPage.setStocktakingNotAuditedStockBill(temp.getContent());

				}
				//单据保存时系统自动审核
				else if (temp.getCode().equals(Globals.BILLSAVESYSTEMWITHEXAMINE_CODE)){
					tScSupplychainConfigEntityPage.setBillSaveSystemWithExamine(temp.getContent());

				}
				//单据审核时系统自带后续业务单据
				else if (temp.getCode().equals(Globals.BILLEXAMINESYSTEMWITHFOLLOW_CODE)){
					tScSupplychainConfigEntityPage.setBillExamineSystemWithFollow(temp.getContent());

				}
				//不允许手工开入库单
				else if (temp.getCode().equals(Globals.CANNOTMANUALOPENINREPERTORY_CODE)){
					tScSupplychainConfigEntityPage.setCannotManualOpenInRepertory(temp.getContent());

				}
				//不允许入库单数量大于采购订单数量
				else if (temp.getCode().equals(Globals.CANNOTINREPERTORYNYTPURCHASEN_CODE)){
					tScSupplychainConfigEntityPage.setCannotInRepertoryngtPurchasen(temp.getContent());

				}
				//采购模块启用价格调用顺序
				else if (temp.getCode().equals(Globals.PURCHASESTARTPRICECALLORDER_CODE)){
					tScSupplychainConfigEntityPage.setPurchaseStartPriceCallOrder(temp.getContent());

				}
				//采购设置下拉框一
				else if (temp.getCode().equals(Globals.PURCHASESELECTONE_CODE)){
					tScSupplychainConfigEntityPage.setPurchaseselectOne(temp.getContent());

				}
				//采购设置下拉框二
				else if (temp.getCode().equals(Globals.PURCHASESELECTTWO_CODE)){
					tScSupplychainConfigEntityPage.setPurchaseselectTwo(temp.getContent());

				}
				//不允许手工开出库单
				else if (temp.getCode().equals(Globals.CANNOTMANUALOPENOUTREPERTORY_CODE)){
					tScSupplychainConfigEntityPage.setCannotManualOpenOutRepertory(temp.getContent());

				}
				//不允许出库单数量大于销售订单数量
				else if (temp.getCode().equals(Globals.CANNOTOUTREPERTORYNGTSALE_CODE)){
					tScSupplychainConfigEntityPage.setCannotOutRepertoryngtSale(temp.getContent());

				}
				//销售模块启用价格调用顺序
				else if (temp.getCode().equals(Globals.SALESTARTPRICECALLORDER_CODE)){
					tScSupplychainConfigEntityPage.setSaleStartPriceCallOrder(temp.getContent());

				}
				//销售设置下拉框一
				else if (temp.getCode().equals(Globals.SALESELECTONE_CODE)){
					tScSupplychainConfigEntityPage.setSaleSelectOne(temp.getContent());

				}
				//销售设置下拉框二
				else if (temp.getCode().equals(Globals.SALESELECTTWO_CODE)){
					tScSupplychainConfigEntityPage.setSaleSelectTwo(temp.getContent());

				}
				//销售设置下拉框三
				else if (temp.getCode().equals(Globals.SALESELECTTHREE_CODE)){
					tScSupplychainConfigEntityPage.setSaleSelectThree(temp.getContent());

				}


			}
		}
		req.setAttribute("tScSupplychainConfigEntityPage", tScSupplychainConfigEntityPage);

		return new ModelAndView("com/qihang/buss/sc/sys/tScSupplychainConfig-update");

	}

	/**
	 * 导入功能跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tSConfigController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

	/**
	 * 导出excel
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TSConfigEntity tSConfig,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TSConfigEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSConfig, request.getParameterMap());
		List<TSConfigEntity> tSConfigs = this.tSConfigService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"t_s_config");
		modelMap.put(NormalExcelConstants.CLASS,TSConfigEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("t_s_config列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,tSConfigs);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 *
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TSConfigEntity tSConfig,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "t_s_config");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TSConfigEntity.class);
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
				List<TSConfigEntity> listTSConfigEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TSConfigEntity.class,params);
				for (TSConfigEntity tSConfig : listTSConfigEntitys) {
					tSConfigService.save(tSConfig);
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

  /**
   * 根据code返回系统配置项值
   * @param code
   * @return
   */
  @RequestMapping(params = "getConfigByCode")
  @ResponseBody
  public String getConfigByCode(String code){
	  String configContent = "";
    List<TSConfig> tsConfigList = this.tSConfigService.findByProperty(TSConfig.class,"code",code);
    if(tsConfigList.size()>0){
      configContent = tsConfigList.get(0).getContents();
    }
    return configContent;
  }

  @RequestMapping(params = "test")
	public ModelAndView test(){
    return new ModelAndView("com/qihang/buss/sc/sys/test");
  }
}
