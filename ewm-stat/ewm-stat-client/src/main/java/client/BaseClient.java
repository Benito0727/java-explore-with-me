package client;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static org.springframework.http.HttpMethod.*;

public class BaseClient {

        private final RestTemplate template;

        public BaseClient(RestTemplate template) {
                this.template = template;
        }

        private static ResponseEntity<Object> prepareGatewayResponse(ResponseEntity<Object> response) {
                if (response.getStatusCode().is2xxSuccessful()) {
                        return response;
                }

                ResponseEntity.BodyBuilder responseBuilder = ResponseEntity.status(response.getStatusCode());

                if (response.hasBody()) {
                        return responseBuilder.body(response.getBody());
                }

                return responseBuilder.build();
        }

        private <T> ResponseEntity<Object> makeAndSendRequest(HttpMethod method,
                                                              String path,
                                                              Long userId,
                                                              Map<String, Object> parameters,
                                                              T body) {

                HttpEntity<T> requestEntity = new HttpEntity<>(body);

                ResponseEntity<Object> serverResponse;
                try {
                        if (parameters != null) {
                                serverResponse = template.exchange(path, method, requestEntity, Object.class, parameters);
                        } else {
                                serverResponse = template.exchange(path, method, requestEntity, Object.class);
                        }
                } catch (HttpStatusCodeException exception) {
                        return ResponseEntity.status(exception.getStatusCode()).body(exception.getResponseBodyAsByteArray());
                }

                return prepareGatewayResponse(serverResponse);
        }

        private ResponseEntity<Object> makeAndSendRequest(HttpMethod method,
                                                          String path,
                                                          Long userId,
                                                          Map<String, Object> parameter) {
                ResponseEntity<Object> serverResponse;
                HttpEntity<?> requestEntity = HttpEntity.EMPTY;
                try {
                        if (parameter != null) {
                                serverResponse = template.exchange(path, method, requestEntity, Object.class, parameter);
                        } else {
                                serverResponse = template.exchange(path, method, requestEntity, Object.class);
                        }
                } catch (HttpStatusCodeException exception) {
                        return ResponseEntity.status(exception.getStatusCode()).body(exception.getResponseBodyAsByteArray());
                }
                return prepareGatewayResponse(serverResponse);
        }

        protected ResponseEntity<Object> get(String path, Long userId, @Nullable Map<String, Object> parameter) {
                return makeAndSendRequest(GET, path, userId, parameter);
        }

        protected ResponseEntity<Object> get(String path, Long userId) {
                return get(path, userId, null);
        }

        protected ResponseEntity<Object> get(String path) {
                return get(path, null, null);
        }

        protected ResponseEntity<Object> get(String path, Map<String, Object> parameter) {
                return get(path, null, parameter);
        }

        protected <T> ResponseEntity<Object> post(String path,
                                                  Long userId,
                                                  @Nullable Map<String, Object> parameter,
                                                  T body) {
                return makeAndSendRequest(POST, path, userId, parameter, body);
        }

        protected <T> ResponseEntity<Object> post(String path,
                                                  Long userId,
                                                  T body) {
                return post(path, userId, null, body);
        }

        protected <T> ResponseEntity<Object> post(String path,
                                                  T body) {
                return post(path, null, body);
        }

        protected <T> ResponseEntity<Object> put(String path,
                                                 Long userId,
                                                 @Nullable Map<String, Object> parameter,
                                                 T body) {
                return makeAndSendRequest(PUT, path, userId, parameter, body);
        }

        protected <T> ResponseEntity<Object> put(String path,
                                                 Long userId,
                                                 T body) {
                return put(path, userId, null, body);
        }

        protected <T> ResponseEntity<Object> patch(String path,
                                                   Long userId,
                                                   @Nullable Map<String, Object> parameter,
                                                   T body) {
                return makeAndSendRequest(PATCH, path, userId, parameter, body);
        }

        protected <T> ResponseEntity<Object> patch(String path,
                                                   Long userId,
                                                   @Nullable Map<String, Object> parameter) {
                return patch(path, userId, parameter, null);
        }

        protected <T> ResponseEntity<Object> patch(String path,
                                                   Long userId,
                                                   T body) {
                return patch(path, userId, null, body);
        }

        protected <T> ResponseEntity<Object> patch(String path,
                                                   Long userId) {
                return patch(path, userId, null);
        }

        protected <T> ResponseEntity<Object> patch(String path,
                                                   T body) {
                return patch(path, null, body);
        }

        protected ResponseEntity<Object> delete(String path,
                                                Long userId,
                                                Map<String, Object> parameter) {
                return makeAndSendRequest(DELETE, path, userId, parameter);
        }

        protected ResponseEntity<Object> delete(String path,
                                                Long userId) {
                return delete(path, userId, null);
        }

        protected ResponseEntity<Object> delete(String path) {
                return delete(path, null);
        }
}
