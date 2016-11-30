package com.qihang.winter.winterdao.factory;

import com.qihang.winter.winterdao.annotation.WinterDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Set;

/**
 * Dao扫描层
 *
 * @author Zerrion
 * @date 2014年11月15日 下午9:46:34
 */
public class WinterDaoClassPathMapperScanner extends ClassPathBeanDefinitionScanner {

  private static final Logger logger = LoggerFactory.getLogger(WinterDaoClassPathMapperScanner.class);

  public WinterDaoClassPathMapperScanner(BeanDefinitionRegistry registry, Class<? extends Annotation> annotation) {
    super(registry, false);
    addIncludeFilter(new AnnotationTypeFilter(annotation));
    if (!WinterDao.class.equals(annotation)) {
      addIncludeFilter(new AnnotationTypeFilter(WinterDao.class));
    }
  }

  @Override
  public Set<BeanDefinitionHolder> doScan(String... basePackages) {
    Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);

    if (beanDefinitions.isEmpty()) {
      logger.warn("No Dao interface was found in '" + Arrays.toString(basePackages) + "' package. Please check your configuration.");
    }
    GenericBeanDefinition definition;
    for (BeanDefinitionHolder holder : beanDefinitions) {
      definition = (GenericBeanDefinition) holder.getBeanDefinition();
      definition.getPropertyValues().add("proxy", getRegistry().getBeanDefinition("winterDaoHandler"));
      definition.getPropertyValues().add("daoInterface", definition.getBeanClassName());
      if (logger.isInfoEnabled()) {
        logger.info("register winterdao name is {}", definition.getBeanClassName());
      }
      definition.setBeanClass(WinterDaoBeanFactory.class);
    }

    return beanDefinitions;
  }

  /**
   * 默认不允许接口的,这里重写,覆盖下,另外默认会Scan @Component 这样所以的被@Component 注解的都会Scan
   */
  @Override
  protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
    return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
  }

}
