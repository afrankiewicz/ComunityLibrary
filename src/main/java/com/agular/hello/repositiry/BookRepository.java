package com.agular.hello.repositiry;

import com.agular.hello.entity.Book;
import com.agular.hello.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    boolean existsByIsbn(String isbn);
    List<Book> getBooksByOwner(User user);
    List<Book> getBooksByBorrower(User user);
    @Query(value = "SELECT * FROM books WHERE borrower_id IS null AND owner_id != ?1",
            nativeQuery = true)
    List<Book> getAllAvailable(Long ownerId);

}
