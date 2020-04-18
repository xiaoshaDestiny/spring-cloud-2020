package com.learn.cloud.lb;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 * @author xrb
 * @create 2020-04-18 23:52
 */
public interface LoadBalance {


    ServiceInstance instancees(List<ServiceInstance> serviceInstances);
}
