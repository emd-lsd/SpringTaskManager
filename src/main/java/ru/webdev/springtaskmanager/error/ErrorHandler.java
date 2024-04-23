package ru.webdev.springtaskmanager.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.webdev.springtaskmanager.exception.DuplicateLoginException;
import ru.webdev.springtaskmanager.exception.InsufficientPermissionException;
import ru.webdev.springtaskmanager.exception.NotFoundDataException;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {
    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerNotFoundDataException(final NotFoundDataException exception) {
        log.warn("{}", exception.getMessage());
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handlerException(final Exception exception) {
        log.warn("{}", exception.getMessage());
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handlerInsufficientPermissionException(final InsufficientPermissionException exception) {
        log.warn("{}", exception.getMessage());
        return new ErrorResponse(exception.getMessage());
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handlerDuplicateEmailException(final DuplicateLoginException exception) {
        log.warn("{}", exception.getMessage());
        return new ErrorResponse(exception.getMessage());
    }
}