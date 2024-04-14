package ru.practicum.controller.adm;

import dto.NewUserRequest;
import dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * AdminUsersController.
 *  - addUserAdmin - add user
 *  - getUsersAdmin - get users on page
 *  - deleteUserAdmin - delete user by id
 */

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/admin/users")
@RequiredArgsConstructor
public class AdminUsersController {
    private static final String User = "/{userId}";
    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto addUserAdmin(@Valid @RequestBody NewUserRequest newUserRequest) {
        log.info("request users POST/addUserAdmin : {}", newUserRequest);

        UserDto response = userService.addUserAdmin(newUserRequest);
        log.info("response users POST/addUserAdmin : {}", response);

        return response;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getUsersAdmin(@Nullable @RequestParam(name = "ids") Integer[] ids,
                                       @RequestParam(defaultValue = "0") Integer from,
                                       @RequestParam(defaultValue = "10") Integer size) {
        log.info("request users GET/getUsersAdmin : ids - {}, from - {}, size - {}", ids, from, size);

        List<UserDto> response = userService.getUsersAdmin(ids, from, size);
        log.info("response users GET/getUsersAdmin : {}", response);

        return response;
    }

    @DeleteMapping(User)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUserAdmin(@Positive(message = "user id must be positive.")
                                @PathVariable(name = "userId") int userId) {
        log.info("request users DELETE/deleteUserAdmin : userId - {}", userId);

        userService.deleteUserAdmin(userId);
        log.info("response users DELETE/deleteUserAdmin : userId - {} deleted", userId);
    }
}