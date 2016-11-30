package com.qihang.buss.sc.init.controller;

import com.qihang.buss.sc.baseinfo.entity.*;
import com.qihang.buss.sc.baseinfo.service.*;
import com.qihang.buss.sc.init.entity.TScBegdataEntity;
import com.qihang.buss.sc.init.entity.TScViewBegdataEntity;
import com.qihang.buss.sc.init.entity.TScViewBegdataincomepayEntity;
import com.qihang.buss.sc.init.entity.TScViewBegdatapayableEntity;
import com.qihang.buss.sc.init.service.TScBegdataServiceI;
import com.qihang.buss.sc.sales.entity.TScCountEntity;
import com.qihang.buss.sc.sysaudit.entity.TScBillAuditStatusEntity;
import com.qihang.buss.sc.util.AccountUtil;
import com.qihang.buss.sc.util.BillNoGenerate;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.common.service.CommonService;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.*;
import com.qihang.winter.poi.excel.ExcelImportUtil;
import com.qihang.winter.poi.excel.entity.ExportParams;
import com.qihang.winter.poi.excel.entity.ImportParams;
import com.qihang.winter.poi.excel.entity.TemplateExportParams;
import com.qihang.winter.poi.excel.entity.vo.NormalExcelConstants;
import com.qihang.winter.poi.excel.entity.vo.TemplateExcelConstants;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.pojo.base.TSUser;
import com.qihang.winter.web.system.service.SystemService;
import com.qihang.winter.web.system.service.UserService;
import org.apache.commons.lang.StringUtils;
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
import java.util.*;

import static com.qihang.winter.core.constant.Globals.SC_IC_BEGDATAINCOMEPAY_TRANTYPE;


