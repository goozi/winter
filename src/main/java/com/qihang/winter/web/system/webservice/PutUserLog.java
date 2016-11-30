package com.qihang.winter.web.system.webservice;

import com.qihang.winter.web.system.webservice.ServiceStub;

/**
 * Created by LenovoM4550 on 2015/12/24.
 */


public class PutUserLog {

  public PutUserLog(String sn, String address, String company, String contact, String email, String ipaddress, String telphone, String user, String version) {
    try {
      ServiceStub stub = new ServiceStub();
      stub._getServiceClient().getOptions().setProperty(
              org.apache.axis2.transport.http.HTTPConstants.CHUNKED,
              Boolean.FALSE);
      ServiceStub.UserLog userLog = new ServiceStub.UserLog();
//      userLog.setSn("112325");
//      userLog.setAddress("Xiamen");
//      userLog.setCompany("微软");
//      userLog.setContact("张三");
//      userLog.setEmail("test@ms.com");
//      userLog.setIpaddress("127.10.10.2");
//      userLog.setTelphone("1234567");
//      userLog.setUser("比尔.辣酱");
//      userLog.setVersion("1");
      userLog.setSn(sn);
      userLog.setAddress(address);
      userLog.setCompany(company);
      userLog.setContact(contact);
      userLog.setEmail(email);
      userLog.setIpaddress(ipaddress);
      userLog.setTelphone(telphone);
      userLog.setUser(user);
      userLog.setVersion(version);
      ServiceStub.UserLogResponse response = stub.userLog(userLog);
      System.out.println(response);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String args[]) {
    PutUserLog log = new PutUserLog("", "厦门", "测试公司", "联系人", "test@ms.com", "127.0.0.1", "1234567", "测试用户", "518");
  }
}
