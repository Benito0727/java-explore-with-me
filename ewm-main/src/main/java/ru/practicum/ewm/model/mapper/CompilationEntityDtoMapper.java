package ru.practicum.ewm.model.mapper;

import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.model.entity.Compilation;

public class CompilationEntityDtoMapper {

    public static CompilationDto mappingDtoFrom(Compilation compilation) {
        CompilationDto dto = new CompilationDto();
        dto.setId(compilation.getId());
        dto.setTitle(compilation.getTitle());
        // todo не забыть про эвенты
        dto.setPinned(compilation.getIsPinned());
        return dto;
    }

    public static Compilation mappingEntityFrom(CompilationDto dto) {
        Compilation comp = new Compilation();
        comp.setId(dto.getId());
        comp.setTitle(dto.getTitle());
        // todo не забыть про эвенты
        comp.setIsPinned(dto.getPinned());
        return comp;
    }
}
