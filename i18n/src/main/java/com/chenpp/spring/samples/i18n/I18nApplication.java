package com.chenpp.spring.samples.i18n;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * @author April.Chen
 * @date 2023/9/11 7:59 下午
 **/
@Slf4j
@SpringBootApplication(scanBasePackages = {"com.chenpp"})
public class I18nApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(I18nApplication.class, args);
        } catch (Throwable e) {
            log.error("start error", e);
        }
        log.info("started.");
    }
}
