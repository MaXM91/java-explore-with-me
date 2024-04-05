package ru.practicum.controller.pub;

import dto.EventFullDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.StatsClient;
import ru.practicum.service.event.EventService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class PublicEventController {
    private final EventService eventService;
    private final StatsClient client;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getEventsPublic(@Nullable @RequestParam("text") String text,
                                              @Nullable @RequestParam("categories") Integer[] categories,
                                              @Nullable @RequestParam("paid") Boolean paid,
                                              @Nullable @RequestParam(name = "rangeStart")
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                  LocalDateTime rangeStart,
                                              @Nullable @RequestParam(name = "rangeEnd")
                                                  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                  LocalDateTime rangeEnd,
                                              @RequestParam(value = "onlyAvailable", defaultValue = "false") Boolean onlyAvailable,
                                              @Nullable @RequestParam("sort") String sort,
                                              @RequestParam(defaultValue = "0") int from,
                                              @RequestParam(defaultValue = "10") int size,
                                              HttpServletRequest servletRequest) {
        log.info("request events GET/getEventsPublic : text - {}, categories - {}, paid - {}, rangeStart - {}," +
                " rangeEnd - {}, onlyAvailable - {}, sort - {}, from - {}, size - {}", text, categories, paid,
                rangeStart, rangeEnd, onlyAvailable, sort, from, size);

        List<EventFullDto> response =
            eventService.getEventsPublic(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, servletRequest, client);
        log.info("response events GET/getEventsPublic : {}", response);

       return response;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getOnlyPublicEventByIdPublic(@Positive(message = "event id must be positive.")
                                                     @PathVariable("id") Integer id,
                                                     HttpServletRequest servletRequest) {
        log.info("request events GET/getEventByIdPublic : id - {}", id);

        EventFullDto response = eventService.getOnlyPublicEventByIdPublic(id, servletRequest, client);
        log.info("response events GET/getEventByIdPublic : {}", response);

        return response;
    }
}