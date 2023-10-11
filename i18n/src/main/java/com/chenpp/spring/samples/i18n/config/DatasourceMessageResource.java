package com.chenpp.spring.samples.i18n.config;

import com.chenpp.spring.samples.i18n.entity.I18nMessage;
import com.chenpp.spring.samples.i18n.mapper.I18nMessageMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author April.Chen
 * @date 2023/9/12 6:53 下午
 **/
@Slf4j
public class DatasourceMessageResource extends AbstractMessageSource implements ResourceLoaderAware, InitializingBean {
    /**
     * default base name
     */
    private static final String DEFAULT_RESOURCE_BUND_BASE_NAME = "messages";

    /**
     * 这个是用来缓存数据库中获取到的配置的 数据库配置更改的时候可以调用reload方法重新加载
     */
    private static final Map<String, Map<String, String>> LOCAL_CACHE = new ConcurrentHashMap<>();

//    @Resource
    private I18nMessageMapper i18nMessageMapper;

    private ResourceLoader resourceLoader;

    @Override
    public void afterPropertiesSet() {
        this.reload();
    }

    /**
     * 重新加载消息到该类的Map缓存中
     */
    public void reload() {

        // 清除该类的缓存
        LOCAL_CACHE.clear();

        // 加载所有的国际化资源
        Map<String, Map<String, String>> localeMsgMap = this.loadAllMessageResources();
        LOCAL_CACHE.putAll(localeMsgMap);
    }

    /**
     * 加载所有的国际化消息资源
     */
    private Map<String, Map<String, String>> loadAllMessageResources() {

        // 从数据库中查询所有的国际化资源
        List<I18nMessage> allLocaleMessage = i18nMessageMapper.selectAll();
        if (ObjectUtils.isEmpty(allLocaleMessage)) {
            allLocaleMessage = new ArrayList<>();
        }

        // 将查询到的国际化资源转换为 Map<地区码, Map<code, 信息>> 的数据格式
        Map<String, Map<String, String>> localeMsgMap = allLocaleMessage
                // stream流
                .stream()
                // 分组
                .collect(Collectors.groupingBy(
                        // 根据国家地区分组
                        I18nMessage::getLocale,
                        // 收集为Map,key为code,value为信息
                        Collectors.toMap(
                                I18nMessage::getCode
                                , I18nMessage::getItem
                        )
                ));

        // 获取国家地区List
        List<Locale> localeList = localeMsgMap.keySet().stream().map(Locale::new).collect(Collectors.toList());
        for (Locale locale : localeList) {

            // 按照国家地区来读取本地的国际化资源文件,我们的国际化资源文件放在i18n文件夹之下
            ResourceBundle resourceBundle = ResourceBundle.getBundle("i18n/messages", locale);
            // 获取国际化资源文件中的key和value
            Set<String> keySet = resourceBundle.keySet();

            // 将 code=信息 格式的数据收集为 Map<code,信息> 的格式
            Map<String, String> msgFromFileMap = keySet.stream()
                    .collect(
                            Collectors.toMap(
                                    Function.identity(),
                                    resourceBundle::getString
                            )
                    );

            // 将本地的国际化信息和数据库中的国际化信息合并
            Map<String, String> localeFileMsgMap = localeMsgMap.get(locale.getLanguage());
            localeFileMsgMap.putAll(msgFromFileMap);
            localeMsgMap.put(locale.getLanguage(), localeFileMsgMap);
        }

        return localeMsgMap;
    }

    @Override
    protected MessageFormat resolveCode(String code, Locale locale) {

        String msg = this.getSourceFromCacheMap(code, locale);
        return new MessageFormat(msg, locale);
    }

    @Override
    protected String resolveCodeWithoutArguments(String code, Locale locale) {
        return this.getSourceFromCacheMap(code, locale);
    }

    /**
     * 缓存Map中加载国际化资源
     */
    private String getSourceFromCacheMap(String code, Locale locale) {

        String language = ObjectUtils.isEmpty(locale)
                ? LocaleContextHolder.getLocale().getLanguage() : locale.getLanguage();

        // 获取缓存中对应语言的所有数据项
        Map<String, String> propMap = LOCAL_CACHE.get(language);
        if (!ObjectUtils.isEmpty(propMap) && propMap.containsKey(code)) {
            // 如果对应语言中能匹配到数据项，那么直接返回
            return propMap.get(code);
        }

        // 如果找不到国际化消息,就直接返回code
        return code;
    }


    private String checkFromCachedOrBundResource(String code, Locale locale) {
        String language = locale.getLanguage();
        Map<String, String> props = LOCAL_CACHE.get(language);
        if (null != props && props.containsKey(code)) {
            return props.get(code);
        } else {
            //check from parent message resource. and catch no such element exception.
            try {
                if (null != this.getParentMessageSource()) {
                    return this.getParentMessageSource().getMessage(code, null, locale);
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
            }
            return code;
        }
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = (resourceLoader == null ? new DefaultResourceLoader() : resourceLoader);
    }
}
