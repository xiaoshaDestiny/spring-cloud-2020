# spring-cloud-2020
微服务技术栈2020年版本 - 知识点整理  

## 初级部分  
1、pom依赖：dependencyManagement 一般出现在父工程中，子工程聚合依赖，子工程就可以不指定版本号。只声明依赖，不实现引入。  
2、开发热部署：devtools 热启动 ctrl+alt+shift+/ 生产环境一般不开启。  
3、服务间调用restTemplate： 是Spring提供的用于访问Rest服务的客户端模板工具集。  
4、maven基本使用：公共entity 可以使用maven clean install之后 提供给其他模块使用。  

## Eureka 作为注册中心  
1、Eureka作为服务注册功能的服务区，它是服务的注册中心。可以实现服务调用、负载均衡、服务注册与发现系统中其他的微服务。
使用Eureka的客户端连接到Eureka Server并且为之心跳连接。
就可以通过Eureka Server来监控系统中各个微服务是否正常运行。服务把服务地址、通讯地址以别名的形式方式注册到注册中心上。  
   
2、@EnableEurekaServer 标注在注册中心server上、@EnableEurekaClient 标注在服务提供者、消费者上。   
  
3、微服务RPC远程服务调用的核心是高可用，搭建Eureka注册中心集群，实现负载均衡+故障容错。集群之间互相注册，相互守望。  
  
4、@LoadBalanced 当服务提供者变成集群的时候，需要开启负载均衡功能，否则服务消费者在调用的时候会报错。
也就是在RestTemplate配置类的地方使用该注解开启。  
  
5、@EnableDiscoveryClient 标注在服务提供者的启动类上，对于注册到eureka里面的服务，可以通过服务发现来获得该服务的信息。 
  
6、Eureka自我保护机制：某一个时刻一个微服务不可用了(没有收到心跳)，Eureka不会立即清理，依旧会对改微服务的信息进行保留。
属于CAP理论的AP分支。在自我保护模式中，EurekaServer会保护服务注册表中的信息，不再注销任何微服务实例。    

## Zookeeper 作为注册中心
1、Eureka停更之后，原来使用Dubbo的就选择使用Zookeeper作为服务的注册中心。
微服务注册进入Zookeeper后，服务端能够正常访问。但是当服务挂掉之后，zookeeper在一段时间之后收不到心跳回复，就会删除服务。
再次连上之后，内部节点的id会改变。

2、集群环境的Zookeeper

## consul 做为服务注册中心
1、一套开源的分布式服务发现和配置管理系统，用Go语言开发的。
提供了微服务系统中的服务治理、配置中心、控制总线等功能。这些功能中每一个都可以根据需要单独使用。
也可以一起使用以构建全方位的服务网络，总之Consul提供了一种完整的服务网络解决方案。
  
2、服务发现：提供HTTP和DNS两种发现方式、健康监测：支持多种方式。HTTP,TCP，Docker，Shell脚本定制化。KV存储。多数据中心。可视化web界面。
  
3、consul.exe agent -dev 本地模式启动consul

三个注册中心的异同点  
Eureka  Java语言  AP 可以配置支持健康检查 对外暴露HTTP接口  
Consul  Go语言    CP 支持服务健康检查     对外暴露HTTP/DNS接口  
Zookeeper Java语言 CP  支持服务健康检查  客户端         
C强一致性、A可用性、P分区容错。


## Ribbon 负载均衡
目前进入了维护模式。netflix-eureka天生就带有ribbon。  

1、负载均衡：将请求平均分配到各个服务器上，达到系统的HA。  
Nginx是服务器负载均衡，客户端请求会交给nginx nginx帮助实现请求的转发。  
Ribbon是本地负载均衡，在调用微服务接口的时候，会在注册中心上获取户厕信息服务列表之后缓存到JVM本地，在本地实现RPC远程服务调用。
  
2、集中式LB:在服务消费方和提供方之间是有的独立LB设置。  
进程式LB:消费方从服务注册中心知道有哪些服务地址可用，自己选择一个合适的服务器。
  
IRule接口下面的子实现类：轮询、随机、重试、带权重、跳过被熔断等。
  
3、如何替换：
自定义负载均衡配置不能放在@ComponentScan扫描的包下面。新建配置类，返回IRule下面的一个自类的实例。    
在访问客户端加上注解，指定要调用的服务及负载均衡配置类即可。@RibbonClient(name = "CLOUD-PAYMENT-SERVICE",configuration = MySelfRule.class)  
  
4、自定义负载均衡算法










 





  





 
  
  
  


  

  
  

