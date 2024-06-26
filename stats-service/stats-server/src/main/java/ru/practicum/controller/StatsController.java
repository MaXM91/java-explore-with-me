package ru.practicum.controller;

import dto.EndpointHitDto;
import dto.ViewStatsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.StatsService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Stats-controller.
 *  - addHit - input statistic.
 *  - getStats - output statistic.
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
public class StatsController {
    private static final String HIT = "/hit";
    private static final String STATS = "/stats";
    private final StatsService statsService;

    @PostMapping(HIT)
    @ResponseStatus(HttpStatus.CREATED)
    public void addHit(@RequestBody @Valid EndpointHitDto hit) {
        log.info("request POST/addHit : {}", hit);

        statsService.addHit(hit);
    }

    @GetMapping(STATS)
    @ResponseStatus(HttpStatus.OK)
    public List<ViewStatsDto> getStats(
                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                           @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                           @RequestParam @Nullable String[] uris,
                           @RequestParam (defaultValue = "false") boolean unique) {
        log.info("request GET/getStats");

        List<ViewStatsDto> responseObject = statsService.getStats(start, end, uris, unique);
        log.info("response GET/getStats : {}", responseObject);

        return responseObject;
    }
}