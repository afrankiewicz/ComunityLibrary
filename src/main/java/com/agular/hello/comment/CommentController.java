package com.agular.hello.comment;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/reviewee/{revieweeId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CommentDto createComment(@Valid @RequestBody AddCommentRequest commentRequest,
                                    @PathVariable Long revieweeId,
                                    Principal principal) {
        return commentService.addComment(commentRequest.getText(), revieweeId, principal.getName());

    }

    @GetMapping("{commentId}")
    @ResponseStatus(value = HttpStatus.OK)
    public CommentDto getComment(@PathVariable Long commentId) {
        return commentService.getComment(commentId);
    }

    @PutMapping("{commentId}")
    @ResponseStatus(value = HttpStatus.OK)
    public CommentDto updateComment(@Valid @RequestBody AddCommentRequest commentRequest,
                                    @PathVariable Long commentId,
                                    Principal principal) {
        return commentService.updateComment(commentRequest.getText(), commentId, principal.getName());
    }

    @DeleteMapping("{commentId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteComment(@PathVariable Long commentId, Principal principal) {
        commentService.deleteComment(commentId, principal.getName());
    }
}
