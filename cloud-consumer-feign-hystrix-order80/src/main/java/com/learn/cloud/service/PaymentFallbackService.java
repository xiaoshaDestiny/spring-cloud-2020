package com.learn.cloud.service;

import org.springframework.stereotype.Component;

/**
 * @author xrb
 * @create 2020-04-20 15:56
 */
@Component
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "---------------PaymentFallbackService fallback  paymentInfo_OK .......  (o..o)";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "---------------PaymentFallbackService fallback paymentInfo_TimeOut .......  (o..o)";
    }
}
