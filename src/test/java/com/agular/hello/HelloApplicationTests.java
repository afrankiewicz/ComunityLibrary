package com.agular.hello;

import com.agular.hello.entity.Book;
import com.agular.hello.entity.User;
import com.agular.hello.repositiry.BookRepository;
import com.agular.hello.repositiry.UserRepository;
import com.agular.hello.service.UserService;
import net.bytebuddy.utility.RandomString;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
class HelloApplicationTests {

	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;
	@Autowired
	BookRepository bookRepository;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	public User createUser(){
		return new User(RandomString.make(), RandomString.make(), RandomString.make() + "@gmail.com",
				RandomString.make(), RandomString.make(), RandomString.make());
	}
	public User createRegisteredUser(){
		return userRepository.save(createUser());
	}
	public Book createBook(){
		return new Book(RandomString.make(), RandomString.make(), RandomString.make(),
				RandomString.make());
	}

	public Book createRegisteredBook(){
		Book book = createBook();
		book.setOwner(createRegisteredUser());
		return bookRepository.save(book);
	}

//	@Test
//	void shouldAddUser() {
//		String rawPassword = "qwe123";
//		User user = userService.addUser(new User("Jan", "Kowalski","jkowalski@wp.pl", rawPassword, "Sportowa", "Warszawa"));
//		Optional<User> optionalUser = userService.getUser(user.getId());
//
//		Assert.assertTrue(optionalUser.isPresent());
//		User testUser = optionalUser.get();
//		Assert.assertEquals(user.getFirstName(),testUser.getFirstName());
//		Assert.assertEquals(user.getLastName(),testUser.getLastName());
//		Assert.assertEquals(user.getEmail(),testUser.getEmail());
//		Assert.assertTrue(bCryptPasswordEncoder.matches(rawPassword, testUser.getPassword()));
//	}

//	@Test
//	void shouldNotAddUserAlreadyExists(){
//		User user = userService.addUser(new User("Jan", "Kowalski","jkowalski@wp.pl", "qwe123", "Sportowa", "Warszawa"));
//		userService.addUser(user);
//
//
//	}

}
