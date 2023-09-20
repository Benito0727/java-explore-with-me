package ru.practicum.ewm.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CategoryDto;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class PublicCategoriesController { // публичный API для работы с категориями

    // получение категорий

    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(value = "size", defaultValue = "10") Integer size,
                                           @RequestParam(value = "from", defaultValue = "0") Integer from) {
        // todo

        // В случае, если по заданным фильтрам не найдено ни одной категории, возвращает пустой список
        return null;
    }

    // получение информации о категории по ее идетнификатору

    @GetMapping("{catId}")
    public CategoryDto getCategoryById(@PathVariable(value = "catId") Long catId) {

        // todo

        // В случае, если категории с заданным id не найдено, возвращает статус код 404

        return null;
    }
}
