package com.chenpp.spring.samples.i18n.controller;

import com.chenpp.spring.samples.i18n.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author April.Chen
 * @date 2023/9/12 9:44 上午
 **/
@Validated
@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {


    @PostMapping("/add")
    public Object addAccount(@Valid @RequestBody Account account) {

        log.info("account: {}", account);
        return account;
    }

    @GetMapping("/get")
    public Account getAccount(@NotBlank String name) {
        Account account = new Account();
        account.setName(name);
        return account;
    }
}
