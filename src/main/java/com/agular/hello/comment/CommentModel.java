package com.agular.hello.comment;

import com.agular.hello.user.UserModel;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "comments")
public class CommentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate reviewDate;

    @Column(columnDefinition = "TEXT")
    private String text;

    @ManyToOne
    private UserModel author;

    @ManyToOne
    private UserModel reviewee;

    public CommentModel() {
    }

    public CommentModel(Long id, LocalDate reviewDate, String text, UserModel author, UserModel reviewee) {
        this.id = id;
        this.reviewDate = reviewDate;
        this.text = text;
        this.author = author;
        this.reviewee = reviewee;
    }

    public CommentDto toDto() {
        return new CommentDto(id, reviewDate, text, author.toDto(), reviewee.toDto());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public UserModel getAuthor() {
        return author;
    }

    public void setAuthor(UserModel author) {
        this.author = author;
    }

    public UserModel getReviewee() {
        return reviewee;
    }

    public void setReviewee(UserModel reviewee) {
        this.reviewee = reviewee;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
