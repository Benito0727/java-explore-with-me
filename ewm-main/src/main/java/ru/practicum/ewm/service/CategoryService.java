package ru.practicum.ewm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dao.CategoryRepository;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.NewCategoryDto;
import ru.practicum.ewm.exception.BadRequestException;
import ru.practicum.ewm.exception.ConflictException;
import ru.practicum.ewm.model.entity.Category;
import ru.practicum.ewm.model.mapper.CategoryEntityDtoMapper;
import ru.practicum.ewm.dto.response.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.model.mapper.CategoryEntityDtoMapper.*;

@Service
@Slf4j
public class CategoryService {

    private final CategoryRepository storage;

    private final ObjectChecker checker;

    @Autowired
    public CategoryService(CategoryRepository storage,
                           ObjectChecker checker) {
        this.storage = storage;
        this.checker = checker;
    }

    public CategoryDto addCategory(NewCategoryDto dto) {
        checker.checkCategory(dto.getName());
        Category category = new Category();
        category.setName(dto.getName());
        log.info("Создана новая категория с именем: {}", dto.getName());
        return mappingDtoFrom(storage.save(category));
    }

    public CategoryDto getCategoryById(Long categoryId) {
        Category category = checker.checkCategory(categoryId);
        return CategoryEntityDtoMapper.mappingDtoFrom(category);
    }

    public Response deleteCategory(Long catId) {
        Category category = checker.checkCategory(catId);
        try {
            if (category.getEvents().isEmpty()) {
                storage.delete(category);
                log.info("Категория {} была удалена", category.getName());
                return new Response("Category was deleted");
            } else {
                throw new ConflictException("You cannot delete a category that events are associated with",
                        "Events are associated with this category",
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        } catch (BadRequestException exception) {
            throw new RuntimeException(exception);
        }
    }

    public CategoryDto updateCategory(Long catId, NewCategoryDto dto) {
        Category category = checker.checkCategory(catId);

        if (dto.getName() != null) {
            if (!category.getName().equals(dto.getName())) {
                checker.checkCategory(dto.getName());
                log.info("Имя категории {} было изменено на {}", category.getName(), dto.getName());
                category.setName(dto.getName());
            }
        }
        return mappingDtoFrom(storage.save(category));
    }

    public Page<CategoryDto> getCategories(Integer from, Integer size) {
        List<Category> categories = storage.findAll();
        if (!categories.isEmpty()) {
            List<CategoryDto> categoriesDto = categories.stream()
                    .map(CategoryEntityDtoMapper::mappingDtoFrom)
                    .collect(Collectors.toList());

            Pageable pageRequest = PageRequest.of((from / size), size, Sort.by("id"));
            int start = (int) pageRequest.getOffset();
            int end = Math.min((start + pageRequest.getPageSize()), categoriesDto.size());
            List<CategoryDto> pageContent = categoriesDto.subList(start, end);

            return new PageImpl<>(pageContent, pageRequest, categoriesDto.size());
        } else {
            return new PageImpl<>(Collections.emptyList());
        }
    }
}