package com.qihang.buss.sc.util;

import com.qihang.buss.sc.sys.entity.TScAccountConfigEntity;
import com.qihang.buss.sc.sys.entity.TScAccountStageEntity;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.util.ApplicationContextUtil;
import com.qihang.winter.core.util.DBUtil;
import com.qihang.winter.core.util.DateUtils;
import com.qihang.winter.core.util.ResourceUtil;
import com.qihang.winter.web.system.service.SystemService;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016-09-20.
 */
public class StageUtil {
    /**
     * 反结账功能后操作主方法
     * @param date
     * @param createname
     * @param createby
     * @return
     */
    public static boolean doUnStageMain(Date date, String createname, final String createby){
        boolean isOk = false;
        //0.下一期
        String nextyear = DateUtils.formatDate(date, "yyyy");
        String nextperiod = DateUtils.formatDate(date, "MM");
        //1.求当前期别的上一期，反结账是对上一期进行的
        String strDate = DateUtils.date2Str(date, DateUtils.date_sdf);
        try {
            strDate = DateUtils.formatAddMonth(strDate, "yyyy-MM-dd",-1);//上期
        }catch (Exception e){

        }
        Date dATE1 = DateUtils.str2Date(strDate, DateUtils.date_sdf);
        String year = DateUtils.formatDate(dATE1, "yyyy");
        String period = DateUtils.formatDate(dATE1, "MM");
        Date accountStartDate = AccountUtil.getAccountStartDate();
        Date accountInitStartDate = AccountUtil.getAccountInitStartDate();
        boolean isFirst = false;
        if (DateUtils.formatDate(accountStartDate,"yyyyMM").equals(DateUtils.formatDate(dATE1,"yyyyMM"))){//开账当期不允许反结账，即第一期不允许反结账
            isFirst = true;
        }
        //2.删除存货结账表、应收应付结账表、收支结账表的本期数据：删除下期结账数据，设置本期结账除期初以外数据为零
        SystemService systemService = ApplicationContextUtil.getContext().getBean(SystemService.class);
        String icbalSql = "";
        //2.1存货结账:删除下一期数据
        icbalSql = "delete from T_SC_IC_Bal where year=? and period=?";
        systemService.executeSql(icbalSql, nextyear, nextperiod);
        //2.2存货结账：清除本期，除期初以外数据为零
        icbalSql = "update T_SC_IC_Bal set Receive=0,SecReceive=0,Send=0,SecSend=0,YtdReceive=0,YtdSend=0,SecYtdReceive=0,SecYtdSend=0,EndQty=0, SecEndQty=0," +
                    "Debit=0,Credit=0,YtdDebit=0,YtdCredit=0,EndBal=0 where year=? and period=?";
        systemService.executeSql(icbalSql, year, period);
        String rpContactbalSql = "";
        //2.3应收应付结账：删除下一期数据
        rpContactbalSql = "delete from T_SC_RP_ContactBal where year=? and period=?";
        systemService.executeSql(rpContactbalSql, nextyear, nextperiod);
        //2.4应收应付结账：清除本期，除期初以外数据为零
        rpContactbalSql = "update T_SC_RP_ContactBal set Debit=0,Credit=0,YtdDebit=0,YtdCredit=0,EndBal=0 where year=? and period=?";
        systemService.executeSql(rpContactbalSql, year, period);
        String rpExpbalSql = "";
        //2.5收支结账：删除下一期数据
        rpExpbalSql = "delete from T_SC_RP_ExpBal where year=? and period=?";
        systemService.executeSql(rpExpbalSql, nextyear, nextperiod);
        rpExpbalSql = "update T_SC_RP_ExpBal set Debit=0,Credit=0,YtdDebit=0,YtdCredit=0,EndBal=0 where year=? and period=?";
        systemService.executeSql(rpExpbalSql, year, period);
        //3.置上一期间的结账状态为未结账
        String updateStageSql = "update t_sc_account_stage set state=? where date=?";
        systemService.executeSql(updateStageSql, 0, dATE1);
        //4.更新scm主库的t_sc_account_config当前账套的当前期别为上一期间[注意跨年的处理]
        String accountId = ResourceUtil.getAccountId();
        //String updateSql = "update t_sc_account_config set stagestartdate=? where id=?";
        //systemService.executeSql(rpExpbalSql, dATE1, accountId);
        DBUtil.updateStagestartdateByAccountId(dATE1, accountId);
        return  isOk;
    }

