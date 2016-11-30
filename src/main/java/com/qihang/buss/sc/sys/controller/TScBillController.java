package com.qihang.buss.sc.sys.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qihang.buss.sc.baseinfo.service.*;
import com.qihang.buss.sc.sales.service.TScPoStockbillServiceI;
import com.qihang.buss.sc.util.AccountUtil;
import com.qihang.buss.sc.util.BillNoGenerate;
import com.qihang.winter.core.util.*;
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
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.web.system.pojo.base.TSDepart;
import com.qihang.winter.web.system.service.SystemService;

import java.io.OutputStream;

import com.qihang.winter.poi.excel.ExcelExportUtil;
import com.qihang.winter.poi.excel.ExcelImportUtil;
import com.qihang.winter.poi.excel.entity.ExportParams;
import com.qihang.winter.poi.excel.entity.ImportParams;
import com.qihang.winter.poi.excel.entity.TemplateExportParams;
import com.qihang.winter.poi.excel.entity.vo.NormalExcelConstants;
import com.qihang.winter.poi.excel.entity.vo.TemplateExcelConstants;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.IOException;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import java.util.Map;

/**
 * @Title: Controller
 * @Description: 账套设置
 * @author hjh
 * @date 2016-08-24 09:59:34
 * @version V1.0
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScBillController")
public class TScBillController extends BaseController {
    @Autowired
    private SystemService systemService;
    @Autowired
    private TScPoStockbillServiceI tScPoStockbillService;

    @Autowired
    private TScIcitemServiceI tScIcitemServiceI;//商品

    @Autowired
    private TScSupplierServiceI supplierServiceI;//供应商

    @Autowired
    private TScStockServiceI stockServiceI;

    @Autowired
    private TScEmpServiceI empServiceI;

    @Autowired
    private TScDepartmentServiceI departmentServiceI;

    /**
     * 通过单据类型获取新单据编号、单据日期、制单人姓名、制单人ID、分支机构ID、分支机构名称、未审核状态、未作废状态，创建人、创建人ID、创建时间、更新人、更新人ID、更新时间
     * @param tranType 单据类型
     * @param billDateType 新单据的单据日期的类型，默认是当天，init表示初始化时是用扎帐日期减1天
     * @param req
     * @return
     */
    @RequestMapping(params = "goBillCopy")
    @ResponseBody
    public AjaxJson goBillCopy(String tranType, String billDateType, HttpServletRequest req){
        AjaxJson j = new AjaxJson();
        try {
            String billNo = "";//业务单据的单据编号
            String number = "";//基础资料的编号
            if (tranType.equals(Globals.SC_BASEINFO_ORGANIZATION_TYPE)
                    || tranType.equals(Globals.SC_BASEINFO_SUPPLIER_TYPE)
                    || tranType.equals(Globals.SC_BASEINFO_LOGISTICAL_TYPE)
                    || tranType.equals(Globals.SC_BASEINFO_ICITEM_TYPE)
                    || tranType.equals(Globals.SC_BASEINFO_EMP_TYPE)
                    || tranType.equals(Globals.SC_BASEINFO_DEPARTMENT_TYPE)
                    || tranType.equals(Globals.SC_BASEINFO_STOCK_TYPE)
                    || tranType.equals(Globals.SC_BASEINFO_FEE_TYPE)){//基础资料的相关复制
                String oldnumber = req.getParameter("number");//源基础资料的编号
                String parentNo = (oldnumber==null ) ? "":(oldnumber.lastIndexOf(".")<0 ? oldnumber : oldnumber.substring(0,oldnumber.lastIndexOf(".")));
                number = BillNoGenerate.getBasicInfoBillNo(tranType, parentNo, parentNo);
            }else {//业务单据的相关复制
                billNo = BillNoGenerate.getBillNo(tranType);
            }
            Map<String,String> entity = new HashMap();
            String cancellation = Globals.CLOSE_NO;//作废标记
            String billerId  = ResourceUtil.getSessionUserName().getId();//制单人ID
            String biller = ResourceUtil.getSessionUserName().getRealName();//制单人姓名
            String sonId = ResourceUtil.getSessionUserName().getCurrentDepart().getId();//分支机构
            String checkState = Globals.AUDIT_NO.toString();//审核标记-未审核
            String closed = Globals.CLOSE_NO;//未关闭
            String date = DateUtils.formatDate(new Date());//新单据的单据日期的类型，默认是当天，init表示初始化时是用扎帐日期减1天
            if (billDateType!=null && billDateType.equals("init")) {
                date = DateUtils.formatDate(AccountUtil.getAccountInitStartDate());
                try {
                    date = DateUtils.formatAddDate(date, "yyyy-MM-dd", -1);//初始化单据的单据日期直接默认为账套启用期间的上一月最后一天的日期，并且不能修改
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            String sonName = ResourceUtil.getSessionUserName().getCurrentDepart().getDepartname();
            String billerName = ResourceUtil.getSessionUserName().getRealName();
            String createDate = DateUtils.formatDatetime(new Date());
            //重置为新数据：新单据编号、单据日期、制单人姓名、制单人ID、分支机构ID、分支机构名称、未审核状态、未作废状态，创建人、创建人ID、创建时间、更新人、更新人ID、更新时间
            entity.put("number",number);//基础资料编号
            entity.put("billNo",billNo);//业务单据编号
            entity.put("billNoStr",billNo);
            entity.put("cancellation",cancellation);
            entity.put("billerId",billerId);
            entity.put("sonId",sonId);
            entity.put("checkState",checkState);
            entity.put("closed",closed);
            entity.put("date",date);
            entity.put("sonName",sonName);
            entity.put("billerName",billerName);
            entity.put("createName",billerName);
            entity.put("createBy",billerId);
            entity.put("createDate",createDate);
            entity.put("updateName",billerName);
            entity.put("updateBy",billerId);
            entity.put("updateDate",createDate);
            //清空主表相关数据：表头主表ID,除初始化以外（经办、部门），来源单据相关（classIDSrc、idSrc、billNoSrc）
            entity.put("id","");
            //entity.put("empId","");
            //entity.put("deptId","");
            entity.put("classIDSrc","");
            entity.put("idSrc","");
            entity.put("billNoSrc","");
            //清空子表相关数据：（附表明细）的主表ID、明细表ID，来源单据相关（idSrc、entryIdSrc、idOrder、entryIdOrder、classIdSrc、billNoOrder），属性字段名前加小数点
            entity.put(".id","");
            entity.put(".fid","");
            entity.put(".idSrc","");
            entity.put(".entryIdSrc","");
            entity.put(".idOrder","");
            entity.put(".entryIdOrder","");
            entity.put(".classIdSrc","");
            entity.put(".billNoOrder","");
            entity.put(".createName",billerName);
            entity.put(".createBy",billerId);
            entity.put(".createDate",createDate);
            entity.put(".updateName",billerName);
            entity.put(".updateBy",billerId);
            entity.put(".updateDate",createDate);
            //页脚设置
            entity.put("footdiv:制单人", biller);//制单人姓名
            entity.put("footdiv:审核人", "");
            entity.put("footdiv:审核日期", "");
            j.setObj(entity);
            j.setSuccess(true);
            j.setMsg("单据复制成功");
        } catch (Exception e) {
            j.setSuccess(false);
            j.setMsg("单据复制成功["+e.getMessage()+"],请重试");
            e.printStackTrace();
        }
        return j;
    }
}
