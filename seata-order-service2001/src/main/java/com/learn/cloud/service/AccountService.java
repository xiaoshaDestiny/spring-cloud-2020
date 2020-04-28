package com.learn.cloud.service;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * @author xu.rb
 * @since 2020-04-28 15:37
 */
@FeignClient("seata-account-service")
public interface AccountService {
}
