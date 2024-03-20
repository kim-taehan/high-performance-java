package com.skcc.orderv1.domain.web;

import com.skcc.orderv1.core.response.ResEntity;
import com.skcc.orderv1.domain.data.OrderCreateRequest;
import com.skcc.orderv1.domain.data.OrderCreateResponse;
import com.skcc.orderv1.domain.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static lombok.AccessLevel.PROTECTED;

@Slf4j
@RequiredArgsConstructor(access = PROTECTED)
@RestController
public class OrderApiController {

    private final OrderService orderService;

    private final String CALL_LOG_TEXT = "CALL 상품주문 (호출방식:HTTP)";

    @PostMapping("/api/v1/orders")
    public ResEntity<OrderCreateResponse> createOrder(@RequestBody OrderCreateRequest request) {
        log.info("{}={}", CALL_LOG_TEXT, request);
        return ResEntity.ok(orderService.createOrderHttp(request));
    }
}
