package com.qihang.winter.web.system.controller.core;

import javax.servlet.http.HttpServletRequest;

import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.dao.jdbc.JdbcDao;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.extend.datasource.DataSourceContextHolder;
import com.qihang.winter.core.extend.datasource.DataSourceType;
import com.qihang.winter.web.system.pojo.base.DuplicateCheckPage;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**   
 * @Title: Action
 * @Description: 校验工具Action
 * @author  Zerrion
 * @date 2013-09-12 22:27:30
 * @version V1.0   
 */
@Controller
@RequestMapping("/duplicateCheckAction")
public class DuplicateCheckAction extends BaseController {

	private static final Logger logger = Logger.getLogger(DuplicateCheckAction.class);

	@Autowired
	//SQL 使用JdbcDao
	private JdbcDao jdbcDao;
	
	/**
	 * 校验数据是否在系统中是否存在
	 * @return
	 */
	@RequestMapping(params = "doDuplicateCheck")
	@ResponseBody
	public AjaxJson doDuplicateCheck(DuplicateCheckPage duplicateCheckPage, HttpServletRequest request) {

		String sessiondataSourceType = "";
		if (duplicateCheckPage.getDbKey()!=null && !duplicateCheckPage.getDbKey().equals("")){//hjhadd20160906需要切换到指定数据源，否则以当前数据源来验证数据
			sessiondataSourceType = DataSourceContextHolder.switchDataSourceTypeBefore(Globals.DATA_SOURCE_JEECG, request);
		}
		AjaxJson j = new AjaxJson();
		Long num = null;
		
		if(StringUtils.isNotBlank(duplicateCheckPage.getRowObid())){
			//[2].编辑页面校验
			String sql = "SELECT count(*) FROM "+duplicateCheckPage.getTableName()
						+" WHERE "+duplicateCheckPage.getFieldName() +" =? and id != ?";
			num = jdbcDao.getCountForJdbcParam(sql, new Object[]{duplicateCheckPage.getFieldVlaue(),duplicateCheckPage.getRowObid()});
		}else{
			//[1].添加页面校验
			String sql = "SELECT count(*) FROM "+duplicateCheckPage.getTableName()
						+" WHERE "+duplicateCheckPage.getFieldName() +" =?";
			num = jdbcDao.getCountForJdbcParam(sql, new Object[]{duplicateCheckPage.getFieldVlaue()});
		}
		
		if(num==null||num==0){
			//该值可用
			j.setSuccess(true);
			j.setMsg("该值可用！");
		}else{
			//该值不可用
			j.setSuccess(false);
			j.setMsg("该值'"+duplicateCheckPage.getFieldVlaue() + "'不可用，系统中已存在！");
		}
		if (duplicateCheckPage.getDbKey()!=null && !duplicateCheckPage.getDbKey().equals("")) {//hjhadd20160906需要切换到指定数据源，否则以当前数据源来验证数据,查询完主库后，再要回到原数据源
			DataSourceContextHolder.switchDataSourceTypeAfter(sessiondataSourceType, request);
		}
		return j;
	}
}
