package com.qihang.winter.core.common.dao;


import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.qihang.winter.core.common.model.json.TreeGrid;
import com.qihang.winter.web.system.pojo.base.TSUser;
import com.qihang.winter.web.system.pojo.base.TSDepart;

import com.qihang.winter.core.common.model.common.UploadFile;
import com.qihang.winter.core.common.model.json.ComboTree;
import com.qihang.winter.core.common.model.json.ImportFile;
import com.qihang.winter.core.extend.template.Template;
import com.qihang.winter.tag.vo.easyui.ComboTreeModel;
import com.qihang.winter.tag.vo.easyui.TreeGridModel;

public interface ICommonDao extends IGenericBaseCommonDao{
	
	
	/**
	 * admin账户密码初始化
	 * @param user
	 */
	public void pwdInit(TSUser user, String newPwd);
	/**
	 * 检查用户是否存在
	 * */
	public TSUser getUserByUserIdAndUserNameExits(TSUser user);
	public String getUserRole(TSUser user);
	/**
	 * 文件上传
	 * @param request
	 */
	public <T> T  uploadFile(UploadFile uploadFile);
	/**
	 * 文件上传或预览
	 * @param uploadFile
	 * @return
	 */
	public HttpServletResponse viewOrDownloadFile(UploadFile uploadFile);

	public Map<Object,Object> getDataSourceMap(Template template);
	/**
	 * 生成XML文件
	 * @param fileName XML全路径
	 */
	public HttpServletResponse createXml(ImportFile importFile);
	/**
	 * 解析XML文件
	 * @param fileName XML全路径
	 */
	public void parserXml(String fileName);
	public List<ComboTree> comTree(List<TSDepart> all,ComboTree comboTree);

	/**
     * 根据模型生成ComboTree JSON
     *
     * @param all 全部对象
     * @param comboTreeModel 模型
     * @param in 已拥有的对象
     * @param recursive 是否递归加载所有子节点
     * @return List<ComboTree>
     */
	public  List<ComboTree> ComboTree(List all, ComboTreeModel comboTreeModel, List in, boolean recursive);
	public  List<TreeGrid> treegrid(List all, TreeGridModel treeGridModel);
}

