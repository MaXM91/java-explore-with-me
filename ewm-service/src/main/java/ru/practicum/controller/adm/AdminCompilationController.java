package ru.practicum.controller.adm;

import dto.CompilationDto;
import dto.NewCompilationDto;
import dto.UpdateCompilationRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.compilation.CompilationService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@Validated
@RequestMapping(path = "/admin/compilations")
@RestController
@RequiredArgsConstructor
public class AdminCompilationController {
    private final CompilationService compilationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto addCompilationAdmin(@Valid @RequestBody NewCompilationDto newCompilationDto) {
        log.info("request compilations POST/addCompilationAdmin : newCompilationDto - {}", newCompilationDto);

        CompilationDto response = compilationService.addCompilationAdmin(newCompilationDto);
        log.info("response compilations POST/addCompilationAdmin : {}", response);

        return response;
    }

    @PatchMapping("/{compId}")
    @ResponseStatus(HttpStatus.OK)
    public CompilationDto updateCompilationAdmin(@Positive(message = "compilation id must be positive.")
                                                 @PathVariable(name = "compId") int compId,
                                                 @Valid @RequestBody UpdateCompilationRequest updateCompilationRequest) {
        log.info("request compilations PATCH/updateCompilationAdmin : compId - {}, updateCompilationRequest - {}",
                compId, updateCompilationRequest);

        CompilationDto response = compilationService.updateCompilationAdmin(compId, updateCompilationRequest);
        log.info("response compilations PATCH/updateCompilationAdmin : {}", response);

        return response;
    }

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilationAdmin(@Positive(message = "compilation id must be positive.")
                                       @PathVariable(name = "compId") int compId) {
        log.info("request compilations DELETE/deleteCompilationAdmin : compId - {}", compId);

        compilationService.deleteCompilationAdmin(compId);
        log.info("response compilations DELETE/deleteCompilationAdmin : compId - {} deleted", compId);
    }
}