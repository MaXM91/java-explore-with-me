package ru.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.entity.request.Request;

import java.util.List;

public interface RequestRepository extends JpaRepository<Request, Integer> {
    List<Request> findAllByRequesterId(int userId);

    List<Request> findAllByEventId(int eventId);

    List<Request> findAllByIdIn(Integer[] requestIds);
}