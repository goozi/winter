package com.qihang.winter.web.system.controller.core;

import javax.servlet.http.HttpSession;
import java.util.HashMap;

/**
 * Created by LenovoM4550 on 2015-12-25.
 */
public class MySessionContext {
    private static MySessionContext instance;
    private HashMap map;

    private MySessionContext(){
        map = new HashMap();
    }

    public static MySessionContext getInstance(){
        if(instance == null){
            instance = new MySessionContext();
        }
        return instance;
    }

    public synchronized void AddSession(HttpSession session){
        if(session != null){
            map.put(session.getId(),session);
        }
    }

    public synchronized void DelSession(HttpSession session){
        if(session != null){
            map.remove(session.getId());
        }
    }

    public synchronized HttpSession getSession(String session_id){
        if(session_id == null){
            return null;
        }
        return (HttpSession) map.get(session_id);
    }
}
