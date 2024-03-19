package com.skcc.orderv1.core.paging.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class PageDto {

    private final int pageSize;
    private final int currentPage;
    private int totalPage;
    private int totalCount;

    public PageDto() {
        this.pageSize = 10;
        this.currentPage = 0;
        this.totalPage = -1;
        this.totalCount = -1;
    }


    public void updateTotal(int totalCount) {
        this.totalCount = totalCount;
        this.totalPage = totalCount / pageSize;
    }
}
