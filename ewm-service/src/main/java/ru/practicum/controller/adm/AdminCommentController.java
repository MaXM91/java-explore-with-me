package ru.practicum.controller.adm;

import dto.CommentDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.service.CommentService;

import javax.validation.constraints.Positive;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/admin/comments")
@RequiredArgsConstructor
public class AdminCommentController {
    private final CommentService commentService;

    @PatchMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public CommentDto blockCommentAdmin(@Positive(message = "commentId must be positive.")
                                            @PathVariable("commentId") Integer commentId) {
        log.info("request comments PATCH/blockCommentAdmin : commentId - {}", commentId);

        CommentDto response = commentService.blockCommentAdmin(commentId);
        log.info("response comments PATCH/blockCommentAdmin : {}", response);

        return response;
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteCommentAdmin(@Positive(message = "commentId must be positive.")
                                             @PathVariable("commentId") Integer commentId) {
        log.info("request comments DELETE/deleteCommentAdmin : commentId - {}", commentId);

        commentService.deleteCommentAdmin(commentId);
        log.info("response comments DELETE/deleteCommentAdmin : commentId - " + commentId + " deleted.");
    }
}
