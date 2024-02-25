package ru.practicum.validation;

import lombok.Data;
/**
 * ErrorResponse.
 */

@Data
public class ErrorResponse {
    String error;

    ErrorResponse(String error) {
        this.error = error;
    }
}