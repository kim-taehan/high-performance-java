package com.skcc.stockv1.domain.service.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.skcc.stockv1.core.mvc.service.SkAbstractService;
import com.skcc.stockv1.domain.data.StockRequest;
import com.skcc.stockv1.domain.repository.StockRepository;
import com.skcc.stockv1.domain.service.StockService;
import com.skcc.stockv1.global.ObjectMapperConverter;
import com.skcc.stockv1.global.queue.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockServiceImpl extends SkAbstractService implements StockService {

    private final StockRepository stockRepository;

    private final MessageProducer messageProducer;
    private final ObjectMapperConverter objectMapperConverter;

    private ExecutorService executorService;

    @PostConstruct
    void init() {
        executorService = new ThreadPoolExecutor(
                3, // 코어 쓰레드 수
                200, // 최대 쓰레드 수
                120, // 최대 유휴 시간
                TimeUnit.SECONDS, // 최대 유휴 시간 단위
                new SynchronousQueue<>() // 작업 큐
        );
    }

    @Transactional
    @Override
    public boolean checkStock(StockRequest request) {
        return synchronizedProcess(request);
    }

    @Override
    public boolean checkStockKafka(StockRequest request) {

        CompletableFuture.runAsync(()->{
            log.info("CompletableFuture.runAsync={}", Thread.currentThread().getName());

            boolean ret = synchronizedProcess(request);

            ObjectNode objectNode = objectMapperConverter.createObjectNode();
            objectNode.put("orderNo", request.getOrderNo());
            objectNode.put("code", ret ? "0000" : "E001");

            messageProducer.sendMessage(objectNode.toString());
            log.info("kafka send message");
        },executorService);
        return false;
    }

    private synchronized boolean synchronizedProcess(StockRequest request) {

        int itemCount = stockRepository.findItemCount(request.getItem().name());
        log.info("select -> item={}, Count={}", request.getItem(), itemCount);
        if (request.getCount() > itemCount) {
            return false;
        }
        HashMap<String,Object> params = new HashMap<>();
        params.put("stockCount", itemCount - request.getCount());
        params.put("item", request.getItem());
        int updateItemCount = stockRepository.updateItemCount(params);
        log.info("update -> item={}, Count={}", request.getItem(), itemCount - request.getCount());

        timeout(100);
        return true;
    }

    private static void timeout(int ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            log.info("e = {}", e);
        }
    }
}
