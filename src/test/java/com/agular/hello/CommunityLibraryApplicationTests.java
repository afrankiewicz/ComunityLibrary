package com.agular.hello;

import com.agular.hello.book.BookDto;
import com.agular.hello.comment.CommentDto;
import com.agular.hello.user.AddUserRequest;
import com.agular.hello.user.UserDto;
import com.agular.hello.book.BookRepository;
import com.agular.hello.commentLike.CommentLikeRepository;
import com.agular.hello.comment.CommentRepository;
import com.agular.hello.user.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@SpringBootTest
@RunWith(SpringRunner.class)
public abstract class CommunityLibraryApplicationTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private CommentLikeRepository commentLikeRepository;

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
                RandomString.make(), RandomString.make(), RandomString.make(),Math.random(),Math.random());
    }

    public UserDto createRegisteredUser() {
        return userRepository.save(createUser(RandomString.make() + "@gmail.com").toModel()).toDto();
    }

    public UserDto createRegisteredUser(String email) {
        return userRepository.save(createUser(email).toModel()).toDto();
    }

    public AddUserRequest createAddUserRequest(String email) {
        return new AddUserRequest(RandomString.make(), RandomString.make(), email,
                RandomString.make(), RandomString.make(), RandomString.make());
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
