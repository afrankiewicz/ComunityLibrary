package com.agular.hello.web;

import com.agular.hello.DTO.AvailableBook;
import com.agular.hello.DTO.BookDto;
import com.agular.hello.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto registerBook(@Valid @RequestBody BookDto book, Principal principal) {
        return bookService.registerBook(book, principal.getName());
    }

    @GetMapping("/owned")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getOwned(Principal principal) {
        return bookService.getOwned(principal.getName());
    }

    @GetMapping("/borrowed")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getBorrowed(Principal principal) {
        return bookService.getBorrowed(principal.getName());
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<AvailableBook> getAll(Principal principal) {
        return bookService.getAllAvailable(principal.getName());
    }

    @PutMapping("{bookId}/borrow")
    @ResponseStatus(HttpStatus.OK)
    public BookDto borrowBookByUser(@Valid @PathVariable Long bookId, Principal principal) {
        return bookService.borrowBook(bookId, principal.getName());
    }

    @PutMapping("{bookId}/return")
    @ResponseStatus(HttpStatus.OK)
    public BookDto returnBookByUser(@Valid @PathVariable Long bookId, Principal principal) {
        return bookService.returnBook(bookId, principal.getName());
    }
}
