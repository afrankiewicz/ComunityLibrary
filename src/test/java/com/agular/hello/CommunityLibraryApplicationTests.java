package com.agular.hello;

import com.agular.hello.DTO.BookDto;
import com.agular.hello.DTO.CommentDto;
import com.agular.hello.DTO.UserDto;
import com.agular.hello.repositiry.BookRepository;
import com.agular.hello.repositiry.CommentLikeRepository;
import com.agular.hello.repositiry.CommentRepository;
import com.agular.hello.repositiry.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CommunityLibraryApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    CommentLikeRepository commentLikeRepository;

    protected final String registeredUserEmail = "test@testmail.com";

    public void cleanupDb() {
        commentLikeRepository.deleteAll();
        commentRepository.deleteAll();
        bookRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Before
    public void setup() {
        cleanupDb();
    }

    public UserDto createUser(String email) {
        return new UserDto(RandomString.make(), RandomString.make(), email,
                RandomString.make(), RandomString.make(), RandomString.make());
    }

    public UserDto createRegisteredUser() {
        return userRepository.save(createUser(RandomString.make() + "@gmail.com").toModel()).toDto();
    }

    public UserDto createRegisteredUser(String email) {
        return userRepository.save(createUser(email).toModel()).toDto();
    }

    public BookDto createBook() {
        return new BookDto(RandomString.make(), RandomString.make(), RandomString.make(),
                RandomString.make());
    }

    public BookDto createRegisteredBook() {
        BookDto book = createBook();
        book.setOwner(createRegisteredUser());
        return bookRepository.save(book.toModel()).toDto();
    }

    public CommentDto createComment() {
        return new CommentDto(null, LocalDate.now(), RandomString.make(), createRegisteredUser(),
                createRegisteredUser());
    }

    public CommentDto createComment(String authorEmail) {
        return new CommentDto(null, LocalDate.now(), RandomString.make(), createRegisteredUser(authorEmail),
                createRegisteredUser());
    }

    public CommentDto createRegisteredComment() {
        return commentRepository.save(createComment().toModel()).toDto();
    }

    public CommentDto createRegisteredComment(String authorEmail) {
        return commentRepository.save(createComment(authorEmail).toModel()).toDto();
    }
}
