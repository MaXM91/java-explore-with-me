package ru.practicum.service;

import dto.EventRequestStatusUpdateRequest;
import dto.EventRequestStatusUpdateResult;
import dto.ParticipationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.entity.event.Event;
import ru.practicum.entity.event.State;
import ru.practicum.entity.request.Request;
import ru.practicum.entity.user.User;
import ru.practicum.mapper.RequestMapper;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.RequestRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.validate.exception.BadDataRequestException;
import ru.practicum.validate.exception.ObjectNotFoundException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * RequestService
 */

@Transactional
@Service
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RequestMapper requestMapper;

    /**
     *
     * @param userId - user id
     * @param eventId - event id
     * @return request on event, logic constraint datetime, published, your event, participant limit
     */
    public ParticipationRequestDto addRequestPrivate(int userId, int eventId) {
        User foundedUser = checkUser(userId);
        Event foundedEvent = checkEvent(eventId);

        if (!foundedEvent.getState().equals(State.PUBLISHED)) {
            throw new BadDataRequestException(
                "event id - " + eventId + " not published.", "Integrity constraint has been violated.");
        }

        if (foundedEvent.getInitiator().getId() == userId) {
            throw new BadDataRequestException(
                "cannot send a request to participate in your event.", "Integrity constraint has been violated.");
        }

        if (foundedEvent.getConfirmedRequests().equals(foundedEvent.getParticipantLimit()) &&
                foundedEvent.getParticipantLimit() != 0) {
            throw new BadDataRequestException(
                "The participant limit has been reached", "For the requested operation the conditions are not met.");
        }

        Request response = requestMapper.toRequest(foundedUser, foundedEvent);

        if (!foundedEvent.isRequestModeration() || foundedEvent.getParticipantLimit() == 0) {
            response.setStatus(State.CONFIRMED);

            foundedEvent.setConfirmedRequests(foundedEvent.getConfirmedRequests() + 1);
            eventRepository.save(foundedEvent);

            return requestMapper.toParticipationRequestDto(requestRepository.save(response));
        }

        return requestMapper.toParticipationRequestDto(requestRepository.save(response));
    }

    /**
     *
     * @param userId - owner requests(user) id
     * @return list requests by owner id
     */
    public List<ParticipationRequestDto> getParticipationRequestDtoByIdPrivate(int userId) {
        checkUser(userId);

        return requestRepository.findAllByRequesterId(userId).stream()
                .map(requestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param userId - user id
     * @param eventId - event id
     * @return - list requests by event id
     */
    public List<ParticipationRequestDto> getRequestsOnEventByThisUserPrivate(int userId, int eventId) {
        checkUser(userId);
        checkEvent(eventId);

        return requestRepository.findAllByEventId(eventId).stream()
                .map(requestMapper::toParticipationRequestDto)
                .collect(Collectors.toList());
    }

    /**
     *
     * @param userId - owner event(user) id
     * @param requestId - request id
     * @return - update request(status) by owner event
     */
    public ParticipationRequestDto updateStatusPrivate(int userId, int requestId) {
        checkUser(userId);

        Request response = checkRequest(requestId);

        if (response.getStatus().equals(State.PUBLISHED)) {
            throw new BadDataRequestException("you cannot cancel confirmed requests.",
                    "For the requested operation the conditions are not met.");
        }

        response.setStatus(State.CANCELED);
        return requestMapper.toParticipationRequestDto(requestRepository.save(response));
    }

    /**
     *
     * @param userId - user id
     * @param eventId - event id
     * @param eventRequestStatusUpdateRequest - update request
     * @return - updated request by owner request, logic constraint datetime, confirmed, participant limit
     */
    public EventRequestStatusUpdateResult changeStatusRequestOnEventByThisUserPrivate(int userId, int eventId,
        EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {

        checkUser(userId);
        Event foundedEvent = checkEvent(eventId);

        List<Request> foundedRequests =
                requestRepository.findAllByIdIn(eventRequestStatusUpdateRequest.getRequestIds());

        if (eventRequestStatusUpdateRequest.getStatus().equals("REJECTED")) {
            for (Request request : foundedRequests) {
                if (request.getStatus().equals(State.CONFIRMED)) {
                    throw new BadDataRequestException("you cannot cancel confirmed request id - " +
                            request.getId() + ".", "For the requested operation the conditions are not met.");
                }

                request.setStatus(State.REJECTED);
            }

            requestRepository.saveAll(foundedRequests);
            return new EventRequestStatusUpdateResult(new ArrayList<>(), foundedRequests.stream()
                    .map(requestMapper::toParticipationRequestDto)
                    .collect(Collectors.toList()));
        } else {
            List<Request> confirmed = new ArrayList<>();
            List<Request> rejected = new ArrayList<>();

            if (foundedEvent.getParticipantLimit() == 0) {
                for (Request request : foundedRequests) {
                    if (request.getStatus().equals(State.CONFIRMED)) {
                        throw new BadDataRequestException("you cannot cancel confirmed request id - " +
                                request.getId() + ".", "For the requested operation the conditions are not met.");
                    }

                    if (foundedEvent.getConfirmedRequests().equals(foundedEvent.getParticipantLimit()) &&
                    foundedEvent.getParticipantLimit() != 0) {
                        throw new BadDataRequestException("The participant limit has been reached",
                            "For the requested operation the conditions are not met.");
                    } else {
                        request.setStatus(State.CONFIRMED);
                        foundedEvent.setConfirmedRequests(foundedEvent.getConfirmedRequests() + 1);
                    }
                }

                requestRepository.saveAll(foundedRequests);
                eventRepository.save(foundedEvent);

                return new EventRequestStatusUpdateResult(foundedRequests.stream()
                        .map(requestMapper::toParticipationRequestDto)
                        .collect(Collectors.toList()), null);
            } else {
                if (foundedEvent.getConfirmedRequests().equals(foundedEvent.getParticipantLimit())) {
                    throw new BadDataRequestException("The participant limit has been reached",
                        "For the requested operation the conditions are not met.");
                }

                for (Request request : foundedRequests) {
                    if (request.getStatus().equals(State.CONFIRMED)) {
                        throw new BadDataRequestException("you cannot cancel confirmed request id - " +
                                request.getId() + ".", "For the requested operation the conditions are not met.");
                    }

                    if (foundedEvent.getConfirmedRequests().equals(foundedEvent.getParticipantLimit()) &&
                            foundedEvent.getParticipantLimit() != 0) {
                        request.setStatus(State.REJECTED);
                        rejected.add(request);
                    } else {
                        request.setStatus(State.CONFIRMED);
                        foundedEvent.setConfirmedRequests(foundedEvent.getConfirmedRequests() + 1);
                        confirmed.add(request);
                    }
                }

                requestRepository.saveAll(foundedRequests);
                eventRepository.save(foundedEvent);

                return new EventRequestStatusUpdateResult(confirmed.stream()
                        .map(requestMapper::toParticipationRequestDto)
                        .collect(Collectors.toList()), rejected.stream()
                        .map(requestMapper::toParticipationRequestDto)
                        .collect(Collectors.toList()));
            }
        }
    }

    private User checkUser(int userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ObjectNotFoundException("user id - " + userId + " not found"));
    }

    private Event checkEvent(int eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
                new ObjectNotFoundException("event id - " + eventId + " not found"));
    }

    private Request checkRequest(int requestId) {
        return requestRepository.findById(requestId).orElseThrow(() ->
                new ObjectNotFoundException("request id - " + requestId + " not found"));
    }
}