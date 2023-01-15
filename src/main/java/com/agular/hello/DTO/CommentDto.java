package com.agular.hello.DTO;

import com.agular.hello.entity.CommentModel;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class CommentDto {

    private Long id;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate reviewDate;

    private UserDto author;

    private UserDto reviewee;

    @NotBlank(message = "Comment must be provided")
    private String text;

    public CommentDto() {
    }

    public CommentDto(Long id, LocalDate reviewDate, UserDto author, UserDto reviewee, String text) {
        this.id = id;
        this.reviewDate = reviewDate;
        this.author = author;
        this.reviewee = reviewee;
        this.text = text;
    }

    public CommentModel toModel(){
        return new CommentModel(id, reviewDate, author.toModel(), reviewee.toModel(), text);
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
