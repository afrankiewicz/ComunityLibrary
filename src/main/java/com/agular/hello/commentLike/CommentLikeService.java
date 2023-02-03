package com.agular.hello.commentLike;

import com.agular.hello.comment.CommentDto;
import com.agular.hello.user.UserDto;
import com.agular.hello.shared.exceptions.BadRequestException;
import com.agular.hello.comment.CommentService;
import com.agular.hello.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;
    private final UserService userService;
    private final CommentService commentService;

    public CommentLikeService(CommentLikeRepository commentLikeRepository, UserService userService, CommentService commentService) {
        this.commentLikeRepository = commentLikeRepository;
        this.userService = userService;
        this.commentService = commentService;
    }

    public CommentLikeDto likeComment(Long commentId, String authorEmail) {
        UserDto author = userService.getUserByEmail(authorEmail);
        CommentDto comment = commentService.getComment(commentId);
        CommentLikeDto commentLike = new CommentLikeDto(null, LocalDateTime.now(), comment, author);

        if (comment.getAuthor().getEmail().equals(authorEmail)) {
            throw new BadRequestException("You can not like your own comment");
        }
        return commentLikeRepository.save(commentLike.toModel()).toDto();
    }

    public CommentLikeDto getCommentLike(Long commentLikeId) {
        return commentLikeRepository.findById(commentLikeId)
                .orElseThrow(() -> new BadRequestException("Comment Like not found.")).toDto();
    }

    public List<CommentLikeDto> getCommentLikes(Long commentId) {
        return commentLikeRepository.getCommentLikesByCommentId(commentId)
                .stream()
                .map(CommentLikeModel::toDto)
                .collect(Collectors.toList());
    }

    public void deleteCommentLike(Long commentLikeId, String authorEmail) {
        CommentLikeDto commentLike = getCommentLike(commentLikeId);
        if (!commentLike.getAuthor().getEmail().equals(authorEmail)) {
            throw new BadRequestException("You are not the author of the comment like");
        }
        commentLikeRepository.deleteById(commentLikeId);
    }

}
