package ru.practicum.ewm.exception;

import lombok.Getter;
import lombok.Setter;

public class ConflictException extends Exception {

    @Getter
    @Setter
    private String reason;

    @Getter
    @Setter
    private String timestamp;

    public ConflictException() {
        super();
    }

    public ConflictException(String message) {
        super(message);
    }

    public ConflictException(String message, String reason, String timestamp) {
        super(message);
        this.reason = reason;
        this.timestamp = timestamp;
    }
}
