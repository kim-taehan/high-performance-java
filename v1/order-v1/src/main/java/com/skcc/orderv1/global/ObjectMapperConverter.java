package com.skcc.orderv1.global;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public JsonNode createJsonNod() {
        return objectMapper.createObjectNode();
    }

    public <T> T stringToObject(String jsonText, Class<T> classType) {
        try {
            return objectMapper.readValue(jsonText, classType);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }
        return null;
    }

}
