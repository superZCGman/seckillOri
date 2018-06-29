package com.limai.user.controller;


import com.limai.user.util.RedisUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class CreazyController {
    private static final Logger logger = LoggerFactory.getLogger(CreazyController.class);

    @Autowired
    private RedisUtil redisUtil;

    private int cnt ;

    private String pre = "keys_";

    List<Integer> numbers = new ArrayList<>();

    @PostMapping("/buy")
    @ApiOperation(value = "/buy",notes = "秒杀活动")
    public synchronized String secKill(HttpServletRequest request){
        try {
            String userIp = request.getRemoteAddr();
            Integer num = new Random().nextInt(cnt);

            if(numbers.size()==cnt){
                return "活动已结束！";
            }
            while(numbers.contains(num)){
                num = new Random().nextInt(cnt);
            }
            numbers.add(num);

            logger.info(num.toString());

            String product = (String) redisUtil.get(pre + num);
            redisUtil.remove(pre + num);

            String result = "恭喜！！！！" + userIp + "用户1元秒杀了====》" + product;

            logger.info(result);

            return result;
        }catch (Exception e){
            e.printStackTrace();
            return "系统繁忙，请重新抢购！";
        }
    }


    @GetMapping("refresh")
    @ApiOperation(value = "/refresh",notes = "刷新库存")
    public String refresh(@ApiParam(value = "抢购数量") @RequestParam(required = false) int count){
        logger.info("刷新前库存总量："+cnt);
        for(int j = 0;j<cnt;j++){
            redisUtil.remove(pre+j);
        }
        numbers.clear();
        for(int i = 0;i < count-1;i++){
            redisUtil.set(pre + i, "全自动洗屁股马桶！");
        }
        int best = count-1;
        redisUtil.set(pre+best,"iphone X");
        this.cnt = count;
        logger.info("刷新后库存总量："+cnt);
        return "货品已更新,更新数量："+count+"个！";
    }

}
