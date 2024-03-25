package ru.practicum.validate;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class ApiError {
    private List<String> errors;

    private String status;

    private String reason;

    private String message;

    private String timestamp;

    public ApiError(String status, String reason, String message, String timestamp) {
        this.status = status;
        this.reason = reason;
        this.message = message;
        this.timestamp = timestamp;
    }

    public ApiError(List<String> errors, String status, String reason, String timestamp) {
        this.errors = errors;
        this.status = status;
        this.reason = reason;
        this.timestamp = timestamp;
    }
}
