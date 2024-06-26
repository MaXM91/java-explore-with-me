package ru.practicum.service;

import dto.NewUserRequest;
import dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.mapper.UserMapper;
import ru.practicum.repository.UserRepository;
import ru.practicum.validate.exception.ObjectNotFoundException;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserService
 */

@Transactional
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     *
     * @param newUserRequest - new user data
     * @return - new user
     */
    public UserDto addUserAdmin(NewUserRequest newUserRequest) {
        return userMapper.toUserDto(userRepository.save(userMapper.toUser(newUserRequest)));
    }

    /**
     *
     * @param ids - list users ids
     * @param from - start page
     * @param size - size this page
     * @return - List<UserDto> depending on the availability of the selection parameters
     */
    public List<UserDto> getUsersAdmin(Integer[] ids, Integer from, Integer size) {
        if ((ids == null) || (ids[0] == null)) {
            return userRepository.findAll(PageRequest.of(from, size)).stream()
                    .map(userMapper::toUserDto)
                    .collect(Collectors.toList());
        } else {
            return userRepository.findAllByIdIn(ids, PageRequest.of(from, size)).stream()
                    .map(userMapper::toUserDto)
                    .collect(Collectors.toList());
        }
    }

    /**
     *
     * @param userId - id user for delete
     */
    public void deleteUserAdmin(Integer userId) {
        userRepository.findById(userId).orElseThrow(() ->
                new ObjectNotFoundException("User with id=" + userId + " was not found"));

        userRepository.deleteById(userId);
    }
}