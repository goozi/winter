package com.qihang.winter.core.extend.datasource;

import javax.servlet.http.HttpServletRequest;

/**
 *类名：DataSourceContextHolder.java
 *功能：获得和设置上下文环境的类，主要负责改变上下文数据源的名称
 */
public class DataSourceContextHolder {

	private static final ThreadLocal contextHolder=new ThreadLocal();
	
	public static void setDataSourceType(DataSourceType dataSourceType){
		contextHolder.set(dataSourceType);
	}
	
	public static DataSourceType getDataSourceType(){
		return (DataSourceType) contextHolder.get();
	}
	
	public static void clearDataSourceType(){
		contextHolder.remove();
	}

	/**
	 * 切换动态数据源前，将http会话里的数据源记录下来后再切换到新数据源
	 * @param newDataSource
	 * @param request
	 * @return
	 * @author:hjh 20160906
	 */
	public static String switchDataSourceTypeBefore(String newDataSource, HttpServletRequest request){
		DataSourceContextHolder.setDataSourceType(DataSourceType.valueOf(newDataSource));
		String sessiondataSourceType = request.getSession().getAttribute("dataSourceType")==null?"":request.getSession().getAttribute("dataSourceType").toString();
		request.getSession().setAttribute("dataSourceType",newDataSource);
		return sessiondataSourceType;
	}

	/**
	 * 将切换前的数据源写回来http会话里
	 * @param oldDataSource
	 * @param request
	 * @author:hjh 20160906
	 */
	public static void switchDataSourceTypeAfter(String oldDataSource, HttpServletRequest request){
		//查询完主库后，再要回到原数据源
		if (request.getSession().getAttribute("dataSourceType")!=null) {
			request.getSession().setAttribute("dataSourceType", oldDataSource);
			java.lang.String dataSourceType = "";
			if (oldDataSource!=null && !oldDataSource.equals("")) {
				dataSourceType = request.getSession().getAttribute("dataSourceType").toString();
			}
			if (dataSourceType!=null && !dataSourceType.equals("")) {
				DataSourceContextHolder.setDataSourceType(DataSourceType.valueOf(dataSourceType));
			}
		}
	}
}
