package com.agular.hello;

import com.agular.hello.DTO.BookDto;
import com.agular.hello.DTO.UserDto;
import com.agular.hello.repositiry.BookRepository;
import com.agular.hello.repositiry.UserRepository;
import net.bytebuddy.utility.RandomString;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class CommunityLibraryApplicationTests {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BookRepository bookRepository;

    protected final String registeredUserEmail = "test@testmail.com";

    public void cleanupDb() {
        bookRepository.deleteAll();
        userRepository.deleteAll();
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
}
