package ru.practicum.client;

import dto.EndpointHitDto;
import dto.ViewStatsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;

import java.util.List;
import java.util.Map;

@Service
public class StatsClient extends StatsClientBase {
    private static final String API_PREFIX = "/";
    private static final String API_HIT_PREFIX = "hit";
    private static final String API_STATS_PREFIX = "stats";

    @Autowired
    public StatsClient(@Value("${stats.server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public void addEndpointHit(EndpointHitDto hit) {
        post(API_HIT_PREFIX, hit);
    }

    public List<ViewStatsDto> getViewStats(String start, String end, String[]uris, String unique) {
        Map<String, Object> parameters = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", unique
        );

        return get(API_STATS_PREFIX + "?start={start}&end={end}&uris={uris}&unique={unique}", parameters);
    }
}