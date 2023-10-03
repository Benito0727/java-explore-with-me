package ru.practicum.ewm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.service.CompilationService;


import java.util.List;

@RestController
@RequestMapping("/compilations")
public class PublicCompilationsController { // Публичный API для работы с подборками событий

    private final CompilationService service;

    @Autowired
    public PublicCompilationsController(CompilationService service) {
        this.service = service;
    }

    @GetMapping
    public List<CompilationDto> getCompilations(@RequestParam(value = "pinned", required = false) Boolean pinned,
                                                @RequestParam(value = "size", defaultValue = "10") Integer size,
                                                @RequestParam(value = "from", defaultValue = "0") Integer from) {
        /*
    В случае, если по заданным фильтрам не найдено ни одной подборки, возвращает пустой список
     */
        return service.getCompilations(pinned, size, from).getContent();
    }

    @GetMapping("/{compId}")
    public CompilationDto getCompilationById(@PathVariable(name = "compId") Long compId) {
        /*
    В случае, если подборки с заданным id не найдено, возвращает статус код 404
     */
        return service.getCompilationById(compId);
    }
}
