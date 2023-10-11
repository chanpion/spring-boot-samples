package com.chenpp.docker.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author April.Chen
 * @date 2023/10/8 1:36 下午
 **/
@RestController
public class HealthController {

    @GetMapping("ok.htm")
    public String ok() {
        return "ok";
    }
}
