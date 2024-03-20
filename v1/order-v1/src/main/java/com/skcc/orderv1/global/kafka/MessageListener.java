package com.skcc.orderv1.global.kafka;

import com.skcc.orderv1.domain.data.OrderStatus;
import com.skcc.orderv1.domain.service.OrderService;
import com.skcc.orderv1.global.ObjectMapperConverter;
import com.skcc.orderv1.global.rest.RiskResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class MessageListener {

    private final ObjectMapperConverter objectMapper;

    private final OrderService orderService;

    @KafkaListener(topics = "order-topic")
    public void messageListener(ConsumerRecord<String, String> record, Acknowledgment acknowledgment) {
        log.info("[kafka receiveMessage]={}, {}", record.topic(), record.value());
        Map ret = objectMapper.stringToObject(record.value(), Map.class);

        if (ret != null) {
            String orderNo = (String) ret.get("orderNo");
            String code = (String) ret.get("code");

            if ("0000".equals(code)) {
                orderService.updateOrderStatus(orderNo, OrderStatus.COMPLETE, RiskResponseCode.S200.value());
            } else {
                orderService.updateOrderStatus(orderNo, OrderStatus.FAILED, RiskResponseCode.F400.value());
            }
        }
        // kafka 메시지 읽어온 곳까지 commit. (이 부분을 하지 않으면 메시지를 소비했다고 commit 된 것이 아니므로 계속 메시지를 읽어온다)
        acknowledgment.acknowledge();
    }
}
