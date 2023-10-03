package ru.practicum.ewm.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends RuntimeException {

    @Getter
    @Setter
    private String reason;

    @Getter
    @Setter
    private String timestamp;

    public ValidationException(String message, String reason, String timestamp) {
        super(message);
        this.reason = reason;
        this.timestamp = timestamp;
    }
}
