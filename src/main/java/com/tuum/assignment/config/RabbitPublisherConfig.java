package com.tuum.assignment.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Rana on 02 Jun, 2023
 */
@Configuration
public class RabbitPublisherConfig {
    @Value("${queue.name}")
    private String name;

    @Bean
    public Queue queue() {
        return new Queue(name, true);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
