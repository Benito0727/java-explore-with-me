package ru.practicum.ewm.model.mapper;

import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.model.entity.Compilation;

import java.util.stream.Collectors;

public class CompilationEntityDtoMapper {

    public static CompilationDto mappingDtoFrom(Compilation compilation) {
        CompilationDto dto = new CompilationDto();
        dto.setId(compilation.getId());
        dto.setTitle(compilation.getTitle());
        dto.setEvents(compilation.getEvents().stream()
                .map(EventEntityDtoMapper::mappingShortDtoFrom)
                .collect(Collectors.toList()));
        dto.setPinned(compilation.getIsPinned());
        return dto;
    }
}
