package ru.practicum.validate.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadDataRequestException extends RuntimeException {
    private String reason;

    public BadDataRequestException(String message, String reason) {
        super(message);
        this.reason = reason;
    }
}