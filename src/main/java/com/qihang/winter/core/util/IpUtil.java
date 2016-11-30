package com.qihang.winter.core.util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

public class IpUtil {
	/**
	 * 获取登录用户IP地址
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (ip.equals("0:0:0:0:0:0:0:1")) {
			ip = "本地";
		}
		return ip;
	}



	public static void main(String[] args) {
//		String dburl = "jdbc:mysql://192.168.0.245:3306/scm_dev?useUnicode=true&characterEncoding=UTF-8";
//		Map<String, String> dbmap = IpUtil.getDbParameterByUrl(dburl);
//		String ip = dbmap.containsKey("ip")?dbmap.get("ip"):"";
//		String port = dbmap.containsKey("port")?dbmap.get("port"):"";
//		String databasename = dbmap.containsKey("databasename")?dbmap.get("databasename"):"";
//		String characterEncoding = dbmap.containsKey("characterEncoding")?dbmap.get("characterEncoding"):"";
//		LogUtil.info("ip:" + ip);
//		LogUtil.info("port:" + port);
//		LogUtil.info("databasename:" + databasename);
//		LogUtil.info("characterEncoding:" + characterEncoding);
	}
}
