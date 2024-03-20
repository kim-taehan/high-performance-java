package com.skcc.orderv1.global.kafka;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
public class MessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private static String TOPIC_NAME = "stock-topic";

    public void sendMessage(String message) {
        log.info("[kafka sendMessage]={}, {}", TOPIC_NAME, message);
        kafkaTemplate.send(TOPIC_NAME, message);
    }
}
