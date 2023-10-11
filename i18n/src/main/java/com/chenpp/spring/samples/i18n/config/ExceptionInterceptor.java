package com.chenpp.spring.samples.i18n.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

/**
 * @author April.Chen
 * @date 2023/9/12 10:25 上午
 **/
@Slf4j
@RestControllerAdvice
public class ExceptionInterceptor {

    /**
     * 拦截所有运行时的全局异常
     *
     * @param e 运行异常
     * @return 拦截结果
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e, HandlerMethod method) {
        log.error("request error", e);
        return e.getMessage();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String msg;
        try {
            String errorField = e.getBindingResult().getFieldErrors().get(0).getField();
            String errorMsg = e.getBindingResult().getFieldErrors().get(0).getDefaultMessage();

            if (errorMsg.startsWith("{") && errorMsg.endsWith("}")) {
                errorMsg = StringUtils.substringBetween(errorMsg, "{", "}");
                errorMsg = I18nUtil.getLocaleMessage(errorMsg);
            }

            msg = "[" + errorField + "] " + errorMsg;
        } catch (Exception ex) {
            msg = ex.getMessage();
        }
        return msg;
    }
}
