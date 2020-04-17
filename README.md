# spring-cloud-2020
微服务技术栈2020年版本 - 知识点整理

## 初级部分  
dependencyManagement 一般出现在父工程中，子工程聚合依赖，子工程就可以不指定版本号。只是声明依赖，并不实现引入。  
devtools 热启动 ctrl+alt+shift+/ 生产环境一般不开启。  
restTemplate 是Spring提供的用于访问Rest服务的客户端模板工具集。  
公共entity 可以使用maven clean install之后 提供给其他模块使用。  

## Eureka
Eureka现在官网已经停止更新。  
可以实现服务调用、负载均衡、服务注册与发现。
Eureka作为服务注册功能的服务区，它是服务的注册中心。系统中其他的微服务，使用Eureka的客户端连接到Eureka Server并且为之心跳连接。就可以通过Eureka Server来监控系统中各个微服务是否正常运行。
服务把把服务地址、通讯地址以别名的形式方式注册到注册中心上。  
@EnableEurekaServer 标注在注册中心server上  
@EnableEurekaClient 标注在：服务提供者、消费者上  
微服务RPC远程服务调用的核心是高可用，搭建Eureka注册中心集群，实现负载均衡+故障容错。集群之间互相注册，相互守望。  
  
当服务提供者变成集群的时候，需要开启负载均衡功能 @LoadBalanced ，否贼服务消费者在调用的时候会报错。也就是在RestTemplate配置类的地方开启。  
  
服务发现 discovery 对于注册到eureka里面的服务，可以通过服务发现来获得该服务的信息。@EnableDiscoveryClient  
  
  


  

  
  

