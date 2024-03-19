package com.skcc.stockv1.domain.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class StockRequest {

    private final Item item;
    private final Integer count;
    private final String orderNo;
}
