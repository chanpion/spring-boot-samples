package com.chenpp.spring.samples.i18n.config;

import com.chenpp.spring.samples.i18n.util.SpringUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;

import java.util.Locale;

/**
 * 国际化信息工具类
 *
 * @author April.Chen
 * @date 2022/6/10
 */
public class I18nUtil {
    private static final Logger log = LoggerFactory.getLogger(I18nUtil.class);

    private I18nUtil(){
        // 工具类，严禁实例化
    }

    public static MessageSource getMessageSource() {
        return SpringUtil.getBean(MessageSource.class);
    }

    /**
     * 获取国际化后内容信息
     *
     * @param localeMessageEnum 国际化洗洗枚举
     * @return 国际化后内容信息
     */
    public static String getLocaleMessage(LocaleMessageEnum localeMessageEnum) {
        String key = localeMessageEnum.name().toLowerCase();
        return getLocaleMessage(key, key);
    }

    /**
     * 获取国际化后内容信息
     *
     * @param key 国际化 key
     * @return 国际化后内容信息
     */
    public static String getLocaleMessage(String key) {
        return getLocaleMessage(key, key);
    }

    /**
     * 获取站内信指定语言 目前只支持 中文与英文两类 默认英文
     *
     * @param key            国际化 key
     * @param defaultMessage 默认值
     * @return 国际化后内容信息
     */
    public static String getLocaleMessage(String key, String defaultMessage) {
        return getLocaleMessage(key, null, defaultMessage);
    }

    /**
     * 获取站内信指定语言 目前只支持 中文与英文两类 默认英文
     *
     * @param key  国际化 key
     * @param args 参数
     * @return 国际化后内容信息
     */
    public static String getLocaleMessage(String key, Object[] args) {
        return getLocaleMessage(key, args, key);
    }

    /**
     * 获取站内信指定语言 目前只支持 中文与英文两类 默认英文
     *
     * @param localeMessageEnum LocaleMessageEnum
     * @param args              参数
     * @return 国际化后内容信息
     */
    public static String getLocaleMessage(LocaleMessageEnum localeMessageEnum, Object... args) {
        String key = localeMessageEnum.name().toLowerCase();
        return getLocaleMessage(key, args, key);
    }

    public static String getLocaleMessage(String key, Object[] args, String defaultMessage) {
        if (StringUtils.isBlank(key)) {
            return defaultMessage;
        }
        Locale locale = LocaleContextHolder.getLocale();
        if (!StringUtils.equalsAny(locale.getLanguage(), "cn", "en")) {
            locale = new Locale("cn");
            LocaleContextHolder.setLocale(locale);
        }
        String message = defaultMessage;
        try {
            message = getMessageSource().getMessage(key.toLowerCase(), args, locale);
        } catch (NoSuchMessageException e) {
            // 已知的异常
            log.warn("国际化参数未找到配套文案===>{}", e.getMessage());
            message = getMessageSource().getMessage(key, args, locale);
        } catch (Exception e) {
            log.error("国际化参数获取失败===>{}", ExceptionUtils.getRootCauseMessage(e));
        }
        return message;
    }

    /**
     * 是否
     * @return 是否中文
     */
    public static boolean isChineseLocale() {
        Locale locale = LocaleContextHolder.getLocale();
        return StringUtils.containsIgnoreCase(locale.getLanguage(), "cn");
    }
}
