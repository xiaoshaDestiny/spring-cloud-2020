package com.learn.cloud.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author xrb
 * @create 2020-04-18 17:00
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/consul")
    public String consul(){
        return "spring cloud with consul: "+serverPort+"\t"+UUID.randomUUID().toString();
    }
}