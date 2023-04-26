package com.chenpp.spring.samples.tkmybatis.controller;

import com.chenpp.spring.samples.tkmybatis.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author April.Chen
 * @date 2023/4/26 2:00 下午
 **/
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping("all")
    public Object all() {
        return userService.selectAll();
    }
}
