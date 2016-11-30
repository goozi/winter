package com.qihang.buss.sc.util;

import com.qihang.buss.sc.sys.entity.TSBillMaxSn;
import com.qihang.buss.sc.sysaudit.entity.TSBillInfoEntity;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.util.ApplicationContextUtil;
import com.qihang.winter.core.util.ResourceUtil;
import com.qihang.winter.web.system.service.SystemService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Administrator on 2016-06-06.
 */
@Scope("prototype")
@Controller
@RequestMapping("/billNoGenerate")
public class BillNoGenerate {

  /**
   * Logger for this class
   */
  private static final Logger logger = Logger.getLogger(BillNoGenerate.class);

  /**
   * 获取单据编号
   * @param billId 单据类型
   * @return
   */
  @RequestMapping(params = "getBillNo", method = RequestMethod.GET)
  @ResponseBody
  public static String getBillNo(String billId) {
    SystemService systemService = ApplicationContextUtil.getContext().getBean(
            SystemService.class);
    String billNo = null;
    List<TSBillInfoEntity> billInfoEntityList = systemService.findByProperty(TSBillInfoEntity.class, "billId", billId);
    if (billInfoEntityList.size() > 0) {
//      String accountId = ResourceUtil.getAccountId();
      TSBillInfoEntity billInfoEntity = billInfoEntityList.get(0);
      List<TSBillMaxSn> billMaxSnList = systemService.findHql("from TSBillMaxSn o where o.billId=?", billId);
      Long sn = null;
      Long startSn = 1L;
      TSBillMaxSn billMaxSn;
      Calendar today = Calendar.getInstance();
      if (billMaxSnList.size() > 0) {
        billMaxSn = billMaxSnList.get(0);
        Calendar lastDate = Calendar.getInstance();
        lastDate.setTime(billMaxSn.getLastDate());
        Long nextSn = billMaxSn.getMaxSn() + 1;
        switch (billInfoEntity.getBackZero()) {
          //不归零
          case 0:
            sn = nextSn;
            break;
          //跨日归零
          case 1:
//            int differ = (today.getTime() - billMaxSn.getLastDate().getTime()) / (24*60*60*1000);
            //如果跨天则流水号置1
            if (today.get(Calendar.DATE) > lastDate.get(Calendar.DATE)) {
              sn = startSn;
            } else {
              sn = nextSn;
            }
            break;
          //跨月归零
          case 2:
            if (today.get(Calendar.MONTH) > lastDate.get(Calendar.MONTH)) {
              sn = startSn;
            } else {
              sn = nextSn;
            }
            break;
          //跨年归零
          case 3:
            if (today.get(Calendar.YEAR) > lastDate.get(Calendar.YEAR)) {
              sn = startSn;
            } else {
              sn = nextSn;
            }
            break;
        }
        billMaxSn.setMaxSn(sn);
        billMaxSn.setLastDate(today.getTime());
        systemService.saveOrUpdate(billMaxSn);
      } else {
        sn = startSn;
        billMaxSn = new TSBillMaxSn();
//        billMaxSn.setAccountId(accountId);
        billMaxSn.setBillId(billId);
        billMaxSn.setMaxSn(sn);
        billMaxSn.setLastDate(today.getTime());
        systemService.save(billMaxSn);
      }
      SimpleDateFormat sdf = new SimpleDateFormat(billInfoEntity.getDateFormatter());
      billNo = billInfoEntity.getPrefix() + sdf.format(today.getTime()) + StringUtils.leftPad(sn.toString(), billInfoEntity.getBillNoLen(), "0");
    } else {
      throw new BusinessException("查不到billId对应的单据类型设置");
    }

    return billNo;
  }

  /**
   * 获取基础资料单据编号
   * @param billId       单据类型
   * @param parentBillNo 父节点编号
   * @param subNode      子节点
   * @return
   */
  @RequestMapping(params = "getBasicInfoBillNo", method = RequestMethod.GET)
  @ResponseBody
  public static String getBasicInfoBillNo(String billId, String parentBillNo,String subNode) {
    SystemService systemService = ApplicationContextUtil.getContext().getBean(
            SystemService.class);
    String accountId = ResourceUtil.getAccountId();
    List<TSBillMaxSn> billMaxSnList = systemService.findHql("from TSBillMaxSn o where o.accountId=? and o.billId=? and o.subNode=?", accountId, billId,subNode);
    Calendar today = Calendar.getInstance();
    TSBillMaxSn billMaxSn;
    Long sn;
    if (billMaxSnList.size() > 0) {
      billMaxSn = billMaxSnList.get(0);
      sn = billMaxSn.getMaxSn() + 1;
      billMaxSn.setMaxSn(sn);
      billMaxSn.setLastDate(today.getTime());
      systemService.saveOrUpdate(billMaxSn);
    } else {
      sn = 1L;
      billMaxSn = new TSBillMaxSn();
      billMaxSn.setAccountId(accountId);
      billMaxSn.setBillId(billId);
      billMaxSn.setMaxSn(sn);
      billMaxSn.setLastDate(today.getTime());
      billMaxSn.setSubNode(subNode);
      systemService.save(billMaxSn);
    }
    String billNo = parentBillNo + "." + StringUtils.leftPad(sn.toString(), 4, "0");
//    String billNo = parentBillNo + StringUtils.leftPad(sn.toString(), 4, "0");
    return billNo;
  }

  /**
   * 获取基础资料分类编号
   * @param parentId       分类id
   * @return
   */
  @RequestMapping(params = "getBasicBillNo", method = RequestMethod.GET)
  @ResponseBody
  public static String getBasicBillNo(String parentId, String parentBillNo) {
    SystemService systemService = ApplicationContextUtil.getContext().getBean(
            SystemService.class);
    String billNo = null;
    String accountId = ResourceUtil.getAccountId();
    List<TSBillMaxSn> billMaxSnList = systemService.findHql("from TSBillMaxSn o where o.accountId=? and o.billId=?", accountId, parentId);
    Long sn = null;
    Long startSn = 1L;
    TSBillMaxSn billMaxSn;
    Calendar today = Calendar.getInstance();
    if (billMaxSnList.size() > 0) {
      billMaxSn = billMaxSnList.get(0);
      Calendar lastDate = Calendar.getInstance();
      lastDate.setTime(billMaxSn.getLastDate());
      Long nextSn = billMaxSn.getMaxSn() + 1;
      sn = nextSn;
      billMaxSn.setMaxSn(sn);
      billMaxSn.setLastDate(today.getTime());
      systemService.saveOrUpdate(billMaxSn);
    } else {
      sn = startSn;
      billMaxSn = new TSBillMaxSn();
      billMaxSn.setAccountId(accountId);
      billMaxSn.setBillId(parentId);
      billMaxSn.setMaxSn(sn);
      billMaxSn.setLastDate(today.getTime());
      billMaxSn.setSubNode(parentBillNo);
      systemService.save(billMaxSn);
    }
    if(StringUtils.isNotEmpty(parentBillNo)) {
      billNo = parentBillNo + "." + StringUtils.leftPad(sn.toString(), 3, "0");
//      billNo = parentBillNo + StringUtils.leftPad(sn.toString(), 3, "0");
    }else{
      billNo = StringUtils.leftPad(sn.toString(), 3, "0");
    }

    return billNo;
  }
}
