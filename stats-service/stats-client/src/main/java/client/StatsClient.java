package client;


import dto.EndpointHitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class StatsClient extends StatsClientBase {
    private static final String URI = "http://localhost:9090";

    @Autowired
    public StatsClient(RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(URI))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object>  addEndpointHit(EndpointHitDto hit) {
        return post(URI + "/hit", hit);
    }

    public ResponseEntity<Object> getViewStats(LocalDateTime start, LocalDateTime end, String[]uris, boolean unique) {
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", unique
        );

        return get(URI + "/stats", null, parameters);
    }
}