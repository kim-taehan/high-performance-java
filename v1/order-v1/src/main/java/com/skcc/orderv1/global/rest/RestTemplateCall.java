package com.skcc.orderv1.global.rest;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class RestTemplateCall {

    @Value("${risk.url}")
    private String riskUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    public RiskResponseCode call(JsonNode request, RiskUrl riskUrlPath) {

        try {
            ResponseEntity<JsonNode> response = restTemplate.postForEntity(
                    riskUrl + riskUrlPath.value(),
                    request,
                    JsonNode.class
            );

            if(response.getStatusCode().is2xxSuccessful()){
                JsonNode body = response.getBody();

                String orderNo = body.get("body").asText();
                String code = body.get("code").asText();
                String message = body.get("message").asText();

                return code.equals("0000") ? RiskResponseCode.S200 : RiskResponseCode.F400;
            }
        } catch (RuntimeException e) {
            log.error("stock server가 응답이 없습니다.");
        }
        return RiskResponseCode.F500;
    }

}
