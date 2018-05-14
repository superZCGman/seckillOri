package com.limai.user.listener;

import com.limai.user.util.RedisUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class RedisListener implements ApplicationListener<ContextRefreshedEvent> {

    private static boolean started = false;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(!started){
            started = true;
        }
    }
}
