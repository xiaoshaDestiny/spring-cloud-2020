package com.learn.cloud.service;

import com.learn.cloud.entities.Payment;
import org.apache.ibatis.annotations.Param;

/**
 * @author xrb
 * @create 2020-04-16 22:56
 */
public interface PaymentService {
    /**
     * 根据ID获取
     * @param id
     * @return
     */
    Payment getPaymentById(Long id);

    /**
     * 创建
     * @param payment
     * @return
     */
    int create(Payment payment);
}
