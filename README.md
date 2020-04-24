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
  
4、轮询负载均衡算法的实现逻辑
rest接口第几次请求 % 服务器集群总数量 = 实际调用服务器的下标位置，每次服务重启之后rest接口计数从1开始。

## OpenFeign 服务接口调用
1、Feign是一个声明式的web服务客户端，用在消费端。让编写Web服务客户点变得非常容易，只需要创建一个接口并且在接口上议案家注解即可。
使用Ribbon+RestTemplate，对http进行了请求的封装处理，形成一套模板化的调用方案。但是在实际开发中，对服务的调用可能不止一处。
往往一个接口会被多处调用，多以通常会针对每个微服务自行封装一些客户端来包装这些依赖服务的调用。
所以。Feign只需要创建一个接口并且使用注解的港式来配置它。Feign是集成了Ribbon的。
  
2、OpenFeign是SpringCloud在Feign的基础上支持了SpringMVC的注解。
  
3、Feign客户端默认只等待一秒钟，但是服务端处理请求超过了一秒钟，导致Feign客户点不想等待，直接报错。在yml文件中可以指定

## Hystrix 熔断器 （重要）
1、服务链路调用，涉及的服务越来越多，叫扇出。Hystrix是一个用于处理分布式系统延迟和容错的开源库。分布式系统中，许多依赖不可避免的会调用失败、超时、异常等。
它能保证在一个依赖出现问题的情况下，不会导致整体服务失败，避免级联故障。
断路器监控故障，向调用方返回一个符合预期的、可处理的预备响应(FallBack)，而不是长时间的等待或者抛出调用方无法处理的异常，这样就保证了服务调用方的线程不会被长时间的占用，避免故障在分布式系统中的蔓延、乃至雪崩。
  
服务降级 fallback 为了避免长时间的等待而向客户端立刻返回一个友好的fallback提示。会发生服务降级的情况有：程序运行异常、超时、服务熔断触发服务降级、线程池/信号量打满也会导致服务降级。    
服务熔断 break  保险丝，直接拒绝访问，然后调用服务降级的方法返回友好提示。
服务限流 flowlimit 高并发操作，严禁拥挤，排队有序进行。

**服务降级**
对服务提供方进行并发压测。服务消费者也进行压测。Jmeter。小问题最终都会变成大问题。tomcat里面的线程池被打满。
服务提供方：超时、down机时候必须要有服务的降级处理。
服务消费方的调用时间满足不了自我要求，也要进行服务的降级处理。
  
@HystrixCommand标注在服务提供者上。可以设置自身调用超时的时间峰值，峰值之内可以正常运行，超时要有兜底的方法处理，作为服务降级fallback。     
@EnableCircuitBreaker 标注在服务提供方的主启动类上,开区服务提供方的服务降级配置。    
@EnableHystrix 标注在服务消费方的主启动类上。  
  
@DefaultProperties(defaultFallback = "paymentGlobalFallbackMethod")标注在controller类上，指定全局的服务降级兜底方法。
因为消费方都是使用FeignClient指定的调用的服务名称( _@FeignClient(value = "CLOUD-PAYMENT-HYSTRIX-SERVICE")_ )，所以可以为Feign客户端定义一个处理服务降级的处理类就可以完成业务和兜底方法的解耦合。

**服务熔断**  
三种状态： close  open  half-close  
熔断机制是应对雪崩效应的一种微服务链路保护机制。当删除链路的某个微服务出错不可用或者响应时间太长时。
会对服务降级，进而熔断该节点的微服务调用，快速返回报错的响应信息。
当检测到该节点的响应正常之后，恢复链路调用。
Hystrix熔断机制，当失败的调用达到一定的阈值，缺省是5秒内20次调用失败，就会启动熔断机制，使用的注解是@HystrixCommand
  
多次错误，然后慢慢正确，刚开始正确率不满足，即时是正确的也不能进行访问。
熔断打开：请求不再进行调用当前服务，内部设置时钟一般为MTTR 平均故障处理事件，当打开时长达到所设时钟时则进入半熔断状态。
熔断关闭：不再对服务进行熔断。
熔断半开：部分请求根据规则调用当前服务，如果请求成功且符合规则，就认为当前服务恢复正常，关闭熔断。
  
断路器的三个重要参数：
快照时间窗：断路器是否打开需要统计一些请求和错误数据，而统计的时间范围就是快照时间窗，默认是10秒钟。
请求总数阈值：在快照时间窗时间之内，在这个数字之内的请求，即时所有请求都超时或者失败，断路器都不会打开。
错误百分比阈值：当请求总数在快照时间窗之内错误的次数占请求总数的百分比超过了这个数值，断路器就会被打开。
  
