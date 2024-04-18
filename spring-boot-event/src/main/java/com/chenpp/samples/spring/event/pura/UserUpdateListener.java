package com.chenpp.samples.spring.event.pura;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author April.Chen
 * @date 2024/4/18 16:29
 */
@Component
public class UserUpdateListener {

    @EventListener
//    @EventListener(condition = "#event.user.name == '张三'")
//    @EventListener(condition = "#root.event.user.age > 18")
//    @EventListener(condition = "#root.args[0].user.gender == '男'")
//    @EventListener(condition = "@userService.isAdmin(#event.user)")
//    @EventListener(condition = "#event.user.score >= ${user.minScore}")
    @Async
    public void onUserUpdate(UserUpdateEvent event) {
        System.out.println("Listening to user update event.");
    }
}
