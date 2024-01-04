package com.chanpion.samples.quartz.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author April.Chen
 */
@Component
public class SpringUtil implements ApplicationContextAware {
    private static ApplicationContext applicationContext;
    private static DefaultListableBeanFactory beanFactory;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
        beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static <T> T getBean(String name) throws BeansException {
        return (T) applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) throws BeansException {
        return applicationContext.getBean(clazz);
    }

    public static <T> T getBean(String name, Class<T> type) throws BeansException {
        return applicationContext.getBean(name, type);
    }

    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return applicationContext.getBeansOfType(clazz);
    }

    /**
     * 注册bean到Spring容器
     *
     * @param clazz
     */
    public static void registerBean(Class clazz) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
        beanFactory.registerBeanDefinition(clazz.getSimpleName(), beanDefinitionBuilder.getRawBeanDefinition());
    }
}
