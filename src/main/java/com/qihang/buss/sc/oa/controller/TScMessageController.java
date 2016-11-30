
package com.qihang.buss.sc.oa.controller;


import com.qihang.buss.sc.oa.entity.TScMessageEntity;
import com.qihang.buss.sc.oa.entity.TScMessageFileEntity;
import com.qihang.buss.sc.oa.entity.TScMessageReceiveEntity;
import com.qihang.buss.sc.oa.page.TScMessagePage;
import com.qihang.buss.sc.oa.service.TScMessageServiceI;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.ExceptionUtil;
import com.qihang.winter.core.util.MyBeanUtils;
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
import com.qihang.winter.tag.vo.datatable.SortDirection;
import com.qihang.winter.web.system.pojo.base.TSUser;
import com.qihang.winter.web.system.service.SystemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Title: Controller
 * @Description: 消息管理
 * @author onlineGenerator
 * @date 2015-12-14 20:53:49
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScMessageController")
public class TScMessageController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScMessageController.class);

	@Autowired
	private TScMessageServiceI tScMessageService;
	@Autowired
	private SystemService systemService;


	/**
	 * 消息管理列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tScMessage")
	public ModelAndView tScMessage(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/oa/tScMessageList");
	}

	@RequestMapping(params = "tScMessageAll")
	public ModelAndView tScMessageAll(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sc/oa/tScMessageAllList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScMessageEntity tScMessage,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScMessageEntity.class, dataGrid);
		String sender = ResourceUtil.getSessionUserName().getId();
		if(null != sender){
			tScMessage.setSender(sender);
		}
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScMessage);
		try{
		//自定义追加查询条件
		String query_createDate_begin = request.getParameter("createDate_begin");
		String query_createDate_end = request.getParameter("createDate_end");
		if(StringUtil.isNotEmpty(query_createDate_begin)){
			cq.ge("createDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_createDate_begin));
		}
		if(StringUtil.isNotEmpty(query_createDate_end)){
			cq.le("createDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_createDate_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScMessageService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除消息管理
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScMessageEntity tScMessage, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScMessage = systemService.getEntity(TScMessageEntity.class, tScMessage.getId());
		String message = "消息管理删除成功";
		try{
			tScMessageService.delMain(tScMessage);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "消息管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除消息管理
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "消息管理删除成功";
		try{
			for(String id:ids.split(",")){
				TScMessageEntity tScMessage = systemService.getEntity(TScMessageEntity.class,
				id
				);
				tScMessageService.delMain(tScMessage);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "消息管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加消息管理
	 *
	 * @param tScMessage
	 * @param tScMessagePage
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScMessageEntity tScMessage,TScMessagePage tScMessagePage, HttpServletRequest request) {
		List<TScMessageFileEntity> tScMessageFileList =  tScMessagePage.getTScMessageFileList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		tScMessage.setContent(tScMessage.getContent().replaceAll("\"","&quot"));
		try{
			String sender = ResourceUtil.getSessionUserName().getId();
			if(null != sender){
				tScMessage.setSender(sender);
			}
			tScMessageService.addMain(tScMessage, tScMessageFileList);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "消息管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新消息管理
	 *
	 * @param tScMessage
	 * @param tScMessagePage
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScMessageEntity tScMessage,TScMessagePage tScMessagePage, HttpServletRequest request) {
		List<TScMessageFileEntity> tScMessageFileList =  tScMessagePage.getTScMessageFileList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			tScMessageService.updateMain(tScMessage, tScMessageFileList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新消息管理失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 消息管理新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScMessageEntity tScMessage, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScMessage.getId())) {
			tScMessage = tScMessageService.getEntity(TScMessageEntity.class, tScMessage.getId());
			req.setAttribute("tScMessagePage", tScMessage);
		}
		return new ModelAndView("com/qihang/buss/sc/oa/tScMessage-add");
	}

	/**
	 * 消息管理编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScMessageEntity tScMessage, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScMessage.getId())) {
			tScMessage = tScMessageService.getEntity(TScMessageEntity.class, tScMessage.getId());
			req.setAttribute("tScMessagePage", tScMessage);
		}
		return new ModelAndView("com/qihang/buss/sc/oa/tScMessage-update");
	}

  /**
  * 导入功能跳转
  *
  * @return
  */
  @RequestMapping(params = "upload")
  public ModelAndView upload(HttpServletRequest req) {
  req.setAttribute("controller_name", "tScMessageController");
  return new ModelAndView("common/upload/pub_excel_upload");
  }

  /**
  * 导出excel
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXls")
  public String exportXls(TScMessageEntity tScMessage,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  CriteriaQuery cq = new CriteriaQuery(TScMessageEntity.class, dataGrid);
  com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScMessage, request.getParameterMap());
  List<TScMessageEntity> tScMessages = this.tScMessageService.getListByCriteriaQuery(cq,false);
  //如需动态排除部分列不导出时启用，列名指Excel中文列名
  String[] exclusions = {"排除列名1","排除列名2"};
  modelMap.put(NormalExcelConstants.FILE_NAME,"消息管理");
  modelMap.put(NormalExcelConstants.CLASS,TScMessageEntity.class);
  modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("消息管理列表", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
  "导出信息",exclusions));
  modelMap.put(NormalExcelConstants.DATA_LIST, tScMessages);
  return NormalExcelConstants.JEECG_EXCEL_VIEW;
  }
  /**
  * 导出excel 使模板
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXlsByT")
  public String exportXlsByT(TScMessageEntity tScMessage,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  modelMap.put(TemplateExcelConstants.FILE_NAME, "消息管理");
  modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
  modelMap.put(TemplateExcelConstants.MAP_DATA,null);
  modelMap.put(TemplateExcelConstants.CLASS,TScMessageEntity.class);
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
    ExcelImportResult importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(), TScMessageEntity.class, params);
    List<TScMessageEntity> listTScMessageEntitys = importResult.getList();
    StringBuilder stringBuilder = new StringBuilder("已存在的数据:");
    boolean flag = false;
    if(!importResult.isVerfiyFail()) {
      for (TScMessageEntity tScMessage : listTScMessageEntitys) {
      //以下检查导入数据是否重复的语句可视需求启用
        //Long count = tScMessageService.getCountForJdbc("这里放检查数据是否重复的SQL语句");
        //if(count >0) {
          //flag = true;
          //stringBuilder.append(tScMessage.getId()+",");
        //} else {
          tScMessageService.save(tScMessage);
        //}
      }
      j.setMsg("文件导入成功！");
      //j.setMsg("文件导入成功！" + (flag? stringBuilder.toString():""));
    }else {
      String excelPath = "/upload/excelUpload/TScMessageEntity/"+importResult.getExcelName();
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
	 * 加载明细列表[消息管理附件表]
	 *
	 * @return
	 */
	@RequestMapping(params = "tScMessageFileList")
	public ModelAndView tScMessageFileList(TScMessageEntity tScMessage, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id0 = tScMessage.getId();
		//===================================================================================
		//查询-消息管理附件表
	    String hql0 = "from TScMessageFileEntity where 1 = 1 AND mESSAGE_ID = ? ";
	    try{
	    	List<TScMessageFileEntity> tScMessageFileEntityList = systemService.findHql(hql0,id0);
			req.setAttribute("tScMessageFileList", tScMessageFileEntityList);
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/qihang/buss/sc/oa/tScMessageFileList");
	}


	@RequestMapping(params = "getDateInfo")
	@ResponseBody
	public Map<String,Object> getDateInfo(TScMessageReceiveEntity message,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		TSUser user = ResourceUtil.getSessionUserName();
		message.setUserId(user.getId());
        dataGrid.setRows(7);
        CriteriaQuery cq = new CriteriaQuery(TScMessageReceiveEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, message, request.getParameterMap());
		try{
			//自定义追加查询条件
			cq.eq("readStatus",0);
			cq.addOrder("createDate", SortDirection.desc);
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScMessageService.getDataGridReturn(cq, true);
		List<TScMessageReceiveEntity> rows = dataGrid.getResults();
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("total", rows.size());
		result.put("rows", rows);
		return result;
		//TagUtil.datagrid(response, dataGrid);
	}

	//App 接口 start zyy 2015-01-15
	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "getAppData")
	@ResponseBody
	public DataGrid getAppData(TScMessageEntity tScMessage,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TScMessageEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScMessage);
		try{
			//自定义追加查询条件
			String query_createDate_begin = request.getParameter("createDate_begin");
			String query_createDate_end = request.getParameter("createDate_end");
			if(StringUtil.isNotEmpty(query_createDate_begin)){
				cq.ge("createDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_createDate_begin));
			}
			if(StringUtil.isNotEmpty(query_createDate_end)){
				cq.le("createDate", new SimpleDateFormat("yyyy-MM-dd").parse(query_createDate_end));
			}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tScMessageService.getDataGridReturn(cq, true);
//		TagUtil.datagrid(response, dataGrid);
		return dataGrid;
	}

	/**
	 * 批量删除消息管理
	 *
	 * @return
	 */
	@RequestMapping(params = "delAppData")
	@ResponseBody
	public AjaxJson delAppData(String ids,String sender,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "消息管理删除成功";
		try{
			for(String id:ids.split(",")){
				TScMessageEntity tScMessage = systemService.getEntity(TScMessageEntity.class, id);
				if(null != sender && sender.equals(tScMessage.getSender())){
					tScMessageService.delMain(tScMessage);
					j.setSuccess(true);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "消息管理删除失败";
			j.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 消息管理编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goView")
	@ResponseBody
	public AjaxJson goView(TScMessageEntity tScMessage, HttpServletRequest req) {
		AjaxJson j = new AjaxJson();
		if (StringUtil.isNotEmpty(tScMessage.getId())) {
			tScMessage = tScMessageService.getEntity(TScMessageEntity.class, tScMessage.getId());
			List<TScMessageFileEntity> list = systemService.findByProperty(TScMessageFileEntity.class,"messageId",tScMessage.getId());
			if(null != list && list.size() > 0){
				List<TScMessageFileEntity> fList = new ArrayList<TScMessageFileEntity>();
				for (TScMessageFileEntity fileEntity : list) {
					if(null != fileEntity.getMessageFile() && !"".equals(fileEntity.getMessageFile())){
						String sql = "select attachmenttitle from t_s_attachment where realpath = '"+fileEntity.getMessageFile()+"'";
						List<String> sqlList = systemService.findListbySql(sql);
						if(null != sqlList && sqlList.size() > 0){
							fileEntity.setFileName(sqlList.get(0));
						}
						fList.add(fileEntity);
					}
				}
				if(fList.size() > 0){
					tScMessage.setFileList(fList);
				}
			}
			j.setObj(tScMessage);
		}
		return j;
	}

	/**
	 * 添加消息管理
	 *
	 * @param tScMessage
	 * @param tScMessagePage
	 * @return
	 */
	@RequestMapping(params = "doAppAdd")
	@ResponseBody
	public AjaxJson doAppAdd(@RequestBody TScMessagePage tScMessagePage, HttpServletRequest request) {
		List<TScMessageFileEntity> tScMessageFileList =  tScMessagePage.getTScMessageFileList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			TScMessageEntity tScMessage = new TScMessageEntity();
			MyBeanUtils.copyBeanNotNull2Bean(tScMessagePage, tScMessage);
			tScMessage.setContent(tScMessage.getContent().replaceAll("\"","&quot"));
			tScMessageService.addMain(tScMessage, tScMessageFileList);
			j.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			message = "消息管理添加失败";
			j.setSuccess(false);
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 未读消息列表
	 * @return
	 */
	@RequestMapping(params = "datagrid_main")
	@ResponseBody
	public List<Map<String,Object>> datagrid_main(){
		String userId = ResourceUtil.getSessionUserName().getId();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<TScMessageReceiveEntity> messageReceiveEntityList = systemService.findHql("from TScMessageReceiveEntity where userId = ? and readStatus=0", userId);
		List<Map<String,Object>> result = new ArrayList<Map<String, Object>>();
 		if(messageReceiveEntityList.size() > 0){
			int length = messageReceiveEntityList.size();
			if(length > 10){
				length = 10;
			}
			for(int i = 0 ; i < length ; i++){
				TScMessageReceiveEntity entity = messageReceiveEntityList.get(i);
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("id",entity.getId());
				map.put("title",entity.getTitle());
				map.put("issueDate",sdf.format(entity.getCreateDate()));
				result.add(map);
			}
		}

		return result;
	}

}
