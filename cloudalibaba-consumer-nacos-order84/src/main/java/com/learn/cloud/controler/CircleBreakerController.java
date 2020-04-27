package com.learn.cloud.controler;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
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
    //@SentinelResource(value = "fallback")//什么都不配置
    //@SentinelResource(value = "fallback",fallback = "handlerFallback")//只负责业务上的异常
    //@SentinelResource(value = "fallback",blockHandler = "blockHandler")//只配置阻塞异常，需要配置降级规则，才能跑到兜底的方法上
    @SentinelResource(value = "fallback",fallback = "handlerFallback", blockHandler = "blockHandler")//两种都配置  各回各家
    public CommonResult<Payment> fallback(@PathVariable("id") Long id){
        CommonResult result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id, CommonResult.class, id);
        if(id == 4){
            throw  new IllegalArgumentException("IllegalArgumentException  非法参数异常.....");
        }else if(result.getData() == null){

            throw new NullPointerException("NullPointerException ,没有对应ID的记录，空指针异常");
        }
        return  result;
    }



    /**
     * 业务员兜底fallback
     * @param id
     * @param e
     * @return
     */
    public CommonResult handlerFallback(@PathVariable("id") Long id,Throwable e){
        Payment payment = new Payment(id,null);
        return new CommonResult(444,"兜底业务异常，exception is:"+e.getMessage(),payment);
    }

    /**
     * 请求上的限流
     * @param id
     * @param blockException
     * @return
     */
    public CommonResult blockHandler(@PathVariable("id") Long id,BlockException blockException){
        Payment payment = new Payment(id,null);
        return new CommonResult(445,"请求被限流，blockException is:" + blockException.getMessage(),payment);
    }
}
