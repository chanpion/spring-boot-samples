package com.chenpp.samples.spring.webflux.service;

/**
 * @author April.Chen
 * @date 2024/3/27 16:49
 */
public class HelloService {

    public String hello(String id) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "hello: " + id;
    }
}
