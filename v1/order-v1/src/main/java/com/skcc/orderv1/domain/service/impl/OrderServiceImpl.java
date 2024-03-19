package com.skcc.orderv1.domain.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.skcc.orderv1.core.mvc.service.SkAbstractService;
import com.skcc.orderv1.domain.data.OrderCreateRequest;
import com.skcc.orderv1.domain.data.OrderCreateResponse;
import com.skcc.orderv1.domain.data.OrderStatus;
import com.skcc.orderv1.domain.mapper.OrderRepository;
import com.skcc.orderv1.domain.service.OrderService;
import com.skcc.orderv1.global.ObjectMapperConverter;
import com.skcc.orderv1.global.rest.RestTemplateCall;
import com.skcc.orderv1.global.rest.RiskResponseCode;
import com.skcc.orderv1.global.rest.RiskUrl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl extends SkAbstractService implements OrderService {

    private final OrderRepository orderRepository;

    private final RestTemplateCall restTemplateCall;

    private final ObjectMapperConverter objectConverter;

    private ExecutorService executorService;
    @PostConstruct
    void init() {
        executorService = new ThreadPoolExecutor(
                3, // 코어 쓰레드 수
                200, // 최대 쓰레드 수
                120, // 최대 유휴 시간
                TimeUnit.SECONDS, // 최대 유휴 시간 단위
                new SynchronousQueue<>() // 작업 큐
        );
    }

    @Override
    @Transactional
    public OrderCreateResponse createOrderHttp(OrderCreateRequest request) {

        String orderNo = createOrderNO();
        int insertCount = insertOrder(request, orderNo);

        // rm http call
        JsonNode stockRequest = objectConverter.javaObjToJsonNode(request);
        ObjectNode objectNode = ((ObjectNode) stockRequest).put("orderNo", orderNo);
        RiskResponseCode responseCode = restTemplateCall.call(objectNode, RiskUrl.ORDER);
        if (responseCode == RiskResponseCode.S200) {
            updateOrderStatus(orderNo, OrderStatus.COMPLETE, "정상처리됨");
        }
        else {
            updateOrderStatus(orderNo, OrderStatus.FAILED, responseCode.value());
        }
        return new OrderCreateResponse(orderNo, responseCode.value());
    }


    @Override
    public String createOrderSocket(OrderCreateRequest request) {
        String orderNo = createOrderNO();
        CompletableFuture.runAsync(()->{
            log.info("CompletableFuture.runAsync={}", Thread.currentThread().getName());
            int insertCount = insertOrder(request, orderNo);

            ObjectNode objectNode = ((ObjectNode) objectConverter.javaObjToJsonNode(request)).put("orderNo", orderNo);

            String stockRequest = objectNode.toString();

            log.info("stockRequest = {}", stockRequest);



        },executorService);
        return orderNo;
    }

    private void updateOrderStatus(String uuid, OrderStatus orderStatus, String message) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("orderNo", uuid);
        params.put("orderStatus", orderStatus);
        params.put("message", message);
        int updateCount = orderRepository.updateOrderStatus(params);
        log.info("updateCount = {}", updateCount);;
    }

    private int insertOrder(OrderCreateRequest request, String uuid) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("orderNo", uuid);
        params.put("item", request.getItem());
        params.put("count", request.getCount());
        params.put("orderStatus", OrderStatus.PENDING);
        return orderRepository.insertOrder(params);
    }

    private static String createOrderNO() {
        return UUID.randomUUID().toString();
    }


}
