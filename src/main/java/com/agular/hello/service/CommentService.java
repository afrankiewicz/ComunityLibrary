package com.agular.hello.service;

import com.agular.hello.DTO.CommentDto;
import com.agular.hello.DTO.UserDto;
import com.agular.hello.entity.CommentModel;
import com.agular.hello.exceptions.BadRequestException;
import com.agular.hello.repositiry.CommentRepository;
import com.agular.hello.repositiry.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class CommentService {

    CommentRepository commentRepository;
    UserRepository userRepository;
    UserService userService;

    public CommentService(CommentRepository commentRepository, UserRepository userRepository, UserService userService) {
        this.commentRepository = commentRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public CommentDto addComment(CommentDto commentDto, Long revieweeId, String authorEmail) {
        UserDto author = userService.getUserByEmail(authorEmail);
        UserDto reviewee = userService.getUser(revieweeId);
        CommentModel comment = new CommentModel(null, LocalDate.now(), author.toModel(), reviewee.toModel(), commentDto.getText());
        return commentRepository.save(comment).toDto();
    }

    public CommentDto getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new BadRequestException("Comment not found")).toDto();
    }

    public CommentDto updateComment(CommentDto commentDto, Long commentId, String authorEmail) {
        Long authorId = userService.getUserByEmail(authorEmail).getId();
        int updatedCommentNo = commentRepository.updateCommentText(commentDto.getText(), commentId, authorId);
        if (updatedCommentNo != 0){
            return getComment(commentId);
        } else {
            throw new BadRequestException("Comment was not updated");
        }
    }

    public void deleteComment(Long commentId, String authorEmail) {
        CommentDto comment = getComment(commentId);
        if (!comment.getAuthor().getEmail().equals(authorEmail)) {
            throw new BadRequestException("You are not the author of the comment");
        }
        commentRepository.deleteById(commentId);
    }

}
