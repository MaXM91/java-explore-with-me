package ru.practicum.controller.adm;

import dto.EventFullDto;
import dto.UpdateEventAdminRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.event.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class AdminEventsController {
    private final EventService eventService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getSuitableEventsAdmin(@Nullable @RequestParam(name = "users") Integer[] users,
                                                     @Nullable @RequestParam(name = "states") String[] states,
                                                     @Nullable @RequestParam(name = "categories") Integer[] categories,
                                                     @Nullable @RequestParam(name = "rangeStart")
                                                         @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                         LocalDateTime rangeStart,
                                                     @Nullable @RequestParam(name = "rangeEnd")
                                                         @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                                         LocalDateTime rangeEnd,
                                                     @RequestParam(defaultValue = "0") Integer from,
                                                     @RequestParam(defaultValue = "10") Integer size) {
        log.info("request events GET/getSuitableEventsAdmin : users - {}, states - {}, categories - {}," +
                "rangeStart - {}, rangeEnd - {}, from - {}, size - {}",
                users, states, categories, rangeStart, rangeEnd, from, size);

        List<EventFullDto> response =
                eventService.getSuitableEventsAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
        log.info("response events GET/getSuitableEventsAdmin : {}", response);

        return response;
    }

    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto changeDataOrStatusEventAdmin(@Positive(message = "event id must be positive.")
                                                 @PathVariable(name = "eventId") Integer eventId,
                                                 @Valid @RequestBody UpdateEventAdminRequest updateEventAdminRequest) {
        log.info("request events PATCH/changeDataOrStatusEventAdmin : eventId - {}, updateEventAdminRequest - {}",
                eventId, updateEventAdminRequest);

        EventFullDto response =
                eventService.changeDataOrStatusEventAdmin(eventId, updateEventAdminRequest);
        log.info("response events PATCH/changeDataOrStatusEventAdmin : {}", response);

        return response;
    }
}