package com.chenpp.samples.spring.event.pura;

/**
 * @author April.Chen
 * @date 2024/4/18 16:28
 */
public class UserUpdateEvent {

    private User user;

    public UserUpdateEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
