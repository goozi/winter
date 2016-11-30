package com.qihang.winter.web.activiti.util;


import org.activiti.engine.ActivitiException;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.FatalBeanException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by LenovoM4550 on 2016/1/21.
 */
public class WorkflowDeployer implements InitializingBean, ApplicationContextAware {

  private static final Logger LOGGER = LoggerFactory.getLogger(WorkflowDeployer.class);

  private Resource[] deploymentResources;

  public void setDeploymentResources(Resource[] resources) {
    this.deploymentResources = resources;
  }

  private String category;

  public void setCategory(String category) {
    this.category = category;
  }

  ApplicationContext appCtx;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.appCtx = applicationContext;
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    if (category == null) {
      throw new FatalBeanException("属性未配置: category");
    }
    if (deploymentResources != null) {
      RepositoryService repositoryService = appCtx.getBean(RepositoryService.class);
      for (Resource r : deploymentResources) {
        String deploymentName = category + "_" + r.getFilename();
        String resourceName = r.getFilename();
        boolean doDeploy = true;
        List<Deployment> deployments = repositoryService.createDeploymentQuery().deploymentName(deploymentName).orderByDeploymenTime().desc().list();
        if (!deployments.isEmpty()) {
          Deployment existing = deployments.get(0);
          try {
            InputStream in = repositoryService.getResourceAsStream(existing.getId(), resourceName);
            if (in != null) {
              File f = File.createTempFile("deployment", "xml", new File(System.getProperty("java.io.tmpdir")));
              f.deleteOnExit();
              OutputStream out = new FileOutputStream(f);
              IOUtils.copy(in, out);
              in.close();
              out.close();
              //判断部署目录中的文件和库中流程文件是否一致，有改变则重新部署
              doDeploy = (FileUtils.checksumCRC32(f) != FileUtils.checksumCRC32(r.getFile()));
            } else
              throw new ActivitiException("不能读取资源" + resourceName + ", 输入流为空");
          } catch (ActivitiException ex) {
            //do nothing, simply re-deploy
            LOGGER.error("Unable to read " + resourceName + " of deployment " + existing.getName() + ", id: " + existing.getId() + ", will re-deploy");
          }
        }
        if (doDeploy) {
          repositoryService.createDeployment()
                  .name(deploymentName)
                  .addInputStream(resourceName, r.getInputStream())
                  .deploy();
          LOGGER.warn("部署BPMN20资源 " + r.getFilename());
        }
      }
    }
  }

}
