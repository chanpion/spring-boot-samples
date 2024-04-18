package com.chenpp.samples.spring.event.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author April.Chen
 * @date 2024/4/18 16:35
 */
public class ApplicationEventBootstrap {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        // 注册自定义事件监听器
        context.addApplicationListener(new MyApplicationListener());
        // 启动上下文
        context.refresh();
        // 发布事件，事件源为Context
        context.publishEvent(new MyApplicationEvent(context));
        // 结束
        context.close();
    }
}
