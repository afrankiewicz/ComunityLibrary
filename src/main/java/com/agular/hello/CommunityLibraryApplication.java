package com.agular.hello;

import com.agular.hello.entity.BookModel;
import com.agular.hello.entity.UserModel;
import com.agular.hello.repositiry.BookRepository;
import com.agular.hello.repositiry.UserRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class CommunityLibraryApplication {

    UserRepository userRepository;

    BookRepository bookRepository;

    public CommunityLibraryApplication(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(CommunityLibraryApplication.class, args);
    }

    @PostConstruct
    void insert() {
        List<UserModel> users = Arrays.asList(
                new UserModel("Agata", "Kowalska", "agata.kowalska@gmail.com",
                        new BCryptPasswordEncoder().encode("123"), "Szewska", "Warszawa",
                        52.237049, 21.017532),
                new UserModel("Mateusz", "Nowak", "mat.nowak@gmail.com",
                        new BCryptPasswordEncoder().encode("456"), "Marszałkowska", "Warszawa",
                        52.237049, 21.017532),
                new UserModel("Jan", "Burak", "jan.burak@gmail.com",
                        new BCryptPasswordEncoder().encode("456"), "Zachodnia", "Wroclaw",
                        51.107883, 17.038538)
        );
        userRepository.saveAll(users);

        List<BookModel> books = Arrays.asList(
                new BookModel("978-83-89405-00-5", "The Land of Laughs", "Jonathan Carroll", "EN"),
                new BookModel("978-83-7432-357-4", "Norwegian Wood", "Haruki Murakami", "EN"),
                new BookModel("978-83-6532-456-1", "Harry Potter and the Deathly Hallows", "J.K. Rowling", "EN"),
                new BookModel("978-83-7432-222-3", "In Search of Lost Time", "Marcel Proust", "EN"),
                new BookModel("978-83-7432-2346-4", "Moby Dick", "Herman Melville", "EN"),
                new BookModel("978-83-3452-12-1", "War and Peace", "Leo Tolstoy", "EN"),
                new BookModel("978-83-3452-2387-1", "The Odyssey", "Homer", "EN"),
                new BookModel("978-83-3452-568-3", "Madame Bovary", "Gustave Flaubert", "EN"),
                new BookModel("978-83-67-5765-3", "The Adventures of Huckleberry Finn", "Mark Twain", "EN"),
                new BookModel("978-83-76432-4774-4", "Alice's Adventures in Wonderland", "Lewis Carroll", "EN"),
                new BookModel("978-83-76432-532-3", " The Iliad", "Homer", "EN"),
                new BookModel("978-83-76432-45-3", "To the Lighthouse", "Virginia Woolf", "EN"),
                new BookModel("978-83-456-43-3", "Heart of Darkness", "Joseph Conrad", "EN"),
                new BookModel("978-83-456-651-2", "The Sound and the Fury", "William Faulkner", "EN"),
                new BookModel("978-83-3452-8762-1", "To Kill a Mockingbird", "Harper Lee", "EN")
        );
        for (int i = 0; i < books.size(); i++) {
            if (i < books.size() / 4) {
                books.get(i).setOwner(users.get(0));
            } else if (i >= books.size() / 4 & i < books.size() / 2) {
                books.get(i).setOwner(users.get(1));
            } else {
                books.get(i).setOwner(users.get(2));
            }
        }
        books.get(books.size() - 1).setBorrower(users.get(0));
        books.get(books.size() - 1).setReturnDate(LocalDate.now().plusMonths(1));
        books.get(books.size() - 2).setBorrower(users.get(0));
        books.get(books.size() - 2).setReturnDate(LocalDate.now().plusMonths(1));
        bookRepository.saveAll(books);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
