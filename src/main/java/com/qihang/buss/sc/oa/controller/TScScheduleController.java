package com.qihang.buss.sc.oa.controller;


import com.qihang.buss.sc.oa.entity.TScScheduleEntity;
import com.qihang.buss.sc.oa.service.TScScheduleServiceI;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.exception.BusinessException;
import com.qihang.winter.core.common.hibernate.qbc.CriteriaQuery;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.constant.Globals;
import com.qihang.winter.core.util.MyBeanUtils;
import com.qihang.winter.core.util.ResourceUtil;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.tag.vo.datatable.SortDirection;
import com.qihang.winter.web.system.pojo.base.TSUser;
import com.qihang.winter.web.system.service.SystemService;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by LenovoM4550 on 2015-12-02.
 */
@Scope("prototype")
@Controller
@RequestMapping("/tScScheduleController")
public class TScScheduleController extends BaseController {

    private static final Logger logger = Logger.getLogger(TScScheduleController.class);

    private String message;

    @Autowired
    private TScScheduleServiceI service;

    @Autowired
    private SystemService systemService;

    @RequestMapping(params = "tScSchedule")
    public ModelAndView tScDaily(HttpServletRequest request) {
        return new ModelAndView("com/qihang/buss/sc/oa/tScSchedule");
    }

