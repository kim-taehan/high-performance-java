package com.skcc.stockv1.domain.data;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class StockRequest {

    private Item item;
    private Integer count;
    private String orderNo;
}
