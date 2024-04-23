package ru.webdev.springtaskmanager.exception;

public class NotFoundDataException extends RuntimeException {
    public NotFoundDataException(String message) {
        super(message);
    }
}
