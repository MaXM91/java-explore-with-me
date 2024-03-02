package ru.practicum.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@RestController("/")
public class EwmController {
    @GetMapping
    public String getHello(@RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                           @RequestParam @Nullable @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                           @RequestParam @NotEmpty(message = "uris cannot be empty.")String[] uris,
                           @RequestParam (defaultValue = "false") boolean unique) {
        return "Hello!";
    }
}
