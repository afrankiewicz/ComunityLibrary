package com.agular.hello.entity;

import com.agular.hello.DTO.CommentLikeDto;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "comments_likes")
public class CommentLikeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dateTime;

    @ManyToOne
    private CommentModel comment;

    @ManyToOne
    private UserModel author;

    public CommentLikeModel() {
    }

    public CommentLikeModel(Long id, LocalDateTime dateTime, CommentModel comment, UserModel author) {
        this.id = id;
        this.dateTime = dateTime;
        this.comment = comment;
        this.author = author;
    }

    public CommentLikeDto toDto(){
        return new CommentLikeDto(id, dateTime, comment.toDto(), author.toDto());
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

    public CommentModel getComment() {
        return comment;
    }

    public void setComment(CommentModel comment) {
        this.comment = comment;
    }

    public UserModel getAuthor() {
        return author;
    }

    public void setAuthor(UserModel author) {
        this.author = author;
    }
}
