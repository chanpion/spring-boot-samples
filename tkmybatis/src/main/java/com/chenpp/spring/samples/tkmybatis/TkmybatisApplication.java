package com.chenpp.spring.samples.tkmybatis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author April.Chen
 * @date 2023/4/26 11:54 上午
 **/
@Slf4j
@tk.mybatis.spring.annotation.MapperScan(basePackages = "com.chenpp")
@SpringBootApplication(scanBasePackages = {"com.chenpp"})
public class TkmybatisApplication {

    public static void main(String[] args) {
        try {
            SpringApplication.run(TkmybatisApplication.class, args);
        } catch (Throwable e) {
            log.error("start error", e);
        }
        log.info("started.");
    }
}
