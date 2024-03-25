package ru.practicum.service;

import dto.EndpointHitDto;
import dto.ViewStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.StatsMapper;
import ru.practicum.StatsRepository;
import ru.practicum.validation.NotValidException;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Stats-Service.
 * - getStats: output with sort on timestamp and uris
 * output without uris
 * output without uris unique
 * output with sort on timestamp and uris and unique
 * output with sort without timestamp and uris
 * output with sort without timestamp and uris and unique
 * *unique on ip, group on hits by ViewStatsDto.class.
 */
@Transactional
@Service
@RequiredArgsConstructor
public class StatsService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final StatsRepository statsRepository;
    private final StatsMapper statsMapper;

    public void addHit(EndpointHitDto hit) {
        statsRepository.save(statsMapper.toEndpointHit(hit));
    }

    public List<ViewStatsDto> getStats(String startTime, String endTime, String[] uris, boolean unique) {
        LocalDateTime start;
        LocalDateTime end;

        if (startTime != null && endTime != null) {
            start = LocalDateTime.parse(startTime, formatter);
            end = LocalDateTime.parse(endTime, formatter);

            if (start.isAfter(end)) {
                throw new NotValidException("The start date cannot be later than the end date.");
            }

            if (start.equals(end)) {
                throw new NotValidException("The start date cannot be equals the end date.");
            }

            if (uris != null && uris[0] != null) {
                if (unique) {
                    return statsRepository.findAllByStartAndEndAndUrisAndUnique(start, end, uris);
                } else {
                    return statsRepository.findAllByStartAndEndAndUris(start, end, uris);
                }
            } else {
                if (unique) {
                    return statsRepository.findAllByStartAndEndAndUnique(start, end);
                } else {
                    return statsRepository.findAllByStartAndEnd(start, end);
                }
            }
        } else {
            throw new NotValidException("The start/end date cannot be null.");
        }
    }
}