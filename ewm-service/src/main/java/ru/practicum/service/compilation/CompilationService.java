package ru.practicum.service.compilation;

import dto.CompilationDto;
import dto.NewCompilationDto;
import dto.UpdateCompilationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.entity.compilation.Compilation;
import ru.practicum.mapper.CompilationMapper;
import ru.practicum.repository.CompilationRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.validate.exception.ObjectNotFoundException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class CompilationService {
    private final CompilationMapper compilationMapper;
    private final CompilationRepository compilationRepository;
    private final EventRepository eventRepository;

    public CompilationDto addCompilationAdmin(NewCompilationDto newCompilationDto) {
        if (newCompilationDto.getEvents() != null) {
            List<Integer> eventsId = newCompilationDto.getEvents().stream()
                                                                  .filter(x -> x != 0)
                                                                  .distinct()
                                                                  .collect(Collectors.toList());

            if (eventsId.isEmpty()) {
                return compilationMapper.toCompilationDto(compilationRepository.save(
                        compilationMapper.toCompilation(newCompilationDto, new ArrayList<>())));
            } else {
                return compilationMapper.toCompilationDto(compilationRepository.save(
                        compilationMapper.toCompilation(newCompilationDto,
                                eventRepository.findAllByIdIn(eventsId))));
            }
        } else {
            return compilationMapper.toCompilationDto(compilationRepository.save(
                    compilationMapper.toCompilation(newCompilationDto, new ArrayList<>())));
        }
    }

    public CompilationDto getCompilationByIdPublic(int compId) {
        Compilation foundedCompilation = checkCompilation(compId);
        return compilationMapper.toCompilationDto(foundedCompilation);
    }

    public List<CompilationDto> getCompilationPublic(Boolean pinned, int from, int size) {
        if (pinned == null) {
            return compilationRepository.findAll(PageRequest.of(from, size)).stream()
                                                                            .map(compilationMapper::toCompilationDto)
                                                                            .collect(Collectors.toList());
        } else {
            return compilationRepository.findAllByPinned(pinned, PageRequest.of(from, size)).stream()
                    .map(compilationMapper::toCompilationDto)
                    .collect(Collectors.toList());
        }
    }

    public CompilationDto updateCompilationAdmin(int compId, UpdateCompilationRequest updateCompilationRequest) {
        Compilation foundedCompilation = checkCompilation(compId);

        if (updateCompilationRequest.getEvents() != null) {
            foundedCompilation.setEvents(eventRepository.findAllByIdIn(updateCompilationRequest.getEvents()));
        }

        if (updateCompilationRequest.getPinned() != null) {
            foundedCompilation.setPinned(updateCompilationRequest.getPinned());
        }

        if (updateCompilationRequest.getTitle() != null) {
            foundedCompilation.setTitle(updateCompilationRequest.getTitle());
        }

        return compilationMapper.toCompilationDto(compilationRepository.save(foundedCompilation));
    }

    private Compilation checkCompilation(int compId) {
        return compilationRepository.findById(compId).orElseThrow(() ->
                new ObjectNotFoundException("compilation id - " + compId + " not found"));
    }

    public void deleteCompilationAdmin(int compId) {
        checkCompilation(compId);

        compilationRepository.deleteById(compId);
    }
}