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

    private final String CALL_LOG_TEXT = "CALL 재고확인 (호출방식:HTTP)";
    @PostMapping("/api/v1/stocks")
    public ResEntity<String> checkStock(@RequestBody StockRequest request) {
        log.info("{}={}", CALL_LOG_TEXT, request);
        boolean ret = stockService.checkStock(request);
        return ret ? ResEntity.ok(request.getOrderNo())
                : new ResEntity<>(request.getOrderNo(), "재고부족");
    }
}
