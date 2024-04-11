package ru.practicum.mapper;

import dto.CommentDto;
import dto.NewCommentDto;
import dto.UserDto;
import org.springframework.stereotype.Component;
import ru.practicum.entity.comment.Comment;
import ru.practicum.entity.event.Event;
import ru.practicum.entity.user.User;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class CommentMapper {
    public CommentDto toCommentDto(Comment comment) {
        if (comment.getResponseToUser() != null) {
            return CommentDto.builder()
                             .id(comment.getId())
                             .text(comment.getText())
                             .registrationTime(comment.getRegistrationTime())
                             .owner(new UserDto(comment.getOwner().getId(), comment.getOwner().getEmail(),
                                     comment.getOwner().getName()))
                             .responseToUser(new UserDto(comment.getResponseToUser().getId(),
                                     comment.getResponseToUser().getEmail(), comment.getResponseToUser().getName()))
                             .build();
        } else {
            return CommentDto.builder()
                    .id(comment.getId())
                    .text(comment.getText())
                    .registrationTime(comment.getRegistrationTime())
                    .owner(new UserDto(comment.getOwner().getId(), comment.getOwner().getEmail(),
                            comment.getOwner().getName()))
                    .build();
        }
    }

    public Comment toComment(User owner, User responseToUser, Event event, NewCommentDto newCommentDto) {
        if (responseToUser != null) {
            return Comment.builder()
                    .text(newCommentDto.getText())
                    .registrationTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                    .owner(owner)
                    .responseToUser(responseToUser)
                    .event(event)
                    .build();
        } else {
            return Comment.builder()
                    .text(newCommentDto.getText())
                    .registrationTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                    .owner(owner)
                    .event(event)
                    .build();
        }
    }
}