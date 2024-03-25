package ru.practicum.mapper;

import dto.ParticipationRequestDto;
import org.springframework.stereotype.Component;
import ru.practicum.entity.event.Event;
import ru.practicum.entity.event.State;
import ru.practicum.entity.request.Request;
import ru.practicum.entity.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class RequestMapper {
    public Request toRequest(User user, Event event) {
        return Request.builder()
                      .created(LocalDateTime.now())
                      .event(event)
                      .requester(user)
                      .status(State.PENDING)
                      .build();
    }

    public ParticipationRequestDto toParticipationRequestDto(Request request) {
        return ParticipationRequestDto.builder()
                                      .created(request.getCreated().format(
                                              DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                                      .event(request.getEvent().getId())
                                      .id(request.getId())
                                      .requester(request.getRequester().getId())
                                      .status(request.getStatus().toString())
                                      .build();
    }
}