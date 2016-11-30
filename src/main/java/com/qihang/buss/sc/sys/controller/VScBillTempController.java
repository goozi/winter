package com.qihang.buss.sc.sys.controller;
import com.qihang.buss.sc.sys.entity.TScBillTempEntity;
import com.qihang.buss.sc.sys.entity.VScBillTempEntity;
import com.qihang.buss.sc.sys.service.VScBillTempServiceI;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.context.annotation.Scope;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.core.util.MyBeanUtils;

import java.io.OutputStream;
import com.qihang.winter.core.util.BrowserUtils;
import com.qihang.winter.poi.excel.ExcelExportUtil;
import com.qihang.winter.poi.excel.ExcelImportUtil;
import com.qihang.winter.poi.excel.entity.ExportParams;
import com.qihang.winter.poi.excel.entity.ImportParams;
import com.qihang.winter.poi.excel.entity.TemplateExportParams;
import com.qihang.winter.poi.excel.entity.vo.NormalExcelConstants;
import com.qihang.winter.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.qihang.winter.core.util.ResourceUtil;
import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;
import com.qihang.winter.core.util.ExceptionUtil;



/**   
 * @Title: Controller
 * @Description: 单据草稿
 * @author onlineGenerator
 * @date 2016-08-26 11:04:54
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/vScBillTempController")
public class VScBillTempController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(VScBillTempController.class);

	@Autowired
	private VScBillTempServiceI vScBillTempService;
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
	 * 单据草稿列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "vScBillTemp")
	public ModelAndView vScBillTemp(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/sys/vScBillTempList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(VScBillTempEntity vScBillTemp,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(VScBillTempEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, vScBillTemp, request.getParameterMap());
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
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.vScBillTempService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 选单页面查询
	 * @param tScBillTemp
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "goSelectDialog")
	public ModelAndView goSelectDialog(VScBillTempEntity tScBillTemp, HttpServletRequest req) {
		if (StringUtil.isNotEmpty( tScBillTemp.getTranType())) {
			req.setAttribute("tranType",  tScBillTemp.getTranType());
		}
		String billerId  = ResourceUtil.getSessionUserName().getId();//制单人
		String sonId = ResourceUtil.getSessionUserName().getCurrentDepart().getId();//分支机构
		String accountId = ResourceUtil.getAccountId();
		req.setAttribute("billerId",billerId);
		req.setAttribute("sonId",sonId);
		req.setAttribute("accountId",accountId);
		return new ModelAndView("com/qihang/buss/sc/sys/vScBillTempList");
	}
	/**
	 * 单据草稿编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(VScBillTempEntity vScBillTemp, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(vScBillTemp.getId())) {
			vScBillTemp = vScBillTempService.getEntity(VScBillTempEntity.class, vScBillTemp.getId());
			req.setAttribute("vScBillTempPage", vScBillTemp);
		}
		return new ModelAndView("com/qihang/buss/sc/sys/vScBillTemp-update");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(VScBillTempEntity vScBillTemp,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(VScBillTempEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, vScBillTemp, request.getParameterMap());
		List<VScBillTempEntity> vScBillTemps = this.vScBillTempService.getListByCriteriaQuery(cq,false);
		modelMap.put(NormalExcelConstants.FILE_NAME,"单据草稿");
		modelMap.put(NormalExcelConstants.CLASS,VScBillTempEntity.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("单据草稿列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
			"导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,vScBillTemps);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(VScBillTempEntity vScBillTemp,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "单据草稿");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,VScBillTempEntity.class);
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
				List<VScBillTempEntity> listVScBillTempEntitys = ExcelImportUtil.importExcel(file.getInputStream(),VScBillTempEntity.class,params);
				for (VScBillTempEntity vScBillTemp : listVScBillTempEntitys) {
					vScBillTempService.save(vScBillTemp);
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
