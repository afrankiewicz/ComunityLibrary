package com.agular.hello.service;

import com.agular.hello.DTO.BookDto;
import com.agular.hello.entity.BookModel;
import com.agular.hello.exceptions.BadRequestException;
import com.agular.hello.repositiry.BookRepository;
import com.agular.hello.repositiry.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookService {

    BookRepository bookRepository;

    UserRepository userRepository;

    UserService userService;

    public BookService(BookRepository bookRepository, UserRepository userRepository, UserService userService) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public BookDto registerBook(BookDto book, String email) {
        book.setOwner(userService.getUserByEmail(email));
        if (bookRepository.existsByIsbn(book.getIsbn())) {
            throw new BadRequestException(String.format("Book with ISBN: '%s' already exists in library.", book.getIsbn()));
        }
        return bookRepository.save(book.toModel()).toDto();
    }

    public List<BookDto> getOwned(String email) {
        return bookRepository.getBooksByOwnerEmail(email)
                .stream()
                .map(BookModel::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDto> getBorrowed(String email) {
        return bookRepository.getBooksByBorrowerEmail(email)
                .stream()
                .map(BookModel::toDto)
                .collect(Collectors.toList());
    }

    public List<BookDto> getAllAvailable(String email) {
        return bookRepository.getAllAvailable(userService.getUserByEmail(email).getId())
                .stream()
                .map(BookModel::toDto)
                .collect(Collectors.toList());
    }

    private BookDto getBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() -> new BadRequestException("Book does not exist.")).toDto();
    }

    public BookDto borrowBook(Long bookId, String email) {
        BookDto book = getBookById(bookId);
        if (book.getOwner() == null) {
            throw new IllegalStateException();
        } else if (book.getBorrower() != null) {
            throw new BadRequestException("Book is already borrowed.");
        } else if (book.getOwner().getEmail().equals(email)) {
            throw new BadRequestException("Book belongs to you.");
        }
        book.setBorrower(userService.getUserByEmail(email));
        book.setReturnDate(LocalDate.now().plusMonths(1));
        return bookRepository.save(book.toModel()).toDto();
    }

    public BookDto returnBook(Long bookId, String email) {
        BookDto book = getBookById(bookId);
        if (book.getBorrower() == null || !book.getBorrower().getEmail().equals(email)) {
            throw new BadRequestException("Book is not borrowed by you.");
        }
        book.setBorrower(null);
        book.setReturnDate(null);
        return bookRepository.save(book.toModel()).toDto();
    }
}