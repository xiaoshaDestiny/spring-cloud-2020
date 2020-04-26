package com.learn.cloud.controller;

import com.learn.cloud.entities.CommonResult;
import com.learn.cloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @author xu.rb
 * @since 2020-04-26 22:39
 */
@RestController
@Slf4j
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    private static HashMap<Long,Payment> map = new HashMap<>();

    static {
        map.put(1L,new Payment(1L,"hello word 01"));
        map.put(2L,new Payment(2L,"hello word 02"));
        map.put(3L,new Payment(3L,"hello word 03"));
    }

    @GetMapping("/paymentSQL/{id}")
    public CommonResult<Payment> paymentSQL(@PathVariable("id") Long id){
        Payment payment = map.get(id);
        CommonResult result = new CommonResult(200, "from mysql port:" + serverPort,payment);
        log.info(result.toString());
        return result;
    }
}
