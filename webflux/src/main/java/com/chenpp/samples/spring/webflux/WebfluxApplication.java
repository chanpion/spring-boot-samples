package com.chenpp.samples.spring.webflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * @author April.Chen
 * @date 2024/3/27 16:48
 */
@SpringBootApplication
public class WebfluxApplication {
    public static void main(String[] args) {
        SpringApplication.run(WebfluxApplication.class, args);
    }

    @Bean
    public Scheduler scheduler() {
        return Schedulers.newBoundedElastic(10, 100, "test-webflux");
    }
}
