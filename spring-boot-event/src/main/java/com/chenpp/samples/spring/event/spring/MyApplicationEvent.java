package com.chenpp.samples.spring.event.spring;

import org.springframework.context.ApplicationEvent;

/**
 * @author April.Chen
 * @date 2024/4/18 16:35
 */
public class MyApplicationEvent extends ApplicationEvent {
    public MyApplicationEvent(Object source) {
        super(source);
    }
}