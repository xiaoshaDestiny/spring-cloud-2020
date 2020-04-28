package com.learn.cloud.service;

import java.math.BigDecimal;

/**
 * @author xu.rb
 * @since 2020-04-28 17:15
 */
public interface AccountService {

    void decrease(Long userId, BigDecimal money);
}
