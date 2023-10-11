package com.chenpp.spring.samples.i18n.controller;

import com.alibaba.fastjson.JSONObject;
import com.chenpp.spring.samples.i18n.entity.Account;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author April.Chen
 * @date 2023/9/13 7:42 下午
 **/
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = AccountController.class)

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AccountControllerTest {
    @Resource
    private MockMvc mvc;

    private Cookie cookie = null;

    @Before
    public void before() {
        cookie = new Cookie("lang", "en");
    }

    @Test
    public void testAddAccount() throws Exception {
        Account account = new Account();
        String body = JSONObject.toJSONString(account);
        MvcResult result = mvc.perform(post("/account/add")
                .contentType("application/json")
                .cookie(cookie)
                .content(body))
                .andExpect(status().isOk())
//                .andExpect(content().string(containsString("text")))
                .andReturn();

        String content = result.getResponse().getContentAsString();
        System.out.println(content);
        System.out.println(containsChineseCharacters(content));
    }

    @Test
    public void whenPathVariableIsInvalid_thenReturnsStatus400() throws Exception {
        mvc.perform(get("/validatePathVariable/3"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void whenRequestParameterIsInvalid_thenReturnsStatus400() throws Exception {
        mvc.perform(get("/validateRequestParameter")
                .param("param", "3"))
                .andExpect(status().isBadRequest());
    }

    public static boolean containsChineseCharacters(String str) {
        if (str == null) {
            return false;
        }
        String regex = "[\u4e00-\u9fa5]+";
        return str.matches(".*" + regex + ".*");
    }
}
