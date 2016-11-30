package com.qihang.buss.sc.util;

import com.qihang.buss.sc.sys.entity.TScAccountConfigEntity;
import com.qihang.buss.sc.sys.entity.TScBalEntity;
import com.qihang.buss.sc.sys.entity.TScContactbalEntity;
import com.qihang.buss.sc.sys.entity.TScExpbalEntity;
import com.qihang.buss.sc.sys.service.TScAccountConfigServiceI;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.ApplicationContextUtil;
import com.qihang.winter.core.util.DateUtils;
import com.qihang.winter.core.util.ResourceUtil;
import com.qihang.winter.web.system.pojo.base.TSConfig;
import com.qihang.winter.web.system.service.SystemService;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016-08-22.
 */
public class AccountUtil {
  /**
   * 获取账套当前期别日期
   *
   * @return
   */
  public static Date getAccountStartDate() {
    String accountId = ResourceUtil.getAccountId();
    //Calendar accountStartDate = Calendar.getInstance();
    //accountStartDate.set(2016, 7, 1);
    //return accountStartDate.getTime();
    TScAccountConfigServiceI tScAccountConfigServiceI = ApplicationContextUtil.getContext().getBean(TScAccountConfigServiceI.class);
    TScAccountConfigEntity tScAccountConfigEntity = tScAccountConfigServiceI.findByAccountId(accountId);
    if (tScAccountConfigEntity!=null){
//      Calendar calendar = Calendar.getInstance();
//      calendar.setTime(tScAccountConfigEntity.getStageStartDate());
//      calendar.add(Calendar.MONTH,-1);
//      calendar.set(Calendar.DAY_OF_MONTH,calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
//      return calendar.getTime();
      return tScAccountConfigEntity.getStageStartDate();
    }{
      Date date = new Date();
      String strdate = DateUtils.formatDate("yyyy-MM") + "-01";
      return null;//return DateUtils.str2Date(strdate, DateUtils.date_sdf) ;//如果为空则返回当前时间所在年月的1日null;
    }

  }


  /**
   * 获取账套起始日期
   *
   * @return
   */
  public static Date getAccountInitStartDate() {
    String accountId = ResourceUtil.getAccountId();
    //Calendar accountStartDate = Calendar.getInstance();
    //accountStartDate.set(2016, 7, 1);
    //return accountStartDate.getTime();
    TScAccountConfigServiceI tScAccountConfigServiceI = ApplicationContextUtil.getContext().getBean(TScAccountConfigServiceI.class);
    TScAccountConfigEntity tScAccountConfigEntity = tScAccountConfigServiceI.findByAccountId(accountId);
    if (tScAccountConfigEntity!=null){
      return tScAccountConfigEntity.getAccountStartDate();
    }{
      Date date = new Date();
      String strdate = DateUtils.formatDate("yyyy-MM") + "-01";
      return null;//return DateUtils.str2Date(strdate, DateUtils.date_sdf) ;//如果为空则返回当前时间所在年月的1日null;
    }
  }

  /**
   * 获取账套是否开账
   * @return
   * @author hjh 2016-09-07
   */
  public static boolean isAccountOpen(){
    String accountId = ResourceUtil.getAccountId();
    TScAccountConfigServiceI tScAccountConfigServiceI = ApplicationContextUtil.getContext().getBean(TScAccountConfigServiceI.class);
    TScAccountConfigEntity tScAccountConfigEntity = tScAccountConfigServiceI.findByAccountId(accountId);
    if (tScAccountConfigEntity!=null && tScAccountConfigEntity.getOpenState()== Globals.ACCOUNT_OPEN_NO) {
      return false;//未开账
    }else{
      return true;//已开账
    }
  }

  /**
   * 获取当前账套是否允许负库存结账
   * @return
   * @author hjh 2016-09-18
     */
  public static boolean isMinusInventoryAccount(){
    //方案一：从账套配置表取，
    /*String accountId = ResourceUtil.getAccountId();
    TScAccountConfigServiceI tScAccountConfigServiceI = ApplicationContextUtil.getContext().getBean(TScAccountConfigServiceI.class);
    TScAccountConfigEntity tScAccountConfigEntity = tScAccountConfigServiceI.findByAccountId(accountId);
    if (tScAccountConfigEntity!=null && tScAccountConfigEntity.getMinusInventoryAccount()== Globals.ACCOUNT_MINUS_INVENTORY_YES) {
      return true;//允许负库存结账
    }else{
      return false;//不允许负库存结账
    }*/
    //方案二：振铃、小叶：是否允许负库存出库、是否允许负库存结账的保存由t_sc_account_config,改为以天航系统设置各账套库的t_s_config里存储
    //允许负库存结账Globals.MINUSINVENTORYACCOUNT_CODE
    String content = getConfigByCode(Globals.MINUSINVENTORYACCOUNT_CODE);
    if (content!=null && content.equals("1")){
      return true;//允许负库存结账
    }else{
      return false;//不允许负库存结账
    }
  }

