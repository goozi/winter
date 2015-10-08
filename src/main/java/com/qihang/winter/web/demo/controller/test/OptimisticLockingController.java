package com.qihang.winter.web.demo.controller.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil;
import com.qihang.winter.core.util.LogUtil;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.web.demo.service.test.OptimisticLockingServiceI;
import com.qihang.winter.web.system.service.SystemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.qihang.winter.web.demo.entity.test.OptimisticLockingEntity;

/**   
 * @Title: Controller
 * @Description: 乐观锁测试
 * @author  Zerrion
 * @date 2013-06-24 14:46:42
 * @version V1.0   
 *
 */
@Scope("prototype")
@Controller
@RequestMapping("/optimisticLockingController")
public class OptimisticLockingController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(OptimisticLockingController.class);

	@Autowired
	private OptimisticLockingServiceI optimisticLockingService;
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
	 * 乐观锁测试列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "optimisticLocking")
	public ModelAndView optimisticLocking(HttpServletRequest request) {
		return new ModelAndView("jeecg/demo/test/optimisticLockingList");
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
	public void datagrid(OptimisticLockingEntity optimisticLocking,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(OptimisticLockingEntity.class, dataGrid);
		//查询条件组装器
		HqlGenerateUtil.installHql(cq, optimisticLocking, request.getParameterMap());
		this.optimisticLockingService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}

	/**
	 * 删除乐观锁测试
	 * 
	 * @return
	 */
	@RequestMapping(params = "del")
	@ResponseBody
	public AjaxJson del(OptimisticLockingEntity optimisticLocking, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		optimisticLocking = systemService.getEntity(OptimisticLockingEntity.class, optimisticLocking.getId());
		message = "删除成功";
		optimisticLockingService.delete(optimisticLocking);
		systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		
		j.setMsg(message);
		return j;
	}


	/**
	 * 添加乐观锁测试
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "save")
	@ResponseBody
	public AjaxJson save(OptimisticLockingEntity optimisticLocking, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		//optimisticLockingService.dd();
		if (StringUtil.isNotEmpty(optimisticLocking.getId())) {
			message = "更新成功";
			OptimisticLockingEntity t = optimisticLockingService.get(OptimisticLockingEntity.class, optimisticLocking.getId());
			try {
				LogUtil.info("提交的版本号："+optimisticLocking.getVer()+"，当前版本号："+t.getVer());
				if(optimisticLocking.getVer()< t.getVer()){
					j.setSuccess(false);
					j.setMsg("提交的数据已过期");
					throw new Exception("提交的数据已过期");
				}
				MyBeanUtils.copyBeanNotNull2Bean(optimisticLocking, t);
				optimisticLockingService.updateEntitie(t);
				systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			message = "添加成功";
			optimisticLockingService.save(optimisticLocking);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}
		
		return j;
	}

	/**
	 * 乐观锁测试列表页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "addorupdate")
	public ModelAndView addorupdate(OptimisticLockingEntity optimisticLocking, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(optimisticLocking.getId())) {
			optimisticLocking = optimisticLockingService.getEntity(OptimisticLockingEntity.class, optimisticLocking.getId());
			req.setAttribute("optimisticLockingPage", optimisticLocking);
		}
		return new ModelAndView("jeecg/demo/test/optimisticLocking");
	}
}
