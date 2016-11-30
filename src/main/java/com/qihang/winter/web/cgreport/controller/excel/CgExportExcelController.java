package com.qihang.winter.web.cgreport.controller.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.druid.util.StringUtils;
import com.qihang.winter.core.util.ResourceUtil;
import com.qihang.winter.web.cgreport.common.CgReportConstant;
import com.qihang.winter.web.cgreport.exception.CgReportNotFoundException;
import com.qihang.winter.web.cgreport.service.core.CgReportServiceI;
import com.qihang.winter.web.cgreport.service.excel.CgReportExcelServiceI;
import com.qihang.winter.web.cgreport.util.CgReportQueryParamUtil;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.util.BrowserUtils;
import com.qihang.winter.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
/**
 * 
 * @Title:ExportExcelController
 * @description:报表excel导出
 * @author Zerrion
 * @date Aug 1, 2013 10:52:26 AM
 * @version V1.0
 */
@Controller
@RequestMapping("/cgExportExcelController")
public class CgExportExcelController extends BaseController {
	@Autowired
	private CgReportServiceI cgReportService;
	@Autowired
	private CgReportExcelServiceI cgReportExcelService;
	/**
	 * 将报表导出为excel
	 * @param request
	 * @param response
	 */
	@SuppressWarnings("all")
	@RequestMapping(params = "exportXls")
	public void exportXls(HttpServletRequest request,
			HttpServletResponse response) {
		//step.1 设置，获取配置信息
		String codedFileName = "报表";
		String sheetName="导出信息";
		if (StringUtil.isNotEmpty(request.getParameter("configId"))) {
			String configId = request.getParameter("configId");
			Map<String, Object> cgReportMap = null;
			try{
				cgReportMap = cgReportService.queryCgReportConfig(configId);
			}catch (Exception e) {
				throw new CgReportNotFoundException("动态报表配置不存在!");
			}
			List<Map<String,Object>> fieldList = (List<Map<String, Object>>) cgReportMap.get(CgReportConstant.ITEMS);
			Map configM = (Map) cgReportMap.get(CgReportConstant.MAIN);
			codedFileName = configM.get("name")+codedFileName;
			String querySql = (String) configM.get(CgReportConstant.CONFIG_SQL);
			//默认查询条件配置
			if(querySql.indexOf("#") > -1){
				String[] info = querySql.split("#");
				for(String item : info) {
					String [] info2 = item.split("}");
					if(info2.length > 1) {
						String query = info2[0];
						query = query.substring(1,query.length());
						String value = ResourceUtil.getUserSystemData(query);
						if(!StringUtils.isEmpty(value)){
							querySql = querySql.replace("#{" + query + "}", "'" + value+"'");
						}
					}else if(info2[0].indexOf("{") > -1){
						String query = info2[0];
						query = query.substring(1,query.length());
						String value = ResourceUtil.getUserSystemData(query);
						if(!StringUtils.isEmpty(value)){
							querySql = querySql.replace("#{" + query + "}", "'" + value+"'");
						}
					}
				}
			}
			List<Map<String,Object>> items = (List<Map<String, Object>>) cgReportMap.get(CgReportConstant.ITEMS);
			Map queryparams =  new LinkedHashMap<String,Object>();
			for(Map<String,Object> item:items){
				String isQuery = (String) item.get(CgReportConstant.ITEM_ISQUERY);
				if(CgReportConstant.BOOL_TRUE.equalsIgnoreCase(isQuery)){
					//step.2 装载查询条件
					CgReportQueryParamUtil.loadQueryParams(request, item, queryparams);
				}
			}
			//step.3 进行查询返回结果
			List<Map<String, Object>> result= cgReportService.queryByCgReportSql(querySql, queryparams, -1, -1);
            dealDic(result,items);
            dealReplace(result,items);
			response.setContentType("application/vnd.ms-excel");
			OutputStream fOut = null;
			try {

				//step.4 根据浏览器进行转码，使其支持中文文件名
				String browse = BrowserUtils.checkBrowse(request);
				if ("MSIE".equalsIgnoreCase(browse.substring(0, 4))) {
					response.setHeader("content-disposition",
							"attachment;filename="
									+ java.net.URLEncoder.encode(codedFileName,
											"UTF-8") + ".xls");
				} else {
					String newtitle = new String(codedFileName.getBytes("UTF-8"),
							"ISO8859-1");
					response.setHeader("content-disposition",
							"attachment;filename=" + newtitle + ".xls");
				}
				//step.5 产生工作簿对象
				HSSFWorkbook workbook = null;
				workbook = cgReportExcelService.exportExcel(codedFileName, fieldList, result);
				fOut = response.getOutputStream();
				workbook.write(fOut);
			} catch (UnsupportedEncodingException e1) {

			} catch (Exception e) {

			} finally {
				try {
					fOut.flush();
					fOut.close();
				} catch (IOException e) {

				}
			}
		} else {
			throw new BusinessException("参数错误");
		}
	}

