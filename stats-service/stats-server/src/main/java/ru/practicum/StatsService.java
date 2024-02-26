package ru.practicum;

import dto.EndpointHitDto;
import dto.ViewStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Stats-Service.
 * - getStats: output with sort on timestamp and uris
 * output with sort on timestamp and uris and unique
 * output with sort without timestamp and uris
 * output with sort without timestamp and uris and unique
 * *unique on ip, group on hits by ViewStatsDto.class.
 */
@Transactional
@Service
@RequiredArgsConstructor
public class StatsService {
    private final StatsRepository statsRepository;
    private final StatsMapper statsMapper;

    public void addHit(EndpointHitDto hit) {
        statsRepository.save(statsMapper.toEndpointHit(hit));
    }

    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        if (start != null && end == null || start == null && end != null) {
            throw new RuntimeException("start and end should be null/not null both");
        }

        if (start != null) {
            if (Objects.requireNonNull(start).isAfter(end)) {
                throw new RuntimeException("start should not be after end");
            }
            if (!unique) {
                return statsRepository.getAllStatsByTime(start, end, List.of(uris));
            } else {
                return statsRepository.getAllStatsByTimeAndDistinct(start, end, List.of(uris));
            }
        } else {
            if (!unique) {
                return statsRepository.getAllStats(List.of(uris));
            } else {
                return statsRepository.getAllStatsDistinct(List.of(uris));
            }
        }
    }
}