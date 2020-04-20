package com.learn.cloud.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author xrb
 * @create 2020-04-19 22:24
 */
@Service
public class PaymentService {

    /**
     * 正常访问 肯定ok
     * @param id
     * @return
     */
    public String paymentInfoOK(Integer id){
        return "线程池： "+ Thread.currentThread().getName()+" paymentInfo_OK,id: "+id;
    }

    /**
     * 超时访问
     * @param id
     * @return
     */
    //设置自身调用超时的时间峰值，峰值之内可以正常运行，超时要有兜底的方法处理，作为服务降级fallback
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String paymentInfoTimeOut(Integer id){

        int testWrong = 10/0;
        int time = 5;
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池： "+ Thread.currentThread().getName()+" paymentInfo_TimeOut,id: "+id+" 耗时"+time+"秒钟";
    }


    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池： "+ Thread.currentThread().getName()+" paymentInfo_TimeOutHandler,id: "+id+"系统繁忙，稍后重试！ o...o ";
    }


}
