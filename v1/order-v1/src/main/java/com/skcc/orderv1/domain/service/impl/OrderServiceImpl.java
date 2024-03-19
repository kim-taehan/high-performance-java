package com.skcc.orderv1.domain.service.impl;

import com.skcc.orderv1.core.mvc.service.SkAbstractService;
import com.skcc.orderv1.domain.data.OrderCreateRequest;
import com.skcc.orderv1.domain.data.OrderStatus;
import com.skcc.orderv1.domain.mapper.OrderRepository;
import com.skcc.orderv1.domain.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl extends SkAbstractService implements OrderService {

    private final OrderRepository orderRepository;
    @Override
    @Transactional
    public String createOrderHttp(OrderCreateRequest request) {

        String uuid = createOrderNO();
        int insertCount = insertOrder(request, uuid);
        log.info("insertCount={}", insertCount);

        // rm http call

        // orderState update
        return uuid;
    }

    private int insertOrder(OrderCreateRequest request, String uuid) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("orderNo", uuid);
        params.put("item", request.getItem().name());
        params.put("count", request.getCount());
        params.put("orderStatus", OrderStatus.PENDING.name());
        int insertCount = orderRepository.insertOrder(params);
        return insertCount;
    }

    private static String createOrderNO() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    @Override
    public String createOrderSocket(OrderCreateRequest request) {
        return null;
    }
}
