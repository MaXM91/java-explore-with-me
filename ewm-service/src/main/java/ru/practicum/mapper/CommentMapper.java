package ru.practicum.mapper;

import dto.CommentDto;
import dto.NewCommentDto;
import dto.NewSonCommentDto;
import dto.UserDto;
import org.springframework.stereotype.Component;
import ru.practicum.entity.comment.Comment;
import ru.practicum.entity.event.Event;
import ru.practicum.entity.user.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
public class CommentMapper {
    public CommentDto toCommentDto(Comment comment) {
        if (comment.getCommentsList() != null && !comment.getCommentsList().isEmpty() &&
                comment.getResponseToUser() != null) {
            return CommentDto.builder()
                    .id(comment.getId())
                    .text(comment.getText())
                    .registrationTime(comment.getRegistrationTime())
                    .owner(new UserDto(comment.getOwner().getId(), comment.getOwner().getEmail(),
                            comment.getOwner().getName()))
                    .responseToUser(new UserDto(comment.getResponseToUser().getId(),
                            comment.getResponseToUser().getEmail(), comment.getResponseToUser().getName()))
                    .commentsList(comment.getCommentsList().stream()
                            .map(this::toCommentDto)
                            .collect(Collectors.toList()))
                    .build();
        } else if (comment.getCommentsList() != null && !comment.getCommentsList().isEmpty()) {
            return CommentDto.builder()
                    .id(comment.getId())
                    .text(comment.getText())
                    .registrationTime(comment.getRegistrationTime())
                    .owner(new UserDto(comment.getOwner().getId(), comment.getOwner().getEmail(),
                            comment.getOwner().getName()))
                    .commentsList(comment.getCommentsList().stream()
                            .map(this::toCommentDto)
                            .collect(Collectors.toList()))
                    .build();
        } else if (comment.getResponseToUser() != null) {
            return CommentDto.builder()
                    .id(comment.getId())
                    .text(comment.getText())
                    .registrationTime(comment.getRegistrationTime())
                    .owner(new UserDto(comment.getOwner().getId(), comment.getOwner().getEmail(),
                            comment.getOwner().getName()))
                    .responseToUser(new UserDto(comment.getResponseToUser().getId(),
                            comment.getResponseToUser().getEmail(), comment.getResponseToUser().getName()))
                    .commentsList(new ArrayList<>())
                    .build();
        } else {
            return CommentDto.builder()
                    .id(comment.getId())
                    .text(comment.getText())
                    .registrationTime(comment.getRegistrationTime())
                    .owner(new UserDto(comment.getOwner().getId(), comment.getOwner().getEmail(),
                            comment.getOwner().getName()))
                    .commentsList(new ArrayList<>())
                    .build();
        }
    }

    public Comment toComment(User owner, Event event, NewCommentDto newCommentDto) {
            return Comment.builder()
                    .text(newCommentDto.getText())
                    .registrationTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                    .owner(owner)
                    .event(event)
                    .build();
    }

    public Comment toComment(User owner, User responseToUser, NewSonCommentDto newSonCommentDto) {
        return Comment.builder()
                .text(newSonCommentDto.getText())
                .registrationTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .owner(owner)
                .responseToUser(responseToUser)
                .build();
    }
}