package ru.practicum;

import dto.EndpointHitDto;
import org.springframework.stereotype.Component;
import ru.practicum.entity.EndpointHit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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
                          .timestamp(LocalDateTime.parse(endpointHitDto.getTimestamp(),
                                  DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                          .build();
    }

    public EndpointHitDto toEndpointHitDto(EndpointHit endpointHit) {
        return EndpointHitDto.builder()
                             .id(endpointHit.getId())
                             .app(endpointHit.getApp())
                             .uri(endpointHit.getUri())
                             .ip(endpointHit.getIp())
                             .timestamp(endpointHit.getTimestamp().format(
                                     DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                             .build();
    }
}