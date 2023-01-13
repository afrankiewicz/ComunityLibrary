package com.agular.hello.entity;

import com.agular.hello.DTO.BookDto;
import com.agular.hello.DTO.UserDto;

import javax.persistence.*;
import java.time.LocalDate;

@Table(name = "books")
@Entity
public class BookModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "isbn", nullable = false, unique = true)
    private String isbn;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "language", nullable = false)
    private String language;

    @Column(name = "return_date")
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private UserModel owner;

    @ManyToOne
    @JoinColumn(name = "borrower_id", referencedColumnName = "id")
    private UserModel borrower;

    public BookModel() {
    }

    public BookModel(String isbn, String title, String author, String language) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.language = language;
    }

    public BookModel(Long id, String isbn, String title, String author, String language, LocalDate returnDate, UserModel owner, UserModel borrower) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.language = language;
        this.returnDate = returnDate;
        this.owner = owner;
        this.borrower = borrower;
    }

    public BookDto toDto() {
        UserDto borrower = this.borrower != null ? this.borrower.toDto(): null;
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
