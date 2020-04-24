package com.learn.cloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author xu.rb
 * @since 2020-04-24 11:16
 */
@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/nacos/{id}")
    public String getPayment(@PathVariable("id")  Integer id){
        String result = "id is : <"+ id+">nacos register  server port is :<"+serverPort+">  uuid is :<"+UUID.randomUUID().toString()+">";
        log.info(result);
        return result;
    }
}
