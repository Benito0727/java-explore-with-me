package client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.dto.EndpointHitDto;

import java.util.List;
import java.util.Map;

public class EwmClient extends BaseClient {

    public EwmClient(@Value("${ewm-server-url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build()
        );
    }

    public ResponseEntity<Object> addHit(EndpointHitDto dto) {
        return post("/hit", dto);
    }

    public ResponseEntity<Object> getStats(String start, String end, List<String> uris, Boolean isUnique) {

        Map<String, Object> parameter = Map.of(
                "start", start,
                "end", end,
                "uris", uris,
                "unique", isUnique
        );

        return get("/stats", parameter);
    }

}
