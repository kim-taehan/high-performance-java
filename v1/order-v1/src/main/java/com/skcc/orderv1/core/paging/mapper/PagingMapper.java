package com.skcc.orderv1.core.paging.mapper;

import lombok.RequiredArgsConstructor;
import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

// TODO 추후 직적 등록하는 것으로 변경
@Repository
@RequiredArgsConstructor
public class PagingMapper extends EgovAbstractMapper {

    public int pagingCall(String queryId, Map param) {
        return selectOne(queryId, param);
    }

}