  /**
   * 获取当前账套是否允许负库存出库
   * @return
   * @author hjh 2016-09-18
   */
  public static boolean isMinusInventorySI(){
    //方案一：从账套配置表取
    /*String accountId = ResourceUtil.getAccountId();
    TScAccountConfigServiceI tScAccountConfigServiceI = ApplicationContextUtil.getContext().getBean(TScAccountConfigServiceI.class);
    TScAccountConfigEntity tScAccountConfigEntity = tScAccountConfigServiceI.findByAccountId(accountId);
    if (tScAccountConfigEntity!=null && tScAccountConfigEntity.getMinusInventorySl()== Globals.ACCOUNT_MINUS_INVENTORY_SI_YES) {
      return true;//允许负库存出库
    }else{
      return false;//不允许负库出库
    }*/
    //方案二：振铃、小叶：是否允许负库存出库、是否允许负库存结账的保存由t_sc_account_config,改为以天航系统设置各账套库的t_s_config里存储
    //允许负库存出库Globals.MINUSINVENTORYSL_CODE
    String content = getConfigByCode(Globals.MINUSINVENTORYSL_CODE);
    if (content!=null && content.equals("1")){
      return true;//允许负库存结账
    }else{
      return false;//不允许负库存结账
    }
  }

  /**
   * 根据dbKey获取账套配套信息
   * @param dbKey
   * @return
   * @author hjh 2016-09-12
     */
  public static TScAccountConfigEntity getAccountByDbkey(String dbKey){
    TScAccountConfigServiceI tScAccountConfigServiceI = ApplicationContextUtil.getContext().getBean(TScAccountConfigServiceI.class);
    TScAccountConfigEntity tScAccountConfigEntity = tScAccountConfigServiceI.findByDbKey(dbKey);
    return tScAccountConfigEntity;
  }

  /**
   * 将当前账套的ehcache缓存清除
   * @param tScAccountConfigEntity
   * @author hjh 2016-09-07
     */
  public static void clearAccountConfigCache(TScAccountConfigEntity tScAccountConfigEntity){
    if (tScAccountConfigEntity!=null) {
      TScAccountConfigServiceI tScAccountConfigServiceI = ApplicationContextUtil.getContext().getBean(TScAccountConfigServiceI.class);
      tScAccountConfigServiceI.clearAccountConfigCacheByAccountId(tScAccountConfigEntity.getId());
      tScAccountConfigServiceI.clearAccountConfigCacheByDbKey(tScAccountConfigEntity.getDbKey());
    }
  }

  /**
   * 清除当前账套的ehcache缓存
   * @author hjh 2016-09-26
   */
  public static void clearAccountConfigCache(){
    String accountId = ResourceUtil.getAccountId();
    String dbkey = ResourceUtil.getDataSourceType();
    TScAccountConfigEntity tScAccountConfigEntity = new TScAccountConfigEntity();
    tScAccountConfigEntity.setId(accountId);
    tScAccountConfigEntity.setDbKey(dbkey);
    if (tScAccountConfigEntity!=null) {
      TScAccountConfigServiceI tScAccountConfigServiceI = ApplicationContextUtil.getContext().getBean(TScAccountConfigServiceI.class);
      tScAccountConfigServiceI.clearAccountConfigCacheByAccountId(tScAccountConfigEntity.getId());
      tScAccountConfigServiceI.clearAccountConfigCacheByDbKey(tScAccountConfigEntity.getDbKey());
    }
  }

  /**
   * 获取账套是否结账
   * @return
   */
  public static boolean isAccountClose(){
    String accountId = ResourceUtil.getAccountId();
    //// TODO: 2016-09-07 待测试
    TScAccountConfigServiceI tScAccountConfigServiceI = ApplicationContextUtil.getContext().getBean(TScAccountConfigServiceI.class);
    TScAccountConfigEntity tScAccountConfigEntity = tScAccountConfigServiceI.findByAccountId(accountId);
    if (tScAccountConfigEntity!=null && tScAccountConfigEntity.getCloseState()== Globals.ACCOUNT_CLOSE_NO) {
      return false;//未结账
    }else{
      return true;//已结账
    }
  }

