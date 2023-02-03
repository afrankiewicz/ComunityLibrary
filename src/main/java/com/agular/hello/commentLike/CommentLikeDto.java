package com.agular.hello.commentLike;

import com.agular.hello.comment.CommentDto;
import com.agular.hello.user.UserDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CommentLikeDto {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    private LocalDateTime dateTime;

    private CommentDto comment;
    private UserDto author;

    public CommentLikeDto() {
    }

    public CommentLikeDto(Long id, LocalDateTime dateTime, CommentDto comment, UserDto author) {
        this.id = id;
        this.dateTime = dateTime;
        this.comment = comment;
        this.author = author;
    }

    public CommentLikeModel toModel() {
        return new CommentLikeModel(id, dateTime, comment.toModel(), author.toModel());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public CommentDto getComment() {
        return comment;
    }

    public void setComment(CommentDto comment) {
        this.comment = comment;
    }

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }
}
