package ru.practicum.controller.adm;

import dto.CategoryDto;
import dto.NewCategoryDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class AdminCategoriesController {
    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto addCategoryAdmin(@Valid @RequestBody NewCategoryDto newCategoryDto) {
        log.info("request categories POST/addCategoryAdmin : {}", newCategoryDto);

        CategoryDto response = categoryService.addCategoryAdmin(newCategoryDto);
        log.info("response categories POST/addCategoryAdmin : {}", response);

        return response;
    }

    @PatchMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto patchCategoryAdmin(@Positive(message = "category id must be positive.")
                                          @PathVariable(name = "catId") int catId,
                                          @Valid @RequestBody CategoryDto categoryDto) {
        log.info("request categories PATCH/patchCategoryAdmin : {}, {}", catId, categoryDto);

        CategoryDto response = categoryService.patchCategoryAdmin(catId, categoryDto);
        log.info("response categories PATCH/patchCategoryAdmin : {}", response);

        return response;
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryAdmin(@Positive(message = "category id must be positive.")
                                    @PathVariable(name = "catId") int catId) {
        log.info("request categories DELETE/deleteCategoryAdmin : catId - {}", catId);

        categoryService.deleteCategoryAdmin(catId);
        log.info("response categories DELETE/deleteCategoryAdmin : catId - {} deleted", catId);
    }
}