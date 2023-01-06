package com.agular.hello.service;

import com.agular.hello.entity.Book;
import com.agular.hello.entity.User;
import com.agular.hello.exceptions.BadRequestException;
import com.agular.hello.repositiry.BookRepository;
import com.agular.hello.repositiry.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService {

    BookRepository bookRepository;
    UserRepository userRepository;

    public BookService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Book registerBook(Book book, String email) {
        book.setOwner(getUserByEmail(email));
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new BadRequestException(String.format("Book with ISBN: '%s' already exists in library.", book.getIsbn()));
        }
        return bookRepository.save(book);
    }

    public Book borrowBook(Long bookId, String email) {
        Book book = getBookById(bookId);
        if (book.getOwner() == null){
            throw new IllegalStateException();
        } else if (book.getBorrower() != null) {
            throw new BadRequestException("Book is already borrowed.");
        } else if (book.getOwner().getEmail().equals(email)) {
            throw new BadRequestException("Book belongs to you.");
        }
        book.setBorrower(getUserByEmail(email));
        book.setReturnDate(LocalDate.now().plusMonths(1));
        return bookRepository.save(book);
    }

    public Book returnBook(Long bookId, String email) {
        Book book = getBookById(bookId);
        if (book.getBorrower() == null || !book.getBorrower().getEmail().equals(email)) {
            throw new BadRequestException("Book is not borrowed by you.");
        }
        book.setBorrower(null);
        book.setReturnDate(null);
        return bookRepository.save(book);
    }

    public List<Book> getOwned(String email) {
        return bookRepository.getBooksByOwner(getUserByEmail(email));
    }

    public List<Book> getBorrowed(String email) {
        return bookRepository.getBooksByBorrower(getUserByEmail(email));
    }

    public List<Book> getAllAvailable(String email) {
        return bookRepository.getAllAvailable(getUserByEmail(email).getId());
    }

    private User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new BadRequestException("User does not exist."));
    }

    private Book getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BadRequestException("Book does not exist."));
    }

}