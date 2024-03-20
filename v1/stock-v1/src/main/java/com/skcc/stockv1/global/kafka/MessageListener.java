package com.skcc.stockv1.global.kafka;

import com.skcc.stockv1.domain.data.StockRequest;
import com.skcc.stockv1.domain.service.StockService;
import com.skcc.stockv1.global.ObjectMapperConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class MessageListener {

    private final ObjectMapperConverter objectMapper;

    private final StockService stockService;

    private final String CALL_LOG_TEXT = "CALL 재고확인 (호출방식:QUEUE)";
    @KafkaListener(topics = "stock-topic")
    public void messageListener(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        log.info("{}={}", CALL_LOG_TEXT, record.value());
        log.info("[kafka receiveMessage]={}, {}", record.topic(), record.value());
        Object object = objectMapper.stringToObject(record.value(), StockRequest.class);
        if (object != null) {
            stockService.checkStockKafka((StockRequest) object);
        }
        else{
            log.error("잘못된 카프카 메시징={}", record.value());
        }
        // kafka 메시지 읽어온 곳까지 commit. (이 부분을 하지 않으면 메시지를 소비했다고 commit 된 것이 아니므로 계속 메시지를 읽어온다)
        acknowledgment.acknowledge();
    }
}
