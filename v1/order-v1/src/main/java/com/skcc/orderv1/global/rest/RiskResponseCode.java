package com.skcc.orderv1.global.rest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RiskResponseCode {
    S200("정상처리"),
    F500("통신 오류!"),
    F400("재고부족");

    private final String value;

    public String value() {
        return value;
    }
}
