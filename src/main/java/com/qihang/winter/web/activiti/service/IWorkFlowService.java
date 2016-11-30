package com.qihang.winter.web.activiti.service;


import com.qihang.winter.core.common.model.json.AjaxJson;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.Map;

/**
 * 工作流接口
 * Created by LenovoM4550 on 2015/9/17.
 */
public interface IWorkFlowService {

    /**
     * 进入流程
     * @param caseName 流程id
     * @param businessKey 单据id
     * @param userId 当前用户id
     * @param variables
     * @return
     */
    ProcessInstance startWorkflow(String caseName, String businessKey, String userId, Map<String, Object> variables);

    /**
     * 流程节点跳转
     * @param taskId 流程id
     * @Param variables 参数变量
     */
    void complet(String taskId, Map<String, Object> variables, String activityId);

    /**
     * 当前流程节点下一步
     * @param pid 流程id
     * @Param variables 参数变量
     */
    public void completeNextUserTask(String pid, Map<String, Object> variables);

    /**
     * 签收操作
     * @param taskId
     * @param userId
     * @return
     */
    AjaxJson claim(String taskId, String userId);

    /**
     * 回退签收注入
     * @param id
     * @return
     */
    AjaxJson backClaim(String id, String userId, String type);

    /**
     * 更新流程中的业务id
     * @param taskId
     * @param id
     */
    void updateBussinesId(String taskId, Integer id);
}
