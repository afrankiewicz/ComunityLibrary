package com.agular.hello.web;

import com.agular.hello.entity.Book;
import com.agular.hello.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

    BookService bookService;

    @PostMapping()
    public ResponseEntity<Book> registerBook(@Valid @RequestBody Book book, @AuthenticationPrincipal String email){
        return new ResponseEntity<>(bookService.registerBook(book, email), HttpStatus.CREATED);
    }

    @PutMapping("{bookId}/borrow")
    public ResponseEntity<Book> borrowBookByUser(@Valid @PathVariable Long bookId, @AuthenticationPrincipal String email){
        return new ResponseEntity<>(bookService.borrowBook(bookId, email), HttpStatus.OK);
    }

    @PutMapping("{bookId}/return")
    public ResponseEntity<Book> returnBookByUser(@Valid @PathVariable Long bookId, @AuthenticationPrincipal String email){
        return new ResponseEntity<>(bookService.returnBook(bookId, email), HttpStatus.OK);
    }

    @GetMapping("/owned")
    public ResponseEntity<List<Book>> getOwned(@AuthenticationPrincipal String email){
        return new ResponseEntity<>(bookService.getOwned(email), HttpStatus.OK);
    }

    @GetMapping("/borrowed")
    public ResponseEntity<List<Book>> getBorrowed(@AuthenticationPrincipal String email){
        return new ResponseEntity<>(bookService.getBorrowed(email), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Book>> getAll(@AuthenticationPrincipal String email){
        return new ResponseEntity<>(bookService.getAllAvailable(email), HttpStatus.OK);
    }

}
