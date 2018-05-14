package com.limai.user.controller;

import com.rabbit.Sender;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class RabbitMQController {

    @Resource
    private Sender sender;

    @GetMapping("/send")
    @ApiOperation(value = "/send" ,notes = "测试消息队列")
    public Object send(@RequestParam(required = false) HttpServletRequest request
            , @ApiParam(value = "消息内容") @RequestParam String msg){
        sender.send(msg);
        return "send ok!";
    }
}
