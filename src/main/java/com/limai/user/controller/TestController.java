package com.limai.user.controller;

import com.limai.user.annotation.LogPrint;
import com.limai.user.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.Date;


/**
 * @author itw_zhangcg
 * @version 1.0
 * @className TestController
 * @description TODO
 * @date 2018/8/2 10:34
 **/
@Controller
@Api(value = "测试controller")
public class TestController {

    @ApiOperation(value = "/testAop", notes = "aop测试")
    @GetMapping("/testAop")
    @ResponseBody
    @LogPrint
    public Object test() {
        return "success";
    }
}
