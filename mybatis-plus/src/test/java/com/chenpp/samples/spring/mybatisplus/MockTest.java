package com.chenpp.samples.spring.mybatisplus;

import com.chenpp.samples.spring.mybatisplus.entity.User;
import com.chenpp.samples.spring.mybatisplus.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

/**
 * @author April.Chen
 * @date 2023/4/27 2:13 下午
 **/
@RunWith(MockitoJUnitRunner.class)
@AutoConfigureMockMvc
public class MockTest {

    @Mock
    private UserMapper userMapper;

    @Test
    public void testMock() {
        User user = userMapper.selectById(1);
        System.out.println(user);
    }
}
