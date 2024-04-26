package com.chenpp.samples.hive;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.Resource;

/**
 * @author April.Chen
 * @date 2024/4/26 11:02
 */
@SpringBootApplication
public class HiveApplication implements ApplicationRunner {

    @Resource
    private HiveService hiveService;
    public static void main(String[] args) {
        SpringApplication.run(HiveApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        hiveService.query();
    }
}
