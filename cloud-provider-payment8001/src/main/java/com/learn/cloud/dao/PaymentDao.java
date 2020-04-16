package com.learn.cloud.dao;

import com.learn.cloud.entities.Payment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author xrb
 * @create 2020-04-16 22:50
 */
@Mapper
public interface PaymentDao {
    /**
     * 根据ID获取
     * @param id
     * @return
     */
    Payment getPaymentById(@Param("id") Long id);

    /**
     * 创建
     * @param payment
     * @return
     */
    int create(Payment payment);
}
