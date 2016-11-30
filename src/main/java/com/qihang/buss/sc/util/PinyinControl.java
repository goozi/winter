package com.qihang.buss.sc.util;

import com.qihang.buss.sc.baseinfo.entity.TScSupplierEntity;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.util.PinyinUtil;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016-06-29.
 */
@Scope("prototype")
@Controller
@RequestMapping("/pinyinControl")
public class PinyinControl {

    /**
     * 查出这条数据，解析汉字转为拼音，更新保存那条数据
     * @param
     * @param req
     * @return
     */
    @RequestMapping(params = "doName", method= RequestMethod.POST)
    @ResponseBody
    public AjaxJson doName(@RequestParam("name") String name, HttpServletRequest req) {
        AjaxJson j = new AjaxJson();
        String pinyin = PinyinUtil.converterToFirstSpell(name);
        j.setObj(pinyin);
        return  j;
    }



}
