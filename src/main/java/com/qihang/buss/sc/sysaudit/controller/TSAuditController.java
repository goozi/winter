
package com.qihang.buss.sc.sysaudit.controller;
import com.qihang.buss.sc.sysaudit.entity.TSAuditEntity;
import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TSBillInfoEntity;
import com.qihang.buss.sc.sysaudit.service.TSAuditServiceI;
import com.qihang.buss.sc.sysaudit.page.TSAuditPage;
import com.qihang.buss.sc.sysaudit.entity.TSAuditLeaveEntity;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.ComboTree;
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
import com.qihang.winter.web.system.pojo.base.TSUserOrg;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @Title: Controller
 * @Description: 多级审核主表
 * @author onlineGenerator
 * @date 2016-06-21 11:48:24
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tSAuditController")
public class TSAuditController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TSAuditController.class);

	@Autowired
	private TSAuditServiceI tSAuditService;
	@Autowired
	private SystemService systemService;

	@Autowired
	private UserService userService;


	/**
	 * 多级审核主表列表 页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "tSAudit")
	public ModelAndView tSAudit(HttpServletRequest request) {
		return new ModelAndView("com/qihang/buss/sysaudit/tSAuditList");
	}

	/**
	 * easyui AJAX请求数据
	 *
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TSAuditEntity tSAudit,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TSAuditEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSAudit);
		try{
		//自定义追加查询条件
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tSAuditService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除多级审核主表
	 *
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TSAuditEntity tSAudit, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tSAudit = systemService.getEntity(TSAuditEntity.class, tSAudit.getId());
		String message = "多级审核主表删除成功";
		try{
			tSAuditService.delMain(tSAudit);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "多级审核主表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 批量删除多级审核主表
	 *
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "多级审核主表删除成功";
		try{
			for(String id:ids.split(",")){
				TSAuditEntity tSAudit = systemService.getEntity(TSAuditEntity.class,
				id
				);
				tSAuditService.delMain(tSAudit);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "多级审核主表删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加多级审核主表
	 *
	 * @param tSAudit
	 * @param tSAuditPage
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TSAuditEntity tSAudit,TSAuditPage tSAuditPage, HttpServletRequest request) {
		List<TSAuditLeaveEntity> tSAuditLeaveList =  tSAuditPage.getTSAuditLeaveList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			tSAuditService.addMain(tSAudit, tSAuditLeaveList);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "多级审核主表添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新多级审核主表
	 *
	 * @param tSAudit
	 * @param tSAuditPage
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TSAuditEntity tSAudit,TSAuditPage tSAuditPage, HttpServletRequest request) {
		List<TSAuditLeaveEntity> tSAuditLeaveList =  tSAuditPage.getTSAuditLeaveList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			if(StringUtils.isNotEmpty(tSAudit.getId())) {
				//若启动多级审核未勾上，则不修改其他数据，只修改那个值
				if(null == tSAudit.getIsAudit()){
					TSAuditEntity auditEntity = tSAuditService.getEntity(TSAuditEntity.class,tSAudit.getId());
					auditEntity.setIsAudit(0);
					tSAuditService.saveOrUpdate(auditEntity);
				}else {
					tSAuditService.updateMain(tSAudit, tSAuditLeaveList);
				}
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			}else{
				tSAuditService.addMain(tSAudit, tSAuditLeaveList);
				systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
				message = "添加成功";
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "更新多级审核主表失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 多级审核主表新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TSAuditEntity tSAudit, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tSAudit.getId())) {
			tSAudit = tSAuditService.getEntity(TSAuditEntity.class, tSAudit.getId());
			req.setAttribute("tSAuditPage", tSAudit);
		}
		return new ModelAndView("com/qihang/buss/sysaudit/tSAudit-add");
	}

	/**
	 * 多级审核主表编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TSAuditEntity tSAudit, HttpServletRequest req) {
		String infoId = req.getParameter("infoId");
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		TSBillInfoEntity infoEntity = tSAuditService.getEntity(TSBillInfoEntity.class,infoId);
		//if (StringUtil.isNotEmpty(tSAudit.getId())) {
		if(null != infoEntity) {
			List<TSAuditEntity> tsAuditEntityList = tSAuditService.findHql("from TSAuditEntity where billId=? and sonId = ?", new Object[]{infoEntity.getBillId(), depart.getId()});
			if (tsAuditEntityList.size() > 0) {
				for (TSAuditEntity tsAuditEntity : tsAuditEntityList) {
					req.setAttribute("tSAuditPage", tsAuditEntity);
				}
			} else {
				TSAuditEntity entity = new TSAuditEntity();
				entity.setBillId(infoEntity.getBillId());
				//分支机构id
				entity.setSonId(depart.getId());
				req.setAttribute("tSAuditPage", entity);
			}
		} else {
			TSAuditEntity entity = new TSAuditEntity();
			//entity.setBillId(infoEntity.getBillId());
			//分支机构id
			entity.setSonId(depart.getId());
			req.setAttribute("tSAuditPage", entity);
		}
		//}
		return new ModelAndView("com/qihang/buss/sysaudit/tSAudit-update");
	}

  /**
  * 导入功能跳转
  *
  * @return
  */
  @RequestMapping(params = "upload")
  public ModelAndView upload(HttpServletRequest req) {
  req.setAttribute("controller_name","tSAuditController");
  return new ModelAndView("common/upload/pub_excel_upload");
  }

  /**
  * 导出excel
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXls")
  public String exportXls(TSAuditEntity tSAudit,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  CriteriaQuery cq = new CriteriaQuery(TSAuditEntity.class, dataGrid);
  com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tSAudit, request.getParameterMap());
  List<TSAuditEntity> tSAudits = this.tSAuditService.getListByCriteriaQuery(cq,false);
  //如需动态排除部分列不导出时启用，列名指Excel中文列名
  String[] exclusions = {"排除列名1","排除列名2"};
  modelMap.put(NormalExcelConstants.FILE_NAME,"多级审核主表");
  modelMap.put(NormalExcelConstants.CLASS,TSAuditEntity.class);
  modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("多级审核主表列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(),
  "导出信息",exclusions));
  modelMap.put(NormalExcelConstants.DATA_LIST, tSAudits);
  return NormalExcelConstants.JEECG_EXCEL_VIEW;
  }
  /**
  * 导出excel 使模板
  *
  * @param request
  * @param response
  */
  @RequestMapping(params = "exportXlsByT")
  public String exportXlsByT(TSAuditEntity tSAudit,HttpServletRequest request,HttpServletResponse response
  , DataGrid dataGrid,ModelMap modelMap) {
  modelMap.put(TemplateExcelConstants.FILE_NAME, "多级审核主表");
  modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
  modelMap.put(TemplateExcelConstants.MAP_DATA,null);
  modelMap.put(TemplateExcelConstants.CLASS,TSAuditEntity.class);
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
    ExcelImportResult importResult = ExcelImportUtil.importExcelVerify(file.getInputStream(),TSAuditEntity.class,params);
    List<TSAuditEntity> listTSAuditEntitys = importResult.getList();
    StringBuilder stringBuilder = new StringBuilder("已存在的数据:");
    boolean flag = false;
    if(!importResult.isVerfiyFail()) {
      for (TSAuditEntity tSAudit : listTSAuditEntitys) {
      //以下检查导入数据是否重复的语句可视需求启用
        //Long count = tSAuditService.getCountForJdbc("这里放检查数据是否重复的SQL语句");
        //if(count >0) {
          //flag = true;
          //stringBuilder.append(tSAudit.getId()+",");
        //} else {
          tSAuditService.save(tSAudit);
        //}
      }
      j.setMsg("文件导入成功！");
      //j.setMsg("文件导入成功！" + (flag? stringBuilder.toString():""));
    }else {
      String excelPath = "/upload/excelUpload/TSAuditEntity/"+importResult.getExcelName();
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
	 * 加载明细列表[多级审核分级信息]
	 *
	 * @return
	 */
	@RequestMapping(params = "tSAuditLeaveList")
	public ModelAndView tSAuditLeaveList(TSAuditEntity tSAudit, HttpServletRequest req) {

		//===================================================================================
		//获取参数
		Object id0 = tSAudit.getId();
		//===================================================================================
		//查询-多级审核分级信息
		List<TSAuditLeaveEntity> tSAuditLeaveEntityList = new ArrayList<TSAuditLeaveEntity>();
		if(StringUtils.isNotEmpty(id0.toString())) {
			String hql0 = "from TSAuditLeaveEntity where 1 = 1 AND pID = ? ";
			try {
				tSAuditLeaveEntityList = systemService.findHql(hql0, id0);
				req.setAttribute("tSAuditLeaveList", tSAuditLeaveEntityList);
			} catch (Exception e) {
				logger.info(e.getMessage());
			}
		}else{
			req.setAttribute("tSAuditLeaveList", tSAuditLeaveEntityList);
		}
		return new ModelAndView("com/qihang/buss/sysaudit/tSAuditLeaveList");
	}

	@RequestMapping(params = "loadUserInfo")
	@ResponseBody
	public List<Map<String,Object>> loadUserInfo(){
		List<Map<String,Object>> userInfo = new ArrayList<Map<String, Object>>();
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		Set<String> sonIds = systemService.getAllSonId(depart.getId());
		String sonIdStr = "";
		for(String sonId : sonIds){
			sonIdStr += "'"+sonId+"',";
		}
		sonIdStr = sonIdStr.substring(0,sonIdStr.length()-1);
		List<TSUserOrg> userOrgList = systemService.findHql("from TSUserOrg where (tsDepart.id in("+sonIdStr+") and tsDepart.orgType = 3) or (tsDepart.id = ?)",new Object[]{depart.getId()});
		//List<TSUser> users = systemService.findHql("from TSUser where status=1");
		for(TSUserOrg userOrg : userOrgList){
			try {
				TSUser user = userOrg.getTsUser();
				String roleCode = userService.getUserRole(user);
				if(roleCode.indexOf("sc_allowAudit") > -1) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("id", user.getId());
					map.put("text", user.getRealName());
					userInfo.add(map);
				}
			}catch (Exception e){
				continue;
			}
		}
		return userInfo;
	}

	@RequestMapping(params = "checkIsAudit")
	@ResponseBody
	public AjaxJson checkIsAudit(String tranType,String billId,String load,HttpServletRequest request,HttpSession session){
		AjaxJson j = new AjaxJson();
		TSUser user = ResourceUtil.getSessionUserName();
		String roleCode = userService.getUserRole(user);
		//获取当前用户所在分支机构
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		j = tSAuditService.checkIsAudit(tranType,user.getId(),billId,roleCode,depart.getId());
		return j;
	}

	/**
	 * 审核提交
	 *
	 */
	@RequestMapping(params = "auditSub")
	@ResponseBody
	public AjaxJson auditSub(TSAuditRelationEntity entity,String entityName){
		TSUser user = ResourceUtil.getSessionUserName();
		//获取当前用户所在分支机构
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		AjaxJson j = tSAuditService.saveAuditInfo(entity, user, entityName,depart.getId());
		return j;
	}

	/**
	 *判断是否可编辑
	 */
	@RequestMapping(params = "checkIsEdit")
	@ResponseBody
	public AjaxJson checkIsEdit(String billId,String tranType){
		TSUser user = ResourceUtil.getSessionUserName();
		AjaxJson j = tSAuditService.checkIsEdit(billId, tranType, user.getId());
		return j;
	}

	/**
	 * 反审核功能
	 *
	 */
	@RequestMapping(params = "unAudit")
	@ResponseBody
	public AjaxJson unAudit(TSAuditRelationEntity entity,String entityName){
		String userId = ResourceUtil.getSessionUserName().getId();
		//获取当前用户所在分支机构
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		AjaxJson j = tSAuditService.unAudit(entity, userId, entityName,depart.getId());
		return j;
	}

	@RequestMapping(params = "goAudit")
	public ModelAndView goAudit(String billId,String tranType,String billNo,String isSub,String billDate,String entityName) throws ParseException {
		ModelAndView view = new ModelAndView("com/qihang/buss/sysaudit/auditInfo");
		view.addObject("billId",billId);
		view.addObject("tranType",tranType);
		view.addObject("billNo",billNo);
		view.addObject("entityName",entityName);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(StringUtils.isNotEmpty(billDate)) {
			view.addObject("billDate", sdf.parse(billDate));
		}
		String url = "";
		if("1".equals(isSub)){
			url = "tSAuditController.do?auditSub&entityName="+entityName;
		}else{
			url = "tSAuditController.do?unAudit&entityName="+entityName;
		}
		view.addObject("url", url);
		view.addObject("isSub", isSub);
		TSAuditRelationEntity auditInfo = new TSAuditRelationEntity();
		auditInfo.setBillId(billId);
		auditInfo.setTranType(tranType);
		view.addObject("auditInfo", auditInfo);
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		List<TSAuditEntity> tsAuditEntityList = tSAuditService.findHql("from TSAuditEntity where billId=? and sonId = ?",new Object[]{tranType,depart.getId()});
		if(tsAuditEntityList.size() > 0) {
			TSAuditEntity auditEntity = tsAuditEntityList.get(0);
			if (null != auditEntity) {
				if (null != auditEntity.getIsSort()) {
					view.addObject("isSort", 1);
				} else {
					view.addObject("isSort", 0);
				}
			}
		}
		return view;
	}

	@RequestMapping(params = "auditInfoList")
	@ResponseBody
	public List<TSAuditRelationEntity> auditInfoList(String billId,String tranType,HttpServletRequest req){
		//查询-多级审核审核内容
		List<TSAuditRelationEntity> relationEntityList = new ArrayList<TSAuditRelationEntity>();
		String hql0 = "from TSAuditRelationEntity where billId = ? and tranType = ? and status > 0 order by orderNum desc";
		try {
			relationEntityList = systemService.findHql(hql0,new Object[]{billId,tranType});
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		//查询单据类型数据
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		List<TSAuditEntity> tsAuditEntityList = tSAuditService.findHql("from TSAuditEntity where billId=? and sonId = ?", new Object[]{tranType, depart.getId()});
		List<TSAuditRelationEntity> result = new ArrayList<TSAuditRelationEntity>();
		if(tsAuditEntityList.size() > 0) {
			TSAuditEntity auditEntity = tsAuditEntityList.get(0);
			//if (null != auditEntity) {
				//查询审核级数数据
				List<TSAuditLeaveEntity> leaveEntityList = systemService.findHql("from TSAuditLeaveEntity where pid = ? order by leaveNum", new Object[]{auditEntity.getId()});
				for (TSAuditLeaveEntity leaveEntity : leaveEntityList) {
					String leaveNum = leaveEntity.getLeaveNum();
					Boolean isFinish = false;
					//判断是否审核完成
					if (relationEntityList.size() > 0 && 1 == relationEntityList.get(0).getIsFinish()) {
						isFinish = true;
					}
					for (int i = relationEntityList.size() - 1; i >= 0; i--) {
						TSAuditRelationEntity relationEntity = relationEntityList.get(i);
						if (isFinish) {
							//if (0 == relationEntity.getDeleted()) {
							Integer status = relationEntity.getStatus();
							if (Integer.parseInt(leaveNum) <= relationEntityList.get(0).getStatus()) {
								if (status.toString().equals(leaveNum)) {
									result.add(relationEntity);
									relationEntityList.remove(i);
									break;
								} else {
									TSAuditRelationEntity temp = new TSAuditRelationEntity();
									temp.setStatus(Integer.parseInt(leaveNum));
									temp.setDeleted(2);
									result.add(temp);
									break;
								}
							}
							//}
						} else {
							Integer status = relationEntity.getStatus();
							if (Integer.parseInt(leaveNum) <= relationEntityList.get(0).getStatus()) {
								if (status.toString().equals(leaveNum)) {
									result.add(relationEntity);
									relationEntityList.remove(i);
									break;
								} else {
									TSAuditRelationEntity temp = new TSAuditRelationEntity();
									temp.setStatus(Integer.parseInt(leaveNum));
									temp.setDeleted(2);
									result.add(temp);
									break;
								}
							}
						}
					}
				}
			//}
		}
		return result;
	}

	/**
	 * 审核级数下拉数据获取
	 * @param billId
	 * @param tranType
	 * @param isSub
	 * @return
	 */
	@RequestMapping(params = "loadStatus")
	@ResponseBody
	public List<Map<String,Object>> loadStatus(String billId,String tranType,String isSub){
		String userId = ResourceUtil.getSessionUserName().getId();
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		List<Map<String,Object>> leaveAudit = tSAuditService.getLeaveInfo(billId, tranType, userId,isSub,depart.getId());
		return leaveAudit;
	}

	/**
	 * 判断是否多级审核
	 * @param tranType
	 * @return
	 */
	@RequestMapping(params = "checkIsMore")
	@ResponseBody
	public AjaxJson checkIsMore(String tranType){
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		AjaxJson j = tSAuditService.checkIsMore(tranType,depart.getId());
		return j;
	}

	/**
	 * 快速审核功能
	 */
	@RequestMapping(params = "auditBill")
	@ResponseBody
	public AjaxJson auditBill(String tranType,String billId,String entityName){
		TSUser user = ResourceUtil.getSessionUserName();
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		AjaxJson j = tSAuditService.auditBill(tranType, billId, user.getId(), user.getRealName(), entityName,depart.getId());
		return j;
	}

	/**
	 * 快速反审核功能
	 */
	@RequestMapping(params = "unAuditBill")
	@ResponseBody
	public AjaxJson unAuditBill(String tranType,String billId,String entityName){
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		AjaxJson j = tSAuditService.unAuditBill(tranType, billId, entityName,depart.getId());
		return j;
	}


	/**
	 * 审核前负库存校验
	 * @param id 单据id
	 * @param tranType 单据类型
	 * @param tableName 明细表名
	 * @return
	 */
	@RequestMapping(params = "checkIsNegative")
	@ResponseBody
	public AjaxJson checkIsNegative(String id,String tranType,String tableName,String parentId){
		AjaxJson j = tSAuditService.checkIsNegative(id,tranType,tableName,parentId);
		return j;
	}
}
