package ru.practicum.controller.pub;

import dto.CategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.CategoryService;

import javax.validation.constraints.Positive;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class PublicCategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getCategoryByIdPublic(@Positive(message = "category id must be positive.")
                                             @PathVariable(name = "catId") int catId) {
        log.info("request categories GET/getCategoryByIdPublic : {}", catId);

        CategoryDto response = categoryService.getCategoryByIdPublic(catId);
        log.info("response categories GET/getCategoryByIdPublic : {}", response);

        return response;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getAllCategoriesPublic(@RequestParam(defaultValue = "0") int from,
                                                    @RequestParam(defaultValue = "10") int size) {
        log.info("request categories GET/getAllCategoriesPublic : {}, {}", from, size);

        List<CategoryDto> response = categoryService.getAllCategoriesPublic(from, size);
        log.info("response categories GET/getAllCategoriesPublic : {}", response);

        return response;
    }
}
