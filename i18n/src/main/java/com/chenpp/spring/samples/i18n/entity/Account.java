package com.chenpp.spring.samples.i18n.entity;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author April.Chen
 * @date 2023/9/12 9:29 上午
 **/
@Data
public class Account {

//    @NotBlank(message = "{com.chenpp.spring.samples.i18n.entity.account.name.NotBlank}")
    @NotBlank(message = "{i18n.account.name.NotBlank}")
    private String name;

    @Min(value = 1L, message = "{com.chenpp.spring.samples.i18n.entity.Account.balance.Min}")
    private double balance;

}
