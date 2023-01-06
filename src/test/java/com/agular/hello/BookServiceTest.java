package com.agular.hello;

import com.agular.hello.entity.Book;
import com.agular.hello.exceptions.BadRequestException;
import com.agular.hello.repositiry.BookRepository;
import com.agular.hello.service.BookService;
import net.bytebuddy.utility.RandomString;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BookServiceTest extends HelloApplicationTests {

    @Autowired
    BookService bookService;
    @Autowired
    BookRepository bookRepository;

    @Test
    public void shouldRegisterBook(){
        // when
        Book book = bookService.registerBook(createBook(), createRegisteredUser().getEmail());
        // then
        Assert.assertNotNull(book.getOwner());
        Assert.assertNotNull(book.getId());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotRegisterBook(){
        // given
        Book book = createRegisteredBook();
        Book book2 = new Book(book.getIsbn(), RandomString.make(),
                RandomString.make(), RandomString.make());
        // when
        bookService.registerBook(book2, createRegisteredUser().getEmail());
    }

    @Test
    public void shouldBorrowBook(){
        // given
        Book registeredBook = createRegisteredBook();
        // when
        Book borrowedBook = bookService.borrowBook(registeredBook.getId(), createRegisteredUser().getEmail());
        // then
        Assert.assertNotNull(borrowedBook.getBorrower());
        Assert.assertNotNull(borrowedBook.getReturnDate());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotBorrowBookWhenBookAlreadyBorrowed() {
        // given
        Book registeredBook = createRegisteredBook();
        bookService.borrowBook(registeredBook.getId(), createRegisteredUser().getEmail());
        // when
        bookService.borrowBook(registeredBook.getId(), createRegisteredUser().getEmail());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotBorrowBookWhenBookBelongsToBorrower() {
        // given
        Book registeredBook = createRegisteredBook();
        // when
        bookService.borrowBook(registeredBook.getId(), registeredBook.getOwner().getEmail());
    }

    @Test
    public void shouldReturnBook(){
        // given
        Book registeredBook = createRegisteredBook();
        Book borrowedBook = bookService.borrowBook(registeredBook.getId(), createRegisteredUser().getEmail());
        // when
        bookService.returnBook(borrowedBook.getId(), borrowedBook.getBorrower().getEmail());
        // then
        Assert.assertNull(registeredBook.getBorrower());
        Assert.assertNull(registeredBook.getReturnDate());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotReturnBookWhenBookIsNotBorrowed() {
        // given
        Book registeredBook = createRegisteredBook();
        // when
        bookService.returnBook(registeredBook.getId(), createRegisteredUser().getEmail());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotReturnBookWhenBookNotBorrowedByReturner() {
        // given
        Book registeredBook = createRegisteredBook();
        bookService.borrowBook(registeredBook.getId(), createRegisteredUser().getEmail());
        // when
        bookService.returnBook(registeredBook.getId(), createRegisteredUser().getEmail());
    }

}
