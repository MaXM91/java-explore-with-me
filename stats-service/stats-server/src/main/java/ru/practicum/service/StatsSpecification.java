package ru.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.practicum.entity.EndpointHit;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class StatsSpecification implements Specification<EndpointHit> {
    private final StatsFilter filter;

    @Override
    public Predicate toPredicate(Root<EndpointHit> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        final List<Predicate> predicates = new ArrayList<>();

        if (filter.getStart() != null) {
            getStartPredicate(root, criteriaBuilder).ifPresent(predicates::add);
        }

        if (filter.getEnd() != null) {
            getEndPredicate(root, criteriaBuilder).ifPresent(predicates::add);
        }

        if (filter.getUris() != null) {
            getUrisPredicate(root, criteriaBuilder).ifPresent(predicates::add);
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }

    private Optional<Predicate> getStartPredicate(Root<EndpointHit> root, CriteriaBuilder criteriaBuilder) {
        return Optional.ofNullable(filter.getStart()).map(start ->
                criteriaBuilder.greaterThan(root.get("stamp"), start));
    }

    private Optional<Predicate> getEndPredicate(Root<EndpointHit> root, CriteriaBuilder criteriaBuilder) {
        return Optional.ofNullable(filter.getEnd()).map(end ->
                criteriaBuilder.lessThan(root.get("stamp"), end));
    }

    private Optional<Predicate> getUrisPredicate(Root<EndpointHit> root, CriteriaBuilder criteriaBuilder) {
        return Optional.ofNullable(criteriaBuilder.in(root.get("uri")).value(filter.getUris()));
    }
}