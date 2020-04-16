package com.learn.cloud.controller;

import com.learn.cloud.entities.CommonResult;
import com.learn.cloud.entities.Payment;
import com.learn.cloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xrb
 * @since 2020-04-17 0:14
 */
@RestController(value = "/payment")
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @PostMapping(value = "/create")
    public CommonResult create(Payment payment){
        int result = paymentService.create(payment);
        log.info("**************插入结果："+result);
        if(result > 0){
            return  new CommonResult(200,"插入成功",result);
        }else {
            return new CommonResult(444,"插入数据库失败",null);
        }
    }

    @GetMapping(value = "/get/{id}")
    public CommonResult create(@PathVariable("id") Long id){
        Payment result = paymentService.getPaymentById(id);
        log.info("**************查询结果："+result);
        if(result != null){
            return  new CommonResult(200,"查询成功",result);
        }else {
            return new CommonResult(444,"查询失败，没有对应记录："+id,null);
        }
    }
}
