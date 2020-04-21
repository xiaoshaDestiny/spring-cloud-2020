package com.learn.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author xu.rb
 * @since 2020-04-21 18:20
 */
@SpringBootApplication
@EnableConfigServer
public class ConfigCenter3344 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigCenter3344.class,args);
    }
}
