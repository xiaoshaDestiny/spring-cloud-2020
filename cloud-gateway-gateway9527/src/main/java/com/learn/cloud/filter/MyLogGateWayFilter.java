package com.learn.cloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author xu.rb
 * @since 2020-04-21 17:18
 *
 * 全局过滤器
 *
 */
@Component
@Slf4j
public class MyLogGateWayFilter implements GlobalFilter,Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("******come in my filter ");

        String username = exchange.getRequest().getQueryParams().getFirst("username");

        if(username == null){
            log.info("*********用户名为null,非法用户，o(...)o");
            exchange.getResponse().setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            return exchange.getResponse().setComplete();
        }
        return chain.filter(exchange);
    }

    /**
     * order是过滤链的顺序
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
