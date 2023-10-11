package com.chenpp.spring.samples.i18n.controller;

import com.chenpp.spring.samples.i18n.config.I18nUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author April.Chen
 * @date 2023/9/11 8:02 下午
 **/
@RestController
@RequestMapping("/i18n")
public class I18nController {

    @GetMapping("/{key}")
    public Object getValue(@PathVariable String key) {
        return I18nUtil.getLocaleMessage(key);
    }
}
