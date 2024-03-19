package com.skcc.stockv1.domain.web;

import com.skcc.stockv1.core.response.ResEntity;
import com.skcc.stockv1.domain.data.StockRequest;
import com.skcc.stockv1.domain.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PROTECTED;

@Slf4j
@RequiredArgsConstructor(access = PROTECTED)
@RestController
public class StockApiController {

    private final StockService stockService;

    @PostMapping("/api/v1/stocks")
    public ResEntity<String> checkStock(@RequestBody StockRequest request) {
        boolean ret = stockService.checkStock(request);
        if (ret) {
            return ResEntity.ok(request.getOrderNo());
        }
        return new ResEntity<>(request.getOrderNo(), "재고부족");
    }
}
