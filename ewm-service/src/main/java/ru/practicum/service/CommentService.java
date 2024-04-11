package ru.practicum.service;

import dto.CommentDto;
import dto.NewCommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.entity.comment.Comment;
import ru.practicum.entity.event.Event;
import ru.practicum.entity.user.User;
import ru.practicum.mapper.CommentMapper;
import ru.practicum.repository.CommentRepository;
import ru.practicum.repository.EventRepository;
import ru.practicum.repository.UserRepository;
import ru.practicum.validate.exception.ObjectNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    public CommentDto addCommentPrivate(int userId, int eventId, NewCommentDto newCommentDto) {
        User foundedUser = checkUser(userId);
        Event foundedEvent = checkEvent(eventId);

        if (newCommentDto.getResponseToUser() != null) {
            return commentMapper.toCommentDto(commentRepository.save(commentMapper.toComment(foundedUser,
                    checkUser(newCommentDto.getResponseToUser()),foundedEvent, newCommentDto)));
        } else {
            return commentMapper.toCommentDto(commentRepository.save(commentMapper.toComment(foundedUser,
                    null,foundedEvent, newCommentDto)));
        }
    }

    public CommentDto getCommentByIdPrivate(int userId, int eventId, int commentId) {
        checkUser(userId);
        checkEvent(eventId);

        return commentMapper.toCommentDto(checkComment(commentId));
    }

    public List<CommentDto> getCommentsByEventIdPrivate(int userId, int eventId) {
        checkUser(userId);
        checkEvent(eventId);

        return commentRepository.findAllByEventId(eventId).stream()
                .map(commentMapper::toCommentDto)
                .collect(Collectors.toList());
    }

    public CommentDto updateCommentByIdPrivate(int userId, int eventId, int commentId, NewCommentDto newCommentDto) {
        checkUser(userId);
        checkEvent(eventId);
        Comment foundedComment = checkComment(commentId);

        if (!newCommentDto.getText().equals(foundedComment.getText())) {
            foundedComment.setText(newCommentDto.getText());
        }

        return commentMapper.toCommentDto(commentRepository.save(foundedComment));
    }

    public CommentDto blockCommentAdmin(int commentId) {
        Comment foundedComment = checkComment(commentId);

        foundedComment.setText("comment blocked by admin");

        return commentMapper.toCommentDto(commentRepository.save(foundedComment));
    }

    public void deleteCommentAdmin(int commentId) {
        commentRepository.delete(checkComment(commentId));
    }

    private User checkUser(int userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new ObjectNotFoundException("user id - " + userId + " not found"));
    }

    private Event checkEvent(int eventId) {
        return eventRepository.findById(eventId).orElseThrow(() ->
                new ObjectNotFoundException("event id - " + eventId + " not found"));
    }

    private Comment checkComment(int commentId) {
        return commentRepository.findById(commentId).orElseThrow(() ->
                new ObjectNotFoundException("comment id - " + commentId + " not found"));
    }
}