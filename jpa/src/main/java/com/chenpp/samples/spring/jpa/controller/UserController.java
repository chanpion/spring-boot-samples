package com.chenpp.samples.spring.jpa.controller;

import com.chenpp.samples.spring.jpa.dao.UserRepository;
import com.chenpp.samples.spring.jpa.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * @author April.Chen
 * @date 2023/10/17 6:58 下午
 **/
@RestController
public class UserController {

    @Resource
    private UserRepository userRepository;

    @GetMapping("/user/all")
    public List<User> queryAll(){
        return userRepository.findAll();
    }

    @GetMapping("/user/{id}")
    public Optional<User> get(@PathVariable Long id){
        return userRepository.findById(id);
    }
}