    /**
     * 结账功能后操作主方法
     * @param date
     * @param createname
     * @param createby
     * @return
     * @author hjh 2016-09-21
     */
    public static boolean doStageMain(Date date, String createname, final String createby){
        String strdate = DateUtils.formatDate(date);
        int year = Integer.parseInt(DateUtils.formatDate(date, "yyyy"));
        int period = Integer.parseInt(DateUtils.formatDate(date, "MM"));
        String nextdate = "";
        boolean isOk = false;
        try{
            nextdate = DateUtils.formatAddMonth(strdate, "yyyy-MM-dd", 1);//下个月初
            Date nextmonth = DateUtils.str2Date(nextdate, DateUtils.date_sdf);
            int nexyear = Integer.parseInt(DateUtils.formatDate(nextmonth, "yyyy"));
            int nextperiod = Integer.parseInt(DateUtils.formatDate(nextmonth, "MM"));
            String endmonth = DateUtils.formatAddDate(nextdate, "yyyy-MM-dd",-1);//当期月底
            Date enddate = DateUtils.str2Date(endmonth, DateUtils.date_sdf);
            SystemService systemService=ApplicationContextUtil.getContext().getBean(SystemService.class);
            //1.检查当期是否未结账且存在，如果不存在，或已结账则抛异常
            String hql = "from TScAccountStageEntity where date=? and state=0";
            List<TScAccountStageEntity> tScAccountStageEntityList1 = systemService.findHql(hql,date);
            if (tScAccountStageEntityList1.size()<=0){
                throw new BusinessException("未找到未结账的当期数据[" + strdate  + "]，请检查");
            }
            //2.检查下期是否未结账且存在，如果不存在，则新增
            hql = "from TScAccountStageEntity where date=?";
            List<TScAccountStageEntity> tScAccountStageEntityList2 = systemService.findHql(hql, nextmonth);
            if (tScAccountStageEntityList2.size()<=0){
                TScAccountStageEntity tScAccountStageEntity = new TScAccountStageEntity();
                tScAccountStageEntity.setDate(nextmonth);
                tScAccountStageEntity.setState(0);
                systemService.saveOrUpdate(tScAccountStageEntity);
            }else{
                TScAccountStageEntity tScAccountStageEntity = tScAccountStageEntityList2.get(0);
                if (tScAccountStageEntity.getState()!=0){
                    throw new BusinessException("下期数据[" + nextdate  + "]已结账，请检查");
                }
            }
            //3.调用期末结账更新存货结账表 存储过程
            isOk = pScIcSettle(year, period, nexyear, nextperiod, date, enddate, createname, createby);
            if (!isOk){
                throw new BusinessException("执行结账-调用期末结账更新存货结账表存储过程出错");
            }
            //4.调用期末结账更新应收应付结账表 存储过程
            isOk = pScRpSettle(year, period, nexyear, nextperiod, date, enddate, createname, createby);
            if (!isOk){
                throw new BusinessException("执行结账-调用期末结账更新应收应付结账表存储过程出错");
            }
            //5.调用期末结账更新应收应付结账表 存储过程
            isOk = pScExpSettle(year, period, nexyear, nextperiod, date, enddate, createname, createby);
            if (!isOk){
                throw new BusinessException("执行结账-调用期末结账更新应收应付结账表存储过程出错");
            }
            //6.置当期结账状态为已结账
            if (tScAccountStageEntityList1.size()>0){
                TScAccountStageEntity tScAccountStageEntity = tScAccountStageEntityList1.get(0);
                tScAccountStageEntity.setState(1);
                systemService.saveOrUpdate(tScAccountStageEntity);
            }
            //7.更新scm主库的t_sc_account_config当前账套的当前期别为下一期
            String accountId = ResourceUtil.getAccountId();
            DBUtil.updateStagestartdateByAccountId(nextmonth, accountId);
            isOk = true;
        }catch (Exception e){
            throw new BusinessException("执行结账存储过程出错：" + e.getMessage());
        }
        return isOk;
    }

