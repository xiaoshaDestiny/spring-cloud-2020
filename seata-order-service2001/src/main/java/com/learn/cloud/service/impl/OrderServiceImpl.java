package com.learn.cloud.service.impl;

import com.learn.cloud.dao.OrderDao;
import com.learn.cloud.domain.Order;
import com.learn.cloud.service.AccountService;
import com.learn.cloud.service.OrderService;
import com.learn.cloud.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xu.rb
 * @since 2020-04-28 15:36
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;
    @Resource
    private StorageService storageService;
    @Resource
    private AccountService accountService;


    @Override
    public void create(Order order) {
        log.info("------------> 开始新建订单");
        orderDao.create(order);

        log.info("------------> 订单微服务开始调用库存服务，做扣减");




    }
}
