package com.skcc.orderv1.core.paging.dto;

import com.skcc.orderv1.core.mvc.mapper.CamelMap;
import lombok.Getter;

import java.util.List;

@Getter
public class PagingResult {

    private final List<CamelMap> contents;
    private int totalCount;

    public PagingResult(List<CamelMap> contents) {
        this.contents = contents;
    }

    public void updateTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
