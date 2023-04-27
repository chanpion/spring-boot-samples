package com.chenpp.samples.spring.mybatisplus;

import com.chenpp.samples.spring.mybatisplus.Application;
import com.chenpp.samples.spring.mybatisplus.entity.User;
import com.chenpp.samples.spring.mybatisplus.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author April.Chen
 * @date 2023/4/27 11:57 上午
 **/
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@WebMvcTest
public class SampleTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

}