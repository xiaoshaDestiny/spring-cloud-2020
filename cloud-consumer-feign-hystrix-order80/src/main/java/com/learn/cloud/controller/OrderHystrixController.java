package com.learn.cloud.controller;

import com.learn.cloud.service.PaymentHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xrb
 * @create 2020-04-19 23:40
 */
@RestController
@Slf4j
@DefaultProperties(defaultFallback = "paymentGlobalFallbackMethod")
public class OrderHystrixController {
    @Resource
    private PaymentHystrixService paymentHystrixService;

    /**
     * 正常访问的
     * @param id
     * @return
     */
    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        return paymentHystrixService.paymentInfo_OK(id);
    }

    /**
     * 有单独指明服务降级方法的
     * @param id
     * @return
     */
    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
    @HystrixCommand(fallbackMethod = "paymentTimeOutFallBackMethod",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")
    })
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
        return paymentHystrixService.paymentInfo_TimeOut(id);
    }


    /**
     * 使用全局服务降级方法
     * @param id
     * @return
     */
    @GetMapping("/consumer/payment/hystrix/timeout/global/{id}")
    @HystrixCommand
    public String paymentInfoTimeOut(@PathVariable("id") Integer id){
        return paymentHystrixService.paymentInfo_TimeOut(id);
    }

    /**
     * 单独指明的服务降级兜底方法
     * @param id
     * @return
     */
    public String paymentTimeOutFallBackMethod(@PathVariable("id") Integer id){
        return " 一对一  我是消费者80,对方支付系统繁忙，请10秒之后再次尝试。（000||000） id = " + id;
    }

    /**
     * 全局fallback方法
     * @return
     */
    public String paymentGlobalFallbackMethod(){
        return "global  全局 异常处理信息，请稍后再尝试！~~~";
    }
}
