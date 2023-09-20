package ru.practicum.ewm.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.NewCategoryDto;
import ru.practicum.ewm.model.response.Response;

@RestController
@RequestMapping("/admin/categories")
public class AdminCategoriesController { // API для работы с категориями

    @PostMapping
    public CategoryDto addNewCategory(@RequestBody NewCategoryDto categoryDto) {
        // todo
        //{
        //  "name": "Концерты"
        //}
        return null;
    }

    @DeleteMapping("/{catId}")
    public Response deleteCategory(@PathVariable(value = "catId") Long catId) {
        // todo
        // Обратите внимание: с категорией не должно быть связано ни одного события.
        return null;
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@PathVariable(value = "catId") Long catId,
                                      @RequestBody NewCategoryDto categoryDto) {
        // todo
        //Обратите внимание: имя категории должно быть уникальным
        return null;
    }
}
