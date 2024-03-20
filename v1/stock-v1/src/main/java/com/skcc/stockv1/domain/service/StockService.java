package com.skcc.stockv1.domain.service;

import com.skcc.stockv1.domain.data.StockRequest;

public interface StockService {

    boolean checkStock(StockRequest request);
    String  checkStockKafka(StockRequest request);
}
