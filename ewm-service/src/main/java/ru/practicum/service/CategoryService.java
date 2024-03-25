package ru.practicum.service;

import dto.CategoryDto;
import dto.NewCategoryDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.entity.category.Category;
import ru.practicum.mapper.CategoryMapper;
import ru.practicum.repository.CategoryRepository;
import ru.practicum.validate.exception.ObjectNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryDto addCategoryAdmin(NewCategoryDto newCategoryDto) {
        return categoryMapper.toCategoryDto(categoryRepository.save(categoryMapper.toCategory(newCategoryDto)));
    }

    public CategoryDto getCategoryByIdPublic(int catId) {
        return categoryMapper.toCategoryDto(checkCategory(catId));
    }

    public List<CategoryDto> getAllCategoriesPublic(int from, int size) {
        return categoryRepository.findAll(PageRequest.of(from, size)).stream()
                .map(categoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    public CategoryDto patchCategoryAdmin(int catId, CategoryDto categoryDto) {
        checkCategory(catId);

        categoryDto.setId(catId);

        return categoryMapper.toCategoryDto(categoryRepository.save(categoryMapper.toCategory(categoryDto)));
    }

    public void deleteCategoryAdmin(int catId) {
        checkCategory(catId);

        categoryRepository.deleteById(catId);
    }

    private Category checkCategory(int catId) {
        return categoryRepository.findById(catId).orElseThrow(() ->
                new ObjectNotFoundException("category id - " + catId + " not found"));
    }
}
