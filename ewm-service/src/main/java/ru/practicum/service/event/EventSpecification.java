package ru.practicum.service.event;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.entity.event.Event;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class EventSpecification implements Specification<Event> {
    private EventFilter filter;

    @Override
    public Predicate toPredicate(Root<Event> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();

        if (filter.getUsers() != null && !filter.getUsers().isEmpty()) {
            getUsersPredicate(root, criteriaBuilder).ifPresent(predicates::add);
        }

        if (filter.getStates() != null) {
            getStatesPredicate(root, criteriaBuilder).ifPresent(predicates::add);
        }

        if (filter.getCategories() != null && !filter.getCategories().isEmpty()) {
            getCategoriesPredicate(root, criteriaBuilder).ifPresent(predicates::add);
        }

        if (filter.getRangeStart() != null) {
            getRangeStartPredicate(root, criteriaBuilder).ifPresent(predicates::add);
        }

        if (filter.getRangeEnd() != null) {
            getRangeEndPredicate(root, criteriaBuilder).ifPresent(predicates::add);
        }

        if (filter.getText() != null) {
            getTextPredicate(root, criteriaBuilder).ifPresent(predicates::add);
        }

        if (filter.getPaid() != null) {
            getPaidDescriptionPredicate(root, criteriaBuilder).ifPresent(predicates::add);
        }

        if (filter.getOnlyAvailable() != null) {
            getOnlyAvailablePredicate(root, criteriaBuilder).ifPresent(predicates::add);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Optional<Predicate> getUsersPredicate(Root<Event> root, CriteriaBuilder criteriaBuilder) {
        return Optional.ofNullable(criteriaBuilder.in(root.get("initiator").get("id")).value(filter.getUsers()));
    }

    private Optional<Predicate> getStatesPredicate(Root<Event> root, CriteriaBuilder criteriaBuilder) {
        return Optional.ofNullable(criteriaBuilder.in(root.get("state")).value(filter.getStates()));
    }

    private Optional<Predicate> getCategoriesPredicate(Root<Event> root, CriteriaBuilder criteriaBuilder) {
        return Optional.ofNullable(criteriaBuilder.in(root.get("category").get("id")).value(filter.getCategories()));
    }

    private Optional<Predicate> getRangeStartPredicate(Root<Event> root, CriteriaBuilder criteriaBuilder) {
        return Optional.ofNullable(filter.getRangeStart()).map(rangeStart ->
                criteriaBuilder.greaterThan(root.get("eventDate"), rangeStart));
    }

    private Optional<Predicate> getRangeEndPredicate(Root<Event> root, CriteriaBuilder criteriaBuilder) {
        return Optional.ofNullable(filter.getRangeEnd()).map(rangeEnd ->
            criteriaBuilder.lessThan(root.get("eventDate"), rangeEnd));
    }

    private Optional<Predicate> getTextPredicate(Root<Event> root, CriteriaBuilder criteriaBuilder) {
        return Optional.ofNullable(filter.getText()).map(text ->
            criteriaBuilder.or(criteriaBuilder.like(criteriaBuilder.lower(root.get("annotation")), text.toLowerCase()),
                criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), text.toLowerCase())));
    }

    private Optional<Predicate> getPaidDescriptionPredicate(Root<Event> root, CriteriaBuilder criteriaBuilder) {
        return Optional.ofNullable(filter.getPaid()).map(paid -> criteriaBuilder.isTrue(root.get("paid")));
    }

    private Optional<Predicate> getOnlyAvailablePredicate(Root<Event> root, CriteriaBuilder criteriaBuilder) {
            return Optional.ofNullable(criteriaBuilder.lessThan(root.get("confirmedRequests"),
                    (root.get("participantLimit"))));
    }
}