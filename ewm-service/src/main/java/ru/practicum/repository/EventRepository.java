package ru.practicum.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.practicum.entity.event.Event;
import ru.practicum.entity.event.State;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Integer>, JpaSpecificationExecutor<Event> {
    Page<Event> findAllByInitiatorId(int initiatorId, Pageable pageable);

    List<Event> findAllByIdIn(List<Integer> eventIds);

    Optional<Event> findByIdAndState(int id, State state);
}