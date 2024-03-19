package com.skcc.stockv1.global.queue;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private static String TOPIC_NAME = "order-topic";

    public String sendMessage(String message) {
        String messageData = "kafka message (stock->order)";

        log.info("kafka sendMessage={}", message);
        kafkaTemplate.send(TOPIC_NAME, message);
        return "success.";
    }
}
