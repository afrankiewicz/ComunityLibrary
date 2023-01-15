package com.agular.hello.entity;

import com.agular.hello.DTO.CommentDto;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "comments")
public class CommentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate reviewDate;

    @ManyToOne
    private UserModel author;

    @ManyToOne
    private UserModel reviewee;

    @Column(columnDefinition = "TEXT")
    private String text;

    public CommentModel() {
    }

    public CommentModel(Long id, LocalDate reviewDate, UserModel author, UserModel reviewee, String text) {
        this.id = id;
        this.reviewDate = reviewDate;
        this.author = author;
        this.reviewee = reviewee;
        this.text = text;
    }

    public CommentDto toDto() {
        return new CommentDto(id, reviewDate, author.toDto(), reviewee.toDto(), text);
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
