package ru.practicum.ewm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.dto.NewCompilationDto;
import ru.practicum.ewm.dto.UpdateCompilationRequest;
import ru.practicum.ewm.dto.response.Response;
import ru.practicum.ewm.service.CompilationService;

import javax.validation.Valid;

@RestController
@RequestMapping("/admin/compilations")
public class AdminCompilationController { // API для работы с подборками событий

    private final CompilationService service;

    @Autowired
    public AdminCompilationController(CompilationService service) {
        this.service = service;
    }


    // добавление новой подборки (подборка может не содержать событий)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto addNewCompilation(@RequestBody @Valid NewCompilationDto compilationDto) {
        return service.addNewCompilation(compilationDto);
    }

    // удаление подборки

    @DeleteMapping("/{compId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Response deleteCompilation(@PathVariable(value = "compId") Long compId) {
        return service.delete(compId);
    }

    // обновление информации о подборке

    @PatchMapping("/{compId}")
    public CompilationDto updateCompilationById(@PathVariable(value = "compId") Long compId,
                                                @RequestBody  UpdateCompilationRequest dto) {
        return service.updateCompilation(compId, dto);
    }
}
