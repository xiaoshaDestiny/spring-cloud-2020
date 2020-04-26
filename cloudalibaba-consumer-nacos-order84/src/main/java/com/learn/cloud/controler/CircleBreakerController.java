package com.learn.cloud.controler;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.learn.cloud.entities.CommonResult;
import com.learn.cloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * @author xu.rb
 * @since 2020-04-26 23:00
 */
@RestController
@Slf4j
public class CircleBreakerController {

    public static final String SERVICE_URL = "http://nacos-payment-provider";

    @Resource
    private RestTemplate restTemplate;

    @RequestMapping("/consumer/fallback/{id}")
    @SentinelResource(value = "fallback")
    public CommonResult<Payment> fallback(@PathVariable("id") Long id){

        CommonResult result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, CommonResult.class, id);
        if(id == 4){
            throw  new IllegalArgumentException("IllegalArgumentException  非法参数异常.....");
        }else if(result.getData() == null){

            throw new NullPointerException("NullPointerException ,没有对应ID的记录，空指针异常");
        }
        return  result;
    }
}
