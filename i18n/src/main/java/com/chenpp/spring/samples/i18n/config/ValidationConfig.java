package com.chenpp.spring.samples.i18n.config;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.validation.MessageInterpolatorFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import javax.validation.Validation;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Locale;

/**
 * @author April.Chen
 * @date 2023/1/11 2:19 下午
 **/
@ConditionalOnProperty("spring.messages.basename")
@Configuration
public class ValidationConfig implements WebMvcConfigurer {

    @Resource
    private MessageSource messageSource;

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 解决controller返回字符串中文乱码问题
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
            } else if (converter instanceof MappingJackson2HttpMessageConverter) {
                ((MappingJackson2HttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
            }
        }
    }

    /**
     * 使用自定义LocalValidatorFactoryBean，
     * 设置Spring国际化消息源
     */
    @Bean
    @Override
    public Validator getValidator() {
        ResourceBundleMessageSource resourceBundleMessageSource = (ResourceBundleMessageSource) this.messageSource;

        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        //仅兼容Spring Boot spring.messages设置的国际化文件和原hibernate-validator的国际化文件
        //不支持resource/ValidationMessages.properties系列
        bean.setValidationMessageSource(this.messageSource);

        bean.getValidationPropertyMap().put(HibernateValidatorConfiguration.FAIL_FAST, Boolean.TRUE.toString());

        MessageInterpolatorFactory interpolatorFactory = new MessageInterpolatorFactory();
        bean.setMessageInterpolator(interpolatorFactory.getObject());
//        bean.setMessageInterpolator(new MessageInterpolator());
        return bean;
    }

    private class MessageInterpolator extends ResourceBundleMessageInterpolator {

        @Override
        public String interpolate(String message, Context context, Locale locale) {
            if (message.startsWith("{") && message.endsWith("}")) {
                return super.interpolate(message, context, locale);
            }
            //如果是中文，并且自定义了message则直接返回
            if (StringUtils.containsIgnoreCase(locale.getLanguage(), "cn") && !(message.startsWith("{") && message.endsWith("}"))) {
                return super.interpolate(message, context, locale);
            }

            //其他语言安装validation框架默认处理
            String annotationTypeName = context.getConstraintDescriptor().getAnnotation().annotationType().getName();
            message = String.format("{%s.message}", annotationTypeName);
            return super.interpolate(message, context, locale);
        }
    }
}
