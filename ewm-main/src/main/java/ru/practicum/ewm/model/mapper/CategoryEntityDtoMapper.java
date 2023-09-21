package ru.practicum.ewm.model.mapper;

import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.model.entity.Category;

public class CategoryEntityDtoMapper {

    public static CategoryDto mappingDtoFrom(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getTitle());
        return dto;
    }

    public static Category mappingEntityFrom(CategoryDto dto) {
        Category category = new Category();
        category.setId(category.getId());
        category.setTitle(category.getTitle());
        return category;
    }
}
