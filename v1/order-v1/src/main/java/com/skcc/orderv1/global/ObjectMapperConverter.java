package com.skcc.orderv1.global;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class ObjectMapperConverter {

    private final ObjectMapper objectMapper;

    public ObjectMapperConverter() {
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Java Object -> JsonNode
     */
    public JsonNode javaObjToJsonNode(Object object) {
        return objectMapper.convertValue(object, JsonNode.class);
    }

    public JsonNode createJsonNod() {
        return objectMapper.createObjectNode();
    }
}
