package com.agular.hello.web;

import com.agular.hello.DTO.CommentLikeDto;
import com.agular.hello.service.CommentLikeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/likes")
public class CommentLikeController {

    private CommentLikeService commentLikeService;

    public CommentLikeController(CommentLikeService commentLikeService) {
        this.commentLikeService = commentLikeService;
    }

    @PostMapping("/comments/{commentId}")
    @ResponseStatus(value = HttpStatus.CREATED)
    public CommentLikeDto likeComment(@PathVariable Long commentId, Principal principal) {
        return commentLikeService.likeComment(commentId, principal.getName());
    }

    @GetMapping("{commentLikeId}")
    @ResponseStatus(value = HttpStatus.OK)
    public CommentLikeDto getCommentLike(@PathVariable Long commentLikeId, Principal principal) {
        return commentLikeService.getCommentLike(commentLikeId);
    }

    @GetMapping("/comments/{commentId}")
    @ResponseStatus(value = HttpStatus.OK)
    public List<CommentLikeDto> getCommentLikes(@PathVariable Long commentId, Principal principal) {
        return commentLikeService.getCommentLikes(commentId);
    }

    @DeleteMapping("{commentLikeId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteCommentLike(@PathVariable Long commentLikeId, Principal principal) {
        commentLikeService.deleteCommentLike(commentLikeId, principal.getName());
    }
}
