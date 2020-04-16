package com.learn.cloud.service.impl;

import com.learn.cloud.dao.PaymentDao;
import com.learn.cloud.entities.Payment;
import com.learn.cloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xrb
 * @since 2020-04-16 22:57
 */

@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;


    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }
}
