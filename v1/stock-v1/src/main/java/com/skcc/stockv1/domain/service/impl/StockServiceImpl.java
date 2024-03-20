package com.skcc.stockv1.domain.service.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.skcc.stockv1.core.mvc.service.SkAbstractService;
import com.skcc.stockv1.domain.data.StockRequest;
import com.skcc.stockv1.domain.repository.StockRepository;
import com.skcc.stockv1.domain.service.StockService;
import com.skcc.stockv1.global.ObjectMapperConverter;
import com.skcc.stockv1.global.kafka.MessageProducer;
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

    private final ObjectMapperConverter objectMapperConverter;


    @Transactional
    @Override
    public boolean checkStock(StockRequest request) {
        return synchronizedProcess(request);
    }

    @Override
    @Transactional
    public String checkStockKafka(StockRequest request) {
        boolean ret = synchronizedProcess(request);

        ObjectNode objectNode = objectMapperConverter.createObjectNode();
        objectNode.put("orderNo", request.getOrderNo());
        objectNode.put("code", ret ? "0000" : "E001");

        return objectNode.toString();
    }

    private synchronized boolean synchronizedProcess(StockRequest request) {

        int itemCount = stockRepository.findItemCount(request.getItem().name());
        
        if (request.getCount() > itemCount) {
            log.info("[재고부족] -> item={}, Count={}", request.getItem(), itemCount);
            return false;
        }
        HashMap<String,Object> params = new HashMap<>();
        params.put("stockCount", itemCount - request.getCount());
        params.put("item", request.getItem());
        int updateItemCount = stockRepository.updateItemCount(params);
        log.info("[stock update] -> item={}, Count={}", request.getItem(), itemCount - request.getCount());

//        timeout(100);
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
