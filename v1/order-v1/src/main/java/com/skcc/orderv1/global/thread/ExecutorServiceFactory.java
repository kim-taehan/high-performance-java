package com.skcc.orderv1.global.thread;

import com.skcc.orderv1.domain.data.OrderCreateRequest;
import com.skcc.orderv1.domain.service.OrderService;
import com.skcc.orderv1.global.kafka.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;


@RequiredArgsConstructor
@Slf4j
public class ExecutorServiceFactory {
    private final ExecutorService executorService;
    private final OrderService orderService;

    private final MessageProducer messageProducer;

    public void call(OrderCreateRequest request) {
        CompletableFuture.runAsync(()->{
            String stockRequest = orderService.createOrderSocket(request);
            messageProducer.sendMessage(stockRequest);
        },executorService);
    }
}