断路器开始的条件：
当满足一定的阈值的时候（默认10秒之内超过20个请求）
当失败率达到一定的百分比之后（默认10秒内超过50%的请求失败）
当断路器开启的时候，所有请求都不会进行转发，一段时间之后（默认是5秒钟）这个时候断路器是半开启状态，会让其中一个请求进行转发，如果成功，断路器会关闭。

**服务限流 接后续Sentinel**


**hystrix的工作流程(重要)**
官网上梳理一遍。面试会问。

**服务监控仪表盘 hystrix dashboard**
在仪表盘服务启动类上标注 @EnableHystrixDashboard
在服务提供者添加监控actuator依赖
监控平台 7色1圈1线

## GateWay 服务网关 
zuul 和 gateway  
1、GateWay是SpringCloud自己研发的一个服务网关组件。  
底层使用的是Webflux中的reactor-netty响应式编程组件，底层使用的是Netty通讯框架。
对于高并发和非阻塞式通讯就非常有优势。
  
2、GateWay能做反向代理、鉴权、流量监控、熔断、日志监控等。  
Zuul1是一个阻塞的IO模型，GateWay是非阻塞的响应式IO模型。
  
3、GateWay的三个核心概念是：  
Route路由:构建网关的基本模块，ID,URI，一系列的断言和过滤器组成，如果断言为true则匹配该路由。  
Predicate断言：Java8的函数式编程，开发人员可以匹配HTTP请求中的所有内容，请求头参数等等，如果请求与断言匹配则进行路由。  
Filter过滤：Spring框架中的GateWayFilter实例，使用过滤器，可以在请求路由前或者之后对请求进行修改。  
Filter在pre类型的过滤器之间可以做参数校验、权限校验、流量监控、日志输出、协议转换等,在post类型的过滤器中可以做响应内容、响应头修改、日志输出、流量监控等，有着很重要的作用。  
核心流程就是：路由转发+过滤链

4、默认情况下，GateWay会根据注册中心注册的服务列表，以微服务名称创建动态路由进行转发，从而实现动态路由的功能。
XXX route predicate factory  --->  RoutePredicateFactory

After Route Predicate   -After=2020-04-21T15:51:37.485+8:00[Asia/Shanghai]  
Before Route Predicate    
Between Route Predicate  
Cookie Route Predicate 需要配置两个参数，一个是Cookie name，一个是正则表达式。路由规则会通过获取对应的Cookie name值和正则表达式的值去匹配，如果匹配上就会去执行路由。
Header\Host\Method\Path\Query   
说白了就是实现一组匹配规则，让请求过来找到对应的Route进行处理。
  
5、Filter  
在请求被路由的前或者后，对请求进行修改。

## config 服务配置
1、集中的管理配置文件。不同环境不同配置，动态化的配置更新，分环境部署（dev/test/prod/beat/release）
运行期间动态调整，服务自己向配置中心拉取配置信息。配置改变的时候不需要重启，服务自己感知并且更新应用。配置信息以REST接口的形式暴露。

2、配置文件的读取规则
>三种： label分支名  name服务名  profile环境名
公式1： /{label}/{application}-{profile}.yml  
master分支: http://config-3344.com:3344/master/config-dev.yml  
dev分支: http://config-3344.com:3344/dev/config-dev.yml  
公式2： /{application}-{profile}.yml  
公式3： /{application}/{profile}[/{label}]  

3、GitHub上面配置文件修改，配置中心能够很快的做出改变，但是连接配置中心的client服务端则不能（需要重启）。
怎么解决呢？保证真正的动态刷新，不用重启每一个连接的服务。  
对3355进行升级：  
(1) pom中有actuator监控。  
(2) bootstrap.yml中有暴露监控端点。 managerment......  
(3) 在业务类上加上 @RefreshScope  
(4) 每次修改完毕github上的内容，都发送post请求给3355  http://localhost:3355/actuator/refresh    
尽管能做到不重启服务就能刷新配置，但还是需要给服务单独发送一个post请求，还是会有麻烦，这就需要消息总线的支持。  

## bus 消息总线
Spring Cloud Bus 配合Spring Cloud Config使用，可以实现配置的动态刷新。Bus支持两种消息代理，RabbitMQ和Kafka
所有微服务订阅了一个主题，就能自动化的更新配置。  
  
Bus是一个将分布式系统的节点与轻量级的消息系统链接起来的框架，它整合了Java的事件处理机制和消息中间件的功能。
Bus能管理和传播分布式系统之间的消息，就像一个分布式执行器，可用于关闭状态更改，事件推送等，也可以当做微服务之间的通讯通道。
  
