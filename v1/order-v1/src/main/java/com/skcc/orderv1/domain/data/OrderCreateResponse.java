package com.skcc.orderv1.domain.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderCreateResponse {

    private final String orderNo;
    private final String message;
}
