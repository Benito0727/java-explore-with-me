package ru.practicum.ewm.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.dto.EndpointHitDto;
import ru.practicum.ewm.dto.ViewStatsDto;

import java.util.*;

@Component("EwmClient")
public class EwmClient {

    private final RestTemplate template;

    public EwmClient(@Value("${ewm-stats-server-url}") String serverUrl, RestTemplateBuilder builder) {
       this.template = builder
               .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
               .requestFactory(HttpComponentsClientHttpRequestFactory::new)
               .build();

    }

    public EndpointHitDto addHit(EndpointHitDto dto) {
        HttpEntity<EndpointHitDto> requestEntity = new HttpEntity<>(dto);
        return template.postForObject("/hit", requestEntity, EndpointHitDto.class);
    }

    public List<ViewStatsDto> getStats(String start, String end, List<String> uris, Boolean isUnique) {

        Map<String, Object> parameter = new HashMap<>();
        StringBuilder path = new StringBuilder();
        path.append("/stats?");
        if (start != null) {
            parameter.put("start", start);
            path.append("start={start}");
        }
        if (end != null) {
            parameter.put("end", end);
            if (start != null) path.append("&");
            path.append("end={end}");
        }
        if (uris != null) {
            parameter.put("uris", uris.toArray());
            if (start != null || end != null) path.append("&");
            path.append("uris={uris}");
        }
        if (isUnique != null) {
            parameter.put("unique", isUnique);
            if (start != null || end != null || uris != null) path.append("&");
            path.append("unique={unique}");
        }

        try {
            ResponseEntity<List<ViewStatsDto>> response = template.exchange(path.toString(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<>() {},
                    parameter);


            return response.getBody();
        } catch (HttpClientErrorException exception) {
            throw new RuntimeException(exception);
        }
    }
}
