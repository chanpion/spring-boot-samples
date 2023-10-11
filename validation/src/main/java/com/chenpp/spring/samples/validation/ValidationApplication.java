package com.chenpp.spring.samples.validation;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author April.Chen
 * @date 2023/9/11 7:56 下午
 **/
@Slf4j
@SpringBootApplication(scanBasePackages = {"com.chenpp"})
public class ValidationApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(ValidationApplication.class, args);
        } catch (Throwable e) {
            log.error("start error", e);
        }
        log.info("started.");
    }
}
