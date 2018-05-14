package com;

import com.limai.user.listener.RedisListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;

@SpringBootApplication
public class RunApplication {
    public static void main(String[] args) {
        SpringApplication.run(RunApplication.class,args);
    }

    @Bean
    public ApplicationListener<ContextRefreshedEvent> applicationListener(){
        return new RedisListener();
    }
}
