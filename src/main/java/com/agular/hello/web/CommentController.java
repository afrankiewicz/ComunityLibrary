package com.agular.hello.web;

import com.agular.hello.DTO.CommentDto;
import com.agular.hello.service.CommentService;
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
    public CommentDto createComment(@Valid @RequestBody CommentDto commentDto,
                                    @Valid @PathVariable Long revieweeId,
                                    Principal principal) {
        return commentService.addComment(commentDto, revieweeId, principal.getName());

    }

    @GetMapping("{commentId}")
    @ResponseStatus(value = HttpStatus.OK)
    public CommentDto getComment(@Valid @PathVariable Long commentId, Principal principal) {
        return commentService.getComment(commentId);
    }

    @PutMapping("{commentId}")
    @ResponseStatus(value = HttpStatus.OK)
    public CommentDto updateComment(@Valid @RequestBody CommentDto commentDto,
                                    @Valid @PathVariable Long commentId,
                                    Principal principal) {
        return commentService.updateComment(commentDto, commentId, principal.getName());
    }

    @DeleteMapping("{commentId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteComment(@Valid @PathVariable Long commentId,
                              Principal principal) {
        commentService.deleteComment(commentId, principal.getName());
    }
}
