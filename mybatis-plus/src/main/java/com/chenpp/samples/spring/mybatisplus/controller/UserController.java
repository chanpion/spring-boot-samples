package com.chenpp.samples.spring.mybatisplus.controller;

import com.chenpp.samples.spring.mybatisplus.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author April.Chen
 * @date 2023/4/27 2:15 下午
 **/
@Slf4j
@RestController
public class UserController {

    @Resource
    private UserMapper userMapper;

    @GetMapping("/user/all")
    public Object getAll() {
        log.info("get all user");
        return userMapper.selectList(null);
    }
}
