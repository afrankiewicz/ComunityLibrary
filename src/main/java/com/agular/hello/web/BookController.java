package com.agular.hello.web;

import com.agular.hello.DTO.BookDto;
import com.agular.hello.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

    BookService bookService;

    @PostMapping()
    public ResponseEntity<BookDto> registerBook(@Valid @RequestBody BookDto book, Principal principal){
        return new ResponseEntity<>(bookService.registerBook(book, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("{bookId}/borrow")
    public ResponseEntity<BookDto> borrowBookByUser(@Valid @PathVariable Long bookId, Principal principal){
        return new ResponseEntity<>(bookService.borrowBook(bookId, principal.getName()), HttpStatus.OK);
    }

    @PutMapping("{bookId}/return")
    public ResponseEntity<BookDto> returnBookByUser(@Valid @PathVariable Long bookId, Principal principal){
        return new ResponseEntity<>(bookService.returnBook(bookId, principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/owned")
    public ResponseEntity<List<BookDto>> getOwned(Principal principal) {
        return new ResponseEntity<>(bookService.getOwned(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/borrowed")
    public ResponseEntity<List<BookDto>> getBorrowed(Principal principal){
        return new ResponseEntity<>(bookService.getBorrowed(principal.getName()), HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<BookDto>> getAll(Principal principal){
        return new ResponseEntity<>(bookService.getAllAvailable(principal.getName()), HttpStatus.OK);
    }

}
