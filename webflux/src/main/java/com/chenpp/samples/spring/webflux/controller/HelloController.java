package com.chenpp.samples.spring.webflux.controller;

import com.chenpp.samples.spring.webflux.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

import javax.annotation.Resource;

/**
 * @author April.Chen
 * @date 2024/3/27 16:49
 */
@RestController
public class HelloController {

    @Resource
    private HelloService helloService;

    @Resource
    private Scheduler scheduler;

    @GetMapping("/hello")
    public Mono<String> hello(@RequestParam String id) {
        return Mono.just("Hello Webflux")
                .publishOn(scheduler)
                .map(s -> helloService.hello(id));
    }
}
