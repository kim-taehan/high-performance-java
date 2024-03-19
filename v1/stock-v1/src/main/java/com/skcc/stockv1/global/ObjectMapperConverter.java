package com.skcc.stockv1.global;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
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

    public JsonNode createJsonNode() {
        return objectMapper.createObjectNode();
    }
    public ObjectNode createObjectNode() {
        return objectMapper.createObjectNode();
    }

    public Object stringToObject(String jsonText, Class<?> classType) {
        try {
            return objectMapper.readValue(jsonText, classType);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }

}
