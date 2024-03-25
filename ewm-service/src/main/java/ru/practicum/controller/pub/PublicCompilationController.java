package ru.practicum.controller.pub;

import dto.CompilationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.compilation.CompilationService;

import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/compilations")
@RequiredArgsConstructor
public class PublicCompilationController {
    private final CompilationService compilationService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CompilationDto> getCompilationPublic(@Nullable @RequestParam(name = "pinned") Boolean pinned,
                                                     @RequestParam(defaultValue = "0") int from,
                                                     @RequestParam(defaultValue = "10") int size) {
        log.info("request compilations GET/getCompilationPublic : pinned - {}, from - {}, size - {}",
                pinned, from, size);

        List<CompilationDto> response = compilationService.getCompilationPublic(pinned, from, size);
        log.info("response compilations GET/getCompilationPublic : {}", response);

        return response;
    }

    @GetMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto getCompilationByIdPublic(@Positive(message = "compId must be positive or null.")
                                                   @PathVariable(name = "compId") int compId) {
        log.info("request compilations GET/getCompilationByIdPublic : compId - {}", compId);

        CompilationDto response = compilationService.getCompilationByIdPublic(compId);
        log.info("response compilations GET/getCompilationByIdPublic : {}", response);

        return response;
    }
}