    /**
     * 处理数据字典
     * @param result 查询的结果集
     * @param beans 字段配置
     */
    @SuppressWarnings("unchecked")
    private void dealDic(List<Map<String, Object>> result,
                         List<Map<String,Object>> beans) {
        for(Map<String,Object> bean:beans){
            String dict_code = (String) bean.get(CgReportConstant.ITEM_DICCODE);
            if(StringUtil.isEmpty(dict_code)){
                //不需要处理字典
                continue;
            }else{
                List<Map<String, Object>> dicDatas = queryDic(dict_code);
                for(Map r:result){
                    String value = String.valueOf(r.get(bean.get(CgReportConstant.ITEM_FIELDNAME)));
                    for(Map m:dicDatas){
                        String typecode = String.valueOf(m.get("typecode"));
                        String typename = String.valueOf(m.get("typename"));
                        if(value.equalsIgnoreCase(typecode)){
                            r.put(bean.get(CgReportConstant.ITEM_FIELDNAME),typename);
                        }
                    }
                }
            }
        }
    }
    /**
     * 处理取值表达式
     * @param result
     * @param beans
     */
    @SuppressWarnings("unchecked")
    private void dealReplace(List<Map<String, Object>> result,
                             List<Map<String,Object>> beans){
        for(Map<String,Object> bean:beans){
            try{
                //获取取值表达式
                String replace = (String) bean.get(CgReportConstant.ITEM_REPLACE);
                if(StringUtil.isEmpty(replace)){
                    continue;
                }
                String[] groups = replace.split(",");
                for(String g:groups){
                    String[] items = g.split("_");
                    String v = items[0];//逻辑判断值
                    String txt = items[1];//要转换的文本
                    for(Map r:result){
                        String value = String.valueOf(r.get(bean.get(CgReportConstant.ITEM_FIELDNAME)));
                        if(value.equalsIgnoreCase(v)){
                            r.put(bean.get(CgReportConstant.ITEM_FIELDNAME),txt);
                        }
                    }
                }
            }catch (Exception e) {
                //这里出现异常原因是因为取值表达式不正确
                e.printStackTrace();
                throw new BusinessException("取值表达式不正确");
            }
        }
    }
    /**
     * 查询数据字典
     * @param diccode 字典编码
     * @return
     */
    private List<Map<String, Object>> queryDic(String diccode) {
        StringBuilder dicSql = new StringBuilder();
        dicSql.append(" SELECT TYPECODE,TYPENAME FROM");
        dicSql.append(" "+CgReportConstant.SYS_DIC);
        dicSql.append(" "+"WHERE TYPEGROUPID = ");
        dicSql.append(" "+"(SELECT ID FROM "+CgReportConstant.SYS_DICGROUP+" WHERE TYPEGROUPCODE = '"+diccode+"' )");
        List<Map<String, Object>> dicDatas = cgReportService.findForJdbc(dicSql.toString());
        return dicDatas;
    }
}
