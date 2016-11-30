package com.qihang.buss.sc.financing.controller;
import com.qihang.buss.sc.baseinfo.entity.TScDepartmentEntity;
import com.qihang.buss.sc.baseinfo.entity.TScEmpEntity;
import com.qihang.buss.sc.baseinfo.entity.TScSettleacctEntity;
import com.qihang.buss.sc.baseinfo.entity.TScSoncompanyEntity;
import com.qihang.buss.sc.baseinfo.service.*;
import com.qihang.buss.sc.financing.entity.TScRpBankbillEntity;
import com.qihang.buss.sc.financing.page.TScRpBankbillPage;
import com.qihang.buss.sc.financing.service.TScRpBankbillServiceI;

import java.util.*;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qihang.buss.sc.sysaudit.entity.TSAuditRelationEntity;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
import com.qihang.buss.sc.util.AccountUtil;
import com.qihang.buss.sc.util.BillNoGenerate;
import com.qihang.winter.core.util.*;
import com.qihang.winter.tag.vo.datatable.SortDirection;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.pojo.base.TSUser;
import com.qihang.winter.web.system.service.UserService;
import org.apache.commons.httpclient.util.DateUtil;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
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
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.poi.excel.ExcelImportUtil;
import com.qihang.winter.poi.excel.entity.ExportParams;
import com.qihang.winter.poi.excel.entity.ImportParams;
import com.qihang.winter.poi.excel.entity.TemplateExportParams;
import com.qihang.winter.poi.excel.entity.vo.NormalExcelConstants;
import com.qihang.winter.poi.excel.entity.vo.TemplateExcelConstants;

import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;


