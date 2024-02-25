package ru.practicum;

import dto.EndpointHitDto;
import dto.ViewStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Stats-Service.
 *  - getStats: output with sort on timestamp and uris
 *              output with sort on timestamp and uris and unique
 *              output with sort without timestamp and uris
 *              output with sort without timestamp and uris and unique
 *  *unique on ip, group on hits by ViewStatsDto.class.
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

        if (start.isAfter(end) && start != null && end != null) {
            throw new RuntimeException("start should not be after end");
        }

        if (start != null && end != null && !unique) {
            return statsRepository.getAllStatsByTime(start, end, Arrays.stream(uris).toList());
        } else if (start != null && end != null) {
            return statsRepository.getAllStatsByTimeAndDistinct(start, end, Arrays.stream(uris).toList());
        } else if (start == null && end == null && !unique) {
            return statsRepository.getAllStats(Arrays.stream(uris).toList());
        } else {
            return statsRepository.getAllStatsDistinct(Arrays.stream(uris).toList());
        }
    }
}