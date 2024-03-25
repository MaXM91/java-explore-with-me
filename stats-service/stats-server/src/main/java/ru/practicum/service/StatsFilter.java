package ru.practicum.service;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class StatsFilter {
    private LocalDateTime start;
    private LocalDateTime end;
    private List<String> uris;

    public StatsFilter(String start, String end, String[] uris) {
        if (start != null && !start.isBlank()) {
            this.start = LocalDateTime.parse(start, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

        if (end != null && !end.isBlank()) {
            this.end = LocalDateTime.parse(end, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

        if (uris != null) {
            this.uris = Stream.of(uris).collect(Collectors.toList());
        }
    }

    @Override
    public String toString() {
        return  "\n" +
                " EventFilter{\n" +
                "            start = " + start + "\n" +
                "            end = " + end + "\n" +
                "            uris = " + uris + "\n" +
                "            }";
    }
}