    /**
     * 调用期末结账更新存货结账表 存储过程
     * @param year 当期年份
     * @param period 当期月份
     * @param nexyear 下期年份
     * @param nextperiod 下期月份
     * @param begdate 当期开始日期
     * @param enddate 当期截止日期
     * @param createname 操作人名称
     * @param createby 操作人ID
     * @return
     * @author hjh 2016-09-21
     */
    public static boolean pScIcSettle(final int year, final int period, final int nexyear, final int nextperiod, final Date begdate, final Date enddate, final String createname, final String createby) {
        JdbcTemplate jdbcTemplate = ApplicationContextUtil.getContext().getBean(
                JdbcTemplate.class);
        Boolean isOk = (Boolean)jdbcTemplate.execute(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                String storedProc = "{call P_SC_IC_Settle(?,?,?,?,?,?,?,?)}";
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setInt(1, year);
                cs.setInt(2, period);
                cs.setInt(3, nexyear);
                cs.setInt(4, nextperiod);
                cs.setDate(5, new java.sql.Date(begdate.getTime()));
                cs.setDate(6, new java.sql.Date(enddate.getTime()));
                cs.setString(7, createname);
                cs.setString(8, createby);
                return cs;
            }
        }, new CallableStatementCallback() {
            public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                Boolean isOk = cs.execute();
                return isOk;
            }
        });
        return true;
    }

    /**
     * 调用期末结账更新应收应付结账表 存储过程
     * @param year 当期年份
     * @param period 当期月份
     * @param nexyear 下期年份
     * @param nextperiod 下期月份
     * @param begdate 当期开始日期
     * @param enddate 当期截止日期
     * @param createname 操作人名称
     * @param createby 操作人ID
     * @return
     * @author hjh 2016-09-21
     */
    public static boolean pScRpSettle(final int year, final int period, final int nexyear, final int nextperiod, final Date begdate, final Date enddate, final String createname, final String createby) {
        JdbcTemplate jdbcTemplate = ApplicationContextUtil.getContext().getBean(
                JdbcTemplate.class);
        Boolean isOk = (Boolean)jdbcTemplate.execute(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                String storedProc = "{call P_SC_RP_Settle(?,?,?,?,?,?,?,?)}";
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setInt(1, year);
                cs.setInt(2, period);
                cs.setInt(3, nexyear);
                cs.setInt(4, nextperiod);
                cs.setDate(5, new java.sql.Date(begdate.getTime()));
                cs.setDate(6, new java.sql.Date(enddate.getTime()));
                cs.setString(7, createname);
                cs.setString(8, createby);
                return cs;
            }
        }, new CallableStatementCallback() {
            public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                Boolean isOk = cs.execute();
                return isOk;
            }
        });
        return true;
    }

    /**
     * 调用期末结账更新应收应付结账表 存储过程
     * @param year 当期年份
     * @param period 当期月份
     * @param nexyear 下期年份
     * @param nextperiod 下期月份
     * @param begdate 当期开始日期
     * @param enddate 当期截止日期
     * @param createname 操作人名称
     * @param createby 操作人ID
     * @return
     * @author hjh 2016-09-21
     */
    public static boolean pScExpSettle(final int year, final int period, final int nexyear, final int nextperiod, final Date begdate, final Date enddate, final String createname, final String createby) {
        JdbcTemplate jdbcTemplate = ApplicationContextUtil.getContext().getBean(
                JdbcTemplate.class);
        Boolean isOk = (Boolean)jdbcTemplate.execute(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection con) throws SQLException {
                String storedProc = "{call P_SC_Exp_Settle(?,?,?,?,?,?,?,?)}";
                CallableStatement cs = con.prepareCall(storedProc);
                cs.setInt(1, year);
                cs.setInt(2, period);
                cs.setInt(3, nexyear);
                cs.setInt(4, nextperiod);
                cs.setDate(5, new java.sql.Date(begdate.getTime()));
                cs.setDate(6, new java.sql.Date(enddate.getTime()));
                cs.setString(7, createname);
                cs.setString(8, createby);
                return cs;
            }
        }, new CallableStatementCallback() {
            public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {
                Boolean isOk = cs.execute();
                return isOk;
            }
        });
        return true;
    }
}
