package ru.practicum.ewm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CategoryDto;
import ru.practicum.ewm.dto.NewCategoryDto;
import ru.practicum.ewm.dto.response.Response;
import ru.practicum.ewm.service.CategoryService;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/categories")
public class AdminCategoriesController { // API для работы с категориями

    private final CategoryService service;

    @Autowired
    public AdminCategoriesController(CategoryService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto addNewCategory(@RequestBody @Valid NewCategoryDto categoryDto) {
        return service.addCategory(categoryDto);
    }

    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Response deleteCategory(@PathVariable(value = "catId") Long catId) {
        // Обратите внимание: с категорией не должно быть связано ни одного события.
        return service.deleteCategory(catId);
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@PathVariable(value = "catId") Long catId,
                                      @RequestBody @Valid NewCategoryDto categoryDto) {
        //Обратите внимание: имя категории должно быть уникальным
        return service.updateCategory(catId, categoryDto);
    }
}
