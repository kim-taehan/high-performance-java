package com.skcc.orderv1.core.mvc.mapper;

import org.apache.commons.collections4.map.ListOrderedMap;
import org.egovframe.rte.psl.dataaccess.util.CamelUtil;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.util.Objects;

public class CamelMap extends ListOrderedMap {

    @Override
    public Object put(Object key, Object value) {
        checkStringType(key);
        return super.put(convert2CamelCase(key), value);
    }

    @Override
    public Object putIfAbsent(Object key, Object value) {
        checkStringType(key);
        return super.putIfAbsent(convert2CamelCase(key), value);
    }

    private void checkStringType(Object key) {
        Assert.isTrue(key instanceof String, "String 타입만 입력할 수 있습니다.");
    }

    private String convert2CamelCase(Object key) {
        return CamelUtil.convert2CamelCase((String) key);
    }

    @Override
    public Object get(Object key) {
        return super.get(key);
    }

    private final String EMPTY_TEXT = "";
    public String getStr(String key) {
        Object ret = super.get(key);

        if (ret instanceof Timestamp) {
            return ret.toString();
        }
        return Objects.isNull(ret) ? EMPTY_TEXT : (String) ret;
    }

    public Timestamp getTimeStamp(String key) {
        Object ret = super.get(key);
        return Objects.isNull(ret) ? null : (Timestamp) ret;

    }

    public long getLong(String key) {
        Object ret = super.get(key);
        return Objects.isNull(ret) ? null : (long) ret;
    }

    public int getInt(String key) {
        Object ret = super.get(key);
        return Objects.isNull(ret) ? null : (int) ret;
    }
}
