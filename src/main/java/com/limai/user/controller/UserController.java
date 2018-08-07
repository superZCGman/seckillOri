package com.limai.user.controller;


import com.limai.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Api(value = "用户测试")
@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @ApiOperation(value = "根据id获取用户")
    @RequiresPermissions("/user/userInfo")
    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public Object queryUsers() {
        return userService.queryUsers();
    }

    @RequestMapping("/addUser")
    @RequiresPermissions("/user/addUser")
    public String addUser(){
        return "/user/addUser";
    }

    @RequestMapping("/updateUser")
    @RequiresPermissions("/user/updateUser")
    public String updateUser(){
        return "/user/updateUser";

    }

    @RequestMapping("/deleteUser")
    @RequiresPermissions("/user/deleteUser")
    public String deleteUser(){
        return "/user/deleteUser";
    }

}
