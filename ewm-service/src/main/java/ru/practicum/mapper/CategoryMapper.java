package ru.practicum.mapper;

import dto.CategoryDto;
import dto.NewCategoryDto;
import org.springframework.stereotype.Component;
import ru.practicum.entity.category.Category;

@Component
public class CategoryMapper {
    public Category toCategory(NewCategoryDto newCategoryDto) {
        return Category.builder()
                       .name(newCategoryDto.getName())
                       .build();
    }

    public Category toCategory(CategoryDto categoryDto) {
        return Category.builder()
                       .id(categoryDto.getId())
                       .name(categoryDto.getName())
                       .build();
    }

    public CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
                          .id(category.getId())
                          .name(category.getName())
                          .build();
    }
}
