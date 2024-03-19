package com.skcc.stockv1.domain.repository;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class StockRepository extends EgovAbstractMapper {

    private static final String SELECT_ITEM_COUNT = "StockMapperDao.selectItemCount";
    private static final String UPDATE_ITEM_COUNT = "StockMapperDao.updateItemCount";

    public int findItemCount(String item) {
        return selectOne(SELECT_ITEM_COUNT, item);
    }

    public int updateItemCount(Map params) {
        return update(UPDATE_ITEM_COUNT, params);
    }

}
