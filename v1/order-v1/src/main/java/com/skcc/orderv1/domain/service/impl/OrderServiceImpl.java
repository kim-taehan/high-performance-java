package com.skcc.orderv1.domain.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.skcc.orderv1.core.mvc.service.SkAbstractService;
import com.skcc.orderv1.domain.data.CallMethod;
import com.skcc.orderv1.domain.data.OrderCreateRequest;
import com.skcc.orderv1.domain.data.OrderCreateResponse;
import com.skcc.orderv1.domain.data.OrderStatus;
import com.skcc.orderv1.domain.mapper.OrderRepository;
import com.skcc.orderv1.domain.service.OrderService;
import com.skcc.orderv1.global.ObjectMapperConverter;
import com.skcc.orderv1.global.kafka.MessageProducer;
import com.skcc.orderv1.global.rest.RestTemplateCall;
import com.skcc.orderv1.global.rest.RiskResponseCode;
import com.skcc.orderv1.global.rest.RiskUrl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.*;

import static com.skcc.orderv1.domain.data.CallMethod.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl extends SkAbstractService implements OrderService {

    private final OrderRepository orderRepository;

    private final RestTemplateCall restTemplateCall;

    private final ObjectMapperConverter objectConverter;


    @Override
    @Transactional
    public OrderCreateResponse createOrderHttp(OrderCreateRequest request) {

        String orderNo = createOrderNO();
        int insertCount = insertOrder(request, orderNo, HTTP);

        // rm http call
        JsonNode stockRequest = objectConverter.javaObjToJsonNode(request);
        ObjectNode objectNode = ((ObjectNode) stockRequest).put("orderNo", orderNo);
        RiskResponseCode responseCode = restTemplateCall.call(objectNode, RiskUrl.ORDER);
        if (responseCode == RiskResponseCode.S200) {
            updateOrderStatus(orderNo, OrderStatus.COMPLETE, responseCode.value());
        }
        else {
            updateOrderStatus(orderNo, OrderStatus.FAILED, responseCode.value());
        }
        return new OrderCreateResponse(orderNo, responseCode.value());
    }


    @Override
    @Transactional
    public String createOrderSocket(OrderCreateRequest request) {
        String orderNo = createOrderNO();
        int insertCount = insertOrder(request, orderNo, TCP);
        ObjectNode objectNode = ((ObjectNode) objectConverter.javaObjToJsonNode(request)).put("orderNo", orderNo);
        String stockRequest = objectNode.toString();

        return stockRequest;
    }

    @Override
    @Transactional
    public void updateOrderStatus(String uuid, OrderStatus orderStatus, String message) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("orderNo", uuid);
        params.put("orderStatus", orderStatus);
        params.put("message", message);
        int updateCount = orderRepository.updateOrderStatus(params);
        log.info("[update order status] count={}", orderStatus);
    }

    private int insertOrder(OrderCreateRequest request, String uuid, CallMethod callMethod) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("orderNo", uuid);
        params.put("item", request.getItem());
        params.put("count", request.getCount());
        params.put("orderStatus", OrderStatus.PENDING);
        params.put("callMethod", callMethod);
        int ret = orderRepository.insertOrder(params);

        log.info("[insert order] count={}", ret);
        return ret;
    }

    private static String createOrderNO() {
        return UUID.randomUUID().toString();
    }


}
