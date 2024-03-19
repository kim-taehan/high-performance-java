package com.skcc.orderv1.core.response;

import com.skcc.orderv1.core.paging.dto.PageDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

@Getter
public class ResEntity <T> {

    @Nullable
    private final T body;
    private PageDto page;
    private String code;
    private String message;

    public ResEntity(@Nullable T body) {
        this.body = body;
        this.code = "0000";
        this.message = "정상처리 되었습니다.";
    }

    @Builder
    public ResEntity(@Nullable T body, PageDto page, String code, String message) {
        this.body = body;
        this.page = page;
        this.code = code;
        this.message = message;
    }

    public void updatePaging(PageDto page) {
        this.page = page;
    }

    public static <T> ResEntity<T> ok(@Nullable T body) {
        return new ResEntity(body);
    }
}
