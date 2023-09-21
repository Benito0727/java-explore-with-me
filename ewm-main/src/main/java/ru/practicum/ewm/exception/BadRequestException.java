package ru.practicum.ewm.exception;

import lombok.Getter;
import lombok.Setter;

public class BadRequestException extends Exception {

    @Getter
    @Setter
    private String reason;

    @Getter
    @Setter
    private String timestamp;
    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException(String message, String reason, String timestamp) {
        super(message);
        this.reason = reason;
        this.timestamp = timestamp;
    }
}
