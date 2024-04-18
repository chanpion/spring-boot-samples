package com.chenpp.samples.spring.event.pura;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * @author April.Chen
 * @date 2024/4/18 16:28
 */
@Component
public class UserUpdatePublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(User user) {
        System.out.println("Publishing user update event.");
        UserUpdateEvent userUpdateEvent = new UserUpdateEvent(user);
        applicationEventPublisher.publishEvent(userUpdateEvent);
    }
}
