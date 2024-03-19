package com.skcc.stockv1.domain.service.impl;

import com.skcc.stockv1.core.mvc.service.SkAbstractService;
import com.skcc.stockv1.domain.data.StockRequest;
import com.skcc.stockv1.domain.repository.StockRepository;
import com.skcc.stockv1.domain.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockServiceImpl extends SkAbstractService implements StockService {

    private final StockRepository stockRepository;

    @Transactional
    @Override
    public boolean checkStock(StockRequest request) {
        return synchronizedProcess(request);
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
