package com.skcc.orderv1.domain.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderCreateRequest {

    private final Item item;
    private final Integer count;
}
