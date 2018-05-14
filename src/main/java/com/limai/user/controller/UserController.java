package com.limai.user.controller;


import com.limai.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(value = "用户测试")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @ApiOperation(value = "根据id获取用户")
    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    public Object queryUsers() {
        return userService.queryUsers();
    }

}