    @RequestMapping(params = "datagrid")
    @ResponseBody
    public Map<String,Object> datagrid(TScScheduleEntity entity,String showdate,String viewtype,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws ParseException {

        CriteriaQuery cq = new CriteriaQuery(TScScheduleEntity.class, dataGrid);
        //查询条件组装器
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, entity, request.getParameterMap());
        String userName = ResourceUtil.getSessionUserName().getUserName();
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd");
        Date today = dateSdf.parse(showdate);
        SimpleDateFormat weekDay = new SimpleDateFormat("EEEE");
        String weekNum = weekDay.format(today);
        Date startDate = null;
        Date endDate = null;
        if("week".equals(viewtype)){
            if("星期一".equals(weekNum)){
                startDate = today;
                endDate = new Date(today.getTime()+7*24*60*60*1000);
            }
            else if("星期二".equals(weekNum)){
                startDate = new Date(today.getTime()-1*24*60*60*1000);
                endDate = new Date(today.getTime()+6*24*60*60*1000);
            }
            else if("星期三".equals(weekNum)){
                startDate = new Date(today.getTime()-2*24*60*60*1000);
                endDate = new Date(today.getTime()+5*24*60*60*1000);
            }
            else if("星期四".equals(weekNum)){
                startDate = new Date(today.getTime()-3*24*60*60*1000);
                endDate = new Date(today.getTime()+4*24*60*60*1000);
            }
            else if("星期五".equals(weekNum)){
                startDate = new Date(today.getTime()-4*24*60*60*1000);
                endDate = new Date(today.getTime()+3*24*60*60*1000);
            }
            else if("星期六".equals(weekNum)){
                startDate = new Date(today.getTime()-5*24*60*60*1000);
                endDate = new Date(today.getTime()+2*24*60*60*1000);
            }
            else{
                startDate = new Date(today.getTime()-6*24*60*60*1000);
                endDate = new Date(today.getTime()+1*24*60*60*1000);
            }
        }else if("month".equals(viewtype)){
            Calendar cal = Calendar.getInstance();
            cal.setTime(today);
            int month = cal.get(Calendar.MONTH)+1;
            int year = cal.get(Calendar.YEAR);
            startDate = dateSdf.parse(year+"-"+(month< 10 ? "0"+month : month)+"-01");
            if(month < 12){
                endDate = dateSdf.parse(year+"-"+(month+1< 10 ? "0"+(month+1) : month+1)+"-01");
            }else{
                endDate = dateSdf.parse((year+1)+"-01-01");
            }
        }
        try{
            //自定义追加查询条件
           cq.eq("createBy", userName);
           if("week".equals(viewtype)){
               cq.ge("calendarStartTime",startDate);
               cq.le("calendarEndTime",endDate);
           }else if("month".equals(viewtype)){
               //cq.ge("calendarStartTime",startDate);
               //cq.lt("calendarEndTime",endDate);
           }else{
               cq.eq("calendarStartTime",today);
           }
            cq.addOrder("calendarStartTime", SortDirection.asc);
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.service.getDataGridReturn(cq, true);
        List<TScScheduleEntity> result = dataGrid.getResults();
        Map<String, Object> params = new HashMap<String, Object>();
        List<Object> eventsItems = new ArrayList<Object>();
        for(int i=0 ; i< result.size() ; i++) {
            TScScheduleEntity item = result.get(i);
            if(i==0){
                startDate = item.getCalendarStartTime();
            }
            if(i==result.size()){
                endDate = item.getCalendarEndTime();
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String start = sdf.format(item.getCalendarStartTime());
            String end = sdf.format(item.getCalendarEndTime());
            List<Object> item1 = new ArrayList<Object>();
            item1.add(item.getId());
            item1.add(item.getCalendarTitle());
            item1.add(item.getCalendarStartTime());
            item1.add(item.getCalendarEndTime());
            item1.add(item.getIsAllDayEvent() ? 1 : 0);
            if(start.equals(end)) {
                item1.add(0);
            }else{
                item1.add(1);
            }
            item1.add(0);
            item1.add(null);
            item1.add(1);
            item1.add(null);
            item1.add(item.getCreateBy());
            eventsItems.add(item1);
        }
        params.put("events", eventsItems);
        params.put("issort",true);
        params.put("start",startDate);
        params.put("end",endDate);
        params.put("error",null);
        return params;
    }

    /**
     * 首页展示数据
     * @param tScViewNews
     * @param request
     * @param response
     * @param dataGrid
     * @return
     */
    @RequestMapping(params = "getDateInfo")
    @ResponseBody
    public Map<String,Object> getDateInfo(TScScheduleEntity tScViewNews,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
        tScViewNews.setCreateBy(ResourceUtil.getSessionUserName().getUserName());
        dataGrid.setRows(7);
        CriteriaQuery cq = new CriteriaQuery(TScScheduleEntity.class, dataGrid);
        //查询条件组装器
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tScViewNews, request.getParameterMap());
        try{
            //自定义追加查询条件
            Calendar todayStart = Calendar.getInstance();
            todayStart.set(Calendar.HOUR_OF_DAY,0);
            todayStart.set(Calendar.MINUTE,0);
            todayStart.set(Calendar.SECOND,0);
            todayStart.set(Calendar.MILLISECOND, 0);
            cq.ge("calendarStartTime", todayStart.getTime());
            Calendar todayEnd = Calendar.getInstance();
            todayEnd.set(Calendar.HOUR_OF_DAY,23);
            todayEnd.set(Calendar.MINUTE,59);
            todayEnd.set(Calendar.SECOND,59);
            todayEnd.set(Calendar.MILLISECOND, 999);
            cq.le("calendarEndTime", todayEnd.getTime());
            cq.addOrder("calendarStartTime", SortDirection.asc);
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.service.getDataGridReturn(cq, true);
        List<TScScheduleEntity> rows = new ArrayList<TScScheduleEntity>();
        for(int i=0 ; i < dataGrid.getResults().size() ; i++){
            if( i < 7) {
                TScScheduleEntity row = (TScScheduleEntity) dataGrid.getResults().get(i);
                rows.add(row);
            }
        }
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("total",rows.size());
        result.put("rows",rows);
        return result;
        //TagUtil.datagrid(response, dataGrid);
    }
    /**
     * 添加工作日志
     *
     * @return
     */
    @RequestMapping(params = "doAdd")
    @ResponseBody
    public Map<String,Object> doAdd(TScScheduleEntity entity,String startTime,String endTime, HttpServletRequest request) throws ParseException {
        //AjaxJson j = new AjaxJson();
        message = "工作日程添加成功";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        entity.setCalendarStartTime(sdf.parse(startTime));
        entity.setCalendarEndTime(sdf.parse(endTime));
        Map<String,Object> params = new HashMap<String, Object>();
        Map<String,Object> errInfo = new HashMap<String, Object>();
        List<Object> eventsItems = new ArrayList<Object>();
        List<Object> item1 = new ArrayList<Object>();
        Boolean isSuccess = false;
        Map<String,Object> result = new HashMap<String, Object>();
        try{
            Serializable id = service.save(entity);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            isSuccess = true;
            //配置返回值
            item1.add(entity.getId());//主键
            item1.add(entity.getCalendarTitle());//内容
            item1.add(entity.getCalendarStartTime());//开始时间
            item1.add(entity.getCalendarEndTime());//结束时间
            item1.add(entity.getIsAllDayEvent() ? 1 : 0);//是否全天日程
            item1.add(0);//是否跨天日程
            item1.add(0);//是否循环日程
            item1.add(null);//颜色主题
            item1.add(1);//是否有权限
            item1.add(null);//地点
            item1.add(entity.getCreateBy());//参与人
            eventsItems.add(item1);
            params.put("events", eventsItems);
            params.put("issort", true);
            params.put("start", entity.getCalendarStartTime());
            params.put("end",entity.getCalendarEndTime());
            //params.put("error",null);
        }catch(Exception e){
            e.printStackTrace();
            message = "工作日程添加失败";
            throw new BusinessException(e.getMessage());
        }
        if(!isSuccess) {
            errInfo.put("ErrorCode", "ER_Add");
            errInfo.put("ErrorMsg", message);
            params.put("error", errInfo);
            result.put("IsSuccess",false);
            result.put("Msg","保存失败,请联系管理员");
        }else{
            params.put("error",null);
            result.put("IsSuccess",isSuccess);
            result.put("Data",params);
        }
        return result;
    }

    @RequestMapping(params = "doUpdate")
    @ResponseBody
    public Map<String,Object> doUpdate(TScScheduleEntity entity,String startTime,String endTime, HttpServletRequest request) throws ParseException {
        message = "工作日程编辑成功";
        Map<String,Object> params = new HashMap<String, Object>();
        Map<String,Object> errInfo = new HashMap<String, Object>();
        Boolean isSuccess = false;
        try{
            if(StringUtils.isNotEmpty(startTime) && StringUtils.isNotEmpty(endTime)){
                Date timeStart = new Date(startTime);
                Date timeEnd = new Date(endTime);
                TSUser user = ResourceUtil.getSessionUserName();
                entity.setCalendarStartTime(timeStart);
                entity.setCalendarEndTime(timeEnd);
                entity.setCreateBy(user.getUserName());
                entity.setCreateName(user.getUserName());
                entity.setIsAllDayEvent(false);
                entity.setCreateDate(timeStart);
            }
            service.saveOrUpdate(entity);
            systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
            isSuccess = true;
            //配置返回值
            List<Object> eventsItems = new ArrayList<Object>();
            List<Object> item1 = new ArrayList<Object>();
            item1.add(entity.getId());//主键
            item1.add(entity.getCalendarTitle());//内容
            item1.add(entity.getCalendarStartTime());//开始时间
            item1.add(entity.getCalendarEndTime());//结束时间
            item1.add(0);//是否全天日程
            item1.add(0);//是否跨天日程
            item1.add(0);//是否循环日程
            item1.add(null);//颜色主题
            item1.add(1);//是否有权限
            item1.add(null);//地点
            item1.add(entity.getCreateBy());//参与人
            eventsItems.add(item1);
            params.put("events", eventsItems);
            params.put("issort", true);
            params.put("start", entity.getCalendarStartTime());
            params.put("end",entity.getCalendarEndTime());
            //params.put("error",null);
        }catch(Exception e){
            e.printStackTrace();
            message = "工作日程编辑失败";
            throw new BusinessException(e.getMessage());
        }
        if(!isSuccess) {
            errInfo.put("ErrorCode", "ER_Add");
            errInfo.put("ErrorMsg", message);
            params.put("error", errInfo);
            params.put("IsSuccess",false);
            params.put("success",false);
            params.put("data",params);
        }else{
            params.put("error",null);
            params.put("IsSuccess",true);
            params.put("success",true);
        }
        return params;
    }

    /**
     * 工作日志编辑页面跳转
     *
     * @return
     */
    @RequestMapping(params = "goUpdate")
    public ModelAndView goUpdate(TScScheduleEntity tScSchecule, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tScSchecule.getId())) {
            tScSchecule = service.getEntity(TScScheduleEntity.class, tScSchecule.getId());
            req.setAttribute("tScSchedulePage", tScSchecule);
        }
        return new ModelAndView("com/qihang/buss/sc/oa/tScSchedule-update");
    }

