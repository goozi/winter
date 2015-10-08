package com.qihang.winter.web.demo.controller.test;

import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.ExceptionUtil;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.core.util.UUIDGenerator;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.web.demo.entity.test.JpPersonEntity;
import com.qihang.winter.web.demo.service.test.JpPersonServiceI;
import com.qihang.winter.web.system.service.SystemService;
import org.apache.log4j.Logger;
import com.qihang.winter.poi.excel.ExcelImportUtil;
import com.qihang.winter.poi.excel.entity.ExportParams;
import com.qihang.winter.poi.excel.entity.ImportParams;
import com.qihang.winter.poi.excel.entity.vo.NormalExcelConstants;
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
 * @author  Zerrion
 * @version V1.0
 * @Title: Controller
 * @Description: Excel导出
 * @date 2013-03-23 21:45:28
 */
@Scope("prototype")
@Controller
@RequestMapping("/jpPersonController")
public class JpPersonController extends BaseController {
    /**
     * Logger for this class
     */
    private static final Logger logger = Logger.getLogger(JpPersonController.class);

    @Autowired
    private JpPersonServiceI jpPersonService;
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
     * Excel导出列表 页面跳转
     *
     * @return
     */
    @RequestMapping(params = "jpPerson")
    public ModelAndView jpPerson(HttpServletRequest request) {
        return new ModelAndView("jeecg/demo/test/jpPersonList");
    }

    /**
     * 导出excel
     *
     * @param request
     * @param response
     */
    @RequestMapping(params = "exportXls")
    public String exportXls(HttpServletRequest request, HttpServletResponse response
    ,ModelMap map) {
        List<JpPersonEntity> jpPersons = this.jpPersonService.loadAll(JpPersonEntity.class);
        map.put(NormalExcelConstants.FILE_NAME, "用户信息");
        map.put(NormalExcelConstants.CLASS, JpPersonEntity.class);
        map.put(NormalExcelConstants.PARAMS, new ExportParams(null, null, "导出信息"));
        map.put(NormalExcelConstants.DATA_LIST, jpPersons);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
    }

    @RequestMapping(params = "goImplXls")
    public ModelAndView goImplXls(HttpServletRequest request) {
        return new ModelAndView("jeecg/demo/test/upload");
    }

    // 老的EasyUI上传方式（2013/05/28废弃）
    @RequestMapping(params = "implXls")
    @ResponseBody
    public AjaxJson implXls(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();
        MultipartHttpServletRequest mulRequest = (MultipartHttpServletRequest) request;
        MultipartFile file = mulRequest.getFile("filedata");
        List<JpPersonEntity> listPersons;
        try {
            boolean isSuccess = true;
            listPersons =   ExcelImportUtil.importExcel(
                    file.getInputStream(), JpPersonEntity.class,new ImportParams());
            for (JpPersonEntity person : listPersons) {
                person.setId(UUIDGenerator.generate());
                if (person.getAge() == null || person.getCreatedt() == null || person.getSalary() == null) {
                    isSuccess = false;
                    break;
                } else {
                    jpPersonService.save(person);
                }
            }
            if (isSuccess)
                j.setMsg("文件导入成功！");
            else
                j.setMsg("文件格式不正确，导入失败！");
        } catch (IOException e) {
            j.setMsg("文件导入失败！");
            logger.error(ExceptionUtil.getExceptionMessage(e));
        } catch (Exception e) {
            j.setMsg("文件格式不正确，导入失败！");
        }
        return j;
    }

    // 统一的Excel上传导入方式
    @RequestMapping(params = "importExcel", method = RequestMethod.POST)
    @ResponseBody
    public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
        AjaxJson j = new AjaxJson();

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
        for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
            MultipartFile file = entity.getValue();// 获取上传文件对象
            List<JpPersonEntity> listPersons;
            try {
                listPersons = ExcelImportUtil.importExcel(
                        file.getInputStream(), JpPersonEntity.class,new ImportParams());
                for (JpPersonEntity person : listPersons) {
                    if (person.getAge() != null) {
                        person.setId(UUIDGenerator.generate());
                        jpPersonService.save(person);
                    }
                }
                j.setMsg("文件导入成功！");
            } catch (Exception e) {
                j.setMsg("文件导入失败！");
                logger.error(ExceptionUtil.getExceptionMessage(e));
            }
            //break; // 不支持多个文件导入？
        }

        return j;
    }

    @RequestMapping("check")
    public void check(HttpServletRequest request, HttpServletResponse response) {
        try {
            if ("open".equals(request.getSession().getAttribute("state"))) {
                request.getSession().setAttribute("state", null);
                response.getWriter().write("true");
                response.getWriter().flush();
            } else {
                response.getWriter().write("false");
                response.getWriter().flush();
            }
        } catch (IOException e) {
        }
    }

    /**
     * easyui AJAX请求数据
     *
     * @param request
     * @param response
     * @param dataGrid
     * @param user
     */

    @RequestMapping(params = "datagrid")
    public void datagrid(HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        CriteriaQuery cq = new CriteriaQuery(JpPersonEntity.class, dataGrid);
        this.jpPersonService.getDataGridReturn(cq, true);
        TagUtil.datagrid(response, dataGrid);
    }

    /**
     * 删除Excel导出
     *
     * @return
     */
    @RequestMapping(params = "del")
    @ResponseBody
    public AjaxJson del(JpPersonEntity jpPerson, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        jpPerson = systemService.getEntity(JpPersonEntity.class, jpPerson.getId());
        message = "删除成功";
        jpPersonService.delete(jpPerson);
        systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);

        j.setMsg(message);
        return j;
    }


    /**
     * 添加Excel导出
     *
     * @param ids
     * @return
     */
    @RequestMapping(params = "save")
    @ResponseBody
    public AjaxJson save(JpPersonEntity jpPerson, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        if (StringUtil.isNotEmpty(jpPerson.getId())) {
            message = "更新成功";
            jpPersonService.saveOrUpdate(jpPerson);
            systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
        } else {
            message = "添加成功";
            jpPersonService.save(jpPerson);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
        }

        return j;
    }

    /**
     * Excel导出列表页面跳转
     *
     * @return
     */
    @RequestMapping(params = "addorupdate")
    public ModelAndView addorupdate(JpPersonEntity jpPerson, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(jpPerson.getId())) {
            jpPerson = jpPersonService.getEntity(JpPersonEntity.class, jpPerson.getId());
            req.setAttribute("jpPersonPage", jpPerson);
        }
        return new ModelAndView("jeecg/demo/test/jpPerson");
    }
}
