package com.learn.cloud.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author xu.rb
 * @since 2020-04-28 15:48
 */
@Configuration
@MapperScan({"com.learn.cloud.dao"})
public class MyBatisConfig {
}
