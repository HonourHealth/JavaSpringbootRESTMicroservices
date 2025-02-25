package com.example.emailservice.consumer;

import com.example.emailservice.dto.OrderEvent;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class OrderConsumer {

    private Logger LOGGER = LoggerFactory.getLogger(OrderConsumer.class);

    @RabbitListener(queues = "${rabbitmq.queue.email.name}")
    private void consume(OrderEvent event) {
        LOGGER.info("Order event received in email service -> {}", event);
        //TODO email the event to the customer
    }
}
