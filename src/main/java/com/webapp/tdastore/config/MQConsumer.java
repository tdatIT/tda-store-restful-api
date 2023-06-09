package com.webapp.tdastore.config;

import com.webapp.tdastore.data.dto.OrderDTO;
import com.webapp.tdastore.services.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MQConsumer {
    @Autowired
    private OrderService orderService;

    @RabbitListener(queues = MQConfig.QUEUE)
    public void consumerMessageFromQueue(OrderDTO order) {
        orderService.insertOrderForUser(order);
        log.info("create order from mq success:" + order);
    }
}
