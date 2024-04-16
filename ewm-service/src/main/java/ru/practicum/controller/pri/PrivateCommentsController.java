package ru.practicum.controller.pri;

import dto.CommentDto;
import dto.NewCommentDto;
import dto.NewSonCommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.CommentService;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * PrivateCommentsController
 *  - addCommentPrivate - add comment
 *  - addSonCommentPrivate - get attached comment
 *  - getCommentByIdPrivate - get comment by id
 *  - getCommentsByEventIdPrivate - get comments by event id
 *  - updateCommentByIdPrivate - update comment
 */

@Slf4j
@Validated
@RestController
@RequestMapping("/users/{userId}/events/{eventId}/comments")
@RequiredArgsConstructor
public class PrivateCommentsController {
    private static final String Comment = "/{commentId}";
    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addCommentPrivate(@Positive(message = "userId must be positive.")
                                            @PathVariable("userId") Integer userId,
                                        @Positive(message = "eventId must be positive.")
                                            @PathVariable("eventId") Integer eventId,
                                        @RequestBody @Valid NewCommentDto newCommentDto) {
        log.info("request comments POST/addCommentPrivate : userId - {}, eventId - {}, newCommentDto - {}", userId,
                eventId, newCommentDto);

        CommentDto response = commentService.addCommentPrivate(userId, eventId, newCommentDto);
        log.info("response comments POST/addCommentPrivate : {}", response);

        return response;
    }

    @PostMapping(Comment)
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto addSonCommentPrivate(@Positive(message = "userId must be positive.")
                                               @PathVariable("userId") Integer userId,
                                           @Positive(message = "eventId must be positive.")
                                               @PathVariable("eventId") Integer eventId,
                                           @Positive(message = "commentId must be positive.")
                                               @PathVariable("commentId") Integer commentId,
                                           @RequestBody @Valid NewSonCommentDto newSonCommentDto) {
        log.info("request comments POST/addSonCommentPrivate : userId - {}, eventId - {}, commentId - {}," +
                        "newSonCommentDto - {}", userId, eventId, commentId, newSonCommentDto);

        CommentDto response = commentService.addSonCommentPrivate(userId, eventId, commentId, newSonCommentDto);
        log.info("response comments POST/addSonCommentPrivate : {}", response);

        return response;
    }

    @GetMapping(Comment)
    @ResponseStatus(HttpStatus.OK)
    public CommentDto getCommentByIdPrivate(@Positive(message = "userId must be positive.")
                                                @PathVariable("userId") Integer userId,
                                            @Positive(message = "eventId must be positive.")
                                                @PathVariable("eventId") Integer eventId,
                                            @Positive(message = "commentId must be positive.")
                                                @PathVariable("commentId") Integer commentId) {
        log.info("request comments GET/getCommentByIdPrivate : userId - {}, eventId - {}, commentId - {}", userId,
                eventId, commentId);

        CommentDto response = commentService.getCommentByIdPrivate(userId, eventId, commentId);
        log.info("response comments GET/getCommentByIdPrivate : {}", response);

        return response;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentDto> getCommentsByEventIdPrivate(@Positive(message = "userId must be positive.")
                                                            @PathVariable("userId") Integer userId,
                                                        @Positive(message = "eventId must be positive.")
                                                            @PathVariable("eventId") Integer eventId) {
        log.info("request comments GET/getCommentByIdPrivate : userId - {}, eventId - {}", userId, eventId);

        List<CommentDto> response = commentService.getCommentsByEventIdPrivate(userId, eventId);
        log.info("response comments GET/getCommentByIdPrivate : {}", response);

        return response;
    }

    @PatchMapping(Comment)
    @ResponseStatus(HttpStatus.OK)
    public CommentDto updateCommentByIdPrivate(@Positive(message = "userId must be positive.")
                                                   @PathVariable("userId") Integer userId,
                                               @Positive(message = "eventId must be positive.")
                                                   @PathVariable("eventId") Integer eventId,
                                               @Positive(message = "commentId must be positive.")
                                                   @PathVariable("commentId") Integer commentId,
                                               @RequestBody @Valid NewCommentDto newCommentDto) {
        log.info("request comments PATCH/updateCommentByIdPrivate : userId - {}, eventId - {}, commentId - {}", userId,
                eventId, commentId);

        CommentDto response = commentService.updateCommentByIdPrivate(userId, eventId, commentId, newCommentDto);
        log.info("response comments PATCH/updateCommentByIdPrivate : {}", response);

        return response;
    }
}