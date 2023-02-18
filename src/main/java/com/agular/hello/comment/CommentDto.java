package com.agular.hello.comment;

import com.agular.hello.shared.Time;
import com.agular.hello.user.UserDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class CommentDto {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Time.DATE_PATTERN)
    private LocalDate reviewDate;

    @NotBlank(message = "Comment must be provided")
    private String text;

    private UserDto author;
    private UserDto reviewee;

    public CommentDto() {
    }

    public CommentDto(Long id, LocalDate reviewDate, String text, UserDto author, UserDto reviewee) {
        this.id = id;
        this.reviewDate = reviewDate;
        this.text = text;
        this.author = author;
        this.reviewee = reviewee;
    }

    public CommentModel toModel() {
        return new CommentModel(id, reviewDate, text, author.toModel(), reviewee.toModel());
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

    public UserDto getAuthor() {
        return author;
    }

    public void setAuthor(UserDto author) {
        this.author = author;
    }

    public UserDto getReviewee() {
        return reviewee;
    }

    public void setReviewee(UserDto reviewee) {
        this.reviewee = reviewee;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
