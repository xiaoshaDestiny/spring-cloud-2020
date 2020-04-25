package com.learn.cloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xu.rb
 * @since 2020-04-25 21:52
 */
@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping("/testA")
    public String getA(){
        return "-------- testA";
    }
    @GetMapping("/testB")
    public String getB(){
        return "-------- testB";
    }
}
