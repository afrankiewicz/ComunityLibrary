package com.agular.hello.book;

import com.agular.hello.shared.Time;
import com.agular.hello.user.UserDto;
import com.agular.hello.user.UserModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

public class BookDto {

    private Long id;

    @NotBlank(message = "ISNB must be provided")
    private String isbn;

    @NotBlank(message = "Book title must be provided")
    private String title;

    @NotBlank(message = "Book author must be provided")
    private String author;

    @NotBlank(message = "Book language must be provided")
    private String language;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = Time.DATE_PATTERN)
    private LocalDate returnDate;

    private UserDto owner;

    @JsonIgnore
    private UserDto borrower;

    public BookDto() {
    }

    public BookDto(String isbn, String title, String author, String language) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.language = language;
    }

    public BookDto(Long id, String isbn, String title, String author, String language, LocalDate returnDate,
                   UserDto owner, UserDto borrower) {
        this(isbn, title, author, language);
        this.id = id;
        this.returnDate = returnDate;
        this.owner = owner;
        this.borrower = borrower;
    }

    public BookModel toModel() {
        UserModel borrower = this.borrower != null ? this.borrower.toModel() : null;
        return new BookModel(id, isbn, title, author, language, returnDate, owner.toModel(), borrower);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public UserDto getOwner() {
        return owner;
    }

    public void setOwner(UserDto owner) {
        this.owner = owner;
    }

    public UserDto getBorrower() {
        return borrower;
    }

    public void setBorrower(UserDto borrower) {
        this.borrower = borrower;
    }
}
