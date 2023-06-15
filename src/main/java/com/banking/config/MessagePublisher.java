package com.banking.config;

import com.banking.model.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by Rana on 02 Jun, 2023
 */
@Component
@RequiredArgsConstructor
public class MessagePublisher {
    private final RabbitTemplate rabbitTemplate;
    private final Queue queue;

    public void publishAccountDetails(Account account) {
        try {
            rabbitTemplate.convertAndSend(queue.getName(), account);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
