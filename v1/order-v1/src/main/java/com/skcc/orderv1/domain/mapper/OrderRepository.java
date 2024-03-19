package com.skcc.orderv1.domain.mapper;

import org.egovframe.rte.psl.dataaccess.EgovAbstractMapper;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class OrderRepository extends EgovAbstractMapper {

    private static final String INSERT_ORDER = "OrderMapperDao.insertOrder";
    private static final String UPDATE_ORDER_STATUS = "OrderMapperDao.updateOrderStatus";

    public int insertOrder(Map param) {
        return insert(INSERT_ORDER, param);
    }

    public int updateOrderStatus(Map param) {
        return update(UPDATE_ORDER_STATUS, param);
    }

}
