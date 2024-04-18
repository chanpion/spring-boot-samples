package com.chenpp.samples.spring.event.pura;

import com.chenpp.samples.spring.event.pura.User;
import com.chenpp.samples.spring.event.pura.UserRepository;
import com.chenpp.samples.spring.event.pura.UserUpdatePublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author April.Chen
 * @date 2024/4/18 16:30
 */
@Service
public class SomeService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserUpdatePublisher userUpdatePublisher;

    public void updateUser(User user) {
        // update user object in database
        userRepository.save(user);

        // publish user update event
        userUpdatePublisher.publishEvent(user);
    }
}