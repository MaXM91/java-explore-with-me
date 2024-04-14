package ru.practicum.controller.pri;

import dto.ParticipationRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.RequestService;

import javax.validation.constraints.Positive;
import java.util.List;

/**
 * PrivateRequestController
 *  - addRequestPrivate - add request
 *  - getParticipationRequestDtoByIdPrivate - get list requests by owner id
 *  - updateStatusPrivate - update status request, on canceled
 */

@Slf4j
@Validated
@RestController
@RequestMapping("users/{userId}/requests")
@RequiredArgsConstructor
public class PrivateRequestController {
    private static final String Request_Cancel = "/{requestId}/cancel";
    private final RequestService requestService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto addRequestPrivate(@Positive(message = "user id must be positive.")
                                                     @PathVariable("userId") Integer userId,
                                                     @Positive(message = "event id must be positive.")
                                                     @Positive @RequestParam("eventId") Integer eventId) {
        log.info("request requests POST/addRequestPrivate : userId - {}, eventId - {}", userId, eventId);

        ParticipationRequestDto response = requestService.addRequestPrivate(userId, eventId);
        log.info("response requests POST/addRequestPrivate : {}", response);

        return response;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getParticipationRequestDtoByIdPrivate(
                                                                 @Positive(message = "user id must be positive.")
                                                                 @PathVariable Integer userId) {
        log.info("request requests GET/getParticipationRequestDtoByIdPrivate : userId - {}", userId);

        List<ParticipationRequestDto> response = requestService.getParticipationRequestDtoByIdPrivate(userId);
        log.info("response requests GET/getParticipationRequestDtoByIdPrivate : {}", response);

        return response;
    }

    @PatchMapping(Request_Cancel)
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto updateStatusPrivate(@Positive(message = "user id must be positive.")
                                                       @PathVariable("userId") Integer userId,
                                                       @Positive(message = "request id must be positive.")
                                                       @PathVariable("requestId") Integer requestId) {
        log.info("request requests PATCH/updateStatusPrivate : userId - {}, requestId - {}", userId, requestId);

        ParticipationRequestDto response = requestService.updateStatusPrivate(userId, requestId);
        log.info("response requests PATCH/updateStatusPrivate : {}", response);

        return response;
    }
}