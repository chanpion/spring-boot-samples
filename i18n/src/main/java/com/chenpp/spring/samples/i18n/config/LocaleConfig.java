package com.chenpp.spring.samples.i18n.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

/**
 * @author April.Chen
 * @date 2022/6/10
 */
@Configuration
@ConditionalOnProperty("spring.messages.basename")
public class LocaleConfig implements WebMvcConfigurer {

//    @Bean
    public DatasourceMessageResource messageResource() {
        return new DatasourceMessageResource();
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        localeResolver.setCookieName("lang");
        localeResolver.setDefaultLocale(new Locale("cn"));
        // 设置cookie有效期
        localeResolver.setCookieMaxAge(3600);
        return localeResolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        CustomLocaleChangeInterceptor localeChangeInterceptor = new CustomLocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加locale拦截器
        registry.addInterceptor(localeChangeInterceptor());
    }

    /**
     * 自定义国际化语言拦截器对response回写cookie
     */
    public class CustomLocaleChangeInterceptor extends LocaleChangeInterceptor {
        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
            Cookie cookie = WebUtils.getCookie(request, "lang");
            if (cookie == null) {
                LocaleResolver localeResolver = RequestContextUtils.getLocaleResolver(request);
                Locale locale = localeResolver.resolveLocale(request);
                cookie = new Cookie("lang", locale.getLanguage());
                cookie.setMaxAge(-1);
                cookie.setPath("/");
            }
            response.addCookie(cookie);
        }
    }
}
