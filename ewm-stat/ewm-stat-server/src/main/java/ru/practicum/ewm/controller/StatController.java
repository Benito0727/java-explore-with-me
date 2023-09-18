package ru.practicum.ewm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.ViewStatsDto;
import ru.practicum.ewm.model.response.Response;
import ru.practicum.ewm.service.StatsService;

import javax.validation.Valid;
import java.util.List;

@RestController
public class StatController {

    private final StatsService service;

    @Autowired
    public StatController(StatsService service) {
        this.service = service;
    }

    @PostMapping("/hit")
    public Response addHit(@Valid @RequestBody EndpointHitDto endpointHit) {
        return service.addEndpointHit(endpointHit);
    }

    @GetMapping("/stats")
    public List<ViewStatsDto> getStats(@RequestParam("start") String start,
                                       @RequestParam("end") String end,
                                       @RequestParam(value = "uris", required = false) List<String> uris,
                                       @RequestParam(value = "unique", defaultValue = "false") Boolean unique) {
        return service.getStatsByParameter(start, end, uris, unique);
    }
}