  /**
   * 存货结账表
   * 根据商品ID，商品批号，仓库ID返回账套期初数
   * @param itemId
   * @param batchNo
   * @param stockId
   * @return
   */
  public static BigDecimal getInitialNumber(String itemId, String batchNo, String stockId){
//    BigDecimal initialNumber = new BigDecimal(5000.00);
    SystemService systemService = ApplicationContextUtil.getContext().getBean(
            SystemService.class);
    List<TScBalEntity> tScBalEntityList = systemService.findHql("from TScBalEntity where itemID='" + itemId + "' and batchNo='" + batchNo + "'and stockID='" + stockId + "'");
    if(tScBalEntityList.size()>0){
      TScBalEntity tScBalEntity = tScBalEntityList.get(0);
      BigDecimal initialNumber = tScBalEntity.getBegQty();
      return initialNumber;
    }

    //// TODO: 2016-08-30  自测貌似可以用
    return BigDecimal.ZERO;
  }

  /**
   * 存货结账表
   * 根据商品ID，商品批号，仓库ID返回账套期初金额
   * @param itemId
   * @param batchNo
   * @param stockId
   * @return
   */
  public static BigDecimal getInitialAmount(String itemId, String batchNo, String stockId){
//    BigDecimal initialAmount = new BigDecimal(10000.00);

    SystemService systemService = ApplicationContextUtil.getContext().getBean(
            SystemService.class);
    List<TScBalEntity> tScBalEntityList = systemService.findHql("from TScBalEntity where itemID='" + itemId + "' and batchNo='" + batchNo + "' and stockID='" + stockId + "'");
    if(tScBalEntityList.size()>0){
      TScBalEntity tScBalEntity = tScBalEntityList.get(0);
      BigDecimal initialAmount = tScBalEntity.getBegBal();
      return initialAmount;
    }
    //// TODO: 2016-08-30  自测貌似可以用
    return BigDecimal.ZERO;
  }


  /**
   * 应收应付结账表
   * 根据业务类型，核算项目，部门主键，职员主键 返回账套期初金额
   * @param Rp
   * @param ItemID
   * @param DeptID
   * @param EmpID
   * @return
   */
  public static BigDecimal getContactBalAmount(String Rp, String ItemID, String DeptID,String EmpID ) {
    SystemService systemService=ApplicationContextUtil.getContext().getBean(SystemService.class);
    List<TScContactbalEntity> tScContactEntityList=systemService.findHql("from TScContactbalEntity where rp='"+Rp+"' and itemID='"+ItemID+"' and deptID='"+DeptID+"' and empID='"+EmpID+"' ");
   if (tScContactEntityList.size()>0){
     TScContactbalEntity tScContactbalEntity=tScContactEntityList.get(0);
     BigDecimal ContactBalAmount=tScContactbalEntity.getBegBal();
     return ContactBalAmount;
   }
    /// TODO: 2016-08-31  自测貌似可以用
    return null;
  }

  /**
   * 收支结账
   * 根据结算账户 ，部门主键，职员主键 ， 返回账套期初金额
   * @param AccountID
   * @param DeptID
   * @param EmpID
   * @return
   */
  public static BigDecimal getExpBalAmount(String AccountID, String DeptID , String EmpID  ) {
    SystemService systemService=ApplicationContextUtil.getContext().getBean(SystemService.class);
    List<TScExpbalEntity> tScExpbalEntityList=systemService.findHql("from TScExpbalEntity where accountID='"+AccountID+"' and deptID='"+DeptID+"' and empID='"+EmpID+"' ");
    if (tScExpbalEntityList.size()>0){
      TScExpbalEntity tScExpbalEntity=tScExpbalEntityList.get(0);
      BigDecimal ExpBalAmount=tScExpbalEntity.getBegBal();
      return ExpBalAmount;
    }
    /// TODO: 2016-08-31  自测貌似可以用
    return null;
  }

  /**
   * 按参数代码获得当前账套的系统设置的参数值
   * @param code
   * @return
     */
  public static String getConfigByCode(String code){
    String configContent = "";
    SystemService systemService=ApplicationContextUtil.getContext().getBean(SystemService.class);
    List<TSConfig> tsConfigList = systemService.findByProperty(TSConfig.class,"code",code);
    if(tsConfigList.size()>0){
      configContent = tsConfigList.get(0).getContents();
    }
    return configContent;
  }

}