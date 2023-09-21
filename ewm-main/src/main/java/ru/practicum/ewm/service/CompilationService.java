package ru.practicum.ewm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dao.CompilationRepository;
import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.dto.NewCompilationDto;

@Service
public class CompilationService {

    private final CompilationRepository storage;

    @Autowired
    public CompilationService(CompilationRepository storage) {
        this.storage = storage;
    }

    public CompilationDto save(NewCompilationDto dto) {
        return null;
    }

}
