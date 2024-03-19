package com.skcc.orderv1.domain.web;

import com.skcc.orderv1.core.response.ResEntity;
import com.skcc.orderv1.domain.data.OrderCreateRequest;
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

    @PostMapping("/api/v1/orders")
    public ResEntity<String> createOrder(@RequestBody OrderCreateRequest request) {
        return ResEntity.ok(orderService.createOrderHttp(request));
    }
}