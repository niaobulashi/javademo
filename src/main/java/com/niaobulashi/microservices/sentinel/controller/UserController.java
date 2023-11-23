package com.niaobulashi.microservices.sentinel.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: UserController
 * @date: 2023/11/15 20:49
 * @author: HuLang
 * @version: V1.0
 **/
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {
    private static final String RESOURCE_NAME = "hello";

    @RequestMapping("/hello")
    public String hello() {
        log.info("hello");
        return "hello";
    }
}
