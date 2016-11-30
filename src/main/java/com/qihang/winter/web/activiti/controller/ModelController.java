package com.qihang.winter.web.activiti.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.qihang.winter.core.common.controller.BaseController;
import com.qihang.winter.core.common.model.json.AjaxJson;
import com.qihang.winter.core.common.model.json.DataGrid;
import com.qihang.winter.core.util.StringUtil;
import com.qihang.winter.tag.core.easyui.TagUtil;
import com.qihang.winter.core.util.String2XMLUtil;
import com.qihang.winter.web.system.pojo.base.TaskNodeEntity;
import com.qihang.winter.web.system.service.SystemService;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 流程模型控制器
 *
 * @author henryyan
 */
@Controller
@RequestMapping(value = "/modelController")
public class ModelController extends BaseController {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    SystemService systemService;
    /**
     * 模型列表
     */
    @RequestMapping(params = "list")
    public ModelAndView modelList() {
        ModelAndView mav = new ModelAndView("workflow/model-list");
        List<Model> list = repositoryService.createModelQuery().list();
        mav.addObject("list", list);
        return mav;
    }

    /**
     * 创建模型
     */
    @RequestMapping(params = "create")
    public void create(@RequestParam("name") String name, @RequestParam("key") String key, @RequestParam("description") String description,
                       HttpServletRequest request, HttpServletResponse response) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode editorNode = objectMapper.createObjectNode();
            editorNode.put("id", "canvas");
            editorNode.put("resourceId", "canvas");
            ObjectNode stencilSetNode = objectMapper.createObjectNode();
            stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
            editorNode.put("stencilset", stencilSetNode);
            Model modelData = repositoryService.newModel();

