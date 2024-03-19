package com.skcc.orderv1.global.rest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RiskUrl {

    ORDER("/api/v1/stocks");

    private final String value;

    public String value() {
        return value;
    }
}