    @RequestMapping(params = "goUpdate2")
    public ModelAndView goUpdate2(TScScheduleEntity tScSchecule, HttpServletRequest req) {
        if (StringUtil.isNotEmpty(tScSchecule.getId())) {
            tScSchecule = service.getEntity(TScScheduleEntity.class, tScSchecule.getId());
            req.setAttribute("tScSchedulePage", tScSchecule);
        }
        return new ModelAndView("com/qihang/buss/sc/oa/tScSchedule-update2");
    }

    /**
     * 删除工作日程
     *
     * @return
     */
    @RequestMapping(params = "doDel")
    @ResponseBody
    public Map<String,Object> doDel(TScScheduleEntity tScDaily, HttpServletRequest request) {

            tScDaily = systemService.getEntity(TScScheduleEntity.class, tScDaily.getId());
            message = "工作日程删除成功";
            Map<String,Object> result = new HashMap<String, Object>();
            try {
                service.delete(tScDaily);
                systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
                result.put("IsSuccess",true);
            } catch (Exception e) {
                e.printStackTrace();
                message = "工作日程删除失败";
                result.put("IsSuccess",false);
                throw new BusinessException(e.getMessage());
            }
        return result;
    }


    //手机端方法

    @RequestMapping(params = "datagridApp")
    @ResponseBody
    public Map<String,Object> datagridApp(TScScheduleEntity entity,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws ParseException {

        CriteriaQuery cq = new CriteriaQuery(TScScheduleEntity.class, dataGrid);
        //查询条件组装器
        TSUser user = systemService.get(TSUser.class,entity.getUserId());
        entity.setUserId(null);
        entity.setStartTime(null);
        entity.setEndTime(null);
        entity.setDate(null);
        com.qihang.winter.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, entity, request.getParameterMap());
        SimpleDateFormat dateSdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String date = request.getParameter("date");

        try{
            //自定义追加查询条件
            cq.eq("createBy", user.getUserName());
            if(StringUtils.isNotEmpty(date)) {
                if (StringUtils.isNotEmpty(startTime)) {
                    String beginDate = date + " " + startTime + ":00";
                    Date startDate = dateSdf.parse(beginDate);
                    cq.ge("calendarStartTime", startDate);
                } else {
                    String beginDate = date + " 00:00:00";
                    Date startDate = dateSdf.parse(beginDate);
                    cq.ge("calendarStartTime", startDate);
                }
                if (StringUtils.isNotEmpty(endTime)) {
                    String finishDate = date + " " + endTime + ":00";
                    Date endDate = dateSdf.parse(finishDate);
                    cq.le("calendarEndTime", endDate);
                } else {
                    String finishDate = date + " 23:59:59";
                    Date endDate = dateSdf.parse(finishDate);
                    cq.le("calendarEndTime", endDate);
                }
            }else{
                Calendar todayStart = Calendar.getInstance();
                todayStart.set(Calendar.HOUR_OF_DAY,0);
                todayStart.set(Calendar.MINUTE,0);
                todayStart.set(Calendar.SECOND,0);
                todayStart.set(Calendar.MILLISECOND, 0);
                cq.ge("calendarStartTime", todayStart.getTime());
            }
            cq.addOrder("calendarStartTime", SortDirection.asc);
        }catch (Exception e) {
            throw new BusinessException(e.getMessage());
        }
        cq.add();
        this.service.getDataGridReturn(cq, true);
        List<TScScheduleEntity> result = dataGrid.getResults();
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("total", result.size());
        params.put("rows",result);
        return params;
    }