            ObjectNode modelObjectNode = objectMapper.createObjectNode();
            modelObjectNode.put(ModelDataJsonConstants.MODEL_NAME, name);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_REVISION, 1);
            description = StringUtils.defaultString(description);
            modelObjectNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
            modelData.setMetaInfo(modelObjectNode.toString());
            modelData.setName(name);
            modelData.setKey(StringUtils.defaultString(key));

            repositoryService.saveModel(modelData);
            repositoryService.addModelEditorSource(modelData.getId(), editorNode.toString().getBytes("utf-8"));

            response.sendRedirect(request.getContextPath() + "/modeler.html?modelId=" + modelData.getId());
        } catch (Exception e) {
            logger.error("创建模型失败：", e);
        }
    }

    /**
     * 根据Model部署流程
     */
    @RequestMapping(params = "deploy")
    public String deploy(@RequestParam("modelId") String modelId, RedirectAttributes redirectAttributes) {
        try {
            Model modelData = repositoryService.getModel(modelId);
            ObjectNode modelNode = (ObjectNode) new ObjectMapper().readTree(repositoryService.getModelEditorSource(modelData.getId()));
            byte[] bpmnBytes = null;

            BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
            bpmnBytes = new BpmnXMLConverter().convertToXML(model);

            //解析xml，将各个节点数据保存
            String xmlStr = new String(bpmnBytes,"UTF-8");
            Document document = new String2XMLUtil().strChangeXML(xmlStr);
            String processInstanceId = modelData.getName()+":"+modelData.getVersion()+":"+modelId;
            saveTaskInfo(document,processInstanceId);

            String processName = modelData.getName() + ".bpmn20.xml";
            Deployment deployment = repositoryService.createDeployment().name(modelData.getName()).addString(processName, new String(bpmnBytes)).deploy();
            redirectAttributes.addFlashAttribute("message", "部署成功，部署ID=" + deployment.getId());
        } catch (Exception e) {
            logger.error("根据模型部署流程失败：modelId={}", modelId, e);
        }
        return "redirect:/workflow/model/list";
    }

    /**
     * 导出model对象为指定类型
     *
     * @param modelId 模型ID
     * @param type    导出文件类型(bpmn\json)
     */
    @RequestMapping(params = "export/{modelId}/{type}")
    public void export(@PathVariable("modelId") String modelId,
                       @PathVariable("type") String type,
                       HttpServletResponse response) {
        try {
            Model modelData = repositoryService.getModel(modelId);
            BpmnJsonConverter jsonConverter = new BpmnJsonConverter();
            byte[] modelEditorSource = repositoryService.getModelEditorSource(modelData.getId());

            JsonNode editorNode = new ObjectMapper().readTree(modelEditorSource);
            BpmnModel bpmnModel = jsonConverter.convertToBpmnModel(editorNode);

            // 处理异常
            if (bpmnModel.getMainProcess() == null) {
                response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
                response.getOutputStream().println("no main process, can't export for type: " + type);
                response.flushBuffer();
                return;
            }

            String filename = "";
            byte[] exportBytes = null;

            String mainProcessId = bpmnModel.getMainProcess().getId();

            if (type.equals("bpmn")) {

                BpmnXMLConverter xmlConverter = new BpmnXMLConverter();
                exportBytes = xmlConverter.convertToXML(bpmnModel);

                filename = mainProcessId + ".bpmn20.xml";
            } else if (type.equals("json")) {

                exportBytes = modelEditorSource;
                filename = mainProcessId + ".json";

            }

            ByteArrayInputStream in = new ByteArrayInputStream(exportBytes);
            IOUtils.copy(in, response.getOutputStream());

            response.setHeader("Content-Disposition", "attachment; filename=" + filename);
            response.flushBuffer();
        } catch (Exception e) {
            logger.error("导出model的xml文件失败：modelId={}, type={}", modelId, type, e);
        }
    }

    @RequestMapping(params = "delete/{modelId}")
    public String delete(@PathVariable("modelId") String modelId) {
        repositoryService.deleteModel(modelId);
        return "redirect:/workflow/model/list";
    }

    //保存节点数据方法
    public void saveTaskInfo(Document document,String processInstanceId) throws DocumentException {

        String delSql = "delete from t_act_task where process_instance_id=?";
        systemService.executeSql(delSql,processInstanceId);
        SAXReader sr = new SAXReader();//获取读取xml的对象。
        Document doc = document;//得到xml所在位置。然后开始读取。并将数据放入doc中
        Element el_root = doc.getRootElement();//向外取数据，获取xml的根节点。
        List list = el_root.elements("process");
        List<Map<String,Object>> taskInfoList = new ArrayList<Map<String,Object>>();
        for(Object obj : list){
            Element row = (Element) obj;
            List userTasks = row.elements("userTask");
            List sequenceFlows = row.elements("sequenceFlow");
            List conditionExpression = row.elements("conditionExpression");
            for(Object userTask : userTasks){
                Element task = (Element) userTask;
                List attrList = task.attributes();
                String id = "";
                TaskNodeEntity entity = new TaskNodeEntity();
                for(int i=0 ; i < attrList.size() ; i++){
                    Attribute item = (Attribute) attrList.get(i);
                    String itemName = item.getName();
                    if(item.getName().equals("id")) {
                        entity.setTaskId(item.getValue());
                    }else if(item.getName().equals("name")){
                        entity.setTaskName(item.getValue());
                    }else{
                        entity.setInfoType(item.getName());
                        String value = item.getValue().substring(2,item.getValue().length()-1);
                        entity.setInfoValue(value);
                    }
                    String judgeSequence = "";
                    for(Object sequenceFlow : sequenceFlows){
                        Element flow = (Element) sequenceFlow;
                        List attrs = flow.attributes();
                        for(Object attr : attrs){
                            Attribute attribute = (Attribute) attr;
                            String attributeName = attribute.getName();
                            if("sourceRef".equals(attributeName)){
                                String sourceRef = attribute.getValue();
                                if(sourceRef.equals(entity.getTaskId())){
                                    for(Object attr2 : attrs){
                                        Attribute attribute2 = (Attribute) attr2;
                                        String attributeName2 = attribute2.getName();
                                        if("targetRef".equals(attributeName2)){
                                            judgeSequence = attribute2.getValue();
                                        }
                                    }
                                }
                            }
                        }
                    }
                    if(StringUtils.isNotEmpty(judgeSequence)) {
                        for (Object sequenceFlow : sequenceFlows) {
                            Element flow = (Element) sequenceFlow;
                            List attrs = flow.attributes();
                            for (Object attr : attrs) {
                                Attribute attribute = (Attribute) attr;
                                String attributeValue = attribute.getValue();
                                if (judgeSequence.equals(attributeValue)) {
                                    List conditionElementList = flow.elements("conditionExpression");
                                    for (Object conditionElement : conditionElementList) {
                                        Element condition = (Element) conditionElement;
                                        String value = condition.getText();
                                        if (value.indexOf("!") > -1) {
                                            value = value.substring(3, value.length() - 1);
                                        } else {
                                            value = value.substring(2, value.length() - 1);
                                        }
                                        entity.setJudgeInfo(value);
                                    }
                                }
                            }
                        }
                    }
                }
                entity.setProcessInstanceId(processInstanceId);
                entity.setTaskStatus(0);
                systemService.saveOrUpdate(entity);
            }
        }
       // return taskInfoList;
    }
}
