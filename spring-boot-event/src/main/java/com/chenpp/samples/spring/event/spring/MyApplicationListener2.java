package com.chenpp.samples.spring.event.spring;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @author April.Chen
 * @date 2024/4/18 16:36
 */
@Component
public class MyApplicationListener2 {
    @EventListener(MyApplicationEvent.class)
    public void onEvent(MyApplicationEvent event) {
        System.out.println("收到事件：" + event);
    }

}
