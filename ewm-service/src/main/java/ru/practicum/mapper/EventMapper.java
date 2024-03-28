package ru.practicum.mapper;

import dto.*;
import org.springframework.stereotype.Component;
import ru.practicum.entity.category.Category;
import ru.practicum.entity.event.Event;
import ru.practicum.entity.event.State;
import ru.practicum.entity.user.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Component
public class EventMapper {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Event toEvent(User user, Category category, NewEventDto newEventDto) {
        return Event.builder()
                    .annotation(newEventDto.getAnnotation())
                    .category(category)
                    .confirmedRequests(0)
                    .createdOn(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                    .description(newEventDto.getDescription())
                    .eventDate(LocalDateTime.parse(newEventDto.getEventDate(), formatter))
                    .initiator(user)
                    .lat(newEventDto.getLocation().getLat())
                    .lon(newEventDto.getLocation().getLon())
                    .paid(newEventDto.isPaid())
                    .participantLimit(newEventDto.getParticipantLimit())
                    .requestModeration(newEventDto.isRequestModeration())
                    .state(State.PENDING)
                    .title(newEventDto.getTitle())
                    .build();
    }

    public EventFullDto toEventFullDto(Event event) {
        if (event.getPublishedOn() == null) {
            return EventFullDto.builder()
                    .annotation(event.getAnnotation())
                    .category(new CategoryDto(event.getCategory().getId(), event.getCategory().getName()))
                    .confirmedRequests(event.getConfirmedRequests())
                    .createdOn(event.getCreatedOn().format(formatter))
                    .description(event.getDescription())
                    .eventDate(event.getEventDate().format(formatter))
                    .id(event.getId())
                    .initiator(new UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()))
                    .location(new Location(event.getLat(), event.getLon()))
                    .paid(event.isPaid())
                    .participantLimit(event.getParticipantLimit())
                    .publishedOn(null)
                    .requestModeration(event.isRequestModeration())
                    .state(event.getState().toString())
                    .title(event.getTitle())
                    .views(0)
                    .build();
        } else {
            return EventFullDto.builder()
                    .annotation(event.getAnnotation())
                    .category(new CategoryDto(event.getCategory().getId(), event.getCategory().getName()))
                    .confirmedRequests(event.getConfirmedRequests())
                    .createdOn(event.getCreatedOn().format(formatter))
                    .description(event.getDescription())
                    .eventDate(event.getEventDate().format(formatter))
                    .id(event.getId())
                    .initiator(new UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()))
                    .location(new Location(event.getLat(), event.getLon()))
                    .paid(event.isPaid())
                    .participantLimit(event.getParticipantLimit())
                    .publishedOn(event.getPublishedOn().format(formatter))
                    .requestModeration(event.isRequestModeration())
                    .state(event.getState().toString())
                    .title(event.getTitle())
                    .views(0)
                    .build();
        }
    }

    public EventShortDto toEventShortDto(Event event) {
        return EventShortDto.builder()
                .annotation(event.getAnnotation())
                .category(new CategoryDto(event.getCategory().getId(), event.getCategory().getName()))
                .confirmedRequests(event.getConfirmedRequests())
                .eventDate(event.getEventDate().format(formatter))
                .id(event.getId())
                .initiator(new UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()))
                .paid(event.isPaid())
                .title(event.getTitle())
                .build();
    }
}