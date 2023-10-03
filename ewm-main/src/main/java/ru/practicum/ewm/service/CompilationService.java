package ru.practicum.ewm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.dao.CompilationRepository;
import ru.practicum.ewm.dao.EventRepository;
import ru.practicum.ewm.dto.CompilationDto;
import ru.practicum.ewm.dto.NewCompilationDto;
import ru.practicum.ewm.dto.ShortEventDto;
import ru.practicum.ewm.dto.UpdateCompilationRequest;
import ru.practicum.ewm.exception.ValidationException;
import ru.practicum.ewm.model.entity.Compilation;
import ru.practicum.ewm.model.entity.Event;
import ru.practicum.ewm.model.mapper.CompilationEntityDtoMapper;
import ru.practicum.ewm.model.mapper.EventEntityDtoMapper;
import ru.practicum.ewm.dto.response.Response;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewm.model.mapper.CompilationEntityDtoMapper.*;

@Service
@Slf4j
public class CompilationService {

    private final CompilationRepository storage;

    private final EventRepository eventStorage;

    private final ObjectChecker checker;

    @Autowired
    public CompilationService(CompilationRepository storage,
                              EventRepository eventStorage,
                              ObjectChecker checker) {
        this.storage = storage;
        this.eventStorage = eventStorage;
        this.checker = checker;
    }

    public CompilationDto addNewCompilation(NewCompilationDto dto) {
        Compilation compilation = new Compilation();
        if (dto.getPinned() != null) {
            compilation.setIsPinned(dto.getPinned());
        } else {
            compilation.setIsPinned(false);
        }
        compilation.setTitle(dto.getTitle());
        List<Event> events = new ArrayList<>();
        if (dto.getEvents() != null && !dto.getEvents().isEmpty()) {
            events = eventStorage.findEventsByIdIn(dto.getEvents());
            compilation.setEvents(events);
        } else {
            compilation.setEvents(Collections.emptyList());
        }
        CompilationDto compilationDto = mappingDtoFrom(storage.save(compilation));

        List<ShortEventDto> shortEvents = events.stream()
              .map(EventEntityDtoMapper::mappingShortDtoFrom)
                      .collect(Collectors.toList());
        compilationDto.setEvents(shortEvents);

        return compilationDto;
    }

    public Response delete(Long compId) {
        Compilation compilation = checker.checkCompilation(compId);
        log.info("Подборка с ид: {} была удалена", compilation);
        storage.delete(compilation);
        return new Response("Compilation has been deleted");
    }

    public CompilationDto updateCompilation(Long compId, UpdateCompilationRequest dto) {
        Compilation compilation = checker.checkCompilation(compId);
        if (dto.getEvents() != null && !dto.getEvents().isEmpty()) {
            compilation.setEvents(eventStorage.findEventsByIdIn(dto.getEvents()));
        }
        if (dto.getPinned() != null) {
            compilation.setIsPinned(dto.getPinned());
        }

        if (dto.getTitle() != null) {
            if (dto.getTitle().isBlank()) {
                throw new ValidationException("Compilation title must be not blank",
                        "Incorrect title",
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
            if (dto.getTitle().length() > 50) {
                throw new ValidationException(String.format("The title is too long length:%d", dto.getTitle().length()),
                        "The title should not be longer than 50 characters",
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
            compilation.setTitle(dto.getTitle());
        }
        return mappingDtoFrom(storage.save(compilation));
    }

    public Page<CompilationDto> getCompilations(Boolean isPinned, Integer size, Integer from) {
        List<CompilationDto> compilations;
        if (isPinned != null) {
            compilations = storage.findAllByIsPinnedIs(isPinned).stream()
                    .map(CompilationEntityDtoMapper::mappingDtoFrom)
                    .collect(Collectors.toList());
        } else {
            compilations = storage.findAll().stream()
                    .map(CompilationEntityDtoMapper::mappingDtoFrom)
                    .collect(Collectors.toList());
        }

        Pageable pageRequest = PageRequest.of((from / size), size, Sort.by("id"));

        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), compilations.size());

        List<CompilationDto> pageContent = compilations.subList(start, end);

        return new PageImpl<>(pageContent, pageRequest, compilations.size());
    }

    public CompilationDto getCompilationById(Long compId) {
        return mappingDtoFrom(checker.checkCompilation(compId));
    }
}
