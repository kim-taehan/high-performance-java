package com.skcc.stockv1.global.thread;

import com.skcc.stockv1.domain.data.StockRequest;
import com.skcc.stockv1.domain.service.StockService;
import com.skcc.stockv1.global.kafka.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;


@RequiredArgsConstructor
@Slf4j
public class ExecutorServiceFactory {
    private final ExecutorService executorService;
    private final StockService stockService;
    private final MessageProducer messageProducer;
    public void call(StockRequest request) {
        CompletableFuture.runAsync(()->{
            String stockRes = stockService.checkStockKafka(request);
             messageProducer.sendMessage(stockRes);
        },executorService);
    }
}
