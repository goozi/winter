package com.qihang.buss.sc.init.controller;
import com.qihang.buss.sc.init.entity.VScIcInitialEntity;
import com.qihang.buss.sc.init.service.VScIcInitialServiceI;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
import com.qihang.buss.sc.util.AccountUtil;
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
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.web.system.service.UserService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @Title: Controller
 * @Description: 存货初始化单
 * @author onlineGenerator
 * @date 2016-08-18 18:05:14
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/vScIcInitialController")
public class VScIcInitialController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VScIcInitialController.class);

	@Autowired
	private VScIcInitialServiceI vIcInitialService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private UserService userService;
	private String message;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 存货初始化单列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "vScIcInitial")
	public ModelAndView vScIcInitial(HttpServletRequest request) {
		request.setAttribute("isAccountOpen",AccountUtil.isAccountOpen());
		request.setAttribute("tranType",Globals.SC_IC_INITIAL_TRANTYPE);//存货初始化单据类型
		return new ModelAndView("com/qihang/buss/sc/init/vScIcInitialList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(VScIcInitialEntity vIcInitial, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(VScIcInitialEntity.class, dataGrid);
		dataGrid.setFooter("qtystr,smallqtystr,basicqtystr,costpricestr,costamountstr");
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, vIcInitial, request.getParameterMap());
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
				Boolean isAudit = userService.isAllowAudit(vIcInitial.getTrantype().toString(),userId,depart.getId());
				cq.eq("cancellation",0);
				//判断当前用户是否在多级审核队列中
				if(isAudit){
					Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId,depart.getId(),vIcInitial.getTrantype().toString());
					if(userAuditLeave.size() > 0){
						String leaves = "";
						for(Integer leave : userAuditLeave){
							leaves += leave+",";
						}
						leaves = leaves.substring(0,leaves.length()-1);
						List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in ("+leaves+")",new Object[]{depart.getId(),vIcInitial.getTrantype().toString()});
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
		this.vIcInitialService.getDataGridReturn(cq, true);
		//测试切换数据源的连接信息[测试通过后需清除]
		//systemService.getConn();
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","vScIcInitialController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(VScIcInitialEntity vIcInitial, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(VScIcInitialEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, vIcInitial, request.getParameterMap());
		List<VScIcInitialEntity> vIcInitials = this.vIcInitialService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"存货初始化单");
		modelMap.put(NormalExcelConstants.CLASS,VScIcInitialEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("存货初始化单列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,vIcInitials);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(VScIcInitialEntity vIcInitial, HttpServletRequest request, HttpServletResponse response
			, DataGrid dataGrid, ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "存货初始化单");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,VScIcInitialEntity.class);
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
				List<VScIcInitialEntity> listVScIcInitialEntities = ExcelImportUtil.importExcel(file.getInputStream(),VScIcInitialEntity.class,params);
				for (VScIcInitialEntity vIcInitial : listVScIcInitialEntities) {
					vIcInitialService.save(vIcInitial);
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
