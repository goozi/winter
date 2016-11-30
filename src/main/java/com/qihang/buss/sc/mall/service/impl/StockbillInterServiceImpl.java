package com.qihang.buss.sc.mall.service.impl;

import com.qihang.buss.sc.mall.controller.HttpTemplate;
import com.qihang.buss.sc.mall.service.StockbillInterServiceI;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.service.impl.CommonServiceImpl;
import com.qihang.winter.core.util.ExceptionUtil;
import com.qihang.winter.core.util.PropertiesUtil;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.web.system.service.SystemService;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by LenovoM4550 on 2016-09-23.
 */
@Service("stockbillInterServiceImpl")
@Transactional
public class StockbillInterServiceImpl extends CommonServiceImpl implements StockbillInterServiceI {
    private static final Logger logger = Logger.getLogger(StockbillInterServiceImpl.class);

    @Autowired
    private SystemService systemService;
    @Override
    public AjaxJson synStockstate(String mallorders,String ids) {
        AjaxJson ajaxJson = new AjaxJson();
        if (StringUtil.isEmpty(mallorders) || StringUtil.isEmpty(ids)){
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("请选择商城订单进行同步");
            return ajaxJson;
        }
        //获取PHP接口地址
        PropertiesUtil p = new PropertiesUtil("mall.properties");
        String phpUrl = p.readProperty("sys_order_state");
      //  StringBuilder str = new StringBuilder();
      //  str.append("{").append("\"mallorders\"").append(":").append("\"").append(mallorders).append("\"").append("}");
        try {
            String result = HttpTemplate.templatePost(phpUrl, mallorders);
            JSONObject object = JSONObject.fromObject(result);
            if ("200".equals(object.getString("code"))) {//成功时处理
                String sql = "update t_sc_sl_stockbill set SYSSTATE=1 where ID=?";
                systemService.executeSql(sql,ids);
                ajaxJson.setSuccess(true);
                ajaxJson.setMsg("同步成功");
            } else {//失败返回信息
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg(object.getString("msg"));
            }
        }catch (Exception e){
            logger.error(ExceptionUtil.getExceptionMessage(e));
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("调用接口发起出错");
        }
        return ajaxJson;
    }
}

