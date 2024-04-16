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
    private final StatsRepository statsRepository;
    private final StatsMapper statsMapper;

    /**
     *
     * @param hit - stats information
     */
    public void addHit(EndpointHitDto hit) {
        statsRepository.save(statsMapper.toEndpointHit(hit));
    }

    /**
     *
     * @param start - min LocalDateTime
     * @param end - max LocalDateTime
     * @param uris - list of endpoints
     * @param unique - unique ip addresses false/true
     * @return - List<ViewStatsDto> depending on the availability of the selection parameters
     */
    public List<ViewStatsDto> getStats(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
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
    }
}