package com.chenpp.samples.spring.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author April.Chen
 * @date 2023/4/27 11:50 上午
 **/
@SpringBootApplication
@MapperScan("com.chenpp.samples.spring.mybatisplus.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}