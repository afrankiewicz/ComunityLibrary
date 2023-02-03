package com.agular.hello.book;

import com.agular.hello.user.UserDto;
import com.agular.hello.user.UserModel;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "books")
public class BookModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String language;

    private LocalDate returnDate;

    @ManyToOne
    private UserModel owner;

    @ManyToOne
    private UserModel borrower;

    public BookModel() {
    }

    public BookModel(String isbn, String title, String author, String language) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.language = language;
    }

    public BookModel(Long id, String isbn, String title, String author, String language, LocalDate returnDate,
                     UserModel owner, UserModel borrower) {
        this(isbn, title, author, language);
        this.id = id;
        this.returnDate = returnDate;
        this.owner = owner;
        this.borrower = borrower;
    }

    public BookDto toDto() {
        UserDto borrower = this.borrower != null ? this.borrower.toDto() : null;
        return new BookDto(id, isbn, title, author, language, returnDate, owner.toDto(), borrower);
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

    public UserModel getOwner() {
        return owner;
    }

    public void setOwner(UserModel owner) {
        this.owner = owner;
    }

    public UserModel getBorrower() {
        return borrower;
    }

    public void setBorrower(UserModel borrower) {
        this.borrower = borrower;
    }
}
