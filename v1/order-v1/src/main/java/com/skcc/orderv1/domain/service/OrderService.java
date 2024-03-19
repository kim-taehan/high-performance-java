package com.skcc.orderv1.domain.service;

import com.skcc.orderv1.domain.data.OrderCreateRequest;

public interface OrderService {
    String createOrderHttp(OrderCreateRequest request);
    String createOrderSocket(OrderCreateRequest request);
}
