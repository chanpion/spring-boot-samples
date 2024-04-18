package com.chenpp.samples.spring.event.spring;

import org.springframework.context.ApplicationListener;

/**
 * @author April.Chen
 * @date 2024/4/18 16:35
 */
public class MyApplicationListener implements ApplicationListener<MyApplicationEvent> {
    @Override
    public void onApplicationEvent(MyApplicationEvent event) {
        System.out.println("收到事件：" + event);
    }
}