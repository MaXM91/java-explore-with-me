package ru.practicum.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.EndpointHitDto;
import dto.ViewStatsDto;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class StatsClientBase {
    protected final RestTemplate rest;

    public StatsClientBase(RestTemplate rest) {
        this.rest = rest;
    }

    protected EndpointHitDto post(String path, EndpointHitDto body) {
        ResponseEntity<EndpointHitDto> responseObject =
                makeAndSendRequest(HttpMethod.POST, path, null, body, new EndpointHitDto());

        return responseObject.getBody();
    }

    protected List<ViewStatsDto> get(String path, @Nullable Map<String, Object> parameters) {
        ResponseEntity<List<ViewStatsDto>> responseObject =
                makeAndSendRequest(HttpMethod.GET, path, parameters, null, new ArrayList<>());

        if (responseObject.getStatusCode().is2xxSuccessful() && responseObject.getBody() != null) {
            ObjectMapper mapper = new ObjectMapper();

            try {
                return List.of(mapper.readValue(
                        mapper.writeValueAsString(responseObject.getBody()),
                        ViewStatsDto[].class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e.getMessage());
            }
        }

        return new ArrayList<>();
    }

    private <T, L> ResponseEntity<L> makeAndSendRequest(HttpMethod method, String path,
                                   @Nullable Map<String, Object> parameters, @Nullable T body, L response) {
        HttpEntity<T> requestEntity = new HttpEntity<>(body, defaultHeaders());

        Class lClass = response.getClass();
        ResponseEntity<L> statsServerResponse;

        try {
            if (parameters != null) {
                statsServerResponse = rest.exchange(path, method, requestEntity, lClass, parameters);
            } else {
                statsServerResponse = rest.exchange(path, method, requestEntity, lClass);
            }
        } catch (HttpStatusCodeException e) {
            return (ResponseEntity<L>) ResponseEntity.status(e.getStatusCode()).body(e.getResponseBodyAsByteArray());
        }

        return prepareGatewayResponse(statsServerResponse);
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        return headers;
    }

    private static <T> ResponseEntity<T> prepareGatewayResponse(ResponseEntity<T> response) {
        if (response.getStatusCode().is2xxSuccessful()) {
            return response;
        }

        ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

        if (response.hasBody()) {
            return responseBuilder.body(response.getBody());
        }

        return responseBuilder.build();
    }
}