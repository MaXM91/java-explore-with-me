package ru.practicum.validate;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.practicum.validate.exception.BadDataRequestException;
import ru.practicum.validate.exception.NotValidException;
import ru.practicum.validate.exception.ObjectNotFoundException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)                                    // 400
    public ApiError violationOfRequestParameters(final ConstraintViolationException exc) {
        log.error(exc.getMessage(), exc);

        final List<String> violations = exc.getConstraintViolations().stream()
                .map(violation -> "Field: " + violation.getPropertyPath() + ". Error: " +
                        violation.getMessage() + " Value: " + violation.getInvalidValue())
                .collect(Collectors.toList());

        return getResponse(violations);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)                                    // 400
    public ApiError violationOfRequestBodyParameters(final MethodArgumentNotValidException exc) {
        log.error(exc.getMessage(), exc);

        final List<String> violations = exc.getBindingResult().getFieldErrors().stream()
                .map(error -> "Field: " + error.getField() + ". Error: " + error.getDefaultMessage() +
                        " Value: " + error.getRejectedValue())
                .collect(Collectors.toList());

        return getResponse(violations);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)                                    // 400
    public ApiError violationOfServiceData(final NotValidException exc) {
        log.error(exc.getMessage(), exc);

        return new ApiError("BAD_REQUEST", "Incorrectly made request.",
                exc.getLocalizedMessage(), LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)                                    // 404
    public ApiError notFound(final ObjectNotFoundException exc) {
        log.error(exc.getMessage(), exc);

        return new ApiError("NOT_FOUND", "The required object was not found.",
                exc.getLocalizedMessage(), LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.CONFLICT)                                      // 409
    public ApiError badLogicDataRequest(final BadDataRequestException exc) {
        log.error(exc.getMessage(), exc);

        return new ApiError("CONFLICT", exc.getReason(), exc.getMessage(), LocalDateTime.now().format(formatter));
    }

    @ExceptionHandler()
    @ResponseStatus(HttpStatus.CONFLICT)                                      // 409
    public ApiError badLogicDataRequest(final DataIntegrityViolationException exc) {
        log.error(exc.getMessage(), exc);

        return new ApiError("CONFLICT", "Integrity constraint has been violated.",
                exc.getMessage(), LocalDateTime.now().format(formatter));
    }

    private ApiError getResponse(List<String> violations) {
        if (violations.size() > 1) {
            return new ApiError(violations, "BAD_REQUEST", "Incorrectly made request.",
                    LocalDateTime.now().format(formatter));
        } else {
            return new ApiError("BAD_REQUEST", "Incorrectly made request.",
                    violations.get(0), LocalDateTime.now().format(formatter));
        }
    }
}