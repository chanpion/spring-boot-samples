package com.chenpp.spring.samples.tkmybatis.service;

import com.chenpp.spring.samples.tkmybatis.dao.UserMapper;
import com.chenpp.spring.samples.tkmybatis.entity.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author April.Chen
 * @date 2023/4/26 1:58 下午
 **/
@Service
public class UserService {

    @Resource
    private UserMapper userMapper;

    public List<User> selectAll() {
        return userMapper.selectAll();
    }
}
