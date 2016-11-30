package com.qihang.winter.web.activiti.service.impl;

import com.alibaba.druid.util.StringUtils;

import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.web.activiti.service.IWorkFlowService;
import org.activiti.engine.*;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by LenovoM4550 on 2015/9/23.
 */
@Component
@Transactional
public class WorkFlowServiceImpl implements IWorkFlowService {

    private static Logger logger = LoggerFactory.getLogger(WorkFlowServiceImpl.class);

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    protected TaskService taskService;

    protected RepositoryService rs;

    protected HistoryService historyService;

    protected RepositoryService repositoryService;

    @Autowired
    private IdentityService identityService;


    /**
     * 启动流程
     *
     * 启动流程时，需要获取立案人所在所所长作为下一个节点接收人，同时更改数据状态值以及数据与工作流事项关联
     */
    public ProcessInstance startWorkflow(String caseName,String businessKey,String userId, Map<String, Object> variables) {
        ProcessInstance processInstance = null;
        try {
            // 用来设置启动流程的人员ID，引擎会自动把用户ID保存到activiti:initiator中
            identityService.setAuthenticatedUserId(userId);
            //根据单据Id获取流程Id，判断该单据是否已存在于流程，若存在，关闭流程，再启用
            //List<Integer> loadFlowInfo = baseDao.loadTaskIdById(Integer.parseInt(businessKey),type);
            //启动流程
            //
//            if(loadFlowInfo.size()>0){
//                for(Integer taskId : loadFlowInfo){
//                    runtimeService.suspendProcessInstanceById(taskId.toString());
//                    baseDao.removeRelevance(taskId);
//                }
//            }
            if(!StringUtils.isEmpty(businessKey)) {
                processInstance = runtimeService.startProcessInstanceByKey(caseName, businessKey, variables);
            }else{
                processInstance = runtimeService.startProcessInstanceByKey(caseName,variables);
            }

        } finally {
            identityService.setAuthenticatedUserId(null);
        }
        return processInstance;
    }

    public void complet(String taskId,Map<String,Object> variables,String activityId) {
       // if(StringUtils.isEmpty(activityId)) {
            taskService.complete(taskId, variables);
//        }else{
//            ActivityImpl currActivity = findActivitiImpl(String taskId,String activityId);
//        }
    }


    /**
     * 当前流程节点下一步
     * @param pid 流程id
     * @Param variables 参数变量
     */
    public void completeNextUserTask(String pid,Map<String,Object> variables){
        Task task = taskService.createTaskQuery().processInstanceId(pid).active().singleResult();
            taskService.complete(task.getId(), variables);
    }



    /**
     * 签收操作
     * @param taskId
     * @param userId
     * @return
     */
    public AjaxJson claim(String taskId, String userId) {
        AjaxJson message = new AjaxJson();
        try{
            taskService.claim(taskId, userId);
            message.setSuccess(true);
            message.setMsg("签收成功");
        }catch (Exception e){
            message.setSuccess(false);
            message.setMsg("签收失败");
        }
        return message;
    }

    /**
     * 回退签收注入
     *
     * @param id
     * @param userId
     * @return
     */
    public AjaxJson backClaim(String id, String userId,String type) {
        AjaxJson message = new AjaxJson();
        try{
            List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(id).orderByProcessDefinitionKey().desc().list();
            ProcessInstance processInstance = null;
//            if(type != null && ("punishChk".equals(type) || "caseSource".equals(type))) {
//                processInstance = processInstanceList.get(0);
//            }else{
//                processInstance = processInstanceList.get(1);
//            }
            for(ProcessInstance pro : processInstanceList){
                if(pro.getProcessDefinitionKey().equals(type)){
                    processInstance = pro;
                }
            }
            List<Task> tasks = taskService.createTaskQuery().processInstanceId(processInstance.getId()).active().orderByTaskAssignee().asc().list();
            Task task = tasks.get(0);
            taskService.claim(task.getId(), userId);
            message.setSuccess(true);
        }catch (Exception e){
            message.setSuccess(false);
            message.setMsg(e.getMessage());
        }

        return message;
    }

    public void updateBussinesId(String taskId, Integer id) {
        runtimeService.updateBusinessKey(taskId,id.toString());
    }

}
