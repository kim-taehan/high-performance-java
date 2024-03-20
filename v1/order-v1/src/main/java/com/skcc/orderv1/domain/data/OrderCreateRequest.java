package com.skcc.orderv1.domain.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequest {

    private Item item;
    private Integer count;

    @Override
    public String toString() {
        return "OrderCreateRequest{" +
                "item=" + item +
                ", count=" + count +
                '}';
    }
}
