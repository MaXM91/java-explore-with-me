package ru.practicum.mapper;

import dto.NewUserRequest;
import dto.UserDto;
import org.springframework.stereotype.Component;
import ru.practicum.entity.user.User;

@Component
public class UserMapper {
    public UserDto toUserDto(User user) {
        return UserDto.builder()
                      .id(user.getId())
                      .email(user.getEmail())
                      .name(user.getName())
                      .build();
    }

    public User toUser(NewUserRequest newUserRequest) {
        return User.builder()
                .email(newUserRequest.getEmail())
                .name(newUserRequest.getName())
                .build();
    }
}
