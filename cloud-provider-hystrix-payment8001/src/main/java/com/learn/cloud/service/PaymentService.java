package com.learn.cloud.service;

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
    public String paymentInfoTimeOut(Integer id){
        int time = 3;
        try {
            TimeUnit.SECONDS.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池： "+ Thread.currentThread().getName()+" paymentInfo_TimeOut,id: "+id+" 耗时"+time+"秒钟";

    }


}
