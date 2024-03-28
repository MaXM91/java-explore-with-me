package ru.practicum.validation;

public class NotValidException extends RuntimeException {
    public NotValidException(String message) {
        super(message);
    }
}
