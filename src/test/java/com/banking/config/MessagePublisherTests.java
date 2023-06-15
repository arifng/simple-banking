package com.banking.config;

import com.banking.model.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Created by Rana on 03 Jun, 2023
 */
@ExtendWith(SpringExtension.class)
public class MessagePublisherTests {
    @MockBean
    private RabbitTemplate rabbitTemplate;
    @MockBean
    private Queue queue;

    private MessagePublisher messagePublisher;

    private final String queueName = "accountQueue";

    @BeforeEach
    void setUp() {
        messagePublisher = new MessagePublisher(rabbitTemplate, queue);
        messagePublisher.publishAccountDetails(new Account());
    }

    @Test
    void testPublishMessageSuccessfully() {
        Mockito.verify(rabbitTemplate, Mockito.times(1))
                .convertAndSend(Mockito.any(), Mockito.any(Account.class));
    }

    @Test
    void publishMessageShouldCallQueueName() {
        Mockito.verify(queue, Mockito.times(1)).getName();
    }
}
