package com.learn.cloud.dao;

import com.learn.cloud.domain.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author xu.rb
 * @since 2020-04-28 15:10
 */
@Mapper
public interface OrderDao {

    void create(Order order);

    void update(@Param("userId") Long userId, @Param("status") Integer status);
}
