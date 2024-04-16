package ru.practicum.service.event;

import dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import ru.practicum.client.StatsClient;
import ru.practicum.entity.category.Category;
import ru.practicum.entity.event.Event;
import ru.practicum.entity.event.State;
import ru.practicum.entity.user.User;
import ru.practicum.mapper.EventMapper;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.validate.exception.BadDataRequestException;
import ru.practicum.validate.exception.NotValidException;
import ru.practicum.validate.exception.ObjectNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * EventService
 */

@Transactional
@Service
@RequiredArgsConstructor
public class EventService {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final EventMapper eventMapper;

    /**
     *
     * @param userId - owner event (user) id
     * @param newEventDto - new event
     * @return - event, logic constraint on participant limit and datetime
     */
    public EventFullDto addEventPrivate(int userId, NewEventDto newEventDto) {
        if (LocalDateTime.parse(newEventDto.getEventDate(), formatter).isBefore(LocalDateTime.now().plusHours(2))) {
            throw new NotValidException("The start date of the event must be no earlier than 2 hours from the" +
                    "current time.");
        }

        if (newEventDto.getParticipantLimit() < 0) {
            throw new NotValidException("ParticipantLimit must be positive.");
        }

        return eventMapper.toEventFullDto(eventRepository.save(eventMapper.toEvent(checkUser(userId),
                checkCategory(newEventDto.getCategory()), newEventDto)));
    }

