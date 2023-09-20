package ru.practicum.ewm.controller;

import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.dto.NewCompilationDto;
import ru.practicum.ewm.model.response.Response;

@RestController
@RequestMapping("/admin/compilations")
public class AdminCompilationController { // API для работы с подборками событий

    // добавление новой подборки (подборка может не содержать событий)

    @PostMapping
    public CompilationDto addNewCompilation(@RequestBody NewCompilationDto compilationDto) {
        // todo
        return null;
    }

    // удаление подборки

    @DeleteMapping("/{compId}")
    public Response deleteCompilation(@PathVariable(value = "compId") Long compId) {
        // todo
        return null;
    }

    // обновление информации о подборке

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilationById(@PathVariable(value = "compId") Long compId) {
        // todo
        return null;
    }
}