/**
 * @author onlineGenerator
 * @version V1.0
 * @Title: Controller
 * @Description: 应收, 应付，收支 初始化
 * @date 2016-08-19 14:42:49
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScBegdataController")
public class TScBegdataController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(TScBegdataController.class);
    @Autowired
    private CountCommonServiceI countCommonService;
    @Autowired
    private TScBegdataServiceI tScBegdataService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private CommonService commonService;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Autowired
    private TScOrganizationServiceI organizationServiceI;//客户
    @Autowired
    private TScSupplierServiceI supplierServiceI;//供应商
    @Autowired
    private TScEmpServiceI empServiceI;//经办人
    @Autowired
    private TScDepartmentServiceI departmentServiceI;//部门
    @Autowired
    private TScSettleacctServiceI settleacctServiceI;//结算账户
    @Autowired
    private UserService userService;


    /**
     * 应收,应付，收支 初始化列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "tScBegdata")
    public ModelAndView tScBegdata(HttpServletRequest request) {
        String tranType = request.getParameter("tranType");
        request.setAttribute("isAccountOpen", AccountUtil.isAccountOpen());
        request.setAttribute("tranType", tranType);//应收初始化单据类型
//        switch (Integer.valueOf(tranType)){
//            case 1030:
//                return  new ModelAndView("com/qihang/buss/sc/init/tScBegdataList");
//            case 1031:
//                return  new ModelAndView("com/qihang/buss/sc/init/tScBegdatapayableList");
//            case 1032:
//                return  new ModelAndView("com/qihang/buss/sc/init/tScBegdataincomepayList");
//        }
        if(tranType.equals(String.valueOf(Globals.SC_IC_BEGDATA_TRANTYPE))){//应收
            return  new ModelAndView("com/qihang/buss/sc/init/tScBegdataList");
        }
        if (tranType.equals(String.valueOf(Globals.SC_IC_BEGDATAPAYABLE_TRANTYPE))){//应付
            return  new ModelAndView("com/qihang/buss/sc/init/tScBegdatapayableList");
        }else {
            return  new ModelAndView("com/qihang/buss/sc/init/tScBegdataincomepayList");
        }
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(TScBegdataEntity tScBegdata, HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        dataGrid.setFooter("dallAmount,dcheckAmount,dunCheckAmount");
        CriteriaQuery cq = new CriteriaQuery(TScBegdataEntity.class, dataGrid);
        //查询条件组装器
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScBegdata, request.getParameterMap());
        try {
            //自定义追加查询条件
            String isWarm = request.getParameter("isWarm");
            if(org.apache.commons.lang.StringUtils.isNotEmpty(isWarm)){
                String userId = ResourceUtil.getSessionUserName().getId();
                TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
                TSDepart depart = systemService.getParentSonInfo(sonInfo);
                Boolean isAudit = userService.isAllowAudit(tScBegdata.getTranType().toString(),userId,depart.getId());
                //判断当前用户是否在多级审核队列中
                if(isAudit){
                    Set<Integer> userAuditLeave = systemService.getUserAuditLeave(userId,depart.getId(),tScBegdata.getTranType().toString());
                    if(userAuditLeave.size() > 0){
                        String leaves = "";
                        for(Integer leave : userAuditLeave){
                            leaves += leave+",";
                        }
                        leaves = leaves.substring(0,leaves.length()-1);
                        List<TScBillAuditStatusEntity> billInfo = systemService.findHql("from TScBillAuditStatusEntity where sonId=? and tranType=? and status in ("+leaves+")",new Object[]{depart.getId(),tScBegdata.getTranType().toString()});
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
        } catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.tScBegdataService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除应收初始化
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public AjaxJson doDel(TScBegdataEntity tScBegdata, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        tScBegdata = systemService.getEntity(TScBegdataEntity.class, tScBegdata.getId());
        message = "应收初始化删除成功";
        try {
            if(tScBegdata.getCheckState() == 2) {
                message = "该信息已审核，不能删除";
                j.setSuccess(true);
                j.setMsg(message);
                return j;
            }
            String tranType = String.valueOf(tScBegdata.getTranType());
            if(tranType.equals(String.valueOf(Globals.SC_IC_BEGDATA_TRANTYPE))) {
                //客户
                if (StringUtils.isNotEmpty(tScBegdata.getItemID())) {
                    TScCountEntity countEntity = new TScCountEntity();
                    countEntity.setType(Globals.COUNT_DEL_TYPE);
                    countEntity.setOldId(tScBegdata.getItemID());
                    boolean updateIsSuccess = organizationServiceI.updateOrganizationCount(countEntity);
                    if (updateIsSuccess == false) {
                        message = "更新基础资料客户引用次数失败";
                        j.setMsg(message);
                        return j;
                    }
                }
            }

            if (tranType.equals(String.valueOf(Globals.SC_IC_BEGDATAPAYABLE_TRANTYPE))) {
                //供应商
                if (StringUtils.isNotEmpty(tScBegdata.getItemID())) {
                    TScCountEntity countEntity = new TScCountEntity();
                    countEntity.setType(Globals.COUNT_DEL_TYPE);
                    countEntity.setOldId(tScBegdata.getItemID());
                    boolean updateIsSuccess = supplierServiceI.updateSupplierCount(countEntity);
                    if (updateIsSuccess == false) {
                        message = "更新基础资料供应商引用次数失败";
                        j.setMsg(message);
                        return j;
                    }
                }
            }
            if (tranType.equals(String.valueOf(SC_IC_BEGDATAINCOMEPAY_TRANTYPE))) {
                //结算账户
                if (StringUtils.isNotEmpty(tScBegdata.getItemID())) {
                    TScCountEntity countEntity = new TScCountEntity();
                    countEntity.setType(Globals.COUNT_DEL_TYPE);
                    countEntity.setOldId(tScBegdata.getItemID());
                    boolean updateIsSuccess = settleacctServiceI.updateOrganizationCount(countEntity);
                    if (updateIsSuccess == false) {
                        message = "更新基础资料供应商引用次数失败";
                        j.setMsg(message);
                        return j;
                    }
                }
            }

            //经办人
            if (StringUtils.isNotEmpty(tScBegdata.getEmpID())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_DEL_TYPE);
                countEntity.setOldId(tScBegdata.getEmpID());
                boolean updateIsSuccess = empServiceI.updateEmpCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料职员引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            //部门
            if (StringUtils.isNotEmpty(tScBegdata.getDeptID())) {
                TScCountEntity countEntity = new TScCountEntity();
                countEntity.setType(Globals.COUNT_DEL_TYPE);
                countEntity.setOldId(tScBegdata.getDeptID());
                boolean updateIsSuccess = departmentServiceI.updateDepartmentCount(countEntity);
                if (updateIsSuccess == false) {
                    message = "更新基础资料部门引用次数失败";
                    j.setMsg(message);
                    return j;
                }
            }
            tScBegdataService.delete(tScBegdata);
            systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            //删除待审核预警数据
            systemService.delBillAuditStatus(tScBegdata.getTranType().toString(), tScBegdata.getId());
        } catch (Exception e) {
            e.printStackTrace();
            message = "应收初始化删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 批量删除应收初始化
     *
     * @return
     */
    @RequestMapping(params = "doBatchDel")
    @ResponseBody
    public AjaxJson doBatchDel(String ids, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "应收初始化删除成功";
        try {
            for (String id : ids.split(",")) {
                TScBegdataEntity tScBegdata = systemService.getEntity(TScBegdataEntity.class,
                        id
                );
                tScBegdataService.delete(tScBegdata);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
            message = "应收初始化删除失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 添加应收初始化
     *
     * @param tScBegdata
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public AjaxJson doAdd(TScBegdataEntity tScBegdata, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "应收初始化添加成功";
        try {
            if (AccountUtil.isAccountOpen()) {//如果 系统已经开帐，则抛出异常，不允许添加应收初始化单据
                throw new BusinessException("系统已经开帐，不允许添加应收初始化单据");
            }
//			tScBegdata.setTranType(Integer.valueOf(Globals.SC_IC_BEGDATA_TRANTYPE + ""));//单据类型
            tScBegdata.setBillerID(ResourceUtil.getSessionUserName().getId());//制单人
            tScBegdata.setSonID(ResourceUtil.getSessionUserName().getCurrentDepart().getId());//分支机构
            tScBegdata.setCheckState(Globals.AUDIT_NO);//审核标记-未审核
            tScBegdata.setUnCheckAmount(tScBegdata.getAllAmount());//新增时未收金额等于总金额
            tScBegdataService.save(tScBegdata);
            //更新相关基础资料引用
            TScBegdataEntity oldEntity = systemService.getEntity(TScBegdataEntity.class, tScBegdata.getId());

            String tranType = String.valueOf(tScBegdata.getTranType());
            if(tranType.equals(String.valueOf(Globals.SC_IC_BEGDATA_TRANTYPE))){
                //客户次数
                countCommonService.addUpdateCount("T_SC_Organization",tScBegdata.getItemID());
            }
            if (tranType.equals(String.valueOf(Globals.SC_IC_BEGDATAPAYABLE_TRANTYPE))) {
                //供应商次数
                countCommonService.addUpdateCount("T_SC_SUPPLIER",tScBegdata.getItemID());
            }
            if(tranType.equals(String.valueOf(SC_IC_BEGDATAINCOMEPAY_TRANTYPE))){
                //结算账户次数
                countCommonService.addUpdateCount("T_SC_SettleAcct",tScBegdata.getItemID());
            }
            //经办人
            if (StringUtils.isNotEmpty(tScBegdata.getEmpID())) {
                countCommonService.addUpdateCount("T_SC_EMP",tScBegdata.getEmpID());
            }
            //部门
            if (StringUtils.isNotEmpty(tScBegdata.getDeptID())) {
                countCommonService.addUpdateCount("T_SC_Department",tScBegdata.getDeptID());
            }
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            //待审核数据提醒操作
            systemService.saveBillAuditStatus(tScBegdata.getTranType().toString(), tScBegdata.getId());
        } catch (Exception e) {
            e.printStackTrace();
            message = "应收初始化添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 更新应收初始化
     *
     * @param tScBegdata
     * @return
     */
    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public AjaxJson doUpdate(TScBegdataEntity tScBegdata, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        message = "应收初始化更新成功";
        TScBegdataEntity t = tScBegdataService.get(TScBegdataEntity.class, tScBegdata.getId());
        try {
            BigDecimal ling = new BigDecimal(0);
            tScBegdata.setUnCheckAmount(tScBegdata.getAllAmount().subtract(t.getCheckAmount()==null?ling:t.getCheckAmount()));
            if (AccountUtil.isAccountOpen()) {//如果 系统已经开帐，则抛出异常，不允许更新应收初始化单据
                throw new BusinessException("系统已经开帐，不允许更新应收初始化单据");
            }
            TScBegdataEntity oldEntity = systemService.getEntity(TScBegdataEntity.class, tScBegdata.getId());
            if (oldEntity.getCheckState() != Globals.AUDIT_NO) {
                throw new BusinessException("单据已审核，不允许更新应收初始化单据");
            }

            String tranType = String.valueOf(tScBegdata.getTranType());
            if(tranType.equals(String.valueOf(Globals.SC_IC_BEGDATA_TRANTYPE))) {
                //客户
                countCommonService.editUpdateCount("T_SC_Organization",t.getItemID(),tScBegdata.getItemID());
            }

            if (tranType.equals(String.valueOf(Globals.SC_IC_BEGDATAPAYABLE_TRANTYPE))) {
                //供应商
                countCommonService.editUpdateCount("T_SC_SUPPLIER",t.getItemID(),tScBegdata.getItemID());
            }
            if (tranType.equals(String.valueOf(SC_IC_BEGDATAINCOMEPAY_TRANTYPE))) {
                //结算账户
                countCommonService.editUpdateCount("T_SC_SettleAcct",t.getItemID(),tScBegdata.getItemID());
            }
            //经办人
            if (StringUtils.isNotEmpty(tScBegdata.getEmpID()) && !oldEntity.getEmpID().equals(tScBegdata.getEmpID())) {
                countCommonService.editUpdateCount("T_SC_EMP",t.getEmpID(),tScBegdata.getEmpID());
            }
            //部门
            if (StringUtils.isNotEmpty(tScBegdata.getDeptID()) && !oldEntity.getDeptID().equals(tScBegdata.getDeptID())) {
                countCommonService.editUpdateCount("T_SC_Department",t.getDeptID(),tScBegdata.getDeptID());
            }
            MyBeanUtils.copyBeanNotNull2Bean(tScBegdata, t);
            tScBegdataService.saveOrUpdate(t);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } catch (Exception e) {
            e.printStackTrace();
            message = "应收初始化更新失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }


    /**
     * 应收初始化新增页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goAdd")
    public ModelAndView goAdd(TScBegdataEntity tScBegdata, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tScBegdata.getId())) {
            tScBegdata = tScBegdataService.getEntity(TScBegdataEntity.class, tScBegdata.getId());
            req.setAttribute("tScBegdataPage", tScBegdata);
        } else {
            //tScBegdata.setTranType(Integer.valueOf(Globals.SC_IC_BEGDATA_TRANTYPE+""));
            String billNo = BillNoGenerate.getBillNo(String.valueOf(tScBegdata.getTranType()));
            req.setAttribute("billNo", billNo);
//			SimpleDateFormat checkDate = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
//			Date date= new Date();
//			String currentTime=checkDate.format(date);
            String checkDate = "2016-06-30";
            req.setAttribute("checkDate", checkDate);
//          //自己另外写的获取上个月最后一天
//			Calendar calendar=Calendar.getInstance();
//			Date accountdate= AccountUtil.getAccountStartDate();//账套启用时间
//			calendar.setTime(accountdate);
//			int year=0;
//			int month=calendar.get(Calendar.MONTH);//上个月月份
//			//设置年月
//			if(month==0){
//				year=calendar.get(Calendar.YEAR)-1;
//				month=12;
//			}else {
//				year=calendar.get(Calendar.YEAR);
//			}
//			//设置天数
//			String temp=year+"-"+month;
//			SimpleDateFormat format=new SimpleDateFormat("yyyy-MM");
//			Date d=null;
//			try {
//				d=format.parse(temp);
//			}catch (ParseException e){
//				e.printStackTrace();
//			}
//			calendar.setTime(d);
//			int day=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
//			String monthStr = "";
//			String dayStr = "";
//			//判断月份和日期是否小于等于9 小于等于则加0 例：09
//			if(month<=9){
//				monthStr = "0"+month;
//			}else{
//				monthStr = month+"";
//			}
//			if(day<=9){
//				dayStr = "0"+day;
//			}else{
//				dayStr = day+"";
//			}
//			String date=year+"-"+monthStr+"-"+dayStr;
//			req.setAttribute("date",date);

			/*默认应收日期为当前时间*/
            SimpleDateFormat RpDate = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            String currentRpDate = RpDate.format(date);
            req.setAttribute("currentRpDate", currentRpDate);

        }
        req.setAttribute("tranType", tScBegdata.getTranType());
        String date = DateUtils.formatDate(AccountUtil.getAccountStartDate());
        try {
            date = DateUtils.formatAddDate(date, "yyyy-MM-dd", -1);//初始化单据的单据日期直接默认为账套启用期间的上一月最后一天的日期，并且不能修改
        } catch (Exception e) {
            e.printStackTrace();
        }
        req.setAttribute("date", date);
        String tranType = String.valueOf(tScBegdata.getTranType());
        TSDepart sonInfo = ResourceUtil.getSessionUserName().getCurrentDepart();
        TSDepart depart = systemService.getParentSonInfo(sonInfo);
        req.setAttribute("sonId",depart.getId());
        req.setAttribute("sonName",depart.getDepartname());
        if(tranType.equals(String.valueOf(Globals.SC_IC_BEGDATA_TRANTYPE))){
            String title = "应收初始化";//"应收初始化";
            req.setAttribute("title", title);
            return new ModelAndView("com/qihang/buss/sc/init/tScBegdata-add");
        }
        if (tranType.equals(String.valueOf(Globals.SC_IC_BEGDATAPAYABLE_TRANTYPE))) {
            String title = "应付初始化";//"应付初始化";
            req.setAttribute("title", title);
            return new ModelAndView("com/qihang/buss/sc/init/tScBegdatapayable-add");
        }else {
            String title = "收支初始化";//"收支初始化";
            req.setAttribute("title", title);
            return new ModelAndView("com/qihang/buss/sc/init/tScBegdataincomepay-add");
        }
    }

    /**
     * 应收初始化编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TScBegdataEntity tScBegdata, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tScBegdata.getId())) {
            tScBegdata = tScBegdataService.getEntity(TScBegdataEntity.class, tScBegdata.getId());
            req.setAttribute("tScBegdataPage", tScBegdata);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String rpDate = sdf.format(new Date());
        req.setAttribute("rpDate",rpDate);
        String tranType = String.valueOf(tScBegdata.getTranType());
        if(tranType.equals(String.valueOf(Globals.SC_IC_BEGDATA_TRANTYPE))){
            //客户
            TScOrganizationEntity organizationEntity = new TScOrganizationEntity();
            if (StringUtils.isNotEmpty(tScBegdata.getItemID())) {
                organizationEntity = systemService.getEntity(TScOrganizationEntity.class, tScBegdata.getItemID());
                if (null != organizationEntity) {
                    tScBegdata.setItemName(organizationEntity.getName());
                }
            }
        }
        if (tranType.equals(String.valueOf(Globals.SC_IC_BEGDATAPAYABLE_TRANTYPE))) {
            //供应商
            TScSupplierEntity supplierEntity=new TScSupplierEntity();
            if(StringUtil.isNotEmpty(tScBegdata.getItemID())){
                supplierEntity=systemService.getEntity(TScSupplierEntity.class,tScBegdata.getItemID());
                if (null!=supplierEntity){
                    tScBegdata.setItemName(supplierEntity.getName());
                }
            }
        }
        if(tranType.equals(String.valueOf(SC_IC_BEGDATAINCOMEPAY_TRANTYPE))){
            //结算账户
            TScSettleacctEntity settleacctEntity=new TScSettleacctEntity();
            if(StringUtil.isNotEmpty(tScBegdata.getItemID())){
                settleacctEntity=systemService.getEntity(TScSettleacctEntity.class,tScBegdata.getItemID());
                if (null!=settleacctEntity){
                    tScBegdata.setItemName(settleacctEntity.getName());
                }
            }
        }

        //经办人
        TScEmpEntity empEntity = new TScEmpEntity();
        if (StringUtils.isNotEmpty(tScBegdata.getEmpID())) {
            empEntity = systemService.getEntity(TScEmpEntity.class, tScBegdata.getEmpID());
            if (null != empEntity) {
                tScBegdata.setEmpName(empEntity.getName());
            }
        }
        //部门
        TScDepartmentEntity departmentEntity = new TScDepartmentEntity();
        if (StringUtils.isNotEmpty(tScBegdata.getDeptID())) {
            departmentEntity = systemService.getEntity(TScDepartmentEntity.class, tScBegdata.getDeptID());
            if (null != departmentEntity) {
                tScBegdata.setDeptName(departmentEntity.getName());
            }
        }
        //审核人
        if (StringUtils.isNotEmpty(tScBegdata.getCheckerID())) {
            TSUser user = systemService.getEntity(TSUser.class, tScBegdata.getCheckerID());
            if (null != user) {
                tScBegdata.setCheckUserName(user.getRealName());
            }
        }
        //制单人
        if (StringUtils.isNotEmpty(tScBegdata.getBillerID())) {
            TSUser user = systemService.getEntity(TSUser.class, tScBegdata.getBillerID());
            if (null != user) {
                tScBegdata.setBillerName(user.getRealName());
            }
        }
        if(StringUtil.isNotEmpty(tScBegdata.getSonID())){
            TSDepart depart = systemService.getEntity(TSDepart.class,tScBegdata.getSonID());
            if(null != depart){
                req.setAttribute("sonName",depart.getDepartname());
            }
        }
        if(tranType.equals(String.valueOf(Globals.SC_IC_BEGDATA_TRANTYPE))) {
            String title = "应收初始化";//"应收初始化化";
            req.setAttribute("title", title);
            return new ModelAndView("com/qihang/buss/sc/init/tScBegdata-update");
        }
        if (tranType.equals(String.valueOf(Globals.SC_IC_BEGDATAPAYABLE_TRANTYPE))) {
            String title = "应付初始化";//"应付初始化";
            req.setAttribute("title", title);
            return new ModelAndView("com/qihang/buss/sc/init/tScBegdatapayable-update");
        } else {
            String title = "收支初始化";//"收支初始化";
            req.setAttribute("title", title);
            return new ModelAndView("com/qihang/buss/sc/init/tScBegdataincomepay-update");
        }

    }

    /**
     * 导入功能跳转
     *
     * @return
     */
    @RequestMapping(params = "upload")
    public ModelAndView upload(HttpServletRequest req) {
        req.setAttribute("controller_name", "tScBegdataController");
        return new ModelAndView("common/upload/pub_excel_upload");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(TScViewBegdataEntity tScBegdata, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        String tranType = String.valueOf(tScBegdata.getTranType());

        if (tranType.equals(String.valueOf(Globals.SC_IC_BEGDATA_TRANTYPE))) {
             CriteriaQuery cq = new CriteriaQuery(TScViewBegdataEntity.class, dataGrid);
//        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScBegdata, request.getParameterMap());
              List<TScViewBegdataEntity> tScBegdatas = this.tScBegdataService.getListByCriteriaQuery(cq, false);
              modelMap.put(NormalExcelConstants.FILE_NAME, "应收初始化");
              modelMap.put(NormalExcelConstants.CLASS, TScViewBegdataEntity.class);
              modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("应收初始化列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                        "导出信息"));
              modelMap.put(NormalExcelConstants.DATA_LIST, tScBegdatas);
    }



        if(tranType.equals(String.valueOf(Globals.SC_IC_BEGDATAPAYABLE_TRANTYPE))) {
            CriteriaQuery cq = new CriteriaQuery(TScViewBegdatapayableEntity.class, dataGrid);
            List<TScViewBegdatapayableEntity> tScViewBegdatapayable = this.tScBegdataService.getListByCriteriaQuery(cq, false);
            modelMap.put(NormalExcelConstants.FILE_NAME, "应付初始化");
            modelMap.put(NormalExcelConstants.CLASS, TScViewBegdatapayableEntity.class);
            modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("应付初始化列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                    "导出信息"));
            modelMap.put(NormalExcelConstants.DATA_LIST, tScViewBegdatapayable);
        }

        if (tranType.equals(String.valueOf(Globals.SC_IC_BEGDATAINCOMEPAY_TRANTYPE))) {
            CriteriaQuery cq = new CriteriaQuery(TScViewBegdataincomepayEntity.class, dataGrid);
            List<TScViewBegdataincomepayEntity> tScViewBegdatapayabl = this.tScBegdataService.getListByCriteriaQuery(cq, false);
            modelMap.put(NormalExcelConstants.FILE_NAME, "收支初始化");
            modelMap.put(NormalExcelConstants.CLASS, TScViewBegdataincomepayEntity.class);
            modelMap.put(NormalExcelConstants.PARAMS, new ExportParams("收支初始化列表", "导出人:" + ResourceUtil.getSessionUserName().getRealName(),
                    "导出信息"));
            modelMap.put(NormalExcelConstants.DATA_LIST, tScViewBegdatapayabl);
        }
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    /**
     * 导出excel 使模板
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXlsByT")
    public String exportXlsByT(TScBegdataEntity tScBegdata, HttpServletRequest request, HttpServletResponse response
            , DataGrid dataGrid, ModelMap modelMap) {
        modelMap.put(TemplateExcelConstants.FILE_NAME, "应收初始化");
        modelMap.put(TemplateExcelConstants.PARAMS, new TemplateExportParams("Excel模板地址"));
        modelMap.put(TemplateExcelConstants.MAP_DATA, null);
        modelMap.put(TemplateExcelConstants.CLASS, TScBegdataEntity.class);
        modelMap.put(TemplateExcelConstants.LIST_DATA, null);
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
                List<TScBegdataEntity> listTScBegdataEntitys = ExcelImportUtil.importExcel(file.getInputStream(), TScBegdataEntity.class, params);
                for (TScBegdataEntity tScBegdata : listTScBegdataEntitys) {
                    tScBegdataService.save(tScBegdata);
                }
                j.setMsg("文件导入成功！");
            } catch (Exception e) {
                j.setMsg("文件导入失败！");
                logger.error(ExceptionUtil.getExceptionMessage(e));
            } finally {
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
