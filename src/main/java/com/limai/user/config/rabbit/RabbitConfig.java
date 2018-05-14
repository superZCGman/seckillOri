package com.limai.user.config.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RabbitListener(queues = AmqpConfig.FOO_QUEUE)
public class RabbitConfig {

    @Bean
    public DirectExchange defaultExchange(){
        return new DirectExchange(AmqpConfig.FOO_EXCHANGE);
    }

    @Bean
    public Queue fooQueue(){
        return new Queue(AmqpConfig.FOO_QUEUE);
    }

    @Bean
    public Binding binding(){
        return BindingBuilder.bind(fooQueue()).to(defaultExchange()).with(AmqpConfig.FOO_ROUTINGKEY);
    }

}
