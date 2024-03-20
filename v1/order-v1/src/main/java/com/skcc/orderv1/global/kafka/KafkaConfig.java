package com.skcc.orderv1.global.kafka;

import com.skcc.orderv1.domain.service.OrderService;
import com.skcc.orderv1.global.ObjectMapperConverter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@ConditionalOnProperty(value = "spring.main.web-application-type", havingValue = "none")
public class KafkaConfig {
    @Bean
    public MessageListener messageListener(ObjectMapperConverter objectMapper, OrderService orderService) {
        return new MessageListener(objectMapper, orderService);
    }

    @Bean
    public MessageProducer messageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        return new MessageProducer(kafkaTemplate);
    }
}