    /**
     * 日程时间安排
     * @param entity
     * @param request
     * @return
     * @throws ParseException
     */
    @RequestMapping(params = "addApp")
    @ResponseBody
    public AjaxJson addApp(@RequestBody TScScheduleEntity entity, HttpServletRequest request) throws ParseException {
        AjaxJson j = new AjaxJson();
        message = "工作日程添加成功";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String beginTime = entity.getStartTime()+":00";
        String finishTime = entity.getEndTime()+":00";
        entity.setTimezone(8);
        entity.setCalendarStartTime(sdf.parse(beginTime));
        entity.setCalendarEndTime(sdf.parse(finishTime));
        entity.setIsAllDayEvent(false);
        TSUser user = systemService.get(TSUser.class,entity.getUserId());
        if(user != null){
            entity.setCreateBy(user.getUserName());
            entity.setCreateName(user.getRealName());
            entity.setCreateDate(new Date());
        }
        try{
            service.save(entity);
        }catch(Exception e){
            e.printStackTrace();
            j.setSuccess(false);
            message = "工作日程添加失败";
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 手机端获取编辑数据
     * @param id
     * @return
     */
    @RequestMapping(params = "loadUpdate")
    @ResponseBody
    public Map<String,Object> loadUpdate(String id){
        TScScheduleEntity entity = systemService.get(TScScheduleEntity.class,id);
        Map<String,Object> result = new HashMap<String, Object>();
        Date startTime = entity.getCalendarStartTime();
        Date endTime = entity.getCalendarEndTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String date = sdf.format(startTime);
        sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String start = sdf.format(startTime);
        String end = sdf.format(endTime);
        start = start.substring(11,start.length()-3);
        end = end.substring(11,end.length()-3);
        result.put("id", id);
        result.put("startTime", start);
        result.put("endTime",end);
        result.put("date",date);
        result.put("calendarTitle", entity.getCalendarTitle());
        return result;
    }


    /**
     * 手机端日程修改
     * @param entity
     * @param request
     * @return
     * @throws ParseException
     */
    @RequestMapping(params = "updateApp")
    @ResponseBody
    public AjaxJson updateApp(@RequestBody TScScheduleEntity entity, HttpServletRequest request) throws ParseException {
        AjaxJson j = new AjaxJson();
        message = "工作日程编辑成功";
        TScScheduleEntity t = service.get(TScScheduleEntity.class,entity.getId());
        try{
            MyBeanUtils.copyBeanNotNull2Bean(entity, t);
            if(StringUtils.isNotEmpty(entity.getStartTime()) && StringUtils.isNotEmpty(entity.getEndTime())){
                String date = entity.getDate();
                String begin = entity.getStartTime()+":00";
                String end = entity.getEndTime()+":00";
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

                Date timeStart = sdf.parse(begin);
                Date timeEnd = sdf.parse(end);
                TSUser user = systemService.get(TSUser.class,entity.getUserId());
                t.setCalendarStartTime(timeStart);
                t.setCalendarEndTime(timeEnd);
                t.setIsAllDayEvent(false);
                t.setUpdateBy(user.getUserName());
                t.setUpdateName(user.getRealName());
                t.setUpdateDate(new Date());
            }
            service.saveOrUpdate(t);

        }catch(Exception e){
            e.printStackTrace();
            message = "工作日程编辑失败";
            j.setSuccess(false);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    @RequestMapping(params = "delApp")
    @ResponseBody
    public AjaxJson delApp(String id, HttpServletRequest request) {
        AjaxJson j = new AjaxJson();
        TScScheduleEntity entity = systemService.getEntity(TScScheduleEntity.class, id);
        message = "工作日程删除成功";
        try {
            service.delete(entity);
        } catch (Exception e) {
            e.printStackTrace();
            message = "工作日程删除失败";
            j.setSuccess(false);
            throw new BusinessException(e.getMessage());
        }
        j.setMsg(message);
        return j;
    }

    /**
     * 当天日程提示
     * @return
     */
    @RequestMapping(params = "datagrid_main")
    @ResponseBody
    public List<Map<String,Object>> datagrid_main(){
        String userName = ResourceUtil.getSessionUserName().getUserName();
        Date today = new Date();//当天日期；
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String timeEnd = sdf.format(today);//结束时间
        List<TScScheduleEntity> scScheduleEntityList = systemService.findHql("from TScScheduleEntity where createBy = ? and calendarEndTime >= '"+timeEnd+"'",new Object[]{userName});
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        int length = scScheduleEntityList.size();
        if(length > 10){
            length = 10;
        }
        for(int i = 0 ; i < length ; i++){
            TScScheduleEntity entity = scScheduleEntityList.get(i);
            Map<String,Object> map = new HashMap<String, Object>();
            map.put("id",entity.getId());
            map.put("CalendarTitle",entity.getCalendarTitle());
            map.put("CalendarStartTime",sdf.format(entity.getCalendarStartTime()));
            map.put("CalendarEndTime",sdf.format(entity.getCalendarEndTime()));
            result.add(map);
        }
        return result;
    }
}
