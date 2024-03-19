package com.skcc.orderv1.domain.service;

import com.skcc.orderv1.domain.data.OrderCreateRequest;
import com.skcc.orderv1.domain.data.OrderCreateResponse;

public interface OrderService {
    OrderCreateResponse createOrderHttp(OrderCreateRequest request);
    String createOrderSocket(OrderCreateRequest request);
}
