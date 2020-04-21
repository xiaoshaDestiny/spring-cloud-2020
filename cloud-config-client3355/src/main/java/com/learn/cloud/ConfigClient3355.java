package com.learn.cloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author xu.rb
 * @since 2020-04-21 20:05
 */
@SpringBootApplication
@EnableEurekaClient
public class ConfigClient3355 {
    public static void main(String[] args) {
        SpringApplication.run(ConfigClient3355.class,args);
    }

}
