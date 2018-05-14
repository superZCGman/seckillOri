package com.rabbit;

import com.limai.user.config.rabbit.AmqpConfig;
import com.limai.user.config.rabbit.RabbitConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = AmqpConfig.FOO_QUEUE)
public class Receiver {
    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitConfig.class);

    @RabbitHandler
    public void process(@Payload String foo){
        LOGGER.info("Listener : " + foo);
    }
}
