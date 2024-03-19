package com.skcc.orderv1.core.paging;

import com.skcc.orderv1.core.paging.dto.PageDto;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@UtilityClass
public class PagingUtils {

    public <T extends PageDto> Map createMap(T t) {
        HashMap<String , Object> target = new HashMap<>();
        target.put("pageSize", t.getPageSize());
        target.put("currentPage", t.getCurrentPage());
        target.put("totalPage", t.getTotalPage());
        target.put("totalCount", t.getTotalCount());
        return target;
    }

}
