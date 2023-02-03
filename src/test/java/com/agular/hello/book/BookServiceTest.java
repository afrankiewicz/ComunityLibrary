package com.agular.hello.book;

import com.agular.hello.CommunityLibraryApplicationTests;
import com.agular.hello.shared.exceptions.BadRequestException;
import net.bytebuddy.utility.RandomString;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BookServiceTest extends CommunityLibraryApplicationTests {

    @Autowired
    private BookService bookService;

    @Test
    public void shouldRegisterBook() {
        BookDto book = bookService.registerBook(createBook(), createRegisteredUser().getEmail());

        Assert.assertNotNull(book.getOwner());
        Assert.assertNotNull(book.getId());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotRegisterBook() {
        BookDto book = createRegisteredBook();
        BookDto book2 = new BookDto(book.getIsbn(), RandomString.make(),
                RandomString.make(), RandomString.make());

        bookService.registerBook(book2, createRegisteredUser().getEmail());
    }

    @Test
    public void shouldBorrowBook() {
        BookDto registeredBook = createRegisteredBook();

        BookDto borrowedBook = bookService.borrowBook(registeredBook.getId(), createRegisteredUser().getEmail());

        Assert.assertNotNull(borrowedBook.getBorrower());
        Assert.assertNotNull(borrowedBook.getReturnDate());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotBorrowBookWhenBookAlreadyBorrowed() {
        BookDto registeredBook = createRegisteredBook();
        bookService.borrowBook(registeredBook.getId(), createRegisteredUser().getEmail());

        bookService.borrowBook(registeredBook.getId(), createRegisteredUser().getEmail());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotBorrowBookWhenBookBelongsToBorrower() {
        BookDto registeredBook = createRegisteredBook();

        bookService.borrowBook(registeredBook.getId(), registeredBook.getOwner().getEmail());
    }

    @Test
    public void shouldReturnBook() {
        BookDto registeredBook = createRegisteredBook();
        BookDto borrowedBook = bookService.borrowBook(registeredBook.getId(), createRegisteredUser().getEmail());

        bookService.returnBook(borrowedBook.getId(), borrowedBook.getBorrower().getEmail());

        Assert.assertNull(registeredBook.getBorrower());
        Assert.assertNull(registeredBook.getReturnDate());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotReturnBookWhenBookIsNotBorrowed() {
        BookDto registeredBook = createRegisteredBook();

        bookService.returnBook(registeredBook.getId(), createRegisteredUser().getEmail());
    }

    @Test(expected = BadRequestException.class)
    public void shouldNotReturnBookWhenBookNotBorrowedByReturner() {
        BookDto registeredBook = createRegisteredBook();
        bookService.borrowBook(registeredBook.getId(), createRegisteredUser().getEmail());

        bookService.returnBook(registeredBook.getId(), createRegisteredUser().getEmail());
    }

}
