package ru.practicum.mapper;

import dto.*;
import org.springframework.stereotype.Component;
import ru.practicum.entity.compilation.Compilation;
import ru.practicum.entity.event.Event;

import java.util.ArrayList;
import java.util.List;

@Component
public class CompilationMapper {
    public Compilation toCompilation(NewCompilationDto newCompilationDto, List<Event> events) {
        if (newCompilationDto.getPinned() == null) {
            return Compilation.builder()
                    .events(events)
                    .pinned(false)
                    .title(newCompilationDto.getTitle())
                    .build();
        } else {
            return Compilation.builder()
                    .events(events)
                    .pinned(newCompilationDto.getPinned())
                    .title(newCompilationDto.getTitle())
                    .build();
        }

    }

    public CompilationDto toCompilationDto(Compilation compilation) {
        return CompilationDto.builder()
                             .events(toEventShortDto(compilation.getEvents()))
                             .id(compilation.getId())
                             .pinned(compilation.getPinned())
                             .title(compilation.getTitle())
                             .build();
    }

    private List<EventShortDto> toEventShortDto(List<Event> events) {
        List<EventShortDto> response = new ArrayList<>();
        for (Event event : events) {
            response.add(new EventShortDto(event.getAnnotation(),
                    new CategoryDto(event.getCategory().getId(), event.getCategory().getName()),
                    event.getConfirmedRequests(),
                    event.getEventDate().toString(),
                    event.getId(),
                    new UserShortDto(event.getInitiator().getId(), event.getInitiator().getName()),
                    event.isPaid(),
                    event.getTitle(),
                    null
                    ));
        }
        return response;
    }
}