/**
 * @Title: Controller
 * @Description: 银行存取款
 * @author onlineGenerator
 * @date 2016-08-31 14:42:42
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScRpBankbillController")
public class TScRpBankbillController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TScRpBankbillController.class);

	@Autowired
	private TScRpBankbillServiceI tScRpBankbillService;
	@Autowired
	private SystemService systemService;
	private String message;
	@Autowired
	private TScEmpServiceI tScEmpService;
	@Autowired
	private TScSoncompanyServiceI tScSoncompanyService;
	@Autowired
	private TScSettleacctServiceI tScSettleacctService;
	@Autowired
	private CountCommonServiceI countCommonService;
	@Autowired
	private TScEmpServiceI empServiceI;//经办人
	@Autowired
	private TScDepartmentServiceI departmentServiceI;//部门
	@Autowired
	private SessionFactory sessionFactory;

	@Autowired
	private UserService userService;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	/**
	 * 银行存取款列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "tScRpBankbill")
	public ModelAndView tScRpBankbill(HttpServletRequest request) {
		request.setAttribute("tranType",Globals.SC_RP_BANKBILL_TRANTYPE);
		return new ModelAndView("com/qihang/buss/sc/financing/tScRpBankbillList");
	}

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TScRpBankbillEntity tScRpBankbill,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		//汇总
		dataGrid.setFooter("allAmount");
		CriteriaQuery cq = new CriteriaQuery(TScRpBankbillEntity.class, dataGrid);
		//查询条件组装器
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScRpBankbill, request.getParameterMap());
		cq.addOrder("createDate", SortDirection.desc);
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
		String query_checkDate_begin = request.getParameter("checkDate _begin");
		String query_checkDate_end = request.getParameter("checkDate _end");
		if(StringUtil.isNotEmpty(query_checkDate_begin)){
			cq.ge("checkDate ", new SimpleDateFormat("yyyy-MM-dd").parse(query_checkDate_begin));
		}
		if(StringUtil.isNotEmpty(query_checkDate_end)){
			cq.le("checkDate ", new SimpleDateFormat("yyyy-MM-dd").parse(query_checkDate_end));
		}
			String isWarm = request.getParameter("isWarm");
			if(org.apache.commons.lang.StringUtils.isNotEmpty(isWarm)){
				String userId = ResourceUtil.getSessionUserName().getId();
				TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
				TSDepart depart = systemService.getParentSonInfo(sonInfo);
				Boolean isAudit = userService.isAllowAudit(tScRpBankbill.getTranType().toString(),userId,depart.getId());
				cq.eq("cancellation",0);
				//判断当前用户是否在多级审核队列中
				if(isAudit){
					Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId,depart.getId(),tScRpBankbill.getTranType().toString());
					if(userAuditLeave.size() > 0){
						String leaves = "";
						for(Integer leave : userAuditLeave){
							leaves += leave+",";
						}
						leaves = leaves.substring(0,leaves.length()-1);
						List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in ("+leaves+")",new Object[]{depart.getId(),tScRpBankbill.getTranType().toString()});
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
		this.tScRpBankbillService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除银行存取款
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TScRpBankbillEntity tScRpBankbill, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tScRpBankbill = systemService.getEntity(TScRpBankbillEntity.class, tScRpBankbill.getId());
		message = "银行存取款删除成功";
		try{
			//修改  经办人  引用的次数
			countCommonService.deleteUpdateCount("T_SC_EMP",tScRpBankbill.gettScEmpEntity().getId());
			//修改  部门 引用的次数
			countCommonService.deleteUpdateCount("T_SC_Department",tScRpBankbill.gettScDepartmentEntity().getId());
			//修改 转出账户 引用的次数
			countCommonService.deleteUpdateCount("T_SC_SettleAcct",tScRpBankbill.getPasettleacctEntity().getId());
			//修改 转入账户 引用的次数
			countCommonService.deleteUpdateCount("T_SC_SettleAcct",tScRpBankbill.getRasettleacctEntity().getId());

			tScRpBankbillService.delete(tScRpBankbill);

			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			//删除待审核预警数据
			systemService.delBillAuditStatus(tScRpBankbill.getTranType().toString(), tScRpBankbill.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "银行存取款删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 批量删除银行存取款
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		message = "银行存取款删除成功";
		try{

			for(String id:ids.split(",")){
				TScRpBankbillEntity tScRpBankbill = systemService.getEntity(TScRpBankbillEntity.class, id);
				//修改  经办人  引用的次数
				countCommonService.deleteUpdateCount("T_SC_EMP",tScRpBankbill.gettScEmpEntity().getId());
				//修改  部门 引用的次数
				countCommonService.deleteUpdateCount("T_SC_Department",tScRpBankbill.gettScDepartmentEntity().getId());
				//修改 转出账户 引用的次数
				countCommonService.deleteUpdateCount("T_SC_SettleAcct",tScRpBankbill.getPasettleacctEntity().getId());
				//修改 转入账户 引用的次数
				countCommonService.deleteUpdateCount("T_SC_SettleAcct",tScRpBankbill.getRasettleacctEntity().getId());
				tScRpBankbillService.delete(tScRpBankbill);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "银行存取款删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加银行存取款
	 * 
	 * @param tScRpBankbill
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TScRpBankbillEntity tScRpBankbill, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		try{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			String accountDate = "";
			if(AccountUtil.getAccountStartDate()!=null){
				accountDate = sdf.format(AccountUtil.getAccountStartDate());
			}
			String data = sdf.format(tScRpBankbill.getDate());
			if(accountDate.compareTo(data) > 0) {
				j.setSuccess(false);
				j.setMsg("单据日期不能小于当前账套的当前期");
				return j;
			}
			String empId = request.getParameter("empId");//经办人ID
			String deptId = request.getParameter("deptId");//部门ID
			String scsonId = request.getParameter("scsonId");//转出机构ID
			String paccountId = request.getParameter("paccountId");//转出账户ID
			String dcsonId = request.getParameter("dcsonId");//转入机构ID
			String raccountId = request.getParameter("raccountId");//转入账户ID
			String sonId = request.getParameter("sonId");//分支机构
			if(StringUtil.isEmpty(empId) || StringUtil.isEmpty(deptId) || StringUtil.isEmpty(scsonId) || StringUtil.isEmpty(paccountId)
					|| StringUtil.isEmpty(dcsonId) || StringUtil.isEmpty(raccountId) ){
				j.setSuccess(false);
				j.setMsg("参数错误,请检查是否正确");
				return j;
			}
			if(scsonId.equals(dcsonId)){//判断是否一样机构
				if(paccountId.equals(raccountId)){//如果是相同的机构，则在判断转出账户和转入账户是否相同
					j.setSuccess(false);
					j.setMsg("转入机构和转出机构相同时,转出账户和转入账户不能相同!");
					return j;
				}
			}
			//经办人
			TScEmpEntity tScEmpEntity = systemService.get(TScEmpEntity.class,empId);
			tScRpBankbill.settScEmpEntity(tScEmpEntity);
			//部门
			TScDepartmentEntity tScDepartmentEntity = new TScDepartmentEntity();
			tScDepartmentEntity.setId(deptId);
			tScRpBankbill.settScDepartmentEntity(tScDepartmentEntity);
			//转出机构
			TSDepart dcsoncompanyEntity = systemService.get(TSDepart.class,scsonId);
			//dcsoncompanyEntity.setId(scsonId);
			tScRpBankbill.setDcsoncompanyEntity(dcsoncompanyEntity);
			//转出账户
			TScSettleacctEntity pasettleacctEntity = new TScSettleacctEntity();
			pasettleacctEntity.setId(paccountId);
			tScRpBankbill.setPasettleacctEntity(pasettleacctEntity);
			//转入机构ID
			TSDepart scsoncompanyEntity = systemService.get(TSDepart.class,dcsonId);
			tScRpBankbill.setScsoncompanyEntity(scsoncompanyEntity);
			//转入账户ID
			TScSettleacctEntity rasettleacctEntity = new TScSettleacctEntity();
			rasettleacctEntity.setId(raccountId);
			tScRpBankbill.setRasettleacctEntity(rasettleacctEntity);
			//当前用户信息
			TSUser loginUser = ResourceUtil.getSessionUserName();
			//分支机构
			TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
			TSDepart depart = systemService.getParentSonInfo(sonInfo);
			tScRpBankbill.setTsDepart(depart);

			tScRpBankbill.setBillerUser(loginUser);
			tScRpBankbill.setCheckState(0);
			tScRpBankbill.setCancellation(0);
			tScRpBankbill.setTranType(Globals.SC_RP_BANKBILL_TRANTYPE);
			tScRpBankbill.setVersion(0);
			message = "银行存取款添加成功";
			String load = request.getParameter("load");
			if("fcopy".equals(load)){
				tScRpBankbill.setBillNo(BillNoGenerate.getBillNo(tScRpBankbill.getTranType()+""));
			}
			tScRpBankbillService.save(tScRpBankbill);
			//修改  经办人  引用的次数
			countCommonService.addUpdateCount("T_SC_EMP",empId);
			//修改  部门 引用的次数
			countCommonService.addUpdateCount("T_SC_Department",deptId);
			//修改 转出账户 引用的次数
			countCommonService.addUpdateCount("T_SC_SettleAcct",paccountId);
			//修改 转入账户 引用的次数
			countCommonService.addUpdateCount("T_SC_SettleAcct",raccountId);

			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
			//待审核数据提醒操作
			systemService.saveBillAuditStatus(tScRpBankbill.getTranType().toString(), tScRpBankbill.getId());
		}catch(Exception e){
			e.printStackTrace();
			message = "银行存取款添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 更新银行存取款
	 * 
	 * @param tScRpBankbill
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TScRpBankbillEntity tScRpBankbill, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		TScRpBankbillEntity t = tScRpBankbillService.get(TScRpBankbillEntity.class, tScRpBankbill.getId());
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			String accountDate = "";
			if(AccountUtil.getAccountStartDate()!=null){
				accountDate = sdf.format(AccountUtil.getAccountStartDate());
			}
			String data = sdf.format(tScRpBankbill.getDate());
			if(accountDate.compareTo(data) > 0) {
				j.setSuccess(false);
				j.setMsg("单据日期不能小于当前账套的当前期");
				return j;
			}
			String empId = request.getParameter("empId");//经办人ID
			String deptId = request.getParameter("deptId");//部门ID
			String scsonId = request.getParameter("scsonId");//转出机构ID
			String paccountId = request.getParameter("paccountId");//转出账户ID
			String dcsonId = request.getParameter("dcsonId");//转入机构ID
			String raccountId = request.getParameter("raccountId");//转入账户ID
			if(StringUtil.isEmpty(empId) || StringUtil.isEmpty(deptId) || StringUtil.isEmpty(scsonId) || StringUtil.isEmpty(paccountId)
					|| StringUtil.isEmpty(dcsonId) || StringUtil.isEmpty(raccountId) ){
				j.setSuccess(false);
				j.setMsg("参数错误,请检查是否正确");
				return j;
			}
			if(scsonId.equals(dcsonId)){//判断是否一样机构
				if(paccountId.equals(raccountId)){//如果是相同的机构，则在判断转出账户和转入账户是否相同
					j.setSuccess(false);
					j.setMsg("转入机构和转出机构相同时,转出账户和转入账户不能相同!");
					return j;
				}
			}
			//修改  经办人  引用的次数
			countCommonService.editUpdateCount("T_SC_EMP",t.gettScEmpEntity().getId(),empId);
			//修改  部门 引用的次数
			countCommonService.editUpdateCount("T_SC_Department",t.gettScDepartmentEntity().getId(),deptId);
			//修改 转出账户 引用的次数
			countCommonService.editUpdateCount("T_SC_SettleAcct",t.getPasettleacctEntity().getId(),paccountId);
			//修改 转入账户 引用的次数
			countCommonService.editUpdateCount("T_SC_SettleAcct",t.getRasettleacctEntity().getId(),raccountId);
			//经办人
			TScEmpEntity tScEmpEntity = systemService.get(TScEmpEntity.class,empId);
			t.settScEmpEntity(tScEmpEntity);
			//部门
			TScDepartmentEntity tScDepartmentEntity = new TScDepartmentEntity();
			tScDepartmentEntity.setId(deptId);
			t.settScDepartmentEntity(tScDepartmentEntity);
			//转出机构
			TSDepart dcsoncompanyEntity = new TSDepart();
			dcsoncompanyEntity.setId(scsonId);
			t.setDcsoncompanyEntity(dcsoncompanyEntity);
			//转出账户
			TScSettleacctEntity pasettleacctEntity = new TScSettleacctEntity();
			pasettleacctEntity.setId(paccountId);
			t.setPasettleacctEntity(pasettleacctEntity);
			//转入机构ID
			TSDepart scsoncompanyEntity = new TSDepart();
			scsoncompanyEntity.setId(dcsonId);
			t.setScsoncompanyEntity(scsoncompanyEntity);
			//转入账户ID
			TScSettleacctEntity rasettleacctEntity = new TScSettleacctEntity();
			rasettleacctEntity.setId(raccountId);
			t.setRasettleacctEntity(rasettleacctEntity);

			MyBeanUtils.copyBeanNotNull2Bean(tScRpBankbill, t);
			tScRpBankbillService.saveOrUpdate(t);
			message = "银行存取款更新成功";
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		} catch (Exception e) {
			e.printStackTrace();
			message = "银行存取款更新失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 银行存取款新增页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TScRpBankbillEntity tScRpBankbill, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tScRpBankbill.getId())) {
			tScRpBankbill = tScRpBankbillService.getEntity(TScRpBankbillEntity.class, tScRpBankbill.getId());
			req.setAttribute("tScRpBankbillPage", tScRpBankbill);
		}
		TSUser loginUser = ResourceUtil.getSessionUserName();
		List<TScSoncompanyEntity> listSon = tScSoncompanyService.loadAll(TScSoncompanyEntity.class);
		String hql0 = "from TScSettleacctEntity where 1 = 1 AND disabled = ?";
		List<TScSettleacctEntity> listSet = tScSettleacctService.findHql(hql0,0);
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("billNo", BillNoGenerate.getBillNo(String.valueOf(Globals.SC_RP_BANKBILL_TRANTYPE)));
		map.put("tranType",Globals.SC_RP_BANKBILL_TRANTYPE);
		map.put("billerName",loginUser.getRealName());
		map.put("billerId",loginUser.getId());
		map.put("listSon",listSon);
		map.put("listSet",listSet);
		map.put("date", DateUtil.formatDate(new Date(),"yyyy-MM-dd"));

		//获取分支机构
		TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
		TSDepart depart = systemService.getParentSonInfo(sonInfo);
		req.setAttribute("sonId",depart.getId());
		req.setAttribute("sonName",depart.getDepartname());
		return new ModelAndView("com/qihang/buss/sc/financing/tScRpBankbill-add").addAllObjects(map);
	}
	/**
	 * 银行存取款编辑页面跳转
	 *
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TScRpBankbillEntity tScRpBankbill, HttpServletRequest req) {
		Map<String,Object> map = new HashMap<String, Object>();
		if (StringUtil.isNotEmpty(tScRpBankbill.getId())) {
			tScRpBankbill = tScRpBankbillService.getEntity(TScRpBankbillEntity.class, tScRpBankbill.getId());
			req.setAttribute("tScRpBankbillPage", tScRpBankbill);
			List<TScSoncompanyEntity> listSon = tScSoncompanyService.loadAll(TScSoncompanyEntity.class);
			String hql0 = "from TScSettleacctEntity where 1 = 1 AND disabled = ?";
			List<TScSettleacctEntity> listSet = tScSettleacctService.findHql(hql0,0);
			map.put("listSon",listSon);
			map.put("listSet",listSet);

			//审核人和审核日期
			List<TSAuditRelationEntity> info = systemService.getAuditInfoList(tScRpBankbill.getId(), String.valueOf(tScRpBankbill.getTranType()));
			String auditor = "";
			String auditDate = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
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
			map.put("auditor",auditor);
			map.put("auditDate",auditDate);
		}
		return new ModelAndView("com/qihang/buss/sc/financing/tScRpBankbill-update").addAllObjects(map);
	}

	/**
	 * 导入功能跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name","tScRpBankbillController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}
	
	/**
	 * 导出excel
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXls")
	public String exportXls(TScRpBankbillEntity tScRpBankbill,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		CriteriaQuery cq = new CriteriaQuery(TScRpBankbillEntity.class, dataGrid);
		com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScRpBankbill, request.getParameterMap());
		List<TScRpBankbillEntity> tScRpBankbills = this.tScRpBankbillService.getListByCriteriaQuery(cq,false);
		List<TScRpBankbillPage> pageList = new ArrayList<TScRpBankbillPage>();
		for(TScRpBankbillEntity e : tScRpBankbills) {
			TScRpBankbillPage page = new TScRpBankbillPage();
			page.setDate(e.getDate());
			page.setBillNo(e.getBillNo());
			page.setEmpName(e.gettScEmpEntity()==null?"":e.gettScEmpEntity().getName());
			page.setDeptName(e.gettScDepartmentEntity()==null?"":e.gettScDepartmentEntity().getName());
			page.setScsonName(e.getDcsoncompanyEntity()==null?"":e.getDcsoncompanyEntity().getDepartname());
			page.setPaccountName(e.getPasettleacctEntity()==null?"":e.getPasettleacctEntity().getName());
			page.setDcsonName(e.getScsoncompanyEntity()==null?"":e.getScsoncompanyEntity().getDepartname());
			page.setRaccountName(e.getRasettleacctEntity()==null?"":e.getRasettleacctEntity().getName());
			page.setAllAmount(e.getAllAmount());
			page.setCheckerName(e.getCheckerUser()==null?"":e.getCheckerUser().getUserName());
			page.setBillerName(e.getBillerUser()==null?"":e.getBillerUser().getUserName());
			page.setCheckDate(e.getCheckDate());
			if(e.getCheckState() == 0){
				page.setCheckStateName("未审核");
			}
			if(e.getCheckState() == 1){
				page.setCheckStateName("审核中");
			}
			if(e.getCheckState() == 2){
				page.setCheckStateName("已审核");
			}
			if(e.getCancellation() == 0){
				page.setCancellationName("未作废");
			}
			if(e.getCancellation() == 1){
				page.setCancellationName("已作废");
			}
			page.setExplanation(e.getExplanation());
			page.setSonName(e.getTsDepart()==null?"":e.getTsDepart().getDepartname());
			pageList.add(page);
		}
		modelMap.put(NormalExcelConstants.FILE_NAME,"银行存取款");
		modelMap.put(NormalExcelConstants.CLASS,TScRpBankbillPage.class);
		modelMap.put(NormalExcelConstants.PARAMS,new ExportParams("银行存取款列表", "导出人:"+ResourceUtil.getSessionUserName().getRealName(), "导出信息"));
		modelMap.put(NormalExcelConstants.DATA_LIST,pageList);
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	 * 导出excel 使模板
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(TScRpBankbillEntity tScRpBankbill,HttpServletRequest request,HttpServletResponse response
			, DataGrid dataGrid,ModelMap modelMap) {
		modelMap.put(TemplateExcelConstants.FILE_NAME, "银行存取款");
		modelMap.put(TemplateExcelConstants.PARAMS,new TemplateExportParams("Excel模板地址"));
		modelMap.put(TemplateExcelConstants.MAP_DATA,null);
		modelMap.put(TemplateExcelConstants.CLASS,TScRpBankbillEntity.class);
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
				List<TScRpBankbillEntity> listTScRpBankbillEntitys = ExcelImportUtil.importExcel(file.getInputStream(),TScRpBankbillEntity.class,params);
				for (TScRpBankbillEntity tScRpBankbill : listTScRpBankbillEntitys) {
					tScRpBankbillService.save(tScRpBankbill);
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