**全局通知  和  精确打击**   
需要安装RabbitMQ。在配置中心服务端和客户端，都添加消息总线RabbitMQ的依赖支持。并且在配置中心添加mq相关的配置。
当配置文件被改动之后，不需要通知每一个微服务，只需要通知配置中心即可。发送一个post请求给配置中心。
```
management:  
  endpoints:  
    web:  
      exposure:  
        include: 'bus-refresh'  #刷新配置 
        
http://localhost:3344/actuator/bus-refresh                 全局通知
http://localhost:3344/actuator/bus-refresh/config-client:3355 （微服务名称:端口） 定点通知，只通知3355
```


## Stream 消息驱动
1、解决的技术痛点：不再关注MQ的细节，一种绑定方式，自动在各种MQ之间切换。屏蔽底层消息中间件的差异，降低切换的成本，统一消息的编程模型。  
它是一个构建消息驱动微服务的架构，程序通过input和output与Binder对象进行交互，而通过配置绑定后，Binder对象与中间件进行交互。目前仅仅支持RabbitMQ和Kafka。
通过定义绑定器Binder作为中间层，实现了应用程序与消息中间件细节之间的隔离。input对应消费者，output对应在生产者。  
  
2、Stream 的流程套路。Binder屏蔽差异，Channel通道，队列的一种抽象，实现存储和转发。Source和Sink输入输出，发布消息就是输出，接收消息就是输入。
  
3、组件注解  
Middleware 中间件，目前仅仅支持RabbitMQ和Kafka  
Binder 是应用与中间件之间的封装。  
@Input 标识输入通道  
@Output 标识输出通道  
@StreamListener 监听队列，消费者的队列消息接收  
@EnableBinding 把Channel和exchange绑定在一起  

消息重复消费  
生产一条消息之后，连接同一个目的地的消费者都会受到消息。假设现在需要让一条消息只会让一个消费者受到消息，就需要用到消息分组来解决。  
Stream中处于同一个group的多个消费者是竞争关系，能够保证一个消息只被一个应用消费一次。处于不同组的是可以全面消费的。    
rabbitmq会分配一个组流水号。当开启了分组之后，mq是默认支持持久化的，当消费者宕机之后重连，是能接收到宕机期间发送的消息的。  

## Sleuth
一个请求最终是由多个微服务节点来共同协调产生最后的结果的，这个调用链路中任何一个环节出现问题都会引起整个请求的最终失败。  
Sleuth负责收集整理，Zipkin负责做数据的展现。  
zipkin从SpringCloud的F版本开始不再需要构建ZipKinServer了，只需要调用Jar包即可。
https://dl.bintray.com/openzipkin/maven/io/zipkin/java/zipkin-server/
下载之后：java -jar zipkin-server-2.12.9-exec.jar  在9411端口
在需要进行链路追踪的模块下，引入zipkin的pom依赖。  
```
order服务 调用 payment服务

第一步：都添加依赖  web actuator zipkin
    <!--zipkin-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-zipkin</artifactId>
    </dependency>
第二步：下载jar包 启动 
https://dl.bintray.com/openzipkin/maven/io/zipkin/java/zipkin-server/
java -jar zipkin-server-2.12.9-exec.jar  暴露在9411端口 
      
第三步：都开启配置
spring:
  application:
    name: cloud-payment-service
  zipkin:
    base-url: http://localhost:9411
  sleuth:
    sampler:
      probability: 1  #采样率的值 介于 0-1 之间  1表示全部采集
```

# Spring Cloud Alibaba  
2018年10月31日 alibaba 入驻Spring Cloud官方孵化器。  
它支持服务降级限流：默认支持Servlet\Feign\RestTemplate\Dubbo\RocketMQ限流降级功能接入，可以在运行时通过控制套实时修改限流降级规则，还支持查看限流降级Metrics监控。    
服务注册与发现：适配SpringCloud的服务注册与发现，默认集成了Ribbon的支持。  
分布式管理配置：支持分布式系统的外部化配置，配置更新时自动刷新。  
消息驱动能力：基于Spring Cloud Stream为微服务应用构建消息驱动能力。  
阿里云对象存储、分布式任务调度。

## Nacos
Naming + Configuration + Service   注册命名、配置的服务组件，是一个更易于构建云原生应用的动态服务发现、配置管理和服务管理平台。(Eureka + Config + Bus)
github地址  https://github.com/alibaba/nacos/releases  

























  




