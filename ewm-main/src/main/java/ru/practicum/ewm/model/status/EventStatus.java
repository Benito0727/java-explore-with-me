package ru.practicum.ewm.model.status;

import ru.practicum.ewm.exception.NotFoundException;

import java.util.Arrays;
import java.util.Locale;

public enum EventStatus {
    WAITING,
    REJECTED,
    APPROVED,
    CANCELED;

    public static EventStatus from(String stringStatus) {
        try {
            return Arrays.stream(EventStatus.values())
                    .filter(status -> status.toString().equals(stringStatus.toUpperCase(Locale.ROOT)))
                    .findAny().orElseThrow(() -> new NotFoundException("Incorrectly status"));
        } catch (NotFoundException exception) {
            throw new RuntimeException(exception);
        }
    }

    public static int from (EventStatus status) {
        switch (status) {
            case APPROVED:
                return 4;
            case WAITING:
                return 1;
            case CANCELED:
                return 3;
            case REJECTED:
                return 2;
        }
        return -1;
    }

    public static EventStatus from(int id) {
        switch (id) {
            case 1:
                return WAITING;
            case 2:
                return REJECTED;
            case 3:
                return CANCELED;
            case 4:
                return APPROVED;
        }
        return null;
    }
}