    /**
     *
     * @param userId - owner events(user) id
     * @param from - start page
     * @param size - size events on page
     * @return - list events from page by owner events (user) id
     */
    public List<EventShortDto> getEventsByUserIdPrivate(int userId, int from, int size) {
        return eventRepository.findAllByInitiatorId(userId, PageRequest.of(from, size)).stream()
                .map(eventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param id - event id
     * @param servletRequest - HttpServletRequest for send stats hit on stats-service
     * @param client - client for send stats hit
     * @return - published event by id, logic constraint published
     */
    public EventFullDto getOnlyPublicEventByIdPublic(int id, HttpServletRequest servletRequest, StatsClient client) {
        Event foundedEvent = eventRepository.findByIdAndState(id, State.PUBLISHED).orElseThrow(() ->
                new ObjectNotFoundException("published event id - " + id + " not found"));

        EventFullDto response = eventMapper.toEventFullDto(foundedEvent);

        sendStatistic(servletRequest, List.of(response), client, false);

        List<ViewStatsDto> views =
                getViews(LocalDateTime.now().minusYears(2).truncatedTo(ChronoUnit.SECONDS).format(formatter),
                        LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(formatter),
                        new Integer[]{foundedEvent.getId()}, "true", client);

        if (!views.isEmpty()) {
            response.setViews(views.get(0).getHits());
        }

        return response;
    }

    /**
     *
     * @param userId - owner event(user) id
     * @param eventId - event id
     * @return - event
     */
    public EventFullDto getEventByUserIdAndEventIdPrivate(int userId, int eventId) {
        checkUser(userId);

        return eventMapper.toEventFullDto(checkEvent(eventId));
    }

    /**
     * selection parameters:
     * @param text - text
     * @param categories - array categories
     * @param paid - paid
     * @param rangeStart - datetime start, found after this time
     * @param rangeEnd - datetime end, found before this time
     * @param onlyAvailable - available
     * @param sort - sort on EVENT_DATE/VIEWS(from statistic)
     * @param from - start page
     * @param size - size events on page
     * @param servletRequest - HttpServletRequest
     * @param client - Stats-Client
     * @return - list events from page by selection parameters, logic constraint datetime, published
     */

    public List<EventFullDto> getEventsPublic(String text, Integer[] categories, Boolean paid, LocalDateTime rangeStart,
                                        LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, int from, int size,
                                        HttpServletRequest servletRequest, StatsClient client) {
        if (rangeStart != null && rangeEnd != null) {
            if (rangeStart.isAfter(rangeEnd)) {
                throw new NotValidException("The start date cannot be later than the end date.");
            }

            if (rangeStart.equals(rangeEnd)) {
                throw new NotValidException("The start date cannot be equals the end date.");
            }
        }

        List<EventFullDto> response;

        if (sort != null) {
            if (sort.equals("EVENT_DATE")) {
                response = eventRepository.findAll(new EventSpecification(new EventFilter(text, new String[]{"PUBLISHED"},
                      categories, paid, rangeStart, rangeEnd, onlyAvailable)), PageRequest.of(from / size, size,
                                Sort.by(Sort.Direction.ASC, "eventDate"))).stream()
                        .map(eventMapper::toEventFullDto)
                        .collect(Collectors.toList());

                return changeStatistic(servletRequest, response, client, true);
            }

            if (sort.equals("VIEWS")) {
                response = eventRepository.findAll(new EventSpecification(new EventFilter(text,
                    new String[]{"PUBLISHED"}, categories, paid, rangeStart, rangeEnd, onlyAvailable)),
                        PageRequest.of(from / size, size)).stream()
                        .map(eventMapper::toEventFullDto)
                        .collect(Collectors.toList());

                return changeStatistic(servletRequest, response, client, true).stream()
                        .sorted(Comparator.comparing(EventFullDto::getViews))
                        .collect(Collectors.toList());
            }
        }

        response = eventRepository.findAll(new EventSpecification(
                        new EventFilter(text, new String[]{"PUBLISHED"}, categories, paid, rangeStart, rangeEnd,
                                onlyAvailable)), PageRequest.of(from / size, size)).stream()
                .map(eventMapper::toEventFullDto)
                .collect(Collectors.toList());

        return changeStatistic(servletRequest, response, client, true);
    }

    /**
     * selection parameters:
     * @param users - array users
     * @param states - array states
     * @param categories - array categories
     * @param rangeStart - datetime start, found after this time
     * @param rangeEnd - datetime end, found before this time
     * @param from - start page
     * @param size - size events on page
     * @return - list events from page by  selection parameters
     */
    public List<EventFullDto> getSuitableEventsAdmin(Integer[] users, String[] states, Integer[] categories,
                                                     LocalDateTime rangeStart, LocalDateTime rangeEnd, int from,
                                                     int size) {

        Page<Event> response =
                eventRepository.findAll(new EventSpecification(
                        new EventFilter(users, states, categories, rangeStart, rangeEnd)), PageRequest.of(from, size));

        return response.stream()
                .map(eventMapper::toEventFullDto)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param userId - user id
     * @param eventId - event id
     * @param updateEventUserRequest update event
     * @return - update event, logic constraint datetime, not published
     */
    public EventFullDto updateEventPrivate(int userId, int eventId, UpdateEventUserRequest updateEventUserRequest) {
        checkUser(userId);

        Event foundedEvent = checkEvent(eventId);

        if (foundedEvent.getState().equals(State.PUBLISHED)) {
            throw new BadDataRequestException("Event must not be published",
                    "For the requested operation the conditions are not met.");
        }

        if (updateEventUserRequest.getAnnotation() != null) {
            foundedEvent.setAnnotation(updateEventUserRequest.getAnnotation());
        }

        if (updateEventUserRequest.getCategory() != null) {
            foundedEvent.setCategory(checkCategory(updateEventUserRequest.getCategory()));
        }

        if (updateEventUserRequest.getDescription() != null) {
            foundedEvent.setDescription(updateEventUserRequest.getDescription());
        }

        if (updateEventUserRequest.getEventDate() != null && !updateEventUserRequest.getEventDate().isBlank()) {
            if (LocalDateTime.parse(updateEventUserRequest.getEventDate(), formatter).isBefore(
                    LocalDateTime.now().plusHours(2))) {
                throw new NotValidException("The start date of the event must be no earlier than 2 hours" +
                        "from the current time.");
            }

            foundedEvent.setEventDate(LocalDateTime.parse(updateEventUserRequest.getEventDate(), formatter));
        }

        if (updateEventUserRequest.getLocation() != null) {
            foundedEvent.setLat(updateEventUserRequest.getLocation().getLat());
            foundedEvent.setLon(updateEventUserRequest.getLocation().getLon());
        }

        if (updateEventUserRequest.getPaid() != null) {
            foundedEvent.setPaid(updateEventUserRequest.getPaid());
        }

        if (updateEventUserRequest.getParticipantLimit() != null) {
            foundedEvent.setParticipantLimit(updateEventUserRequest.getParticipantLimit());
        }

        if (updateEventUserRequest.getRequestModeration() != null) {
            foundedEvent.setRequestModeration(updateEventUserRequest.getRequestModeration());
        }

        if (updateEventUserRequest.getStateAction() != null) {
            if (updateEventUserRequest.getStateAction().equals("SEND_TO_REVIEW")) {
                foundedEvent.setState(State.PENDING);
            } else if (updateEventUserRequest.getStateAction().equals("CANCEL_REVIEW")) {
                foundedEvent.setState(State.CANCELED);
            }
        }

        if (updateEventUserRequest.getTitle() != null) {
            foundedEvent.setTitle(updateEventUserRequest.getTitle());
        }

        return eventMapper.toEventFullDto(eventRepository.save(foundedEvent));
    }

    /**
     *
     * @param eventId - event id
     * @param updateEventAdminRequest - update event
     * @return - event with update datetime, status
     */
    public EventFullDto changeDataOrStatusEventAdmin(int eventId, UpdateEventAdminRequest updateEventAdminRequest) {
        Event foundedEvent = checkEvent(eventId);

        if (updateEventAdminRequest.getEventDate() != null) {
            if (LocalDateTime.parse(updateEventAdminRequest.getEventDate(), formatter).isBefore(
                    LocalDateTime.now().plusHours(1))) {
                throw new NotValidException("The start date of the event must be no earlier than 1 hours from the" +
                        "current time.");
            }
            foundedEvent.setEventDate(LocalDateTime.parse(updateEventAdminRequest.getEventDate(), formatter));
        }

        if (updateEventAdminRequest.getAnnotation() != null) {
            foundedEvent.setAnnotation(updateEventAdminRequest.getAnnotation());
        }

        if (updateEventAdminRequest.getCategory() != null) {
            foundedEvent.setCategory(checkCategory(updateEventAdminRequest.getCategory()));
        }

        if (updateEventAdminRequest.getDescription() != null) {
            foundedEvent.setDescription(updateEventAdminRequest.getDescription());
        }

        if (updateEventAdminRequest.getEventDate() != null && !updateEventAdminRequest.getEventDate().isBlank()) {
            foundedEvent.setEventDate(LocalDateTime.parse(updateEventAdminRequest.getEventDate(), formatter));
        }

        if (updateEventAdminRequest.getLocation() != null) {
            foundedEvent.setLat(updateEventAdminRequest.getLocation().getLat());
            foundedEvent.setLon(updateEventAdminRequest.getLocation().getLon());
        }

        if (updateEventAdminRequest.getPaid() != null) {
            foundedEvent.setPaid(updateEventAdminRequest.getPaid());
        }

        if (updateEventAdminRequest.getParticipantLimit() != null) {
            foundedEvent.setParticipantLimit(updateEventAdminRequest.getParticipantLimit());
        }

        if (updateEventAdminRequest.getRequestModeration() != null) {
            foundedEvent.setRequestModeration(updateEventAdminRequest.getRequestModeration());
        }

        if (updateEventAdminRequest.getStateAction() != null) {
            if (updateEventAdminRequest.getStateAction().equals("PUBLISH_EVENT")) {
                if (foundedEvent.getState().equals(State.PUBLISHED)) {
                    throw new BadDataRequestException("Cannot publish the event because it's not in the right state:" +
                            "PUBLISHED", "For the requested operation the conditions are not met.");
                }

                if (foundedEvent.getState().equals(State.CANCELED)) {
                    throw new BadDataRequestException("Cannot publish the event because it's not in the right state:" +
                            "CANCELED", "For the requested operation the conditions are not met.");
                }

                foundedEvent.setState(State.PUBLISHED);
                foundedEvent.setPublishedOn(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            } else if (updateEventAdminRequest.getStateAction().equals("REJECT_EVENT")) {
                if (foundedEvent.getState().equals(State.PUBLISHED)) {
                    throw new BadDataRequestException("Cannot cancel the event because it's not in the right state:" +
                            "PUBLISHED", "For the requested operation the conditions are not met.");
                }

                foundedEvent.setState(State.CANCELED);
            }
        }

        if (updateEventAdminRequest.getTitle() != null) {
            foundedEvent.setTitle(updateEventAdminRequest.getTitle());
        }

        return eventMapper.toEventFullDto(eventRepository.save(foundedEvent));
    }

    /**
     *
     * @param userId - user id
     * @return - check user or not found exception
     */
    private User checkUser(int userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ObjectNotFoundException("user id - " + userId + " not found"));
    }

    /**
     *
     * @param categoryId - category id
     * @return - check category or not found exception
     */
    private Category checkCategory(int categoryId) {
        return categoryRepository.findById(categoryId).orElseThrow(() ->
                new ObjectNotFoundException("category id - " + categoryId + " not found"));
    }

    /**
     *
     * @param eventId - event id
     * @return - check event or not found exception
     */
    private Event checkEvent(int eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
                new ObjectNotFoundException("event id - " + eventId + " not found"));
    }

    private void sendStatistic(HttpServletRequest servletRequest, List<EventFullDto> hits, StatsClient client,
                               boolean selector) {
        for (EventFullDto event : hits) {
            client.addEndpointHit(new EndpointHitDto(null, "ewm-main-service", "/events/" + event.getId(),
                    servletRequest.getRemoteAddr(), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        }

        if (selector) {
            client.addEndpointHit(new EndpointHitDto(null, "ewm-main-service", "/events",
                    servletRequest.getRemoteAddr(), LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));
        }
    }

    private List<ViewStatsDto> getViews(String start, String end, Integer[] ids, String unique, StatsClient client) {
        List<String> requestUris = new ArrayList<>();

        for (Integer id : List.of(ids)) {
            requestUris.add("/events/" + id);
        }

        return client.getViewStats(start, end, requestUris.toArray(new String[0]), unique);
    }

    private List<EventFullDto> changeStatistic(HttpServletRequest servletRequest, List<EventFullDto> response,
                                               StatsClient client, boolean selector) {
        if (!response.isEmpty()) {
            sendStatistic(servletRequest, response, client, selector);

            List<Integer> ids = new ArrayList<>();

            for (EventFullDto event : response) {
                ids.add(event.getId());
            }

            List<ViewStatsDto> views =
                    getViews(LocalDateTime.now().minusYears(2).truncatedTo(ChronoUnit.SECONDS).format(formatter),
                            LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).format(formatter),
                            ids.toArray(new Integer[0]), "true", client);

            for (EventFullDto event : response) {
                for (ViewStatsDto stats : views) {
                    if (stats.getUri().contains(String.valueOf(event.getId()))) {
                        event.setViews(stats.getHits());
                    }
                }

                if (event.getViews() == null) {
                    event.setViews(0);
                }
            }
        }

        return response;
    }
}