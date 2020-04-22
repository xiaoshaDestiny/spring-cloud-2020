package com.learn.cloud.controller;

import com.learn.cloud.service.IMessageProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author xu.rb
 * @since 2020-04-23 0:00
 */
@RestController
public class SendMessageController {

    @Resource
    private IMessageProvider iMessageProvider;

    @GetMapping("/sendMessage")
    public String sentMessage(){
        return iMessageProvider.send();
    }
}
