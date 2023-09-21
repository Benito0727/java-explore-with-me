package ru.practicum.ewm.exception;

import lombok.Getter;
import lombok.Setter;

public class ValidationException extends Exception {

    @Getter
    @Setter
    private String reason;

    @Getter
    @Setter
    private String timestamp;

    public ValidationException() {
        super();
    }

    public ValidationException(String message) {
        super(message);
    }

    public ValidationException(String message, String reason, String timestamp) {
        super(message);
        this.reason = reason;
        this.timestamp = timestamp;
    }
}
