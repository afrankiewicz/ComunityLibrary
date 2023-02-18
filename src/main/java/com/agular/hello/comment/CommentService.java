package com.agular.hello.comment;

import com.agular.hello.user.UserDto;
import com.agular.hello.shared.exceptions.BadRequestException;
import com.agular.hello.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserService userService;

    public CommentService(CommentRepository commentRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.userService = userService;
    }

    public CommentDto addComment(String commentText, Long revieweeId, String authorEmail) {
        UserDto author = userService.getUserByEmail(authorEmail);
        UserDto reviewee = userService.getUser(revieweeId);

        if (authorEmail.equals(reviewee.getEmail())) {
            throw new BadRequestException("You can not review yourself.");
        } else if (commentRepository.findByAuthorIdAndRevieweeId(author.getId(), revieweeId) != null) {
            throw new BadRequestException("You already commented on this user.");
        }
        CommentModel comment = new CommentModel(null, LocalDate.now(), commentText, author.toModel(),
                reviewee.toModel());
        return commentRepository.save(comment).toDto();
    }

    public CommentDto getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new BadRequestException("Comment not found.")).toDto();
    }

    public CommentDto updateComment(String commentText, Long commentId, String authorEmail) {
        Long authorId = userService.getUserByEmail(authorEmail).getId();
        int updatedCommentNo = commentRepository.updateCommentText(commentText, commentId, authorId);
        if (updatedCommentNo == 0) {
            throw new BadRequestException("Comment was not updated.");
        }
        return getComment(commentId);
    }

    public void deleteComment(Long commentId, String authorEmail) {
        CommentDto comment = getComment(commentId);
        if (!comment.getAuthor().getEmail().equals(authorEmail)) {
            throw new BadRequestException("You are not the author of the comment");
        }
        commentRepository.deleteById(commentId);
    }
}
