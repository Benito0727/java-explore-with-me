package ru.practicum.ewm.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.ewm.dto.response.ErrorResponse;

import javax.validation.constraints.NotNull;

@ControllerAdvice()
@Slf4j
public class ErrorHandler {

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> badRequestExceptionHandler(@NotNull final BadRequestException exception) {
        log.error(exception.getMessage());
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.toString(),
                exception.getReason(),
                exception.getMessage(),
                exception.getTimestamp());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> notFoundExceptionHandler(@NotNull final NotFoundException exception) {
        log.error(exception.getMessage());
        ErrorResponse response = new ErrorResponse(HttpStatus.NOT_FOUND.toString(),
                exception.getReason(),
                exception.getMessage(),
                exception.getTimestamp());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> validationExceptionHandler(@NotNull final ValidationException exception) {
        log.error(exception.getMessage());
        ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.toString(),
                exception.getReason(),
                exception.getMessage(),
                exception.getTimestamp());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorResponse> conflictExceptionHandler(@NotNull final ConflictException exception) {
        log.error(exception.getMessage());

        ErrorResponse response = new ErrorResponse(HttpStatus.CONFLICT.toString(),
                exception.getReason(),
                exception.getMessage(),
                exception.getTimestamp());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
