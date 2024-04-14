package ru.practicum.controller.pri;

import dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.RequestService;
import ru.practicum.service.event.EventService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * PrivateEventsController
 *  - addEventPrivate - add new event
 *  - getEventsByUserIdPrivate - get list events from page by user id
 *  - getEventByUserIdAndEventIdPrivate - get event by user id and event id
 *  - getRequestsOnEventByThisUserPrivate - get requests
 *  - updateEventPrivate - update event
 *  - changeStatusRequestOnEventByThisUserPrivate - change status request from requester by owner event
 */

@Slf4j
@Validated
@RestController
@RequestMapping("/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventsController {
    private static final String Event = "/{eventId}";
    private static final String Request_On_Event = "/{eventId}/requests";
    private final EventService eventService;
    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto addEventPrivate(@Positive(message = "user id must be positive.")
                                        @PathVariable("userId") Integer userId,
                                        @Valid @RequestBody NewEventDto newEventDto) {
        log.info("request events POST/addEventPrivate : userId - {}, newEventDto - {}", userId, newEventDto);

        EventFullDto response = eventService.addEventPrivate(userId, newEventDto);
        log.info("response events POST/addEventPrivate : {}", response);

        return response;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getEventsByUserIdPrivate(@Positive(message = "user id must be positive.")
                                                     @PathVariable("userId") Integer userId,
                                                     @RequestParam(value = "from", defaultValue = "0") Integer from,
                                                     @RequestParam(value = "size", defaultValue = "10") Integer size) {
        log.info("request events GET/getEventsByUserIdPrivate : userId - {}, from - {}, size - {}", userId, from, size);

        List<EventShortDto> response = eventService.getEventsByUserIdPrivate(userId, from, size);
        log.info("response events GET/getEventsByUserIdPrivate : {}", response);

        return response;
    }

    @GetMapping(Event)
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEventByUserIdAndEventIdPrivate(@Positive(message = "user id must be positive.")
                                                          @PathVariable("userId") Integer userId,
                                                          @Positive(message = "user id must be positive.")
                                                          @PathVariable("eventId") Integer eventId) {
        log.info("request events GET/getEventByUserIdAndEventIdPrivate : userId - {}, eventId - {}", userId, eventId);

        EventFullDto response = eventService.getEventByUserIdAndEventIdPrivate(userId, eventId);
        log.info("response events GET/getEventByUserIdAndEventIdPrivate : {}", response);

        return response;
    }

    @GetMapping(Request_On_Event)
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getRequestsOnEventByThisUserPrivate(
                                                               @Positive(message = "user id must be positive.")
                                                               @PathVariable("userId") Integer userId,
                                                               @Positive(message = "user id must be positive.")
                                                               @PathVariable("eventId") Integer eventId) {
        log.info("request events GET/getRequestsOnEventByThisUserPrivate : userId - {}, eventId - {}", userId, eventId);

        List<ParticipationRequestDto> response = requestService.getRequestsOnEventByThisUserPrivate(userId, eventId);
        log.info("response events GET/getRequestsOnEventByThisUserPrivate : {}", response);

        return response;
    }

    @PatchMapping(Event)
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEventPrivate(@Positive(message = "user id must be positive.")
                                           @PathVariable("userId") Integer userId,
                                           @Positive(message = "user id must be positive.")
                                           @PathVariable("eventId") Integer eventId,
                                           @Valid @RequestBody UpdateEventUserRequest updateEventUserRequest) {
        log.info("request events PATCH/updateEventPrivate : userId - {}, eventId - {}, updateEventUserRequest - {}",
                userId, eventId, updateEventUserRequest);

        EventFullDto response = eventService.updateEventPrivate(userId, eventId, updateEventUserRequest);
        log.info("response events PATCH/updateEventPrivate : {}", response);

        return response;
    }

    @PatchMapping(Request_On_Event)
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResult changeStatusRequestOnEventByThisUserPrivate(
                                 @Positive(message = "user id must be positive.")
                                 @PathVariable("userId") Integer userId,
                                 @Positive(message = "user id must be positive.")
                                 @PathVariable("eventId") Integer eventId,
                                 @Valid @RequestBody EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        log.info("request events PATCH/changeStatusRequestOnEventByThisUserPrivate : userId - {}, eventId - {}," +
                "updateEventUserRequest - {}", userId, eventId, eventRequestStatusUpdateRequest);

        EventRequestStatusUpdateResult response = requestService.changeStatusRequestOnEventByThisUserPrivate(
                userId, eventId, eventRequestStatusUpdateRequest);
        log.info("response events PATCH/changeStatusRequestOnEventByThisUserPrivate : {}", response);

        return response;
    }
}