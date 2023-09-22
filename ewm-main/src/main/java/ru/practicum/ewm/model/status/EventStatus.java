package ru.practicum.ewm.model.status;

import ru.practicum.ewm.exception.NotFoundException;

import java.util.Arrays;
import java.util.Locale;

public enum EventStatus {
    WAITING_EVENT,
    REJECTED_EVENT,
    PUBLISHED_EVENT,
    CANCELED_EVENT;

    public static EventStatus from(String stringStatus) {
        try {
            return Arrays.stream(EventStatus.values())
                    .filter(status -> status.toString().equals(stringStatus.toUpperCase(Locale.ROOT)))
                    .findAny().orElseThrow(() -> new NotFoundException("Incorrectly status"));
        } catch (NotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static int getIdFrom (EventStatus status) {
        switch (status) {
            case PUBLISHED_EVENT:
                return 4;
            case WAITING_EVENT:
                return 1;
            case CANCELED_EVENT:
                return 3;
            case REJECTED_EVENT:
                return 2;
        }
        return -1;
    }

    public static int getIdFrom(String status) {
        switch (status.toUpperCase()) {
            case "WAITING_EVENT" :
                return 1;
            case "REJECTED_EVENT" :
                return 2;
            case "PUBLISHED_EVENT" :
                return 3;
            case "CANCELED_EVENT" :
                return 4;
            default:
                return -1;
        }
    }

    public static EventStatus getStatusFrom(int id) {
        switch (id) {
            case 1:
                return WAITING_EVENT;
            case 2:
                return REJECTED_EVENT;
            case 3:
                return CANCELED_EVENT;
            case 4:
                return PUBLISHED_EVENT;
        }
        return null;
    }
}
