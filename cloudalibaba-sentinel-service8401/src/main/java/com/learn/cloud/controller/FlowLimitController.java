package com.learn.cloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xu.rb
 * @since 2020-04-25 21:52
 */
@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping("/testA")
    public String getA(){
        return "-------- testA";
    }
    @GetMapping("/testB")
    public String getB(){
        return "-------- testB";
    }

    @GetMapping("/testD")
    public String getD(){
        return "-------- testD";
    }


    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey",blockHandler = "dealTestHotKey")
    public String getHotKey(@RequestParam(value = "p1",required = false) String p1,
                            @RequestParam(value = "p2",required = false) String p2){
        return "-------- testHotKey  p1:"+p1+"  p2:"+p2;
    }


    public String dealTestHotKey(String p1, String p2, BlockException exception){

        return "dealTestHotKey......";
    }


}
