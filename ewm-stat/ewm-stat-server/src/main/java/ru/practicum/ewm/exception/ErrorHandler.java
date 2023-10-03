package ru.practicum.ewm.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.practicum.ewm.dto.response.ErrorResponse;

import javax.validation.constraints.NotNull;

@ControllerAdvice("ru.practicum.ewm")
@Slf4j
public class ErrorHandler {

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ru.practicum.ewm.dto.response.ErrorResponse> badRequestExceptionHandler(@NotNull final BadRequestException exception) {
        log.error(exception.getMessage());
        ru.practicum.ewm.dto.response.ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.toString(),
                exception.getReason(),
                exception.getMessage(),
                exception.getTimestamp());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
