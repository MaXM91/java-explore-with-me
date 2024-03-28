package ru.practicum.service.event;

import lombok.Data;
import ru.practicum.entity.event.State;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class EventFilter {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private List<Integer> users;
    private List<State> states;
    private List<Integer> categories;
    private LocalDateTime rangeStart;
    private LocalDateTime rangeEnd;
    private String text;
    private Boolean paid;
    private Boolean onlyAvailable;

    public EventFilter(Integer[] users, String[] states, Integer[] categories, String rangeStart, String rangeEnd) {
        if (users != null) {
            this.users = Stream.of(users).filter(x -> x != 0).distinct().collect(Collectors.toList());
        }
        if (states != null) {
            this.states = Stream.of(states).map(State::toState).distinct().collect(Collectors.toList());
        }
        if (categories != null) {
            this.categories = Stream.of(categories).filter(x -> x != 0).distinct().collect(Collectors.toList());
        }
        if (rangeStart != null && !rangeStart.isBlank()) {
            this.rangeStart = LocalDateTime.parse(rangeStart, formatter);
        }
        if (rangeEnd != null && !rangeEnd.isBlank()) {
            this.rangeEnd = LocalDateTime.parse(rangeEnd, formatter);
        }
        if (rangeStart != null && !rangeStart.isBlank() && rangeEnd != null && !rangeEnd.isBlank()) {
            this.rangeStart = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        }
    }

    public EventFilter(String text, String[] states, Integer[] categories, Boolean paid, String rangeStart, String rangeEnd,
                       Boolean onlyAvailable) {
        if (states != null) {
            this.states = Stream.of(states).map(State::toState).distinct().collect(Collectors.toList());
        }
        if (categories != null) {
            this.categories = Stream.of(categories).filter(x -> x != 0).distinct().collect(Collectors.toList());
        }
        if (rangeStart != null && !rangeStart.isBlank()) {
            this.rangeStart = LocalDateTime.parse(rangeStart, formatter);
        }
        if (rangeEnd != null && !rangeEnd.isBlank()) {
            this.rangeEnd = LocalDateTime.parse(rangeEnd, formatter);
        }
        if (rangeStart != null && !rangeStart.isBlank() && rangeEnd != null && !rangeEnd.isBlank()) {
            this.rangeStart = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        }
        this.text = text;
        this.paid = paid;
        this.onlyAvailable = onlyAvailable;
    }

    @Override
    public String toString() {
        return  "\n" +
                " EventFilter{\n" +
                "            users = " + users + "\n" +
                "            states = " + states + "\n" +
                "            categories = " + categories + "\n" +
                "            rangeStart = " + rangeStart + "\n" +
                "            rangeEnd = " + rangeEnd + "\n" +
                "            text = " + text + "\n" +
                "            paid = " + paid + "\n" +
                "            onlyAvailable = " + onlyAvailable + "\n" +
                "            }";
    }
}