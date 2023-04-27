package com.chenpp.samples.spring.mybatisplus;

import com.baomidou.mybatisplus.test.autoconfigure.MybatisPlusTest;
import com.chenpp.samples.spring.mybatisplus.entity.User;
import com.chenpp.samples.spring.mybatisplus.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author April.Chen
 * @date 2023/4/27 1:39 下午
 **/
@MybatisPlusTest
public class MybatisPlusSampleTest {
    @Autowired
    private UserMapper sampleMapper;

    @Test
    void testInsert() {
        User sample = new User();
        sample.setName("chenpp");
        sampleMapper.insert(sample);
        assertThat(sample.getId()).isNotNull();
    }
}
