package com.skcc.stockv1.global.kafka;

import com.skcc.stockv1.global.ObjectMapperConverter;
import com.skcc.stockv1.global.thread.ExecutorServiceFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@ConditionalOnProperty(value = "spring.main.web-application-type", havingValue = "none")
public class KafkaConfig {
    @Bean
    public MessageListener messageListener(ObjectMapperConverter converter, ExecutorServiceFactory factory) {
        return new MessageListener(converter, factory);
    }

    @Bean
    public MessageProducer messageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        return new MessageProducer(kafkaTemplate);
    }
}
