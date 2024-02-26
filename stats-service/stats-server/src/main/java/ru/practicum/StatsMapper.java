package ru.practicum;

import dto.EndpointHitDto;
import org.springframework.stereotype.Component;
import ru.practicum.entity.EndpointHit;
/**
 * StatsMapper.
 */

@Component
public class StatsMapper {
    public EndpointHit toEndpointHit(EndpointHitDto endpointHitDto) {
        return EndpointHit.builder()
                          .app(endpointHitDto.getApp())
                          .uri(endpointHitDto.getUri())
                          .ip(endpointHitDto.getIp())
                          .timestamp(endpointHitDto.getTimestamp())
                          .build();
    }

    public EndpointHitDto toEndpointHitDto(EndpointHit endpointHit) {
        return EndpointHitDto.builder()
                             .id(endpointHit.getId())
                             .app(endpointHit.getApp())
                             .uri(endpointHit.getUri())
                             .ip(endpointHit.getIp())
                             .timestamp(endpointHit.getTimestamp())
                             .build();
    }
}