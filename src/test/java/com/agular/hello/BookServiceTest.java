package com.agular.hello;

import com.agular.hello.entity.BookModel;
import com.agular.hello.exceptions.BadRequestException;
import com.agular.hello.repositiry.BookRepository;
import com.agular.hello.service.BookService;
import net.bytebuddy.utility.RandomString;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BookServiceTest extends CommunityLibraryApplicationTests {

    @Autowired
    BookService bookService;
    @Autowired
    BookRepository bookRepository;

    @Test
    public void shouldRegisterBook(){
        // when
        BookModel book = bookService.registerBook(createBook(), createRegisteredUser().getEmail());
        // then
        Assert.assertNotNull(book.getOwner());
        Assert.assertNotNull(book.getId());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotRegisterBook(){
        // given
        BookModel book = createRegisteredBook();
        BookModel book2 = new BookModel(book.getIsbn(), RandomString.make(),
                RandomString.make(), RandomString.make());
        // when
        bookService.registerBook(book2, createRegisteredUser().getEmail());
    }

    @Test
    public void shouldBorrowBook(){
        // given
        BookModel registeredBook = createRegisteredBook();
        // when
        BookModel borrowedBook = bookService.borrowBook(registeredBook.getId(), createRegisteredUser().getEmail());
        // then
        Assert.assertNotNull(borrowedBook.getBorrower());
        Assert.assertNotNull(borrowedBook.getReturnDate());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotBorrowBookWhenBookAlreadyBorrowed() {
        // given
        BookModel registeredBook = createRegisteredBook();
        bookService.borrowBook(registeredBook.getId(), createRegisteredUser().getEmail());
        // when
        bookService.borrowBook(registeredBook.getId(), createRegisteredUser().getEmail());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotBorrowBookWhenBookBelongsToBorrower() {
        // given
        BookModel registeredBook = createRegisteredBook();
        // when
        bookService.borrowBook(registeredBook.getId(), registeredBook.getOwner().getEmail());
    }

    @Test
    public void shouldReturnBook(){
        // given
        BookModel registeredBook = createRegisteredBook();
        BookModel borrowedBook = bookService.borrowBook(registeredBook.getId(), createRegisteredUser().getEmail());
        // when
        bookService.returnBook(borrowedBook.getId(), borrowedBook.getBorrower().getEmail());
        // then
        Assert.assertNull(registeredBook.getBorrower());
        Assert.assertNull(registeredBook.getReturnDate());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotReturnBookWhenBookIsNotBorrowed() {
        // given
        BookModel registeredBook = createRegisteredBook();
        // when
        bookService.returnBook(registeredBook.getId(), createRegisteredUser().getEmail());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotReturnBookWhenBookNotBorrowedByReturner() {
        // given
        BookModel registeredBook = createRegisteredBook();
        bookService.borrowBook(registeredBook.getId(), createRegisteredUser().getEmail());
        // when
        bookService.returnBook(registeredBook.getId(), createRegisteredUser().getEmail());
    }

}
