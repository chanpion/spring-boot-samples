package com.chenpp.spring.samples.i18n.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author 山人二小
 * @date 2020-01-13 10:26
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    private static void setContext(ApplicationContext applicationContext) {
        SpringUtil.applicationContext = applicationContext;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        setContext(applicationContext);
    }

    /**
     * 获得spring上下文
     *
     * @return ApplicationContext spring上下文
     */
    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * 获取bean
     *
     * @param name service注解方式name为小驼峰格式
     * @return Object bean的实例对象
     */
    public static Object getBean(String name) throws BeansException {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    public static  <T> List<T> loadSPI(Class<T> clazz){
      return SpringFactoriesLoader.loadFactories(clazz, Thread.currentThread().getContextClassLoader());
    }
}