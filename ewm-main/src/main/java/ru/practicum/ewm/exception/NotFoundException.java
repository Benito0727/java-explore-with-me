package ru.practicum.ewm.exception;

import lombok.Getter;
import lombok.Setter;

public class NotFoundException extends Exception {

    @Getter
    @Setter
    private String reason;

    @Getter
    @Setter
    private String timestamp;

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, String reason, String timestamp) {
        super(message);
        this.reason = reason;
        this.timestamp = timestamp;
    }
}
